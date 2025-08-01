package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/*
 * @Auther:fz
 * @Date:2025/8/1
 * @Description:
 */
@Mapper
public interface ShoppingCartMapper {

    /**
     * 根据条件查询购物车列表
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新商品数量
     * @param cart
     */
    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void update(ShoppingCart cart);

    /**
     * 添加购物车数据
     * @param shoppingCart
     */
    @Insert("INSERT INTO shopping_cart (name,user_id, dish_id, setmeal_id, dish_flavor, number, amount, image,create_time)" +
            "VALUES (#{name},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{image},#{createTime})")
    void insert(ShoppingCart shoppingCart);

    @Delete("DELETE FROM shopping_cart WHERE user_id=#{currentId}")
    void clean(Long currentId);
}
