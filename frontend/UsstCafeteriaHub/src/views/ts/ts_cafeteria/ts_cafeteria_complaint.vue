<template>
  <div class="complaint-container">
    <el-row :gutter="20">
      <el-col :span="16" :offset="4">
        <el-card class="complaint-card" shadow="hover">
          <h2 class="complaint-title">食堂投诉栏</h2>
          <el-form @submit.prevent="submitComplaint">
            <el-form-item label="投诉内容">
              <el-input
                  type="textarea"
                  v-model="content"
                  placeholder="请描述您的投诉详情">
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitComplaint">提交投诉</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'TsCafeteriaContent',
  props: {
    // userId: Number,
    // userName: String,
    // cafeterias: {
    //   type: Object,
    //   required: true
    // },
    // cafeteria: Object,
    cafeteria: {
      type: Object,
      default: () => ({})
    }
    // 其他可能的 props...
  },
  data() {
    return {
      content: '',
      user: JSON.parse(localStorage.getItem('user') || '{}'),
    };
  },
  methods: {
    submitComplaint() {
      const payload = {
        userId: this.user.userId,
        userName: this.user.name,
        cafeteriaId: this.cafeteria.cafeteriaId,
        cafeteriaName: this.cafeteria.name,
        content: this.content,
      };

      this.$request.post('/complaints/actions/addComplaint', payload)
          .then(response => {
            // 显示成功消息
            this.$message({
              message: '投诉提交成功，我们将尽快处理！',
              type: 'success'
            });
            // 清空表单
            this.complaint.content = '';
            // 可以在这里添加其他的成功后逻辑，比如关闭弹窗或刷新列表

            // 使用 return 防止继续执行 .catch() 回调函数
            return response;
          })
          .catch(error => {
            // // 显示错误消息
            // let message = '提交投诉时出现错误，请稍后重试。';
            // // 如果错误中有更具体的信息，可以使用它
            // if (error.response && error.response.data) {
            //   message = error.response.data.message || message;
            // }
            // this.$message({
            //   message: message,
            //   type: 'error'
            // });
            // // 可以在这里添加其他的错误处理逻辑
          });
    },
  },
};
</script>

<style scoped>
.complaint-container {
  margin: 20px;
}

.complaint-card {
  margin-bottom: 20px;
}

.complaint-title {
  text-align: center;
  font-size: 20px;
  color: #333;
  margin-bottom: 20px;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-button {
  width: 100%; /* 使按钮宽度与表单项宽度一致 */
}
</style>
