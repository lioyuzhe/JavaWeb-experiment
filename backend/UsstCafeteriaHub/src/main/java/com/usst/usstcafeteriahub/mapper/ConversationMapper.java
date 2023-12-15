package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.Conversation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usst.usstcafeteriahub.model.entity.User;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author Klein
* @description 针对表【conversation(会话表)】的数据库操作Mapper
* @createDate 2023-12-13 21:56:14
* @Entity com.usst.usstcafeteriahub.model.entity.Conversation
*/
public interface ConversationMapper extends BaseMapper<Conversation> {

    /**
     * 根据conversationId更新conversation表的last_message和last_message_time
     * @param conversationId
     * @param lastMessage
     * @param lastMessageTime
     */
    void updateLastMessageAndTime(Long conversationId, String lastMessage, LocalDateTime lastMessageTime);

    /**
     * 根据用户id获取私信列表，且read_status为0的在前，时间降序
     * @param id
     * @return
     */
    List<Conversation> getConversationsByUserId(Long id);

    /**
     * 根据两用户id获取用户间会话
     * @param userId
     * @param targetId
     * @return
     */
    Conversation selectByUserIdAndTargetId(Long userId, Long targetId);

    /**
     * 根据两用户User创建会话
     * @param user
     * @param target
     * @return Conversation
     */
    Conversation createConversation(User user, User target);

}




