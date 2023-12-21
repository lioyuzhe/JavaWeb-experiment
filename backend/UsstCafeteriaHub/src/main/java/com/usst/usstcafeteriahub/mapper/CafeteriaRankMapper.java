package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.CafeteriaRank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HrizonX
* @description 针对表【cafeteria_rank(食堂排名表)】的数据库操作Mapper
* @createDate 2023-12-20 06:55:19
* @Entity com.usst.usstcafeteriahub.model.entity.CafeteriaRank
*/
public interface CafeteriaRankMapper extends BaseMapper<CafeteriaRank> {

    /**
     * 获取食堂排行榜,totalScore,由高到低排列
     * @return
     */
    List<CafeteriaRank> getCafeteriaRank();

    /**
     * 根据食堂ID获取食堂排行榜
     * @param cafeteriaId
     * @return
     */
    CafeteriaRank getCafeteriaRankByCafeteriaId(Long cafeteriaId);
}




