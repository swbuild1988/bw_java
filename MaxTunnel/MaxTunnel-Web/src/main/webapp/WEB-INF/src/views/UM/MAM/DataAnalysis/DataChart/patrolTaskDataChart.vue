<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <!-- <Col span="6">
                    <span>数据类型：</span>
                    <Select v-model="conditions.dataType" style="width: 60%;">
                        <Option v-for="(item,index) in dataTypes" :value="item.key" :key="index">{{ item.val }}</Option>
                    </Select>
                </Col> -->
                <Col span="6">
                    <span>所属管廊：</span>
                    <Select v-model="conditions.tunnelId" style="width:60%">
                        <Option v-for="(item,index) in tunnels" :value="item.id" :key="index">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span>周期：</span>
                    <Select v-model="conditions.sycle" style="width:60%" @on-change="changeCycle">
                        <Option v-for="(item,index) in sycles" :value="item.key" :key="index">{{ item.val }}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span>时间：</span>
                    <DatePicker v-model="conditions.time" type="datetimerange" placeholder="请选择"
                                :readonly="conditions.readonly" style="width:275px;"></DatePicker>
                </Col>
                <Col span="6">
                    <Button @click="query()">查询</Button>
                </Col>
            </Row>
        </div>
        <div style="margin-top: 10px;position: fixed">
            <Row :gutter="16">
                <Col span="13">
                    <Row :gutter="16">
                        <Col span="13">
                            <StackBar ref="stackBar" style="width: 46vw;height: 36vh;" v-bind="stacChart"></StackBar>
                        </Col>
                        <Col span="13">
                            <SimplelineChart style="width: 46vw;height: 36vh;" v-bind="lineChart"></SimplelineChart>
                        </Col>
                    </Row>    
                </Col>
                <Col span="11">
                    <Row>
                        <Col span="23">
                            <div>
                                <Table border stripe :columns="column" :data="tableData" :height="totalheight*0.72"></Table>
                            </div>
                            <div style="position: absolute; margin-bottom: 10px;float: right;right: 2px;">
                                <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
                                placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator ></Page> 
                            </div>
                        </Col>
                    </Row>
                </Col>
            </Row>
        </div>    
    </div>
</template>
<script>
import StackBar from "../../../../../components/Common/Chart/StackChart";
import SimplelineChart from "../../../../../components/Common/Chart/SimpleLineChart";
import { TunnelService } from "../../../../../services/tunnelService";
export default {
    components: {
        StackBar,
        SimplelineChart
    },
    data() {
        return {
            totalheight: document.documentElement.clientHeight,
            conditions: {
                tunnelId: null,
                dataType: null,
                sycle: null,
                time: null,
                readonly: false
            },
            page: {
                pageSize: 10,
                pageNum: 1,
                pageTotal: null
            },
            lineChart: {
                id: "lineChart",
                requestUrl: "lineChart",
                parameters: {
                    option: {
                        title: {
                            text: "11",
                            textStyle: {
                                color: "#030303"
                            }
                        }
                    },
                    timer: {
                        interval: 10000
                    }
                }
            },
            stacChart: {
                id: "stacChart",
                requestUrl: "stacChart",
                titleName: "",
                title: "22",
                titleColor: "#030303",
                intervalTime: 1000,
                tunnelId: null,
                timeCycle: null,
                xData: [],
                legendData: [
                    "未完成巡检计划",
                    "已完成巡检计划",
                    "未完成巡检任务",
                    "已完成巡检任务",
                    "未解决缺陷",
                    "已解决缺陷"
                ]
            },
            tunnels: [],
            dataTypes: [
                { id: 1, key: 1, val: "巡检任务" },
                { id: 2, key: 2, val: "缺陷" },
                { id: 3, key: 3, val: "维修工单" }
            ],
            sycles: [
                { key: 1, val: "最近一天" },
                { key: 2, val: "最近一周" },
                { key: 3, val: "最近一月" },
                { key: 4, val: "最近一年" },
                { key: 5, val: "自定义" }
            ],
            column: [
                {
                    title: "名称",
                    key: "name"
                },
                {
                    title: "位置",
                    render: (h, params) => {
                        let temp =
                            params.row.tunnelName +
                            params.row.area.name +
                            params.row.store.name;
                        return h("span", temp);
                    }
                },
                {
                    title: "创建时间",
                    key: "createTime",
                    render: (h, params) => {
                        return h(
                            "div",
                            new Date(params.row.createTime).format("yyyy-MM-dd")
                        );
                    }
                },
                {
                    title: "数量",
                    key: "num"
                }
            ],
            tableData: [
                {
                    id: 1,
                    name: "test",
                    tunnelName: "古城一街",
                    area: { id: 1, name: "一区" },
                    store: { id: 1, name: "燃气仓" },
                    createTime: new Date().getTime(),
                    num: 10
                }
            ]
        };
    },
    watch() {},
    mounted() {
        //获取管廊
        let _this = this;
        TunnelService.getTunnels().then(
            function(result) {
                _this.tunnels = result;
            },
            function(error) {
                console.log(error);
            }
        );
        // this.axios.get("/tunnels ").then(response => {
        //     let { code, data } = response.data;
        //     if (code == 200) {
        //         this.tunnels = data;
        //     }
        // });
    },
    methods: {
        query() {
            this.stacChart.tunnelId = this.conditions.tunnelId;
        },
        changeCycle(index) {
            const date = new Date();
            switch (index) {
                case 1: {
                    this.conditions.readonly = true;
                    let startTime = new Date(
                        date.valueOf() - 1 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        startTime.format("yyyy-MM-dd hh:mm:ss") +
                        "-" +
                        date.format("yyyy-MM-dd hh:mm:ss");
                    this.conditions.time = result;
                    break;
                }
                case 2: {
                    this.conditions.readonly = true;
                    let startTime = new Date(
                        date.valueOf() - 7 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        startTime.format("yyyy-MM-dd hh:mm:ss") +
                        "-" +
                        date.format("yyyy-MM-dd hh:mm:ss");
                    this.conditions.time = result;
                    this.getWeekTime(startTime, date, 7, 1);
                    break;
                }
                case 3: {
                    this.conditions.readonly = true;
                    let startTime = new Date(
                        date.valueOf() - 30 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        startTime.format("yyyy-MM-dd hh:mm:ss") +
                        "-" +
                        date.format("yyyy-MM-dd hh:mm:ss");
                    this.conditions.time = result;
                    break;
                }
                case 4: {
                    this.conditions.readonly = true;
                    let startTime = new Date(
                        date.valueOf() - 365 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        startTime.format("yyyy-MM-dd hh:mm:ss") +
                        "-" +
                        date.format("yyyy-MM-dd hh:mm:ss");
                    this.getWeekTime(startTime, date, 12, 30.4);
                    this.conditions.time = result;
                    break;
                }
                case 5: {
                    this.conditions.readonly = false;
                    this.conditions.time = null;
                    break;
                }
            }
        },
        //按周查询
        getWeekTime(start, end, k, days) {
            this.stacChart.xData = [];
            for (var i = 1; i <= k; i++) {
                if (start <= end) {
                    var time = new Date(
                        new Date(start).valueOf() +
                            days * (24 * 60 * 60 * 1000) * i
                    );
                    var weekDay;
                    if (k == 7) {
                        if (new Date(time).getDay() == 0) {
                            weekDay = "星期天";
                        } else {
                            weekDay = "星期" + new Date(time).getDay();
                        }
                    } else if (k == 12) {
                        weekDay = new Date(time).getMonth() + 1 + "月";
                    }
                    this.stacChart.xData.push(weekDay);
                }
            }
            this.$refs.stackBar.fetchData("stacChart");
        },
        mGetDate(year, month) {
            var d = new Date(year, month, 0);
            return d.getDate();
        },
        handlePage(value) {
            this.page.pageNum = value;
        },
        handlePageSize(value) {
            this.page.pageSize = value;
        }
    }
};
</script>
<style scoped>
</style>
