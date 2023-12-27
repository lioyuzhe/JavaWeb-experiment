<template>
  <body>
  <div class="my-main">
    <div  class="my-body" style="">
      <div class="my-center">
        <el-form class="my-form" :model="user" style="width: 80%" :rules="rules" ref="registerRef">
          <div style="display: flex; justify-content: space-between; align-items: center;">
            <img src="@/assets/usst_logo.png" alt="" style="width: 45%;">
            <div class="my-title">
             用户注册界面
            </div>
          </div>
          <el-form-item prop="account">
            <el-input class="my-input" prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.account"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input class="my-input"  prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPass">
            <el-input class="my-input"  prefix-icon="el-icon-lock" size="medium" show-password placeholder="请确认密码" v-model="user.confirmPass"></el-input>
          </el-form-item>
          <el-form-item prop="role">
            <el-radio-group v-model="user.role">
              <!--              <el-radio :label=0>系统管理员</el-radio>  不能注册系统管理员-->
              <!--              <el-radio :label=1>食堂管理员</el-radio> -->
              <el-radio class="radio" :label="2">学生</el-radio>
              <el-radio class="radio"  :label="3">老师</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="info" style="width: 100%" @click="register">注 册</el-button>
          </el-form-item>
          <div style="display: flex">
            <div class="apply">已经有账号了？请 <span style="color: #6e77f2; cursor: pointer" @click="$router.push('/login')">登录</span></div>
          </div>
        </el-form>
      </div>
    </div>
  </div>
  </body>

</template>

<script>

export default {
  name: "Register",
  data() {
    // 验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'))
      } else if (confirmPass !== this.user.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      user: {
        account: '',
        password: '',
        confirmPass: '',
        role: 0,
      },
      rules: {
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'blur' },
        ],
      }
    }
  },
  created() {

  },
  methods: {
    register() {
      this.$refs['registerRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/register', this.user).then(res => {
            console.log("user:",this.user);
            if (res.code === 200 && this.user.role === 0) {
              this.$router.push('/login')
              this.$message.success('注册成功')
            }else if(res.code === 200 && this.user.role === 1){
              this.$router.push('/login')
              this.$message.success('注册成功')
            }else if (res.code === 200 && (this.user.role === 2 || this.user.role === 3) ) {
              this.$router.push('/login')
              this.$message.success('注册成功')
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

/*body部分用于设置背景图片*/
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

.my-register{
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  color:#FFFFFF;
}

.my-input{
  width:100%;
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

/*登录按钮*/
.my-login{
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  color:#FFFFFF;
}

/*表单部分*/
.my-form{
  margin-top: 20px;
  width: 55%;
}

/*主体部分*/
.my-body{
  display: flex;
  width: 32%;
  overflow: hidden;
  border-radius: 8%;
  height: 60%;
  background-color: rgba(50, 50, 50, 0.80);/*文本框背景色*/
}

/*整个界面*/
.my-main{
 height: 110vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

/*中心部分*/
.my-center{
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center
}

/*标题*/
.my-title{
  font-size: 30px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  color: #FFFFFF;
  left:0px;
}

/*账号申请*/
.apply{
  flex: 1;
  color:#8c939d;
}

/*选择按钮*/
.radio{
  color:#8c939d;
}
</style>