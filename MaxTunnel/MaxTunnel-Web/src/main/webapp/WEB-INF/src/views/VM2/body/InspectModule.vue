<template>
    <div class="main" ref="main">
        <div class="title">
            <module-title :title="title"></module-title>
        </div>
        <div class="charts" ref="chart">
            <line-chart v-bind="corssBarChartData" ref="corssBarChar"></line-chart>
        </div>
        <div class="inspectCount">
            <ul class="taskLists">
                <li>
                    <div>
                        <i-circle
                            :percent="80"
                            :size="circleWidth"
                            stroke-linecap="square"
                            stroke-color="#00f6fc"
                            trail-color="#001D49"
                        >
                            <span class="demo-Circle-inner">{{taskCount.nowYearTaskCount}}个</span>
                        </i-circle>
                    </div>
                    <div>
                        <div class="countTitle">本年巡检任务</div>
                        <div class="iconBox">
                            <span>同比</span>
                            <Icon
                                :type="taskCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                                :color="taskCount.isRise ? '#ff0000': '#11d67c'"
                            ></Icon>
                        </div>
                    </div>
                </li>
                <li>
                    <div>
                        <i-circle
                            :percent="75"
                            :size="circleWidth"
                            stroke-linecap="square"
                            stroke-color="#11c0ff"
                            trail-color="#001E4A"
                        >
                            <span class="demo-Circle-inner">{{defectCount.nowYearDefectCount}}个</span>
                        </i-circle>
                    </div>
                    <div>
                        <div class="countTitle">本年缺陷</div>
                        <div class="iconBox">
                            <span>同比</span>
                            <Icon
                                :type="defectCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                                :color="defectCount.isRise ? '#ff0000': '#11d67c'"
                            ></Icon>
                        </div>
                    </div>
                </li>
                <li>
                    <div ref="circleBox">
                        <i-circle
                            :percent="62"
                            :size="circleWidth"
                            stroke-linecap="square"
                            stroke-color="#008bfe"
                            trail-color="#00204D"
                        >
                            <span class="demo-Circle-inner">{{maintenanceCount.nowYearOrderCount}}个</span>
                        </i-circle>
                    </div>
                    <div>
                        <div class="countTitle">本年维修</div>
                        <div class="iconBox">
                            <span>同比</span>
                            <Icon
                                :type="maintenanceCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                                :color="maintenanceCount.isRise ? '#ff0000': '#11d67c'"
                            ></Icon>
                        </div>
                    </div>
                </li>
                <li>
                    <div>
                        <i-circle
                            :percent="maintenanceRateCount.nowYearOrderPercentage"
                            :size="circleWidth"
                            stroke-linecap="square"
                            stroke-color="#2562e9"
                            trail-color="#002251"
                        >
                            <span
                                class="demo-Circle-inner"
                            >{{maintenanceRateCount.nowYearOrderPercentage}}%</span>
                        </i-circle>
                    </div>
                    <div>
                        <div class="countTitle">维修率</div>
                        <div class="iconBox">
                            <span>同比</span>
                            <Icon
                                :type="maintenanceCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                                :color="maintenanceCount.isRise ? '#ff0000': '#11d67c'"
                            ></Icon>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import ModuleTitle from "../../../components/VM2/ModuleTitle";
import LineChart from "../../../components/Common/Chart/StackChart";
import { InspectService } from "../../../services/inspectService";
import { converArrayFun } from "../../../scripts/commonFun";

export default {
    data() {
        return {
            title: "任务统计",
            taskCount: {
                nowYearTaskCount: 0,
                isRise: false
            },
            defectCount: {
                nowYearDefectCount: 0,
                isRise: false
            },
            maintenanceCount: {
                nowYearOrderCount: 0,
                isRise: false
            },
            maintenanceRateCount: {
                nowYearOrderPercentage: 0,
                isRise: false
            },
            corssBarChartData: {
                id: "corssBarChartID",
                title: "巡检缺陷维修统计",
                legendData: [],
                xData: [],
                seriesData: []
            },
            monthValArray1: [],
            monthValArray2: [],
            circleWidth: (window.innerHeight / 100) * 6.2
        };
    },
    components: {
        ModuleTitle,
        LineChart
    },
    mounted() {
        this.init();
        // this.refreshData();
        this.getCircleWidth();
        window.onresize = () => {
            return (() => {
                this.circleWidth = (window.innerHeight / 100) * 6.2;
            })();
        };
    },
    watch: {
        circleWidth(newVal, oldVal) {
            this.circleWidth = newVal;
        }
    },
    methods: {
        init() {
            let p1 = InspectService.getPatrolTaskCount();
            let p2 = InspectService.getDefectCount();
            let p3 = InspectService.getMaintenanceCount();
            Promise.all([p1, p2, p3]).then(res => {
                //获取今年的巡检任务总数
                this.taskCount.nowYearTaskCount = res[0].nowYearTaskCount;
                this.taskCount.isRise =
                    res[0].nowYearTaskCount >= res[0].beforeYearTaskCount
                        ? true
                        : false;

                //获取缺陷总数
                let defectCount1 = res[1].nowYearDefectCount;
                let defectCount2 = res[1].beforeYearDefectCount;
                this.defectCount.nowYearDefectCount = res[1].nowYearDefectCount;
                this.taskCount.isRise =
                    res[1].nowYearDefectCount >= res[1].beforeYearDefectCount
                        ? true
                        : false;

                //获取维修数
                let orderCount1 = res[2].nowYearOrderCount;
                let orderCount2 = res[2].beforeYearOrderCount;
                this.maintenanceCount.nowYearOrderCount =
                    res[2].nowYearOrderCount;
                this.maintenanceCount.isRise =
                    res[2].nowYearOrderCount >= res[2].beforeYearOrderCount
                        ? true
                        : false;

                //获取维修率 客户需求，模拟假数据，用于展示
                this.maintenanceRateCount.nowYearOrderPercentage = (
                    (orderCount1 / defectCount1) *
                    100
                ).toFixed(1);
                // this.maintenanceRateCount.nowYearOrderPercentage = 99.9;
                this.maintenanceRateCount.isRise =
                    orderCount1 / defectCount1 >= orderCount2 / defectCount2
                        ? true
                        : false;
            });
            //获取今年与去年的每月巡检计划数
            InspectService.getEveryMonthMaintenance().then(res => {
                var arr1 = [];
                var arr2 = [];

                this.corssBarChartData.xData.length && this.corssBarChartData.xData.splice(0);
                this.corssBarChartData.legendData.length && this.corssBarChartData.legendData.splice(0);
                this.corssBarChartData.seriesData.length && this.corssBarChartData.seriesData.splice(0);

                for (var i = 0; i < res.length; i++) {
                    this.corssBarChartData.xData.push(res[i].key);
                    for (var j = 0; j < res[i].val.length; j++) {
                        if (i == 0) {
                            this.corssBarChartData.legendData.push(
                                res[i].val[j].key
                            );
                        }
                        if (j == 0) {
                            arr1.push(res[i].val[j].val);
                        }
                        if (j == 1) {
                            arr2.push(res[i].val[j].val);
                        }
                    }
                }
                arr1 = arr1.reverse();
                arr2 = arr2.reverse();
                this.corssBarChartData.xData = this.corssBarChartData.xData.reverse();
                this.corssBarChartData.seriesData.push({ data: arr1 });
                this.corssBarChartData.seriesData.push({ data: arr2 });
            })
                .finally(() => {

                    setTimeout(() => {
                        this.init();
                    }, parseFloat(this.refreshTime));
                });
            // window.addEventListener('resize',this.getCircleWidth())
        },
        refreshData() {
            setInterval(() => {
                this.$refs.corssBarChar.drawBar();
            }, parseFloat(this.refreshTime));
        },
        getCircleWidth() {
            this.circleWidth = (window.innerHeight / 100) * 6.2;
        }
    }
};
</script>

<style scoped>
.main {
    width: 100%;
    height: 100%;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.main .title {
    width: 100%;
    height: 15%;
}
.charts {
    width: 100%;
    height: 50%;
    display: inline-block;
    vertical-align: top;
}
.inspectCount {
    width: 100%;
    height: 35%;
    display: inline-block;
    vertical-align: top;
}
.taskLists {
    list-style-type: none;
    height: 100%;
}
.taskLists > li {
    float: left;
    width: 25%;
    height: 100%;
    text-align: center;
}
.countTitle,
.iconBox {
    text-align: center;
    font-size: 1.3vmin;
    line-height: 2.2vmin;
}
.countTitle {
    color: #ffffff;
}
.iconBox {
    color: #999fac;
}
.demo-Circle-inner {
    color: #ffffff;
    font-size: 1.8vmin;
}
</style>
