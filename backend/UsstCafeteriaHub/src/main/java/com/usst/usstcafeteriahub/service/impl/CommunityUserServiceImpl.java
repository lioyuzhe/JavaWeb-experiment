package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.CommunityUser;
import com.usst.usstcafeteriahub.service.CommunityUserService;
import com.usst.usstcafeteriahub.mapper.CommunityUserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【community_user(社区用户表)】的数据库操作Service实现
* @createDate 2023-12-10 15:33:51
*/
@Service
public class CommunityUserServiceImpl extends ServiceImpl<CommunityUserMapper, CommunityUser>
    implements CommunityUserService{

    @Resource
    private CommunityUserMapper communityUserMapper;

    /**
     * 根据社区用户名进行模糊查询
     * @param communityUserName
     * @return
     */
    @Override
    public List<CommunityUser> getCommunityUserByCommunityUserName(String communityUserName) {
        return communityUserMapper.getCommunityUserByCommunityUserName(communityUserName);
    }

    /**
     * 根据user_id查找社区User
     * @param userId
     * @return
     */
    @Override
    public CommunityUser getCommunityUserByUserId(Long userId) {
        return communityUserMapper.getCommunityUserByUserId(userId);
    }
}




