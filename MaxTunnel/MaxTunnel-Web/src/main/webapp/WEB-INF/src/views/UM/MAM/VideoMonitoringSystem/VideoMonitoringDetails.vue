<template>
    <div>
        <Row class="query">
            <Col span="9" offset="1">
                监测仓:
                <Select v-model="conditions.storeId" style="width:60%;z-index: 9999;" id="store" @on-open-change="heightChange('store')">
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in init.stores" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="9">
                区域:
                <Select v-model="conditions.areaId" style="width:60%;z-index: 9999;" id="area" @on-open-change="heightChange('area')">
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
                    <h2>云台控制</h2>
                    <Row>
                        <Col span="20">
                            <video-controls @startDirectCtrl="start" @stopDirectCtrl="stop" v-bind:isDisabled="isDisabled"></video-controls>
                        </Col>
                    </Row>
                    <div class="indent">
                    </div>
                </div>
                <div class="lists">
                    <div v-if="this.curInfo.curScene">
                        <h2>{{ this.showScenesList[this.curInfo.curIndex].name }}</h2>
                        <ul>
                            <li>{{ this.showScenesList[this.curInfo.curIndex].description }}</li>
                        </ul>
                    </div>
                </div>
            </Col>
            <Col span="19">
                <div class="scenesList">
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
                    <Row>
                        <Col span="1" class="slipContent">
                            <Icon type="chevron-left" size="30" :class="['slipLeft',{'enabled': curPage == 1}]" @click.native="prev"></Icon>
                        </Col>
                        <Col span="22">
                            <h1 v-if="nodata" style="text-align: center;">暂无数据</h1>
                            <Row>
                                <Col :span="screensNum == 4 ? 12 : (screensNum == 1 ? 24 : 8)" v-for="(item,index) in showScenesList" :key="item.id" :class="['monitors',{'active':item.id == curInfo.curScene}]">
                                    <div @click="selectScene(item.id,index)">
                                        <div :class="['monitor',{'oneScreen':screensNum == 1}]" v-if="videoStyle.show">    
                                            <video-component v-bind:video="item" v-bind:id="'camera'+item.id"></video-component>
                                        </div>
                                        <div class="options">
                                            <Button type="primary" @click="history(index)">历史记录</Button>
                                            <Button type="primary" @click="config(item)" class="config">相机设置</Button>
                                        </div>
                                    </div>
                                </Col>
                            </Row>
                        </Col>
                        <Col span="1"  class="slipContent">
                           <Icon type="chevron-right" size="30"  :class="['slipRight',{'enabled' : curPage == totalPage}]" @click.native="next"></Icon>
                        </Col>
                    </Row>
                </div>
            </Col>
        </Row>
    </div>
</template>
<script>
import { TunnelService } from "../../../../services/tunnels";
import { VideoService } from "../../../../services/videos";
import VideoControls from "../../../../components/UM/MAM/VideoControls";
import VideoComponent from "../../../../components/Common/Video/VideoComponent";

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
            scenesList: [
                {
                    id: 1,
                    name: "摄像头1",
                    url:
                        "rtsp://admin:123456@192.168.3.202:554/h264/ch1/main/av_stream",
                    positionSupport: true,
                    description: "A Camera for tunnel"
                }
            ],
            curInfo: {
                curScene: null,
                curIndex: null
            },
            videoStyle: {
                show: true
            },
            curPage: 1,
            nodata: false,
            isDisabled: true,
            screensNum: 4
        };
    },
    components: { VideoControls, VideoComponent },
    watch: {
        $route: function() {
            this.conditions.tunnelId = this.$route.params.id;
            this.initData();
            this.screensNum = 4;
            this.curInfo.curScene = null;
            this.curInfo.curIndex = null;
            this.search();
            this.isDisabled = true;
            if ($("#iframearea").length > 0) {
                let ifr = document.getElementById("iframearea");
                ifr.parentNode.removeChild(ifr);
            }
        }
    },
    computed: {
        showScenesList() {
            switch (this.screensNum) {
                case 1:
                    return this.scenesList.slice(
                        this.curPage - 1,
                        this.curPage
                    );
                    break;
                case 4:
                    return this.scenesList.slice(
                        (this.curPage - 1) * 4,
                        this.curPage * 4
                    );
                    break;
                case 9:
                    return this.scenesList.slice(
                        (this.curPage - 1) * 9,
                        this.curPage * 9
                    );
                    break;
            }
        },
        totalPage() {
            return Math.ceil(this.scenesList.length / this.screensNum);
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
                    _this.scenesList = [];
                    result.forEach(camera => {
                        let temp = {};
                        temp.id = camera.id;
                        temp.name = camera.name;
                        temp.url = camera.url;
                        temp.positionSupport = camera.ptzOperationsSupported;
                        temp.description = camera.description;
                        _this.scenesList.push(temp);
                    });
                    _this.Log.info("videos:", _this.scenesList);
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
                    _this.scenesList = [];
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
                            _this.scenesList.push(temp);
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
            this.screensNum = num;
            // this.videoStyle.show = false
            this.curPage = 1;
        },
        selectScene(id, index) {
            this.curInfo.curScene = id;
            this.curInfo.curIndex = index;
            if (this.showScenesList[index].positionSupport) {
                this.isDisabled = false;
            } else {
                this.isDisabled = true;
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
            if (this.curInfo.curScene) {
                let _this = this;
                VideoService.cameraMove(
                    _this.curInfo.curScene,
                    data.direction
                ).then(
                    result => {
                        console.log("move success");
                    },
                    error => {
                        console.log(error);
                    }
                );
                // this.axios.get('videos/'+ this.curInfo.curScene +'/move/'+ data.direction).then(res=>{
                //     console.log("move success"+res);
                // })
            } else {
                this.$Message.error({
                    content: "请选择摄像头",
                    duration: 5
                });
            }
        },
        stop(data) {
            if (this.curInfo.curScene) {
                let _this = this;
                VideoService.cameraStop(_this.curInfo.curScene).then(
                    result => {
                        console.log("stop success");
                    },
                    error => {
                        console.log(error);
                    }
                );
                // this.axios.get('videos/'+ this.curInfo.curScene +'/stop').then(res=>{
                //     console.log("stop success");
                // })
            }
        },
        prev() {
            if (this.curPage > 1) {
                this.curPage -= 1;
            }
        },
        next() {
            if (this.curPage < this.totalPage) {
                this.curPage += 1;
            }
        },
        heightChange(id) {
            let length = null;
            switch (id) {
                case "store":
                    length = this.init.stores.length;
                    break;
                case "area":
                    length = this.init.areas.length;
                    break;
            }
            let height = (length + 1) * 32.5;
            if ($("#iframe" + id).length == 0) {
                $("#" + id + " .ivu-select-dropdown-list").append(
                    '<iframe src="about:blank" frameborder="0" marginheight="0" marginwidth="0" style="position:absolute;visibility:inherit;top:0;left:0;width:100%;height:' +
                        height +
                        'px;z-Index:-1;filter:alpha(opacity=0)" id="iframe' +
                        id +
                        '"/>'
                );
            }
        }
    }
};
</script>
<style scoped>
.modal {
    display: flex;
    align-items: center;
    justify-content: center;
}
.modal .ivu-modal {
    top: 0;
}
.content {
    padding: 20px;
    margin-top: 10px;
    background-color: white;
}
.control {
    border: 1px solid #b3b0b0;
    width: 100%;
    height: 26vh;
}
.control h2 {
    padding: 10px;
}
.indent {
    float: right;
    margin-right: 20px;
    margin-top: 4vh;
}
.small {
    margin-top: 10px;
}
.lists {
    width: 100%;
    border: 1px solid #b3b0b0;
    height: 46vh;
    position: relative;
}
.lists h2 {
    padding: 10px;
}
.lists ul {
    list-style-type: none;
}
.scenesList {
    width: 100%;
    border: 1px solid #b3b0b0;
    height: 72vh;
    padding: 10px;
    overflow-y: auto;
}
.monitors {
    padding: 6px;
}
.monitor {
    width: 90%;
    margin-left: 5%;
    height: 28vh;
    cursor: pointer;
}
.oneScreen {
    height: 62vh;
}
.options {
    margin-left: 5%;
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
    width: 100%;
    /* margin-right: 10px;*/
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
</style>
