package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.DishDisableFailedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void saveWithFlavor(DishDTO dishDTO) {
        // save dish to dish table
        // create a dish object
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);

        // get auto-generated dishId, primary key return
        Long dishId = dish.getId();

        // save flavors to flavor table, note that flavors are optional
        List<DishFlavor> flavors = dishDTO.getFlavors();

        if(flavors != null && flavors.size() > 0){
            // assign dishId to flavor object
            flavors.forEach(flavor -> {
                flavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }


    }

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        // start paging, inject para of LIMIT
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        // get list of DishVO
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());


    }

    @Override
    public void deleteBatch(List<Long> ids) {

        for(Long id : ids){
            Dish dish = dishMapper.getById(id);
            // decide the status of dishes, dishes on sale can't be deleted
            if(dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }

            // TODO: delete dishes and flavors in a single sql request instead of doing it in a for loop
            // decide whether the dish is being in a set, if yes, can't be deleted
            List<Long> setmealIds = setmealMapper.getSetmealIdByDishId(id);
            if(setmealIds != null && setmealIds.size() > 0){
                throw new DeletionNotAllowedException(dish.getName() + MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }

            // delete the dishes
            dishMapper.deleteById(id);

            // delete the flavors wired with dishes
            dishFlavorMapper.deleteByDishId(id);


        }


    }

    @Override
    public DishVO getByIdWithFlavor(Long id) {
        // get dish
        Dish dish = dishMapper.getById(id);

        // get flavors
        List<DishFlavor> flavors = dishFlavorMapper.getFlavorByDishId(id);

        // construct dishVO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        // update dish
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        // delete original flavors
        dishFlavorMapper.deleteByDishId(dish.getId());

        // add new flavors
        dishFlavorMapper.insertBatch(dishDTO.getFlavors());
    }

    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        // when trying to disable a dish, decide whether it belongs to the setmeal
        if(status == StatusConstant.DISABLE){
            // get setmeal data by joining the setmeal table and setmeal_dish table
            List<Setmeal> setmeals = setmealMapper.getByDishId(id);
            if(setmeals != null && setmeals.size() > 0){
                throw new DishDisableFailedException(MessageConstant.DISH_DISABLE_FAILURE);
            }
        }
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();
        dishMapper.update(dish);
    }
}
