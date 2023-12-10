package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.CommunityUser;
import com.usst.usstcafeteriahub.service.CommunityUserService;
import com.usst.usstcafeteriahub.mapper.CommunityUserMapper;
import org.springframework.stereotype.Service;

/**
* @author Klein
* @description 针对表【community_user(社区用户表)】的数据库操作Service实现
* @createDate 2023-12-10 15:33:51
*/
@Service
public class CommunityUserServiceImpl extends ServiceImpl<CommunityUserMapper, CommunityUser>
    implements CommunityUserService{

}




