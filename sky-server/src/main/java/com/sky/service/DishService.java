package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
