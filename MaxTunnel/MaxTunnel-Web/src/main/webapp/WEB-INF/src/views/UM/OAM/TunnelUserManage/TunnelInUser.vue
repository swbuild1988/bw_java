<template>
    <div>
        <div class="queryCondition">
            <Row>
                <Col span="6">             
                    所属管廊:
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
                    <img src="../../../../../static/UM/TunnelUser.png">
                </Col>
                <Col span="10">
                    <div class="userInfoBox">
                        <div class="userInfoSpecific" v-for="(item,index) in userInfo" :key="item.id" v-if="activeIndex===index">
                            <div class="picVideoBox">
                                <div class="userInfoSpecificPic">
                                    <img :src="item.imgUrl">
                                </div>
                                <div class="userInfoSpecificVideo">我是视频</div>
                            </div>
                            <div class="userInfoSpecificList">
                                <h2 align="center">{{item.userName}}</h2>
                                <div><span>单位：</span><span>{{item.unit}}</span></div>
                                <div><span>职位：</span><span>{{item.position}}</span></div>
                                <div><span>联系方式：</span><span>{{item.tel}}</span></div>
                                <div><span>上次进入时间：</span><span>{{item.lastTime}}</span></div>
                                <div>
                                    <span>位置描述：</span><br>
                                    <textarea class="location" :value="item.locationDec"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="userInfoRough" v-for="(item,index) in userInfo" :key="item.userId" @click="toArticleDetail(index)" v-if="activeIndex!=index">{{item.userName}}</div>
                    </div>
                </Col>
            </Row>
        </div>
    </div>
</template>
<script>
import { TunnelService } from '../../../../services/tunnelService'
export default {
    data(){
        return{
            activeIndex: 0,
            conditions: {
                tunnelId: null
            },
            tunnels:[],
            userInfo:[
                {
                    userId: 1,
                    userName: '张安',
                    unit: '北京市燃气公司',
                    position: '总督工',
                    tel: '13756851203',
                    lastTime: '2018-6-14 19:33:06',
                    locationDec: '我在第一大区燃气管道',
                    imgUrl: '../../../../../static/UM/01.jpg'
                },
                {
                    userId: 2,
                    userName: '李铭顺',
                    unit: '北京市燃气公司',
                    position: '总督工',
                    tel: '13725694585',
                    lastTime: '2018-6-14 19:33:06',
                    locationDec: '我在第一大区燃气管道',
                    imgUrl: '../../../../../static/UM/02.jpeg'
                },
                {
                    userId: 3,
                    userName: '孟冬秋',
                    unit: '北京市燃气公司',
                    position: '总施工',
                    tel: '17256891585',
                    lastTime: '2018-6-14 19:33:06',
                    locationDec: '我在第一大区燃气管道',
                    imgUrl: '../../../../../static/UM/03.jpg'
                }
            ],
        }
    },
    mounted() {
        // 从数据库读取select的option选项
        let _this = this;
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnels = result;
            },
            (error)=>{
                console.log(error)
        })
    },
    methods:{
        toArticleDetail(index) {
            this.activeIndex = index;
        }
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
    }
    .userInfoSpecific{
        width: 100%;
        height: 70%;

    }
    .userInfoRough{
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
    .picVideoBox,.userInfoSpecificList{
        display: inline-block;   
        vertical-align: top;
        width: 49%;
    }
    .userInfoSpecificPic,.userInfoSpecificVideo{
        width: 100%;
        border: 1px solid #b3b0b0;
        border-radius: 8px;
        height: 22vh;
        line-height: 22vh;
        text-align: center;
    }
    .userInfoSpecificPic img{
        height: 22vh;
    }
    .userInfoSpecificPic{
        margin-bottom: 10px;
        border: none;
    }
    .userInfoSpecificList{
        width: 50%;
    }
    .userInfoSpecificList div{
        padding-left: 5vh;
        line-height: 5vh;
        font-size: 14px;    
    }
    .location{
        width: 100%;
        border-radius: 4px;
        padding: 5px;
    }
</style>
