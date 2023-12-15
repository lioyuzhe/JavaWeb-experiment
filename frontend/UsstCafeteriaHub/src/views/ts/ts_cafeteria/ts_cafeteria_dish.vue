<template>
  <div class="dish-container">
    <!-- 搜索框容器 -->
    <div class="dish-top-container">
      <el-input class="dish-search" placeholder="请输入菜品" prefix-icon="el-icon-search"></el-input>
      <el-button type="primary" icon="el-icon-search" @click="searchDish">搜索菜品</el-button>
    </div>
    <!-- 菜品展示 -->
    <div class="dish-display-container">
      <el-card class="dish-item" v-for="dish in dishes" :key="dish.id" @click.native="viewDetails(dish)">
        <img :src="dish.imageUrl" :alt="dish.name" class="dish-image" @click="viewDetails(dish)" />
        <div slot="header" class="clearfix">
          <span class="dish-name" @click="viewDetails(dish)">{{ dish.name }}</span>
        </div>
      </el-card>
    </div>
    <!-- 弹窗组件 -->
    <el-dialog :visible.sync="dialogVisible" >
      <div v-if="selectedDish" class="dialog-content">
        <h2>{{ selectedDish.name }}</h2>
        <img :src="selectedDish.imageUrl" alt="Dish Image" class="dialog-image" />
        <div class="dish-details">
                  <p>菜品类别：{{ selectedDish.cuisine }}</p>
                  <p>菜品价格：{{ selectedDish.price }} 元</p>
          <p class="rating-label">菜品评分：</p>
          <el-rate v-model="rating"></el-rate> <!-- 星级评分组件 -->
          <p class="review-label">菜品评价：</p>
          <el-input
              type="textarea"
              placeholder="请输入评价"
              v-model="review"
          ></el-input>
          <div class="image-upload">
            <div class="upload-item" @click="addImage">
              <i class="el-icon-plus"></i>
              <span>添加图片</span>
            </div>
            <!-- 如果需要显示预览已添加的图片，可以在这里添加 -->
          </div>
          <el-button type="primary" @click="submitReview">发布</el-button>
          <el-button @click="cancelReview">取消</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'TsCafeteriaDish',
  data() {
    return {
      dialogVisible: false, // 控制弹窗显示
      selectedDish: null, // 被选中的菜品
      dishes: [],
      rating: 0, // 评分
      review: '', // 评价内容
    };
  },
  created() {
    this.fetchDishes();
  },
  methods: {
    viewDetails(dish) {
      this.selectedDish = dish; // 设置被选中的菜品
      this.dialogVisible = true; // 显示弹窗
    },
    async fetchDishes() {
      try {
        const response = await this.$request.get('/dishes/actions/getDishByCafeteriaID?cafeteriaId=1');
        this.dishes = response.data;
      } catch (error) {
        console.error('Error fetching dishes:', error);
      }
    },
    addImage() {
      // 实现添加图片的逻辑
    },
    submitReview() {
      // 实现提交评价的逻辑
    },
    cancelReview() {
      // 实现取消操作的逻辑
    },
  }
};
</script>

<style scoped>

.dish-top-container {
  display: flex;        /* 使用Flexbox */
  justify-content: flex-end; /* 组件对齐到右边 */
  align-items: center;  /* 垂直居中对齐 */
}

.dish-search {
  width: 200px; /* 设置搜索框的宽度 */
  margin-right: 10px; /* 组件之间的间距 */
}

/* 特定段落的底部外边距 */
.rating-label,
.review-label {
  margin-bottom: 10px;
}

.dialog-image {
  max-width: 50%;
  height: auto;
}

.image-upload {
  display: flex;
  justify-content: start;
  align-items: center;
  padding: 10px 0;
}

.upload-item {
  border: 2px dashed #ccc;
  border-radius: 10px;
  text-align: center;
  padding: 20px;
  cursor: pointer;
}
.upload-item i {
  font-size: 24px;
  margin-bottom: 10px;
}
.upload-item span {
  display: block;
  color: #666;
}

.dish-container {
  flex-direction: column;
}

.dish-display-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  padding: 10px;
}

.dish-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px;
}

.dish-image {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  border: 1px solid #ddd;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.dish-name {
  margin-top: 8px;
  font-size: 16px;
  color: #333;
}
</style>