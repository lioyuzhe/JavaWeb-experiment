<template>
  <div class="ts-cafeteria-content">
    <div class="page-header">
      <h2>食堂简介</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-introduction ref="introduction"></ts-cafeteria-introduction>
    <div class="page-header">
      <h2>食堂菜品</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-dish ref="dish"></ts-cafeteria-dish>
    <div class="page-header">
      <h2>投票调查</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-vote ref="vote"></ts-cafeteria-vote>
    <div class="page-header">
      <h2>投诉食堂</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-complaint ref="complaint"></ts-cafeteria-complaint>
  </div>
</template>

<script>
import TsCafeteriaIntroduction from './ts_cafeteria/ts_cafeteria_introduction.vue';
import TsCafeteriaDish from './ts_cafeteria/ts_cafeteria_dish.vue';
import TsCafeteriaVote from './ts_cafeteria/ts_cafeteria_vote.vue';
import TsCafeteriaComplaint from './ts_cafeteria/ts_cafeteria_complaint.vue';

export default {
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
