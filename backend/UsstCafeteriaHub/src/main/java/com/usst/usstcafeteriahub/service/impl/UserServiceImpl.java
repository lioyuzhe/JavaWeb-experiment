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
import com.usst.usstcafeteriahub.utils.UserHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.usst.usstcafeteriahub.constant.SystemConstants.*;

/**
* @author HrizonX
* @description 针对表【user(账号管理表)】的数据库操作Service实现
* @createDate 2023-12-10 14:42:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{



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
    public BaseResponse loginUser(LoginDTO loginDTO) {
        User user = query().eq("account", loginDTO.getAccount()).one();
        // 5.判断用户是否存在
        if (user == null) {
            // 6.不存在，创建新用户并保存
            return Result.error("用户不存在");
        }else {
            if(user.getPassword().equals(loginDTO.getPassword())){
                UserHolder.saveUser(user);
                return Result.success("登录成功");
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
        user.setRole( registerDTO.getRole());
        // 为用户设置一个随机的名字。
        user.setName(USER_NAME_PREFIX + RandomUtil.randomString(10));

        // 为用户设置默认的头像
        user.setAvatar(DEFAULT_AVATAR_URL);

        // 2.保存用户 并获取数据库中的用户id
        save(user);
        return user;
    }
}




