package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.*;
import com.usst.usstcafeteriahub.service.*;
import com.usst.usstcafeteriahub.utils.CafeteriaAdminHolder;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.usst.usstcafeteriahub.constant.WebConstants.ADMIN_LOGIN_STATE;
import static com.usst.usstcafeteriahub.constant.WebConstants.CAFETERIA_ADMIN_LOGIN_STATE;

/**
 * 食堂管理员接口
 * @author 李英杰
 */
@Slf4j
@RestController
@RequestMapping("/CafeteriaAdmins/actions")
public class CafeteriaAdminController {

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

    // 食堂管理员管理
    @ApiOperation("获取自己信息")   // 这里前端其实有处理，不过还是加上吧
    @GetMapping("/getCurrentCafeteriaAdmin")
    public BaseResponse getCurrentCafeteriaAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(CAFETERIA_ADMIN_LOGIN_STATE);
        CafeteriaAdmin cafeteriaAdmin = (CafeteriaAdmin) userObj;
        return Result.success(cafeteriaAdmin);
    }

    @ApiOperation(value = "增加食堂管理员")
    @PostMapping("/addCafeteriaAdmin")
    public BaseResponse addCafeteriaAdmin(@RequestBody CafeteriaAdmin admin){
        return cafeteriaAdminService.addAdmin(admin);
    }

    @ApiOperation(value = "删除食堂管理员")
    @PostMapping("/deleteCafeteriaAdmin")
    public BaseResponse deleteCafeteriaAdmin(@RequestBody CafeteriaAdmin admin){
        return cafeteriaAdminService.deleteAdmin(admin);
    }

    @ApiOperation(value = "修改食堂管理员")
    @PostMapping("/updateCafeteriaAdmin")
    public BaseResponse updateCafeteriaAdmin(@RequestBody CafeteriaAdmin admin){
        return cafeteriaAdminService.updateAdmin(admin);
    }

    @ApiOperation(value = "根据id查询管理员信息")
    @PostMapping("/getCafeteriaAdminById")
    public BaseResponse getCafeteriaAdminById(@RequestParam Integer id){
        CafeteriaAdmin admin = cafeteriaAdminService.getById(id);
        log.info("根据id获取管理员: {}", admin);
        return Result.success(admin);
    }

    @ApiOperation(value = "获取所有食堂管理员")
    @GetMapping("/getAllCafeteriaAdmin")
    public BaseResponse getAllCafeteriaAdmin(){
        return Result.success(cafeteriaAdminService.list());
    }

    // 食堂信息管理
    @ApiOperation(value = "获取自己管理的食堂信息")
    @GetMapping("/getCurrentCafeteria")
    public BaseResponse getCurrentCafeteria(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(CAFETERIA_ADMIN_LOGIN_STATE);
        CafeteriaAdmin cafeteriaAdmin = (CafeteriaAdmin) userObj;
        return cafeteriaAdminService.getCafeteriaByCafeteriaAdminId(cafeteriaAdmin.getAdminId());
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

    // 菜品维护
    @ApiOperation("添加菜品")
    @PostMapping ("/addDish")
    public BaseResponse addDish(@RequestBody Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        log.info("添加菜品: {}", dish);
        boolean save = dishService.save(dish);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }
    @ApiOperation("修改菜品")
    @PostMapping("/updateDish")
    public BaseResponse updateDish(@RequestBody Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        log.info("修改菜品: {}", dish);
        boolean update = dishService.updateById(dish);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }
    @ApiOperation("删除菜品")
    @PostMapping("/deleteDish")
    public BaseResponse deleteDish(@RequestBody Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        log.info("要删除菜品: {}", dish);
        boolean remove = dishService.removeById(dish.getDishId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    // 食堂评价处理
    @ApiOperation("按照食堂ID筛选食堂评价")
    @GetMapping("/getCafeteriaRemarksByCafeteriaID")
    public BaseResponse getCafeteriaRemarksByCafeteriaID(@RequestParam Integer id){
        if(id == null){
            return Result.error("参数为空");
        }
        log.info("传入食堂id参数: {}", id);
        List<CafeteriaRemark> list = cafeteriaRemarkService.getCafeteriaRemarksByCafeteriaID(id);
        log.info("获取食堂评价列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("修改食堂评价--包括回复")
    @PostMapping("/updatecafeteriaRemark")
    public BaseResponse updateCafeteria(@RequestBody CafeteriaRemark cafeteriaRemark) {
        if (cafeteriaRemark == null) {
            return Result.error("参数为空");
        }
        log.info("修改食堂评价: {}", cafeteriaRemark);
        boolean update = cafeteriaRemarkService.updateById(cafeteriaRemark);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }


    // 活动公告





}
