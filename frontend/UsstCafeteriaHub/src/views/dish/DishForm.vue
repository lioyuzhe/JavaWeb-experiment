<template>
  <div class="dish-form-container">
    <h1>上海理工大学菜品管理</h1>
    <div class="form-group">
      <label for="name">菜名:</label>
      <input id="name" v-model="dish.name" @input="validateName" placeholder="请输入菜名" :class="{'is-invalid': !validation.nameValid}" />
      <div v-if="!validation.nameValid" class="invalid-feedback">菜名不能为空</div>
    </div>
    <div class="form-group">
      <label for="price">价格:</label>
      <input id="price" v-model.number="dish.price" type="number" @input="validatePrice" placeholder="请输入价格" :class="{'is-invalid': !validation.priceValid}" />
      <div v-if="!validation.priceValid" class="invalid-feedback">价格必须大于0</div>
    </div>
    <div class="form-group">
      <label for="promoPrice">促销价格:</label>
      <input id="promoPrice" v-model.number="dish.promoPrice" type="number" @input="validatePromoPrice" placeholder="请输入促销价格" :class="{'is-invalid': !validation.promoPriceValid}" />
      <div v-if="!validation.promoPriceValid" class="invalid-feedback">促销价格必须小于普通价格且大于0</div>
    </div>
    <div class="form-group">
      <label for="cuisine">菜系:</label>
      <input id="cuisine" v-model="dish.cuisine" @input="validateCuisine" placeholder="请输入菜系" :class="{'is-invalid': !validation.cuisineValid}" />
      <div v-if="!validation.cuisineValid" class="invalid-feedback">菜系不能为空</div>
    </div>
    <div class="form-group">
      <label for="image">菜品图片:</label>
      <input id="image" type="file" @change="handleImageChange" />
      <div v-if="dish.image">
        <img :src="dish.image" alt="菜品预览" class="image-preview"/>
        <button @click="deleteImage">删除图片</button>
      </div>
    </div>
    <button class="btn btn-primary submit-btn" @click="submitForm">{{ existingDish ? '更新菜品' : '提交新菜品' }}</button>
    <button class="btn btn-secondary" @click="cancelForm">取消</button>
  </div>
</template>

<script>
export default {
  props: {
    existingDish: Object
  },
  data() {
    return {
      dish: {
        name: '',
        price: 0,
        promoPrice: 0,
        cuisine: '',
        image: '',
        cafeteria_id: null,
        status: 0,
        deleted: 0
      },
      validation: {
        nameValid: true,
        priceValid: true,
        promoPriceValid: true,
        cuisineValid: true
      },
      editMode: false
    };
  },
  methods: {
    validateName() {
      this.validation.nameValid = this.dish.name.trim().length > 0;
    },
    validatePrice() {
      this.validation.priceValid = this.dish.price > 0;
    },
    validatePromoPrice() {
      this.validation.promoPriceValid = this.dish.promoPrice > 0 && this.dish.promoPrice < this.dish.price;
    },
    validateCuisine() {
      this.validation.cuisineValid = this.dish.cuisine.trim().length > 0;
    },
    handleImageChange(e) {
      const file = e.target.files[0];
      this.dish.image = URL.createObjectURL(file);
    },
    deleteImage() {
      this.dish.image = '';
    },
    submitForm() {
      // 提交表单逻辑
      this.$emit('formSubmitted');
    },
    cancelForm() {
      // 发出表单取消事件
      this.$emit('formCanceled');
    }
  },
  created() {
    if (this.existingDish) {
      this.dish = { ...this.existingDish };
    }
  }
};
</script>
<style>
.dish-form-container {
  max-width: 500px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.form-group {
//margin-bottom: 20px;
  margin-bottom: 15px;
  width: 100%; /* 确保占满可用宽度 */
}
.form-group label {
  display: block;
  margin-bottom: 5px;
}
.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box; /* 确保边框和内边距包含在宽度内 */
}
.form-group input[type="text"], .form-group input[type="number"], .form-group input[type="file"] {
  padding: 10px;
  border-radius: 5px;
}
.form-group .is-invalid {
  border-color: #dc3545;
}
.form-group .invalid-feedback {
  color: #dc3545;
}
.btn-primary {
  background-color: #007bff;
  color: white;
}
.btn-primary:hover {
  background-color: #0056b3;
}
.image-preview {
  max-width: 100%;
  max-height: 200px;
  margin-top: 10px;
}
.submit-btn {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  background-color: #4CAF50; /* 绿色 */
  color: white;
}
.submit-btn:hover {
  background-color: #45a049;
}

.image-preview {
  /* 保持原有样式 */
}

/* 添加响应式布局，确保在不同屏幕大小下也能良好显示 */
@media screen and (max-width: 600px) {
  .dish-form-container {
    width: 100%;
    padding: 10px;
  }
}
</style>