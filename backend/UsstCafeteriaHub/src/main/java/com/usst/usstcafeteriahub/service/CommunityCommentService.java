package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.CommunityComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Klein
* @description 针对表【community_comment(社区评论表)】的数据库操作Service
* @createDate 2023-12-16 20:11:06
*/
public interface CommunityCommentService extends IService<CommunityComment> {

    CommunityComment getByMessageId(Long messageId);
}
