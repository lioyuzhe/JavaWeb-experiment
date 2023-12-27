package com.usst.usstcafeteriahub.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.*;
import com.usst.usstcafeteriahub.service.*;
//import com.usst.usstcafeteriahub.utils.AdminHolder;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.usst.usstcafeteriahub.constant.WebConstants.ADMIN_LOGIN_STATE;


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

    @ApiOperation("获取当前管理员的信息") // 这里前端其实有处理，不过还是加上吧
    @GetMapping("/getCurrentAdmin")
    public BaseResponse getCurrentAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(ADMIN_LOGIN_STATE);
        Admin admin = (Admin) userObj;
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


    @ApiOperation(value = "多条件模糊查询食堂管理员信息")
    @GetMapping("/selectCafeteriaAdminByPage")
    public BaseResponse selectCafeteriaAdminByPage(@RequestParam Integer pageNum,
                                                   @RequestParam Integer pageSize,
                                                   @RequestParam String username,
                                                   @RequestParam String name) {
        QueryWrapper<CafeteriaAdmin> queryWrapper = new QueryWrapper<CafeteriaAdmin>().orderByDesc("admin_id");  // 默认倒序，让最新的数据在最上面
        queryWrapper.like(StrUtil.isNotBlank(username), "account", username);
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        // select * from user where username like '%#{username}%' and name like '%#{name}%'
        Page<CafeteriaAdmin> page = cafeteriaAdminService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
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
    public BaseResponse deleteUser(@RequestBody User user){
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


    /**
     * 多条件模糊查询用户信息
     * pageNum 当前的页码
     * pageSize 每页查询的个数
     */
    @ApiOperation(value = "多条件模糊查询用户信息")
    @GetMapping("/selectByPage")
    public BaseResponse selectByPage(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam String username,
                                     @RequestParam String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("user_id");  // 默认倒序，让最新的数据在最上面

        // 检查username和name是否为空，然后使用OR逻辑连接两个条件
        if (StrUtil.isNotBlank(username) || StrUtil.isNotBlank(name)) {
            queryWrapper.and(wrapper ->
                    wrapper.like(StrUtil.isNotBlank(username), "account", username)
                            .or()
                            .like(StrUtil.isNotBlank(name), "name", name)
            );
        }

        // select * from user where (username like '%#{username}%' OR name like '%#{name}%')
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
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
    public BaseResponse getCafeterias(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String cafeteriaName,
            @RequestParam(required = false) String location) {

        QueryWrapper<Cafeteria> queryWrapper = new QueryWrapper<Cafeteria>().orderByDesc("cafeteria_id");  // 假设按食堂ID倒序排列

        // 检查cafeteriaName和location是否为空，然后使用OR逻辑连接两个条件
        if (StrUtil.isNotBlank(cafeteriaName) || StrUtil.isNotBlank(location)) {
            queryWrapper.and(wrapper ->
                    wrapper.like(StrUtil.isNotBlank(cafeteriaName), "name", cafeteriaName)
                            .or()
                            .like(StrUtil.isNotBlank(location), "location", location)
            );
        }

        // select * from cafeteria where (cafeteria_name like '%#{cafeteriaName}%' OR location like '%#{location}%')
        Page<Cafeteria> page = cafeteriaService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
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


//    @ApiOperation(value = "获取食堂的管理员")
//    @GetMapping("/getCafeteriaAdmins")
//    public BaseResponse getCafeteriaAdmins(@RequestParam Integer id){
//        Cafeteria cafeteria = cafeteriaService.getById(id);
//        log.info("根据id获取食堂: {}", cafeteria);
//        if (cafeteria == null){
//            return Result.error("该食堂不存在");
//        }
//        return cafeteriaManageService.getCafeteriaAdmins(id);
//    }




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
        return Result.success(communityUserService.list());
    }

    @ApiOperation(value = "查看社区消息")
    @GetMapping("/getCommunityMessages")
    public BaseResponse getCommunityMessages(){
        return Result.success(communityMessageService.list());
    }

    @ApiOperation(value = "删除社区消息")
    @PostMapping("/deleteCommunityMessage")
    public BaseResponse deleteCommunityMessage(@RequestBody CommunityMessage communityMessage){
        if (communityMessage == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CommunityMessage communityMessage1 = communityMessageService.getById(communityMessage);
        if(communityMessage1 == null){
            return Result.error("该社区消息不存在");
        }
        // 删除
        if (communityMessageService.removeById(communityMessage)){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }


    @ApiOperation(value = "删除社区用户")
    @PostMapping("/deleteCommunityUser")
    public BaseResponse deleteCommunityUser(@RequestBody CommunityUser communityUser){
        if (communityUser == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CommunityUser communityUser1 = communityUserService.getById(communityUser);
        if(communityUser1 == null){
            return Result.error("该社区用户不存在");
        }
        // 删除
        if (communityUserService.removeById(communityUser)){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }


    @ApiOperation(value = "修改社区用户")
    @PostMapping("/updateCommunityUser")
    public BaseResponse updateCommunityUser(@RequestBody CommunityUser communityUser){
        if (communityUser == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CommunityUser communityUser1 = communityUserService.getById(communityUser);
        if(communityUser1 == null){
            return Result.error("该社区用户不存在");
        }
        // 更新
        if (communityUserService.updateById(communityUser)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }



    @ApiOperation(value = "修改社区消息")
    @PostMapping("/updateCommunityMessage")
    public BaseResponse updateCommunityMessage(@RequestBody CommunityMessage communityMessage){
        if (communityMessage == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CommunityMessage communityMessage1 = communityMessageService.getById(communityMessage);
        if(communityMessage1 == null){
            return Result.error("该社区消息不存在");
        }
        // 更新
        if (communityMessageService.updateById(communityMessage)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }



    // 评价信息管理
    @ApiOperation(value = "食堂评价信息删除")
    @PostMapping("/deleteCafeteriaRemark")
    public BaseResponse deleteCafeteriaRemark(@RequestBody CafeteriaRemark cafeteriaRemark){
        if (cafeteriaRemark == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CafeteriaRemark cafeteriaRemark1 = cafeteriaRemarkService.getById(cafeteriaRemark);
        if(cafeteriaRemark1 == null){
            return Result.error("该食堂评价信息不存在");
        }
        // 删除
        if (cafeteriaRemarkService.removeById(cafeteriaRemark)){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }


    @ApiOperation(value = "食堂评价信息修改")
    @PostMapping("/updateCafeteriaRemark")
    public BaseResponse updateCafeteriaRemark(@RequestBody CafeteriaRemark cafeteriaRemark){
        if (cafeteriaRemark == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CafeteriaRemark cafeteriaRemark1 = cafeteriaRemarkService.getById(cafeteriaRemark);
        if(cafeteriaRemark1 == null){
            return Result.error("该食堂评价信息不存在");
        }
        // 更新
        if (cafeteriaRemarkService.updateById(cafeteriaRemark)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }

    @ApiOperation(value = "食堂评价信息查询")
    @GetMapping("/getCafeteriaRemarkById")
    public BaseResponse getCafeteriaRemarkById(@RequestParam Integer id){
        CafeteriaRemark cafeteriaRemark = cafeteriaRemarkService.getById(id);
        log.info("根据id获取食堂评价信息: {}", cafeteriaRemark);
        if (cafeteriaRemark == null){
            return Result.error("该食堂评价信息不存在");
        }
        return Result.success(cafeteriaRemark);
    }

    @ApiOperation(value = "查看所有食堂评价信息")
    @GetMapping("/getCafeteriaRemarks")
    public BaseResponse getCafeteriaRemarks(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String cafeteriaName,
            @RequestParam(required = false) String userName) {

        Page<CafeteriaRemark> page = new Page<>(pageNum, pageSize);
        QueryWrapper<CafeteriaRemark> queryWrapper = new QueryWrapper<>();

        if (cafeteriaName != null && !cafeteriaName.isEmpty()) {
            queryWrapper.like("cafeteria_name", cafeteriaName);
        }
        if (userName != null && !userName.isEmpty()) {
            queryWrapper.like("user_name", userName);
        }

        IPage<CafeteriaRemark> resultPage = cafeteriaRemarkService.page(page, queryWrapper);
        return Result.success(resultPage);
    }

    // 菜品评价信息管理
    @ApiOperation(value = "菜品评价信息删除")
    @PostMapping("/deleteDishRemark")
    public BaseResponse deleteDishRemark(@RequestBody DishRemark dishRemark){
        if (dishRemark == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        DishRemark dishRemark1 = dishRemarkService.getById(dishRemark);
        if(dishRemark1 == null){
            return Result.error("该菜品评价信息不存在");
        }
        // 删除
        if (dishRemarkService.removeById(dishRemark)){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }

    @ApiOperation(value = "菜品评价信息修改")
    @PostMapping("/updateDishRemark")
    public BaseResponse updateDishRemark(@RequestBody DishRemark dishRemark){
        if (dishRemark == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        DishRemark dishRemark1 = dishRemarkService.getById(dishRemark);
        if(dishRemark1 == null){
            return Result.error("该菜品评价信息不存在");
        }
        // 更新
        if (dishRemarkService.updateById(dishRemark)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }

    @ApiOperation(value = "菜品评价信息查询")
    @GetMapping("/getDishRemarkById")
    public BaseResponse getDishRemarkById(@RequestParam Integer id){
        DishRemark dishRemark = dishRemarkService.getById(id);
        log.info("根据id获取菜品评价信息: {}", dishRemark);
        if (dishRemark == null){
            return Result.error("该菜品评价信息不存在");
        }
        return Result.success(dishRemark);
    }

    @ApiOperation(value = "查看所有菜品评价信息")
    @GetMapping("/getDishRemarks")
    public BaseResponse getDishRemarks(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String dishName,
            @RequestParam(required = false) String userName) {

        Page<DishRemark> page = new Page<>(pageNum, pageSize);
        QueryWrapper<DishRemark> queryWrapper = new QueryWrapper<>();

        if (dishName != null && !dishName.isEmpty()) {
            queryWrapper.like("dish_name", dishName);
        }
        if (userName != null && !userName.isEmpty()) {
            queryWrapper.like("user_name", userName);
        }

        IPage<DishRemark> resultPage = dishRemarkService.page(page, queryWrapper);
        return Result.success(resultPage);
    }




}
