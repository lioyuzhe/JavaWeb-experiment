package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.PrivateMessages;
import com.usst.usstcafeteriahub.service.PrivateMessagesService;
import com.usst.usstcafeteriahub.mapper.PrivateMessagesMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author Klein
* @description 针对表【private_messages(私信表)】的数据库操作Service实现
* @createDate 2023-12-10 15:34:15
*/
@Service
public class PrivateMessagesServiceImpl extends ServiceImpl<PrivateMessagesMapper, PrivateMessages>
    implements PrivateMessagesService{
    @Resource
    private PrivateMessagesMapper privateMessagesMapper;

    @Override
    public boolean addPrivateMessages(PrivateMessages privateMessages) {
        privateMessages.setTimestamp(LocalDateTime.now());//设置时间戳
        privateMessages.setReadStatus(0);//设置未读

        int result = privateMessagesMapper.insert(privateMessages);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setRead(PrivateMessages privateMessages) {
        privateMessages.setReadStatus(1);//设置已读
        int result = privateMessagesMapper.updateById(privateMessages);
        if (result == 1) {
            return true;
        }
        return false;
    }

}




