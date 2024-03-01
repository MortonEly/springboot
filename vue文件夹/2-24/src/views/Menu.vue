<template>
  <div>
	<div style="margin: 10px 0">
    <!-- 搜索框组件 -->
		<el-input
				  style="width: 200px"
				  suffix-icon="el-icon-search"
				  placeholder="请输入名称"
				  class="ml-5"
				  v-model="name"></el-input>
<!-- 按钮组件 -->
		<el-button
				   style="width: 100px"
				   type="warning"
				   @click="reset"
				   class="menu_button">重置</el-button>
		<el-button
				   style="width: 100px"
				   type="primary"
				   @click="load"
				   class="menu_button">搜索</el-button>
	</div>
  <!-- 子菜单组件 -->
	<div style="padding: 20px 0; display: flex; gap: 10px"
		 class="button-container">
		<el-button id="role-button" type="primary" @click="handleAdd">新增子菜单<i class="el-icon-circle-plus-outline"></i>
		</el-button>
		<el-popconfirm
					   class="ml-5"
					   confirm-button-text="确定"
					   cancel-button-text="我再想想"
					   icon="el-icon-info"
					   icon-color="red"
					   title="您确定批量删除这些数据吗？"
					   @confirm="handleDelete">
			<el-button type="danger" slot="reference" id="delete-button">
				批量删除 <i class="el-icon-remove-outline"></i>
			</el-button>
		</el-popconfirm>

		<div></div>
	</div>
<!-- 主页数据 -->
	<el-table
			  :data="tableData"
			  border
			  stripe
			  :header-cell-class-name="'getRowClass'"
			  @selection-change="handleSelectionChange"
			  row-key="id"
			  default-expand-all
			  :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
		<el-table-column type="selection" width="55"></el-table-column>
		<el-table-column prop="id" label="ID" width="80"></el-table-column>
		<el-table-column prop="name" label="名称"></el-table-column>
		<el-table-column prop="description" label="描述"></el-table-column>
		<van-icon prop="icon" label="菜单图标" />
		<el-table-column
						 prop="icon"
						 label="图标"
						 class-name="fontSize18"
						 align="center">
			<template slot-scope="scope">
				<i :class="scope.row.icon" style="font-size: 35px"></i>
				<!-- <span style="float:left">{{cope.row.name}}</span> -->
			</template>
		</el-table-column>
		<el-table-column prop="path" label="路径"></el-table-column>
		<el-table-column prop="sortNum" label="排序"></el-table-column>
		<el-table-column label="操作" width="280" align="center">
			<template slot-scope="scope">
				<el-button
						   type="info"
						   @click="handleAdd(scope.row.id)"
						   v-if="!scope.row.pid && !scope.row.path">分配子菜单 <i class="el-icon-menu"></i>
				</el-button>
				<el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i>
				</el-button>
				<el-popconfirm
							   class="ml-5"
							   confirm-button-text="确定"
							   cancel-button-text="我再想想"
							   icon="el-icon-info"
							   icon-color="red"
							   title="您确定删除吗？"
							   @confirm="del(scope.row.id)">
					<el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i>
					</el-button>
				</el-popconfirm>
			</template>
		</el-table-column>
	</el-table>
	<div style="padding: 10px 0">
		<el-pagination
					   @size-change="handleSizeChange"
					   @current-change="handleCurrentChange"
					   :current-page="pageNum"
					   :page-sizes="[2, 5, 10, 20]"
					   :page-size="pageSize"
					   layout="total, sizes, prev, pager, next, jumper"
					   :total="total">
		</el-pagination>
	</div>
  <!-- 新增子菜单 -->
	<el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
		<el-form label-width="80px" size="small">
			<el-form-item label="菜单名称">
				<el-input v-model="form.name" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="菜单图标">
				<el-input v-model="form.path" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="页码路径">
				<el-input v-model="form.pagePath" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="排序">
				<el-input
						  v-model="form.sortNum"
						  autocomplete="off"
						  type="number"></el-input>
			</el-form-item>
      <!-- <el-form-item label="图标" label-width="formLabelWidth"> -->
      <el-form-item label="图标" >
          <el-select
            clearable
            v-model="form.icon"
            placeholder="请选择"
            style="width: 25%"
          >
            <el-option
              v-for="dict in dictList"
              :key="dict.name"
              :value="dict.value"
              :label="dict.name"
            >
        <i :class="dict.value" />&nbsp;{{dict.name}}
          
            </el-option>
          </el-select>
        </el-form-item>
			<!-- <el-form-item label="图标">
          <el-select
            clearable
            v-model="form.icon"
            placeholder="请选择"
            style="width: 25%"
          >
            <el-option
              v-for="item in options"
              :key="item.name"
              :value="item.value"
              :label="item.name"
            >
              <i :class="item.value" />{{item.name}}
          
            </el-option>
          </el-select>
        </el-form-item> -->
			<!-- <span style="float:left">{{item.name}}</span> -->
			<el-form-item label="描述">
				<el-input v-model="form.description" autocomplete="off"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click="dialogFormVisible = false">取 消</el-button>
			<el-button type="primary" @click="save">确 定</el-button>
		</div>
	</el-dialog>
<!-- 菜单分配框 -->
	<el-dialog title="菜单分配" :visible.sync="menuDialogVis" width="30%">
		<el-tree
				 :props="props"
				 :data="menuData"
				 show-checkbox
				 node-key="id"
				 ref="tree"
				 :default-expanded-keys="expends"
				 :default-checked-keys="checks">
			<span class="custom-tree-node" slot-scope="{ node, data }">
				<span>
					<i :class="data.icon"></i> {{ data.name }}
				</span>
			</span>
		</el-tree>
		<div slot="footer" class="dialog-footer">
			<el-button @click="menuDialogVis = false">取 消</el-button>
			<el-button type="primary" @click="save">确 定</el-button>
		</div>
	</el-dialog>
</div>
</template>

<script>
import request from "@/utils/request.js";
export default {
  name: "Menu",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form: {},
      dialogFormVisible: false,
      menuDialogVis: false,
      multipleSelection: [],
      menuData: [],
      props: {
        label: "name"
      },
      expends: [],
      checks: [],
      roleId: 0,
      roleFlag: "",
      ids: [],
      options: [],
      //字典数据管理
      dictList: [],
      formLabelWidth: "80px"
    };
  },

  created() {
    //重载
    this.load();
  },
  methods: {
    //分页接口
    load() {
      // this.request
      //   .get("/menu/findPage", {
      //     params: {
      //       pageNum: this.pageNum,
      //       pageSize: this.pageSize,
      //       name: this.name
      //     }
      //   })
      //   .then(res => {
      //     this.tableData = res.data.records;
      //     this.total = parseInt(res.data.total);
      //     console.log(res.data.records);
      //   });
      this.request
        .post("/menu/findAl", {
          params: {
            name: this.name
          }
        })
        .then(res => {
          this.tableData = res.data;
          console.log(res.data);
        });
      this.request
        .post("/dict/findAll", {
          params: {
            type: "icon"
          }
        })
        .then(res => {
          this.dictList = res.data;
        });
    },
    // 保存菜单方法接口
    save() {
      this.request.post("/menu/save", this.form).then(res => {
        if (res.code === "200") {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error("保存失败");
        }
      });
    },
    // 分配子菜单方法
    handleAdd(pid) {
      this.dialogFormVisible = true;
      this.form = {};
      if (pid) {
        this.form.pid = pid;
      }
    },

    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
      //获取icon
      // this.request.get("/dict/icon").then(res => {
      //   if (res.code === "200") {
      //     this.options = res.data;
      //   }
      // });
    },
    del(id) {
      this.request.delete("/menu/" + id).then(res => {
        if (res.code === "200") {
          this.$message.success("删除成功");
          this.load();
        } else {
          this.$message.error("删除失败");
        }
      });
    },
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
    },
    //批量删除菜单方法接口
    handleDelete() {
      let ids = this.multipleSelection.map(v => v.id); // [{}, {}, {}] => [1,2,3]
      request
        .post("user/deleteBatch/", ids) // 向后端发送批量删除请求
        .then(res => {
          if (res.code === 200) {
            // let successCount = res.data.successCount; // 获取后端返回的成功删除的条数
            this.$message.success("删除成功");
            // this.$message.success(`成功删除${successCount}条数据`);
            this.load(); // 重新加载数据
          } else {
            //    let failureCount = res.data.failureCount; // 获取后端返回的失败删除的条数
            this.$message.error("删除失败");
            //   this.$message.error(`删除${failureCount}条数据失败`);
          }
        })
        .catch(() => {
          // 用户取消删除操作
          this.$message.info("已取消删除操作");
        });
    },

    reset() {
      this.name = "";
      this.load();
    },
    //根据pageSize分页
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      this.load();
    },
    //根据pageSize分页
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum = pageNum;
      this.load();
    }

    //   handleExcelImportSuccess() {
    //     this.$message.success("导入成功");
    //     this.load();
    //   },
    //   selectMenu() {}
  }
};
</script>

<style>
.getRowClass {
  background: #eee !important;
}
</style>