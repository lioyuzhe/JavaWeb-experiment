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
            placement="bottom"
            width="200"
            trigger="hover"
            v-model="userProfileVisible"
        >
          <p>用户活跃度：{{ user.activityLevel }}</p>
          <p>用户id：{{ user.userId }}</p>
          <p>用户账号：{{ user.account }}</p>
          <p>用户名：{{ user.name }}</p>
          <p>用户邮箱：{{ user.email }}</p>
          <p>用户手机号：{{ user.phone }}</p>

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