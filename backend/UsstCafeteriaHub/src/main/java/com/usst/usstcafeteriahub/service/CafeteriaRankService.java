package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.CafeteriaRank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author HrizonX
* @description 针对表【cafeteria_rank(食堂排名表)】的数据库操作Service
* @createDate 2023-12-20 06:55:19
*/
public interface CafeteriaRankService extends IService<CafeteriaRank> {

    /**
     * 获取食堂排行榜,食堂评价分数占30%，食堂管理下的菜品分数占60%，投诉数量 10% 10次扣完为止
     * @return
     */
    List<CafeteriaRank> getCafeteriaRank();
}
