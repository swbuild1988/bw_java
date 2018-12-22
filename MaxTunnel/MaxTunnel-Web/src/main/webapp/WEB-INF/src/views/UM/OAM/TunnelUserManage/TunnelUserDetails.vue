<template>
    <div>
        <Row>
            <Col span="12">
                <div class="userInfo">
                    <div class="userImg">
                        <img :src="user1.imgUrl">
                    </div>
                    <div class="userDetails">
                        <h2 align="center">{{user1.userName}}</h2>
                        <div>性别：{{user1.sex}}</div>
                        <div>年龄：{{user1.age}}</div>
                        <div>公司：{{user1.unit}}</div>
                        <div>职位：{{user1.position}}</div>
                        <div>联系方式：{{user1.tel}}</div>
                        <div>当前位置：{{user1.curLocation}}</div>
                    </div>
                </div>
            </Col>
            <Col span="12">
                <div class="permission">
                    <h2>权限展示</h2>
                </div>
            </Col>
            <Col span="12">
                <div class="userEnterTunnelRecord">
                    <h2>用户入廊日志</h2>
                    <ul>
                        <li v-for="(item,index) in user1.enterTunnelRecords" :key="item.index">
                            {{item.enterTunnelTime}}从{{item.enterTunnelLocation}}进入管廊
                        </li>
                    </ul>
                </div>
            </Col>
            <Col span="12">
                <div class="map"></div>
            </Col>
        </Row>
    </div>
</template>
<script>
export default {
    data(){
        return{
            user1:{
                userName: '李安',
                sex: '男',
                age: '25',
                unit: '北京市燃气公司',
                position: '总督工',
                tel: '13756851203',
                curLocation: '第一大区燃气管道',
                imgUrl: '../../../../../static/UM/01.jpg',
                enterTunnelRecords:[
                    {
                        enterTunnelTime: '2018-06-15 14:04:36',
                        enterTunnelLocation: '第三十六燃气管道口'
                    },
                    {
                        enterTunnelTime: '2018-06-15 14:04:36',
                        enterTunnelLocation: '第三十六燃气管道口'
                    },
                    {
                        enterTunnelTime: '2018-06-15 14:04:36',
                        enterTunnelLocation: '第三十六燃气管道口'
                    },
                ]           
            }
        }
    }
}
</script>
<style scoped>
    .userImg,.userDetails{
        display: inline-block;
        vertical-align: top;
        width: 25vh;
        height: 31vh;
    }
    .userImg img{
        width: 100%;
        height: 100%;
    }
    .userDetails{
        border: 1px solid #b3b0b0;
        border-radius: 8px;
        margin-left: 5vh;
        width: 29vh;
        font-size: 14px;
        overflow-y: auto;
        padding-top: 5px;
    }
    .userDetails div{
        padding: 1vh;
        padding-left: 4vh;
    }
    .userInfo,.permission,.userEnterTunnelRecord,.map{
        width: 60vh;
        height: 31vh;
        border: 1px solid #b3b0b0;
        border-radius: 8px;
        margin-bottom: 5vh;
        padding: 1vh;
    }
    .userInfo{
        border: none;
        padding: 0;
    }
    .userEnterTunnelRecord ul{
        border: 1px solid #b3b0b0;
        border-radius: 8px;
        height: 25vh;
        padding: 5px;
        overflow-y: auto;
    }
    .userEnterTunnelRecord ul li{
        list-style: none;
        font-size: 13px;
        line-height: 3vh;
    }
</style>
