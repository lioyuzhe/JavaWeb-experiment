package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.CafeteriaRemark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_remark(食堂评价信息表)】的数据库操作Mapper
* @createDate 2023-12-13 17:42:37
* @Entity com.usst.usstcafeteriahub.model.entity.CafeteriaRemark
*/
public interface CafeteriaRemarkMapper extends BaseMapper<CafeteriaRemark> {

    /**
     * 按照食堂ID筛选食堂评价
     * @param cafeteriaId
     * @return
     */
    List<CafeteriaRemark> getCafeteriaRemarksByCafeteriaID(Long cafeteriaId);

    /**
     * 按照用户ID筛选食堂评价
     * @param id
     * @return
     */
    List<CafeteriaRemark> getCafeteriaRemarksByUserID(Long id);
}




