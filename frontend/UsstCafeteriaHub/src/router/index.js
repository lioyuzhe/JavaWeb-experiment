import Vue from 'vue'
import VueRouter from 'vue-router'

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/ts_cafeteria_content',
    name: 'ts_cafeteria_content',
    component: () => import('../views/ts/ts_cafeteria_content.vue') // 根据你的实际文件路径
  },
  {
    path: '/manager',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: 'manager/home',  // 重定向到主页
    children: [
      { path: '403', name: 'Auth', meta: { name: '无权限' }, component: () => import('../views/manager/Auth') },
      { path: 'home', name: 'Home', meta: { name: '管理员首页' }, component: () => import('../views/manager/Home') },
      { path: 'user', name: 'User', meta: { name: '用户管理' }, component: () => import('../views/manager/User') },
      { path: 'cafeteria_manager', name: 'CadfeteriaManager', meta: { name: '食堂管理员管理' }, component: () => import('../views/manager/CafeteriaManager') },
       { path: 'cafeteria', name: 'Cadfeteria', meta: { name: '食堂管理' }, component: () => import('../views/manager/Cafeteria') },
      {
        path: 'remark',
        name: 'Remark',
        meta: { name: '评价信息管理' },
        component: () => import('../views/manager/Remark'),
      },
      {
        path: 'remark/cafeteria',
        name: 'CafeteriaRemark',
        meta: { name: '食堂评价信息管理' },
        component: () => import('../views/manager/CafeteriaRemark.vue')
      },
      {
        path: 'remark/dish',
        name: 'DishRemark',
        meta: { name: '菜品评价信息管理' },
        component: () => import('../views/manager/DishRemark.vue')
      } ,
       { path: 'community', name: 'Community', meta: { name: '社区信息管理' }, component: () => import('../views/manager/Community') },
      {
        path: 'community/community-user',
        name: 'CommunityUser',
        meta: { name: '社区用户管理' },
        component: () => import('../views/manager/CommunityUser')
      },
      {
        path: 'community/community-message',
        name: 'CommunityMessage',
        meta: { name: '社区消息管理' },
        component: () => import('../views/manager/CommunityMessage')
      },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/manager/Person') },
    ]
  },
  {
    path: '/cafeteria_manager',
    name: 'cafeteria_manager',
    component: () => import('../views/CafeteriaManager.vue'),
    redirect: 'cafeteria_manager/home',  // 重定向到主页
    children: [
      { path: '403', name: 'Auth', meta: { name: '无权限' }, component: () => import('../views/cafeteria_manager/Auth') },
      { path: 'home', name: 'c_home', meta: { name: '食堂管理员首页' }, component: () => import('../views/cafeteria_manager/c_home.vue') },
      { path: 'cafeteria_info', name: 'c_cafeteria_info', meta: { name: '食堂管理' }, component: () => import('../views/cafeteria_manager/c_cafeteria_info.vue') },
      { path: 'cafeteria_dish', name: 'c_cafeteria_dish', meta: { name: '菜品管理' }, component: () => import('../views/cafeteria_manager/c_dish.vue') },
      { path: 'cafeteria_remark', name: ' c_remark',meta: {name: '食堂评价管理' },component: () => import('../views/cafeteria_manager/c_remark.vue') },
      { path: 'announcement',name : 'announcement',meta: {name: '活动公告'},component: () => import('../views/cafeteria_manager/c_announcement.vue') },
      { path: 'cafeteria_vote', name: 'c_cafeteria_vote', meta: { name: '投票信息管理' }, component: () => import('../views/cafeteria_manager/c_vote.vue') },
      { path: 'cafeteria_complaint', name: 'c_cafeteria_complaint', meta: { name: '投诉管理' }, component: () => import('../views/cafeteria_manager/c_complaint.vue') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: '404', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
//   xuyang 的测试
  { path: '/test', name: 'test', meta: { name: '食堂管理' }, component: () => import('../views/cafeteria_manager/c_cafeteria_info.vue') },
// 师生 teacher and student  有冲突我把你们的都保留了，加了注释 ，你们自己选择要不要修改  -- 李英杰  
  {path : '/',redirect: '/login'},

  {
    path: '/ts',
    name: 'ts',
    component: () => import('../views/Ts.vue'),
    children: [

      { path: 'ts_home', name: 'ts_home', meta: { name: '师生首页' }, component: () => import('../views/ts/ts_home'), props: (route) => ({ userInfo: route.params.user }) },
      { path: 'ts_community', name: 'ts_community', meta: { name: '师生社区' }, component: () => import('../views/ts/ts_community'), props: (route) => ({ userInfo: route.params.user }) },
      { path: 'ts_community_dynamic', name: 'ts_community_dynamic', meta: { name: '师生社区动态' }, component: () => import('../views/ts/ts_community_dynamic'),props: (route) => ({ userInfo: route.params.user }) },
      {
        path: '/',
        name: 'ts_cafeteria',
        meta: { name: '师生食堂' },
        component: () => import('../views/ts/ts_cafeteria.vue'),
        children: [
          {path: 'ts_cafeteria_introduction', name: 'ts_cafeteria_introduction', meta: { name: '师生食堂介绍' }, component: () => import('../views/ts/ts_cafeteria/ts_cafeteria_introduction.vue'), props: (route) => ({ userInfo: route.params.user })},
          {path: 'ts_cafeteria_dish', name: 'ts_cafeteria_dish', meta: { name: '师生食堂菜品' }, component: () => import('../views/ts/ts_cafeteria/ts_cafeteria_dish.vue'), props: (route) => ({ userInfo: route.params.user })},
          {path: 'ts_cafeteria_complaint', name: 'ts_cafeteria_complaint', meta: { name: '师生食堂投诉' }, component: () => import('../views/ts/ts_cafeteria/ts_cafeteria_complaint.vue'), props: (route) => ({ userInfo: route.params.user })},
          {path: 'ts_cafeteria_vote', name: 'ts_cafeteria_vote', meta: { name: '师生食堂投票' }, component: () => import('../views/ts/ts_cafeteria/ts_cafeteria_vote.vue'), props: (route) => ({ userInfo: route.params.user })}
        ]
      },
      {
        path: '/',
        name: 'ts_cafeteria',
        meta: { name: '师生食堂' },
        component: () => import('../views/ts/ts_cafeteria.vue'),
        children: [
          // ...已有的子路由...
          {
            path: 'ts_cafeteria_content', // 修改这里
            name: 'ts_cafeteria_content',
            meta: { name: '师生食堂内容' },
            component: () => import('../views/ts/ts_cafeteria_content.vue')
          }
        ],
        props: (route) => ({ userInfo: route.params.user })
      }
    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {
  // to 是到达的路由信息
  // from 是开源的路由信息
  // next 是帮助我们跳转路由的函数
  let adminPaths = ['/user']
  let user = JSON.parse(localStorage.getItem('honey-user') || '{}')

  if (user.role !== '管理员' && adminPaths.includes(to.path)) {
    // 如果当前登录的用户不是管理员，然后当前的到达的路径是管理员才有权限访问的路径，那这个时候我就让用户去到一个没有权限的页面，不让他访问实际的页面
    next('/403')
  } else {
    next()
  }
})

export default router
