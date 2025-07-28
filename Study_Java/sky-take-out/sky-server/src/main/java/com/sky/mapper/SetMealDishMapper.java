package com.sky.mapper;

/*
 * @Auther:fz
 * @Date:2025/7/28
 * @Description:
 */

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
}
