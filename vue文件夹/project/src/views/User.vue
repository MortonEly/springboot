<template>
	<div>
<!-- 搜索栏 -->
		<div style="display: flex; flex-direction: column; align-items: center;">
			<div style="padding: 5px 0;display: flex; justify-content: center;">
				<el-input style="width:200px" suffix-icon="el-icon-search" placeholder="请输入名称" class="ml-5" v-model="username"></el-input>
				<el-input style="width:200px" suffix-icon="el-icon-message" placeholder="请输入邮箱" class="ml-5" v-model="email"></el-input>
				<el-input style="width:200px" suffix-icon="el-icon-position" placeholder="请输入地址" class="ml-5" v-model="address"></el-input>
				<!-- 搜索按钮 -->
				<!-- 重置按钮 -->
				<el-button style="width:100px;margin-left:50px" type="warning" @click="reset" class="el-icon-search">重置</el-button>
				<el-button style="width:100px ;margin-left:50px" type="primary" @click="load" class="el-icon-circle-plus">搜索</el-button>
			</div>
<!-- 任务栏 -->
			<div style="padding: 20px 0;display: flex; justify-content: space-between;" class="button-container">
				<el-button id="my-button" type="primary" @click="handleAdd" class="el-icon-plus">新增<i class="el-icon-circle-plus-outline"></i>
				</el-button>
				<el-button id="my-button" type="danger" @click="handleDelete" class="el-icon-close">删除<i class="el-icon-remove-outline"></i>
				</el-button>
				<el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="HandExcelImportSuccess" style="display: inline-block">
					<button id="my-button" type="button" class="el-button el-button--success el-button--medium import-button">
						<span>导入<i class="el-icon-top"></i>

						</span>
					</button>
				</el-upload>
				<button id="my-button" type="button" class="el-button el-button--info el-button--medium export-button">
					<span>导出<i class="el-icon-bottom"></i>
					</span>
				</button>
			</div>
		</div>
<!-- 主体部分框架代码 -->
		<el-table :data="tableData" border header-cell-class-name="headerBag"
    @selection-change="handleSelectionChange" :header-cell-style="getRowClass">
 <!-- :header-cell-style设置表头样式 -->
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column prop="id" label="编号" width="50px">
			</el-table-column>
			<el-table-column prop="username" label="用户名" width="150px">
			</el-table-column>
			<el-table-column prop="nickname" label="姓名" width="100px">
			</el-table-column>
      <el-table-column prop="role" label="角色" width="75px">
			</el-table-column>
			<el-table-column prop="roleId" label="唯一标识" width="50px">
			</el-table-column>
			<el-table-column prop="password" label="密码" width="100px">
			</el-table-column>
			<el-table-column prop="address" label="地址" width="125px">
			</el-table-column>
			<el-table-column prop="email" label="邮箱" width="165px">
			</el-table-column>
			<el-table-column prop="phone" label="电话" width="120px">
			</el-table-column>
			<el-table-column label="操作">
				<template v-slot:default="scope">
					<el-popconfirm
								   confirm-button-text='确定删除'
								   cancel-button-text='取消删除'
								   icon="el-icon-info"
								   icon-color="red"
								   title="确定删除吗？"
								   @confirm="DeleteEdit(scope.row.id)"
								   class="ml-5">
						<el-button type="danger" @click="DeleteEdit(scope.row.id)" slot="reference" class="Userel-button">删除<i class="el-icon-remove-outline"> </i>
						</el-button>

					</el-popconfirm>
					<el-button type="warning" @click="handleEdit(scope.row)" @row-click="handleRowClick" class="Userel-button">修改<i class="el-icon-edit"></i>
					</el-button>
				</template>
			</el-table-column>
		</el-table>
    <!-- 数据分页框代码 -->
		<div style="padding: 50px 0; margin-left: auto; margin-right: auto;">
			<el-pagination
						   :current-page="pageNum"
						   :page-sizes="[1, 5, 10, 15]"
						   :page-size="pageSize"
						   layout="total, sizes, prev, pager, next, jumper"
						   :total="total"
						   @size-change="handleSizeChange"
						   @current-change="handleCurrentChange">
			</el-pagination>
		</div>

		<!-- 添加用户信息Form框 -->

		<el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" size="small">
			<el-form label-width="80px">
				<el-form-item label="用户名">
					<el-input v-model="form.username" autocomplete="off"></el-input>
				</el-form-item>
        <el-form-item label="密码">
					<el-input v-model="form.password" autocomplete="off" type="password" show-password></el-input>
				</el-form-item>
				<el-form-item label="角色">
<el-select clearable v-model="form.role" placeholder="请选择角色" style="width:80%">
    <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.role"></el-option>
</el-select>
				</el-form-item>
				<el-form-item label="昵称">
					<el-input v-model="form.nickname" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="邮箱">
					<el-input v-model="form.email" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="电话">
					<el-input v-model="form.phone" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="地址">
					<el-input v-model="form.address" autocomplete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="Cancel">取 消</el-button>
				<el-button type="primary" @click="Save">确 定</el-button>
			</div>
		</el-dialog>
	</div>

</template>
<script>
import Aside from "@/components/Aside.vue"; //侧边栏部分
import Header from "@/components/Header.vue"; //头部
import request from "@/utils/request.js"; //axios网络封装部分
export default {
  name: "User", //user页面
  components: {
    Aside,
    Header
  },
  data() {
    return {
      tableData: [],
      //分页数据
      total: 0,
      pageNum: 1,
      pageSize: 5,
      //用户数据
      email: "",
      address: "",
      username: "",
      //
      form: {},
      dialogFormVisible: false,
      multipleSelection: [], //接收多选行数据变量存储
      roles: []
    };
  },
  created() {
    // 请求分页查询数据
    this.load();
  },
  methods: {
    //重载
    load() {
      const params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        username: this.username,
        email: this.email,
        address: this.address
      };
      //网络请求
      this.request
        .post(
          "/user/page?pageNum=" +
            this.pageNum +
            "&pageSize=" +
            this.pageSize +
            "&username=" +
            this.username
        )
        .then(res => {
          console.log(res);
          if (res.data && res.data.records) {
            this.tableData = res.data.records; // 假设 records 是后端返回的数据列表
          }
          if (res.data && res.data.total) {
            // 将 total 字段转换为数字类型
            this.total = parseInt(res.data.total);
            // 假设 total 是后端返回的数据总数
            // this.total = res.data.total;
          }
        })
        .catch(error => {
          console.log(error);
        });
      this.request
        .post("/role/findAll")
        .then(res => {
          this.roles = res.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    //表头颜色
    getRowClass({ rowIndex, columnIndex }) {
      if (rowIndex === 0) {
        return "background:'#eef1f6',color:'#606266'";
      }
    },
    handleRowClick(row) {
      // 深拷贝当前行数据
      this.form = JSON.parse(JSON.stringify(row));
      // 显示弹出框
      this.dialogFormVisible = true;
    },
    /**
     *取消操作
     * **/
    Cancel() {
      // 取消操作
      this.dialogFormVisible = false;
      this.load();
    },
    //统计删除数据
    //选择数据方法handleSelectionChange
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val);
    },
    /**
     * 多用户
     * 批量删除操作
     * **/
    handleDelete() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning("请勾选至少一行");
        return;
      }

      this.$confirm("确定删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          const ids = this.multipleSelection.map(item => item.id);
          let successCount = 0; // 统计删除成功的条数
          let failureCount = 0;
          request.post("user/deleteBatch/", ids).then(res => {
            if (res.code === 200) {
              successCount = res.data.successCount; // 获取后端返回的删除成功的条数

              this.$message.success("删除成功");
              this.$message.success(`成功删除${successCount}条数据`);
              this.this.load();
            } else {
              failureCount = res.data.failureCount;
              this.$message.error("删除失败");
              this.$message.error(`删除${failureCount}条数据失败`);
            }
          });
        })
        .catch(() => {
          // 用户取消删除操作
          this.$message.info("已取消删除操作");
        });
    },
    /***
     *单用户
     * 删除确认
     * */
    DeleteEdit(id) {
      this.$confirm("确定删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          request
            .post("/user/deleteById" + id)
            .then(res => {
              if (res.code === 200) {
                this.$message.success("删除成功");
                this.load();
              } else {
                this.$message.error("删除失败");
              }
            })
            .catch(() => {
              // 发生错误
              this.$message.error("删除失败");
            });
        })
        .catch(() => {
          // 用户取消删除操作
          this.$message.info("已取消删除操作");
        });
    },
    /**
     * 插入操作
     * **/
    Save() {
      // 在开始处添加深拷贝
      // this.form = JSON.parse(JSON.stringify(row));
      request
        .post("/user/save", this.form)
        .then(res => {
          if (res.code === 200) {
            if (this.form.id) {
              this.$message.success("保存成功");
            }
          } else {
            this.$message.error("保存失败,用户名以存在");
          }
          this.dialogFormVisible = false;
          this.load();
        })
        .catch(error => {
          // 处理请求发送过程中发生的异常，例如网络错误等
          console.error("请求异常:", error);
          this.$message.error("保存失败，未知错误");
        });
    },
    //代码页码重载方法
    handleCalPageNum() {
      const params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        username: this.username,
        email: this.email,
        address: this.address
      };

      this.request
        .post("/user/page", params)
        .then(res => {
          console.log(res);
          if (res.data && res.data.records) {
            this.tableData = res.data.records; // 假设 records 是后端返回的数据列表
          }
          if (res.data && res.data.total) {
            this.total = parseInt(res.data.total); // 将 total 字段转换为数字类型

            // 重新计算页码
            this.pageNum = Math.ceil(this.total / this.pageSize);
          }
        })
        .catch(() => {
          // 发生错误
          this.$message.error("加载数据失败");
        });
    },
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    /***
     * 重载方法
     */
    reset() {
      this.username = "";
      this.email = "";
      this.address = "";
      this.load();
    },
    //修改方法
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      console.log(row);
      // 添加这行代码
      this.dialogFormVisible = true;
      this.load();
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
    },
    Export() {
      window.open("http://localhost:9090/user/export");
    },
    HandExcelImportSuccess() {
      this.$message("文件导入成功");
      this.load();
    }
  }
};
</script>
