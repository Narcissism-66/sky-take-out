package com.sky.mapper;

/*
 * @Auther:fz
 * @Date:2025/7/27
 * @Description:
 */

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味数据
     * @param flavorList
     */
    void insertBatch(List<DishFlavor> flavorList);

    /**
     * 根据菜品数据删除口味数据
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据菜品id查询口味数据
     * @param id
     * @return
     */
    List<DishFlavor> getByDishId(Long dishId);

    /**
     * 根据菜品id删除口味数据
     * @param id
     */
    void deleteByDishId(Long id);
}
