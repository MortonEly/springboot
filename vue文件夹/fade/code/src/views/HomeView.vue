<template>
  <!-- style="height: 500px; border: 1px solid #eee" -->
  <el-container class="container" >
    <!-- 侧边栏 -->
    <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246); height:100%" >
      <Aside :collapse="isCollapse"></Aside>
    </el-aside>
    <el-container>
      <!-- 头部 -->
     <el-header style="display: flex; justify-content: space-between; align-items: center; font-size: 20px; border-bottom: 2px solid blue">
  <div>
    <!-- 收缩图标 -->
    <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
  </div>
  <div>    
    <i class="el-icon-full-screen" style="padding:10px 10px"></i>
      <i class="el-icon-setting"></i>    
    <el-dropdown style="font-size:20px">
  <div>
    <span class="el-dropdown-link" >王小虎</span>
    <i class="el-icon-arrow-down el-icon--right">       
      </i>
    </div>

      
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>个人信息</el-dropdown-item>
        <el-dropdown-item>新增</el-dropdown-item>
        <el-dropdown-item>退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</el-header>
<!-- 主体 -->
      <el-main>
        <div style="padding:10px 10px">
        <el-input placeholder="请输入名称" suffix-icon="el-icon-search" style="width:200px"></el-input>
        <el-input placeholder="请输入邮箱" suffix-icon="el-icon-chat-dot-square" style="width:200px"></el-input>
        <el-input placeholder="请输入地址" suffix-icon="el-icon-position" style="width:200px"></el-input>
       <el-button type="primary" style="margin-left:10px 10px"  icon="el-icon-search">确认</el-button>
       <el-button type="info" style="margin-left:10px 10px" icon="el-icon-search" >删除</el-button>
        </div>
        
      <div style="margin:10px 10px;text-align:center">
<el-button class="btn" plain @click="handleAdd">增加
  <i class="el-icon-circle-plus"></i>
</el-button>
  <el-button class="btn" type="primary" plain>批量删除
    <i class="el-icon-remove"></i>
  </el-button>
  <el-button class="btn" type="success" plain>导入
    <i class="el-icon-bottom"></i>
  </el-button>
  <el-button class="btn" type="info" plain>导出
    <i class="el-icon-top"></i>
  </el-button>
      </div>
        <el-table
          :data="tableData"
          border
          fit
          
          :cell-style="{borderColor:'#C0C0C0'}"
       
          style="width: 100%"
        >   
        <!-- :row-class-name="tableRowClassName" 
        :header-cell-style="rowclass"
        
        -->
        <el-table-column prop="id" label="编号" width="100px">
          </el-table-column>
          <el-table-column prop="name" label="姓名" width="125px">
          </el-table-column>
          <el-table-column prop="password" label="密码" width="125px">
          </el-table-column>
          <el-table-column prop="username" label="昵称" width="125px">
          </el-table-column>
          <el-table-column prop="date" label="日期" width="125px">
          </el-table-column>
          <el-table-column prop="address" label="地址" width="150px">
          </el-table-column>
          <el-table-column prop="phone" label="电话" width="125px">
          </el-table-column>
          <el-table-column prop="email" label="邮件" width="125px">
          </el-table-column>
        
          <el-table-column fixed="right" label="操作" >
  <el-button type="success" circle size="small" icon="el-icon-edit" >修改</el-button>
  <el-button type="danger" circle size="small"  icon="el-icon-delete">删除</el-button>
          </el-table-column>
           </el-table>
           <!-- 新增用户表单 -->
       <!-- 添加用户的对话框 -->
<el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
  <!-- 内容主体区 -->
  <el-form :model="addUserForm" :rules="addUserFormRules" ref="addUserFormRef" label-width="70px">
  <el-form-item label="编号" prop="id"> <!-- prop是验证规则属性 -->
      <el-input v-model="addUserForm.id"></el-input>
    </el-form-item>
    <el-form-item label="用户名" prop="username"> <!-- prop是验证规则属性 -->
      <el-input v-model="addUserForm.username"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="addUserForm.password" type="password"></el-input>
    </el-form-item>
<el-form-item label="姓名" prop="name">
      <el-input v-model="addUserForm.name"></el-input>
    </el-form-item>
<!--     <el-form-item label="邮箱" prop="email">
      <el-input v-model="addUserForm.email"></el-input>
    </el-form-item>
    <el-form-item label="手机" prop="mobile">
      <el-input v-model="addUserForm.mobile"></el-input>
    </el-form-item>
<el-form-item label="地址" prop="address">
      <el-input v-model="addUserForm.address"></el-input>
    </el-form-item> -->
  </el-form>
  <!-- 底部区 -->
  <span slot="footer" class="dialog-footer">
    <el-button @click="addDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addInsert ">确 定</el-button>
  </span>
</el-dialog>
           </el-main>
           <!-- 底部 -->
      <el-footer>
        <div class="block" style="padding:15px 10px text-align:center"> 
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[1,5, 10, 15, 20,25,30]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
        </div>


      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import Aside from "@/components/Aside.vue";
import request from "@/utils/request";
export default {
  name: "HomeView",
  components: {
    HelloWorld,
    Aside
  },
  created() {
    this.load();
  },
  methods: {
    // 重载
    load() {
      request
        .get(
          "/user/selectByPage/?pageNum=" +
            this.pageNum +
            "&pageSize=" +
            this.pageSize +
            ""
        )
        .then(res => {
          console.log(res.records);
          this.tableData = res.records;
          console.log(res.total);
          this.total = res.total;
        });
    },
    // 分页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.load();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.load();
    },
    // 增加用户
    handleAdd() {
      this.addDialogVisible = true;
    },
    addDialogClosed() {
      this.$refs.addUserFormRef.resetFields();
    },
    // 添加用户
    addInsert() {
      this.addDialogVisible = false;
      // 发起POST请求以添加用户
      request
        .post("/user/add", this.addUserForm)
        .then(response => {
          if (response.code === 200) {
            // 请求成功
            // 显示成功的操作
            this.$message.success("这是一条正确消息");
            this.load();
          } else {
            // 请求成功但返回错误信息
            // 显示错误的操作
            this.$message.error("错了哦，这是一条错误消息");
          }
        })
        .catch(error => {
          // 请求失败
          this.error("保存失败，请检查网络连接");
        });
    },
    // 编辑用户
    //     handleEdit(row){
    // this.form=row;
    // console.log(row);
    // this.addDialogVisible=tr
    //     },
    //点击收缩按钮触发
    collapse() {
      //collapse为false时展开，为true时折叠。
      this.isCollapse = !this.isCollapse;
      //折叠时
      if (this.isCollapse) {
        //折叠时宽度为64
        this.sideWidth = 60;
        //变换图标样式
        this.collapseBtnClass = "el-icon-s-unfold";
        //折叠时不展示名称“ML后台管理系统”
        this.logoTextShow = false;
        //展开时
      } else {
        //展开时宽度为200
        this.sideWidth = 200;
        //变换图标样式
        this.collapseBtnClass = "el-icon-s-fold";
        //展开时展示名称“ML后台管理系统”
        this.logoTextShow = true;
      }
    }
  },

  data() {
    var checkEmail = (rule, value, callback) => {
      //验证邮箱的正则表达式
      const regEmail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
      if (regEmail.test(value)) {
        return callback(); //合法邮箱
      }
      return callback(new Error("请输入合法的邮箱"));
    };
    var checkMobile = (rule, value, callback) => {
      //验证手机号的正则表达式
      const regMobile = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if (regMobile.test(value)) {
        return callback(); //合法手机号
      }
      return callback(new Error("请输入合法的手机号"));
    };
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      //默认图标为"el-icon-s-fold"
      collapseBtnClass: "el-icon-s-fold",
      //菜单默认不折叠
      isCollapse: false,
      //默认宽度200
      sideWidth: 200,
      addDialogVisible: false, //控制添加用户对话框的显示与隐藏 //添加用户的表单数据
      addUserForm: {}, //添加表单的验证规则对象
      addUserFormRules: {
        // username: [
        //   { required: true, message: "请输入用户名", trigger: "blur" },
        //   {
        //     min: 3,
        //     max: 10,
        //     message: "用户名长度在3~10个字符",
        //     trigger: "blur"
        //   }
        // ],
        // password: [
        //   { required: true, message: "请输入密码", trigger: "blur" },
        //   { min: 6, max: 15, message: "密码长度在6~15个字符", trigger: "blur" }
        // ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { validator: checkEmail, trigger: "blur" }
        ],
        mobile: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { validator: checkMobile, trigger: "blur" }
        ]
      }
    };
  }
};
</script>