package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.PrivateMessages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【private_messages(私信表)】的数据库操作Service
* @createDate 2023-12-13 17:25:48
*/
public interface PrivateMessagesService extends IService<PrivateMessages> {
    /**
     * 创建新私信 同时更新conversation表的last_message和last_message_time
     * @param privateMessages
     * @return
     */
    boolean addPrivateMessages(PrivateMessages privateMessages);

    /**
     * 设置私信已读
     * @param privateMessages
     * @return
     */
    boolean setRead(PrivateMessages privateMessages);

    // /**
    //  * 获取私信列表，且read_status为0的在前，时间降序
    //  * @return
    //  */
    // PrivateMessages getPrivateMessagesById(Long id);

    /**
     * 根据用户id获取消息列表，时间升序
     * @param id
     * @return
     */
    List<PrivateMessages> getPrivateMessages(Long id);

    /**
     * 根据conversationId获取私信列表，时间升序
     * @param conversationId
     * @return
     */
    List<PrivateMessages> getPrivateMessagesByConversationId(Long conversationId);

    /**
     * 根据发送方Id和接受方Id获取私信，时间升序
     * @param senderId
     * @param receiverId
     * @return
     */
    PrivateMessages getPrivateMessagesBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
