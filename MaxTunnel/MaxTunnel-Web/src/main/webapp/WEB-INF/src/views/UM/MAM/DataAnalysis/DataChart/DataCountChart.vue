<template>
  <div>
    <div class="query">
      <Col span="6">
      <Row>
        <Col span="24">
        <label class="queryLabel">周期</label>
        <Select v-model="queryCondition.cycleType" style="width:14.5vw;" @on-change="changeCycle">
          <Option v-for="item in sycles" :value="item.key" :key="item.key">{{ item.val }}</Option>
        </Select>
        </Col>
        <Col span="24" style="margin-top: 10px;">
        <label class="queryLabel">时间</label>
        <DatePicker v-model="queryCondition.time" type="datetimerange" placeholder="请选择"
                    :readonly="queryCondition.readonly" style="width:14.5vw;"></DatePicker>
        </Col>
      </Row>
      </Col>
      <Col span="6">
      <Row>
        <Col span="24">
        <label class="queryLabel">数据类型</label>
        <Select v-model="queryCondition.objType" style="width:14.5vw; ">
          <Option v-for="item in dataType" :value="item.key" :key="item.key">{{ item.val }}</Option>
        </Select>
        </Col>
        <Col span="24" style="margin-top: 10px;">
        <label class="queryLabel">监测位置</label>
        <Select v-model="queryCondition.locations" multiple style="width:14.5vw;height: 3vh; ">
          <Option v-for="item in sycles" :value="item.key" :key="item.key">{{ item.val }}</Option>
        </Select>
        </Col>
      </Row>
      </Col>
      <Col span="11">
      <label class="queryLabel">对象</label>
      <Select v-model="queryCondition.objs" multiple style="width:32vw;height: 3vh; z-index: 10001">
        <Option v-for="item in sycles" :value="item.key" :key="item.key">{{ item.val }}</Option>
      </Select>
      </Col>
      <Col span="1" style="position: relative;float: right;right: 15px;margin-top: 15px;">
      <Button type="primary" icon="ios-search" @click="queryData">查询</Button>
      </Col>
      </Row>
    </div>
    <div style="margin-top: 10px;position: fixed">
      <Row :gutter="16">
        <Col span="13">
        <Row :gutter="16">

          <Col span="13">
          <ComplexBar style="width: 46vw;height: 36vh;" v-bind="barChart"></ComplexBar>
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
            <Page @on-change="changePage" :total="queryCondition.total"
                  show-total show-elevator :page-size="queryCondition.pageSize"></Page>
          </div>
          </Col>
        </Row>
        </Col>
      </Row>
    </div>
  </div>
</template>

<script>
import ComplexBar from "../../../../../components/Common/Chart/ComplexBarChart";
import SimplelineChart from "../../../../../components/Common/Chart/SimpleLineChart";

export default {
    name: "data-count-chart",
    data() {
        return {
            totalheight: document.documentElement.clientHeight,
            queryCondition: {
                total: 0,
                pageSize: 10,
                cycleType: null,
                time: null,
                objs: [],
                locations: [],
                objType: null,
                readonly: false,
                pageNum: 0
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
            barChart: {
                id: "barChart",
                requestUrl: "barChart",
                titleName: "",
                title: "22",
                titleColor: "#030303",
                intervalTime: 1000
            },
            column: [
                {
                    title: "时间",
                    key: "time"
                },
                {
                    title: "对象名称",
                    key: "name"
                },
                {
                    title: "位置",
                    key: "location"
                },
                {
                    title: "数值",
                    key: "value"
                }
            ],
            tableData: [
                {
                    name: "John Brown",
                    value: 18,
                    location: "古城大街电力仓区段1",
                    time: "2016-10-03"
                },
                {
                    name: " 小明",
                    value: 13,
                    location: "古城大街污水仓区段1",
                    time: "2016-10-02"
                },
                {
                    name: "John Brown",
                    value: 18,
                    location: "古城大街电力仓区段1",
                    time: "2016-10-03"
                },
                {
                    name: " 小明",
                    value: 13,
                    location: "古城大街污水仓区段1",
                    time: "2016-10-02"
                },
                {
                    name: "John Brown",
                    value: 18,
                    location: "古城大街电力仓区段1",
                    time: "2016-10-03"
                },
                {
                    name: " 小明",
                    value: 13,
                    location: "古城大街污水仓区段1",
                    time: "2016-10-02"
                },
                {
                    name: "John Brown",
                    value: 18,
                    location: "古城大街电力仓区段1",
                    time: "2016-10-03"
                },
                {
                    name: " 小明",
                    value: 13,
                    location: "古城大街污水仓区段1",
                    time: "2016-10-02"
                }
            ],
            sycles: [
                { key: 1, val: "最近一天" },
                { key: 2, val: "最近一周" },
                { key: 3, val: "最近一月" },
                { key: 4, val: "最近一年" },
                { key: 5, val: "自定义" }
            ],
            dataType: [
                { key: 1, val: "视频流" },
                { key: 2, val: "温度" },
                { key: 3, val: "湿度" },
                { key: 4, val: "甲烷浓度" },
                { key: 5, val: "应力" },
                { key: 6, val: "氧气" }
            ]
        };
    },
    mounted() {
        this.resize();
        window.addEventListener("resize", this.resize);
    },
    components: {
        ComplexBar,
        SimplelineChart
    },
    methods: {
        changeCycle(index) {
            const date = new Date();
            switch (index) {
                case 1: {
                    this.queryCondition.readonly = true;
                    let endTime = new Date(
                        date.valueOf() - 1 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        date.format("yyyy-MM-dd hh:mm:ss") +
                        " - " +
                        endTime.format("yyyy-MM-dd hh:mm:ss");
                    this.queryCondition.time = result;
                    break;
                }
                case 2: {
                    this.queryCondition.readonly = true;
                    let endTime = new Date(
                        date.valueOf() - 7 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        date.format("yyyy-MM-dd hh:mm:ss") +
                        " - " +
                        endTime.format("yyyy-MM-dd hh:mm:ss");
                    this.queryCondition.time = result;
                    break;
                }
                case 3: {
                    this.queryCondition.readonly = true;
                    let endTime = new Date(
                        date.valueOf() - 30 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        date.format("yyyy-MM-dd hh:mm:ss") +
                        " - " +
                        endTime.format("yyyy-MM-dd hh:mm:ss");
                    this.queryCondition.time = result;
                    break;
                }
                case 4: {
                    this.queryCondition.readonly = true;
                    let endTime = new Date(
                        date.valueOf() - 365 * 24 * 60 * 60 * 1000
                    );
                    let result =
                        date.format("yyyy-MM-dd hh:mm:ss") +
                        " - " +
                        endTime.format("yyyy-MM-dd hh:mm:ss");
                    this.queryCondition.time = result;
                    break;
                }
                case 5: {
                    this.queryCondition.readonly = false;
                    this.queryCondition.time = null;
                    break;
                }
            }
        },

        resize() {
            this.totalheight = document.documentElement.clientHeight;
        },

        changePage(index) {
            let _this = this;
            _this.queryCondition.pageNum = index;
            _this.queryData();
        },

        queryData() {
            let _this = this;
            let pram = {};
            pram.pageNum = _this.queryCondition.pageNum;
            _this.axios.post("", pram).then(response => {
                let { code, data } = response.data;
                if (code == 200) {
                }
            });
        }
    }
};
</script>

<style scoped>
.query {
    padding: 10px;
    margin-top: 4px;
    height: 90px;
    background-color: #fff;
}

.queryLabel {
    margin-right: 10px;
}
</style>
