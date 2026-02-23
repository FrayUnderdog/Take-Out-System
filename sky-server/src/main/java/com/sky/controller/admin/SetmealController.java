package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
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

    /**
     * page query
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("page query")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("page query: {}", setmealPageQueryDTO);
        PageResult listSetMeal = setmealService.page(setmealPageQueryDTO);
        return Result.success(listSetMeal);
    }

    /**
     * delete Setmeals by ids
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("delete Setmeals by ids")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("delete these setmeals by id: {}", ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * change the status of this set meal
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("change the status of this set meal")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("change the status of this set meal with id: {}",  id);
        setmealService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * update Setmeal
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("update Setmeal")
    public Result<SetmealVO> update(@RequestBody SetmealDTO setmealDTO) {
        log.info("update Setmeal: {}", setmealDTO);
        SetmealVO updatedSetmealVO = setmealService.update(setmealDTO);
        return Result.success(updatedSetmealVO);
    }

    @GetMapping("/{id}")
    @ApiOperation("get Setmeal by id")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("get Setmeal by id: {}", id);
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }
}
