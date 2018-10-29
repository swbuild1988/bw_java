<template>
    <div>
        <Row class="body">
            <Col span="14">
            <div class="sm">
                <sm-viewer id="virtualInspectEditSmViewer" @refreshCameraPosition="refreshCameraPosition" ref="smViewer"></sm-viewer>
            </div>
            <div class="cameraContent">
                <video-component v-bind:video="curVideo" v-bind:id="'virtualInspectEdit'"></video-component>
            </div>
            </Col>
            <Col span="10">
            <Row class="control">
                <Col span="8">
                <div class="cameraList">
                    <h2>摄像头列表</h2>
                    <br>
                    <ul>
                        <li v-for="(camera,index) in cameraList" :key="index" @click="selectCamera(camera)">{{ camera.name }}</li>
                    </ul>
                </div>
                </Col>
                <Col span="16">
                <div class="controlContent">
                    <h2 class="titleCtr">云台控制</h2>
                    <VideoControls @startDirectCtrl="start" @stopDirectCtrl="stop" v-bind:isDisabled="!isSelected"></VideoControls>
                </div>
                <Button type="ghost" @click="back" class="back">返回</Button>
                <div>
                    <h2 class="titlePos">预置位</h2>
                    <Button type="primary" @click="add" class="add" size="small" v-if="!isAdd">添加</Button>
                    <div class="posContent">
                        <div class="positions" v-for="(pos,index) in perPositions" :key="index">
                            <span class="name">{{ pos }}</span>
                            <div class="options">
                                <Button type="primary" size="small" @click="set(pos)">设置</Button>
                                <Button type="primary" size="small" @click="match(pos)">匹配</Button>
                                <Button type="primary" size="small" icon="edit" @click="edit(pos)"></Button>
                                <Button type="error" size="small" icon="trash-a" @click="del(pos)"></Button>
                            </div>
                        </div>
                    </div>
                    <Input type="text" v-model="name" class="text" v-if="isAdd" placeholder="请填写预置位名称" />
                    <br>
                    <Button type="primary" @click="addConfirm" size="small" v-if="isAdd" class="btn">确定</Button>
                    <Button type="ghost" @click="cancel" size="small" class="cancel" v-if="isAdd">取消</Button>
                </div>
                </Col>
            </Row>
            </Col>
            <!-- <Col span="2">
            <div class="camera">
                <div class="cameraContent">
                    <vlc-video v-bind:val="val"></vlc-video>
                </div>
            </div> -->
            </Col>
        </Row>
    </div>
</template>

<script>
import VideoControls from "../../../../components/UM/MAM/VideoControls";
import SmViewer from "../../../../components/Common/3D/3DViewer";
import VideoComponent from "../../../../components/Common/Video/VideoComponent";

export default {
    name: "VirtualInspectEdit",
    data() {
        return {
            cameraList: [
                {
                    id: 7001,
                    name: "红外摄像头1",
                    url:
                        "rtsp://admin:123456@192.168.3.201:554/h264/ch1/main/av_stream",
                    positionSupport: true,
                    description: "A Camera for tunnel"
                },
                {
                    id: 7002,
                    name: "红外摄像头2",
                    url:
                        "rtsp://admin:123456@192.168.3.202:554/h264/ch1/main/av_stream",
                    positionSupport: true,
                    description: "A Camera for tunnel"
                }
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
            cameraPosition: null
        };
    },
    components: {
        VideoControls,
        SmViewer,
        VideoComponent
    },
    mounted() {
        this.Log.info("初始化调用刷新");
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
        start(data) {
            this.axios
                .get("videos/" + this.curVideo.id + "/move/" + data.direction)
                .then(res => {
                    this.Log.info("move success", res);
                });
        },
        stop(data) {
            this.axios.get("videos/" + this.curVideo.id + "/stop").then(res => {
                this.Log.info("stop success", res);
            });
        },
        selectCamera(camera) {
            this.Log.info("selected camera", camera);
            this.curVideo.id = camera.id;
            this.isSelected = true;
            this.curVideo.url = camera.url;
            this.getPositions();
        },
        getPositions() {
            this.axios
                .get("videos/" + this.curVideo.id + "/presets")
                .then(res => {
                    let { code, data } = res.data;
                    if (code == 200) {
                        this.perPositions = [];
                        this.perPositions = data;
                        if (this.perPositions.length == 0)
                            this.perPositions = ["default"];
                    }
                });
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
                this.axios
                    .post(
                        "videos/" + this.curVideo.id + "/presets",
                        this.addParam
                    )
                    .then(res => {
                        let { code, data } = res.data;
                        if (code == 200) {
                            this.isAdd = false;
                            this.name = null;
                            this.getPositions();
                        }
                    });
            }
        },
        set(name) {
            this.axios
                .get(
                    "videos/" + this.curVideo.id + "/presets/" + name + "/goto"
                )
                .then(res => {
                    this.Log.info("setted" + res.data);
                });
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
                    _this.axios
                        .delete("videos/" + _this.curVideo.id + "/presets", {
                            data: param
                        })
                        .then(res => {
                            let { code, data } = res.data;
                            if (code == 200) {
                                _this.getPositions();
                            }
                        });
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
                    _this.axios
                        .put("videos/" + _this.curVideo.id + "/presets", param)
                        .then(res => {
                            let { code, data } = res.data;
                            if (code == 200) {
                                _this.getPositions();
                            }
                        });
                }
            });
        },
        // 3D相机位置刷新
        refreshCameraPosition(position) {
            this.cameraPosition = position;
            this.Log.info("get position:", this.cameraPosition);
            // 根据相机位置获得section信息
            this.axios.post("sections/gps", this.cameraPosition).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.Log.info("get section ", data);
                    if (this.curSection.id != data.sectionInfo.id) {
                        // 如果section变了，查找section中的相机
                        this.curSection = data.sectionInfo;
                        this.getVideos(this.curSection.id);
                    }
                }
            });

            this.axios
                .post("virual-inspection/video", this.cameraPosition)
                .then(res => {
                    let { code, data } = res.data;
                    if (code == 200) {
                        this.Log.info("get camera", data);
                    }
                });
        },
        getVideos(sectionId) {
            this.axios
                .post("videos/condition", {
                    sectionId: sectionId
                })
                .then(res => {
                    let { code, data } = res.data;
                    if (code == 200) {
                        this.cameraList = data;
                    }
                });
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
            this.axios.post("preset-3d", videoPreset).then(res => {
                let { code, data } = res.data;
                this.Log.info(data);
            });
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
    border: 1px solid #b3b0b0;
}
.camera {
    height: 86vh;
    border: 1px solid #b3b0b0;
}
.cameraList {
    margin: 10px;
    padding: 10px;
    background-color: white;
}
.cameraList ul {
    list-style-type: none;
}
.cameraList ul li {
    font-size: 16px;
    cursor: pointer;
    padding: 4px;
}
.cameraList ul li:hover {
    background-color: #ddd;
}
.controlContent {
    margin-top: 20px;
    margin-left: 20px;
    width: 80%;
    display: inline-block;
}
.titleCtr {
    margin-bottom: 10px;
}
.titlePos {
    margin-top: 20px;
    display: inline-block;
}
.add {
    float: right;
    margin-right: 10px;
    margin-top: 20px;
}
.posContent {
    margin-top: 10px;
    max-height: 40vh;
    overflow-y: auto;
    width: 100%;
}
.positions {
    padding: 10px;
    width: 100%;
}
.name {
    font-size: 16px;
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
    height: 42vh;
}
.text {
    width: 90%;
    margin-top: 10px;
    margin-left: 5%;
}
.sm {
    height: 42vh;
}
.back {
    float: right;
}
</style>
