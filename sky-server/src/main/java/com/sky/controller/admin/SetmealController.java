package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "Setmeal related interfaces")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishService dishService;

    /**
     * create Setmeal
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("create Setmeal")
    public Result createSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("create Setmeal: {}", setmealDTO);
        setmealService.create(setmealDTO);
        return Result.success();
    }

    /**
     * list dishes by categoryId
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("list dishes by categoryId")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("list dishes by categoryId: {}", categoryId);
        List<Dish> listDishes = dishService.list(categoryId);
        return Result.success(listDishes);
    }
}
