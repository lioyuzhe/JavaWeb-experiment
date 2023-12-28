package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.mapper.DishRankMapper;
import com.usst.usstcafeteriahub.model.entity.Dish;
import com.usst.usstcafeteriahub.model.entity.DishRank;
import com.usst.usstcafeteriahub.service.DishRankService;
import com.usst.usstcafeteriahub.service.DishService;
import com.usst.usstcafeteriahub.mapper.DishMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    private DishRankService dishRankService;
    @Resource
    private DishRankMapper dishRankMapper;

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
     * 添加菜品，但如果数据库中已经有相同的cafeteriaName和name，则不添加。且每次添加时自动在dish_rank表里补充该菜品
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
        boolean dishsaved = addDishAndDishRank(dish);
        if (!dishsaved) {
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    /**
     * 事务管理保证dish和dishRank表同时添加
     * @param dish
     * @return
     */
    @Transactional
    public boolean addDishAndDishRank(Dish dish){
        boolean dishsaved = this.save(dish);
        // 同时添加到菜品排名
        DishRank dishRank = new DishRank();
        dishRank.setCafeteriaId(dish.getCafeteriaId());
        dishRank.setCafeteriaName(dish.getCafeteriaName());
        dishRank.setDishId(dish.getDishId());
        dishRank.setDishName(dish.getName());
        dishRank.setTotalScore(0.0);
        dishRankService.save(dishRank);

        return dishsaved;
    }


    /**
     * 删除菜品同时删除菜品排行表里的菜品
     * @param dish
     * @return
     */
    @Override
    public BaseResponse removeDish(Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        boolean dishRemoved = removeDishAndDishRank(dish);
        if (!dishRemoved) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    /**
     * 事务管理保证dish和dishRank表同时添加
     * @param dish
     * @return
     */
    @Transactional
    public boolean removeDishAndDishRank(Dish dish){
        boolean dishRemoved = this.removeById(dish.getDishId());
        dishRankMapper.removeDishRankByDishId(dish.getDishId());
        return dishRemoved;
    }



    /**
     * 更新菜品同时更新菜品排行表里的菜品
     * @param dish
     * @return
     */
    @Override
    public BaseResponse updateDish(Dish dish) {
        if (dish == null) {
            return Result.error("参数为空");
        }
        boolean dishUpdated = updateDishAndDishRank(dish);
        if (!dishUpdated) {
            return Result.error("更新失败");
        }
        return Result.success("更新成功");
    }

    @Override
    public List<Dish> getTopNRecommendDish(int n) {
        Page<Dish> page = new Page<>(1, n);
        QueryWrapper<Dish> query = new QueryWrapper<>();
        query.lambda().eq(Dish::getStatus, 1);
        return dishMapper.selectPage(page, query).getRecords();
    }

    /**
     * 事务管理保证dish和dishRank表同时更新
     * @param dish
     * @return
     */
    @Transactional
    public boolean updateDishAndDishRank(Dish dish){
        // 更新菜品表
        boolean dishUpdated = this.updateById(dish);
        // 同步跟新菜品排名表
        DishRank dishRank = dishRankMapper.getDishRankByDishId(dish.getDishId());
        dishRank.setDishName(dish.getName());
        dishRank.setCafeteriaName(dish.getCafeteriaName());
        dishRankMapper.updateById(dishRank);
        return dishUpdated;
    }
}




