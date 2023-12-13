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
 * @TableName private_messages
 */
@TableName(value ="private_messages")
@Data
public class PrivateMessages implements Serializable {
    private Long messageId;

    private Long conversationId;

    private Long senderId;

    private String senderName;

    private String senderAvatar;

    private Long receiverId;

    private String receiverName;

    private String receiverAvatar;

    private String content;

    private LocalDateTime timestamp;

    private Integer readStatus;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}