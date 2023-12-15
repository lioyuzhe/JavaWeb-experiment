package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.DishRemark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Klein
* @description 针对表【dish_remark(菜品评价表)】的数据库操作Mapper
* @createDate 2023-12-10 15:34:09
* @Entity com.usst.usstcafeteriahub.model.entity.DishRemark
*/
public interface DishRemarkMapper extends BaseMapper<DishRemark> {

    /**
     * 根据菜品ID获取菜品评价
     * @param dishId
     * @return
     */
    List<DishRemark> getDishRemarkByDishId(Long dishId);

    /**
     * 根据用户ID和菜品ID获取菜品评价
     * @param userId
     * @param dishId
     * @return
     */
    DishRemark getDishRemarkByUserIdAndDishId(Long userId, Long dishId);
}




