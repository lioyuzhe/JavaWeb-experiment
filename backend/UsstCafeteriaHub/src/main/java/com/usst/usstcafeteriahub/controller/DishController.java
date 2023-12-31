package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Log;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Dish;
import com.usst.usstcafeteriahub.service.DishRankService;
import com.usst.usstcafeteriahub.service.DishService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品 接口
 * @author 黄泽旭
 */
@RestController
@Slf4j
@RequestMapping("/dishes/actions")
public class DishController {
    @Resource
    private DishService dishService;



    @Log
    @ApiOperation("获取所有菜品")
    @GetMapping("/getDishes")
    public BaseResponse getDishes() {
        List<Dish> list = dishService.list();
        log.info("获取所有菜品: {}", list);
        return Result.success(list);
    }


    @Log
    @ApiOperation("根据菜品的菜系获取菜品列表")
    @GetMapping("/getDishByCuisine")
    public BaseResponse getDishByCuisine(@RequestParam("cuisine") String cuisine) {
        List<Dish> list = dishService.getDishByCuisine(cuisine);
        log.info("根据菜品的菜系获取菜品列表: {}", list);
        return Result.success(list);
    }


    @Log
    @ApiOperation("根据菜品所属食堂获取菜品列表")
    @GetMapping("/getDishByCafeteriaID")
    public BaseResponse getDishByCafeteriaID(@RequestParam("cafeteriaId") Long cafeteriaId) {
        List<Dish> list = dishService.getDishByCafeteriaID(cafeteriaId);
        log.info("根据菜品所属食堂获取菜品列表: {}", list);
        return Result.success(list);
    }



    @Log
    @ApiOperation("获取所有菜品的列表，并按菜品价格排序,接受参数order，1为从低到高，2为从高到低")
    @GetMapping("/getDishOrderByPrice")
    public BaseResponse getDishOrderByPriceDesc(@RequestParam("order") Integer order) {
        List<Dish> list = dishService.getDishOrderByPrice(order);
        log.info("获取所有菜品的列表，并按菜品价格排序: {}", list);
        return Result.success(list);
    }


    @Log
    @ApiOperation("删除菜品")
    @PostMapping("/deleteDish")
    public BaseResponse deleteDish(@RequestBody Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        log.info("要删除菜品: {}", dish);
        return dishService.removeDish(dish);
    }


    @Log
    @ApiOperation("添加菜品")
    @PostMapping ("/addDish")
    public BaseResponse addDish(@RequestBody Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        log.info("添加菜品: {}", dish);
        return dishService.add(dish);
    }


    @Log
    @ApiOperation("修改菜品")
    @PostMapping("/updateDish")
    public BaseResponse updateDish(@RequestBody Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        log.info("修改菜品: {}", dish);
        return dishService.updateDish(dish);
    }


    @Log
    @ApiOperation("获取所有推荐菜品列表（status=1）")
    @GetMapping("/getRecommendDish")
    public BaseResponse getRecommendDish() {
        List<Dish> list = dishService.getRecommendDish();
        log.info("获取所有推荐菜品: {}", list);
        return Result.success(list);
    }


    // 在你的Controller中
    @Log
    @ApiOperation("获取前5个推荐菜品列表（status=1）")
    @GetMapping("/getTop5RecommendDish")
    public BaseResponse getTop5RecommendDish() {
        List<Dish> list = dishService.getTopNRecommendDish(5);
        log.info("获取前5个推荐菜品: {}", list);
        return Result.success(list);
    }

}
