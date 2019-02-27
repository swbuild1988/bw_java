<template>
	<div class="whole">
		<Row class="body">
			<Col span="8">
				<div class="persons">
					<Collapse v-model="curPerson" accordion v-if="!none" @on-change="personChange">
					<Panel v-for="person in personnelInfo" :name="person.id+''" :key="person.id">
						{{ person.username }}
						<div slot="content" class="content">
						<img src="../../../../assets/UM/能耗.png" width="100" height="100">
						<ul class="perList">
							<li>所属公司：{{ person.companyName }}</li>
							<li>手机号：{{ person.phoneNum }}</li>
							<li>身份证号：{{ person.idCard }}</li>
						</ul>
						<div class="options">
							<Button type="primary">历史轨迹</Button>
							<Button type="primary" class="call">通话</Button>
						</div>
						</div>
					</Panel>
					</Collapse>
					<h2 v-if="none" class="none">暂无入廊人员或未分配设备</h2>
				</div>
				<div class="cameras">
					<Row>
                        <Col span="1" class="slipContent">
                            <Icon type="chevron-left" :class="['slipLeft',{'disabled': cameras.curPage == 1},{'clicked' : clicked.prev && cameras.curPage != 1}]" @click.native="pageChange('prev')" @mousedown.native="down('prev')" @mouseup.native="up('prev')"></Icon>
                        </Col>
                        <Col span="22">
                        <div class="videos">
                            <h1 v-if="cameras.nodata" style="text-align: center;margin-top: 2vmin;">暂无数据</h1>
                            <Row>
                                <Col span="12" v-for="(item,index) in cameras.showList" :key="item.id" class="monitors">
                                    <video-component v-bind:video="item" v-bind:id="'camera'+item.id"></video-component>
                                </Col>
                            </Row>
                        </div>
                        </Col>
                        <Col span="1"  class="slipContent">
                           <Icon type="chevron-right" :class="['slipRight',{'disabled' : cameras.curPage == cameras.totalPage},
						   {'clicked' : clicked.next && cameras.curPage != cameras.totalPage}]" @click.native="pageChange('next')" @mousedown.native="down('next')" @mouseup.native="up('next')"></Icon>
                        </Col>
                    </Row>
				</div>
			</Col>
			<Col span="15" offset="1" class="view">
				<!-- <sm-viewer id="personnelPositionSMViewer" :cameraPosition="VMConfig.CAMERA"  :personnelPosition="personnelPosition" @onload="onload" style="height:74vh">
					</sm-viewer>-->				
				<test-sm-viewer :cameraPosition="VMConfig.CAMERA" :personnelPosition="personnelPosition" ref="smViewer"></test-sm-viewer>
			</Col>
		</Row>
	</div>
</template>
<script>
	import {
		personnelPositionService
	} from "../../../../services/personnelPositionService";
	import TestSmViewer from "../../../../components/Common/3D/overLook3DViewer";
	import {
		TunnelService
	} from "../../../../services/tunnelService";
	import {
		VideoService
	} from "../../../../services/videoService";
	//   import { setViewAngle ,bubble ,doSqlQuery , addBillboard,processFailed,getEntitySet,switchShowEntity,getEntityProperty  } from '../../../../scripts/commonFun'
	import VideoComponent from "../../../../components/Common/Video/VideoComponent"
  	export default {
		data() {
			return {
				curPerson: '',
				personnelInfo: [
				// {
				//   id: '1',
				//   name: '章散',
				//   figure: '../../../../assets/UM/用户.png',
				//   phoneNum: 13678263529,
				//   idCard: 223911199208102238
				// },
				// {
				//   id: '2',
				//   name: '里斯',
				//   figure: '../../../../assets/UM/用户.png',
				//   phoneNum: 13678263525,
				//   idCard: 213811199203102238
				// },
				// {
				//   id: '3',
				//   name: '王武',
				//   figure: '../../../../assets/UM/用户.png',
				//   phoneNum: 13978263529,
				//   idCard: 223911199208102641
				// },
				// {
				//   id: '4',
				//   name: '朱柳',
				//   figure: '../../../../assets/UM/用户.png',
				//   phoneNum: 18378263525,
				//   idCard: 213811199203107264
				// }
				],
				none: true,
				personnelPosition: {
					openPosition: true,
					isShow: true,
					refreshTime: 10000
				},
				cameras: {
					allList: [],
					showList: [],
					nodata: false,
					totalPage: 1,
					curPage: 1
				},
				clicked: {
					prev: false,
					next: false
				}
			};
		},
		components: {
			// SmViewer
			TestSmViewer,
			VideoComponent
		},
		beforeRouteLeave(to, from, next) {
			if (
				to.name == "UMPatrolHomePage" ||
				to.name == "设备管理主页" ||
				to.name == "人员定位详情" ||
				to.name == "设备分配" ||
				to.name == "管廊安防监控列表" ||
				to.name == "管廊环境监控列表" ||
				from.name == "UMPatrolHomePage" ||
				from.name == "设备管理主页" ||
				from.name == "人员定位详情" ||
				from.name == "设备分配" ||
				from.name == "管廊安防监控列表" ||
				from.name == "管廊安防监控详情" ||
				from.name == "管廊环境监控列表" ||
				from.name == "UMDetailEquipment"
			) {
				from.meta.keepAlive = true;
				to.meta.keepAlive = true;
				this.$destroy();
				next();
			} else {
				from.meta.keepAlive = false;
				to.meta.keepAlive = false;
				this.$destroy();
				next();
			}
		},
		watch: {
			$route: function () {
				// this.$refs.smViewer.stopPersonnelPosition();
			}
		},
		mounted() {
			// this.$refs.smViewer.startPersonnelPosition();
			this.getVisitors();
			this.getCameraList()
		},
		methods: {
			getVisitors() {
				let _this = this;
				personnelPositionService.getActiveLocators().then(
				result => {
					_this.personnelInfo = [];
					if (result.length != 0) {
						_this.none = false;
						_this.curPerson = result[0].id + ''
						result.forEach(locator => {
							let temp = {};
							temp.id = locator.id;
							temp.height = locator.height;
							temp.latitude = locator.latitude;
							temp.longitude = locator.longitude;
							temp.username = locator.owner.name;
							temp.companyName = locator.owner.companyName;
							temp.idCard = locator.owner.idCard;
							temp.phoneNum = locator.owner.phoneNum;
							_this.personnelInfo.push(temp);
						});
					} else {
						_this.none = true;
					}
				},
				error => {
					console.log(error);
				}
				);
			},
			getCameraList(){
				let _this = this
				VideoService.getCameraList().then(
					result=>{
						if(result != null){
							_this.cameras.nodata = false
							_this.cameras.allList = result
							_this.cameras.showList = result.slice(0,4)
							_this.cameras.totalPage = Math.ceil(result.length / 4)
						} else {
							_this.cameras.allList = []
							_this.cameras.showList = []
							_this.cameras.nodata = true
						}
					}
				)
			},
			personChange() {
				if(this.curPerson.length){
					this.getCameraList()
				} else {
					this.cameras.allList = []
					this.cameras.showList = []
					this.cameras.nodata = true
				}
			},
			pageChange(type){
				switch(type){
					case 'prev':
						if(this.cameras.curPage > 1){
							this.cameras.curPage -= 1
						}
						break
					case 'next':
						if(this.cameras.curPage < this.cameras.totalPage){
							this.cameras.curPage += 1
						}
						break
				}
				this.cameras.showList = this.cameras.allList.slice((this.cameras.curPage - 1) * 4, this.cameras.curPage * 4)
			},
			down(type){
				this.clicked[type] = true
			},
			up(type){
				this.clicked[type] = false
			}
		},
  	};
</script>
<style scoped>
	.whole {
		min-height: 100%;
		position: relative;
		background-color: white;
		overflow: hidden;
	}
	.body{
		margin: 4vmin 2vmin;
	}
	.view {
		border: 1px solid #b3b0b0;
		box-shadow: 0 0 1.3vmin 0.3vmin rgba(0, 0, 0, 0.5);
		height: 74vh;
		margin-top: 1.4vh;
	}

	.perList {
		display: inline-block;
		margin-left: 40px;
		list-style-type: none;
		position: relative;
		top: -32px;
	}

	.options{
		position: absolute;
		right: 0;
		bottom: -0.8vmin;
	}

	.content {
		position: relative;
		font-size: 1.66vmin;
	}

	.title {
		font-size: 3.2vmin;
	}

	.none {
		font-size: 2vmin;
		padding: 1vmin 2vmin;
	}

	.persons{
		height: 30vh;
		width: 100%;
		overflow-y: auto;
		/* border: 1px solid black; */
	}

	.persons >>> .ivu-collapse .ivu-collapse-header{
		font-size: 1.4vmin;
		height: 3.8vmin;
		line-height: 3.8vmin;
	}
	.cameras{
		margin-top: 1.2vmin;
		height: 46vh;
		background-color: #f3e7d1;
		border-radius: 4px;
	}
	.slipContent {
		position: relative;
		width: 4%;
		height: 46vh;
	}
	.slipLeft {
		position: absolute;
		top: 46%;
		left: 4px;
		cursor: pointer;
		color: lightgray;
		font-size: 2.88vmin;
	}
	.slipRight {
		position: absolute;
		top: 46%;
		right: 4px;
		cursor: pointer;
		color: #656464;
		font-size: 2.88vmin;
	}
	.disabled {
		display: none;
	}
	.clicked {
		color: rgb(0,228,236);
	}
	.monitors {
		padding: 1.4vmin 0.6vmin 0vmin 0.6vmin;
		height: 22.4vh;
	}
</style>