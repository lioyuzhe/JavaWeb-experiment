package com.usst.usstcafeteriahub.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.*;
import com.usst.usstcafeteriahub.service.*;
import com.usst.usstcafeteriahub.utils.CafeteriaAdminHolder;
import com.usst.usstcafeteriahub.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.usst.usstcafeteriahub.constant.WebConstants.ADMIN_LOGIN_STATE;
import static com.usst.usstcafeteriahub.constant.WebConstants.CAFETERIA_ADMIN_LOGIN_STATE;

/**
 * 食堂管理员接口
 * @author 李英杰
 */
@Slf4j
@RestController
@RequestMapping("/cafeteriaAdmins/actions")
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
    @Resource
    private CafeteriaNoticeService cafeteriaNoticeService;
    @Resource
    private ComplaintService complaintService;
    @Resource
    private PromotionService promotionService;


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
    public BaseResponse addCafeteria(@RequestBody Cafeteria cafeteria, HttpServletRequest request) {
        if (cafeteria == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        Cafeteria cafeteria1 = cafeteriaService.getById(cafeteria);
        if(cafeteria1 != null){
            return Result.error("该食堂已存在");
        }
        if (cafeteriaService.save(cafeteria)){
            // 同时要在cafeteria_manage表中添加一条记录
            CafeteriaManage cafeteriaManage = new CafeteriaManage();
            // 从token中获取食堂管理员的account
            String token = request.getHeader("token");
            log.info("请求头中的token:{}",token);
            String account = JwtUtils.parseToken(token).get("account").toString();
            log.info("解析token得到的account:{}",account);
            // 根据account获取食堂管理员的id
            CafeteriaAdmin cafeteriaAdmin = cafeteriaAdminService.getOne(new QueryWrapper<CafeteriaAdmin>().eq("account", account));
            log.info("根据account获取食堂管理员: {}", cafeteriaAdmin);
            cafeteriaManage.setAdminId(cafeteriaAdmin.getAdminId());
            cafeteriaManage.setCafeteriaId(cafeteria.getCafeteriaId());


            cafeteriaManageService.save(cafeteriaManage);
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
            // 同时要在cafeteria_manage表中删除一条记录
            cafeteriaManageService.remove(new QueryWrapper<CafeteriaManage>().eq("cafeteria_id", cafeteria.getCafeteriaId()));

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

    @ApiOperation("获取所有食堂信息")
    @GetMapping("/getAllCafeteria")
    public BaseResponse getAllCafeteria(){
        return Result.success(cafeteriaService.list());
    }


    @ApiOperation("获取当前食堂管理员管理的食堂 ID 列表")
    @GetMapping("/getCafeteriaIdList/{adminId}")
    public BaseResponse getCafeteriaIdList(@RequestParam long adminId,HttpServletRequest request) {
        List<CafeteriaManage> cafeteriaManageList = cafeteriaManageService.list(new QueryWrapper<CafeteriaManage>().eq("admin_id", adminId));
        return Result.success(cafeteriaManageList);
    }



    // 菜品维护

    @ApiOperation(value = "多条件模糊查询菜品信息")
    @GetMapping("/selectDishByPage")
    public BaseResponse selectDishByPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String cafeteriaName,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) BigDecimal price) {

        QueryWrapper<Dish> queryWrapper = new QueryWrapper<Dish>().orderByDesc("dish_id"); // 默认倒序，让最新的数据在最上面

        // 构建查询条件
        if (StringUtils.isNotBlank(cafeteriaName)) {
            queryWrapper.like("cafeteria_name", cafeteriaName);
        }
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        if (StringUtils.isNotBlank(cuisine)) {
            queryWrapper.like("cuisine", cuisine);
        }
        if (price != null) {
            queryWrapper.eq("price", price);
        }

        // select * from dish where (cafeteria_name like '%#{cafeteriaName}%' OR name like '%#{dishName}%' OR cuisine like '%#{cuisine}%' OR price = #{price})
        Page<Dish> page = dishService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }


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
    public BaseResponse getCafeteriaRemarksByCafeteriaID(@RequestParam Long id){
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
    @ApiOperation("添加食堂公告")
    @PostMapping("/addCafeteriaNotice")
    public BaseResponse addCafeteriaNotice(@RequestBody CafeteriaNotice cafeteriaNotice) {
        if (cafeteriaNotice == null) {
            return Result.error("参数为空");
        }
        log.info("添加食堂公告: {}", cafeteriaNotice);
        boolean save = cafeteriaNoticeService.save(cafeteriaNotice);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @ApiOperation("按照食堂ID筛选食堂公告")
    @GetMapping("/getCafeteriaNoticesByCafeteriaID")
    public BaseResponse getCafeteriaNoticesByCafeteriaID(@RequestParam Long id){
        if(id == null){
            return Result.error("参数为空");
        }
        log.info("传入食堂id参数: {}", id);
        List<CafeteriaNotice> list = cafeteriaNoticeService.getCafeteriaNoticesByCafeteriaID(id);
        log.info("获取食堂公告列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("删除食堂公告")
    @PostMapping("/deleteCafeteriaNotice")
    public BaseResponse deleteCafeteriaNotice(@RequestBody CafeteriaNotice cafeteriaNotice) {
        if (cafeteriaNotice == null) {
            return Result.error("参数为空");
        }
        log.info("要删除食堂公告: {}", cafeteriaNotice);
        boolean remove = cafeteriaNoticeService.removeById(cafeteriaNotice.getNoticeId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("修改食堂公告")
    @PostMapping("/updateCafeteriaNotice")
    public BaseResponse updateCafeteriaNotice(@RequestBody CafeteriaNotice cafeteriaNotice) {
        if (cafeteriaNotice == null) {
            return Result.error("参数为空");
        }
        log.info("要修改食堂公告: {}", cafeteriaNotice);
        boolean update = cafeteriaNoticeService.updateById(cafeteriaNotice);
        if (!update) {
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }

    // 投诉信息管理
    @ApiOperation("获取投诉列表")
    @GetMapping("/getComplaint")
    public BaseResponse getComplaint() {
        List<Complaint> list = complaintService.list();
        log.info("获取投诉列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("添加投诉")
    @PostMapping("/addComplaint")
    public BaseResponse addComplaint(@RequestBody Complaint complaint) {
        if (complaint == null) {
            return Result.error("参数为空");
        }
        log.info("添加投诉: {}", complaint);
        complaint.setStatus(0);
        boolean save = complaintService.save(complaint);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @ApiOperation("删除投诉")
    @PostMapping("/deleteComplaint")
    public BaseResponse deleteComplaint(@RequestBody Complaint complaint) {
        if (complaint == null) {
            return Result.error("参数为空");
        }
        log.info("要删除投诉: {}", complaint);
        boolean remove = complaintService.removeById(complaint.getComplaintId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("修改投诉")
    @PostMapping("/updateComplaint")
    public BaseResponse updateComplaint(@RequestBody Complaint complaint) {
        if (complaint == null) {
            return Result.error("参数为空");
        }
        log.info("修改投诉: {}", complaint);
        boolean update = complaintService.updateById(complaint);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }

    @ApiOperation("食堂管理员根据食堂id获取投诉列表,且未处理的投诉优先显示")
    @GetMapping("/getComplaintByCafeteriaId")
    public BaseResponse getComplaintByCafeteriaId(@RequestParam("cafeteriaId") Long cafeteriaId) {
        if (cafeteriaId == null) {
            return Result.error("参数为空");
        }
        List<Complaint> list = complaintService.getComplaintByCafeteriaId(cafeteriaId);
        if (list == null) {
            return Result.error("查询失败");
        }
        log.info("根据食堂id获取投诉列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("食堂管理员根据食堂id获取投诉列表获取未处理的投诉数量")
    @GetMapping("/getComplaintNumByCafeteriaId")
    public BaseResponse getComplaintNumByCafeteriaId(@RequestParam("cafeteriaId") Long cafeteriaId) {
        if (cafeteriaId == null) {
            return Result.error("参数为空");
        }
        List<Complaint> list = complaintService.getComplaintByCafeteriaId(cafeteriaId);
        int num = 0;
        for (Complaint complaint : list) {
            if (complaint.getStatus() == 0) {
                num++;
            }
        }
        log.info("根据食堂id获取投诉列表: {}", list);
        return Result.success(num);
    }

    @ApiOperation("用户根据用户id获取已回复的投诉列表")
    @GetMapping("/getComplaintReplyByUserId")
    public BaseResponse getComplaintReplyByUserId(@RequestParam("userId") Long userId) {
        if (userId == null) {
            return Result.error("参数为空");
        }
        List<Complaint> list = complaintService.getComplaintReplyByUserId(userId);
        if (list == null) {
            return Result.error("查询失败");
        }
        log.info("根据用户id获取已回复的投诉列表: {}", list);
        return Result.success(list);
    }


    @ApiOperation("获取促销菜品列表")
    @GetMapping("/getPromotion")
    public BaseResponse getPromotion() {
        List<Promotion> list = promotionService.list();
        log.info("获取促销菜品列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("删除促销菜品")
    @PostMapping("/deletePromotion")
    public BaseResponse deletePromotion(@RequestBody Promotion promotion) {
        if (promotion == null) {
            return Result.error("参数为空");
        }
        log.info("要删除促销菜品: {}", promotion);
        boolean remove = promotionService.removeById(promotion.getPromotionId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("添加促销菜品")
    @PostMapping("/addPromotion")
    public BaseResponse addPromotion(@RequestBody Promotion promotion) {
        if (promotion == null) {
            return Result.error("参数为空");
        }
        log.info("添加促销菜品: {}", promotion);
        boolean save = promotionService.save(promotion);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @ApiOperation("修改促销菜品")
    @PostMapping("/updatePromotion")
    public BaseResponse updatePromotion(@RequestBody Promotion promotion) {
        if (promotion == null) {
            return Result.error("参数为空");
        }
        log.info("修改促销菜品: {}", promotion);
        boolean update = promotionService.updateById(promotion);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }


}
