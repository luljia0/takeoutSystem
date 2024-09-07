package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "Dish Apis")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @ApiOperation("add new dish")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("add new dish" + dishDTO.toString());

        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("dish paging")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("dish paging query" + dishPageQueryDTO.toString());
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }
}
