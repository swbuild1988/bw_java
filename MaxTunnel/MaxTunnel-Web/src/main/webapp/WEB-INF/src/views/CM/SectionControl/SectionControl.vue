<template>
    <!--管仓 -->
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">区段管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
            <div>
                <span>区段名称：</span>
                <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth" />
            </div>
            </Col>
            <Col span="6">
            <div>
                <span>所属管廊：</span>
                <Select v-model="researchInfo.tunnelId" placeholder="请选择所属管廊" class="inputWidth">
                    <Option value="null">不限</Option>
                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>         
                </Select>
            </div>
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
                    <Option v-for="item in types" :value="item.id" :key="item.id">{{item.name}}</Option>
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
                    <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                    <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>    
        </Row>
        <Table border ref="selection" :columns="columns7" :data="data6" @on-selection-change="startdelete" style="margin:20px;"></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
              @on-change="handlePage" show-elevator class="pageStyle"></Page>
        <div>
            <section-module v-bind="addSectionInfo" v-on:listenToAdd="saveSection"></section-module>
        </div>
        <div>
            <section-modification v-bind="changeSectionInfo" v-on:listenToChange="saveChangeSection"></section-modification>
        </div>
        <Modal 
            v-model="batchCreate.isShow" 
            width='900' 
            style="padding-left: 20px;padding-right: 20px;"
            title="批量添加区段"
            ok-text="提交"
            @on-ok="save"
            @on-visible-change="visibleChanged"
            >
            <div>
                <span>所属管廊：</span>
                <Select v-model="batchCreate.tunnelId" placeholder="请选择所属管廊" class="inputWidth" @on-change="showSectionsInfo">
                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>         
                </Select>
            </div>
            <div style="margin-top: 20px">
                <Tabs v-model="batchCreate.tabValue">
                    <TabPane  v-for="section in batchCreate.sectionsInfo" :label="section.storeName" :name="section.storeName" :key="section.id">
                        <div style="border-bottom: 1px solid #e9e9e9;padding-bottom: 6px;margin-bottom: 6px">
                            <Checkbox :indeterminate="section.indeterminate" :value="section.checkAll" @click.prevent.native="handleCheckAll(section)">
                                全选
                            </Checkbox>
                        </div>
                        <CheckboxGroup v-model="section.checkAllGroup" @on-change="checkChanged">
                            <Checkbox v-for="area in section.areas" :label="area.name" :key="area.id" v-model="area.check" :value="area.check"></Checkbox>
                        </CheckboxGroup>
                    </TabPane>
                </Tabs>
            </div>
        </Modal>
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
                    align: "center"
                },
                {
                    title: "所属管仓",
                    key: "storeName",
                    align: "center"
                },
                {
                    title: "所属区域",
                    key: "areaName",
                    align: "center"
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
                    title: "创建时间",
                    key: "crtTime",
                    align: "center"
                },
                {
                    title: "操作",
                    key: "action",
                    align: "center",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button",
                                {
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    style: {
                                        marginLeft: "5px"
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
            data6: [],
            types: [],
            tunnels: [],
            areas: [],
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            formValidate: {
                name: "",
                tunnelId: null,
                storeId: null,
                areaId: null,
                totalCableNumber: null,
                camera: null,
                startPoint: null,
                endPoint: null
            },
            ruleValidate: {},
            addSectionInfo: {
                show: { state: false },
                addInfo: {}
            },
            changeSectionInfo: {
                show: { state: false },
                changeInfo: {}
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
        this.getstoretype();
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
        params() {
            // 新增
            let param = {
                name: this.formValidate.name,
                tunnelId: this.formValidate.tunnelId,
                storeId: this.formValidate.storeId,
                areaId: this.formValidate.areaId,
                totalCableNumber: this.formValidate.totalCableNumber,
                camera: this.formValidate.camera,
                startPoint: this.formValidate.startPoint,
                endPoint: this.formValidate.endPoint
            };
            return Object.assign({}, param);
        },
        modifications() {
            let param = {
                id: this.formValidate.id,
                name: this.formValidate.name,
                tunnelId: this.formValidate.tunnelId,
                storeId: this.formValidate.storeId,
                areaId: this.formValidate.areaId,
                totalCableNumber: this.formValidate.totalCableNumber,
                camera: this.formValidate.camera,
                startPoint: this.formValidate.startPoint,
                endPoint: this.formValidate.endPoint
            };
            return Object.assign({}, param);
        }
    },
    methods: {
        showTable() {
            this.axios.post("/sections/datagrid", this.researches).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    let allinfo = [];
                    for (let index in data.list) {
                        let info = {};
                        info.id = data.list[index].id;
                        info.name = data.list[index].name;
                        info.crtTime = new Date(
                            data.list[index].crtTime
                        ).format("yyyy-MM-dd hh:mm:s");
                        if (data.list[index].totalCableNumber != null) {
                            info.totalCableNumber =
                                data.list[index].totalCableNumber;
                        }
                        if (
                            data.list[index].store != null &&
                            data.list[index].store.tunnel != null
                        ) {
                            info.tunnelName =
                                data.list[index].store.tunnel.name;
                            info.tunnelId = data.list[index].store.tunnel.id;
                        }
                        if (data.list[index].store != null) {
                            info.storeName = data.list[index].store.name;
                            info.storeId = data.list[index].store.id;
                        }
                        if (data.list[index].area != null) {
                            info.areaName = data.list[index].area.name;
                            info.areaId = data.list[index].area.id;
                        }
                        if (data.list[index].camera != null) {
                            info.camera = data.list[index].camera;
                        }
                        if (data.list[index].startPoint != null) {
                            info.startPoint = data.list[index].startPoint;
                        }
                        if (data.list[index].endPoint != null) {
                            info.endPoint = data.list[index].endPoint;
                        }
                        allinfo.push(info);
                    }
                    this.data6 = allinfo;
                    this.page.pageTotal = data.total;
                }
            });
        },
        addNewSection() {
            this.addSectionInfo.show.state = !this.addSectionInfo.show.state;
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
        getstoretype() {
            this.axios.get("/store-type/list").then(res => {
                let { code, data } = res.data;
                let _types = [];
                if (code == 200) {
                    for (let i = 0; i < data.length; i++) {
                        let type = {};
                        type.id = data[i].id;
                        type.name = data[i].name;
                        _types.push(type);
                    }
                    this.types = _types;
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
        saveSection(_data) {
            //保存新区段
            this.formValidate = _data;
            this.axios.post("/sections", this.params).then(res => {
                // console.log(this.params);
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.$Message.success("添加成功！");
                    this.addSectionInfo.show.state = !this.addSectionInfo.show
                        .state;
                    this.showTable();
                }
            });
        },
        editSection(index) {
            this.changeSectionInfo.changeInfo = this.data6[index];
            this.formValidate.id = this.data6[index].id;
            console.log(this.changeSectionInfo.changeInfo);
            this.changeSectionInfo.show.state = !this.changeSectionInfo.show
                .state;
        },
        saveChangeSection(data) {
            this.formValidate = data;
            this.axios.put("/sections", this.modifications).then(res => {
                console.log(this.modifications);
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.showTable();
                    this.changeSectionInfo.show.state = !this.changeSectionInfo
                        .show.state;
                    this.$Message.success("修改成功！");
                }
            });
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
        handleCheckAll(section){
            if(section.indeterminate){
                section.checkAll = false
            } else {
                section.checkAll = !section.checkAll
            }
            section.indeterminate = false

            if(section.checkAll){
                section.areas.forEach(area=>{
                    section.checkAllGroup.push(area.name)
                })
            } else {
                section.checkAllGroup = []
            }
        },
        checkChanged(data){
            let curItem = this.batchCreate.sectionsInfo.find(item=>{
                return item.storeName == this.batchCreate.tabValue
            })
            let length = curItem.areas.length
            if(data.length == length){
                curItem.indeterminate = false
                curItem.checkAll = true
            }else if(data.length > 0){
                curItem.indeterminate = true
                curItem.checkAll = false
            } else {
                curItem.indeterminate = false
                curItem.checkAll = false
            }
        },
        visibleChanged(status){
            if(!status){
                this.batchCreate.tunnelId = null
                this.batchCreate.sectionsInfo = null
            }
        },
        save(){
            let res ={
                section: []
            }
            this.batchCreate.sectionsInfo.forEach(info=> {
                let tmp_section = {
                    storeId: info.storeId,
                    areaIds:[]
                }
                info.checkAllGroup.forEach(checked=> {
                    tmp_section.areaIds.push(info.areas.find(area=>area.name == checked).id)
                })
                res.section.push(tmp_section)
            })
            let _this = this
            TunnelService.batchCreateSections(res).then(
                result=>{
                    _this.$Message.info("添加成功！");
                    _this.showTable()
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




