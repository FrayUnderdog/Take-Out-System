package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * create Setmeal
     * @param setmealDTO
     * @return
     */
    void create(SetmealDTO setmealDTO);

    /**
     * page query
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * delete Setmeals by ids
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * change the status of this set meal
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * update Setmeal
     * @param setmealDTO
     * @return
     */
    SetmealVO update(SetmealDTO setmealDTO);

    /**
     * query setmeal by id
     * @param id
     * @return
     */
    SetmealVO getById(Long id);
}
