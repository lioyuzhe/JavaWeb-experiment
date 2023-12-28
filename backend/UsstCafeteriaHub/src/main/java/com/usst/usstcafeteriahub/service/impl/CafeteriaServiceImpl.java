package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Cafeteria;
import com.usst.usstcafeteriahub.model.entity.CafeteriaAdmin;
import com.usst.usstcafeteriahub.model.entity.CafeteriaManage;
import com.usst.usstcafeteriahub.model.entity.CafeteriaRank;
import com.usst.usstcafeteriahub.service.CafeteriaAdminService;
import com.usst.usstcafeteriahub.service.CafeteriaManageService;
import com.usst.usstcafeteriahub.service.CafeteriaRankService;
import com.usst.usstcafeteriahub.service.CafeteriaService;
import com.usst.usstcafeteriahub.mapper.CafeteriaMapper;
import com.usst.usstcafeteriahub.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author Klein
* @description 针对表【cafeteria(食堂基本信息表)】的数据库操作Service实现
* @createDate 2023-12-10 14:44:38
*/
@Slf4j
@Service
public class CafeteriaServiceImpl extends ServiceImpl<CafeteriaMapper, Cafeteria>
    implements CafeteriaService{

    @Resource
    private CafeteriaMapper cafeteriaMapper;
    @Resource
    private CafeteriaRankService cafeteriaRankService;
    @Resource
    private CafeteriaAdminService cafeteriaAdminService;
    @Resource
    private CafeteriaManageService cafeteriaManageService;

    /**
     * 添加食堂，但如果数据库中已经有相同的cafeteriaName，则返回“已存在同名食堂”。每次添加食堂时，都会添加一条食堂排名
     * @param cafeteria
     * @return
     */
    @Override
    public BaseResponse add(Cafeteria cafeteria, HttpServletRequest request) {
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

        // 同时要在cafeteria_manage表中添加一条记录
        CafeteriaManage cafeteriaManage = new CafeteriaManage();
        // 从token中获取食堂管理员的account
        String token = request.getHeader("token");
        log.info("请求头中的token:{}",token);
        String account = JwtUtils.parseToken(token).get("account").toString();
        log.info("解析token得到的account:{}",account);
        // 根据account获取食堂管理员的id
        CafeteriaAdmin cafeteriaAdmin = cafeteriaAdminService.getOne(new QueryWrapper<CafeteriaAdmin>().eq("account", account));
        log.info("根据account获取食堂管理员: {}", cafeteriaAdmin);
        cafeteriaManage.setAdminId(cafeteriaAdmin.getAdminId());
        cafeteriaManage.setCafeteriaId(cafeteria.getCafeteriaId());


        cafeteriaManageService.save(cafeteriaManage);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }
}




