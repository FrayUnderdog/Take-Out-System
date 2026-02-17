package com.sky.mapper;

import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * get setmealIds by dishId
     * @param ids
     * @return
     */
    // slq: select setmeal_id from setmeal_dish where dish_id in (?,?,?)
    public List<Long> getSetmealIdsByDishId(List<Long> ids);
}