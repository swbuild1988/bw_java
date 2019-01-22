<template>
    <div class="main">
        <div class="title">
            <module-title :title="title"></module-title>
        </div>
        <div
            class="charts"
            ref="chart"
        >
            <CrossBarChart
                v-bind="corssBarChartData"
                ref="corssBarChar"
            ></CrossBarChart>
        </div>
        <div class="inspectCount">
            <div class="untilNow">至今</div>
            <div class="ratio">同比</div>
            <div class="countDetailBox">
                <div class="countTitle">巡检任务</div>
                <div class="count">{{taskCount.nowYearTaskCount}}</div>
                <div class="iconBox">
                    <Icon
                        :type="taskCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                        :color="taskCount.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </div>
            </div>
            <div class="countDetailBox">
                <div class="countTitle">缺陷</div>
                <div class="count">{{defectCount.nowYearDefectCount}}</div>
                <div class="iconBox">
                    <Icon
                        :type="defectCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                        :color="defectCount.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </div>
            </div>
            <div class="countDetailBox">
                <div class="countTitle">维修</div>
                <div class="count">{{maintenanceCount.nowYearOrderCount}}</div>
                <div class="iconBox">
                    <Icon
                        :type="maintenanceCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                        :color="maintenanceCount.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </div>
            </div>
            <div class="countDetailBox">
                <div class="countTitle">维修率</div>
                <div class="count">{{maintenanceRateCount.nowYearOrderPercentage}}%</div>
                <div class="iconBox">
                    <Icon
                        :type="maintenanceRateCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                        :color="maintenanceRateCount.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ModuleTitle from "../../../components/VM2/ModuleTitle";
import CrossBarChart from "../../../components/Common/Chart/CrossBarChart"
import { InspectService } from '../../../services/inspectService'

export default {
    data() {
        return {
            title: "巡检模块",
            taskCount: {
                nowYearTaskCount: 0,
                isRise: false,
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
                id: 'corssBarChartID',
                title: '巡检缺陷维修统计',
                legendData: [],
                yAxisData: [],
                seriesData: [],
            },
            monthValArray1: [],
            monthValArray2: []
        };
    },
    components: {
        ModuleTitle,
        CrossBarChart
    },
    mounted() {
        this.init()
        this.refreshData()
    },
    methods: {
        init() {
            let p1 = InspectService.getPatrolTaskCount();
            let p2 = InspectService.getDefectCount();
            let p3 = InspectService.getMaintenanceCount();
            Promise.all([p1, p2, p3]).then(res => {
                //获取今年的巡检任务总数
                this.taskCount.nowYearTaskCount = res[0].nowYearTaskCount
                this.taskCount.isRise = res[0].nowYearTaskCount >= res[0].beforeYearTaskCount ? true : false

                //获取缺陷总数
                let defectCount1 = res[1].nowYearDefectCount
                let defectCount2 = res[1].beforeYearDefectCount
                this.defectCount.nowYearDefectCount = res[1].nowYearDefectCount
                this.taskCount.isRise = res[1].nowYearDefectCount >= res[1].beforeYearDefectCount ? true : false

                //获取维修数
                let orderCount1 = res[2].nowYearOrderCount
                let orderCount2 = res[2].beforeYearOrderCount
                this.maintenanceCount.nowYearOrderCount = res[2].nowYearOrderCount
                this.maintenanceCount.isRise = res[2].nowYearOrderCount >= res[2].beforeYearOrderCount ? true : false

                //获取维修率
                this.maintenanceRateCount.nowYearOrderPercentage = (orderCount1 / defectCount1 * 100).toFixed(1)
                this.maintenanceRateCount.isRise = (orderCount1 / defectCount1) >= (orderCount2 / defectCount2) ? true : false;
            })
            //获取今年与去年的每月巡检计划数
            InspectService.getEveryMonthMaintenance().then(res => {
                var arr1 = []
                var arr2 = []
                for (var i = 0; i < res.length; i++) {
                    this.corssBarChartData.yAxisData.push(res[i].key)
                    for (var j = 0; j < res[i].val.length; j++) {
                        if (i == 0) {
                            this.corssBarChartData.legendData.push(res[i].val[j].key)
                        }
                        if (j == 0) {
                            arr1.push(res[i].val[j].val)
                        }
                        if (j == 1) {
                            arr2.push(res[i].val[j].val)
                        }
                    }
                }
                arr1 = arr1.reverse()
                arr2 = arr2.reverse()
                this.corssBarChartData.yAxisData = this.corssBarChartData.yAxisData.reverse()
                this.corssBarChartData.seriesData.push({ data: arr1 })
                this.corssBarChartData.seriesData.push({ data: arr2 })
            });
        },
        refreshData() {
            setInterval(() => {
                this.$refs.corssBarChar.drawBar()
            }, 1000 * 5 * 60)
        }
    },
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
    width: 47%;
    height: 83%;
    display: inline-block;
    vertical-align: top;
    margin-left: 4%;
}
.inspectCount {
    width: 47%;
    height: 83%;
    display: inline-block;
    vertical-align: top;
    padding-left: 2%;
    padding-right: 1%;
}
.untilNow,
.ratio {
    display: inline-block;
    color: #ccc;
    font-size: 1.9vmin;
}
.untilNow {
    width: 71%;
    padding-left: 10%;
}
.ratio,
.countDetailBox .iconBox {
    text-align: center;
}
.countDetailBox {
    margin-top: 0.5vh;
    line-height: 5vh;
    background: url(../../../assets/VM/doubt.png) no-repeat;
    background-size: 100%;
}
.countDetailBox .countTitle,
.countDetailBox .count,
.countDetailBox .iconBox {
    display: inline-block;
    color: #fff;
    font-size: 1.6vmin;
}
.countDetailBox .iconBox {
    font-size: 1.9vmin;
}
.countDetailBox .countTitle {
    width: 48%;
    padding-left: 5%;
}
.countDetailBox .count {
    width: 29%;
}
</style>
