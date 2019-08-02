<template>
    <div class="categoryBar" :id="id" ref="categoryBar"></div>
</template>
<script>
export default {
    name: "CategoryBarChart",
    props: {
        id: {
            type: String,
            required: true
        },
        title: {
            default: "",
            type: String
        },
        series: {
            type: Array,
            default: () => {
                return [
                    {
                        name: "古城大街",
                        data: [320, 332, 301, 334, 390]
                    },
                    {
                        name: "实验路",
                        data: [220, 182, 191, 234, 290]
                    },
                    {
                        name: "经三路",
                        data: [150, 232, 201, 154, 190]
                    }
                ];
            }
        },
        xLable: {
            type: Array,
            default: () => {
                return ["温度", "湿度", "氧气", "硫化氢", "风机"];
            }
        },
        colors: {
            type: Array,
            default: () => {
                return [
                    "#e5c52f",
                    "#6bade1",
                    "#6fe46c",
                    "#e06ce4",
                    "#e48e6c",
                    "#c23531"
                ];
            }
        }
    },
    data() {
        return {
            option: {
                title: {
                    text: ""
                }
            },
            myChart: null
        };
    },
    watch: {
        series: {
            handler(newValue, oldValue) {
                if (newValue.length && newValue.length > 0) this.draw();
            },
            deep: true,
            immediate: true
        }
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            let _this = this;
            _this.myChart = _this.$echarts.init(
                document.getElementById(_this.id)
            );
            _this.draw();
            window.addEventListener("resize", this.myChart.resize);
            window.addEventListener("resize", this.resize);
        },
        resize() {
            this.draw();
        },
        draw() {
            this.option = {
                color: this.colors,
                tooltip: {
                    trigger: "axis",
                    axisPointer: {
                        type: "shadow"
                    }
                },
                title: {
                    text: this.title,
                    textStyle: {
                        color: "#fff",
                        fontSize: this.getFontSize("4%")
                    }
                },
                legend: {
                    data: [],
                    textStyle: {
                        color: "#fff",
                        fontSize: this.getFontSize("3%")
                    }
                },
                calculable: true,
                xAxis: [
                    {
                        type: "category",
                        axisTick: { show: false },
                        data: this.xLable,
                        axisLabel: {
                            color: "#fff",
                            fontSize: this.getFontSize("3%")
                        }
                    }
                ],
                yAxis: [
                    {
                        type: "value",
                        axisLabel: {
                            color: "#fff",
                            fontSize: this.getFontSize("3%")
                        }
                    }
                ],
                series: []
            };

            this.series.forEach(item => {
                this.option.legend.data.push(item.name);
                this.option.series.push({
                    name: item.name,
                    type: "bar",
                    data: item.data
                });
            });

            this.myChart.setOption(this.option);
        },
        sizeFunction(x) {
            var min = Math.min.apply(
                null,
                this.series.map(o1 => {
                    return Math.min.apply(
                        null,
                        o1.map(o2 => {
                            return parseFloat(o2[2]);
                        })
                    );
                })
            );
            var max = Math.max.apply(
                null,
                this.series.map(o1 => {
                    return Math.max.apply(
                        null,
                        o1.map(o2 => {
                            return parseFloat(o2[2]);
                        })
                    );
                })
            );

            // 最小的5%，最大的15%
            var y = 5 + ((x - min) / (max - min)) * (15 - 5);
            var size = this.getFontSize(y + "%");
            return size;
        },
        getFontSize(val) {
            if (typeof val == "number") return val;

            if (typeof val == "string") {
                if (val.indexOf("%") > 0) {
                    var tmp = parseFloat(val.replace("%", "")) / 100;
                    let height = this.$refs.categoryBar.offsetHeight;
                    return Math.round(height * tmp);
                }
            }

            return 0;
        }
    }
};
</script>
<style scoped>
.categoryBar {
    position: relative;
    height: 100%;
    width: 100%;
}
</style>
