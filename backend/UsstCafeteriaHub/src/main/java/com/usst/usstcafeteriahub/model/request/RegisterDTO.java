package com.usst.usstcafeteriahub.model.request;

import lombok.Data;

@Data
public class RegisterDTO {
    private String account;
    private String password;
    private String confirmPass;
    private Integer role;  // 0-管理员，1-食堂管理员，2-学生，3-教师
}
