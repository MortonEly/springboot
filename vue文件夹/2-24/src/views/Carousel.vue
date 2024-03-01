<template>
	<div>
		<div style="margin: 10px 0">
    <!-- 搜索栏 -->
			<el-input style="width:200px" suffix-icon="el-icon-search" placeholder="请输入名称" class="ml-5" v-model="name"></el-input>
			<el-button style="width:100px" type="warning" @click="reset" class="carousel_button">重置</el-button>
			<el-button style="width:100px" type="primary" @click="load" class="carousel_button">搜索</el-button>
		</div>
		<div style="padding: 20px 0; display: flex; gap: 10px;" class="button-container">
			<el-button id="carousel-button" type="primary" @click="handleAdd">
				新增轮播图<i class="el-icon-circle-plus-outline"></i>
			</el-button>
			<el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info" icon-color="red" title="您确定批量删除这些数据吗？" @confirm="delBatch">
				<el-button type="danger" slot="reference" id="delete-button">
					批量删除 <i class="el-icon-remove-outline"></i>
				</el-button>
			</el-popconfirm>
		</div>
<!-- 主体框架 -->
		<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="55"></el-table-column>
			<el-table-column prop="id" label="ID" width="80"></el-table-column>
			<el-table-column prop="name" label="名称"></el-table-column>
			<el-table-column prop="description" label="描述"></el-table-column>
			<el-table-column prop="flag" label="唯一标识"></el-table-column>
      <el-table-column prop="createTime" label="时间"></el-table-column>
 <el-table-column prop="updateTime" label="最后修改时间"></el-table-column>
 <el-table-column prop="isDeleted" label="是否能被删除"></el-table-column>
			<el-table-column label="操作" width="280" align="center">
				<template slot-scope="scope">
          <el-button  type="primary"  class="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
						<el-button type="danger" @click="DeleteEdit(scope.row.id)" slot="reference" class="Userel-button">删除<i class="el-icon-remove-outline"> </i>
						</el-button>


				</template>
			</el-table-column>
		</el-table>

		<div style="padding: 10px 0">
      <!-- 页脚 -->
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
<!-- 新增轮播图栏 -->
		<el-dialog title="轮播图信息栏" :visible.sync="dialogFormVisible" width="30%">
			<el-form label-width="80px" size="small">
				<el-form-item label="轮播图名称">
					<el-input v-model="form.name" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="上传图片">
					<el-input v-model="form.description" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="类型">
					<el-input v-model="form.type" autocomplete="off"></el-input>
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
  name: "Carousel",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      props: {
        label: "name"
      },
      expends: [],
      checks: [],
      ids: []
    };
  },
  created() {
    this.load();
  },
  methods: {
    //数据重载
    load() {
      this.request
        .get("/carousel/findPage", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name
          }
        })
        .then(res => {
          this.tableData = res.data.records;
          this.total = parseInt(res.data.total);
        });
    },
    //保存方法接口
    save() {
      this.request.post("/carousel/save", this.form).then(res => {
        if (res.code === 200) {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error("保存失败");
        }
      });
    },
    //新增方法接口
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    //编辑方法接口
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
    },
    //单个数据删除方法
    DeleteEdit(id) {
      this.$confirm("确定删除吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          request
            .post("/carousel/deleteById/" + id)
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

    //多个数据删除方法
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id); // [{}, {}, {}] => [1,2,3]
      this.request.post("/carousel/del/batch", ids).then(res => {
        if (res.code === 200) {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      });
    },

    //多选方法接口
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
    },
    //reset方法
    reset() {
      this.name = "";
      this.load();
    },
    //pageSize方法接口
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      this.load();
    },
    //pageNum方法接口
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