package com.sky.mapper;

import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * get related setmealIds by dishId
     * @param id
     * @return
     */
    @Select("SELECT id FROM setmeal WHERE  id = #{id}")
    List<Long> getSetmealIdByDishId(Long id);
}
