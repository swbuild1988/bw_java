<template>
    <div>
        <Row class="conditions">
            <Col span="20">
                <span>周期：</span>
                <Select v-model="query.cycleType" style="width:20vw;" @on-change="changeCycle">
                      <Option v-for="item in sycles" :value="item.key" :key="item.key">{{ item.val }}</Option>
                </Select>
            </Col>
           <!--  <Col span="10">
                <span>时间：</span>
                <DatePicker v-model="query.time" type="datetimerange" placeholder="请选择"  :readonly="query.readonly" style="width:20vw;"></DatePicker>
            </Col> -->
            <!-- <Col span="6">
                <span>单位：</span>
                <Select v-model="query.period" style="width:14vw;" @on-change="changePeriod">
                      <Option v-for="item in periodList" :value="item.val" :key="item.val">{{ item.key }}</Option>
                </Select>
            </Col> -->
            <Col span="2">
                <Button type="primary" icon="ios-search" @click="getData">查询</Button>
            </Col>
            <Col span="2">
                <Button type="ghost" @click="exportTable">导出</Button>
            </Col>
        </Row>
        <Row class="list">
            <Col span="12" style="height: 74vh">
                <Table :columns="columns" :data="data" border ref="table"></Table>
                <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle">
                </Page>
            </Col>
            <Col span="12">
                   <!--  <div id="eCReportBar" style="height:100%"></div> -->
                  <pile-bar-chart v-bind="pileBarChart" style="width: 42vw;height: 36vh;"></pile-bar-chart>
                  <div id="eCReportLine" style="width: 42vw;height: 36vh;"></div>
            </Col>
        </Row>
    </div>
</template>

<script>
import pileBarChart from "../../../../../components/Common/Chart/PileBarChart";

export default {
    name: "energyConsumptionReport",
    data() {
        return {
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
            query: {
                cycleType: null,
                time: null,
                readonly: false
            },
            columns: [
                {
                    title: "管廊",
                    // fixed: 'left',
                    key: "tunnelName"
                },
                {
                    title: "类别",
                    // fixed: 'left',
                    key: "category"
                },
                {
                    title: "时间",
                    width: 200,
                    key: "date",
                    sortable: true
                },
                {
                    title: "数据(千瓦时)",
                    key: "val",
                    sortable: true
                }
            ],
            data: [
                {
                    tunnelName: "古城大街",
                    category: "风机类电表",
                    date: "2018-08-10",
                    val: 2049
                },
                {
                    tunnelName: "古城大街",
                    category: "灯类电表",
                    date: "2018-08-11",
                    val: 2090
                },
                {
                    tunnelName: "古城大街",
                    category: "水泵类电表",
                    date: "2018-08-12",
                    val: 902
                },
                {
                    tunnelName: "古城大街",
                    category: "其他类电表",
                    date: "2018-08-13",
                    val: 1032
                }
            ],
            // columns:
            //   [
            //     {
            //         title: '管廊',
            //         fixed: 'left',
            //         key: 'tunnelName',
            //         render: (h,params)=>{
            //             return h('span',params.row.tunnelName)
            //         }
            //     },
            //     {
            //         title: '类别',
            //         fixed: 'left',
            //         key: 'categories.title',
            //         render: (h,params)=>{
            //             let length = params.row.value[0].data.length
            //             let a = params.row.value
            //             let b = []
            //             a.map((val,index) => {
            //                 b.push(h('div', {
            //                     style: {
            //                         padding: '4px',
            //                         height: length * 32 + 'px'
            //                     }
            //                 },val.category))
            //                 if (a.length !== index + 1) {
            //                   b.push(h('hr', {
            //                       style: {
            //                           backgroundColor: '#eee',
            //                           height: '1px',
            //                           border: 'none'
            //                       }
            //                   }))
            //                 }
            //             })
            //             return b
            //           }

            //     },
            //     {
            //         // renderHeader: (h,params)=>{
            //         //     return h('span',this.data[params.index - 2].data[0].title)
            //         // },
            //         title: '时间',
            //         render: (h,params)=>{
            //             let data = params.row.value
            //             let months = []
            //             data.map((val,index)=>{
            //                 val.data.map(month=>{
            //                     months.push(h('div', {
            //                       style: {
            //                           padding: '4px'
            //                       }
            //                     },month.month))
            //                     months.push(h('hr', {
            //                         style: {
            //                             backgroundColor: '#ddd',
            //                             height: '1px',
            //                             border: 'none'
            //                         }
            //                     }))
            //                 })
            //                 months.push(h('hr', {
            //                       style: {
            //                           backgroundColor: '#eee',
            //                           height: '1px',
            //                           border: 'none'
            //                       }
            //                 }))
            //             })
            //             return months
            //         }
            //     },
            //     {
            //         title: '数据',
            //         render: (h,params)=>{
            //             let data = params.row.value
            //             let nums = []
            //             data.map((val,index)=>{
            //                 val.data.map(num=>{
            //                     nums.push(h('div', {
            //                       style: {
            //                           padding: '4px'
            //                       }
            //                     },num.value))
            //                     nums.push(h('hr', {
            //                         style: {
            //                             backgroundColor: '#ddd',
            //                             height: '1px',
            //                             border: 'none'
            //                         }
            //                     }))
            //                 })
            //                 nums.push(h('hr', {
            //                       style: {
            //                           backgroundColor: '#eee',
            //                           height: '1px',
            //                           border: 'none'
            //                       }
            //                 }))
            //             })
            //             return nums
            //         }
            //     }
            //   ],
            // data: [
            //     {
            //         tunnelId: 1,
            //         tunnelName: '古城大街',
            //         value: [
            //             {
            //                 category: '风机类电表',
            //                 data: [{
            //                     month: '一月',
            //                     value: 12334
            //                 },{
            //                     month: '二月',
            //                     value: 353636
            //                 },{
            //                     month: '三月',
            //                     value: 34325
            //                 },{
            //                     month: '四月',
            //                     value: 453637
            //                 },]
            //             },
            //             {
            //                 category: '灯类电表',
            //                 data: [{
            //                     month: '一月',
            //                     value: 12334
            //                 },{
            //                     month: '二月',
            //                     value: 353636
            //                 },{
            //                     month: '三月',
            //                     value: 34325
            //                 },{
            //                     month: '四月',
            //                     value: 453637
            //                 },]
            //             },
            //             {
            //                 category: '水泵类电表',
            //                 data: [{
            //                     month: '一月',
            //                     value: 12334
            //                 },{
            //                     month: '二月',
            //                     value: 353636
            //                 },{
            //                     month: '三月',
            //                     value: 34325
            //                 },{
            //                     month: '四月',
            //                     value: 453637
            //                 },]
            //             },
            //             {
            //                 category: '其他类电表',
            //                 data: [{
            //                     month: '一月',
            //                     value: 12334
            //                 },{
            //                     month: '二月',
            //                     value: 353636
            //                 },{
            //                     month: '三月',
            //                     value: 34325
            //                 },{
            //                     month: '四月',
            //                     value: 453637
            //                 },]
            //             }
            //         ],
            //     },
            //     {
            //         tunnelId: 2,
            //         tunnelName: '实验路',
            //         value: [
            //             {
            //                 category: '风机类电表',
            //                 data: [{
            //                     month: '一月',
            //                     value: 12334
            //                 },{
            //                     month: '二月',
            //                     value: 353636
            //                 },{
            //                     month: '三月',
            //                     value: 34325
            //                 },{
            //                     month: '四月',
            //                     value: 453637
            //                 },]
            //             },
            //             {
            //                 category: '灯类电表',
            //                 data: [{
            //                     month: '一月',
            //                     value: 12334
            //                 },{
            //                     month: '二月',
            //                     value: 353636
            //                 },{
            //                     month: '三月',
            //                     value: 34325
            //                 },{
            //                     month: '四月',
            //                     value: 453637
            //                 },]
            //             },
            //             {
            //                 category: '水泵类电表',
            //                 data: [{
            //                     month: '一月',
            //                     value: 12334
            //                 },{
            //                     month: '二月',
            //                     value: 353636
            //                 },{
            //                     month: '三月',
            //                     value: 34325
            //                 },{
            //                     month: '四月',
            //                     value: 453637
            //                 },]
            //             },
            //             {
            //                 category: '其他类电表',
            //                 data: [{
            //                     month: '一月',
            //                     value: 12334
            //                 },{
            //                     month: '二月',
            //                     value: 353636
            //                 },{
            //                     month: '三月',
            //                     value: 34325
            //                 },{
            //                     month: '四月',
            //                     value: 453637
            //                 },]
            //             }
            //         ]
            //     }
            // ],
            sycles: [
                // {key: 1, val: "最近一天"},
                { key: 2, val: "最近一周" },
                { key: 3, val: "最近一月" },
                { key: 4, val: "最近一年" }
                // {key: 5, val: "自定义"}
            ],
            lineChart: null,
            periodList: [],
            // lineChart: {
            //   id: 'eCReportLine',
            //   requestUrl: 'lineChart',
            //   titleName: '23',
            //   title: "11",
            //   titleColor: '#030303'
            // },
            pileBarChart: {
                id: "eCReportBar",
                xAxisData: [
                    "周一",
                    "周二",
                    "周三",
                    "周四",
                    "周五",
                    "周六",
                    "周日"
                ],
                data: [
                    {
                        tunnelName: "古城大街",
                        category: "风机类电表",
                        value: [320, 332, 301, 334, 390, 330, 320]
                    },
                    {
                        tunnelName: "古城大街",
                        category: "灯类电表",
                        value: [320, 432, 301, 334, 490, 330, 410]
                    },
                    {
                        tunnelName: "古城大街",
                        category: "水泵类电表",
                        value: [220, 182, 191, 234, 290, 330, 310]
                    },
                    {
                        tunnelName: "古城大街",
                        category: "其他类电表",
                        value: [150, 232, 201, 154, 190, 330, 410]
                    },
                    {
                        tunnelName: "实验路",
                        category: "风机类电表",
                        value: [862, 1018, 964, 1026, 1679, 1600, 1570]
                    },
                    {
                        tunnelName: "实验路",
                        category: "灯类电表",
                        value: [620, 732, 701, 734, 1090, 1130, 1120]
                    },
                    {
                        tunnelName: "实验路",
                        category: "水泵类电表",
                        value: [120, 132, 101, 134, 290, 230, 220]
                    },
                    {
                        tunnelName: "实验路",
                        category: "其他类电表",
                        value: [60, 72, 71, 74, 190, 130, 110]
                    }
                ]
            },
            lineChartData: {
                legendData: [
                    "风机类电表",
                    "灯类电表",
                    "水泵类电表",
                    "其他类电表"
                ],
                xAxisData: [
                    "周一",
                    "周二",
                    "周三",
                    "周四",
                    "周五",
                    "周六",
                    "周日"
                ],
                data: [
                    {
                        name: "风机类电表",
                        type: "line",
                        data: [120, 132, 101, 134, 90, 230, 210]
                    },
                    {
                        name: "灯类电表",
                        type: "line",
                        data: [220, 182, 191, 234, 290, 330, 310]
                    },
                    {
                        name: "水泵类电表",
                        type: "line",
                        data: [150, 232, 201, 154, 190, 330, 410]
                    },
                    {
                        name: "其他类电表",
                        type: "line",
                        data: [320, 332, 301, 334, 390, 330, 320]
                    }
                ]
            }
        };
    },
    components: { pileBarChart },
    mounted() {
        // this.getData()
        this.drawChart();
    },
    watch: {},
    methods: {
        getData() {
            // this.axios.get('time-enums').then(res=>{
            //   let { code,data } = res.data
            //   this.periodList = data
            // })
            let queryTime = null;
            switch (this.query.cycleType) {
                case 2:
                    queryTime = "one-week";
                    break;
                case 3:
                    queryTime = "one-month";
                    break;
                case 4:
                    queryTime = "one-year";
                    break;
            }
            let param = {
                startTime: new Date().getTime(),
                pageSize: this.page.pageSize,
                pageNum: this.page.pageNum
            };
            this.axios
                .post("energies/" + queryTime + "/datagrid", param)
                .then(res => {
                    let { code, data } = res.data;
                    this.data = [];
                    if (this.query.cycleType == 2) {
                        this.data = data.pagedList;
                        this.data.forEach(time => {
                            time.date = new Date(time.date).format(
                                "yyyy-MM-dd"
                            );
                        });
                    } else {
                        data.pagedList.forEach(time => {
                            let temp = {};
                            temp.tunnelName = time.tunnelName;
                            temp.category = time.category;
                            temp.date =
                                new Date(time.start).format("yyyy-MM-dd") +
                                " ~ " +
                                new Date(time.end).format("yyyy-MM-dd");
                            temp.val = time.val;
                            this.data.push(temp);
                        });
                    }

                    this.page.pageTotal = data.total;
                });
            let startTime = param.startTime.toString().slice(0, 10);
            this.axios
                .get("energies/" + queryTime + "/" + startTime)
                .then(res => {
                    let { code, data } = res.data;
                    this.pileBarChart.xAxisData = [];
                    this.pileBarChart.data = [];
                    data[0].data.forEach(time => {
                        let date = null;
                        if (this.query.cycleType == 2) {
                            date = new Date(time.date).format("yyyy-MM-dd");
                        } else {
                            date =
                                new Date(time.start).format("yyyy-MM-dd") +
                                " ~ " +
                                new Date(time.end).format("yyyy-MM-dd");
                        }
                        this.pileBarChart.xAxisData.push(date);
                    });

                    data.forEach(val => {
                        let temp = {};
                        temp.tunnelName = val.tunnelName;
                        temp.category = val.category;
                        temp.value = [];
                        val.data.forEach(number => {
                            temp.value.push(number.val);
                        });
                        this.pileBarChart.data.push(temp);
                    });
                });
            this.axios
                .get("energies/" + queryTime + "01/" + startTime)
                .then(res => {
                    let { code, data } = res.data;
                    this.lineChartData.legendData = [];
                    this.lineChartData.xAxisData = [];
                    this.lineChartData.data = [];
                    data.filter(cat => {
                        this.lineChartData.legendData.push(cat.category);
                        let temp = {};
                        temp.name = cat.category;
                        temp.type = "line";
                        temp.data = [];
                        cat.data.filter(data => {
                            temp.data.push(data.val);
                        });
                        this.lineChartData.data.push(temp);
                    });
                    if (this.query.cycleType == 2) {
                        data[0].data.filter(time => {
                            let date = new Date(time.date).format("yyyy-MM-dd");
                            this.lineChartData.xAxisData.push(date);
                        });
                    } else {
                        data[0].data.filter(time => {
                            let date =
                                new Date(time.start).format("yyyy-MM-dd") +
                                " ~ " +
                                new Date(time.end).format("yyyy-MM-dd");
                            this.lineChartData.xAxisData.push(date);
                        });
                    }

                    this.drawChart();
                });
        },
        exportTable() {
            this.$refs.table.exportCsv({
                filename: "能耗报表"
            });
        },
        changePeriod() {
            // this.lineChart.period = this.period
        },
        changeCycle(index) {
            const date = new Date();
            switch (index) {
                case 1: {
                    this.query.readonly = true;
                    let endTime = new Date(
                        date.valueOf() - 1 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        date.format("yyyy-MM-dd hh:mm:ss") +
                        " - " +
                        endTime.format("yyyy-MM-dd hh:mm:ss");
                    this.query.time = result;
                    break;
                }
                case 2: {
                    this.query.readonly = true;
                    let endTime = new Date(
                        date.valueOf() - 7 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        date.format("yyyy-MM-dd hh:mm:ss") +
                        " - " +
                        endTime.format("yyyy-MM-dd hh:mm:ss");
                    this.query.time = result;
                    break;
                }
                case 3: {
                    this.query.readonly = true;
                    let endTime = new Date(
                        date.valueOf() - 30 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        date.format("yyyy-MM-dd hh:mm:ss") +
                        " - " +
                        endTime.format("yyyy-MM-dd hh:mm:ss");
                    this.query.time = result;
                    break;
                }
                case 4: {
                    this.query.readonly = true;
                    let endTime = new Date(
                        date.valueOf() - 365 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        date.format("yyyy-MM-dd hh:mm:ss") +
                        " - " +
                        endTime.format("yyyy-MM-dd hh:mm:ss");
                    this.query.time = result;
                    break;
                }
                case 5: {
                    this.query.readonly = false;
                    this.query.time = null;
                    break;
                }
            }
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.getData();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.getData();
        },
        drawChart() {
            this.lineChart = this.$echarts.init(
                document.getElementById("eCReportLine")
            );
            // _this.myChart.showLoading();

            let lineOption = {
                title: {
                    text: "类别总能耗"
                },
                tooltip: {
                    trigger: "axis"
                },
                legend: {
                    data: this.lineChartData.legendData
                },
                grid: {
                    left: "3%",
                    right: "4%",
                    bottom: "3%",
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: "category",
                    boundaryGap: false,
                    data: this.lineChartData.xAxisData
                },
                yAxis: {
                    type: "value"
                },
                series: this.lineChartData.data
            };

            this.lineChart.setOption(lineOption);
        }
    }
};
</script>

<style scoped>
</style>
