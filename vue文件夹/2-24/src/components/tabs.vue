<template>
  <div class="tabs-container" v-horizontal-scroll>
    <el-tag
      class="tag"
      size="medium"
      :closable="index > 0"
      v-for="(item, index) in tagsData"
      :key="item.path"
      @click="goPath(item.path)"
      @close="close(index)"
      :effect="item.title == $route.meta.title ? 'dark' : 'plain'"
      @contextmenu.native.prevent="rightClick($event, index)"
    >
      <i class="cir" v-show="item.title == $route.meta.title"></i
      >{{ item.title }}
    </el-tag>
    <TagsMenuVue
      v-show="isShowTagsMenu"
      :clientX="clientX"
      :clientY="clientY"
      :clickIndex="clickIndex"
      :tagsLength="tagsData.length"
      @fn="clickMenu"
    />
  </div>
</template>

<script>
import TagsMenuVue from "./TagsMenu.vue";
export default {
  components: {
    TagsMenuVue
  },
  directives: {
    "horizontal-scroll": {
      bind: function(el) {
        el.addEventListener("wheel", function(event) {
          event.preventDefault();
          el.scrollLeft += event.deltaY;
        });
      }
    }
  },
  data() {
    return {
      tagsData: [
        {
          title: "首页",
          path: "/home"
        }
      ],
      isShowTagsMenu: false,
      clientX: 0,
      clientY: 0,
      clickIndex: 0
    };
  },
  watch: {
    // 监听当前路由
    $route: {
      immediate: true,
      handler(val, oldval) {
        console.log(val, "路由");
        const bool = this.tagsData.find(item => val.path == item.path);
        if (!bool) {
          this.tagsData.push({
            title: val.meta.title,
            path: val.path
          });
        }
        console.log(this.tagsData, "路由地址");
      }
    },
    tagsData: {
      immediate: true,
      handler(val, oldval) {
        return;
      }
    }
  },

  mounted() {
    // 给文档添加点击事件
    document.addEventListener("click", this.closeMenus);
  },
  methods: {
    // 点击标签跳转
    goPath(path) {
      // 解决重复点击会报错，数据冗余
      if (path !== this.$route.path) {
        this.$router.push(path);
      }
      this.closeMenus();
      console.log(path, this.tagsData, this.$route.meta.title, "9999999999");
    },
    // 关闭选择菜单
    closeMenus() {
      this.isShowTagsMenu = false;
    },
    //   点击操作之后实现
    clickMenu(i) {
      // 关闭其他，就保留自己的包含首页
      console.log(i);
      if (i === 0) {
        // 关闭其他，前提是保留首页
        let clickItem = [];
        clickItem = this.tagsData.filter(
          (item, index) => index === this.clickIndex
        );
        console.log(clickItem, "关闭其他");
        const newTags = [{ title: "首页", path: "/home" }, ...clickItem];
        if (newTags[1].path == "/home") {
          this.tagsData = [{ title: "首页", path: "/home" }];
        } else {
          this.tagsData = [{ title: "首页", path: "/home" }, ...clickItem];
        }
        const activePath =
          newTags[1].path === "/home" ? "/home" : newTags[1].path;
        this.goPath(activePath);
      } else if (i === 1) {
        // 关闭左侧
        this.tagsData.splice(1, this.clickIndex - 1);
      } else if (i === 2) {
        // 关闭右侧
        this.tagsData.splice(this.clickIndex + 1);
        this.goPath(this.tagsData[this.clickIndex].path);
      } else {
        // 全部关闭
        // 1 处理数组
        this.tagsData = [
          {
            title: "首页",
            path: "/home"
          }
        ];
        // 跳转到首页
        this.goPath("/home");
      }
    },
    close(i) {
      if (
        this.tagsData[i].path == this.$route.path &&
        i !== this.tagsData.length - 1
      ) {
        // 直接跳转到最后一项了
        this.$router.push(this.tagsData[this.tagsData.length - 1].path);
      } else if (i === this.tagsData.length - 1) {
        this.$router.push(this.tagsData[this.tagsData.length - 2].path);
      }
      // 关闭当前项，本质上就是删除tags的对应项
      this.tagsData.splice(i, 1);
      this.closeMenus();
    },
    // 右键点击
    rightClick(e, i) {
      console.log("右键点击了", e.clientX, e.clientY);
      this.isShowTagsMenu = true;
      this.clientX = e.clientX;
      this.clientY = e.clientY;
      this.clickIndex = i;
    }
  }
};
</script>

<style scoped>
.tabs-container {
  width: 100%;
  height: 100%;
  overflow-x: scroll;
  overflow-y: hidden;
  white-space: nowrap;
  display: flex;
}

.tag {
  cursor: pointer;
  margin-right: 5px;
  height: 37px;
  line-height: 34px;
  font-size: 16px;
}

.cir {
  width: 8px;
  height: 8px;
  margin-right: 4px;
  background-color: #ffffff;
  border-radius: 50%;
  display: inline-block;
}

.tabs-container::-webkit-scrollbar {
  display: none;
}
</style>