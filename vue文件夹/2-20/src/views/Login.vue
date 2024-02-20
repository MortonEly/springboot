<template>
	<div class="login clearfix">
		<div class="login-wrap">
			<el-row type="flex" justify="center">
				<el-form ref="loginForm" :model="user" :rules="rules" status-icon label-width="80px">
					<h3 :style="{ marginTop: '20px', textAlign: 'center' }">登录</h3>
					<hr>
					<el-form-item prop="username" label="用户名">
						<el-input v-model="user.username" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
					</el-form-item>
					<el-form-item prop="password" label="密码">
						<el-input v-model="user.password" show-password placeholder="请输入密码" prefix-icon="el-icon-edit"></el-input>
					</el-form-item>
					<div class="link-container" style="text-align: center; margin-top: 20px;">
						<router-link to="/" style="margin-right: 20px;">找回密码</router-link>
						<router-link to="/register">注册账号</router-link>
					</div>
					<el-form-item>
						<el-button type="primary" icon="el-icon-upload" @click="doLogin()">登录</el-button>
						<el-button type="warning" icon="el-icon-upload" @click="doRegister()">注册</el-button>
					</el-form-item>
				</el-form>
			</el-row>
		</div>
	</div>
</template>

<script>
import request from "@/utils/request.js";
export default {
  name: "login",
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 20, message: "长度在 3 到 20个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 16,
            message: "密码长度在 6 到 16 个字符",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    doLogin() {
      this.$refs["loginForm"].validate(valid => {
        if (valid) {
          request.post("/user/login", this.user).then(res => {
            if (res.code == "200") {
              localStorage.setItem("user", JSON.stringify(res.data));
              localStorage.setItem("menus",JSON.stringify(res.data.menus));
              this.$router.push("/");
              this.$message.success("登录成功");
            } else {
              this.$message.error("用户名或密码错误");
              this.$message.error(res.msg);
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    doRegister() {
      this.$router.push("/register");
      this.$message("注册界面");
    }
  }
};
</script>