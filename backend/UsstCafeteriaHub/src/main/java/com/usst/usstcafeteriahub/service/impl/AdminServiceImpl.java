package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.model.entity.Admin;
import com.usst.usstcafeteriahub.service.AdminService;
import com.usst.usstcafeteriahub.mapper.AdminMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author Klein
* @description 针对表【admin(系统管理员表)】的数据库操作Service实现
* @createDate 2023-12-10 14:39:20
*/
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

    @Resource
    private AdminMapper adminMapper;
    @Override
    public BaseResponse addAdmin(Admin admin) {
        BaseResponse baseResponse = new BaseResponse();
        int result = adminMapper.insert(admin);
        System.out.println(result);
        log.info("添加成功:{｝ ", result );
        if(result == 1){
            baseResponse.setCode(200);
            baseResponse.setMessage("添加成功");
        }else{
            baseResponse.setCode(500);
            baseResponse.setMessage("添加失败");
        }
        return baseResponse;
    }
}




