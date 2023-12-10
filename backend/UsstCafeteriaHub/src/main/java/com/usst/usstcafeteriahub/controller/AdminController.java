package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Admin;
import com.usst.usstcafeteriahub.service.AdminService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * 管理员接口
 * @author 李英杰
 */
@Slf4j
@RestController
@RequestMapping("/admins/actions")
public class AdminController {

    @Resource
    private AdminService adminService;

    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    public BaseResponse test(){
        return Result.error("功能还未开发");
    }


    @ApiOperation(value = "增加管理员")
    @PostMapping("/addAdmin")
    public BaseResponse addAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }




}
