package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.service.AdminService;
import com.usst.usstcafeteriahub.mapper.AdminMapper;
import com.usst.usstcafeteriahub.model.entity.Admin;
import org.springframework.stereotype.Service;

/**
* @author Klein
* @description 针对表【admin(系统管理员表)】的数据库操作Service实现
* @createDate 2023-12-06 22:04:37
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService {

}




