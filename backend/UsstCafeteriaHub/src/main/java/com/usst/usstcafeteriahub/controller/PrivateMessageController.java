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
 * Promotion 接口
 * @author 黄泽旭
 */
@Slf4j
@RestController
@RequestMapping("/privateMessages/actions")
public class PrivateMessageController {
    @Resource
    private PrivateMessagesService privateMessagesService;

    @ApiOperation("获取私信列表")
    @GetMapping("/getPrivateMessages")
    public BaseResponse getPrivateMessages() {
        List<PrivateMessages> list = privateMessagesService.list();
        log.info("获取私信列表: {}", list);
        return Result.success(list);
    }

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

    @ApiOperation("私信设置已读")
    @PostMapping("/setRead")
    public BaseResponse setRead(@RequestBody PrivateMessages privateMessages) {
        if (privateMessages == null) {
            return Result.error("参数为空");
        }
        log.info("私信设置已读: {}", privateMessages);
        boolean ans = privateMessagesService.setRead(privateMessages);
        if(!ans){
            return Result.error("设置失败");
        }
        return Result.success("设置成功");
    }

}
