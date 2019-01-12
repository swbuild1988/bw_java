<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="charts">
            <CrossBarChart v-bind="corssBarChartData"></CrossBarChart>
        </div>
        <div class="inspectCount">
            <div class="untilNow">至今</div>
            <div class="ratio">同比</div>
            <div class="countDetailBox">
                <div class="countTitle">巡检任务</div>
                <div class="count">{{taskCount.nowYearTaskCount}}</div>
                <div class="iconBox">
                    <Icon :type="taskCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                          :color="taskCount.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </div>
            </div>
            <div class="countDetailBox">
                <div class="countTitle">缺陷</div>
                <div class="count">{{defectCount.nowYearDefectCount}}</div>
                <div class="iconBox">
                    <Icon :type="defectCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                          :color="defectCount.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </div>
            </div>
            <div class="countDetailBox">
                <div class="countTitle">维修</div>
                <div class="count">{{maintenanceCount.nowYearOrderCount}}</div>
                <div class="iconBox">
                    <Icon :type="maintenanceCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                          :color="maintenanceCount.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </div>
            </div>
            <div class="countDetailBox">
                <div class="countTitle">维修率</div>
                <div class="count">{{maintenanceRateCount.nowYearOrderPercentage}}%</div>
                <div class="iconBox">
                    <Icon :type="maintenanceRateCount.isRise ? 'arrow-up-c' : 'arrow-down-c'"
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

export default {
    data() {
        return {
            title: "巡检",
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
                title: '巡检计划对比',
                legendData: ['2018年','2019年'],
                yAxisData: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                seriesData: [
                    {
                        data: ['15','26','86','78','53','45','82','78','29','156','75','65']
                    },{
                        data: ['56','89','56','28','76','15','26','86','78','53','45','82']
                    }
                ],
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
        this.getPatrolTaskCount()
        this.getDefectCount()
        this.getMaintenanceCount()
        this.getMaintenanceRate()
        // this.getEveryMonthPlan()
    },
    methods: {
        //获取今年的巡检任务总数
        getPatrolTaskCount(){
            this.axios.get('inspection-tasks/count-year').then(res=>{
                let { code,data } = res.data
                if( code==200 ){
                    this.taskCount.nowYearTaskCount = data.nowYearTaskCount
                    if(data.nowYearTaskCount>=data.beforeYearTaskCount){
                        this.taskCount.isRise = true
                    }else{
                        this.taskCount.isRise = false
                    }
                }
            })
        },
        //获取缺陷总数
        getDefectCount(){
            this.axios.get('defects/count-year').then(res=>{
                let { code,data } = res.data
                if( code==200 ){
                    this.defectCount.nowYearDefectCount = data.nowYearDefectCount
                    if(data.nowYearDefectCount>=data.beforeYearDefectCount){
                        this.defectCount.isRise = true
                    }else{
                        this.defectCount.isRise = false
                    }
                }
            })
        },
        //获取维修数
        getMaintenanceCount(){
            this.axios.get('/orders/count-year').then(res=>{
                let { code,data } = res.data
                if( code==200 ){
                    this.maintenanceCount.nowYearOrderCount = data.nowYearOrderCount
                    if(data.nowYearOrderCount>=data.beforeYearOrderCount){
                        this.maintenanceCount.isRise = true
                    }else{
                        this.maintenanceCount.isRise = false
                    }
                }
            })
        },
        //获取维修率
        getMaintenanceRate(){
            this.axios.get('orders/percentage-year').then(res=>{
                let { code,data } = res.data
                if( code==200 ){
                    this.maintenanceRateCount.nowYearOrderPercentage = data.nowYearOrderPercentage
                    if(data.nowYearOrderPercentage>=data.beforeYearOrderPercentage){
                        this.maintenanceRateCount.isRise = true
                    }else{
                        this.maintenanceRateCount.isRise = false
                    }
                }
            })
        },
        //获取今年与去年的每月巡检计划数
        getEveryMonthPlan(){
            this.axios.get('inspection-tasks/count-month').then(res=>{
                let { code,data } = res.data
                if( code==200 ){
                    for(var i=0; i<data.length; i++){
                        var year = data[i].key
                        for(var j=0; j<data[i].val.length; j++){
                            if(i==0){
                                var month = data[0].val[j].key
                                this.corssBarChartData.yAxisData.push(month)
                                this.monthValArray1.push(data[0].val[j].val)
                            }else if(i==1){
                                this.monthValArray2.push(data[i].val[j].val)
                            }
                        }
                        this.corssBarChartData.legendData.push(year)
                    }
                    this.corssBarChartData.seriesData.push({
                        data: this.monthValArray1
                    },{
                        data: this.monthValArray2
                    })
                }
            })
        }
    }
};
</script>

<style scoped>
.Main {
    width: 100%;
    height: 100%;
    background: url("../../../assets/VM/vm_module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.Main .Title {
    width: 100%;
    height: 15%;
}
.charts, .inspectCount {
    width: 48%;
    height: 85%;
    display: inline-block;
    vertical-align: top;
}
.untilNow,.ratio{
    display: inline-block;
    color: #fff;
    font-size: 1.9vmin;
}
.untilNow{
    width: 79%;
}
.ratio,.countDetailBox .iconBox{
    width: 19%;
    text-align: center;
}
.countDetailBox{
    line-height: 5.5vh;
}
.countDetailBox .countTitle,.countDetailBox .count, .countDetailBox .iconBox{
    display: inline-block;
    color: #fff;
    font-size: 1.6vmin;
}
.countDetailBox .iconBox{
    font-size: 1.9vmin;
}
.countDetailBox .countTitle{
    width: 48%
}
.countDetailBox .count{
    width: 29%
}
</style>
