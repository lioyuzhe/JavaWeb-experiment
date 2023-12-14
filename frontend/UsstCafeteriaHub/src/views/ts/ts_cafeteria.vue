te<template>
  <div class="main-container">
    <!-- 侧边栏 -->
    <el-menu class="sidebar" style="position: fixed; top: 140px; width:120px; height: calc(100% - 60px); z-index: 1000;">
      <el-menu-item index="1" @click="scrollToComponent('introduction')">食堂介绍</el-menu-item>
      <el-menu-item index="2" @click="scrollToComponent('dish')">食堂菜品</el-menu-item>
      <el-menu-item index="3" @click="scrollToComponent('vote')">投票调查</el-menu-item>
      <el-menu-item index="4" @click="scrollToComponent('complaint')">投诉食堂</el-menu-item>
    </el-menu>

    <div class="content" style="width: 100%;">
      <!-- 顶部栏 -->
      <div class="top-container" style="position: fixed; top: 70px; margin-left: 120px; width: 100%; z-index: 1000;">
      <!-- 食堂搜索框 -->
        <el-input
            class="cafeteria-search"
            placeholder="搜索食堂"
            prefix-icon="el-icon-search">
        </el-input>
        <!-- 食堂选择列表 -->
        <div class="cafeteria-select">
          <el-button style="width: 100px;">一食堂</el-button>
          <el-button style="width: 100px;">二食堂</el-button>
          <el-button style="width: 100px;">三食堂</el-button>
          <!-- 更多按钮 -->
          <el-button class="more-cafeterias" icon="el-icon-arrow-right"></el-button>
        </div>
        <!-- 菜品搜索框 -->
        <el-input
            class="dish-search"
            placeholder="搜索菜品"
            prefix-icon="el-icon-search">
        </el-input>
      </div>
      <!-- 内容区域 -->
      <div class="content-area" style="margin-top: 50px; margin-left: 150px;">
        <ts-cafeteria-content ref="cafeteriaContent"></ts-cafeteria-content>
      </div>
    </div>
  </div>
</template>

<script>
import TsCafeteriaContent from './ts_cafeteria_content.vue';
export default {
  name: 'TsCafeteria',
  data() {
    return {
      isFixed: false,
      offsetTop: 0, // 父组件底部到页面顶部的距离
    };
  },
  components: {
    TsCafeteriaContent
  },
  mounted() {
    this.offsetTop = this.$el.querySelector('.parent-component').offsetHeight;
    window.addEventListener('scroll', this.handleScroll);
  },
  methods: {
    handleScroll() {
      if (window.scrollY > this.offsetTop) {
        this.isFixed = true;
      } else {
        this.isFixed = false;
      }
    },
    scrollToComponent(componentRef) {
      this.$refs.cafeteriaContent.$refs[componentRef].$el.scrollIntoView({ behavior: 'smooth' });
    }
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  },
};
</script>

<style scoped>

.main-container {
  display: flex;
}

.top-container {
  display: flex;
  align-items: center;
  padding: 10px;
}

.cafeteria-search {
  flex: 0 1 50%;
  margin-right: 10px;
}

.cafeteria-select {
  display: flex;
  align-items: center;
}

.cafeteria-select {
  margin-right: 5px;
}

.dish-search {
  margin-left: auto; /* 自动占据所有剩余空间，将元素推到最右边 */
}

.content-area {
  flex-grow: 1;
  padding: 10px;
}
</style>
