package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.usst.usstcafeteriahub.model.request.LoginDTO;
import com.usst.usstcafeteriahub.model.request.RegisterDTO;

import java.util.List;

/**
* @author HrizonX
* @description 针对表【user(账号管理表)】的数据库操作Service
* @createDate 2023-12-10 14:42:29
*/
public interface UserService extends IService<User> {

    BaseResponse registerUser(RegisterDTO registerDTO);

    BaseResponse loginUser(LoginDTO loginDTO);

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

    /**
     * 根据用户名进行模糊查询
     * @param name
     * @return
     */
    List<User> getUserByName(String name);
}
