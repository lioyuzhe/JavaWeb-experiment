<template>

  <div>

    <div style="margin: 10px 0"></div>
    <el-table
        ref="filterTable"
        :data="complaint"
        style="width: 100%">
<!--      <el-table-column prop="createTime" label="日期" sortable width="180">-->
<!--        <template slot-scope="scope">-->
<!--          {{ renderTime(scope.row.createTime) }}-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column prop="userName" label="用户名" width="200"></el-table-column>
<!--      <el-table-column prop="score" label="评分" width="100"></el-table-column>-->

      <el-table-column prop="cafeteriaName" label="食堂" width="200"
                       :filters="filters"
                       :filter-method="filterTag" filter-placement="bottom-end">
      </el-table-column>
      <el-table-column prop="content" label="内容">
        <template v-slot="scope">
          <el-button @click="showContent(scope.row.content)" size="mini">显示内容</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="180">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 0 ? 'primary' : 'success'" disable-transitions>
            {{ scope.row.status === 0 ? '未回复' : '已回复' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template v-slot="scope">
          <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">回复</el-button>
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

    <el-dialog title="查看投诉" :visible.sync="fromVisible1" width="60%">
      <el-card class="w-e-text">
        <div v-html="content"></div>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="fromVisible1 = false">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="回复投诉" :visible.sync="fromVisible">
      <!--              <el-form :model="reply_form">-->
      <el-form v-model="currentComplaint">
        <el-form-item label="接收人" :label-width="formLabelWidth">
          <el-input v-model="currentComplaint.userName" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="食堂" :label-width="formLabelWidth">
          <el-input v-model="currentComplaint.cafeteriaName" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="回复内容" :label-width="formLabelWidth">
          <el-input type="textarea" :rows="6" placeholder="请输入内容"
                    v-model="currentComplaint.reply"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitComplaint">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import E from "wangeditor"
import hljs from 'highlight.js'
import axios from "axios";

export default {
  name: "complaint",
  data() {
    return {
      complaint: [{
        complaintId: 0,
        cafeteriaId: 0,
        cafeteriaName: "string",
        userId: 0,
        userName: "string",
        content: "string",
        reply: "string",
        status: 0,
        deleted: 0
      }],  // 所有的数据
      cafeteriaManage: [],
      filters: [],//过滤器
      content: '',  // 富文本内容
      editor: null,  // 富文本编辑器对象
      formLabelWidth: '120px',
      pageNum: 1,   // 当前的页码
      pageSize: 8,  // 每页显示的个数
      total: 0,
      currentComplaint: {},
      fromVisible: false,
      fromVisible1: false,
      cafeteria_admin: JSON.parse(localStorage.getItem('cafeteria_admin') || '{}'),
    }
  },
  created() {
    this.load()
  },
  methods: {
    //显示内容
    showContent(content) {
      this.content = content
      this.fromVisible1 = true
    },
    //关闭编辑器
    closeDialog() {
      // 销毁编辑器
      this.editor.destroy()
      this.editor = null
    },
    //加载评价
    setRichText() {
      this.$nextTick(() => {
        this.editor = new E(`#editor`)
        this.editor.highlight = hljs
        this.editor.config.uploadImgServer = this.$baseUrl + '/file/editor/upload'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadImgParams = {
          type: 'img',
        }
        this.editor.config.uploadVideoServer = this.$baseUrl + '/file/editor/upload'
        this.editor.config.uploadVideoName = 'file'
        this.editor.config.uploadVideoHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadVideoParams = {
          type: 'video',
        }
        this.editor.create()  // 创建
      })
    },
    //加载评价
    handleEdit(row) {   // 编辑数据
      this.currentComplaint = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
      // this.setRichText()
      // setTimeout(() => {
      //   this.editor.txt.html(row.data)  // 设置富文本内容
      // }, 0)
    },
    //负责从服务器获取所有投诉
    fetchComplaints() {
      return this.$request.get('/cafeteriaAdmins/actions/getComplaint')
          .then(response => response.data.data)
          .catch(error => {
            console.error("Error fetching data: ", error);
            return []; // 返回空数组以避免进一步的错误
          });
    },
    //对过滤后的评价进行分页
    paginateComplaints(complaints) {
      const total = complaints.length;
      this.total = total; // 更新总投诉数
      const startIndex = (this.pageNum - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return complaints.slice(startIndex, endIndex);
    },
    // 获取当前管理员管理的食堂 ID 列表
    fetchManagedCafeteriaIds() {
      const url = '/cafeteriaAdmins/actions/getCafeteriaIdList/'+this.cafeteria_admin.adminId;
      return this.$request.get(url)
          .then(res => {
            // 假设返回的数据结构是 { data: [{ cafeteriaId: 1 }, { cafeteriaId: 2 }, ...] }
            return res.data.map(manage => manage.cafeteriaId);
          })
          .catch(error => {
            console.error("Error fetching cafeteria IDs: ", error);
            return [];
          });
    },


    // 根据食堂 ID 筛选出管理员负责的食堂的投诉
    filterResponsibleComplaints(allComplaints, managedCafeteriaIds) {
      return allComplaints.filter(complaint =>
          managedCafeteriaIds.includes(complaint.cafeteriaId)
      );
    },
    // 提取过滤器数据
    extractFilters(complaints) {
      const cafeteriaNames = complaints.map(complaint => complaint.cafeteriaName);
      const uniqueCafeteriaNames = Array.from(new Set(cafeteriaNames)); // 去重

      this.filters = uniqueCafeteriaNames.map(name => {
        return { text: name, value: name };
      });
      console.log(this.filters);
    },
    // 加载当前页数据
    load() {
      this.fetchManagedCafeteriaIds().then(managedCafeteriaIds => {
        this.fetchComplaints().then(allComplaints => {
          let responsibleComplaints = this.filterResponsibleComplaints(allComplaints, managedCafeteriaIds);

          // 对投诉进行排序，未回复的投诉在前
          responsibleComplaints.sort((a, b) => {
            if (a.status === 0 && b.status !== 0) {
              return -1; // 如果 a 未回复而 b 已回复，a 排在前面
            } else if (a.status !== 0 && b.status === 0) {
              return 1; // 如果 a 已回复而 b 未回复，b 排在前面
            }
            return 0; // 如果 a 和 b 状态相同，则保持原有顺序
          });

          this.complaint = this.paginateComplaints(responsibleComplaints);
          this.total = responsibleComplaints.length; // 更新总投诉数
          this.extractFilters(responsibleComplaints); // 提取过滤器数据
        });
      });
    },
    // 加载评价数据
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum; // 更新页码
      this.load(); // 重新加载数据
    },

    // 过滤标签
    filterTag(value, row) {
      return row.cafeteriaName === value;
    },
    //改写时间格式
    renderTime(date) {
      var date = new Date(date).toJSON();
      return new Date(+new Date(date) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    },
    //提交表单
    submitComplaint() {
      this.currentComplaint.status = 1; // 手动设置状态
      const url = '/complaints/actions/updateComplaint';
      const headers = { 'Content-Type': 'application/json' };

      this.$request.post(url, this.currentComplaint, {headers})
          .then(() => {
            console.log('评价回复提交成功');
            this.fromVisible = false;
            this.load(); // 重新加载评价数据
          })
          .catch(error => {
            console.error('提交失败', error);
          });
    },
  }
}
</script>

<style scoped>

</style>