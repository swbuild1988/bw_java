<template>
    <div class="container">
        <!--<Button @click="edit">编辑</Button>-->
        <!--<Tabs value="manual">-->
        <!--<TabPane label="手动巡检" name="manual">-->
        <div class="coolBox" style="height: 60px;padding: 12px;color: #fff;font-size: 16px;">
            <Row>
                <Col span="9"> 飞行路径：
                <Select v-model="conditions.routeIndex" style="width: 60%" @on-change="getStopsList"> 
                    <Option value="00">全部</Option> 
                    <Option v-for="(item,index) in list.routes" :key="index" :value="index">{{item.routeName}}</Option>
                </Select>
                </Col>
                <Col span="9"> 站点：
                <Select v-model="conditions.stopId" style="width: 60%">
                    <Option v-for="(item,index) in list.stops" :key="index" :value="item.id">{{item.stopName}}</Option>
                </Select>
                </Col>
                <Col span="6" style="text-align: right">
                <!--<i-Switch size="large">-->
                <!--<span slot="open">手动</span>-->
                <!--<span slot="close">自动</span>-->
                <!--</i-Switch>-->
                <!-- <Button type="primary" icon="ios-search">查询</Button> -->
                <Button type="primary" @click="isManual = false">手动</Button>
                <Button type="primary" @click="isManual = true">自动</Button>
                <Button type="primary" icon="compose" @click="edit">编辑</Button>
                </Col>
            </Row>
        </div>
        <Row :gutter="20" class="mainInfo">
            <Col span="12">
            <div class="manualShowInspect coolBox">
                <div class="options" v-if="isManual">
                    <Button type="primary" class="buttons" icon="ios-rewind" @click="speedDown"></Button>
                    <Button type="primary" class="buttons" :icon="playOrPause.isPlay ? 'pause' : 'play' " @click="play"></Button>
                    <Button type="primary" class="buttons" icon="stop" @click="stop"></Button>
                    <Button type="primary" class="buttons" icon="ios-fastforward" @click="speedUp"></Button>
                </div>
                <sm-viewer id="virtualSmViewer" @refreshCameraPosition="refreshCameraPosition" ref="smViewer"></sm-viewer>
            </div>
            </Col>
            <Col span="12">
            <div class="manualShowInspect coolBox" style="color: #fff">
                <video-component v-bind:video="curVideo" v-bind:id="'vitrulInspect'"></video-component>
            </div>
            </Col>
        </Row>
        <div v-if="bottomView.isShow">
            {{ bottomView.data }}
        </div>
        <Row>
        </Row>
    </div>
</template>

<script>
import SmViewer from "../../../../components/Common/3D/3DViewer";
import VideoComponent from "../../../../components/Common/Video/VideoComponent";
import { VideoService } from "../../../../services/VideoService";
import { TunnelService } from "../../../../services/tunnelService";

export default {
    data() {
        return {
            conditions: {
                routeIndex: '00',
                stopId: null
            },
            SmSetting: {
                flyManagerAttr: {
                    enable: true
                }
            },
            curVideo: {
                id: 0,
                url: "",
                // "rtsp://admin:123456@192.168.3.201:554/h264/ch1/main/av_stream",
                defaultAddress: null
            },
            curPerset: null,
            list: {
                routes: null,
                stops: [],
            },
            allStops: [],
            cameraPosition: null,
            playOrPause: {
                isPlay: false,
                text: "开始"
            },
            bottomView: {
                isShow: true,
                data: null
            },
            isManual: false
        };
    },
    mounted() {
        let data = this.$refs.smViewer.getRoutesAndStops()
        this.list.routes = data.routes
        this.allStops = data.allStops
        this.getStopsList()
        this.Log.info("初始化调用刷新");
        this.$refs.smViewer.startCameraPositionRefresh();
    },
    beforeDestroy() {
        this.Log.info("停止刷新");
        this.$refs.smViewer.stopCameraPositionRefresh();
    },
    components: {
        SmViewer,
        VideoComponent
    },
    methods: {
        getStopsList() {
            if(this.conditions.routeIndex == '00'){
                this.list.stops = this.allStops
            } else {
                this.list.stops = this.list.routes[this.conditions.routeIndex].stopCollection
            }
        },

        // 3D相机位置刷新
        refreshCameraPosition(position) {
            this.cameraPosition = position;
            this.Log.info("get position:", this.cameraPosition);
            let _this = this
            VideoService.getVICameras(this.cameraPosition).then(
                result=>{
                    this.Log.info("get camera", result.video);
                    this.Log.info("get preset", result.preset);
                    if (result.video) {
                        // 如果是不同相机，则预置位更新并走到对应预置位
                        if (this.curVideo.id != result.video.id) {
                            this.curVideo = result.video;
                            this.curPerset = result.preset;
                            if (this.curPerset != null) {
                                VideoService.goToPreset(_this.curVideo.id,_this.curPerset).then(
                                    result=>{
                                        _this.Log.info(
                                            "goto" + _this.curPerset
                                        );
                                    },
                                    error=>{
                                        _this.Log.info(error)
                                    })
                                // this.axios
                                //     .get(
                                //         "videos/" +
                                //             this.curVideo.id +
                                //             "/presets/" +
                                //             this.curPerset +
                                //             "/goto"
                                //     )
                                //     .then(res => {
                                //         this.Log.info(
                                //             "goto" + this.curPerset
                                //         );
                                //     });
                            }
                        } else {
                            // 如果是同一个相机，并且预置位变了
                            if (
                                this.curPerset != result.preset &&
                                result.preset != null
                            ) {
                                this.curPerset = result.preset;
                                VideoService.goToPreset(_this.curVideo.id,_this.curPerset).then(
                                    result=>{
                                        _this.Log.info(
                                            "goto" + _this.curPerset
                                        );
                                    },
                                    error=>{
                                        _this.Log.info(error)
                                    })
                                // this.axios
                                //     .get(
                                //         "videos/" +
                                //             this.curVideo.id +
                                //             "/presets/" +
                                //             this.curPerset +
                                //             "/goto"
                                //     )
                                //     .then(res => {
                                //         this.Log.info(
                                //             "goto" + this.curPerset
                                //         );
                                //     });
                            }
                        }
                    }
                })
            // this.axios
            //     .post("virual-inspection/video", this.cameraPosition)
            //     .then(res => {
            //         let { code, data } = res.data;
            //         if (code == 200) {
            //             this.Log.info("get camera", data.video);
            //             this.Log.info("get preset", data.preset);
            //             if (data.video) {
            //                 // 如果是不同相机，则预置位更新并走到对应预置位
            //                 if (this.curVideo.id != data.video.id) {
            //                     this.curVideo = data.video;
            //                     this.curPerset = data.preset;
            //                     if (this.curPerset != null) {
            //                         this.axios
            //                             .get(
            //                                 "videos/" +
            //                                     this.curVideo.id +
            //                                     "/presets/" +
            //                                     this.curPerset +
            //                                     "/goto"
            //                             )
            //                             .then(res => {
            //                                 this.Log.info(
            //                                     "goto" + this.curPerset
            //                                 );
            //                             });
            //                     }
            //                 } else {
            //                     // 如果是同一个相机，并且预置位变了
            //                     if (
            //                         this.curPerset != data.preset &&
            //                         data.preset != null
            //                     ) {
            //                         this.curPerset = data.preset;
            //                         this.axios
            //                             .get(
            //                                 "videos/" +
            //                                     this.curVideo.id +
            //                                     "/presets/" +
            //                                     this.curPerset +
            //                                     "/goto"
            //                             )
            //                             .then(res => {
            //                                 this.Log.info(
            //                                     "goto" + this.curPerset
            //                                 );
            //                             });
            //                     }
            //                 }
            //             }
            //         }
            //     });
        },

        edit() {
            this.$router.push("/UM/virtualInspectEdit");
        },

        play() {
            if (this.playOrPause.isPlay) {
                this.$refs.smViewer.pauseFly();
            } else {
                this.$refs.smViewer.playFly();
            }
            this.playOrPause.isPlay = !this.playOrPause.isPlay;
        },

        stop() {
            this.$refs.smViewer.stopFly();
            this.playOrPause.isPlay = false;
        },

        speedUp() {
            this.Log.info("加速");
            this.$refs.smViewer.speedUp();
        },

        speedDown() {
            this.Log.info("减速");
            this.$refs.smViewer.speedDown();
        }
    }
};
</script>
<style scoped>
.container {
    height: 93vh;
    padding: 10px;
    /*background: #fff;*/
    background: linear-gradient(
        to bottom,
        #16a8c9 0%,
        #ffffff 50%,
        #16a8c9 100%
    );
}

.mainInfo {
    margin-top: 20px;
}

.manualShowInspect {
    border: 1px solid #ccc;
    margin: 4px;
    height: 80vh;
}

.automaticBim,
.automaticShowInspect {
    border: 1px silver solid;
    height: 77vh;
}

.btn {
    background-color: rgb(255, 255, 180, 0.5);
    padding: 5px;
    text-align: right;
}

.bim {
    margin: 5px;
}

.coolBox {
    border: 1px solid rgba(0, 0, 0, 0.2);
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    background-clip: padding-box;
    background: rgba(36, 40, 42, 0.5);
    box-shadow: 0 0 13px 3px rgba(0, 0, 0, 0.5);
    /*overflow: hidden;*/
}
.options {
    z-index: 10;
    position: absolute;
    top: 0;
    left: 32%;
}
.buttons {
    margin: auto 10px;
}
</style>
