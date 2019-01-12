<template>
    <div class="radar" :id="id">
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
            option: {
                title: {
                    text: this.title,
                    textStyle: {
                            color: '#ccc'
                        }
                },
                tooltip: {  
                },
                legend: {
                    data: this.legendData
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
                    indicator: this.radarindicator
                },
                series: [{
                    name: '预算 vs 开销（Budget vs spending）',
                    type: 'radar',
                    data : this.seriesData
                }]
            },
        }
    },
    mounted(){
        this.drawBar()
    },
    methods: {
        drawBar() {
            this.myChart = this.$echarts.init(
                document.getElementById(this.id)
            );
            this.myChart.setOption(this.option);
            window.addEventListener("resize", this.myChart.resize);
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
