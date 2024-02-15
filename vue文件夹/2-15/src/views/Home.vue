<template>
	<div>
		<el-row type="flex" :gutter="10" style="margin-bottom:50px">
			<el-col :span="8">
				<el-card style="color:#409EFF;font-family:Kaiti">
					<div ><i class="el-icon-user-solid"></i>用户总数</div>
          <div style="padding:10px 10px; text-align:center;font-weight: normal"></div>
              <!-- <el-tag style="font-size:20px">100</el-tag> -->
              100
				</el-card>

			</el-col>

			<el-col :span="8">
				<el-card style="color:#F56C6C">
<div ><i class="el-icon-s-goods"></i>商品数量</div>
          <div style="padding:10px 10px; text-align:center;font-weight: normal"></div>
100

        </el-card>
			</el-col>

			<el-col :span="8">
				<el-card style="color:#67C23A"><div><i class="el-icon-goblet-square"></i>收益  </div>
          <div style="padding:10px 10px; text-align:center;font-weight: normal"></div>
1000￥
				</el-card>
			</el-col>

			<el-col :span="8">
				<el-card style="color:#DCDFE6"><div ><i  class="el-icon-school"></i>数量</div>
          <div style="padding:10px 10px; text-align:center;font-weight: normal"></div>
100￥
				</el-card>
			</el-col>
		</el-row>
		<el-row type="flex">
			<el-col :span="8">
				<div ref="lineChart" style="width: 500px; height: 400px"></div>
			</el-col>
			<el-col :span="8">
				<div ref="barChart" style="width: 500px; height: 400px"></div>
			</el-col>
			<el-col :span="8">
				<div ref="mainChart" style="width: 500px; height: 400px"></div>
			</el-col>
		</el-row>
		<el-row type="flex">
			<el-col :span="10">
				<div ref="seasonChart" style="width: 500px; height: 400px"></div>
			</el-col>
			<el-col :span="10">
				<div ref="pieChart" style="width: 500px; height: 400px"></div>
			</el-col>
			<el-col :span="10">
				<div ref="pieUserChart" style="width: 500px; height: 400px"></div>
			</el-col>
		</el-row>

	</div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "Home",
  mounted() {
    this.drawLineChart();
    this.drawBarChart();
    this.drawMainChart();
    this.drawSeansonChart();
    this.drawpieChart();
    this.drawpieUserChart();
  },
  methods: {
    drawLineChart() {
      const lineChartDom = this.$refs.lineChart;
      const lineChart = echarts.init(lineChartDom);
      const option = {
        xAxis: {
          type: "category",
          data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: "line"
          }
        ]
      };
      lineChart.setOption(option);
    },
    drawBarChart() {
      const barChartDom = this.$refs.barChart;
      const barChart = echarts.init(barChartDom);
      const option = {
        xAxis: {
          type: "category",
          data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: "bar"
          }
        ]
      };
      barChart.setOption(option);
    },
    drawMainChart() {
      this.request
        .get("/echarts/example")
        .then(response => {
          const data = response.data;
          const mainChartDom = this.$refs.mainChart;
          const mainChart = echarts.init(mainChartDom);
          const option = {
            xAxis: {
              type: "category",
              data: data.x
            },
            yAxis: [
              {
                type: "value"
              },
              {
                type: "value"
              }
            ],
            series: [
              {
                data: data.y,
                type: "line",
                yAxisIndex: 0
              },
              {
                data: data.y,
                type: "bar",
                yAxisIndex: 1
              }
            ]
          };
          mainChart.setOption(option);
        })
        .catch(error => {
          console.error(error);
        });
    },
    drawSeansonChart() {
      this.request // 假设 this.request 是你的网络请求方法
        .get("/echarts/members")
        .then(response => {
          const data = response.data;
          const mainChartDom = this.$refs.seasonChart;
          const mainChart = echarts.init(mainChartDom);
          const option = {
            title: {
              text: "会员数量图",
              left: "center"
            },
            xAxis: {
              type: "category",
              data: ["第一季度", "第二季度", "第三季度", "第四季度"]
            },
            yAxis: [
              {
                type: "value"
              },
              {
                type: "value"
              }
            ],
            series: [
              {
                data: Object.values(data), // 将对象的值转换成数组
                type: "line",
                yAxisIndex: 0
              },
              {
                data: Object.values(data), // 将对象的值转换成数组
                type: "bar",
                yAxisIndex: 1
              }
            ]
          };
          mainChart.setOption(option);
        })
        .catch(error => {
          console.error(error);
        });
    },
    drawpieChart() {
      var pieChartDom = this.$refs.pieChart;
      var pieChart = echarts.init(pieChartDom);
      var option;
      option = {
        title: {
          text: "Referer of a Website",
          subtext: "Fake Data",
          left: "center"
        },
        tooltip: {
          trigger: "item"
        },
        legend: {
          orient: "vertical",
          left: "left"
        },
        series: [
          {
            name: "Access From",
            type: "pie",
            radius: "50%",
            data: [
              { value: 1048, name: "Search Engine" },
              { value: 735, name: "Direct" },
              { value: 580, name: "Email" },
              { value: 484, name: "Union Ads" },
              { value: 300, name: "Video Ads" }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      };
      pieChart.setOption(option); // 应用option到echarts图表中
    },
    drawpieUserChart() {
      this.request
        .get("/echarts/members")
        .then(response => {
          var pieUserChartDom = this.$refs.pieUserChart;
          var pieUserChart = echarts.init(pieUserChartDom);
          var data = response.data;
          var option = {
            title: {
              text: "User Distribution by Quarter",
              left: "center"
            },
            tooltip: {
              trigger: "item",
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient: "vertical",
              left: "left"
            },
            series: [
              {
                label: {
                  //饼图图形上的文本标签
                  normal: {
                    show: true,
                    position: "inner", //标签的位置
                    textStyle: {
                      fontWeight: 300,
                      fontSize: 16 //文字的字体大小
                    },
                    formatter: "{d}%"
                  }
                },
                name: "Quarter",
                type: "pie",
                radius: "50%",
                data: [
                  { value: data.Q1, name: "Q1" },
                  { value: data.Q2, name: "Q2" },
                  { value: data.Q3, name: "Q3" },
                  { value: data.Q4, name: "Q4" }
                ],
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: "rgba(0, 0, 0, 0.5)"
                  }
                }
              }
            ]
          };
          pieUserChart.setOption(option);
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>