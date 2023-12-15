package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName cafeteria_remark
 */
@TableName(value ="cafeteria_remark")
@Data
public class CafeteriaRemark implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long remarkId;

    private Long cafeteriaId;

    private String cafeteriaName;

    private Long userId;

    private String userName;

    private String content;

    private Double score;

    private String reply;

    private Integer status;

    private Date createTime;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}