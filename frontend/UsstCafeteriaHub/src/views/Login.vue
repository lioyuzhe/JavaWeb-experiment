<template>
  <body><div style="height: 100vh; display: flex; align-items: center; justify-content: center">
    <div class="right-container">
      <div style="height: 80%;display: flex;  background-color: rgba(0, 0, 0, 0.66); ; width: 30%; border-radius: 5px; overflow: hidden">
        <div style="flex: 1; display: flex; align-items: center; justify-content: center;">
          <el-form :model="user" style="width: 50%" :rules="rules" ref="loginRef">
<!--            <div style="flex: 1">-->
<!--              <img src="@/assets/usst_logo.jpg" alt="" style="width: 100%">-->
<!--            </div>-->
            <div style="display: flex; justify-content: space-between; align-items: center;">
              <img src="@/assets/usst_logo.png" alt="" style="width: 45%;">
              <div style="font-size: 30px; font-weight: bold; text-align: center; margin-bottom: 20px; color: #FFFFFF;">
                上理点评
              </div>
            </div>
            <div class="analysis">
              上海理工大学食堂点评分析平台
            </div>


            <el-form-item prop="account">
              <el-input class="my-input" prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" style="background-color: #f5f5f5;" v-model="user.account"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input class="my-input" prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" style="width: 250px" v-model="user.password"></el-input>
            </el-form-item>
            <el-form-item prop="code">
              <div style="display: flex;width: 250px">
                <el-input placeholder="验证码" prefix-icon="el-icon-circle-check" size="medium" style="flex: 1;width: 150px" v-model="user.code"></el-input>
                <div style="flex: 1; height: 36px;width: 100px;background-color: #8c939d">
                  <valid-code @update:value="getCode" />
                </div>
              </div>
            </el-form-item>
            <el-radio-group v-model="user.role" style="display: flex; justify-content: center; width: 60%; margin: auto; font-size: 20px;">
              <el-radio :label="0" style="background-color: transparent; border-color: #8c939d; text-align: center;">系统管理员</el-radio>
              <el-radio :label="1" style="background-color: transparent; border-color: #8c939d; text-align: center;">食堂管理员</el-radio>
              <el-radio :label="2" style="background-color: transparent; border-color: #8c939d; text-align: center;">学生</el-radio>
              <el-radio :label="3" style="background-color: transparent; border-color: #8c939d; text-align: center;">老师</el-radio>
            </el-radio-group>


            <!--            <el-tabs v-model="activeName" @tab-click="handleClick">-->
            <!--              <el-tab-pane label="用户管理" name="first">用户管理</el-tab-pane>-->
            <!--              <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>-->
            <!--              <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>-->
            <!--              <el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane>-->
            <!--            </el-tabs>-->
            <el-form-item>
              <el-button type="primary" style="width: 250px" @click="login">登 录</el-button>
            </el-form-item>
            <div style="display: flex">
              <div  class="regist" style="flex: 1" >还没有账号？请 <span style="color: #0f9876; cursor: pointer" @click="$router.push('/register')">注册</span></div>
              <!--            <div style="flex: 1; text-align: right"><span style="color: #0f9876; cursor: pointer" @click="handleForgetPass">忘记密码</span></div>-->
            </div>
          </el-form>
        </div>
      </div>

      <!--    <el-dialog title="忘记密码" :visible.sync="forgetPassDialogVis" width="30%">-->
      <!--      <el-form :model="forgetUserForm" label-width="80px" style="padding-right: 20px">-->
      <!--        <el-form-item label="用户名">-->
      <!--          <el-input v-model="forgetUserForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>-->
      <!--        </el-form-item>-->
      <!--        <el-form-item label="密码">-->
      <!--          <el-input v-model="forgetUserForm.phone" autocomplete="off" placeholder="请输入手机号"></el-input>-->
      <!--        </el-form-item>-->
      <!--      </el-form>-->
      <!--      <div slot="footer" class="dialog-footer">-->
      <!--        <el-button @click="forgetPassDialogVis = false">取 消</el-button>-->
      <!--        <el-button type="primary" @click="resetPassword">确 定</el-button>-->

      <!--      </div>-->

      <!--    </el-dialog>-->

    </div>
  </div>
  <div class="text-box">
    <div class="my-input-text" style="font-size: 30px;">
      欢迎来到上理点评
    </div>
    <div class="my-input-text" style="font-size: 15px">
      <p>上海理工大学食堂在线点评网站</p>该系统的重要功能：
    </div>
    <div class="my-input-text" style="font-size: 10px">
      三种用户角色：系统管理员、师生用户、食堂管理员
      系统具有一个初始管理员账号，其余账号可以注册。
      所有账号登录后，可以修改自己的密
      码，并可以看到自己角色相应的不同界面。
    </div>
  </div>
  </body>

</template>

<script>
import ValidCode from "@/conponents/ValidCode";

export default {
  name: "Login",
  components: {
    ValidCode
  },
  data() {

    // 验证码校验
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'))
      } else if (value.toLowerCase() !== this.code) {
        callback(new Error('验证码错误'))
      } else {
        callback()
      }
    }

    return {
      // forgetUserForm: {},   // 忘记密码的表单数据
      // forgetPassDialogVis: false,
      code: '',  // 验证码组件传递过来的code
      user: {
        code: '',   // 表单里用户输入的code 验证码
        account: '',
        password: '',
        role: 0  // 默认是系统管理员
      },
      rules: {
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        code: [
          { validator: validateCode, trigger: 'blur' },
        ],
        role :[
          {required:true,message:'请选择你的角色',trigger:'blur'},
        ]
      },

    }
  },
  created() { // // 在组件创建后立即发起网络请求获取用户数据,可以用来初始化数据

  },
  methods: {
    // handleForgetPass() {   //  初始化表单的数据
    //   // this.forgetUserForm = {}
    //   // this.forgetPassDialogVis = true
    // },
    // resetPassword() {
    //   this.$request.put('/password', this.forgetUserForm).then(res => {
    //     if (res.code === '200') {
    //       this.$message.success('重置成功')
    //       this.forgetPassDialogVis = false
    //     } else {
    //       this.$message.error(res.msg)
    //     }
    //   })
    // },
    getCode(code) {
      this.code = code.toLowerCase()
    },
    login() {
      this.$refs['loginRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/login', this.user).then(res => {
            console.log(this.user);
            if (res.code === 200 && this.user.role === 0) {
              this.$router.push('/manager')
              this.$message.success('登录成功')
              localStorage.setItem("admin", JSON.stringify(res.data));  // 存储管理员数据
              localStorage.setItem("role", JSON.stringify(this.user.role));   // 存储角色数据
            }else if(res.code === 200 && this.user.role === 1){
              this.$router.push('/cafeteria_manager')
              this.$message.success('登录成功')
              localStorage.setItem("cafeteria_admin", JSON.stringify(res.data));  // 存储食堂管理员数据
              localStorage.setItem("role", JSON.stringify(this.user.role));   // 存储角色数据
            }else if (res.code === 200 && (this.user.role === 2 || this.user.role === 3) ) {
              this.$router.push('/ts/ts_home')
              this.$message.success('登录成功')
              localStorage.setItem("user", JSON.stringify(res.data));   // 存储学生数据
              localStorage.setItem("role", JSON.stringify(this.user.role));   // 存储角色数据
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
body, html {
  margin: 0;
  padding: 0;
  height: 100%;
}

/* 设置背景图片 */
body {
  background-image: url('@/assets/usst_login.jpg'); /* 替换为实际的图片路径 */
  background-size: cover; /* 确保图片覆盖整个页面 */
  background-position: center; /* 图片居中显示 */
  background-repeat: no-repeat; /* 不重复图片 */
}
.right-container {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  height: 80%;
  width: 90%;
}

.text-box {
  position: fixed;
  left: 10px; /* 距离左侧的距离 */
  bottom: 10px; /* 距离底部的距离 */
  padding: 5px;
  background-color: rgba(50, 50, 50, 0.70);/*文本框背景色*/
  width: 25%;
  height: 35%;
  border-radius: 8%;
}


.my-form /deep/ .el-input__inner {
  background-color: #f5f5f5; /* 你希望的颜色 */
}

.regist{
  font-size: 15px;
  color:#8c939d;
}

.my-input{
  width:250px;
  left: 0px;
  border-radius: 25%;
}

.my-input-text{
  font-weight: bold;
  text-align:left;
  margin-bottom: 20px;
  color: rgba(240, 240, 240, 0.80)
}
.analysis{
  font-size: 15px;
  color: rgb(255,255,255,0.8);

  margin-block: 25px;
  margin-top: 0px; /* 或其他你想要的值 */
}
</style>