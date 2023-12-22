<template>
  <div class="table-container">
    <h1>上海理工大学菜品管理</h1>
    <button @click="showAddDishForm">新增菜品</button>
    <button @click="deleteSelectedDishes">批量删除</button>
    <el-table
        :data="tableData"
        border
        style="width: 100%"
        :scroll-x="true"
        row-class-name="table-row-center"
        class="centered-table"
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column fixed prop="name" label="菜品" width="150"></el-table-column>
      <el-table-column prop="cafeteria_name" label="食堂名" width="180"></el-table-column>
      <el-table-column prop="description" label="菜品描述"></el-table-column>
      <el-table-column prop="price" label="价格" width="100"></el-table-column>
      <el-table-column prop="promoPrice" label="促销价格" width="100"></el-table-column>
      <el-table-column prop="cuisine" label="菜系" width="100"></el-table-column>
      <el-table-column prop="image_url" label="图片" width="180">
        <template slot-scope="scope">
          <img :src="scope.row.image_url" alt="菜品图片" style="width: 50px; height: 50px;"/>
        </template>
      </el-table-column>
      <el-table-column prop="start_time" label="促销开始时间" width="180"></el-table-column>
      <el-table-column prop="end_time" label="促销结束时间" width="180"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.status === '0' ? '普通菜品' : '推荐菜品' }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="180">
        <template slot-scope="scope">
          <el-button @click="row_editDish(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="deleteDish(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹出式对话框表单 -->
    <el-dialog :visible.sync="dialogVisible" title="新增菜品" width="50%">
      <el-form ref="dishForm" :model="dishForm" label-width="100px">
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveDish">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [
        {
          cafeteria_name: '食堂A',
          name: '红烧肉',
          description: '经典川菜，鲜香浓郁',
          price: '35',
          promoPrice: '30', // 促销价格
          cuisine: '川菜',
          image_url: 'link-to-red-braised-pork-image', // 图片链接
          start_time: '2023-01-01',
          end_time: '2023-01-10',
          status: '0' // 状态
        },
        {
          cafeteria_name: '食堂B',
          name: '宫保鸡丁',
          description: '酸甜可口，口感丰富',
          price: '25',
          promoPrice: '20',
          cuisine: '川菜',
          image_url: 'link-to-kung-pao-chicken-image',
          start_time: '2023-02-01',
          end_time: '2023-02-15',
          status: '1'
        },
        {
          cafeteria_name: '食堂C',
          name: '清蒸鲈鱼',
          description: '鱼肉细嫩，味道鲜美',
          price: '45',
          promoPrice: '',
          cuisine: '江浙菜',
          image_url: 'link-to-steamed-bass-image',
          start_time: '',
          end_time: '',
          status: '0'
        },
        {
          cafeteria_name: '食堂D',
          name: '番茄炒蛋',
          description: '家常味道，老少皆宜',
          price: '15',
          promoPrice: '12',
          cuisine: '家常菜',
          image_url: 'link-to-tomato-and-egg-image',
          start_time: '2023-03-10',
          end_time: '2023-03-20',
          status: '1'
        },
        {
          cafeteria_name: '食堂E',
          name: '酸辣土豆丝',
          description: '酸辣爽口，开胃佳肴',
          price: '18',
          promoPrice: '',
          cuisine: '湘菜',
          image_url: 'link-to-spicy-and-sour-potato-image',
          start_time: '',
          end_time: '',
          status: '0'
        }
        // 您可以根据需要继续添加更多菜品数据
      ],
      dishes: [
        { dish_id: 1, name: "麻辣香锅", price: 10 },
        { dish_id: 2, name: "麻婆豆腐", price: 15 },
      ], // 存储所有菜品
      showForm: false, // 控制显示DishForm
      editableDish: null, // 存储要编辑的菜品
      dialogVisible: false, // 控制对话框的显示和隐藏
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
      hasPromotion: false,// 是否有促销活动
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
  methods: {
    row_editDish(row) {
      this.editableDish = row;
      this.showForm = true;
      console.log('编辑操作', row);
      // 重定向到编辑页面，并传递当前菜品的信息
      this.$router.push({ name: 'DishEdit', params: {dishId: row.dish_id} });
    },
    deleteDish(row) {
      // 发送删除单个菜品的请求到后端
      this.$http.delete(`http://localhost:9090/dishes/actions/deleteDish${row.dish_id}`)
          .then(response => {
            console.log('菜品删除成功', response);
            // 从本地数据中移除这个菜品
            const index = this.tableData.findIndex(dish => dish.dish_id === row.dish_id);
            if (index !== -1) {
              this.tableData.splice(index, 1);
            }
          })
          .catch(error => {
            console.error('菜品删除失败', error);
            // 这里可以添加错误处理逻辑
          });
    },
    deleteSelectedDishes() {
      // 批量删除菜品的逻辑
      if (this.selectedDishes.length === 0) {
        alert("请选择要删除的菜品");
        return;
      }
      this.selectedDishes.forEach(selectedDish => {
        const index = this.tableData.findIndex(dish => dish.dish_id === selectedDish.dish_id);
        if (index !== -1) {
          this.tableData.splice(index, 1);
        }
      });
      this.selectedDishes = [];
      console.log("批量删除的菜品：", this.selectedDishes);
    },
    handleSelectionChange(selection) {
      this.selectedDishes = selection;
    },
    fetchAllDishes() {
      // 获取所有菜品的逻辑...
    },
    showAddDishForm() {
      this.editableDish = null;
      this.dialogVisible = true;
    }
  },
  mounted() {
    this.fetchAllDishes();
  },
  handleSelectionChange(selection) {
    this.selectedDishes = selection;
  },
  deleteSelectedDishes() {
    if (this.selectedDishes.length === 0) {
      alert("请选择要删除的菜品");
      return;
    }
    this.selectedDishes.forEach(selectedDish => {
      const index = this.tableData.findIndex(dish => dish.dish_id === selectedDish.dish_id);
      if (index !== -1) {
        this.tableData.splice(index, 1);
      }
    });
    this.selectedDishes = [];
    console.log("批量删除的菜品：", this.selectedDishes);
  },
  row_editDish(row) {
    this.editableDish = row;
    this.dialogVisible = true; // 显示对话框并加载数据进行编辑
  },
  saveDish() {
    // 提交前再次验证所有字段
    this.validateName();
    this.validatePrice();
    this.validateCafeteriaId();
    this.validateCafeteriaName();
    this.validateDishId();
    this.validateCuisine();
    this.dialogVisible = false;
    // 如果有错误，则不提交表单
    if (Object.values(this.validationErrors).some(error => error)) {
      return;
    }
    // 发送保存菜品信息的请求到后端
    this.$http.post('http://localhost:9090/dishes/actions/addDish', this.dishForm)
        .then(response => {
          console.log('菜品保存成功', response);
          this.dialogVisible = false; // 关闭对话框
          // 这里可以添加更多的响应处理逻辑
        })
        .catch(error => {
          console.error('菜品保存失败', error);
          // 这里可以添加错误处理逻辑
        });
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
    this.dialogVisible = false;
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
};
</script>
<style>
.table-container {
  overflow-x: auto; /* 允许水平滚动 */
  max-width: 100%; /* 最大宽度为100% */
}

.el-table {
  min-width: 1000px; /* 根据需要调整最小宽度 */
}
input[type="checkbox"] {
  margin-right: 10px; /* 调整勾选框与其他元素之间的间距 */
}
.dish-form-container input[disabled] {
  background-color: #e9ecef; /* 灰色背景表示不可编辑 */
}
.el-table .table-row-center .cell {
  text-align: center; /* 水平居中 */
  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: center;
}
.centered-table .el-table__header-wrapper .cell,
.centered-table .cell {
  text-align: center; /* 水平居中 */
  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: center;
}
</style>
