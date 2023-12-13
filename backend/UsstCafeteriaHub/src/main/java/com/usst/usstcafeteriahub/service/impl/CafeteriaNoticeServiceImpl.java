package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.CafeteriaNotice;
import com.usst.usstcafeteriahub.service.CafeteriaNoticeService;
import com.usst.usstcafeteriahub.mapper.CafeteriaNoticeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_notice(食堂活动公告表)】的数据库操作Service实现
* @createDate 2023-12-10 15:33:35
*/
@Service
public class CafeteriaNoticeServiceImpl extends ServiceImpl<CafeteriaNoticeMapper, CafeteriaNotice>
    implements CafeteriaNoticeService{

    @Resource
    private CafeteriaNoticeMapper cafeteriaNoticeMapper;

    /**
     * 按照食堂ID筛选食堂公告
     * @param id
     * @return
     */
    @Override
    public List<CafeteriaNotice> getCafeteriaNoticesByCafeteriaID(Long id) {
        return cafeteriaNoticeMapper.getCafeteriaNoticesByCafeteriaID(id);
    }
}




