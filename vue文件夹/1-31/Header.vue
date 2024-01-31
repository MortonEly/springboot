<template>
	<div style="font-size: 12px; line-height: 60px; display: flex; align-items: center;">
		<!-- 左侧区域 -->
		<div style="flex: 1; font-size: 20px; display: flex; align-items: center;">
			<!-- 折叠按钮 -->
<span :class="collapseButtonClass" style="cursor: pointer; font-size: 20px;" @click="collapse"></span>

			<!-- 面包屑导航 -->
			<el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px;">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
			</el-breadcrumb>
		</div>

<!-- 右侧区域 -->
<el-dropdown style="width: 70px; cursor:pointer; margin-left: 1050px;">
  <div style="display: inline-block">
    <img :src="user.avatar" style="width:30px; border-radius: 50%; position:absolute;top:15px;right:70px; display: inline-block; vertical-align: middle;">
    <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px; vertical-align: middle;"></i>
  </div>

			<!-- 下拉菜单 -->
			<el-dropdown-menu slot="dropdown">
				<el-dropdown-item>
					<router-link to="/person">个人信息</router-link>
				</el-dropdown-item>
				<el-dropdown-item>新增</el-dropdown-item>
				<el-dropdown-item>删除</el-dropdown-item>
				<el-dropdown-item>
					<!-- 将to属性设置为"/login"，并添加style属性 -->
					<router-link to="/login" style="text-decoration: none" @click="logout">退出</router-link>
				</el-dropdown-item>
			</el-dropdown-menu>
		</el-dropdown>
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
      if (this.vModels === "true") {
        // 将vModels的值与字符串"true"进行比较
        this.$emit("update:isCollapse", !this.isCollapse);
      } else {
        this.$emit("on-collapse");
      }
    },
    logout() {
      this.$router.push("/login");
      localStorage.removeItem("user");
      this.$message.success("退出成功");
    }
  },
  computed: {
    currentPathName() {
      return this.$store.state.currentPathName;
    }
  }
};
</script>