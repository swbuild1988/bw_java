<template>
    <div class="nonCleanedCount​">
        <div class="NonCleanedTitle">{{ Title }}</div>
        <div class="NonCleanedCount">123</div>
        <div style="text-align: center; font-size: .8rem; color: rgba(255, 255, 255, 0.7);">{{ unit }}</div>
    </div>
</template>

<script>
    import { getJson } from "../../../scripts/commonFun";

    export default {
        data(){
            return {
                Title:'未清除告警总数',
                NonCleanedCount:0,
                unit:'条',
                timer:null,
            }
        },
        mounted(){
            this.init();
        },
        methods:{
            init(){
                this.getAllNonCleanedAlarm();
            },
            getAllNonCleanedAlarm(){
                let { timer,NonCleanedCount } = this;

                timer = setInterval(()=>{  //每秒轮询未清除告警
                    getJson('alarms/non-cleaned/list')
                        .then(result => NonCleanedCount = result.length)
                },1000)

            }
        },
        beforeDestroy(){
            clearInterval( this.timer );
        }
    }
</script>

<style scoped>
    .nonCleanedCount​{
        position: absolute;
        top: 15.5%;
        left: 2%;
        z-index: 1000;
        color: white;
        text-align: center;
        height: 16%;
        width: 20%;
        background: url(../../../assets/VM/alarmContent.png) no-repeat;
        background-size: contain;
        background-position: center;
    }
    .NonCleanedTitle{
        text-align: center;
        color: rgba(0, 255, 255, 0.8);
        font-weight: bold;
        font-size: 1rem;
        margin: 1rem 0 0.3rem;
    }
    .NonCleanedCount {
        text-align: center;
        font-size: 1.6rem;
        color: rgba(0, 255, 255, 0.8);
        font-weight: bold;
    }
</style>