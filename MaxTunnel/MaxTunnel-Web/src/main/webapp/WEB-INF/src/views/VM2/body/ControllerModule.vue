<template>
	<div class="Main">
		<div class="Title">
			<module-title :title="title"></module-title>
		</div>

		<div style="display:inline">
			<div class="infoBox">
				<div class="infoTitle">
					<div>{{jinggai.name}}</div>
					<div>
						<img :src="jinggai.url" width="50%">
					</div>
				</div>
				<div class="infoContent">
					<div class="content openClass">
						<div>开</div>
						<div>{{jinggai.open}}</div>
					</div>
					<div class="content closeClass">
						<div>关</div>
						<div>{{jinggai.close}}</div>
					</div>
					<div class="content faultClass">
						<div>故障</div>
						<div>{{jinggai.fault}}</div>
					</div>
					<!-- <div class="content">
						<div>其他</div>
						<div>{{jinggai.other}}</div>
					</div> -->
				</div>
			</div>
			<div class="infoBox">
				<div class="infoTitle">
					<div>{{baiye.name}}</div>
					<div>
						<img :src="baiye.url" width="50%">
					</div>
				</div>
				<div class="infoContent">
					<div class="content openClass">
						<div>开</div>
						<div>{{baiye.open}}</div>
					</div>
					<div class="content closeClass">
						<div>关</div>
						<div>{{baiye.close}}</div>
					</div>
					<div class="content faultClass">
						<div>故障</div>
						<div>{{baiye.fault}}</div>
					</div>
					<!-- <div class="content">
						<div>其他</div>
						<div>{{baiye.other}}</div>
					</div> -->
				</div>
			</div>
		</div>

		<div style="display:inline">
			<div class="infoBox2">
				<div class="infoTitle2">
					<div>{{fengji.name}}</div>
					<div>
						<img :src="fengji.url" width="60%">
					</div>
				</div>
				<div class="infoContent2">
					<div class="content2 openClass">
						<div>开</div>
						<div>{{fengji.open}}</div>
					</div>
					<div class="content2 closeClass">
						<div>关</div>
						<div>{{fengji.close}}</div>
					</div>
				</div>
			</div>
			<div class="infoBox2">
				<div class="infoTitle2">
					<div>{{zhaoming.name}}</div>
					<div>
						<img :src="zhaoming.url" width="60%">
					</div>
				</div>
				<div class="infoContent2">
					<div class="content2 openClass">
						<div>开</div>
						<div>{{zhaoming.open}}</div>
					</div>
					<div class="content2 closeClass">
						<div>关</div>
						<div>{{zhaoming.close}}</div>
					</div>
				</div>
			</div>
			<div class="infoBox2">
				<div class="infoTitle2">
					<div>{{shuibeng.name}}</div>
					<div>
						<img :src="shuibeng.url" width="60%">
					</div>
				</div>
				<div class="infoContent2">
					<div class="content2 openClass">
						<div>开</div>
						<div>{{shuibeng.open}}</div>
					</div>
					<div class="content2 closeClass">
						<div>关</div>
						<div>{{shuibeng.close}}</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import ModuleTitle from "../../../components/VM2/ModuleTitle";
	import {
		MeasObjServer
	} from "../../../services/MeasObjectSerivers";
	import jingGaiImg from "../../../assets/VM/jinGai.png";
	import baiyeImg from "../../../assets/UM/百叶-开.png"
	import fengjiImg from "../../../assets/UM/风机.png"
	import zhaomingImg from "../../../assets/UM/照明-开.png"
	import shuibengImg from "../../../assets/UM/水泵.png"

	export default {
		data() {
			return {
				title: "控制状态",
				iconSize: window.innerWidth * 0.012,
				freshTime: 60000,
				isFresh: true,
				jinggai: {
					name: "智能井盖",
					url: jingGaiImg,
					open: 0,
					close: 0,
					fault: 0,
					other: 0
				},
				baiye: {
					name: "电动百叶",
					url: baiyeImg,
					open: 0,
					close: 0,
					fault: 0,
					other: 0
				},
				fengji: {
					name: "风机",
					url: fengjiImg,
					open: 0,
					close: 0
				},
				zhaoming: {
					name: "照明",
					url: zhaomingImg,
					open: 0,
					close: 0
				},
				shuibeng: {
					name: "水泵",
					url: shuibengImg,
					open: 0,
					close: 0
				}

			};
		},
		components: {
			ModuleTitle
		},
		mounted() {
			this.getComplexobjDatas();
		},
		methods: {
			getComplexobjDatas() {
				MeasObjServer.getComplexobjDatas().then(
						result => {
							this.Log.info("getComplexobjDatas", result)
							for (let index in result) {
								switch (result[index].typeId) {
									case 56:
										this.jinggai.open = result[index].open
										this.jinggai.close = result[index].close + result[index].other
										this.jinggai.fault = result[index].fault
										// this.jinggai.other = result[index].other
										break;
									case 58:
										this.baiye.open = result[index].open
										this.baiye.close = result[index].close + result[index].other
										this.baiye.fault = result[index].fault
										// this.baiye.other = result[index].other
										break;
									case 10:
										this.fengji.open = result[index].open
										this.fengji.close = result[index].close
										break;
									case 12:
										this.zhaoming.open = result[index].open
										this.zhaoming.close = result[index].close
										break;
									case 59:
										this.shuibeng.open = result[index].open
										this.shuibeng.close = result[index].close
										break;

									default:
										break;
								}
							}
						},
						error => {
							this.Log.info(error);
						}
					)
					.finally(() => {
						if (this.isFresh) {
							let _this = this
							setTimeout(() => {
								_this.getComplexobjDatas()
							}, _this.freshTime)
						}
					});
			}
		},
		beforeDestroy() {
			this.isFresh = false
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

	.infoBox {
		margin: 3% 1.5%;
		color: #4dfaff;
		font-family: UnidreamLED;
		width: 47%;
		height: 30%;
		float: left;
		background: url("../../../assets/VM/doubt.png") no-repeat;
		background-size: 100% 100%;
		border-radius: 1.2vh 0;
	}

	.infoBox .infoTitle {
		margin: 6% 0%;
		text-align: center;
		width: 35%;
		float: left;
		font-size: 1.5vh;
	}

	.infoBox .infoContent {
		margin: 12% 0;
		text-align: center;
		width: 60%;
		float: left;
		font-size: 1.5vh;
	}

	.infoBox .infoContent .content {
		width: 33%;
		float: left;
	}


	.infoBox2 {
		margin: 5% 1.5%;
		color: #4dfaff;
		font-family: UnidreamLED;
		width: 30%;
		height: 30%;
		float: left;
		background: url("../../../assets/VM/doubt.png") no-repeat;
		background-size: 100% 100%;
		border-radius: 1.2vh 0;
	}

	.infoBox2 .infoTitle2 {
		margin: 8% 0;
		text-align: center;
		width: 40%;
		float: left;
		font-size: 1.5vh;
	}

	.infoBox2 .infoContent2 {
		margin: 18% 0;
		text-align: center;
		width: 60%;
		float: left;
		font-size: 1.5vh;
	}

	.infoBox2 .infoContent2 .content2 {
		width: 49%;
		float: left;
	}

	.openClass {
		color: green
	}

	.closeClass {
		color: yellow
	}

	.faultClass {
		color: red
	}
</style>