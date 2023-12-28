<template>
    <div>
      <el-container>
        <!--    侧边栏  -->
        <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #f8f8f8">
          <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center">
            <img src="@/assets/logo2.png" alt="" style="width: 40px; height: 40px">
            <span class="logo-title" v-show="!isCollapse" style="color: black;">上海理工大学食堂点评交流社区</span>
          </div>
          <el-menu :default-openeds="['info']" :collapse="isCollapse" :collapse-transition="false" router background-color="#f8f8f8"
                   active-text-color="gray" style="border: none" :default-active="$route.path">
<!--            <el-menu-item index="/home">-->
<!--              <i class="el-icon-s-home"></i>-->
<!--              <span slot="title">系统首页</span>-->
<!--            </el-menu-item>-->
            <el-submenu index="info" class="el-submenu">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>信息管理</span>
              </template>
              <el-menu-item index="/cafeteria_manager/home" >食堂管理员首页</el-menu-item>
              <el-menu-item index="/cafeteria_manager/cafeteria_info">食堂管理</el-menu-item>
              <el-menu-item index="/cafeteria_manager/cafeteria_dish" >菜品管理</el-menu-item>
              <el-menu-item index="/cafeteria_manager/cafeteria_remark" >食堂评价管理</el-menu-item>
              <el-menu-item index="/cafeteria_manager/announcement" >活动公告</el-menu-item>
              <el-menu-item index="/cafeteria_manager/cafeteria_vote" >投票信息管理</el-menu-item>
              <el-menu-item index="/cafeteria_manager/cafeteria_complaint" >投诉管理</el-menu-item>
<!--              添加一个个人信息管理页面-->
              <el-menu-item index="/cafeteria_manager/person" >个人信息管理</el-menu-item>
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
          </el-main>
  
        </el-container>
  
  
      </el-container>
    </div>
  </template>
  
  <script>
  import user from "@/views/manager/User";
  
  export default {
    name: 'HomeView',
    data() {
      return {
        isCollapse: false,  // 不收缩
        asideWidth: '200px',
        collapseIcon: 'el-icon-s-fold',
        user: JSON.parse(localStorage.getItem('cafeteria_admin') || '{}'),
      }
    },
    mounted() {   // 页面加载完成之后触发
      if (!this.user.adminId) {   // 当前的浏览器没有用户信息
        this.$router.push('/login')
      }
    },
    methods: {
      updateUser(user) {   // 获取子组件传过来的数据  更新当前页面的数据
        this.user = JSON.parse(JSON.stringify(user))  // 让父级的对象跟子级的对象毫无关联
      },
      logout() {
        localStorage.removeItem('cafeteria_admin')  // 清除当前的token和用户数据
        localStorage.removeItem('role')
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
.el-menu-item {
  color: #000000;
  background-color: #f8f8f8;
  padding-left: 49px !important;
}
.el-menu-item:hover, .el-submenu__title:hover {
  color: #000000;
}
.el-submenu__title:hover i {
  color: #000000;
}
.el-menu-item:hover i {
  color: #000000;
}
.el-menu-item.is-active {
  color: #6e77f2;
  background-color: #ffff00;
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
/*.el-submenu__icon-arrow {*/
/*  margin-top: -5px;*/
/*}*/

.el-aside {
  transition: width .3s;
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
}
.logo-title {
  margin-left: 5px;
  font-size: 20px;
  transition: all .3s;   /* 0.3s */
}
.el-header {
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  display: flex;
  align-items: center;
}

.el-submenu__icon-arrow {
  background-color: #f8f8f8;
  margin-top: -5px;
}
</style>
