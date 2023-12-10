package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.Dish;
import com.usst.usstcafeteriahub.service.DishService;
import com.usst.usstcafeteriahub.mapper.DishMapper;
import org.springframework.stereotype.Service;

/**
* @author Klein
* @description 针对表【dish(菜品维护表)】的数据库操作Service实现
* @createDate 2023-12-10 15:34:02
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

}




