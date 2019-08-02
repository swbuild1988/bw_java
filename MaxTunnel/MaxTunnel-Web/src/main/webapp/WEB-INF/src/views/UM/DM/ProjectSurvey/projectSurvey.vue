<template>
    <div class="containerBox">
        <section class="surveyTitle">
            <p v-text="titleInfo"></p>
        </section>
        <!-- 管廊介绍 -->
        <section class="tunnelSection">
            <div class="tunnelSurvey">
                <img class="icon" :src="tunnelIcon" />
                    晋源综合管廊一期共有管廊{{tunnelsNum}}条
                </div>
            <div class="tunnelDetail" v-for="(item, index) in tunnels" :key="item.id">
                {{index+1}}、{{item.name}}长&nbsp;<span class="num">{{item.length}}</span>&nbsp;公里
            </div>
        </section>
        <!-- 设备个数介绍 -->
        <section class="equipmentSection">
            <div class="equipmentSurvey">
                <img class="icon" :src="equipmentIcon" />
                    晋源综合管廊一期共有设备{{equipmentNum}}个
            </div>
            <div class="equipmentDetail" v-for="(item, index) in equipments" :key="item.id">
                {{index+1}}、{{item.key}}：&nbsp;<span class="num">{{item.val}}</span>&nbsp;个
            </div>
        </section>
    </div>
</template>
<script>
import { EquipmentService } from "@/services/equipmentService";
import { TunnelService } from "@/services/tunnelService"
export default {
    data(){
        return{
            tunnels: [],
            tunnelsNum: null,
            equipments: [],
            equipmentNum: null,
            tunnelIcon: require('../../../../assets/UM/tunnelIcon.png'),
            equipmentIcon: require('../../../../assets/UM/equipmentIcon.png'),
            tunnelLogo: require('../../../../assets/UM/tunnelLogo.png'),
            titleInfo: '晋源综合管廊概览'
        }
    },
    mounted() {
        EquipmentService.getEquTypeAndCount().then(
            result => {
                this.equipments = result
                this.getEquipmentNums()
            },
            error => {
                this.Log.info(error)
            }
        )
        TunnelService.getTunnels().then(
            result => {
                result.splice(5, 1)
                this.tunnels = result
                result.forEach(item=>{
                    item.length = item.length/1000
                })
                this.tunnelsNum = result.length
            },
            error => {
                this.Log.info(error)
            }
        )
    },
    methods: {
        getEquipmentNums(){
            this.equipments.forEach(item=>{
                this.equipmentNum += Number(item.val)
            })
        }
    }
}
</script>
<style scoped>
.containerBox{
    background: url("../../../../assets/UM/surveyBG.png") no-repeat;
    background-size: 100% 100%;
    height: 106vh;
    margin-top: -9vh;
}
.surveyTitle{
    background: url("../../../../assets/UM/titleBG.png");
    background-size: 100% 100%;
    width: 50vmin;
    height: 5vmin;
    line-height: 5vmin;
    position: absolute;
    left: 50%;
    margin-left: -25vmin;
    top: 11vmin;
}
.surveyTitle p{
    font-size: 2.5vmin;
    text-align: center;
    color: #fff;
    font-weight: 700;
    letter-spacing: 0.5vmin;
}
.icon{
    width: 2.1vmin;
    height: 2.1vmin;
}
.tunnelSection, .equipmentSection{
    display: inline-block;
    vertical-align: top;
    color: #ffffff;
    padding: 2vmin;
    padding-top: 0;
    border-radius: 1.4vmin;
    letter-spacing: 0.2vmin;
    margin-top: 20vmin;
    word-wrap:break-word;
    top: 1vmin;
}
.tunnelSurvey,.equipmentSurvey{
    font-size: 2.1vmin;
    font-weight: 1000;
    margin-left: 3vmin;
    margin-bottom: 2vmin;
    width: 64vmin;
    padding-left: 2vmin;
    height: 5vmin;
    line-height: 5vmin;
}
.tunnelSurvey{
    background: url("../../../../assets/UM/leftBG.png");
    background-size: 100% 100%;
}
.equipmentSurvey{
    background: url("../../../../assets/UM/rightBG.png");
    background-size: 100% 100%;
    width: 89vmin;
    text-align: right;
    padding-right: 3vmin;
}
.tunnelDetail,.equipmentDetail{
    font-size: 2vmin;
    line-height: 4.5vmin;
    margin-left: 8vmin;
}
.equipmentDetail{
    margin-left: 54vmin;
}
.equipmentSection{
    position: absolute;
    right: 3vmin;
}
.num{
    font-size: 3vmin;
    color: #06dcfa;
}
</style>
