<template>
  <div class="home-container">
    <!-- 左侧内容 -->
    <div class="left-content">
      <!-- 动态轮播部分 -->
      <div class="dynamic-carousel">
        <el-carousel :interval=4000 type="card" height="200px">
          <el-carousel-item v-for="promo in promotions" :key="promo.promotion_id">
            <el-card>
              <img :src="promo.image_url" alt="促销图片">
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
        </el-badge>
        <el-badge :value="likesCount" class="icon">
          <el-button icon="el-icon-thumb" circle @click="handleIconClick('likes')"></el-button>
        </el-badge>
        <el-badge :value="complaintsCount" class="icon">
          <el-button icon="el-icon-warning" circle @click="handleIconClick('complaints')"></el-button>
        </el-badge>
      </div>
      <!-- 消息栏 -->
      <el-card class="message-card">
        <div class="message-header">
          <!-- 标题部分代码省略 -->
        </div>
        <el-scrollbar class="message-list">
          <div v-for="item in activeMessages" :key="item.id" class="message-item">
            <span>{{ item.user_name }}：</span>
            <p>{{ item.content }}</p>
          </div>
        </el-scrollbar>
      </el-card>
    </div>
  </div>
</template>
<script>

export default {
  name: 'ts_home',
  data() {
    return {
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

      avatarUrl: '/ts_images/avatar.png',
      commentsCount: 3,
      likesCount: 2,
      complaintsCount: 2,
      promotions: [
        {
          promotion_id: 1,
          dish_name: '促销菜品1',
          description: '促销菜品1描述。',
          image_url: 'https://example.com/promo1.jpg'
        },
        // 可以添加更多模拟的促销信息
        {
          promotion_id: 2,
          dish_name: '最新投票调查',
          description: '最新投票调查描述。',
          image_url: 'https://example.com/promo2.jpg'
        },
        {
          promotion_id: 3,
          dish_name: '最新食堂推荐菜品',
          description: '最新食堂推荐菜品描述。',
          image_url: 'https://example.com/promo3.jpg'
        },
        {
          promotion_id: 4,
          dish_name: '社区热门话题',
          description: '被点赞最多的社区信息。',
          image_url: 'https://example.com/promo4.jpg'
        },
        {
          promotion_id: 5,
          dish_name: '最新食堂排名',
          description: '最新食堂排名描述。',
          image_url: 'https://example.com/promo5.jpg'
        },
        {
          promotion_id: 6,
          dish_name: '最新高评价菜品排名',
          description: '最新高评价菜品排名描述。',
          image_url: 'https://example.com/promo6.jpg'
        },
      ],
      communityMessages: [
        {message_id: 1, user_name: 'Alice', content: '今天的餐点非常美味！'},
        {message_id: 2, user_name: 'Bob', content: '期待更多的素食选择。'},
        {message_id: 3, user_name: 'Carol', content: '服务态度非常好，环境也很舒适。'},
      ],
      likes: [
        {id: 1, user_name: 'Dave', content: 'Dave觉得你的评论很赞'},
        {id: 2, user_name: 'Eve', content: 'Eve为你的分享点了赞'},
      ],
      complaints: [
        {
          complaint_id: 1,
          user_name: 'Frank',
          content: '午餐时排队等待时间太长了。',
          reply: '我们会尽快改进排队系统，感谢反馈。'
        },
        {
          complaint_id: 2,
          user_name: 'Grace',
          content: '食堂内部分区域卫生条件需要提高。',
          reply: '已经通知清洁团队进行深度清理，感谢您的建议。'
        },
      ],

    };

  },
  methods: {
    async submitComment() {
      try {
        await this.$axios.post('/community/comments/test', this.comment);
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
      // 根据活动标签返回相应的消息列表
      switch (this.activeTab) {
        case 'comments':
          return this.communityMessages;
        case 'likes':
          return this.likes;
        case 'complaints':
          return this.complaints;
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