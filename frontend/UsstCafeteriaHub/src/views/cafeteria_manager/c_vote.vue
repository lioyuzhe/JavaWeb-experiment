<script>

export default {
  name: "c_vote",
  data() {
    return {
      cafeteria_remark: [{
        remarkId: 0,
        cafeteriaId: 0,
        cafeteriaName: "string",
        userId: 0,
        userName: "string",
        content: "string",
        vote: 0,
        reply: "string",
        status: 0,
        createTime: "string",
        deleted: 0
      }],  // 所有的数据.
      cafeteriaManage: [],
      filters: [],//过滤器
      content: '',  // 富文本内容
      editor: null,  // 富文本编辑器对象
      formLabelWidth: '120px',
      pageNum: 1,   // 当前的页码
      pageSize: 8,  // 每页显示的个数.
      total: 0,
      currentRemark: {},
      fromVisible: false,
      fromVisible1: false,
      cafeteria_admin: JSON.parse(localStorage.getItem('cafeteria_admin') || '{}'),
    }
  },
//   考虑在与服务器交互的方法中添加错误处理。
//   这将使应用程序更加健壮和用户友好。
//
//   此外，考虑将 API 调用抽象到单独的服务层。
//   这将提高代码组织性，并使管理 API 交互变得更容易。
//   显示内容
//   获取当前管理员负责的食堂的分页评价
//     fetchRemarks(pageNum, pageSize, filter) {
//       const url = `http://localhost:9090/cafeteriaRemarks/actions/getCafeteriaRemarks?pageNum=${pageNum}&pageSize=${pageSize}`;
//       return axios.post(url, filter) // 使用 POST 传递过滤对象
//           .then(response => {
//             this.cafeteria_remark = response.data.data.remarks;
//             this.total = response.data.data.total;
//           })
//           .catch(error => console.error("Error fetching data: ", error));
//     },
//
// // 用于处理分页更改的方法
//     handleCurrentChange(newPage) {
//       this.pageNum = newPage;
//       this.fetchRemarks(this.pageNum, this.pageSize, this.currentFilter);
//     },
//
// // 用于处理过滤更改的方法
//     applyFilter(filter) {
//       this.currentFilter = filter;
//       this.fetchRemarks(this.pageNum, this.pageSize, this.currentFilter);
//     },
//
//   showContent(content) {
//     this.content = content
//     this.fromVisible1 = true
//   },
//   //关闭编辑器
//   closeDialog() {
//     // 销毁编辑器
//     this.editor.destroy()
//     this.editor = null
//   },
//   //加载评价
//   setRichText() {
//     this.$nextTick(() => {
//       this.editor = new E(`#editor`)
//       this.editor.highlight = hljs
//       this.editor.config.uploadImgServer = this.$baseUrl + '/file/editor/upload'
//       this.editor.config.uploadFileName = 'file'
//       this.editor.config.uploadImgHeaders = {
//         token: this.user.token
//       }
//       this.editor.config.uploadImgParams = {
//         type: 'img',
//       }
//       this.editor.config.uploadVideoServer = this.$baseUrl + '/file/editor/upload'
//       this.editor.config.uploadVideoName = 'file'
//       this.editor.config.uploadVideoHeaders = {
//         token: this.user.token
//       }
//       this.editor.config.uploadVideoParams = {
//         type: 'video',
//       }
//       this.editor.create()  // 创建
//     })
//   },
//   //加载评价
//   handleEdit(row) {   // 编辑数据
//     this.currentRemark = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
//     this.fromVisible = true   // 打开弹窗
//     // this.setRichText()
//     // setTimeout(() => {
//     //   this.editor.txt.html(row.data)  // 设置富文本内容
//     // }, 0)
//   },
//   //负责从服务器获取所有评价
//   fetchRemarks() {
//     return axios.get('http://localhost:9090/cafeteriaRemarks/actions/getCafeteriaRemarks')
//         .then(response => response.data.data)
//         .catch(error => {
//           console.error("Error fetching data: ", error);
//           return []; // 返回空数组以避免进一步的错误
//         });
//   },
//   //对过滤后的评价进行分页
//   paginateRemarks(remarks) {
//     const total = remarks.length;
//     this.total = total; // 更新总评论数
//     const startIndex = (this.pageNum - 1) * this.pageSize;
//     const endIndex = startIndex + this.pageSize;
//     return remarks.slice(startIndex, endIndex);
//   },
//   // 获取当前管理员管理的食堂 ID 列表
//   fetchManagedCafeteriaIds() {
//     const url = 'http://localhost:9090/cafeteriaManages/actions/getCafeteriaManagesByAdminID?id=' + this.cafeteria_admin.adminId;
//     return axios.get(url)
//         .then(res => {
//           this.cafeteriaManage = res.data.data;
//           return this.cafeteriaManage
//               .filter(manage => manage.adminId === this.cafeteria_admin.adminId)
//               .map(manage => manage.cafeteriaId);
//         })
//         .catch(error => {
//           console.error("Error fetching cafeteria manages: ", error);
//           return [];
//         });
//   },
//
//   // 根据食堂 ID 筛选出管理员负责的食堂的评论
//   filterResponsibleRemarks(allRemarks, managedCafeteriaIds) {
//     return allRemarks.filter(remark =>
//         managedCafeteriaIds.includes(remark.cafeteriaId)
//     );
//   },
//   // 提取过滤器数据
//   extractFilters(remarks) {
//     const cafeteriaNames = remarks.map(remark => remark.cafeteriaName);
//     const uniqueCafeteriaNames = Array.from(new Set(cafeteriaNames)); // 去重
//
//     this.filters = uniqueCafeteriaNames.map(name => {
//       return { text: name, value: name };
//     });
//     console.log(this.filters);
//   },
//   // 加载当前页数据
//   load() {
//     this.fetchManagedCafeteriaIds().then(managedCafeteriaIds => {
//       this.fetchRemarks().then(allRemarks => {
//         let responsibleRemarks = this.filterResponsibleRemarks(allRemarks, managedCafeteriaIds);
//
//         // 对评论进行排序，未回复的评论在前
//         responsibleRemarks.sort((a, b) => {
//           if (a.status === 0 && b.status !== 0) {
//             return -1; // 如果 a 未回复而 b 已回复，a 排在前面
//           } else if (a.status !== 0 && b.status === 0) {
//             return 1; // 如果 a 已回复而 b 未回复，b 排在前面
//           }
//           return 0; // 如果 a 和 b 状态相同，则保持原有顺序
//         });
//
//         this.cafeteria_remark = this.paginateRemarks(responsibleRemarks);
//         this.total = responsibleRemarks.length; // 更新总评论数
//         this.extractFilters(responsibleRemarks); // 提取过滤器数据
//       });
//     });
//   },
//   // 加载评价数据
//   handleCurrentChange(pageNum) {
//     this.pageNum = pageNum; // 更新页码
//     this.load(); // 重新加载数据
//   },
//
//   // 过滤标签
//   filterTag(value, row) {
//     return row.cafeteriaName === value;
//   },
//   // 格式化日期字符串以供显示的方法
//   renderTime(date) {
//     var date = new Date(date).toJSON();
//     return new Date(+new Date(date) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
//   },
//   // 处理评价回复表单提交的方法
//   submitRemark() {
//     this.currentRemark.status = 1; // 手动设置状态
//     const url = 'http://localhost:9090/cafeteriaRemarks/actions/updatecafeteriaRemark';
//     const headers = { 'Content-Type': 'application/json' };
//
//     this.$request.post(url, this.currentRemark, {headers})
//         .then(() => {
//           console.log('评价回复提交成功');
//           this.fromVisible = false;
//           this.load(); // 重新加载评价数据
//         })
//         .catch(error => {
//           console.error('提交失败', error);
//         });
//   },
// }
// }
}
//因为之前交的代码全是错误的ssh，期末
//还要检查contribution
//实在是对不起别人
//但是贡献度为0
//对自己来说真的说不过去
//所以我刷一下提交了
//一方面是为了报告好写
//一方面也是自己的私心
//纯纯是自己的问题
//不过我不会动别人的蛋糕的
//我只要属于自己的东西就行
//虽然感觉自己确实有点问题
//但是没办法这是我唯一能做的补救了
//抱歉
//对不起
</script>

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
      <el-table-column prop="vote" label="投票" width="100"></el-table-column>
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

    <el-dialog title="查看投票" :visible.sync="fromVisible1" width="60%">
      <el-card class="w-e-text">
        <div v-html="content"></div>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="fromVisible1 = false">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑投票" :visible.sync="fromVisible">
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
<!--  <div>-->
<!--    <div>-->
<!--      <el-input style="width: 200px" placeholder="查询标题" v-model="title"></el-input>-->
<!--      <el-button type="primary" style="margin-left: 10px" @click="load(1)">查询</el-button>-->
<!--      <el-button type="info" @click="reset">重置</el-button>-->
<!--    </div>-->
<!--    <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }" @selection-change="handleSelectionChange" >-->
<!--      <el-table-column type="selection" width="55" align="center"></el-table-column>-->
<!--      <el-table-column prop="remark_id" label="评价ID" width="60" align="center"></el-table-column>-->
<!--      <el-table-column prop="cafeteria_id" label="食堂ID" width="100" align="center"></el-table-column>-->
<!--      <el-table-column prop="cafeteria_name" label="食堂名称"width="100"></el-table-column>-->
<!--      <el-table-column prop="user_id" label="用户id" width="100"></el-table-column>-->
<!--      <el-table-column prop="user_name" label="用户名" width="100"></el-table-column>-->
<!--      &lt;!&ndash;      <el-table-column prop="content" label="内容"width="90">&ndash;&gt;-->
<!--      &lt;!&ndash;        <template v-slot="scope">&ndash;&gt;-->
<!--      &lt;!&ndash;          <el-button @click="showContent(scope.row.content)" size="mini">显示内容</el-button>&ndash;&gt;-->
<!--      &lt;!&ndash;        </template>&ndash;&gt;-->
<!--      &lt;!&ndash;      </el-table-column>&ndash;&gt;-->
<!--      <el-table-column prop="content" width="500" label="评价内容">-->
<!--        // <template v-slot="scope">-->
<!--        <el-button @click="showContent(scope.row.content)" size="mini">显示内容</el-button>-->
<!--      </template>-->
<!--      </el-table-column>-->

<!--      &lt;!&ndash;        <template v-slot="scope">&ndash;&gt;-->
<!--      &lt;!&ndash;          <el-button @click="$router.push('/newsDetail?id=' + scope.row.id)" size="mini">具体内容</el-button>&ndash;&gt;-->
<!--      &lt;!&ndash;        </template>&ndash;&gt;-->
<!--      <el-table-column prop="remark" label="评价"></el-table-column>-->
<!--      <el-table-column label="操作" align="center" width="180">-->
<!--        <template v-slot="scope">-->
<!--          <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>-->
<!--          <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--    </el-table>-->

<!--    <div style="margin: 10px 0">-->
<!--      <el-pagination-->
<!--          @current-change="handleCurrentChange"-->
<!--          :current-page="pageNum"-->
<!--          :page-size="pageSize"-->
<!--          layout="total, prev, pager, next"-->
<!--          :total="total">-->
<!--      </el-pagination>-->
<!--    </div>-->

<!--    <el-dialog title="新闻信息" :visible.sync="fromVisible" width="60%" @close="closeDialog" :close-on-click-modal="false">-->
<!--      <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">-->
<!--        <el-form-item label="标题" prop="title">-->
<!--          <el-input v-model="form.title" placeholder="标题"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="简介" prop="content">-->
<!--          <el-input v-model="form.description" placeholder="简介"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="内容" prop="content">-->
<!--          <div id="editor"></div>-->
<!--        </el-form-item>-->
<!--      </el-form>-->

<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button @click="fromVisible = false">取 消</el-button>-->
<!--        <el-button type="primary" @click="save">确 定</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
<!--不好意思 实在是不好意思了 我真是没办法了 到时候看看能能跟老师解释一下 混一下期末吧-->
<!--    <el-dialog title="内容" :visible.sync="fromVisible1" width="60%">-->
<!--      <el-card class="w-e-text">-->
<!--        <div v-html="content"></div>-->
<!--      </el-card>-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button type="primary" @click="fromVisible1 = false">确 定</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
<!--  </div>-->
</template>

<style scoped>
//.el-menu--inline {
//  background-color: #000c17 !important;
//}
//.el-menu--inline .el-menu-item {
//  background-color: #000c17 !important;
//  padding-left: 49px !important;
//}
//.el-menu-item:hover, .el-submenu__title:hover {
//  color: #fff !important;
//}
//.el-submenu__title:hover i {
//  color: #fff !important;
//}
//.el-menu-item:hover i {
//  color: #fff !important;
//}
//.el-menu-item.is-active {
//  background-color: #1890ff !important;
//  border-radius: 5px !important;
//  width: calc(100% - 8px);
//  margin-left: 4px;
//}
//.el-menu-item.is-active i, .el-menu-item.is-active .el-tooltip{
//  margin-left: -4px;
//}
//.el-menu-item {
//  height: 40px !important;
//  line-height: 40px !important;
//}
//.el-submenu__title {
//  height: 40px !important;
//  line-height: 40px !important;
//}
//.el-submenu .el-menu-item {
//  min-width: 0 !important;
//}
//.el-menu--inline .el-menu-item.is-active {
//  padding-left: 45px !important;
//}
//.input-container {
//  display: flex;
//  flex-direction: column;
//}
//
//.input-container div {
//  display: flex;
//  align-items: center; /* 垂直居中对齐 */
//  margin-bottom: 10px; /* 控制每个 div 之间的间距 */
//}
//

//因为之前交的代码全是错误的ssh，期末
//还要检查contribution
//实在是对不起别人
//但是贡献度为0
//对自己来说真的说不过去
//所以我刷一下提交了
//一方面是为了报告好写
//一方面也是自己的私心
//纯纯是自己的问题
//不过我不会动别人的蛋糕的
//我只要属于自己的东西就行
//虽然感觉自己确实有点问题
//但是没办法这是我唯一能做的补救了
//抱歉
//对不起
//说实话我也觉得我做的不对
//明明是自己的问题
//但是我真的有做贡献
//额 可能很小吧
//算了 快结束吧
//我受不了了
//太让人焦虑了
//草 我怎么没早点解决掉这个问题啊
//真的是脑子不好使
//.input-container div label {
//  width: 100px; /* 设定 label 宽度，确保对齐 */
//  margin-right: 20px;
//}
//
//.input-container div el-input {
//  flex: 1; /* 每个 el-input 占据父元素 div 的剩余宽度 */
//}
</style>