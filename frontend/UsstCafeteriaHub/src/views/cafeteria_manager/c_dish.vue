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
  </div>
</template>

<script>
import DishForm from './c_dishForm.vue';
export default {
  components: {
    DishForm
  },
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
      editableDish: null // 存储要编辑的菜品
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
      // 删除单个菜品的逻辑
      const index = this.tableData.findIndex(dish => dish.dish_id === row.dish_id);
      if (index !== -1) {
        this.tableData.splice(index, 1);
      }
      console.log('删除操作', row);
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
      this.showForm = true;
      this.$router.push({ name: 'DishForm' });
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
    // 执行批量删除逻辑
    console.log("批量删除的菜品：", this.selectedDishes);
    // 根据需要调整删除逻辑
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
