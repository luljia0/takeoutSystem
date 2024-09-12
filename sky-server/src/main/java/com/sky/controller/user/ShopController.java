package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "User Apis")
@Slf4j
public class ShopController {
    static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping()
    @ApiOperation("get shop status")
    public Result<Integer> getShopStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("shop status:{}", status == 1? "opening" : "closing");
        return Result.success(status);
    }



}

