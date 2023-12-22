package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.DishRank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author HrizonX
* @description 针对表【dish_rank(菜品排名表)】的数据库操作Service
* @createDate 2023-12-20 11:11:55
*/
public interface DishRankService extends IService<DishRank> {

    List<DishRank> getDishRank();
}
