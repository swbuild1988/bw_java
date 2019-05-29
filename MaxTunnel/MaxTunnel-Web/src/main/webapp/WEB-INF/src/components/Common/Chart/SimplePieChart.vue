<template>
  	<div  class="SimplePie" :id="id" ref="simplePieChart"></div>
</template>
<style scoped>
.SimplePie {
	position: relative;
	height: 100%;
	width: 100%;	
}
</style>
<script>
export default {
	name: "SimplePie",
	props: {
		id: {
			type: String
		},
		requestUrl: {
			type: String
		},
		parameters: {
			type: Object
		},
		titleSize: {
			default: "5%",
			type: String
		},
		titleColor: {
			default: '#fff',
			type: String
		},	
		legendTextSize: {
			default: '5%',
			type: String
		},
		seriesSize: {
			default: "6%",
			type: String
		},
		seriesColor: {
			type: Array,
			default: function(){
				return ['#C23531','#2F4554','#61A0A8','#D48265','#91C7AE','#749F83']
			}
		},
		legendColor: {
			default: '#cccc',
			type: String
		}
	},
	data() {
		return {
		myChart: {},
		option: {
			legend: {
				data: []
			},
			series: [
				{
					type: "pie",
					radius: "65%",
					center: ["62%", "58%"],
					data: [],
					color: this.seriesColor
				}
			]
		}
		};
	},
	mounted() {
		this.init();
		// this.refreshData();
	},
	methods: {
		init() {
			this.drawBar();
			this.fetchData(this.requestUrl);
		},
		drawBar() {
			let _this = this;
			_this.myChart = _this.$echarts.init(document.getElementById(_this.id));
			// 加载默认参数
			_this.myChart.setOption(_this.option);
			_this.myChart.setOption({
				title: {
					left: "top",
					top: "2%",
					textStyle: {
						fontSize: _this.getFontSize(this.titleSize),
						color: _this.titleColor
					}
				},
				legend: {
					data: [],
					margin: _this.getFontSize(1),
					itemGap: 1,
					itemWidth: 20,
					x: "left",
					y: 'center',
					orient: "vertical",
					left: 20,
					textStyle: {
						color: _this.legendColor,
						fontSize: _this.getFontSize(this.legendTextSize)
					}
				},
				series: {
					label: {
						normal: {
							formatter: "{c}%",
							textStyle: {
								fontSize: _this.getFontSize(this.seriesSize)
							}
						},
					},
					labelLine: {
						normal: {
							length: 8,
							length2: 0	
						}
					}
				}
		});
		// 加载新的参数
		if (_this.parameters.option) {
			_this.myChart.setOption(_this.parameters.option);
		}
		window.addEventListener("resize", _this.myChart.resize);
		window.addEventListener("resize", _this.init);
		},
		fetchData(requestUrl) {
		let _this = this;
		if (this.parameters.params) {
			_this.axios.post(requestUrl, this.parameters.params).then(result => {
				let { code, data } = result.data;
				if (code == 200) {
					_this.option.series[0].data = [];
					_this.option.legend.data = [];
					var tempCount = 0;
					data.filter(function(item) {
						tempCount += item.val;
					});
					_this.option.series[0].data = data.map(curObj => {
					_this.option.legend.data.push(curObj.key);
						return {
							value: ((100 * curObj.val) / tempCount).toFixed(2),
							name: curObj.key
						};
					});
					_this.myChart.setOption(_this.option);
				}
			})
			.finally(()=>{
				if (this.parameters.timer) {
				setTimeout(() => {
					_this.option.series[0].data.forEach(a => (a.val = 1));
					_this.myChart.setOption(_this.option);
					_this.fetchData(_this.requestUrl);
				}, this.parameters.timer);
			}
			});
		} else {
			_this.axios.get(requestUrl).then(result => {
			let { code, data } = result.data;
			if (code == 200) {
				_this.option.series[0].data = [];
				_this.option.legend.data = [];
				var tempCount = 0;
				data.filter(function(item) {
					tempCount += item.val;
				});
				_this.option.series[0].data = data.map(curObj => {
					_this.option.legend.data.push(curObj.key);
					return {
						value: ((100 * curObj.val) / tempCount).toFixed(2),
						name: curObj.key
					};
				});
				_this.myChart.setOption(_this.option);
			}
			})
			.finally(()=>{
				if (this.parameters.timer) {
					setTimeout(() => {
						_this.option.series[0].data.forEach(a => (a.val = 1));
						_this.myChart.setOption(_this.option);
						_this.fetchData(_this.requestUrl);
					}, this.parameters.timer);
				}
			});;
		}
		},
		//定时刷新数据
		refreshData() {
		let _this = this;
		if (_this.parameters.timer) {
			// let { intervalId, intervalTime } = _this.parameters.timer;
			// console.log("intervalTime",intervalTime)
			// intervalId = setInterval(() => {
			// _this.option.series[0].data.forEach(a => (a.val = 1));
			// _this.myChart.setOption(_this.option);
			// _this.fetchData(_this.requestUrl);
			// }, intervalTime);
			setInterval(() => {
			_this.option.series[0].data.forEach(a => (a.val = 1));
			_this.myChart.setOption(_this.option);
			_this.fetchData(_this.requestUrl);
			}, _this.parameters.timer);
		}
		},
		sizeFunction(x) {
			var min = Math.min.apply( null, this.series.map(o1 => {
				return Math.min.apply(
					null,
					o1.map(o2 => {
					return parseFloat(o2[2]);
					})
				);
			})
		);
		var max = Math.max.apply(
			null,
			this.series.map(o1 => {
			return Math.max.apply(
				null,
				o1.map(o2 => {
				return parseFloat(o2[2]);
				})
			);
			})
		);

		// 最小的5%，最大的15%
		var y = 5 + ((x - min) / (max - min)) * (15 - 5);
		var size = this.getFontSize(y + "%");
		return size;
		},
		getFontSize(val) {
		if (typeof val == "number") return val;

		if (typeof val == "string") {
			if (val.indexOf("%") > 0) {
			var tmp = parseFloat(val.replace("%", "")) / 100;
			let height = this.$refs.simplePieChart.offsetHeight;
			return Math.round(height * tmp);
			}
		}

		return 0;
		}
	}
};
</script>
