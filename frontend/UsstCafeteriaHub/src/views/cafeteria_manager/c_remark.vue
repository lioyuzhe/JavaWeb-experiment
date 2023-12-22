<template>

  <div>

    <div style="margin: 10px 0"></div>
    <el-table
        ref="filterTable"
        :data="cafeteria_remark"
        style="width: 100%">
      <el-table-column prop="createTime" label="日期" sortable width="180">
        <template slot-scope="scope">
          {{ renderTime(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="userName" label="用户名" width="100"></el-table-column>
      <el-table-column prop="score" label="评分" width="100"></el-table-column>
      <el-table-column prop="status" label="状态" width="180">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 0 ? 'primary' : 'success'" disable-transitions>
            {{ scope.row.status === 0 ? '未回复' : '已回复' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="cafeteriaName" label="食堂" width="100"
                       :filters="filters"
                       :filter-method="filterTag" filter-placement="bottom-end">
      </el-table-column>
      <el-table-column prop="content" label="内容">
        <template v-slot="scope">
          <el-button @click="showContent(scope.row.content)" size="mini">显示内容</el-button>
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

    <el-dialog title="查看评价" :visible.sync="fromVisible1" width="60%">
      <el-card class="w-e-text">
        <div v-html="content"></div>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="fromVisible1 = false">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="回复评价" :visible.sync="fromVisible">
      <!--              <el-form :model="reply_form">-->
      <el-form v-model="currentRemark">
        <el-form-item label="接收人" :label-width="formLabelWidth">
          <el-input v-model="currentRemark.userName" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="食堂" :label-width="formLabelWidth">
          <el-input v-model="currentRemark.cafeteriaName" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="回复内容" :label-width="formLabelWidth">
          <el-input type="textarea" :rows="6" placeholder="请输入内容"
                    v-model="currentRemark.reply"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitRemark">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import E from "wangeditor"
import hljs from 'highlight.js'
import axios from "axios";

export default {
  name: "remark",
  data() {
    return {
      cafeteria_remark: [{
        remarkId: 0,
        cafeteriaId: 0,
        cafeteriaName: "string",
        userId: 0,
        userName: "string",
        content: "string",
        score: 0,
        reply: "string",
        status: 0,
        createTime: "string",
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
      currentRemark: {},
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
      this.currentRemark = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
      // this.setRichText()
      // setTimeout(() => {
      //   this.editor.txt.html(row.data)  // 设置富文本内容
      // }, 0)
    },
    //负责从服务器获取所有评价
    fetchRemarks() {
      return axios.get('http://localhost:9090/cafeteriaRemarks/actions/getCafeteriaRemarks')
          .then(response => response.data.data)
          .catch(error => {
            console.error("Error fetching data: ", error);
            return []; // 返回空数组以避免进一步的错误
          });
    },
    //对过滤后的评价进行分页
    paginateRemarks(remarks) {
      const total = remarks.length;
      this.total = total; // 更新总评论数
      const startIndex = (this.pageNum - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return remarks.slice(startIndex, endIndex);
    },
    // 获取当前管理员管理的食堂 ID 列表
    fetchManagedCafeteriaIds() {
      const url = 'http://localhost:9090/cafeteriaManages/actions/getCafeteriaManagesByAdminID?id=' + this.cafeteria_admin.adminId;
      return axios.get(url)
          .then(res => {
            this.cafeteriaManage = res.data.data;
            return this.cafeteriaManage
                .filter(manage => manage.adminId === this.cafeteria_admin.adminId)
                .map(manage => manage.cafeteriaId);
          })
          .catch(error => {
            console.error("Error fetching cafeteria manages: ", error);
            return [];
          });
    },

    // 根据食堂 ID 筛选出管理员负责的食堂的评论
    filterResponsibleRemarks(allRemarks, managedCafeteriaIds) {
      return allRemarks.filter(remark =>
          managedCafeteriaIds.includes(remark.cafeteriaId)
      );
    },
    // 提取过滤器数据
    extractFilters(remarks) {
      const cafeteriaNames = remarks.map(remark => remark.cafeteriaName);
      const uniqueCafeteriaNames = Array.from(new Set(cafeteriaNames)); // 去重

      this.filters = uniqueCafeteriaNames.map(name => {
        return { text: name, value: name };
      });
      console.log(this.filters);
    },
    // 加载当前页数据
    load() {
      this.fetchManagedCafeteriaIds().then(managedCafeteriaIds => {
        this.fetchRemarks().then(allRemarks => {
          let responsibleRemarks = this.filterResponsibleRemarks(allRemarks, managedCafeteriaIds);

          // 对评论进行排序，未回复的评论在前
          responsibleRemarks.sort((a, b) => {
            if (a.status === 0 && b.status !== 0) {
              return -1; // 如果 a 未回复而 b 已回复，a 排在前面
            } else if (a.status !== 0 && b.status === 0) {
              return 1; // 如果 a 已回复而 b 未回复，b 排在前面
            }
            return 0; // 如果 a 和 b 状态相同，则保持原有顺序
          });

          this.cafeteria_remark = this.paginateRemarks(responsibleRemarks);
          this.total = responsibleRemarks.length; // 更新总评论数
          this.extractFilters(responsibleRemarks); // 提取过滤器数据
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
    submitRemark() {
      this.currentRemark.status = 1; // 手动设置状态
      const url = 'http://localhost:9090/cafeteriaRemarks/actions/updatecafeteriaRemark';
      const headers = { 'Content-Type': 'application/json' };

      this.$request.post(url, this.currentRemark, {headers})
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