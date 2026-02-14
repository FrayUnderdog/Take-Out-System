package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "Dish related interfaces")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * create & save new dish
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("create & save new dish")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("create & save new dish: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * query dish page
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("query dish page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("query dish page: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return new Result().success(pageResult);
    }
}
