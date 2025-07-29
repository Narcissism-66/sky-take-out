package com.sky.controller.admin.user;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺管理")
public class ShopController {

    public static final String KEY="SHOP_STATUS";
    @Autowired
    StringRedisTemplate redisTemplate;

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
