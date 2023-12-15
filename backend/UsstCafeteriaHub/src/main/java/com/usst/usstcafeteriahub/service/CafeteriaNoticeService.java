package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.CafeteriaNotice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_notice(食堂活动公告表)】的数据库操作Service
* @createDate 2023-12-10 15:33:35
*/
public interface CafeteriaNoticeService extends IService<CafeteriaNotice> {

    List<CafeteriaNotice> getCafeteriaNoticesByCafeteriaID(Long id);
}
