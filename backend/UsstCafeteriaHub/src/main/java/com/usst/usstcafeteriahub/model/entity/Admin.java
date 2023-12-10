package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName admin
 */
@TableName(value ="admin")
@Data
public class Admin implements Serializable {
    @TableId(type = IdType.AUTO)
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