package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.CommunityMessage;
import com.usst.usstcafeteriahub.service.CommunityMessageService;
import com.usst.usstcafeteriahub.mapper.CommunityMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author Klein
* @description 针对表【community_message(社区信息表)】的数据库操作Service实现
* @createDate 2023-12-15 14:53:57
*/
@Service
public class CommunityMessageServiceImpl extends ServiceImpl<CommunityMessageMapper, CommunityMessage>
    implements CommunityMessageService{

}




