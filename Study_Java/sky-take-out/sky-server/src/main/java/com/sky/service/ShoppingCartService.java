package com.sky.service;

/*
 * @Auther:fz
 * @Date:2025/8/1
 * @Description:
 */

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 获取购物车内容
     * @return
     */
    List<ShoppingCart> listShoppingCart();

    /**
     * 清空购物车
     */
    void cleanShoppingCart();
}
