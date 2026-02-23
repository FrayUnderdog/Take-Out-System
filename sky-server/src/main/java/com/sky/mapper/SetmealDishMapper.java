package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * get setmealIds by dishId
     * @param ids
     * @return
     */
    // SQL: select setmeal_id from setmeal_dish where dish_id in (?,?,?)
    List<Long> getSetmealIdsByDishId(List<Long> ids);

    /**
     * add setmealDishes
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * get setmealDishes by setmealId
     * @param id
     * @return
     */
    // SQL: select * from setmeal_dish where setmeal_id = ?
    List<SetmealDish> getSetmealDishesBySetmealId(Long id);

    /**
     * delete setmealDishes by setmealId
     * @param id
     */
    @Delete
   ("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);
}