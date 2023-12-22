<template>
  <el-container>
    <!-- 头部导航栏 -->
    <el-header class="header" style="position: fixed; top: 0; width: 100%; z-index: 1000;">
      <el-row type="flex" justify="space-between" align="middle">
        <el-col :span="18">
          <el-menu mode="horizontal" class="menu">
            <el-menu-item index="1" @click="goto('home')">首页</el-menu-item>
            <el-menu-item index="2" @click="goto('community')">社区</el-menu-item>
            <el-menu-item index="3" @click="goto('canteen')">食堂</el-menu-item>
          </el-menu>
        </el-col>
        <!-- 填充剩余空间，将头像推到右侧 -->
        <el-col :span="18"></el-col> <!-- 根据实际情况调整span大小，以填充头像左侧的空间 -->
        <el-col :span="6">
          <el-popover
              class="user-inf"
              placement="bottom"
              width="200"
              trigger="hover"
              v-model="userProfileVisible"
          >
            <p><strong>用户活跃度：</strong>{{ user.activityLevel }}</p>
            <p><strong>用户id：</strong>{{ user.userId }}</p>
            <p><strong>用户账号：</strong>{{ user.account }}</p>
            <p><strong>用户名：</strong>{{ user.name }}</p>
            <p><strong>用户邮箱：</strong>{{ user.email }}</p>
            <p><strong>用户手机号：</strong>{{ user.phone }}</p>

            <el-button type="text" @click="logout">退出登录</el-button>

            <template #reference>
              <el-avatar :src="user.avatar" size="large"></el-avatar>
            </template>
          </el-popover>

        </el-col>
      </el-row>
    </el-header>

    <el-main style="margin-top: 60px;">
      <router-view></router-view>
    </el-main>

  </el-container>
</template>

<script>
export default {
  name: 'Ts',
  data() {
    return {
      userProfileVisible: false, // 控制用户详情信息的显示状态
      user: {
        account: '',
        activityLevel: 0,
        avatar: '',
        email: '',
        name: '',
        phone: '',
        userId: 0,
        // ... 其他属性 ...
      },
    };
  },
  created() {
    this.fetchUserData();
  },
  methods: {
    logout() {
      // 清除用户数据
      localStorage.removeItem('user');
      // 重定向到登录页面或主页
      this.$router.push('/login'); // 根据您的路由配置适当修改
    },
    fetchUserData() {
      // 假设用户信息存储在localStorage的"user"项中
      const userData = JSON.parse(localStorage.getItem('user'));
      if (userData) {
        this.user.account = userData.account;
        this.user.activityLevel = userData.activityLevel;
        this.user.avatar = userData.avatar;
        this.user.email = userData.email;
        this.user.name = userData.name;
        this.user.phone = userData.phone;
        this.user.userId = userData.userId;
        // ... 设置其他属性 ...
      }
    },
    goto(destination) {
      let url = '';
      switch (destination) {
        case 'home':
          url = 'http://localhost:7000/ts/ts_home';
          break;
        case 'community':
          url = 'http://localhost:7000/ts/ts_community';
          break;
        case 'canteen':
          url = 'http://localhost:7000/ts/ts_cafeteria_introduction';
          break;
        case 'vote':
          url = 'http://localhost:7000/ts/ts_cafeteria_vote';
          break;
        default:
          // 默认地址或错误处理
          url = 'http://localhost:7000';
      }
      window.location.href = url;
    }
  }
};

</script>

<style scoped>
/* 修改后的样式 */
/* 下拉框主体样式 */
.el-popover {
  //background-color: #fff; /* 背景色 */
  border: 1px solid #ebeef5; /* 边框 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 阴影 */
  border-radius: 4px; /* 圆角 */
  padding: 15px; /* 内边距 */
}

/* 下拉框内的文本样式 */
.el-popover p {
  color: #666; /* 文字颜色 */
  font-size: 14px; /* 字体大小 */
  line-height: 1.5; /* 行高 */
  margin-bottom: 10px; /* 底部边距 */
}

/* 加强文本的显示效果 */
.el-popover p strong {
  color: #333; /* 强调文本的颜色 */
  font-weight: 600; /* 字体加粗 */
}

/* 按钮样式 */
.el-popover .el-button--text {
  color: #409eff; /* 按钮颜色 */
  font-size: 14px; /* 字体大小 */
  margin-top: 10px; /* 顶部边距 */
}

/* 按钮悬停效果 */
.el-popover .el-button--text:hover {
  color: #67c23a; /* 按钮悬停时的颜色 */
}

/* 调整箭头样式 */
.el-popover .popper__arrow {
  /* 根据需要调整箭头样式 */
}

.el-popover__reference {
  /* 边框和圆角 */
  border: 1px solid #ebeef5;
  border-radius: 50%; /* 圆形头像 */

  /* 阴影效果 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  /* 过渡动画 */
  transition: all 0.3s ease;

  /* 头像背景和大小（根据需要调整） */
  //background-color: #f9f9f9;
  width: 40px; /* 或其他尺寸 */
  height: 40px; /* 或其他尺寸 */
}

/* 鼠标悬停时的样式 */
.el-popover__reference:hover {
  /* 边框颜色变化 */
  border-color: #409eff;

  /* 轻微放大 */
  transform: scale(1.05);
}

.el-popover {
  //background-color: #fff; /* 设置背景颜色 */
  border: 1px solid #ebeef5; /* 设置边框颜色和宽度 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.12); /* 添加阴影以提升层次感 */
  border-radius: 4px; /* 圆角边框 */
}

.el-popover p {
  color: #666; /* 文本颜色 */
  font-size: 14px; /* 字体大小 */
  margin: 10px 0; /* 设置上下边距 */
}

.el-popover .el-button {
  color: #409eff; /* 按钮字体颜色 */
  margin-top: 10px; /* 调整与上方元素的距离 */
  align-self: center; /* 居中对齐 */
}

.el-popover .el-avatar {
  margin: 10px auto; /* 头像居中显示 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影 */
}

.header {
  //background-color: #f8f8f8; /* 更现代的背景色 */
  box-shadow: 0 2px 4px rgba(0,0,0,0.1); /* 添加阴影效果 */
  padding: 0 15px;
  height: 70px; /* 增加高度以适应更大的头像 */
}

.menu {
  line-height: 70px; /* 与头部高度保持一致 */
}

.menu .el-menu-item {
  font-size: 16px; /* 调整菜单字体大小 */
  color: #333; /* 字体颜色 */
}

.el-avatar {
  width: 40px;
  height: 40px;
  border: 2px solid #fff; /* 头像边框 */
  box-shadow: 0 2px 4px rgba(0,0,0,0.1); /* 头像阴影 */
}

/* 响应式调整 */
@media (max-width: 768px) {
  .header {
    padding: 0 10px;
  }

  .menu .el-menu-item {
    font-size: 14px;
  }

  .el-avatar {
    width: 35px;
    height: 35px;
  }
}

.avatar-hover {
  transition: transform 0.3s ease;
}

.avatar-hover:hover {
  transform: scale(1.1) translateX(-10px); /* 头像放大并向左移动 */
}

.header {
  /* 设定头部的样式和对齐 */
  background-color: #fff; /* 根据需要设置背景颜色 */
  padding: 0 70px;
  height: 60px; /* 设定一个固定高度 */
  box-shadow: 0 2px 4px rgba(0,0,0,0.1); /* 添加阴影效果 */
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