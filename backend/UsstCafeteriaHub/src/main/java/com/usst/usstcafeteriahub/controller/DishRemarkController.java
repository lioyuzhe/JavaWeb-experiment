package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.DishRemark;
import com.usst.usstcafeteriahub.service.DishRemarkService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * DishRemark 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/dishRemarks/actions")
public class DishRemarkController {
    @Resource
    private DishRemarkService dishRemarkService;

    @ApiOperation("根据菜品ID获取菜品评价")
    @GetMapping("/getDishRemark")
    public BaseResponse getDishRemark(@RequestParam("dishId") Integer dishId) {
        DishRemark dishRemark = dishRemarkService.getById(dishId);
        log.info("根据菜品ID获取菜品评价: {}", dishRemark);
        return Result.success(dishRemark);
    }

}
