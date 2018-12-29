<template>
    <div class="positionInformation">
        <div class="titleInformation">{{ Title }}</div>
        <div class="rowInfor lf">
            <strong>街道 :</strong>
            {{ currPosition.tunnelName }}
        </div> 
        <div class="storeInfor lf">
            <strong>区域 :</strong>
            {{ currPosition.areaName }}
        </div>
        <div class="sectionInfor lf">
            <strong>监测仓 :</strong>
            {{ currPosition.storeName }}
        </div>
    </div>
</template>
<script>
export default {
    props:{
        currPosition:{
            default: ()=>{
                return {
                    tunnelName:'',
                    areaName:'',
                    storeName:''
                }
            }
        }
    },
    data(){
        return {
            Title:'当前位置',
        }
    },
    watch:{
    }
}
</script>
<style scoped>
    .positionInformation {
        position: absolute;
        top: 5%;
        right: 7%;
        z-index: 1000;
        color: white;
        height: 18%;
        width: 28%;
        background: url(../../../assets/VM/alarmContent.png) no-repeat;
        background-size: contain;
        background-position: center;
    }
    .titleInformation {
        text-align: center;
        color: rgba(0, 255, 255, 0.8);
        font-weight: bold;
        font-size: 1rem;
        margin: 1rem 0 0.3rem;
    }
    .lf{
        margin-left: 2rem;
        width: 80%;
        margin-bottom: .2rem;
        color: rgba(0, 255, 255, 0.8);
    }
      /* 小屏幕（显示器，小于等于 1920px） */
@media (max-width: 1920px) {
    .positionInformation {
        display: none;
    }
}
</style>


