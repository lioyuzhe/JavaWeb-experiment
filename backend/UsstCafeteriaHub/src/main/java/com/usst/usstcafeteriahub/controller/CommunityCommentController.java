package com.usst.usstcafeteriahub.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Log;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.CommunityComment;
import com.usst.usstcafeteriahub.service.CommunityCommentService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 社区消息评论
 * @author 李英杰
 */
@Slf4j
@RestController
@RequestMapping("/community/comments")
public class CommunityCommentController {

    @Resource
    private CommunityCommentService communityCommentService;


    @Log
    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    public BaseResponse test(){
        return Result.error("功能还未开发");
    }



    @Log
    @ApiOperation("添加评论")
    @PostMapping
    public BaseResponse addComment(@RequestBody CommunityComment comment) {
        // 这里调用服务层的方法添加评论
        // 示例代码，具体逻辑根据您的服务层实现
        if (comment==null){
            return Result.error("评论不能为空");
        }
        comment.setCreateTime(LocalDateTime.now());
        boolean save = communityCommentService.save(comment);
        if (save ){
            return Result.success("添加成功");
        }else {
            return Result.error("添加失败 ");
        }
    }


    @Log
    @ApiOperation("删除评论")
    @DeleteMapping("/{commentId}")
    public BaseResponse deleteComment(@PathVariable Long commentId) {
        // 调用服务层的方法删除评论
        if (communityCommentService.removeById(commentId)){
            return Result.success("删除成功");
        }else {
            return Result.error("删除失败");
        }

    }


    @Log
    @ApiOperation("更新评论")
    @PutMapping
    public BaseResponse updateComment(@RequestBody CommunityComment comment) {
        // 调用服务层的方法更新评论
        if (comment==null){
            return Result.error("评论不能为空");
        }
        comment.setCreateTime(LocalDateTime.now());
        if (communityCommentService.updateById(comment)) {
            return Result.success("更新成功");
        }else{
            return Result.success("更新失败");
        }
    }


    @Log
    @ApiOperation("获取特定社区消息id的评论")
    @GetMapping("/{messageId}")
    public BaseResponse getCommentsByMessageId(@PathVariable Long messageId) {
        // 使用 QueryWrapper 来构建查询条件
        QueryWrapper<CommunityComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id", messageId);

        // 调用服务层的方法获取特定评论列表
        List<CommunityComment> comments = communityCommentService.list(queryWrapper);

        if (comments != null && !comments.isEmpty()) {
            return Result.success(comments);
        } else {
            return Result.error("未找到相关评论");
        }
    }


}
