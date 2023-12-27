package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Log;
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



    @Log
    @ApiOperation(value = "注册用户")
    @PostMapping("/register")
    public BaseResponse register(@RequestBody RegisterDTO registerDTO){
        if (registerDTO == null) {
            return Result.error("参数为空");
        }
        if (registerDTO.getAccount() == null || registerDTO.getPassword() == null || registerDTO.getRole() == null) {
            return Result.error("参数不合法");
        }
//        if (registerDTO.getConfirmPass()!=null && !registerDTO.getConfirmPass().equals(registerDTO.getPassword())) {
//            return Result.error("两次密码不一致");
//        }  // 前端已经做了判断
        Integer role = registerDTO.getRole();
        System.out.println("role:"+role);
        if(role==0){
            return adminService.registerAdmin(registerDTO);
        }else if(role==1){
            return cafeteriaAdminService.registerCafeteriaAdmin(registerDTO);
        }else if(role == 2 || role == 3){
            return userService.registerUser(registerDTO);
        }else{
            return Result.error("角色错误");
        }
    }


}
