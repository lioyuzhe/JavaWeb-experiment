<template>
  <div class="cafeteria-intro" v-if="cafeteria">
    <h2 class="cafeteria-title">{{ cafeteria.name }}</h2>
    <div class="cafeteria-description">
      <p><strong>食堂位置：</strong>{{ cafeteria.location }}</p>
      <p><strong>食堂管理员：</strong>{{ cafeteria.adminName }}</p>
      <p><strong>食堂开放时间：</strong>{{ cafeteria.openTime }}~{{ cafeteria.closeTime }}</p>
      <p><strong>食堂简介：</strong></p>
      <p v-for="paragraph in formattedDescription" :key="paragraph">{{ paragraph }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TsCafeteriaIntroduction',
  props: {
    cafeteria: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      // cafeteria: null,
    };
  },
  computed: {
    formattedDescription() {
      if (this.cafeteria && this.cafeteria.description) {
        // 按换行符分割字符串
        return this.cafeteria.description.split('\n');
      }
      return [];
    }
  },
  // 在每个子组件中
  mounted() {
    console.log('Initial cafeteria prop in subcomponent:', this.cafeteria);
  },
  created() {
    this.fetchCafeteriaData();
  },
  // 在每个子组件中
  watch: {
    cafeteria(newVal) {
      console.log('Cafeteria prop in subcomponent updated:', newVal);
      // 在这里添加处理 prop 变化的逻辑
    }
  },
  methods: {
    async fetchCafeteriaData() {
      try {
        const response = await this.$request.get('/cafeterias/actions/getCafeteria?cafeteriaId=1');
        this.cafeteria = response.data[0]; // 假设返回的是一个数组，我们取第一个食堂的信息
      } catch (error) {
        console.error('Error fetching cafeteria data:', error);
      }
    },
  },
}
</script>

<style scoped>
.cafeteria-intro {
  padding: 30px; /* 增加内边距 */
  background-color: #f8f8f8;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  font-family: 'Arial', sans-serif;
}

.cafeteria-title {
  color: #2c3e50;
  text-align: center;
  margin-bottom: 20px;
}

p {
  text-align: justify;
  line-height: 1.6;
  margin-bottom: 10px;
  text-indent: 2em; /* 首行缩进 */
}
</style>
