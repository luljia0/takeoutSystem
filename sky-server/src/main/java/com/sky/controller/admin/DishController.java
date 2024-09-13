package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "Dish Apis")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @ApiOperation("add new dish")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("add new dish" + dishDTO.toString());

        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("dish paging")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("dish paging query" + dishPageQueryDTO.toString());
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    @DeleteMapping
    @ApiOperation("delete dish(es)")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("delete dish" + ids.toString());
        dishService.deleteBatch(ids);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("get dish by id")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("get Dish by id" + id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }
    @PutMapping
    @ApiOperation("update dish info")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("update dish" + dishDTO.toString());
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * get dishes by category id
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("get dishes by category id")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }
}
