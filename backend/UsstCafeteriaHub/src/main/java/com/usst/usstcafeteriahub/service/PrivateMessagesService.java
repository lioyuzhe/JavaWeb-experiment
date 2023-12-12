package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.PrivateMessages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【private_messages(私信表)】的数据库操作Service
* @createDate 2023-12-10 15:34:15
*/
public interface PrivateMessagesService extends IService<PrivateMessages> {
    /**
     * 创建新私信
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
}
