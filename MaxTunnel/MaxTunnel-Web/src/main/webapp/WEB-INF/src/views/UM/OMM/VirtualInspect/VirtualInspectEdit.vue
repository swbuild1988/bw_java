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
                    <div class="titleCam">摄像头列表</div>
                    <br>
                    <ul>
                        <li v-for="(camera,index) in cameraList" :key="index" @click="selectCamera(camera)" :class="{'active' : curVideo.id == camera.id}">{{ camera.name }}</li>
                    </ul>
                </div>
                </Col>
                <Col span="16">
                <div class="controlContent">
                    <div class="titleCtr">云台控制</div>
                    <div style="width:80%;height:80%;margin: 10%;">
                        <VideoControl @startDirectCtrl="start" @stopDirectCtrl="stop" v-bind:isDisabled="!isSelected"></VideoControl>
                    </div>
                </div>
                <Button type="ghost" @click="back" class="back">返回</Button>
                <div class="posBox">
                    <div class="titlePos">预置位</div>
                    <Button type="primary" @click="add" class="add" v-if="!isAdd">添加</Button>
                    <div class="posContent">
                        <div class="positions" v-for="(pos,index) in perPositions" :key="index">
                            <div class="posTag"></div>
                            <span class="name">{{ pos }}</span>
                            <div class="options">
                                <Button type="primary" size="small" @click="set(pos)">设置</Button>
                                <Button type="primary" size="small" @click="match(pos)">匹配</Button>
                                <Button type="primary" size="small" icon="edit" @click="edit(pos)"></Button>
                                <Button type="error" size="small" icon="trash-a" @click="del(pos)"></Button>
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
import SmViewer from "../../../../components/Common/3D/3DViewer";
import VideoComponent from "../../../../components/Common/Video/VideoComponent";
import { VideoService } from "../../../../services/videoService";

export default {
    name: "VirtualInspectEdit",
    data() {
        return {
            cameraList: [
                {
                    id: 7005,
                    name: "红外摄像头1",
                    url: "192.168.6.156:8078",
                    positionSupport: true,
                    description: "A Camera for tunnel"
                },
                {
                    id: 7002,
                    name: "红外摄像头2",
                    url: "192.168.6.156:8078",
                    positionSupport: true,
                    description: "A Camera for tunnel"
                }
            ],
            curId: null,
            curSection: {
                id: 0
            },
            isSelected: false,
            perPositions: ['预置位1','预置位2','预置位4','预置位预置5预置位预置','预置位预置位预置位6','预置位7','预置位8'],
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
        VideoControl,
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
            let _this = this
            VideoService.cameraMove(this.curVideo.id,data.direction).then(
                result=>{
                    _this.Log.info('move success')
                },
                error=>{
                    _this.Log.info(error)
                })
            // this.axios
            //     .get("videos/" + this.curVideo.id + "/move/" + data.direction)
            //     .then(res => {
            //         this.Log.info("move success", res);
            //     });
        },
        stop(data) {
            let _this = this
            VideoService.cameraStop(this.curVideo.id).then(
                result=>{
                    _this.Log.info('stop success')
                },
                error=>{
                    _this.Log.info(error)
                })
            // this.axios.get("videos/" + this.curVideo.id + "/stop").then(res => {
            //     this.Log.info("stop success", res);
            // });
        },
        selectCamera(camera) {
            this.Log.info("selected camera", camera);
            this.curVideo.id = camera.id;
            this.isSelected = true;
            this.curVideo.url = camera.url;
            this.getPositions();
        },
        getPositions() {
            let _this = this
            VideoService.getPresetsByCameraId(this.curVideo.id).then(
                result=>{
                    _this.perPositions = [];
                    _this.perPositions = result;
                    if (_this.perPositions.length == 0)
                        _this.perPositions = ["default"];
                },
                error=>{
                    _this.Log.info(error);
                })
            // this.axios
            //     .get("videos/" + this.curVideo.id + "/presets")
            //     .then(res => {
            //         let { code, data } = res.data;
            //         if (code == 200) {
            //             this.perPositions = [];
            //             this.perPositions = data;
            //             if (this.perPositions.length == 0)
            //                 this.perPositions = ["default"];
            //         }
            //     });
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
                let _this = this
                VideoService.addPreset(this.curVideo.id,this.addParam).then(
                    result=>{
                        _this.isAdd = false;
                        _this.name = null;
                        _this.getPositions();
                    })
                // this.axios
                //     .post(
                //         "videos/" + this.curVideo.id + "/presets",
                //         this.addParam
                //     )
                //     .then(res => {
                //         let { code, data } = res.data;
                //         if (code == 200) {
                //             this.isAdd = false;
                //             this.name = null;
                //             this.getPositions();
                //         }
                //     });
            }
        },
        set(name) {
            let _this = this
            VideoService.goToPreset(this.curVideo.id,name).then(
                result=>{
                    _this.Log.info("setted" + res.data);
                },
                error=>{
                    _this.Log.info(error)
                })
            // this.axios
            //     .get(
            //         "videos/" + this.curVideo.id + "/presets/" + name + "/goto"
            //     )
            //     .then(res => {
            //         this.Log.info("setted" + res.data);
            //     });
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
                        result=>{
                             _this.getPositions();
                        },
                        error=>{
                            _this.Log.info(error)
                        })
                    // _this.axios
                    //     .delete("videos/" + _this.curVideo.id + "/presets", {
                    //         data: param
                    //     })
                    //     .then(res => {
                    //         let { code, data } = res.data;
                    //         if (code == 200) {
                    //             _this.getPositions();
                    //         }
                    //     });
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
                    VideoService.editPreset(_this.curVideo.id,param).then(
                        result=>{
                             _this.getPositions();
                        },
                        error=>{
                            _this.Log.info(error)
                        })
                    // _this.axios
                    //     .put("videos/" + _this.curVideo.id + "/presets", param)
                    //     .then(res => {
                    //         let { code, data } = res.data;
                    //         if (code == 200) {
                    //             _this.getPositions();
                    //         }
                    //     });
                }
            });
        },
        // 3D相机位置刷新
        refreshCameraPosition(position) {
            this.cameraPosition = position;
            this.Log.info("get position:", this.cameraPosition);
            // 根据相机位置获得section信息
            let _this = this
            VideoService.getSections(this.cameraPosition).then(
                result=>{
                    _this.Log.info("get section ", result);
                    if (_this.curSection.id != result.sectionInfo.id) {
                        // 如果section变了，查找section中的相机
                        _this.curSection = result.sectionInfo;
                        _this.getVideos(_this.curSection.id);
                    }
                },
                error=>{
                    _this.Log.info(error)
                })
            // this.axios.post("sections/gps", this.cameraPosition).then(res => {
            //     let { code, data } = res.data;
            //     if (code == 200) {
            //         this.Log.info("get section ", data);
            //         if (this.curSection.id != data.sectionInfo.id) {
            //             // 如果section变了，查找section中的相机
            //             this.curSection = data.sectionInfo;
            //             this.getVideos(this.curSection.id);
            //         }
            //     }
            // });
            VideoService.getVICameras(this.cameraPosition).then(
                result=>{
                    _this.Log.info("get camera", result);
                },
                error=>{
                    _this.Log.info(error);
                })
            // this.axios
            //     .post("virual-inspection/video", this.cameraPosition)
            //     .then(res => {
            //         let { code, data } = res.data;
            //         if (code == 200) {
            //             this.Log.info("get camera", data);
            //         }
            //     });
        },
        getVideos(sectionId) {
            let _this = this
            let param = {
                 sectionId: sectionId
            }
            VideoService.getCamerasByConditions(param).then(
                result=>{
                    _this.cameraList = result
                },
                error=>{
                    _this.Log.info(error)
                })
            // this.axios
            //     .post("videos/condition", {
            //         sectionId: sectionId
            //     })
            //     .then(res => {
            //         let { code, data } = res.data;
            //         if (code == 200) {
            //             this.cameraList = data;
            //         }
            //     });
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
            let _this = this
            VideoService.matchPresetAnd3D(videoPreset).then(
                result=>{
                    _this.Log.info(result);
                },
                error=>{
                    _this.Log.info(error);
                })
            // this.axios.post("preset-3d", videoPreset).then(res => {
            //     let { code, data } = res.data;
            //     this.Log.info(data);
            // });
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
    margin: 0 10px;
    padding: 10px;
}
.titleCam{
    background-image: url('../../../../assets/UM/title4.png');
    background-size: 100% 100%;
    font-size: 18px;
    font-weight: bold;
    padding: 10px;
    text-align: center;

}
.cameraList ul {
    list-style-type: none;
    background-image: url('../../../../assets/UM/videoListBody.png');
    background-size: 100% 100%;
    padding: 10px;
}
.cameraList ul li {
    font-size: 16px;
    cursor: pointer;
    padding: 4px;
    text-align: center;
}
.cameraList ul li:hover {
    background-color: rgba(53,122,163);
    color: #fff;
}
.active {
    background-color: rgba(25, 190, 107, 0.4);
}
.controlContent {
    margin-top: 20px;
    padding-left: 20px;
    width: 82%;
    display: inline-block;
    background-image: url('../../../../assets/UM/videoSettingBody.png');
    background-size: 100% 100%;
    height: 24vh;
    position: relative;
}
.titleCtr {
    margin-bottom: 10px;
    font-size: 18px;
    font-weight: bold;
    position: absolute;
    width: 42%;
    height: 40px;
    top: -10px;
    left: 0;
    background: url('../../../../assets/UM/title.jpg') no-repeat;
    background-size: 100% 100%;
    color: #fff;
    text-align: center;
    padding: 8px 0;
}
.titlePos {
    margin: 10px;
    font-size: 18px;
    font-weight: bold;
    background: url('../../../../assets/UM/title.jpg') no-repeat;
    background-size: 100% 100%;
    color: #fff;
    text-align: center;
    position: absolute;
    width: 42%;
    height: 40px;
    top: -10px;
    left: -6px;
    padding: 8px 0;
}
.add {
    float: right;
    margin: 30px 24px 0px 0;
}
.posContent {
    max-height: 50vh;
    overflow-y: auto;
    width: 100%;
    padding: 10px;
}
.positions {
    background-color: rgb(53,122,163);
    padding: 10px 10px 10px 20px;
    color: #fff;
    margin: 10px;
    position: relative;
    border-radius: 6px;
    line-height: 26px;
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
    height: 43vh;
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
    background-color: #f0f0f0;
}
.posBox{
  background: url('../../../../assets/UM/videoSettingBody.png') no-repeat;
  background-size: 100% 100%;
  margin-top: 10px;
  height: 58vh;
  position: relative;
}
.posTag{
  position: absolute;
  background-color: rgb(185,121,144);
  height: 100%;
  width: 6px;
  top: 0;
  left: 0;
  border-radius: 6px;
}
</style>
