package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Cafeteria;
import com.usst.usstcafeteriahub.model.entity.CafeteriaRemark;
import com.usst.usstcafeteriahub.service.CafeteriaRemarkService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 食堂评价 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/cafeteriaRemarks/actions")
public class CafeteriaRemarkController {
    @Resource
    private CafeteriaRemarkService cafeteriaRemarkService;

    @ApiOperation("查询食堂评价列表")
    @GetMapping("/getCafeteriaRemarks")
    public BaseResponse getCafeteriaRemarks() {
        List<CafeteriaRemark> list = cafeteriaRemarkService.list();
        log.info("获取食堂评价列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("按照食堂ID筛选食堂评价")
    @GetMapping("/getCafeteriaRemarksByCafeteriaID")

    public BaseResponse getCafeteriaRemarksByCafeteriaID(@RequestParam Long id){
        if(id == null){
            return Result.error("参数为空");
        }
        log.info("传入食堂id参数: {}", id);
        List<CafeteriaRemark> list = cafeteriaRemarkService.getCafeteriaRemarksByCafeteriaID(id);
        log.info("获取食堂评价列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("按照用户ID筛选食堂评价")
    @GetMapping("/getCafeteriaRemarksByUserID")
    public BaseResponse getCafeteriaRemarksByUserID(@RequestParam Long id){
        if(id == null){
            return Result.error("参数为空");
        }
        log.info("传入用户id参数: {}", id);
        List<CafeteriaRemark> list = cafeteriaRemarkService.getCafeteriaRemarksByUserID(id);
        log.info("获取食堂评价列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("添加食堂评价")
    @PostMapping("/addCafeteriaRemark")
    public BaseResponse addCafeteriaRemark(@RequestBody CafeteriaRemark cafeteriaRemark) {
        if (cafeteriaRemark == null) {
            return Result.error("参数为空");
        }
        log.info("添加食堂评价: {}", cafeteriaRemark);
        return cafeteriaRemarkService.addcafeteriaRemark(cafeteriaRemark);
    }

    @ApiOperation("删除食堂评价")
    @PostMapping("/deleteCafeteriaRemark")
    public BaseResponse deleteCafeteriaRemark(@RequestBody CafeteriaRemark cafeteriaRemark) {
        if (cafeteriaRemark == null) {
            return Result.error("参数为空");
        }
        log.info("要删除食堂评价: {}", cafeteriaRemark);
        boolean remove = cafeteriaRemarkService.removeById(cafeteriaRemark.getCafeteriaId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("修改食堂评价")
    @PostMapping("/updatecafeteriaRemark")
    public BaseResponse updateCafeteria(@RequestBody CafeteriaRemark cafeteriaRemark) {
        if (cafeteriaRemark == null) {
            return Result.error("参数为空");
        }
        log.info("修改食堂评价: {}", cafeteriaRemark);
        boolean update = cafeteriaRemarkService.updateById(cafeteriaRemark);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }

}
