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
                <Select v-model="researchInfo.tunnelId" placeholder="请选择所属管廊" class="inputWidth">
                    <Option value="null">所有</Option>
                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>         
                </Select>
            </Col>
            <Col span="6">
                <Poptip placement="bottom" width="1000">
                    <span>所属管仓：</span>
                    <Input v-model="barnNameShow" placeholder="请选择管仓类型" class="inputWidth"/>
                    <div class="pop" slot="content">
                        <barn-choose v-on:listenToBarnChoose="getBarn"></barn-choose>
                    </div>
                </Poptip>
            </Col>
            <Col span="6">
                <span>所属区域：</span>
                <Select v-model="researchInfo.areaId" placeholder="请选择区域类型" class="inputWidth">
                    <Option value="null">所有</Option>
                    <Option v-for="item in types" :value="item.id" :key="item.id">{{item.name}}</Option>
                </Select>
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
    </div>
</template>

<script>
import BarnChoose from "../../../views/CM/Store/BarnChoose";
import SectionModule from "../../../views/CM/SectionControl/SectionModule";
import SectionModification from "../../../views/CM/SectionControl/SectionModification";
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
                    align: "center",
                    width: 60
                },
                {
                    title: "区段名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "可安装管线数",
                    key: "totalCableNumber",
                    align: "center",
                    width: 100
                },
                {
                    title: "所属管廊",
                    key: "tunnelId",
                    align: "center"
                },
                {
                    title: "所属管仓",
                    key: "storeName",
                    align: "center",
                    render: (h,params) => {
                        return h('div',params.row.store.name)
                    }
                },
                {
                    title: "所属区域",
                    key: "areaName",
                    align: "center",
                    width: 110,
                    render: (h,params) => {
                        return h('div',params.row.area.name)
                    }
                },
                {
                    title: "相机视角",
                    key: "camera",
                    align: "center",
                    render: (h,params) => {
                        if(params.row.camera!=null){
                            let str = params.row.camera.split(",");
                            let temp = str[0]
                            return h('div',temp)
                        }
                    }
                },
                {
                    title: "开始坐标",
                    key: "startPoint",
                    align: "center",
                    render: (h,params) => {
                        if(params.row.camera!=null){
                            let str = params.row.camera.split(",");
                            let temp = str[1]
                            return h('div',temp)
                        }
                    }
                },
                {
                    title: "结束坐标",
                    key: "endPoint",
                    align: "center",
                    render: (h,params) => {
                        if(params.row.camera!=null){
                            let str = params.row.camera.split(",");
                            let temp = str[2]
                            return h('div',temp)
                        }
                    }
                },
                {
                    title: "创建时间",
                    key: "crtTime",
                    align: "center",
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
            deleteSelect: []
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
                startTime: this.researchInfo.startTime,
                endTime: this.researchInfo.endTime
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
                    this.data6 = data.list;
                    this.page.pageTotal = data.total;
                }
            });
        },
        addNewSection() {
            this.addSectionInfo.show.state = !this.addSectionInfo.show.state;
        },
        create() {
            this.canCreate = false;
            this.axios.get("/sections/create").then(res => {
                this.canCreate = true;
            });
        },
        gettunnel() {
            this.axios.get("/tunnels").then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.tunnels = data;
                }
            });
        },
        getstoretype() {
            this.axios.get("/store-type/list").then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.types = data;
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
            this.changeSectionInfo.show.state = !this.changeSectionInfo.show
                .state;
        },
        saveChangeSection(data) {
            this.formValidate = data;
            this.axios.put("/sections", this.modifications).then(res => {
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
    margin-top: 20px;
    margin-right: 10px;
}
.ivu-poptip >>> .ivu-poptip-rel{
    width: 100%;
}
</style>




