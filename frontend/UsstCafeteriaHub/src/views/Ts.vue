<template>
  <el-container>
    <!-- 头部导航栏 -->
    <el-header class="header">
      <el-row type="flex" justify="space-between" align="middle">
        <el-col :span="18">
          <el-menu mode="horizontal" class="menu">
            <el-menu-item index="1" @click="goto('home')">首页</el-menu-item>
            <el-menu-item index="2" @click="goto('community')">社区</el-menu-item>
            <el-menu-item index="3" @click="goto('canteen')">食堂</el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="6">
          <div class="personal-information">
            <el-badge is-dot v-if="hasUnread">
              <el-avatar :src="avatarUrl" size="large" @click="goto('profile')"></el-avatar>
            </el-badge>
          </div>
        </el-col>
      </el-row>
    </el-header>

    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'Ts',
  data() {
    return {
      hasUnread: true, // 是否有未读信息
      avatarUrl: '/ts_images/avatar.png' // 您的猫猫头像URL
    };
  },
  methods: {
    goto(page) {
      // 页面跳转逻辑
    }
  }
};
</script>

<style scoped>
.el-container {
  /* 确保容器填满整个视口的宽度 */
  width: 100%;
  margin: 0;
}

.header {
  /* 设定头部的样式和对齐 */
  background-color: #fff; /* 根据需要设置背景颜色 */
  padding: 0 70px;
  height: 60px; /* 设定一个固定高度 */
}

.menu {
  /* 菜单样式调整 */
  line-height: 60px; /* 调整行高以垂直居中 */
}

.menu .el-menu-item {
  /* 单独菜单项的样式 */
  font-size: 20px; /* 字体大小根据需要调整 */
}

.personal-information {
  /* 个人信息区域样式 */
  display: flex;
  justify-content: flex-end;
  align-items: center;
  position: relative; /* 设置为相对定位 */
}

.el-avatar {
  width: 50px;
  height: 50px;
}

.el-badge {
  /* 未读消息徽章样式 */
  position: absolute;
  top: -10px; /* 徽章位置调整 */
  right: 0;
}

.main-content {
  /* 主内容区域样式 */
  display: flex;
  justify-content: space-between;
  padding-top: 20px; /* 从头部下方开始 */
}

.left-side {
  /* 左侧内容样式 */
  width: 80%;
}

.right-side {
  /* 右侧图标区域样式 */
  width: 30%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.icons {
  /* 图标区域样式 */
  display: flex;
  justify-content: space-around; /* 平均分配空间 */
  align-items: center;
  padding: 10px;
  border-top: 1px solid #eee; /* 可选的分割线 */
}

.icons .el-button {
  /* 单独图标按钮样式 */
  font-size: 20px; /* 根据需要调整图标大小 */
  margin: 10px 0; /* 调整上下边距 */
}

.icons-row {
  gap: 30px; /* 增大功能图标之间的间隔 */
}
/* 添加媒体查询以适应不同屏幕尺寸 */
@media (max-width: 768px) {
  .header,
  .main-content {
    flex-direction: column;
  }

  .personal-information,
  .icons {
    width: 100%;
    justify-content: center;
  }

  .el-avatar {
    margin-bottom: 10px; /* 在移动视图中将头像与菜单项分开 */
  }
}
</style>

