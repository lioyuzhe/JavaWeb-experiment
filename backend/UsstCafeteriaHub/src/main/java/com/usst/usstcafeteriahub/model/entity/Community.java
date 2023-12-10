package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName community
 */
@TableName(value ="community")
@Data
public class Community implements Serializable {
    private Long communityId;

    private Long userId;

    private String userName;

    private String name;

    private String description;

    private Date createTime;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}