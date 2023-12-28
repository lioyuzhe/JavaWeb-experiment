package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Log;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Cafeteria;
import com.usst.usstcafeteriahub.model.entity.Promotion;
import com.usst.usstcafeteriahub.service.CafeteriaService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 食堂 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/cafeterias/actions")
public class CafeteriaController {
    @Resource
    private CafeteriaService cafeteriaService;


    @Log
    @ApiOperation("查询食堂信息")
    @GetMapping("/getCafeteria")
    public BaseResponse getCafeteria() {
        List<Cafeteria> list = cafeteriaService.list();
        log.info("获取食堂列表: {}", list);
        return Result.success(list);
    }


    @Log
    @ApiOperation("添加食堂")
    @PostMapping("/addCafeteria")
    public BaseResponse addCafeteria(@RequestBody Cafeteria cafeteria, HttpServletRequest request) {
        if (cafeteria == null) {
            return Result.error("参数为空");
        }
        log.info("添加食堂: {}", cafeteria);
        return cafeteriaService.add(cafeteria, request);
    }


    @Log
    @ApiOperation("删除食堂")
    @PostMapping("/deleteCafeteria")
    public BaseResponse deleteCafeteria(@RequestBody Cafeteria cafeteria) {
        if (cafeteria == null) {
            return Result.error("参数为空");
        }
        log.info("要删除食堂: {}", cafeteria);
        boolean remove = cafeteriaService.removeById(cafeteria.getCafeteriaId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }


    @Log
    @ApiOperation("修改食堂信息")
    @PostMapping("/updateCafeteria")
    public BaseResponse updateCafeteria(@RequestBody Cafeteria cafeteria) {
        if (cafeteria == null) {
            return Result.error("参数为空");
        }
        log.info("修改食堂: {}", cafeteria);
        boolean update = cafeteriaService.updateById(cafeteria);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }





}
