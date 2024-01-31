<template>
  <el-container style="min-height: 100vh">

    <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
      <!-- <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" style="padding-bottom: 20px"  :opens="opens" /> -->
      <el-menu
                    :default-openeds="opens"
                    style="min-height: 100%; overflow-x: hidden"
                    background-color="rgb(48,65,86)"
                    text-color="#fff"
                    active-text-color="#ffd04b"
                    :collapse-transition="false"
                    :collapse="isCollapse"
                    class="el-menu-vertical"
                    router
                    @select="HandSelect">
                    <!-- 标题 -->
     <div style="margin-top: 10px; margin-left: 20px; color: #fff; font-size: 18px; font-family:KaiTi,Time New Rome">文件管理系统 </div>
  <!-- el-menu 的内容 -->
<div v-for="item in menus" :key="item.id"> 
  <div v-if="item.path">
    <el-menu-item :index="item.path">
      <template slot="title">
        <i :class="item.icon"></i>
        <span slot="title">{{item.name}}</span>
      </template>
    </el-menu-item>
  </div>
    <div v-else>
      <el-submenu index="item.id">
        <template slot="title">
          <i :class="item.icon"></i>
          <span slot="title">{{item.name}}</span>
        </template>
        <div v-for="subitem in subitem.children" :key="subitem.id">
    <el-menu-item :index="subitem.path">
      <i v-bind:class="subitem.icon"></i>
      <span slot="title">{{subitem.name}}</span>
    </el-menu-item>
  </div>
  </el-submenu>
  </div>
  
</div>      
<!-- 列表 -->
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item index="/home">
        <i class="el-icon-s-custom"></i>
        <span slot="title">主页</span>
      </el-menu-item>
      <el-menu-item index="/user">
        <i class="el-icon-s-custom"></i>
        <span slot="title">用户</span>
      </el-menu-item>
      <el-menu-item index="/role">
        <i class="el-icon-user-solid"></i>
        <span slot="title">角色权限</span>
      </el-menu-item>
      <el-menu-item index="/file">
        <i class="el-icon-document"></i>
        <span slot="title">文件</span>
      </el-menu-item>
      <el-menu-item index="/menu">
        <i class="el-icon-s-home"></i>
        <span slot="title">菜单</span>
      </el-menu-item>
    </el-submenu>
    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-setting"></i>
        <span slot="title">导航三</span>
      </template>
      <el-menu-item-group>
        <template slot="title">分组一</template>
        <el-menu-item index="3-1">选项1</el-menu-item>
        <el-menu-item index="3-2">选项2</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="分组2">
        <el-menu-item index="3-3">选项3</el-menu-item>
      </el-menu-item-group>
      <el-submenu index="3-4">
        <template slot="title">选项4</template>
        <el-menu-item index="3-4-1">选项4-1</el-menu-item>
      </el-submenu>
    </el-submenu>
  </el-menu>
    </el-aside>

    <el-container>
      <el-header style="border-bottom: 1px solid #ccc;">
        <!-- <Header :collapseBtnClass="collapseBtnClass" @asideCollapse="collapse" :user="user" :menus="menus"/> -->
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
      </el-header>

      <el-main>
<!--        表示当前页面的子路由会在 <router-view /> 里面展示-->
        <router-view @refreshUser="getUser" />
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
// import Aside from "@/components/Aside";
// import Header from "@/components/Header";

export default {
  name: "Home",
  data() {
    return {
      collapseButtonClass: "el-icon-s-fold", //收缩按钮名称
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      opens: ["1", "2", " 3"],
      user: {}
    };
  },
  // components: {
  //   Aside,
  //   Header
  // },
  created() {
    // 从后台获取最新的User数据
    this.getUser();
  },
  methods: {
    collapse() {
      // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse;
      console.log("点击成功");
      if (this.isCollapse) {
        // 收缩
        this.sideWidth = 64;
        this.collapseButtonClass = "el-icon-s-unfold";
        this.logoTextShow = false;
      } else {
        // 展开
        this.sideWidth = 200;
        this.collapseButtonClass = "el-icon-s-fold";
        this.logoTextShow = true;
      }
    },
    getUser() {
      let username = localStorage.getItem("user")
        ? JSON.parse(localStorage.getItem("user")).username
        : "";
      if (username) {
        // 从后台获取User数据
        this.request.get("/user/username/" + username).then(res => {
          // 重新赋值后台的最新User数据
          this.user = res.data;
        });
      }
    }
  }
};
</script>