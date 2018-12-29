<template>
      <div class="BackgroundImage">
        <div class="FaultChart" :id=radarChartid></div>
        <vm-title :title-text="title"></vm-title>
      </div>
</template>

<style scoped>
.FaultChart {
    position: relative;
    top: 15%;
    left: 0%;
    width: 100%;
    height: 84%;
}
</style>

<script>
import VmTitle from "../VMTitle";
export default {
    name: "FaultChart",
    props: ["radarChartid", "requestUrl"],
    data() {
        return {
            title: "故障数量对比",
            myChart: "",
            indicator: [],
            data: []
        };
    },
    components: {
        VmTitle
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.drawRadar();
            this.fetchData(this.requestUrl, this.flatDate);
        },
        drawRadar() {
            let _this = this;
            _this.myChart = _this.$echarts.init(
                document.getElementById(_this.radarChartid)
            );
            let option = {
                title: {},
                tooltip: {},
                legend: {
                    data: []
                },
                radar: {
                    name: {
                        formatter: `{value}`,
                        textStyle: {}
                    },
                    splitArea: {
                        areaStyle: {
                            color: ["#001A27"]
                        }
                    },
                    indicator: [
                        { name: "安全防范" },
                        { name: "视频监控" },
                        { name: "环境监测" },
                        { name: "电缆本体" },
                        { name: "火灾报警" },
                        { name: "隧道通信" }
                    ]
                },
                series: [
                    {
                        type: "radar",
                        // areaStyle: {normal: {}},
                        data: _this.data
                    }
                ]
            };

            //["安全防范", "视频监控", "环境监测", "电缆本体", "火灾报警", "隧道通信"]
            _this.myChart.setOption(option);
            window.addEventListener("resize", this.myChart.resize);
        },
        fetchData(requestUrl, callback) {
            if (typeof callback != "function") return;
            let _this = this;
            _this.axios.get(requestUrl).then(result => {
                let { code, data } = result.data;
                if (code == 200) {
                    let baseData = callback.call(
                        this.fetchData,
                        data,
                        this.recursion
                    );

                    if (
                        JSON.stringify(baseData.indicator) !=
                            JSON.stringify(_this.indicator) ||
                        JSON.stringify(baseData.initObj) !=
                            JSON.stringify(_this.initObj)
                    ) {
                        _this.indicator = baseData.indicator;
                        _this.data = baseData.initObj;

                        _this.myChart.setOption({
                            radar: { indicator: _this.indicator },
                            series: { data: _this.data }
                        });
                    }
                }
            });
        },
        //由[{val:[{val:xx,key:xx},{val:xx,key:xx}],key:xx},val:[{val:xx,key:xx},{val:xx,key:xx}],key:xx}]
        //转成{xx:[{name:xx},{name:xx}],xx:[{value:[x,x,x],name:xx},value:[x,x,x],name:xx}]}
        flatDate(data, callback) {
            if (typeof callback != "function") return;
            let initObj = [];
            let indicator = [];
            callback.call(this.flatDate, data, initObj, indicator);
            return {
                initObj,
                indicator
            };
        },
        recursion(data, initObj, indicator) {
            for (let index = 0; index < data.length; index++) {
                let item = data[index];

                if (typeof item.val == "object") {
                    this.recursion(item.val, initObj);
                    indicator.push({
                        name: item.key,
                        max: item.val.reduce(
                            (maxVal, curVal) =>
                                curVal.val > maxVal ? curVal.val : maxVal,
                            0
                        )
                    });
                    continue;
                }

                if (initObj.length < data.length) {
                    initObj.push({ name: item.key, value: [item.val] });
                } else {
                    initObj[index].value.push(item.val);
                }
            }
        }
    }
};
</script>
