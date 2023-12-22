<template>
  <div class="table-container">
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
  </div>
</template>



<script>
import DishForm from './DishForm.vue';
export default {
  components: {
    DishForm
  },
  data() {
    return {
      dishes: [], // 存储所有菜品
      showForm: false, // 控制显示DishForm
      editableDish: null // 存储要编辑的菜品
    };
  },
  methods: {
    fetchAllDishes() {
      // 获取所有菜品的逻辑...
    },
    showAddDishForm() {
      this.editableDish = null;
      this.showForm = true;
      this.$router.push({ name: 'DishForm' });
    },
    editDish(dish) {
      // 编辑菜品的逻辑...
      this.editableDish = dish;
      this.showForm = true;
    },
    deleteDish(dishId) {
      // 删除单个菜品的逻辑...
    },
    deleteSelectedDishes() {
      // 批量删除菜品的逻辑...
    },
    cancelForm() {
      this.showForm = false;
      this.editableDish = null;
    }
  },
  mounted() {
    this.fetchAllDishes();
  }
};
</script>
<style>
ul {
  list-style-type: none;
  padding: 0;
}

li {
  padding: 10px;
  border-bottom: 1px solid #ddd; /* 分隔线 */
}

li button {
  float: right;
  padding: 5px 10px;
  margin-left: 10px;
  background-color: #007bff;
  color: white;
  border-radius: 5px;
}

li button:hover {
  background-color: #0056b3;
}
.dish-form-container input[disabled] {
  background-color: #e9ecef; /* 灰色背景表示不可编辑 */
}
</style>