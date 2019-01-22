<template>
    <div
        class="pie"
        :id="id"
        ref="element"
    >
    </div>
</template>
<script>
export default {
    name: 'pie',
    props: {
        id: {
            type: String
        },
        title: {
            type: String
        },
        legendData: {
            type: Array
        },
        seriesData: {
            type: Array
        },
    },
    data() {
        return {
            myChart: {},
            option: {},
            itemStyleColor: ['#75af99', '#c1a048', '#bcd3bb', '#e88f70', '#edc1a5', '#9dc5c8', '#e1e8c8', '#7b7c68', '#e5b5b5', '#f0b489', '#928ea8', '#bda29a']
        }
    },
    watch: {
        seriesData(newVal, oldVal) {
            if (this.seriesData && this.seriesData.length > 0) {
                this.drawBar()
                this.fetchData()
            }
        }
    },
    mounted() {
        this.init()
        this.drawBar()
    },
    methods: {
        init() {
            this.drawBar()
            window.addEventListener("resize", this.myChart.resize);
            window.addEventListener("resize", this.resize)
        },
        resize() {
            this.drawBar()
            this.fetchData()
        },
        drawBar() {
            var _this = this;
            this.myChart = this.$echarts.init(document.getElementById(this.id));
            this.option = {
                title: {
                    text: this.title,
                    x:'center',
                    textStyle: {
                        color: '#ccc',
                        fontSize: _this.getFontSize('10%')
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'bottom',
                    data: ["运行", "故障", "停运", "其它"]
                },
                series: [
                    {
                        name: '各类状态',
                        type: "pie",
                        radius: '50%',
                        center: ['50%', '50%'],
                        data: [],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            }
            this.myChart.setOption(_this.option);
        },
        fetchData() {
            var _this = this;
            while (this.itemStyleColor.length < this.seriesData.length) {
                this.itemStyleColor = this.itemStyleColor.concat(this.itemStyleColor)
            }

            var tempCount = 0;
            for (let i = 0; i < this.seriesData.length; i++) tempCount += this.seriesData[i];
            let newData = [];
            for (let i = 0; i < this.legendData.length; i++) {
                newData.push({
                    value: this.seriesData[i],
                    name:
                        this.legendData[i] +
                        ":" +
                        ((100 * this.seriesData[i]) / tempCount).toFixed(
                            2
                        ) +
                        "%"
                })
            }
            this.option.series[0].data = newData;

            this.myChart.setOption(this.option);
        },
        getFontSize(val) {
            if (typeof (val) == 'number') return val;

            if (typeof (val) == 'string') {

                if (val.indexOf('%') > 0) {
                    var tmp = parseFloat(val.replace('%', '')) / 100;
                    let height = this.$refs.element.offsetHeight;
                    return Math.round(height * tmp);
                }
            }

            return 0;
        }
    }
}
</script>
<style scoped>
.pie {
    width: 100%;
    height: 100%;
}
</style>
