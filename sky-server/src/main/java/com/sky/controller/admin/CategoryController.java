package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * admin category management
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "category management")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * create new category
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("create new category")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO){
        log.info("create new category：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * query category by page
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("query category by page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("query category by page：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * delete category
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("delete category")
    public Result<String> deleteById(Long id){
        log.info("delete category：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * update category
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("update category")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * start or stop category
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("start or stop category")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * query category list by type
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("query category list by type")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
