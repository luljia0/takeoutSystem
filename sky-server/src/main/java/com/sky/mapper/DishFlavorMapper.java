package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * insert a batch of flavor
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * delete flavors related to dish id
     * @param dishId
     */
    @Delete("DELETE FROM dish_flavor WHERE dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    @Select("SELECT  * FROM dish_flavor WHERE dish_id = #{dishId}")
    List<DishFlavor> getFlavorByDishId(Long dishId);
}
