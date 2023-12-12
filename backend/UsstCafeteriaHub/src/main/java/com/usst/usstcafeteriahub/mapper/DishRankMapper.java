package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.DishRank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Klein
* @description 针对表【dish_rank(菜品排名表)】的数据库操作Mapper
* @createDate 2023-12-10 15:34:04
* @Entity com.usst.usstcafeteriahub.model.entity.DishRank
*/
public interface DishRankMapper extends BaseMapper<DishRank> {

    /**
     *
     * @return
     */
    List<DishRank> getDishRankByScore();
}




