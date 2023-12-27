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
            <el-button @click="addCafeteria()">添加食堂</el-button>
          </div>
          <el-dialog :visible.sync="addDialogVisible" title="食堂添加" @close="addDialogVisible = false">
            <div class="input-container">
            <div>
              <label>食堂名称:</label>
              <el-input v-model="newcanteens[1]" style="width:50%"></el-input></div>
            <div>
              <label>食堂地址:</label>
              <el-input v-model="newcanteens[2]" style="width:50%"></el-input>
            </div>
            <div>
              <label>食堂描述:</label>
              <el-input v-model="newcanteens[3]" style="width:50%"></el-input>
            </div>
<!--            <div>-->
<!--              <label>管理员ID:</label>-->
<!--              <el-input v-model="newcanteens[4]" style="width:50%"></el-input>-->
<!--            </div>-->
<!--            <div>-->
<!--              <label>管理员姓名:</label>-->
<!--              <el-input v-model="newcanteens[5]" style="width:50%"></el-input>-->
<!--            </div>-->
            <div>
              <label>开业时间:</label>
              <el-input v-model="newcanteens[6]" style="width:50%"></el-input>
            </div>
            <div>
              <label>歇业时间:</label>
              <el-input v-model="newcanteens[7]" style="width:50%"></el-input>
            </div>
              <div style="display: flex; justify-content: center;">
              <el-button type="primary" @click="addToBackend()">确定</el-button>
              <el-button @click="addDialogVisible = false">取消</el-button>
              </div>
            </div>
          </el-dialog>
          <div v-for="canteen in canteens" :key="canteen.cafeteriaId">
            <div style="display: flex; justify-content: space-between;">
              <p style="margin-right: 10px;">{{ canteen.name }}</p>
              <div>
                <el-button @click="showCanteenInfo(canteen.cafeteriaId)">查看</el-button>
                <el-button @click="decideToDelete(canteen)">删除</el-button>
              </div>
            </div>
          </div>
          <el-dialog :visible.sync="decideDialogVisible" title="食堂信息删除" @close="decideDialogVisible = false">
            <p style="text-align: center; margin-bottom: 20px;font-size: 18px;">确定要删除{{this.tempcanteen.name}}吗</p>
            <div style="text-align: center;">
              <el-button @click="deleteToBackend">确定</el-button>
              <el-button @click="decideDialogVisible = false">取消</el-button>
            </div>
          </el-dialog>
          <!-- el-dialog 用于展示食堂信息和编辑 -->
          <el-dialog :visible.sync="dialogVisible" title="食堂信息编辑" @close="dialogVisible = false">
            <el-table :data="currentCanteenInfo" style="width: 100%">
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
            <div style="display: flex; justify-content: center;">
            <el-button type="primary" @click="saveToBackend">确定</el-button>
              <el-button @click="dialogVisible = false">取消</el-button></div>
          </el-dialog>
        </el-main>

      </el-container>


    </el-container>
  </div>
</template>

<script>

import user from "@/views/manager/User";
import axios from 'axios';
export default {
  name: 'c_cafeteria_info',
  data() {
    return {
      tempcanteen:[],//删除时临时存放选中餐厅信息
      newcanteens:[], //存放新食堂信息
      canteens: [], // 存放从后端获取的多个食堂信息
      decideDialogVisible: false,
      addDialogVisible: false,
      dialogVisible: false,
      editableRowIndex: -1,
      currentCanteenInfo: [], // 存放当前选中食堂的信息
      isCollapse: false,  // 不收缩
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold',
      user: JSON.parse(localStorage.getItem('cafeteria_admin') || '{}'),
    }
  },
  // mounted() {   // 页面加载完成之后触发
  //   if (!this.user.id) {   // 当前的浏览器没有用户信息
  //     this.$router.push('/login')
  //   }
  // },
  created() {
    this.fetchCanteens(); // 获取多个食堂信息
  },
  methods: {
    load(){
      this.fetchCanteens();
    },
    deleteToBackend(){
      this.$request.post('/cafeterias/actions/deleteCafeteria',{
        cafeteriaId:this.tempcanteen.cafeteriaId,
        name:this.tempcanteen.name,
        location:this.tempcanteen.location,
        description:this.tempcanteen.description,
        adminId: this.tempcanteen.adminId,
        adminName:this.tempcanteen.adminName,
        openTime:this.tempcanteen.openTime,
        closeTime:this.tempcanteen. closeTime,
        deleted: this.tempcanteen.deleted,
      })
          .then(response => {
            console.log('Successfully deleted');
            this.$message.success("删除成功");

            this.decideDialogVisible = false; // 关闭
            this.load();

          })
          .catch(error => {
            console.error('Error saving to backend:', error);
          });
    },
    decideToDelete(canteen){
      this.tempcanteen=canteen;
      this.decideDialogVisible = true;
    },
    addCafeteria(){
      this.addDialogVisible = true;
    },
    addToBackend(){
      this.$request.post('/cafeterias/actions/addCafeteria',{
        cafeteriaId:null,
        name:this.newcanteens[1],
        location:this.newcanteens[2],
        description:this.newcanteens[3],
        adminId: this.user.adminId,
        adminName:this.user.name,
        openTime:this.newcanteens[6],
        closeTime:this.newcanteens[7],
        deleted: 0,
      })
          .then(response => {
            console.log('Successfully saved to backend');
            this.$message.success("添加成功");
            this.addDialogVisible = false; // 关闭
            this.load();
          })
          .catch(error => {
            console.error('Error saving to backend:', error);
          });
    },
    saveRow(index) {
      this.editableRowIndex = -1; // 退出编辑状态
    },
    fetchCanteens() {
      this.$request.get('/cafeterias/actions/getCafeteria')
          .then(response => {
            this.canteens = response.data;
          })
          .catch(error => {
            this.$message.error("获取食堂信息失败");
            console.error('Error fetching canteens:', error);
          });
    },
    showCanteenInfo(canteenId) {
      // 根据食堂 ID 获取食堂详细信息
      this.$request.get('/admins/actions/getCafeteriaById',{
        params: {
          id: canteenId
        }
      })
          .then(response => {
            const myObject = response.data; // 将后端返回的食堂信息赋值给 currentCanteenInfo
            this.currentCanteenInfo =[]
            this.currentCanteenInfo.push({ property: '食堂ID', value: myObject.cafeteriaId });
            this.currentCanteenInfo.push({ property: '食堂名称', value: myObject.name });
            this.currentCanteenInfo.push({ property: '食堂位置', value: myObject.location });
            this.currentCanteenInfo.push({ property: '食堂描述', value: myObject.description });
            this.currentCanteenInfo.push({ property: '管理员ID', value: myObject.adminId });
            this.currentCanteenInfo.push({ property: '管理员姓名', value: myObject.adminName });
            this.currentCanteenInfo.push({ property: '开业时间', value: myObject.openTime });
            this.currentCanteenInfo.push({ property: '歇业时间', value: myObject.closeTime });
            console.log(this.currentCanteenInfo)
            this.dialogVisible = true; // 显示 el-dialog
          })
          .catch(error => {
            console.error('Error fetching canteen info:', error);
          });
    },
    saveToBackend() {
      //发送编辑后的食堂信息到后端
      this.$request.post('/cafeterias/actions/updateCafeteria',{
          cafeteriaId:this.currentCanteenInfo[0].value,
          name:this.currentCanteenInfo[1].value,
          location:this.currentCanteenInfo[2].value,
          description:this.currentCanteenInfo[3].value,
          adminId: this.currentCanteenInfo[4].value,
          adminName:this.currentCanteenInfo[5].value,
          openTime:this.currentCanteenInfo[6].value,
          closeTime:this.currentCanteenInfo[7].value,
          deleted:0
      })
          .then(response => {
            console.log('Successfully saved to backend');
            this.$message.success("修改成功");
            this.dialogVisible = false; // 关闭 el-dialog
          })
          .catch(error => {
            this.$message.error("修改失败");
            console.error('Error saving to backend:', error);
          });
    },
    updateUser(user) {   // 获取子组件传过来的数据  更新当前页面的数据
      this.user = JSON.parse(JSON.stringify(user))  // 让父级的对象跟子级的对象毫无关联
    },
    logout() {
      localStorage.removeItem('cafeteria_admin')  // 清除当前的token和用户数据
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
<style>
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
  margin-right: 20px; /* 可选项，控制 label 与 input 之间的间距 */
}

.input-container div el-input {
  flex: 1; /* 每个 el-input 占据父元素 div 的剩余宽度 */
}
</style>