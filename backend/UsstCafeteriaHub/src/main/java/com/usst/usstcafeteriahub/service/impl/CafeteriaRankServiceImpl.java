package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.CafeteriaRank;
import com.usst.usstcafeteriahub.service.CafeteriaRankService;
import com.usst.usstcafeteriahub.mapper.CafeteriaRankMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_rank(食堂排名表)】的数据库操作Service实现
* @createDate 2023-12-10 15:33:38
*/
@Service
public class CafeteriaRankServiceImpl extends ServiceImpl<CafeteriaRankMapper, CafeteriaRank>
    implements CafeteriaRankService{
    @Resource
    private CafeteriaRankMapper cafeteriaRankMapper;

    /**
     * 获取食堂排行榜,食堂评价分数占30%，食堂管理下的菜品分数占60%，投诉数量 10% 10次扣完为止
     * @return
     */
    @Override
    public List<CafeteriaRank> getCafeteriaRank() {
        return cafeteriaRankMapper.getCafeteriaRank();
    }
}




