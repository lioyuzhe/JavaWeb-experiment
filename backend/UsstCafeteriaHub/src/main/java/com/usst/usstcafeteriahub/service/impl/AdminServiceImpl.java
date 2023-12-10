package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
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
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        Admin admin1 = adminMapper.selectById(admin);
        if(admin1 != null){
            return Result.error("该管理员已存在");
        }

        int result = adminMapper.insert(admin);
        System.out.println(result);
        log.info("添加成功:{｝ ", result );
        if(result == 1){
            return Result.success("添加成功");
        }else{
            return Result.error("添加失败");
        }
    }

    @Override
    public BaseResponse deleteAdmin(Admin admin) {
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        Admin admin1 = adminMapper.selectById(admin);
        if(admin1 == null){
            return Result.error("该管理员不存在");
        }
        // 删除
        int result =  adminMapper.deleteById(admin);
        if (result == 1){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }
}




