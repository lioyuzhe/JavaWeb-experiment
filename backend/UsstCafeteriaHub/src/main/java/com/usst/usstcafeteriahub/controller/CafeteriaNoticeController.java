package com.usst.usstcafeteriahub.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Log;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.CafeteriaNotice;
import com.usst.usstcafeteriahub.service.CafeteriaNoticeService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 食堂公告 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/cafeteriaNotices/actions")
public class CafeteriaNoticeController {
    @Resource
    private CafeteriaNoticeService cafeteriaNoticeService;

    @Log
    @ApiOperation(value = "多条件模糊查询食堂公告信息")
    @GetMapping("/getCafeteriaNoticesByConditions")
    public BaseResponse getCafeteriaNoticesByConditions(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @RequestParam String cafeteriaName,
                                            @RequestParam String title) {
        QueryWrapper<CafeteriaNotice> queryWrapper = new QueryWrapper<CafeteriaNotice>().orderByDesc("notice_Id");  // 默认倒序，让最新的数据在最上面
        // 如果 cafeteriaName 和 title 参数不为空，添加 like 查询
        queryWrapper.like(StrUtil.isNotBlank(cafeteriaName), "cafeteria_name", cafeteriaName);
        queryWrapper.like(StrUtil.isNotBlank(title), "title", title);

        Page<CafeteriaNotice> page = cafeteriaNoticeService.page(new Page<>(pageNum, pageSize), queryWrapper);

        return Result.success(page);
    }




    @Log
    @ApiOperation("查询食堂公告列表")
    @GetMapping("/getCafeteriaNotices")
    public BaseResponse getCafeteriaNotices() {
        List<CafeteriaNotice> list = cafeteriaNoticeService.list();
        log.info("获取食堂公告列表: {}", list);
        return Result.success(list);
    }




    @Log
    @ApiOperation("添加食堂公告")
    @PostMapping("/addCafeteriaNotice")
    public BaseResponse addCafeteriaNotice(@RequestBody CafeteriaNotice cafeteriaNotice) {
        if (cafeteriaNotice == null) {
            return Result.error("参数为空");
        }
        log.info("添加食堂公告: {}", cafeteriaNotice);
        cafeteriaNotice.setCreateTime(LocalDateTime.now());
        boolean save = cafeteriaNoticeService.save(cafeteriaNotice);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }


    @Log
    @ApiOperation("按照食堂ID筛选食堂公告")
    @GetMapping("/getCafeteriaNoticesByCafeteriaID")
    public BaseResponse getCafeteriaNoticesByCafeteriaID(@RequestParam Long id){
        if(id == null){
            return Result.error("参数为空");
        }
        log.info("传入食堂id参数: {}", id);
        List<CafeteriaNotice> list = cafeteriaNoticeService.getCafeteriaNoticesByCafeteriaID(id);
        log.info("获取食堂公告列表: {}", list);
        return Result.success(list);
    }


    @Log
    @ApiOperation("删除食堂公告")
    @PostMapping("/deleteCafeteriaNotice")
    public BaseResponse deleteCafeteriaNotice(@RequestBody CafeteriaNotice cafeteriaNotice) {
        if (cafeteriaNotice == null) {
            return Result.error("参数为空");
        }
        log.info("要删除食堂公告: {}", cafeteriaNotice);
        boolean remove = cafeteriaNoticeService.removeById(cafeteriaNotice.getNoticeId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }


    @Log
    @ApiOperation("修改食堂公告")
    @PostMapping("/updateCafeteriaNotice")
    public BaseResponse updateCafeteriaNotice(@RequestBody CafeteriaNotice cafeteriaNotice) {
        if (cafeteriaNotice == null) {
            return Result.error("参数为空");
        }
        log.info("要修改食堂公告: {}", cafeteriaNotice);
        boolean update = cafeteriaNoticeService.updateById(cafeteriaNotice);
        if (!update) {
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }


}
