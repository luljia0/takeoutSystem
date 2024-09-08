package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DishService {
    /**
     * save new dish with flavors
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * dish paging query
     * @param dishPageQueryDTO
     * @return
     */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * delete dish(es) by id
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
