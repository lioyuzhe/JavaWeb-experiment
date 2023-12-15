package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.model.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.usst.usstcafeteriahub.model.request.LoginDTO;
import com.usst.usstcafeteriahub.model.request.RegisterDTO;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Klein
* @description 针对表【admin(系统管理员表)】的数据库操作Service
* @createDate 2023-12-10 14:39:20
*/
public interface AdminService extends IService<Admin> {


    BaseResponse addAdmin(Admin admin);

    BaseResponse deleteAdmin(Admin admin);

    BaseResponse updateAdmin(Admin admin);

    BaseResponse registerAdmin(RegisterDTO registerDTO);

    BaseResponse loginAdmin(LoginDTO loginDTO, HttpServletRequest request);
}
