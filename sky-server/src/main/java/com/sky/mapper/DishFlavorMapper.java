package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * insert flavors in batch
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * delete flavors by dishIds
     * @param dishIds
     */
    void deleteByDishIds(List<Long> dishIds);

    /**
     * get flavors by dishId
     * @param dishId
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
