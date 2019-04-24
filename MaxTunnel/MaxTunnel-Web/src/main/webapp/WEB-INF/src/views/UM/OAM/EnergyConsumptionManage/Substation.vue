<template>
    <div>
        <div class="title">变电所信息</div>
        <Table 
            border 
            :columns="powerSubstationColumns" 
            :data="powerSubstationsData" 
            style="margin:20px;"
        ></Table>
    </div>
</template>
<script>
import PowerSubstationService from '../../../../services/powerSubstationService'
    export default {
        data(){
            return {
                powerSubstationColumns:[
                    // {
                    //     title: '所属管廊',
                    //     key: 'tunnel',
                    //     align: 'center',
                    //     render: (h,params)=>{
                    //         return h('div',params.row.tunnel.name)
                    //     }
                    // },
                    {
                        title: '进线电压监测对象',
                        key: 'invId',
                        align: 'center',
                        // render: (h,params) => {
                        //     return h('div',params.row.inv.name)
                        // }
                    },
                    {
                        title: '进线电流监测对象',
                        key: 'inaId',
                        align: 'center',
                        // render: (h,params) => {
                        //     return h('div',params.row.ina.name)
                        // }
                    },
                    {
                        title: '出线电压监测对象',
                        key: 'outvId',
                        align: 'center',
                        // render: (h,params) => {
                        //     return h('div',params.row.outv.name)
                        // }
                    },
                    {
                        title: '出线电流监测对象',
                        key: 'outaId',
                        align: 'center',
                        // render: (h,params) => {
                        //     return h('div',params.row.outa.name)
                        // }
                    },
                    {
                        title: '实时功率监测对象',
                        key: 'powerId',
                        align: 'center',
                        // render: (h,params) => {
                        //     return h('div',params.row.power.name)
                        // }
                    },
                    {
                        title: '供电半径',
                        key: 'eleRadius',
                        align: 'center',
                    },
                    {
                        title: '功率因数',
                        key: 'powerFactor',
                        align: 'center',
                    },
                    {
                        title: '功率补偿因数',
                        key: 'compensationFactor',
                        align: 'center',
                    },
                    {
                        title: '位置信息',
                        key: 'position',
                        align: 'center',
                    },
                    {
                        title: '创建时间',
                        key: 'time',
                        align: 'center',
                        render: (h,params)=>{
                            let crtTime = params.row.time
                            let time = crtTime == null ? '' : new Date(crtTime).format('yyyy-MM-dd hh:mm:ss')
                            return h('div',time)
                        }
                    }
                ],
                powerSubstationsData: [],
                curTunnelId: null
            }
        },
        mounted(){
            this.curTunnelId = this.$route.params.id
            this.init()
        },
        watch: {
            '$route': function () {
                this.curTunnelId = this.$route.params.id;
                this.init()
            }
        },
        methods:{
            init(){
                let param = {
                    tunnelId: this.curTunnelId
                }
                PowerSubstationService.getSubstationList(param).then(
                    res=>{
                        this.powerSubstationsData = res
                    },
                    error=>{
                        this.Log.info(error)
                    }
                )
            }
            
        }
    }
</script>
<style scoped>
    .title{
        font-size: 2vmin;
        margin: 1vmin 2vmin;
        font-weight: bold;
    }
</style>


