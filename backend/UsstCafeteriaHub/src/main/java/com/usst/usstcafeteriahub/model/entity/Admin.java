package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName admin
 */
@TableName(value ="admin")
@Data
public class Admin implements Serializable {
    private Long adminId;

    private String account;

    private String name;

    private String password;

    private String avatar;

    private String email;

    private String phone;

    private Date createTime;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}