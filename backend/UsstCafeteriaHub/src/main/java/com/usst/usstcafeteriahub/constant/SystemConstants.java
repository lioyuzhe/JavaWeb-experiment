package com.usst.usstcafeteriahub.constant;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;

/**
 * 系统常量类
 * @author 李英杰
 */
public class SystemConstants {

    // 项目名
    public static final String PROJECT_NAME = "上海理工大学食堂点评交流社区";
    public static final String PROJECT_NAME_ENGLISH = "UsstCafeteriaHub";


    // 分页相关常量
    public static final int DEFAULT_PAGE_SIZE = 5;
    public static final int MAX_PAGE_SIZE = 10;



    // apifox 有关常量
    public static final String APIFOX_KEY = "APS-7g2AwQMP6kuwyoEjSIxW8ZYB7l9qx2pM";
    // 这时我们项目的api接口的访问url，密码为usst
    public static final String APIFOX_URL = "https://apifox.com/apidoc/shared-37ddae17-4d78-454f-ad63-7a6319abd441";
    public static final String APIFOX_PROJECT_ID = "3713980";


    //
    public static final String USER_NAME_PREFIX = "user_";
    public static final String Admin_NAME_PREFIX = "user_";
    public static final String CAFETERIA_ADMIN_NAME_PREFIX = "user_";


    // 文件路径
    @Value("${ip:localhost}")
    public static String ip;

    @Value("${server.port}")
    public static String port;

    public static final String ROOT_PATH =  System.getProperty("user.dir") + File.separator + "src/main/resources/files";

    public static final String ADMIN_FILE_PATH = ROOT_PATH + File.separator+"admins";

    public static final String CAFETERIA_ADMIN_FILE_PATH = ROOT_PATH + File.separator+"cafeteriaAdmins";
    public static final String STUDENT_FILE_PATH = ROOT_PATH + File.separator+"students";
    public static final String TEACHER_FILE_PATH = ROOT_PATH + File.separator+"teachers";

    public static final String PUBLIC_FILE_PATH = ROOT_PATH + File.separator+"public";

    public static final String BASE_PATH = "http://" + ip + ":" + port ;

    public static final String DEFAULT_AVATAR_URL = BASE_PATH+ File.separator + "files/getDefaultAvatar";


     // 默认头像路径
    public static final String DEFAULT_AVATAR_PATH = PUBLIC_FILE_PATH + File.separator+"defaultAvatar.png";



}
