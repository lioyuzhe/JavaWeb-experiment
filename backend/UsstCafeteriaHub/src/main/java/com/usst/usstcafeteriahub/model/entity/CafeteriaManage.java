package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName cafeteria_manage
 */
@TableName(value ="cafeteria_manage")
@Data
public class CafeteriaManage implements Serializable {
    private Long manageId;

    private Long adminId;

    private Long cafeteriaId;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}