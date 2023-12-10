package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long userId;

    private String account;

    private String name;

    private String password;

    private String avatar;

    private String email;

    private String phone;

    private Integer status;

    private Integer activityLevel;

    private Integer role;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}