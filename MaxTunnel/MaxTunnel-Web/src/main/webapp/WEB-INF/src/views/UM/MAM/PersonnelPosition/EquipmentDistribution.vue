<template>
    <div>
        <!-- <Row class="conditions">
            <Col span="9" offset="2">
                <span>访客姓名:</span>
                    <Input v-model="query.staffName" placeholder="请输入入廊人员姓名" style="width: 60%"></Input>
            </Col>
            <Col span="9">
                <span>设备名称：</span>
                    <Input v-model="query.equipmentName" placeholder="请输入设备名称" style="width: 60%"></Input>
            </Col>
            <Col span="3">
                <Button type="primary" @click="search" icon="ios-search">查询</Button>
            </Col>
        </Row>-->
        <div class="main">
            <Table :columns="columns" :data="distributionList"></Table>
        </div>
        <!--  <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle">
        </Page>-->
    </div>
</template>
<script>
import { personnelPositionService } from "../../../../services/personnelPositionService";

export default {
    data() {
        return {
            addEquipment: [],
            distributionList: [],
            visitorList: [],
            equipmentList: [],
            // query: {
            //     staffName: null,
            //     equipmentName: null
            // },
            curEquipment: null,
            columns: [
                {
                    title: "预计入廊时间",
                    key: "expectTime",
                    align: "center"
                },
                {
                    title: "流程状态",
                    key: "processStatus",
                    align: "center"
                },
                {
                    title: "入廊人数",
                    key: "number",
                    align: "center"
                },
                {
                    title: "访客姓名",
                    key: "username",
                    align: "center",
                    render: (h, params) => {
                        let usernames = [];
                        params.row.visitorList.forEach(visitor => {
                            usernames.push(
                                h(
                                    "div",
                                    {
                                        style: {
                                            height: "4vmin",
                                            overflow: "hidden",
                                            display: "flex",
                                            justifyContent: "center",
                                            alignItems: "center"
                                        }
                                    },
                                    [
                                        h("Icon", {
                                            props: {
                                                type: "person",
                                                color: "#f4ea2a"
                                            },
                                            style: {
                                                float: "left",
                                                margin: "0.2vmin 2vmin"
                                            }
                                        }),
                                        h("span", visitor.user.name)
                                    ]
                                )
                            );
                        });
                        return h("div", usernames);
                    }
                },
                {
                    title: "设备",
                    key: "equipmentName",
                    align: "center",
                    render: (h, params) => {
                        if (params.row.bindable) {
                            let equipments = [];
                            params.row.visitorList.forEach(visitor => {
                                if (visitor.equipmentName) {
                                    equipments.push(
                                        h(
                                            "div",
                                            {
                                                style: {
                                                    height: "4vmin",
                                                    overflow: "hidden",
                                                    display: "flex",
                                                    justifyContent: "center",
                                                    alignItems: "center"
                                                }
                                            },
                                            [h("span", visitor.equipmentName)]
                                        )
                                    );
                                } else {
                                    equipments.push(
                                        h(
                                            "div",
                                            {
                                                style: {
                                                    height: "4vmin",
                                                    overflow: "hidden",
                                                    display: "flex",
                                                    justifyContent: "center",
                                                    alignItems: "center"
                                                }
                                            },
                                            [
                                                h(
                                                    "Select",
                                                    {
                                                        props: {
                                                            value: this
                                                                .curEquipment,
                                                            "not-found-text":
                                                                "暂无可用设备"
                                                        },
                                                        style: {
                                                            width: "80%"
                                                        },
                                                        on: {
                                                            "on-change": event => {
                                                                this.handleAddEquipment(
                                                                    event,
                                                                    visitor.user
                                                                );
                                                            }
                                                        }
                                                    },
                                                    this.equipmentList.map(
                                                        item => {
                                                            return h(
                                                                "Option",
                                                                {
                                                                    props: {
                                                                        value:
                                                                            item.id,
                                                                        key:
                                                                            item.id
                                                                    }
                                                                },
                                                                item.name
                                                            );
                                                        }
                                                    )
                                                )
                                            ]
                                        )
                                    );
                                }
                            });
                            return h("div", equipments);
                        } else {
                            return;
                        }
                    }
                },
                {
                    title: "操作",
                    key: "equipmentId",
                    width: 200,
                    align: "center",
                    render: (h, params) => {
                        if (params.row.bindable) {
                            let options = [];
                            params.row.visitorList.forEach(visitor => {
                                if (visitor.equipmentId) {
                                    options.push(
                                        h(
                                            "div",
                                            {
                                                style: {
                                                    height: "4vmin",
                                                    overflow: "hidden",
                                                    display: "flex",
                                                    justifyContent: "center",
                                                    alignItems: "center"
                                                }
                                            },
                                            [
                                                h(
                                                    "Button",
                                                    {
                                                        props: {
                                                            type: "primary",
                                                            size: "small"
                                                        },
                                                        on: {
                                                            click: () => {
                                                                this.unbind(
                                                                    visitor.equipmentId
                                                                );
                                                            }
                                                        }
                                                    },
                                                    "解绑"
                                                )
                                            ]
                                        )
                                    );
                                } else {
                                    options.push(
                                        h(
                                            "div",
                                            {
                                                style: {
                                                    height: "4vmin",
                                                    overflow: "hidden",
                                                    display: "flex",
                                                    justifyContent: "center",
                                                    alignItems: "center"
                                                }
                                            },
                                            [
                                                h(
                                                    "Button",
                                                    {
                                                        props: {
                                                            type: "success",
                                                            size: "small"
                                                        },
                                                        on: {
                                                            click: () => {
                                                                this.bind(
                                                                    visitor.user
                                                                );
                                                            }
                                                        }
                                                    },
                                                    "绑定"
                                                )
                                            ]
                                        )
                                    );
                                }
                            });
                            return h("div", options);
                        } else {
                            return;
                        }
                    }
                }
            ],
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            pageStyle: {
                position: "absolute",
                bottom: "20px",
                right: "15px"
            }
        };
    },
    mounted() {
        this.getData();
    },
    methods: {
        getData() {
            let _this = this;
            Promise.all([
                personnelPositionService.getPersonsInfo(),
                personnelPositionService.getActiveLocators(),
                personnelPositionService.getAllLocators()
            ]).then(
                function(result) {
                    let visitorGroups = result[0];
                    let actLocators = result[1];
                    let locators = result[2];
                    _this.equipmentList = [];
                    locators.forEach(equ => {
                        let f = actLocators.find(a => {
                            return a.id == equ.id;
                        });
                        if (!f) {
                            let temp = {};
                            temp.id = equ.id;
                            temp.name = equ.name;
                            _this.equipmentList.push(temp);
                        }
                    });

                    _this.distributionList = [];
                    visitorGroups.forEach(visitors => {
                        let group = {};
                        group.expectTime = new Date(visitors.preTime).format(
                            "yyyy-MM-dd hh:mm:ss"
                        );
                        group.processStatus = visitors.processStatus;
                        group.bindable =
                            group.processStatus != "确认出廊" ? false : true;
                        group.number = visitors.visitorNumber;
                        group.visitorList = [];
                        visitors.list.forEach(visitor => {
                            let f = actLocators.find(a => {
                                if (a.owner == null) return false;
                                return a.owner.id == visitor.id;
                            });
                            let temp = {};
                            temp.user = visitor;
                            if (f) {
                                temp.equipmentId = f.id;
                                temp.equipmentName = f.name;
                            } else {
                                temp.equipmentId = null;
                                temp.equipmentName = "";
                            }
                            group.visitorList.push(temp);
                        });
                        _this.distributionList.push(group);
                    });
                    // console.log(_this.distributionList)
                },
                function(error) {
                    _this.Log.info(error);
                }
            );
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.getData();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.getData();
        },
        bind(userInfo) {
            let equ = this.addEquipment.find(add => {
                return add.user.id == userInfo.id;
            });
            if (!equ) {
                this.$Message.error({
                    content: "请选择设备",
                    duration: 5
                });
            } else {
                let _this = this;
                this.$Modal.confirm({
                    title: "确认",
                    content: "<p>确定绑定吗？</p>",
                    onOk: () => {
                        let params = {
                            user: equ.user
                        };
                        let _this = this;
                        personnelPositionService
                            .bindLocator(equ.equipmentId, params)
                            .then(
                                function(result) {
                                    _this.getData();
                                    _this.addEquipment = [];
                                    _this.curEquipment = null;
                                },
                                function(error) {
                                    _this.Log.info(error);
                                }
                            );
                    }
                });
            }
        },
        unbind(equipmentId) {
            let _this = this;
            this.$Modal.confirm({
                title: "确认",
                content: "<p>确定解绑吗？</p>",
                onOk: () => {
                    let _this = this;
                    personnelPositionService.unbindLocator(equipmentId).then(
                        function(result) {
                            _this.getData();
                        },
                        function(error) {
                            _this.Log.info(error);
                        }
                    );
                }
            });
        },
        handleAddEquipment(equId, userInfo) {
            let addFlag = true;
            this.addEquipment.forEach(equ => {
                if (equ.equipmentId === equId) {
                    equ.user = userInfo;
                    addFlag = false;
                    this.curEquipment = null;
                }
            });
            if (addFlag) {
                let bind = {
                    equipmentId: equId,
                    user: userInfo
                };
                this.addEquipment.push(bind);
            }
            // console.log(this.addEquipment)
        }
    }
};
</script>
<style scoped>
.conditions {
    width: 90vw;
    margin-left: 5vw;
}
.main {
    padding: 5%;
    padding-top: 10vh;
    width: 100%;
    height: 86vh;
    text-align: center;
    background: url("../../../../assets/UM/infoBox.png") no-repeat;
    background-size: 100% 100%;
}
.main >>> .ivu-select-selection {
    height: 3.2vmin;
}

.main >>> .ivu-select-placeholder {
    font-size: 1.28vmin;
    padding-top: 0.64vmin;
    height: 2.2vmin;
    line-height: 2vmin;
}
.main >>> .ivu-select-selected-value {
    font-size: 1.28vmin;
    padding-top: 0.64vmin;
    height: 2.2vmin;
    line-height: 2vmin;
}
.main >>> .ivu-select-dropdown {
    max-height: 20vmin !important;
}
.main .ivu-table-wrapper {
    border: none;
}
.main .ivu-table-wrapper >>> .ivu-table {
    color: #ffffff !important;
    background-color: #fffdfd00 !important;
}
.main .ivu-table-wrapper >>> .ivu-table th,
.ivu-table-wrapper >>> .ivu-table td {
    background-color: #fffdfd00 !important;
    border-bottom: 1px solid #7d7d7d;
}
.main .ivu-table-wrapper >>> .ivu-btn-primary,
.ivu-table-wrapper >>> .ivu-btn-info {
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border: none;
}
.main .ivu-table-wrapper >>> .ivu-table:before,
.main .ivu-table-wrapper >>> .ivu-table:after {
    background-color: #fffdfd00 !important;
}
</style>
