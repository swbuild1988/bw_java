<template>
    <div class="whole">
        <Row class="query">
            <Col span="10" offset="2">
                <span style="font-size: 1.66vmin;">区域:</span>
                <Select
                    v-model="conditions.areaId"
                    style="width:60%;"
                    id="area"
                    @on-change="search"
                >
                    <Option value="null" key="0">所有</Option>
                    <Option
                        v-for="item in init.areas"
                        :value="item.id"
                        :key="item.id"
                    >{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="10" offset="2">
                <span style="font-size: 1.66vmin;">监测仓:</span>
                <Select
                    v-model="conditions.storeId"
                    style="width:60%;"
                    id="store"
                    @on-change="search"
                >
                    <Option value="null" key="0">所有</Option>
                    <Option
                        v-for="item in init.stores"
                        :value="item.id"
                        :key="item.id"
                    >{{ item.name }}</Option>
                </Select>
            </Col>
        </Row>
        <Row class="content">
            <Col span="5">
                <div class="control">
                    <h2 class="title">云台控制</h2>
                    <Row class="controlBody">
                        <Col span="24">
                            <div class="controlContent">
                                <video-control
                                    @startDirectCtrl="start"
                                    @stopDirectCtrl="stop"
                                    v-bind:isDisabled="isDisabled"
                                ></video-control>
                            </div>
                        </Col>
                    </Row>
                </div>
                <div class="lists">
                    <h2 class="title">摄像头信息</h2>
                    <div style="height: 90%;" class="cameraDetail">
                        <h2 class="videoName" v-if="!curVideo">请选择摄像机</h2>
                        <h2
                            v-if="curVideo"
                            class="videoName"
                        >{{ curVideo.name + ' ' + curVideo.id }}</h2>
                        <p
                            v-if="curVideo"
                        >{{ curVideo.tunnelName + ' ' + curVideo.areaName + ' ' + curVideo.storeName }}</p>
                        <p v-if="curVideo">{{ curVideo.description }}</p>
                        <div class="posContent" v-if="curVideo">
                            <div class="positions" v-for="(pos,index) in perPositions" :key="index">
                                <span class="name">{{ pos }}</span>
                                <Button
                                    type="primary"
                                    size="small"
                                    @click="set(pos)"
                                    class="option"
                                >设置</Button>
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
                            <Icon type="navicon-round" class="button" color="#fff"></Icon>
                            <div class="api" slot="content">
                                <Icon
                                    class="screens"
                                    type="android-checkbox-outline-blank"
                                    @click.native="handleScreensNum(1)"
                                ></Icon>
                                <Icon
                                    class="screens"
                                    type="social-windows"
                                    @click.native="handleScreensNum(4)"
                                ></Icon>
                                <Icon
                                    class="screens"
                                    type="android-apps"
                                    @click.native="handleScreensNum(9)"
                                ></Icon>
                            </div>
                        </Poptip>
                    </div>
                    <Row style="width: 94%;margin-left: 3%;">
                        <Col span="1" class="slipContent">
                            <Icon
                                type="chevron-left"
                                :class="['slipLeft',{'disabled': curPage == 1},{'clicked' : clicked.prev && curPage != 1}]"
                                @click.native="pageChange('prev')"
                                @mousedown.native="down('prev')"
                                @mouseup.native="up('prev')"
                            ></Icon>
                        </Col>
                        <Col span="22">
                            <div class="videos">
                                <h1 v-if="nodata" style="text-align: center;">暂无数据</h1>
                                <Row>
                                    <Col
                                        :span="videoNum == 4 ? 12 : (videoNum == 1 ? 24 : 8)"
                                        v-for="(item,index) in tempVideoList"
                                        :key="item.id"
                                        :class="['monitors',{'active': curVideo && item.id == curVideo.id},{'oneSBody': videoNum == 1},{'nineSBody': videoNum == 9}]"
                                    >
                                        <div @click="selectScene(item)">
                                            <div
                                                :class="['monitor',{'oneScreen':videoNum == 1},{'nineScreen': videoNum == 9}]"
                                            >
                                                <video-component
                                                    :index="index"
                                                    :video="item"
                                                    :id="'camera'+item.id"
                                                ></video-component>
                                            </div>
                                            <div class="options">
                                                <div
                                                    type="primary"
                                                    @click="history(item)"
                                                    class="history"
                                                >历史记录</div>
                                                <div
                                                    type="primary"
                                                    @click="config(item)"
                                                    class="config"
                                                >相机设置</div>
                                            </div>
                                        </div>
                                    </Col>
                                </Row>
                            </div>
                        </Col>
                        <Col span="1" class="slipContent">
                            <Icon
                                type="chevron-right"
                                :class="['slipRight',{'disabled' : curPage >= totalPage},{'clicked' : clicked.next && curPage != totalPage}]"
                                @click.native="pageChange('next')"
                                @mousedown.native="down('next')"
                                @mouseup.native="up('next')"
                            ></Icon>
                        </Col>
                    </Row>
                </div>
            </Col>
        </Row>
    </div>
</template>
<script>
import { TunnelService } from "../../../../services/tunnelService";
import { VideoService } from "../../../../services/videoService";
import VideoControl from "../../../../components/UM/MAM/videoControls/VideoControl";
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
            cameraList: [],
            // curVideo: {
            //     id: null
            // },
            curVideo: null,
            curPage: 1,
            nodata: false,
            isDisabled: true,
            videoNum: 4,
            perPositions: [],
            clicked: {
                prev: false,
                next: false
            },
            tempVideoList: []
        };
    },
    components: { VideoControl, VideoComponent },
    watch: {
        $route: function() {
            this.conditions.tunnelId = this.$route.params.id;
            this.clearStateParams();
            this.initData();
            this.videoNum = 4;
            this.curVideo = null;
            this.isDisabled = true;
            this.conditions.storeId = null;
            this.conditions.areaId = null;
        },
        showVideosList() {
            this.tempVideoList = [];

            this.$nextTick(function() {
                this.tempVideoList = this.showVideosList;
            });
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
                case 4:
                    return this.cameraList.slice(
                        (this.curPage - 1) * 4,
                        this.curPage * 4
                    );
                case 9:
                    return this.cameraList.slice(
                        (this.curPage - 1) * 9,
                        this.curPage * 9
                    );
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
            Promise.all([
                TunnelService.getAreasByTunnelId(this.conditions.tunnelId),
                TunnelService.getStoresByTunnelId(this.conditions.tunnelId)
            ]).then(
                res => {
                    this.init.areas = res[0];
                    this.init.stores = res[1];
                    this.getStateParams();
                    this.search();
                },
                err => {
                    this.Log.info(err);
                }
            );
        },
        getStateParams() {
            if (this.$store.state.UMstate.videoDetailParams) {
                let setting = this.$store.state.UMstate.videoDetailParams;
                this.conditions.storeId = setting.storeId;
                this.conditions.areaId = setting.areaId;
                this.videoNum = setting.videoNum;
            }
        },
        clearStateParams() {
            let data = {
                storeId: null,
                areaId: null,
                videoNum: 4
            };
            this.$store.commit("setVideoDetailParams", data);
        },
        setStateParams() {
            let data = {
                storeId: this.conditions.storeId,
                areaId: this.conditions.areaId,
                videoNum: this.videoNum
            };
            this.$store.commit("setVideoDetailParams", data);
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
                        _this.curPage = 1;
                        _this.curVideo = null;
                        result.forEach(camera => {
                            _this.cameraList.push({
                                id: camera.id,
                                name: camera.name,
                                url: camera.url,
                                tunnelId: _this.conditions.tunnelId,
                                storeId: camera.storeId,
                                areaId: camera.areaId,
                                positionSupport: camera.ptzOperationsSupported,
                                description: camera.description,
                                tunnelName: camera.tunnelName
                                    ? camera.tunnelName
                                    : "",
                                storeName: camera.storeName
                                    ? camera.storeName
                                    : "",
                                areaName: camera.areaName ? camera.areaName : ""
                            });
                        });
                    }
                },
                error => {
                    console.log(error);
                }
            );
        },
        history(camera) {
            this.setStateParams();
            this.$router.push({
                name: "历史照片",
                params: {
                    camera: camera
                }
            });
        },
        handleScreensNum(num) {
            this.videoNum = num;
            this.curPage = 1;

            if (num == 1) {
                this.curVideo = null;
                this.curVideo = this.showVideosList[0];
            }
        },
        selectScene(camera) {
            if (this.curVideo && this.curVideo.id == camera.id) {
                this.curVideo = null;
                this.isDisabled = true;
            } else {
                this.curVideo = null;
                this.curVideo = camera;
                let _this = this;
                VideoService.getPresetsByCameraId(this.curVideo.id).then(
                    result => {
                        _this.perPositions = [];
                        _this.perPositions = result;
                    },
                    error => {
                        _this.Log.info(error);
                    }
                );
                if (camera.positionSupport) {
                    this.isDisabled = false;
                } else {
                    this.isDisabled = true;
                }
            }
        },
        config(camera) {
            this.setStateParams();
            this.$router.push({
                name: "预置位",
                params: {
                    camera: camera
                }
            });
        },
        start(data) {
            if (this.curVideo.id) {
                let _this = this;
                VideoService.cameraMove(_this.curVideo.id, data.direction).then(
                    result => {
                        _this.Log.info("move success");
                    },
                    error => {
                        _this.Log.info(error);
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
                        _this.Log.info("stop success");
                    },
                    error => {
                        _this.Log.info(error);
                    }
                );
            }
        },
        pageChange(type) {
            let changed = false;
            switch (type) {
                case "prev":
                    if (this.curPage > 1) {
                        this.curPage -= 1;
                        changed = true;
                    }
                    break;
                case "next":
                    if (this.curPage < this.totalPage) {
                        this.curPage += 1;
                        changed = true;
                    }
                    break;
            }
            if (changed && this.videoNum == 1) {
                this.curVideo = this.showVideosList[0];
            }
        },
        set(name) {
            let _this = this;
            VideoService.goToPreset(_this.curVideo.id, name).then(
                result => {
                    _this.Log.info("setted");
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        down(type) {
            this.clicked[type] = true;
        },
        up(type) {
            this.clicked[type] = false;
        }
    }
};
</script>
<style scoped>
.whole {
    background-size: 100% 100%;
    min-height: 100%;
    position: relative;
}
.content {
    padding: 2vmin 2vmin 0 2vmin;
    /* margin-top: 10px;*/
    /*background-color: white;*/
}
.control {
    width: 100%;
    height: 28vh;
}
.title {
    padding: 1vmin;
    background: url("../../../../assets/UM/title4.png") no-repeat;
    background-size: 100% 100%;
    color: #fff;
    text-align: center;
    font-size: 1.66vmin;
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
    padding: 6% 1.2vmin 0 10%;
    font-size: 1.66vmin;
}
.lists p {
    margin: 1vmin 0 1vmin 10%;
    font-size: 1.66vmin;
}
.scenesList {
    width: 100%;
    border-radius: 40px 10px 40px 40px;
    height: 76vh;
    padding: 1vmin;
    overflow-y: auto;
    margin-left: 1vmin;
    background: url("../../../../assets/UM/videosBg.png") no-repeat;
    background-size: 100% 100%;
}
.monitors {
    padding: 1.4vmin 0.6vmin 0.6vmin 0.6vmin;
    /* margin-top: 10px;*/
    height: 34vh;
}
.monitor {
    width: 90%;
    margin-left: 5%;
    height: 28vh;
    cursor: pointer;
}
.oneSBody {
    height: 69vh;
}
.nineSBody {
    height: 23vh;
}
.oneScreen {
    height: 62vh;
    margin-top: 1vmin;
}
.nineScreen {
    height: 16vh;
}
.options {
    margin-left: 5%;
    margin-top: 0.4vmin;
}
.active {
    background-color: rgba(25, 190, 107, 0.4);
}
.query {
    padding: 2vh 0;
    color: #fff;
}
.more {
    text-align: right;
    height: 20px;
    position: relative;
    margin-right: 3%;
    margin-top: 0.2%;
}
.screens {
    padding: 0 10px;
    cursor: pointer;
    font-size: 2vmin;
}
.button {
    cursor: pointer;
    font-size: 2vmin;
}
.config {
    margin: 0 10px;
    background: url("../../../../assets/UM/button2.png");
    background-size: 100% 100%;
    display: inline-block;
    padding: 10px 20px;
    color: #fff;
    cursor: pointer;
    font-size: 1.28vmin;
}
.history {
    background: url("../../../../assets/UM/button2.png");
    background-size: 100% 100%;
    display: inline-block;
    padding: 10px 20px;
    color: #fff;
    cursor: pointer;
    font-size: 1.28vmin;
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
    color: lightgray;
    font-size: 2.88vmin;
}
.slipRight {
    position: absolute;
    top: 46%;
    right: 10px;
    cursor: pointer;
    color: lightgray;
    font-size: 2.88vmin;
}
.disabled {
    display: none;
}
.clicked {
    color: rgb(0, 228, 236);
}
.controlContent {
    padding-top: 1.6vmin;
    margin: 0 auto;
    height: 18vh;
    width: 78%;
}
.positions {
    background-color: rgb(53, 122, 163);
    padding: 10px;
    color: #fff;
    margin: 1vmin;
    position: relative;
    border-radius: 6px;
    line-height: 2.6vmin;
    font-size: 1.4vmin;
    width: 88%;
    min-height: 4vmin;
}
.posContent {
    max-height: 31vh;
    overflow-y: auto;
    width: 90%;
    margin: 0 auto;
}
.posContent::-webkit-scrollbar {
    width: 1vmin;
    height: 0.2vmin;
}
.posContent::-webkit-scrollbar-thumb {
    border-radius: 1vmin;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #83a6ed;
}
.posContent::-webkit-scrollbar-track {
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    border-radius: 1vmin;
    background: #ededed;
}
.option {
    float: right;
}
.listTitle {
    color: #fff;
    font-weight: bold;
    font-size: 1.66vmin;
    position: absolute;
    top: 4px;
    left: 5%;
}
.controlBody {
    background: url("../../../../assets/UM/videoListBody.png") no-repeat;
    background-size: 100% 100%;
    color: #fff;
    margin-top: 1vmin;
    height: 20vh;
}
.cameraDetail {
    background: url("../../../../assets/UM/videoDetailBg.png") no-repeat;
    background-size: 100% 100%;
    color: #fff;
    margin-top: 1vmin;
}

.query >>> .ivu-select-selection {
    height: 3.2vmin;
    border-radius: 1vmin;
    background: transparent;
}

.videos {
    margin-top: 2vh !important;
}
.query >>> .ivu-select-placeholder,
.query >>> .ivu-select-selected-value {
    font-size: 1.28vmin !important;
    height: 2.6vmin !important;
    line-height: 3.2vmin !important;
    color: #fff;
}
.query >>> .ivu-select-dropdown {
    max-height: 20vmin !important;
}
</style>
