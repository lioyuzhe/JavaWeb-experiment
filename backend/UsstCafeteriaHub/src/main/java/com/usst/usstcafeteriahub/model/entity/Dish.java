package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @TableName dish
 */
@TableName(value ="dish")
@Data
public class Dish implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long dishId;

    private Long cafeteriaId;

    private String cafeteriaName;

    private String name;

    private BigDecimal price;

    private String cuisine;

    private String imageUrl;

    private Integer status;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}