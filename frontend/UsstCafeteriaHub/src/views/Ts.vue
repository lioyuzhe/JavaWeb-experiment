<template>
  <div id="Ts">
    <el-row class="header">
      <el-col :span="18">
        <ul class="navigation">
          <li>首页</li>
          <li>社区</li>
          <li>食堂</li>
        </ul>
      </el-col>
      <el-col :span="6" class="personal-information">
        <div class="avatar-container" @click="goto('profile')">
          <img src="../assets/ts_images/avatar.png" alt="头像" class="avatar">
          <div class="notification-dot" v-if="hasUnread"></div>
        </div>
      </el-col>
    </el-row>
    <router-view></router-view>
  </div>
</template>

<script>
import { ref } from 'vue';

const hasUnread = ref(true); // 假设有未读消息

const goto = (page) => {
  // 根据page参数跳转到不同页面
  console.log('导航到:', page);
  // 这里可以添加实际的导航逻辑
};

export default {
  name: 'Ts',
  // 可以添加一些逻辑
};
</script>

<style scoped>
#app {
  background-color: #fff;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
}

.header {
  padding: 14px 70px;
}

.navigation {
  display: flex;
  list-style: none;
  padding: 0;
  margin: 0;
}

.navigation li {
  padding: 14px 42px;
  transition: background-color 0.3s;
  border-right: 1px solid #ddd;
}

.navigation li:last-child {
  border-right: none; /* 移除最后一个元素的右边框 */
}

.navigation li a {
  text-decoration: none;
  color: #333;
  font-weight: 600;
  font-size: 22px;
  transition: color 0.3s;
}

.navigation li a:hover,
.navigation li a:focus {
  color: #d32f2f;
}

.personal-information {
  display: flex;
  align-items: center;
}

.avatar-container {
  position: relative;
  padding-left: 28px;
  cursor: pointer;
}

.avatar {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  border: 4px solid #d32f2f;
  transition: border-color 0.3s;
}

.avatar:hover {
  border-color: #ff6659;
}

.notification-dot {
  position: absolute;
  top: -2px;
  right: 0;
  width: 14px;
  height: 14px;
  background-color: #d32f2f;
  border-radius: 50%;
  border: 3px solid #fff;
}
</style>
