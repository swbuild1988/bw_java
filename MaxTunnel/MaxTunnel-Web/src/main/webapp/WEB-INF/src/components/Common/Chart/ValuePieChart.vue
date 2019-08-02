<template>
    <div class="valuePie" :id="id" ref="valuePieChart"></div>
</template>
<script>
export default {
    name: "value-pie-chart",
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
                        value: 9
                    },
                    {
                        name: "实验路",
                        value: 0
                    },
                    {
                        name: "经三路",
                        value: 0
                    }
                ];
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
            deep: true
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
                    trigger: "item",
                    formatter: "{b} : {c} ({d}%)",
                    textStyle: {
                        fontSize: this.getFontSize("4%")
                    }
                },
                title: {
                    text: this.title,
                    textStyle: {
                        color: "#fff",
                        fontSize: this.getFontSize("6%")
                    },
                    x: "center",
                    top: "2%"
                },
                legend: {
                    data: [],
                    textStyle: {
                        color: "#fff",
                        fontSize: this.getFontSize("4%")
                    },
                    orient: "vertical",
                    right: "2%",
                    top: "4%"
                },
                series: [
                    {
                        type: "pie",
                        radius: "55%",
                        center: ["40%", "54%"],
                        data: this.series,
                        label: {
                            formatter: "{b}:{c}",
                            textStyle: {
                                fontSize: this.getFontSize("4%")
                            }
                        }
                    }
                ]
            };

            this.series.forEach(item => {
                this.option.legend.data.push(item.name);
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
                    let height = this.$refs.valuePieChart.offsetHeight;
                    return Math.round(height * tmp);
                }
            }

            return 0;
        }
    }
};
</script>
<style scoped>
.valuePie {
    width: 100%;
    height: 100%;
}
</style>
