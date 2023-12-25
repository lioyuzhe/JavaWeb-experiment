package com.usst.usstcafeteriahub.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.User;
import com.usst.usstcafeteriahub.model.request.LoginDTO;
import com.usst.usstcafeteriahub.model.request.RegisterDTO;
import com.usst.usstcafeteriahub.service.UserService;
import com.usst.usstcafeteriahub.mapper.UserMapper;
import com.usst.usstcafeteriahub.utils.JwtUtils;
import com.usst.usstcafeteriahub.utils.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import com.usst.usstcafeteriahub.utils.RegexUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


import static com.usst.usstcafeteriahub.constant.SystemConstants.*;
import static com.usst.usstcafeteriahub.constant.WebConstants.USER_LOGIN_STATE;

/**
* @author HrizonX
* @description 针对表【user(账号管理表)】的数据库操作Service实现
* @createDate 2023-12-10 14:42:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;

    /**
     * 添加用户,判断用户邮箱手机号正则
     * @param user
     * @return 0:添加成功 1:邮箱或手机号格式错误 2:添加失败
     */
    @Override
    public int addUser(User user) {
        boolean emailInvalid = RegexUtils.isEmailInvalid(user.getEmail());
        boolean phoneInvalid = RegexUtils.isPhoneInvalid(user.getPhone());
        if(emailInvalid && phoneInvalid){
            return 1;
        }
        int result = userMapper.insert(user);
        if(result == 1){
            return 0;
        }
        return 2;
    }

    /**
     * 修改用户，判断用户邮箱手机号正则
     * @param user
     * @return 0:添加成功 1:邮箱或手机号格式错误 2:添加失败
     */
    @Override
    public int updateUser(User user) {
        boolean emailInvalid = RegexUtils.isEmailInvalid(user.getEmail());
        boolean phoneInvalid = RegexUtils.isPhoneInvalid(user.getPhone());
        if(emailInvalid && phoneInvalid){
            return 1;
        }
        int result = userMapper.updateById(user);
        if(result == 1){
            return 0;
        }
        return 2;
    }

    /**
     * 根据用户名进行模糊查询
     * @param name
     * @return
     */
    @Override
    public List<User> getUserByName(String name) {
        return userMapper.getUserByName(name);
    }


    @Override
    public BaseResponse registerUser(RegisterDTO registerDTO) {
        // 通过account获取用户
        User user = query().eq("account", registerDTO.getAccount()).one();
        // 5.判断用户是否存在
        if (user == null) {
            // 6.不存在，创建新用户并保存
            user =createUser(registerDTO);
            // 获取id创建一个文件夹
            User usertmp = query().eq("account", user.getAccount()).one();
            Integer role = usertmp.getRole();
            if(role == 0){
                File file = new File(STUDENT_FILE_PATH+File.separator+usertmp.getUserId());
                file.mkdir();
            }else if(role == 1){
                File file = new File(TEACHER_FILE_PATH+File.separator+usertmp.getUserId());
                file.mkdir();
            }else {
                return Result.error("角色错误");
            }
            return Result.success("注册成功");
        }else {
            return Result.error("用户已存在");
        }
    }

    @Override
    public BaseResponse loginUser(LoginDTO loginDTO, HttpServletRequest request) {
        User user = query().eq("account", loginDTO.getAccount()).one();
        // 5.判断用户是否存在
        if (user == null) {
            // 6.不存在，创建新用户并保存
            return Result.error("用户不存在");
        }else {
            if(user.getPassword().equals(loginDTO.getPassword())){
                Map<String,Object> claims = new HashMap<>();
                claims.put("id",user.getUserId());
                claims.put("account",user.getAccount());
                claims.put("password",user.getPassword());
                String token = JwtUtils.generateToken(claims);
                user.setToken(token);
                // 将用户信息保存到session中
                request.getSession().setAttribute(USER_LOGIN_STATE,user);

                user.setPassword(null); // 密码不返回
                return Result.success(user,"登录成功");
            }else{
                return Result.error("密码错误");
            }
        }
    }

    private User createUser(RegisterDTO registerDTO) {
        // 创建用户
        User user = new User();
        user.setAccount(registerDTO.getAccount());
        user.setPassword(registerDTO.getPassword());
        if (registerDTO.getRole() == 2) {
            user.setRole(0);
        } else if (registerDTO.getRole() == 3) {
            user.setRole(1);
        } else {
            return null;
        }
        // 为用户设置一个随机的名字。
        user.setName(USER_NAME_PREFIX + RandomUtil.randomString(10));
        // 为用户设置默认的头像
        user.setAvatar(DEFAULT_AVATAR_URL);

        // 2.保存用户 并获取数据库中的用户id
        save(user);
        return user;
    }
}




