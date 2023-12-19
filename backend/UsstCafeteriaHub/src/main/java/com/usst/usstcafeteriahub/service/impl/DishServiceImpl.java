package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Dish;
import com.usst.usstcafeteriahub.service.DishService;
import com.usst.usstcafeteriahub.mapper.DishMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【dish(菜品维护表)】的数据库操作Service实现
* @createDate 2023-12-10 15:34:02
*/
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{
    @Resource
    private DishMapper dishMapper;

    /**
     * 根据菜品的菜系获取菜品列表
     * @param cuisine
     * @return
     */
    @Override
    public List<Dish> getDishByCuisine(String cuisine) {
        return dishMapper.getDishByCuisine(cuisine);
    }

    /**
     * 获取所有菜品的列表，并按菜品价格排序,接受参数order，1为从低到高，2为从高到低
     * @param order
     * @return
     */
    @Override
    public List<Dish> getDishOrderByPrice(Integer order) {
        if (order == 1) {
            return dishMapper.getDishOrderByPriceAsc();
        }
        return dishMapper.getDishOrderByPriceDesc();
    }

    /**
     * 根据菜品所属食堂获取菜品列表
     * @param cafeteriaId
     * @return
     */
    @Override
    public List<Dish> getDishByCafeteriaID(Long cafeteriaId) {
        return dishMapper.getDishByCafeteriaID(cafeteriaId);
    }

    /**
     * 获取推荐菜品
     * @return
     */
    @Override
    public List<Dish> getRecommendDish() {
        return dishMapper.getRecommendDish();
    }

    /**
     * 添加菜品，但如果数据库中已经有相同的cafeteriaName和name，则不添加
     * @param dish
     * @return
     */
    @Override
    public BaseResponse add(Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        List<Dish> dishes = dishMapper.selectList(null);
        for (Dish d : dishes) {
            if (d.getCafeteriaName().equals(dish.getCafeteriaName()) && d.getName().equals(dish.getName())) {
                return Result.error("已经存在相同的菜品");
            }
        }
        boolean save = this.save(dish);
        if (!save) {
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }
}




