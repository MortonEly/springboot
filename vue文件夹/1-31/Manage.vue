<template>
<el-container style="min-height: 100vh">
<el-aside :width="sideWidth + 'px'" style="background-color: rgb(48,65,86); box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);">
    <div style="height:60px; line-height:60px; text-align:center">
      <b style="color:#fff; font-size:30px; font-family:STKaiti" v-show="LogoTextShow">管理系统</b>
    </div>
<!-- 导入Aside -->
<Aside :isCollapse="isCollapse" :LogoTextShow="LogoTextShow" />
    </el-aside>

    <el-container>
<el-header style="font-size:12px;border-bottom:1px solid #ccc;line-height:60px;display:flex;justify-content:space-between;align-items:center">
    <Header :collapseBtnClass="collapseBtnClass" :isCollapse="true" />
  </el-header>

<!-- 搜索框 -->
        <el-main>
          <!-- 表示当前子路由 -->
       <router-view  @reference="getUser" />
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import request from "@/utils/request.js";
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      email: "",
      address: "",
      username: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      collapseBtnClass: "el-icon-s-fold",
      isCollapse: false, // 是否收缩
      sideWidth: 200, // 侧边栏宽度
      LogoTextShow: true
    };
  },
  created() {
    this.load();
  },
  components: {
    Aside,
    Header
  },
  methods: {
    collapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.sideWidth = 50;
        this.collapseBtnClass = "el-icon-s-unfold";
        this.LogoTextShow = false;
      } else {
        this.sideWidth = 200;
        this.collapseBtnClass = "el-icon-s-fold";
        this.LogoTextShow = true;
      }
    },
    getUser() {},
    load() {
      const params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        username: this.username,
        email: this.email,
        address: this.address
      };

      request
        .post("/user/page", params)
        .then(res => {
          console.log(res);
          this.tableData = res.records;
          this.total = res.total;
        })
        .catch(error => {
          console.error(error);
        });
    },
    handleRowClick(row) {
      // 深拷贝当前行数据
      this.form = JSON.parse(JSON.stringify(row));
      // 显示弹出框
      this.dialogFormVisible = true;
    },
    Cancel() {
      // 取消操作
      this.dialogFormVisible = false;
      this.load();
    },
    DeleteEdit(id) {
      this.$confirm("确定删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          request.delete("/user/" + id).then(res => {
            if (res) {
              this.$message.success("删除成功");
              this.load();
            } else {
              this.$message.error("删除失败");
            }
          });
        })
        .catch(() => {
          // 用户取消删除操作
        });
    },
    Save() {
      this.$confirm("确定保存该用户信息吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          request.post("/user/insert", this.form).then(res => {
            if (res) {
              this.$message.success("保存成功");
              this.dialogFormVisible = false;
              this.load();
            } else {
              this.$message.error("保存失败");
            }
          });
        })
        .catch(() => {
          this.$message.info("已取消保存");
        });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val);
    },
    DeleteBatch() {
      let ids = this.multipleSelection.map(v => v.ids);
      this.$confirm("确定删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          request.delete("/user/Delete/Deletebatch/" + ids).then(res => {
            if (res) {
              this.$message.success("删除成功");
              this.load();
            } else {
              this.$message.error("删除失败");
            }
          });
        })
        .catch(() => {
          // 用户取消删除操作
        });
    },
    handleAdd() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    reset() {
      this.username = "";
      this.email = "";
      this.address = "";
      this.load();
    },
    handleEdit(row) {
      this.form = row;
      this.dialogFormVisible = true;
    },
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum = pageNum;
      this.load();
    }
  }
};
</script>