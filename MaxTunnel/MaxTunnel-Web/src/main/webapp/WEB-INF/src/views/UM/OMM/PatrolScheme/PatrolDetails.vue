<template>
	<div style="margin: 24px">
		<Row class="formBG">
		<h2 style="margin-bottom: 10px;color: #fff; margin-left: 3vmin;">巡检计划详情</h2>
			<Col span="12" style="padding-right: 20px;">
				<div class="leftContainer">
					<Form :model="plan" :label-width="100" @submit.native.prevent>
						<Col span="12">
							<FormItem label="计划编号：">
								<Input v-model='plan.planId' disabled></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label="计划名称：">
								<Input v-model='plan.name' disabled></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label="所属管廊：">
								<Select v-model='plan.tunnelId' disabled>
									<Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label="计划状态：">
								<Input type="text" v-model="plan.processStatus" disabled></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label="责任班组：">
								<Select v-model='plan.groupId' disabled>
									<Option v-for="item in groups" :value="item.id" :key="item.id">{{ item.name }}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label="责任人：">
								<Input v-model="plan.inspectionGroup.leaderName" disabled></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label="巡检方式：">
								<Input v-model="plan.inspectionWayName" disabled></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label="巡检路径：">
								<Input v-model="plan.inspectionPathName" disabled></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label="巡检对象：">
								<Input v-model="plan.inspectionObjectName" disabled></Input>
							</FormItem>
						</Col>
						<!-- <Col span="12">
							<FormItem label="巡检日期：">
								<Input v-model="plan.inspectTime" disabled></Input>
							</FormItem>
						</Col> -->
						<Col span="12">
							<FormItem label="执行天数：">
								<Input v-model="plan.tasks.length" disabled></Input>
							</FormItem>
						</Col>
						<Col span="24">
							<FormItem label="计划步骤：">
								<ul style="max-height: 100px;overflow-y: auto;">
									<li v-for="(item, index) in plan.steps" :key="index" class="todoLi">
										<span style="color: #fff">{{index+1}}、</span>
										<input class="todoEidt" v-model="item.name" placeholder="请输入要执行的计划步骤" />
									</li>
								</ul>
							</FormItem>
						</Col>
						<Col span="24">
							<FormItem label="计划描述：">
								<Input v-model="plan.remark" type="textarea" :rows="4" placeholder="请输入描述..." disabled></Input>
							</FormItem>
						</Col>
						<Col span="24" style="text-align: center;padding-bottom: 20px;">
							<Button type="default" @click="goBack()">返回</Button>
						</Col>
					</Form>
				</div>
			</Col>
			<Col span="12">
				<div class="rightContainer">
					<h3>巡检记录：（
						流程未结束：<div class="finish noFinish"></div>
						流程结束：<div class="finish isFinish"></div>
					）
					</h3>
					<ul class="patrolRecordUl">
						<li v-for="item in patrolRecords" :value="item.id" :key="item.id" :class="[item.taskType==1?'noFinish':(item.taskType==2?'isFinish':'') ]">{{item.id}}</li>
					</ul>
					<Table :columns="columns1" :data="plan.tasks" style="margin: 20px auto;" height:440></Table>
					<Col span="24" v-show="pageType==pageTypes.Edit" style="text-align: right;margin-top: 20px;">
						<Button type="primary" @click="submit()">确定</Button>
						<Button type="primary">取消</Button>
					</Col>
				</div>
			</Col>
		</Row>
		<!-- <Icon class="goBack" type="chevron-left" size="30" @click="goBack()" title="返回"></Icon> -->
	</div>
</template>

<script>
import types from "../../../../../static/Enum.json";
import SmViewer from "../../../../components/Common/3D/3DViewer";
import { URL_CONFIG } from "../../../../../static/3DMap/js/3DMapConfig";
import { setViewAngle } from "../../../../scripts/commonFun";
import { TunnelService } from '../../../../services/tunnelService';
import { PatrolService } from '../../../../services/patrolService';
import { EnumsService } from '../../../../services/enumsService';
export default {
	name: "plans",
	data() {
		return {
		// 页面类型 1：查看 2：编辑 3：新增
		pageType: 1,
		pageTypes: types.pageType,
		plan: {
			planId: null,
			name: null,
			tunnelId: null,
			processStatus: null,
			groupId: null,
			createTime: null,
			inspectTime: null,
			tasks: [],
			remark: null,
			inspectionGroup: {
				leader: {name: null}
			},
			inspectionObjectName: null,
			inspectionPathName: null,
			inspectionWayName: null,
			steps: []
		},
		reqstatus: [],
		// 管廊
		tunnels: [],
		groups: [
			{
				id: 1,
				name: "group1",
				leader: {
					id: 0,
					name: ""
				}
			}
		],
		patrolRecords: [],
		columns1: [
			{
				type: 'index',
				width: 70,
				align: "center"
			},
			{
				title: "巡检人",
				key: "inspectManName",
				align: "center"
			},
			{
				title: "巡检日期",
				key: "taskTime",
				align: "center"
			},
			{
				title: "流程状态",
				key: "processStatus",
				align: "center"
			}
		],
		year: 1,
		month: 1,
		days: 1
		};
	},
	components: {
		SmViewer
	},
	mounted() {
		this.plan.planId = this.$route.params.id;
		this.pageType = this.$route.params.type;
		let _this = this

		// 获取所有的管廊
		TunnelService.getTunnels().then(
			(result)=>{
				_this.tunnels = result;
			},
			(error)=>{
				_this.Log.info(error)
			}
		)

		// 从数据库读取责任班组select的option选项
		PatrolService.getGroups().then(
			(result)=>{
				_this.groups = result;
			},
			(error)=>{
				_this.Log.info(error)
			}
		)

		//申请状态
		EnumsService.getReqStatus().then(
		(result)=>{
			_this.reqstatus = result;
			for (let index in _this.reqstatus) {
				if (_this.reqstatus[index].val == _this.plan.requestStatus) {
					_this.plan.requestStatus = _this.reqstatus[index].key;
				}
			}
		},
		(error)=>{
			_this.Log.info(error)
		})
		this.getDetails()
	},
	watch: {
		$route: function() {
		//2. $route发生变化时再次赋值planId
		this.plan.id = this.$route.params.id;
		}
	},
	methods: {
		//获取详情
		getDetails(){
		PatrolService.getPDetailByPlanId(this.plan.planId).then(
			(result)=>{
				this.plan = result;
				// 暂定默认值
				this.plan.inspectTime = new Date(this.plan.inspectTime).format("yyyy-MM");
				this.plan.tasks.forEach(element => {
					element.inspectTime = new Date(element.inspectTime).format("yyyy-MM-dd");
					element.taskTime = new Date(element.taskTime).format("yyyy-MM-dd");
				});
				this.getRecords();
			},
			(error)=>{
				this.Log.info(error)
			})
		},
		//获取天数
		getRecords() {
			this.year = new Date(this.plan.inspectTime).getFullYear();
			this.month = new Date(this.plan.inspectTime).getMonth() + 1;

			if (this.month == 2) {
				this.days = year % 4 == 0 ? 29 : 28;
			} else if (
				this.month == 1 ||
				this.month == 3 ||
				this.month == 5 ||
				this.month == 7 ||
				this.month == 8 ||
				this.month == 10 ||
				this.month == 12
			) {
				this.days = 31;
			} else {
				this.days = 30;
			}
			//循环得出所有的天数
			for (var i = 1; i <= this.days; i++) {
				// taskType 0:没任务， 1，任务未完成； 2： 任务完成
				this.patrolRecords.push({
				id: i,
				taskType: 0,
				getColor: function(type) {
					if (type == 1) return "noFinish";
					if (type == 2) return "isFinish";
					// return "#fff";
				}
				});
			}
			this.plan.tasks.forEach(a => {
				let day = new Date(a.taskTime).getDate();
				this.patrolRecords[day - 1].taskType = a.isFinished ? 2 : 1;
			});
		},
		//返回
		goBack(){
			this.$router.back(-1);
		}
	}
};
</script>

<style  scoped>
todoLi{
	line-height: 3.5vh;
	height: 3.5vh;
	margin-right: 0.5vw;
	display: flex;
	flex-wrap: wrap;
	margin-bottom: 0.5vh;
}
.todoLi .todoEidt{
	border: none;
	box-shadow: 2px 2px 10px 0px #ccc;
	flex: 1;
	margin-right: 0.5vw;
	padding-left: 0.5vw;
}
.patrolRecordUl li {
	list-style: none;
	display: inline-block;
	width: 24px;
	height: 24px;
	border: 1px solid #eeeeee;
	text-align: center;
	line-height: 24px;
}
.leftContainer,
.rightContainer {
	padding: 20px;
	min-height: 40vh;
}
h3 {
  	line-height: 45px;
}
.ivu-input[disabled] {
	background: #fff;
	color: #495060;
}
.isFinish {
	background: #8524c6b3;
	color: #fff;
}
.noFinish {
	background: #338f8f;
	color: #fff;
}
.finish{
		display: inline-block;
		vertical-align: middle;
		width: 30px;
		height: 14px;
		border-radius: 4px;
		margin-right: 5px;
}
.ivu-input-wrapper >>> .ivu-input[disabled]{
    background-color: #ffffff !important;
    color: #495060 !important;
}
.goBack{
    position: absolute;
    bottom: 7vh;
    right: 3vw;
}
.formBG{
	background: url("../../../../assets/UM/infoBox.png") no-repeat;
	background-size: 100% 100%;
	padding-top: 3vmin;
	padding-bottom: 3vmin;
}

.formBG >>> .ivu-form-item-label,.rightContainer h3, .rightContainer ul{
	color: #fff;
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
    border-bottom: none;
}
@media (min-width: 2200px){
    /* .ivu-form.ivu-form-label-right{
        width: 50%;
    } */
    h2{
        font-size: 2.4vmin;
    }
    .ivu-form-item >>> .ivu-form-item-label{
        width: 10vmin !important;
        line-height: 6.5vmin;
    }
    .ivu-form-item >>> .ivu-form-item-content{
        margin-left: 10vmin !important;
        line-height: 6.5vmin;
    }
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .ivu-form-item-content{
        line-height: 6.5vmin;
    }
    .ivu-form-item-required .ivu-form-item-label:before{
        font-size: 1.6vmin !important;
    }
    .ivu-input-number,.ivu-input-number >>> .ivu-input-number-input{
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .patrolRecordUl li{
        width: 2.3vmin;
        height: 2.3vmin;
        font-size: 1.4vmin;
        line-height: 2.3vmin;
    }
    .rightContainer h3{
        line-height: 4vmin;
        font-size: 1.6vmin;
    }
    .ivu-table td, .ivu-table th{
      	height: 4.5vmin;
    }
    .finish{
		width: 3vmin;
		height: 1.4vmin;
		border-radius: 0.4vmin;
		margin-right: 0.5vmin;
    }
}
</style>
