<template>
  <div>
    <div>
      <el-input style="width: 200px" placeholder="查询食堂名" v-model="cafeteriaName"></el-input>
      <el-input style="width: 200px; margin: 0 5px" placeholder="查询菜名"  v-model="name"></el-input>
      <el-input style="width: 200px; margin: 0 5px" placeholder="查询菜系"  v-model="cuisine"></el-input>
      <el-input style="width: 200px; margin: 0 5px" placeholder="查询价格"  v-model="price"></el-input>
      <el-button type="primary" @click="load(1)">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>
    <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="dishId" label="序号" width="70" align="center"></el-table-column>
      <el-table-column prop="cafeteriaName" label="食堂名"></el-table-column>
      <el-table-column prop="name" label="菜名"></el-table-column>
      <el-table-column prop="price" label="价格"></el-table-column>
      <el-table-column prop="cuisine" label="菜系"></el-table-column>
      <el-table-column prop="status" label="状态" width="90" align="center"></el-table-column>
      <el-table-column label="图片">
        <template v-slot="scope">
          <div style="display: flex; align-items: center">
            <el-image style="width: 70px; height: 50px; border-radius: 10%" v-if="scope.row.imageUrl" :src="scope.row.imageUrl" :preview-src-list="[scope.row.imageUrl]"></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template v-slot="scope">
          <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" plain @click="del(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="菜品信息" :visible.sync="fromVisible" width="30%">
      <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
        <el-form-item label="食堂名" prop="cafeteriaName">
          <el-select v-model="form.cafeteriaName" placeholder="选择食堂" @change="onCafeteriaChange">
            <el-option
                v-for="cafeteria in cafeterias"
                :key="cafeteria.cafeteriaId"
                :label="cafeteria.name"
                :value="cafeteria.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜名" prop="name">
          <el-input v-model="form.name" placeholder="菜名"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="价格"></el-input>
        </el-form-item>
        <el-form-item label="菜系" prop="cuisine">
          <el-input v-model="form.cuisine" placeholder="菜系"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-input v-model="form.status" placeholder="状态"></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              :file-list="form.imageUrl? [form.imageUrl] : []"
              list-type="picture"
              :on-success="handleImageSuccess"
          >
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Dish",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('cafeteria_admin') || '{}'),
      cafeterias: [], // 存储食堂信息的数组
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      cafeteriaName: '',
      name: '',
      cuisine: '',
      price: '',

      total: 0,
      fromVisible: false,
      form: {},
      rules: {
        name: [
          { required: true, message: '请输入菜名', trigger: 'blur' },
        ]
      }
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    onCafeteriaChange(value) {
      const selectedCafeteria = this.cafeterias.find(c => c.name === value);
      if (selectedCafeteria) {
        this.form.cafeteriaName = selectedCafeteria.name;
        this.form.cafeteriaId = selectedCafeteria.cafeteriaId;
      }
    },
    fetchCafeterias() {
      // 发起请求获取食堂信息，以下是示例代码
      this.$request.get('/cafeteriaAdmins/actions/getAllCafeteria')
          .then(response => {
            this.cafeterias = response.data; // 假设后端返回的数据结构是 { data: [食堂列表] }
            this.$message.success("获取成功")
          })
          .catch(error => {
            this.$message.error(response.message)
            console.error('获取食堂信息失败:', error);
          });
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.dishId)
    },
    load(pageNum) {  // 分页查询
      if (pageNum)  this.pageNum = pageNum
      this.$request.get('/cafeteriaAdmins/actions/selectDishByPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          cafeteriaName: this.cafeteriaName,
          name: this.name,
          cuisine: this.cuisine,
          price: this.price
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
      this.fetchCafeterias();
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$request({
        url: this.form.dishId ? '/dishes/actions/updateDish': '/dishes/actions/addDish',
        method: 'POST',
        data: this.form
      }).then(res => {
        if (res.code === 200) {  // 表示成功保存
          this.$message.success('保存成功')
          this.load(1)
          this.fromVisible = false
        } else {
          this.$message.error(res.msg)  // 弹出错误的信息
        }
      })
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    reset() {
      this.cafeteriaName = ''
      this.name = ''
      this.cuisine = ''
      this.price = ''
      this.load(1)
    },
    handleImageSuccess(response, file, fileList) {
      // dish的图片属性换成上传的图片的链接
      this.form.imageUrl = response.data
    },
    del(dish) {
      this.$confirm('您确认删除这个菜品吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.post('/dishes/actions/deleteDish', dish).then(res => {
          if (res.code === 200) {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {})
    }
  }
}
</script>