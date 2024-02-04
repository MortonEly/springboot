<template>

      <div style="font-size: 12px; line-height: 60px; display: flex; align-items: center;">
      <!-- 左侧区域 -->
		    <div style="flex: 1; font-size: 20px; display: flex; align-items: center;">
			<!-- 折叠按钮 -->
			<span :class="collapseButtonClass" style="cursor: pointer; font-size: 20px;" @click="collapse"></span>
      <!-- 面包屑导航 -->
			<el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px; ">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
			</el-breadcrumb>
      </div>

    <div style="text-align:center;">
      <el-dropdown style="width:150px;cursor:pointer;">
        <div style="display:inline-block;">
          <!-- <img src="../assets/logo.png" style="width:20px;height:20px;border-radius:50% ;position:relative;margin-right:10px;"/>
          <span>名字</span> -->
          <img :src="user.avatar" style="width:30px; border-radius: 50%; position:absolute;top:15px;right:70px; display: inline-block; vertical-align: middle;">
            <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px; vertical-align: middle;"></i>
        </div>
        <el-dropdown-menu slot="dropdown" style="dropdown-menu">
          <el-dropdown-item style="manage-personal">
         <router-link to="/person"   style="text-decoration:none;color:#483D8B" @click="handleClick">
            查看个人信息
         </router-link>
          </el-dropdown-item>
          <el-dropdown-item style="manage-personal">
            修改个人信息
          </el-dropdown-item>
          <el-dropdown-item style="manage-personal">
            <router-link to="/login"  @click="logout" style="text-decoration:none;color:#483D8B">
            退出
            </router-link>
          </el-dropdown-item>

          <el-dropdown-item>
            个人信息
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseButtonClass: String,
    isCollapse: Boolean,
    vModels: {
      type: String, // 将vModels的类型改为字符串
      default: "false" // 默认值为字符串"false"
    }
  },
  data() {
    return {
      user: localStorage.getItem("user")
        ? JSON.parse(localStorage.getItem("user"))
        : {}
    };
  },
  methods: {
    collapse() {
      // if (this.vModels === "true") {
      //   // 将vModels的值与字符串"true"进行比较
      //   this.$emit("update:isCollapse", !this.isCollapse);
      // } else {
      //   this.$emit("on-collapse");
      // }
      this.$emit("collapse");
    },
    logout() {
      this.$router.push("/login");
      localStorage.removeItem("user");
      this.$message.success("退出成功");
    }
  },
  handleClick() {
    this.$router.push("/person");
    this.$message.success("跳转成功");
  },
  computed: {
    currentPathName() {
      return this.$store.state.currentPathName;
    }
  }
};
</script>
