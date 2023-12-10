<template>
  <div class="dish-container">
    <el-card class="dish-item" v-for="dish in dishes" :key="dish.id" @click.native="viewDetails(dish)">
      <img :src="dish.imageUrl" :alt="dish.name" class="dish-image" />
      <div slot="header" class="clearfix">
        <span class="dish-name">{{ dish.name }}</span>
      </div>
    </el-card>
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
      ]

    };
  },
  methods: {
    viewDetails(dish) {
      // 点击菜品后的逻辑，例如打开一个详细信息的模态框
      // 这里简单地打印出点击的菜品信息
      console.log('查看菜品详情', dish);
    }
  }
};
</script>

<style scoped>
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
