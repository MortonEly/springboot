<template>
  <!-- Tags右键之后的菜单组件 -->
  <div class="tags-menu" :style="{ left: clientX + 'px', top: clientY + 'px' }">
    <ul>
      <li
        v-for="(item, index) in tmenu"
        :key="index"
        v-show="isShowLi(index)"
        @click="hdClick(index)"
      >
        <i :class="item.icon"></i>
        {{ item.text }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: ["clientX", "clientY", "clickIndex", "tagsLength"],
  data() {
    return {
      tmenu: [
        {
          icon: "el-icon-circle-close",
          text: "关闭其他"
        },
        {
          icon: "el-icon-back",
          text: "关闭左侧"
        },
        {
          icon: "el-icon-right",
          text: "关闭右侧"
        },
        {
          icon: "el-icon-circle-close",
          text: "全部关闭"
        }
      ]
    };
  },

  methods: {
    hdClick(i) {
      this.$emit("fn", i);
      // if(i==5){
      // 全部关闭 ，要操作的数组在父组件

      // }
    },
    isShowLi(i) {
      if (this.tagsLength === 1) {
        // 只有首页
        return i == 0;
      }
      // clickIndex 点击的标签的clickIndex
      // i 是li的index
      if (this.clickIndex == 0) {
        // 用户点击首页只有关闭其他
        return i == 0;
      }

      if (this.clickIndex == 1 && this.clickIndex == this.tagsLength - 1) {
        // 用户点击第二个且是最后一个
        // return ![2, 4].includes(i);
        return false;
      } else if (
        this.clickIndex == 1 &&
        this.clickIndex != this.tagsLength - 1
      ) {
        // return ![1, 3].includes(i);
        return i !== 1;
      }

      if (this.clickIndex == this.tagsLength - 1) {
        // 用户点击最后一项
        return i !== 2;
      }

      return true;
    }
  }
};
</script>

<style scoped>
.tags-menu {
  z-index: 999;
  background-color: #17102b;
  /* box-shadow: 2px 2px 4px #cccccc; */
  position: absolute;
  border-radius: 6px;
}

.tags-menu ul {
  padding: 10px 0;
  list-style: none;
}

.tags-menu li {
  padding: 8px 15px;
  cursor: pointer;
}

.tags-menu li:hover {
  background-color: #57d132;
}
</style>
