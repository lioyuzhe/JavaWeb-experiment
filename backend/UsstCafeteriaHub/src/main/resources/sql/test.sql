
INSERT INTO cafeteria_remark
(cafeteria_id, cafeteria_name, user_id, user_name, content, score, reply, status, create_time, deleted)
VALUES
    (1, '第一食堂', 123, 'John Doe', '这是一条评价内容', 4.5, '感谢您的评价！', 1, '2023-12-22 14:30:00', 0);


INSERT INTO dish_remark
(user_id, user_name, dish_id, dish_name, content, score, deleted)
VALUES
    (1, 'user1', 101, '红烧肉', '这是一条红烧肉的评价内容', 4.5, 0);

INSERT INTO community_user
(user_id, user_name, community_id, community_name, post_count, like_count, deleted)
VALUES
    (1, 'user1', 1, '上海理工大学食堂点评交流社区', 10, 5, 0);

INSERT INTO community_message
(community_id, community_name, user_id, user_name, title, content, like_count, create_time, deleted)
VALUES
    (1, '上海理工大学食堂点评交流社区', 1, 'user1', 'hello', 'hello 大家好', 10, '2023-12-22 14:30:00', 0);