package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import java.util.List;

public interface CategoryService {

    /**
     * create new category
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * page query for category
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * delete category
     * @param id
     */
    void deleteById(Long id);

    /**
     * update category
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * start or stop category
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * get category list
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
