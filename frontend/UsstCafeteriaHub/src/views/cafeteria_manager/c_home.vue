<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
      欢迎登录上海理工大学食堂点评交流社区 管理员页面，<strong>{{ cafeteria_admin.name }}</strong>，祝你开心每一天！
    </div>

    <div style="display: flex">

      <el-card style="width: 100%; margin-right: 10px">
        <div style="margin-bottom: 15px; font-size: 20px; font-weight: bold">系统公告</div>
        <el-timeline style="padding: 0">
          <el-timeline-item v-for="item in complaint" :key="item.id" :timestamp="item.time" placement="top">
            <el-card>
              <h4>{{ item.username }}</h4>
              <div v-html="item.content"></div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-card>

    </div>

    <div style="display: flex; margin: 15px 0">

      <el-card style="width: 50%">
        <div style="margin-bottom: 15px; font-size: 20px; font-weight: bold">
          投诉栏

          <!--           待回复-->
          <el-badge :value="complaint_number" class="item">
            <el-button size="small">待回复</el-button>
          </el-badge>
        </div>

        <el-collapse v-model="activeName1" accordion>
          <el-collapse-item v-for="(item, index) in filteredComplaints" :key="item.complaint_id" :name="index + ''">
            <template slot="title">
              <div style="display: flex; align-items: center; width: 100%">
                <h4 style="flex: 1">{{ item.username }}</h4>
                <div style="width: 150px; color: #888">{{ item.time }}</div>
              </div>
            </template>
            <div v-html="item.content"></div>

            <!-- Form -->
            <el-button type="text" @click="dialogFormVisible = true">回复</el-button>

            <el-dialog title="回复" :visible.sync="dialogFormVisible">
              <el-form>
                <el-form-item label="回复人" :label-width="formLabelWidth">
                  <el-input v-model="cafeteria_admin.name" :disabled="true" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="接收人" :label-width="formLabelWidth">
                  <el-input v-model="item.username" :disabled="true" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="回复内容" :label-width="formLabelWidth">
                  <el-input type="textarea" :rows="6" placeholder="请输入内容"
                            v-model="cafeteria_remark.reply"></el-input>
                </el-form-item>
                <!--                <el-form-item label="活动区域" :label-width="formLabelWidth">-->
                <!--                  <el-select v-model="form.region" placeholder="请选择活动区域">-->
                <!--                    <el-option label="区域一" value="shanghai"></el-option>-->
                <!--                    <el-option label="区域二" value="beijing"></el-option>-->
                <!--                  </el-select>-->
                <!--                </el-form-item>-->
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
              </div>
            </el-dialog>
          </el-collapse-item>
        </el-collapse>
      </el-card>

      <el-card style="width: 50%">
        <div style="margin-bottom: 15px; font-size: 20px; font-weight: bold">
          评价栏

          <!--           待回复-->
          <el-badge :value="remark_number" class="item">
            <el-button size="small">待回复</el-button>
          </el-badge>
        </div>

        <el-collapse v-model="activeName2" accordion>
          <el-collapse-item v-for="(item, index) in filteredRemarks" :key="item.remark_id" :name="index + ''">
            <template slot="title">
              <div style="display: flex; align-items: center; width: 100%">
                <h4 style="flex: 1">{{ item.username }}</h4>
                <div style="width: 150px; color: #888">{{ item.createtime }}</div>
              </div>
            </template>
            <div v-html="item.content"></div>

            <!-- Form -->
            <el-button type="text" @click="dialogFormVisible = true">回复</el-button>

            <el-dialog title="回复" :visible.sync="dialogFormVisible">
              <!--              <el-form :model="reply_form">-->
              <el-form>
                <el-form-item label="回复人" :label-width="formLabelWidth">
                  <el-input v-model="cafeteria_admin.name" :disabled="true" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="接收人" :label-width="formLabelWidth">
                  <el-input v-model="item.username" :disabled="true" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="回复内容" :label-width="formLabelWidth">
                  <el-input type="textarea" :rows="6" placeholder="请输入内容"
                            v-model="cafeteria_remark.reply"></el-input>
                </el-form-item>

              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
              </div>
            </el-dialog>
          </el-collapse-item>
        </el-collapse>
      </el-card>
    </div>

  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      // user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      cafeteria_admin: {
        adminId: 0,
        account: "string",
        name: "昕哥哥",
        password: "string",
        avatar: "string",
        email: "string",
        phone: "string",
        createTime: "string",
        deleted: 0
      },
      complaint: [
        {
          complaint_id: 0,
          user_id: 0,
          username: "小璇",
          cafeteria_id: 0,
          cafeteria_name: "五食堂",
          content: "月入过万！",
          statue: 0,
          reply: "",
          time: "2010-01-01 12:00:00",
          //title: "昕哥做前端"
        },
        {
          complaint_id: 1,
          user_id: 0,
          username: "小璇",
          cafeteria_id: 0,
          cafeteria_name: "五食堂",
          content: "月入过万！",
          statue: 0,
          reply: "",
          time: "2010-01-01 12:00:00",
          //title: "昕哥做前端"
        }
      ],
      cafeteria_remark: [
        {
          remark_id: 0,
          cafeteria_id: 0,
          cafeteria_name: "五食堂",
          user_id: 0,
          username: "小璇",
          content: "月入过万！",
          statue: 0,
          reply: "",
          createtime: "2010-01-01 12:00:00",
          deleted: "",
          //title: "昕哥做前端"
        },
        {
          remark_id: 1,
          cafeteria_id: 0,
          cafeteria_name: "五食堂",
          user_id: 0,
          username: "小璇",
          content: "月入过万！",
          statue: 1,
          reply: "",
          createtime: "2010-01-01 12:00:00",
          deleted: "",
          //title: "昕哥做前端"
        }
      ],
      complaint_number: 0,
      remark_number: 0,
      formLabelWidth: '120px',
      dialogTableVisible: false,
      dialogFormVisible: false,
      activeName1: '0',
      activeName2: '0'
    }
  },
  computed: {
    filteredComplaints() {
      return this.complaint.filter(item => item.statue === 0);
    },
    filteredRemarks() {
      return this.cafeteria_remark.filter(item => item.statue === 0);
    }
  },
  methods: {
    updateComplaintNumber() {
      this.complaint_number = this.filteredComplaints.length;
    },
    updateRemarkNumber() {
      this.remark_number = this.filteredRemarks.length;
    }
  },
  watch: {
    complaint: {
      handler() {
        this.updateComplaintNumber();
      },
      deep: true
    },
    cafeteria_remark: {
      handler() {
        this.updateRemarkNumber();
      },
      deep: true
    }
  },
  mounted() {
    this.updateComplaintNumber();
    this.updateRemarkNumber();
  },
  // created() {
  //   this.loadNotice()
  // },
  // methods: {
  //   loadNotice: function () {
  //     this.$request.get('/notice/selectUserData').then(res => {
  //       this.notices = res.data
  //     })
  //   }
  // }
}
</script>

<style>
.item {
  margin-top: 10px;
  margin-right: 40px;
  margin-left: 600px;
}
</style>