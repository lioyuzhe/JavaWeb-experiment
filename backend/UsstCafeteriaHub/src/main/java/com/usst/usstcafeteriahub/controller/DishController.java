package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Dish;
import com.usst.usstcafeteriahub.service.DishService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Dish接口
 * @author 黄泽旭
 */
@RestController
@Slf4j
@RequestMapping("/dishes/actions")
public class DishController {
    @Resource
    private DishService dishService;

    @ApiOperation("获取所有菜品")
    @GetMapping("/getDishes")
    public BaseResponse getDishes() {
        List<Dish> list = dishService.list();
        log.info("获取所有菜品: {}", list);
        return Result.success(list);
    }

    @ApiOperation("删除菜品")
    @PostMapping("/deleteDish")
    public BaseResponse deleteDish(@RequestBody Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        log.info("要删除菜品: {}", dish);
        boolean remove = dishService.removeById(dish.getDishId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("添加菜品")
    @PostMapping ("/addDish")
    public BaseResponse addDish(@RequestBody Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        log.info("添加菜品: {}", dish);
        boolean save = dishService.save(dish);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @ApiOperation("修改菜品")
    @PostMapping("/updateDish")
    public BaseResponse updateDish(@RequestBody Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        log.info("修改菜品: {}", dish);
        boolean update = dishService.updateById(dish);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }


}
