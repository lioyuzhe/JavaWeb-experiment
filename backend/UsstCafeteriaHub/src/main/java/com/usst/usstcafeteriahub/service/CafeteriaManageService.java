package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.model.entity.CafeteriaManage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_manage(食堂管理表)】的数据库操作Service
* @createDate 2023-12-10 15:33:31
*/
public interface CafeteriaManageService extends IService<CafeteriaManage> {


//    BaseResponse getCafeteriaAdmins(Integer id);


    /**
     * 按照食堂ID筛选食堂管理
     * @param id
     * @return
     */
    List<CafeteriaManage> getCafeteriaManagesByCafeteriaID(Long id);

    /**
     * 按照食堂管理员ID筛选食堂管理
     * @param id
     * @return
     */
    List<CafeteriaManage> getCafeteriaManagesByAdminID(Long id);
}
