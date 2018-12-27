<template>
    <div>
        <!-- 应急预案流程管理 -->
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">应急管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <div>
                    <span class="word64">预案名</span><span>：</span>
                    <Select v-model="researchInfo.processKey" placeholder="请选择预案名" class="inputWidth">
                        <Option value=null>不限</Option>
                        <Option v-for="item in planEnums" :value="item.processKey" :key="item.val">{{item.key}}</Option>
                    </Select>
                </div>
            </Col>
            <Col span="6">
                <div>
                    <span>BPMN节点ID</span><span>：</span>
                    <Input v-model="researchInfo.taskKey" placeholder="支持模糊查询" class="inputWidth"/>
                </div>
            </Col>
            <Col span="6">
                <div>
                    <span>BPMN节点Name</span><span>：</span>
                    <Input v-model="researchInfo.taskName" placeholder="支持模糊查询" class="inputWidth"/>
                </div>
            </Col>
            <Col span="6">
                <Button type="primary" size="small"  icon="ios-search" @click="showTable">查询</Button>
                <Button type="error" size="small" @click="addNewEmPlan()">新增流程节点</Button> 
                <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col> 
        </Row>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <div>
                    <span>目标类型</span><span>：</span>
                    <Select v-model="researchInfo.targetKey" placeholder="请选择应急目标值" class="inputWidth">
                        <Option value=null>不限</Option>
                        <Option v-for="item in targetEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </div>
            </Col>
            <Col span="6">
                <div>
                    <span>应急行为类型</span><span>：</span>
                    <Select v-model="researchInfo.actionKey" placeholder="请选择应急行为值" class="inputWidth">
                        <Option value=null>不限</Option>
                        <Option v-for="item in actionEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </div>
            </Col>
            <Col span="6">
                <div>
                    <span class="word63">应急结束</span><span>：</span>
                    <Select v-model="researchInfo.finishKey" placeholder="请选择应急结束值" class="inputWidth">
                        <Option value=null>不限</Option>
                        <Option v-for="item in finishEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </div>
            </Col>
            <Col span="6">
                <div>
                    <span>是否结束节点：</span>
                    <Select v-model="researchInfo.isFinished" placeholder="请选择是否结束" class="inputWidth">
                        <Option value=null>不限</Option>
                        <Option v-for="item in isFinishEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </div>
            </Col>
        </Row>
        <Row  style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <span>开始时间</span><span>：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="researchInfo.startTime">
                </DatePicker>
                </Col>
            <Col span="6">
                <span class="word64">结束时间</span><span>：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" class="inputWidth" v-model="researchInfo.endTime">
                </DatePicker>
            </Col>   
            <Col span="3" offset="9">
                <Button type="primary" size="large" @click="publish.flag = true">部署预案</Button>
            </Col>
        </Row>
        <div style="margin:20px;">
            <Table border ref="selection" :columns="columns7" :data="data6" @on-selection-change="startdelete"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
            @on-change="handlePage" show-elevator class="pageStyle"></Page>
        </div>
        <div>
            <em-plan-module v-bind="emPlanInfo" v-on:addOrEdit="save"></em-plan-module>
        </div>
        <Modal title="请选择需要部署的预案" v-model="publish.flag" :mask-closable="false" @on-ok="pubConfirm" @on-cancel="cancel">
            <Checkbox v-for="(item,index) in publish.list" v-model="item.isChecked" :key="item.key" class="checkbox">{{ item.key }}</Checkbox>
        </Modal>
    </div>
</template>

<script>
import EmPlanModule from '../../CM/EmPlanControl/EmPlanModule.vue'
import { EnumsService } from '../../../services/enumsService'
import { PlanService } from '../../../services/planService'
export default {
    name: "em-plan-controller",
    data(){
        return {
            targetEnums:[],
            actionEnums:[],  
            finishEnums:[],  
            planEnums:[],
            isFinishEnums:[
                {   val: 0,key: "否" },
                {   val: 1,key: "是" }
            ],  
            researchInfo:{
                processKey: null,
                taskKey: null,
                taskName: null,
                targetKey: null,
                actionKey: null,
                finishKey: null,
                isFinished: null,
                startTime: null,
                endTime: null
            },
            columns7:[
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '流程名',
                    key: 'processKey',
                    align: 'center'
                },
                {
                    title: 'BPMN节点ID',
                    key: 'taskKey',
                    align: 'center'
                },
                {
                    title: 'BPMN节点Name',
                    key: 'taskName',
                    align: 'center'
                },
                {
                    title: '目标类型',
                    key: 'targetName',
                    align: 'center'
                },
                {
                    title: '目标对象',
                    key: 'targetValue',
                    align: 'center'
                },
                {
                    title: '应急行为类型',
                    key: 'actionName',
                    align: 'center'
                },
                {
                    title: '应急行为',
                    key: 'actionValue',
                    align: 'center'
                },
                {
                    title: '应急结束类型',
                    key: 'finishName',
                    align: 'center'
                },
                {
                    title: '应急结束',
                    key: 'finishKey',
                    align: 'center'
                },
                {
                    title: '是否结束节点',
                    key: 'isFinished',
                    align: 'center'
                },
                {
                    title: '创建时间',
                    key: 'crtTime',
                    align: 'center'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: 100,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginLeft: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.editEmPlan(params.row.id)
                                    }
                                }
                            }, '修改')
                        ]);
                    }
                }
            ],
            data6:[],
            formValidate:{
                processKey:null,
                taskKey:null,
                taskName:null,
                targetKey:null,
                targetValue:null,
                actionKey:null,
                actionValue:null,
                finishKey:null,
                finishValue:null,
                isFinished:null
            },
            page:{
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            deleteShow: false,
            deleteSelect:[],
            emPlanInfo:{
                show:{state: false},
                editInfo:{},
                type: null
            },
            publish: {
                flag: false,
                list: []
            }
        }
    },
    computed:{
        researches(){
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                processKey: this.researchInfo.processKey,
                taskKey: this.researchInfo.taskKey,
                taskName: this.researchInfo.taskName,
                targetKey: this.researchInfo.targetKey,
                actionKey: this.researchInfo.actionKey,
                finishKey: this.researchInfo.finishKey,
                isFinished: this.researchInfo.isFinished,
                startTime: new Date(this.researchInfo.startTime).getTime(),
                endTime: new Date(this.researchInfo.endTime).getTime()
            };
            return Object.assign({}, param);
        }
    },
    mounted(){
        this.init()
        this.showTable()
    },
    methods:{
        showTable(){
            let _this = this
            Promise.all([PlanService.emPlansDatagrid(this.researches),EnumsService.getUnitType(),EnumsService.getSwitch()]).then(
                result=>{
                    let plans = result[0].list
                    let unitTypeEnums = result[1]
                    let switchEnums = result[2]
                    _this.data6 = plans
                    _this.data6.forEach(item=>{
                        item.crtTime = new Date(item.crtTime).format('yyyy-MM-dd hh:mm:s')
                        if(item.actionKey == 2){
                            let actionValue = unitTypeEnums.find(a=>{
                                return a.val == item.actionValue
                            })
                            item.actionValue = actionValue.key
                        } else {
                            if(item.actionKey == 3){
                                let actionValue = switchEnums.find(a=>{
                                    return a.val == item.actionValue
                                })
                                item.actionValue = actionValue.key
                            }
                        }
                    })
                    _this.page.pageTotal = result[0].total;
                })
        },
        init(){      
            let _this = this
            //获取应急目标对象列表
            EnumsService.getEmTargetObjs().then(
                result=>{
                    _this.targetEnums = result
                },
                error=>{
                    _this.Log.info(error)
                })
            //获取应急行为
            EnumsService.getEmAction().then(
                result=>{
                    _this.actionEnums = result
                },
                error=>{
                    _this.Log.info(error)
                })
            //获取应急结束类型
            EnumsService.getEmFinishType().then(
                result=>{
                    _this.finishEnums = result
                },
                error=>{
                    _this.Log.info(error)
                })
            EnumsService.getPlanType().then(
                result=>{
                    _this.planEnums = result
                    _this.planEnums.forEach(plan=>{
                        let temp = {}
                        temp.key = plan.key
                        temp.val = plan.val
                        temp.isChecked = false
                        _this.publish.list.push(temp)
                    })
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        addNewEmPlan(){
            this.emPlanInfo.show.state = !this.emPlanInfo.show.state
            this.emPlanInfo.type = 'add'
        },
        save(data){
            let _this = this
            if(this.emPlanInfo.type == 'add'){
                PlanService.addEMPlanNode(data).then(
                result=>{
                    _this.$Message.success('添加成功！')
                    _this.emPlanInfo.show.state = !this.emPlanInfo.show.state
                    _this.showTable()
                },
                error=>{
                    _this.Log.info(error)
                })
            } else {
                PlanService.updateEMPlanNode(data).then(
                    result=>{
                        _this.$Message.success('更新成功！')
                        _this.emPlanInfo.show.state = !this.emPlanInfo.show.state
                        _this.showTable()
                    })
            }
            
        },
        editEmPlan(id){
            let _this = this
            PlanService.getNodeDetailById(id).then(
                result=>{
                    _this.emPlanInfo.editInfo = result
                    _this.emPlanInfo.show.state = !this.emPlanInfo.show.state
                    this.emPlanInfo.type = 'edit'
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        startdelete(selection){
            if(selection.length != 0){
                this.deleteShow = true;
                this.deleteSelect = selection;
            }else{
                this.deleteShow = false;
            }
        },
        alldelete(){
            this.$Modal.confirm({
                title: '删除确认',
                content: '<p>确认要删除选中的信息吗？</p>',
                onOk: () => {
                    let ids=this.deleteSelect[0].id;
                    for(let i=1;i<this.deleteSelect.length;i++){
                        ids += "," + this.deleteSelect[i].id;
                    }
                    let _this = this
                    PlanService.batchDeletePlans(ids).then(
                        result=>{
                            _this.$Message.info('已删除')
                            _this.deleteShow = false
                            _this.showTable()
                        })
                },
                onCancel: () => {
                    this.$Message.info('已取消操作');
                    this.showTable();
                }
            });
        },
        pubConfirm() {
            let vals = []
            this.publish.list.forEach(item=>{
                if(item.isChecked == true){
                    vals.push(item.val)
                }
            })
            this.Log.info(vals.join(','))
            let _this = this
            PlanService.publishPlans(vals.join(',')).then(
                result=>{
                    _this.$Message.success('部署成功！')
                    _this.publish.list.forEach(item=>{
                        item.isChecked = false
                    })
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        cancel() {
            this.publish.list.forEach(item=>{
                item.isChecked = false
            })
        }
    },
    components:{
        EmPlanModule
    }
}
</script>

<style scoped>
.inputWidth{
    width: 60%;
}
.pageStyle{
    text-align: right;
    margin-top: 100px;
    margin-right: 10px;
}
.word63{
    letter-spacing: 1.2em;
    margin-right: -1.2em
}
.word64{
    letter-spacing: 0.67em;
    margin-right: -0.67em;
}
.word65{
    letter-spacing: 0.25em;
    margin-right: -0.25em;
}
.checkbox{
    margin: 10px 20px;
}
</style>


