package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.DishRank;
import com.usst.usstcafeteriahub.service.DishRankService;
import com.usst.usstcafeteriahub.mapper.DishRankMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【dish_rank(菜品排名表)】的数据库操作Service实现
* @createDate 2023-12-10 15:34:04
*/
@Service
public class DishRankServiceImpl extends ServiceImpl<DishRankMapper, DishRank>
    implements DishRankService{

    /**
     * 返回菜品排名并按照分数降序排序
     * @return
     */
    @Override
    public List<DishRank> getDishRank() {
        return baseMapper.getDishRankByScore();
    }


}




