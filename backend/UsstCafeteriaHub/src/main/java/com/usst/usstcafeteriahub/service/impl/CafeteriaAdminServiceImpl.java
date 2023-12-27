package com.usst.usstcafeteriahub.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.mapper.CafeteriaManageMapper;
import com.usst.usstcafeteriahub.mapper.CafeteriaMapper;
import com.usst.usstcafeteriahub.model.entity.*;
import com.usst.usstcafeteriahub.model.entity.CafeteriaAdmin;
import com.usst.usstcafeteriahub.model.request.LoginDTO;
import com.usst.usstcafeteriahub.model.request.RegisterDTO;
import com.usst.usstcafeteriahub.service.CafeteriaAdminService;
import com.usst.usstcafeteriahub.mapper.CafeteriaAdminMapper;
import com.usst.usstcafeteriahub.service.CafeteriaManageService;
import com.usst.usstcafeteriahub.service.CafeteriaService;
import com.usst.usstcafeteriahub.utils.CafeteriaAdminHolder;
import com.usst.usstcafeteriahub.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.usst.usstcafeteriahub.constant.SystemConstants.*;
import static com.usst.usstcafeteriahub.constant.WebConstants.CAFETERIA_ADMIN_LOGIN_STATE;


/**
* @author Klein
* @description 针对表【cafeteria_admin(食堂管理员表)】的数据库操作Service实现
* @createDate 2023-12-10 15:33:27
*/
@Slf4j
@Service
public class CafeteriaAdminServiceImpl extends ServiceImpl<CafeteriaAdminMapper, CafeteriaAdmin>
    implements CafeteriaAdminService{

    @Resource
    private  CafeteriaAdminMapper cafeteriaAdminMapper;
    @Resource
    private CafeteriaManageMapper cafeteriaManageMapper;
    @Resource
    private CafeteriaMapper cafeteriaMapper;
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
    public BaseResponse loginCafeteriaAdmin(LoginDTO loginDTO, HttpServletRequest request) {
        CafeteriaAdmin Cafeteriaadmin = query().eq("account", loginDTO.getAccount()).one();
        // 5.判断用户是否存在
        if (Cafeteriaadmin == null) {
            // 6.不存在，创建新用户并保存
            return Result.error("用户不存在");
        }else {
            if(Cafeteriaadmin.getPassword().equals(loginDTO.getPassword())){
                Map<String,Object> claims = new HashMap<>();
                claims.put("account",Cafeteriaadmin.getAccount());
                claims.put("password",Cafeteriaadmin.getPassword());
                String token = JwtUtils.generateToken(claims);
                Cafeteriaadmin.setToken(token);

                // 将用户信息保存到session中
                request.getSession().setAttribute(CAFETERIA_ADMIN_LOGIN_STATE,Cafeteriaadmin);
                Object o = request.getSession().getAttribute(CAFETERIA_ADMIN_LOGIN_STATE);
                CafeteriaAdmin cafeteriaadmin1 = (CafeteriaAdmin) o;
                System.out.println("session中的用户信息："+cafeteriaadmin1);

                Cafeteriaadmin.setPassword(null); // 密码不返回
                return Result.success(Cafeteriaadmin,"登录成功");
            }else{
                return Result.error("密码错误");
            }
        }
    }

    @Override
    public BaseResponse addAdmin(CafeteriaAdmin admin) {
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CafeteriaAdmin admin1 = cafeteriaAdminMapper.selectById(admin);
        if(admin1 != null){
            return Result.error("该管理员已存在");
        }

        int result = cafeteriaAdminMapper.insert(admin);
        System.out.println(result);
        log.info("添加成功:{｝ ", result );
        if(result == 1){
            return Result.success("添加成功");
        }else{
            return Result.error("添加失败");
        }
    }

    @Override
    public BaseResponse deleteAdmin(CafeteriaAdmin admin) {
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CafeteriaAdmin admin1 = cafeteriaAdminMapper.selectById(admin);
        if(admin1 == null){
            return Result.error("该管理员不存在");
        }
        // 删除
        int result = cafeteriaAdminMapper.deleteById(admin);
        if (result == 1){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }

    @Override
    public BaseResponse updateAdmin(CafeteriaAdmin admin) {
        if (admin == null){
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CafeteriaAdmin admin1 = cafeteriaAdminMapper.selectById(admin);
        if(admin1 == null){
            return Result.error("该管理员不存在");
        }
        // 更新
        int result = cafeteriaAdminMapper.updateById(admin);
        if (result == 1){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }

    @Override
    public BaseResponse getCafeteriaByCafeteriaAdminId(Long adminId) {
        if (adminId == null) {
            return Result.error("参数不能为空");
        }
        // 判断是否存在
        CafeteriaAdmin admin1 = cafeteriaAdminMapper.selectById(adminId);
        if (admin1 == null) {
            return Result.error("该管理员不存在");
        }
        // 获取食堂id 可能有多个食堂
        QueryWrapper<CafeteriaManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", adminId);
        List<CafeteriaManage> cafeteriaManageList = cafeteriaManageMapper.selectList(queryWrapper);
        if (cafeteriaManageList == null || cafeteriaManageList.isEmpty()) {
            return Result.error("该管理员没有管理食堂");
        }
        // 从cafeteriaManageList获取食堂id
        List<Long> cafeteriaIds = cafeteriaManageList.stream()
                .map(CafeteriaManage::getCafeteriaId)
                .collect(Collectors.toList());

        // 获取食堂信息
        List<Cafeteria> cafeteriaList = cafeteriaMapper.selectBatchIds(cafeteriaIds);
        if (cafeteriaList == null || cafeteriaList.isEmpty()) {
            return Result.error("无法找到相应的食堂信息");
        }
        return Result.success(cafeteriaList);
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




