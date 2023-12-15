package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.mapper.UserMapper;
import com.usst.usstcafeteriahub.model.entity.Conversation;
import com.usst.usstcafeteriahub.model.entity.User;
import com.usst.usstcafeteriahub.service.ConversationService;
import com.usst.usstcafeteriahub.mapper.ConversationMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【conversation(会话表)】的数据库操作Service实现
* @createDate 2023-12-13 21:56:14
*/
@Service
@Slf4j
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation>
    implements ConversationService{

    @Resource
    private ConversationMapper conversationMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户id获取私信列表，时间降序
     * @param id
     * @return
     */
    @Override
    public List<Conversation> getConversationsByUserId(Long id) {
        return baseMapper.getConversationsByUserId(id);
    }

    /**
     * 获取用户间会话，不存在则创建
     * @param userId
     * @param targetId
     * @return
     */
    @Override
    public Conversation getConversationByTargetId(Long userId, Long targetId) {
        Conversation conversation = conversationMapper.selectByUserIdAndTargetId(userId, targetId);
        if(conversation == null){
            log.info("会话不存在，创建会话");
            User user = userMapper.selectById(userId);
            User targetUser = userMapper.selectById(targetId);
            conversation = conversationMapper.createConversation(user, targetUser);
        }
        log.info("会话: {}", conversation);
        return conversation;
    }
}




