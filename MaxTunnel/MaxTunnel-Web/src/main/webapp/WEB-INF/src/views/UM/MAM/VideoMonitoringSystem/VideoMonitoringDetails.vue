<template>
    <div>
        <Row class="query">
            <Col span="9" offset="1">
                监测仓:
                <Select v-model="conditions.storeId" style="width:60%;z-index: 9999;" id="store">
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in init.stores" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="9">
                区域:
                <Select v-model="conditions.areaId" style="width:60%;z-index: 9999;" id="area">
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in init.areas" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="2" offset="1">
                <Button type="primary" icon="ios-search" @click="search">查询</Button>
            </Col>
        </Row>
        <Row class="content">
            <Col span="5">
                <div class="control">
                    <h2 class="title">云台控制</h2>
                    <Row class="controlBody" style="height: 20vh"> 
                        <Col span="24">
                            <div class="controlContent">
                            <video-control @startDirectCtrl="start" @stopDirectCtrl="stop" v-bind:isDisabled="isDisabled"></video-control>
                            </div>
                        </Col>
                    </Row>
                </div>
                <div class="lists">
                    <h2 class="title">摄像头信息</h2>
                    <div style="height: 90%;" class="controlBody">
                        <h2 class="videoName" v-if="!curVideo">请选择摄像机</h2>
                        <h2 v-if="curVideo" class="videoName">{{ curVideo.name }}</h2>
                        <p v-if="curVideo" >{{ curVideo.description }}</p>
                        <div class="posContent" v-if="curVideo" >
                            <div class="positions" v-for="(pos,index) in perPositions" :key="index">
                                <span class="name">{{ pos }}</span>
                                <Button type="primary" size="small" @click="set(pos)" class="option">设置</Button>
                            </div>
                        </div>
                    </div>
                </div>
            </Col>
            <Col span="19">
                <div class="scenesList">
                    <span class="listTitle">摄像头列表</span>
                    <div class="more">
                        <Poptip placement="left">
                            <Icon type="navicon-round" size="20" class="button"></Icon>
                            <div class="api" slot="content">
                                <Icon class="screens" type="android-checkbox-outline-blank" size="20" @click.native="handleScreensNum(1)"></Icon>
                                <Icon class="screens" type="social-windows" size="20" @click.native="handleScreensNum(4)"></Icon>
                                <Icon class="screens" type="android-apps" size="20" @click.native="handleScreensNum(9)"></Icon>
                            </div>
                        </Poptip>
                    </div>
                    <Row style="width: 94%;margin-left: 3%;">
                        <Col span="1" class="slipContent">
                            <Icon type="chevron-left" size="30" :class="['slipLeft',{'enabled': curPage == 1}]" @click.native="pageChange('prev')"></Icon>
                        </Col>
                        <Col span="22">
                            <h1 v-if="nodata" style="text-align: center;">暂无数据</h1>
                            <Row>
                                <Col :span="videoNum == 4 ? 12 : (videoNum == 1 ? 24 : 8)" v-for="(item,index) in showVideosList" :key="item.id" :class="['monitors',{'active': curVideo && item.id == curVideo.id}]">
                                    <div @click="selectScene(item)">
                                        <div :class="['monitor',{'oneScreen':videoNum == 1}]" v-if="videoStyle.show">    
                                            <video-component v-bind:video="item" v-bind:id="'camera'+item.id"></video-component>
                                        </div>
                                        <div class="options">
                                            <div type="primary" @click="history(index)" class="history">历史记录</div>
                                            <div type="primary" @click="config(item)" class="config">相机设置</div>
                                        </div>
                                    </div>
                                </Col>
                            </Row>
                        </Col>
                        <Col span="1"  class="slipContent">
                           <Icon type="chevron-right" size="30"  :class="['slipRight',{'enabled' : curPage == totalPage}]" @click.native="pageChange('next')"></Icon>
                        </Col>
                    </Row>
                </div>
            </Col>
        </Row>
    </div>
</template>
<script>
import { TunnelService } from "../../../../services/tunnelService"
import { VideoService } from "../../../../services/videoService"
// import VideoControls from "../../../../components/UM/MAM/VideoControls"
import VideoControl from "../../../../components/UM/MAM/videoControls/VideoControl"
import VideoComponent from "../../../../components/Common/Video/VideoComponent"

export default {
    data() {
        return {
            conditions: {
                tunnelId: null,
                storeId: null,
                areaId: null
            },
            init: {
                stores: [],
                areas: []
            },
            cameraList: [
                // {
                //     id: 7001,
                //     name: "摄像头1",
                //     url: "192.168.6.156:8078",
                //     positionSupport: true,
                //     description: "A Camera for tunnel1"
                // },
                // {
                //     id: 7002,
                //     name: "摄像头2",
                //     url: "192.168.6.156:8078",
                //     positionSupport: false,
                //     description: "A Camera for tunnel2"
                // },
                // {
                //     id: 7003,
                //     name: "摄像头3",
                //     url: "192.168.6.156:8078",
                //     positionSupport: true,
                //     description: "A Camera for tunnel3"
                // }
            ],
            // curVideo: {
            //     id: null
            // },
            curVideo: null,
            videoStyle: {
                show: true
            },
            curPage: 1,
            nodata: false,
            isDisabled: true,
            videoNum: 4,
            perPositions: []
        };
    },
    components: { VideoControl, VideoComponent },
    watch: {
        $route: function() {
            this.conditions.tunnelId = this.$route.params.id;
            this.initData();
            this.videoNum = 4;
            this.curVideo = null;
            this.search();
            this.isDisabled = true;
            // if ($("#iframearea").length > 0) {
            //     let ifr = document.getElementById("iframearea");
            //     ifr.parentNode.removeChild(ifr);
            // }
        }
    },
    computed: {
        showVideosList() {
            switch (this.videoNum) {
                case 1:
                    return this.cameraList.slice(
                        this.curPage - 1,
                        this.curPage
                    );
                    break;
                case 4:
                    return this.cameraList.slice(
                        (this.curPage - 1) * 4,
                        this.curPage * 4
                    );
                    break;
                case 9:
                    return this.cameraList.slice(
                        (this.curPage - 1) * 9,
                        this.curPage * 9
                    );
                    break;
            }
        },
        totalPage() {
            return Math.ceil(this.cameraList.length / this.videoNum);
        }
    },
    mounted() {
        this.conditions.tunnelId = this.$route.params.id;
        this.initData();
    },
    methods: {
        initData() {
            let _this = this;
            TunnelService.getStoresByTunnelId(_this.conditions.tunnelId).then(
                result => {
                    _this.init.stores = result;
                },
                error => {
                    console.log(error);
                }
            );
            TunnelService.getAreasByTunnelId(_this.conditions.tunnelId).then(
                result => {
                    _this.init.areas = result;
                },
                error => {
                    console.log(error);
                }
            );
            VideoService.getCamerasByTunnelId(_this.conditions.tunnelId).then(
                result => {
                    _this.cameraList = [];
                    result.forEach(camera => {
                        let temp = {};
                        temp.id = camera.id;
                        temp.name = camera.name;
                        temp.url = camera.url;
                        temp.positionSupport = camera.ptzOperationsSupported;
                        temp.description = camera.description;
                        _this.cameraList.push(temp);
                    });
                    _this.Log.info("videos:", _this.cameraList);
                },
                error => {
                    console.log(error);
                }
            );
        },
        search() {
            let _this = this;
            VideoService.getCamerasByConditions(_this.conditions).then(
                result => {
                    _this.cameraList = [];
                    if (result.length == 0) {
                        _this.nodata = true;
                    } else {
                        _this.nodata = false;
                        result.forEach(camera => {
                            let temp = {};
                            temp.id = camera.id;
                            temp.name = camera.name;
                            temp.url = camera.url;
                            temp.positionSupport =
                                camera.ptzOperationsSupported;
                            temp.description = camera.description;
                            _this.cameraList.push(temp);
                        });
                    }
                },
                error => {
                    console.log(error);
                }
            );
        },
        history(index) {
            this.$router.push({
                name: "历史详情",
                params: {
                    tunnelId: this.conditions.tunnelId
                }
            });
        },
        handleScreensNum(num) {
            this.videoNum = num;
            // this.videoStyle.show = false
            this.curPage = 1;
            
            if(num == 1){
                this.curVideo = null
                this.curVideo = this.showVideosList[0]
            } 
        },
        selectScene(camera) {
            if(this.curVideo && this.curVideo.id == camera.id){
                this.curVideo = null
                this.isDisabled = true
            }else{
                this.curVideo = null
                this.curVideo = camera
                let _this = this
                VideoService.getPresetsByCameraId(this.curVideo.id).then(
                    result=>{
                        _this.perPositions = []
                        _this.perPositions = result
                    },
                    error=>{
                        _this.Log.info(error)
                    })
                if (camera.positionSupport) {
                    this.isDisabled = false
                } else {
                    this.isDisabled = true
                }
            }    
        },
        config(camera) {
            this.$router.push({
                name: "预置位",
                params: {
                    tunnelId: this.conditions.tunnelId,
                    camera: camera
                }
            });
        },
        start(data) {
            if (this.curVideo.id) {
                let _this = this;
                VideoService.cameraMove(
                    _this.curVideo.id,
                    data.direction
                ).then(
                    result => {
                        console.log("move success");
                    },
                    error => {
                        console.log(error);
                    }
                );
            } else {
                this.$Message.error({
                    content: "请选择摄像头",
                    duration: 5
                });
            }
        },
        stop(data) {
            if (this.curVideo.id) {
                let _this = this;
                VideoService.cameraStop(_this.curVideo.id).then(
                    result => {
                        console.log("stop success");
                    },
                    error => {
                        console.log(error);
                    }
                );
            }
        },
        pageChange(type){
            let changed = false
            switch(type){
                case 'prev':
                    if(this.curPage > 1){
                        this.curPage -= 1
                        changed = true
                    }
                    break
                case 'next':
                    if(this.curPage < this.totalPage){
                        this.curPage += 1
                        changed = true
                    }
                    break
            }
            if(changed && this.videoNum == 1){
                this.curVideo = this.showVideosList[0]
            }
        },
        set(name) {
          let _this = this
          VideoService.goToPreset(_this.curVideo.id,name).then(
              result=>{
                  console.log('setted')
              },
              error=>{
                _this.Log.info(error)
              })
        }
    }
};
</script>
<style scoped>
.content {
    padding: 20px;
    margin-top: 10px;
    background-color: white;
}
.control {
    width: 100%;
    height: 28vh;
}
.title {
    padding: 10px;
    background: url('../../../../assets/UM/title4.png') no-repeat;
    background-size: 100% 100%;
    /*color: #fff;*/
    text-align: center;
}
.small {
    margin-top: 10px;
}
.lists {
    width: 100%;
    /*border: 1px solid #b3b0b0;*/
    height: 48vh;
    position: relative;
}
.videoName {
    padding: 20px 10px 0 18px;
}
.lists p {
    margin: 10px 0 10px 18px;
    font-size: 16px;
}
.scenesList {
    width: 100%;
    border-radius: 40px 10px 40px 40px;
    height: 76vh;
    padding: 10px;
    overflow-y: auto;
    margin-left: 10px;
    background: url('../../../../assets/UM/videosBg.png') no-repeat;
    background-size: 100% 100%;
}
.monitors {
    padding: 14px 6px 6px 6px;
    margin-top: 10px;
}
.monitor {
    width: 90%;
    margin-left: 5%;
    height: 28vh;
    cursor: pointer;
}
.oneScreen {
    height: 62vh;
    margin-top: 10px;
}
.options {
    margin-left: 5%;
    margin-top: 4px;
}
.active {
    background-color: rgba(25, 190, 107, 0.4);
}
.query {
    background-color: white;
    padding: 10px;
}
.more {
    text-align: right;
    height: 20px;
   /* width: 100%;*/
    position: relative;
    margin-right: 24px;
}
.screens {
    padding: 0 10px;
    cursor: pointer;
}
.button {
    cursor: pointer;
}
.config {
    margin: 0 10px;
    background: url('../../../../assets/UM/button2.png');
    background-size: 100% 100%;
    display: inline-block;
    padding: 10px 20px;
    color: rgb(130,199,255);
    cursor: pointer;
    font-size: 14px;
}
.history{
    background: url('../../../../assets/UM/button2.png');
    background-size: 100% 100%;
    display: inline-block;
    padding: 10px 20px;
    color: rgb(130,199,255);
    cursor: pointer;
    font-size: 14px;
}
.slipContent {
    position: relative;
    width: 4%;
    height: 66vh;
}
.slipLeft {
    position: absolute;
    top: 46%;
    left: 10px;
    cursor: pointer;
}
.slipRight {
    position: absolute;
    top: 46%;
    right: 10px;
    cursor: pointer;
}
.enabled {
    color: lightgray;
}
.controlContent{
    padding-top: 16px;
    margin: 0 auto;
    height: 18vh;
    width: 78%;
}
.positions{
    background-color: rgb(53,122,163);
    padding: 10px;
    color: #fff;
    margin: 10px 0 10px 6%;
    position: relative;
    border-radius: 6px;
    line-height: 26px;
    font-size: 14px;
    width: 88%;
}
.posContent{
    max-height: 34vh;
    overflow-y: auto;
}
.option{
    float: right;
}
.listTitle{
    color: #fff;
    font-weight: bold;
    font-size: 16px;
    position: absolute;
    top: 8px;
    left: 5%;
}
.controlBody{
    background: url('../../../../assets/UM/videoListBody.png') no-repeat;
    background-size: 100% 100%
}
</style>
