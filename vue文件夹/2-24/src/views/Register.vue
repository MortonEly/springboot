<template>
	<div class="register clearfix">
		<div class="register-wrap">
			<el-row type="flex" justify="center">
				<el-form ref="loginForm" :model="user" :rules="rules" status-icon label-width="80px">
					<h3 :style="{ marginTop: '20px', textAlign: 'center' }">注册</h3>
					<hr>
					<el-form-item prop="username" label="账号" :error="errors.username">
						<el-input v-model="user.username" placeholder="请输入账号" prefix-icon="el-icon-user"></el-input>
					</el-form-item>
					<el-form-item prop="password" label="密码" :error="errors.password">
						<el-input v-model="user.password" show-password placeholder="请输入密码" prefix-icon="el-icon-edit"></el-input>
					</el-form-item>
					<el-form-item prop="confirmpassword" label="确认密码" :error="errors.confirmpassword">
						<el-input v-model="user.confirmpassword" show-password placeholder="请再一次输入密码" prefix-icon="el-icon-edit"></el-input>
					</el-form-item>
					<el-form-item class="button-group" style="display: flex; justify-content: center;">
						<router-link to="/login">
							<el-button type="primary" icon="el-icon-upload" autocomplete="off">返回登录</el-button>
						</router-link>
						<el-button type="warning" icon="el-icon-upload" @click="doRegister()" autocomplete="off" style="margin-left: 48px;">注册账号</el-button>
					</el-form-item>
				</el-form>
			</el-row>
		</div>
	</div>
</template>

<script>
import request from "@/utils/request.js";
import { Message, MessageBox } from "element-ui";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmpassword: ""
      },
      errors: {
        username: "",
        password: "",
        confirmpassword: ""
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 16,
            message: "密码长度在 6 到 16 个字符",
            trigger: "blur"
          }
        ],
        confirmpassword: [
          { required: true, message: "请再次输入密码", trigger: "blur" },
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
    // 注册方法
    doRegister() {
      this.$refs["loginForm"].validate(valid => {
        if (valid) {
          if (this.user.password !== this.user.confirmpassword) {
            this.errors.confirmpassword = "两次输入的密码不一致";
            return false;
          } else {
            this.errors.confirmpassword = "";
            // 发起注册请求
            request.post("/user/register", this.user).then(res => {
              if (res.code === "200") {
                // 注册成功提示
                Message.success("注册成功");
                // 弹出确认框，是否跳转到登录页面
                MessageBox.confirm("注册成功，是否跳转到登录页面？", "提示", {
                  confirmButtonText: "确定",
                  cancelButtonText: "取消",
                  type: "success"
                }).then(() => {
                  setTimeout(() => {
                    this.$router.push("/login");
                  }, 3000);
                });
              } else {
                this.$message.error("注册失败：" + res.msg);
              }
            });
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    }
  }
};
</script>