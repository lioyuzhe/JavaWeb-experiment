package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.mapper.CafeteriaRankMapper;
import com.usst.usstcafeteriahub.model.entity.CafeteriaRank;
import com.usst.usstcafeteriahub.model.entity.CafeteriaRemark;
import com.usst.usstcafeteriahub.model.entity.DishRank;
import com.usst.usstcafeteriahub.model.entity.DishRemark;
import com.usst.usstcafeteriahub.service.CafeteriaRankService;
import com.usst.usstcafeteriahub.service.CafeteriaRemarkService;
import com.usst.usstcafeteriahub.mapper.CafeteriaRemarkMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author Klein
* @description 针对表【cafeteria_remark(食堂评价信息表)】的数据库操作Service实现
* @createDate 2023-12-13 17:42:37
*/
@Service
@Slf4j
public class CafeteriaRemarkServiceImpl extends ServiceImpl<CafeteriaRemarkMapper, CafeteriaRemark>
    implements CafeteriaRemarkService{

    @Resource
    private CafeteriaRemarkMapper cafeteriaRemarkMapper;
    @Resource
    private CafeteriaRankMapper cafeteriaRankMapper;

    /**
     * 按照食堂ID筛选食堂评价
     * @param id
     * @return
     */
    @Override
    public List<CafeteriaRemark> getCafeteriaRemarksByCafeteriaID(Long id) {
        return cafeteriaRemarkMapper.getCafeteriaRemarksByCafeteriaID(id);
    }

    /**
     * 按照用户ID筛选食堂评价
     * @param id
     * @return
     */
    @Override
    public List<CafeteriaRemark> getCafeteriaRemarksByUserID(Long id) {
        return cafeteriaRemarkMapper.getCafeteriaRemarksByUserID(id);

    }

    /**
     * 添加食堂评价如果已经评价过（根据userid和dishid查找）则更新评价
     * 每次添加或者更新都要维护菜品在rank表里的总分
     * @param cafeteriaRemark
     * @return
     */
    @Override
    public BaseResponse addcafeteriaRemark(CafeteriaRemark cafeteriaRemark) {
        //查询是否已经评价过
        Long cafeteriaId = cafeteriaRemark.getCafeteriaId();
        Long userId = cafeteriaRemark.getUserId();
        CafeteriaRemark cafeteriaRemarkOld = cafeteriaRemarkMapper.getCafeteriaRemarkByUserIdAndCafeteriaId(userId, cafeteriaId);
        if(cafeteriaRemarkOld != null){// 评价过
            log.info("原来的食堂评价: {}", cafeteriaRemarkOld);
            log.info("更新食堂评价: {}", cafeteriaRemark);
            cafeteriaRemark.setRemarkId(cafeteriaRemarkOld.getRemarkId());
            cafeteriaRemarkMapper.updateById(cafeteriaRemark);
            // 维护该食堂在rank表里的总分
            CafeteriaRank cafeteriaRank = cafeteriaRankMapper.getCafeteriaRankByCafeteriaId(cafeteriaId);
            cafeteriaRank.setTotalScore(cafeteriaRank.getTotalScore() - cafeteriaRemarkOld.getScore() + cafeteriaRemark.getScore());
            cafeteriaRankMapper.updateById(cafeteriaRank);
            return Result.success("更新成功");
        }
        // 没评价过
        if(!saveAndUpdateRank(cafeteriaRemark)){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    /**
     * 用于实际存储到数据库和维护rank表的总分 但添加事务管理 防止两表的数据不统一
     * @param cafeteriaRemark
     * @return
     */
    @Transactional
    public boolean saveAndUpdateRank(CafeteriaRemark cafeteriaRemark){
        // 如果该用户未评价过该菜品 则添加
        cafeteriaRemark.setRemarkId(null);
        boolean save = save(cafeteriaRemark);
        // 维护该菜品在rank表里的总分
        CafeteriaRank cafeteriaRank = cafeteriaRankMapper.getCafeteriaRankByCafeteriaId(cafeteriaRemark.getCafeteriaId());
        cafeteriaRank.setTotalScore(cafeteriaRank.getTotalScore() + cafeteriaRemark.getScore());
        cafeteriaRankMapper.updateById(cafeteriaRank);
        return save;
    }
}




