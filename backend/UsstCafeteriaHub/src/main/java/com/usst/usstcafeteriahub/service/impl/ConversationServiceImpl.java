package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.Conversation;
import com.usst.usstcafeteriahub.service.ConversationService;
import com.usst.usstcafeteriahub.mapper.ConversationMapper;
import org.springframework.stereotype.Service;

/**
* @author Klein
* @description 针对表【conversation(会话表)】的数据库操作Service实现
* @createDate 2023-12-10 15:33:58
*/
@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation>
    implements ConversationService{

}




