<template>
  <div>
    <div style="height: 100vh; overflow: hidden; display: flex; align-items: center; justify-content: center">
      <div style="font-size: 40px">404 找不到页面 <router-link to="/">返回首页</router-link></div>
      <el-button type="primary" @click="test1">{{this.test}}</el-button>
    </div>

  </div>
</template>

<script>
export default {
  name: "404",
  data() {
    return {
      test:'',
    }
  },
  created() {

  },
  methods: {
    test1(){
      this.$request.get('/test/test').then(res => {
        if (res.code === 200) {
          this.$message.success(res.data);
          this.test = res.data;
        } else {
          this.$message.error(res.message)
        }
      })
    },
  }
}
</script>

<style scoped>

</style>