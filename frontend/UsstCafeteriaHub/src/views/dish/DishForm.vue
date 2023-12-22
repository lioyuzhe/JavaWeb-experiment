<template>
  <div>
    <h1>上海理工大学菜品管理</h1>
    <el-form ref="dishForm" :model="dishForm" label-width="100px">
      <!-- 食堂ID验证 -->
      <el-form-item label="食堂ID" :error="validationErrors.cafeteria_id">
        <el-input v-model="dishForm.cafeteria_id" @input="validateCafeteriaId"></el-input>
      </el-form-item>
      <!-- 食堂名验证 -->
      <el-form-item label="食堂名" :error="validationErrors.cafeteria_name">
        <el-input v-model="dishForm.cafeteria_name" @input="validateCafeteriaName"></el-input>
      </el-form-item>
      <!-- 菜品ID验证 -->
      <el-form-item label="菜品ID" :error="validationErrors.dish_id">
        <el-input v-model="dishForm.dish_id" :disabled="isEdit" @input="validateDishId"></el-input>
      </el-form-item>
      <el-form-item label="菜名" :error="validationErrors.name">
        <el-input v-model="dishForm.name" @input="validateName"></el-input>
      </el-form-item>
      <el-form-item label="价格" :error="validationErrors.price">
        <el-input v-model.number="dishForm.price" @input="validatePrice"></el-input>
      </el-form-item>
      <!-- 菜系验证 -->
      <el-form-item label="菜系" :error="validationErrors.cuisine">
        <el-input v-model="dishForm.cuisine" @input="validateCuisine"></el-input>
      </el-form-item>
      <el-form-item label="图片">
        <input type="file" @change="handleImageChange"/>
        <div v-if="dishForm.image_url">
          <img :src="dishForm.image_url" alt="菜品预览" class="image-preview"/>
          <el-button @click="deleteImage">删除图片</el-button>
        </div>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="dishForm.status">
          <el-option label="普通菜品" value="0"></el-option>
          <el-option label="最新推荐菜品" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否促销">
        <el-switch v-model="hasPromotion" @change="handlePromotionSwitch"></el-switch>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveDish">保存菜品</el-button>
        <el-button @click="resetForm('dishForm')">重置菜品表单</el-button>
      </el-form-item>
    </el-form>
    <!-- 当 hasPromotion 为 true 时显示促销表单 -->
    <div v-if="hasPromotion">
      <el-form ref="promotionForm" :model="promotionForm" label-width="120px">
        <el-form-item label="促销ID">
          <el-input v-model="promotionForm.promotion_id" disabled></el-input>
        </el-form-item>
        <el-form-item label="食堂ID">
          <el-input v-model="promotionForm.cafeteria_id"></el-input>
        </el-form-item>
        <el-form-item label="食堂名称">
          <el-input v-model="promotionForm.cafeteria_name"></el-input>
        </el-form-item>
        <el-form-item label="促销价格" :error="promotionValidationErrors.promoPrice">
          <el-input v-model.number="promotionForm.promoPrice" @input="validatePromotionPrice"></el-input>
        </el-form-item>
        <el-form-item label="菜品ID" :error="promotionValidationErrors.dish_id">
          <el-input v-model="promotionForm.dish_id" @input="validateDishId"></el-input>
        </el-form-item>
        <el-form-item label="促销菜品名称" :error="promotionValidationErrors.dish_name">
          <el-input v-model="promotionForm.dish_name" @input="validatePromotionDishName"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" :error="promotionValidationErrors.start_time">
          <el-date-picker
              v-model="promotionForm.start_time"
              type="datetime"
              placeholder="选择开始时间"
              @input="validatePromotionStartTime">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="结束时间" :error="promotionValidationErrors.end_time">
          <el-date-picker
              v-model="promotionForm.end_time"
              type="datetime"
              placeholder="选择结束时间"
              @input="validatePromotionEndTime">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="描述信息">
          <el-input type="textarea" v-model="promotionForm.description"></el-input>
        </el-form-item>
        <el-form-item label="图片URL">
          <el-input v-model="promotionForm.image_url"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="promotionForm.status">
            <el-option label="未开始" value="0"></el-option>
            <el-option label="进行中" value="1"></el-option>
            <el-option label="已结束" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="savePromotion">保存促销信息</el-button>
          <el-button @click="resetPromotionForm">重置促销表单</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    existingDish: Object
  },
  data() {
    return {
      dishForm: {
        dish_id: '',
        cafeteria_id: '',
        cafeteria_name: '',
        name: '',
        price: 0,
        cuisine: '',
        image_url: '',
        status: '0'
      },
      promotionForm: {
        promotion_id: '',
        cafeteria_id: '',
        cafeteria_name: '',
        dish_id: '',
        dish_name: '',
        start_time: '',
        end_time: '',
        description: '',
        image_url: '',
        status: '0'
      },
      hasPromotion: false, // 是否有促销活动
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      dish: {
        dishId: '', // 菜品ID
        cafeteriaId: '', // 食堂ID
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
      editMode: false,
      validationErrors: {
        name: '',
        price: '',
        cafeteria_id: '',
        cafeteria_name: '',
        dish_id: '',
        cuisine: ''
      }
    };
  },
  created() {
    if (this.existingDish) {
      this.dish = {...this.existingDish};
      this.editMode = true;
    }
  },
  methods: {
    saveDish() {
      // 提交前再次验证所有字段
      this.validateName();
      this.validatePrice();
      this.validateCafeteriaId();
      this.validateCafeteriaName();
      this.validateDishId();
      this.validateCuisine();
      // 如果有错误，则不提交表单
      if (Object.values(this.validationErrors).some(error => error)) {
        return;
      }
      // 发送保存菜品信息的请求
      console.log('Saving dish:', this.dishForm);
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.validationErrors = {
        name: '',
        price: '',
        cafeteria_id: '',
        cafeteria_name: '',
        dish_id: '',
        cuisine: ''
      };
    },
    onSubmit() {
      console.log('submit!');
    },
    savePromotion() {
      // 在提交前验证所有促销字段
      this.validatePromotionPrice();
      this.validateDishId();
      this.validatePromotionDishName();
      this.validatePromotionStartTime();
      this.validatePromotionEndTime();
      // 省略提交逻辑
    },
    resetPromotionForm() {
      this.$refs.promotionForm.resetFields();
      this.promotionValidationErrors = {promoPrice: '', dish_id: '', /* 省略其他字段 */};
    },
    validateCafeteriaId() {
      this.validationErrors.cafeteria_id = this.dishForm.cafeteria_id ? '' : '食堂ID不能为空';
    },
    validateCafeteriaName() {
      this.validationErrors.cafeteria_name = this.dishForm.cafeteria_name ? '' : '食堂名不能为空';
    },
    validateDishId() {
      this.validationErrors.dish_id = this.dishForm.dish_id ? '' : '菜品ID不能为空';
    },
    validateCuisine() {
      this.validationErrors.cuisine = this.dishForm.cuisine ? '' : '菜系不能为空';
    },
    validateName() {
      if (!this.dishForm.name) {
        this.validationErrors.name = '菜名不能为空';
      } else {
        this.validationErrors.name = '';
      }
    },
    validatePrice() {
      if (this.dishForm.price <= 0) {
        this.validationErrors.price = '价格必须大于0';
      } else {
        this.validationErrors.price = '';
      }
    },
    validatePromotionPrice() {
      if (this.promotionForm.promoPrice <= 0 || this.promotionForm.promoPrice >= this.dishForm.price) {
        this.promotionValidationErrors.promoPrice = '促销价格必须小于普通价格且大于0';
      } else {
        this.promotionValidationErrors.promoPrice = '';
      }
    },
    // 处理促销开关变化
    handlePromotionSwitch(value) {
      console.log("促销开关状态变化，当前状态：", value);
      // 这里可以添加更多处理逻辑
    },
    handleImageChange(e) {
      const file = e.target.files[0];
      this.dishForm.image_url = URL.createObjectURL(file);
    },
    deleteImage() {
      this.dishForm.image_url = '';
    },
    validatePromotionDishName() {
      if (!this.promotionForm.dish_name) {
        this.promotionValidationErrors.dish_name = '促销菜品名称不能为空';
      } else {
        this.promotionValidationErrors.dish_name = '';
      }
    },
    validatePromotionStartTime() {
      if (!this.promotionForm.start_time) {
        this.promotionValidationErrors.start_time = '开始时间不能为空';
      } else {
        this.promotionValidationErrors.start_time = '';
      }
    },
    validatePromotionEndTime() {
      if (!this.promotionForm.end_time) {
        this.promotionValidationErrors.end_time = '结束时间不能为空';
      } else {
        this.promotionValidationErrors.end_time = '';
      }
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
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
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