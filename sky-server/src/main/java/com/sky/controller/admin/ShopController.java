package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "Shop Apis")
@Slf4j
public class ShopController {
    static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("/{status}")
    @ApiOperation("set shop status")
    public Result setStaus(@PathVariable Integer status) {
        log.info("shop status:{}", status == 1? "opening" : "closing");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }
    @GetMapping()
    @ApiOperation("get shop status")
    public Result<Integer> getShopStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("shop status:{}", status == 1? "opening" : "closing");
        return Result.success(status);
    }



}
