<template>
    <!--管仓 -->
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">管仓管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
            <div>
                <span>管仓名称：</span>
                <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth" />
            </div>
            </Col>
            <Col span="6">
            <div>
                <span>所属管廊：</span>
                <Select v-model="researchInfo.tunnelId" placeholder="请选择所属管廊" class="inputWidth">
                    <Option value=null>不限</Option>
                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>         
                </Select>
            </div>
            </Col>
            <Col span="6">
            <div>
                <span>管仓类型：</span>
                <Select v-model="researchInfo.storeTypeId" placeholder="请选择管仓类型" class="inputWidth">
                    <Option value=null>不限</Option>
                    <Option v-for="item in types" :value="item.id" :key="item.id">{{item.name}}</Option>
                </Select>
            </div>
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
            <Col span="10">
                    <Button type="primary" size="small"  icon="ios-search" @click="research()">查询</Button>
                    <Button type="error" size="small" @click="addNewStore()">新增管仓</Button> 
                    <Button type="info" size="small" @click= "addMultiStores()">批量新增管仓</Button>
                    <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                    <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>    
        </Row>
        <div style="margin:20px;">
            <Table border ref="selection" :columns="columns7" :data="data6" @on-selection-change="startdelete"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
            @on-change="handlePage" show-elevator class="pageStyle"></Page>
        </div>
        <div>
            <barn-module v-bind="addStoreInfo" v-on:listenToAdd="saveStore"></barn-module>
        </div>
        <div>
            <barn-multi-module v-bind="addMultiStoreInfo" v-on:listenToAddMulti="saveMultiStore"></barn-multi-module>
        </div>
        <div>
            <barn-modification v-bind="changeStoreInfo" v-on:listenToChange="saveChangeStore"></barn-modification>
        </div>
    </div>
</template>

<script>
import BarnModule from "../../CM/Store/BarnModule.vue";
import BarnMultiModule from "../../CM/Store/BarnMultiModule.vue";
import BarnModification from "../../CM/Store/BarnModification.vue";
export default {
    name: "store-manage",
    data() {
        return {
            researchInfo: {
                name: "",
                tunnelId: null,
                storeTypeId: null,
                startTime: "",
                endTime: ""
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
                    title: "管仓名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "所属管廊",
                    key: "tunnelName",
                    align: "center"
                },
                {
                    title: "管仓类型",
                    key: "storeTypeName",
                    align: "center"
                },
                {
                    title: "类型编号",
                    key: "sn",
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
                                            this.editStore(params.index);
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
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            formValidate: {
                name: "",
                tunnelId: "",
                storeTypeId: "",
                camera: ""
            },
            addStoreInfo: {
                show: { state: false },
                addInfo: {}
            },
            addMultiStoreInfo: {
                show: { state: false },
                addInfo: {}
            },
            changeStoreInfo: {
                show: { state: false },
                changeInfo: {}
            },
            deleteShow: false,
            deleteSelect: []
        };
    },
    mounted() {
        this.showTable();
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
        }),
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
    computed: {
        params() {
            // 查询
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.researchInfo.name,
                tunnelId: this.researchInfo.tunnelId,
                storeTypeId: this.researchInfo.storeTypeId,
                startTime: new Date(this.researchInfo.startTime).getTime(),
                endTime: new Date(this.researchInfo.endTime).getTime()
            };
            return Object.assign({}, param);
        },
        newparams() {
            // 新增
            let param = {
                name: this.formValidate.name,
                tunnelId: this.formValidate.tunnelId,
                storeTypeId: this.formValidate.storeTypeId,
                camera: this.formValidate.camera
            };
            return Object.assign({}, param);
        },
        modifications() {
            // 修改
            let param = {
                id: this.formValidate.id,
                name: this.formValidate.name,
                tunnelId: this.formValidate.tunnelId,
                storeTypeId: this.formValidate.storeTypeId,
                camera: this.formValidate.camera
            };
            return Object.assign({}, param);
        }
    },
    methods: {
        showTable() {
            this.axios
                .post("/tunnels/stores/datagrid", this.params)
                .then(res => {
                    let { code, data } = res.data;
                    if (code == 200) {
                        let allinfo = [];
                        for (let index in data.list) {
                            let info = {};
                            info.id = data.list[index].id;
                            info.name = data.list[index].name;
                            info.sn = data.list[index].sn;
                            info.crtTime = new Date(
                                data.list[index].crtTime
                            ).format("yyyy-MM-dd hh:mm:s");
                            if (data.list[index].tunnel != null) {
                                info.tunnelId = data.list[index].tunnel.id;
                                info.tunnelName = data.list[index].tunnel.name;
                            }
                            if (data.list[index].storeType != null) {
                                info.storeTypeId =
                                    data.list[index].storeType.id;
                                info.storeTypeName =
                                    data.list[index].storeType.name;
                            }
                            if (data.list[index].camera != null) {
                                let str = data.list[index].camera.split(",");
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
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        addNewStore() {
            this.addStoreInfo.show.state = !this.addStoreInfo.show.state;
        },
        addMultiStores() {
            this.addMultiStoreInfo.show.state = !this.addMultiStoreInfo.show
                .state;
        },
        saveStore(_data) {
            this.formValidate = _data;
            this.axios.post("/stores", this.newparams).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.$Message.success("添加成功！");
                    this.addStoreInfo.show.state = !this.addStoreInfo.show
                        .state;
                    this.showTable();
                }
            });
        },
        saveMultiStore(_data) {
            this.formValidate = _data;
            console.log("get multi stores", _data);
            this.axios.post("stores/multi", _data).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.$Message.success("添加成功！");
                    this.addMultiStoreInfo.show.state = !this.addMultiStoreInfo
                        .show.state;
                    this.showTable();
                }
            });
        },
        editStore(index) {
            this.changeStoreInfo.changeInfo = this.data6[index];
            this.formValidate.id = this.data6[index].id;
            this.changeStoreInfo.show.state = !this.changeStoreInfo.show.state;
        },
        saveChangeStore(data) {
            this.formValidate = data;
            this.axios.put("/stores", this.modifications).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.showTable();
                    this.changeStoreInfo.show.state = !this.changeStoreInfo.show
                        .state;
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
        alldelete() {
            this.$Modal.confirm({
                title: "删除确认",
                content: "<p>确认要删除选中的信息吗？</p>",
                onOk: () => {
                    let ids = this.deleteSelect[0].id;
                    for (let i = 1; i < this.deleteSelect.length; i++) {
                        ids += "," + this.deleteSelect[i].id;
                    }
                    this.axios.delete("/stores/batch/" + ids).then(res => {
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
        BarnModule,
        BarnMultiModule,
        BarnModification
    }
};
</script>

<style scoped>
.inputWidth {
    width: 60%;
}
.pageStyle {
    text-align: right;
    margin-top: 100px;
    margin-right: 10px;
}
</style>




