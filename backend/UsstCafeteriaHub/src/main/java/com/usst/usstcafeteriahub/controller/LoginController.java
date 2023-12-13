package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.request.LoginDTO;
import com.usst.usstcafeteriahub.model.request.RegisterDTO;
import com.usst.usstcafeteriahub.service.AdminService;
import com.usst.usstcafeteriahub.service.CafeteriaAdminService;
import com.usst.usstcafeteriahub.service.UserService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 登录接口
 * @author 李英杰
 */
@Slf4j
@RestController
@RequestMapping("")
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private AdminService adminService;

    @Resource
    private CafeteriaAdminService cafeteriaAdminService;




    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginDTO loginDTO){
        Integer role = loginDTO.getRole();
        if(role==0){
            return adminService.loginAdmin(loginDTO);
        }else if(role==1){
            return cafeteriaAdminService.loginCafeteriaAdmin(loginDTO);
        }else if(role==2 || role == 3){
            return userService.loginUser(loginDTO);
        }else{
            return Result.error("角色错误");
        }

    }


}
