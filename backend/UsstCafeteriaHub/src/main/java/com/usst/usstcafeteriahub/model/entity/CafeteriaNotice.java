package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName cafeteria_notice
 */
@TableName(value ="cafeteria_notice")
@Data
public class CafeteriaNotice implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long noticeId;

    private Long cafeteriaId;

    private String cafeteriaName;

    private String title;

    private String content;

    private Date createTime;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}