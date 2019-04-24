<template>
    <div>
        <h1 style="margin-left: 20px;margin-top:10px">变电所信息配置</h1>
        <Row style="margin: 20px;">
            <Col span="6">
                <span>所属管廊：</span>
                <Select v-model="conditions.tunnelId" class="inputWidth">
                    <Option value="null">所有</Option>
                    <Option v-for="item in selectList.tunnels" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span>开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="conditions.startTime">
                </DatePicker>
            </Col>
            <Col span="6">
                <span>结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" class="inputWidth" v-model="conditions.endTime">
                </DatePicker>
            </Col>  
            <Col span="6">
                <Button type="primary" size="small"  icon="ios-search" @click="resetPageSearch">查询</Button>
                <Button type="error" size="small" @click="add">新增变电所</Button> 
            </Col>
        </Row>
        <Table 
            border 
            :columns="powerSubstationColumns" 
            :data="powerSubstationsData" 
            style="margin:20px;"
        ></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
              @on-change="handlePage" show-elevator :style="pageStyle"></Page>
        <Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset('powerSubstationForm')" :mask-closable="false">
            <Form ref="powerSubstationForm" :model="modal.info" :rules="powerSubstationForm" :label-width="120">
                <FormItem label="所属管廊：" prop="tunnelId">
                    <Select v-model="modal.info.tunnelId">
                        <Option v-for="item in selectList.tunnels" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="进线电压对象Id：" prop="invId">
                    <Input v-model="modal.info.invId" @on-change="changeObjId('invId')" placeholder="请输入进线电压对象Id"/>
                    <ul class="chooseObj" v-show="ulShowFlag.invId">
                        <li v-for="item in objs" :value="item" :key="item" @click="replaceInputValue(item,'invId')">{{item}}</li>
                    </ul>
                </FormItem>
                <FormItem label="进线电流对象Id：" prop="inaId">
                    <Input v-model="modal.info.inaId" @on-change="changeObjId('inaId')" placeholder="请输入进线电流对象Id"/>
                    <ul class="chooseObj" v-show="ulShowFlag.inaId">
                        <li v-for="item in objs" :value="item" :key="item" @click="replaceInputValue(item,'inaId')">{{item}}</li>
                    </ul>
                </FormItem>
                <FormItem label="出线电压对象Id：" prop="outvId">
                    <Input v-model="modal.info.outvId" @on-change="changeObjId('outvId')" placeholder="请输入出线电压对象Id"/>
                    <ul class="chooseObj" v-show="ulShowFlag.outvId">
                        <li v-for="item in objs" :value="item" :key="item" @click="replaceInputValue(item,'outvId')">{{item}}</li>
                    </ul>
                </FormItem>
                <FormItem label="出线电流对象Id：" prop="outaId">
                    <Input v-model="modal.info.outaId" @on-change="changeObjId('outaId')" placeholder="请输入出线电流对象Id"/>
                    <ul class="chooseObj" v-show="ulShowFlag.outaId">
                        <li v-for="item in objs" :value="item" :key="item" @click="replaceInputValue(item,'outaId')">{{item}}</li>
                    </ul>
                </FormItem>
                <FormItem label="实时功率：" prop="powerId">
                    <Input v-model="modal.info.powerId" @on-change="changeObjId('powerId')" placeholder="请输入实时功率对象Id"/>
                    <ul class="chooseObj" v-show="ulShowFlag.powerId">
                        <li v-for="item in objs" :value="item" :key="item" @click="replaceInputValue(item,'powerId')">{{item}}</li>
                    </ul>
                </FormItem>
                <FormItem label="供电半径：" prop="eleRadius">
                    <Input v-model="modal.info.eleRadius" placeholder="请输入供电半径"/>
                </FormItem>
                <FormItem label="功率因数：" prop="powerFactor">
                    <Input v-model="modal.info.powerFactor" placeholder="请输入功率因数"/>
                </FormItem>
                <FormItem label="功率补偿因数：" prop="compensationFactor">
                    <Input v-model="modal.info.compensationFactor" placeholder="请输入功率补偿因数"/>
                </FormItem>
                 <FormItem label="位置信息：" prop="position">
                    <Input v-model="modal.info.position" placeholder="请输入位置信息"/>
                </FormItem>
            </Form>   
            <div slot="footer">
                <Button type="primary" @click="save('powerSubstationForm')">确定</Button>
            </div> 
        </Modal>
    </div>
</template>

<script>
import { TunnelService } from '../../../services/tunnelService';
import PowerSubstationService from '../../../services/powerSubstationService'
import { EquipmentService } from "../../../services/equipmentService"

    export default {
        data() {
            const validateNumber = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('不能为空'));
                } else {
                    if (isNaN(value)) {
                        callback(new Error('请输入数字'))
                    } else {
                        callback()
                    }  
                }
            };
            return {
                page:{
                    pageNum: 1,
                    pageSize: 10,
                    pageTotal: 0 
                },
                pageStyle: {
                    position: 'absolute',
                    bottom: '35px',
                    right: '40px'
                },
                powerSubstationColumns: [
                    {
                        type: 'index',
                        align: 'center',
                        width: 100
                    },
                    {
                        title: '所属管廊',
                        key: 'tunnel',
                        align: 'center',
                        render: (h,params)=>{
                            return h('div',params.row.tunnel.name)
                        }
                    },
                    {
                        title: '进线电压监测对象',
                        key: 'invId',
                        align: 'center',
                        // render: (h,params) => {
                        //     return h('div',params.row.inv.name)
                        // }
                    },
                    {
                        title: '进线电流监测对象',
                        key: 'inaId',
                        align: 'center',
                        // render: (h,params) => {
                        //     return h('div',params.row.ina.name)
                        // }
                    },
                    {
                        title: '出线电压监测对象',
                        key: 'outvId',
                        align: 'center',
                        // render: (h,params) => {
                        //     return h('div',params.row.outv.name)
                        // }
                    },
                    {
                        title: '出线电流监测对象',
                        key: 'outaId',
                        align: 'center',
                        // render: (h,params) => {
                        //     return h('div',params.row.outa.name)
                        // }
                    },
                    {
                        title: '实时功率监测对象',
                        key: 'powerId',
                        align: 'center',
                        // render: (h,params) => {
                        //     return h('div',params.row.power.name)
                        // }
                    },
                    {
                        title: '供电半径',
                        key: 'eleRadius',
                        align: 'center',
                    },
                    {
                        title: '功率因数',
                        key: 'powerFactor',
                        align: 'center',
                    },
                    {
                        title: '功率补偿因数',
                        key: 'compensationFactor',
                        align: 'center',
                    },
                    {
                        title: '位置信息',
                        key: 'position',
                        align: 'center',
                    },
                    {
                        title: '创建时间',
                        key: 'time',
                        align: 'center',
                        render: (h,params)=>{
                            let crtTime = params.row.time
                            let time = crtTime == null ? '' : new Date(crtTime).format('yyyy-MM-dd hh:mm:ss')
                            return h('div',time)
                        }
                    },
                    {
                        title: '操作',
                        align: 'center',
                        render: (h,params) => {
                            return h('div',[
                                h('Button',{
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    style: {
                                        marginRight: "5px"
                                    },
                                    on: {
                                        click: () => {
                                            this.edit(params.row.id);
                                        }
                                    }
                                },'修改'),
                                h('Button',{
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    on: {
                                        click: () => {
                                            this.delete(params.row.id);
                                        }
                                    }
                                },'删除')
                            ])
                        }
                    }
                ],
                powerSubstationsData: [],
                modal: {
                    isShow: false,
                    title: '添加权限',
                    info: {
                        id: null,
                        tunnelId: null,
                        invId: null,
                        inaId: null,
                        outvId: null,
                        outaId: null,
                        powerId: null,
                        eleRadius: null,
                        powerFactor: null,
                        compensationFactor: null,
                        position: null
                    },
                    type: 'add'
                },
                powerSubstationForm: {
                    tunnelId: [
                        { required: true, message: '请选择所属管廊', trigger: 'blur' }
                    ],
                    invId: [
                        { required: true, message: '请输入进线电压监测对象', trigger: 'blur' }
                    ],
                    inaId: [
                        { required: true, message: '请输入进线电流监测对象', trigger: 'blur' }
                    ],
                    outvId: [
                        { required: true, message: '请输入出线电压监测对象', trigger: 'blur' }
                    ],
                    outaId: [
                        { required: true, message: '请输入出线电流监测对象', trigger: 'blur' }
                    ],
                    powerId: [
                        { required: true, message: '请输入实时功率', trigger: 'blur' }
                    ],
                    eleRadius: [
                        { required: true, message: '请输入供电半径', trigger: 'blur' },
                        { validator: validateNumber, trigger: 'blur' }
                    ],
                    powerFactor: [
                        { required: true, message: '请输入功率因数', trigger: 'blur' },
                        { validator: validateNumber, trigger: 'blur' }
                    ],
                    compensationFactor: [
                        {  required: true, message: '请输入功率补偿因数', trigger: 'blur' },
                        { validator: validateNumber, trigger: 'blur' }
                    ],
                    position: [
                        { required: true, message: '请输入位置', trigger: 'blur' }
                    ]
                },
                conditions: {
                    tunnelId: null,
                    startTime: null,
                    endTime: null
                },
                selectList: {
                    tunnels: []
                },
                ulShowFlag:{
                    invId: false,
                    inaId: false,
                    outvId: false,
                    outaId: false,
                    powerId: false
                },
                objs: []
            };
        },
        components: {
           
        },
        mounted() {
            this.init()
            this.resetPageSearch()
        },
        methods: {
            init(){
                TunnelService.getTunnels().then(
                    res=>{
                        this.selectList.tunnels = res
                    },
                    error=>{
                        this.Log.info(error)
                    }
                )
            },
            search(){
                let params = {
                    pageNum: this.page.pageNum,
                    pageSize: this.page.pageSize,
                    tunnelId: this.conditions.tunnelId === 'null' ? null : this.conditions.tunnelId,
                    startTime: new Date(this.conditions.startTime).getTime(),
                    endTime: new Date(this.conditions.endTime).getTime()
                }
                let _this = this;
                PowerSubstationService.substationDatagrid(params).then(
                    res=>{
                        _this.page.pageTotal = res.total;
                        _this.powerSubstationsData = res.list;
                    }
                )
            },
            add(){
               this.modal.isShow = true;
               this.modal.type = 'add';
               this.modal.title = '添加变电所信息'
            },
            edit(id){
                PowerSubstationService.substationDetailById(id).then(
                    res=>{
                        this.modal.title = '修改变电所信息';
                        this.modal.isShow = true;
                        this.modal.info = res;
                        for(let item in this.modal.info){
                            if(item === 'eleRadius' || item === 'powerFactor' || item === 'compensationFactor'){
                                this.modal.info[item] =  this.modal.info[item] + ''
                            }
                        }
                        this.modal.type = 'edit';
                    },
                    error=>{
                        this.$Message.error('查询失败');
                    }
                )
            },
            save(name){
                switch(this.modal.type){
                    case 'add':
                        PowerSubstationService.addSubstation(this.modal.info).then(
                            res=>{
                                this.$Message.success('添加成功');
                                this.handleReset(name);
                                this.$nextTick(()=>{
                                    this.resetPageSearch()
                                })
                            },
                            error=>{
                                this.$Message.error('添加失败');
                            }
                        )
                        break;
                    case 'edit':
                        PowerSubstationService.editSubstation(this.modal.info).then(
                            res=>{
                                this.$Message.success('修改成功');
                                this.handleReset(name);
                                this.$nextTick(()=>{
                                    this.resetPageSearch()
                                })
                            },
                            error=>{
                                this.$Message.error('修改失败');
                            }
                        )
                        break;
                }
                this.modal.isShow = false;
            },
            handlePage(value) {
                this.page.pageNum = value;
                this.search();
            },
            delete(id){
                this.$Modal.confirm({
                    title: '变电所信息管理',
                    content: '<p>确定删除这条变电所信息吗</p>',
                    onOk: () => {
                        // console.log(id)
                        PowerSubstationService.deleteSubstation(id).then(
                            res=>{
                                this.$Message.info('删除成功');
                                this.resetPageSearch();
                            },
                            error=>{
                                this.$Message.error('删除失败');
                            }
                        )
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            },
            resetPageSearch(){
                this.page.pageNum = 1;
                this.search();
            },
            changeObjId(type){
                let objId = this.modal.info[type]
                this.ulShowFlag[type] = true 
               
                EquipmentService.changeObjId(objId).then(
                    result => {
                        this.objs = result
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
            },
            replaceInputValue(id,type){
                this.modal.info[type] = id
                this.ulShowFlag[type] = false
            }
        }
    };
</script>

<style scoped>
    .inputWidth{
        width: 60%;
    }
    .chooseObj{
        width: 19vw;
        max-height: 8.1vh;
        position: relative;
        border: 1px solid #cccccc;
        border-radius: 4px;
        overflow-y: auto;
    }
    .chooseObj:before, .chooseObj:after{
        width: 0vw;
        height: 0vh;
        border: transparent solid;
        position: absolute;
        bottom: 100%;
        content: ''
    }
    .chooseObj:before{
        border-width: 10px;
        border-bottom-color: #cccccc;
        left: 20px;
    }
    .chooseObj:after{
        border-width: 8px;
        border-bottom-color: #ffffff;
        left: 22px;
    }
    .chooseObj li{
        list-style: none;
        line-height: 2.7vh;
        padding-left: 1vw;
        cursor: pointer;
    }
    .chooseObj li:hover{
        background: #f3f3f3;
    }
</style>