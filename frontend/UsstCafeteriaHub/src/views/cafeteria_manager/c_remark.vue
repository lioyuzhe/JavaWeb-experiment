
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

</script>
<style>

</style>
