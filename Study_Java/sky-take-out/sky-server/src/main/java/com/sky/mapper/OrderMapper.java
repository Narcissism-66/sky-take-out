package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/*
 * @Auther:fz
 * @Date:2025/8/1
 * @Description:
 */
@Mapper
public interface OrderMapper {
    /**
     * 向订单中插入一条数据
     * @param orders
     */
    void insert(Orders orders);
}
