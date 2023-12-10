package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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

    private Date openTime;

    private Date closeTime;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}