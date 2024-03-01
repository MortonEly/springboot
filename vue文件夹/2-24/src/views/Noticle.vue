<template>
<div>
	<div style="margin: 10px 0">
		<!-- 搜索栏 -->
		<el-input style="width:200px" suffix-icon="el-icon-search" placeholder="请输入标题" class="ml-5" v-model="title"></el-input>
		<el-input style="width:200px" suffix-icon="el-icon-user" placeholder="请输入创建者名称" class="ml-5" v-model="user"></el-input>
		<!-- 搜索栏按钮 -->
		<el-button style="width:100px" type="warning" @click="reset" class="notice_button">重置</el-button>
		<el-button style="width:100px" type="primary" @click="load" class="notice_button">搜索</el-button>
	</div>
	<div style="padding: 20px 0; display: flex; gap: 10px;" class="button-container">
		<el-button id="notice-button" type="primary" @click="handleAdd">
			新增公告<i class="el-icon-circle-plus-outline"></i>
		</el-button>
		<el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info" icon-color="red" title="您确定批量删除这些数据吗？" @confirm="delBatch">
			<el-button type="danger" slot="reference" id="delete-button">
				批量删除 <i class="el-icon-remove-outline"></i>
			</el-button>
		</el-popconfirm>
	</div>
<!-- 主体框架 -->
	<el-table :data="tableData"
			  border stripe
			  :header-cell-class-name="'headerBg'"
			  @selection-change="handleSelectionChange">
		<el-table-column type="selection" width="55"></el-table-column>
		<el-table-column prop="id" label="ID" width="80"></el-table-column>
    <el-table-column prop="title" label="公告标题"></el-table-column>
		<el-table-column prop="content" label="公告内容">
			<template slot-scope="scope">
			<el-card>{{scope.row.content}}</el-card>
			</template>
		</el-table-column>
		<el-table-column prop="user" label="创建用户"></el-table-column>
		<el-table-column prop="createTime" label="创建时间"></el-table-column>
		
		<el-table-column label="操作" width="280" align="center">
			<template slot-scope="scope">
			
				<el-button type="danger" @click="DeleteEdit(scope.row.id)" slot="reference" class="Userel-button">删除<i class="el-icon-remove-outline"> </i>
				</el-button>


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
<!-- 新增公告信息栏 -->
	<el-dialog title="公告信息栏" :visible.sync="dialogFormVisible" width="30%">
		<el-form label-width="100px" size="small">
			<el-form-item label="公告名称">
				<el-input v-model="form.title" autocomplete="off"></el-input>
			</el-form-item>

			<el-form-item label="创建者">
				<el-input v-model="form.user" autocomplete="off"></el-input>
			</el-form-item>

			<el-form-item label="公告类型">
				<el-input v-model="form.type" autocomplete="off"></el-input>
			</el-form-item>
<el-form-item label="公告内容">
    <el-input v-model="form.content" autocomplete="off" type="textarea" :rows="15"></el-input>
</el-form-item>


		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click="dialogFormVisible = false">取 消</el-button>
			<el-button type="primary" @click="save">确 定</el-button>
		</div>
	</el-dialog>
</div>
</template>

<script>
export default {
  name: "notice",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      title: "",
      user: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      props: {
        label: "name"
      },
      ids: []
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.request
        .get("/notice/findPage", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            title: this.title,
            user: this.user
          }
        })
        .then(res => {
          this.tableData = res.data.records;
          this.total = parseInt(res.data.total);
        });
    },
    save() {
      this.request.post("/notice/save", this.form).then(res => {
        if (res.code === 200) {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error("保存失败");
        }
      });
    },

    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
    },
    DeleteEdit(id) {
      this.$confirm("确定删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          request
            .post("/notice/deleteById/" + id)
            .then(res => {
              if (res.code === 200) {
                this.$message.success("删除成功");
                this.load();
              } else {
                this.$message.error("删除失败");
                this.load();
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
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
    },

    //批量删除数据
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id); // [{}, {}, {}] => [1,2,3]
      this.request.post("/notice/del/batch", ids).then(res => {
        if (res.code === 200) {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      });
    },

    reset() {
      this.title = "";
      this.user = "";
      this.load();
    },

    //pageSize
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      this.load();
    },
    //pageNum
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum = pageNum;
      this.load();
    }
  }
};
</script>


<style>
.headerBg {
  background: #eee !important;
}
</style>