package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.CafeteriaRank;
import com.usst.usstcafeteriahub.model.entity.DishRank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HrizonX
* @description 针对表【dish_rank(菜品排名表)】的数据库操作Mapper
* @createDate 2023-12-20 05:44:28
* @Entity com.usst.usstcafeteriahub.model.entity.DishRank
*/
public interface DishRankMapper extends BaseMapper<DishRank> {

    /**
     * 返回菜品排名并按照分数降序排序
     * @return
     */
    List<DishRank> getDishRankByScore();

    /**
     * 根据菜品ID获取菜品排名
     * @param dishId
     * @return
     */
    DishRank getDishRankByDishId(Long dishId);

    /**
     * 根据食堂ID获取菜品排名
     * @param cafeteriaId
     * @return
     */
    List<CafeteriaRank> getDishRanksBycafeteriaId(Long cafeteriaId);
}




