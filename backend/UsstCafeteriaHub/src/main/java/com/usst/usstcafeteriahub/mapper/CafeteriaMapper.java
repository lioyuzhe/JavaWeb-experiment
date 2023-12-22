package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.Cafeteria;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Klein
* @description 针对表【cafeteria(食堂基本信息表)】的数据库操作Mapper
* @createDate 2023-12-10 14:44:38
* @Entity com.usst.usstcafeteriahub.model.entity.Cafeteria
*/
public interface CafeteriaMapper extends BaseMapper<Cafeteria> {

    /**
     * 根据食堂名称获取食堂信息
     * @param name
     * @return
     */
    Cafeteria getCafeteriaByName(String name);
}




