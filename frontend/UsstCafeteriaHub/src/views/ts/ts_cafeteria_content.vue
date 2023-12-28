<template>
  <div class="ts-cafeteria-content">
<!--    <h3>当前选中的食堂:</h3>-->
<!--    <pre>{{ cafeteria }}</pre>-->
    <div class="page-header" id="introduction">
      <h2>食堂介绍</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-introduction :cafeteria="cafeteria"></ts-cafeteria-introduction>

    <div class="page-header" id="dish">
      <h2>食堂菜品</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-dish :cafeteria="cafeteria"></ts-cafeteria-dish>

    <div class="page-header" id="vote">
      <h2>投票调查</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-vote :cafeteria="cafeteria"></ts-cafeteria-vote>

    <div class="page-header" id="complaint">
      <h2>投诉食堂</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-complaint :cafeteria="cafeteria"></ts-cafeteria-complaint>

    <div class="page-header" id="announcement">
      <h2>食堂公告</h2>
      <el-divider></el-divider>
    </div>
    <ts-cafeteria-announcement :cafeteria="cafeteria"></ts-cafeteria-announcement>
  </div>
</template>

<script>
import TsCafeteriaIntroduction from './ts_cafeteria/ts_cafeteria_introduction.vue';
import TsCafeteriaDish from './ts_cafeteria/ts_cafeteria_dish.vue';
import TsCafeteriaVote from './ts_cafeteria/ts_cafeteria_vote.vue';
import TsCafeteriaComplaint from './ts_cafeteria/ts_cafeteria_complaint.vue';
import TsCafeteriaAnnouncement from './ts_cafeteria/ts_cafeteria_announcement.vue'; // 新导入的组件

export default {
  props: {
    cafeteria: Object,
  },
  watch: {
    cafeteria(newVal, oldVal) {
      // 当 cafeteria prop 改变时执行的代码
      console.log('Cafeteria changed:', newVal);
      // 这里可以加入更新组件内容的逻辑
    }
  },
  computed: {
    cafeteriaInfo() {
      // 返回处理后的食堂信息
      return this.cafeteria ? someProcessingFunction(this.cafeteria) : '未选择食堂';
    }
  },
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
    console.log('Initial cafeteria prop:', this.cafeteria);
  },
  components: {
    TsCafeteriaIntroduction,
    TsCafeteriaDish,
    TsCafeteriaVote,
    TsCafeteriaComplaint,
    TsCafeteriaAnnouncement // 注册新组件
  }
};
</script>

<style>
/* 在这里添加您的CSS样式 */
.ts-cafeteria-content {
  /* 示例样式 */
}
</style>
