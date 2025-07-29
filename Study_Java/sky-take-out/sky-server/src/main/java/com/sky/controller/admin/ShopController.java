package com.sky.controller.admin;

/*
 * @Auther:fz
 * @Date:2025/7/29
 * @Description:
 */

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺管理")
public class ShopController {
    public static final String KEY="SHOP_STATUS";

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 设置店铺的营业状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺的营业状态")
    public Result setStatus(@PathVariable Integer status){
        redisTemplate.opsForValue().set(KEY, String.valueOf(status));
        return Result.success();
    }

    /**
     * 获取店铺的营业状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus(){
        Integer status = Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(KEY)));
        return Result.success(status);
    }

}
