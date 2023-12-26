package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Log;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Promotion;
import com.usst.usstcafeteriahub.service.PromotionService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 促销 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/promotions/actions")
public class PromotionController {

    @Resource
    private PromotionService promotionService;


    @Log
    @ApiOperation("获取促销菜品列表")
    @GetMapping("/getPromotion")
    public BaseResponse getPromotion() {
        List<Promotion> list = promotionService.list();
        log.info("获取促销菜品列表: {}", list);
        return Result.success(list);
    }


    @Log
    @ApiOperation("删除促销菜品")
    @PostMapping("/deletePromotion")
    public BaseResponse deletePromotion(@RequestBody Promotion promotion) {
        if (promotion == null) {
            return Result.error("参数为空");
        }
        log.info("要删除促销菜品: {}", promotion);
        boolean remove = promotionService.removeById(promotion.getPromotionId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }


    @Log
    @ApiOperation("添加促销菜品")
    @PostMapping("/addPromotion")
    public BaseResponse addPromotion(@RequestBody Promotion promotion) {
        if (promotion == null) {
            return Result.error("参数为空");
        }
        log.info("添加促销菜品: {}", promotion);
        boolean save = promotionService.save(promotion);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }


    @Log
    @ApiOperation("修改促销菜品")
    @PostMapping("/updatePromotion")
    public BaseResponse updatePromotion(@RequestBody Promotion promotion) {
        if (promotion == null) {
            return Result.error("参数为空");
        }
        log.info("修改促销菜品: {}", promotion);
        boolean update = promotionService.updateById(promotion);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }


}
