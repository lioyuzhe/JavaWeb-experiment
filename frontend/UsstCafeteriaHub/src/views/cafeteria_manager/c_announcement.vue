<script>
import axios from "axios";

export default {
  name: "c_announcement",
  data() {
    return {
      tempNotice:[],//删除时临时存放选中公告信息
      newAnnouncement:[], //存放新公告信息
      notices: [], // 存放从后端获取的多个公告信息
      decideDialogVisible: false,
      addDialogVisible: false,
      dialogVisible: false,
      editableRowIndex: -1,
      currentNoticeInfo: [], // 存放当前选中公告的信息
      isCollapse: false,  // 不收缩
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold',
      user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
    }
  },
  // mounted() {   // 页面加载完成之后触发
  //   if (!this.user.id) {   // 当前的浏览器没有用户信息
  //     this.$router.push('/login')
  //   }
  // },
  created() {
    this.fetchnotices(); // 获取多个公告信息
  },
  methods: {
    deleteToBackend(){
      this.$request.post('http://localhost:9090/cafeteriaNotices/actions/deleteCafeteriaNotice',{
        noticeId:this.tempNotice.noticeId,
        cafeteriaId:this.tempNotice.cafeteriaId,
        cafeteriaName:this.tempNotice.cafeteriaName,
        title:this.tempNotice.title,
        content: this.tempNotice.content,
        createTime:this.tempNotice.createTime,
        deleted:this.tempNotice.deleted,
      })
          .then(response => {
            console.log('Successfully deleted');
            this.$message.success("删除成功");
            this.decideDialogVisible = false; // 关闭
          })
          .catch(error => {
            console.error('Error saving to backend:', error);
          });
    },
    decideToDelete(notice){
      this.tempNotice=notice;
      this.decideDialogVisible = true;
    },
    addAnnouncement(){
      this.addDialogVisible = true;
    },
    addToBackend(){
      this.$request.post('http://localhost:9090/cafeteriaNotices/actions/addCafeteriaNotice',{
        noticeId:this.newAnnouncement[0],
        cafeteriaId:this.newAnnouncement[1],
        cafeteriaName:this.newAnnouncement[2],
        title:this.newAnnouncement[3],
        content: this.newAnnouncement[4],
        createTime:this.newAnnouncement[5],
        deleted:this.newAnnouncement[6],
      })
          .then(response => {
            console.log('Successfully saved to backend');
            this.$message.success("添加成功");
            this.addDialogVisible = false; // 关闭
          })
          .catch(error => {
            console.error('Error saving to backend:', error);
          });
    },
    saveRow(index) {
      this.editableRowIndex = -1; // 退出编辑状态
    },
    fetchnotices() {
      axios.get('http://localhost:9090/cafeteriaNotices/actions/getCafeteriaNotices')
          .then(response => {
            this.notices = response.data.data;
          })
          .catch(error => {
            console.error('Error fetching canteens:', error);
          });
    },
    showCanteenInfo(canteenId,noticeId) {
      // 根据公告 ID 获取公告详细信息
      axios.get('http://localhost:9090/cafeteriaNotices/actions/getCafeteriaNoticesByCafeteriaID',{
        params: {
          id: canteenId
        }
      })
          .then(response => {
            const myObject = response.data.data; // 将后端返回的公告信息赋值给 currentNoticeInfo
            console.log(noticeId)
            for(let i=0;i<myObject.length;i++){
              if(myObject[i].noticeId === noticeId){
                this.currentNoticeInfo =[]
                this.currentNoticeInfo.push({ property: 'noticeId', value: myObject[i].noticeId });
                this.currentNoticeInfo.push({ property: 'cafeteriaId', value: myObject[i].cafeteriaId });
                this.currentNoticeInfo.push({ property: 'cafeteriaName', value: myObject[i].cafeteriaName });
                this.currentNoticeInfo.push({ property: 'title', value: myObject[i].title });
                this.currentNoticeInfo.push({ property: 'content', value: myObject[i].content });
                this.currentNoticeInfo.push({ property: 'createTime', value: myObject[i].createTime });
                this.currentNoticeInfo.push({ property: 'deleted', value: myObject[i].deleted });
              }
          }

            this.dialogVisible = true; // 显示 el-dialog
          })
          .catch(error => {
            console.error('Error fetching canteen info:', error);
          });
    },
    saveToBackend() {
      //发送编辑后的公告信息到后端
      this.$request.post('http://localhost:9090/cafeteriaNotices/actions/updateCafeteriaNotice',{
        noticeId:this.currentNoticeInfo[0].value,
        cafeteriaId:this.currentNoticeInfo[1].value,
        cafeteriaName:this.currentNoticeInfo[2].value,
        title:this.currentNoticeInfo[3].value,
        content: this.currentNoticeInfo[4].value,
        createTime:this.currentNoticeInfo[5].value,
        deleted:this.currentNoticeInfo[6].value,
      })
          .then(response => {
            console.log('Successfully saved to backend');
            this.$message.success("修改成功");
            this.dialogVisible = false; // 关闭 el-dialog
          })
          .catch(error => {
            console.error('Error saving to backend:', error);
          });
    },
    updateUser(user) {   // 获取子组件传过来的数据  更新当前页面的数据
      this.user = JSON.parse(JSON.stringify(user))  // 让父级的对象跟子级的对象毫无关联
    },
    logout() {
      localStorage.removeItem('honey-user')  // 清除当前的token和用户数据
      this.$router.push('/login')
    },
    handleFull() {
      document.documentElement.requestFullscreen()
    },
    handleCollapse() {
      this.isCollapse = !this.isCollapse
      this.asideWidth = this.isCollapse ? '64px' : '200px'
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
    }
  }
}
</script>

<template>
  <div>
    <el-container>
      <!--    侧边栏  -->

      <el-container>
        <!--        头部区域-->

        <!--        主体区域-->
        <el-main>
          <router-view @update:user="updateUser" />
          <div>
            <el-button @click="addAnnouncement()">添加公告</el-button>
          </div>
          <el-dialog :visible.sync="addDialogVisible" title="添加公告" @close="addDialogVisible = false">
            <div class="input-container">
              <div>
                <label>noticeId:</label>
                <el-input v-model="newAnnouncement[0]" style="width:50%"></el-input>
              </div>
              <div>
                <label>cafeteriaId:</label>
                <el-input v-model="newAnnouncement[1]" style="width:50%"></el-input></div>
              <div>
                <label>cafeteriaName:</label>
                <el-input v-model="newAnnouncement[2]" style="width:50%"></el-input>
              </div>
              <div>
                <label>title:</label>
                <el-input v-model="newAnnouncement[3]" style="width:50%"></el-input>
              </div>
              <div>
                <label>content:</label>
                <el-input v-model="newAnnouncement[4]" style="width:50%"></el-input>
              </div>
              <div>
                <label>createTime:</label>
                <el-input v-model="newAnnouncement[5]" style="width:50%"></el-input>
              </div>
              <div>
                <label>deleted:</label>
                <el-input v-model="newAnnouncement[6]" style="width:50%"></el-input>
              </div >
              <div style="display: flex; justify-content: center;">
              <el-button type="primary" @click="addToBackend()">确定</el-button>
              <el-button @click="addDialogVisible = false">取消</el-button>
              </div>
            </div>
          </el-dialog>
          <div v-for="notice in notices" :key="notice.noticeId">
            <div style="display: flex; justify-content: space-between;">
              <p style="margin-right: 10px;">{{ notice.title }}</p>
              <div>
                <el-button @click="showCanteenInfo(notice.cafeteriaId,notice.noticeId)">修改</el-button>
                <el-button @click="decideToDelete(notice)">删除</el-button>
              </div>
            </div>
          </div>
          <el-dialog :visible.sync="decideDialogVisible" title="公告信息删除" @close="decideDialogVisible = false">
            <p style="text-align: center; margin-bottom: 20px;font-size: 18px;">确定要删除{{this.tempNotice.title}}吗</p>
            <div style="text-align: center;">
              <el-button @click="deleteToBackend">确定</el-button>
              <el-button @click="decideDialogVisible = false">取消</el-button>
            </div>
          </el-dialog>
          <!-- el-dialog 用于展示公告信息和编辑 -->
          <el-dialog :visible.sync="dialogVisible" title="公告信息编辑" @close="dialogVisible = false">
            <el-table :data="currentNoticeInfo" style="width: 100%">
              <el-table-column prop="property" label="属性"></el-table-column>
              <el-table-column prop="value" label="信息">
                <template v-slot="scope">
                  <div v-if="scope.$index === editableRowIndex">
                    <el-input v-model="scope.row.value" @blur="saveRow(scope.$index)"></el-input>
                  </div>
                  <div v-else @click="editableRowIndex = scope.$index">{{ scope.row.value }}</div>
                </template>
              </el-table-column>
            </el-table>
            <div style="text-align: center;">
            <el-button type="primary" @click="saveToBackend">修改</el-button>
            <el-button @click="dialogVisible = false">取消</el-button>
            </div>
          </el-dialog>
        </el-main>

      </el-container>


    </el-container>
  </div>
</template>

<style scoped>
.el-menu--inline {
  background-color: #000c17 !important;
}
.el-menu--inline .el-menu-item {
  background-color: #000c17 !important;
  padding-left: 49px !important;
}
.el-menu-item:hover, .el-submenu__title:hover {
  color: #fff !important;
}
.el-submenu__title:hover i {
  color: #fff !important;
}
.el-menu-item:hover i {
  color: #fff !important;
}
.el-menu-item.is-active {
  background-color: #1890ff !important;
  border-radius: 5px !important;
  width: calc(100% - 8px);
  margin-left: 4px;
}
.el-menu-item.is-active i, .el-menu-item.is-active .el-tooltip{
  margin-left: -4px;
}
.el-menu-item {
  height: 40px !important;
  line-height: 40px !important;
}
.el-submenu__title {
  height: 40px !important;
  line-height: 40px !important;
}
.el-submenu .el-menu-item {
  min-width: 0 !important;
}
.el-menu--inline .el-menu-item.is-active {
  padding-left: 45px !important;
}
.input-container {
  display: flex;
  flex-direction: column;
}

.input-container div {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  margin-bottom: 10px; /* 控制每个 div 之间的间距 */
}

.input-container div label {
  width: 100px; /* 设定 label 宽度，确保对齐 */
  margin-right: 20px;
}

.input-container div el-input {
  flex: 1; /* 每个 el-input 占据父元素 div 的剩余宽度 */
}
</style>