<template>
  <div class="community">
    <el-row>
      <!-- 左侧 -->
      <el-col :span="16">
        <!-- 社区动态 -->
        <el-card class="community-dynamic" shadow="hover">
          <el-select v-model="sortType" placeholder="按时间排序">
            <el-option label="按时间排序" value="time"></el-option>
            <el-option label="按热度排序" value="hot"></el-option>
          </el-select>

          <el-input
              v-model="searchText"
              placeholder="输入关键字搜索"
              prefix-icon="el-icon-search"
          ></el-input>

          <el-input
              type="textarea"
              placeholder="发表动态..."
              v-model="newPost"
              autosize
          ></el-input>

          <!-- 这里可以展示社区动态列表，你可以根据实际需要进行修改 -->
          <div class="dynamic-list">
            <!-- 社区动态列表内容 -->
            <!-- 示例 -->
            <div class="dynamic-item" v-for="(post, index) in dynamicList" :key="index">
              {{ post.content }}
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 右侧 -->
      <el-col :span="8">
        <el-card class="message-list" shadow="hover">
          <h3>我的消息</h3>

          <!-- 这里可以展示消息列表，你可以根据实际需要进行修改 -->
          <div class="message-item" v-for="(message, index) in messageList" :key="index">
            {{ message.content }}
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  data() {
    return {
      sortType: 'time',
      searchText: '',
      newPost: '',
      dynamicList: [
        { content: '社区动态1' },
        { content: '社区动态2' },
        { content: '社区动态3' },
      ],
      messageList: [
        { content: '消息1' },
        { content: '消息2' },
        { content: '消息3' },
      ],
    };
  },
};
</script>

<style scoped>
.community {
  padding: 20px;
}

.community-dynamic {
  margin-bottom: 20px;
}

.dynamic-list {
  margin-top: 20px;
}

.dynamic-item {
  margin-bottom: 10px;
}

.message-list {
  h3 {
    margin-bottom: 10px;
  }
}

.message-item {
  margin-bottom: 10px;
}
</style>
