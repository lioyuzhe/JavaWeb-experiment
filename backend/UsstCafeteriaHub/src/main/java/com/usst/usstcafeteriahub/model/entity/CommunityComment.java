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
 * @TableName community_comment
 */
@TableName(value ="community_comment")
@Data
public class CommunityComment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long commentId;

    private Long messageId;

    private Long userId;

    private String userName;

    private String content;

    private Integer likeCount;

    private LocalDateTime createTime;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}