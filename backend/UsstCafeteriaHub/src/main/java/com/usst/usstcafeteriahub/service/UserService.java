package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author HrizonX
* @description 针对表【user(账号管理表)】的数据库操作Service
* @createDate 2023-12-10 14:42:29
*/
public interface UserService extends IService<User> {

    /**
     * 添加用户,判断用户邮箱手机号正则
     * @param user
     * @return 0:添加成功 1:邮箱或手机号格式错误 2:添加失败
     */
    int addUser(User user);

    /**
     * 修改用户信息,判断用户邮箱手机号正则
     * @param user
     * @return 0:添加成功 1:邮箱或手机号格式错误 2:添加失败
     */
    int updateUser(User user);
}
