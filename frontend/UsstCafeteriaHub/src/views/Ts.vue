<template>
  <div id="app">
    <router-view></router-view> <!-- 子组件在这里渲染 -->
    <ul class="navigation">
      <li>首页</li>
      <li>社区</li>
      <li>食堂</li>
    </ul>
    <div class="personal-information">
      <div class="avatar-container" @click="goto('profile')">
        < img src="avatar.png" alt="头像" class="avatar">
        <div class="notification-dot" v-if="hasUnread"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';

const hasUnread = ref(true); // 假设有未读消息

const goto = (page) => {
  // 根据page参数跳转到不同页面
  console.log('Navigate to:', page);
  // 这里可以添加实际的导航逻辑
};
export default {
  name: 'Ts'
// 可以添加一些逻辑
}
</script>

<style scoped>
#app {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 70px; /* 调整内边距以匹配140%缩放效果 */
  background-color: #fff;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
}
.navigation {
  display: flex;
  list-style: none;
  padding: 0;
  margin: 0;
}
.navigation li {
  padding: 14px 42px; /* 增加间距 */
  transition: background-color 0.3s;
}
.navigation li:not(:last-of-type) {
  border-right: 1px solid #ddd;
}
.navigation li a {
  text-decoration: none;
  color: #333;
  font-weight: 600;
  font-size: 22px; /* 增加字体大小 */
  transition: color 0.3s;
}
.navigation li a:hover, .navigation li a:focus {
  color: #d32f2f;
}
.personal-information {
  display: flex;
  align-items: center;
}
.avatar-container {
  position: relative;
  padding-left: 28px; /* 调整头像容器的左填充 */
  cursor: pointer;
}
.avatar {
  width: 56px; /* 增加头像大小 */
  height: 56px; /* 增加头像大小 */
  border-radius: 50%;
  border: 4px solid #d32f2f; /* 增加边框宽度 */
  transition: border-color 0.3s;
}
.avatar:hover {
  border-color: #ff6659;
}
.notification-dot {
  position: absolute;
  top: -2px;
  right: 0;
  width: 14px; /* 增加通知点大小 */
  height: 14px; /* 增加通知点大小 */
  background-color: #d32f2f;
  border-radius: 50%;
  border: 3px solid #fff; /* 增加边框宽度 */
}
</style>

