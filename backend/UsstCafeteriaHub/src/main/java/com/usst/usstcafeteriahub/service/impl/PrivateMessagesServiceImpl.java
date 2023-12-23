package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.mapper.ConversationMapper;
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
* @createDate 2023-12-13 17:25:48
*/
@Service
public class PrivateMessagesServiceImpl extends ServiceImpl<PrivateMessagesMapper, PrivateMessages>
    implements PrivateMessagesService{
    @Resource
    private PrivateMessagesMapper privateMessagesMapper;
    @Resource
    private ConversationMapper conversationMapper;

    /**
     * 创建新私信 同时更新conversation表的last_message和last_message_time
     * @param privateMessages
     * @return
     */
    @Override
    public boolean addPrivateMessages(PrivateMessages privateMessages) {
        privateMessages.setTimestamp(LocalDateTime.now());//设置时间戳
        privateMessages.setReadStatus(0);//设置未读
        //更新conversation表的last_message和last_message_time
        conversationMapper.updateLastMessageAndTime(privateMessages.getConversationId(), privateMessages.getContent(), privateMessages.getTimestamp());
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

    // /**
    //  * 根据用户id获取私信列表，且read_status为0的在前，时间降序
    //  * @param id
    //  * @return
    //  */
    // @Override
    // public PrivateMessages getPrivateMessagesById(Long id) {
    //     PrivateMessages privateMessages = privateMessagesMapper.getPrivateMessagesById(id);
    //     return privateMessages;
    // }

    /**
     * 根据用户id获取消息列表，时间升序
     * @param id
     * @return
     */
    @Override
    public List<PrivateMessages> getPrivateMessages(Long id) {
        return privateMessagesMapper.getPrivateMessages(id);
    }

    /**
     * 根据conversationId获取私信列表，时间升序
     * @param conversationId
     * @return
     */
    @Override
    public List<PrivateMessages> getPrivateMessagesByConversationId(Long conversationId) {
        return privateMessagesMapper.getPrivateMessagesByConversationId(conversationId);
    }

    /**
     * 根据发送方Id和接受方Id获取私信，时间升序
     * @param senderId
     * @param receiverId
     * @return
     */
    @Override
    public PrivateMessages getPrivateMessagesBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        return privateMessagesMapper.getPrivateMessagesBySenderIdAndReceiverId(senderId, receiverId);
    }

}




