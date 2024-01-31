<template>

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
     <div style="margin-top: 10px; margin-left: 20px; color: #fff; font-size: 18px; font-family:KaiTi,Time New Rome">文件管理系统 </div>
  <!-- el-menu 的内容 -->
<!-- <div v-for="item in menus" :key="item.id"> 
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
  
</div> -->
           

    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系统管理</span>
      </template>
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
    return {
      opens: ["1", "2", " 3"]
      // menus: localStorage.getItem("menus")
      //   ? JSON.parse(localStorage.getItem("menus"))
      //   : [],
      // opens: localStorage.getItem("menus")
      //   ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + "")
      //   : []
    };
  }
};
</script>