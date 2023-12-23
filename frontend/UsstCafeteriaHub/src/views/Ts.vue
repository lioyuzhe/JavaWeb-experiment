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

            <el-button type="text" @click="openEditDialog">修改个人信息</el-button>
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

    <el-dialog title="个人信息" :visible.sync="infoDialogVisible" width="500px">
      <div class="dialog-content">
        <!-- 头像展示与修改按钮 -->
        <div class="avatar-container">
          <el-avatar :src="user.avatar" size="large"></el-avatar>
          <el-button type="text" icon="el-icon-edit" @click="enterEditMode">修改</el-button>
        </div>

        <!-- 用户信息展示 -->
        <div v-if="!editMode">
          <p><strong>用户活跃度：</strong>{{ user.activityLevel }}</p>
          <p><strong>用户id：</strong>{{ user.userId }}</p>
          <p><strong>用户账号：</strong>{{ user.account }}</p>
          <p><strong>用户名：</strong>{{ user.name }}</p>
          <p><strong>用户邮箱：</strong>{{ user.email }}</p>
          <p><strong>用户手机号：</strong>{{ user.phone }}</p>
        </div>

        <!-- 编辑模式表单 -->
        <el-form v-if="editMode" :model="editForm" ref="editFormRef" label-width="100px">
          <el-form-item label="用户账号">
            <el-input v-model="editForm.account" disabled></el-input>
          </el-form-item>
          <!-- 用户名 -->
          <el-form-item label="用户名">
            <el-input v-model="editForm.name"></el-input>
          </el-form-item>
          <!-- 用户id -->
          <el-form-item label="用户id">
            <el-input v-model="editForm.userId" disabled></el-input>
          </el-form-item>
          <!-- 用户邮箱 -->
          <el-form-item label="用户邮箱">
            <el-input v-model="editForm.email"></el-input>
          </el-form-item>
          <!-- 用户手机号 -->
          <el-form-item label="用户手机号"  >
            <el-input v-model="editForm.phone"></el-input>
          </el-form-item>
          <!-- 头像上传 -->
          <el-form-item label="头像" >
            <el-upload
                class="avatar-uploader"
                :action="$baseUrl + '/files/upload'"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
            >
              <!-- 如果有头像URL，则显示预览；否则显示加号 -->
              <img v-if="editForm.avatar" :src="editForm.avatar" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>

        <!-- 操作按钮 -->
        <div class="dialog-footer">
          <el-button @click="cancelEdit">取消</el-button>
          <el-button v-if="editMode" type="primary" @click="saveEdit">保存</el-button>
        </div>
      </div>
    </el-dialog>
  </el-container>

</template>

<script>

export default {
  name: 'Ts',
  data() {
    return {

      userProfileVisible: false, // 控制用户详情信息的显示状态
      user: JSON.parse(localStorage.getItem('user')),
      infoDialogVisible: false,
      editMode: false, // 初始设置为false，表示查看模式
      editForm: {
        username: '',
        password: '',
        email: '',
        phone: '',
        avatar: '',
        userId: '',
      },
      avatarUrl: ''
    };
  },
  created() {
    this.fetchUserData();
    // 从Local Storage获取用户信息
    const userData = JSON.parse(localStorage.getItem('user'));
    if (userData) {
      // 设置用户信息给this.user
      this.user.account = userData.account;
      this.user.activityLevel = userData.activityLevel;
      this.user.avatar = userData.avatar;
      this.user.email = userData.email;
      this.user.name = userData.name;
      this.user.phone = userData.phone;
      this.user.userId = userData.userId;
    }
  },
  methods: {
    openEditDialog() {
      this.infoDialogVisible = true;
      this.editMode = false; // 确保初始状态是查看模式
    },

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
    },
    enterEditMode() {
      this.editMode = true;
      this.editForm = {...this.user}; // 复制当前用户信息到表单
    },
    handleAvatarSuccess(res, file) {
      // 处理头像上传成功的逻辑
      if (res.code === 200) {
        // 更新编辑表单中的头像URL
        this.editForm.avatar = res.data; // 假设服务器返回头像URL
// 更新用户信息中的头像URL
        // 更新用户信息中的头像URL
        this.user.avatar = res.data;

        localStorage.setItem('user', JSON.stringify(this.user));

      } else {
        // 处理上传失败的情况
        this.$message.error('头像上传失败');
      }
    },
    beforeAvatarUpload(file) {
      // 处理上传头像之前的逻辑，如文件格式验证等
      // 返回 true 表示允许上传，返回 false 表示取消上传
      // 这里可以添加文件格式验证逻辑
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      if (!isJPG) {
        this.$message.error('头像只支持上传 JPG 或 PNG 格式的图片');
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('头像图片大小不能超过 2MB');
      }
      return isJPG && isLt2M;
    },
    saveEdit() {
      // 向服务器发送请求，将修改后的数据传递给后端
      // 这里假设您使用axios库发送请求
      this.$request.post('/users/actions/updateUser', this.editForm)
          .then(response => {
            // 如果后端成功更新了数据
            if (response.code === 200) {
              // 更新localStorage中的用户数据
              const updatedUserData = {
                account: this.user.account,
                activityLevel: this.user.activityLevel,
                avatar: this.user.avatar,
                email: this.editForm.email, // 更新邮箱为修改后的值
                name: this.editForm.name,   // 更新用户名为修改后的值
                phone: this.editForm.phone  // 更新手机号为修改后的值

              };
              console.log("test:",updatedUserData)
              // 更新localStorage中的用户信息
              // 删除旧的user数据
              localStorage.removeItem('user');
              localStorage.setItem('user', JSON.stringify(updatedUserData));

              // 关闭编辑模式
              this.infoDialogVisible = false;
              this.editMode = false;

              // 更新user信息
              this.user.email = this.editForm.email;
              this.user.name = this.editForm.name;
              this.user.phone = this.editForm.phone;

              this.$message.success('用户信息更新成功！');

            }
          })
          .catch(error => {
            // 处理错误
            console.error('Error updating user information:', error);
          });
    },
    cancelEdit() {
      this.infoDialogVisible = false;
      this.editMode = false; // 退出编辑模式
    },
  }
};

</script>

<style scoped>
/* 修改后的样式 */
.dialog-content {
  text-align: center;
}

.avatar-container {
  margin-bottom: 20px;
}

.avatar-uploader .avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}

.dialog-footer {
  margin-top: 20px;
  text-align: right;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

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