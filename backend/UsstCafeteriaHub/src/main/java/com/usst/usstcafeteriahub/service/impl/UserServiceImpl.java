package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.User;
import com.usst.usstcafeteriahub.service.UserService;
import com.usst.usstcafeteriahub.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author HrizonX
* @description 针对表【user(账号管理表)】的数据库操作Service实现
* @createDate 2023-12-10 14:42:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




