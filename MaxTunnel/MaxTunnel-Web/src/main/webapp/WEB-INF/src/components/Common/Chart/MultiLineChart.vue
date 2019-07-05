<template>
  	<div class='MultiLine' :id=id></div>
</template>

<script>
  	import {ChartService} from '../../../services/chartService.js'

	export default {
		name: "MultiLine",
		props: {
			id: {
				type: String
			},
			requestUrl: {
				type: String
			},
			parameters: {
				type: Object
			}
		},
		data() {
			return {
				myChart: {},
				xData: [],
				yData: [],
				legendData: [],
				series: [],
				option: {
					title: {
						text: ''
					},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						data: [],
						textStyle: { 
							color: '#fff'
						}
					},
					grid: {},
					toolbox: {
						show: true,
						feature: {
						saveAsImage: {}
						}
					},
					dataZoom: {
						type: 'inside'
					},
					xAxis: {
						type: 'category',
						boundaryGap: false,
						data: [],
						axisLabel: {
							show: true,
							textStyle: {
								color: '#fff'
							},                            
						}
					},
					yAxis: {
						type: 'value',
						axisLabel: {
							show: true,
							textStyle: {
								color: '#fff'
							},                            
						}
					},
					series: []
				},
			}
		},
		mounted() {
			this.init();
			this.refreshData();
		},
		methods: {
			init() {
				this.drawMultiLine();
				this.fetchData();
			},
			drawMultiLine() {
				let _this = this;
				_this.myChart = _this.$echarts.init(
					document.getElementById(_this.id)
				);
				// 加载默认参数
				_this.myChart.setOption(_this.option);
				// 加载新的参数
				if (_this.parameters.option) {
					_this.myChart.setOption(_this.parameters.option);
				}
				window.addEventListener("resize", _this.myChart.resize);
			},
			fetchData() {
				let _this = this;
				if (_this.parameters.queryPram) {
					var prams = _this.parameters.queryPram;
					prams.requestUrl = _this.requestUrl;
					ChartService.getMulitLineData(prams).then((result) => {
						if (result) {
							_this.series = []
							_this.legendData = []
							_this.xData=[]
							result.val.forEach(a => {
								let temp = {}
								temp.name = a.key
								temp.type = "line"
								temp.data = a.val
								_this.legendData.push(a.key);
								_this.series.push(temp);
							});
							// _this.series.roam = true
							result.key.forEach(a=>{
								_this.xData.push(new Date(a).format("yy-MM-dd hh:mm:ss"))
							});
							_this.myChart.setOption({
								legend: {data: _this.legendData},
								xAxis: {data: _this.xData},
								series: _this.series,
							})
						}
					})
				}
			},
			//定时刷新数据
			refreshData() {
				let _this = this;
				// setInterval(() => {
				//   _this.fetchData(_this.requestUrl);
				// }, _this.intervalTime)
			}
		}
	};
</script>

<style scoped>
	.MultiLine {
		width: 100%;
		height: 100%;
	}
</style>
