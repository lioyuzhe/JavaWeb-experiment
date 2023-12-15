package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.CafeteriaNotice;
import com.usst.usstcafeteriahub.service.CafeteriaNoticeService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CafeteriaNotice 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/cafeteriaNotices/actions")
public class CafeteriaNoticeController {
    @Resource
    private CafeteriaNoticeService cafeteriaNoticeService;

    @ApiOperation("查询食堂公告列表")
    @GetMapping("/getCafeteriaNotices")
    public BaseResponse getCafeteriaNotices() {
        List<CafeteriaNotice> list = cafeteriaNoticeService.list();
        log.info("获取食堂公告列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("添加食堂公告")
    @PostMapping("/addCafeteriaNotice")
    public BaseResponse addCafeteriaNotice(@RequestBody CafeteriaNotice cafeteriaNotice) {
        if (cafeteriaNotice == null) {
            return Result.error("参数为空");
        }
        log.info("添加食堂公告: {}", cafeteriaNotice);
        boolean save = cafeteriaNoticeService.save(cafeteriaNotice);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

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
