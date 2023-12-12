package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.DishRank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【dish_rank(菜品排名表)】的数据库操作Service
* @createDate 2023-12-10 15:34:04
*/
public interface DishRankService extends IService<DishRank> {

    List<DishRank> getDishRank();
}
