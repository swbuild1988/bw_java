<template>
    <div class="allDiv">
        <Row class="queryCondition">
            <Col span="6">
                备品名称：
                <Input v-model="conditions.name" style="width: 60%"></Input>
            </Col>
            <Col span="6">
                备品类别：
                <Select v-model="conditions.typeId" style="width: 60%">
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in equipmentTypes" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="6">
                备品型号：
                <Select v-model="conditions.modelId" style="width: 60%">
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in equipmentModels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="6">
                备品状态：
                <Select style="width: 60%" v-model="conditions.isInStorage">
                    <!-- <Option v-for="item in status" :key="item.id" :value="item.isInStorage">{{item.name}}</Option> -->
                    <Option v-for="item in status" :key="item.id" :value="item.key">{{item.val}}</Option>
                </Select>
            </Col>
            <Col span="6">
                开始时间：
                <DatePicker type="datetime" v-model="conditions.startTime" placeholder="请输入开始时间" style="width: 60%"></DatePicker>
            </Col>
            <Col span="6">
                结束时间：
                <DatePicker type="datetime" v-model="conditions.endTime" placeholder="请输入结束时间" style="width: 60%"></DatePicker>
            </Col>
            <Col span="6" offset="6">
                <Button type="primary" icon="ios-search" size="small" @click="showTable()">查询</Button>
            </Col>
        </Row>
        <div class="list">
            <div style="text-align: right">
                <Button type="primary" @click="add({path: '/UM/equipment/addBackUp'})" icon="forward">
                批量入库</Button>
                <Button type="error" icon="reply" @click="outStroage">批量出库
                </Button>
            </div>
            <Row :gutter="8">
                <Col span="6" v-for="(item,index) in equipments" :key="index" style="margin-top: 6px;padding: 5px;">
                    <div :style="{backgroundImage:'url(' + item.imgUrl + ')'}" class="backGoundBox">
                        <div class="topBox">
                            <a class="ivu-modal-close" style="right: 8px;top:0px;"><i class="ivu-icon ivu-icon-ios-close-empty"></i></a>
                            <p class="equipentTitle">{{ item.name }}</p>
                            <div class="imgBox"><img :src="item.imgUrl"></div>
                        </div>
                    </div>
                    <div class="detailsBox">
                        <div>
                            <div class="backUpInfo">设备类型：{{ item.typeName }}</div>
                            <div class="backUpInfo">设备型号：{{ item.modelName }}</div>
                        </div>
                        <div>
                            <div class="backUpInfo">设备状态：<span :style="{color: isColor}">{{ item.status }}</span></div>
                            <div class="backUpInfo">入库时间：{{ item.inTime }}</div>
                        </div>
                    </div>
                    <div class="operation">
                        <Row>
                            <Col span="8" class="operationSee">
                                <Icon type="reply" size=20></Icon>
                                <div class="borrowBox">
                                    <Button class="borrowBtn" @click="show(item.id)" :disabled="isDisabled">取用出库</Button>
                                </div>    
                            </Col>
                            <Col span="8" class="operationEdit">
                                <Icon type="edit" size=19></Icon>
                                <p @click="edit(index)">编辑</p>
                            </Col>
                            <Col span="8" class="operationDel">
                                <Icon type="trash-a" size=20></Icon>
                                <p @click="del(index)">删除</p>
                            </Col>
                        </Row>
                    </div>  
                </col>
            </Row> 
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show-sizer
                placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
                :style='pageStyle'></Page>       
        </div>
        <Modal
            title="批量出库"
            v-model="isOutStorage"
            width="840"
        >
            <Row class="queryCondition">
                <Col span="8">
                    备品名称：
                    <Input type="text" v-model="outStorageConditions.name" style="width: 60%"></Input>
                </Col>
                <Col span="8">
                    备品类别：
                    <Select v-model="outStorageConditions.typeId" style="width: 60%">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in equipmentTypes" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    备品型号：
                    <Select v-model="outStorageConditions.modelId" style="width: 60%">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in equipmentModels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    开始时间：
                    <DatePicker type="datetime" v-model="outStorageConditions.startTime" placeholder="请输入开始时间" style="width: 60%"></DatePicker>
                </Col>
                <Col span="8">
                    结束时间：
                    <DatePicker type="datetime" v-model="outStorageConditions.endTime" placeholder="请输入结束时间" style="width: 60%"></DatePicker>
                </Col>
                <Col span="8" style="text-align: right;padding-right: 43px;">
                    <Button type="primary" @click="showInStorage()" size="small" icon="ios-search">查询</Button>
                </Col>
            </Row>
            <Table stripe border height="330" ref="selection" :columns="outStorageColums"  :data="outStorageData" @on-selection-change="checkTable" @on-selection-all="checkTable"></Table>
            
            <Page :total="OutStoragePage.pageTotal" :current="OutStoragePage.pageNum" :page-size="OutStoragePage.pageSize" show-total show-sizer
              placement="top" @on-change="handlePageStorage" @on-page-size-change='handPageSizeStorage' show-elevator style="margin-top: 10px;text-align: right"></Page>              
            
            <Row style="margin-top: 20px;">
                <Form ref="outStorageSubmitData" :model="outStorageSubmitData" :rules="ruleInline" inline>
                    <Col span="12">
                        <FormItem prop="staffId" style="width: 100%" class="borrower">
                            <span style="letter-spacing: 0.5em;margin-right: -0.5em">取用人</span><span>：</span>
                            <Select style="width: 60%;" v-model="outStorageSubmitData.staffId">
                                <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem prop="outTime" class="outTime" style="width: 100%">
                            <span>取用时间</span><span>：</span>
                            <DatePicker type="datetime"  placeholder="请输入取用时间" style="width: 60%" v-model="outStorageSubmitData.outTime"></DatePicker>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem style="width: 100%" prop="whither">
                            <span>备品去向</span><span>：</span>
                            <Select style="width: 60%" v-model="outStorageSubmitData.whither">
                                <Option v-for="item in whitheres" :key="item.val" :value="item.val">{{item.key}}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem style="width: 100%">
                            <span class="purpose">备注</span><span style="display: inline-block;vertical-align: top">：</span>
                            <Input type="textarea" style="width: 60%;" v-model="outStorageSubmitData.describe"></Input>
                        </FormItem>
                    </Col>
                </Form>
            </Row>

            <div slot="footer">
                <Button type="text" size="large" @click="cancelBatchOut('outStorageSubmitData')">取消</Button>
                <Button type="primary" size="large" @click="BatchOut('outStorageSubmitData')" :disabled="isOutStorageSubmit">确定</Button>
            </div>

        </Modal>
        <Modal
            title="取用出库信息登记"
            v-model="isBorrow"
        >
            <Form ref="borrow" :model="borrow" :rules="borrowValidateRules" :label-width="100" @submit.native.prevent>
                <FormItem label="取用人：" prop="borrower">
                    <Select v-model="borrow.borrower">
                        <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="操作员：">
                    <Input type="text" v-model="borrow.operation"></Input>
                </FormItem>
                <FormItem label="取用目的：">
                    <Input type="textarea" v-model="borrow.borrowPurpose"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" size="large" @click="isBorrow=false">取消</Button>
                <Button type="primary" size="large" @click="confirmBorrow('borrow')">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import types from "../../../../../static/Enum.json";
import { TunnelService } from "../../../../services/tunnelService";
import { EquipmentService } from "../../../../services/equipmentService";
// import func from './vue-temp/vue-editor-bridge';

export default {
    name: "query-backup",
    data() {
        return {
            conditions:{
                name: null,
                typeId: null,
                modelId: null,
                startTime: null,
                endTime: null,
                isInStorage: null,
            },
            status:[
                { key: 0, val: '出库' },
                { key: 1, val: '在库' }
            ],
            isOutStorage: false,
            outStorageColums: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },{
                    type: 'index',
                    width: 60,
                    align: 'center'
                },{
                    title: '备品名称',
                    key: 'name',
                    align: 'center'
                },{
                    title: '备品类别',
                    key: 'typeName',
                    align: 'center'
                },{
                    title: '备品型号',
                    key: 'modelName',
                    align: 'center'
                },{
                    title: '入库时间',
                    key: 'inTime',
                    align: 'center',
                    width: 195
                }
            ],
            outStorageData:[
                { id: 1, backUpName: '温度计1', backUpType: '安全防范类', backUpModel: '温度计', inTime: '2018-11-27 12:30:52',}
            ],
            outStorageConditions:{
                name: null,
                typeId: null,
                modelId: null,
                startTime: null,
                endTime: null,
                //批量出库查询是否在库，否
                status: 1,
            },
            OutStoragePage:{
                pageNum: 1,
                pageSize: 6,
                pageTotal: 0
            },
            outStorageSubmitData:{
                ids: null,
                staffId: null,
                userId: null,
                outTime: null,
                describe: null,
                whither: null
            },
            showOn: true,
            equipments: [],
            tunnels: [],
            tunnelId: 1,
            equipmentTypes: [],
            equipmentModels:[],
            page: {
              pageNum: 1,
              pageSize: 8,
              pageTotal: 0
            },
            pageStyle: {
              position: "absolute",
              bottom: "20px",
              right: "15px"
            },
            staffs:[],
            borrow:{
                borrower: null,
                operation: null,
                borrowPurpose: null
            },
            isBorrow: false,
            borrowValidateRules:{
                borrower:[
                    { type: 'number', required: true, message: '请选择借用人', trigger: 'change'}
                ]
            },
            ruleInline:{
                staffId:[
                    { type: 'number', required: true, message: '请选择借用人', trigger: 'change' }
                ],
                outTime: [
                    { type: 'date', required: true, message: '请选择取用时间', trigger: 'change'}
                ],
                whither: [
                    { type: 'number', required: true, message: '请选择备品去向', trigger: 'change' }
                ]
            },
            isOutStorageSubmit: false,
            isDisabled: false,
            isColor: '#000',
            whitheres: []
        };
    },
    watch: {
        $route: function() {
            //2. $route发生变化时再次赋值planId
            this.tunnelId = this.$route.params.id;
            this.tunnels.forEach(a => {
                if (a.id == this.tunnelId) {
                  this.showTable();
                }
            });
        }
    },
    computed: {
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.conditions.name,
                typeId: this.conditions.typeId,
                modelId: this.conditions.modelId,
                startTime: this.conditions.startTime,
                endTime: this.conditions.endTime,
                status: this.conditions.isInStorage
            };
            return Object.assign({}, param);
        },
        outStorageParams() {
            let param = {
                pageNum: this.OutStoragePage.pageNum,
                pageSize: this.OutStoragePage.pageSize,
                name: this.outStorageConditions.name,
                typeId: this.outStorageConditions.typeId,
                modelId: this.outStorageConditions.modelId,
                startTime: this.outStorageConditions.startTime,
                endTime: this.outStorageConditions.endTime,
                status: 1,
            };
            return Object.assign({},param)
        },
        outStorageSubmitParams(){
            let param = {
                ids: this.outStorageSubmitData.ids,
                staffId: this.outStorageSubmitData.staffId,
                userId: this.outStorageSubmitData.userId,
                outTime: this.outStorageSubmitData.outTime,
                describe: this.outStorageSubmitData.describe,
                whither: this.outStorageSubmitData.whither
            }
            return Object.assign({},param)
        }
    },
    mounted() {
        let _this = this;
        //从数据库读取select的option选项
        TunnelService.getTunnels().then(
            result => {
                _this.tunnels = result;
                _this.selectedTunnel = _this.tunnels.length > 0 ? _this.tunnels[0] : {};
                _this.selectedTunnelName = _this.selectedTunnel.name;
            },
            error => {
                _this.Log.info(error);
            }
        );
        this.showTable();
        //获取借用人列表
        this.axios.get('staffs').then(res=>{
            let{ code,data } = res.data
            if(code==200){
                this.staffs = data
            }
        })
        //获取type
        EquipmentService.getEquipmentTypes().then(
            res=>{
                this.equipmentTypes = res
            },
            error => {
                this.Log.info(error);
            },
        );
        //获取model
        EquipmentService.getEquipmentModels().then(
            res=>{
                this.equipmentModels = res
            },
            error => {
                this.Log.info(error)
            }
        ),
        this.getSessionUserName()
        this.showTable()
        //获取备品去向
        EquipmentService.getWhither().then(
            result => {
                this.whitheres = result
            },
            error => {
                this.Log.info(error)
            }
        )
    },
    methods: {
        // type 1:查看， 2：编辑
        goToMoudle: function(index, type) {
            this.$router.push({
                name: "UMDetailEquipment",
                params: {
                    id: this.equipments[index].id,
                    type: type
                }
            });
        },
        //备品备件分页查询
        showTable() {
            let _this = this;
            EquipmentService.backUpDatagrid(this.params).then(
                result => {
                    for (let index in result.list) {
                        result.list[index].inTime = new Date(result.list[index].inTime).format("yyyy-MM-dd hh:mm:ss");
                        if (result.list[index].imgUrl != null) {
                            result.list[index].imgUrl = _this.ApiUrl + result.list[index].imgUrl.replace(/\\/g, "/");
                        }
                        if(result.list[index].status == false){
                            result.list[index].status = '出库'
                            this.isDisabled = true
                            this.isColor = 'red'
                        }else{
                            result.list[index].status = '在库'
                            this.isDisabled = false;
                            this.isColor = 'green'
                        }
                    }
                    _this.equipments = result.list;
                    _this.page.pageTotal = result.total;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.showTable();
        },
        //批量出库查询在库备件
        showInStorage(){
            EquipmentService.backUpDatagrid(this.outStorageParams).then(
                result => {
                    for(let index in result.list){
                        result.list[index].inTime = new Date(result.list[index].inTime).format("yyyy-MM-dd hh:mm:ss");
                    }
                    this.outStorageData = result.list
                    this.OutStoragePage.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        outStroage() {
            this.showInStorage()
            this.isOutStorage = true
        },
        handlePageStorage(value){
            this.OutStoragePage.pageNum = value;
            this.showInStorage()
        },
        handPageSizeStorage(value){
            this.OutStoragePage.pageSize = value;
            this.showInStorage()
        },
        //删除 
        del(index) {
            let _this = this;
            EquipmentService.equipmentDatagird(this.params).then(
                result => {
                    _this.deleteEquipmentInfo = result.list[index];
                },
                error => {
                    _this.Log.info(error);
                }
            );
            this.$Modal.confirm({
                title: "删除",
                content: "<p>确认删除吗?</p>",
                onOk: () => {
                    let _this = this;
                    EquipmentService.deleteEquipment(this.deleteEquipmentInfo.id).then(
                        result => {
                            _this.showTable();
                        },
                        error => {
                            _this.Log.info(error);
                        }
                    );
                }
            });
        },
        //全选全不选
        // handleSelectAll (status) {
        //     this.$refs.selection.selectAll(status);
        // },
        add(path) {
            this.$router.push(path);
        },
        checkTable(selection){
            this.selection = selection;
            var str = ''
            this.selection.forEach(element=>{
                str += element.id+","
            })
            this.outStorageSubmitData.ids = str.substr(0,str.length-1)
        },
        show(id){
            this.isBorrow = !this.isBorrow
            this.borrow.borrower = null
            this.borrow.operation = null
            this.borrow.borrowPurpose = null
        },
        //取用出库modal框的“确认”按钮
        confirmBorrow(name){
            this.isBorrow = true
            this.$refs[name].validate((valid) => {
                if(valid){
                    this.isBorrow = false
                }else{
                    // this.isBorrow = true
                    this.$Message.error("请填写取用人姓名")
                }
            })
        },
        //读取cookie
        getSessionUserName:function () {
            if(sessionStorage.UMUerName!=null||sessionStorage.UMUerName!=undefined||sessionStorage.UMUerName!=''){
                this.outStorageSubmitData.userId =  parseInt(sessionStorage.UMUerId)
            }
        },
        BatchOut(name){
            this.isOutStorageSubmit = true;
            setTimeout(()=>{
                this.isOutStorageSubmit = false;
                this.$refs[name].validate((valid)=>{
                    if(valid){
                        if(this.outStorageSubmitData.ids!=null){
                            EquipmentService.batchOutBound(this.outStorageSubmitData.ids,this.outStorageSubmitParams).then(
                                result=>{
                                    this.isOutStorage = false
                                    this.showTable()
                                    this.$refs[name].resetFields()
                                    this.outStorageSubmitData.describe = null
                                },
                                error=>{
                                    this.Log.info(error)
                                }
                            )
                        }
                    }else{
                        this.$Message.error("请填写正确的批量出库信息")
                    }
                })
            },2000)
        },
        cancelBatchOut(name){
            this.$refs[name].resetFields()
        }
    },
};
</script>
<style scoped>

.backGoundBox {
    position: relative;
    height: 70px;
    width: 100%;
}

.equipentTitle{
    font-size: 22px;
    text-align: center;
}

.imgBox img {
    height: 40px;
    width: 40px;
    border-radius: 40px;
}

.detailsBox {
    border: 1px solid #ebe8e8;
    padding-top: 20px;
    background: #fff;
}

.operationSee,
.operationEdit,
.operationDel {
    text-align: center;
    background: #fff;
    padding: 5px 0;
    border-bottom: 1px solid #ebe8e8;
}

.operationDel {
    border-right: 1px solid #ebe8e8;
}

.operationSee {
    border-left: 1px solid #ebe8e8;
}

.operationSee p,
.operationEdit p,
.operationDel p {
    cursor: pointer;
}

.backUpInfo{
    display: inline-block;
    width: 49%;
    line-height: 36px;
    padding-left: 5px;
}
.purpose{
    display: inline-block;
    vertical-align: top;
    line-height: 32px;
    letter-spacing: 2em;
    margin-right: -2em;
}
.borrower .ivu-form-item-error-tip{
    left: 67px !important;
}
.outTime .ivu-form-item-error-tip{
    left: 85px !important;
}
.borrowBox{
    height: 18px;
}
.borrowBtn{
    text-align: center;
    padding: 0px;
    font-size: 12px !important;
    border: none;
    background: none;
    color: #495060;
}
</style>


