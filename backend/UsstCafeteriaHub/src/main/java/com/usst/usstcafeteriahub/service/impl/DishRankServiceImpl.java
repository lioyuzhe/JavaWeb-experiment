package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.mapper.DishRemarkMapper;
import com.usst.usstcafeteriahub.model.entity.DishRank;
import com.usst.usstcafeteriahub.service.DishRankService;
import com.usst.usstcafeteriahub.mapper.DishRankMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author HrizonX
* @description 针对表【dish_rank(菜品排名表)】的数据库操作Service实现
* @createDate 2023-12-20 11:11:55
*/
@Service
@Slf4j
public class DishRankServiceImpl extends ServiceImpl<DishRankMapper, DishRank>
    implements DishRankService{
    @Resource
    private DishRankMapper dishRankMapper;
    @Resource
    private DishRemarkMapper dishRemarkMapper;

    /**
     * 返回菜品排名。
     * 先获取dishRank所有表，
     * 遍历(DishRank dishRank : dishRankList){
     *      查询dishRemark表中dishId为dishRank.dishId的所有数据的数量 num
     *      计算dishRank的平均分数 average_score = dishRank.totalScore / num
     * }
     * 根据average_score重新排序，返回排序后的dishRankList
     * @return
     */
    @Override
    public List<DishRank> getDishRank() {
        // 先获取dishRank表所有数据
        List<DishRank> dishRankList = dishRankMapper.selectList(null);
        log.info("排序前菜品排行榜: {}", dishRankList);

        // 遍历dishRankList，计算每个菜品的平均分数
        for (DishRank dishRank : dishRankList) {
            // 查询dishRemark表中dishId为dishRank.dishId的所有数据的数量 num
            int num = dishRemarkMapper.getCountByDishId(dishRank.getDishId());
            // 计算dishRank的平均分数 average_score = dishRank.totalScore / num
            double averageScore = 0.0;
            if (num == 0) {
                averageScore = 0.0;
            }else{
                averageScore = dishRank.getTotalScore() / num;
            }
            dishRank.setAverageScore(averageScore);
        }

        // 根据average_score重新排序，返回排序后的dishRankList
        dishRankList.sort((o1, o2) -> {
            if (o1.getAverageScore() > o2.getAverageScore()) {
                return -1;
            } else if (o1.getAverageScore() < o2.getAverageScore()) {
                return 1;
            } else {
                return 0;
            }
        });
        log.info("菜品排行榜: {}", dishRankList);
        return dishRankList;
    }
}




