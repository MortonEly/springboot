<template>
<el-menu
                default-active="1-4-1"
                class="el-menu-vertical-demo"
                text-color="#fff"
                style="min-height:100%; overflow-x:hidden; "
                active-text-color="#ffd046"
                background-color="rgb(48,65,86)"
                router
                :collapse="isCollapse"
>
<div style="color: #fff; text-align: center;line-height: 48px; font-size: 16px; font-weight: 400;">
  <img src="../assets/logo.png" style="width: 30px; position: relative; margin-right: 5px; top:6px">
  <b v-show="LogoTextShow">后台管理系统</b>
</div>
  <el-submenu index="1">
    <template slot="title">
      <i class="el-icon-location"></i>
      <span slot="title">导航一</span>
    </template>
    <el-menu-item-group>
      <span slot="title">分组一</span>
      <el-menu-item index="/home">主页管理</el-menu-item>
      <el-menu-item index="/user">用户管理</el-menu-item>
      <el-menu-item index="/role">角色管理</el-menu-item>
    </el-menu-item-group>
    <el-menu-item-group title="分组2">
      <el-menu-item index="/file">文件管理</el-menu-item>
    </el-menu-item-group>
    <el-submenu index="1-4">
      <span slot="title">选项4</span>
      <el-menu-item index="1-4-1">选项1</el-menu-item>
    </el-submenu>
  </el-submenu>
  <el-menu-item index="2">
    <i class="el-icon-menu"></i>
    <span slot="title">导航二</span>
  </el-menu-item>
  <el-menu-item index="3" disabled>
    <i class="el-icon-document"></i>
    <span slot="title">导航三</span>
  </el-menu-item>
  <el-menu-item index="4">
    <i class="el-icon-setting"></i>
    <span slot="title">导航四</span>
  </el-menu-item>
</el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    LogoTextShow: Boolean,
    HandSelect: {
      type: Function,
      default: function() {
        // 空函数，什么也不做
      }
    }
  },
  data() {
    return {};
  }
};
</script>
