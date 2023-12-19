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
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 添加菜品评价，如果已经评价过（根据userid和dishid查找）则更新评价
     * 每次添加或者更新都要维护菜品在rank表里的总分
     * @param dishRemark
     * @return
     */
    @Override
    public BaseResponse addDishRemark(DishRemark dishRemark) {
        DishRemark dishRemarkOld = dishRemarkMapper.getDishRemarkByUserIdAndDishId(dishRemark.getUserId(), dishRemark.getDishId());
        if(dishRemarkOld != null) { // 该用户评价过该菜品
            log.info("原来菜品评价: {}", dishRemarkOld);
            log.info("更新菜品评价: {}", dishRemark);
            //维护该菜品在rank表里的总分
            dishRemark.setRemarkId(dishRemarkOld.getRemarkId());
            dishRemarkMapper.updateById(dishRemark);
            DishRank dishRank = dishRankMapper.getDishRankByDishId(dishRemark.getDishId());
            dishRank.setTotalScore(dishRank.getTotalScore() - dishRemarkOld.getScore() + dishRemark.getScore());
            dishRankMapper.updateById(dishRank);
            return Result.success("更新成功");
        }
        // 用户未评价过该菜品
        if(!saveAndUpdateRank(dishRemark)){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    /**
     * 用于实际存储到数据库和维护rank表的总分 但添加事务管理 防止两表的数据不统一
     * @param dishRemark
     * @return
     */
    @Transactional
    public boolean saveAndUpdateRank(DishRemark dishRemark){
        // 如果该用户未评价过该菜品 则添加
        dishRemark.setRemarkId(null);
        boolean save = save(dishRemark);
        // 维护该菜品在rank表里的总分
        DishRank dishRank = dishRankMapper.getDishRankByDishId(dishRemark.getDishId());
        dishRank.setTotalScore(dishRank.getTotalScore() + dishRemark.getScore());
        dishRankMapper.updateById(dishRank);
        return save;
    }
}




