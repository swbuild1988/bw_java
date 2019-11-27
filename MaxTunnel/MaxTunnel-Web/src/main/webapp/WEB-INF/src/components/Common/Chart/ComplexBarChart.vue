<template>
	<div class="ComplexBar" :id="id" ref="element"></div>
</template>

<script>
	import {
		ChartService
	} from '@/services/chartService.js'
	export default {
		name: "complex-bar-chart",
		props: {
			id: {
				default: ""
			},
			requestUrl: {
				default: ""
			},
			yAxisName: {
				default: ""
			},
			title: {
				default: "ComplexBar"
			},
			color: {
				default: "#336666"
			},
			intervalTime: {
				default: 60000
			},
			fontSizeNum: {
				default: "5%",
				type: String
			},
			gridTop: {
				default: "10%",
				type: String
			},
			textColor: {
				default: '#fff',
				type: String
			},
			itemStyleColor: {
				type: Array,
				default: function () {
					return ['#b945ef', '#f8a42b', '#38db2c', '#45ef8d', '#2734e1', '#ef45d9']
				}
			}
		},
		data() {
			return {
				myChart: {},
				xData: [],
				yData: [],
				series: [],
				option: {
					title: {
						text: "title",
						fontSize: 25
					},
					tooltip: {
						trigger: "axis",
						fontSize: 16,
						axisPointer: {
							// 坐标轴指示器，坐标轴触发有效
							type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
						}
					},
					legend: {
						align: "right",
						right: 10,
						textStyle: {
							fontSize: 16
						}
					},
					xAxis: [{
						type: "category",
						axisLabel: {
							fontSize: 16
						},
						data: []
					}],
					yAxis: [{
						type: "value",
						fontSize: 16,
						name: "单位(个)",
						nameTextStyle: {
							color: "#5fb6ff",
							fontSize: 14 //单位：件的字号大小
						}
					}],
					series: []
				}
			};
		},
		mounted() {
			this.init();
			this.refreshData();
		},
		methods: {
			init() {
				this.drawComplexBar();
				this.fetchData(this.requestUrl);
				window.addEventListener("resize", this.drawComplexBar);
			},

			drawComplexBar() {
				let _this = this;
				_this.myChart = _this.$echarts.init(document.getElementById(_this.id));
				_this.option.title.text = _this.title;
				this.option = {
					title: {
						text: this.title,
						textStyle: {
							fontSize: this.getFontSize(this.fontSizeNum),
							color: this.textColor
						},
						top: "2%"
					},
					tooltip: {
						trigger: "axis",
						fontSize: this.getFontSize(this.fontSizeNum),
						axisPointer: {
							// 坐标轴指示器，坐标轴触发有效
							type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
						}
					},
					legend: {
						align: "right",
						// right: 1,
						// left: 30,
						// x: 'right',
						top: _this.getFontSize(this.fontSizeNum),
						textStyle: {
							fontSize: this.getFontSize("5%"),
							color: this.textColor
						}
					},
					grid: {
						left: "4%",
						right: "4%",
						bottom: "5%",
						top: this.gridTop,
						containLabel: true,
						fontSize: this.getFontSize("5%")
					},
					xAxis: [{
						type: "category",
						axisLabel: {
							fontSize: this.getFontSize(this.fontSizeNum),
							color: this.textColor
						},
						axisLine: {
							lineStyle: {
								color: this.textColor
							}
						},
						data: []
					}],
					yAxis: [{
						type: "value",
						// name: '单位  (个)',
						nameTextStyle: {
							color: "#5fb6ff",
							fontSize: 14 //单位：件的字号大小
						},
						axisLine: {
							lineStyle: {
								color: this.textColor
							}
						},
						axisLabel: {
							fontSize: this.getFontSize(this.fontSizeNum),
							color: this.textColor
						}
					}],
					series: []
				};
				_this.myChart.setOption(_this.option);
				window.addEventListener("resize", _this.myChart.resize);
			},

			fetchData(requestUrl) {
				var _this = this
				ChartService.getData(requestUrl).then(
					result => {
						let serises = [],
							xData = [];
						if (JSON.stringify(result) != null) {
							for (var item in result) {
								let temp = {};
								temp.name = item;
								let tempdata = result[item];
								xData = [];
								let yData = [];
								tempdata.forEach(a => {
									xData.push(a.key);
									yData.push(a.val);
								});
								temp.data = yData;
								temp.type = "bar";
								serises.push(temp);
							}
						}
						_this.series = serises;
						_this.xData = xData;
						//测试使用模拟静态数据
						_this.myChart.setOption({
							xAxis: {
								data: _this.xData
							},
							series: _this.series
						});
					},
					error => {
						this.Log.info(error)
					}
				)
			},
			//定时刷新数据
			refreshData() {
				let _this = this;
				setInterval(() => {
					_this.fetchData(_this.requestUrl);
				}, _this.intervalTime);
			},
			getFontSize(val) {
				if (typeof val == "number") return val;

				if (typeof val == "string") {
					if (val.indexOf("%") > 0) {
						var tmp = parseFloat(val.replace("%", "")) / 100;
						let height = this.$refs.element.offsetHeight;
						return Math.round(height * tmp);
					}
				}
				return 0;
			}
		}
	};
</script>

<style scoped>
	.ComplexBar {
		width: 100%;
		height: 100%;
	}
</style>