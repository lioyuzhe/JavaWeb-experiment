package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.model.entity.Cafeteria;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Klein
* @description 针对表【cafeteria(食堂基本信息表)】的数据库操作Service
* @createDate 2023-12-24 10:15:50
*/
public interface CafeteriaService extends IService<Cafeteria> {

    /**
     * 添加食堂，但如果数据库中已经有相同的cafeteriaName，则返回已存在
     * @param cafeteria
     * @return
     */
    BaseResponse add(Cafeteria cafeteria, HttpServletRequest request);
}
