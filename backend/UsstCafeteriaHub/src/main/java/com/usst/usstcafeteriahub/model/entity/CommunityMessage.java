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
 * @TableName community_message
 */
@TableName(value ="community_message")
@Data
public class CommunityMessage implements Serializable {
    private Long messageId;

    private Long communityId;

    private String communityName;

    private Long userId;

    private String userName;

    private String title;

    private String content;

    private Integer likeCount;

    private LocalDateTime createTime;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}