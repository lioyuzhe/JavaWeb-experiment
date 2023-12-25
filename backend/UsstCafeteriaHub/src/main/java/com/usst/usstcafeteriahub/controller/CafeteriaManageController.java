package com.usst.usstcafeteriahub.controller;


import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Log;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Admin;
import com.usst.usstcafeteriahub.model.entity.Cafeteria;
import com.usst.usstcafeteriahub.model.entity.CafeteriaAdmin;
import com.usst.usstcafeteriahub.model.entity.CafeteriaManage;
import com.usst.usstcafeteriahub.service.AdminService;
import com.usst.usstcafeteriahub.service.CafeteriaAdminService;
import com.usst.usstcafeteriahub.service.CafeteriaManageService;
import com.usst.usstcafeteriahub.service.CafeteriaService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 食堂管理员食堂分配 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/cafeteriaManages/actions")
public class CafeteriaManageController {
    @Resource
    private CafeteriaManageService cafeteriaManageService;
    @Resource
    private CafeteriaAdminService adminService;
    @Resource
    private CafeteriaService cafeteriaService;

    @Log
    @ApiOperation("查询食堂管理列表")
    @GetMapping("/getCafeteriaManages")
    public BaseResponse getCafeteriaManages() {
        List<CafeteriaManage> list = cafeteriaManageService.list();
        log.info("获取食堂管理列表: {}", list);
        return Result.success(list);
    }

    @Log
    @ApiOperation("按照食堂ID筛选食堂管理")
    @GetMapping("/getCafeteriaManagesByCafeteriaID")
    public BaseResponse getCafeteriaManagesByCafeteriaID(@RequestParam Long id){
        if(id == null){
            return Result.error("参数为空");
        }
        Cafeteria cafeteria = cafeteriaService.getById(id);
        if(cafeteria == null){
            return Result.error("该食堂不存在");
        }
        log.info("传入食堂id参数: {}", id);
        List<CafeteriaManage> list = cafeteriaManageService.getCafeteriaManagesByCafeteriaID(id);
        log.info("获取食堂管理列表: {}", list);
        return Result.success(list);
    }

    @Log
    @ApiOperation("按照食堂管理员ID筛选食堂管理")
    @GetMapping("/getCafeteriaManagesByAdminID")
    public BaseResponse getCafeteriaManagesByAdminID(@RequestParam Long id){
        if(id == null){
            return Result.error("参数为空");
        }
        log.info("传入食堂管理员id参数: {}", id);
        CafeteriaAdmin cafeteriaAdmin = adminService.getById(id);
        if(cafeteriaAdmin == null){
            return Result.error("该管理员不存在");
        }
        List<CafeteriaManage> list = cafeteriaManageService.getCafeteriaManagesByAdminID(id);
        log.info("获取食堂管理列表: {}", list);
        return Result.success(list);
    }

    @Log
    @ApiOperation("添加食堂管理")
    @PostMapping("/addCafeteriaManage")
    public BaseResponse addCafeteriaManage(@RequestBody CafeteriaManage cafeteriaManage) {
        if (cafeteriaManage == null) {
            return Result.error("参数为空");
        }
        log.info("添加食堂管理: {}", cafeteriaManage);
        boolean save = cafeteriaManageService.save(cafeteriaManage);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @Log
    @ApiOperation("删除食堂管理")
    @PostMapping("/deleteCafeteriaManage")
    public BaseResponse deleteCafeteriaManage(@RequestBody CafeteriaManage cafeteriaManage) {
        if (cafeteriaManage == null) {
            return Result.error("参数为空");
        }
        log.info("要删除食堂管理: {}", cafeteriaManage);
        boolean remove = cafeteriaManageService.removeById(cafeteriaManage.getManageId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }


    @Log
    @ApiOperation("修改食堂管理")
    @PostMapping("/updateCafeteriaManage")
    public BaseResponse updateCafeteriaManage(@RequestBody CafeteriaManage cafeteriaManage) {
        if (cafeteriaManage == null) {
            return Result.error("参数为空");
        }
        log.info("要修改食堂管理: {}", cafeteriaManage);
        boolean update = cafeteriaManageService.updateById(cafeteriaManage);
        if (!update) {
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }

}
