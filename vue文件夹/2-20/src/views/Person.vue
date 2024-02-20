<template>
  <el-card style="text-decoration:none;color:black;width:700px;padding:25px;border:3px solid  red;position:center">
    <el-form label-width="80px">
      <el-form-item label="用户名">
        <el-upload
  class="avatar-uploader"
  action="http://localhost:9090/file/upload"
  :show-file-list="false"
  :on-success="handleAvatarSuccess"
  >
<img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="Save">确定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "Person",
  data() {
    return {
      form: {
        username: "",
        nickname: "",
        email: "",
        phone: "",
        address: ""
      }
    };
  },
  created() {
    const username = localStorage.getItem("user")
      ? JSON.parse(localStorage.getItem("user")).username
      : "";
    if (!username) {
      this.$message.warning("请先登录");
      this.$router.push("/login"); // 假设登录页面路径为 '/login'
    } else {
      this.request.get("/user/username/" + username).then(res => {
        this.form = res;
        this.form.avatarUrl = res.avatar; // 将后端返回的头像地址赋值给form.avatarUrl
      });
    }
    this.getUser();
  },
  methods: {
    getUser() {
      this.request.get("/user/username" + this.user.username).then(res => {
        if (res.code == "200") {
          this.form = res.data;
        }
      });
    },
    Save() {
      request.post("/user", this.form).then(res => {
        if (res.data == "200") {
          this.$message.success("保存成功");
          this.getUser();
        } else {
          this.$message.error("保存失败");
        }
      });
    },
    handleAvatarSuccess(res) {
      // this.form.avatarUrl = res;
      // 将返回的图像地址赋值给form.avatarUrl
      // this.form.avatarUrl = URL.createObjectURL(res.raw);
      // 使用URL.createObjectURL方法生成一个临时的图片地址
      this.form.avatarUrl = res.url; // 确保将返回的图像地址赋值给form.avatarUrl
    }
  }
};
</script>