<template>
    <div class="nonCleanedCount​">
        <div class="NonCleanedTitle">{{ Title }}</div>
        <div class="NonCleanedCount">{{ NonCleanedCount }}</div>
        <div class="NonCleanedUnit">{{ unit }}</div>
    </div>
</template>

<script>
    import { DataAnalysisService } from "../../../services/dataAnalysisService";

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
                // this.timer = setInterval(()=>{  //每秒轮询未清除告警
                //     DataAnalysisService.NonClearAlarms().then( result =>  this.NonCleanedCount = result.length )
                // },1000)
                DataAnalysisService.NonClearAlarms()
                .then( result =>  this.NonCleanedCount = result.length )
                .finally(()=>{
                    let _this = this
                    this.timer = setTimeout(()=>{
                        _this.getAllNonCleanedAlarm()
                    },1000)
                })
            }
        },
        beforeDestroy(){
            clearTimeout( this.timer );
            this.timer = null;
            console.log('nonCleanedCount​',document.getElementsByClassName('nonCleanedCount​'))
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
    }
    .NonCleanedCount {
        text-align: center;
        color: rgba(0, 255, 255, 0.8);
        font-weight: bold;
    }
    .NonCleanedUnit {
        text-align: center;
        color: rgba(255, 255, 255, 0.7);
    }
    /* 小屏幕（显示器，小于等于 1920px） */
    @media (max-width: 1920px) {

        .NonCleanedTitle{
            font-size: 1rem;
            margin: .8rem 0 0 0rem;
        }
        .NonCleanedCount {
            font-size: 1.1rem;
        }
        .NonCleanedUnit {
            font-size: .8rem;
        }
        
    }
    /* 大屏幕（显示器，大于等于 1920px） */
    @media (min-width: 1921px) {
            
        .NonCleanedTitle{
            font-size: 2rem;
            margin: 2rem 0 0.3rem;
        }
        .NonCleanedCount {
            font-size: 2rem;
        }
        .NonCleanedUnit {
            font-size: 1.8rem;
        }
    }
</style>