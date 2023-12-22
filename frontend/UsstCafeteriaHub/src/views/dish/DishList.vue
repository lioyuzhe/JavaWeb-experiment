<template>
  <div>
    <button @click="showAddDishForm">新增菜品</button>
    <button @click="deleteSelectedDishes">批量删除</button>
    <ul>
      <li v-for="dish in dishes" :key="dish.dish_id">
        {{ dish.name }} - {{ dish.price }} 元
        <button @click="editDish(dish)">编辑</button>
        <button @click="deleteDish(dish.id)">删除</button>
        <input type="checkbox" :value="dish.id" v-model="selectedDishes">
      </li>
    </ul>
    <!-- DishForm 组件用于新增或编辑菜品 -->
    <dish-form v-if="showForm" :existing-dish="editableDish" @formSubmitted="fetchAllDishes" @cancelForm="cancelForm"></dish-form>
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

</style>