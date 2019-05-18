<template>
  <div :style="backStyle">
    <Form ref="uploadPlan" :model="uploadPlan" label-position="top" :rules="ruleValidate" @submit.native.prevent>
		<h2 class="formTitle" style="color: white">制定巡检计划</h2>
		<Row>
			<Col span="12" class="leftForm">
                <div class="planContainer leftContainer" prop="planId">
                    <FormItem label="计划编号：" prop="planId">
                        <Input v-model="uploadPlan.planId"></Input>
                    </FormItem>
                    <FormItem label="计划名称：" prop="name">
                        <Input v-model="uploadPlan.name"></Input>
                    </FormItem>
                    <FormItem label="所属管廊：" prop="tunnelId">
                        <Select v-model="uploadPlan.tunnelId" readonly>
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                        </Select>
                    </FormItem>
                    <FormItem label="责任班组：" prop="groupId">
                        <Select v-model="uploadPlan.groupId">
                        	<Option v-for="item in groups" :value="item.id" :key="item.id">{{ item.name }}</Option>
                        </Select>
                    </FormItem>
					<FormItem label="巡检方式：" prop="inspectionWay">
						<Select v-model="uploadPlan.inspectionWay">
							<Option v-for="(item, index) in inspectWay" :value="item.val" :key="index">{{item.key}}</Option>
						</Select>
					</FormItem>
					<FormItem label="巡检路径：" prop="inspectionPath">
						<Select v-model="uploadPlan.inspectionPath">
							<Option v-for="(item, index) in inspectPath" :value="item.val" :key="index">{{item.key}}</Option>
						</Select>
					</FormItem>
					<FormItem label="巡检对象：" prop="inspectionObject">
						<Select v-model="uploadPlan.inspectionObject">
							<Option v-for="(item, index) in inspectObject" :value="item.val" :key="index">{{item.key}}</Option>
						</Select>
					</FormItem>
                    <FormItem label="申请人：">
                        <Input v-model="uploadPlan.requestStaffId" readonly v-show="false"></Input>
                        <Input v-model="requestStaffName" readonly></Input>
                    </FormItem>
                    <FormItem label="审批人：">
                        <Input v-model="uploadPlan.approverId" readonly style="display: none"></Input>
                        <Input v-model="approver.name" readonly></Input>
                    </FormItem>
                    <FormItem label="计划步骤：">
                        <ul style="max-height: 100px;overflow-y: auto;">
                            <li v-for="(item, index) in uploadPlan.steps" :key="index" class="todoLi">
                                <span>{{index+1}}、</span>
                                <input class="todoEidt" v-model="item.name" placeholder="请输入要执行的计划步骤" />
                                <Button class="todoButton" type="error" size="small" style="margin-right: 5px" :disabled="index==0" @click="delList(index)">删除</Button>
                                <Button class="todoButton" type="primary" size="small" style="margin-right: 5px" @click="addList(index)">添加</Button>
                            </li>
                        </ul>
                    </FormItem>
                </div>
			</Col>
			<Col span="12" class="rightForm">
                <div class="planContainer rightContainer">
					<FormItem label="计划描述：" prop="remark">
						<Input v-model="uploadPlan.remark" type="textarea" :autosize="{minRows: 4,maxRows: 4}" placeholder="请输入计划描述"></Input>
					</FormItem>
					<Tabs type="card" @on-click="clearInput">
						<TabPane label="年份" name="name1" style="padding-left: 1vmin;">
							<FormItem label="巡检年份：">
								<Input placeholder="请选择巡检年份" style="width: 100%;"></Input>
								<yearCalender ref="getYearChild" v-on:getYearChild="getActiveYearText"></yearCalender>
							</FormItem>
						</TabPane>
						<TabPane label="月份" name="name2" style="padding-left: 1vmin;">
							<FormItem label="巡检月份：">
								<Input placeholder="请选择巡检月份" style="width: 100%"></Input>
								<monthCalender ref="getMonthChild" v-on:getMonthChild="getActiveMonthText"></monthCalender>
							</FormItem>
						</TabPane>
						<TabPane label="日期" name="name3" style="padding-left: 1vmin;">
							<FormItem label="巡检月份：">
								<DatePicker type="month" placeholder="请选择巡检月份" style="width: 100%" v-model="uploadPlan.inspectTime"
								@on-change="getChooseMonth()"></DatePicker>
							</FormItem>
							<FormItem label="巡检日期安排：">
								<Tabs value="name1">
									<TabPane label="间隔模式" name="name1">
										<div>
										开始日期：
										<input v-model="intervalMode.startDay" type="number" min="1" max="31">
										结束日期：<input v-model="intervalMode.endDay" type="number" min="1" max="31">
										间隔天数：
										<input v-model="intervalMode.interval" type="number" min="1" max="31">
										<Button type="default" @click="getDay" class="tabBtn">确定</Button>
										<Button type="default" @click="defaultBtn" class="tabBtn">重置</Button>
										</div>
									</TabPane>
									<TabPane label="星期模式" name="name2">
										<Checkbox v-for="(item, index) in weekDay" v-model="item.value" :value='item.value' :key='index'
										@on-change="getWeek">{{item.name}}</Checkbox>
									</TabPane>
									<TabPane label="自定义" name="name3" style="text-align: center">
										请根据您的需求自由选择日期
									</TabPane>
								</Tabs>
								<calender ref="calender" :currentMonth="currMonth" :currentYear="currYear" v-on:childByValue="getActiveText"
								style="margin: 10px auto;margin-bottom: 0px;"></calender>
							</FormItem>
						</TabPane>
					</Tabs>
                </div>
			</Col>
		</Row>
		<FormItem style="text-align: center;margin-bottom: 0px">
			<Button type="ghost" style="margin-right: 8px" @click="goBack()">返回</Button>
			<Button type="primary" @click="submitPlan('uploadPlan')" :disabled="isDisable">提交</Button>
		</FormItem>
    </Form>
  </div>
</template>

<script>
import calender from "../../../Common/calender.vue";
import yearCalender from "../../../Common/yearCalender.vue"
import monthCalender from "../../../Common/monthCalender.vue"
import types from "../../../../../static/Enum.json";
import { PatrolService } from '@/services/patrolService'
import { GroupServices } from '@/services/groupService.js'
import { TunnelService } from '@/services/tunnelService'
export default {
    name: "plans",
    components: {
      	calender,
		yearCalender,
		monthCalender
    },
    data() {
		const validatePlanId = (rule, value, callback) => {
			if(value==null||value==''){
				callback(new Error('计划编号不能为空'))
			}else{
				callback()
			}
		}
		return {
			// 页面类型 1：查看 2：编辑 3：新增
			pageType: 1,
			pageTypes: types.pageType,
			currMonth: '',
			currYear: '',
			requestStaffName: '',
			approver: {},
			uploadPlan: {
                planId: new Date().getTime(),
                name: "",
				groupId: null,
				inspectionWay: null,
				inspectionPath: null,
				inspectionObject: null,
                inspectTime: null,
                tasks: [],
                approverId: 1,
                tunnelId: null,
                requestStaffId: null,
                remark: '',
                steps: [
                    { name: '' }
                ]
			},
			groups: [],
			selectedMode: "",
			tunnels: [],
			weekDay: [
                { name: "星期日", value: false },
                { name: "星期一", value: false },
                { name: "星期二", value: false },
                { name: "星期三", value: false },
                { name: "星期四", value: false },
                { name: "星期五", value: false },
                { name: "星期六", value: false }
			],
			intervalMode: {
				startDay: 1,
				endDay: 31,
				interval: 3
			},
			inspectTimeContainer: [],
			isDisable: false,
			ruleValidate: {
				planId: [
					// { required: true, message: '请填写计划编号', trigger: 'blur' },
					{ validator: validatePlanId, trigger: 'blur' }
				],
				name: [
					{ required: true, message: '请填写计划名称', trigger: 'blur'	}
				],
				tunnelId: [
					{ type: 'number', required: true, message: '请选择所属管廊', trigger: 'change'}
				],
				groupId: [
					{ type: 'number', required: true, message: '请选择责任班组', trigger: 'change' }
				],
				remark: [
					{ required: true, message: '请输入计划描述', trigger: 'blur'}
					],
				inspectTime: [
					{ type: 'array', required: true, message: '请选择巡检月份', trigger: 'change'}
				],
				inspectionWay: [
					{ type: 'number', required: true, message: '巡检方式不能为空', trigger: 'change'}
				],
				inspectionPath: [
					{ type: 'number', required: true, message: '巡检路径不能为空', trigger: 'change'}
				],
				inspectionObject: [
					{ type: 'number', required: true, message: '巡检对象不能为空', trigger: 'change'}
				]
			},
			backStyle: {
				backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",
				position: 'relative',
				backgroundAttachment: 'fixed',
				backgroundSize: 'cover',
				minHeight: '100%',
				paddingTop: '10px'
			},
			inspectWay: [],
			inspectPath: [],
			inspectObject: []
		};
    },
    watch: {
		'currMonth': function (curVal, oldVal) {
			if(curVal) {
				this.$refs.calender.cancelActive();
				var week = [];
				for (let index = 0; index < this.weekDay.length; index++) {
					if (this.weekDay[index].value) {
					this.weekDay[index].value = false
					}
				}
			}
		}
    },
    mounted() {
      	this.pageType = this.$route.params.type;
		//从数据库读取所属管廊select的option选项
		TunnelService.getTunnels().then(
			result => {
				this.tunnels = result
			},
			error => {
				this.Log.info(error)
			}
		)
		this.getChooseMonth()
		this.getSessionUserName()
		//获取审批人
		PatrolService.getApprover().then(
			result => {
				this.approver = result[0]
				this.uploadPlan.approverId = result[0].id
			},
			error => {
				this.Log.info(error)
			}
		)
		//获取责任班组
		GroupServices.getGroups().then(
			result => {
				this.groups = result
			},
			error => {
				this.Log.info(error)
			}
		)
		// 获取巡检方式
		PatrolService.getInspectWay().then(
			result => {
				this.inspectWay = result
			},
			error => {
				this.Log.info(error)
			}
		)
		//获取巡检路径
		PatrolService.getInspectionPath().then(
			result => {
				this.inspectPath = result
			},
			error => {
				this.Log.info(error)
			}
		)
		//获取巡检对象
		PatrolService.getInspectionObjects().then(
			result => {
				this.inspectObject = result
			},
			error => {
				this.Log.info(error)
			}
		)
    },
    methods: {
		//提交巡检计划
		submitPlan(name) {
			this.isDisable = true
			//提交数据
			setTimeout(() => {
				this.isDisable = false
				this.$refs[name].validate((valid) => {
					if (valid) {
						PatrolService.submitPlan(this.uploadPlan).then(
							result => {
								this.$router.push("/UM/patrol/query/" + this.uploadPlan.tunnelId);
							},
							error => {
								this.Log.info(error)
							}
						)
					} else {
						this.$Message.error("请填写正确的巡检计划信息")
					}
				})
			}, 2000)
		},
		//表单重置
		handleReset(name) {
			this.$refs[name].resetFields()
		},

		getChooseMonth() {
			if (this.currMonth == undefined || this.currMonth == null || this.currMonth == '' || this.currYear == undefined ||
			this.currYear == null || this.currYear == '') {
				this.currMonth = new Date().getMonth() + 1
				this.currYear = new Date().getFullYear()
			} else {
				this.currMonth = this.uploadPlan.inspectTime.getMonth() + 1
				this.currYear = this.uploadPlan.inspectTime.getFullYear()
			}
		},
		getDay() {
			this.$refs.calender.cancelActive();
			if(this.intervalMode.interval==0){
				this.$Message.error("间隔天数最小单位为1")
				return false
			}else{
				this.$refs.calender.interVal(this.intervalMode.startDay, this.intervalMode.endDay, this.intervalMode.interval);
			}
			this.$refs.calender.outputHeighLight();
		},
		//日期重置
		defaultBtn() {
			this.$refs.calender.cancelActive();
			this.$refs.calender.outputHeighLight();
		},
		//星期模式
		getWeek() {
			this.$refs.calender.cancelActive();
			var week = [];
			for (let index = 0; index < this.weekDay.length; index++) {
				if (this.weekDay[index].value) week.push(index);
			}
			this.$refs.calender.chooseWeek(week);
			this.$refs.calender.outputHeighLight();
		},
		//拿到选择的yyyy
		getActiveYearText(childValue){
			this.uploadPlan.tasks = [];
			for (var i = 0; i < childValue.length; i++) {
				this.uploadPlan.tasks.push({
					taskTime: childValue[i]
				})
			}
		},
		//拿到选择的yyyy-MM-dd
		getActiveText(childValue) {
			this.uploadPlan.tasks = [];
			for (var i = 0; i < childValue.length; i++) {
				this.uploadPlan.tasks.push({
					taskTime: childValue[i]
				})
			}
		},
		//拿到选择的yyyy-MM
		getActiveMonthText(childValue){
			this.uploadPlan.tasks = [];
			for (var i = 0; i < childValue.length; i++) {
				this.uploadPlan.tasks.push({
					taskTime: childValue[i]
				})
			}
		},
		getSessionUserName: function () {
			this.uploadPlan.requestStaffId = sessionStorage.UMUerId
			this.requestStaffName = sessionStorage.UMUerName
		},
		//返回
		goBack() {
			this.$router.back(-1);
        },
        //添加todoList
        addList(index){
            this.uploadPlan.steps.push(
                { name: '' }
            )
		},
		//删除todoList
        delList(index){
            this.$Modal.confirm({
                title: '删除计划步骤',
                content: '<p>确定要删除这条计划步骤吗</p>',
                onOk: () => {
                    this.uploadPlan.steps.splice(index, 1)
                },
                onCancel: () => {}
            })
		},
		clearInput(name){
			this.uploadPlan.inspectTime = null
			this.$refs.calender.cancelActive();
			this.$refs.getYearChild.cancelActive();
			this.$refs.getMonthChild.cancelActive();
		}
    }
}

</script>

<style scoped>
    .todoLi{
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
	input[type='number'] {
		display: inline-block;
		height: 27px;
		width: 45px;
		line-height: 1.5;
		padding: 4px 7px;
		font-size: 12px;
		border: 1px solid #dddee1;
		border-radius: 4px;
		color: #495060;
		background-color: #fff;
		background-image: none;
		position: relative;
		cursor: text;
		transition: border .2s ease-in-out, background .2s ease-in-out, box-shadow .2s ease-in-out;
		margin-right: 5px;
	}

	.tabBtn {
		line-height: 1.3;
		padding: 4px 7px;
	}

	.ivu-form.ivu-form-label-right {
		margin: 10px auto;
		background: #fff;
		padding: 10px 20px;
	}

	.planContainer {
		width: 80%;
		background: #fff;
		padding: 10px 20px;
		border-radius: 8px;
	}

	.leftContainer {
		float: right;
		margin: 10px 20px 10px;
	}

	.rightContainer {
		margin: 10px 20px 10px;
	}

	.inputStyle {
		display: inline-block;
		width: 100%;
		height: 4vh;
		line-height: 4vh;
		padding: 4px 7px;
		font-size: 12px;
		border: 0.2vmin solid #dddee1;
		border-radius: 4px;
		color: #495060;
		background-color: #fff;
		background-image: none;
		position: relative;
		cursor: text;
		transition: border .2s ease-in-out, background .2s ease-in-out, box-shadow .2s ease-in-out;
	}

	.ivu-select-single .ivu-select-selection,
	.ivu-input {
		height: 4vh;
		line-height: 4vh;
	}

	@media (min-width: 2200px) {
		.ivu-form.ivu-form-label-right {
			width: 50%;
		}

		h2 {
			font-size: 2.4vmin;
		}

		.ivu-form-item>>>.ivu-form-item-label {
			/* width: 15vmin !important; */
			line-height: 4.5vmin;
		}

		.ivu-form-item>>>.ivu-form-item-content {
			/* margin-left: 15vmin !important; */
			line-height: 4.5vmin;
		}

		.ivu-select,
		.ivu-select>>>.ivu-select-selection,
		.ivu-input-wrapper>>>.ivu-input,
		.ivu-date-picker>>>.ivu-input,
		.ivu-select.ivu-select-single>>>.ivu-select-selected-value,
		.ivu-select.ivu-select-single>>>.ivu-select-placeholder {
			height: 4vmin;
			line-height: 4vmin;
			font-size: 1.4vmin;
		}

		.ivu-form-item-content {
			line-height: 6.5vmin;
		}

		.btn {
			margin-left: 20% !important;
		}

		.ivu-input-number,
		.ivu-input-number>>>.ivu-input-number-input {
			height: 4vmin;
			line-height: 4vmin;
			font-size: 1.4vmin;
		}

		input[type='number'] {
			height: 4vmin;
			width: 8vmin;
			font-size: 1.6vmin;
		}

		.inputStyle {
			font-size: 1.4vmin;
		}

		/* 年月时间选择器 */
		.ivu-picker-panel-body>>>.ivu-date-picker-cells {
			width: 18vmin;
		}

		.ivu-date-picker-cells span {
			width: 5.7vmin;
			height: 5.6vmin;
		}

		.ivu-date-picker-cells-month span {
			line-height: 2.8vmin;
		}

		.ivu-date-picker-cells-month span,
		.ivu-date-picker-cells-year span {
			width: 4vmin !important;
			height: 2.8vmin !important;
			line-height: 2.8vmin !important;
			margin: 1vmin 1.5vmin !important;
			border-radius: 0.3vmin !important;
		}

		.ivu-date-picker-header {
			height: 3.2vmin !important;
			line-height: 3.2vmin !important;
		}
	}

</style>
