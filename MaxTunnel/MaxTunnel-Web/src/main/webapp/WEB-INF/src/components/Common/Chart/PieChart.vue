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
                    textStyle: {
                        color: '#ccc',
                        fontSize: _this.getFontSize('6.5%')
                    }
                },
                grid: {
                    top: this.title.length > 0 ? '12%' : '1%',
                    left: '3%',
                    right: '3%',
                    bottom: '1%'
                },
                series: [
                    {
                        type: "pie",
                        radius: '50%',
                        center: ['50%', '50%'],
                        data: []
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

            console.log("this.seriesData", this.seriesData);
            console.log("this.legendData", this.legendData)

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
