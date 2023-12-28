<template>
  <div class="main-container">
    <!-- 侧边栏 -->
    <el-menu class="sidebar" style="position: fixed; top: 140px; width:120px; height: calc(100% - 60px); z-index: 1000;">
      <el-menu-item index="introduction" @click="scrollToSection('introduction')">食堂介绍</el-menu-item>
      <el-menu-item index="dish" @click="scrollToSection('dish')">食堂菜品</el-menu-item>
      <el-menu-item index="vote" @click="scrollToSection('vote')">投票调查</el-menu-item>
      <el-menu-item index="complaint" @click="scrollToSection('complaint')">投诉食堂</el-menu-item>
      <el-menu-item index="announcement" @click="scrollToSection('announcement')">食堂公告</el-menu-item>
    </el-menu>
    <div class="content" style="width: 100%;">
      <!-- 顶部栏 -->
      <div class="top-container" style="position: fixed; top: 70px; margin-left: 120px;">
        <!-- 食堂选择顶部栏 -->
        <el-menu class="cafeteria-select" mode="horizontal" style="left: 10px">

          <!-- 使用 v-for 遍历食堂数据 -->
          <el-menu-item
              v-for="cafeteria in cafeterias"
              :key="cafeteria.cafeteriaId"
              :index="cafeteria.cafeteriaId"
              @click="selectCafeteria(cafeteria)"
              style="width: 100px;"
          >
            {{ cafeteria.name }} <!-- 显示食堂名称 -->
          </el-menu-item>
        </el-menu>
        <!-- 食堂搜索框 -->
        <div class="right-container">
          <!-- 食堂搜索框 -->
          <!-- 使用表单来包裹输入框和按钮 -->
          <form @submit.prevent="searchCafeteria" class="searchCa">
            <!-- 绑定 v-model 到 cafeteriaSearch -->
            <el-input class="cafeteria-search" v-model="cafeteriaSearch" placeholder="请输入食堂" prefix-icon="el-icon-search"></el-input>
            <el-button type="primary" icon="el-icon-search" @click="searchCafeteria">搜索食堂</el-button>
          </form>
        </div>
      </div>
      <!-- 内容区域 -->
      <div class="content-area" style="margin-top: 50px; margin-left: 150px;">
        <ts-cafeteria-content ref="cafeteriaContent" :cafeteria="selectedCafeteria"></ts-cafeteria-content>
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
      cafeterias: [], // 存储从后端接收的食堂数据
      selectedCafeteria: null, // 添加选中的食堂信息
      cafeteriaSearch: '', // 用于绑定搜索框的输入值
    };
  },
  components: {
    TsCafeteriaContent
  },
  mounted() {
    // 在组件挂载后，从后端请求食堂数据
    this.fetchCafeterias();
    // this.offsetTop = this.$el.querySelector('.parent-component').offsetHeight;
    // window.addEventListener('scroll', this.handleScroll);
  },

  methods: {
    searchCafeteria() {
      // 将搜索关键词转换为小写以实现大小写不敏感的搜索
      const searchKeyword = this.cafeteriaSearch.toLowerCase();

      // 查找第一个名称包含搜索关键词的食堂
      const foundCafeteria = this.cafeterias.find(cafeteria =>
          cafeteria.name.toLowerCase().includes(searchKeyword)
      );

      if (foundCafeteria) {
        this.selectCafeteria(foundCafeteria);
        this.$message({
          message: `已切换到${foundCafeteria.name}！`,
          type: 'success'
        });
      } else {
        this.$message({
          message: '未找到该食堂！',
          type: 'warning'
        });
      }
    },
    selectCafeteria(cafeteria) {
      this.selectedCafeteria = cafeteria;
      console.log('Selected Cafeteria:', this.selectedCafeteria);
      this.$forceUpdate();
      this.$message({
        message: `已切换到${cafeteria.name}`,
        type: 'success'
      });
    },
    handleScrollToSection() {
      const hash = window.location.hash;
      if (hash) {
        this.scrollToSection(hash.substring(1)); // 去掉 # 符号
      }
    },
    handleScroll() {
      if (window.scrollY > this.offsetTop) {
        this.isFixed = true;
      } else {
        this.isFixed = false;
      }
    },
    scrollToSection(sectionId) {
      // 获取选中板块的标题栏位置
      const sectionElement = document.getElementById(sectionId);
      if (sectionElement) {
        // 滚动到标题栏位置
        sectionElement.scrollIntoView({ behavior: 'smooth' });
      }
    },
    fetchCafeterias() {
      // 发起后端请求，获取食堂数据
      this.$request.get('/cafeterias/actions/getCafeteria') // 替换为实际的后端接口地址
          .then((response) => {
            // 将从后端接收的数据存储到 cafeterias 中
            this.cafeterias = response.data;
            // console.log('Cafeteria Data:', this.cafeterias);
          })
          .catch((error) => {
            console.error('Failed to fetch cafeterias', error);
          });
    },
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  },
};
</script>

<style scoped>
.searchCa {
  display: flex;
  justify-content: flex-end; /* 将内容推向右边 */
  align-items: center; /* 垂直居中对齐 */
  /* 您可以根据需要添加更多样式，比如外边距、内边距等 */
}

.main-container {
  display: flex;
}

.top-container {
  display: flex;
  align-items: center;
  padding: 10px;
  z-index: 1000; /* 或者更高的值，确保高于其他元素 */
  position: relative; /* 或 absolute/fixed，z-index 需要定位属性配合使用 */
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

.right-container {
  display: flex;        /* 使用Flexbox */
  justify-content: flex-end; /* 组件对齐到右边 */
  align-items: center;  /* 垂直居中对齐 */
}

.cafeteria-search {
  width: 200px; /* 设置搜索框的宽度 */
  margin-right: 10px; /* 组件之间的间距 */
}

.dish-search {
  //margin-left: auto; /* 自动占据所有剩余空间，将元素推到最右边 */
}

.content-area {
  flex-grow: 1;
  padding: 10px;
}
</style>