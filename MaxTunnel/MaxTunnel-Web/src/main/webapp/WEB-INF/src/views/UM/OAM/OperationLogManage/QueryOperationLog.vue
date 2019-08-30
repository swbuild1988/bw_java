<template>
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">系统日志管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="4">
                <span>模块类型：</span>
                <Input
                    v-model="conditions.moduleType"
                    placeholder="请输入模块类型"
                    class="inputWidth"
                    type="text"
                />
            </Col>
            <Col span="4">
                <span>操作人姓名：</span>
                <Input
                    v-model="conditions.reqUser"
                    placeholder="请输入操作人姓名"
                    class="inputWidth"
                    type="text"
                />
            </Col>
            <Col span="4">
                <span>请求结果：</span>
                <Select v-model="conditions.result" class="inputWidth">
                    <Option v-for="item in result" :value="item.name" :key="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="4">
                <span>开始时间：</span>
                <DatePicker
                    type="datetime"
                    placeholder="请选择开始时间"
                    class="inputWidth"
                    v-model="conditions.startTime"
                ></DatePicker>
            </Col>
            <Col span="4">
                <span>结束时间：</span>
                <DatePicker
                    type="datetime"
                    placeholder="请选择结束时间"
                    class="inputWidth"
                    v-model="conditions.endTime"
                ></DatePicker>
            </Col>
            <Col span="4">
                <Button type="primary" icon="ios-search" @click="queryOperationLog">查询</Button>
            </Col>
        </Row>

        <div style="margin:20px;">
            <Table border stripe :columns="columns1" :data="operationLog"></Table>
            <Page
                :total="page.pageTotal"
                :current="page.pageNum"
                :page-size="page.pageSize"
                show-sizer
                show-total
                placement="top"
                @on-change="handlePage"
                @on-page-size-change="handlePageSize"
                show-elevator
                style="position: absolute;bottom: 20px; right: 15px;"
            ></Page>
        </div>
    </div>
</template>
<script>
import { OperationLogService } from "../../../../services/operationLogService";

export default {
    data() {
        return {
            conditions: {
                moduleType: null,
                reqUser: null,
                result: null,
                startTime: null,
                endTime: null
            },
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            result: [
                {
                    id: 0,
                    name: "成功"
                },
                {
                    id: 1,
                    name: "失败"
                }
            ],
            operationLog: [],
            columns1: [
                {
                    type: "index",
                    align: "center",
                    width: 100
                },
                {
                    title: "模块类型",
                    key: "moduleType",
                    align: "center"
                },
                {
                    title: "请求IP",
                    key: "reqIp",
                    align: "center"
                },
                {
                    title: "请求人",
                    key: "reqUser",
                    align: "center"
                },
                {
                    title: "请求结果",
                    key: "result",
                    align: "center"
                },
                {
                    title: "操作描述",
                    key: "desc",
                    align: "center",
                    width: 400
                },
                {
                    title: "操作时间",
                    key: "crtTime",
                    align: "center"
                },
                {
                    title: "",
                    width: 200,
                    align: "center",
                    render: (h, params) => {
                        return (
                            "div",
                            [
                                h(
                                    "Button",
                                    {
                                        props: {
                                            type: "error",
                                            size: "small"
                                        },
                                        on: {
                                            click: () => {
                                                this.del(params.row.id);
                                            }
                                        }
                                    },
                                    "删除"
                                )
                            ]
                        );
                    }
                }
            ]
        };
    },
    mounted() {
        this.queryOperationLog();
    },
    computed: {
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                moduleType: this.conditions.moduleType,
                reqUser: this.conditions.reqUser,
                startTime: new Date(this.conditions.startTime).getTime(),
                endTime: new Date(this.conditions.endTime).getTime(),
                result:
                    this.conditions.result === "null"
                        ? null
                        : this.conditions.result
            };
            return Object.assign({}, param);
        }
    },
    methods: {
        queryOperationLog() {
            let _this = this;
            if (
                new Date(_this.conditions.startTime) >
                new Date(_this.conditions.endTime)
            ) {
                _this.$Message.error("开始时间必须小于结束时间！");
                return;
            }
            OperationLogService.logDatagrid(_this.params).then(
                result => {
                    _this.operationLog = [];
                    if (result.list != null) {
                        for (let index in result.list) {
                            result.list[index].crtTime = new Date(
                                result.list[index].crtTime
                            ).format("yyyy-MM-dd hh:mm:s");
                        }
                        _this.operationLog = result.list;
                        _this.page.pageTotal = result.total;
                    }
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryOperationLog();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryOperationLog();
        },
        del(id) {
            this.$Modal.confirm({
                title: "操作日志",
                content: "<p>是否删除这条操作记录</p>",
                onOk: () => {
                    let _this = this;
                    OperationLogService.deleteLog(id).then(
                        result => {
                            _this.operationLog.splice(id, 1);
                        },
                        error => {
                            _this.Log.info(error);
                        }
                    );
                    this.queryOperationLog();
                },
                onCancel: () => {}
            });
        }
    }
};
</script>
<style scoped>
.inputWidth {
    width: 60%;
}
</style>
