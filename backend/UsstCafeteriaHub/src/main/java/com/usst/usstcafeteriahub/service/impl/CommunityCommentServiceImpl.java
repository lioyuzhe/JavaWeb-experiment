package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.mapper.CommunityMapper;
import com.usst.usstcafeteriahub.model.entity.CommunityComment;
import com.usst.usstcafeteriahub.service.CommunityCommentService;
import com.usst.usstcafeteriahub.mapper.CommunityCommentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author Klein
* @description 针对表【community_comment(社区评论表)】的数据库操作Service实现
* @createDate 2023-12-16 20:11:06
*/
@Service
public class CommunityCommentServiceImpl extends ServiceImpl<CommunityCommentMapper, CommunityComment>
    implements CommunityCommentService{

    @Resource
    private CommunityCommentMapper communityCommentMapper ;
    @Override
    public CommunityComment getByMessageId(Long messageId) {
        if (messageId==null){
            return null;
        }
        CommunityComment comment =  communityCommentMapper.getByMessageId(messageId);

        return comment;
    }
}




