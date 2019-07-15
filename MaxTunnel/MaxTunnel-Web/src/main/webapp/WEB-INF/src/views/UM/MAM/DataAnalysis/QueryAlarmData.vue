<!--告警数据查询-->
<template>
	<div class="queryAlarm">
		<Row class="queryCondition">
			<Col span="6">
				<span class="conditionTitle">告警状态：</span>
				<Select v-model="queryPrams.cleaned" style="width:65%">
					<Option v-for="item in alarmStatusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
				</Select>
			</Col>
			<Col span="6">
				<span class="conditionTitle">所属管廊：</span>
				<Select v-model="queryPrams.tunnelId" style="width:65%">
					<Option v-for="item in tunnelList" :value="item.id" :key="item.id">{{ item.name }}</Option>
				</Select>
			</Col>
			<Col span="6">
				<span class="conditionTitle">告警级别：</span>
				<Select v-model="queryPrams.alarmLevels" multiple style="width:65%">
					<Option v-for="item in enumData.alarmLevel" :value="item.val" :key="item.val">{{ item.key }}</Option>
				</Select>
			</Col>
			<Col span="6">
				<span class="conditionTitle">监测对象：</span>
				<Input v-model="queryPrams.objectId" @on-blur="queryObject" style="width: 65%;margin-top:14px;"></Input>
			</Col>
			<Col span="6">
				<span class="conditionTitle">时间类型：</span>
				<Select v-model="queryPrams.alarmTimeType" style="width:65%" @on-change="changeAlarmType">
					<Option v-for="item in enumData.timeType" :value="item.key" :key="item.key">{{ item.value }}</Option>
				</Select>
			</Col>
			<Col span="6">
				<span class="conditionTitle">开始时间：</span>
				<DatePicker :readonly="isReady" v-model="queryPrams.startTime" type="datetime" placeholder="选择结束时间"
							style="width: 65%;"></DatePicker>
			</Col>
			<Col span="6">
				<span class="conditionTitle">结束时间：</span>
				<DatePicker :readonly="isReady" v-model="queryPrams.endTime" type="datetime" placeholder="选择结束时间"
							style="width: 65%;"></DatePicker>
			</Col>
			<Col span="6">
				<Button type="primary"  @click="queryAlarmData" icon="ios-search"  style="font-size: 1.7vmin">查询告警</Button>
				<Button type="info"  @click="clearAlarms" icon="ios-trash-outline"  style="font-size: 1.7vmin">清除告警</Button>
			</Col>
		</Row>
		<Row>
			<Col span="24">
				<div style="position:absolute; height: 40px;right: 5px;top:-55px;z-index: 10001">
					<ShowMonitorObjectSelect v-bind="dataObjectSelect"></ShowMonitorObjectSelect>
					<ClearAlarm v-bind="alarmsClear"></ClearAlarm>
				</div>
				<div class="boxBG">
					<Table :height="tableHeight" stripe border :columns="tableColumn" :data="tableData" ref="selection"
						:loading="tableLoad"
						@on-selection-change="selectionClick"></Table>
				</div>
			</Col>
		</Row>
		<div>
			<div style="float: left">
				<div class="tipBox">
					<Icon class="iconSize" type="information-circled" color="#00fff6" size="30"></Icon>
					<span class="alarmCircleText">提示：{{alarmLevelCount[0]}}</span>
				</div>
				<div class="tipBox">
					<Icon class="iconSize" type="checkmark-circled" color="#2cec47" size="30"></Icon>
					<span class="alarmCircleText">一般：{{alarmLevelCount[1]}}</span>
				</div>
				<div class="tipBox">
					<img :src='iconWarning' style="width: 28px">
					<span class="alarmCircleText">严重：{{alarmLevelCount[2]}}</span>
				</div>
				<div class="tipBox">
					<Icon class="iconSize" type="close-circled" color="#d81e06" size="30"></Icon>
					<span class="alarmCircleText">危急：{{alarmLevelCount[3]}}</span>
				</div>
			</div>
			<Page class="nextPage" @on-change="changePage" @on-page-size-change="handlePageSize"
					:total="queryPrams.total" :page-size-opts=[12,24,36]
					show-total show-elevator show-sizer placement="top"
					:page-size="queryPrams.pageSize"></Page>
		</div>
		<showAlarm
            :modalPrams="videoModal.modalPrams"
            :alarmContainer="videoModal.alarmContainer"
            ref="video"
        ></showAlarm>
	</div>
</template>

<script>
	import {EnumsService} from '@/services/enumsService.js'
	import {TunnelService} from '@/services/tunnelService.js'
	import {DataAnalysisService} from '@/services/dataAnalysisService.js'
	import ClearAlarm from '@/components/Common/Modal/ClearAlarm'
	import ShowMonitorObjectSelect from '@/components/Common/Modal/ShowMonitorObjectSelect'
	import iconWarning from "@/assets/UM/tipWarning.png";
	import showAlarm from "@/components/Common/Modal/showAlarms";

	export default {
		name: "query-alarm-data",
		data() {
			return {
				tableHeight:450,
				selectSelection: null,
				tableLoad: true,
				isReady: true,
				tunnelList: [],
				dataObjectSelect: {
					show: {state: false},
					selectObjects: {},
					selectData: {idList: ""},
				},
				alarmsClear: {
					modalPrams: {state: false, ids: [],},
				},
				curlineChart: {
					id: "historyDataChart",
					requestUrl: 'lineChart',
					titleName: '23',
					title: "历史数据",
					titleColor: '#030303',
					intervalTime: 1000,
				},
				queryPrams: {
					cleaned: 0,
					tunnelId: null,
					alarmLevels: [],
					objectId: null,
					alarmTimeType: 3,
					monitorZone: null,
					endTime: null,
					startTime: null,
					total: 0,
					pageSize: 12,
					pageNum: 1,
				},
				enumData: {
					alarmLevel: [],
					timeType: [{key: 1, value: "最近一天"}, {key: 2, value: "最近一周"}, {key: 3, value: "最近一月"}, {key: 4, value: "自定义"}],
				},
				dataTypeEnum: [],
				alarmStatusList: [{value: -1, label: "全部"}, {value: 1, label: "已清除"}, {value: 0, label: "未清除"}],
				alarmLevelCount: [0, 0, 0, 0],
				tableColumn: [
					{
						type: 'selection',
						align: 'center',
						width: window.innerWidth/100*80/100*5
					},
					{
						title: 'Id',
						key: 'id',
						align: 'center'
					},
					{
						title: '告警名称',
						key: 'alarmName',
						align: 'center'
					},
					{
						title: '所属管廊', 
						key: 'tunnel',
						render: (h, params) => {
							return h('span', params.row.tunnel.name)
						},
						align: 'center'
					},
					{
						title: '监测对象名', 
						key: 'objectName',
						align: 'cenetr'
					},
					{
						title: '告警级别', 
						key: 'alarmLevelName',
						align: 'center'	
					},
					{
						title: '告警时间', 
						key: 'alarmDate',
						align: 'center',
						render: (h, params) => {
							return h('span', new Date(params.row.alarmDate).format('yyyy-MM-dd hh:mm:s'))
						}
					},
					{
						title: '是否清除', 
						key: 'cleaned',
						align: 'center',
						render: (h, params) => {
							let temp = null
							if(params.row.cleaned==true){
								temp = '已清除'
							}else{
								temp = '未清除'
							}
							return h('span', temp)
						}
					},
					{
						title: '清除时间', 
						key: 'cleanedDate',
						align: 'center',
						render: (h, params) => {
							if(params.row.cleanedDate!=null){
								return h('span', new Date(params.row.cleanedDate).format('yyyy-MM-dd hh:mm:s'))
							}
						}
					},
					{
						title: '操作',
						key: 'action',
						align: 'center',
						render: (h, params) => {
						return h('div', [
							h('Button', {
								props: {
									type: 'success',
									size: 'small'
								},
								style: {
									display: (params.row.cleaned == true) ? "none" : "inline-block",
									'margin-right': '0.5vmin'
								},
								on: {
									click: () => {
										this.clearAlarms(params.index)
									}
								}
							}, '清除'),
							h('Button', {
								props: {
									type: 'primary',
									size: 'small'
								},
								on: {
									click: () => {
										this.showAlarmDetailss(params.row.id)
									}
								}
							}, '详情')
						]);
						}
					},
				],
				tableData: [],
				iconWarning: iconWarning,
				videoModal: {
                	modalPrams: {
						state: false
					},
					alarmContainer: []
				}
			}
		},
		components: {
			ShowMonitorObjectSelect,
			ClearAlarm,
			showAlarm
		},
		watch: {
			"dataObjectSelect.selectData.idList": function () {
				this.queryPrams.objectId = this.dataObjectSelect.selectData.idList;
			},
			"alarmsClear.modalPrams.state": function () {
				if (!this.alarmsClear.modalPrams.state && this.alarmsClear.modalPrams.ids.length > 0) {
					this.queryAlarmData();
				}

			}
		},
		mounted() {
			if(this.$route.params){
				this.queryPrams.alarmLevels = this.$route.params.alarmLevels
			}
			this.tableHeight = window.innerHeight * 0.64;
			this.inItData();
			this.changeAlarmType(this.queryPrams.alarmTimeType);
			this.queryAlarmData();
			EnumsService.getAlarmLevel().then(
				result => {
					this.enumData.alarmLevel = result
				},
				error => {
					this.Log.info(error)
				}
			)
		},
		methods: {
			showAlarmDetailss(id){
				this.axios.get('alarms/'+id).then(res=>{
					let{ code, data, msg } = res.data
					if(code==200){
						this.videoModal.modalPrams.state = true
						this.videoModal.alarmContainer=[]
						this.videoModal.alarmContainer.unshift(data);
						this.saveAlarmContainer.unshift(data);
						// let plans = data.plans; //[{"name":"通风预案","id":4003}]
						// if (plans && plans.length) {
							// _this.selectPlan = plans[0].id;
						// }
						// _this.alarmLevel.forEach(a => {
						// 	if (a.val == alarm.alarmLevel) {
						// 		des = a.key;
						// 	}
						// });
					}
				})
			},
			//查询监测对象
			queryObject() {
				let _this = this;
				_this.dataObjectSelect.show.state = !_this.dataObjectSelect.show.state;
			},

			//初始化查询条件下拉列表数据
			inItData() {
				var _this = this;
				EnumsService.getMonitorType().then((result) => {
					_this.objectList = result;
				});
				EnumsService.getDataType().then((result) => {
					_this.dataTypeEnum = result;
				});
				TunnelService.getTunnels().then((result) => {
					_this.tunnelList = [{id: -1, name: "全部"}];
					result.reduce((a,b)=>{
						_this.tunnelList.push(b);
					}, _this.tunnelList)
				});
				DataAnalysisService.getAlarmCountByAlarmLevel().then((result) => {
					if (result) {
						_this.alarmLevelCount = [];
						result.forEach(a => {
							_this.alarmLevelCount.push(a.val);
						})
					}
				});
			},

			queryAlarmData() {
				var _this = this;
				if(new Date(_this.queryPrams.startTime)>new Date(_this.queryPrams.endTime)){
					_this.$Message.error('开始时间必须小于结束时间！');
					return;
				}
				_this.tableLoad = true;
				let prams = {
					startTime: new Date(_this.queryPrams.startTime),
					endTime: new Date(_this.queryPrams.endTime),
					tunnelId: _this.queryPrams.tunnelId,
					cleaned: _this.queryPrams.cleaned,
					alarmLevels: _this.queryPrams.alarmLevels,
					pageNum: _this.queryPrams.pageNum,
					pageSize: _this.queryPrams.pageSize
				};
				DataAnalysisService.getAlarmData(prams).then((result) => {
				if (result) {
					_this.queryPrams.total = result.total;
					_this.tableData = result.list
					if(prams.cleaned==0){
						this.NumGlobal.alarmNum = result.total
					}
				}
				}).then(() => {
					_this.tableLoad = false;
				})
			},
			//勾选数据行
			selectionClick(arr) {
				var _this = this;
				_this.selectSelection = arr;
				if (arr.length > 0) {
				_this.alarmsClear.modalPrams.ids = [];
				arr.forEach(a => {
					_this.alarmsClear.modalPrams.ids.push(a.id);
				})
				}
			},
			//更改告警时间类型
			changeAlarmType(index) {
				var _this = this;
				var date = new Date();
				if (index == 1) {
					date.setTime(date.getTime() - 3600 * 1000 * 24);
					_this.queryPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
					_this.queryPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
					_this.isReady = true;
				}
				else if (index == 2) {
					date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
					_this.queryPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
					_this.queryPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
					_this.isReady = true;
				}
				else if (index == 3) {
					date.setTime(date.getTime() - 3600 * 1000 * 24 * 30);
					_this.queryPrams.startTime = date.format("yyyy-MM-dd hh:mm:ss");
					_this.queryPrams.endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
					_this.isReady = true;
				}
				else {
					_this.isReady = false;
					_this.queryPrams.startTime = "";
					_this.queryPrams.endTime = "";
				}
			},
			//切换页面
			changePage(index) {
				let _this = this;
				_this.queryPrams.pageNum = index;
				_this.queryAlarmData();
			},
			//切换页码数
			handlePageSize(value) {
				this.queryPrams.pageSize = value;
				this.queryAlarmData();
			},

			//清除勾选告警
			clearAlarms(index) {
				var _this = this;
				if (typeof (index) === "number") {
				_this.alarmsClear.modalPrams.ids = [_this.tableData[index].id];
				}
				_this.alarmsClear.modalPrams.state = !_this.alarmsClear.modalPrams.state;
			}
		}
	}
</script>

<style scoped>
	.queryHis {
		padding-right: 5px;
		background-color: #e5eae99c;
		line-height: 50px;
		font-size: 16px;
	}

	.queryEquipment {
		position: relative;
		min-height: 100%;
		padding-bottom: 50px;
	}

	.nextPage {
		float: right;
		position: relative;
		top: 8px;
		right: 8px;
	}

	.slide-fade-enter-active {
		transition: all .5s ease;
	}

	.slide-fade-leave-active {
		transition: all .5s cubic-bezier(1.0, 0.5, 0.8, 1.0);
	}

	.slide-fade-enter, .slide-fade-leave-to {
		transform: translateX(10px);
		opacity: 0;
	}

	.ivu-select-single >>> .ivu-select-selection >>> .ivu-select-selected-value {
		font-size: 1.4vmin;
	}

	.ivu-select-single >>> .ivu-select-selection >>> .ivu-select-placeholder {
		font-size: 1.4vmin;
	}
	.ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
	.ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder,
	.ivu-select-multiple.ivu-select-selection >>> .ivu-select-placeholder
	{
		height: 4vmin;
		line-height: 4vmin;
		font-size: 1.4vmin;
		color: #fff;
	}
	.ivu-select-multiple >>> .ivu-tag,.ivu-tag-checked{
		height: 3.2vmin;
		line-height: 3.2vmin;
		font-size: 1.2vmin;
	}
	.queryAlarm {
		position: relative;
		min-height: 96%;
		padding-bottom: 40px;
	}

	.alarmCircle {
		float: left;
	}

	.alarmCircleText {
		margin-left: 1.5vh;
		color: #fff;
		margin-right: 2vmin;
		font-size: 1.8vmin;
	}
  	.ivu-table-wrapper{
        border: none;
    }
    .ivu-table-wrapper>>>.ivu-table{
        color: #ffffff !important;
        background-color: #fffdfd00 !important;
    }
    .ivu-table-wrapper>>>.ivu-table:before,.ivu-table-wrapper>>>.ivu-table:after{
        background-color: #fffdfd00 !important;
    }
    .ivu-table-wrapper>>>.ivu-table th,.ivu-table-wrapper>>>.ivu-table td{
        background-color: #fffdfd00 !important;
        border-bottom: 1px solid #7d7d7d;
    }
	.ivu-table-wrapper>>>.ivu-table-tip{
		overflow-x: hidden;
	}
	.ivu-table-wrapper>>>.ivu-table-overflowY::-webkit-scrollbar{
		width: 0.4vmin;
		height: 0.4vmin;
	}
	.ivu-table-wrapper>>>.ivu-table-overflowY::-webkit-scrollbar-thumb{
		border-radius: 1vmin;
		box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
		background: #83a6ed;
	}
	.ivu-table-wrapper>>>.ivu-table-overflowY::-webkit-scrollbar-track{
		box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
		border-radius: 1vmin;
		background: #ededed;
	}
    .nextPage .ivu-page>>>.ivu-page-total, .ivu-page>>>.ivu-page-options-elevator,.nextPage.ivu-page>>>.ivu-page-total{
        color: #fff;
    }
    .boxBG{
        background: url("../../../../assets/UM/infoBox.png") no-repeat;
        background-size: 100% 100%;
        padding: 2vmin 4vmin;
    }
    .ivu-table-wrapper>>>.ivu-table-border td, .ivu-table-wrapper>>>.ivu-table-border th{
        border-right: none;
    }
    .ivu-select,.ivu-select >>> .ivu-select-selection {
        background-color: #fffdfd00 !important;
    }
    .queryCondition .ivu-select{
        color: #fff;
	}
	.iconSize{
		width: 3vh;
		height: 3vh;
		margin-top: 0.5vmin;
	}
	.tipBox{
		display: inline-block;
	}
	.queryCondition .ivu-btn-info{
		background: linear-gradient(to left, #f61a1a, #f68380)
	}
</style>

