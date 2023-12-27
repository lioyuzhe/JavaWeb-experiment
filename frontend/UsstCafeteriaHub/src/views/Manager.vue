<template>
  <div>
    <el-container>
      <!--    侧边栏  -->
      <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #f8f8f8 ">
        <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center">
          <img src="@/assets/logo2.png" alt="" style="width: 40px; height: 40px">
          <span class="logo-title"   v-show="!isCollapse" style="color: black;">上海理工大学食堂点评交流社区</span>
        </div>

        <el-menu :default-openeds="['info']" :collapse="isCollapse" :collapse-transition="false" router background-color="#f8f8f8"
                 active-text-color="#f8f8f8" style="border: none" :default-active="$route.path">
<!--          <el-menu-item index="/home">-->
<!--            <i class="el-icon-s-home"></i>-->
<!--            <span slot="title">系统首页</span>-->
<!--          </el-menu-item>-->
          <el-submenu index="info" class="el-submenu">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/manager/home" >管理员首页</el-menu-item>
            <el-menu-item index="/manager/user" >用户管理</el-menu-item>
            <el-menu-item index="/manager/cafeteria_manager" >食堂管理员管理</el-menu-item>
            <el-menu-item index="/manager/cafeteria" >食堂管理</el-menu-item>
<!--            <el-menu-item index="/manager/remark" >评价信息管理</el-menu-item>-->
            <el-submenu index="/manager/remark">
              <template slot="title">评价信息管理</template>
              <el-menu-item index="/manager/remark/cafeteria">食堂评价信息管理</el-menu-item>
              <el-menu-item index="/manager/remark/dish">菜品评价信息管理</el-menu-item>
            </el-submenu>
            <el-submenu index="/manager/community">
              <template slot="title">社区信息管理</template>
              <el-menu-item index="/manager/community/community-user">社区用户管理</el-menu-item>
              <el-menu-item index="/manager/community/community-message">社区消息管理</el-menu-item>
            </el-submenu>
<!--            <el-menu-item index="/manager/community" >社区信息管理</el-menu-item>-->
            <el-menu-item index="/manager/person" >个人信息</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

	<!--    主页部分 -->
      <el-container>
        <!--        头部区域-->
        <el-header>

          <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/manager' }">主页</el-breadcrumb-item>
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
                <el-dropdown-item @click.native="$router.push('/manager/person')">个人信息</el-dropdown-item>
                <el-dropdown-item @click.native="$router.push('/manager/password')">修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="$router.push('/login')">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </el-header>

        <!--  路由选择页面的渲染-->
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
        user: JSON.parse(localStorage.getItem('admin') || '{}'),
      }
      },
      mounted() {   // 页面加载完成之后触发
      if (!this.user.id) {   // 当前的浏览器没有用户信息
        // this.$router.push('/login')
      }
      },
      methods: {
      updateUser(user) {   // 获取子组件传过来的数据  更新当前页面的数据
        this.user = JSON.parse(JSON.stringify(user))  // 让父级的对象跟子级的对象毫无关联
      },
      logout() {
        localStorage.removeItem('admin')  // 清除当前的token和用户数据
        // this.$router.push('/login')
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
  background-color: #f8f8f8;
}
.el-menu--inline .el-menu-item {
  background-color: #f8f8f8;
  padding-left: 49px !important;
}
.el-menu-item:hover, .el-submenu__title:hover {
  color: #f8f8f8;
}
.el-submenu__title:hover i {
  color: #f8f8f8;
}
.el-menu-item:hover i {
  color: #f8f8f8;
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
