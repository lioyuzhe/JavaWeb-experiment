package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【dish(菜品维护表)】的数据库操作Service
* @createDate 2023-12-10 15:34:02
*/
public interface DishService extends IService<Dish> {

    /**
     * 根据菜品的菜系获取菜品列表
     * @param cuisine
     * @return
     */
    List<Dish> getDishByCuisine(String cuisine);

    /**
     * 获取所有菜品的列表，并按菜品价格排序,接受参数order，1为从低到高，2为从高到低
     * @param order
     * @return
     */
    List<Dish> getDishOrderByPrice(Integer order);

    /**
     * 根据菜品所属食堂获取菜品列表
     * @param cafeteriaId
     * @return
     */
    List<Dish> getDishByCafeteriaID(Integer cafeteriaId);
}
