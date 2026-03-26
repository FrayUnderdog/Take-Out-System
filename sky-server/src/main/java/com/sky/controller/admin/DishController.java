package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "Dish related interfaces")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

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

        // clean cache specifically
        String key = "dish_" + dishDTO.getCategoryId();
        redisTemplate.delete(key);

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

    /**
     * delete dishes by id
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("delete dishes by id")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("delete these dishes by id: {}", ids);
        dishService.deleteBatch(ids);

        // clean cache
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * get dish by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("get dish by id")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("get dish by id: {}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);

        return Result.success(dishVO);
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
     * update dish with flavors info by id
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("update dish with flavors info by id")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("update dish: {}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        // clean cache
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * clean redis cache
     * @param pattern
     */
    private void cleanCache(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
