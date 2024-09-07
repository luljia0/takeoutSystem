package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * insert a batch of flavor
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

}
