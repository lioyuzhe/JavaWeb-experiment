package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.model.entity.CafeteriaAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.usst.usstcafeteriahub.model.request.LoginDTO;
import com.usst.usstcafeteriahub.model.request.RegisterDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Klein
* @description 针对表【cafeteria_admin(食堂管理员表)】的数据库操作Service
* @createDate 2023-12-10 15:33:27
*/
public interface CafeteriaAdminService extends IService<CafeteriaAdmin> {

    BaseResponse registerCafeteriaAdmin(RegisterDTO registerDTO);

    BaseResponse loginCafeteriaAdmin(LoginDTO loginDTO, HttpServletRequest request);

    BaseResponse addAdmin(CafeteriaAdmin admin);

    BaseResponse deleteAdmin(CafeteriaAdmin admin);

    BaseResponse updateAdmin(CafeteriaAdmin admin);

    BaseResponse getCafeteriaByCafeteriaAdminId(Long adminId);
}
