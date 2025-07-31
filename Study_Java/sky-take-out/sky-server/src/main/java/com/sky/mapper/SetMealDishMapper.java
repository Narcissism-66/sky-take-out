package com.sky.mapper;

/*
 * @Auther:fz
 * @Date:2025/7/28
 * @Description:
 */

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    /**
     * 根据菜品id查询套餐id
     * @param dishId
     * @return
     */
    List<Long> getSetMealIdsByDishIds(List<Long>dishIds);

    /**
     * 套餐里面添加菜品
     * @param setmealDishes
     */
    void insert(List<SetmealDish> setmealDishes);

    /**
     * 批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据套餐id查询菜品
     * @param id
     */
    List<SetmealDish> getById(Long id);

    /**
     * 根据套餐id删除菜品
     * @param id
     */
    @Delete("DELETE FROM setmeal_dish WHERE setmeal_id = #{id}")
    void delete(Long id);
}
