<template>
  <div>
    <div>
      <el-input style="width: 200px; margin: 0 5px" placeholder="查询食堂名" v-model="cafeteriaName"></el-input>
      <el-input style="width: 200px; margin: 0 5px" placeholder="查询公告标题" v-model="title"></el-input>
      <el-button  type="primary" @click="load(1)">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增</el-button>
    </div>
    <el-table :data="tableData" stripe>
      <el-table-column prop="noticeId" label="序号" width="70"></el-table-column>
      <el-table-column prop="cafeteriaName" label="食堂名"></el-table-column>
      <el-table-column prop="title" label="公告标题"></el-table-column>
      <el-table-column prop="content" label="公告内容"></el-table-column>
      <el-table-column prop="createTime" label="发布时间">
        <template v-slot="scope">
          <span>{{ scope.row.createTime | formatDate }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template v-slot="scope">
          <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" @click="handleDel(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="total">
    </el-pagination>

    <el-dialog title="新增/编辑公告" :visible.sync="fromVisible" width="50%" top="15vh">
      <el-form :model="form" label-width="80px" ref="formRef">
<!--        <el-form-item label="食堂" prop="cafeteriaName">-->
<!--          <el-input v-model="form.cafeteriaName" placeholder="食堂名称"></el-input>-->
<!--        </el-form-item>-->
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
        <el-form-item label="公告标题" prop="title">
          <el-input v-model="form.title" placeholder="公告标题"></el-input>
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
          <el-input type="textarea" v-model="form.content" placeholder="公告内容"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      cafeteriaName: '',
      title: '',
      fromVisible: false,
      form: {},
      cafeterias: [], // 存储食堂信息的数组
    };
  },
  created() {
    this.load(1);
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
    load(pageNum) {
      this.pageNum = pageNum;
      this.$request.get('/cafeteriaNotices/actions/getCafeteriaNoticesByConditions', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          cafeteriaName: this.cafeteriaName,
          title: this.title,
        },
      }).then((res) => {
        this.tableData = res.data.records;
        this.total = res.data.total;
      });
      this.fetchCafeterias();
    },
    handleAdd() {
      this.fromVisible = true;
      this.form = {};
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.fromVisible = true;
    },
    handleDel(row) {
      this.$request.post('/cafeteriaNotices/actions/deleteCafeteriaNotice', { noticeId: row.noticeId })
          .then(() => {
            this.load(this.pageNum);
            this.$message.success('删除成功');
          });
    },
    handleSubmit() {
      const url = this.form.noticeId ? '/cafeteriaNotices/actions/updateCafeteriaNotice' : '/cafeteriaNotices/actions/addCafeteriaNotice';
      this.$request.post(url, this.form).then(() => {
        this.load(1);
        this.fromVisible = false;
        this.$message.success('操作成功');
      });
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum);
    },
    reset() {
      this.cafeteriaName = '';
      this.title = '';
      this.load(1);
    },
  },
};
</script>