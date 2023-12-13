package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.Conversation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【conversation(会话表)】的数据库操作Service
* @createDate 2023-12-13 21:56:14
*/
public interface ConversationService extends IService<Conversation> {
    /**
     * 根据用户id获取私信列表，时间降序
     * @param id
     * @return
     */
    List<Conversation> getConversationsByUserId(Long id);

    /**
     * 获取用户间会话，不存在则创建
     * @param userId
     * @param targetId
     * @return
     */
    Conversation getConversationByTargetId(Long userId, Long targetId);
}
