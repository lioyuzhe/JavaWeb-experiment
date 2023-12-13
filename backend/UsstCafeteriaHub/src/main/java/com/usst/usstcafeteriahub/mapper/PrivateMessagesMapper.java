package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.PrivateMessages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Klein
* @description 针对表【private_messages(私信表)】的数据库操作Mapper
* @createDate 2023-12-10 15:34:15
* @Entity com.usst.usstcafeteriahub.model.entity.PrivateMessages
*/
public interface PrivateMessagesMapper extends BaseMapper<PrivateMessages> {

    // /**
    //  * 根据用户id获取私信列表，且read_status为0的在前，时间降序
    //  * @param id
    //  * @return
    //  */
    // PrivateMessages getPrivateMessagesById(Integer id);

    /**
     * 根据用户id获取消息列表，时间升序
     * @param id
     * @return
     */
    List<PrivateMessages> getPrivateMessages(Integer id);

    /**
     * 根据conversationId获取私信列表，时间升序
     * @param conversationId
     * @return
     */
    List<PrivateMessages> getPrivateMessagesByConversationId(Integer conversationId);
}




