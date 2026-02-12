package com.sky.service;

import com.sky.dto.DishDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
public interface DishService {

    /**
     * save dish and corresponding dishFlavors
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);
}
