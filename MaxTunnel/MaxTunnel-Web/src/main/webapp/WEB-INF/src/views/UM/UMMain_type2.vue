<template>
	<div class="main" :style="{height:fullHeight+'px'}">
		<Row>
			<Col span="24">
				<div id="top" class="topOpacity">
					<div class="Icon"></div>
					<span class="titleName">{{titleName}}</span>
					<div style="float: right;padding-top: 0px;padding-right: 5px;">
						<Dropdown divided @click.native="showAboutUs">
							<a>
								<Icon type="information-circled" color="#fff" title="关于我们" class="about"></Icon>
							</a>
						</Dropdown>
						<showAboutUs v-bind="aboutUs"></showAboutUs>
					</div>
					<div style="float: right;padding-top: 1vmin;">
						<Dropdown divided @click.native="logout">
							<a>
								<Icon type="power" color="#fff" class="power"></Icon>
							</a>
						</Dropdown>
					</div>
					<div class="time">{{nowFormat}}</div>
				</div>
			</Col>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<div class="modals">
				<Row>
					<Col span="12">
						<div class="title">
							<p>运行监控</p>
						</div>
						<Row type="flex" justify="center" align="top" class="code-row-bg">
							<Col span="10" offset="2">
								<Button
									class="btn-guanxian renYuandinwei"
									@click="goToMoudle({ path:'/UM/TunnelEnvironment'})"
								>
									<div>
										<img src="../../assets/UM/environment.png" :width="imgWidth">
										<div :style="{fontSize:mySize}">管廊环境监测</div>
									</div>
								</Button>
							</Col>
							<Col span="12">
								<Button type="info" class="renYuandinwei" @click="goToMoudle({ path:'/UM/TunnelSafety'})">
									<div>
										<img src="../../assets/UM/safe.png" :width="imgWidth">
										<div :style="{fontSize:mySize}">管廊安防监测</div>
									</div>
								</Button>
							</Col>
						</Row>
						<Row>
							<Col span="22" offset="2">
								<Button
									class="btn-nenghao"
									style="width: 32.6vw; height: 14vh; margin: 0.6vh 0 0.2vh;
                 box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);"
									@click="goToMoudle({ path:'/UM/virtualInspect'})"
								>
									<div>
										<img src="../../assets/UM/virtualInspect.png" :width="imgWidth">
										<div :style="{fontSize:mySize}">虚拟巡检</div>
									</div>
								</Button>
							</Col>
						</Row>
						<Row type="flex" justify="center" align="top" class="code-row-bg">
							<Col span="10" offset="2">
								<Button
									type="error"
									class="renYuandinwei"
									@click="goToMoudle({ path:'/UM/PersonnelPosition/tunnel'})"
								>
									<div>
										<img src="../../assets/UM/position.png" :width="imgWidth">
										<div :style="{fontSize:mySize}">人员定位</div>
									</div>
								</Button>
							</Col>
							<Col span="12">
								<Button
									class="renYuandinwei btn-shiping"
									@click="goToMoudle({path: '/UM/VideoMonitoring/details/1'})"
								>
									<div>
										<img src="../../assets/UM/monitor.png" :width="imgWidth">
										<div :style="{fontSize:mySize}">视频监控系统</div>
									</div>
								</Button>
							</Col>
						</Row>
					</Col>
					<Col span="12">
						<div class="title">
							<p style="margin-right: 8vw">综合管理</p>
						</div>
						<Row type="flex" justify="center" align="top" class="code-row-bg">
							<Col span="10">
								<Button
									class="btn-xunjian"
									style="height: 13.6vh;width: 16vw;margin-top: 0.5vh;box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);"
									@click="goToMoudle({ path:'/UM/patrol/homePage'})"
								>
									<div>
										<img :width="imgWidth" src="../../assets/UM/巡检.png">
										<div :style="{fontSize:mySize}">巡检管理</div>
									</div>
								</Button>
								<Button
									style="height: 13.6vh;width: 16vw;margin-top: 0.5vh;box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);"
									type="primary"
									@click="goToMoudle({ path:'/UM/equipment/homePage'})"
								>
									<div>
										<img :width="imgWidth" src="../../assets/UM/equipments.png">
										<div :style="{fontSize:mySize}">设备管理</div>
									</div>
								</Button>
							</Col>
							<Col span="14">
								<Button
									class="btn-jiqiren renYuandinwei"
									@click="goToMoudle({ path:'/UM/tunnelCustomer/list'})"
								>
									<div>
										<img :width="imgWidth" src="../../assets/UM/用户.png">
										<div :style="{fontSize:mySize}">运营管理</div>
									</div>
								</Button>
							</Col>
						</Row>
						<Row>
							<Col span="10">
								<Button
									type="success"
									class="renYuandinwei"
									@click="goToMoudle({ path:'/UM/DataAnalysis/QueryData'})"
								>
									<div>
										<img src="../../assets/UM/dataAnalysis.png" :width="imgWidth">
										<div :style="{fontSize:mySize}">数据分析</div>
									</div>
								</Button>
							</Col>
							<Col span="14">
								<Button
									class="btn-yonhu ywGuanli"
									@click="goToMoudle({ path:'/UM/plans/detial/FirePlanProcess'})"
								>
									<div>
										<img :width="imgWidth" src="../../assets/UM/消防.png">
										<div :style="{fontSize:mySize}">预案管理</div>
									</div>
								</Button>
								<Button
									class="ywGuanli"
									type="primary"
									@click="goToMoudle({ path:'/UM/TunnelEnergy/homepage'})"
								>
									<div>
										<img src="../../assets/UM/energy.png" :width="imgWidth">
										<div :style="{fontSize:mySize}">管廊能耗管理</div>
									</div>
								</Button>
							</Col>
							<Col span="24">
								<Button
									class="ywGuanli btn-guanxian"
									style="width: 32.6vw"
									@click="goToMoudle({ path:'/VMMain2'})"
								>
									<div>
										<img src="../../assets/UM/空间.png" :width="imgWidth">
										<div :style="{fontSize:mySize}">可视化平台</div>
									</div>
								</Button>
							</Col>
						</Row>
					</Col>
				</Row>
			</div>
		</Row>
	</div>
</template>

<script>
import { getFormatTime } from "../../scripts/DateFormat.js";
import { LoginService } from "../../services/loginService.js";
import showAboutUs from "../../components/Common/Modal/ShowAboutUs";
// import { removeToken } from "@/utils/auth"; // 验权
import session from '@/utils/auth'
import Cookies from "js-cookie";
export default {
	name: "UmMain",
	data() {
		return {
			aboutUs: {
				show: { state: false },
				company: {
					version: "",
					name: "上海波汇科技有限公司",
					email: "Bandweaver@.cn",
					telephone: "",
					address: "上海市浦东新区上科路88号豪威科技园1幢503"
				}
			},
			fullHeight: window.innerHeight,
			nowFormat: null,
			titleName: "综合管廊统一管理平台",
			mySize: "1.4rem",
			smSize: "16px",
			imgWidth: "52px"
		};
	},
	methods: {
		goToMoudle: function(path) {
			if (path.path == "/VMMain") {
				const { href } = this.$router.resolve({
					name: "可视化主页"
				});
				window.open(href, "_blank");
			} else {
				this.$router.push(path);
			}
		},
		showAboutUs() {
			this.aboutUs.show.state = !this.aboutUs.show.state;
		},
		getUsData() {
			var _this = this;
			EnterGalleryService.getAboutUsData().then(result => {
				if (result) {
					_this.aboutUs.company.version = result.version;
					_this.aboutUs.company.email = result.email;
					_this.aboutUs.company.telephone = result.contact;
					_this.aboutUs.company.address = result.address;
					_this.aboutUs.company.name = result.company;
				}
			});
		},

		logout: function() {
			var _this = this;
			this.$Modal.confirm({
				title: "注销",
				content: "<p>确认退出吗?</p>",
				onOk: () => {
					LoginService.UMLogout().then(
						result => {
							Cookies.remove("userName");
							// removeToken();
                            session.removeSession();
							_this.$router.push("/UMlogin");
						},
						error => {
							_this.$Message.error(msg);
						}
					);
				},
				onCancel: () => {
					// this.$Message.info('Clicked cancel');
				}
			});
		},

		updateTime() {
			this.nowFormat = getFormatTime();
			this.fullHeight = window.innerHeight;
			if (window.innerWidth <= 1400) {
				this.mySize = "2.2vmin";
				this.smSize = "10px";
				this.imgWidth = "32%";
			} else {
				if (window.innerWidth <= 1920) {
					this.mySize = "2.2vmin";
					this.smSize = "16px";
					this.imgWidth = "50vw";
				} else {
					this.mySize = "2.2vmin";
					this.smSize = "16px";
					this.imgWidth = "100vw";
				}
			}
		}
	},
	mounted() {
		var _this = this;
		_this.updateTime();
		setInterval(_this.updateTime, 1000);
	},
	beforeDestroy() {
		if (this.timer) {
			clearInterval(this.timer); //在vue实例销毁钱，清除我们的定时器
		}
	},
	components: {
		showAboutUs
	}
};
</script>


<style scoped>
.ywGuanli {
	width: 16vw;
	height: 13.8vh;
	margin-top: 0.5vh;
	box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
}

.renYuandinwei {
	height: 28vh;
	width: 16vw;
	margin-top: 0.5vh;
	box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
}

.zhJiankon {
	height: 27.2vh;
	width: 12vw;
	margin-top: 0.5vh;
	box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
}

.yyGuanLi {
	height: 13.8vh;
	width: 24.2vw;
	margin-top: 1vh;
	box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
}

.yjGuanLi {
	width: 12vw;
	height: 14vh;
	margin-top: 1.5vh;
	box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
}

.main {
	background-image: url("../../assets/UM/UMMainbg.png");
	color: #fff;
	background-size: 100% 100%;
	-moz-background-size: 100% 100%;
	-webkit-background-size: 100% 100%;
}

.topOpacity {
	background-color: rgba(0, 0, 0, 0.2);
}

.btn-xunjian {
	color: #fff;
	background-color: #df5631;
	border-color: #d58512;
	border-width: thin;
}

.btn-xunjian:focus,
.btn-xunjian:hover {
	color: #fff;
	background-color: #cb500a;
	background-position: 0 -15px;
}

.btn-xunjian:active {
	color: #fff;
	background-color: #d83c38;
	border-color: #d58512;
}

.btn-quexian {
	color: #fff;
	background-color: #753be5;
	border-color: #8e6ae5;
	border-width: thin;
}

.btn-quexian:focus,
.btn-quexian:hover {
	color: #fff;
	background-color: #715fc8;
	background-position: 0 -15px;
}

.btn-quexian:active {
	color: #fff;
	background-color: #7123b9;
	border-color: #8e6ae5;
}

.btn-yonhu {
	color: #fff;
	background-color: #d500d1;
	border-color: #d50076;
	border-width: thin;
}

.btn-yonhu:focus,
.btn-yonhu:hover {
	color: #fff;
	background-color: #ef00df;
	background-position: 0 -15px;
}

.btn-yonhu:active {
	color: #fff;
	background-color: #b70071;
	border-color: #d50076;
}

.btn-nenghao {
	color: #fff;
	background-color: #323aed;
	border-color: #3a20ed;
	border-width: thin;
}

.btn-nenghao:focus,
.btn-nenghao:hover {
	color: #fff;
	background-color: #3a1ac7;
	background-position: 0 -15px;
}

.btn-nenghao:active {
	color: #fff;
	background-color: #3a5aff;
	border-color: #3a20ed;
}

.btn-tonxing {
	color: #fff;
	background-color: #d064c2;
	border-color: #d02cc2;
	border-width: thin;
}

.btn-tonxing:focus,
.btn-tonxing:hover {
	color: #fff;
	background-color: #fa4586;
	background-position: 0 -15px;
}

.btn-tonxing:active {
	color: #fff;
	background-color: #d000b7;
	border-color: #d02cc2;
}

.btn-shiping {
	color: #fff;
	background-color: #f88252;
	border-color: #f83f58;
	border-width: thin;
}

.btn-shiping:focus,
.btn-shiping:hover {
	color: #fff;
	background-color: #f83f52;
	background-position: 0 -15px;
}

.btn-shiping:active {
	color: #fff;
	background-color: #f80b6b;
	border-color: #f83f58;
}

.btn-jiqiren {
	color: #fff;
	background-color: #e5b23b;
	border-color: #e57f3b;
	border-width: thin;
}

.btn-jiqiren:focus,
.btn-jiqiren:hover {
	color: #fff;
	background-color: #aeda3b;
	background-position: 0 -15px;
}

.btn-jiqiren:active {
	color: #fff;
	background-color: #ae993b;
	border-color: #e57f3b;
}

.btn-guanxian {
	color: #fff;
	background-color: #089fb2;
	border-color: #519fb2;
	border-width: thin;
}

.btn-guanxian:focus,
.btn-guanxian:hover {
	color: #fff;
	background-color: #25c8b2;
	background-position: 0 -15px;
}

.btn-guanxian:active {
	color: #fff;
	background-color: #258486;
	border-color: #519fb2;
}

.titleName {
	font-size: 4vmin;
	padding-top: 0.5vmin;
}

.Icon {
	height: 6vmin;
	width: 7vmin;
	background-image: url("../../assets/UM/Maxview.png");
	background-size: 100% 100%;
	-moz-background-size: 100% 100%;
	-webkit-background-size: 100% 100%;
	float: left;
}

.time {
	font-size: 2vmin;
	float: right;
	padding-right: 1vmin;
	padding-top: 1.8vmin;
}

.power {
	font-size: 4vmin;
}

.about {
	font-size: 2vmin;
}

.title {
	text-align: center;
	font-size: 5vmin;
	font-weight: bold;
	margin-bottom: 2vh;
}
@media (max-width: 1921px) {
	.modals {
		width: 80vw;
		margin-left: 10vw;
	}
}
@media (min-width: 1921px) {
	.modals {
		width: 80vw;
		margin-left: 10vw;
		margin-top: 4vh;
	}
}
</style>
