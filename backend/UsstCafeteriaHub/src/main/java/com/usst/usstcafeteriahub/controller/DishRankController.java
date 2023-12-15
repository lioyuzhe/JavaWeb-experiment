package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.DishRank;
import com.usst.usstcafeteriahub.service.DishRankService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DishRank 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/dishRanks/actions")
public class DishRankController {
    @Resource
    private DishRankService dishRankService;

    @ApiOperation("获取菜品排行榜,Score由高到低排列")
    @GetMapping("/getDishRank")
    public BaseResponse getDishRank() {
        List<DishRank> list = dishRankService.getDishRank();
        log.info("获取菜品排行榜: {}", list);
        return Result.success(list);
    }



}
