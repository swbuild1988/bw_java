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
                太原市<br />综合管廊展示
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
            equipments: [],
            equipmentNum: null,
            tunnelIcon: require('../../../../assets/UM/tunnelIcon.png'),
            equipmentIcon: require('../../../../assets/UM/equipmentIcon.png')
        }
    },
    mounted() {
        this.tunnelsNum = this.tunnels.length
        EquipmentService.getEquTypeAndCount().then(
            result => {
                this.equipments = result
                this.getEquipmentNums()
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
    font-size: 9vmin;
    color: #fff;
    font-weight: 800;
    text-align: center;
    margin-left: 32vmin;
    margin-top: 13vmin;
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
