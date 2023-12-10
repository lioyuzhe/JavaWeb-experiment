package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName dish_remark
 */
@TableName(value ="dish_remark")
@Data
public class DishRemark implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long remarkId;

    private Long userId;

    private String userName;

    private Long dishId;

    private String dishName;

    private String content;

    private Double score;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}