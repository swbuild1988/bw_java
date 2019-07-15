<template>
    <div>
        <Row class="body">
            <Col span="12">
                <div class="sm">
                    <div class="heading">
                        <span>{{ direction }}</span>
                    </div>
                    <vm-select
                        id="list-dropdown"
                        :optionList="optionList"
                        :selectStyle="{ left:'1vmin',top: '1vmin'}"
                        @getSelectVal="getAreas"
                    ></vm-select>
                    <vm-select
                        id="area-dropdown"
                        :optionList="areaList"
                        :selectStyle="{ left:'14.2%',top:'1vmin' }"
                        @getSelectVal="getStores"
                    ></vm-select>
                    <vm-select
                        id="store-dropdown"
                        :optionList="storeList"
                        :selectStyle="{ left:'27.4%',top: '1vmin' }"
                        @getSelectVal="changeStore"
                    ></vm-select>
                    <sm-viewer @refreshCameraPosition="refreshCameraPosition" ref="smViewer"></sm-viewer>
                </div>
                <div class="cameraContent">
                    <video-component :video="curVideo" :id="'virtualInspectEdit'+curVideo.id"></video-component>
                </div>
            </Col>
            <Col span="12">
                <Row class="control">
                    <Col span="8">
                        <div class="cameraList">
                            <div class="titles">摄像头列表</div>
                            <br>
                            <ul>
                                <li
                                    v-for="camera in cameraList"
                                    :key="camera.id"
                                    @click="selectCamera(camera)"
                                    :class="{'active' : curVideo.id == camera.id}"
                                >{{ camera.name + ' ' + camera.id }}</li>
                            </ul>
                        </div>
                    </Col>
                    <Col span="16">
                        <div class="controlContent">
                            <div class="titles">云台控制</div>
                            <br>
                            <div class="controlWrapper">
                                <VideoControl
                                    @startDirectCtrl="start"
                                    @stopDirectCtrl="stop"
                                    v-bind:isDisabled="!isSelected"
                                ></VideoControl>
                            </div>
                        </div>
                        <Button type="ghost" @click="back" class="back">返回</Button>
                    </Col>
                    <Col span="24">
                        <div class="posBox">
                            <div class="titles">预置位</div>
                            <br>
                            <div class="posContent">
                                <Button type="primary" @click="add" class="add">添加</Button>
                                <div
                                    class="positions"
                                    v-for="(pos,index) in perPositions"
                                    :key="index"
                                >
                                    <span class="name">{{ pos }}</span>
                                    <div class="options">
                                        <Button
                                            type="primary"
                                            size="small"
                                            @click="set(pos)"
                                            class="set"
                                        >到达预置位</Button>
                                        <Button
                                            type="primary"
                                            size="small"
                                            @click="match(pos)"
                                            class="match"
                                        >匹配</Button>
                                        <Button
                                            type="primary"
                                            size="small"
                                            class="edit"
                                            @click="edit(pos)"
                                        >编辑</Button>
                                        <Button
                                            type="error"
                                            size="small"
                                            class="del"
                                            @click="del(pos)"
                                        >删除</Button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Col>
                </Row>
            </Col>
        </Row>
        <Modal v-model="isAdd" title="添加预置位" @on-ok="addConfirm" @on-cancel="cancel">
            <Input type="text" v-model="name" class="text" v-if="isAdd" placeholder="请填写预置位名称"/>
        </Modal>
    </div>
</template>

<script>
import VideoControl from "../../../../components/UM/MAM/videoControls/VideoControl";
import SmViewer from "../../../../components/Common/3D/simple3DViewer";
import VideoComponent from "../../../../components/Common/Video/VideoComponent";
import { VideoService } from "../../../../services/videoService";
import vmSelect from "../../../../components/Common/Select";
import { TunnelService } from "../../../../services/tunnelService";
import Vue from "vue";
import { flyToMyLocation } from "../../../../scripts/commonFun";

export default {
    name: "VirtualInspectEdit",
    data() {
        return {
            cameraList: [
                // {
                //     id: 7005,
                //     name: "红外摄像头1",
                //     url: "192.168.6.156:8078",
                //     positionSupport: true,
                //     description: "A Camera for tunnel"
                // },
                // {
                //     id: 7002,
                //     name: "红外摄像头2",
                //     url: "192.168.6.156:8078",
                //     positionSupport: true,
                //     description: "A Camera for tunnel"
                // }
            ],
            curId: null,
            curSection: {
                id: 0
            },
            isSelected: false,
            perPositions: [],
            isAdd: false,
            name: null,
            isShow: false,
            curVideo: {
                id: null,
                url: ""
            },
            cameraPosition: null,
            optionList: [],
            areaList: [],
            storeList: [],
            location: [],
            direction: null
        };
    },
    components: {
        VideoControl,
        SmViewer,
        VideoComponent,
        vmSelect
    },
    mounted() {
        this.Log.info("初始化调用刷新");
        this.init();
        this.$refs.smViewer.startCameraPositionRefresh();
    },
    beforeDestroy() {
        this.Log.info("停止刷新");
        this.$refs.smViewer.stopCameraPositionRefresh();
    },
    computed: {
        addParam() {
            return {
                presetName: this.name
            };
        }
    },
    methods: {
        init() {
            this.getTunnels().then(tunnel => {
                this.getAreas(tunnel.value).then(areas => {
                    let area = this.getValByArray(areas);

                    this.getStores(area.id).then(areaId =>
                        this.changeStore(areaId)
                    );
                });
            });
        },
        getTunnels() {
            let _this = this;

            return new Promise((resolve, reject) => {
                TunnelService.getTunnels().then(tunnels => {
                    _this.optionList.splice(0); //清空管廊数组
                    tunnels.forEach(tunnel =>
                        _this.optionList.push({
                            value: tunnel.id,
                            label: tunnel.name
                        })
                    );

                    let tunnel = this.getValByArray(_this.optionList);

                    resolve(tunnel);
                });
            });
        },
        getAreas(val) {
            let _this = this;

            return new Promise((resolve, reject) => {
                TunnelService.getAreasByTunnelId(val).then(data => {
                    _this.showArea = true;
                    _this.areaList.splice(0); //清空管廊数组

                    if (data.length) {
                        data.reverse().forEach(area => {
                            _this.areaList.push({
                                value: area.id,
                                label: area.name
                            });
                        });
                        resolve(data);
                    }
                });
            });
        },
        changeStore(storeId) {
            let _this = this;
            let [curCamera] = _this.location.filter(
                position => position.id == storeId
            );
            let { camera } = curCamera;
            // console.log('camera',camera)
            try {
                let [
                    longitude,
                    latitude,
                    height,
                    roll,
                    pitch,
                    heading
                ] = camera.split(",");

                flyToMyLocation({
                    scene: Vue.prototype["$simpleViewer"].scene,
                    position: {
                        longitude: longitude,
                        latitude: latitude,
                        height: height,
                        roll: roll,
                        pitch: pitch,
                        heading: heading
                    },
                    maximumHeight: 4
                });

                this.direction = this.getDirection(heading);
            } catch (e) {}
        },
        getDirection(heading) {
            let direction = Math.abs(heading % 6.28).toFixed(2);
            let value = null;
            switch (direction) {
                case 0:
                    value = "正北";
                    break;
                case 1.57:
                    value = heading > 0 ? "正东" : "正西";
                    break;
                case 3.14:
                    value = "正南";
                    break;
                case 4.71:
                    value = heading > 0 ? "正西" : "正东";
                    break;
                default:
                    value = null;
            }
            if (!value) {
                let angle = (direction * 180) / 3.14;
                if (direction > 0 && direction < 1.57) {
                    value =
                        heading > 0
                            ? "北偏东" + angle.toFix(2) + "度"
                            : "北偏西" + angle.toFixed(2) + "度";
                } else {
                    if (direction > 1.57 && direction < 3.14) {
                        value =
                            heading > 0
                                ? "东偏南" + (angle - 90).toFixed(2) + "度"
                                : "西偏南" + (angle - 90).toFixed(2) + "度";
                    } else {
                        if (direction > 3.14 && direction < 4.71) {
                            value =
                                heading > 0
                                    ? "南偏西" + (angle - 180).toFixed(2) + "度"
                                    : "南偏东" +
                                      (angle - 180).toFixed(2) +
                                      "度";
                        } else {
                            value =
                                heading > 0
                                    ? "西偏北" + (angle - 270).toFixed(2) + "度"
                                    : "东偏北" +
                                      (angle - 270).toFixed(2) +
                                      "度";
                        }
                    }
                }
            }

            return value;
        },
        getStores(areaId) {
            let _this = this;

            return new Promise((resolve, reject) => {
                TunnelService.getStoresByAreaId({ areaId }).then(stores => {
                    _this.storeList.splice(0);
                    _this.location.splice(0);

                    stores.forEach(store => {
                        if (!store.store || !store.startPoint) return;
                        _this.storeList.push({
                            value: store.store.id,
                            label: store.store.name
                        });
                        _this.location.push({
                            id: store.store.id,
                            camera: store.camera
                        });
                    });

                    let store = this.getValByArray(_this.storeList);

                    resolve(store.value);
                });
            });
        },
        getValByArray([firstVal, ...otherVal]) {
            return firstVal;
        },
        start(data) {
            let _this = this;
            VideoService.cameraMove(this.curVideo.id, data.direction).then(
                result => {
                    _this.Log.info("move success");
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        stop(data) {
            let _this = this;
            VideoService.cameraStop(this.curVideo.id).then(
                result => {
                    _this.Log.info("stop success");
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        selectCamera(camera) {
            this.Log.info("selected camera", camera);
            this.curVideo.id = camera.id;
            this.isSelected = true;
            this.curVideo.url = camera.url;
            this.getPositions();
        },
        getPositions() {
            let _this = this;
            VideoService.getPresetsByCameraId(this.curVideo.id).then(
                result => {
                    _this.perPositions = [];
                    _this.perPositions = result;
                    if (_this.perPositions.length == 0)
                        _this.perPositions = ["default"];
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        add() {
            this.isAdd = true;
        },
        addConfirm() {
            let addFlag = true;
            this.perPositions.forEach(pos => {
                if (pos.name == this.name) {
                    this.$Message.error({
                        content: "名称已被占用，请输入其他名称",
                        duration: 5
                    });
                    addFlag = false;
                    this.name = null;
                }
            });
            if (addFlag) {
                let _this = this;
                VideoService.addPreset(this.curVideo.id, this.addParam).then(
                    result => {
                        _this.isAdd = false;
                        _this.name = null;
                        _this.getPositions();
                    }
                );
            }
        },
        set(name) {
            let _this = this;
            VideoService.goToPreset(this.curVideo.id, name).then(
                result => {
                    _this.Log.info("setted" + result);
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        cancel() {
            this.isAdd = false;
            this.name = null;
        },
        del(name) {
            let param = {
                presetName: name
            };
            let _this = this;
            this.$Modal.confirm({
                title: "确认",
                content: "<p>确定删除" + name + "吗？</p>",
                onOk: () => {
                    VideoService.deletePreset(_this.curVideo.id).then(
                        result => {
                            _this.getPositions();
                        },
                        error => {
                            _this.Log.info(error);
                        }
                    );
                }
            });
        },
        edit(name) {
            let param = {
                presetName: name
            };
            let _this = this;
            this.$Modal.confirm({
                title: "确认",
                content: "<p>确定修改当前位置为" + name + "吗？</p>",
                styles: "top: '80px', left: '20%',width: '300px'",
                onOk: () => {
                    VideoService.editPreset(_this.curVideo.id, param).then(
                        result => {
                            _this.getPositions();
                        },
                        error => {
                            _this.Log.info(error);
                        }
                    );
                }
            });
        },
        // 3D相机位置刷新
        refreshCameraPosition(position) {
            this.cameraPosition = position;
            // 根据相机位置获得section信息
            let _this = this;
            VideoService.getSections(this.cameraPosition).then(
                result => {
                    _this.Log.info("get section ", result);
                    if (
                        result &&
                        _this.curSection.id != result.sectionInfo.id
                    ) {
                        // 如果section变了，查找section中的相机
                        _this.curSection = result.sectionInfo;
                        _this.getVideos(_this.curSection.id);
                    }
                },
                error => {
                    _this.Log.info(error);
                }
            );
            VideoService.getVICameras(this.cameraPosition).then(
                result => {
                    _this.Log.info("get camera", result);
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        getVideos(sectionId) {
            let _this = this;
            let param = {
                sectionId: sectionId
            };
            VideoService.getCamerasByConditions(param).then(
                result => {
                    _this.cameraList = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        match(pos) {
            if (this.cameraPosition == null) return;
            var videoPreset = {
                videoId: this.curVideo.id,
                presetName: pos,
                longitude: this.cameraPosition.longitude,
                latitude: this.cameraPosition.latitude,
                height: this.cameraPosition.height,
                pitch: this.cameraPosition.pitch,
                roll: this.cameraPosition.roll,
                heading: this.cameraPosition.heading
            };
            this.Log.info("matched", videoPreset);
            let _this = this;
            VideoService.matchPresetAnd3D(videoPreset).then(
                result => {
                    _this.$Message.success("匹配成功");
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        back() {
            this.$router.push("/UM/virtualInspect");
        }
    }
};
</script>
<style scoped>
.body {
    margin: 20px 10px;
    padding: 20px;
}
.view {
    height: 86vh;
    border: 1px solid #b3b0b0;
}
.control {
    height: 86vh;
}
.camera {
    height: 86vh;
    border: 1px solid #b3b0b0;
}
.cameraList {
    margin: 0 1vmin;
    padding: 1vmin;
    color: #0efcff;
}
.titles {
    background-image: url("../../../../assets/UM/title2.png");
    background-size: 100% 100%;
    font-size: 1.8vmin;
    font-weight: bold;
    padding: 0.8vmin 0.8vmin 0.8vmin 4vmin;
    color: #0efcff;
    width: 24vmin;
}
.cameraList ul {
    list-style-type: none;
    background-image: url("../../../../assets/UM/energyBorder2.png");
    background-size: 100% 100%;
    padding: 10px;
    overflow-y: auto;
    height: 21.2vmin;
}
.cameraList ul li {
    font-size: 1.66vmin;
    cursor: pointer;
    padding: 4px;
    text-align: center;
}
.cameraList ul li:hover {
    background-color: rgba(53, 122, 163);
    color: #fff;
    border-radius: 1vmin;
}
.active {
    background-color: rgba(25, 190, 107, 0.4);
    border-radius: 1vmin;
}
.controlContent {
    margin: 0 1vmin;
    padding: 1vmin;
    width: 100%;
    display: inline-block;
    height: 26vmin;
    position: relative;
}
.controlWrapper {
    background-image: url("../../../../assets/UM/energyBorder2.png");
    background-size: 100% 100%;
    width: 100%;
    height: 88%;
    padding-left: 20%;
}
.add {
    float: right;
    margin: 1vmin;
    background: -webkit-linear-gradient(left, #98bee6, #007bfc);
    background: -o-linear-gradient(right, #98bee6, #007bfc);
    background: -moz-linear-gradient(right, #98bee6, #007bfc);
    background: linear-gradient(to right, #98bee6, #007bfc);
    border-color: #33525a;
    border-radius: 1vmin;
    font-size: 1.4vmin;
}
.posContent {
    overflow-y: auto;
    width: 100%;
    padding: 10px;
    background: url("../../../../assets/UM/energyBorder2.png") no-repeat;
    background-size: 100% 100%;
    height: 49.2vmin;
}
.positions {
    background: #0e1c3b;
    padding: 1vmin 1vmin 1vmin 2vmin;
    color: #fff;
    margin: 1vmin;
    position: relative;
    border-radius: 6px;
    line-height: 2.6vmin;
    min-height: 4vmin;
    width: 84%;
    border: 1px solid #345391;
}
.name {
    font-size: 1.66vmin;
}
.options {
    float: right;
}
.btn {
    margin-top: 10px;
    float: right;
    margin-right: 10px;
}
.cancel {
    float: right;
    margin-top: 10px;
    margin-right: 10px;
}
.cameraContent {
    margin-top: 10px;
    width: 100%;
    background: url("../../../../assets/UM/energyBorder1.png") no-repeat;
    background-size: 100% 100%;
    height: 43vh;
    padding: 1vmin;
}
.text {
    width: 90%;
    margin-top: 10px;
    margin-left: 5%;
}
.sm {
    height: 42vh;
    background: url("../../../../assets/UM/energyBorder1.png") no-repeat;
    background-size: 100% 100%;
    position: relative;
    padding: 1vmin;
}
.back {
    color: #fff;
    background: -webkit-linear-gradient(left, #98bee6, #007bfc);
    background: -o-linear-gradient(right, #98bee6, #007bfc);
    background: -moz-linear-gradient(right, #98bee6, #007bfc);
    background: linear-gradient(to right, #98bee6, #007bfc);
    border-color: #33525a;
    border-radius: 1vmin;
    font-size: 1.4vmin;
    position: fixed;
    top: 10vmin;
    right: 2vmin;
}
.set {
    background-color: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #3e4f61;
    border-radius: 1vmin;
}
.match {
    background-color: -webkit-linear-gradient(left, #dcd77c, #cabf11);
    background: -o-linear-gradient(right, #dcd77c, #cabf11);
    background: -moz-linear-gradient(right, #dcd77c, #cabf11);
    background: linear-gradient(to right, #dcd77c, #cabf11);
    border-color: #3e4f61;
    border-radius: 1vmin;
}
.edit {
    background-color: -webkit-linear-gradient(left, #bcdca9, #6be421);
    background: -o-linear-gradient(right, #bcdca9, #6be421);
    background: -moz-linear-gradient(right, #bcdca9, #6be421);
    background: linear-gradient(to right, #bcdca9, #6be421);
    border-color: #3e4f61;
    border-radius: 1vmin;
}
.del {
    background-color: -webkit-linear-gradient(left, #e49b9b, #f61a1a);
    background: -o-linear-gradient(right, #e49b9b, #f61a1a);
    background: -moz-linear-gradient(right, #e49b9b, #f61a1a);
    background: linear-gradient(to right, #e49b9b, #f61a1a);
    border-color: #3e4f61;
    border-radius: 1vmin;
}
.posBox {
    margin: 1vmin 0 1vmin 1vmin;
    height: 58vh;
    position: relative;
    padding: 0 0 0 1vmin;
}
.heading {
    position: absolute;
    top: 1vmin;
    left: 63%;
    font-size: 1.4vmin;
    z-index: 10;
    border: 1px solid rgba(16, 159, 181, 0.5);
    box-shadow: 0 0 1rem #109fb5 inset;
    color: #fff;
    padding: 0.4vmin 2.4vmin;
    background: #88b0b6;
}
.vm-select >>> .cd-dropdown > span,
.vm-select >>> .cd-dropdown ul li{
    background: #88b0b6;
}

</style>
