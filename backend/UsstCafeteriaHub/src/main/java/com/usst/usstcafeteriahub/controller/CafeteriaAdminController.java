package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.CafeteriaAdmin;
import com.usst.usstcafeteriahub.service.CafeteriaAdminService;
import com.usst.usstcafeteriahub.utils.CafeteriaAdminHolder;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 食堂管理员接口
 * @author 李英杰
 */
@Slf4j
@RestController
@RequestMapping("/CafeteriaAdmins/actions")
public class CafeteriaAdminController {

    @Resource
    private CafeteriaAdminService cafeteriaAdminService;

    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    public BaseResponse test(){
        return Result.error("功能还未开发");
    }


    @ApiOperation("获取自己信息")
    @GetMapping("/getCurrentCafeteriaAdmin")
    public BaseResponse getCurrentCafeteriaAdmin(){
        CafeteriaAdmin cafeteriaAdmin = CafeteriaAdminHolder.getCafeteriaAdmin();
        if(cafeteriaAdmin==null) return Result.error("尚未登录");
        return Result.success(cafeteriaAdmin);
    }






}
