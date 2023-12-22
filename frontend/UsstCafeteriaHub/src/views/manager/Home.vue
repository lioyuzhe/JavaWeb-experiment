<template>
  <el-container direction="vertical">
    <el-header>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>管理员 {{this.user.account}}，欢迎来到管理员页面</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <el-main>
      <el-card>
        <h2 slot="header">上海理工大学食堂点评交流社区 管理员页面</h2>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>主题色</span>
              </div>
              <div>
                <el-button type="primary">处理评价</el-button>
                <el-button type="success">添加菜品</el-button>
                <el-button type="warning">发布公告</el-button>
                <el-button type="danger">删除用户</el-button>
                <el-button type="info">查看统计</el-button>
              </div>
            </el-card>
          </el-col>

          <el-col :span="16">
            <el-card class="box-card">
              <!-- Your custom components or data display here -->
              <el-table :data="tableData" style="width: 100%">
                <!-- Your table columns -->
              </el-table>
            </el-card>
          </el-col>
        </el-row>
      </el-card>
    </el-main>
  </el-container>
</template>

<!-- Add your JavaScript here -->

<!-- Add your CSS here -->


<script>
export default {
  name: "Home",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('admin') || '{}'),
      notices: [],
      activeName: '0'
    }
  },
  created() {
    this.loadNotice()
  },
  methods: {
    loadNotice() {
      this.$request.get('/notice/selectUserData').then(res => {
        // this.notices = res.data
        this.notices = []
      })
    }
  }
}
</script>
<style scoped>

</style>