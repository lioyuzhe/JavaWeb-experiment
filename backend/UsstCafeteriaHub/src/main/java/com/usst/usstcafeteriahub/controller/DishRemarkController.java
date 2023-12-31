package com.usst.usstcafeteriahub.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Log;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.DishRemark;
import com.usst.usstcafeteriahub.service.DishRemarkService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品评价 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/dishRemarks/actions")
public class DishRemarkController {
    @Resource
    private DishRemarkService dishRemarkService;


    @Log
    @ApiOperation("根据菜品ID获取菜品评价列表")
    @GetMapping("/getDishRemark")
    public BaseResponse getDishRemarkByDishId(@RequestParam("dishId") Long dishId) {
        if (dishId == null) {
            return Result.error("参数为空");
        }
        // 根据菜品id获取菜品评价列表
        QueryWrapper<DishRemark> wrapper = new QueryWrapper<>();
        wrapper.eq("dish_id", dishId);
        List<DishRemark> list = dishRemarkService.list(wrapper);
        log.info("根据菜品id获取菜品评价列表: {}", list);
        return Result.success(list);
    }


    @Log
    @ApiOperation("用户查看当前菜品自己的评价")
    @GetMapping("/getDishRemarkByUserIdAndDishId")
    public BaseResponse getDishRemarkByUserIdAndDishId(@RequestParam("userId") Long userId, @RequestParam("dishId") Long dishId) {
        if (userId == null || dishId == null) {
            return Result.error("参数为空");
        }
        DishRemark dishRemark = dishRemarkService.getDishRemarkByUserIdAndDishId(userId, dishId);
        if (dishRemark == null) {
            return Result.error("尚未评价");
        }
        log.info("根据用户id和菜品id获取菜品评价: {}", dishRemark);
        return Result.success(dishRemark);
    }


    @Log
    @ApiOperation("用户添加&修改菜品评价，如果已经评价过则更新评价")
    @PostMapping("/addDishRemark")
    public BaseResponse addDishRemark(@RequestBody DishRemark dishRemark) {
        if (dishRemark == null) {
            return Result.error("参数缺少");
        }
        log.info("添加菜品评价: {}", dishRemark);
        return dishRemarkService.addDishRemark(dishRemark);
    }


    @Log
    @ApiOperation("删除菜品评价")
    @PostMapping("/deleteDishRemark")
    public BaseResponse deleteDishRemark(@RequestBody DishRemark dishRemark) {
        if (dishRemark == null) {
            return Result.error("参数为空");
        }
        log.info("要删除菜品评价: {}", dishRemark);
        boolean remove = dishRemarkService.removeById(dishRemark.getRemarkId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    // 废置 ，和添加菜品合并
    //@Log
    // @ApiOperation("更新菜品评价")
    // @PostMapping("/updateDishRemark")
    // public BaseResponse updateDishRemark(@RequestBody DishRemark dishRemark) {
    //     if (dishRemark == null) {
    //         return Result.error("参数为空");
    //     }
    //     log.info("要更新菜品评价: {}", dishRemark);
    //     boolean update = dishRemarkService.updateById(dishRemark);
    //     if (!update) {
    //         return Result.error("更新失败");
    //     }
    //     return Result.success("更新成功");
    // }

}
