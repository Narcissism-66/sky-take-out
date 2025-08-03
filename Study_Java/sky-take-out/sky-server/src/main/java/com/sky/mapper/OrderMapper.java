package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

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


    Page<Orders> historyOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单
     * @param id
     */
    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    /**
     * 更新订单状态
     * @param orders
     */
    void update(Orders orders);

    /**
     * 分页查询订单
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);
    /**
     * 根据状态统计订单数量
     * @param status
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 查询订单状态为已下单且下单时间早于指定时间的订单
     * @param status
     * @param orderTime
     * @return
     */
    @Select("SELECT * FROM orders WHERE status = #{status} AND order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

}
