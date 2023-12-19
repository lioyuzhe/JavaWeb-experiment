package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Klein
* @description 针对表【dish(菜品维护表)】的数据库操作Mapper
* @createDate 2023-12-10 15:34:02
* @Entity com.usst.usstcafeteriahub.model.entity.Dish
*/
public interface DishMapper extends BaseMapper<Dish> {

    /**
     * 根据菜品的菜系获取菜品列表
     * @param cuisine
     * @return
     */
    List<Dish> getDishByCuisine(String cuisine);

    /**
     * 获取所有菜品的列表，并按菜品价格升序排序
     * @param
     * @return
     */
    List<Dish> getDishOrderByPriceAsc();

    /**
     * 获取所有菜品的列表，并按菜品价格降序排序
     * @param
     * @return
     */
    List<Dish> getDishOrderByPriceDesc();

    /**
     * 根据菜品所属食堂获取菜品列表
     * @param cafeteriaId
     * @return
     */
    List<Dish> getDishByCafeteriaID(Long cafeteriaId);

    /**
     * 获取推荐菜品
     * @return
     */
    List<Dish> getRecommendDish();
}




