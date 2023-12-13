package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.CafeteriaRemark;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_remark(食堂评价信息表)】的数据库操作Service
* @createDate 2023-12-10 15:33:42
*/
public interface CafeteriaRemarkService extends IService<CafeteriaRemark> {

    /**
     * 按照食堂ID筛选食堂评价
     * @param id
     * @return
     */
    List<CafeteriaRemark> getCafeteriaRemarksByCafeteriaID(Integer id);



}
