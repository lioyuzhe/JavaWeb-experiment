<template>
  <div class="cafeteria-announcement" v-if="notices && notices.length">
    <h2 class="announcement-title">{{ '食堂公告' }}</h2>
    <div class="announcement-list">
      <div class="announcement-item" v-for="notice in formattedNotices" :key="notice.noticeId">
        <h3>{{ notice.title }}</h3>
        <p v-for="(contentParagraph, index) in notice.content" :key="index">{{ contentParagraph }}</p>
        <p class="announcement-time">{{ formatTime(notice.createTime) }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TsCafeteriaAnnouncement',
  props: {
    cafeteria: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      notices: [],
    };
  },
  computed: {
    cafeteriaId() {
      console.log('Cafeteria ID:',  this.cafeteria.cafeteriaId);
      return this.cafeteria ? this.cafeteria.cafeteriaId : null;
    },
    formattedNotices() {
      return this.notices.map(notice => ({
        ...notice,
        content: notice.content.split('\n')
      }));
    }
  },
  methods: {
    async fetchCafeteriaNotices() {
      if (!this.cafeteria) {
        console.error('食堂ID未提供');
        return;
      }
      try {
        const cafeteriaId = this.cafeteria.cafeteriaId;
        const response = await this.$request.get(`/cafeteriaNotices/actions/getCafeteriaNoticesByCafeteriaID?id=${cafeteriaId}`);
        this.notices = response.data;
      } catch (error) {
        console.error('Error fetching cafeteria notices by ID:', error);
      }
    },
    formatTime(time) {
      return new Date(time).toLocaleString();
    }
  },
  watch: {
    cafeteria: {
      deep: true,
      handler(newVal) {
        if (newVal && newVal.cafeteriaId) {
          this.fetchCafeteriaNotices();
        }
      }
    }
  },
  mounted() {
    if (this.cafeteria && this.cafeteria.cafeteriaId) {
      this.fetchCafeteriaNotices();
    }
  }
}
</script>

<!-- 样式部分保持不变 -->

<style scoped>
.cafeteria-announcement {
  /* 保持与cafeteria-intro样式一致 */
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  font-family: 'Arial', sans-serif;
  margin-bottom: 30px;
  background: linear-gradient(
  					to bottom, 
  					rgba(3,153,253, 0.2) 0%,
  					rgba(153,243,153, 0.2) 50%,
  					rgba(0,0,0,0)
  				  );
}

.announcement-title {
  /* 保持与cafeteria-title样式一致 */
  color: #2c3e50;
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
}

.announcement-item {
  /* 保持与描述部分样式一致 */
  border-bottom: 1px solid #eaecef;
  padding-bottom: 15px;
  margin-bottom: 15px;
}

.announcement-item h3 {
  color: #34495e;
  font-size: 20px;
}

.announcement-item p {
  text-align: justify;
  line-height: 1.6;
  margin-bottom: 10px;
  text-indent: 2em;
}

.announcement-time {
  text-align: right;
  color: #7f8c8d;
  font-size: 14px;
}
</style>
