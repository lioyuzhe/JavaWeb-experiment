<template>
  <div class="complaint-container">
    <el-row :gutter="20">
      <el-col :span="16" :offset="4">
        <el-card class="complaint-card" shadow="hover">
          <h2 class="complaint-title">食堂投诉栏</h2>
          <el-form @submit.prevent="submitComplaint">
            <el-form-item label="食堂名称">
              <el-select v-model="complaint.cafeteria_id" placeholder="请选择">
                <el-option
                    v-for="cafeteria in cafeterias"
                    :key="cafeteria.cafeteria_id"
                    :label="cafeteria.cafeteria_name"
                    :value="cafeteria.cafeteria_id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="投诉内容">
              <el-input
                  type="textarea"
                  v-model="complaint.content"
                  placeholder="请描述您的投诉详情">
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" native-type="submit">提交投诉</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      complaint: {
        cafeteria_id: null,
        content: ''
      },
      cafeterias: [
        // 假设这是从后端获取的食堂列表
        { cafeteria_id: 1, cafeteria_name: '一食堂' },
        { cafeteria_id: 2, cafeteria_name: '二食堂' },
        { cafeteria_id: 3, cafeteria_name: '三食堂' },
        { cafeteria_id: 4, cafeteria_name: 'xx食堂' },
        // ...更多食堂
      ]
    };
  },
  methods: {
    submitComplaint() {
      // 这里应该有将投诉数据发送到后端的逻辑
      console.log('提交的投诉信息:', this.complaint);
      // 发送到后端的代码可以使用 axios 或其他 HTTP 客户端
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
