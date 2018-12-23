<template>
    <div style="padding: 10px;">
        <div class="queryCondition">
            <Row>
                <Col span="6">             
                    监测仓:
                    <Select v-model="conditions.tunnelId" style="width:60%">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
            </Row>
        </div>
        <div class="userInfo">
            <Row>
                <Col span="14" class="TunnelImg">
                    <img :src=TunnelUser>
                </Col>
                <Col span="10">
                    <div class="userInfoBox">
                       <div class="robotInfo" v-for="(item,index) in robotInfo" :key="index" v-if="activeIndex===index">
                           <div class="robotDetails">
                               <h2>{{item.robotName}}</h2>
                               <div>电量：{{item.electricQuantity}}</div>
                               <div>预计剩余时间：{{item.estimatedTimeRemaining}}</div>
                               <div>当前状态：{{item.curStatus}}</div>
                               <div>累计使用时间：{{item.accumulatedUseTime}}</div>
                           </div>
                           <div class="robotImg">
                               <img :src="item.imgUrl">
                           </div>
                           <div class="btnBox">
                               <Button>查看视频</Button>
                               <!-- <Button>详细信息</Button> -->
                           </div>
                           <div class="btnBox">
                               <!-- <Button>查看视频</Button> -->
                               <Button @click="goToMoudle({ path:'/UM/RobotMonitoring/details'})">详细信息</Button>
                           </div>
                       </div>
                        <div class="robotInfoRough" v-for="(item,index) in robotInfo" :key="item.userId" @click="toArticleDetail(index)" v-if="activeIndex!=index">{{item.robotName}}</div>
                    </div>
                </Col>
            </Row>
        </div>
    </div>
</template>
<script>
import { TunnelService } from '../../../../services/tunnelService'
import wheelRobot from '../../../../assets/UM/wheelRobot.jpg'
import snakeRobot from '../../../../assets/UM/snakeRobot.jpg'
import tractRobot from '../../../../assets/UM/tractRobot.png'
import TunnelUser from '../../../../assets/UM/TunnelUser.png'
export default {
    data(){
        return{
            activeIndex: 0,
            conditions: {
                tunnelId: null
            },
            tunnels:[],
            robotInfo:[
                {
                    robotId: 1,
                    robotName: '轮式机器人',
                    electricQuantity: '80%',
                    estimatedTimeRemaining: '36h',
                    curStatus: '正常',
                    accumulatedUseTime: '200h',
                    imgUrl: wheelRobot
                },
                {
                    robotId: 2,
                    robotName: '蛇形机器人',
                    electricQuantity: '70%',
                    estimatedTimeRemaining: '25h',
                    curStatus: '正常',
                    accumulatedUseTime: '200h',
                    imgUrl: snakeRobot
                },
                {
                    robotId: 3,
                    robotName: '履带式机器人',
                    electricQuantity: '100%',
                    estimatedTimeRemaining: '236h',
                    curStatus: '正常',
                    accumulatedUseTime: '0h',
                    imgUrl: tractRobot
                }
            ],
        }
    },
    mounted() {
        // 从数据库读取select的option选项
        let _this = this
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnels = result
            },
            (error)=>{
                console.log(error)
            })
    },
    methods:{
        goToMoudle: function (path) {
            this.$router.push(path);
        },
        toArticleDetail(index) {
            this.activeIndex = index;
        },
    } 
}
</script>

<style scoped>
    .queryCondition{
        margin-bottom: 20px;
    }
    .TunnelImg{
        padding-right: 20px;
    }
    .TunnelImg img{
        width: 100%;
        height: 70vh;
    }
    .userInfoBox{
        border: 1px solid #b3b0b0;
        border-radius: 8px;
        width: 100%;
        height: 70vh;
        padding: 10px;
        overflow-y: auto;
        text-align: center;
    }
    .robotInfoRough{
        border: 1px solid #b3b0b0;
        border-radius: 8px;
        width: 100%;
        height: 7.5vh;
        margin-top: 10px;
        line-height: 7.5vh;
        text-align: center;
        font-size: 17px;
        font-weight: 700;
    }
    .robotDetails,.robotImg{
        display: inline-block;
        vertical-align: top;
        height: 25vh;
    }
    .robotDetails{
        width: 25vh;
        text-align: center;
        line-height: 5vh;
        font-size: 14px;
    }
    .robotImg{
        width: 25vh;
        border: 1px solid black;
    }
    .robotImg img{
        height: 100%;
        width: 100%;
    }
    .btnBox{
        margin-top: 3vh;
        margin-bottom: 2vh;
        width: 49%;
        display: inline-block;

    }
</style> 
