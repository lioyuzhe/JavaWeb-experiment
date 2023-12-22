<template>
  <div class="home-container">
    <!-- 左侧内容 -->
    <div class="left-content">
      <!-- 动态轮播部分 -->
      <div class="dynamic-carousel">
        <el-carousel :interval=4000 type="card" height="200px">
          <el-carousel-item v-for="promo in promotions" :key="promo.promotion_id">
            <el-card>
              <div>
                <h3>{{ promo.dish_name }}</h3>
                <p>{{ promo.description }}</p>
              </div>
            </el-card>
          </el-carousel-item>
        </el-carousel>
      </div>
      <!-- 功能入口部分 -->
      <div class="feature-entrances">
        <el-row :gutter="20">
          <el-col :span="8" v-for="entry in featureEntrances" :key="entry.id">
            <!-- 使用 router-link 来实现路由跳转 -->
            <router-link :to="getLink(entry.id)">
              <el-card>
                <img :src="entry.imageUrl" class="entrance-image" alt="Feature">
                <div>
                  <h3>{{ entry.title }}</h3>
                  <p>{{ entry.description }}</p>
                </div>
              </el-card>
            </router-link>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- 右侧内容 -->
    <div class="right-content">
      <!-- 功能图标部分 -->
      <div class="icon-tray">
        <el-badge :value="commentsCount" class="icon">
          <el-button icon="el-icon-message" circle @click="handleIconClick('comments')"></el-button>
          <!-- 显示未查看的新评论数量 -->
          <template v-if="hasUnreadComments">
            <div class="unread-indicator"></div>
          </template>
        </el-badge>

        <el-badge :value="likesCount" class="icon">
          <el-button icon="el-icon-thumb" circle @click="handleIconClick('likes')"></el-button>
          <!-- 显示未查看的新点赞数量 -->
          <template v-if="hasUnreadLikes">
            <div class="unread-indicator"></div>
          </template>
        </el-badge>

        <el-badge :value="complaintsCount" class="icon">
          <el-button icon="el-icon-warning" circle @click="handleIconClick('complaints')"></el-button>
          <!-- 显示未查看的新投诉回复数量 -->
          <template v-if="hasUnreadComplaintReplies">
            <div class="unread-indicator"></div>
          </template>
        </el-badge>
      </div>
      <!-- 消息栏 -->
      <el-card class="message-card">
        <div class="message-header">
          <!-- 标题部分代码省略 -->
        </div>
        <el-scrollbar class="message-list">
          <div v-for="item in activeMessages" :key="item.id" class="message-item">
            <span>{{ generateMessageContent(item) }}</span>
          </div>
        </el-scrollbar>


      </el-card>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'ts_home',
  data() {
    return {
      hasUnreadComments: false,//用于标识是否有未读的评论消息。
      hasUnreadLikes: false,// 用于标识是否有未读的点赞消息。
      hasUnreadComplaintReplies: false,//用于标识是否有未读的投诉回复消息。
      commentsCount: 0,//用于存储评论消息的总数。
      likesCount: 0,//用于存储点赞消息的总数。
      complaintsCount: 0,//用于存储投诉回复消息的总数。
      latestComments: [],//用于存储最新的评论
      latestComplaintReplies: [],//用于存储最新的投诉回复
      comment: {
        comment_id: null, // 通常由后端生成
        message_id: '',
        user_id: '',
        user_name: '',
        content: '',
        like_count: 0,
        create_time: new Date(), // 设置为当前时间或由用户选择
        deleted: 0 // 默认为0
      },
      activeTab: 'comments', // 默认显示评论
      dialogVisible: false,
      selectedItem: {},

      featureEntrances: [
        // 现有的功能入口数据
        {
          id: 'promo',
          title: '最新促销',
          description: '探索我们的特价菜品',
          imageUrl: 'path/to/promotion-image.jpg'
        },
        {
          id: 'vote',
          title: '投票调查',
          description: '参与我们的最新调查',
          imageUrl: 'path/to/vote-image.jpg'
        },
        // 新增加的功能入口数据
        {
          id: 'recommendation',
          title: '食堂推荐',
          description: '最新食堂推荐菜品',
          imageUrl: 'path/to/recommendation-image.jpg'
        },
        {
          id: 'communityTopic',
          title: '社区话题',
          description: '社区热门话题，参与讨论',
          imageUrl: 'path/to/community-topic-image.jpg'
        },
        {
          id: 'canteenRank',
          title: '食堂排名',
          description: '查看最新食堂排名',
          imageUrl: 'path/to/canteen-rank-image.jpg'
        },
        {
          id: 'dishRank',
          title: '菜品排名',
          description: '最新高评价菜品排名',
          imageUrl: 'path/to/dish-rank-image.jpg'
        }
      ],
      promotions: [
        {
          promotion_id: 1,
          dish_name: '促销菜品1',
          description: '促销菜品1描述。',
        },
        // 可以添加更多模拟的促销信息
        {
          promotion_id: 2,
          dish_name: '最新投票调查',
          description: '最新投票调查描述。',
        },
        {
          promotion_id: 3,
          dish_name: '最新食堂推荐菜品',
          description: '最新食堂推荐菜品描述。',
        },
        {
          promotion_id: 4,
          dish_name: '社区热门话题',
          description: '被点赞最多的社区信息。',
        },
        {
          promotion_id: 5,
          dish_name: '最新食堂排名',
          description: '最新食堂排名描述。',
        },
        {
          promotion_id: 6,
          dish_name: '最新高评价菜品排名',
          description: '最新高评价菜品排名描述。',
        },
      ],
      communityMessages: [

      ],
      likes: [

      ],
      complaints: [

      ],

    };

  },
  created() {
    this.fetchData();
    // 定时器，每隔一定时间请求最新数据
    setInterval(() => {
      this.fetchLatestComments();
      this.fetchLatestComplaintReplies();
    }, 10000); // 比如每10秒更新一次
  },
  methods: {
    getMessageType(item) {
      // 根据消息的字段判断消息类型
      if (item.cafeteriaName && item.content && item.reply) {
        return 'complaint';
      } else {
        return 'normal';
      }
    },

    generateMessageContent(item) {
      // 根据消息的类型生成消息内容
      if (this.getMessageType(item) === 'complaint') {
        // 修改此处以反映新的格式
        return `${item.cafeteriaName}食堂回复了你的投诉"${item.content}": (${item.reply})`;
      } else {
        return `${item.userName}：${item.content}`;
      }
    },


    async fetchData() {
      await this.fetchLatestComments();
      await this.fetchLatestComplaintReplies();
      // ...其他可能需要获取的数据
    },

    async fetchLatestComments() {
      try {
        const response = await this.$request.get('/community/comments/test');
        this.latestComments = response.data;
        // 根据返回的数据更新评论计数
        this.commentsCount = response.data.length; // 从后端获取的数据更新评论计数
        // this.commentsCount = this.latestComments.length;
      } catch (error) {
        console.error('Error fetching latest comments:', error);
      }
    },

    async fetchLatestComplaintReplies() {
      try {
        const response = await this.$request.get('/complaints/actions/getComplaintReplyByUserId?userId=1');
        this.latestComplaintReplies = response.data;
        // 根据返回的数据更新投诉回复计数
        // this.complaintsCount = this.latestComplaintReplies.length;
        this.complaintsCount = response.data.length; // 从后端获取的数据更新投诉回复计数
      } catch (error) {
        console.error('Error fetching latest comments:', error.response.status, error.response.statusText);

      }
    },
    async submitComment() {
      try {
        await this.$request.post('/community/comments/test', this.comment);
        // 处理提交后的操作，例如清空表单、显示消息等
      } catch (error) {
        console.error(error);
        // 错误处理
      }
    },

    // 点击事件处理器，用于切换活动标签
    handleIconClick(tab) {
      console.log('Tab clicked:', tab); // 添加调试信息
      this.activeTab = tab;

    },
    getLink(entryId) {
      switch (entryId) {
        case 'vote':
          // 导航到 ts_cafeteria_content 页面的锚点位置
          return '/ts/ts_cafeteria_content#vote';
        case 'communityTopic':
          return '/ts/ts_community';
        case 'recommendation':
          return '/ts/ts_cafeteria_introduction';
          // 其他入口的路由链接可以在这里添加
        default:
          return '/';
      }
    },


    // 方法部分可以根据需要实现API调用等
  },
  computed: {
    activeMessages() {
      switch (this.activeTab) {
        case 'comments':
          this.fetchLatestComments();
          return this.latestComments;
        case 'likes':
          return this.likes;
        case 'complaints':
          this.fetchLatestComplaintReplies();
          return this.latestComplaintReplies;
        default:
          return [];
      }
    }
  },

}
</script>


<style scoped>
/* 全局布局 */
.home-container {
  display: flex;
  justify-content: space-between; /* 确保左右内容分布在两侧 */
}

/* 导航栏下方左侧内容样式 */
.left-content {
  width: calc(100% - 320px); /* 计算右侧内容宽度后剩余的宽度 */
  margin-right: 20px; /* 右侧内容与左侧内容之间的间隔 */
}

/* 功能图标和消息栏样式 */
.right-content {
  width: 300px; /* 功能图标和消息栏宽度 */
  display: flex;
  flex-direction: column;
  align-items: flex-end; /* 右对齐 */
}

/* 功能图标样式 */
.icon-tray {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 20px; /* 与消息栏之间的间距 */
}

.icon-tray .icon {
  flex: 1; /* 平均分布图标 */
  text-align: center; /* 图标居中 */
}

/* 消息栏样式 */
.message-card {
  width: 100%;
  margin-top: 20px; /* 消息栏与图标的间距 */
}

.message-header h3 {
  font-size: 16px;
  margin-bottom: 10px;
}

.message-list {
  height: calc(100vh - 220px);
  overflow-y: auto;
}

.message-item {
  margin-bottom: 10px;
}

/* 适应移动视图 */
@media (max-width: 768px) {
  .home-container {
    flex-direction: column;
  }

  .left-content,
  .right-content {
    width: 100%;
    margin-right: 0;
  }

  .icon-tray {
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
    margin-bottom: 10px;
  }

  .icon-tray .icon {
    width: 33%;
    margin-bottom: 10px;
  }

  .message-list {
    height: auto;
  }
}


</style>