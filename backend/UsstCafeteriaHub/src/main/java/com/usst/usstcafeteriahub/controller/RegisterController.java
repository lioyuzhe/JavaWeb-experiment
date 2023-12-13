package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.User;
import com.usst.usstcafeteriahub.model.request.RegisterDTO;
import com.usst.usstcafeteriahub.service.AdminService;
import com.usst.usstcafeteriahub.service.CafeteriaAdminService;
import com.usst.usstcafeteriahub.service.UserService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 注册接口
 * @author 李英杰
 */
@Slf4j
@RestController
@RequestMapping("")
public class RegisterController {



    @Resource
    private UserService userService;

    @Resource
    private AdminService  adminService;

    @Resource
    private CafeteriaAdminService cafeteriaAdminService;



    @ApiOperation(value = "注册用户")
    @PostMapping("/register")
    public BaseResponse register(@RequestBody RegisterDTO registerDTO){
        Integer role = registerDTO.getRole();
        if(role==0){
            return adminService.registerAdmin(registerDTO);
        }else if(role==1){
            return cafeteriaAdminService.registerCafeteriaAdmin(registerDTO);
        }else if(role==2 || role == 3){
            return userService.registerUser(registerDTO);
        }else{
            return Result.error("角色错误");
        }

    }


}
