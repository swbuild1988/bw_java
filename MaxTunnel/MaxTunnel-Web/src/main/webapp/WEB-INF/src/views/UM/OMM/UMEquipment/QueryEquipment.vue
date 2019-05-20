<template>
  <div class="allDiv">
    <Row class="queryCondition">
        <Col span="6">
            <span>设备名称</span><span>：</span>
            <Input v-model="conditions.name" style="width:60%"></Input>
        </Col>
        <Col span="6">
            <span>设备类型：</span>
            <Select v-model="conditions.typeId" style="width: 60%">
                <Option value=null key=0>所有</Option>
                <Option v-for="item in equipmentType" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
        </Col>
        <Col span="6">
            <span>设备型号：</span>
            <Select v-model="conditions.modelId" style="width: 60%">
                <Option value=null key=0>所有</Option>
                <Option v-for="item in equipmentModel" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
        </Col>
        <Col span="6">
            <span>设备状态：</span>
            <Select v-model="conditions.status" style="width:60%">
            <Option value=null key="0">所有</Option>
            <Option v-for="item in equipmentStatus" :value="item.val" :key="item.val">{{ item.key }}</Option>
            </Select>
        </Col>
        <Col span="6">
            <Span class="word43">供应商</Span><span>：</span>
            <Select v-model="conditions.venderId" style="width:60%">
                <Option value=null key="0">所有</Option>
                <Option v-for="item in venders" :key="item.id" :value="item.id">{{item.name}}</Option>
            </Select>
        </Col>
        <Col span="6">
            <span>开始时间：</span>
            <DatePicker type="date" placeholder="请选择开始时间" style="width:60%" v-model="conditions.startTime"></DatePicker>
        </Col>
        <Col span="6">
            <span>结束时间：</span>
            <DatePicker type="date" placeholder="请选择结束时间" style="width:60%" v-model="conditions.endTime"></DatePicker>
        </Col>
        <Col span="6">
            <Span>所属管廊</Span><span>：</span>
            <Select v-model="conditions.tunnelId" style="width:60%" @on-change="getStores">
                <Option value=null key="0">所有</Option>
                <Option v-for="item in tunnels" :key="item.id" :value="item.id">{{item.name}}</Option>
            </Select>
        </Col>
        <Col span="6">
            <Span>所属管舱</Span><span>：</span>
            <Select v-model="conditions.storeId" style="width:60%">
                <Option value=null key="0">所有</Option>
                <Option v-for="item in stores" :key="item.id" :value="item.id">{{item.name}}</Option>
            </Select>
        </Col>
        <Col span="6">
            <Button type="primary"  icon="ios-search" @click="showTable()">查询</Button>
            <!-- <Button type="primary">批量导入</Button> -->
            <vue-xlsx-table @on-select-file="batchAddEquipment">批量导入管廊设备</vue-xlsx-table>
        </Col>
    </Row>
    <div class="list">
        <Row>
            <h1 class="equipmentList">设备列表</h1>
            <Col span="24">
                <Button class="addList" @click="add({path: '/UM/equipment/add'})" long>
                    <Icon type="plus-round"></Icon>
                    添加
                </Button>
            </Col>
            <Tabs :value="chooseEquipmentTabPane" :animated="false" style="border: 1px solid #fff" @on-click="chooseTab">
                <TabPane label="卡片" name="卡片">
                    <Col span="24">
                        <div class="nullData" v-show="isNullData">暂无数据</div>
                        <Row :gutter="16">
                            <Col span="6" v-for="(item,index) in equipments" :key="index" style="margin-top: 1vmin;">
                                <div :style="backImage" class="backGoundBox">
                                <div class="topBox">
                                    <a class="ivu-modal-close" style="right: 8px;top:0px;color: #f5650b" ><i class="ivu-icon ivu-icon-ios-close-empty" @click="del(index)"></i></a>
                                    <p class="equipentTitle">{{ item.name }}</p>
                                    <div class="imgBox"><img :src="imgUrl"></div>
                                </div>
                                </div>
                                <Row class="detailsBox">
                                    <Col span="12" :title="item.tunnel.name">所属管廊：{{item.tunnel.name}}</Col>
                                    <Col span="12" :title="item.typeName">设备类型：{{ item.typeName }}</Col>
                                    <Col span="12" :title="item.vender.name">供应商：{{item.vender.name}}</Col>
                                    <Col span="12" :title="item.model.name">所属型号：{{item.model.name}}</Col>
                                    <Col span="12" :title="item.statusName">设备状态：{{item.statusName}}</Col>
                                    <Col span="12" :title="item.crtTime">启用时间：{{ item.crtTime }}</Col>
                                </Row>
                                <div class="operation">
                                <Row>
                                    <Col span="8" class="operationSee">
                                        <div @click="show(index)">
                                            <Icon type="eye" size=20></Icon>
                                            <p>查看</p>
                                        </div>
                                    </Col>
                                    <Col span="8" class="operationEdit">
                                        <div @click="edit(index)">
                                            <Icon type="edit" size=19></Icon>
                                            <p>编辑</p>
                                        </div>
                                    </Col>
                                    <Col span="8" class="operationDel">
                                        <div @click="del(index)">
                                            <Icon type="trash-a" size=20></Icon>
                                            <p>删除</p>
                                        </div>
                                    </Col>
                                </Row>
                                </div>
                            </Col>
                        </Row>
                    </Col>
                </TabPane>
                <TabPane label="表格" name="表格">
                    <Table :columns='columnsEquipment' :data="equipments"></Table>
                </TabPane>
            </Tabs>
        </Row>
    </div>
    <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show-sizer
          placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
          :style='pageStyle'></Page>
  </div>
</template>

<script>
import types from "../../../../../static/Enum.json";
import { TunnelService } from "../../../../services/tunnelService";
import { EquipmentService } from "../../../../services/equipmentService";
import equipemtTunnel from "../../../../assets/UM/equipemtTunnel.jpg";
export default {
    name: "queryEquipment",
    data() {
        return {
            imgUrl: equipemtTunnel,
            showOn: true,
            equipments: [],
            tunnels: [],
            stores: [],
            tunnelId: 1,
            equipmentTypes: [],
            deleteEquipmentInfo: [], //要删除的设备
            conditions: {
                name: null,
                typeId: null,
                modelId: null,
                status: null,
                venderId: null,
                startTime: null,
                endTime: null,
                tunnelId: null,
                storeId: null
            },
            equipmentStatus: [],
            pagingList: [],
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
            equipmentType: [],
            equipmentModel: [],
            venders: [],
            backImage: {
                backgroundImage: "url(" + require("../../../../assets/UM/equipemtTunnel.jpg") + ")",
            },
            columnsEquipment: [
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '设备名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '资产编码',
                    key: 'assetNo',
                    align: 'center'
                },
                {
                    title: '所属管廊',
                    align: 'center',
                    render: (h, params) => {
                        let temp = params.row.tunnel.name
                        return h('div', temp)
                    }
                },
                {
                    title: '设备所属系统',
                    align: 'center',
                    key: 'typeName'
                },
                {
                    title: '设备状态',
                    key: 'statusName',
                    align: 'center'
                },
                {
                    title: '规格型号',
                    align: 'center',
                    render: (h, params) => {
                        let temp = params.row.model.name
                        return h('div', temp)
                    }
                },
                {
                    title: '量程',
                    key: 'range',
                    align: 'center'
                },
                {
                    title: '额定电压',
                    key: 'ratedVoltage',
                    align: 'center'
                },
                {
                    title: '质保期限',
                    key: 'qaTerm',
                    align: 'center'
                },
                {
                    title: '厂家',
                    key: 'factory',
                    align: 'center'
                },
                {
                    title: '品牌',
                    key: 'brand',
                    align: 'center'
                },
                {
                    title: '供应商',
                    align: 'center',
                    render: (h, params) => {
                        let temp = params.row.vender.name
                        return h('div', temp)
                    }
                },
                {
                    title: '安装时间',
                    align: 'center',
                    render: (h, params) => {
                        let temp = new Date(params.row.runTime).format('yyyy-MM-dd hh:mm:ss')
                        return h('div', temp)
                    }
                },
                {
                    title: '安装位置',
                    align: 'center',
                    render: (h, params) => {
                        let temp = null
                        if(params.row.section!=null){
                            temp = params.row.section.name
                        }else{
                            temp = ''
                        }
                        return h('div', temp)
                    }
                },
                {
                    title: '关联设备',
                    align: 'center',
                    render: (h, params) => {
                        let temp = params.row.obj.name
                        return h('div', temp)
                    }
                }
            ],
            chooseEquipmentTabPane: '卡片',
            isNullData: false
        };
    },
    beforeRouteLeave(to, from, next) {
        if (to.name == "设备详情") {
            to.meta.keepAlive = false;
            next();
        } else {
            from.meta.keepAlive = false;
            next();
        }
    },
    created(){
        if(localStorage.getItem('choosedEquipmentTab')){
            this.chooseEquipmentTabPane = localStorage.getItem('choosedEquipmentTab')
        }else{
            this.chooseEquipmentTabPane = '卡片'
        }
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
                name: this.conditions.name,
                type: this.conditions.typeId,
                status: this.conditions.status,
                tunnelId: this.conditions.tunnelId,
                venderId: this.conditions.venderId,
                modelId: this.conditions.modelId,
                startTime: this.conditions.startTime,
                endTime: this.conditions.endTime,
                storeId: this.conditions.storeId,
                tunnelId: this.conditions.tunnelId,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize
            };
            return Object.assign({}, param);
        }
    },
    mounted() {
        //从数据库读取select的option选项
        let _this = this;

        //获取type
        EquipmentService.getEquipmentTypes().then(
            res=>{
                this.equipmentType = res
            },
            error => {
                this.Log.info(error);
            },
        );

        //获取model
        EquipmentService.getEquipmentModels().then(
            res=>{
                this.equipmentModel = res
            },
            error => {
                this.Log.info(error)
            }
        )
        //获取供应商
        EquipmentService.getVenders().then(
            res=>{
                this.venders = res
            },
            error=>{
                this.Log.info(error)
            }
        ),
        //设备状态
        EquipmentService.getStatus().then(
            res=>{
                this.equipmentStatus = res
            },
            error=>{
                this.Log.info(error)
            }
        )

        TunnelService.getTunnels().then(
            result => {
                _this.tunnels = result;
                if(_this.$route.params.tunnelId){
                    _this.conditions.tunnelId = +_this.$route.params.tunnelId
                    _this.getStores()
                }
                _this.showTable();
                
            },
            error => {
                _this.Log.info(error);
            }
        );
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
        showTable() {
            let _this = this;
            if(new Date(_this.conditions.startTime)>new Date(_this.conditions.endTime)){
                _this.$Message.error('开始时间必须小于结束时间！');
                return;
            }
            EquipmentService.equipmentDatagird(this.params).then(
                result => {
                    if(result.pagedList.length==0){
                        this.isNullData = true
                    }else{
                        this.isNullData = false
                    }
                    for (let index in result.pagedList) {
                        result.pagedList[index].crtTime = new Date(result.pagedList[index].crtTime).format("yyyy-MM-dd hh:mm:s");
                        // if (result.list[index].imgUrl != null) {
                        //   result.list[index].imgUrl =
                        //     _this.ApiUrl + result.list[index].imgUrl.replace(/\\/g, "/");
                        // }
                    }
                    _this.equipments = result.pagedList;
                    _this.page.pageTotal = result.total;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        batchAddEquipment(data){
            let arr = []
            data.body.forEach(element => {
                let temp = {
                    name: element[data.header[0]],
                    assetNo: element[data.header[1]],
                    tunnelId: element[data.header[2]],
                    type: element[data.header[3]],
                    status: element[data.header[4]],
                    modelId: element[data.header[5]],
                    range: element[data.header[6]],
                    ratedVoltage: element[data.header[7]],
                    qaTerm: element[data.header[8]],
                    factory: element[data.header[9]],
                    brand: element[data.header[10]],
                    venderId: element[data.header[11]],
                    runTime: element[data.header[12]],
                    sectionId: element[data.header[13]],
                    objId: element[data.header[14]]
                }
                arr.push(temp)
            })
            EquipmentService.batchAdd(arr).then(
                result => {
                    this.$Message.success("批量添加成功！")
                    this.showTable()
                },
                error => {
                    this.$Message.error("批量添加失败！")
                    this.Log.info(error)
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.showTable();
        },
        show(index) {
            this.goToMoudle(index, types.pageType.Read);
        },
        edit(index) {
            this.goToMoudle(index, types.pageType.Edit);
        },
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
                width: '24vw',
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
        add(path) {
            this.$router.push(path);
        },
        pickUp() {
            this.showOn = !this.showOn;
        },
        chooseTab(name){
            localStorage.setItem("choosedEquipmentTab",name)
        },
        getStores(){
            if(this.conditions.tunnelId){
                this.conditions.storeId = null
                TunnelService.getStoresByTunnelId(this.conditions.tunnelId).then(
                    res=>{
                        this.stores = res
                        if(this.$route.params.storeId && res.length){
                             this.conditions.storeId = +this.$route.params.storeId
                        }
                    },
                    err=>{
                        this.Log.info(err)
                    }
                )
            } 
        }
    }
};
</script>
<style scoped>
.queryCondition{
    height: 14vh;
}
.equipmentList {
    font-size: 17px;
    font-weight: 700;
}

.addList {
    border: 1px dashed #dfdfdf;
    text-align: center;
    line-height: 40px;
    color: #8b8b8b;
    margin-top: 20px;
    border-radius: 10px;
    background: #fff;
    z-index: 10001;
}

/*new*/
.equipentTitle {
    color: #fff;
    font-size: 20px;
    text-align: center;
    line-height: 10vh;
}

.imgBox {
    text-align: center;
}

.table li {
    list-style: none;
    width: 80%;
    margin: 10px auto;
}

.backGoundBox {
    position: relative;
    height: 10vh;
    width: 100%;
}

.topBox {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(255, 255, 255, 0.3);
}

.imgBox img {
    width: 5vh;
    height: 5vh;
    border-radius: 5vh;
    position: absolute;
    z-index: 5;
    left: 50%;
    top: 70%;
    margin-left: -2.5vh
}

.detailsBox {
    border: 1px solid #ebe8e8;
    padding-top: 20px;
    background: #fff;
}

.detailsBox .ivu-col-span-12,.detailsBox .ivu-col-span-12 {
    line-height: 35px;
    padding-left: 2vmin;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
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
.word43{
    letter-spacing: 0.5em;
    margin-right:  -0.5em;
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
    .equipmentList{
        font-size: 2.5vmin;
    }
    .addList {
        border: 0.2min dashed #dfdfdf;
        text-align: center;
        line-height: 4min;
        color: #8b8b8b;
        margin-top: 20px;
        border-radius: 10px;
        background: #fff;
        z-index: 10001;
    }
    .detailsBox{
        padding-top: 2vh;
    }
    .detailsBox .ivu-col-span-10,.detailsBox .ivu-col-span-14{
        line-height: 4vmin;
        font-size: 1.3vmin;
    }
    .operation{
        font-size: 1.4vmin;
    }
    .equipentTitle{
        font-size: 2vmin;
    }
    .ivu-icon{
        font-size: 2vmin !important;
    }
}
</style>


