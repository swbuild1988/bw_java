<template>
    <div class="containerBox">
        <!-- 管廊介绍 -->
        <section class="tunnelSection">
            <div class="tunnelSurvey">太原市晋原综合管廊一期共有管廊{{tunnelsNum}}条</div>
            <div class="tunnelDetail" v-for="item in tunnels" :key="item.id">
                {{item.name}}长{{item.length}}公里
            </div>
        </section>
        <!-- 设备个数介绍 -->
        <section class="equipmentSection">
            <div class="equipmentSurvey">太原市晋原综合管廊一期共有设备{{equipmentNum}}个</div>
            <div class="equipmentDetail" v-for="item in equipments" :key="item.id">
                {{item.name}}共{{item.number}}个
            </div>
        </section>
    </div>
</template>
<script>
export default {
    data(){
        return{
            tunnels: [
                { id: 1, name: '古城大街', length: '3.87' },
                { id: 2, name: '实验路', length: '0.84' },
                { id: 3, name: '纬三路', length: '3.84' },
                { id: 4, name: '经二路', length: '0.795' },
                { id: 5, name: '经三路', length: '0.805' }
            ],
            tunnelsNum: null,
            equipments: [
                { id: 1, name: '管廊设备', number: '30' },
                { id: 2, name: '备品备件', number: '21' },
                { id: 1, name: '仪表工具', number: '25' }
            ],
            equipmentNum: null
        }
    },
    mounted() {
        this.tunnelsNum = this.tunnels.length
        this.equipments.forEach(item=>{
            this.equipmentNum += Number(item.number)
        })
    }
}
</script>
<style scoped>
.containerBox{
    height: 90vh; 
    margin-top: 12vmin;
}
.tunnelSection,.equipmentSection{
    color: #ffffff;
    width: 50%;
    margin: 5vmin auto;
    border: 1px solid #367dc1;
    padding: 2vmin;
    padding-top: 0;
    border-radius: 1.4vmin;
    letter-spacing: 0.2vmin;
}
.tunnelSurvey,.equipmentSurvey{
    font-size: 2.8vmin;
    line-height: 7vmin;
}
.tunnelDetail,.equipmentDetail{
    font-size: 2vmin;
    line-height: 4.5vmin;
    margin-left: 4vmin;
}
</style>
