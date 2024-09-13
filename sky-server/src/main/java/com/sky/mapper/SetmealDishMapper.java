package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SetmealDishMapper {
    /**
     * insert relationship between dish and setmeals in a batch
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
}
