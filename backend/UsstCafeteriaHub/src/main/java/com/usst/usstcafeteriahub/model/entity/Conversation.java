package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @TableName conversation
 */
@TableName(value ="conversation")
@Data
public class Conversation implements Serializable {
    private Long conversationId;

    private Long userOneId;

    private String userOneName;

    private String userOneAvatar;

    private Long userTwoId;

    private String userTwoName;

    private String userTwoAvatar;

    private String lastMessage;

    private LocalDateTime lastMessageTime;

    private static final long serialVersionUID = 1L;
}