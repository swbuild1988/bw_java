<template>
	<div class="Main">
		<div class="Title">
			<module-title :title="title"></module-title>
		</div>

		<!-- <div class="info" :key="key" v-for="(item,key) in countData">
			<div class="infoTitle">
				<div v-if="item.type==1">
					<div style="float: left;margin-left: 5%;width:30%;margin-top: 6.5%;">
						<img :src="jinGaiUrl" width="60%">
					</div>
					<div class="infoTitle">
						<p>{{item.name}}</p>
						<span
							style="color: #ffd50a;margin-left: 1vw;margin-right: 1vw;font-size: 2.5vmin;"
						>{{item.value}}</span>个
					</div>
				</div>
				<div v-else>
					<div style="float: left;margin-left: 5%;width:30%;margin-top: 6.5%;">
						<img :src="userUrl" width="60%">
					</div>
					<div class="infoTitle">
						<p>{{item.name}}</p>
						<span
							style="color: #ffd50a;margin-left: 1vw;margin-right: 1vw;font-size: 2.5vmin;"
						>{{item.value}}</span>人
					</div>
				</div>
			</div>
		</div> -->

		<div v-for="(item,key) in gaugeChart" :key="key" :class="'myChart myChart_item_'+key">
			<Simple-gauge v-bind="item" :ref="item.id"></Simple-gauge>
		</div>
	</div>
</template>

<script>
	import ModuleTitle from "../../../components/VM2/ModuleTitle";
	import SimpleGauge from "../../../components/Common/Chart/SimpleGauge";
	import {
		MeasObjServer
	} from "../../../services/MeasObjectSerivers";
	import userImg from "../../../assets/VM/userIcon.png";
	import jinGaiImg from "../../../assets/VM/jinGai.png";
	export default {
		data() {
			return {
				title: "环境监测",
				userUrl: userImg,
				jinGaiUrl: jinGaiImg,
				iconSize: window.innerWidth * 0.012,
				fetchTime: 5000,
				countData: [],
				gaugeChart: [{
						name: "最高温度",
						type: 1, //数据类型为温度的枚举值
						time: "",
						id: "temperatureGaugeChart",
						gaugeData: [],
						parameters: {
							option: {
								title: {
									subtext: "",
									subtextStyle: {
										fontWeight: 'bolder',
									},
								},
								series: [{
									type: 'gauge',
									min: 0,
									max: 220,
									splitNumber: 5,
									radius: '90%',
									axisLine: { // 坐标轴线
										lineStyle: { // 属性lineStyle控制线条样式
											color: [
												[0.09, 'lime'],
												[0.82, '#1e90ff'],
												[1, '#ff4500']
											],
											width: 1,
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisLabel: { // 坐标轴小标记
										textStyle: { // 属性lineStyle控制线条样式
											fontWeight: 'bolder',
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisTick: { // 坐标轴小标记
										length: 10, // 属性length控制线长
										lineStyle: { // 属性lineStyle控制线条样式
											color: 'auto',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									splitLine: { // 分隔线
										length: 15, // 属性length控制线长
										lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
											width: 2,
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									pointer: { // 分隔线
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										width: 5,
									},
									// title : {
									// 	textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
									// 		fontWeight: 'bolder',
									// 		fontSize: 20,
									// 		color: '#FFFF09',
									// 		shadowColor : '#fff', //默认透明
									// 		shadowBlur: 3
									// 	}
									// },
									detail: {
										backgroundColor: 'rgba(30,144,255,0.8)',
										borderWidth: 1,
										borderColor: '#fff',
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										offsetCenter: ['0', '70%'], // x, y，单位px
										textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
											fontWeight: 'bolder',
											color: '#fff'
										},
										// fontSize:10,
										padding: 2
										// formatter:"{value}%"
									},

									// data:[{ value: 0, name: "最高温度" }]
								}]
							}
						}
					},
					{
						name: "最高湿度",
						type: 2, //数据类型为氧气的枚举值
						time: "",
						id: "humidityGaugeChart",
						gaugeData: [],
						parameters: {
							option: {
								title: {
									subtext: "",
									subtextStyle: {
										fontWeight: 'bolder',
									},
								},
								series: [{
									type: 'gauge',
									min: 0,
									max: 220,
									splitNumber: 5,
									radius: '90%',
									axisLine: { // 坐标轴线
										lineStyle: { // 属性lineStyle控制线条样式
											color: [
												[0.09, 'lime'],
												[0.82, '#1e90ff'],
												[1, '#ff4500']
											],
											width: 1,
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisLabel: { // 坐标轴小标记
										textStyle: { // 属性lineStyle控制线条样式
											fontWeight: 'bolder',
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisTick: { // 坐标轴小标记
										length: 10, // 属性length控制线长
										lineStyle: { // 属性lineStyle控制线条样式
											color: 'auto',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									splitLine: { // 分隔线
										length: 15, // 属性length控制线长
										lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
											width: 2,
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									pointer: { // 分隔线
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										width: 5,
									},
									// title : {
									// 	textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
									// 		fontWeight: 'bolder',
									// 		fontSize: 15,
									// 		color: '#FFFF09',
									// 		shadowColor : '#fff', //默认透明
									// 		shadowBlur: 3
									// 	}
									// },
									detail: {
										backgroundColor: 'rgba(30,144,255,0.8)',
										borderWidth: 1,
										borderColor: '#fff',
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										offsetCenter: ['0', '70%'], // x, y，单位px
										textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
											fontWeight: 'bolder',
											color: '#fff'
										},
										// fontSize:10,
										padding: 2
										// formatter:"{value}%"
									},

									// data:[{ value: 0, name: "最高温度" }]
								}]
							}
						}
					},
					{
						name: "最高水位",
						type: 21, //数据类型为氧气的枚举值
						time: "",
						gaugeData: [],
						id: "yeweiyiGaugeChart",
						parameters: {
							option: {
								title: {
									subtext: "",
									subtextStyle: {
										fontWeight: 'bolder',
									},
								},
								series: [{
									type: 'gauge',
									min: 0,
									max: 220,
									splitNumber: 5,
									radius: '90%',
									axisLine: { // 坐标轴线
										lineStyle: { // 属性lineStyle控制线条样式
											color: [
												[0.09, 'lime'],
												[0.82, '#1e90ff'],
												[1, '#ff4500']
											],
											width: 1,
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisLabel: { // 坐标轴小标记
										textStyle: { // 属性lineStyle控制线条样式
											fontWeight: 'bolder',
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisTick: { // 坐标轴小标记
										length: 10, // 属性length控制线长
										lineStyle: { // 属性lineStyle控制线条样式
											color: 'auto',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									splitLine: { // 分隔线
										length: 15, // 属性length控制线长
										lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
											width: 2,
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									pointer: { // 分隔线
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										width: 5,
									},
									// title : {
									// 	textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
									// 		fontWeight: 'bolder',
									// 		fontSize: 20,
									// 		color: '#FFFF09',
									// 		shadowColor : '#fff', //默认透明
									// 		shadowBlur: 3
									// 	}
									// },
									detail: {
										backgroundColor: 'rgba(30,144,255,0.8)',
										borderWidth: 1,
										borderColor: '#fff',
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										offsetCenter: ['0', '70%'], // x, y，单位px
										textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
											fontWeight: 'bolder',
											color: '#fff'
										},
										// fontSize:10,
										padding: 2
										// formatter:"{value}%"
									},

								}]
							}
						}
					},
					{
						name: "最高甲烷",
						type: 5, //数据类型为甲烷的枚举值
						time: "",
						gaugeData: [],
						id: "methaneGaugeChart",
						parameters: {
							option: {
								title: {
									subtext: "",
									subtextStyle: {
										fontWeight: 'bolder',
									},
								},
								series: [{
									type: 'gauge',
									min: 0,
									max: 220,
									splitNumber: 5,
									radius: '90%',
									axisLine: { // 坐标轴线
										lineStyle: { // 属性lineStyle控制线条样式
											color: [
												[0.09, 'lime'],
												[0.82, '#1e90ff'],
												[1, '#ff4500']
											],
											width: 1,
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisLabel: { // 坐标轴小标记
										textStyle: { // 属性lineStyle控制线条样式
											fontWeight: 'bolder',
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisTick: { // 坐标轴小标记
										length: 10, // 属性length控制线长
										lineStyle: { // 属性lineStyle控制线条样式
											color: 'auto',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									splitLine: { // 分隔线
										length: 15, // 属性length控制线长
										lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
											width: 2,
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									pointer: { // 分隔线
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										width: 5,
									},
									// title : {
									// 	textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
									// 		fontWeight: 'bolder',
									// 		fontSize: 20,
									// 		color: '#FFFF09',
									// 		shadowColor : '#fff', //默认透明
									// 		shadowBlur: 3
									// 	}
									// },
									detail: {
										backgroundColor: 'rgba(30,144,255,0.8)',
										borderWidth: 1,
										borderColor: '#fff',
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										offsetCenter: ['0', '70%'], // x, y，单位px
										textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
											fontWeight: 'bolder',
											color: '#fff'
										},
										// fontSize:10,
										padding: 2
										// formatter:"{value}%"
									},

									// data:[{ value: 0, name: "最高温度" }]
								}]
							}
						}
					},
					{
						name: "最低含氧量",
						type: 3, //数据类型为氧气的枚举值
						time: "",
						id: "oxygenGaugeChart",
						gaugeData: [],
						parameters: {
							option: {
								title: {
									subtext: "",
									subtextStyle: {
										fontWeight: 'bolder',
									},
								},
								series: [{
									type: 'gauge',
									min: 0,
									max: 220,
									splitNumber: 5,
									radius: '90%',
									axisLine: { // 坐标轴线
										lineStyle: { // 属性lineStyle控制线条样式
											color: [
												[0.09, 'lime'],
												[0.82, '#1e90ff'],
												[1, '#ff4500']
											],
											width: 1,
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisLabel: { // 坐标轴小标记
										textStyle: { // 属性lineStyle控制线条样式
											fontWeight: 'bolder',
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisTick: { // 坐标轴小标记
										length: 10, // 属性length控制线长
										lineStyle: { // 属性lineStyle控制线条样式
											color: 'auto',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									splitLine: { // 分隔线
										length: 15, // 属性length控制线长
										lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
											width: 2,
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									pointer: { // 分隔线
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										width: 5,
									},
									// title : {
									// 	textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
									// 		fontWeight: 'bolder',
									// 		fontSize: 20,
									// 		color: '#FFFF09',
									// 		shadowColor : '#fff', //默认透明
									// 		shadowBlur: 3
									// 	}
									// },
									detail: {
										backgroundColor: 'rgba(30,144,255,0.8)',
										borderWidth: 1,
										borderColor: '#fff',
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										offsetCenter: ['0', '70%'], // x, y，单位px
										textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
											fontWeight: 'bolder',
											color: '#fff'
										},
										// fontSize:10,
										padding: 2
										// formatter:"{value}%"
									},

									// data:[{ value: 0, name: "最高温度" }]
								}]
							}
						}
					},
					{
						name: "最高硫化氢",
						type: 4, //数据类型为氧气的枚举值
						time: "",
						gaugeData: [],
						id: "hybrothionGaugeChart",
						parameters: {
							option: {
								title: {
									subtext: "",
									subtextStyle: {
										fontWeight: 'bolder',
									},
								},
								series: [{
									type: 'gauge',
									min: 0,
									max: 220,
									splitNumber: 5,
									radius: '90%',
									axisLine: { // 坐标轴线
										lineStyle: { // 属性lineStyle控制线条样式
											color: [
												[0.09, 'lime'],
												[0.82, '#1e90ff'],
												[1, '#ff4500']
											],
											width: 1,
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisLabel: { // 坐标轴小标记
										textStyle: { // 属性lineStyle控制线条样式
											fontWeight: 'bolder',
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									axisTick: { // 坐标轴小标记
										length: 10, // 属性length控制线长
										lineStyle: { // 属性lineStyle控制线条样式
											color: 'auto',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									splitLine: { // 分隔线
										length: 15, // 属性length控制线长
										lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
											width: 2,
											color: '#fff',
											shadowColor: '#fff', //默认透明
											shadowBlur: 3
										}
									},
									pointer: { // 分隔线
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										width: 5,
									},
									// title : {
									// 	textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
									// 		fontWeight: 'bolder',
									// 		fontSize: 20,
									// 		color: '#FFFF09',
									// 		shadowColor : '#fff', //默认透明
									// 		shadowBlur: 3
									// 	}
									// },
									detail: {
										backgroundColor: 'rgba(30,144,255,0.8)',
										borderWidth: 1,
										borderColor: '#fff',
										shadowColor: '#fff', //默认透明
										shadowBlur: 5,
										offsetCenter: ['0', '70%'], // x, y，单位px
										textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
											fontWeight: 'bolder',
											color: '#fff'
										},
										// fontSize:10,
										padding: 2
										// formatter:"{value}%"
									},

									// data:[{ value: 0, name: "最高温度" }]
								}]
							}
						}
					}
				],
				dataInterval: null,
				dataTimeout: {
					todayExtreFlag: true,
					triggerTimesFlag: null
				}

			};
		},
		components: {
			ModuleTitle,
			SimpleGauge
		},
		mounted() {
			this.getToDayExtreDatas();
			this.getTriggerTimes();
			// this.fetchData();
		},
		methods: {
			fetchData() {
				let _this = this;
				_this.dataInterval = setInterval(() => {
					_this.init();
				}, _this.fetchTime);
			},
			getToDayExtreDatas() {
				let _this = this;

				MeasObjServer.getToDayExtreDatas().then(
						result => {
							if (result) {
								result.forEach(a => {
									_this.gaugeChart.forEach(b => {
										if (b.type === a.type) {
											b.name = a.name;
											b.time = new Date(a.time).format(
												"yyyy-MM-dd hh:mm:ss"
											);
											b.parameters.option.title.subtext =
												a.location;

											let min = a.objTypeParam.measMin
											let max = a.objTypeParam.measMax

											b.parameters.option.series.min = min;
											b.parameters.option.series.max = max;

											let color = []
											// 先添加正常的区间
											color = _this.getColorArray(color, min, max, a.objTypeParam
												.normalMin, a.objTypeParam.normalMax, '#00FF00')
											// 再添加警告的区间
											color = _this.getColorArray(color, min, max, a.objTypeParam
												.warmingMin, a.objTypeParam.warmingMax, '#FFA500')
											// color = _this.getColorArray(color, min, max, a.objTypeParam
											// 	.warmingMin, a.objTypeParam.warmingMax, '#0000FF')
											// 最后添加报警的区间
											color = _this.getColorArray(color, min, max, a.objTypeParam
												.errorMin, a.objTypeParam.errorMax, '#FF0000')

											b.parameters.option.series[0].axisLine.lineStyle.color = color
											b.gaugeData = [{
												value: a.value,
												name: a.name.substr(2)
											}]

											b.parameters.option.series[0].detail.formatter =
												"{value}" + a.unit;

										}
									});
								});
							}
						},
						error => {
							_this.Log.info(error);
						}
					)
					.finally(() => {
						if (_this.dataTimeout.todayExtreFlag) {
							setTimeout(() => {
								_this.getToDayExtreDatas()
							}, _this.fetchTime)
						}
					});

			},
			getColorArray(arr, min, max, curMin, curMax, color) {
				// 添加在第一个
				if (curMin == min) {
					arr.splice(0, 0, [(curMax - curMin) / (max - min), color])
				}
				// 添加在最后一个
				else if (curMax == max) {
					arr.push([1, color])
				}
				// 添加在中间 
				else {
					arr.splice(1, 0, [(curMax - min) / (max - min), color])
				}

				return arr
			},
			getTriggerTimes() {
				MeasObjServer.getMeasTriggerCounts().then(
						result => {
							this.countData = result;
						},
						error => {
							this.Log.info(error);
						}
					)
					.finally(() => {
						if (this.dataTimeout.triggerTimesFlag) {
							let _this = this
							setTimeout(() => {
								_this.getTriggerTimes()
							}, parseFloat(this.refreshTime))
						}
					});
			}
		},
		beforeDestroy() {
			this.dataTimeout.triggerTimesFlag = false
			this.dataTimeout.todayExtreFlag = false
		}
	};
</script>

<style scoped>
	.Main {
		width: 100%;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		background: url("../../../assets/VM/module_bg.png") no-repeat;
		background-size: 100% 100%;
	}

	.Main .Title {
		width: 100%;
		height: 15%;
	}

	.myChart {
		margin-left: 0.1vw;
		width: 32.8%;
		height: 13.5vh;
		/* float: left; */
		position: absolute
	}

	.myChart_item_0,
	.myChart_item_1,
	.myChart_item_2 {
		top: 3.5vmin;
	}

	.myChart_item_1 {
		left: 33%;
	}

	.myChart_item_2 {
		right: 0
	}

	.myChart_item_3,
	.myChart_item_4,
	.myChart_item_5 {
		top: 17.5vmin;
	}

	.myChart_item_4 {
		left: 33%;
	}

	.myChart_item_5 {
		right: 0
	}

	.chartInfo {
		font-family: "Microsoft YaHei";
		text-align: center;
		font-size: 1.2vmin;
		color: #fff;
		background-color: #2562e9;
		margin-left: 0.2vw;
		margin-right: 0.2vw;
		border-radius: 25px;
		box-shadow: 0.5vh 0.5vh .5vh #000000;
	}

	.info {
		margin: 0.1vmin 2.5% 1vmin 2.5%;
		width: 45%;
		height: 6.5vh;
		float: left;
		background: url("../../../assets/VM/doubt.png") no-repeat;
		background-size: 100% 100%;
		border-radius: 1.2vh 0;
	}

	.infoTitle {
		text-align: center;
		font-size: 1.4vmin;
		color: #4dfaff;
		line-height: 2.8vmin;
		font-family: UnidreamLED;
	}
</style>