<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
      <h2>欢迎登录上海理工大学食堂点评交流社区 食堂管理员页面，当前管理员：<strong>{{ cafeteria_admin.name }}</strong></h2>
    </div>

    <div style="display: flex">
      <!--      系统公告区域-->
      <el-card style="width: 100%; margin-right: 10px">
        <div style="margin-bottom: 15px; font-size: 20px; font-weight: bold">我管理的食堂</div>
        <el-timeline style="padding: 0">
          <el-timeline-item v-for="item in filteredCafeteria" :key="item.cafeteriaId" :timestamp="'营业时间：'+renderTime(item.openTime)"
                            placement="top">
            <el-card>
              <h4>{{ item.name }}</h4>
              <div v-html="'简介：'+item.description"></div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </div>

    <div style="display: flex; margin: 15px 0">
      <!--      投诉栏区域-->
      <el-card style="width: 50%">
        <div style="margin-bottom: 15px; font-size: 20px; font-weight: bold">
          投诉栏
          <!--           待回复按钮-->
          <el-badge :value="complaint_number" class="item">
            <el-button size="small">待回复</el-button>
          </el-badge>
        </div>

        <el-collapse v-model="activeName1" accordion>
          <el-collapse-item v-for="(item, index) in filteredComplaints" :key="item.complaintId" :name="index + ''">
            <template slot="title">
              <div style="display: flex; align-items: center; width: 100%">
                <h4 style="flex: 1">{{ item.cafeteriaName }}</h4>
                <div style="width: 150px; color: #888">{{ item.userName }}</div>
              </div>
            </template>
            <div v-html="item.content"></div>

            <!-- Form -->
            <el-button type="text" @click="setCurrentComplaint(item)">回复</el-button>

            <!--            表单标题-->
            <el-dialog title="回复投诉" :visible.sync="dialogFormVisibleForComplaint">
              <!--              表单-->
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
                <el-button @click="dialogFormVisibleForComplaint = false">取 消</el-button>
                <el-button type="primary" @click="submitComplaint">确 定</el-button>
              </div>
            </el-dialog>
          </el-collapse-item>
        </el-collapse>
      </el-card>

      <!--      评价栏区域-->
      <el-card style="width: 50%">
        <div style="margin-bottom: 15px; font-size: 20px; font-weight: bold">
          评价栏
          <!--           待回复按钮-->
          <el-badge :value="remark_number" class="item">
            <el-button size="small">待回复</el-button>
          </el-badge>
        </div>

        <el-collapse v-model="activeName2" accordion>
          <el-collapse-item v-for="(item, index) in filteredRemarks" :key="item.remarkId" :name="index + ''">
            <template slot="title">
              <div style="display: flex; align-items: center; width: 100%">
                <h4 style="flex: 1">{{ item.cafeteriaName + "：" + item.userName }}</h4>
                <div style="width: 250px; color: #888">{{ renderTime(item.createTime) }}</div>
              </div>
            </template>
            <div v-html="item.content"></div>

            <!-- Form -->
            <el-button type="text" @click="setCurrentRemark(item)">回复</el-button>

            <el-dialog title="回复评价" :visible.sync="dialogFormVisibleForRemark">
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
                <el-button @click="dialogFormVisibleForRemark = false">取 消</el-button>
                <el-button type="primary" @click="submitRemark">确 定</el-button>
              </div>
            </el-dialog>
          </el-collapse-item>
        </el-collapse>
      </el-card>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Home",
  data() {
    return {
      //食堂管理员
      cafeteria_admin: JSON.parse(localStorage.getItem('cafeteria_admin') || '{}'),
      //食堂表
      cafeteria: [],
      //投诉表
      complaint: [],
      //评价表
      cafeteria_remark: [],
      complaint_number: 0,
      remark_number: 0,
      formLabelWidth: '120px',
      dialogTableVisible: false,
      dialogFormVisibleForComplaint: false,
      dialogFormVisibleForRemark: false,
      currentComplaint: {}, // 当前正在回复的投诉
      currentRemark: {}, // 当前正在回复的评价
      activeName1: '0',
      activeName2: '0'
    }
  },
  async created() {
    await this.loadData();
  },
  computed: {
    // 获取当前管理员管理的所有食堂的 ID
    managedCafeteriaIds() {
      return this.cafeteria.filter(c => c.adminId === this.cafeteria_admin.adminId).map(c => c.cafeteriaId);
    },

    // 仅显示状态为0且属于管理员管理食堂的投诉信息
    filteredComplaints() {
      return this.complaint.filter(item => item.status === 0 && this.managedCafeteriaIds.includes(item.cafeteriaId));
    },

    // 仅显示状态为0且属于管理员管理食堂的评价信息
    filteredRemarks() {
      return this.cafeteria_remark.filter(item => item.status === 0 && this.managedCafeteriaIds.includes(item.cafeteriaId));
    },
    //仅显示当前管理员管理的食堂
    filteredCafeteria() {
      return this.cafeteria.filter(item => item.adminId === this.cafeteria_admin.adminId);
    },
  },
  methods: {
    async loadData() {
      //await this.loadCafeteriaAdmin();
      await this.loadCafeteria();
      await this.loadComplaint();
      await this.loadRemark();
      this.updateComplaintNumber();
      this.updateRemarkNumber();
    },
    //显示待回复的投诉数量
    updateComplaintNumber() {
      this.complaint_number = this.filteredComplaints.length;
    },
    //显示待回复的评价数量
    updateRemarkNumber() {
      this.remark_number = this.filteredRemarks.length;
    },
    //加载食堂信息
    async loadCafeteria() {
      try {
        const response = await axios.get('http://localhost:9090/cafeterias/actions/getCafeteria');
        this.cafeteria = Array.isArray(response.data.data) ? response.data.data : [];
        console.log(this.cafeteria);
      } catch (error) {
        console.error('加载食堂数据出错', error);
        this.cafeteria = [];
      }
    },
    //加载投诉信息
    async loadComplaint() {
      try {
        const response = await axios.get('http://localhost:9090/complaints/actions/getComplaint');
        this.complaint = Array.isArray(response.data.data) ? response.data.data : [];
        console.log(this.complaint);
      } catch (error) {
        console.error('加载投诉数据出错', error);
        this.complaint = [];
      }
    },
    //加载评价信息
    async loadRemark() {
      try {
        const response = await axios.get('http://localhost:9090/cafeteriaRemarks/actions/getCafeteriaRemarks');
        this.cafeteria_remark = response.data.data;
        console.log(this.cafeteria_remark.content);
      } catch (error) {
        console.error('加载评价数据出错', error);
      }
    },
    // 点击回复按钮时调用此方法
    setCurrentComplaint(complaint) {
      console.log(complaint); // 检查传入的数据
      this.currentComplaint = Object.assign({}, complaint);
      this.$nextTick(() => {
        this.dialogFormVisibleForComplaint = true;
      });
    },

    setCurrentRemark(remark) {
      console.log(remark); // 检查传入的数据
      this.currentRemark = Object.assign({}, remark);
      this.$nextTick(() => {
        this.dialogFormVisibleForRemark = true;
      });
    },
    //提交投诉回复
    async submitComplaint() {
      this.currentComplaint.status = 1; // 手动设置状态
      const url = 'http://localhost:9090/complaints/actions/updateComplaint'; // 替换为您的API地址
      const headers = {
        'Content-Type': 'application/json'
      };
      try {
        await this.$request.post(url, this.currentComplaint,{headers});
        console.log('投诉回复提交成功');
        this.dialogFormVisibleForComplaint = false;
        await this.loadComplaint(); // 重新加载投诉数据
        this.updateComplaintNumber(); // 更新待回复投诉数量
        this.currentComplaint = {}; // 重置当前投诉
      } catch (error) {
        console.error('提交失败', error);
      }
    },
    //提交评价回复
    async submitRemark() {
      this.currentRemark.status = 1; // 手动设置状态
      const url = 'http://localhost:9090/cafeteriaRemarks/actions/updatecafeteriaRemark'; // 替换为您的API地址
      // 设置请求头部，指定内容类型为 JSON
      const headers = {
        'Content-Type': 'application/json'
      };
      try {
        await this.$request.post(url, this.currentRemark,{headers});
        console.log('评价回复提交成功');
        this.dialogFormVisibleForRemark = false;
        await this.loadRemark(); // 重新加载评价数据
        this.updateRemarkNumber(); // 更新待回复评价数量
        this.currentRemark = {}; // 重置当前评价
      } catch (error) {
        console.error('提交失败', error);
      }
    },
    renderTime(date) {
      var date = new Date(date).toJSON();
      return new Date(+new Date(date) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    }
  },
  watch: {
    //监听投诉信息变化
    complaint: {
      handler() {
        this.updateComplaintNumber();
      },
      deep: true
    },
    //监听评价信息变化
    cafeteria_remark: {
      handler() {
        this.updateRemarkNumber();
      },
      deep: true
    }
  },
}

</script>

<style>
.item {
  margin-top: 10px;
  margin-right: 40px;
  margin-left: 400px;
}
</style>