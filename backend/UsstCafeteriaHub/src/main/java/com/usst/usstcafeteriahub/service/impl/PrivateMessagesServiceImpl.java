package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.PrivateMessages;
import com.usst.usstcafeteriahub.service.PrivateMessagesService;
import com.usst.usstcafeteriahub.mapper.PrivateMessagesMapper;
import org.springframework.stereotype.Service;

/**
* @author Klein
* @description 针对表【private_messages(私信表)】的数据库操作Service实现
* @createDate 2023-12-10 15:34:15
*/
@Service
public class PrivateMessagesServiceImpl extends ServiceImpl<PrivateMessagesMapper, PrivateMessages>
    implements PrivateMessagesService{

}




