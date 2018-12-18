<template>
    <div :id="id" class="SimpleRing"></div>
</template>
<script>
export default {
    name: 'SimpleRing',
    props:{
        id: {
            default: 'ring'
        },
        explain: {
            default: '占比'
        }
    },
    data(){
        return {
                myChart: {},
                option: {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    graphic:{
                        type:'text',
                        left:'center',
                        top:'center',
                        style:{
                            text:'explain',
                            textAlign:'center',
                            fill:'#000',
                            width:30,
                            height:30
                        }
                    },
                    series : [
                        {
                            name:'',
                            type:'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    show: false,
                                    textStyle: {
                                        fontSize: '30',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            data:[
                                // {value:335, name:'备品占比'},
                                // {value:310, name:'邮件营销'},
                            ]
                        }
                    ]
                }
            }
    },
    mounted(){
        this.init()
    },
    methods:{
        init(){
            this.drawBar();
            // this.fetchData(this.requestUrl);
        },
        drawBar(){
            let _this=this;
            _this.myChart = _this.$echarts.init(document.getElementById(_this.id));
            window.addEventListener('resize', _this.myChart.resize);
            _this.option.graphic.style.text = _this.explain;
            _this.myChart.setOption(_this.option);
        },
        fetchData(requestUrl){
            let _this=this;
            _this.axios.get(requestUrl).then(result=>{
                let { code, data } = result.data;
                if(code==200){
                    let newData=data.reduce((init,item)=>{
                        return{
                            xData:init.xData==undefined?[].concat(item.key):[].concat(init.xData,item.key),
                            yData:init.yData==undefined?[].concat(item.val):[].concat(init.yData,item.val)
                        }
                    },{})
                    if(JSON.stringify(newData.xData)!=JSON.stringify(_this.xData)||JSON.stringify(newData.yData)!=JSON.stringify(_this.xData)){
                        _this.xData=newData.xData;
                        _this.yData=newData.yData;
                        _this.myChart.setOption({
                            xAxis:{data:_this.xData},
                            series:{data:_this.yData}
                        })
                    }
                }
            })
        },
    },

}
</script>
<style scoped>
.SimpleRing{
    width: 100%;
    height: 100%;
}
</style>


