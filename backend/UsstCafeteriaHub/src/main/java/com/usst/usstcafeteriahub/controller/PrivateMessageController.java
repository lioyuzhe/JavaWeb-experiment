package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.PrivateMessages;
import com.usst.usstcafeteriahub.service.PrivateMessagesService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会话的聊天记录 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/privateMessages/actions")
public class PrivateMessageController {
    @Resource
    private PrivateMessagesService privateMessagesService;

    @ApiOperation("根据conversationId获取私信列表（聊天记录）时间升序")
    @GetMapping("/getPrivateMessagesByConversationId")
    public BaseResponse getPrivateMessagesByConversationId(@RequestParam Long conversationId) {
        if (conversationId == null){
            return Result.error("参数为空");
        }
        List<PrivateMessages> list = privateMessagesService.getPrivateMessagesByConversationId(conversationId) ;
        log.info("获取私信列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("根据发送方Id和接受方Id获取私信，未读优先，时间降序")
    @GetMapping("/getPrivateMessagesBySenderIdAndReceiverId")
    public BaseResponse getPrivateMessagesBySenderIdAndReceiverId(@RequestParam Long senderId, @RequestParam Long receiverId) {
        if (senderId == null || receiverId == null) {
            return Result.error("参数缺少");
        }
        PrivateMessages privateMessages = privateMessagesService.getPrivateMessagesBySenderIdAndReceiverId(senderId, receiverId);
        log.info("根据id获取私信: {}", privateMessages);
        return Result.success(privateMessages);
    }

    // @ApiOperation("(废置)根据用户id获取私信列表（聊天记录）时间升序")
    // @GetMapping("/getPrivateMessages")
    // public BaseResponse getPrivateMessages(@RequestParam Long id) {
    //     if (id == null){
    //         return Result.error("参数为空");
    //     }
    //     List<PrivateMessages> list = privateMessagesService.getPrivateMessages(id) ;
    //     log.info("获取私信列表: {}", list);
    //     return Result.success(list);
    // }

    // @ApiOperation("（废置）根据用户id获取私信，未读优先，时间降序")
    // @GetMapping("/getPrivateMessagesById")
    // public BaseResponse getPrivateMessagesById(@RequestParam Long id) {
    //     if (id == null) {
    //         return Result.error("参数为空");
    //     }
    //     PrivateMessages privateMessages = privateMessagesService.getPrivateMessagesById(id);
    //     log.info("根据id获取私信: {}", privateMessages);
    //     return Result.success(privateMessages);
    // }

    @ApiOperation("添加私信")
    @PostMapping("/addPrivateMessages")
    public BaseResponse addPrivateMessages(@RequestBody PrivateMessages privateMessages) {
        if (privateMessages == null) {
            return Result.error("参数为空");
        }
        log.info("添加私信: {}", privateMessages);
        boolean ans = privateMessagesService.addPrivateMessages(privateMessages);
        if(!ans){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    // @ApiOperation("（废置）私信设置已读")
    // @PostMapping("/setRead")
    // public BaseResponse setRead(@RequestBody PrivateMessages privateMessages) {
    //     if (privateMessages == null) {
    //         return Result.error("参数为空");
    //     }
    //     log.info("私信设置已读: {}", privateMessages);
    //     boolean ans = privateMessagesService.setRead(privateMessages);
    //     if(!ans){
    //         return Result.error("设置失败");
    //     }
    //     return Result.success("设置成功");
    // }



}
