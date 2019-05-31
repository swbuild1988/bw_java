<template>
	<div>
		<Form class="formBG" ref="uploadPlan" :model="uploadPlan" :label-width="120" :rules="ruleValidate" @submit.native.prevent>
			<h2 class="formTitle" style="color: white">制定巡检计划</h2>
			<Row>
				<Col span="12" class="leftForm">
					<div class="planContainer leftContainer" prop="planId">
						<FormItem label="计划编号：" prop="planId">
							<Input v-model="uploadPlan.planId"></Input>
						</FormItem>
						<FormItem label="计划名称：" prop="name">
							<Input v-model="uploadPlan.name" placeholder="请输入计划名称"></Input>
						</FormItem>
						<FormItem label="所属管廊：" prop="tunnelId">
							<Select v-model="uploadPlan.tunnelId" @on-change="getInfo()">
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
							<Select v-model="uploadPlan.inspectionPath" @on-change="chooseInspectionPath()">
								<Option v-for="(item, index) in inspectPath" :value="item.val" :key="index">{{item.key}}</Option>
							</Select>
						</FormItem>
						<FormItem label="巡检路径详情：" v-if="uploadPlan.inspectionPath==1||uploadPlan.inspectionPath==2">
							<Input v-if="uploadPlan.inspectionPath==1" :value="uploadPlan.choosedStoreName" readonly></Input>
							<Input v-if="uploadPlan.inspectionPath==2" :value="uploadPlan.pathConName" readonly></Input>
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
									<span style="color: #fff">{{index+1}}、</span>
									<input class="todoEidt" v-model="item.name" placeholder="请输入要执行的计划步骤" />
									<div class="todoButton delBtn" :disabled="index==0" @click="delList(index)">删除</div>
									<div class="todoButton showBtn" @click="addList(index)">添加</div>
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
						<Tabs class="moduleStyle" v-model="uploadPlan.type" @on-click="clearInput" :animated="false">
							<TabPane label="年份" name="name1" style="padding-left: 1vmin;">
								<FormItem label="巡检年份：">
									<Input placeholder="请选择巡检年份" style="width: 100%;"></Input>
									<yearCalender ref="getYearChild" v-on:getYearChild="getActiveYearText" :tasks="uploadPlan.tasks" :type="uploadPlan.type"></yearCalender>
								</FormItem>
							</TabPane>
							<TabPane label="月份" name="name2" style="padding-left: 1vmin;">
								<FormItem label="巡检月份：">
									<Input placeholder="请选择巡检月份" style="width: 100%"></Input>
									<monthCalender ref="getMonthChild" v-on:getMonthChild="getActiveMonthText" :tasks="uploadPlan.tasks" :type="uploadPlan.type"></monthCalender>
								</FormItem>
							</TabPane>
							<TabPane label="日期" name="name3" style="padding-left: 1vmin;">
								<FormItem label="巡检月份：">
									<DatePicker type="month" placeholder="请选择巡检月份" style="width: 100%" v-model="uploadPlan.inspectTime"
									@on-change="getChooseMonth()"></DatePicker>
								</FormItem>
								<FormItem label="巡检日期安排：">
									<Tabs class="moduleStyle" v-model="uploadPlan.dateType" @click="chooseDate">
										<TabPane label="间隔模式" name="intervalType">
											<div>
												开始日期：
												<input v-model="uploadPlan.intervalMode.startDay" type="number" min="1" max="31">
												结束日期：<input v-model="uploadPlan.intervalMode.endDay" type="number" min="1" max="31">
												间隔天数：
												<input v-model="uploadPlan.intervalMode.interval" type="number" min="1" max="31">
												<Button type="default" @click="getDay" class="tabBtn">确定</Button>
												<Button type="default" @click="defaultBtn" class="tabBtn">重置</Button>
											</div>
										</TabPane>
										<TabPane label="星期模式" name="weekType">
											<Checkbox v-for="(item, index) in weekDay" v-model="item.value" :value='item.value' :key='index'
											@on-change="getWeek()">{{item.name}}</Checkbox>
										</TabPane>
										<TabPane label="自定义" name="customType" style="text-align: center">
											请根据您的需求自由选择日期
										</TabPane>
									</Tabs>
									<calender ref="calender" :currentMonth="currMonth" :currentYear="currYear" v-on:childByValue="getActiveText" :tasks="uploadPlan.tasks" :type="uploadPlan.type"
									style="margin: 10px auto;margin-bottom: 0px;"></calender>
								</FormItem>
							</TabPane>
						</Tabs>
					</div>
				</Col>
			</Row>
			<FormItem style="text-align: center;margin-bottom: 0px">
				<div class="planBtn backBtn" @click="goBack()">返回</div>
				<div class="planBtn submitBtn" @click="submitPlan('uploadPlan')" :disabled="isDisable">提交</div>
				<div class="planBtn importantBtn" @click="importTemplate">导入模板</div>
				<div class="planBtn createBtn" @click="createTemplate">生成模板</div>
			</FormItem>
		</Form>
		<!-- 模板名称提交 -->
		<Modal
			v-model="isShowCreateTemp"
			title="模板"
		>
			<Form ref="createTemp" :model="createTemp" :rules="ruleTemp" :label-width="100">
				<FormItem label="模板名称：" prop="name">
					<Input v-model="createTemp.name"></Input>
				</FormItem>
			</Form>
			<div slot="footer">
				<Button type="primary" @click="submitTemp">提交</Button>
			</div>
		</Modal>
		<!-- 模板选择 -->
		<Modal
			v-model="isImportTemp"
			title="选择模板"
		>
			<Row style="margin-bottom: 2vmin;">
				<Col span="12">
					模板名称：
					<Input style="width: 60%" v-model="tempCondition.name"></Input>
				</Col>
				<Col span="4">
					<Button type="primary" @click="seachTemp">确定</Button>
				</Col>
			</Row>
			<Table :columns="tempColumns" :data="tempData"></Table>
			<div slot="footer">
				<Button type="primary" @click="chooseTemp">确定</Button>
			</div>
		</Modal>
		<!-- store -->
		<Modal
			v-model="isShowStore"
			title="选择舱室"
		>
			<RadioGroup v-model="choosedStoreId">
				<Radio v-for="item in stores" :key="item.id" :label="item.id" class="radioBox">{{item.name}}
				</Radio>
			</RadioGroup>
			<div slot="footer">
				<Button type="primary" @click="submitChoosedStore">确定</Button>
			</div>
		</Modal>
		<!-- areas -->
		<Modal
			v-model="isShowArea"
			title="选择区间"
		>
		<div style="text-align: center;">
			<Select v-model="choosedArea.startArea" class="choosedAreaId">
				<Option v-for="(item, index) in areas" :key="index" :value="item.id">{{item.name}}</Option>
			</Select>
			<span>至</span>	
			<Select v-model="choosedArea.endArea" class="choosedAreaId">
				<Option v-for="(item, index) in areas" :key="index" :value="item.id">{{item.name}}</Option>
			</Select>
		</div>
			<div slot="footer">
				<Button type="primary" @click="submitChoosedArea">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
import calender from "../../../Common/calender.vue";
import yearCalender from "../../../Common/yearCalender.vue"
import monthCalender from "../../../Common/monthCalender.vue"
import types from "../../../../../static/Enum.json";
import { PatrolService } from '../../../../services/patrolService'
import { GroupServices } from '../../../../services/groupService.js'
import { TunnelService } from '../../../../services/tunnelService'
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
                name: null,
				groupId: null,
				inspectionWay: null,
				inspectionPath: null,
				choosedStoreName: null,
				pathConName: null,
				inspectionObject: null,
                inspectTime: null,
                tasks: [],
                approverId: 1,
                tunnelId: null,
                requestStaffId: null,
                remark: '',
                steps: [
                    { name: '' }
				],
				type: 'name1',
				dateType: 'intervalType',
				intervalMode: {
					startDay: 1,
					endDay: 31,
					interval: 3
				}
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
			inspectTimeContainer: [],
			isDisable: false,
			ruleValidate: {
				planId: [
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
			inspectWay: [],
			inspectPath: [],
			inspectObject: [],
			createTemp: {
				name: null
			},
			isShowCreateTemp: false,
			ruleTemp: {
				name: [
					{ required: true, message: '模板名称不能为空', trigger: 'blur' }
				]
			},
			tempCondition:{
				name: null
			},
			isImportTemp: false,
			tempId: null,
			tempColumns: [
				{
					title: '选中',
					align:'center',
					key: 'checkBox',
					width: 60,
					render:(h,params)=>{
						return h('div',[
							h('Checkbox',{
								props:{
									value:params.row.checkBox
								},
								on:{
									'on-change':(e)=>{
										this.tempData.forEach((items)=>{      //先取消所有对象的勾选，checkBox设置为false
											this.$set(items,'checkBox',false)
										});
										this.tempData[params.index].checkBox = e;  //再将勾选的对象的checkBox设置为true
										this.tempId = params.row.id
									}
								}
							})
						])
					}
				},
				{
					type: 'index',
					width: 60,
					align: 'center'
				},
				{
					title: '模板名称',
					key: 'name',
					align: 'center'
				},
				{
					title: '模板制作人',
					key: 'staffName',
					align: 'center'
				},
				{
					title: '操作',
					align: 'center',
					render: (h, params) => {
						return h('Button', {
							props: {
								type: "error",
								size: "small"
							},
							on: {
								click: () => {
									this.del(params.row.id);
								}
							}
						}, '删除')
					}
				}
			],
			tempData: [],
			isShowStore: false,
			isShowArea: false,
			areas: [],
			stores: [],
			choosedStoreId: null,
			choosedArea: {
				startArea: null,
				endArea: null
			},
			pathCon: null
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
						var otherIds = ''
						if(this.choosedStoreId==null&&this.pathCon!=null){
							otherIds = this.pathCon
						}else if(this.choosedStoreId!=null&&this.pathCon==null){
							otherIds = this.choosedStoreId.toString()
						}
						let params = {
							planId: this.uploadPlan.planId,
							name: this.uploadPlan.name,
							groupId: this.uploadPlan.groupId,
							inspectionWay: this.uploadPlan.inspectionWay,
							inspectionPath: this.uploadPlan.inspectionPath,
							inspectionObject: this.uploadPlan.inspectionObject,
							inspectTime: this.uploadPlan.inspectTime,
							tasks: this.uploadPlan.tasks,
							approverId: 1,
							tunnelId: this.uploadPlan.tunnelId,
							requestStaffId: this.uploadPlan.requestStaffId,
							remark: this.uploadPlan.remark,
							steps: this.uploadPlan.steps,
							otherIds: otherIds
						}
						PatrolService.submitPlan(params).then(
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
			if(this.uploadPlan.intervalMode.interval==0){
				this.$Message.error("间隔天数最小单位为1")
				return false
			}else{
				this.$refs.calender.interVal(this.uploadPlan.intervalMode.startDay, this.uploadPlan.intervalMode.endDay, this.uploadPlan.intervalMode.interval);
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
		},
		chooseDate(name){
		},
		//生成模板
		createTemplate(){
			this.isShowCreateTemp = true
		},
		//模板提交
		submitTemp(){
			let params = {
				name: this.createTemp.name,
				info: this.uploadPlan
			}
			PatrolService.submitTemp(params).then(
				result => {
					this.isShowCreateTemp = false
					this.$Message.success("模板保存成功！")
				},
				error => {
					this.$Message.error('模板保存失败！')
				}
			)
		},
		//导入模板
		importTemplate(){
			this.isImportTemp = true
			this.seachTemp()
		},
		seachTemp(){
			let params = {
				name: this.tempCondition.name
			}
			PatrolService.importTemp(params).then(
				result => {
					this.tempData = result
				},
				error => {
					this.$Message.error('查询失败！')
				}
			)
		},
		chooseTemp(){
			PatrolService.getTempInfo(this.tempId).then(
				result => {
					this.uploadPlan = JSON.parse(result.info)
					this.isImportTemp = false
					this.uploadPlan.planId = new Date().getTime()
					if(this.uploadPlan.type=='name1'){

					}else if(this.uploadPlan.type == 'name2'){
						
					}else if(this.uploadPlan.type == 'name3'){
						this.currYear = new Date(this.uploadPlan.inspectTime).getFullYear()
						this.currMonth = new Date(this.uploadPlan.inspectTime).getMonth()+1
						this.uploadPlan.inspectTime = new Date(this.currYear, this.currMonth-1)
						// 间隔模式
						if(this.uploadPlan.dateType=='intervalType'){
						}else if(this.uploadPlan.dateType=='weekType'){
							// 星期模式
							for(let i = 0; i<this.uploadPlan.tasks.length; i++){
								// if(new Date(this.uploadPlan.tasks[0].taskTime).getDay()){
									for(let k = 0; k < this.weekDay.length; k++ ){
										if(new Date(this.uploadPlan.tasks[0].taskTime).getDay()==k){
											this.weekDay[k].value = true
										}
										this.getWeek()
									}
								// }
							}
							
						}else{
							// 自定义
						}
					}
				},
				error => {
					this.Log.info(error)
				}
			)
		},
		//获取area与store
		getInfo(){
			if(this.uploadPlan.tunnelId!=null){
				//获取areas
				TunnelService.getAreasByTunnelId(this.uploadPlan.tunnelId).then(
					result => {
						this.areas = result
					},
					error => {
						this.Log.info(error)
					}
				)
				// 获取stores
				TunnelService.getStoresByTunnelId(this.uploadPlan.tunnelId).then(
					result => {
						this.stores = result
					},
					error => {
						this.Log.info(error)
					}
				)
			}
		},
		//提交选择store
		submitChoosedStore(){
			this.isShowStore = false
			this.getChoosedRadio()
		},
		//提交选择area
		submitChoosedArea(){
			var len = this.choosedArea.endArea - this.choosedArea.startArea
			this.pathCon = ''
			for(let i = 0; i<=len; i++){
				let temp = this.choosedArea.startArea + i
				this.pathCon += temp + ','
			}	
			this.pathCon = this.pathCon.substring(0, this.pathCon.length-1)
			this.isShowArea = false
			this.getAreasInfo()
		},
		chooseInspectionPath(){
			//按舱室
			if(this.uploadPlan.inspectionPath==1){
				this.isShowStore = true
				this.choosedStoreId = null
			}else if(this.uploadPlan.inspectionPath==2){
				this.isShowArea = true
				this.choosedArea.startArea = null
				this.choosedArea.endArea = null
			}
		},
		// 删除模板
		del(id){
			this.$Modal.confirm({
                title: '计划模板管理',
                content: '<p>确定删除该计划模板吗</p>',
                onOk: () => {
					PatrolService.delTemplate(id).then(
						result => {
							this.$Message.success('删除成功')
							this.seachTemp()
						},
                        error => {
                            this.Log.info(error)
                        }
					)
                },
                onCancel: () => {}
            })
		},
		//获取选择的舱室名称
		getChoosedRadio(){
			TunnelService.getStoreInfo(this.choosedStoreId).then(
				result => {
					this.choosedStoreName = result.name
				},
				error => {
					this.Log.info(error)
				}
			)
		},
		//获取选择的area名称
		getAreasInfo(){
			TunnelService.getAreaInfo(this.choosedArea.startArea).then(
				result => {
					this.uploadPlan.pathConName = result.name + '至'
				},
				error => {
					this.Log.info(error)
				}
			)
			TunnelService.getAreaInfo(this.choosedArea.endArea).then(
				result => {
					this.uploadPlan.pathConName += result.name
				},
				error => {
					this.Log.info(error)
				}
			)
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

	.planContainer {
		width: 80%;
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
	.createTempBtn{
		position: absolute;
		top: 2vmin;
		left: 2vmin;
	}

	.radioBox{
		width: 23%;
		display: inline-block;
		line-height: 3.5vh;
	}

	.choosedAreaId{
		display: inline-block;
		width: 30%;
	}

	.formBG{
		background: url("../../../../assets/UM/infoBox.png") no-repeat;
		background-size: 100% 100%;
		padding-top: 3vmin;
		padding-bottom: 3vmin;
	}

	.formBG >>> .ivu-form-item-label{
		color: #fff;
	}

	.formBG >>> .ivu-form-item-required .ivu-form-item-label:before, .formBG >>>.ivu-form-item-label:before {
		color: #00fff6;
		content: '★';
		display: inline-block;
		margin-right: 4px;
		line-height: 1;
		font-family: SimSun;
		font-size: 12px;
	}

	.detailsBtn{
		color: #fff;
		border-radius: 0.5vmin;
		line-height: 3.5vmin;
		text-align: center;
		width: 5vmin;
	}
	.showBtn{
		background: linear-gradient(to top right, #2734e1, #b195ed)
	}
	.delBtn{
		background: linear-gradient(to top right, #f61a1a, #fa8785);
		margin-right: 5px;
	}
	.todoButton{
		color: #fff;
		border-radius: 0.5vmin;
		line-height: 3.5vmin;
		text-align: center;
		width: 5vmin;
		cursor: pointer;
	}
	.moduleStyle>>>.ivu-tabs-tab,.moduleStyle{
		color: #fff;
	}
	.moduleStyle>>>.ivu-tabs-nav .ivu-tabs-tab-active{
		color: #2d8cf0
	}
	.planBtn{
		display: inline-block;
		margin-right: 0.8vmin;
		padding: 0.1vmin 2vmin;
		color: #fff;
		border-radius: 4px;
		cursor: pointer;
	}
	.backBtn{
		background: linear-gradient(to right, #f68380, #f61a1a)
	}
	.submitBtn{
		background: linear-gradient(to right, #b195ed, #2734e1)
	}
	.importantBtn{
		background: linear-gradient(to right, #e4e884, #eef61a)
	}
	.createBtn{
		background: linear-gradient(to right, #a7ecd7, #1af6b0)
	}

	@media (min-width: 2200px) {
		h2 {
			font-size: 2.4vmin;
		}

		.ivu-form-item>>>.ivu-form-item-label {
			/* width: 15vmin !important; */
			line-height: 2.5vmin;
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
