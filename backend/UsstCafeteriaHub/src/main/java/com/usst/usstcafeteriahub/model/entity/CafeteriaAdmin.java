package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName cafeteria_admin
 */
@TableName(value ="cafeteria_admin")
@Data
public class CafeteriaAdmin implements Serializable {
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

    @TableField(exist = false)
    private String token;

    private static final long serialVersionUID = 1L;
}