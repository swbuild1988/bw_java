<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div
            v-for="item in gaugeChart"
            class="myChart"
        >
            <Simple-gauge
                v-bind="item"
                :ref="item.id"
            ></Simple-gauge>
        </div>
        <div
            class="info"
            v-for="item in countData"
        >
            <div class="infoTitle">
                <p>{{item.name}}</p>
            </div>
            <div class="infoTitle">
                <p><span style="color: #00a1ff;margin-left: 1vw;margin-right: 1vw;font-size: 2.5vmin;">{{item.value}}</span>个 </p>
            </div>
        </div>
    </div>
</template>

<script>
import ModuleTitle from "../../../components/VM2/ModuleTitle"
import SimpleGauge from "../../../components/Common/Chart/SimpleGauge2"
import { MeasObjServer } from '../../../services/MeasObjectSerivers'

export default {
    data() {
        return {
            title: '管廊监测',
            countData: [],
            gaugeChart: [],
        };
    },
    components: {
        ModuleTitle,
        SimpleGauge
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            let _this = this
            MeasObjServer.getToDayExtreDatas().then(
                result => {
                    _this.gaugeChart = [];
                    if (result) {
                        result.forEach((a, index) => {
                            let temp = {};
                            temp.min = a.min - parseFloat(((a.max - a.min) / 3).toFixed(1));
                            temp.max = a.max + parseFloat(((a.max - a.min) / 3).toFixed(1));
                            temp.id = index + "_Chart";
                            temp.parameters = {
                                option: {
                                    series: [{
                                        data: [{
                                            value: a.value,
                                            name: a.name
                                        }],
                                        detail: {
                                            formatter: '{val|{value}' + a.unit + '}\n' + '{section|' + a.location + '}',
                                            rich: {
                                                val: {
                                                    fontSize: 40
                                                },
                                                section: {
                                                    fontSize: 20
                                                }
                                            }
                                        }
                                    }]
                                }
                            };
                            _this.gaugeChart.push(temp);
                        })
                    }
                },
                error => {
                    _this.Log.info(error)
                })
            MeasObjServer.getMeasTriggerCounts().then(
                result => {
                    _this.countData = result
                },
                error => {
                    _this.Log.info(error)
                })
        }
    }
};
</script>

<style scoped>
.Main {
    width: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}

.Main .Title {
    width: 100%;
    height: 15%;
}

.myChart {
    width: 33%;
    height: 16vh;
    float: left;
}

.info {
    margin: 2vmin 2.5% 1vmin 2.5%;
    width: 45%;
    height: 8vh;
    float: left;

    border: 0.2vh solid #66ccee;
    border-radius: 1.2vh 0;
}

.infoTitle {
    text-align: center;
    font-size: 1.66vmin;
    color: #fff;
    line-height: 3.2vmin;
    font-family: UnidreamLED;
}
</style>
