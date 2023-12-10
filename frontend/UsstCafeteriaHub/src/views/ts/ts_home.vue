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
      <!-- 插槽，用于从父组件传递内容 -->
      <slot></slot>
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
      ]
    };
  }
};
</script>

<style scoped>
/* 在这里编写该子组件特有的CSS */
.left-side {
  width: 70%;
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
/* ...其他样式... */
</style>
