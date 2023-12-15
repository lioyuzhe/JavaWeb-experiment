package com.usst.usstcafeteriahub.mapper;

import com.usst.usstcafeteriahub.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HrizonX
* @description 针对表【user(账号管理表)】的数据库操作Mapper
* @createDate 2023-12-10 14:42:29
* @Entity com.usst.usstcafeteriahub.model.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名进行模糊查询
     * @param name
     * @return
     */
    List<User> getUserByName(String name);
}




