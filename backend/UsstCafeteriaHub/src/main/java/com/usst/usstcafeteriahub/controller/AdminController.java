package com.usst.usstcafeteriahub.controller;

import cn.hutool.core.codec.Base32;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Admin;
import com.usst.usstcafeteriahub.service.AdminService;
import com.usst.usstcafeteriahub.service.CafeteriaAdminService;
import com.usst.usstcafeteriahub.service.CommunityService;
import com.usst.usstcafeteriahub.service.UserService;
import com.usst.usstcafeteriahub.utils.AdminHolder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
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
    @Resource
    private CafeteriaAdminService cafeteriaAdminService;
    @Resource
    private UserService userService;
    @Resource
    private CommunityService communityService;

    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    public BaseResponse test(){
        return Result.error("功能还未开发");
    }

    // 管理员管理

    @ApiOperation("获取当前管理员的信息")
    @GetMapping("/getCurrentAdmin")
    public BaseResponse getCurrentAdmin(){
        Admin admin = AdminHolder.getAdmin();
        if(admin==null) return Result.error("尚未登录");
        return Result.success(admin);
    }

    @ApiOperation(value = "增加管理员")
    @PostMapping("/addAdmin")
    public BaseResponse addAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }

    @ApiOperation(value = "删除管理员")
    @PostMapping("/deleteAdmin")
    public BaseResponse deleteAdmin(@RequestBody Admin admin){
        return adminService.deleteAdmin(admin);
    }


    @ApiOperation(value = "修改管理员信息")
    @PostMapping("/updateAdmin")
    public BaseResponse updateAdmin(@RequestBody Admin admin){
        return adminService.updateAdmin(admin);
    }

    @ApiOperation(value = "根据id查询管理员信息")
    @GetMapping("/getAdminById")
    public BaseResponse getAdminById(@RequestParam Integer id){
        Admin admin = adminService.getById(id);
        log.info("根据id获取管理员: {}", admin);
        return Result.success(admin);
    }


    @ApiOperation(value = "获取所有管理员信息")
    @GetMapping("/getAdmins")
    public BaseResponse getAdmins(){
        // 做一个判断，如果是管理员则返回所有管理员信息，如果是普通用户则返回自己的信息
        return Result.success(adminService.list());
    }


    // 食堂管理员管理




    // 用户（账号管理）管理（老师和学生）




    // 食堂信息管理




    // 社区管理



    //  评价信息管理















}
