package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.CommunityComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Klein
* @description 针对表【community_comment(社区评论表)】的数据库操作Mapper
* @createDate 2023-12-16 20:11:06
* @Entity com.usst.usstcafeteriahub.model.entity.CommunityComment
*/
public interface CommunityCommentMapper extends BaseMapper<CommunityComment> {

    CommunityComment getByMessageId(Long messageId);
}




