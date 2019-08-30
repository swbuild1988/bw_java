<template>
    <div class="allDiv">
        <Row class="queryCondition">
            <Col span="6">
                <span class="conditionTitle">备品名称：</span>
                <Input v-model="conditions.name" style="width: 60%"></Input>
            </Col>
            <Col span="6">
                <span class="conditionTitle">备品类别：</span>
                <Select v-model="conditions.typeId" style="width: 60%">
                    <Option value=null>所有</Option>
                    <Option v-for="item in equipmentTypes" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">备品型号：</span>
                <Select v-model="conditions.modelId" style="width: 60%">
                    <Option value=null>所有</Option>
                    <Option v-for="item in equipmentModels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">备品状态：</span>
                <Select style="width: 60%" v-model="conditions.isInStorage">
                    <!-- <Option v-for="item in status" :key="item.id" :value="item.isInStorage">{{item.name}}</Option> -->
                    <Option v-for="item in status" :key="item.key" :value="item.key">{{item.val}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">开始时间：</span>
                <DatePicker type="datetime" v-model="conditions.startTime" placeholder="请输入开始时间" style="width: 60%"></DatePicker>
            </Col>
            <Col span="6">
                <span class="conditionTitle">结束时间：</span>
                <DatePicker type="datetime" v-model="conditions.endTime" placeholder="请输入结束时间" style="width: 60%"></DatePicker>
            </Col>
            <Col span="6">
                <Button type="primary" icon="ios-search"  @click="showTable()" style="margin-right: 2vmin">查询</Button>
                <Button type="primary" @click="add({path: '/UM/equipment/addBackUp'})" icon="forward">
                批量入库</Button>
            </Col>
        </Row>
        <div class="list">
            <!-- <div style="text-align: right">
            </div> -->
            <div class="nullData" v-show="isNullData">暂无数据</div>
            <Row :gutter="16">
                <Col span="6" v-for="item in equipments" :key="item.id" style="margin-top: 6px;padding: 5px;">
                    <div class="boxBG">
                        <div class="backGoundBox">
                            <div class="topBox">
                                <!-- <a class="ivu-modal-close" style="right: 8px;top:0px;"><i class="ivu-icon ivu-icon-ios-close-empty"></i></a> -->
                                <p class="equipentTitle">{{ item.name }}</p>
                                <div class="imgBox"><img :src="imgUrl"></div>
                            </div>
                        </div>
                        <div class="detailsBox">
                            <div>
                                <div class="backUpInfo" style="width: 44%">设备类型：{{ item.typeName }}</div>
                                <div class="backUpInfo">设备状态：<span :class="item.status ? 'trueStatus' : 'falseStatus'">{{item.status?'在库':'出库'}}</span></div>
                            </div>
                            <div>
                                <div class="backUpInfo" style="width: 44%">设备型号：{{ item.modelName }}</div>
                                <div class="backUpInfo">入库时间：{{ item.inTime }}</div>
                            </div>
                        </div>
                        <div class="operation">
                            <Row>
                                <Col span="12" class="operationSee">
                                    <Icon type="reply" size=20></Icon>
                                    <div class="borrowBox">
                                        <Button class="borrowBtn" @click="show(item.id, item.inTime)" :disabled="item.status==false">取用出库</Button>
                                    </div>
                                </Col>
                                <Col span="12" class="operationDel">
                                    <Icon type="trash-a" size=20></Icon>
                                    <div class="borrowBox">
                                        <Button class="borrowBtn" @click="del(item.id)" :disabled="item.status==false">删除</Button>
                                    </div>
                                </Col>
                            </Row>
                        </div>
                    </div>
                </col>
            </Row>
        </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show-sizer
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
            :style='pageStyle'></Page>
        <Modal
            title="取用出库信息登记"
            v-model="isBorrow"
            width="44vw"
        >
            <Form ref="borrow" :model="borrow" :rules="borrowValidateRules" :label-width="120" @submit.native.prevent>
                <FormItem label="资产编码：" prop="assetNo">
                    <Input v-model="borrow.assetNo"></Input>
                </FormItem>
                <FormItem label="安装位置：" class="location">
                    <Row :gutter="8">
                        <Col span="7">  
                            <FormItem prop="tunnelId" class="locationForm">
                                <Select v-model="borrow.tunnelId" @on-change="changeTunnelId(borrow.tunnelId)">
                                    <Option v-for="(item, index) in tunnels" :key="index" :value="item.id">{{item.name}}</Option>
                                </Select>
                            </FormItem>  
                        </Col>
                        <Col span="7">
                            <FormItem prop="areaId" class="locationForm">
                                <Select v-model="borrow.areaId" @on-change="changeSection()">
                                    <Option v-for="(item, index) in areas" :value="item.id" :key="index">{{ item.name }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="7">
                            <FormItem prop="storeId" class="locationForm">
                                <Select v-model="borrow.storeId" @on-change="changeSection()">
                                    <Option v-for="(item, index) in stores" :value="item.id" :key="index">{{ item.name }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="5" v-show="false">
                            <FormItem>
                                <Input v-model="borrow.sectionId" v-show="false"></Input>
                            </FormItem>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="关联监测对象：" prop="objId">
                    <Input v-model="borrow.objId" @on-change="changeObjId(borrow.objId)"></Input>
                    <ul class="chooseObj" v-show="isShowObjs">
                        <li v-for="item in objs" :value="item" :key="item.id" @click="replaceInputValue(item)">{{item}}</li>
                    </ul>
                </FormItem>
                <FormItem label="对象类型：">
                    <div v-for="item in objTypes" :key="item.objtypeId">
                        <Input :value="item.objtypeName" readonly></Input>
                    </div>
                </FormItem>
                <FormItem label="取用人：" prop="borrower">
                    <Select v-model="borrow.borrower">
                        <Option v-for="(item,index) in staffs" :key="index" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="取用时间：" prop="outTime">
                    <DatePicker type="datetime"  placeholder="请输入取用时间" style="width: 100%" v-model="borrow.outTime"></DatePicker>
                </FormItem>
                <FormItem label="取用目的：">
                    <Input type="textarea" v-model="borrow.borrowPurpose" style="width: 100%"></Input>
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
import equipemtTunnel from "../../../../assets/UM/equipemtTunnel.jpg";

export default {
    name: "query-backup",
    data() {
        return {
            imgUrl: equipemtTunnel,
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
                position: 'absolute',
                bottom: '1vmin',
                right: '2.5vmin'
            },
            staffs:[],
            borrow:{
                borrower: null,
                outTime: null,
                objId: null,
                tunnelId: null,
                borrowPurpose: null,
                sectionId: null,
                assetNo: null,
                areaId: null,
                storeId: null,
            },
            aloneBorrowId: null,
            isBorrow: false,
            borrowValidateRules:{
                borrower:[
                    { type: 'number', required: true, message: '请选择取用人', trigger: 'change'}
                ],
                outTime: [
                    { type: 'date', required: true, message: '请选择取用时间', trigger: 'change'},
                    { validator: this.checkOutTime, trigger: 'change' }
                ],
                objId: [
                    { type: 'number', required: true, message: '请选择关联监测对象', trigger: 'change' }
                ],
                tunnelId: [
                    { type: 'number', required: true, message: '所属管廊不能为空', trigger: 'change' }
                ],
                areaId: [
                    { type: 'number', required: true, message: '所属区域不能为空', trigger: 'change' }
                ],
                storeId: [
                    { type: 'number', required: true, message: '所属管仓不能为空', trigger: 'change' }
                ],
                assetNo: [
                    { required: true, message:'资产编码不能为空', trigger: 'blur' }
                ]
            },
            isDisabled: false,
            isColor: '#000',
            objs: [],
            objTypes: [],
            userId: null,
            checkIntime: null,
            areas: null,
            stores: null,
            isNullData: false,
            isShowObjs: false
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
        },
        'borrow.tunnelId': function(newVal, oldVal){
            this.borrow.areaId = null
            this.borrow.storeId = null
            this.borrow.sectionId = null
            // this.borrow.sectionName = null
            this.changeTunnelId(newVal)
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
        EquipmentService.getStaffs().then(
            result => {
                this.staffs = result;
            },
            error => {
                this.Log.info(error)
            }
        )
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
        this.getObj()
    },
    methods: {
        // type 1:查看， 2：编辑
        goToMoudle: function(index, type) {
            this.$router.push({
                name: "设备详情",
                params: {
                    id: this.equipments[index].id,
                    type: type
                }
            });
        },
        //备品备件分页查询
        showTable() {
            let _this = this;
            if(new Date(_this.conditions.startTime)>new Date(_this.conditions.endTime)){
                _this.$Message.error('开始时间必须小于结束时间！');
                return;
            }
            EquipmentService.backUpDatagrid(this.params).then(
                result => {
                    if(result.list.length==0){
                        this.isNullData = true
                    }else{
                        this.isNullData = false
                    }
                    for (let index in result.list) {
                        result.list[index].inTime = new Date(result.list[index].inTime).format("yyyy-MM-dd hh:mm:ss");
                        if (result.list[index].imgUrl != null) {
                            result.list[index].imgUrl = _this.ApiUrl + result.list[index].imgUrl.replace(/\\/g, "/");
                        }
                        if(result.list[index].status == false){
                            this.isDisabled = true
                        }else{
                            this.isDisabled = false;
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
        handlePageStorage(value){
            this.OutStoragePage.pageNum = value;
            this.showInStorage()
        },
        handPageSizeStorage(value){
            this.OutStoragePage.pageSize = value;
            this.showInStorage()
        },
        //删除
        del(id) {
            let _this = this;
            this.$Modal.confirm({
                title: "删除",
                width: '24vw',
                content: "<p>确认删除吗?</p>",
                onOk: () => {
                    let _this = this;
                    EquipmentService.deSpare(id).then(
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
        add(path) {
            this.$router.push(path);
        },
        show(id,inTime){
            this.isBorrow = !this.isBorrow
            this.borrow.borrower = null
            this.borrow.operation = null
            this.borrow.borrowPurpose = null
            this.aloneBorrowId = id
            this.checkIntime = inTime
        },
        checkOutTime(rule, value, callback){  
            if(new Date(value)<=new Date(this.checkIntime)){
                callback(new Error("备品取用时间不能早于备品入库时间"))
            }else{
                callback()
            }
        },
        //取用出库modal框的“确认”按钮
        confirmBorrow(name){
            this.isBorrow = true
            this.$refs[name].validate((valid) => {
                if(valid){
                    var param = {
                        equipment : {
                            tunnelId: this.borrow.tunnelId,
                            sectionId: this.borrow.sectionId,
                            objId: this.borrow.objId,
                            assetNo: this.borrow.assetNo
                        },
                        spareOut : {
                            describe: this.borrow.borrowPurpose,
                            userId: this.userId,
                            staffId: this.borrow.borrower,
                            outTime: this.borrow.outTime,
                        }
                    }
                    EquipmentService.batchOutBound(this.aloneBorrowId,param).then(
                        result => {
                            this.isBorrow = false
                            this.showTable()
                            this.$refs[name].resetFields()
                            this.borrow.describe = null
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                }else{
                    this.$Message.error("请填写取用信息")
                }
            })
        },
        //读取cookie
        getSessionUserName:function () {
            if(sessionStorage.UMUerId!=null||sessionStorage.UMUerId!=undefined||sessionStorage.UMUerId!=''){
                this.userId =  parseInt(sessionStorage.UMUerId)
            }
        },
        //关联监测对象
        getObj() {
            EquipmentService.getObj().then(
                res=> {
                    this.objs = res
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        // 对象类型
        getObjType() {
            var info = {
                id: this.borrow.objId,
                pageSize: 10,
                pageNum: 1
            };
            EquipmentService.getObjType(info).then(
                result => {
                    this.objTypes = result.list
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //根据TunnelId,获取area和store
        changeTunnelId(id){
            if(id!=null){
                //获取store
                TunnelService.getStoresByTunnelId(id).then(
                    result => {
                        this.stores = result
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
                //获取area
                TunnelService.getAreasByTunnelId(id).then(
                    result => {
                        this.areas = result
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
            }
        },
        //根据areaId,storeId获取section
        changeSection(){
            if(this.borrow.storeId!=null&&this.borrow.areaId!=null){
                TunnelService.getSectionByAreaIdStoreId(this.borrow.storeId, this.borrow.areaId).then(
                    result => {
                        if(result!=null){
                            this.borrow.sectionId = result.id
                            // this.borrow.sectionName = result.name
                        }
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
            }
        },
        changeObjId(objId){
            this.isShowObjs = true 
            EquipmentService.changeObjId(objId).then(
                result => {
                    this.objs = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        replaceInputValue(id){
            this.borrow.objId = id
            this.isShowObjs = false
            this.getObjType()
        }
    },
};
</script>
<style scoped>
.boxBG{
    background: url("../../../../assets/UM/boxBGImg.png") no-repeat;
    background-size: 100% 100%;
}
.backGoundBox {
    position: relative;
    height: 10vh;
    width: 100%;
}

.equipentTitle{
    color: #fff;
    font-size: 2.4vmin;
    text-align: center;
    line-height: 10vh;
    margin-left: 6vh;
}

.detailsBox{
    color: #fff;
}

.imgBox img {
    width: 5vh;
    height: 5vh;
    border-radius: 5vh;
    position: absolute;
    left: 35%;
    top: 50%;
    margin-top: -2.5vh;
}

.operationSee,
.operationEdit,
.operationDel {
    text-align: center;
    padding: 1vmin 0;
    color: #fff;
}


.operationSee p,
.operationEdit p,
.operationDel p {
    cursor: pointer;
}

.backUpInfo{
    display: inline-block;
    line-height: 36px;
    padding-left: 2vmin;
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
    color: #fff;
}
.trueStatus{
    color: green
}
.falseStatus{
    color: red;
}
.ivu-form .ivu-form-item-label{
    width: 120px;
}
.location >>> .ivu-form-item-label:before {
    content: '*';
    display: inline-block;
    margin-right: 0.4vmin;
    line-height: 1;
    font-family: SimSun;
    font-size: 1.2vmin;
    color: #ed3f14;
}

.ivu-page>>>.ivu-page-total, .ivu-page>>>.ivu-page-options-elevator{
    color: #fff;
}
.list{
    height: 66vh;
    overflow-y: auto;
    overflow-x: hidden;
}
.list .ivu-btn-primary{
    background: linear-gradient(to left, #2734e1, #b195ed)
}
.list::-webkit-scrollbar{
    width: 0.4vmin;
    height: 0.4vmin;
}
.list::-webkit-scrollbar-thumb{
    border-radius: 1vmin;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #83a6ed;
}
.list::-webkit-scrollbar-track{
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    border-radius: 1vmin;
    background: #ededed;
}

.chooseObj{
    width: 13vw;
    max-height: 13.1vh;
    position: relative;
    border-radius: 4px;
    overflow-y: auto;
    z-index: 999;
    background: #fff;
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

@media (min-width: 2200px){
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .queryCondition{
        font-size: 1.4vmin;
    }
    .detailsBox{
        padding-top: 2vh;
    }
    .backUpInfo{
        line-height: 4vh;
        font-size: 1.6vmin;
    }
    .operation,.borrowBtn{
        font-size: 1.4vmin !important;
    }
    .ivu-form-item >>> .ivu-form-item-label{
        width: 13vmin !important;
        line-height: 4.5vmin;
    }
    .ivu-form-item >>> .ivu-form-item-content{
        margin-left: 13vmin !important;
        line-height: 4.5vmin;
    }
    .locationForm.ivu-form-item >>> .ivu-form-item-content{
        margin-left: 0px !important;
    }
    .location >>> .ivu-form-item >>> .ivu-form-item-content{
        margin-left: 0px !important;
    }
    .equipentTitle{
        font-size: 2vmin;
    }
    .borrowBox{
        height: 1.8vmin;
    }
    .ivu-icon{
        font-size: 2vmin !important;
    }
}
</style>


