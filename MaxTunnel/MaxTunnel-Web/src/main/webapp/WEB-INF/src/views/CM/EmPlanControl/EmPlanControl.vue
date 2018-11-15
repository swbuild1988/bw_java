<template>
    <div>
        <!-- 应急预案流程管理 -->
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">应急管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <div>
                    <span class="word63">预案名</span><span>：</span>
                    <Select v-model="researchInfo.processKey" placeholder="请选择预案名" class="inputWidth">
                        <Option value=null>不限</Option>
                        <Option v-for="item in planEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </div>
            </Col>
            <Col span="6">
                <div>
                    <span class="word65">流程节点值</span><span>：</span>
                    <Input v-model="researchInfo.taskKey" placeholder="支持模糊查询" class="inputWidth"/>
                </div>
            </Col>
            <Col span="6">
                <div>
                    <span class="word65">流程节点名</span><span>：</span>
                    <Input v-model="researchInfo.taskName" placeholder="支持模糊查询" class="inputWidth"/>
                </div>
            </Col>
            <Col span="6">
                <Button type="primary" size="small"  icon="ios-search" @click="research()">查询</Button>
                <Button type="error" size="small" @click="addNewEmPlan()">新增流程节点</Button> 
                <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col> 
        </Row>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <div>
                    <span>应急目标—值</span><span>：</span>
                    <Select v-model="researchInfo.targetKey" placeholder="请选择应急目标值" class="inputWidth">
                        <Option value=null>不限</Option>
                        <Option v-for="item in targetEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </div>
            </Col>
            <Col span="6">
                <div>
                    <span>应急行为—值</span><span>：</span>
                    <Select v-model="researchInfo.actionKey" placeholder="请选择应急行为值" class="inputWidth">
                        <Option value=null>不限</Option>
                        <Option v-for="item in actionEnums" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </div>
            </Col>
            <Col span="6">
                <div>
                    <span>应急结束—值</span><span>：</span>
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
                <span class="word64">开始时间</span><span>：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="researchInfo.startTime">
                </DatePicker>
                </Col>
            <Col span="6">
                <span class="word64">结束时间</span><span>：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" class="inputWidth" v-model="researchInfo.endTime">
                </DatePicker>
            </Col>   
        </Row>
        <div style="margin:20px;">
            <Table border ref="selection" :columns="columns7" :data="data6" @on-selection-change="startdelete"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
            @on-change="handlePage" show-elevator class="pageStyle"></Page>
        </div>
        <div>
            <em-plan-module v-bind="addEmPlanInfo" v-on:listenToAdd="saveEmPlan"></em-plan-module>
        </div>
    </div>
</template>

<script>
import EmPlanModule from '../../CM/EmPlanControl/EmPlanModule.vue'
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
                    title: '流程节点值',
                    key: 'taskKey',
                    align: 'center'
                },
                {
                    title: '流程节点名',
                    key: 'taskName',
                    align: 'center'
                },
                {
                    title: '应急目标',
                    key: 'targetName',
                    align: 'center'
                },
                {
                    title: '应急目标-值',
                    key: 'targetKey',
                    align: 'center'
                },
                {
                    title: '应急行为',
                    key: 'actionName',
                    align: 'center'
                },
                {
                    title: '应急行为-值',
                    key: 'actionKey',
                    align: 'center'
                },
                {
                    title: '应急结束',
                    key: 'finishName',
                    align: 'center'
                },
                {
                    title: '应急结束-值',
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
                                        this.editEmPlan(params.index)
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
            addEmPlanInfo:{
                show:{state: false},
                addInfo:{}
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
        },
        newParams(){
            // 新增
            let param = {
                processKey :this.formValidate.processKey,
                taskKey: this.formValidate.taskKey,
                taskName: this.formValidate.taskName,
                targetKey: this.formValidate.targetKey,
                targetValue: this.formValidate.targetValue,
                actionKey: this.formValidate.actionKey,
                actionValue: this.formValidate.actionValue,
                finishKey: this.formValidate.finishKey,
                finishValue: this.formValidate.finishValue,
                isFinished: this.formValidate.isFinished
            };
            return Object.assign({}, param);
        }
    },
    mounted(){
        this.getTargetEnumList();
        this.getActionEnumList();
        this.getFinishEnumList();
        this.getPlanEnumList();
        this.showTable();
    },
    methods:{
        showTable(){
            this.axios.post('/emplans/datagrid',(this.researches)).then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.data6 = data.list;
                    for( let index in data.list){
                        this.data6[index].crtTime = new Date(data.list[index].crtTime).format('yyyy-MM-dd hh:mm:s');
                    }
                    this.page.pageTotal = data.total;
                }
            })
        },
        getTargetEnumList(){      //获取应急目标对象列表
            this.axios.get('/em/target-enums').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.targetEnums = data;
                }
            })
        },
        getActionEnumList(){       //获取应急行为
            this.axios.get('/em/action-enums').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.actionEnums = data;
                }
            })
        },
        getFinishEnumList(){        //获取应急结束类型
            this.axios.get('/em/finish-enums').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.finishEnums = data;
                }
            })
        },
        getPlanEnumList(){
            this.axios.get('/plan-enums').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.planEnums = data;
                }
            })
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        addNewEmPlan(){
            this.addEmPlanInfo.show.state = !this.addEmPlanInfo.show.state;
        },
        saveEmPlan(_data){
            this.formValidate = _data;
            this.axios.post('/emplans',(this.newParams)).then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.page.pageTotal = data.total;
                    this.$Message.success('添加成功！');
                    this.addEmPlanInfo.show.state = !this.addEmPlanInfo.show.state;
                    this.showTable();
                }
            })
        },
        editEmPlan(index){

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
                    this.axios.delete('/emplans/batch/' + ids).then(res =>{
                        let {code,data} = res.data;
                        if (code == 200){
                            this.$Message.info('已删除');
                            this.deleteShow = false;
                            this.showTable();
                        }
                    })    
                },
                onCancel: () => {
                    this.$Message.info('已取消操作');
                    this.showTable();
                }
            });
        },
        research(){
            this.showTable();
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
    letter-spacing: 1.5em;
    margin-right: -1.5em
}
.word64{
    letter-spacing: 0.67em;
    margin-right: -0.67em;
}
.word65{
    letter-spacing: 0.25em;
    margin-right: -0.25em;
}
</style>


