package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName cafeteria_rank
 */
@TableName(value ="cafeteria_rank")
@Data
public class CafeteriaRank implements Serializable {
    private Long rankId;

    private Long cafeteriaId;

    private String cafeteriaName;

    private Double totalScore;

    private Integer rank;

    private Integer deleted;

    private Double averageScore;

    private static final long serialVersionUID = 1L;
}