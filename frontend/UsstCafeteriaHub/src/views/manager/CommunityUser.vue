<template>
  <div>
    <div>
<!--      <el-input style="width: 200px" placeholder="查询用户名称" v-model="userName"></el-input>-->
<!--      <el-input style="width: 200px; margin: 0 5px" placeholder="查询社区名称"  v-model="communityName"></el-input>-->
      <el-button type="primary" @click="load()">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
<!--      <el-button type="primary" plain @click="handleAdd">新增</el-button>-->
      <span style="margin-right: 10px"></span>
    </div>

    <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="communityUserId" label="序号" width="70"></el-table-column>
      <el-table-column prop="userName" label="用户名称"></el-table-column>
      <el-table-column prop="communityName" label="社区名称" ></el-table-column>
      <el-table-column prop="postCount" label="发帖数"></el-table-column>
      <el-table-column prop="likeCount" label="点赞数"></el-table-column>
      <el-table-column label="操作" width="180">
        <template v-slot="scope">
          <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" plain @click="del(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible">
      <el-form :model="dialogForm" label-width="80px" :rules="rules" ref="formRef">
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="dialogForm.userName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="社区名称" prop="communityName">
          <el-input v-model="dialogForm.communityName" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="发帖数" prop="postCount">
          <el-input-number v-model="dialogForm.postCount" :min="0" :max="10000" controls-position="right"></el-input-number>
        </el-form-item>
        <el-form-item label="点赞数" prop="likeCount">
          <el-input-number v-model="dialogForm.likeCount" :min="0" :max="10000" controls-position="right"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'CommunityUser',
  data() {
    return {
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      userName: '',
      communityName: '',
      total: 0,
      dialogFormVisible: false,
      dialogForm: {
        communityUserId: '',
        userId: '',
        userName: '',
        communityId: '',
        communityName: '',
        postCount: '',
        likeCount: ''
      },
      rules: {
        userName: [
          { required: true, message: '请输入用户名称', trigger: 'blur' },
        ],
        communityName: [
          { required: true, message: '请输入社区名称', trigger: 'blur' },
        ]
      },
    }
  },
  created() {
    console.log('created')
    this.load()
  },
  methods: {
    reset() {
      this.userName = ''
      this.communityName = ''
      this.load()
    },
    load() {
      this.$request.get('/admins/actions/getCommunityUsers', {
      }).then(res => {
        this.tableData = res.data
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.load()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.load()
    },
    del(row) {
      this.$confirm('是否删除该用户？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$request.post('/admins/actions/deleteCommunityUser' ,row).then(res => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.load()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleEdit(row) {
      this.dialogForm = Object.assign({}, row)
      this.dialogFormVisible = true
    },
    handleAdd() {
      this.dialogForm = {
        communityUserId: '',
        userId: '',
        userName: '',
        communityId: '',
        communityName: '',
        postCount: '',
        likeCount: ''
      }
      this.dialogFormVisible = true
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.dialogForm.communityUserId ? '/admins/actions/updateCommunityUser': '/admins/actions/addUser',
            method: 'post',
            data: this.dialogForm
          }).then(res => {
            if (res.code === 200) {
              this.$message.success('保存成功')
              this.load()
              this.dialogFormVisible = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id)
    },
  },
}
</script>

<style scoped>

</style>