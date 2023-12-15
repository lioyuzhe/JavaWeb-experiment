package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.CafeteriaAdmin;
import com.usst.usstcafeteriahub.model.entity.CafeteriaManage;
import com.usst.usstcafeteriahub.service.CafeteriaAdminService;
import com.usst.usstcafeteriahub.service.CafeteriaManageService;
import com.usst.usstcafeteriahub.mapper.CafeteriaManageMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_manage(食堂管理表)】的数据库操作Service实现
* @createDate 2023-12-10 15:33:31
*/
@Service
public class CafeteriaManageServiceImpl extends ServiceImpl<CafeteriaManageMapper, CafeteriaManage>
    implements CafeteriaManageService{

    @Resource
    private CafeteriaManageMapper cafeteriaManageMapper;
    @Resource
    private CafeteriaAdminService cafeteriaAdminService;

    @Override
    public BaseResponse getCafeteriaAdmins(Integer id) {
       List<CafeteriaManage> result =  cafeteriaManageMapper.getCafeteriaAdmins(id);
       List<CafeteriaAdmin> cafeteriaAdmins = new ArrayList<>();
       for (CafeteriaManage cafeteriaManage : result) {
            CafeteriaAdmin cafeteriaAdmin = cafeteriaAdminService.getById(cafeteriaManage.getAdminId());
            if (cafeteriaAdmin != null) {
               cafeteriaAdmins.add(cafeteriaAdmin);
            }
       }
       return Result.success(cafeteriaAdmins);

    }

    /**
     * 按照食堂ID筛选食堂管理
     * @param id
     * @return
     */
    @Override
    public List<CafeteriaManage> getCafeteriaManagesByCafeteriaID(Long id) {
        return cafeteriaManageMapper.getCafeteriaManagesByCafeteriaID(id);
    }

    /**
     * 按照食堂管理员ID筛选食堂管理
     * @param id
     * @return
     */
    @Override
    public List<CafeteriaManage> getCafeteriaManagesByAdminID(Long id) {
        return cafeteriaManageMapper.getCafeteriaManagesByAdminID(id);
    }
}




