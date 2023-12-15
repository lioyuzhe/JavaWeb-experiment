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


    // 你这里有点问题，传入参数id 没有映射到你xml文件里的cafeteriaId 给你注释掉了，用我下面这个
    // List<CafeteriaManage> getCafeteriaAdmins(Integer id);

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




