<template>
  <div>
	<div style="margin: 10px 0; display: flex; align-items: center; padding:15px,15px">
		<el-input style="width: 200px; margin-right: 10px;" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
		<!-- <el-button class="subjectbutton" type="primary" @click="load">搜索</el-button>
		<el-button type="warning" @click="reset" class="subjectbutton">重置</el-button> -->
	</div>

	<div style="margin: 10px 0; display: flex; align-items: center;">

		<!-- action：必要属性，上传文件的地址，可以不给，但必须要有，不给就i调接口上传 -->

<!-- 上传文件方法 -->
		<el-upload
				   action="http://localhost:9090/file/upload"
				   :show-file-list="false"
				   :on-success="handleFileUploadSuccess"
				   :on-error="handleFileUploadError"
				   style="display: inline-block">
			<el-button type="primary" class="filebutton">上传文件 <i class="el-icon-top"></i></el-button>
		</el-upload>

		<!-- <el-button type="danger" slot="reference" class="filebutton">批量删除 <i class="el-icon-remove-outline"></i>
		</el-button> -->
	</div>
	<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
		<el-table-column type="selection" width="55px"></el-table-column>
		<el-table-column prop="id" label="ID" width="80px"></el-table-column>
		<el-table-column prop="name" label="文件名称" width="200px"></el-table-column>
		<el-table-column prop="type" label="文件类型" width="80px"></el-table-column>
		<el-table-column prop="size" label="文件大小(kb)" width="120px"></el-table-column>
		<el-table-column label="预览" width="120px">
			<template slot-scope="scope">
				<el-button type="primary" @click="view(scope.row.url)" class="file_button">预览</el-button>
			</template>
		</el-table-column>
    <!-- 启用文件按钮 -->
		<el-table-column label="是否启用" width="80">
			<template slot-scope="scope">
				<el-switch v-model="scope.row.enable"
						   active-color="#13ce66"
						   inactive-color="#ccc"
						   @change="changeEnable(scope.row)"
						   :active-value="1"
						   :inactive-value="0">
				</el-switch>
			</template>
		</el-table-column>
  <!-- 操作栏按钮 -->
		<el-table-column label="操作" width="200" align="center">
			<template slot-scope="scope">

				<el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i>
				</el-button>
				<el-button type="primary" @click="download(scope.row.url)">下载 <i class="el-icon-download"></i>
				</el-button>
			</template>
		</el-table-column>
	</el-table>
  <!-- 分页栏 -->
	<div style="padding: 10px 0">
		<el-pagination
					   @size-change="handleSizeChange"
					   @current-change="handleCurrentChange"
					   :current-page="pageNum"
					   :page-sizes="[1, 5, 10, 20]"
					   :page-size="pageSize"
					   layout="total, sizes, prev, pager, next, jumper"
					   :total="total">
		</el-pagination>
	</div>
  <el-dialog title="图片信息栏" :visible.sync="dialogFormVisible" width="30%">
			<img width="100% :src=dialogImageUrl " alt="图片">
		</el-dialog>
</div>
</template>

<script>
export default {
  name: "File",
  data() {
    return {
      tableData: [],
      name: "",
      multipleSelection: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      dialogImageUrl: "",
      dialogFormVisible: false //图片信息栏收缩
    };
  },
  created() {
    this.load();
  },
  methods: {
    //重载方法
    load() {
      // 假设request对象已经通过props或mixins引入
      this.request
        .get("/file/findPage", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name
          }
        })
        .then(res => {
          this.tableData = res.data.records;
          this.total = parseInt(res.data.total);
        })
        .catch(error => {
          console.error("Failed to fetch data:", error);
          this.$message.error("数据加载失败");
        });
    },
    //浏览
    view(url) {
      this.dialogImageUrl = url;
      this.dialogFormVisible = false;
    },
    //启用文件
    changeEnable(row) {
      this.request.post("/file/updateEnable", row).then(res => {
        if (res.code === 200) {
          this.$message.success("操作成功");
        } else {
          this.$message.error("操作失败");
        }
      });
    },
    //单个删除文件
    del(id) {
      this.request.delete("/file/" + id).then(res => {
        if (res.code === "200") {
          this.$message.success("删除成功");
          this.load();
        } else {
          this.$message.error("删除失败");
        }
      });
    },
    //批量删除文件
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id); // [{}, {}, {}] => [1,2,3]
      this.request.post("/file/delete/deletebatch", ids).then(res => {
        if (res.code === "200") {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      });
    },

    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
    },

    //重置
    reset() {
      this.name = "";
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
    },

    //上传成功
    handleFileUploadSuccess(res, file) {
      console.log(res);
      this.$message.success("上传成功");
      this.load();
    },
    //上传失败
    handleFileUploadError() {
      this.$message.error("上传失败");
      this.load();
    },
    download(url) {
      window.open(url);
    }
  }
};
</script>