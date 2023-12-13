package com.usst.usstcafeteriahub.controller;

import cn.hutool.core.codec.Base32;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.*;
import com.usst.usstcafeteriahub.service.*;
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
    @Resource
    private CafeteriaService cafeteriaService;
    @Resource
    private DishService dishService;
    @Resource
    private CafeteriaRemarkService cafeteriaRemarkService;
    @Resource
    private DishRemarkService dishRemarkService;
    @Resource
    private CafeteriaManageService cafeteriaManageService;
    @Resource
    private CommunityUserService communityUserService;
    @Resource
    private CommunityMessageService communityMessageService;


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
    @ApiOperation(value = "增加食堂管理员")
    @PostMapping("/addCafeteriaAdmin")
    public BaseResponse addCafeteriaAdmin(@RequestBody CafeteriaAdmin admin){
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CafeteriaAdmin admin1 = cafeteriaAdminService.getById(admin);
        if(admin1 != null){
            return Result.error("该管理员已存在");
        }
        if (cafeteriaAdminService.save(admin)){
            return Result.success("添加成功");
        }else{
            return Result.error("添加失败");
        }
    }

    @ApiOperation(value = "删除食堂管理员")
    @PostMapping("/deleteCafeteriaAdmin")
    public BaseResponse deleteCafeteriaAdmin(@RequestBody CafeteriaAdmin admin){
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CafeteriaAdmin admin1 = cafeteriaAdminService.getById(admin);
        if(admin1 == null){
            return Result.error("该管理员不存在");
        }
        // 删除
        if (cafeteriaAdminService.removeById(admin)){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }

    @ApiOperation(value = "修改食堂管理员信息")
    @PostMapping("/updateCafeteriaAdmin")
    public BaseResponse updateCafeteriaAdmin(@RequestBody CafeteriaAdmin admin){
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CafeteriaAdmin admin1 = cafeteriaAdminService.getById(admin);
        if(admin1 == null){
            return Result.error("该管理员不存在");
        }
        // 更新
        if (cafeteriaAdminService.updateById(admin)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }

    @ApiOperation(value = "根据id查询食堂管理员信息")
    @GetMapping("/getCafeteriaAdminById")
    public BaseResponse getCafeteriaAdminById(@RequestParam Integer id){
        CafeteriaAdmin admin = cafeteriaAdminService.getById(id);
        log.info("根据id获取食堂管理员: {}", admin);
        if (admin == null){
            return Result.error("该管理员不存在");
        }
        return Result.success(admin);
    }

    @ApiOperation(value = "获取食堂管理员管理的食堂信息")
    @GetMapping("/getCafeteriasByCafeteriaAdminId")
    public BaseResponse getCafeteriasByCafeteriaAdminId(@RequestParam Integer id){
        CafeteriaAdmin admin = cafeteriaAdminService.getById(id);
        log.info("根据id获取食堂管理员: {}", admin);
        if (admin == null){
            return Result.error("该管理员不存在");
        }

        if (cafeteriaManageService.list() == null){
            return Result.error("该管理员没有管理的食堂");
        }
        return Result.success(cafeteriaManageService.list());
    }


    // 用户（账号管理）管理（老师和学生）
    @ApiOperation(value = "删除用户")
    @PostMapping("/deleteUser")
    public BaseResponse deleteUser(@RequestParam User user){
        if (user == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        User user1 = userService.getById(user);
        if(user1 == null){
            return Result.error("该用户不存在");
        }
        // 删除
        if (userService.removeById(user)){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/updateUser")
    public BaseResponse updateUser(@RequestBody User user){
        if (user == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        User user1 = userService.getById(user);
        if(user1 == null){
            return Result.error("该用户不存在");
        }
        // 更新
        if (userService.updateById(user)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }


    @ApiOperation(value = "根据id查询用户信息")
    @GetMapping("/getUserById")
    public BaseResponse getUserById(@RequestParam Integer id){
        User user = userService.getById(id);
        log.info("根据id获取用户: {}", user);
        if (user == null){
            return Result.error("该用户不存在");
        }
        return Result.success(user);
    }

    @ApiOperation(value = "获取所有用户信息")
    @GetMapping("/getUsers")
    public BaseResponse getUsers(){
        // 做一个判断，如果是管理员则返回所有管理员信息，如果是普通用户则返回自己的信息
        return Result.success(userService.list());
    }

    @ApiOperation(value = "增加用户")
    @PostMapping("/addUser")
    public BaseResponse addUser(@RequestBody User user){
        if (user == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        User user1 = userService.getById(user);
        if(user1 != null){
            return Result.error("该用户已存在");
        }
        if (userService.save(user)){
            return Result.success("添加成功");
        }else{
            return Result.error("添加失败");
        }
    }



    // 食堂信息管理
    @ApiOperation(value = "增加食堂")
    @PostMapping("/addCafeteria")
    public BaseResponse addCafeteria(@RequestBody Cafeteria cafeteria) {
        if (cafeteria == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        Cafeteria cafeteria1 = cafeteriaService.getById(cafeteria);
        if(cafeteria1 != null){
            return Result.error("该食堂已存在");
        }
        if (cafeteriaService.save(cafeteria)){
            return Result.success("添加成功");
        }else{
            return Result.error("添加失败");
        }
    }

    @ApiOperation(value = "删除食堂")
    @PostMapping("/deleteCafeteria")
    public BaseResponse deleteCafeteria(@RequestBody Cafeteria cafeteria){
        if (cafeteria == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        Cafeteria cafeteria1 = cafeteriaService.getById(cafeteria);
        if(cafeteria1 == null){
            return Result.error("该食堂不存在");
        }
        // 删除
        if (cafeteriaService.removeById(cafeteria)){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }

    @ApiOperation(value = "修改食堂信息")
    @PostMapping("/updateCafeteria")
    public BaseResponse updateCafeteria(@RequestBody Cafeteria cafeteria){
        if (cafeteria == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        Cafeteria cafeteria1 = cafeteriaService.getById(cafeteria);
        if(cafeteria1 == null){
            return Result.error("该食堂不存在");
        }
        // 更新
        if (cafeteriaService.updateById(cafeteria)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }

    @ApiOperation(value = "根据id查询食堂信息")
    @GetMapping("/getCafeteriaById")
    public BaseResponse getCafeteriaById(@RequestParam Integer id){
        Cafeteria cafeteria = cafeteriaService.getById(id);
        log.info("根据id获取食堂: {}", cafeteria);
        if (cafeteria == null){
            return Result.error("该食堂不存在");
        }
        return Result.success(cafeteria);
    }

    @ApiOperation(value = "获取所有食堂信息")
    @GetMapping("/getCafeterias")
    public BaseResponse getCafeterias(){
        // 做一个判断，如果是管理员则返回所有管理员信息，如果是普通用户则返回自己的信息
        return Result.success(cafeteriaService.list());
    }

    @ApiOperation(value = "给食堂添加管理员")
    @PostMapping("/addAdminToCafeteria")
    public BaseResponse addAdminToCafeteria(@RequestBody CafeteriaAdmin admin, @RequestBody Cafeteria cafeteria){
        if (admin == null || cafeteria == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CafeteriaAdmin admin1 = cafeteriaAdminService.getById(admin);
        Cafeteria cafeteria1 = cafeteriaService.getById(cafeteria);
        if(admin1 == null){
            return Result.error("该管理员不存在");
        }
        if(cafeteria1 == null){
            return Result.error("该食堂不存在");
        }
        // 更新
        CafeteriaManage cafeteriaManage = new CafeteriaManage(admin1.getAdminId(), cafeteria1.getCafeteriaId());
        if (cafeteriaManageService.save(cafeteriaManage)){
            return Result.success("添加成功");
        }else{
            return Result.error("添加失败");
        }
    }

    @ApiOperation(value = "获取食堂的管理员")
    @GetMapping("/getCafeteriaAdmins")
    public BaseResponse getCafeteriaAdmins(@RequestParam Integer id){
        Cafeteria cafeteria = cafeteriaService.getById(id);
        log.info("根据id获取食堂: {}", cafeteria);
        if (cafeteria == null){
            return Result.error("该食堂不存在");
        }
        return cafeteriaManageService.getCafeteriaAdmins(id);
    }



    // 社区管理
    @ApiOperation(value = "查看社区信息")
    @GetMapping("/getCommunityById")
    public BaseResponse getCommunityById(){
        // 只有一个社区，id为1
        Community community = communityService.getById(1);
        log.info("根据id获取社区: {}", community);
        if (community == null){
            return Result.error("该社区不存在");
        }
        return Result.success(community);
    }

    @ApiOperation(value = "查看社区用户")
    @GetMapping("/getCommunityUsers")
    public BaseResponse getCommunityUsers(){
//        // 只有一个社区，id为1
//        Community community = communityService.getById(1);
//        log.info("根据id获取社区: {}", community);
//        if (community == null){
//            return Result.error("该社区不存在");
//        }
        return Result.success(communityUserService.list());
    }

    @ApiOperation(value = "查看社区消息")
    @GetMapping("/getCommunityMessages")
    public BaseResponse getCommunityMessages(){
//        // 只有一个社区，id为1
//        Community community = communityService.getById(1);
//        log.info("根据id获取社区: {}", community);
//        if (community == null){
//            return Result.error("该社区不存在");
//        }
        return Result.success(communityMessageService.list());

    }

















}
