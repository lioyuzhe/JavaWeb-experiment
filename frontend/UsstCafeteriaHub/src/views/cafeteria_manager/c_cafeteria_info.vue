<template>
  <div>
    <el-container>
      <!--    侧边栏  -->
      <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #001529">
        <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center">
          <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px">
          <span class="logo-title" v-show="!isCollapse">honey2024</span>
        </div>

        <el-menu :default-openeds="['info']" :collapse="isCollapse" :collapse-transition="false" router background-color="#001529" text-color="rgba(255, 255, 255, 0.65)"
                 active-text-color="#fff" style="border: none" :default-active="$route.path">
          <el-menu-item index="/home">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu index="info" >
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/user" v-if="user.role === '管理员'">用户信息</el-menu-item>
            <el-menu-item index="/news">新闻信息</el-menu-item>
            <el-menu-item index="/notice" v-if="user.role === '管理员'">系统公告</el-menu-item>
            <el-menu-item index="/logs" v-if="user.role === '管理员'">系统日志</el-menu-item>
            <el-menu-item index="/charts" v-if="user.role === '管理员'">数据统计</el-menu-item>
            <el-menu-item index="/orders">订单管理</el-menu-item>
            <el-menu-item index="/test">食堂信息</el-menu-item>
          </el-submenu>
        </el-menu>

      </el-aside>

      <el-container>
        <!--        头部区域-->
        <el-header>

          <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">主页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
            <i class="el-icon-quanping" style="font-size: 26px" @click="handleFull"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: default">
                <img :src="user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" alt="" style="width: 40px; height: 40px; border-radius: 50%; margin: 0 5px">
                <span>{{ user.name }}</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="$router.push('/person')">个人信息</el-dropdown-item>
                <el-dropdown-item @click.native="$router.push('/password')">修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </el-header>

        <!--        主体区域-->
        <el-main>
          <router-view @update:user="updateUser" />
          <div v-for="canteen in canteens" :key="canteen.cafeteriaId">
            <p>{{ canteen.name }}
              <el-button @click="showCanteenInfo(canteen.cafeteriaId)">查看</el-button></p>
          </div>

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
            <el-button type="primary" @click="saveToBackend">确定</el-button>
            <el-button @click="dialogVisible = false">取消</el-button>
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
  name: 'HomeView',
  data() {
    return {
      canteens: [], // 存放从后端获取的多个食堂信息
      dialogVisible: false,
      editableRowIndex: -1,
      currentCanteenInfo: [], // 存放当前选中食堂的信息
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
    this.fetchCanteens(); // 获取多个食堂信息
  },
  methods: {
    saveRow(index) {
      this.editableRowIndex = -1; // 退出编辑状态
    },
    fetchCanteens() {
      axios.get('http://localhost:9090/cafeterias/actions/getCafeteria')
          .then(response => {
            this.canteens = response.data.data;
          })
          .catch(error => {
            console.error('Error fetching canteens:', error);
          });
    },
    showCanteenInfo(canteenId) {
      // 根据食堂 ID 获取食堂详细信息
      axios.get('http://localhost:9090/admins/actions/getCafeteriaById',{
        params: {
          id: canteenId
        }
      })
          .then(response => {
            const myObject = response.data.data; // 将后端返回的食堂信息赋值给 currentCanteenInfo
            this.currentCanteenInfo =[]
            this.currentCanteenInfo.push({ property: 'cafeteriaId', value: myObject.cafeteriaId });
            this.currentCanteenInfo.push({ property: 'name', value: myObject.name });
            this.currentCanteenInfo.push({ property: 'location', value: myObject.location });
            this.currentCanteenInfo.push({ property: 'description', value: myObject.description });
            this.currentCanteenInfo.push({ property: 'adminId', value: myObject.adminId });
            this.currentCanteenInfo.push({ property: 'adminName', value: myObject.adminName });
            this.currentCanteenInfo.push({ property: 'openTime', value: myObject.openTime });
            this.currentCanteenInfo.push({ property: 'closeTime', value: myObject.closeTime });
            this.currentCanteenInfo.push({ property: 'deleted', value: myObject.deleted });
            console.log(this.currentCanteenInfo)
            this.dialogVisible = true; // 显示 el-dialog
          })
          .catch(error => {
            console.error('Error fetching canteen info:', error);
          });
    },
    saveToBackend() {
      //发送编辑后的食堂信息到后端
      axios.post('http://localhost:9090/cafeterias/actions/updateCafeteria',{
          cafeteriaId:this.currentCanteenInfo[0].value,
          name:this.currentCanteenInfo[1].value,
          location:this.currentCanteenInfo[2].value,
          description:this.currentCanteenInfo[3].value,
          adminId: this.currentCanteenInfo[4].value,
          adminName:this.currentCanteenInfo[5].value,
          openTime:this.currentCanteenInfo[6].value,
          closeTime:this.currentCanteenInfo[7].value,
          deleted: this.currentCanteenInfo[8].value
      })
          .then(response => {
            console.log('Successfully saved to backend:');
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