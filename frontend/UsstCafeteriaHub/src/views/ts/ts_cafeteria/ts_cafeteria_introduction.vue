<template>
  <div class="cafeteria-intro" v-if="cafeteria">
    <h2 class="cafeteria-title">{{ cafeteria.name }}</h2>
    <p v-for="paragraph in cafeteria.description" :key="paragraph">{{ paragraph }}</p>
  </div>
</template>

<script>
export default {
  name: 'TsCafeteriaIntroduction',
  data() {
    return {
      cafeteria: null,
    };
  },
  created() {
    this.fetchCafeteriaData();
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
