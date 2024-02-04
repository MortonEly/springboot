<template>
  <div>
<div style="margin: 10px 0"> 
        <el-input style="width:200px" suffix-icon="el-icon-search" placeholder="请输入名称" class="ml-5" v-model="name"></el-input>
        <el-button style="width:100px" type="warning" @click="reset" class="menu_button">重置</el-button>
        <el-button style="width:100px" type="primary" @click="load" class="menu_button">搜索</el-button>
    </div>
    <div style="padding: 20px 0; display: flex; gap: 10px;" class="button-container">
        <el-button id="role-button" type="primary" @click="handleAdd">
            新增<i class="el-icon-circle-plus-outline"></i>
        </el-button>
        <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info" icon-color="red" title="您确定批量删除这些数据吗？" @confirm="delBatch">
            <el-button type="danger" slot="reference" id="delete-button">
                批量删除 <i class="el-icon-remove-outline"></i>
            </el-button>
        </el-popconfirm>

        <div>
    
        </div>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange" row-key="id" default-all >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>      <el-table-column prop="description" label="描述"></el-table-column>
      <van-icon prop="icon" label="图标"/>
      <el-table-column prop="icon" label="图标" class-name="fontSize18" align="center" >
        <template slot-scope="scope">
<i :class="scope.row.icon" style="font-size:35px"></i>
<!-- <span style="float:left">{{cope.row.name}}</span> -->
        </template>
      </el-table-column>
      <el-table-column prop="path" label="路径"></el-table-column>
      <el-table-column label="操作"  width="280" align="center">
        <template slot-scope="scope">
          <el-button type="info" @click="handleAdd(scope.row.id)"  v-if="!scope.row.pid && !scope.row.path">分配菜单 <i class="el-icon-menu"></i></el-button>
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
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

    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
          <el-form-item label="页面路径">
          <el-input v-model="form.pathPage" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
<el-select clearable v-model="form.icon" placeholder="请选择" style="width:25%">
  <el-option v-for="item in options" :key="item.name" :value="item.value" :label="item.name">
  <i :class="item.value"/>{{item.name}}
  <!-- <span style="float:left">{{item.name}}</span> -->
  </el-option>
</el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

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
            <span><i :class="data.icon"></i> {{ data.name }}</span>
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
      options: []
    };
  },

  created() {
    //重载
    this.load();
  },
  methods: {
    //分页接口
    load() {
      this.request
        .get("/menu/findPage", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name
          }
        })
        .then(res => {
          this.tableData = res.data.records;
          this.total = parseInt(res.data.total);
          console.log(res.data.records);
        });
    },
    save() {
      this.request.post("/menu", this.form).then(res => {
        if (res.code === "200") {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error("保存失败");
        }
      });
    },
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
      this.request.get("/menu/icon").then(res => {
        if (res.code === "200") {
          this.options = res.data;
        }
      });
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
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id); // [{}, {}, {}] => [1,2,3]
      this.request.post("/menu/del/batch", ids).then(res => {
        if (res.code === "200") {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      });
    },
    reset() {
      this.name = "";
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
    handleExcelImportSuccess() {
      this.$message.success("导入成功");
      this.load();
    },
    selectMenu() {}
  }
};
</script>

<style>
.headerBg {
  background: #eee !important;
}
</style>