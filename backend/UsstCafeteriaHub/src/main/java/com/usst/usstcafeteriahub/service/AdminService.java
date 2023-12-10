package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.model.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Klein
* @description 针对表【admin(系统管理员表)】的数据库操作Service
* @createDate 2023-12-10 14:39:20
*/
public interface AdminService extends IService<Admin> {


    BaseResponse addAdmin(Admin admin);

    BaseResponse deleteAdmin(Admin admin);
}
