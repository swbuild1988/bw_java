<template>
    <div class="Main">
        <div class="topLeft">
            <div class="Title">
                <module-title :title="title"></module-title>
            </div>
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
        <div class="topRight">
            <simple-pie-chart v-bind="pieChart"></simple-pie-chart>
        </div>
        <div class="bottom">
            <StackBar
                ref="stackBar"
                v-bind="stackBar"
            ></StackBar>
        </div>
    </div>
</template>

<script>
import SimplePieChart from '../../../components/Common/Chart/SimplePieChart'
import StackBar from "../../../components/Common/Chart/StackChart"
import ModuleTitle from "../../../components/VM2/ModuleTitle";

export default {
    data() {
        return {
            title: '告警统计',
            yearTotal: {
                number: 271,
                isRise: false
            },
            monthTotal: {
                number: 32,
                isRise: true
            },
            pieChart: {
                requestUrl: "tunnels/items/energies",
                id: "tunnelEnergyPieChart",
                parameters: {
                    option: {
                        title: {
                            text: "管廊告警统计",
                            x: "center",
                            textStyle: {
                                fontWeight: "bold",
                                color: "white",
                                fontSize: "16"
                            }
                        }
                    }
                }
            },
            stackBar: {
                id: "stackChart",
                requestUrl: "stacChart",
                titleName: "",
                title: "22",
                titleColor: "#030303",
                intervalTime: 1000,
                tunnelId: null,
                timeCycle: null,
                xData: [],
                legendData: [
                    "提示",
                    "一般",
                    "严重",
                    "危急"
                ]
            },
        };
    },
    components: {
        SimplePieChart,
        StackBar,
        ModuleTitle
    },
    mounted() { },
    methods: {}
};
</script>

<style scoped>
.Main {
    width: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    background: url("../../../assets/VM/vm_module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.topLeft {
    width: 43%;
    height: 50%;
    position: absolute;
    top: 0%;
    left: 0%;
}
.topRight {
    width: 44%;
    height: 50%;
    position: absolute;
    top: 5%;
    right: 6%;
}
.bottom {
    position: absolute;
    width: 90%;
    height: 42%;
    top: 50%;
    right: 6%;
}
.Title {
    width: 100%;
    height: 30%;
}
.subTitle {
    font-size: 1.4vmin;
    color: white;
    margin: 0.8vh 0.4vw;
}
.number {
    font-size: 2vmin;
    margin-left: 0.6vw;
    color: #37bbcc;
}
.yearIcon {
    font-size: 3vmin;
    position: absolute;
    top: 5vh;
    right: 1.4vw;
}
.monthIcon {
    font-size: 3vmin;
    position: absolute;
    top: 8.6vh;
    right: 1.4vw;
}
</style>
