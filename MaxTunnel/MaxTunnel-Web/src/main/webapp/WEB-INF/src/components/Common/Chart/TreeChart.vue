<template>
    <div class="treeChart" :id="id"></div>
</template>
<script>
export default {
    name: 'tree-chart',
    props: {
        id: null,
        data: null
    },
    data(){
        return {
            myChart: {},
            option: {}
        }
    },
    watch: {
        'data': function(newVal, oldVal){
            this.init()
        }
    },
    mounted(){
        this.init()
    },
    methods: {
        init(){
            this.myChart = this.$echarts.init(document.getElementById(this.id))
            this.myChart.setOption({
                tooltip: {
                    trigger: 'item',
                    triggerOn: 'mousemove'
                },
                series:[
                    {
                        type: 'tree',

                        data: [this.data],

                        left: '2%',
                        right: '2%',
                        top: '8%',
                        bottom: '20%',

                        symbol: 'emptyCircle',

                        orient: 'vertical',

                        expandAndCollapse: true,

                        label: {
                            normal: {
                                position: 'top',
                                verticalAlign: 'middle',
                                align: 'center',
                                fontSize: 16,
                                color: '#ccc'
                            }
                        },

                        leaves: {
                            label: {
                                normal: {
                                    position: 'bottom',
                                    rotate: -90,
                                    verticalAlign: 'middle',
                                    align: 'left'
                                }
                            }
                        },

                        animationDurationUpdate: 750
                    }
                ]
            })
            this.myChart.on('click', function(params){})
        },
    }
}
</script>
<style scope>
.treeChart{
    width: 100%;
    height: 80vh;
}
</style>
