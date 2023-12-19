<template>
  <div class="community">
    <el-row>
      <!-- 左侧 -->
      <el-col :span="16">
        <!-- 社区动态 -->
        <div class="community-dynamic" shadow="hover">
          <div class="top-bar">
            <!-- 空白占位 -->
            <div class="placeholder"></div>
            <!-- 搜索框 -->
            <el-input
                v-model="searchText"
                placeholder="输入关键字搜索"
                prefix-icon="el-icon-search"
                class="search-box"
            ></el-input>
          </div>
          <!-- 排序切换和发表按钮 -->
          <div class="sort-and-post">
            <!-- 排序切换 -->
            <div class="sort-toggle">
              <span @click="toggleSort('time')" :class="{'active': sortType === 'time'}">时间</span>
              <span>|</span>
              <span @click="toggleSort('hot')" :class="{'active': sortType === 'hot'}">热度</span>
            </div>

            <!-- 发表 -->
            <el-button icon="el-icon-plus" @click="showEditor">发布动态</el-button>
          </div>
          <!-- 编辑器容器 -->
          <el-dialog :visible.sync="showDialog" title="发布动态" width="60%">
            <div>
              <input v-model="postDynamicData.newTitle" placeholder="标题" class="dynamic-title-input" />
            </div>
            <div class="post-editor">
              <Toolbar
                  style="border-bottom: 1px solid #ccc"
                  :editor="editor"
                  :defaultConfig="postDynamicData.toolbarConfig"
                  :mode="postDynamicData.mode"
              />
              <Editor
                  style="height: 300px; overflow-y: hidden;"
                  v-model="postDynamicData.html"
                  :defaultConfig="postDynamicData.editorConfig"
                  :mode="postDynamicData.mode"
                  @onCreated="onCreated"
              />
            </div>
            <span slot="footer" class="dialog-footer">
              <el-button @click="showDialog = false">取消</el-button>
              <el-button type="primary" @click="postDynamic">发布</el-button>
             </span>
          </el-dialog>
          <!-- 动态列表 -->
          <div class="dynamic-list">
            <div class="dynamic-item" v-for="post in dynamicList" :key="post.message_id">
              <!-- 动态元数据 -->
              <div class="dynamic-meta">
                <!-- 头像 -->
                <img :src="post.avatar" alt="Avatar" class="dynamic-avatar"/>
                <div class="dynamic-user-info">
                  <!-- 用户名 -->
                  <span class="dynamic-username">{{ post.user_name }}</span>
                  <!-- 时间 -->
                  <span class="dynamic-time">{{ post.time }}</span>
                </div>
              </div>
              <!-- 显示标题 -->
              <h3>{{ post.title }}</h3>
              <!-- 动态内容 -->
              <div class="dynamic-content">{{ post.content }}</div>
              <!-- 动态操作区域 -->
              <div class="dynamic-actions">
                <!-- 点赞按钮 -->
                <button @click="toggleLike(post)" class="action-button">
                  <i :class="['el-icon-thumb', { liked: post.liked }]"></i>
                  <span>{{ post.like_count }}</span>
                </button>
                <!-- 评论按钮 -->
                <button @click="toggleComments(post)" class="action-button">
                  <i class="el-icon-chat-dot-square"></i>
                </button>
              </div>
              <!-- 评论区域 -->
              <div v-if="post.showComments" class="comments">
                <!-- 现有评论列表 -->
                <div v-for="comment in post.comments" :key="comment.comment_id" class="comment">
                  <strong>{{ comment.user_name }}：</strong>{{ comment.content }}
                </div>
                <!-- 添加新评论的输入框 -->
                <div class="new-comment">
                  <input
                      type="text"
                      v-model="post.newComment"
                      placeholder="写下你的评论..."
                      class="comment-input"
                  />
                  <button @click="addComment(post)" class="comment-submit">评论</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-col>
      <!-- 右侧 -->
      <el-col :span="8">
        <div class="notification-section">
          <div class="notification-tabs">
            <button @click="switchMessageType('likes')" :class="{ 'active': currentMessageType === 'likes' }">
              <i class="el-icon-thumb" :style="{ color: currentMessageType === 'likes' ? 'blue' : 'gray' }"></i>
            </button>
            <button @click="switchMessageType('comments')" :class="{ 'active': currentMessageType === 'comments' }">
              <i class="el-icon-chat-dot-square" :style="{ color: currentMessageType === 'comments' ? 'blue' : 'gray' }"></i>
            </button>
            <button @click="switchMessageType('messages')" :class="{ 'active': currentMessageType === 'messages' }">
              <i class="el-icon-message" :style="{ color: currentMessageType === 'messages' ? 'blue' : 'gray' }"></i>
            </button>
          </div>

          <!-- 点赞消息列表 -->
          <div v-if="currentMessageType === 'likes'">
            <ul>
              <li v-for="like in likes" :key="like.id">{{ like.user }} 点赞了您的动态: "{{ like.title }}"</li>
            </ul>
          </div>

          <!-- 评论消息列表 -->
          <div v-if="currentMessageType === 'comments'">
            <ul>
              <li v-for="comment in comments" :key="comment.id">{{ comment.user }}: "{{ comment.content }}"</li>
            </ul>
          </div>

          <!-- 私信消息列表 -->
          <div v-if="currentMessageType === 'messages' && !showChatBox">
            <ul>
              <li v-for="message in messages" :key="message.message_id" @click="selectMessage(message)">
                <img :src="message.sender_avatar" class="avatar">
                <div>{{ message.sender_name }}: {{ message.content }}</div>
                <div>{{ message.timestamp }}</div>
              </li>
            </ul>
          </div>

          <!-- 聊天框 -->
          <div v-if="showChatBox" class="chat-window">
            <div class="chat-header">
              <img :src="selectedMessage.sender_avatar" class="chat-avatar">
              <h3 class="chat-name">{{ selectedMessage.sender_name }}</h3>
              <el-button type="text" icon="el-icon-close" @click="closeChat"></el-button>
            </div>
            <div class="chat-body">
              <div v-for="msg in selectedMessage.conversationContent" :key="msg.id" class="chat-message" :class="{ 'is-sender': msg.sender_id === currentUser.id }">
                <div class="chat-message-content">{{ msg.content }}</div>
                <div class="chat-message-time">{{ msg.timestamp }}</div>
              </div>
            </div>
            <div class="chat-footer">
              <el-input
                  type="textarea"
                  v-model="newMessageText"
                  placeholder="写点什么..."
                  class="chat-input"
                  @keyup.enter="sendMessage"
              ></el-input>
              <el-button type="primary" icon="el-icon-send" @click="sendMessage"></el-button>
            </div>
          </div>
        </div>

      </el-col>

    </el-row>
  </div>
</template>

<script>
import '@wangeditor/editor/dist/css/style.css'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import request from '@/utils/request'
export default {
  components: {
    Editor,
    Toolbar
  },
  data() {
    return {
      searchText: '',
      editor: null,
      postDynamicData: {
        newTitle: '',
        html: '',
        communityId: '',
        communityName: '',
        userId: '',
        userName: '',
        likeCount: 0,
        created_time: '',
        deleted: 0,
        toolbarConfig: { },
        editorConfig: { placeholder: '请输入内容...' },
        mode: 'default', // or 'simple'
      },
      showDialog: false, // 控制对话框的显示

      sortType: 'time',//默认时间排序
      dynamicList: [],//存储动态列表

      currentMessageType: 'likes',
      currentUser: {
        id: 1000,
        name: '当前用户',
      },
      likes: [
        // 假设这些是点赞通知
        { id: 1, user: '用户A', title: '分享了一张图片', like_count: 5 },
        { id: 2, user: '用户B', title: '这是一个很棒的动态', like_count: 3 },
        // ...更多假数据...
      ],
      comments: [
        // 假设这些是评论通知
        { id: 1, user: '用户B', title: '评论了您的动态', content: '很有意思！' },
        { id: 2, user: '用户C', title: '评论了您的动态', content: '赞同你的观点' },
        // ...更多假数据...
      ],
      messages: [
        // 假设这些是私信消息
        {
          message_id: 1,
          sender_id: 2,
          sender_name: '用户C',
          sender_avatar: 'path/to/avatar.jpg',
          receiver_id: 1000,
          receiver_name: '当前用户',
          content: '您好，有时间聊聊天吗？',
          timestamp: '2023-01-01 10:00',
          read_status: 0,
        },
        {
          message_id: 2,
          sender_id: 3,
          sender_name: '用户D',
          sender_avatar: 'path/to/avatar2.jpg',
          receiver_id: 1000,
          receiver_name: '当前用户',
          content: '昨天的聚会很开心。',
          timestamp: '2023-01-02 15:30',
          read_status: 0,
        },
        // ...更多假数据...
      ],
      selectedMessage: null,
      showChatBox: false,
      newMessageText: '',

      // 其他数据定义
    };
  },
  methods: {
    toggleSort(type) {
      this.sortType = type;
      this.fetchDynamicList();
    },
    onCreated(editor) {
      this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
    },
    showEditor() {
      this.showDialog = true; // 点击按钮时显示对话框
    },
    postDynamic() {
      const postData = {
        communityId: this.postDynamicData.communityId,
        communityName: this.postDynamicData.communityName,
        userId: this.postDynamicData.userId,
        userName: this.postDynamicData.userName,
        title: this.postDynamicData.newTitle,
        content: this.postDynamicData.html,
        likeCount: this.postDynamicData.likeCount,
        created_time: this.postDynamicData.created_time,
        deleted: this.postDynamicData.deleted
      };
      console.log("postData", postData);
      // 发送请求
      request.post('/communityMessages/actions/addCommunityMessage', postData)
          .then(response => {
            // 假设 response.data 包含了新发布的动态数据
            const newDynamic = response.data;
            // 将新动态添加到列表的开头
            this.dynamicList.unshift(newDynamic);
            // 处理响应
            console.log('发布成功', response);
            this.resetPostDynamicData();
          })
          .catch(error => {
            // 处理错误
            console.error('发布失败', error);
          });
    },
    resetPostDynamicData() {
      // 清空发布动态表单
      this.postDynamicData.newTitle = '';
      this.postDynamicData.html = '';
      // 可以根据需要重置其他字段
      this.showDialog = false;
    },

    async fetchDynamicList() {
      let url = '';
      if (this.sortType === 'time') {
        url = '/communityMessages/actions/getCommunityMessageByTime';
      } else {
        url = '/communityMessages/actions/getCommunityMessageByLike';
      }
      try {
        const response = await this.$request.get(url);
        this.dynamicList = response.data;
        this.dynamicList.forEach(dynamic => {
          dynamic.showComments = false; // 初始不显示评论
          dynamic.comments = []; // 初始化评论数组
        });
      } catch (error) {
        console.error('Error fetching dynamics:', error);
      }
    },
    async fetchComments(dynamic) {
      try {
        const response = await this.$request.get(`/communityMessages/actions/getComments?messageId=${dynamic.message_id}`);
        dynamic.comments = response.data;
      } catch (error) {
        console.error('Error fetching comments:', error);
      }
    },
    toggleComments(dynamic) {
      dynamic.showComments = !dynamic.showComments;
      if (dynamic.showComments && dynamic.comments.length === 0) {
        this.fetchComments(dynamic);
      }
    },
    toggleLike(dynamic) {
      dynamic.liked = !dynamic.liked; // 切换点赞状态
      dynamic.like_count += dynamic.liked ? 1 : -1; // 点赞数加1或减1
    },
    async addComment(dynamic) {
      if (!dynamic.newComment || dynamic.newComment.trim() === '') {
        alert('评论内容不能为空');
        return;
      }
      try {
        const newCommentData = {
          messageId: dynamic.message_id, // 当前动态的 ID
          userId: this.userId,
          userName: this.userName,
          content: dynamic.newComment,
          likeCount: 0, // 新评论的点赞数初始为 0
          //创建时间
          // ...其他需要的字段
        };

        const response = await this.$request.post('/community/comments', newCommentData);
        if (response.code === 0) {
          // 假设添加成功后，后端返回新评论的完整数据
          dynamic.comments.push(response.data);
          dynamic.newComment = ''; // 清空输入框
        } else {
          alert('添加评论失败: ' + response.message);
        }
      } catch (error) {
        console.error('Error adding comment:', error);
        alert('添加评论时发生错误');
      }
    },

    switchMessageType(type) {
      // 切换消息类型
      this.currentMessageType = type;
    },
    selectMessage(message) {
      this.selectedMessage = message;
      // 将选定的消息标记为已读
      message.read_status = 1;
      this.showChatBox = true;
    },
    // sendMessage() {
    //   const newMsg = {
    //     id: Date.now(),
    //     content: this.newMessageText,
    //     timestamp: new Date().toLocaleTimeString(),
    //     sender_id: this.currentUser.id,
    //   };
    //   // 假设selectedMessage.conversationContent是一个数组，存储对话内容
    //   this.selectedMessage.conversationContent.push(newMsg);
    //   this.newMessageText = ''; // 清空输入框
    // },

    closeChat() {
      this.showChatBox = false;
      this.selectedMessage = null;
    }
  },
  mounted() {
    //从父组件中获取数据
    const user = JSON.parse(localStorage.getItem("user"));
    if (user) {
      this.postDynamicData.userId = user.user_id;
      this.postDynamicData.userName = user.name;
    }
  },
  created() {
    this.fetchDynamicList();
    this.fetchComments();
  },
  beforeDestroy() {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁编辑器
  }
};
</script>

<style scoped>
.community {
  padding: 20px;
}

.community-dynamic {
  margin-bottom: 20px;
  flex-grow: 1;
}

.top-bar {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;
}

.placeholder {
  flex: 1;
}

.search-box {
  width: 200px; /* 搜索框的宽度 */
}

.sort-and-post {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.sort-toggle {
  margin-right: 10px;
}

.sort-toggle span {
  cursor: pointer;
  padding: 0 5px;
}

.sort-toggle .active {
  color: #409EFF;
}

.post-editor {
  border: 1px solid #d3d3d3; /* 添加边框 */
  border-radius: 4px; /* 圆角 */
  background-color: #fff; /* 白色背景 */
  padding: 10px; /* 内部留白 */
  margin-bottom: 20px; /* 和下方元素的间距 */
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.dynamic-list {
  display: flex;
  flex-direction: column;
  align-items: center; /* 居中对齐动态项 */
  padding: 0; /* 根据需要调整内边距 */
  margin-top: 20px;
}

.dynamic-title-input {
  width: 100%;
  padding: 8px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.dynamic-item {
  width: 90%;
  margin-bottom: 20px;
  border: 1px solid #ebebeb;
  border-radius: 8px; /* 添加圆角 */
  overflow: hidden; /* 确保所有内容都在边框内部 */
  background: #fff; /* 设置背景颜色 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.dynamic-content {
  font-size: 14px;
  margin-bottom: 5px;
}

.dynamic-meta {
  font-size: 12px;
  color: #999;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}


.dynamic-avatar {
  width: 40px;
  height: 40px;
  border-radius: 20px;
  object-fit: cover;
  margin-right: 10px;
}

.dynamic-user-info {
  display: flex;
  flex-direction: column;
}

.dynamic-username {
  font-weight: bold;
  margin-bottom: 5px;
}

.dynamic-time {
  color: #888;
  font-size: 0.9em;
}

.dynamic-actions {
  display: flex;
  align-items: center;
  justify-content: space-between; /* 将点赞和评论按钮分开 */
}

.action-button {
  border: none;
  background: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  margin-right: 10px; /* 两个按钮之间的距离 */
}

.action-button i {
  font-size: 20px; /* 图标大小 */
  margin-right: 5px; /* 图标和数字之间的距离 */
}

.action-button span {
  font-size: 14px; /* 点赞数的字体大小 */
}
/* 默认状态下的图标颜色 */
.el-icon-thumb {
  color: #C0C4CC;
}

/* 点赞状态下的图标颜色 */
.el-icon-thumb.liked {
  color: #409EFF;
}


.action-button .el-icon-message {
  color: #909399; /* 评论图标颜色 */
}

button {
  margin-right: 10px;
  background-color: #f0f0f0;
  border: none;
  padding: 5px 10px;
  border-radius: 3px;
  cursor: pointer;
}

button:hover {
  background-color: #e0e0e0;
}

.comments {
  margin-top: 10px;
  border-top: 1px solid #eee;
  padding-top: 10px;
}

.comment {
  background-color: #f9f9f9;
  padding: 5px;
  border-radius: 5px;
  margin-bottom: 5px;
}

.new-comment {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.comment-input {
  flex: 1;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-right: 10px;
}

.comment-submit {
  padding: 5px 15px;
  background-color: #409EFF;
  border: none;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

.comment-submit:hover {
  background-color: #3079ED;
}
/* 适应不同屏幕宽度的响应式设计 */
@media (max-width: 768px) {
  .post-editor {
    margin: 10px; /* 在较小屏幕上减小外边距 */
  }
}

/* 右侧样式 */
.notification-section {
  display: flex;
  flex-direction: column;
  height: 100%; /* 右侧部分占满整个高度 */
  border-left: 1px solid #eee;
  padding: 0; /* 可以根据需要调整内边距 */
  flex-grow: 1;
}

.notification-tabs {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

.notification-tabs button {
  background: none;
  border: none;
  cursor: pointer;
}

.notification-tabs i {
  font-size: 20px;
  color: #409EFF;
}

.notifications ul {
  list-style: none;
  padding: 0;
}

.notifications li {
  margin-bottom: 5px;
  font-size: 14px;
}
/* 聊天窗口样式 */
.chat-window {
  display: flex;
  flex-direction: column;
  height: 600px; /* 使聊天窗口占满容器 */
  background: #FFF;
}

/* 聊天头部 */
.chat-header {
  padding: 15px;
  background-color: #F5F5F5;
  border-bottom: 1px solid #E0E0E0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.chat-avatar {
  width: 40px;
  height: 40px;
  border-radius: 20px;
  object-fit: cover;
}

.chat-name {
  margin-left: 10px;
  flex-grow: 1;
  font-weight: bold;
}

/* 聊天内容区域 */
.chat-body {
  flex-grow: 1;
  overflow-y: auto;
  padding: 10px;
  background-color: #F9F9F9;
}

/* 消息样式 */
.chat-message {
  display: flex;
  margin-bottom: 10px;
  align-items: flex-end;
}

.is-sender .chat-message-content {
  background-color: #DCF8C6;
  align-self: flex-end;
}

.chat-message-content {
  max-width: 60%;
  padding: 10px;
  border-radius: 15px;
  background-color: #FFF;
  margin: 5px;
  box-shadow: 0 1px 1px rgba(0,0,0,0.05);
}

.chat-message-time {
  font-size: 12px;
  color: #888;
  margin-top: 5px;
}

/* 输入区域 */
.chat-footer {
  border-top: 1px solid #E0E0E0;
  padding: 10px;
  background-color: #F5F5F5;
  display: flex;
  align-items: center;
}

/* 滚动条样式 */
.chat-body::-webkit-scrollbar {
  width: 5px;
}

.chat-body::-webkit-scrollbar-thumb {
  background-color: #c1c1c1;
  border-radius: 10px;
}

.chat-body::-webkit-scrollbar-track {
  background-color: #f1f1f1;
}

</style>
