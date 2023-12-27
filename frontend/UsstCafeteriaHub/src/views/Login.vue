<template>
  <div style="height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #0f9876">
    <div style="display: flex; background-color: white; width: 50%; border-radius: 5px; overflow: hidden">
      <div style="flex: 1">
        <img src="@/assets/usst.jpg" alt="" style="width: 100%">
      </div>
      <div style="flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="loginRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px">欢迎登录 上海理工大学食堂点评交流社区</div>
          <el-form-item prop="account">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.account"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex">
              <el-input placeholder="请输入验证码" prefix-icon="el-icon-circle-check" size="medium" style="flex: 1" v-model="user.code"></el-input>
              <div style="flex: 1; height: 36px">
                <valid-code @update:value="getCode" />
              </div>
            </div>
          </el-form-item>
          <el-radio-group v-model="user.role">
            <el-radio :label="0">系统管理员</el-radio>
            <el-radio :label="1">食堂管理员</el-radio>
            <el-radio :label="2">学生</el-radio>
            <el-radio :label="3">老师</el-radio>
          </el-radio-group>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login">登 录</el-button>
          </el-form-item>
          <div style="display: flex">
            <div style="flex: 1">还没有账号？请 <span style="color: #0f9876; cursor: pointer" @click="$router.push('/register')">注册</span></div>
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
              this.$message.error(res.message)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>