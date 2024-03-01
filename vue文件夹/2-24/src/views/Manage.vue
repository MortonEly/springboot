<template>
<el-container style="min-height: 100vh">
  <el-aside :width="sideWidth+'px'" class="hide-scrollbar">
    <!-- style="background-color: rgb(238, 241, 246)" -->
   <Aside :isCollapse="isCollapse"></Aside>
  </el-aside>

  <el-container>
    <el-header style="border-bottom:5px solid #1E90FF">
    <Header @collapse="collapse" :collapseButtonClass="collapseButtonClass"></Header>
    </el-header>
    <el-main>
    <el-card style="width: 100%; height: 70px"> <Tabs /></el-card>
    <router-view></router-view>
<!--
  router-link 实现路由之间的跳转
router-view 当你的路由path 与访问的地址相符时，会将指定的组件替换该 router-view
-->

    </el-main>
  </el-container>
</el-container>
</template>

<script>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";
import Tabs from "@/components/tabs.vue";
export default {
  name: "Manage",
  components: {
    Aside,
    Header,
    Tabs
  },
  computed: {
    currentPathName() {
      return this.$store.state.currentPathName;
    }
  },
  data() {
    return {
      isCollapse: false,
      collapseButtonClass: "el-icon-s-fold", //收缩按钮名称
      sideWidth: 200,
      logoTextShow: true
    };
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
    }
  }
};
</script>
