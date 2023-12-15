package com.usst.usstcafeteriahub.model.request;

import lombok.Data;

@Data
public class LoginDTO {
    private String account;
    private String password;
    private Integer role;  // 0-管理员，1-食堂管理员，2-学生，3-教师
}
