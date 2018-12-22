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
        </Row> -->
        <div class="main">
            <Table border :columns="columns" :data="distributionList" :height="this.distributionList.length * 50 + 40 >　700 ? 700 : this.distributionList.length * 50 + 40"></Table>
        </div>
        <!--  <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle">
        </Page> -->
    </div>
</template>
<script>
import { personnelPositionService } from '../../../../services/personnelPositionService'

export default {
    data() {
        return {
            addEquipment: [],
            distributionList: [
                // {
                //     username: "章散",
                //     equipmentId: null
                // },
                // {
                //     username: "里斯",
                //     equipmentId: 1,
                //     equipmentName: "设备1"
                // },
                // {
                //     username: "王武",
                //     equipmentId: 3,
                //     equipmentName: "设备3"
                // },
                // {
                //     username: "李柳",
                //     equipmentId: 2,
                //     equipmentName: "设备2"
                // }
            ],
            visitorList: [],
            equipmentList: [
                // {
                //     id: 1,
                //     name: "设备1"
                // },
                // {
                //     id: 2,
                //     name: "设备2"
                // },
                // {
                //     id: 3,
                //     name: "设备3"
                // },
                // {
                //     id: 4,
                //     name: "设备4"
                // }
            ],
            query: {
                staffName: null,
                equipmentName: null
            },
            columns: [
                {
                    title: "访客姓名",
                    key: "username",
                    align: "center",
                    render: (h, params) => {
                        return h("div", [
                            h("Icon", {
                                props: {
                                    type: "person",
                                    color: "#ff9b00"
                                }
                            }),
                            h(
                                "span",
                                {
                                    style: {
                                        marginLeft: "20px"
                                    }
                                },
                                params.row.username
                            )
                        ]);
                    }
                },
                {
                    title: "设备",
                    key: "equipmentName",
                    align: "center",
                    render: (h, params) => {
                        if (params.row.equipmentName) {
                            return h("div", [
                                h("span", params.row.equipmentName),
                                h("div", {
                                    style: {
                                        float: "right"
                                    }
                                })
                            ]);
                        } else {
                            return h("div", [
                                h(
                                    "Select",
                                    {
                                        props: {
                                            value: this.curEquipment,
                                            "not-found-text": "暂无可用设备"
                                        },
                                        style: {
                                            width: "80%"
                                        },
                                        on: {
                                            "on-change": event => {
                                                this.handleAddEquipment(
                                                    event,
                                                    params.index
                                                );
                                            }
                                        }
                                    },
                                    this.equipmentList.map(item => {
                                        return h(
                                            "Option",
                                            {
                                                props: {
                                                    value: item.id,
                                                    key: item.id
                                                }
                                            },
                                            item.name
                                        );
                                    })
                                )
                            ]);
                        }
                    }
                },
                {
                    title: "操作",
                    key: "equipmentId",
                    width: 200,
                    align: "center",
                    render: (h, params) => {
                        if (params.row.equipmentId) {
                            return h(
                                "Button",
                                {
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    on: {
                                        click: () => {
                                            this.unbind(params.row.equipmentId);
                                        }
                                    }
                                },
                                "解绑"
                            );
                        } else {
                            return h(
                                "Button",
                                {
                                    props: {
                                        type: "success",
                                        size: "small"
                                    },
                                    on: {
                                        click: () => {
                                            this.bind(
                                                params.row.user,
                                                params.index
                                            );
                                        }
                                    }
                                },
                                "绑定"
                            );
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
            Promise.all([personnelPositionService.getPersonsInfo(),personnelPositionService.getActiveLocators(),
                personnelPositionService.getAllLocators()
                ]).then(
                    function(result){
                        let visitors = result[0]
                        let actLocators = result[1]
                        let locators = result[2]
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
                            visitors.forEach(visitor => {
                                let f = actLocators.find(a => {
                                    if (a.owner == null) return false;
                                    return a.owner.idCard == visitor.idCard;
                                });
                                let temp = {};
                                temp.username = visitor.name;
                                temp.user = visitor;
                                if (f) {
                                    temp.equipmentId = f.id;
                                    temp.equipmentName = f.name;
                                } else {
                                    temp.equipmentId = null;
                                    temp.equipmentName = "";
                                }
                                _this.distributionList.push(temp);
                            });
                    },
                    function(error){
                        console.log(error)
                    }
                )
            // axios
            //     .all([
            //         axios.get("req-historys/visitors"),
            //         axios.get("actived-locators"),
            //         axios.get("locators")
            //     ])
            //     .then(
            //         axios.spread(function(res1, res2, res3) {
            //             let visitors = res1.data.data;
            //             // console.log("visitors", visitors);
            //             let actLocators = res2.data.data;
            //             // console.log("actLocators", actLocators);
            //             let locators = res3.data.data;
            //             // console.log("locators", locators);

            //             if (
            //                 res1.data.code == 200 &&
            //                 res2.data.code == 200 &&
            //                 res3.data.code == 200
            //             ) {
            //                 //  获取没有使用的设备
            //                 _this.equipmentList = [];
            //                 locators.forEach(equ => {
            //                     let f = actLocators.find(a => {
            //                         return a.id == equ.id;
            //                     });
            //                     if (!f) {
            //                         let temp = {};
            //                         temp.id = equ.id;
            //                         temp.name = equ.name;
            //                         _this.equipmentList.push(temp);
            //                     }
            //                 });

            //                 // console.log("equipment", _this.equipmentList);

            //                 _this.distributionList = [];
            //                 visitors.forEach(visitor => {
            //                     let f = actLocators.find(a => {
            //                         if (a.owner == null) return false;
            //                         return a.owner.idCard == visitor.idCard;
            //                     });
            //                     let temp = {};
            //                     temp.username = visitor.name;
            //                     temp.user = visitor;
            //                     if (f) {
            //                         temp.equipmentId = f.id;
            //                         temp.equipmentName = f.name;
            //                     } else {
            //                         temp.equipmentId = null;
            //                         temp.equipmentName = "";
            //                     }
            //                     _this.distributionList.push(temp);
            //                 });

            //                 // console.log("distribution", _this.distributionList);
            //             }
            //         })
            //     );
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.getData();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.getData();
        },
        bind(user, index) {
            let param = {
                user: user
            };
            if (!this.addEquipment[index]) {
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
                        let _this = this
                        console.log(_this.addEquipment[index])
                        personnelPositionService.bindLocator(_this.addEquipment[index],param).then(
                            function(result){
                                _this.getData();
                                _this.addEquipment = [];
                            },
                            function(error){
                                console.log(error)
                            })
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
                    let _this = this
                    personnelPositionService.unbindLocator(equipmentId).then(
                        function(result){
                            _this.getData()
                        },
                        function(error){
                            console.log(error)
                        })
                }
            });
        },
        handleAddEquipment(equipmentId, index) {
            this.addEquipment[index] = equipmentId;
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
    margin-left: 5%;
    padding-top: 10vh;
    width: 90%;
    height: 88vh;
    text-align: center;
}
</style>
