package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Log;
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
 * 菜品排名 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/dishRanks/actions")
public class DishRankController {
    @Resource
    private DishRankService dishRankService;


    @Log
    @ApiOperation("获取菜品排行榜,根据 平均score：total_score/数据数 由高到低排列")
    @GetMapping("/getDishRank")
    public BaseResponse getDishRank() {
        List<DishRank> list = dishRankService.getDishRank();
        log.info("获取菜品排行榜: {}", list);
        return Result.success(list);
    }

    // 不开设菜品排名的添加，这个行为在添加菜品时自动完成
}
