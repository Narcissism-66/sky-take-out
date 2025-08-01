package com.sky.service;

/*
 * @Auther:fz
 * @Date:2025/8/1
 * @Description:
 */

import com.sky.dto.ShoppingCartDTO;

public interface ShoppingCartService {
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
