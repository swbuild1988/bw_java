
<template>
    <!--管仓类型 -->
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">管仓类型管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
            <div>
                <span>管仓类型名：</span>
                <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth" />
            </div>
            </Col>
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
            <Col span="6">
                    <Button type="primary" size="small"  icon="ios-search" @click="research()">查询</Button>
                    <Button type="error" size="small" @click="addNewStoreType()">新增管仓类型</Button> 
                    <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                    <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>   
        </Row>
        <div style="margin:20px;">
            <Table border ref="selection" :columns="columns7" :data="data6" @on-selection-change="startdelete"></Table>
        </div>
        <div>
            <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
            @on-change="handlePage" show-elevator :style="pageStyle"></Page>
        </div>
        <div>
            <store-type-module v-bind="addStoreTypeInfo" v-on:listenToAdd="saveNewStoreType"></store-type-module>
        </div>
        <div>
            <store-type-modification v-bind="changeStoreTypeInfo" v-on:listenToChange="saveUpdateStoreType"></store-type-modification>
        </div>
    </div>
</template>

<script>
import StoreTypeModule from "../../CM/StoreTypeControl/StoreTypeModule.vue";
import StoreTypeModification from "../../CM/StoreTypeControl/StoreTypeModification.vue";
export default {
    name: "store-type-control",
    data() {
        return {
            researchInfo: {
                name: "",
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
                    title: "管仓类型名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "管仓类型编号",
                    key: "sn",
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
                                            this.editStoreType(params.index);
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
                name: ""
            },
            pageStyle: {
                position: "absolute",
                bottom: "35px",
                right: "40px"
            },
            addStoreTypeInfo: {
                show: { state: false },
                addInfo: {}
            },
            changeStoreTypeInfo: {
                show: { state: false },
                changeInfo: {}
            },
            deleteShow: false,
            deleteSelect: []
        };
    },
    mounted() {
        this.showTable();
    },
    computed: {
        params() {
            // 查询
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.researchInfo.name,
                startTime: new Date(this.researchInfo.startTime).getTime(),
                endTime: new Date(this.researchInfo.endTime).getTime()
            };
            return Object.assign({}, param);
        },
        newparams() {
            let param = {
                name: this.formValidate.name,
                sn: this.formValidate.sn
            };
            return Object.assign({}, param);
        },
        modifications() {
            let param = {
                id: this.formValidate.id,
                name: this.formValidate.name
            };
            return Object.assign({}, param);
        }
    },
    methods: {
        showTable() {
            this.axios.post("/store-type/datagrid", this.params).then(res => {
                let { code, data } = res.data;
                console.log("storeTypes:", data);
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
        handlePageSize(value) {
            this.page.pageSize = value;
            this.showTable();
        },
        addNewStoreType() {
            this.addStoreTypeInfo.show.state = !this.addStoreTypeInfo.show
                .state;
        },
        saveNewStoreType(data) {
            this.formValidate = data;
            this.axios.post("/store-type", this.newparams).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.$Message.success("添加成功！");
                    this.addStoreTypeInfo.show.state = !this.addStoreTypeInfo
                        .show.state;
                    this.showTable();
                }
            });
        },
        editStoreType(index) {
            this.changeStoreTypeInfo.changeInfo = this.data6[index];
            this.formValidate.id = this.data6[index].id;
            this.changeStoreTypeInfo.show.state = !this.changeStoreTypeInfo.show
                .state;
        },
        saveUpdateStoreType(data) {
            this.formValidate = data;
            this.axios.put("/store-type", this.modifications).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.showTable();
                    this.changeStoreTypeInfo.show.state = !this
                        .changeStoreTypeInfo.show.state;
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
                    this.axios.delete("/store-type/batch/" + ids).then(res => {
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
        StoreTypeModule,
        StoreTypeModification
    }
};
</script>

<style scoped>
.inputWidth {
    width: 60%;
}
</style>




