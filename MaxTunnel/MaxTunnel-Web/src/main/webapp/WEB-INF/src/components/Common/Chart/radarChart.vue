<template>
    <div class="radar" :id="id" ref="element">
    </div>
</template>
<script>
export default {
    name: 'radar',  
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
        radarindicator: {
            type: Array
        },
        seriesData: {
            type: Array
        }  
    },   
    data() {
        return {
            myChart: {},
            option: {}
        }
    },
    mounted(){
        this.init()
    },
    methods: {
        init(){
            this.resize()
            window.addEventListener('resize', this.myChart.resize);
            window.addEventListener('resize', this.resize);
        },
        resize(){
            this.drawBar()
        },
        drawBar() {
            var _this = this
            this.myChart = this.$echarts.init(
                document.getElementById(this.id)
            );
            this.option = {
                title: {
                    text: this.title,
                    textStyle: {
                        color: '#ccc',
                        fontSize: _this.getFontSize('8%')
                    }
                },
                tooltip: {  
                },
                legend: {
                    data: this.legendData,
                    textStyle: {
                        color: '#ccc',
                        fontSize: _this.getFontSize('7%')
                    },
                },
                radar: {
                    name: {
                        textStyle: {
                            color: '#fff',
                            backgroundColor: '#999',
                            borderRadius: 3,
                            padding: [3, 5]
                        }
                    },
                    indicator: this.radarindicator,
                    splitLine: {
                        show: true,
                        areaStyle: {
                            width: 1,
                            color: '#286fbb'
                        }
                    }
                },
                series: [{
                    name: '预算 vs 开销（Budget vs spending）',
                    type: 'radar',
                    data : this.seriesData
                }]
            },
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
<style scope>
.radar{
    width: 80%;
    height: 100%;
}
</style>
