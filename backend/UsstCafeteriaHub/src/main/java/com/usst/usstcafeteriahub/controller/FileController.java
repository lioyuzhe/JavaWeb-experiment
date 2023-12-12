package com.usst.usstcafeteriahub.controller;


import cn.hutool.core.io.FileUtil;
import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.constant.AuthAccess;
import com.usst.usstcafeteriahub.model.entity.User;
import com.usst.usstcafeteriahub.utils.AdminHolder;
import com.usst.usstcafeteriahub.utils.CafeteriaAdminHolder;
import com.usst.usstcafeteriahub.utils.UserHolder;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 文件上传下载接口
 * @author 李英杰
 */
@Slf4j
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    // 每个用户注册成功之后都会在files文件夹下创建一个文件夹，文件夹名字为用户或管理员的id，里面存放用户的头像，上传的文件等等
    private static final String ROOT_PATH =  System.getProperty("user.dir") + File.separator + "src/main/resources/files";

    private static final String ADMIN_FILE_PATH = ROOT_PATH + File.separator+"admins";

    private static final String CAFETERIA_ADMIN_FILE_PATH = ROOT_PATH + File.separator+"cafeteriaAdmins";
    private static final String STUDENT_FILE_PATH = ROOT_PATH + File.separator+"students";
    private static final String TEACHER_FILE_PATH = ROOT_PATH + File.separator+"teachers";

    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    public BaseResponse test(){
        return Result.error("功能还未开发");
    }

    @ApiOperation(value = "管理员上传文件")
    @PostMapping("/admins/actions/upload")
    public BaseResponse uploadAdmin(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件的原始名称
        // aaa.png
        String mainName = FileUtil.mainName(originalFilename);  // aaa
        String extName = FileUtil.extName(originalFilename);// png
        if (!FileUtil.exist(ADMIN_FILE_PATH)) {
            FileUtil.mkdir(ADMIN_FILE_PATH);  // 如果当前文件的父级目录不存在，就创建
        }
        Long id = AdminHolder.getAdmin().getAdminId();
        if (FileUtil.exist(ADMIN_FILE_PATH + File.separator + id+ File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ADMIN_FILE_PATH + File.separator + id+ File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://" + ip + ":" + port + "/admins/actions/download/" + originalFilename;
        return Result.success(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
    }


    @ApiOperation(value = "管理员下载文件")
    @AuthAccess // 放行
    @GetMapping("/admins/actions/download/{fileName}")
    public void downloadAdmin(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 附件下载
        response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 预览
        Long id = AdminHolder.getAdmin().getAdminId();
        String filePath = ADMIN_FILE_PATH + File.separator + id + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }

    @ApiOperation(value = "食堂管理员上传文件")
    @PostMapping("/cafeteriaAdmins/actions/upload")
    public BaseResponse uploadCafeteriaAdmin(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件的原始名称
        // aaa.png
        String mainName = FileUtil.mainName(originalFilename);  // aaa
        String extName = FileUtil.extName(originalFilename);// png
        if (!FileUtil.exist(CAFETERIA_ADMIN_FILE_PATH)) {
            FileUtil.mkdir(CAFETERIA_ADMIN_FILE_PATH);  // 如果当前文件的父级目录不存在，就创建
        }
        Long id = CafeteriaAdminHolder.getCafeteriaAdmin().getAdminId();
        if (FileUtil.exist(CAFETERIA_ADMIN_FILE_PATH + File.separator + id+ File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(CAFETERIA_ADMIN_FILE_PATH + File.separator + id+ File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://" + ip + ":" + port + "/cafeteriaAdmins/actions/download/" + originalFilename;
        return Result.success(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
    }

    @ApiOperation(value = "食堂管理员下载文件")
    @AuthAccess // 放行
    @GetMapping("/cafeteriaAdmins/actions/download/{fileName}")
    public void downloadCafeteriaAdmin(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 附件下载
        response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 预览
        Long id = CafeteriaAdminHolder.getCafeteriaAdmin().getAdminId();
        String filePath = CAFETERIA_ADMIN_FILE_PATH + File.separator + id + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }

    @ApiOperation(value = "用户上传文件")
    @PostMapping("/users/actions/upload")
    public BaseResponse uploadUser(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件的原始名称
        // aaa.png
        String mainName = FileUtil.mainName(originalFilename);  // aaa
        String extName = FileUtil.extName(originalFilename);// png
        User user = UserHolder.getUser();
        Integer role = user.getRole();
        if(role==1){
            if (!FileUtil.exist(TEACHER_FILE_PATH)) {
                FileUtil.mkdir(TEACHER_FILE_PATH);  // 如果当前文件的父级目录不存在，就创建
            }
            Long id = UserHolder.getUser().getUserId();
            if (FileUtil.exist(TEACHER_FILE_PATH + File.separator + id+ File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
                originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
            }
            File saveFile = new File(TEACHER_FILE_PATH + File.separator + id+ File.separator + originalFilename);
            file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
            String url = "http://" + ip + ":" + port + "/users/actions/download/" + originalFilename;
            return Result.success(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
        }else if(role==0){
            if (!FileUtil.exist(STUDENT_FILE_PATH)) {
                FileUtil.mkdir(STUDENT_FILE_PATH);  // 如果当前文件的父级目录不存在，就创建
            }
            Long id = UserHolder.getUser().getUserId();
            if (FileUtil.exist(STUDENT_FILE_PATH + File.separator + id+ File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
                originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
            }
            File saveFile = new File(STUDENT_FILE_PATH + File.separator + id+ File.separator + originalFilename);
            file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
            String url = "http://" + ip + ":" + port + "/users/actions/download/" + originalFilename;
            return Result.success(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
        }else{
            return Result.error("用户角色错误，请联系管理员");
        }

    }

    @ApiOperation(value = "用户下载文件")
    @AuthAccess // 放行
    @GetMapping("/users/actions/download/{fileName}")
    public void downloadUser(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        //        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 附件下载
        response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 预览
        User user = UserHolder.getUser();
        Integer role = user.getRole();
        if(role==1){
            Long id = UserHolder.getUser().getUserId();
            String filePath = TEACHER_FILE_PATH + File.separator + id + File.separator + fileName;
            if (!FileUtil.exist(filePath)) {
                return;
            }
            byte[] bytes = FileUtil.readBytes(filePath);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
            outputStream.flush();
            outputStream.close();
        }else if(role==0){
            Long id = UserHolder.getUser().getUserId();
            String filePath = STUDENT_FILE_PATH + File.separator + id + File.separator + fileName;
            if (!FileUtil.exist(filePath)) {
                return;
            }
            byte[] bytes = FileUtil.readBytes(filePath);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
            outputStream.flush();
            outputStream.close();
        }else{
            return;
        }
    }








}
