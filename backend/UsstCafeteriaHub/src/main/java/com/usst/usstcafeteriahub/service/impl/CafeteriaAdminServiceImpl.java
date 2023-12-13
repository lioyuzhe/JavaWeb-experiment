package com.usst.usstcafeteriahub.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.CafeteriaAdmin;
import com.usst.usstcafeteriahub.model.entity.CafeteriaAdmin;
import com.usst.usstcafeteriahub.model.request.LoginDTO;
import com.usst.usstcafeteriahub.model.request.RegisterDTO;
import com.usst.usstcafeteriahub.service.CafeteriaAdminService;
import com.usst.usstcafeteriahub.mapper.CafeteriaAdminMapper;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.usst.usstcafeteriahub.constant.SystemConstants.*;


/**
* @author Klein
* @description 针对表【cafeteria_admin(食堂管理员表)】的数据库操作Service实现
* @createDate 2023-12-10 15:33:27
*/
@Service
public class CafeteriaAdminServiceImpl extends ServiceImpl<CafeteriaAdminMapper, CafeteriaAdmin>
    implements CafeteriaAdminService{

    @Override
    public BaseResponse registerCafeteriaAdmin(RegisterDTO registerDTO) {
        CafeteriaAdmin Cafeteriaadmin = query().eq("account", registerDTO.getAccount()).one();
        // 5.判断用户是否存在
        if (Cafeteriaadmin == null) {
            // 6.不存在，创建新用户并保存
            Cafeteriaadmin =createCafeteriaAdmin(registerDTO);
            // 获取id创建一个文件夹
            CafeteriaAdmin Cafeteriaadmintmp = query().eq("account", Cafeteriaadmin.getAccount()).one();
            if (Cafeteriaadmintmp != null){
                File file = new File(CAFETERIA_ADMIN_FILE_PATH+File.separator+Cafeteriaadmintmp.getAdminId());
                file.mkdir();
            }else {
                return Result.error("注册失败");
            }

            return Result.success("注册成功");
        }else {
            return Result.error("用户已存在");
        }
    }

    @Override
    public BaseResponse loginCafeteriaAdmin(LoginDTO loginDTO) {
        CafeteriaAdmin Cafeteriaadmin = query().eq("account", loginDTO.getAccount()).one();
        // 5.判断用户是否存在
        if (Cafeteriaadmin == null) {
            // 6.不存在，创建新用户并保存
            return Result.error("用户不存在");
        }else {
            if(Cafeteriaadmin.getPassword().equals(loginDTO.getPassword())){
                return Result.success("登录成功");
            }else{
                return Result.error("密码错误");
            }
        }
    }

    private CafeteriaAdmin createCafeteriaAdmin(RegisterDTO registerDTO) {
        // 创建用户
        CafeteriaAdmin Cafeteriaadmin = new CafeteriaAdmin();
        Cafeteriaadmin.setAccount(registerDTO.getAccount());
        Cafeteriaadmin.setPassword(registerDTO.getPassword());
        // 为用户设置一个随机的名字。
        Cafeteriaadmin.setName(CAFETERIA_ADMIN_NAME_PREFIX + RandomUtil.randomString(10));

        // 为用户设置默认的头像
        Cafeteriaadmin.setAvatar(DEFAULT_AVATAR_URL);

        // 2.保存用户
        save(Cafeteriaadmin);
        return Cafeteriaadmin;
    }
}




