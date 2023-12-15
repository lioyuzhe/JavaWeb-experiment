package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.CommunityUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Klein
* @description 针对表【community_user(社区用户表)】的数据库操作Mapper
* @createDate 2023-12-10 15:33:51
* @Entity com.usst.usstcafeteriahub.model.entity.CommunityUser
*/
public interface CommunityUserMapper extends BaseMapper<CommunityUser> {

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




