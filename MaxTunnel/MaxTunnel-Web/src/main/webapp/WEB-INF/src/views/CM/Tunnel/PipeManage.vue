<template>
    <div>
        <h1 style="text-align:center;margin-bottom:25px;margin-top:10px;">管廊管理</h1>
        <!-- 查找条件 -->
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
            <div>
                <span>管廊名称：</span>
                <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth" />
            </div>
            </Col>
            <Col span="6">
            <div>
                <span style="marginRight:12px">负责人：</span>
                <Select v-model="researchInfo.responsibilityId" placeholder="请选择负责人" class="inputWidth">
                    <Option value=null>不限</Option>
                    <Option v-for="(item,index) in staffs" :value="item.value" :key="index">{{item.label}}</Option>         
                </Select>
            </div>
            </Col>
            <Col span="6">
            <div>
                <span>建筑单位：</span>
                <Select v-model="researchInfo.constructId" placeholder="请选择建筑单位" class="inputWidth">
                    <Option value=null>不限</Option>
                    <Option v-for="(item,index) in companies" :value="item.value" :key="index">{{item.label}}</Option>
                </Select>
            </div>
            </Col>
            <Col span="6">
            <div>
                <span>运营单位：</span>
                <Select v-model="researchInfo.operationId" placeholder="请选择运营单位" class="inputWidth">
                    <Option value=null>不限</Option>
                    <Option v-for="(item,index) in companies" :value="item.value" :key="index">{{item.label}}</Option>
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
                    <Button type="error" size="small" @click="addNewPipe()">新增管廊</Button> 
                    <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                    <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>    
        </Row>
        <div style="margin: 20px;">
            <Table border ref="selection" :columns="columns7" :data="data6" @on-selection-change="startdelete"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
                    @on-change="handlePage" show-elevator :style="pageStyle"></Page>
        </div>
        <div>
            <pipe-module v-bind="addPipeInfo" v-on:listenToAdd="savePipe"></pipe-module>
        </div>
        <div>
            <pipe-modification v-bind="changePipeInfo" v-on:listenToChange="saveChangePipe"></pipe-modification>
        </div>
    </div>
</template>

<script>
import PipeModule from "../../CM/Tunnel/PipeModule.vue";
import PipeModification from "../../CM/Tunnel/PipeModification.vue";
export default {
    name: "pipe-manage",
    data() {
        return {
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
                    title: "管廊名",
                    key: "name",
                    align: "center"
                },
                {
                    title: "编号",
                    key: "sn",
                    align: "center"
                },
                {
                    title: "管廊长度",
                    key: "length",
                    align: "center"
                },
                {
                    title: "负责人",
                    key: "responsibility",
                    align: "center"
                },
                {
                    title: "建筑单位",
                    key: "construct",
                    align: "center"
                },
                {
                    title: "运营单位",
                    key: "operation",
                    align: "center"
                },
                {
                    title: "MaxView终端",
                    key: "maxviewConfigName",
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
                                            this.changeBarn(params.index);
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
            staffs: [],
            companies: [],
            researchInfo: {
                name: "",
                startTime: "",
                endTime: "",
                responsibilityId: null,
                constructId: null,
                operationId: null
            },
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            pageStyle: {
                position: "absolute",
                bottom: "20px",
                right: "15px"
            },
            formValidate: {
                id: 0,
                name: "",
                length: 0,
                responsibilityId: "",
                constructId: "",
                operationId: "",
                longitude: 0,
                latitude: 0,
                highness: 0
            },
            addPipeInfo: {
                show: { state: false },
                addInfo: {}
            },
            changePipeInfo: {
                show: { state: false },
                changeInfo: {}
            },
            deleteShow: false,
            deleteSelect: []
        };
    },
    mounted() {
        this.showTable();
        this.getStaffs();
        this.getCompanies();
    },
    computed: {
        researches() {
            // 查询
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.researchInfo.name,
                responsibilityId: this.researchInfo.responsibilityId,
                constructId: this.researchInfo.constructId,
                operationId: this.researchInfo.operationId,
                startTime: new Date(this.researchInfo.startTime).getTime(),
                endTime: new Date(this.researchInfo.endTime).getTime()
            };
            return Object.assign({}, param);
        },
        params() {
            // 新增
            let param = {
                name: this.formValidate.name,
                length: this.formValidate.length,
                responsibilityId: this.formValidate.responsibilityId,
                constructId: this.formValidate.constructId,
                operationId: this.formValidate.operationId,
                camera:
                    this.formValidate.longitude +
                    "," +
                    this.formValidate.latitude +
                    "," +
                    this.formValidate.highness,
                maxviewConfigId: this.formValidate.maxviewConfigId
            };
            return Object.assign({}, param);
        },
        modifications() {
            // 修改
            let modification = {
                id: this.formValidate.id,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.formValidate.name,
                length: this.formValidate.length,
                responsibilityId: this.formValidate.responsibilityId,
                constructId: this.formValidate.constructId,
                operationId: this.formValidate.operationId,
                camera:
                    this.formValidate.longitude +
                    "," +
                    this.formValidate.latitude +
                    "," +
                    this.formValidate.highness,
                maxviewConfigId: this.formValidate.maxviewConfigId
            };
            return Object.assign({}, modification);
        }
    },
    methods: {
        showTable() {
            this.axios.post("/tunnels/datagrid", this.researches).then(res => {
                let { code, data } = res.data;
                // console.log("get tunnels:", data);
                if (code == 200) {
                    let allinfo = [];
                    for (let index in data.list) {
                        let info = {};
                        info.id = data.list[index].id;
                        info.name = data.list[index].name;
                        info.length = data.list[index].length;
                        info.sn = data.list[index].sn;
                        if (data.list[index].responsibility != null) {
                            info.responsibility =
                                data.list[index].responsibility.name;
                            info.responsibilityId =
                                data.list[index].responsibility.id;
                        }
                        if (data.list[index].construct != null) {
                            info.construct = data.list[index].construct.name;
                            info.constructId = data.list[index].construct.id;
                        }
                        if (data.list[index].operation != null) {
                            info.operation = data.list[index].operation.name;
                            info.operationId = data.list[index].operation.id;
                        }
                        if (data.list[index].maxviewConfig) {
                            info.maxviewConfigName =
                                data.list[index].maxviewConfig.name;
                            info.maxviewConfigId =
                                data.list[index].maxviewConfig.id;
                        }
                        // console.log(data.list[index].camera);
                        if (data.list[index].camera != null) {
                            let str = data.list[index].camera.split(",");
                            info.longitude = str[0] == "null" ? "" : str[0];
                            info.latitude = str[1] == "null" ? "" : str[1];
                            info.highness = str[2] == "null" ? "" : str[2];
                        }
                        allinfo.push(info);
                    }
                    this.data6 = allinfo;
                    this.page.pageTotal = data.total;
                }
            });
        },
        addNewPipe() {
            this.addPipeInfo.show.state = !this.addPipeInfo.show.state;
        },
        savePipe(_data) {
            // 保存新管廊，插入到后端中
            this.formValidate = _data;
            this.axios.post("/tunnels", this.params).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.$Message.success("添加成功!");
                    this.addPipeInfo.show.state = !this.addPipeInfo.show.state;
                    this.showTable();
                }
            });
        },
        research() {
            this.showTable();
        },
        getStaffs() {
            this.axios.get("/staffs").then(res => {
                let { code, data } = res.data;
                let _staff = [];
                if (code == 200) {
                    for (let i = 0; i < data.length; i++) {
                        let staff = {};
                        staff.value = data[i].id;
                        staff.label = data[i].name;
                        _staff.push(staff);
                    }
                    this.staffs = _staff;
                }
            });
        },
        getCompanies() {
            this.axios.get("/companies").then(res => {
                let { code, data } = res.data;
                let _company = [];
                if (code == 200) {
                    for (let i = 0; i < data.length; i++) {
                        let company = {};
                        company.value = data[i].id;
                        company.label = data[i].name;
                        _company.push(company);
                    }
                }
                this.companies = _company;
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
        changeBarn(index) {
            this.changePipeInfo.changeInfo = this.data6[index];
            this.formValidate.id = this.data6[index].id;
            this.changePipeInfo.show.state = !this.changePipeInfo.show.state;
        },
        saveChangePipe(data) {
            // 保存修改，插入到后端中
            this.formValidate = data;
            this.axios.put("/tunnels", this.modifications).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.showTable();
                    this.changePipeInfo.show.state = !this.changePipeInfo.show
                        .state;
                    this.$Message.success("修改成功!");
                }
            });
        },
        handleSelectionChange(val) {
            this.deleteSelect = val;
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
                    this.axios.delete("/tunnels/batch/" + ids).then(res => {
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
        }
    },
    components: {
        PipeModule,
        PipeModification
    }
};
</script>

<style scoped>
.inputWidth {
    width: 60%;
}
</style>