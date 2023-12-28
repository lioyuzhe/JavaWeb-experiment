<template>
  <div class="home-container">
    <el-row :gutter="20" justify="center">
      <!-- 左侧内容 -->
      <el-col :span="16" :offset="2">
        <!-- 动态轮播部分 -->
<!--        <el-card class="box-card">-->
<!--          <el-carousel :interval="4000" type="card" height="200px">-->
<!--            <el-carousel-item v-for="promo in promotions" :key="promo.promotion_id">-->
<!--              <h3>{{ promo.dish_name }}</h3>-->
<!--              <p>{{ promo.description }}</p>-->
<!--            </el-carousel-item>-->
<!--          </el-carousel>-->
<!--        </el-card>-->

        <el-card class="box-card">
          <el-carousel :interval="4000" type="card" height="200px">
            <el-carousel-item v-for="dish in dishes" :key="dish.dishId">
              <img :src="dish.imageUrl" class="dish-image" alt="Dish Image">
              <h3>{{ dish.name }}</h3>
              <p>所属食堂：{{ dish.cafeteriaName }}</p>
            </el-carousel-item>
          </el-carousel>
        </el-card>

        <!-- 功能入口部分 -->
        <el-row :gutter="20">
          <el-col :span="8" v-for="entry in featureEntrances" :key="entry.id">
            <router-link :to="getLink(entry.id)">
              <el-card class="box-card">
                <img :src="entry.imageUrl" class="entrance-image" alt="Feature">
                <div>
                  <h3 style="color:#000000; font-size: 20px; font-weight: bolder; text-align: center;">{{ entry.title }}</h3>
                  <p style="color:#009966; font-size: 16px; text-align: center;">{{ entry.description }}</p>
                </div>
              </el-card>
            </router-link>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <!-- 最新促销 -->
          <el-col :span="8">
            <el-card class="box-card" >
              <img src="https://cdn.jsdelivr.net/gh/Yu-Ring/obsidian/20231228184859.png" class="entrance-image">
              <div class="feature-info">
                <h3 @click="showPromoDialog('promo')">最新促销</h3>
                <p>探索我们的特价菜品</p>
              </div>
            </el-card>
          </el-col>
          <!-- 食堂排名 -->
          <!-- 食堂排名 -->
          <el-col :span="8">
            <el-card class="box-card" >
              <img src="https://cdn.jsdelivr.net/gh/Yu-Ring/obsidian/20231228190211.png" class="entrance-image">
              <div class="feature-info">
                <h3 @click="showCanteenRankDialog" >食堂排名</h3>
                <p>查看最新食堂排名</p>
              </div>
            </el-card>
          </el-col>
          <!-- 菜品排名 -->
          <el-col :span="8">
            <el-card class="box-card" @click="showDialog('dishRank')">
              <img src="https://cdn.jsdelivr.net/gh/Yu-Ring/obsidian/20231228190159.png" class="entrance-image">
              <div class="feature-info">
                <h3>菜品排名</h3>
                <p>最新高评价菜品排名</p>
              </div>
            </el-card>
          </el-col>
        </el-row>


        <!-- 促销菜品对话框 -->
        <el-dialog title="促销菜品" :visible.sync="promoDialogVisible">
          <el-table :data="promoDishes">
            <el-table-column prop="name" label="菜品名称"></el-table-column>
            <el-table-column prop="cafeteriaName" label="所属食堂"></el-table-column>
            <el-table-column prop="price" label="价格"></el-table-column>
            <el-table-column prop="cuisine" label="菜系"></el-table-column>
          </el-table>
        </el-dialog>
        <!-- 食堂排名对话框 -->
        <el-dialog title="食堂排名" :visible.sync="canteenRankDialogVisible">
          <el-table :data="canteenRanks">
            <el-table-column prop="cafeteriaName" label="食堂名称"></el-table-column>
            <el-table-column prop="totalScore" label="总分"></el-table-column>
            <el-table-column prop="averageScore" label="均分"></el-table-column>
            <el-table-column prop="ranking" label="排名"></el-table-column>
          </el-table>
        </el-dialog>


      </el-col>

      <!-- 右侧内容 -->
      <el-col :span="5" >
        <!-- 功能图标部分 -->
        <el-card class="box-card">
          <div class="icon-tray">
            <el-badge :value="commentsCount" class="icon">
              <el-button icon="el-icon-message" circle @click="handleIconClick('comments')"></el-button>
            </el-badge>
            <el-badge :value="likesCount" class="icon">
              <el-button icon="el-icon-star-off" circle @click="handleIconClick('likes')"></el-button>
            </el-badge>
            <el-badge :value="complaintsCount" class="icon">
              <el-button icon="el-icon-warning" circle @click="handleIconClick('complaints')"></el-button>
            </el-badge>
          </div>
        </el-card>

        <!-- 消息栏 -->
        <el-card class="box-card">
          <el-scrollbar class="message-list">
            <div v-for="item in activeMessages" :key="item.id" class="message-item" v-html="generateMessageContent(item)">
            </div>
          </el-scrollbar>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'ts_home',
  data() {
    return {
      dishes: [], // 用于存储菜品数据
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
      //   {
      //     id: 'promo',
      //     title: '最新促销',
      //     description: '探索我们的特价菜品',
		  // imageUrl: 'https://cdn.jsdelivr.net/gh/Yu-Ring/obsidian/20231228184859.png'
      //   },
        {
          id: 'vote',
          title: '投票调查',
          description: '参与我们的最新调查',
		  imageUrl: 'https://cdn.jsdelivr.net/gh/Yu-Ring/obsidian/20231228185236.png'
        },
        // 新增加的功能入口数据
        {
          id: 'recommendation',
          title: '食堂推荐',
          description: '最新食堂推荐菜品',
		  imageUrl: 'https://cdn.jsdelivr.net/gh/Yu-Ring/obsidian/20231228185614.png'
        },
        {
          id: 'communityTopic',
          title: '社区话题',
          description: '社区热门话题，参与讨论',
		  imageUrl: 'https://cdn.jsdelivr.net/gh/Yu-Ring/obsidian/20231228185853.png'
        },
      //   {
      //     id: 'canteenRank',
      //     title: '食堂排名',
      //     description: '查看最新食堂排名',
		  // imageUrl: 'https://cdn.jsdelivr.net/gh/Yu-Ring/obsidian/20231228190211.png'
      //   },
      //   {
      //     id: 'dishRank',
      //     title: '菜品排名',
      //     description: '最新高评价菜品排名',
		  // imageUrl: 'https://cdn.jsdelivr.net/gh/Yu-Ring/obsidian/20231228190159.png'
      //   }
      ],
      promotions: [
      ],
      communityMessages: [

      ],
      likes: [

      ],
      complaints: [

      ],

      promoDialogVisible: false,
      promoDishes: [],// 用于存储促销菜品数据
      canteenRankDialogVisible: false,
      canteenRanks: [] // 用于存储食堂排名数据
    };

  },
  created() {
    this.fetchData();
    this.fetchDishes(); // 获取菜品数据
    // 定时器，每隔一定时间请求最新数据
    setInterval(() => {
      this.fetchLatestComments();
      // this.fetchLatestComplaintReplies();
    }, 100000); // 比如每10秒更新一次
  },
  methods: {
    showCanteenRankDialog() {

      this.fetchCanteenRanks();
    },
    async fetchCanteenRanks() {
      try {
        const response = await this.$request.get('/cafeteriaRanks/actions/getCafeteriaRank');
        if (response.code ===200) {
          this.canteenRanks = response.data;
          this.canteenRankDialogVisible = true;
          this.$message.success("获取成功")
        }
      } catch (error) {
        console.error('Error fetching canteen ranks:', error);
        // 这里可以根据需要添加错误处理逻辑
      }
    },
    showPromoDialog() {
      this.promoDialogVisible = true;
      this.fetchPromoDishes();
    },
    async fetchPromoDishes() {
      try {
        const response = await this.$request.get('/dishes/actions/getTop5RecommendDish');
        if (response && response.data) {
          this.promoDishes = response.data;
        }
      } catch (error) {
        console.error('Error fetching promo dishes:', error);
        // 这里可以根据需要添加错误处理逻辑
      }
    },
    async fetchDishes() {
      try {
        const response = await this.$request.get('/dishes/actions/getDishes');
        if (response && response.data) {
          this.dishes = response.data;
        }
      } catch (error) {
        console.error('Error fetching dishes:', error);
      }
    },
    getMessageType(item) {
      // 根据消息的字段判断消息类型
      if (item.cafeteriaName && item.content && item.reply) {
        return 'complaint';
      } else {
        return 'normal';
      }
    },

    generateMessageContent(item) {
      if (this.getMessageType(item) === 'complaint') {
        return `<strong>${item.cafeteriaName}</strong>回复了你的投诉"<strong>${item.content}</strong>": <strong>${item.reply}</strong>`;
      } else {
        return `${item.userName}：${item.content}`;
      }
    },



    async fetchData() {
      await this.fetchLatestComments();
      // await this.fetchLatestComplaintReplies();
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
          // this.fetchLatestComplaintReplies();
          return this.latestComplaintReplies;
        default:
          return [];
      }
    }
  },

}
</script>

<style scoped>
.box-card {
  margin-bottom: 40px;
}

.icon-tray .icon {
  text-align: center;
  margin: 12px;
}

.entrance-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.message-list {
  height: calc(100vh - 220px);
  overflow-y: auto;
}

@media (max-width: 768px) {
  .icon-tray .icon {
    width: 33%;
    margin-bottom: 10px;
  }
}
.dish-image {
  width: 60%;
  height: 70%;
  object-fit: cover;
}
.feature-info {
  text-align: center;
}
.entrance-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}
.feature-info {
  text-align: center;
}
.entrance-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}
</style>
