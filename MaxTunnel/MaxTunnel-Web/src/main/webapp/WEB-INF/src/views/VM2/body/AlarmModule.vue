<template>
    <div class="main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="leftTop">
            <div class="subTitle">
                <span>本年度告警：</span>
                <span class="number">{{ yearTotal.number }}</span>
                <span class="yearIcon">
                    <Icon
                        :type="yearTotal.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                        :color="yearTotal.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </span>
            </div>
            <div class="subTitle">
                <span style="letter-spacing: 0.25em;margin-right: -0.25em;">本月告警：</span>
                <span class="number">{{ monthTotal.number }}</span>
                <span class="monthIcon">
                    <Icon
                        :type="monthTotal.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                        :color="monthTotal.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </span>
            </div>
        </div>
        <div class="leftBottom">
            <simple-pie-chart v-bind="pieChart"></simple-pie-chart>
        </div>
        <div class="right">
            <!-- <pile-bar-chart v-bind="pileBarChart" ref="pileBar"></pile-bar-chart> -->
            <!-- <textra :data="words" :timer="4" filter="bottom-top" :infinite='true'></textra> -->
            <div class="play" @click="changeState">
                <Icon :type="carouselInfo.isCarousel ? 'pause' : 'play'" class="playIcon"></Icon>
            </div>
            <Table :columns="columns" :data="alarmShowData" class="table" :show-header="false"></Table>
        </div>
    </div>
</template>

<script>
import SimplePieChart from '../../../components/Common/Chart/SimplePieChart'
import pileBarChart from "../../../components/Common/Chart/PileBarChart"
import ModuleTitle from "../../../components/VM2/ModuleTitle"
import { AlarmService } from '../../../services/alarmService'

export default {
    data() {
        return {
            title: '警报统计',
            yearTotal: {
                number: 271,
                isRise: false
            },
            monthTotal: {
                number: 32,
                isRise: true
            },
            pieChart: {
                requestUrl: "tunnels/alarm-count",
                id: "tunnelAlarmPieChart",
                parameters: {
                    option: {
                        title: {
                            text: "管廊警报统计",
                            x: "center",
                            textStyle: {
                                color: "white",
                                fontSize: "10%"
                            }
                        },
                        series: {
                            label: {
                                normal: {
                                    textStyle: {
                                        fontSize: '8%'
                                    }
                                }
                            }
                        },
                        grid: {
                            top: '6%',
                            left: 0,
                            right: 0
                        },
                    }
                }
            },
            pileBarChart: {
                id: 'alarmsReportBar',
                requestUrl: "alarms/level-count–everymonth",
                parameters: {option: {
                    legend: {
                        data: ["严重", "一般", "提示", "致命"],
                        textStyle: {
                            color: 'white',
                            fontSize: '10%'
                        },
                        orient: 'horizontal',
                        left: 20
                    },
                    xAxis: {
                        axisLabel: {
                           color: 'white',
                           fontSize: '8%'
                        }
                    },
                    yAxis: {
                        axisLabel: {
                            color: 'white',
                            fontSize: '8%'
                        }
                    }
                }, prams: {}},
                type: 'get'
            },
            words: ['暂无告警'],
            alarmShowData: [],
            alarmAllData:[],
            columns: [
                {
                    title: "告警",
                    key: "name",
                    align: "center",
                    // width: '130',
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "span",
                                {
                                    style: {
                                        display: "inline-block",
                                        overflow: "hidden",
                                        textOverflow: "ellipsis",
                                        whiteSpace: "nowrap",
                                        cursor: "pointer"
                                    },
                                    domProps: {
                                        title: params.row.name
                                    }
                                },
                                params.row.name
                            )
                        ]);
                    }
                },
                {
                    title: "级别",
                    key: "alarmLevel",
                    align: "center",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "span",
                                {
                                    style: {
                                        display: "inline-block",
                                        overflow: "hidden",
                                        textOverflow: "ellipsis",
                                        whiteSpace: "nowrap",
                                        cursor: "pointer",
                                        color: params.row.color,
                                        fontSize: '1.8vmin'
                                    },
                                    domProps: {
                                        title: params.row.alarmLevel
                                    }
                                },
                                params.row.alarmLevel
                            )
                        ]);
                    }
                },
                {
                    title: "位置",
                    key: "location",
                    align: "center",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "span",
                                {
                                    style: {
                                        display: "inline-block",
                                        overflow: "hidden",
                                        textOverflow: "ellipsis",
                                        whiteSpace: "nowrap",
                                        cursor: "pointer"
                                    },
                                    domProps: {
                                        title: params.row.location
                                    }
                                },
                                params.row.location
                            )
                        ]);
                    }
                },
                {
                    title: '时间',
                    key: 'alarmDate',
                    align: 'center',
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "span",
                                {
                                    style: {
                                        display: "inline-block",
                                        overflow: "hidden",
                                        textOverflow: "ellipsis",
                                        whiteSpace: "nowrap",
                                        cursor: "pointer"
                                    },
                                    domProps: {
                                        title: params.row.alarmDate
                                    }
                                },
                                params.row.alarmDate
                            )
                        ]);
                    }
                }
            ],
            carouselInfo: {
                intervalId: null,
                totalPage: null,
                curPage: 1,
                isCarousel: true,
                time: 2000
            },
            refresh: {
                time: 300000,
                intervalId: null
            }
        };
    },
    components: {
        SimplePieChart,
        pileBarChart,
        ModuleTitle
    },
    mounted() {
        this.init()
        let _this = this
        this.refresh.intervalId = setInterval(()=>{
            _this.init()
        },this.refresh.time)
    },
    methods: {
        init(){
            let _this = this
            AlarmService.getYearAndMonthAlarmCount().then(
                result=>{
                    result.forEach(item=>{
                        if(item.key == 'year'){
                            _this.yearTotal.number = item.value
                            _this.yearTotal.isRise = item.isRise
                        } else {
                            _this.monthTotal.number = item.value
                            _this.monthTotal.isRise = item.isRise
                        }
                    })
                })
            AlarmService.getNewAlarmsList().then(
                result=>{
                    if(result){
                        _this.words = []
                        result.forEach(alarm=>{
                            let info = alarm.description + ' ' + alarm.alarmLevel + ' ' + alarm.location + ' ' + alarm.alarmTime
                            _this.words.push(info)
                        })
                        _this.alarmAllData = result
                        _this.alarmAllData.forEach(alarm=>{
                            alarm.alarmLevel = alarm.alarmLevel.slice(0,2)
                            alarm.alarmDate = new Date(alarm.alarmDate).format('MM.dd.yy')
                            switch(alarm.alarmLevel){
                                case '提示':
                                    alarm.color = '#0066ff'
                                    break
                                case '一般':
                                    alarm.color = '#ffff00'
                                    break
                                case '严重':
                                    alarm.color = '#ffae00'
                                    break
                                case '危急':
                                    alarm.color = '#ff0000'
                                    break
                            }
                        })
                        _this.carouselInfo.totalPage = result.length % 4 == 0 ? result.length / 4 : result.length / 4 + 1
                    }
                })
            if(this.carouselInfo.isCarousel){
                this.carouselInfo.intervalId = setInterval(()=>{
                    _this.carousel()
                },this.carouselInfo.time)
            }
        },
        carousel() {
            this.alarmShowData = this.alarmAllData.slice((this.carouselInfo.curPage-1) * 4, this.carouselInfo.curPage * 4)
            if(this.carouselInfo.curPage === this.carouselInfo.totalPage){
                this.carouselInfo.curPage = 1
            } else {
                this.carouselInfo.curPage += 1
            }
        },
        changeState() {
            this.carouselInfo.isCarousel = !this.carouselInfo.isCarousel
            if(this.carouselInfo.isCarousel){
                let _this = this
                this.carouselInfo.intervalId = setInterval(()=>{
                    _this.carousel()
                },this.carouselInfo.time)
            } else {
                clearInterval(this.carouselInfo.intervalId)
                clearInterval(this.refresh.intervalId)
            }
        }
    },
    beforeDestory(){
        clearInterval(this.carouselInfo.intervalId)
        clearInterval(this.refresh.intervalId)
    }
};
</script>

<style scoped>
.main {
    width: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.leftTop {
    width: 42%;
    height: 28%;
    position: absolute;
    top: 18%;
    left: 4%;
    background: url("../../../assets/UM/videoBody.png") no-repeat;
    background-size: 100% 100%;
}
.leftBottom {
    width: 42%;
    height: 50%;
    position: absolute;
    top: 50%;
    left: 6%;
}
.right {
    position: absolute;
    width: 12.2vw;
    height: 20vh;
    top: 8%;
    right: 2%;
}
.table{
    width: 11.4vw;
    height: 18vh;
}
.Title {
    width: 42vmin;
    height: 5vmin;
}
.subTitle {
    font-size: 1vmin;
    color: white;
    margin: 0.2vh 0.8vw;
}
.number {
    font-size: 2vmin;
    margin-left: 0.6vw;
    color: #37bbcc;
}
.yearIcon {
    font-size: 2.6vmin;
    position: absolute;
    top: 0;
    right: 0.8vmin;
}
.monthIcon {
    font-size: 2.6vmin;
    position: absolute;
    top: 3vmin;
    right: 0.8vmin;
}

.right >>> .mainTextra{
    font-size: 2vmin;
    color: white;
}

.right >>> .ivu-table-wrapper {
    border: 1px solid transparent;
    border-bottom: 0;
    border-right: 0;
    font-size: 1.42vmin;
}
.right >>> .ivu-table {
   /* color: #e1e4e5;*/
    color: white;
    background-color: transparent;
    font-size: 1.42vmin !important;
}
.right >>> .ivu-table td {
    background-color: transparent;
    border-bottom: transparent;
}
.right >>> .ivu-table-stripe .ivu-table-body tr:nth-child(2n) td {
    background: transparent;
}
.right >>> .ivu-table:after,
.right >>> .ivu-table:before {
    background-color: transparent;
}
.play{
    float: right;
    margin-right: 2vmin;
    margin-top: -0.8vmin;
    width: 2vmin;
    height: 2vmin;
    color: #aaa;
    border: 2px solid #aaa;
    border-radius: 50%;
    font-size: 1.2vmin;
    display: table;
    cursor: pointer;
}
.playIcon{
    display: table-cell;
    vertical-align: middle;
    text-align: center;
}
</style>
