<template>
  <div>
    <div>
      <el-input style="width: 200px" placeholder="查询社区名称" v-model="communityName"></el-input>
      <el-input style="width: 200px; margin: 0 5px" placeholder="查询用户名称"  v-model="userName"></el-input>
      <el-button type="primary" @click="load(1)">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>
    <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="messageId" label="序号" width="70" align="center"></el-table-column>
      <el-table-column prop="communityName" label="社区名称"></el-table-column>
      <el-table-column prop="userName" label="用户名称"></el-table-column>
      <el-table-column prop="title" label="标题"></el-table-column>
      <el-table-column prop="content" label="内容"></el-table-column>
      <el-table-column prop="likeCount" label="点赞数"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" align="center"></el-table-column>
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

    <el-dialog title="社区信息" :visible.sync="fromVisible" width="30%">
      <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
        <el-form-item label="社区名称" prop="communityName">
          <el-input v-model="form.communityName" placeholder="社区名称"></el-input>
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="form.userName" placeholder="用户名称"></el-input>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input type="textarea" v-model="form.content" placeholder="内容"></el-input>
        </el-form-item>
        <el-form-item label="点赞数" prop="likeCount">
          <el-input-number v-model="form.likeCount" :min="0"></el-input-number>
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
  name: "CommunityMessage",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      communityName: '',
      userName: '',
      total: 0,
      fromVisible: false,
      form: {},
      rules: {
        content: [
          { required: true, message: '请输入消息内容', trigger: 'blur' },
          { required: true, message: '请输入标题', trigger: 'blur' },
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { required: true, message: '请输入社区名', trigger: 'blur' }
        ]
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    reset() {
      this.communityName = ''
      this.userName = ''
      this.load()
    },
    load() {
      this.$request.get('/admins/actions/getCommunityMessages', {
      }).then(res => {
        this.tableData = res.data
      })
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    del(communityMessage) {
      this.$confirm('您确认删除吗？', '确认删除', {type: "warning"}).then(() => {
        this.$request.delete('/admins/actions/deleteCommunityMessage' + communityMessage).then(res => {
          if (res.code === '200') {
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
      this.form = {}
      this.fromVisible = true
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.messageId ? '/admins/actions/updateCommunityMessage': '/admins/actions/addCommunityMessage',
            method: 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {
              this.$message.success('保存成功')
              this.load()
              this.fromVisible = false
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
  }
}
</script>

<style scoped>

</style>
