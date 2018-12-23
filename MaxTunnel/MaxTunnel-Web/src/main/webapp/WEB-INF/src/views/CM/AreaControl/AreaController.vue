<template>
    <!--区域管理 -->
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">区域管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
            <div>
                <span>区域名称：</span>
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
            <Col span="10">
                <Button type="primary" size="small"  icon="ios-search" @click="research()">查询</Button>
                <Button type="error" size="small" @click="addNewArea()">新增区域</Button> 
                <Button type="info" size="small" @click="addMultiArea()">批量新增区域</Button> 
                <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>  
        </Row>
        <Row type="flex" align="middle" class="code-row-bg" style="marginLeft:25px;marginBottom:10px;">
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
        </Row>
        <Table border ref="selection" :columns="columns7" :data="data6" @on-selection-change="startdelete" style="margin:20px;"></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
              @on-change="handlePage" show-elevator class="pageStyle"></Page>
        <div>
            <area-module v-bind="addAreaInfo" v-on:listenToAdd="saveArea"></area-module>
        </div>
        <div>
            <area-multi-module v-bind="addMultiAreaInfo" v-on:listenToAddMulti="saveMultiArea"></area-multi-module>
        </div>
        <div>
            <area-modifiation v-bind="modificationAreaInfo" v-on:listenToChange="saveChangeArea"></area-modifiation>
        </div>
    </div>
</template>

<script>
import AreaModule from "../../../views/CM/AreaControl/AreaModule.vue";
import AreaMultiModule from "../../../views/CM/AreaControl/AreaMultiModule.vue";
import AreaModifiation from "../../../views/CM/AreaControl/AreaModification.vue";
export default {
    name: "barn-manage",
    data() {
        return {
            researchInfo: {
                name: null,
                tunnelId: null,
                startTime: null,
                endTime: null
            },
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
                    title: "区域名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "区域编号",
                    key: "sn",
                    align: "center"
                },
                {
                    title: "区域位置",
                    key: "location",
                    align: "center"
                },
                {
                    title: "所属管廊",
                    key: "tunnelName",
                    align: "center"
                },
                {
                    title: "经度",
                    key: "longitude",
                    align: "center"
                },
                {
                    title: "纬度",
                    key: "latitude",
                    align: "center"
                },
                {
                    title: "高度",
                    key: "highness",
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
                                            this.editArea(params.index);
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
                camera: null,
                startPoint: null,
                endPoint: null
            },
            ruleValidate: {},
            addAreaInfo: {
                show: { state: false },
                addInfo: {}
            },
            addMultiAreaInfo: {
                show: { state: false },
                addInfo: {}
            },
            modificationAreaInfo: {
                show: { state: false },
                modificationInfo: {}
            },
            deleteShow: false,
            deleteSelect: []
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
                camera: this.formValidate.camera
            };
            return Object.assign({}, param);
        },
        modifications() {
            let param = {
                id: this.formValidate.id,
                name: this.formValidate.name,
                tunnelId: this.formValidate.tunnelId,
                camera: this.formValidate.camera
            };
            return Object.assign({}, param);
        }
    },
    methods: {
        showTable() {
            this.axios.post("/areas/datagrid", this.researches).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    let allinfo = [];
                    console.log("areas", data);
                    for (let index in data.list) {
                        let info = {};
                        info.id = data.list[index].id;
                        info.name = data.list[index].name;
                        info.sn = data.list[index].sn;
                        info.crtTime = new Date(
                            data.list[index].crtTime
                        ).format("yyyy-MM-dd hh:mm:s");
                        if (data.list[index].tunnel != null) {
                            info.tunnelName = data.list[index].tunnel.name;
                            info.tunnelId = data.list[index].tunnel.id;
                        }
                        if (data.list[index].location != null) {
                            info.location = data.list[index].location;
                        }
                        if (data.list[index].camera != null) {
                            info.camera = data.list[index].camera;
                            let str = info.camera.split(",");
                            info.longitude = str[0];
                            info.latitude = str[1];
                            info.highness = str[2];
                        }
                        allinfo.push(info);
                    }
                    this.data6 = allinfo;
                    this.page.pageTotal = data.total;
                }
            });
        },
        addNewArea() {
            this.addAreaInfo.show.state = !this.addAreaInfo.show.state;
        },
        addMultiArea() {
            this.addMultiAreaInfo.show.state = !this.addMultiAreaInfo.show
                .state;
        },
        gettunnel() {
            this.axios.get("/tunnels").then(res => {
                let { code, data } = res.data;
                let _tunnels = [];
                if (code == 200) {
                    for (let i = 0; i < data.length; i++) {
                        let tunnel = {};
                        tunnel.id = data[i].id;
                        tunnel.name = data[i].name;
                        _tunnels.push(tunnel);
                    }
                    this.tunnels = _tunnels;
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
        saveArea(_data) {
            //保存新区域
            this.formValidate = _data;
            this.axios.post("/areas", this.params).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.$Message.success("添加成功！");
                    this.addAreaInfo.show.state = !this.addAreaInfo.show.state;
                    this.showTable();
                }
            });
        },
        saveMultiArea(_data) {
            console.log("新加区域", _data);
            //保存新区域
            this.axios.post("/areas/multi", _data).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.$Message.success("添加成功！");
                    this.addMultiAreaInfo.show.state = !this.addMultiAreaInfo
                        .show.state;
                    this.showTable();
                }
            });
        },
        editArea(index) {
            this.modificationAreaInfo.modificationInfo = this.data6[index];
            this.formValidate.id = this.data6[index].id;
            this.modificationAreaInfo.show.state = !this.modificationAreaInfo
                .show.state;
        },
        saveChangeArea(data) {
            this.formValidate = data;
            this.axios.put("/areas", this.modifications).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.showTable();
                    this.modificationAreaInfo.show.state = !this
                        .modificationAreaInfo.show.state;
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
                    this.axios.delete("/areas/batch/" + ids).then(res => {
                        let { code, data } = res.data;
                        if (code == 200) {
                            this.$Message.success("已删除");
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
        AreaModule,
        AreaModifiation,
        AreaMultiModule
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
.ivu-poptip {
    width: 100%;
}
</style>




