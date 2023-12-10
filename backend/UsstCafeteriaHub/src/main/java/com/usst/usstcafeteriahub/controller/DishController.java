package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Dish;
import com.usst.usstcafeteriahub.service.DishService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("/deleteDish")
    public BaseResponse deleteDish(Dish dish) {
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
    @GetMapping("/addDish")
    public BaseResponse addDish(Dish dish) {
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
    @PutMapping("/updateDish")
    public BaseResponse updateDish(Dish dish) {
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
