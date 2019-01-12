<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="equipmentCount">
            <div class="countDetailsBox">
                <div class="countTitle">设备数量</div>
                <div class="count">1806</div>
            </div>
            <div class="countDetailsBox">
                <div class="countTitle">最长运行时间</div>
                <div class="count">569 d</div>
            </div>
            <div class="countDetailsBox">
                <div class="countTitle">备品数量</div>
                <div class="count">56</div>
            </div>
            <div class="countDetailsBox">
                <div class="countTitle">工具数量</div>
                <div class="count">506</div>
            </div>
        </div>
        <div class="radarChartBox">
            <radarChart v-bind="radarData"></radarChart>
        </div>
        <div class="mixedLineBarBox">
            <mixedLineBarChart v-bind="mixedLineBarData"></mixedLineBarChart>
        </div>
    </div>
</template>

<script>
    import ModuleTitle from "../../../components/VM2/ModuleTitle";
    import radarChart from "../../../components/Common/Chart/radarChart"
    import mixedLineBarChart from "../../../components/Common/Chart/MixedLineBarChart"

    export default {
        data() {
            return {
                title: "设备",
                radarData: {
                    id: 'radarID',
                    title: '各类型设备数量',
                    legendData: ['各类型设备数量'],
                    radarindicator: [
                        { name: '环境类', max: 15900 },
                        { name: '安防类', max: 12590 },
                        { name: '通讯类', max: 5690 },
                        { name: '结构类', max: 5892 },
                        { name: '工具类', max: 8694 },
                        { name: '监视类', max: 9999 }
                    ],
                    seriesData: [
                        {
                            value : [4300, 10000, 2800, 3500, 5000, 1900],
                            name : '设备数'
                        }
                    ]
                },
                mixedLineBarData: {
                    id: 'mixedLineBarID',
                    legendData: ['设备缺陷','备品使用量','工具借用次数'],
                    xAxisData: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                    yAxisData1: '个',
                    yAxisData2: '次',
                    seriesData: [
                        {
                            name:'设备缺陷',
                            type:'bar',
                            data:[15,55,55,58,19,15,36,78,159,28,65,47],
                            itemStyle: {
                                normal: {
                                    color: '#19be6b'
                                }
                            }
                        },
                        {
                            name:'备品使用量',
                            type:'bar',
                            data:[59,55,55,58,19,15,36,28,102,28,65,47],
                            itemStyle: {
                                normal: {
                                    color: '#c857f3'
                                }
                            }
                        },
                        {
                            name:'工具借用次数',
                            type:'line',
                            yAxisIndex: 1,
                            data:[88,55,55,58,19,15,36,156,159,28,65,47],
                        }
                    ]
                }
            };
        },
        components: {
            ModuleTitle, radarChart, mixedLineBarChart
        },
        // mounted() {},
        // methods: {}
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

    .equipmentCount{
        height: 40%;
        width: 49%;
        display: inline-block;
        vertical-align: top;
    }

    .countDetailsBox{
        line-height: 3vh;
    }

    .countDetailsBox .countTitle,.countDetailsBox .count{
        display: inline-block;
        vertical-align: top;
        color: #fff;
        font-size: 1.6vmin;
    }

    .countDetailsBox .countTitle{
        width: 80%;
        padding-left: 1vw;
    }

    .radarChartBox{
        width: 48%;
        height: 40%;
        display: inline-block;
        vertical-align: top;
    }
    .mixedLineBarBox{
        width: 96%;
        height: 45%;
        margin: 0px 2%;
    }
</style>