package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    DishMapper dishMapper;
    @Autowired
    SetmealDishMapper setmealDishMapper;

    /**
     * create Setmeal
     * @param setmealDTO
     * @return
     */
    @Override
    @Transactional
    public void create(SetmealDTO setmealDTO) {
        // sql: insert into setmeal (name, price, status, category_id, create_time, update_time, create_user, update_user) values (?, ?, ?, ?, ?, ?, ?, ?)

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // insert set meal into table setmeal
        setmealMapper.insert(setmeal);

        // update bond info with setmeal_id and dish_id in table setmeal_dish
        Long setmealId = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });

        setmealDishMapper.insertBatch(setmealDishes);
    }


    /**
     *  query setmeals by page
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        // get setmeals from DB
        // SQL: selecet * from setmeal like '%name%' limit 0, 10
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<Setmeal> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        List<SetmealVO> listSetMeal = new ArrayList<>();

        // get related setmeal_dish of each setmeal from DB & assign to each setmeal
        for (Setmeal setmeal : page) {
            SetmealVO setmealVO = new SetmealVO();
            // get related setmeal_dish by setmeal_id
            List<SetmealDish> setmealDishes = setmealDishMapper.getSetmealDishesBySetmealId(setmeal.getId());
            BeanUtils.copyProperties(setmeal, setmealVO);
            setmealVO.getSetmealDishes().addAll(setmealDishes);
            listSetMeal.add(setmealVO);
        }

        return new PageResult(page.getTotal(), listSetMeal);
    }

    /**
     * delete Setmeals by ids
     * @param ids
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        // delete related setmeal_dish in table setmeal_dish
        ids.forEach(id -> {
            setmealDishMapper.deleteBySetmealId(id);
        });

        // delete setmeals in table setmeal
        // SQL: delete from setmeal where id in (?, ?, ?)
        setmealMapper.deleteBatch(ids);
    }

    /**
     * change the status of this set meal
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        //起售套餐时，判断套餐内是否有停售菜品，有停售菜品提示"套餐内包含未启售菜品，无法启售"
        if(status == StatusConstant.ENABLE) {
            //select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = ?
            List<Dish> dishList = dishMapper.getBySetmealId(id);
            if (dishList != null && dishList.size() > 0) {
                dishList.forEach(dish -> {
                    if (StatusConstant.DISABLE == dish.getStatus()) {
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }
        }
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.update(setmeal);
    }

    /**
     * update Setmeal
     * @param setmealDTO
     * @return
     */
    @Override
    @Transactional
    public SetmealVO update(SetmealDTO setmealDTO) {
        // get setmeal info
        Long setmealId = setmealDTO.getId();
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // insert new setmeal into table setmeal
        setmealMapper.update(setmeal);

        // delete all related setmeal_dish in table setmeal_dish
        setmealDishMapper.deleteBySetmealId(setmealId);

        // insert new setmeal_dish into table setmeal_dish
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        // set setmeal_id for each setmeal_dish
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
        }
        setmealDishMapper.insertBatch(setmealDishes);

        // bond new setmeal_dish with setmeal to form updated setmeal
        SetmealVO updatedSetmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, updatedSetmealVO);
        updatedSetmealVO.getSetmealDishes().addAll(setmealDishes);

        return updatedSetmealVO;
    }

    /**
     * query setmeal by id
     * @param id
     * @return
     */
    @Override
    public SetmealVO getById(Long id) {
        Setmeal setmeal = setmealMapper.getSetmealById(id);
        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDishMapper.getSetmealDishesBySetmealId(setmealId);

        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
