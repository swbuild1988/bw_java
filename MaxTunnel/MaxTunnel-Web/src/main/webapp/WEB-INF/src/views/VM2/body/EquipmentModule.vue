<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="topBox">
            <ul class="stateLists">
                <li :key="key" v-for="(item,key) in stateDataLists">
                    <div class="stateList-content" >
                        <span>{{ item.name }}</span>
                        <span>{{ item.value }}</span>
                    </div>
                </li>
            </ul>
        </div>
        <div class="bottomBox">
            <stack-chart
                    v-bind="stackCrossData"
                    ref="stackCrossChart"
            >
            </stack-chart>
        </div>
    </div>
</template>

<script>
    import ModuleTitle from "../../../components/VM2/ModuleTitle";
    import stackCrossChart from "../../../components/Common/Chart/StackCrossChart"
    import simplePieChart from '../../../components/Common/Chart/SimplePieChart'
    import CrossBarChart from "../../../components/Common/Chart/CrossBarChart"
    import PieChart from "../../../components/Common/Chart/PieChart"
    import StackChart from "../../../components/Common/Chart/StackChart"
    import ComplexBarChart from "../../../components/Common/Chart/ComplexBarChart.vue"
    import { EquipmentService } from '../../../services/equipmentService'

    export default {
        data() {
            return {
                title: "廊内设备",
                stackCrossData: {
                    id: 'stackCrossID',
                    title: '设备类型状态统计',
                    legendData: [],
                    xData: [],
                    seriesData: []
                },
                    pieData: {
                    id: 'pieID',
                    title: '设备状态',
                    legendData: [],
                    seriesData: []
                },
                stateDataLists:[],
                timerId:null

                
            };
        },
        components: {
            ModuleTitle,
            stackCrossChart,
            simplePieChart,
            CrossBarChart,
            PieChart,
            ComplexBarChart,
            StackChart
        },
        mounted() {
            this.init()
        },
        methods: {
            init() {
                // 获取数据
                EquipmentService.getEquipmentTypeAndStatusCount().then(res => {
                    // 柱状图数据
                    var arr = []
                    this.stackCrossData.legendData = [],
                    this.stackCrossData.xData = [],
                    this.stackCrossData.seriesData = []
                    for (var i = 0; i < res.length; i++) {
                        this.stackCrossData.xData.push(res[i].key)
                        var tmp = [];
                        for (var j = 0; j < res[i].val.length; j++) {
                            if (i == 0) {
                                this.stackCrossData.legendData.push(res[i].val[j].key)
                                arr.push([res[i].val[j].val])
                            } else {
                                arr[j].push(res[i].val[j].val)
                            }
                        }
                    }
                    for (var i = 0; i < arr.length; i++) {
                        this.stackCrossData.seriesData.push({ data: arr[i] })
                    }

                    // 饼图
                    this.pieData.legendData = [];
                    for (var i = 0; i < this.stackCrossData.legendData.length; i++) {
                        this.pieData.legendData.push(this.stackCrossData.legendData[i]);
                    }
                    this.pieData.seriesData = [];
                    for (var i = 0; i < arr.length; i++) {
                        this.pieData.seriesData.push(0);
                        for (var j = 0; j < arr[i].length; j++) this.pieData.seriesData[i] += arr[i][j];
                    }
                    // this.pieData.legendData = this.pieData.legendData.reverse();
                    // this.pieData.seriesData = this.pieData.seriesData.reverse();
                    this.stateDataLists.splice(0);
                    for(let i=0,pieData = this.pieData;i<pieData.seriesData.length;i++){
                        this.stateDataLists.push({
                            name:pieData.legendData[i],
                            value:this.percentage(pieData.seriesData,i)
                        })
                    }

                })
                .finally(()=>{
                    let _this = this
                    
                    this.timerId= setTimeout(()=>{
                        _this.init()
                    },parseFloat(_this.refreshTime))
                });
            },
            percentage(array,index){
                let sum = array.reduce((prev,cur) => prev + cur );
                return Math.round( (array[index] / sum) * 100 ) +'%';
            },
            refreshData() {
                setInterval(() => {
                    this.$refs.stackCrossChart.drawBar()
                }, parseFloat(this.refreshTime))
            }
        },
        beforeDestroy(){
            clearTimeout( this.timerId );
        }
    };
</script>

<style scoped>
    .Main {
        width: 100%;
        height: 100%;
        background: url("../../../assets/VM/module_bg.png") no-repeat;
        background-size: 100% 100%;
    }

    .Main .Title {
        width: 100%;
        height: 15%;
    }

    .topBox{
        width: 100%;
        height: 20%;
        /*display: inline-block;*/
        /*vertical-align: top;*/
        /*margin-left: 3%;*/
    }

    .simplePieChartBox {
        width: 100%;
        height: 45%;
    }

    .letBottomBox{
        width: 100%;
        height: 55%;
    }

    .stackCrossBox {
        width: 52%;
        height: 85%;
        display: inline-block;
    }
    .stateLists{
        list-style-type: none;
        height: 100%;
    }
    .stateLists > li {
        float: left;
        width: 25%;
        height: 100%;
        border-right: 1px solid transparent;
        border-image: linear-gradient(0deg,transparent 0% ,  rgba(22,78,137,.5) 30%,  rgba(22,78,137,1) 50%,rgba(22,78,137,.5) 80%, transparent 100%) 1;
    }
    .stateLists > li:last-child{
        border-right: 1px solid transparent;
    }
    .stateList-content{
        text-align: center;
    }
    .stateList-content > span{
        display: block;
        color: #fff;
        font-family: "Microsoft YaHei";
    }
    .bottomBox{
        width: 100%;
        height: 64%;
    }
    /* 小屏幕（显示器，小于等于 1920px） */
    @media (max-width: 1920px) {
        .stateList-content{
            margin-top: 9%;
        }
        .stateList-content > span:first-child{
            font-size: 0.7rem;
        }
        .stateList-content > span:last-child{
            font-size: 1rem;
        }

    }
    /* 大屏幕（显示器，大于等于 1920px） */
    @media (min-width: 1921px) {

        .stateList-content{
            margin-top: 11%;
        }
        .stateList-content > span:first-child{
            font-size: 1.5rem;
        }
        .stateList-content > span:last-child{
            font-size: 2.25rem;
        }
    }
</style>
