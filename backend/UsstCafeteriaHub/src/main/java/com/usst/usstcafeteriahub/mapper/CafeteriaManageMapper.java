package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.model.entity.CafeteriaAdmin;
import com.usst.usstcafeteriahub.model.entity.CafeteriaManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_manage(食堂管理表)】的数据库操作Mapper
* @createDate 2023-12-10 15:33:31
* @Entity com.usst.usstcafeteriahub.model.entity.CafeteriaManage
*/
public interface CafeteriaManageMapper extends BaseMapper<CafeteriaManage> {


    List<CafeteriaManage> getCafeteriaAdmins(Integer id);
}




