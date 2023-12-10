package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName promotion
 */
@TableName(value ="promotion")
@Data
public class Promotion implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long promotionId;

    private Long cafeteriaId;

    private String cafeteriaName;

    private Long dishId;

    private String dishName;

    private Date startTime;

    private Date endTime;

    private String description;

    private String imageUrl;

    private Integer status;

    private Date createTime;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}