<template>
  <div class="chartContent">
    <div v-if = "chartData.chartType === echartsType.Line.key">
        <line-chart :chartData = "chartData"></line-chart>
    </div>
    <div v-if = "chartData.chartType === echartsType.MultiLine.key">
        <multi-line-chart :chartData = "chartData"></multi-line-chart>
    </div>
    <div v-else-if = "chartData.chartType === echartsType.Bar.key">
        <bar-chart :chartData = "chartData"></bar-chart>
    </div>
    <div v-else-if = "chartData.chartType === echartsType.MultiBar.key">
        <multi-bar-chart :chartData = "chartData"></multi-bar-chart>
    </div>
    <div v-else-if = "chartData.chartType === echartsType.Pie.key">
        <pie-chart  :chartData = "chartData"></pie-chart>
    </div>
    <div v-else-if = "chartData.chartType === echartsType.Radar.key">
        <radar-chart :chartData = "chartData"></radar-chart>
    </div>
    <div v-else-if = "chartData.chartType === echartsType.MultiRadar.key">
        <multi-radar-chart :chartData = "chartData"></multi-radar-chart>
    </div>
  </div>
</template>

<style scoped>
.chartContent {
    position: relative;
    width: 100%;
    height: 100%;
}
</style>

<script>
import LineChart from "./SimpleLineChart";
import MultiLineChart from "./MultiLineChart";
import BarChart from "./SimpleBarChart";
import MultiBarChart from "./MultiBarChart";
import PieChart from "./SimplePieChart";
import RadarChart from "./SimpleRadarChart";
import MultiRadarChart from "./MultiRadarChart";

import types from "../../../../../static/Enum.json";

export default {
    name: "chart-component",
    props: {
        chartData: null
    },
    data() {
        return {
            echartsType: null
        };
    },
    computed: {},
    components: {
        LineChart,
        MultiLineChart,
        BarChart,
        MultiBarChart,
        PieChart,
        RadarChart,
        MultiRadarChart
    },
    mounted() {
        this.echartsType = types.echartsType;
        console.log("chartData", this.chartData);
    }
};
</script>
