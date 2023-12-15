package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Conversation;
import com.usst.usstcafeteriahub.model.entity.User;
import com.usst.usstcafeteriahub.service.ConversationService;
import com.usst.usstcafeteriahub.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.loading.ClassInjector;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Conversation 会话列表 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/conversations/actions")
public class ConversationController {
    @Resource
    private ConversationService conversationService;
    @Resource
    private UserService userService;

    @ApiOperation("根据用户id获取会话列表")
    @GetMapping("/getConversationsByUserId")
    public BaseResponse getConversationsByUserId(@RequestParam Long id) {
        if (id == null){
            return Result.error("参数为空");
        }
        List<Conversation> list = conversationService.getConversationsByUserId(id) ;
        log.info("获取会话列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("查看用户间会话or创建")
    @GetMapping("/createConversation")
    public BaseResponse createConversation(@RequestParam Long userId, @RequestParam Long targetId) {
        if (userId == null || targetId == null){
            return Result.error("参数为空");
        }
        User targetUser = userService.getById(targetId);
        if (targetUser == null){
            return Result.error("目标用户不存在");
        }
        Conversation conversation = conversationService.getConversationByTargetId(userId, targetId);
        log.info("创建会话: {}", conversation);
        if (conversation == null){
            return Result.error("创建会话失败");
        }
        return Result.success(conversation);
    }


}
