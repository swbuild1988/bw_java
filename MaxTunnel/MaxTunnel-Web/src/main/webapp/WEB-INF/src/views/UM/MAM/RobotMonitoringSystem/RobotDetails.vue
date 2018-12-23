<template>
    <div>
        <Row>
            <Col span="12">
                <div class="robotImg">
                    <img :src="robotTop">
                </div>
                <div class="robotImg">
                    <img :src="robotBottom">
                </div>
            </Col>
            <Col span="12">
                <div class="robotDetailsBox">
                    <div class="robotDetails">
                        <div class="robotDetailsImg">
                            <img :src="robotDetails.imgUrl">
                        </div>
                        <div class="robotDetailsInfo">
                            <h2>{{robotDetails.robotName}}</h2>
                            <div>当前电量：{{robotDetails.electricQuantity}}</div>
                            <div>预计剩余时间：{{robotDetails.estimatedTimeRemaining}}</div>
                            <div>当前状态：{{robotDetails.curStatus}}</div>
                            <div style="margin-top: 10px;">当前位置：{{robotDetails.curLocation}}</div>
                        </div>
                    </div>
                    <div class="tableBox">
                        <Table border :columns="columns1" :data="data1"></Table>
                    </div>
                </div>
            </Col>
        </Row>
    </div>
</template>
<script>
import tractRobot from '../../../../assets/UM/tractRobot.png'
import robotTop from '../../../../assets/UM/robotTop.png'
import robotBottom from '../../../../assets/UM/robotBottom.png'
export default {
    data(){
        return{
            robotDetails:{
                robotId: 3,
                robotName: '履带式机器人',
                electricQuantity: '100%',
                estimatedTimeRemaining: '236h',
                curStatus: '正常',
                accumulatedUseTime: '0h',
                curLocation: '管廊25.8KM处',
                imgUrl: tractRobot
            },
            columns1:[
                {
                    title: '开始时间',
                    key: 'startTime',
                    align: 'center'
                },
                {
                    title: '结束时间',
                    key: 'endTime',
                    align: 'center'
                },
                {
                    title: '结果',
                    key: 'result',
                    align: 'center'
                }
            ]
        }
    }
}
</script>
<style scoped>
    .robotImg{
        width: 100%;
        height: 35vh;
        margin-bottom: 2vh;
    }
    .robotImg img{
        width: 98%;
        height: 100%;
    }
    .robotDetailsBox{
        border: 1px solid #b3b0b0;
        border-radius: 8px;
        height: 72vh;        
    }
    .robotDetailsImg,.robotDetailsInfo{
        display: inline-block;
        vertical-align: top;
        width: 49%;
        padding: 5%;
    }
    .robotDetailsImg img{
        width: 80%;
    }
    .robotDetailsInfo div{
        line-height: 5vh;
        font-size: 14px;
    }
    .tableBox{
        margin: 5px;
    }
</style>
