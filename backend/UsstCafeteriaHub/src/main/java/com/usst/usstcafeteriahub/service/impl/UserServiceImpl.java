package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.User;
import com.usst.usstcafeteriahub.service.UserService;
import com.usst.usstcafeteriahub.mapper.UserMapper;
import com.usst.usstcafeteriahub.utils.RegexUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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


}




