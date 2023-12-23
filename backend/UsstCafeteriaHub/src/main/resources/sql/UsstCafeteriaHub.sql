

create schema if not exists UsstCafeteriaHub;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

use UsstCafeteriaHub;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
                         `admin_id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
                         `account` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户名',
                         `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名 通过代码逻辑随机生成',
                         `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                         `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
                         `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱地址',
                         `phone` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
                         `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                         `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                         PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin', '123456', NULL, NULL, NULL, '2023-12-05 21:32:00', 0);





-- ----------------------------
-- Table structure for cafeteria_admin
-- ----------------------------

DROP TABLE IF EXISTS `cafeteria_admin`;
CREATE TABLE `cafeteria_admin` (
                                   `admin_id` bigint NOT NULL AUTO_INCREMENT COMMENT '食堂管理员ID',
                                   `account` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户名',
                                   `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名 通过代码逻辑随机生成',
                                   `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                                   `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
                                   `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱地址',
                                   `phone` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                                   PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '食堂管理员表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of cafeteria_admin
-- ----------------------------
INSERT INTO `cafeteria_admin` (`admin_id`, `account`, `name`, `password`, `avatar`, `email`, `phone`, `create_time`, `deleted`) VALUES
                                                                                                                                    (1, 'admin001', '张三', '123', 'avatar_url_1.jpg', 'zhangsan@example.com', '13800000001', '2023-12-5 22:00:00', 0);



-- ----------------------------
-- Table structure for user
-- ----------------------------

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                        `account` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户名',
                        `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
                        `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                        `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
                        `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
                        `phone` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
                        `status` int NOT NULL DEFAULT '0' COMMENT '在线状态, 默认为0,0表示离线，1表示在线',
                        `activity_level` int DEFAULT NULL COMMENT '活跃度',
                        `role` int NOT NULL COMMENT '用户角色，0表示学生，1表示其他',
                        `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                        PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '账号管理表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` (`user_id`, `account`, `name`, `password`, `avatar`, `email`, `phone`, `status`, `activity_level`, `role`, `deleted`) VALUES
                                                                                                                                             (1, 'user1', 'klein', '12345678', 'avatar1.jpg', 'klein@example.com', '13900000001', 0, 10, 0, 0);



-- ----------------------------
-- Table structure for private_messages
-- ----------------------------
DROP TABLE IF EXISTS `private_messages`;
CREATE TABLE `private_messages` (
                                    `message_id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
                                    `conversation_id` bigint COMMENT '会话id,外键，绑定到conversation表',
                                    `sender_id` bigint NOT NULL COMMENT '发送者ID',
                                    `sender_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发送者姓名',
                                    `sender_avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送者头像',
                                    `receiver_id` bigint NOT NULL COMMENT '接收者ID',
                                    `receiver_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接受者姓名',
                                    `receiver_avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接受者头像',
                                    `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
                                    `timestamp` datetime NOT NULL COMMENT '发送时间',
                                    `read_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '阅读状态， 默认为0，表示未读，1表示已读',
                                    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                                    PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '私信表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of private_messages
-- ----------------------------



-- ----------------------------
-- Table structure for conversation
-- ----------------------------

DROP TABLE IF EXISTS `conversation`;
CREATE TABLE `conversation` (
                                `conversation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '会话ID',
                                `user_one_id` bigint NOT NULL COMMENT '参与会话的用户之一的ID，外键关联user表',
                                `user_one_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参与会话的用户之一的姓名',
                                `user_one_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参与会话的用户之一的头像',
                                `user_two_id` bigint NOT NULL COMMENT '参与会话的另一用户的ID，外键关联user表',
                                `user_two_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参与会话的另一用户的姓名',
                                `user_two_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参与会话的另一用户的头像',
                                `last_message` text COMMENT '会话中最后一条消息',
                                `last_message_time` datetime  COMMENT '会话中最后一条消息的发送时间',
                                PRIMARY KEY (`conversation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会话表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of conversation
-- ----------------------------



-- ----------------------------
-- Table structure for cafeteria
-- ----------------------------

DROP TABLE IF EXISTS `cafeteria`;
CREATE TABLE `cafeteria` (
                             `cafeteria_id` bigint NOT NULL AUTO_INCREMENT COMMENT '食堂ID',
                             `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '食堂名称',
                             `location` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '位置',
                             `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述信息',
                             `admin_id` bigint DEFAULT NULL COMMENT '食堂管理员ID',
                             `admin_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '食堂管理员姓名',
                             `open_time` datetime DEFAULT NULL COMMENT '开放时间',
                             `close_time` datetime DEFAULT NULL COMMENT '关闭时间',
                             `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                             PRIMARY KEY (`cafeteria_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '食堂基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria
-- ----------------------------
INSERT INTO `cafeteria` (`cafeteria_id`, `name`, `location`, `description`, `admin_id`, `admin_name`, `open_time`, `close_time`, `deleted`) VALUES
                                                                                                                                             (1, '第一食堂', '北校区', '便宜实惠', 1, '张三', NULL, NULL, 0);


-- ----------------------------
-- Table structure for cafeteria_remark
-- ----------------------------

DROP TABLE IF EXISTS `cafeteria_remark`;
CREATE TABLE `cafeteria_remark` (
                                    `remark_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
                                    `cafeteria_id` bigint NOT NULL COMMENT '食堂ID',
                                    `cafeteria_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '食堂名',
                                    `user_id` bigint NOT NULL COMMENT '用户ID',
                                    `user_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                                    `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评价内容',
                                    `score` float DEFAULT NULL COMMENT '评分',
                                    `reply` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '对用户评价的回复',
                                    `status` int NOT NULL DEFAULT '0' COMMENT '默认为0，创建的时候默认为0, 0表示没有回复，1表示已回复。',
                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                                    PRIMARY KEY (`remark_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '食堂评价信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_remark
-- ----------------------------


-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------

DROP TABLE IF EXISTS `cafeteria_manage`;
CREATE TABLE `cafeteria_manage` (
                                    `manage_id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理记录ID',
                                    `admin_id` bigint NOT NULL COMMENT '食堂管理员ID',
                                    `cafeteria_id` bigint NOT NULL COMMENT '食堂ID',
                                    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                                    PRIMARY KEY (`manage_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '食堂管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------


-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------

DROP TABLE IF EXISTS `cafeteria_notice`;
CREATE TABLE `cafeteria_notice` (
                                    `notice_id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
                                    `cafeteria_id` bigint NOT NULL COMMENT '食堂ID',
                                    `cafeteria_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '食堂名',
                                    `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
                                    `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                                    PRIMARY KEY (`notice_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '食堂活动公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------



-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------

DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint` (
                             `complaint_id` bigint NOT NULL AUTO_INCREMENT COMMENT '投诉ID',
                             `user_id` bigint NOT NULL COMMENT '用户ID',
                             `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                             `cafeteria_id` bigint NOT NULL COMMENT '食堂ID',
                             `cafeteria_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '食堂名',
                             `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投诉内容',
                             `status` int NOT NULL COMMENT '投诉处理状态，0表示未处理，1表示已处理',
                             `reply` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投诉处理回复内容',
                             `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0表示不删除，1表示删除',
                             PRIMARY KEY (`complaint_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '投诉表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------

-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------

DROP TABLE IF EXISTS `cafeteria_rank`;
CREATE TABLE `cafeteria_rank` (
                                  `rank_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Ranking ID',
                                  `cafeteria_id` bigint NOT NULL COMMENT 'Cafeteria ID',
                                  `cafeteria_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Name of the cafeteria',
                                  `total_score` float NOT NULL COMMENT 'Total score',
                                  `rank` int DEFAULT NULL COMMENT 'Ranking',
                                  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                                  PRIMARY KEY (`rank_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '食堂排名表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------



-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------

DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
                        `dish_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜品ID',
                        `cafeteria_id` bigint NOT NULL COMMENT '食堂ID',
                        `cafeteria_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '食堂名',
                        `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜名',
                        `price` decimal(10,2) NOT NULL COMMENT '价格',
                        `cuisine` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜系',
                        `image_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片',
                        `status` int NOT NULL DEFAULT '0' COMMENT '默认为0，0 表示普通菜品，1表示最新推荐菜品',
                        `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                        PRIMARY KEY (`dish_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜品维护表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------


-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------

DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
                             `promotion_id` bigint NOT NULL AUTO_INCREMENT COMMENT '促销ID',
                             `cafeteria_id` bigint NOT NULL COMMENT '食堂ID',
                             `cafeteria_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '食堂名称',
                             `dish_id` bigint NOT NULL COMMENT '菜品id',
                             `dish_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '促销菜品名称',
                             `start_time` datetime NOT NULL COMMENT '开始时间',
                             `end_time` datetime NOT NULL COMMENT '结束时间',
                             `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述信息',
                             `image_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片',
                             `status` int NOT NULL DEFAULT '0' COMMENT '默认为0，0表示未开始；1表示进行中；2表示已结束',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0表示不删除，1表示删除',
                             PRIMARY KEY (`promotion_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '促销信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------

-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------


DROP TABLE IF EXISTS `dish_remark`;
CREATE TABLE `dish_remark` (
                               `remark_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
                               `user_id` bigint NOT NULL COMMENT '用户ID',
                               `user_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名称',
                               `dish_id` bigint NOT NULL COMMENT '菜品ID',
                               `dish_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜品名称',
                               `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评价内容',
                               `score` float DEFAULT NULL COMMENT '评分',
                               `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0 ，表示不删除，1 表示删除',
                               PRIMARY KEY (`remark_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜品评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------


-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------
DROP TABLE IF EXISTS `dish_rank`;
CREATE TABLE `dish_rank` (
                             `rank_id` bigint NOT NULL AUTO_INCREMENT COMMENT '排名ID',
                             `cafeteria_id` bigint NOT NULL COMMENT '食堂ID',
                             `cafeteria_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '食堂名',
                             `dish_id` bigint NOT NULL COMMENT '菜品ID',
                             `dish_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜品名',
                             `total_score` float DEFAULT NULL COMMENT '总评分',
                             `rank` int  COMMENT '排名',
                             PRIMARY KEY (`rank_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜品排名表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------

-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
                             `community_id` bigint NOT NULL AUTO_INCREMENT COMMENT '社区ID',
                             `user_id` bigint NOT NULL COMMENT '创建者用户ID 为某个用户创建',
                             `user_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者姓名，这里专门设计一个user_name,可以方便之后查询数据，二不用使用join连接数据库表',
                             `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社区名称',
                             `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '社区描述',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0表示不删除，1表示删除',
                             PRIMARY KEY (`community_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社区表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------


-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------
DROP TABLE IF EXISTS `community_user`;
CREATE TABLE `community_user` (
                                  `community_user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '社区用户ID',
                                  `user_id` bigint NOT NULL COMMENT '用户ID',
                                  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                                  `community_id` bigint default 1 COMMENT '社区ID',
                                  `community_name` varchar(50) default '上海理工大学食堂点评交流社区' COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社区名',
                                  `post_count` int DEFAULT NULL COMMENT '发布消息数',
                                  `like_count` int DEFAULT NULL COMMENT '点赞总数',
                                  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0表示不删除，1表示删除',
                                  PRIMARY KEY (`community_user_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社区用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------


-- ----------------------------
-- Table structure for cafeteria_rank
-- ----------------------------
DROP TABLE IF EXISTS `community_message`;
CREATE TABLE `community_message` (
                                     `message_id` bigint NOT NULL AUTO_INCREMENT COMMENT '信息ID',
                                     `community_id` bigint default 1 COMMENT '社区ID',
                                     `community_name` varchar(256) default '上海理工大学食堂点评交流社区'  COLLATE utf8mb4_unicode_ci  COMMENT '社区名',
                                     `user_id` bigint NOT NULL COMMENT '用户ID',
                                     `user_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                                     `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
                                     `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容（具体的存储的内容可以在前端通过富文本编辑器实现）',
                                     `like_count` int NOT NULL COMMENT '点赞数',
                                     `create_time` datetime COMMENT '创建时间',
                                     `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认为0表示不删除，1表示删除',
                                     PRIMARY KEY (`message_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社区信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cafeteria_rank
-- ----------------------------



-- ----------------------------
-- Table structure for community_comment
-- ----------------------------


DROP TABLE IF EXISTS `community_comment`;
CREATE TABLE `community_comment` (
                                     `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
                                     `message_id` bigint NOT NULL COMMENT '信息ID，对应社区信息表的信息ID',
                                     `user_id` bigint NOT NULL COMMENT '评论用户ID',
                                     `user_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论用户名',
                                     `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
                                     `like_count` int  COMMENT '评论点赞数',
                                     `create_time` datetime COMMENT '评论创建时间',
                                     `deleted` tinyint(1)  DEFAULT '0' COMMENT '逻辑删除，默认为0表示不删除，1表示删除',
                                     PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='社区评论表' ROW_FORMAT=DYNAMIC;


# complaint表  师生用户/首页 右侧的投诉回复
INSERT INTO usstcafeteriahub.complaint (complaint_id, user_id, user_name, cafeteria_id, cafeteria_name, content, status, reply, deleted) VALUES (1, 1, 'aaa', 1, 'aaa', '难吃', 1, '收到投诉', 0);


