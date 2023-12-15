package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.CommunityMessage;
import com.usst.usstcafeteriahub.service.CommunityMessageService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * communityMessage接口
 * @author 黄泽旭
 */
@RestController
@Slf4j
@RequestMapping("/communityMessages/actions")
public class CommunityMessageController {
    @Resource
    private CommunityMessageService communityMessageService;

    @ApiOperation("根据用户id筛选communityMessage")
    @GetMapping("/getCommunityMessageByUserId")
    public BaseResponse getCommunityMessageByUserId(@RequestParam Long userId) {
        if (userId == null) {
            return Result.error("参数为空");
        }
        List<CommunityMessage> list = communityMessageService.getCommunityMessageByUserId(userId);
        if (list == null) {
            return Result.error("查询失败");
        }
        log.info("根据用户id筛选communityMessage: {}", list);
        return Result.success(list);
    }



    @ApiOperation("根据文章名进行模糊查询")
    @GetMapping("/getCommunityMessageByCommunityMessageName")
    public BaseResponse getCommunityMessageByTitle(@RequestParam String communityMessageName) {
        if (communityMessageName == null) {
            return Result.error("参数为空");
        }
        List<CommunityMessage> list = communityMessageService.getCommunityMessageByTitle(communityMessageName);
        if (list == null) {
            return Result.error("查询失败");
        }
        log.info("根据文章名进行模糊查询: {}", list);
        return Result.success(list);
    }

    @ApiOperation("根据点赞数由高到低获取文章列表")
    @GetMapping("/getCommunityMessageByLike")
    public BaseResponse getCommunityMessageByLike() {
        List<CommunityMessage> list = communityMessageService.getCommunityMessageByLike();
        if (list == null) {
            return Result.error("查询失败");
        }
        log.info("根据点赞数由高到低获取文章列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("根据时间由近到远获取文章列表")
    @GetMapping("/getCommunityMessageByTime")
    public BaseResponse getCommunityMessageByTime() {
        List<CommunityMessage> list = communityMessageService.getCommunityMessageByTime();
        if (list == null) {
            return Result.error("查询失败");
        }
        log.info("根据时间由近到远获取文章列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("添加文章")
    @PostMapping("/addCommunityMessage")
    public BaseResponse addCommunityMessage(@RequestBody CommunityMessage communityMessage) {
        if (communityMessage == null) {
            return Result.error("参数为空");
        }
        log.info("添加文章: {}", communityMessage);
        communityMessage.setCreateTime(LocalDateTime.now());
        boolean save = communityMessageService.save(communityMessage);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @ApiOperation("删除文章")
    @PostMapping("/deleteCommunityMessage")
    public BaseResponse deleteCommunityMessage(@RequestBody CommunityMessage communityMessage) {
        if (communityMessage == null) {
            return Result.error("参数为空");
        }
        log.info("要删除文章: {}", communityMessage);
        boolean remove = communityMessageService.removeById(communityMessage.getCommunityId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("修改文章")
    @PostMapping("/updateCommunityMessage")
    public BaseResponse updateCommunityMessage(@RequestBody CommunityMessage communityMessage) {
        if (communityMessage == null) {
            return Result.error("参数为空");
        }
        log.info("修改文章: {}", communityMessage);
        boolean update = communityMessageService.updateById(communityMessage);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }

}
