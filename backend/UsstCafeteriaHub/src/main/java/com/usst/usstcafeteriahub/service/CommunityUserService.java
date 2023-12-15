package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.CommunityUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【community_user(社区用户表)】的数据库操作Service
* @createDate 2023-12-10 15:33:51
*/
public interface CommunityUserService extends IService<CommunityUser> {

    /**
     * 根据社区用户名进行模糊查询
     * @param communityUserName
     * @return
     */
    List<CommunityUser> getCommunityUserByCommunityUserName(String communityUserName);

    /**
     * 根据user_id查找社区User
     * @param userId
     * @return
     */
    CommunityUser getCommunityUserByUserId(Long userId);
}
