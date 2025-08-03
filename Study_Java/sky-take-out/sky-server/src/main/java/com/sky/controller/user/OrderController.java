package com.sky.controller.user;

/*
 * @Auther:fz
 * @Date:2025/8/1
 * @Description:
 */

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "用户订单相关接口")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        OrderSubmitVO orderSubmitVO=orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    @GetMapping("/historyOrders")
    @ApiOperation("历史订单查询")
    public Result<PageResult> historyOrders(int page, int pageSize, Integer status){
        PageResult pageResult=orderService.historyOrders(page, pageSize, status);
        return Result.success(pageResult);
    }

    @GetMapping("/orderDetail/{id}")
    @ApiOperation("订单详情查询")
    public Result<OrderVO> detail(@PathVariable Long id){
        OrderVO orderVO=orderService.detail(id);
        return Result.success(orderVO);
    }

    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result<String> cancel(@PathVariable Long id){
        orderService.userCancelById(id);
        return Result.success("取消成功");
    }

    /**
     * 再来一单
     *
     * @param id
     * @return
     */
    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result repetition(@PathVariable Long id) {
        orderService.repetition(id);
        return Result.success();
    }

}
