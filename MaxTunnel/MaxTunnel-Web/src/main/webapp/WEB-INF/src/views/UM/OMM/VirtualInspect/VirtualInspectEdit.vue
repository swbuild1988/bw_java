<template>
    <div>
        <Row class="body">
            <Col span="14">
            <div class="sm">
                <div class="heading">
                    <span>{{ direction }}</span>
                </div>
                <vm-select id="list-dropdown" :optionList="optionList" :selectStyle="{ left:'0',top: '0'}" @getSelectVal="getAreas"></vm-select>
                <vm-select id="area-dropdown" :optionList="areaList" :selectStyle="{ left:'13.2%',top:'0' }" @getSelectVal="getStores"></vm-select>
                <vm-select id="store-dropdown" :optionList="storeList" :selectStyle="{ left:'26.4%',top: '0' }" @getSelectVal="changeStore"></vm-select>
                <sm-viewer @refreshCameraPosition="refreshCameraPosition" ref="smViewer"></sm-viewer>
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
                        <li v-for="camera in cameraList" :key="camera.id" @click="selectCamera(camera)" :class="{'active' : curVideo.id == camera.id}">
                            {{ camera.name + ' ' + camera.id }}
                        </li>
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
                    <Button type="primary" @click="add" class="add">添加</Button>
                    <div class="posContent">
                        <div class="positions" v-for="(pos,index) in perPositions" :key="index">
                            <div class="posTag"></div>
                            <span class="name">{{ pos }}</span>
                            <div class="options">
                                <Button type="primary" size="small" @click="set(pos)">到达预置位</Button>
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
import SmViewer from "../../../../components/Common/3D/simple3DViewer";
import VideoComponent from "../../../../components/Common/Video/VideoComponent";
import { VideoService } from "../../../../services/videoService";
import vmSelect from '../../../../components/Common/Select';
import { TunnelService } from '../../../../services/tunnelService';
import Vue from 'vue'
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
        this.init()
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
        init(){
            this.getTunnels().then(tunnel => {
                    this.getAreas(tunnel.value)
                        .then(areas => {
                            let area = this.getValByArray(areas);

                            this.getStores(area.id)
                                .then(areaId => this.changeStore(areaId));

                        });
                });
        },
        getTunnels() {
            let _this = this;

            return new Promise((resolve, reject) => {
                TunnelService.getTunnels().then(tunnels => {

                    _this.optionList.splice(0); //清空管廊数组
                    tunnels.forEach(tunnel => _this.optionList.push({
                        value: tunnel.id,
                        label: tunnel.name
                    }));

                    let tunnel = this.getValByArray(_this.optionList);

                    resolve(tunnel)
                })
            })
        },
        getAreas(val) {
            let _this = this;

            return new Promise((resolve, reject) => {
                TunnelService.getAreasByTunnelId(val).then(data => {

                    _this.showArea = true;
                    _this.areaList.splice(0) //清空管廊数组

                    if (data.length) {
                        data.reverse().forEach(area => {
                            _this.areaList.push({
                                value: area.id,
                                label: area.name
                            })
                        });
                        resolve(data);
                    }
                })
            })
        },
        changeStore(storeId) {
            let _this = this;
            let [ curCamera ] = _this.location.filter(position => position.id == storeId);
            let {
                camera
            } = curCamera;
            // console.log('camera',camera)
            try {
                let [longitude, latitude, height, roll, pitch, heading] = camera.split(',');

                flyToMyLocation({
                    scene: Vue.prototype['$simpleViewer'].scene,
                    position: {
                        longitude: longitude,
                        latitude: latitude,
                        height: height,
                        roll: roll,
                        pitch: pitch,
                        heading: heading
                    },
                    maximumHeight:4
                });
                
                this.direction = this.getDirection(heading)

            } catch (e) {

            }
        },
        getDirection(heading) {
            let direction = Math.abs((heading % 6.28)).toFixed(2)
            let value = null
            switch(direction){
                case 0:
                    value = '正北'
                    break
                case 1.57:
                    value = heading > 0 ? '正东' : '正西'           
                    break
                case 3.14:
                    value = '正南'
                    break
                case 4.71:
                    value = heading > 0 ? '正西' : '正东'
                    break
                default:
                    value = null
            }
            if(!value){
                let angle = direction * 180 / 3.14
                if(direction > 0 && direction < 1.57){ 
                    value = heading > 0 ? '北偏东' + angle.toFix(2) + '度' : 
                    '北偏西' + angle.toFixed(2) + '度'
                } else {
                    if(direction > 1.57 && direction < 3.14){
                        value = heading > 0 ? '东偏南' + (angle - 90).toFixed(2) + '度' : 
                        '西偏南' + (angle - 90).toFixed(2) + '度'
                    } else {
                        if(direction > 3.14 && direction < 4.71){
                            value = heading > 0 ? '南偏西' + (angle - 180).toFixed(2) + '度' :
                            '南偏东' + (angle - 180).toFixed(2) + '度'
                        } else {
                            value = heading > 0 ? '西偏北' + (angle - 270).toFixed(2) + '度' :
                            '东偏北' + (angle - 270).toFixed(2) + '度'
                        }
                    }
                }
            }
    
            return value
        },
        getStores(areaId) {
            let _this = this;

            return new Promise((resolve, reject) => {
                TunnelService.getStoresByAreaId({ areaId })
                    .then(stores => {
                        _this.storeList.splice(0);
                        _this.location.splice(0);

                        stores.forEach(store => {
                            if(!store.store || !store.startPoint) return;
                            _this.storeList.push({
                                value: store.store.id,
                                label: store.store.name
                            });
                            _this.location.push({
                                id: store.store.id,
                                camera: store.camera
                            })
                        })

                        let store = this.getValByArray(_this.storeList);

                        resolve(store.value);
                    })
            })

        },
        getValByArray([firstVal, ...otherVal]) {
            return firstVal;
        },
        start(data) {
            let _this = this
            VideoService.cameraMove(this.curVideo.id,data.direction).then(
                result=>{
                    _this.Log.info('move success')
                },
                error=>{
                    _this.Log.info(error)
                })
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
            VideoService.getVICameras(this.cameraPosition).then(
                result=>{
                    _this.Log.info("get camera", result);
                },
                error=>{
                    _this.Log.info(error);
                })
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
    font-size: 1.8vmin;
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
    font-size: 1.66vmin;
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
    font-size: 1.8vmin;
    font-weight: bold;
    position: absolute;
    width: 42%;
    height: 4vmin;
    top: -10px;
    left: 0;
    background: url('../../../../assets/UM/title.png') no-repeat;
    background-size: 100% 100%;
    color: #fff;
    text-align: center;
    padding: 8px 0;
}
.titlePos {
    margin: 10px;
    font-size: 1.8vmin;
    font-weight: bold;
    background: url('../../../../assets/UM/title.png') no-repeat;
    background-size: 100% 100%;
    color: #fff;
    text-align: center;
    position: absolute;
    width: 42%;
    height: 4vmin;
    top: -10px;
    left: -6px;
    padding: 8px 0;
}
.add {
    float: right;
    margin: 6% 4% 0px 0;
}
.posContent {
    max-height: 50vh;
    overflow-y: auto;
    width: 100%;
    padding: 10px;
}
.positions {
    background-color: rgb(53,122,163);
    padding: 1vmin 1vmin 1vmin 2vmin;
    color: #fff;
    margin: 1vmin;
    position: relative;
    border-radius: 6px;
    line-height: 2.6vmin;
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
    height: 43vh;
}
.text {
    width: 90%;
    margin-top: 10px;
    margin-left: 5%;
}
.sm {
    height: 42vh;
    position: relative;
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
.heading{
    position: absolute;
    top: 0;
    left: 63%;
    font-size: 1.4vmin;
    z-index: 10;
    border: 1px solid rgba(16,159,181,.5);
    box-shadow: 0 0 1rem #109FB5 inset;
    color: #fff;
    padding: 0.4vmin 2.4vmin;
}
</style>
