-- 说明：此sql脚本仅用于填写部份表的前置测试数据
-- 已插入的数据不会影响到其他表，但请勿直接独立于食堂表和食堂排名表 菜品表和菜品排名表分开插入数据 否则后端会报错

-- 插入前请保证数据库表的数据清除，尤其是菜品和食堂相关表
-- 插入请保证完整运行，全选后插入

-- admin表
INSERT INTO usstcafeteriahub.admin (admin_id, account, name, password, avatar, email, phone, create_time, deleted) VALUES (1, 'admin', 'admin', '123456', null, null, null, '2023-12-05 21:32:00', 0);
INSERT INTO usstcafeteriahub.admin (admin_id, account, name, password, avatar, email, phone, create_time, deleted) VALUES (2, 'admin2', '管理员', '000000', 'pic-bed-e5v.pages.dev/img/头像/Mimikyu.jpg', '1807169624@qq.com', '18017526986', '2023-12-24 16:08:40', 0);

-- cafeteria表
INSERT INTO usstcafeteriahub.cafeteria (cafeteria_id, name, location, description, admin_id, admin_name, open_time, close_time, deleted) VALUES (1, '第一食堂', '北校区', '大众餐厅，便宜实惠', 2, '食堂管理员1', '2013-12-24 16:15:34', '2033-12-24 16:15:38', 0);
INSERT INTO usstcafeteriahub.cafeteria (cafeteria_id, name, location, description, admin_id, admin_name, open_time, close_time, deleted) VALUES (2, '第二食堂', '北校区', '教师餐厅 和 湘味餐厅', 3, '食堂管理员1', '2013-12-24 16:15:34', '2033-12-24 16:15:38', 0);
INSERT INTO usstcafeteriahub.cafeteria (cafeteria_id, name, location, description, admin_id, admin_name, open_time, close_time, deleted) VALUES (3, '第三食堂', '北校区', '这是哪个餐厅来着', 4, '食堂管理员1', '2013-12-24 16:15:34', '2033-12-24 16:15:38', 0);
INSERT INTO usstcafeteriahub.cafeteria (cafeteria_id, name, location, description, admin_id, admin_name, open_time, close_time, deleted) VALUES (4, '第四食堂', '基础学院', '面向大一新生，提供最基本的大众餐', 5, '食堂管理员1', '2013-12-24 16:15:34', '2033-12-24 16:15:38', 0);
INSERT INTO usstcafeteriahub.cafeteria (cafeteria_id, name, location, description, admin_id, admin_name, open_time, close_time, deleted) VALUES (5, '第五食堂', '北校区', '北校最近装修改进的餐厅，可对标南校思餐厅', 6, '食堂管理员1', '2013-12-24 16:15:34', '2033-12-24 16:15:38', 0);
INSERT INTO usstcafeteriahub.cafeteria (cafeteria_id, name, location, description, admin_id, admin_name, open_time, close_time, deleted) VALUES (6, '思餐厅', '南校区', '上理最好的餐厅，配备现代化扫脸支付，提供大众餐，特色餐等等', 7, '食堂管理员1', '2013-12-24 16:15:34', '2033-12-24 16:15:38', 0);

-- cafeteria_admin表
INSERT INTO usstcafeteriahub.cafeteria_admin (admin_id, account, name, password, avatar, email, phone, create_time, deleted) VALUES (1, 'admin001', '张三', '123', 'avatar_url_1.jpg', 'zhangsan@example.com', '13800000001', '2023-12-05 22:00:00', 0);
INSERT INTO usstcafeteriahub.cafeteria_admin (admin_id, account, name, password, avatar, email, phone, create_time, deleted) VALUES (2, 'adminC1', '食堂管理员1', '000000', 'pic-bed-e5v.pages.dev/img/头像/Mimikyu.jpg', 'shitang1@usst.com', '1234562413', '2023-12-24 16:11:08', 0);
INSERT INTO usstcafeteriahub.cafeteria_admin (admin_id, account, name, password, avatar, email, phone, create_time, deleted) VALUES (3, 'adminC2', '食堂管理员2', '000000', 'pic-bed-e5v.pages.dev/img/头像/Mimikyu.jpg', 'shitang2@usst.com', '1234562413', '2023-12-24 16:11:08', 0);
INSERT INTO usstcafeteriahub.cafeteria_admin (admin_id, account, name, password, avatar, email, phone, create_time, deleted) VALUES (4, 'adminC3', '食堂管理员3', '000000', 'pic-bed-e5v.pages.dev/img/头像/Mimikyu.jpg', 'shitang3@usst.com', '1234562413', '2023-12-24 16:11:08', 0);
INSERT INTO usstcafeteriahub.cafeteria_admin (admin_id, account, name, password, avatar, email, phone, create_time, deleted) VALUES (5, 'adminC4', '食堂管理员4', '000000', 'pic-bed-e5v.pages.dev/img/头像/Mimikyu.jpg', 'shitang4@usst.com', '1234562413', '2023-12-24 16:11:08', 0);
INSERT INTO usstcafeteriahub.cafeteria_admin (admin_id, account, name, password, avatar, email, phone, create_time, deleted) VALUES (6, 'adminC5', '食堂管理员5', '000000', 'pic-bed-e5v.pages.dev/img/头像/Mimikyu.jpg', 'shitang5@usst.com', '1234562413', '2023-12-24 16:11:08', 0);

-- cafeteria_manage表
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (1, 1, 1, 0);
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (2, 1, 2, 0);
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (3, 1, 3, 0);
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (4, 1, 4, 0);
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (5, 1, 5, 0);
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (6, 1, 6, 0);
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (7, 2, 1, 0);
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (8, 3, 2, 0);
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (10, 4, 3, 0);
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (11, 5, 4, 0);
INSERT INTO usstcafeteriahub.cafeteria_manage (manage_id, admin_id, cafeteria_id, deleted) VALUES (12, 6, 5, 0);

-- cafeteria_rank表
INSERT INTO usstcafeteriahub.cafeteria_rank (rank_id, cafeteria_id, cafeteria_name, total_score, ranking, deleted, average_score) VALUES (1, 1, '第一食堂', 40, null, 0, 0);
INSERT INTO usstcafeteriahub.cafeteria_rank (rank_id, cafeteria_id, cafeteria_name, total_score, ranking, deleted, average_score) VALUES (2, 2, '第二食堂', 12, 0, 0, 0);
INSERT INTO usstcafeteriahub.cafeteria_rank (rank_id, cafeteria_id, cafeteria_name, total_score, ranking, deleted, average_score) VALUES (3, 3, '第三食堂', 43, null, 0, 0);
INSERT INTO usstcafeteriahub.cafeteria_rank (rank_id, cafeteria_id, cafeteria_name, total_score, ranking, deleted, average_score) VALUES (4, 4, '第四食堂', 42, null, 0, 0);
INSERT INTO usstcafeteriahub.cafeteria_rank (rank_id, cafeteria_id, cafeteria_name, total_score, ranking, deleted, average_score) VALUES (5, 5, '第五食堂', 60, null, 0, 0);
INSERT INTO usstcafeteriahub.cafeteria_rank (rank_id, cafeteria_id, cafeteria_name, total_score, ranking, deleted, average_score) VALUES (6, 6, '思餐厅', 80, null, 0, 0);


-- dish表
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (6, 2, '二食堂', '炸酱面', 20.00, '面食类', 'string', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (7, 2, '二食堂', '鸡蛋面', 15.00, '面食类', 'string', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (8, 2, '二食堂', '猪肚鸡', 15.00, '汤类', 'string', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (9, 5, '五食堂', '黑椒牛肉炒饭111', 18.00, '饭类', 'string', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (10, 1, '一食堂', '红烧肉', 25.00, '荤菜', 'image10.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (11, 1, '一食堂', '鱼香肉丝', 22.00, '荤菜', 'image11.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (12, 1, '一食堂', '宫保鸡丁', 20.00, '荤菜', 'image12.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (13, 1, '一食堂', '麻婆豆腐', 18.00, '素菜', 'image13.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (14, 1, '一食堂', '土豆丝', 16.00, '素菜', 'image14.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (15, 1, '一食堂', '糖醋排骨', 24.00, '荤菜', 'image15.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (16, 2, '二食堂', '牛肉面', 21.00, '面食类', 'image16.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (17, 2, '二食堂', '羊肉串', 30.00, '小吃', 'image17.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (18, 2, '二食堂', '鸡翅', 12.00, '小吃', 'image18.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (19, 2, '二食堂', '肉夹馍', 10.00, '小吃', 'image19.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (20, 3, '三食堂', '炒面', 14.00, '面食类', 'image20.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (21, 3, '三食堂', '鸡蛋汤', 9.00, '汤类', 'image21.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (22, 3, '三食堂', '紫菜蛋花汤', 7.00, '汤类', 'image22.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (23, 3, '三食堂', '香菇滑鸡', 19.00, '荤菜', 'image23.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (24, 3, '三食堂', '豆腐炖排骨', 23.00, '荤菜', 'image24.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (25, 4, '四食堂', '水煮鱼', 28.00, '荤菜', 'image25.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (26, 4, '四食堂', '番茄炒蛋', 15.00, '素菜', 'image26.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (27, 4, '四食堂', '凉拌黄瓜', 10.00, '素菜', 'image27.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (28, 4, '四食堂', '酸菜鱼', 26.00, '荤菜', 'image28.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (29, 4, '四食堂', '炒年糕', 17.00, '面食类', 'image29.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (30, 5, '五食堂', '鸡蛋炒饭', 14.00, '饭类', 'image30.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (31, 5, '五食堂', '猪肚鸡', 26.00, '荤菜', 'image31.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (32, 5, '五食堂', '烤肉拌饭', 22.00, '饭类', 'image32.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (33, 5, '五食堂', '麻辣香锅', 28.00, '荤菜', 'image33.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (34, 5, '五食堂', '泡椒凤爪', 18.00, '小吃', 'image34.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (35, 6, '六食堂', '糖醋里脊', 20.00, '荤菜', 'image35.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (36, 6, '六食堂', '辣子鸡', 24.00, '荤菜', 'image36.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (37, 6, '六食堂', '麻辣烫', 15.00, '面食类', 'image37.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (38, 6, '六食堂', '炒米粉', 14.00, '面食类', 'image38.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (39, 6, '六食堂', '肉丝炒面', 18.00, '面食类', 'image39.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (40, 7, '七食堂', '牛肉拉面', 21.00, '面食类', 'image40.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (41, 7, '七食堂', '酸辣粉', 12.00, '面食类', 'image41.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (42, 7, '七食堂', '馄饨', 10.00, '面食类', 'image42.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (43, 7, '七食堂', '煎饼果子', 8.00, '小吃', 'image43.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (44, 7, '七食堂', '包子', 3.00, '小吃', 'image44.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (45, 8, '八食堂', '鸡排饭', 16.00, '饭类', 'image45.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (46, 8, '八食堂', '糯米鸡', 12.00, '小吃', 'image46.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (47, 8, '八食堂', '炸鸡腿', 14.00, '小吃', 'image47.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (48, 8, '八食堂', '牛排饭', 25.00, '饭类', 'image48.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (49, 8, '八食堂', '炒河粉', 13.00, '面食类', 'image49.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (50, 9, '九食堂', '韩式炒饭', 17.00, '饭类', 'image50.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (51, 9, '九食堂', '冒菜', 20.00, '面食类', 'image51.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (52, 9, '九食堂', '烤肉饭', 22.00, '饭类', 'image52.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (53, 9, '九食堂', '炸鸡', 15.00, '小吃', 'image53.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (54, 9, '九食堂', '烤鱼', 28.00, '荤菜', 'image54.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (55, 10, '十食堂', '蛋炒饭', 12.00, '饭类', 'image55.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (56, 10, '十食堂', '炒牛河', 18.00, '面食类', 'image56.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (57, 10, '十食堂', '麻辣烤鱼', 30.00, '荤菜', 'image57.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (58, 10, '十食堂', '猪肉炖粉条', 16.00, '饭类', 'image58.jpg', 0, 0);
INSERT INTO usstcafeteriahub.dish (dish_id, cafeteria_id, cafeteria_name, name, price, cuisine, image_url, status, deleted) VALUES (59, 10, '十食堂', '炸串', 2.00, '小吃', 'image59.jpg', 0, 0);


-- dish_rank表
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (1, 2, '二食堂', 6, '炸酱面', 50, 1, 8);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (2, 2, '二食堂', 7, '鸡蛋面', 40, 2, 7);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (3, 2, '二食堂', 8, '猪肚鸡', 30, 3, 6);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (4, 5, '五食堂', 9, '黑椒牛肉炒饭111', 25, 4, 5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (5, 1, '一食堂', 10, '红烧肉', 45, 1, 7.5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (6, 1, '一食堂', 11, '鱼香肉丝', 35, 2, 6.5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (7, 1, '一食堂', 12, '宫保鸡丁', 20, 3, 5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (8, 1, '一食堂', 13, '麻婆豆腐', 40, 4, 7);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (9, 1, '一食堂', 14, '土豆丝', 30, 5, 6);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (10, 1, '一食堂', 15, '糖醋排骨', 20, 6, 5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (11, 2, '二食堂', 16, '牛肉面', 50, 1, 8);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (12, 2, '二食堂', 17, '羊肉串', 45, 2, 7.5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (13, 2, '二食堂', 18, '鸡翅', 40, 3, 7);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (14, 2, '二食堂', 19, '肉夹馍', 35, 4, 6.5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (15, 3, '三食堂', 20, '炒面', 30, 1, 6);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (16, 3, '三食堂', 21, '鸡蛋汤', 25, 2, 5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (17, 3, '三食堂', 22, '紫菜蛋花汤', 20, 3, 4);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (18, 3, '三食堂', 23, '香菇滑鸡', 45, 4, 7.5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (19, 3, '三食堂', 24, '豆腐炖排骨', 40, 5, 7);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (20, 4, '四食堂', 25, '水煮鱼', 50, 1, 8);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (21, 4, '四食堂', 26, '番茄炒蛋', 45, 2, 7.5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (22, 4, '四食堂', 27, '凉拌黄瓜', 40, 3, 7);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (23, 4, '四食堂', 28, '酸菜鱼', 35, 4, 6.5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (24, 4, '四食堂', 29, '炒年糕', 30, 5, 6);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (25, 5, '五食堂', 30, '鸡蛋炒饭', 25, 1, 5);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (26, 5, '五食堂', 31, '猪肚鸡', 20, 2, 4);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (27, 5, '五食堂', 32, '烤肉拌饭', 15, 3, 3);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (28, 5, '五食堂', 33, '麻辣香锅', 10, 4, 2);
INSERT INTO usstcafeteriahub.dish_rank (rank_id, cafeteria_id, cafeteria_name, dish_id, dish_name, total_score, ranking, average_score) VALUES (29, 5, '五食堂', 34, '泡椒凤爪', 5, 5, 1);


-- promotion表
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (1, 1, '一食堂', 3, '番茄鱼', '2023-12-19 07:23:59', '2024-01-01 07:23:59', '不知道什么菜品的清仓甩卖，买一送二', 'string', 0, '2023-12-24 16:32:13', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (2, 2, '二食堂', 4, '方便面', '2023-12-19 07:23:59', '2024-01-01 07:23:59', '总之就是方便面', 'string', 0, '2023-12-24 16:32:10', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (3, 3, '三食堂', 5, '辣子鸡丁', '2023-12-20 08:00:00', '2024-01-10 20:00:00', '特色辣子鸡丁，买二送一', 'image3.jpg', 1, '2023-12-20 08:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (4, 4, '四食堂', 6, '酸菜鱼', '2023-12-21 09:00:00', '2024-01-15 21:00:00', '酸菜鱼优惠活动，限时折扣', 'image4.jpg', 1, '2023-12-21 09:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (5, 1, '一食堂', 7, '水煮肉片', '2023-12-22 10:00:00', '2024-01-20 22:00:00', '新品尝鲜，水煮肉片特价', 'image5.jpg', 1, '2023-12-22 10:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (6, 2, '二食堂', 8, '宫保鸡丁', '2023-12-23 11:00:00', '2024-01-25 23:00:00', '宫保鸡丁，买一送一', 'image6.jpg', 1, '2023-12-23 11:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (7, 3, '三食堂', 9, '红烧牛肉', '2023-12-24 12:00:00', '2024-01-30 00:00:00', '红烧牛肉特价促销中', 'image7.jpg', 1, '2023-12-24 12:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (8, 4, '四食堂', 10, '干锅菜花', '2023-12-25 13:00:00', '2024-02-01 01:00:00', '干锅菜花买一送一', 'image8.jpg', 1, '2023-12-25 13:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (9, 1, '一食堂', 11, '麻婆豆腐', '2023-12-26 14:00:00', '2024-02-05 02:00:00', '麻婆豆腐特价促销', 'image9.jpg', 1, '2023-12-26 14:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (10, 2, '二食堂', 12, '糖醋里脊', '2023-12-27 15:00:00', '2024-02-10 03:00:00', '糖醋里脊，限时特价', 'image10.jpg', 1, '2023-12-27 15:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (11, 3, '三食堂', 13, '土豆炖排骨', '2023-12-28 16:00:00', '2024-02-15 04:00:00', '土豆炖排骨，买二赠一', 'image11.jpg', 1, '2023-12-28 16:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (12, 4, '四食堂', 14, '番茄炒蛋', '2023-12-29 17:00:00', '2024-02-20 05:00:00', '番茄炒蛋特价，欢迎品尝', 'image12.jpg', 1, '2023-12-29 17:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (13, 1, '一食堂', 15, '清蒸鲈鱼', '2023-12-30 18:00:00', '2024-02-25 06:00:00', '清蒸鲈鱼限时优惠', 'image13.jpg', 1, '2023-12-30 18:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (14, 2, '二食堂', 16, '糖醋鱼', '2023-12-31 19:00:00', '2024-03-01 07:00:00', '糖醋鱼特惠活动', 'image14.jpg', 1, '2023-12-31 19:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (15, 3, '三食堂', 17, '干煸四季豆', '2024-01-01 20:00:00', '2024-03-05 08:00:00', '干煸四季豆买一赠一', 'image15.jpg', 1, '2024-01-01 20:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (16, 4, '四食堂', 18, '宫保虾球', '2024-01-02 21:00:00', '2024-03-10 09:00:00', '宫保虾球新品尝鲜', 'image16.jpg', 1, '2024-01-02 21:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (17, 1, '一食堂', 19, '红烧茄子', '2024-01-03 22:00:00', '2024-03-15 10:00:00', '红烧茄子限时折扣', 'image17.jpg', 1, '2024-01-03 22:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (18, 2, '二食堂', 20, '香辣蟹', '2024-01-04 23:00:00', '2024-03-20 11:00:00', '香辣蟹特惠，欢迎品尝', 'image18.jpg', 1, '2024-01-04 23:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (19, 3, '三食堂', 21, '酸辣汤', '2024-01-05 00:00:00', '2024-03-25 12:00:00', '酸辣汤优惠促销', 'image19.jpg', 1, '2024-01-05 00:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (20, 4, '四食堂', 22, '麻辣烫', '2024-01-06 01:00:00', '2024-03-30 13:00:00', '麻辣烫新品上市', 'image20.jpg', 1, '2024-01-06 01:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (21, 1, '一食堂', 23, '油焖大虾', '2024-01-07 02:00:00', '2024-04-01 14:00:00', '油焖大虾限时特价', 'image21.jpg', 1, '2024-01-07 02:00:00', 0);
INSERT INTO usstcafeteriahub.promotion (promotion_id, cafeteria_id, cafeteria_name, dish_id, dish_name, start_time, end_time, description, image_url, status, create_time, deleted) VALUES (22, 2, '二食堂', 24, '麻婆豆腐', '2024-01-08 03:00:00', '2024-04-05 15:00:00', '麻婆豆腐买一送一', 'image22.jpg', 1, '2024-01-08 03:00:00', 0);


--