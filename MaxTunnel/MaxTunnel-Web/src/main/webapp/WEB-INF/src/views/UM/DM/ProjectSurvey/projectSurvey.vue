<template>
    <div class="containerBox">
        <section class="showBG">
            <!-- 管廊介绍 -->
            <section class="tunnelSection">
                <div class="tunnelSurvey">
                    <img class="icon" :src="tunnelIcon" />
                        晋源综合管廊一期共有管廊{{tunnelsNum}}条
                    </div>
                <div class="tunnelDetail" v-for="item in tunnels" :key="item.id">
                    {{item.name}}长&nbsp;<span class="num">{{item.length}}</span>&nbsp;公里
                </div>
            </section>
            <section class="surveyTitle">
                <img :src="tunnelLogo" />
                <!-- <div style="font-size: 9vmin">晋源管廊</div>
                <div style="font-size: 11vmin">平台展示</div>       -->
            </section>
            <!-- 设备个数介绍 -->
            <section class="equipmentSection">
                <div class="equipmentSurvey">
                    <img class="icon" :src="equipmentIcon" />
                        晋源综合管廊一期共有设备{{equipmentNum}}个
                </div>
                <div class="equipmentDetail" v-for="item in equipments" :key="item.id">
                    {{item.key}}：&nbsp;<span class="num">{{item.val}}</span>&nbsp;个
                </div>
            </section>
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
            tunnelLogo: require('../../../../assets/UM/tunnelLogo.png')
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
    height: 90vh; 
    margin-top: 12vmin;
    padding: 0 5vmin;
}
.showBG{
    background: url("../../../../assets/UM/showBG.png") no-repeat;
    background-size: 100% 100%;
    width: 100%;
    height: 100%;
    position: relative;
}
.icon{
    width: 2.1vmin;
    height: 2.1vmin;
}
.tunnelSection, .surveyTitle, .equipmentSection{
    display: inline-block;
    vertical-align: top;
}
.tunnelSection,.equipmentSection{
    color: #ffffff;
    padding: 2vmin;
    padding-top: 0;
    border-radius: 1.4vmin;
    letter-spacing: 0.2vmin;
    margin-top: 6vmin;
    word-wrap:break-word
}
.tunnelSurvey,.equipmentSurvey{
    font-size: 2.1vmin;
    font-weight: 1000;
    margin-left: 1vmin;
    margin-bottom: 2vmin;
}
.tunnelDetail,.equipmentDetail{
    font-size: 2vmin;
    line-height: 4.5vmin;
    margin-left: 6vmin;
}
.equipmentSection{
    position: absolute;
    right: 0vmin;
    top: 1vmin;
}
.surveyTitle{
    margin-left: 17vmin;
    margin-top: 2vmin;
}
.surveyTitle img{
    width: 85vmin;
    height: 85vmin;
}
.num{
    font-size: 3vmin;
}
@media (min-width: 2100px){
    .surveyTitle{
        margin-left: 18vmin;
    }
    .equipmentSection{
        position: absolute;
        right: 0vmin;
    }
}
@media (width: 1920px){
    .showBG{
        width: 100%;
        height: 90%;
    }
    .surveyTitle{
        margin-left: 18vmin;
    }
}
</style>
