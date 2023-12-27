package com.usst.usstcafeteriahub.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Admin;

import com.usst.usstcafeteriahub.model.request.LoginDTO;
import com.usst.usstcafeteriahub.model.request.RegisterDTO;
import com.usst.usstcafeteriahub.service.AdminService;
import com.usst.usstcafeteriahub.mapper.AdminMapper;
import com.usst.usstcafeteriahub.utils.AdminHolder;
import com.usst.usstcafeteriahub.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.usst.usstcafeteriahub.constant.SystemConstants.*;
import static com.usst.usstcafeteriahub.constant.WebConstants.ADMIN_LOGIN_STATE;

/**
* @author Klein
* @description 针对表【admin(系统管理员表)】的数据库操作Service实现
* @createDate 2023-12-10 14:39:20
*/
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

    @Resource
    private AdminMapper adminMapper;
    @Override
    public BaseResponse addAdmin(Admin admin) {
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        Admin admin1 = adminMapper.selectById(admin);
        if(admin1 != null){
            return Result.error("该管理员已存在");
        }

        int result = adminMapper.insert(admin);
        System.out.println(result);
        log.info("添加成功:{｝ ", result );
        if(result == 1){
            return Result.success("添加成功");
        }else{
            return Result.error("添加失败");
        }
    }

    @Override
    public BaseResponse deleteAdmin(Admin admin) {
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        Admin admin1 = adminMapper.selectById(admin);
        if(admin1 == null){
            return Result.error("该管理员不存在");
        }
        // 删除
        int result =  adminMapper.deleteById(admin);
        if (result == 1){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }

    @Override
    public BaseResponse updateAdmin(Admin admin) {
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        Admin admin1 = adminMapper.selectById(admin);
        if(admin1 == null){
            return Result.error("该管理员不存在");
        }
        // 更新
        int result = adminMapper.updateById(admin);
        if (result == 1){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }

    @Override
    public BaseResponse registerAdmin(RegisterDTO registerDTO) {
        Admin admin = query().eq("account", registerDTO.getAccount()).one();
        // 5.判断用户是否存在
        if (admin == null) {
            // 6.不存在，创建新用户并保存
            admin =createAdmin(registerDTO);
            Admin admintmp = query().eq("account", admin.getAccount()).one();
            if(admintmp!=null){
                File file = new File(ADMIN_FILE_PATH+File.separator+admintmp.getAdminId());
                file.mkdir();
            }else{
                return Result.error("注册失败");
            }
            return Result.success("注册成功");
        }else {
            return Result.error("用户已存在");
        }
    }

    @Override
    public BaseResponse loginAdmin(LoginDTO loginDTO, HttpServletRequest request) {
        Admin admin = query().eq("account", loginDTO.getAccount()).one();
        // 5.判断用户是否存在
        if (admin == null) {
            // 6.不存在，创建新用户并保存
            return Result.error("用户不存在");
        }else {
            if (admin.getPassword().equals(loginDTO.getPassword())){
                log.info("登录用户信息：{}",admin);
                Map<String,Object> claims = new HashMap<>();
                claims.put("id",admin.getAdminId());
                claims.put("account",admin.getAccount());
                claims.put("password",admin.getPassword());
                String token = JwtUtils.generateToken(claims);
                log.info("登录用户信息生成的token:{}",token);
                admin.setToken(token);
                // 用session保存用户信息
                request.getSession().setAttribute(ADMIN_LOGIN_STATE,admin);
                admin.setPassword(null); // 密码不返回
                return Result.success(admin,"登录成功");
            }else{
                return Result.error("密码错误");
            }
        }
    }


    private Admin createAdmin(RegisterDTO registerDTO) {
        // 创建用户
        Admin admin = new Admin();
        admin.setAccount(registerDTO.getAccount());
        admin.setPassword(registerDTO.getPassword());

        // 为用户设置一个随机的名字。
        admin.setName(Admin_NAME_PREFIX + RandomUtil.randomString(10));

        // 为用户设置默认的头像
        admin.setAvatar(DEFAULT_AVATAR_URL);

        // 2.保存用户
        save(admin);
        return admin;
    }

}




