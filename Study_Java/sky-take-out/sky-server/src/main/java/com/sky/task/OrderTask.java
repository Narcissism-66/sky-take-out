package com.sky.task;

/*
 * @Auther:fz
 * @Date:2025/8/3
 * @Description:
 */

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTask {
    @Autowired
    OrderMapper orderMapper;

    /**
     * 处理订单超时
     */
    @Scheduled(cron = "0 * * * * *")
    public void processTimeoutOrder(){
        LocalDateTime time=LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList=orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT,time);
        if (ordersList!=null && !ordersList.isEmpty()){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时，自动取消");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }

    /**
     * 处理配送订单
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void processDeliveryOrder(){
        LocalDateTime time=LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList=orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS,time);
        if (ordersList!=null && !ordersList.isEmpty()){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.CANCELLED);
                orderMapper.update(orders);
            }
        }

    }
}
