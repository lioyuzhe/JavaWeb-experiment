package com.usst.usstcafeteriahub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName community_user
 */
@TableName(value ="community_user")
@Data
public class CommunityUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long communityUserId;

    private Long userId;

    private String userName;

    private Long communityId;

    private String communityName;

    private Integer postCount;

    private Integer likeCount;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}