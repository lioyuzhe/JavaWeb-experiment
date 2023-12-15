<template>
  <div class="ts-cafeteria-content">
    <div class="page-header" id="introduction">
      <h2>食堂介绍</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-introduction></ts-cafeteria-introduction>

    <div class="page-header" id="dish">
      <h2>食堂菜品</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-dish></ts-cafeteria-dish>

    <div class="page-header" id="vote">
      <h2>投票调查</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-vote></ts-cafeteria-vote>

    <div class="page-header" id="complaint">
      <h2>投诉食堂</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-complaint></ts-cafeteria-complaint>
  </div>
</template>

<script>
import TsCafeteriaIntroduction from './ts_cafeteria/ts_cafeteria_introduction.vue';
import TsCafeteriaDish from './ts_cafeteria/ts_cafeteria_dish.vue';
import TsCafeteriaVote from './ts_cafeteria/ts_cafeteria_vote.vue';
import TsCafeteriaComplaint from './ts_cafeteria/ts_cafeteria_complaint.vue';

export default {
  mounted() {
    // 检查是否有锚点
    const hash = window.location.hash;
    if (hash) {
      // 如果有锚点，滚动到锚点位置
      const element = document.querySelector(hash);
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    }
  },
  components: {
    TsCafeteriaIntroduction,
    TsCafeteriaDish,
    TsCafeteriaVote,
    TsCafeteriaComplaint
  }
};
</script>

<style>
/* 在这里添加您的CSS样式 */
.ts-cafeteria-content {
  /* 示例样式 */
}
</style>
