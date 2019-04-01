<template>
    <!--管仓 -->
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">区段管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <span>区段名称：</span>
                <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth" />
            </Col>
            <Col span="6">
                <span>所属管廊：</span>
                <Select v-model="researchInfo.tunnelId" placeholder="请选择所属管廊" class="inputWidth" @on-change="getstores">
                    <Option value="null">不限</Option>
                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>         
                </Select>
            </Col>
            <Col span="6">
                <Poptip placement="bottom" width="1000">
                    <span>所属管仓：</span>
                    <Input v-model="barnNameShow" placeholder="请选择管仓类型" style="width: 60%"/>
                    <div class="pop" slot="content">
                        <barn-choose v-on:listenToBarnChoose="getBarn"></barn-choose>
                    </div>
                </Poptip>
            </Col>
            <Col span="6">
            <div>
                <span>所属区域：</span>
                <Select v-model="researchInfo.areaId" placeholder="请选择区域类型" class="inputWidth">
                    <Option value="null">不限</Option>
                    <Option v-for="item in areas" :value="item.id" :key="item.id">{{item.name}}</Option>
                </Select>
            </div>
            </Col>
        </Row>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <span>开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="researchInfo.startTime">
                </DatePicker>
                </Col>
            <Col span="6">
                <span>结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" class="inputWidth" v-model="researchInfo.endTime">
                </DatePicker>
            </Col>
            <Col span="10">
                    <Button type="primary" size="small"  icon="ios-search" @click="research()">查询</Button>
                    <!-- <Button type="error" size="small" @click="addNewSection()">新增区段</Button>  -->
                    <Button type="warning" size="small" @click="create()" :enable="canCreate">根据现有区和仓新建区段</Button> 
                    <vue-xlsx-table @on-select-file="getPositions">导入经纬度</vue-xlsx-table>
                    <Button type="primary" size="small" @click="savePos">保存经纬度</Button>
                    <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                    <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>    
        </Row>
        <Table border ref="selection" :columns="columns7" :data="sectionData" @on-selection-change="startdelete" style="margin:20px;"></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top"  show-sizer @on-page-size-change='handlePageSize' :page-size="page.pageSize"
              @on-change="handlePage" show-elevator class="pageStyle"></Page>
        <!-- 修改区段信息 -->
        <div>
            <section-modification v-bind="changeSectionInfo"></section-modification>
        </div>
    </div>
</template>

<script>
import BarnChoose from "../../../views/CM/Store/BarnChoose";
import SectionModule from "../../../views/CM/SectionControl/SectionModule";
import SectionModification from "../../../views/CM/SectionControl/SectionModification";
import { TunnelService } from '../../../services/tunnelService'

export default {
    name: "barn-manage",
    data() {
        return {
            researchInfo: {
                name: "",
                tunnelId: null,
                storeId: null,
                areaId: null,
                startTime: "",
                endTime: ""
            },
            barnNameShow: "",
            canCreate: true,
            columns7: [
                {
                    type: "selection",
                    width: 60,
                    align: "center"
                },
                {
                    type: "index",
                    width: 60,
                    align: "center"
                },
                {
                    title: "区段名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "可安装管线数",
                    key: "totalCableNumber",
                    align: "center"
                },
                {
                    title: "所属管廊",
                    key: "tunnelName",
                    align: "center",
                    render: (h, params) => {
                        let temp = params.row.store.tunnel.name
                        return h('span', temp)
                    }
                },
                {
                    title: "所属管仓",
                    key: "storeName",
                    align: "center",
                    render: (h, params) => {
                        let temp = params.row.store.name
                        return h('span', temp)
                    }
                },
                {
                    title: "所属区域",
                    key: "areaName",
                    align: "center",
                    render: (h, params) => {
                        return h('span', params.row.area.name)
                    }
                },
                {
                    title: "相机视角",
                    key: "camera",
                    align: "center"
                },
                {
                    title: "开始坐标",
                    key: "startPoint",
                    align: "center"
                },
                {
                    title: "结束坐标",
                    key: "endPoint",
                    align: "center"
                },
                {
                    title: 'S1',
                    align: 'center'
                },
                {
                    title: 'S2',
                    align: 'center'
                },
                {
                    title: '创建时间',
                    key: 'crtTime',
                    align: 'center',
                    width: 190,
                    render: (h,params) => {
                        let temp = new Date(params.row.crtTime).format('yyyy-MM-dd hh:mm:s')
                        return h('div',temp)
                    }
                },
                {
                    title: "操作",
                    key: "action",
                    align: "center",
                    width: 80,
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button",
                                {
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    on: {
                                        click: () => {
                                            this.editSection(params.index);
                                        }
                                    }
                                },
                                "修改"
                            )
                        ]);
                    }
                }
            ],
            sectionData: [],
            types: [],
            tunnels: [],
            areas: [],
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            ruleValidate: {},
            changeSectionInfo: {
                show: { 
                    state: false 
                },
                sectionId: null
            },
            deleteShow: false,
            deleteSelect: [],
            batchCreate: {
                isShow: false,
                tunnelId: null,
                sectionsInfo: [],  
                tabValue: null
            }
        };
    },
    mounted() {
        this.gettunnel();
        this.showTable();
    },
    computed: {
        researches() {
            let research = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.researchInfo.name,
                tunnelId: this.researchInfo.tunnelId,
                storeId: this.researchInfo.storeId,
                areaId: this.researchInfo.areaId,
                startTime: new Date(this.researchInfo.startTime).getTime(),
                endTime: new Date(this.researchInfo.endTime).getTime()
            };
            return Object.assign({}, research);
        },
    },
    methods: {
        showTable() {
            this.axios.post("/sections/datagrid", this.researches).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.sectionData = data.list
                    this.page.pageTotal = data.total;
                }
            });
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.showTable();
        },
        create() {
            this.canCreate = false;
            this.batchCreate.isShow = true;
            // this.axios.get("/sections/create").then(res => {
            //     this.canCreate = true;
            // });
        },
        gettunnel() {
            let _this = this
            TunnelService.getTunnels().then(
                res=>{
                    let _tunnels = []
                    for (let i = 0; i < res.length; i++) {
                        let tunnel = {};
                        tunnel.id = res[i].id;
                        tunnel.name = res[i].name;
                        _tunnels.push(tunnel);
                    }
                    _this.tunnels = _tunnels;
                })
        },
        getstores() {
            let _this = this
            TunnelService.getAreasByTunnelId(this.researchInfo.tunnelId).then(
                result=>{
                    _this.areas = result
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        //编辑区段信息
        editSection(index) {
            this.changeSectionInfo.sectionId = this.sectionData[index].id
            this.changeSectionInfo.show.state = !this.changeSectionInfo.show.state;
            console.log('this.changeSectionInfo.sectionId', this.changeSectionInfo.sectionId)
        },
        startdelete(selection) {
            if (selection.length != 0) {
                this.deleteShow = true;
                this.deleteSelect = selection;
            } else {
                this.deleteShow = false;
            }
        },
        getBarn(_data) {
            //获取所属管仓
            this.researchInfo.storeId = _data.id;
            this.barnNameShow = _data.name;
        },
        // 删除所选区段
        alldelete() {
            this.$Modal.confirm({
                title: "删除确认",
                content: "<p>确认要删除选中的信息吗？</p>",
                onOk: () => {
                    let ids = this.deleteSelect[0].id;
                    for (let i = 1; i < this.deleteSelect.length; i++) {
                        ids += "," + this.deleteSelect[i].id;
                    }
                    this.axios.delete("/sections/batch/" + ids).then(res => {
                        let { code, data } = res.data;
                        if (code == 200) {
                            this.$Message.info("已删除");
                            this.deleteShow = false;
                            this.showTable();
                        }
                    });
                },
                onCancel: () => {
                    this.$Message.info("已取消操作");
                    this.showTable();
                }
            });
        },
        research() {
            this.showTable();
        },
        showSectionsInfo() {
            let _this = this
            if(this.batchCreate.tunnelId){
                Promise.all([TunnelService.getStoresByTunnelId(this.batchCreate.tunnelId),TunnelService.getAreasByTunnelId(this.batchCreate.tunnelId)]).then(
                result=>{
                    _this.batchCreate.sectionsInfo = []
                    let areas = []
                    let checkAllGroup = []
                    result[1].forEach(area=>{
                        let areaInfo = {}
                        areaInfo.id = area.id
                        areaInfo.name = area.name
                        areas.push(areaInfo)
                        checkAllGroup.push(area.name)
                    })
                    result[0].forEach(store=>{
                        let temp = {}
                        temp.storeId = store.id
                        temp.storeName = store.name
                        temp.areas = areas
                        temp.indeterminate = false
                        temp.checkAll = true
                        temp.checkAllGroup = checkAllGroup
                        _this.batchCreate.sectionsInfo.push(temp)
                    })
                    _this.batchCreate.tabValue = result[0][0].name
                },
                error=>{
                    _this.Log.info(error)
                })
            }
        },
        getPositions(data) {
            this.sectionData.forEach(section=>{
                let curPos = data.body.find(pos=>{
                    return section.id == pos[data.header[0]]
                })
                if(curPos != undefined){
                    let curSection = this.sectionData.find(item=>{
                        return item.id == curPos.sectionId
                    })
                    if(curSection != undefined){
                        curSection.startPoint = curPos[data.header[1]] + ',' + curPos[data.header[2]] + ',' + curPos[data.header[3]]
                        curSection.endPoint = curPos[data.header[4]] + ',' + curPos[data.header[5]] + ',' + curPos[data.header[6]]
                        curSection.camera = curPos[data.header[1]] + ',' + curPos[data.header[2]] + ',' + curPos[data.header[3]] + ',' + curPos[data.header[7]] + ',' + curPos[data.header[8]] + ',' + curPos[data.header[9]]
                    }
                }
                
            })
        },
        savePos(){
            let params = []
            this.sectionData.forEach(data=>{
                if(data.camera.length != 0){
                    let temp = {}
                    temp.id = data.id
                    temp.camera = data.camera
                    temp.startPoint = data.startPoint
                    temp.endPoint = data.endPoint
                    params.push(temp)
                }
            })
            let _this = this
            TunnelService.batchAddPositions(params).then(
                result=>{
                    _this.$Message.info("添加成功！")
                },
                error=>{
                    _this.Log.info(error)
                })
        }
    },
    components: {
        BarnChoose,
        SectionModule,
        SectionModification
    }
};
</script>

<style scoped>
.inputWidth {
    width: 60%;
}
.pop {
    max-height: 500px;
    overflow-y: auto;
}
.pageStyle {
    text-align: right;
    margin-top: 100px;
    margin-right: 10px;
}
.ivu-poptip,
.ivu-poptip-rel {
    width: 100%;
}
</style>




