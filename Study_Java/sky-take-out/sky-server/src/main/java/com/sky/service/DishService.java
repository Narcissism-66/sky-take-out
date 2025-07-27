package com.sky.service;

/*
 * @Auther:fz
 * @Date:2025/7/27
 * @Description:
 */

import com.sky.dto.DishDTO;

public interface DishService {
    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);
}
