<template>
	<div>
		<Tabs type="card" @on-click="changePane" class="tab">
			<TabPane label="预案详情" name="detial">
				<div>
				<WorkModal v-if="showModal.val!=null" v-bind="showModal"></WorkModal>
				<!-- <Card style="width: 100%;">
					<Row :gutter="16">
						<Col span="2">
							<div class="begin">
								<img style="width:3vmin;height:3vmin;" v-bind:src="imgUrl">
							</div>
						</Col>
						<Col span="1">
							<div class="next">
								<img style=" width:3vmin;height:3vmin;" v-bind:src="next">
							</div>
						</Col>
						<div v-for="(item,index) in allSteps" :key="index">
							<Col span="2">
								<div
									style="width: 7vw;height:3vh;margin-top: -10px;"
									v-if="index > 0 && allSteps[index - 1].actionKey == 2"
									:style="{paddingTop : allSteps[index - 1].toTop ? (item.toTop-1) * 3 + 'vh' : '0'}"
								>
									<img style="width:5vmin;height:5vmin;" v-bind:src="status">
								</div>
								<Tooltip placement="bottom" class="toolTip">
									<div
										:style="{ marginTop : item.toTop  ? (allSteps[index - 1].actionKey == 2 ? (item.toTop > 1 ? '6vh': '2vh') : (item.toTop > 2 ? (item.toTop-1) * 4 + 3 + 'vh' : item.toTop * 4 + 'vh')) : '0'}"
										class="border"
										>
										<Icon type="record" color></Icon>
										{{ item.stepName }}
									</div>
									<div slot="content" class="target">
										<p>{{ item.target }}</p>
									</div>
								</Tooltip>
							</Col>
							<Col span="1" v-if="index + 1 != allSteps.length && item.actionKey != 2">
								<div
									class="next"
									:style="{ paddingTop : item.toTop ? (item.toTop > 2 ? (item.toTop-1) * 3 + 6 + 'vh' : (item.toTop-1) * 4 + 5 + 'vh') : '0'}"
								>
									<img style=" width:3vmin;height:3vmin;" v-bind:src="next">
								</div>
							</Col>
							<Col span="1" v-if="item.actionKey == 2">
								<div :style="{ marginTop: item.toTop ? item.toTop * 4 + 'vh' : '1vh'}">
									<img style=" width:5vmin;height:2vmin;" v-bind:src="toUp">
									<img style=" width:5vmin;height:2vmin;" v-bind:src="toDown">
								</div>
							</Col>
						</div>
					</Row>
				</Card> -->
				<hr class="hr3">
				<!-- <img
					style="width: 100%;height: 81vh"
					v-bind:src="bgImg"
				> -->
				<div 
				:style="{width: '100%',height: '81vh',background:'url('+bgImg+')',backgroundSize: 'over',position:'relative'}">
					<div class="nodesPic">
						<image-from-url :url="nodeImgSrc"></image-from-url>
					</div>
				</div>
				</div>
			</TabPane>
			<TabPane label="执行记录" name="log">
				<Row class="top">
					<Col span="6">
						<span>开始时间:</span>
						<DatePicker
							style="width: 65%;"
							v-model="queryPram.startTime"
							format="yyyy年MM月dd日-hh:mm:ss"
							type="datetime"
							placeholder="选择时间"
						></DatePicker>
					</Col>
					<Col span="6">
						<span>结束时间</span>
						<DatePicker
							style="width: 65%;"
							v-model="queryPram.endTime"
							format="yyyy年MM月dd日-hh:mm:ss"
							type="datetime"
							placeholder="选择时间"
						></DatePicker>
					</Col>
					<Col span="2" offset="10">
						<div style="position: relative;float: right;right: 0px;">
						<Button type="primary" icon="ios-search" @click="queryTable">查询</Button>
						</div>
					</Col>
				</Row>
				<Row>
					<Col span="24" style="margin-top: 10px;">
						<hr class="hr1">
					</Col>
				</Row>
				<Row>
					<Col span="24">
						<Table
							:height="tableHeight"
							stripe
							border
							:columns="tableColumns"
							:data="tableData"
							style="margin: 1vh;"
						></Table>
						<Modal width="900px;" title="详细信息:" v-model="showTableDetial" :mask-closable="false">
						<Table
							stripe
							border
							:columns="detialTableColumns"
							style="height: 540px;"
							:data="detialTableData"
						></Table>
						</Modal>
					</Col>
				</Row>
				<div>
					<Page
						style="position: relative;float: right;right: 10px;"
						@on-page-size-change="handlePageSize"
						:total="pageTotal"
						:page-size="queryPram.pageSize"
						:current="queryPram.pageNum"
						show-total
						show-elevator
						show-sizer
						placement="top"
						@on-change="changePage"
					></Page>
				</div>
			</TabPane>
		</Tabs>
		<ShowStartPlan v-bind="showModal"></ShowStartPlan>
	</div>
</template>

<script>
import start from "../../../../assets/UM/start.png";
import status from "../../../../assets/UM/status.png";
import toDown from "../../../../assets/UM/toDown.png";
import next from "../../../../assets/UM/next.png";
import toUp from "../../../../assets/UM/toUp.png";
import waterPlan from "../../../../assets/UM/waterPlan.png";
import windPlan from "../../../../assets/UM/wind.png";
import ShowStartPlan from "../../../../components/Common/Modal/ShowStartPlan";
import { PlanService } from "../../../../services/planService";
import { EnumsService } from "../../../../services/enumsService";
import ImageFromUrl from "../../../../components/Common/ImageFromUrl"

export default {
	name: "detial-plan",
	data() {
		return {
			imgUrl: start,
			next: next,
			status: status,
			tableHeight: 450,
			toUp: toUp,
			toDown: toDown,
			waterPlan: waterPlan,
			windPlan: windPlan,
			pageTotal: 1,
			showTableDetial: false,
			queryPram: {
				startTime: null,
				endTime: null,
				pageNum: 1,
				pageSize: 12,
				processKey: null
			},
			showModal: {
				modalPrams: { state: false }
			},
			tableColumns: [
				{
					title: "Id",
					key: "processInstanceId"
				},
				{
					title: "开始时间",
					key: "startTime"
				},
				{
					title: "结束时间",
					key: "endTime"
				},
				{
					title: "操作",
					key: "action",
					width: 150,
					align: "center",
					render: (h, params) => {
						return h("div", [
						h(
							"Button",
							{
							props: {
								type: "primary",
								size: "default"
							},
							style: {
								marginRight: "5px"
							},
							on: {
								click: () => {
								this.show(params.index);
								}
							}
							},
							"查看详情"
						)
						]);
					}
				}
			],
			detialTableColumns: [
				{
					title: "Id",
					key: "id"
				},
				{
					title: "开始时间",
					key: "startTime"
				},
				{
					title: "结束时间",
					key: "endTime"
				},
				{
					title: "流程",
					key: "activityName"
				},
				{
					title: "流程类型",
					key: "activityType"
				}
			],
			detialTableData: [],
			tableData: [],
			allSteps: [],
			bgImg: null,
			nodeImgSrc: null
		};
	},
	mounted() {
		// 设置表格高度
		this.tableHeight = window.innerHeight * 0.69;
		this.queryPram.processKey = this.$route.params.processKey;
		this.setBgImg()
		// this.getProcess();
		this.getNodes()
	},
	methods: {
		getNodes(){
			EnumsService.getPlanType().then(
				res=>{
					let curPlan = res.find(plan=>{
						return plan.processKey == this.$route.params.processKey
					})
					this.nodeImgSrc = '/emplans/png/static/'+ curPlan.val
				}
			)
			
		},
		// 获取预案步骤
		getProcess() {
		let _this = this;
		PlanService.getAllSteps(this.queryPram.processKey).then(
			result => {
			// this.Log.info(result)
				let index = result.planStatus.findIndex(step => {
					return step.stepName == "值班人员确认";
			});
			if (index > -1) {
				result.planStatus.splice(index, 1);
				result.planStatus[index - 1].actionKey = 2;
			}
			this.allSteps = [];
			this.allSteps = result.planStatus;
			let branchIndexs = [];
			this.allSteps.filter((step, index) => {
				if (step.actionKey == 2) {
					branchIndexs.push(index);
				}
				switch (step.targetName) {
				case "类型":
					step.target =
					step.stepName.slice(0, 2) +
					"所有类型为" +
					step.targetValue +
					"的" +
					step.stepName.slice(2);
					break;
				case "指定对象":
					step.target =
					step.stepName.slice(0, 2) +
					"id 为" +
					step.targetValue +
					"的" +
					step.stepName.slice(2);
					break;
				}
			});
			let indexRange = [];
			this.allSteps.forEach((step, index) => {
				if (
				(index > 6 &&
					Number.isInteger((index - 7) / 8) &&
					step.actionKey == 2) ||
				(index == 6 && step.actionKey == 2)
				) {
					indexRange[0] = index;
					indexRange[1] = index + 8;
				}
				let count = index > indexRange[0] && index <= indexRange[1] ? 1 : 0;
				step.toTop = null;
				let rows = index > 6 ? 6 + Math.floor((index - 7) / 8) * 8 : 0;
				branchIndexs.forEach(a => {
					if (index > a && (index > rows ? a > rows : 1)) {
						count++;
					}
				});
				step.toTop = count;
			});
			},
			error => {
			this.Log.info(error);
			}
		);
		},
		setBgImg(){
			switch(this.queryPram.processKey){
				case 'VentilationPlanSOP_KEY':
					this.bgImg = windPlan
					break
				case 'WaterPlanProcess':
					this.bgImg = waterPlan
					break
				default: 
					this.bgImg = waterPlan
			}
		},
		//切换路由
		goToMoudle(path) {
			this.$router.push(path);
		},
		//切换页面
		changePage(index) {
			let _this = this;
			_this.queryPram.pageNum = index;
			_this.queryTable();
		},
		//切换页码数
		handlePageSize(value) {
			this.queryPram.pageSize = value;
			this.queryTable();
		},

		//点击切换面板事件
		changePane(name) {
			if (name == "log") {
				this.queryTable();
			} else {
			}
		},
		// 查询表数据
		queryTable() {
			this.queryPram.processKey = this.$route.params.processKey;
			let prams = {};
			prams = this.queryPram;
			let _this = this;
		if (
			new Date(_this.queryPram.startTime) > new Date(_this.queryPram.endTime)
		) {
			_this.$Message.error("开始时间必须小于结束时间！");
			return;
		}
		_this.axios.post("plans/his-proc-inst/datagrid", prams).then(result => {
			let { code, data } = result.data;
			if (code == 200) {
			_this.tableData = [];
			data.list.forEach(a => {
				let temp = {};
				temp.startTime = new Date(a.startTime).format(
				"YYYY-MM-dd hh:mm:ss"
				);
				temp.endTime = new Date(a.endTime).format("YYYY-MM-dd hh:mm:ss");
				temp.processInstanceId = a.processInstanceId;
				_this.tableData.push(temp);
			});
			_this.pageTotal = data.totalCount;
			}
		});
		},

		//手动开启预案
		startPlan() {
			this.showModal.modalPrams.state = !this.showModal.modalPrams.state;
		}
	},
	components: { ShowStartPlan,ImageFromUrl },
	watch: {
		$route: function() {
			// $route发生变化时再次赋值
			this.queryPram.processKey = this.$route.params.processKey;
			// this.getProcess();
			this.setBgImg()
			this.getNodes()
		}
	}
};
</script>

<style scoped>
	.top {
		margin-top: 1vh;
		background-color: #fff;
		font-size: 1.66vmin;
		height: 5.5vh;
		padding: 1vh;
		margin-left: 10px;
		margin-right: 10px;
	}
	.ivu-tabs-card >>> .ivu-tabs-bar {
		margin-bottom: 0px !important;
	}
	.ivu-tabs >>> .ivu-card-body {
		padding: 10px !important;
	}
	.ivu-tooltip >>> .ivu-tooltip-inner {
		background-color: rgba(110, 110, 110, 0.9);
		text-align: center;
		color: rgb(174, 205, 237);
		height: 100px;
		font-size: 1.6vmin;
		display: table-cell;
		vertical-align: middle;
		font-weight: bold;
		min-width: 180px;
	}
	.ivu-tooltip >>> .ivu-tooltip-arrow {
		border-bottom-color: rgb(112, 112, 112);
	}
	.border {
		box-shadow: 5px 5px 15px #cdf5ff inset;
		border: 1px solid #d9fff1;
		border-radius: 8px;
		color: #fff;
		width: 7vw;
		height: 6vh;
		background-color: #22a5f5;
		padding: 7px;
		cursor: pointer;
		font-size: 1.66vmin;
	}
	.hr3 {
		height: 7px;
		border: none;
		border-top: 5px ridge #45d6d8;
	}
	.hr1 {
		margin: 0 auto;
		border: 0;
		height: 5px;
		background: #333;
		background-image: linear-gradient(to right, #ccc, #333, #ccc);
	}
	.next {
		display: table-cell;
		text-align: center;
		width: 4vw;
		height: 6vh;
		vertical-align: middle;
	}
	.begin {
		display: table-cell;
		text-align: right;
		width: 7vw;
		height: 6vh;
		vertical-align: middle;
	}
	.target {
		width: 22vmin;
		letter-spacing: 2px;
		font-size: 1.6vmin;
	}
	.toolTip >>> .ivu-tooltip-inner {
		max-width: 2000px;
		height: 4vmin;
	}
	.tab >>> .ivu-tabs-bar {
		height: 3.2vmin;
	}
	.tab >>> .ivu-tabs-bar .ivu-tabs-tab {
		height: 3.2vmin;
	}
	.tab >>> .ivu-tabs-nav-container {
		height: 3.2vmin !important;
	}
	.ivu-select,
	.ivu-select >>> .ivu-select-selection,
	.ivu-input-wrapper >>> .ivu-input,
	.ivu-date-picker >>> .ivu-input,
	.ivu-select.ivu-select-single >>> .ivu-select-selected-value,
	.ivu-select.ivu-select-single >>> .ivu-select-placeholder,
	.ivu-select-multiple.ivu-select-selection >>> .ivu-select-placeholder {
		height: 4vmin;
		line-height: 4vmin;
		font-size: 1.4vmin;
	}
	.tab >>> .ivu-tabs-nav-container{
		font-size: 1.66vmin;
	}
	.nodesPic{
		width: 20vw;
		height: 60vh;
		position: absolute;
		top: 12vh;
		left: 11vw;
	}
</style>
