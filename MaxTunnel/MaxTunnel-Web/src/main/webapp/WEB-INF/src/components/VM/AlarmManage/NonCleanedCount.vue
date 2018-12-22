<template>
    <div class="nonCleanedCount​">
        <div class="NonCleanedTitle">{{ Title }}</div>
        <div class="NonCleanedCount">123</div>
        <div class="NonCleanedUnit">{{ unit }}</div>
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
            margin: 1rem 0 0.3rem;
        }
        .NonCleanedCount {
            font-size: 1.6rem;
        }
        .NonCleanedUnit {
            font-size: .8rem;
        }
        
    }
    /* 大屏幕（显示器，大于等于 1920px） */
    @media (min-width: 1921px) {
            
        .NonCleanedTitle{
            font-size: 3rem;
            margin: 5rem 0 0.3rem;
        }
        .NonCleanedCount {
            font-size: 2.6rem;
        }
        .NonCleanedUnit {
            font-size: 1.8rem;
        }
    }
</style>