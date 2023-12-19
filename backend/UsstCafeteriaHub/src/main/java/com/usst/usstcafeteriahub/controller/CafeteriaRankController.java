package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.CafeteriaRank;
import com.usst.usstcafeteriahub.service.CafeteriaRankService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 食堂排名 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/cafeteriaRanks/actions")
public class CafeteriaRankController {
    @Resource
    private CafeteriaRankService cafeteriaRankService;

    @ApiOperation("获取食堂排行榜,totalScore,由高到低排列")
    @GetMapping("/getCafeteriaRank")
    public BaseResponse getCafeteriaRank() {
        List<CafeteriaRank> cafeteriaRanks = cafeteriaRankService.getCafeteriaRank();
        log.info("食堂排行榜: {}", cafeteriaRanks);
        return Result.success(cafeteriaRanks);
    }

}
