package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.mapper.DishRankMapper;
import com.usst.usstcafeteriahub.model.entity.DishRank;
import com.usst.usstcafeteriahub.model.entity.DishRemark;
import com.usst.usstcafeteriahub.service.DishRemarkService;
import com.usst.usstcafeteriahub.mapper.DishRemarkMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【dish_remark(菜品评价表)】的数据库操作Service实现
* @createDate 2023-12-10 15:34:09
*/
@Service
@Slf4j
public class DishRemarkServiceImpl extends ServiceImpl<DishRemarkMapper, DishRemark>
    implements DishRemarkService{

    @Resource
    private DishRemarkMapper dishRemarkMapper;

    @Resource
    private DishRankMapper dishRankMapper;

    /**
     * 根据菜品ID获取菜品评价
     * @param dishId
     * @return
     */
    @Override
    public List<DishRemark> getDishRemarkByDishId(Long dishId) {
        return baseMapper.getDishRemarkByDishId(dishId);
    }

    /**
     * 根据用户ID和菜品ID获取菜品评价
     * @param userId
     * @param dishId
     * @return
     */
    @Override
    public DishRemark getDishRemarkByUserIdAndDishId(Long userId, Long dishId) {
        DishRemark dishRemarkByUserIdAndDishId = dishRemarkMapper.getDishRemarkByUserIdAndDishId(userId, dishId);
        return dishRemarkByUserIdAndDishId;
    }

    @Override
    public BaseResponse addDishRemark(DishRemark dishRemark) {
        DishRemark dishRemarkOld = dishRemarkMapper.getDishRemarkByUserIdAndDishId(dishRemark.getUserId(), dishRemark.getDishId());
        if(dishRemarkOld != null) { // 该用户评价过该菜品
            log.info("原来菜品评价: {}", dishRemarkOld);
            log.info("更新菜品评价: {}", dishRemark);
            //维护改菜品在rank表里的总分
            DishRank dishRank = dishRankMapper.getDishRankByDishId(dishRemark.getDishId());
            dishRank.setTotalScore(dishRank.getTotalScore() - dishRemarkOld.getScore() + dishRemark.getScore());
            return Result.success("更新成功");
        }
        // 该用户未评价过该菜品
        dishRemark.setRemarkId(null);
        boolean save = save(dishRemark);
        //维护改菜品在rank表里的总分
        DishRank dishRank = dishRankMapper.getDishRankByDishId(dishRemark.getDishId());

        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }
}




