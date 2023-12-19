package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.model.entity.DishRemark;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【dish_remark(菜品评价表)】的数据库操作Service
* @createDate 2023-12-10 15:34:09
*/
public interface DishRemarkService extends IService<DishRemark> {

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

    /**
     * 用户添加&修改菜品评价，如果已经评价过则更新评价
     * @param dishRemark
     * @return
     */
    BaseResponse addDishRemark(DishRemark dishRemark);
}
