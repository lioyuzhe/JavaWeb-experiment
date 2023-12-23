<template>
  <div class="dish-container">
    <!-- 搜索框容器 -->
    <div class="dish-top-container">
      <!-- 绑定 v-model 到 dishSearch -->
      <!-- 使用表单来包裹输入框和按钮 -->
      <form @submit.prevent="searchDish">
        <el-input class="dish-search" v-model="dishSearch" placeholder="请输入菜品" prefix-icon="el-icon-search"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="searchDish">搜索菜品</el-button>
      </form>
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
            <div class="upload-item" @click="addImage" v-if="!imagePreviewUrl">
              <i class="el-icon-plus"></i>
              <span>添加图片</span>
            </div>
            <!-- 图片预览区域 -->
            <div v-if="imagePreviewUrl" class="image-preview" @click="addImage">
              <img :src="imagePreviewUrl" alt="Image preview" class="preview-image" />
            </div>
            <input type="file" ref="fileInput" @change="handleFileChange" hidden>
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
  // props: {
  //   user: JSON.parse(localStorage.getItem('user'))
  // },
  props: {
    cafeteria: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      dialogVisible: false, // 控制弹窗显示
      selectedDish: null, // 被选中的菜品
      dishes: [],
      rating: 0, // 评分
      review: '', // 评价内容
      imagePreviewUrl: '', // 用于存储图片预览的 URL
      user: JSON.parse(localStorage.getItem('user')),
      dishSearch: '', // 用于绑定搜索框的输入值
    };
  },
  created() {
    console.log('Component created！');
    this.fetchDishes();
    console.log('TsCafeteriaDish created with cafeteria:', this.cafeteria);
  },
  mounted() {
    console.log('Component mounted！');
    console.log('TsCafeteriaDish mounted with cafeteria:', this.cafeteria);
    if (this.cafeteria && this.cafeteria.cafeteriaId) {
      this.fetchDishes();
    }
  },
  watch: {
    cafeteria(newVal, oldVal) {
      if (newVal && newVal.cafeteriaId) {
        console.log('Cafeteria updated, fetching dishes for:', newVal);
        this.fetchDishes();
      }
    }
  },
  methods: {
    searchDish() {
      const searchKeyword = this.dishSearch.toLowerCase();

      const foundDish = this.dishes.find(dish =>
          dish.name.toLowerCase().includes(searchKeyword)
      );

      if (foundDish) {
        this.selectedDish = foundDish;
        this.dialogVisible = true; // 显示弹窗
        this.$message({
          message: `找到${foundDish.name}！`,
          type: 'success'
        });
      } else {
        this.$message({
          message: `未找到该菜品！`,
          type: 'warning'
        });
      }
    },
    viewDetails(dish) {
      this.selectedDish = dish; // 设置被选中的菜品
      this.dialogVisible = true; // 显示弹窗
    },
    async fetchDishes() {
      console.log('Fetching dishes for cafeteria:', this.cafeteria);
      // 确保有有效的食堂 ID
      if (!this.cafeteria || !this.cafeteria.cafeteriaId) {
        console.error('No cafeteria ID available for fetching dishes.');
        return;
      }

      const cafeteriaId = this.cafeteria.cafeteriaId; // 从 cafeteria prop 获取食堂 ID
      const url = `/dishes/actions/getDishByCafeteriaID?cafeteriaId=${cafeteriaId}`; // 构建请求 URL

      try {
        const response = await this.$request.get(url);
        this.dishes = response.data;
      } catch (error) {
        console.error('Error fetching dishes:', error);
      }
    },
    addImage() {
      // 触发文件输入的点击事件
      this.$refs.fileInput.click();
    },
    handleFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        // 创建一个 FileReader 对象用于读取文件
        const reader = new FileReader();
        // 当文件被读取时，设置 imagePreviewUrl 为读取的内容
        reader.onload = (e) => {
          this.imagePreviewUrl = e.target.result;
        };
        // 读取文件
        reader.readAsDataURL(file);
        // 这里还可以添加将文件上传到服务器的逻辑
      }
    },
    submitReview() {
      console.log("this.user",this.user)
      const payload = {
        userId: this.user.userId, // 从props中获取用户ID
        userName: this.user.name, // 从props中获取用户名
        dishId: this.selectedDish.dishId,
        dishName: this.selectedDish.name,
        content: this.review,
        score: this.rating,
        // 如果有图片，也应该包含在 payload 中
        // deleted: 0
      };
      console.log("payload",payload);

      // 发送数据
      this.$request.post('/dishRemarks/actions/addDishRemark', payload)
          .then(response => {
            // 处理成功响应
            this.$message({
              message: '评价提交成功！',
              type: 'success'
            });
            // 这里还可以添加更新界面的逻辑
            // 例如，清空表单，关闭弹窗，刷新列表等
          })
          .catch(error => {
            // 处理错误

            let message = '提交失败，请稍后重试。';
            // 如果错误中有更具体的信息，可以使用它
            if (error.response && error.response.data) {
              message = error.response.data.message || message;
            }
            this.$message({
              message: message,
              type: 'error'
            });
            // 可以在这里添加任何其他错误处理逻辑
          });
    },

    cancelReview() {
      this.clearForm();
      this.dialogVisible = false;
    },

    clearForm() {
      this.rating = 0;
      this.review = '';
      // 清除图片预览URL或其他相关数据
      this.imagePreviewUrl = '';
    },

  }
};
</script>

<style scoped>
.preview-image {
  max-width: 100px; /* 设置图片预览的最大宽度 */
  max-height: 100px; /* 设置图片预览的最大高度 */
  margin-top: 10px; /* 为了与加号按钮对齐，可以调整上边距 */
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