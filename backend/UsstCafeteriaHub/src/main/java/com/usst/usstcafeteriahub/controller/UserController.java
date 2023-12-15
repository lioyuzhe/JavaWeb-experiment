package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.User;
import com.usst.usstcafeteriahub.service.UserService;
import com.usst.usstcafeteriahub.utils.UserHolder;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * User接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/users/actions")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("获取所有用户")
    @GetMapping("/getUsers")
    public BaseResponse getUsers() {
        List<User> list = userService.list();
        log.info("获取所有用户: {}", list);
        return Result.success(list);
    }

    @ApiOperation("根据id获取用户")
    @GetMapping("/getUserById")
    public BaseResponse getUserById(@RequestParam Long id) {
        if (id == null) {
            return Result.error("参数为空");
        }
        User user = userService.getById(id);
        log.info("根据id获取用户: {}", user);
        return Result.success(user);
    }

    @ApiOperation("根据用户名进行模糊查询")
    @GetMapping("/getUserByName")
    public BaseResponse getUserByName(@RequestParam String name) {
        if (name == null) {
            return Result.error("参数为空");
        }
        List<User> list = userService.getUserByName(name);
        log.info("根据用户名进行模糊查询: {}", list);
        return Result.success(list);
    }

    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public BaseResponse addUser(@RequestBody User user) {
        if (user == null) {
            return Result.error("参数为空");
        }
        log.info("添加用户: {}", user);
        int addResult = userService.addUser(user); // 0:添加成功 1:邮箱或手机号格式错误 2:添加失败
        if(addResult == 1) {
            return Result.error("邮箱或手机号格式错误");
        }
        if(addResult == 2) {
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @ApiOperation("删除用户")
    @PostMapping("/deleteUser")
    public BaseResponse deleteUser(@RequestBody User user) {
        if (user == null) {
            return Result.error("参数为空");
        }
        log.info("要删除用户: {}", user);
        boolean remove = userService.removeById(user.getUserId());
        if(!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("修改用户")
    @PostMapping("/updateUser")
    public BaseResponse updateUser(@RequestBody User user) {
        if (user == null) {
            return Result.error("参数为空");
        }
        log.info("修改用户: {}", user);
        int updateResult = userService.updateUser(user); // 0:添加成功 1:邮箱或手机号格式错误 2:添加失败
        if (updateResult == 1){
            return Result.error("邮箱或手机号格式错误");
        }
        if(updateResult == 2) {
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }











    @ApiOperation("获取当前用户")
    @GetMapping("/getCurrentUser")
    public BaseResponse getCurrentUser(){
        User user = UserHolder.getUser();
        if (user==null) return Result.error("尚未登录");
        return Result.success(user);
    }



}
