package com.sky.mapper;

/*
 * @Auther:fz
 * @Date:2025/8/1
 * @Description:
 */

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    /**
     * 批量插入
     * @param orderDetails
     */
    void insertBatch(List<OrderDetail> orderDetails);
}
