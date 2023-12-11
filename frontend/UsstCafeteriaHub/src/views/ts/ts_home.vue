<!-- 子组件 ts_home.vue -->
<template>
  <div class="main-content">
    <div class="left-side">
      <div class="feed-item" v-for="feed in feeds" :key="feed.id">
        <div class="feed-content">
          <h3>{{ feed.title }}</h3>
          <p>{{ feed.description }}</p>
          <div class="feed-meta">
            <span>{{ feed.timeAgo }}</span>
            <span>{{ feed.likes }}个赞</span>
          </div>
        </div>
      </div>
    </div>
    <div class="right-side">
      <div class="notifications">
        <div class="notification-item" v-for="notification in notifications" :key="notification.id">
          <h4>{{ notification.type }}</h4>
          <p>{{ notification.message }}</p>
          <span v-if="notification.isNew">新的!</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ts_home',
  data() {
    return {
      feeds: [
        // 示例数据，实际应用中应从外部获取
        {
          id: 1,
          title: '动态标题',
          description: '这里是动态的详细描述，可能包含了用户的活动或者其他的社交信息。',
          timeAgo: '2小时前',
          likes: 123
        },
        // 更多动态数据...
      ],

      notifications: [
        // 示例通知数据
        { id: 1, type: '评论', message: '您有新的评论', isNew: true },
        { id: 2, type: '点赞', message: '您的帖子收到了新的点赞', isNew: true },
        { id: 3, type: '投诉回复', message: '您的投诉有了新的回复', isNew: false },
        // 更多通知数据...
      ]
    };
  }
};
</script>

<style scoped>
.main-content {
  display: flex; /* 设置为flex布局 */
  justify-content: space-between; /* 子元素间留有空间 */
}
.left-side {
  width: 70%;
  padding: 0 20px;
}
.right-side {
  width: 30%;
  padding: 0 20px;
}
.feed-item {
  margin-bottom: 20px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 10px;
}
.feed-content {
  padding: 10px;
}
/* ...右侧样式... */

.notifications {
  margin-bottom: 20px;
}
.notification-item {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 10px;
  margin-bottom: 10px;
}
.notification-item h4 {
  margin: 0;
  color: #333;
  font-size: 1.1em;
}
.notification-item p {
  color: #666;
}
.notification-item span {
  font-weight: bold;
  color: #d32f2f;
}
</style>
