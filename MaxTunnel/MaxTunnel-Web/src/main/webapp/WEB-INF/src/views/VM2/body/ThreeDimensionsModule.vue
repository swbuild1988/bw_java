<template>
    <div class="Main">
        <div class="BIM">
            <sm-viewer
                ref="smViewer"
                :camera="camera"
                :infoBox="false"
                :navigation="false"
                :refreshCameraPosition="refreshCameraPosition"
                @showStorePosition="showStorePosition"
            ></sm-viewer>
        </div>
        <div class="Title">
            <module-title
                :title="title"
                @click.native="goTo"
            ></module-title>
        </div>
        <vm-select
            id="list-dropdown"
            :optionList="optionList"
            @getSelectVal="getAreas"
        ></vm-select>
        <vm-select
            id="area-dropdown"
            :optionList="areaList"
            :selectStyle="{ left:'15.5%' }"
            @getSelectVal="getStores"
        ></vm-select>
        <vm-select
            id="store-dropdown"
            :optionList="storeList"
            :selectStyle="{ left:'29%' }"
            @getSelectVal="changeStore"
        ></vm-select>
        <show-store-position v-bind:currPosition="storePosition"></show-store-position>
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
            optionList: [],//所有管廊
            areaList: [],//当前管廊下所有区域
            storeList: [],//当前区域下所有仓
            location: [],//缓存当前管廊下所有区域的位置
            showArea: false,
            refreshCameraPosition: {
                enable: true
            },
            storePosition: {
                tunnelName: '',
                areaName: '',
                storeName: ''
            }
        }
    },
    components: {
        SmViewer,
        showStorePosition,
        ModuleTitle,
        vmSelect
    },
    mounted() {
        this.init();
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

                    _this.optionList.splice(0);//清空管廊数组
                    tunnels.forEach(tunnel => _this.optionList.push({ value: tunnel.id, label: tunnel.name }));

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
                    _this.areaList.splice(0)//清空管廊数组

                    if (data.length) {
                        data.reverse().forEach(area => {
                            _this.areaList.push({ value: area.id, label: area.name })
                        });
                        resolve(data);
                    }
                })
            })
        },
        changeStore(storeId) {
            let _this = this;

            let [curCamera] = _this.location.filter(position => position.id == storeId);
            let { camera } = curCamera;

            try {
                let [longitude, latitude, height, roll, pitch, heading] = camera.split(',');

                this.$refs.smViewer.flyToMyLocation({
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
                            _this.storeList.push({ value: store.store.id, label: store.store.name });
                            _this.location.push({
                                id: store.store.id,
                                camera: store.camera
                            })
                        })

                        let store = this.getValByArray(stores);

                        resolve(store.store.id);
                    })
            })

        },
        getValByArray([firstVal, ...otherVal]) {
            return firstVal;
        },
        sendAlarms() {

            setTimeout(() => {
                this.axios.post('/alarms',
                    {
                        time: +new Date(),
                        alarmName: 'sad',
                        objectId: 222022501,
                        latitude: "112.49069725638859",
                        longitude: "37.71331808517105",
                        description: 'sad',
                        alarmSeverity: 1,
                        additionalText: '',
                        alarmSource: ''
                    })
                    .then(err => console.log(err)).catch(err => console.log('err2', err))
            }, 1000)
        },
        showStorePosition(position) {
            this.storePosition = position;
        },
        destroyViewer() {
            this.$refs.smViewer.destory3D();
        },
    }
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
.Main >>> .positionInformation {
    top: 14%;
    right: 1%;
}
</style>
<style>
.cesium-viewer-bottom {
    display: none;
}
</style>
