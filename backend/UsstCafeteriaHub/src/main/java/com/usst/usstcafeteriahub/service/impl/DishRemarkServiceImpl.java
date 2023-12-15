package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.DishRemark;
import com.usst.usstcafeteriahub.service.DishRemarkService;
import com.usst.usstcafeteriahub.mapper.DishRemarkMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【dish_remark(菜品评价表)】的数据库操作Service实现
* @createDate 2023-12-10 15:34:09
*/
@Service
public class DishRemarkServiceImpl extends ServiceImpl<DishRemarkMapper, DishRemark>
    implements DishRemarkService{

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
        return baseMapper.getDishRemarkByUserIdAndDishId(userId, dishId);
    }
}




