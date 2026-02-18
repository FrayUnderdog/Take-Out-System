package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * get setmealIds by dishId
     * @param ids
     * @return
     */
    @AutoFill(value = OperationType.INSERT)
    // slq: select setmeal_id from setmeal_dish where dish_id in (?,?,?)
    public List<Long> getSetmealIdsByDishId(List<Long> ids);

    /**
     * add setmealDishes
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
}