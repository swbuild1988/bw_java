<template>
    <div class="Main ThreeDMain">
        <div class="BIM">
            <sm-viewer ref="smViewer" :camera="camera" :infoBox="false" :navigation="false" :refreshCameraPosition="refreshCameraPosition"
                @showStorePosition="showStorePosition"></sm-viewer>
        </div>
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <vm-select id="list-dropdown" :optionList="optionList" @getSelectVal="getAreas"></vm-select>
        <vm-select id="area-dropdown" :optionList="areaList" :selectStyle="{ left:'15.5%' }" @getSelectVal="getStores"></vm-select>
        <vm-select id="store-dropdown" :optionList="storeList" :selectStyle="{ left:'29%' }" @getSelectVal="changeStore"></vm-select>
        <show-store-position v-bind:currPosition="storePosition"></show-store-position>
        <Button type="primary" v-show="showControlBtn" class="buttons contorlBtn" :icon="playOrPause.isPlay ? 'pause' : 'play' " @click="playFly"></Button>
        <Button type="primary" v-show="showControlBtn" class="buttons contorlBtn"  style="right:4vmin" icon="stop" @click="stopFly"></Button>
    
    </div>
</template>

<script>
    import SmViewer from '../../../components/Common/3D/simple3DViewer'
    import ModuleTitle from "../../../components/VM2/ModuleTitle";
    import Vue from 'vue'
    import showStorePosition from '../../../components/Common/Modal/showStorePosition'
    import {
        TunnelService,
    } from '../../../services/tunnelService'
    import vmSelect from '../../../components/Common/Select'
    import {
        flyToMyLocation
    } from "../../../scripts/commonFun";

    export default {
        data() {
            return {
                title: 'BIM系统',
                id: 'threeDimensional',
                camera: {
                    longitude: 112.49397907438006,
                    latitude: 37.710661662983384,
                    height: -1.7297007316681086,
                    roll: 2.5845992013273644e-12,
                    pitch: -0.30235107580130394,
                    heading: 1.7164865602395531
                },
                optionList: [], //所有管廊
                areaList: [], //当前管廊下所有区域
                storeList: [], //当前区域下所有仓
                location: [], //缓存当前管廊下所有区域的位置
                showArea: false,
                refreshCameraPosition: {
                    enable: true
                },
                storePosition: {
                    tunnelName: '',
                    areaName: '',
                    storeName: ''
                },
                showControlPanel: true,
                screenWidth: 1920,
                playOrPause: {
                    isPlay: false,
                    text: "开始"
                },
                showControlBtn:false
            }
        },
        components: {
            SmViewer,
            showStorePosition,
            ModuleTitle,
            vmSelect,
        },
        mounted() {
            let _this =this;

            this.init();
            this.listenerWindowSize();
            
            window.onresize = function(){
                _this.listenerWindowSize();
            }
        },
        methods: {
            init() {
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
                // return
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
                        }
                    });
                } catch (e) {

                }

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
            showStorePosition(position) {
                this.storePosition = position;
            },
            playFly() {
                if (this.playOrPause.isPlay) {
                    this.$refs.smViewer.pauseFly();
                } else {
                    this.$refs.smViewer.playFly();
                    console.log('ssssssss')
                }
                this.playOrPause.isPlay = !this.playOrPause.isPlay;
            },
            stopFly() {
                this.$refs.smViewer.stopFly();
                this.playOrPause.isPlay = false;
            },
            destroyViewer() {
                this.$refs.smViewer.destory3D();
            },
            listenerWindowSize(){
               this.showControlBtn = window.innerWidth > 1440 ? true : false;
            }
        },
        beforeDestroy() {
            this.stopFly();
        },
        destroyed() {
        },
    }
</script>

<style scoped>
    .Main {
        width: 100%;
        height: 100%;
        background: url("../../../assets/VM/module_bg.png") no-repeat;
        background-size: 100% 100%;
    }

    .Main .Title {
        width: 100%;
        height: 15%;
    }

    .Main .BIM {
        position: absolute;
        width: 98%;
        height: 85%;
        top: 14%;
        left: 1%;
    }

    .Main>>>.positionInformation {
        top: 14%;
        right: 1%;
    }

    .Main>>>.moveControlPanel {
        top: 25.5%;
        right: 2%;
        width: 25%;
    }

    .Main>>>.ControlPanelTitleDiff {
        font-size: 1rem;
        margin: .8rem 0 0 0;
    }

    .Main>>>.moveControlPanel {
        display: none;
    }
    .contorlBtn {
        position: absolute;
        right: 8vmin;
        background: transparent;
        top: 8.5vmin;
        border: 1px solid rgba(16,159,181,.5);
        -webkit-box-shadow: 0 0 1rem #109FB5 inset;
        box-shadow: 0 0 1rem #109FB5 inset;
    }
</style>
<style>
    .cesium-viewer-bottom {
        display: none;
    }
</style>