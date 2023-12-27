<template>
  <div>
    <div>
      <el-input style="width: 200px" placeholder="查询食堂名称" v-model="cafeteriaName"></el-input>
      <el-input style="width: 200px; margin: 0 5px" placeholder="查询食堂位置"  v-model="location"></el-input>
      <el-button type="primary" @click="load(1)">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>
    <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="cafeteriaId" label="序号" width="70" align="center"></el-table-column>
      <el-table-column prop="name" label="食堂名称"></el-table-column>
      <el-table-column prop="location" label="位置"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column prop="adminId" label="管理员ID"></el-table-column>
      <el-table-column prop="adminName" label="管理员名称"></el-table-column>
      <el-table-column prop="openTime" label="开放时间" width="180" align="center"></el-table-column>
      <el-table-column prop="closeTime" label="关闭时间" width="180" align="center"></el-table-column>
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
    <el-dialog title="食堂信息" :visible.sync="fromVisible" width="30%">
      <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
        <el-form-item label="食堂名称" prop="name">
          <el-input v-model="form.name" placeholder="食堂名称"></el-input>
        </el-form-item>
        <el-form-item label="位置" prop="location">
          <el-input v-model="form.location" placeholder="位置"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" placeholder="描述"></el-input>
        </el-form-item>
        <el-form-item label="管理员ID" prop="adminId">
          <el-input v-model="form.adminId" placeholder="管理员ID"></el-input>
        </el-form-item>
        <el-form-item label="管理员名称" prop="adminName">
          <el-input v-model="form.adminName" placeholder="管理员名称"></el-input>
        </el-form-item>
        <el-form-item label="开放时间" prop="openTime">
          <el-input v-model="form.openTime" style="width:50%"></el-input>
        </el-form-item>
        <el-form-item label="关闭时间" prop="closeTime">
          <el-input v-model="form.closeTime" style="width:50%"></el-input>
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
  name: "Cafeteria",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      cafeteriaName: '',
      location: '',
      total: 0,
      fromVisible: false,
      form: {},
      rules: {},
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.cafeteriaId)
    },
    del(cafeteria) {
      this.$confirm('您确认删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.post('/admins/actions/deleteCafeteria' ,cafeteria).then(res => {
          if (res.code === 200) {
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.fromVisible = true
    },
    handleAdd() {
      this.form = { }
      this.fromVisible = true
    },
    save() {
      this.$request({
        url: this.form.cafeteriaId ? '/admins/actions/updateCafeteria': '/admins/actions/addCafeteria',
        method: 'POST',
        data: this.form
      }).then(res => {
        if (res.code === 200) {
          this.$message.success('保存成功')
          this.load()
          this.fromVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reset() {
      this.cafeteriaName = ''
      this.location = ''
      this.load(1)
    },
    load(pageNum) {
      if (pageNum) {
        this.pageNum = pageNum;
      }
      this.$request.get('/admins/actions/getCafeterias', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          cafeteriaName: this.cafeteriaName,
          location: this.location
        }
      }).then(res => {
        this.tableData = res.data.records; // 假设后端返回的是分页结构
        this.total = res.data.total;       // 总记录数
      });
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>