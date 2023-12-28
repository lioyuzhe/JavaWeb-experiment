<template>
  <div>
    <el-card style="width: 50%">
      <el-form :model="admin" label-width="80px" style="padding-right: 20px">
        <div style="margin: 15px; text-align: center">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: admin.token }"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="admin.avatar" :src="admin.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="admin.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="admin.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="admin.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">保 存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "CafeteriaAdminProfile",
  data() {
    return {
      admin: JSON.parse(localStorage.getItem('cafeteria_admin') || '{}')
    }
  },
  methods: {
    update() {
      // 保存当前的管理员信息到数据库
      this.$request.post('/cafeteriaAdmins/actions/updateCafeteriaAdmin', this.admin).then(res => {
        if (res.code === 200) {
          // 成功更新
          this.$message.success('保存成功');

          // 更新浏览器缓存里的管理员信息
          localStorage.setItem('cafeteria_admin', JSON.stringify(this.admin));

          // 可以在这里触发其他需要的操作
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    handleAvatarSuccess(response, file, fileList) {
      // 更新头像链接
      this.admin.avatar = response.data;
    },
  }
}
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
/deep/.el-upload {
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border-radius: 50%;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
  border-radius: 50%;
}
</style>
