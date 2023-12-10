package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName complaint
 */
@TableName(value ="complaint")
@Data
public class Complaint implements Serializable {
    private Long complaintId;

    private Long userId;

    private String userName;

    private Long cafeteriaId;

    private String cafeteriaName;

    private String content;

    private Integer status;

    private String reply;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}