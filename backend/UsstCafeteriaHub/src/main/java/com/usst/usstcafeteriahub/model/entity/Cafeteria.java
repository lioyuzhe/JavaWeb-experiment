package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName cafeteria
 */
@TableName(value ="cafeteria")
@Data
public class Cafeteria implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long cafeteriaId;

    private String name;

    private String location;

    private String description;

    private Long adminId;

    private String adminName;

    private String openTime;

    private String closeTime;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}