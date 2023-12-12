<template>
  <div class="dish-container">
    <el-card class="dish-item" v-for="dish in dishes" :key="dish.id" @click.native="viewDetails(dish)">
      <img :src="dish.imageUrl" :alt="dish.name" class="dish-image" @click="viewDetails(dish)" />
      <div slot="header" class="clearfix">
        <span class="dish-name" @click="viewDetails(dish)">{{ dish.name }}</span>
      </div>
    </el-card>
    <!-- 弹窗组件 -->
    <el-dialog :visible.sync="dialogVisible">
      <h2>{{ selectedDish.name }}</h2>
      <img :src="selectedDish.imageUrl" alt="Dish Image" class="dialog-image" />

      <div class="dish-details">
        <p>菜品详情信息：{{ selectedDish.details }}</p>
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
        <el-button @click="test1">{{this.test}}</el-button>
      </div>



    </el-dialog>




  </div>

</template>

<script>


export default {
  name: 'TsCafeteriaDish',
  data() {
    return {
      dishes: [
        {"id": 1, "name": "红烧肉", "imageUrl": "/ts_images/avatar.png"},
        {"id": 2, "name": "清蒸鱼", "imageUrl": "/ts_images/avatar.png"},
        {"id": 3, "name": "宫保鸡丁", "imageUrl": "/ts_images/avatar.png"},
        {"id": 4, "name": "鱼香茄子", "imageUrl": "/ts_images/avatar.png"},
        {"id": 5, "name": "麻婆豆腐", "imageUrl": "/ts_images/avatar.png"},
        {"id": 6, "name": "青椒肉丝", "imageUrl": "/ts_images/avatar.png"},
        {"id": 7, "name": "土豆丝", "imageUrl": "/ts_images/avatar.png"},
        {"id": 8, "name": "糖醋排骨", "imageUrl": "/ts_images/avatar.png"},
        {"id": 9, "name": "拍黄瓜", "imageUrl": "/ts_images/avatar.png"},
        {"id": 10, "name": "番茄炒蛋", "imageUrl": "/ts_images/avatar.png"},
        {"id": 11, "name": "西红柿鸡蛋汤", "imageUrl": "/ts_images/avatar.png"},
        {"id": 12, "name": "紫菜蛋花汤", "imageUrl": "/ts_images/avatar.png"},
        {"id": 13, "name": "凉拌木耳", "imageUrl": "/ts_images/avatar.png"},
        {"id": 14, "name": "蒜泥白肉", "imageUrl": "/ts_images/avatar.png"},
        {"id": 15, "name": "口水鸡", "imageUrl": "/ts_images/avatar.png"},
        {"id": 16, "name": "辣子鸡", "imageUrl": "/ts_images/avatar.png"},
        {"id": 17, "name": "水煮牛肉", "imageUrl": "/ts_images/avatar.png"},
        {"id": 18, "name": "炒河粉", "imageUrl": "/ts_images/avatar.png"},
        {"id": 19, "name": "牛肉面", "imageUrl": "/ts_images/avatar.png"},
        {"id": 20, "name": "酸辣土豆丝", "imageUrl": "/ts_images/avatar.png"}
        // ... 更多菜品
      ],
      dialogVisible: false, // 控制弹窗显示
      selectedDish: {}, // 存储被选中的菜品信息
      rating: 0, // 评分
      review: '', // 评价内容

      test:'',
    };
  },
  methods: {
    viewDetails(dish) {
      this.selectedDish = dish; // 设置被选中的菜品
      this.dialogVisible = true; // 显示弹窗
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
    test1(){
      this.$request.get('/test/test',).then(res => {
        console.log("请求到达");
        if (res.code === 200) {
          this.$message.success('测试成功');
          this.test=res.data;
        } else {
          this.$message.error(res.message);
        }
      })
    },
  }
};
</script>

<style scoped>

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
