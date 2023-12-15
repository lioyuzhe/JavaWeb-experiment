package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.CafeteriaNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_notice(食堂活动公告表)】的数据库操作Mapper
* @createDate 2023-12-10 15:33:35
* @Entity com.usst.usstcafeteriahub.model.entity.CafeteriaNotice
*/
public interface CafeteriaNoticeMapper extends BaseMapper<CafeteriaNotice> {

    /**
     * 按照食堂ID筛选食堂公告
     * @param id
     * @return
     */
    List<CafeteriaNotice> getCafeteriaNoticesByCafeteriaID(Long id);
}




