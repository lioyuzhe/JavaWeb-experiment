package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.CafeteriaRemark;
import com.usst.usstcafeteriahub.service.CafeteriaRemarkService;
import com.usst.usstcafeteriahub.mapper.CafeteriaRemarkMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_remark(食堂评价信息表)】的数据库操作Service实现
* @createDate 2023-12-13 17:42:37
*/
@Service
public class CafeteriaRemarkServiceImpl extends ServiceImpl<CafeteriaRemarkMapper, CafeteriaRemark>
    implements CafeteriaRemarkService{

    @Resource
    private CafeteriaRemarkMapper cafeteriaRemarkMapper;

    /**
     * 按照食堂ID筛选食堂评价
     * @param id
     * @return
     */
    @Override
    public List<CafeteriaRemark> getCafeteriaRemarksByCafeteriaID(Long id) {
        return cafeteriaRemarkMapper.getCafeteriaRemarksByCafeteriaID(id);
    }

    /**
     * 按照用户ID筛选食堂评价
     * @param id
     * @return
     */
    @Override
    public List<CafeteriaRemark> getCafeteriaRemarksByUserID(Long id) {
        return cafeteriaRemarkMapper.getCafeteriaRemarksByUserID(id);

    }
}




