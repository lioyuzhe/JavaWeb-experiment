package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.mapper.CafeteriaRemarkMapper;
import com.usst.usstcafeteriahub.mapper.ComplaintMapper;
import com.usst.usstcafeteriahub.mapper.DishRankMapper;
import com.usst.usstcafeteriahub.model.entity.CafeteriaRank;
import com.usst.usstcafeteriahub.model.entity.CafeteriaRemark;
import com.usst.usstcafeteriahub.service.CafeteriaRankService;
import com.usst.usstcafeteriahub.mapper.CafeteriaRankMapper;
import com.usst.usstcafeteriahub.service.DishRankService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author HrizonX
* @description 针对表【cafeteria_rank(食堂排名表)】的数据库操作Service实现
* @createDate 2023-12-20 11:08:22
*/
@Service
@Slf4j
public class CafeteriaRankServiceImpl extends ServiceImpl<CafeteriaRankMapper, CafeteriaRank>
    implements CafeteriaRankService{
    @Resource
    private CafeteriaRankMapper cafeteriaRankMapper;
    @Resource
    private CafeteriaRemarkMapper cafeteriaRemarkMapper;
    @Resource
    private DishRankMapper dishRankMapper;
    @Resource
    private ComplaintMapper complaintMapper;

    /**
     * 获取食堂排行榜,食堂评价分数占30%，食堂管理下的菜品分数占60%，投诉数量 10% 10次扣完为止
     * @return
     */
    @Override
    public List<CafeteriaRank> getCafeteriaRank() {
        // 先获取cafeteriaRank表所有数据
        List<CafeteriaRank> cafeteriaRankList = cafeteriaRankMapper.selectList(null);
        // 遍历cafeteriaRankList，计算每个食堂评价的平均分数
        for (CafeteriaRank cafeteriaRank : cafeteriaRankList) {
            // 计算食堂评价 30%
            // 查询cafeteriaRemark表中cafeteriaId为cafeteriaRank.cafeteriaId的所有数据的数量 num
            int num = cafeteriaRemarkMapper.getCountByCafeteriaId(cafeteriaRank.getCafeteriaId());
            // 计算cafeteriaRank的平均分数 average_score = cafeteriaRank.totalScore / num
            double remarkScore = 0.0;
            if (num == 0) {
                remarkScore = 0.0;
            }else{
                remarkScore = cafeteriaRank.getTotalScore() / num;
            }
            log.info("计算食堂评价 30%: {}", remarkScore);

            // 计算食堂管理下的菜品分数 60%
            // 根据cafeteriaRank的cafeteriaId查询dishRank表中所有菜品
            List<CafeteriaRank> dishRankList = dishRankMapper.getDishRanksBycafeteriaId(cafeteriaRank.getCafeteriaId());
            // dishRankList中的每个菜品的平均分数相加
            double dishRankAverageScore = 0.0;
            for (CafeteriaRank dishRank : dishRankList) {
                dishRankAverageScore += dishRank.getAverageScore();
            }
            // 计算菜品平均分数的平均分数
            double dishRankAverageScoreAverageScore = dishRankAverageScore / dishRankList.size();
            log.info("计算菜品平均分数的平均分数 60%: {}", dishRankAverageScoreAverageScore);

            // 计算投诉数量 10% 满分10分 每次扣1分 扣完为止
            // 查询complaint表中cafeteriaId为cafeteriaRank.cafeteriaId的所有数据的数量 num
            int complaintNum = complaintMapper.getCountByCafeteriaId(cafeteriaRank.getCafeteriaId());
            double complaintScore = 10 - complaintNum;
            if (complaintScore < 0) {
                complaintScore = 0;
            }
            log.info("计算投诉数量 10%: {}", complaintScore);


            // 计算出最终分数
            double finalScore = remarkScore * 0.3 + dishRankAverageScoreAverageScore * 0.6 + complaintScore * 0.1;
            log.info("计算出最终分数: {}", finalScore);

            cafeteriaRank.setAverageScore(finalScore);
        }

        // 根据average_score重新排序，返回排序后的cafeteriaRankList
        cafeteriaRankList.sort((o1, o2) -> {
            if (o1.getAverageScore() > o2.getAverageScore()) {
                return -1;
            } else if (o1.getAverageScore() < o2.getAverageScore()) {
                return 1;
            } else {
                return 0;
            }
        });
        return cafeteriaRankList;
    }
}




