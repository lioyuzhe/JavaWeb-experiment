import axios from 'axios'
import router from "@/router";

// 创建可一个新的axios对象
const request = axios.create({
    baseURL: process.env.VUE_APP_BASEURL,   // 后端的接口地址 在.env.development文件夹中
    timeout: 30000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let user = JSON.parse(localStorage.getItem("user") || '{}')
    let admin = JSON.parse(localStorage.getItem("admin") || '{}')
    let cafeteria_admin = JSON.parse(localStorage.getItem("cafeteria_admin") || '{}')
    if (user.token == null && admin.token == null && cafeteria_manager.token == null) {
        $message.error('请先登录');
        router.push('/login')
    }
    let role = JSON.parse(localStorage.getItem("role") || '{}')
    if (role===2 || role ===3 ) config.headers['token'] = user.token  // 设置请求头
    if (role === 0) config.headers['token'] = admin.token  // 设置请求头
    if (role === 1 ) config.headers['token'] = cafeteria_admin.token  // 设置请求头
    return config
}, error => {
    console.error('request error: ' + error) // for debug
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;

        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        // if (res.code === '401') {
        //     router.push('/login')
        // }
        return res;
    },
    error => {
        console.error('response error: ' + error) // for debug
        return Promise.reject(error)
    }
)


export default request