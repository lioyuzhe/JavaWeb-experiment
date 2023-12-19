package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName dish_rank
 */
@TableName(value ="dish_rank")
@Data
public class DishRank implements Serializable {
    private Long rankId;

    private Long cafeteriaId;

    private String cafeteriaName;

    private Long dishId;

    private String dishName;

    private Double totalScore;

    private Integer rank;

    private Double averageScore;

    private static final long serialVersionUID = 1L;
}