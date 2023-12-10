package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.User;
import com.usst.usstcafeteriahub.service.UserService;
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
    public BaseResponse getUserById(@RequestParam Integer id) {
        User user = userService.getById(id);
        log.info("根据id获取用户: {}", user);
        return Result.success(user);
    }

    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public BaseResponse addUser(@RequestBody User user) {
        // 如果传入参数为空则返回Result.error("参数为空")
        if (user == null) {
            return Result.error("参数为空");
        }
        boolean save = userService.save(user);
        log.info("添加用户: {}", user);
        return Result.success(save);
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
        User user1 = userService.getById(user.getUserId());
        if (user1 == null) {
            return Result.error("用户不存在");
        }
        boolean update = userService.updateById(user);
        if(update){
            return Result.success("修改成功");
        }else{
            return Result.error("修改失败");
        }
    }

}
