package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {

    /**
     * save dish and corresponding dishFlavors
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * query dish page
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * delete dishes by id
     * @param ids
     * @return
     */
    void deleteBatch(List<Long> ids);

    /**
     * get dish info by id
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * update dish with flavors info by id
     * @param dishDTO
     * @return
     */
    void updateWithFlavor(DishDTO dishDTO);
}
