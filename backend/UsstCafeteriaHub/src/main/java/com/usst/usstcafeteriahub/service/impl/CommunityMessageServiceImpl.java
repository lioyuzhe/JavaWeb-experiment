package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.CommunityMessage;
import com.usst.usstcafeteriahub.service.CommunityMessageService;
import com.usst.usstcafeteriahub.mapper.CommunityMessageMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【community_message(社区信息表)】的数据库操作Service实现
* @createDate 2023-12-10 15:33:47
*/
@Service
public class CommunityMessageServiceImpl extends ServiceImpl<CommunityMessageMapper, CommunityMessage>
    implements CommunityMessageService{
    @Resource
    private CommunityMessageMapper communityMessageMapper;

    /**
     * 根据用户id筛选communityMessage
     * @param userId
     * @return
     */
    @Override
    public List<CommunityMessage> getCommunityMessageByUserId(Long userId) {
        return communityMessageMapper.getCommunityMessageByUserId(userId);
    }

    /**
     * 根据文章标题进行模糊查询
     * @param communityMessageName
     * @return
     */
    @Override
    public List<CommunityMessage> getCommunityMessageByTitle(String communityMessageName) {
        return communityMessageMapper.getCommunityMessageByTitle(communityMessageName);
    }

    /**
     * 根据点赞数降序返回社区信息
     * @return
     */
    @Override
    public List<CommunityMessage> getCommunityMessageByLike() {
        return communityMessageMapper.getCommunityMessageByLike();
    }
}




