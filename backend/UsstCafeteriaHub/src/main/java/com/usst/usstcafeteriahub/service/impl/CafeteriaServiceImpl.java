package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Cafeteria;
import com.usst.usstcafeteriahub.model.entity.CafeteriaRank;
import com.usst.usstcafeteriahub.service.CafeteriaRankService;
import com.usst.usstcafeteriahub.service.CafeteriaService;
import com.usst.usstcafeteriahub.mapper.CafeteriaMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author Klein
* @description 针对表【cafeteria(食堂基本信息表)】的数据库操作Service实现
* @createDate 2023-12-10 14:44:38
*/
@Service
public class CafeteriaServiceImpl extends ServiceImpl<CafeteriaMapper, Cafeteria>
    implements CafeteriaService{

    @Resource
    private CafeteriaMapper cafeteriaMapper;
    @Resource
    private CafeteriaRankService cafeteriaRankService;

    /**
     * 添加食堂，但如果数据库中已经有相同的cafeteriaName，则返回“已存在同名食堂”。每次添加食堂时，都会添加一条食堂排名
     * @param cafeteria
     * @return
     */
    @Override
    public BaseResponse add(Cafeteria cafeteria) {
        Cafeteria cafeteriaOld = cafeteriaMapper.getCafeteriaByName(cafeteria.getName());
        if (cafeteriaOld != null) {
            return Result.error("已存在同名食堂");
        }
        boolean save = save(cafeteria);
        CafeteriaRank cafeteriaRank = new CafeteriaRank();
        cafeteriaRank.setCafeteriaId(cafeteria.getCafeteriaId());
        cafeteriaRank.setCafeteriaName(cafeteria.getName());
        cafeteriaRank.setTotalScore(0.0);
        cafeteriaRankService.save(cafeteriaRank);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }
}




