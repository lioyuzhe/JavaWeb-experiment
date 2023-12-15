package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.CommunityMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Klein
* @description 针对表【community_message(社区信息表)】的数据库操作Mapper
* @createDate 2023-12-15 14:53:57
* @Entity com.usst.usstcafeteriahub.model.entity.CommunityMessage
*/
public interface CommunityMessageMapper extends BaseMapper<CommunityMessage> {

    /**
     * 根据用户id筛选communityMessage
     * @param userId
     * @return
     */
    List<CommunityMessage> getCommunityMessageByUserId(Long userId);

    /**
     * 根据文章标题进行模糊查询
     * @param communityMessageName
     * @return
     */
    List<CommunityMessage> getCommunityMessageByTitle(String communityMessageName);

    /**
     * 根据点赞数降序返回社区信息
     * @return
     */
    List<CommunityMessage> getCommunityMessageByLike();
}




