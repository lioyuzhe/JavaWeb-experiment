package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.usst.usstcafeteriahub.model.request.LoginDTO;
import com.usst.usstcafeteriahub.model.request.RegisterDTO;

/**
* @author HrizonX
* @description 针对表【user(账号管理表)】的数据库操作Service
* @createDate 2023-12-10 14:42:29
*/
public interface UserService extends IService<User> {

    BaseResponse registerUser(RegisterDTO registerDTO);

    BaseResponse loginUser(LoginDTO loginDTO);
}
