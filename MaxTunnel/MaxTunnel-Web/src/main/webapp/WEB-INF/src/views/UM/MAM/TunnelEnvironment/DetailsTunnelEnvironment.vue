<template>
    <div>
        <div style="margin: 1vh;">
            <RadioGroup v-model="queryCondition.curDataType" type="button" @on-change="changeDataType" size="large">
                <Radio v-for="(item,key) in curDataTypeList" :key="key" :label="item.val" style="font-size: 1.5vmin;height: 3vmin;line-height: 3vmin"
                    :class="{select_radio:queryCondition.curDataType==item.val}">{{item.key}}</Radio>
            </RadioGroup>
        </div>
        <div style="margin: 1vh;">
            <RadioGroup v-model="queryCondition.areaId" type="button" @on-change="changeAreaLocation" size="large">
                <Radio v-for="(item,key) in areas" :key="key" :label="item.id" style="font-size: 1.5vmin;height: 3vmin;line-height: 3vmin"
                    :class="{select_radio:queryCondition.areaId==item.id}">{{item.name}}</Radio>
            </RadioGroup>
        </div>
        <div style="margin: 1vh;">
            <check-select v-bind="storeProp"></check-select>
        </div>
        <Row :gutter="16">
            <Col span="12">
            <div class="data">
                <div class="titles">
                    <div class="title" @click="chooseModule(0)" :class="{'active' : curModule === 0}">
                        <span>
                            <Icon type="ios-film" class="icons"></Icon>视频
                        </span>
                    </div>
                    <div class="title" @click="chooseModule(1)" :class="{'active' : curModule === 1}">
                        <span>
                            <Icon type="map" class="icons"></Icon>管廊模型
                        </span>
                    </div>
                </div>
                <div class="map">
                    <Carousel v-bind="curCarousel" v-if="curModule === 0"></Carousel>
                    <TestSmViewer ref="smViewer" v-if="curModule === 1"></TestSmViewer>
                </div>
            </div>
            </Col>
            <Col span="12" class="data" style="overflow-y:auto ">
            <Row :gutter="16" style="margin-right: 2px;">
                <Col span="8" v-for="item in Obj" :value="item.ObjName" :key="item.id">
                <SimulatedData v-bind:Obj="item" v-if="item.datatypeId==1" @changeStatus="changeStatus"></SimulatedData>
                <showSwitchData v-bind:Obj="item" v-else @changeStatus="changeStatus"></showSwitchData>
                </Col>
            </Row>
            </Col>
        </Row>
    </div>
</template>
<script>
    import videoComponent from "../../../../components/Common/Video/VideoComponent.vue";
    import Modal from "../../../../components/Common/Modal/ShowMapDataModal.vue";
    import TestSmViewer from "../../../../components/Common/3D/simple3DViewer";
    import SimulatedData from "../../../../components/UM/MAM/ShowSimulatedData";
    import showSwitchData from "../../../../components/UM/MAM/ShowSwitchData";
    import {
        TunnelService
    } from "../../../../services/tunnelService";
    import {
        EnumsService
    } from "../../../../services/enumsService";
    import {
        MonitorDataService
    } from "../../../../services/monitorDataService";
    import {
        SuperMapSqlQuery
    } from "../../../../scripts/three.js";
    import EnvironmentShow from "../../../../components/Common/TunnelDisplay/EnvironmentShow";
    import Carousel from "../../../../components/Common/Carousel.vue";
    import checkSelect from "../../../../components/Common/CheckSelect.vue";

    export default {
        name: "detail-tunnel-environment",
        data() {
            return {
                tabName: "",
                storeProp: {
                    itemLen: 12,
                    dataList: [],
                    selectObj: {
                        selectId: ""
                    }
                },
                curCarousel: {
                    videolist: []
                },
                dataInterval: null,
                Obj: [],
                scene: null,
                queryCondition: {
                    tunnelId: null, //监测仓ID
                    storeId: null, //监测区段ID
                    areaId: null,
                    curDataType: "",
                    monitorType: 1 //监测内容类型,1为环境
                },
                curDataTypeList: [],
                tunnelId: 0,
                tunnels: [],
                tunnelProps: [], //管廊统计数据
                tunnelPropsMax: [], //监测数据对应最大值
                areas: [], //管廊对应区段数据
                curTunnelName: "",
                curModule: 0
            };
        },
        watch: {
            $route: function () {
                this.tunnelId = parseInt(this.$route.params.id);
                this.queryCondition.tunnelId = this.tunnelId;
                this.fentchData();
                this.getObjDetialData();
            },
            storeProp: {
                handler: function (newVal, oldVal) {
                    let _this = this;
                    _this.queryCondition.storeId = newVal.selectObj.selectId;
                    _this.changeStore();
                },
                deep: true
            }
        },
        beforeRouteLeave(to, from, next) {
            if (
                to.name == "设备管理主页" ||
                to.name == "UMPatrolHomePage" ||
                to.name == "虚拟巡检" ||
                to.name == "人员定位详情" ||
                to.name == "管廊安防监控列表" ||
                to.name == "管廊环境监控列表"
            ) {
                from.meta.keepAlive = true;
                to.meta.keepAlive = true;
                this.$destroy();
                next();
            } else {
                to.meta.keepAlive = false;
                from.meta.keepAlive = false;
                this.$destroy();
                next();
            }
        },
        components: {
            SimulatedData,
            showSwitchData,
            Modal,
            EnvironmentShow,
            TestSmViewer,
            videoComponent,
            Carousel,
            checkSelect
        },
        mounted() {
            this.fentchData();
            this.changeDataType();
            this.intervalData();
        },
        methods: {
            changeTabs() {
                var _this = this;
            },

            intervalData() {
                let _this = this;
                _this.dataInterval = setInterval(
                    () => _this.getObjDetialData(),
                    10000
                );
            },
            //变更监测仓
            changeStore() {
                //获取区段列表
                let _this = this;
                //获取位置信息
                let curView = _this.storeProp.dataList.filter(
                    a => a.id == _this.queryCondition.storeId
                )[0].camera;
                if (curView && curView != "") {
                    _this.changeArea(curView);
                }
                _this.getObjDetialData();
                _this.getvideos();
            },
            //变更区段
            changeAreaLocation() {
                this.getObjDetialData();
                this.getvideos();
            },
            //切换数据类型
            changeDataType() {
                this.getObjDetialData();
            },
            //获取数据
            fentchData() {
                this.tunnelId = parseInt(this.$route.params.id);
                this.queryCondition.tunnelId = this.tunnelId;
                let _this = this;
                //获取监测内容
                EnumsService.getMonitorType().then(result => {
                    if (result) {
                        result.forEach(a => {
                            if (a.val == _this.queryCondition.monitorType) {
                                _this.curDataTypeList = a.objectTypeList;
                                _this.queryCondition.curDataType =
                                    _this.curDataTypeList[0].val;
                                _this.getObjDetialData();
                            }
                        });
                    }
                });
                //获取管廊列表
                TunnelService.getTunnels().then(
                    result => {
                        _this.tunnels = result;
                        _this.tunnels.forEach(a => {
                            if (a.id == _this.tunnelId) {
                                _this.curTunnelName = a.name;
                            }
                        });
                        _this.getObjDetialData();
                    },
                    error => {
                        console.log(error);
                    }
                );

                //获取监测仓列表
                TunnelService.getStoresByTunnelId(_this.tunnelId).then(
                    result => {
                        _this.storeProp.dataList = [{
                            id: 0,
                            name: "全部"
                        }];
                        result.forEach((a, index) => {
                            let temp = {};
                            temp.id = a.id;
                            temp.name = a.name;
                            _this.storeProp.dataList.push(temp);
                        });
                        _this.storeProp.selectObj.selectId =
                            _this.storeProp.dataList[0].id;
                        _this.queryCondition.storeId =
                            _this.storeProp.selectObj.selectId;
                        _this.getObjDetialData();
                        _this.getvideos();
                    },
                    error => {
                        console.log(error);
                    }
                );

                //获取区域列表
                TunnelService.getTunnelArea(_this.tunnelId).then(result => {
                    if (result) {
                        _this.areas = [{
                            name: "全部",
                            id: 0
                        }];
                        result.forEach(a => {
                            var temp = {};
                            temp.name = a.name;
                            temp.id = a.id;
                            _this.areas.push(temp);
                        });
                        _this.queryCondition.areaId = _this.areas[0].id;
                        result.forEach(a => {
                            if (a.name == "22区") {
                                _this.queryCondition.areaId = a.id;
                            }
                        });
                        _this.getObjDetialData();
                        _this.getvideos();
                    }
                });
            },
            //变更模型视角
            changeArea(area) {
                let _this = this;
                try {
                    let location = area.split("/");
                    _this.scene.camera.flyTo({
                        destination: new Cesium.Cartesian3.fromDegrees(
                            parseFloat(location[0]),
                            parseFloat(location[1]),
                            parseFloat(location[2])
                        ),
                        orientation: {
                            heading: parseFloat(location[3]),
                            pitch: parseFloat(location[4]),
                            roll: parseFloat(location[5])
                        }
                    });
                } catch (e) {}
            },

            //定位设备切换开关量控制
            changeStatus(id, ObjVal, datatypeId, clickStatus) {
                if (datatypeId != 1) {
                    this.Obj.filter(a => a.id == id)[0].ObjVal = ObjVal;
                }
                if (clickStatus) {
                    this.Obj.forEach(b => {
                        if (b.id == id) {
                            b.clickStatus = clickStatus;
                            this.Log.info("click " + b.id);
                            SuperMapSqlQuery(
                                    this.SuperMapConfig.BIM_DATA,
                                    this.VMConfig.queryParam,
                                    "moid = " + b.id
                                )
                                .then(res => {
                                    this.Log.info("查找成功", res);
                                    if (res.length > 0) {
                                        this.$refs.smViewer.LookAt1(
                                            res[0],
                                            50,
                                            -10,
                                            5
                                        );
                                    }
                                })
                                .then(res => {
                                    this.Log.info("查找失败", res);
                                });
                        } else {
                            b.clickStatus = !clickStatus;
                        }
                    });
                }
            },
            // //获取详情面板的数据
            getObjDetialData() {
                if (this.areas.length == 0) return;
                if (this.storeProp.dataList.length == 0) return;
                if (this.curDataTypeList.length == 0) return;
                let _this = this;
                var Params = {
                    tunnelId: _this.queryCondition.tunnelId,
                    storeId: _this.queryCondition.storeId == 0 ?
                        null : _this.queryCondition.storeId,
                    areaId: _this.queryCondition.areaId == 0 ?
                        null : _this.queryCondition.areaId,
                    objtypeId: _this.queryCondition.curDataType
                };
                MonitorDataService.objDetailDatagrid(Params).then(
                    result => {
                        _this.Obj = [];
                        result.forEach(a => {
                            let temp = {};
                            temp.ObjName = a.name;
                            temp.id = a.id;
                            temp.clickStatus = false;
                            temp.ObjVal = false;
                            temp.objtypeId = _this.queryCondition.curDataType;
                            temp.datatypeId = a.datatypeId;
                            temp.maxValue = a.maxValue;
                            temp.minValue = a.minValue;
                            temp.unit = a.unit;
                            temp.time =
                                a.time == undefined || a.time == "" ?
                                "" :
                                new Date(a.time).format(
                                    "yyyy-MM-dd hh:mm:ss"
                                );
                            if (a.datatypeId == 1) {
                                temp.ObjVal = a.curValue.toFixed(2);
                            } else {
                                temp.ObjVal = a.curValue;
                            }
                            temp.objtypeName =
                                _this.curTunnelName + a.area + a.store;
                            _this.Obj.push(temp);
                        });
                    },
                    error => {
                        console.log(error);
                    }
                );
            },

            //获取视频
            getvideos() {
                if (this.areas.length == 0) return;
                if (this.storeProp.dataList.length == 0) return;
                // if  (this.curDataTypeList.length == 0) return;
                let _this = this;
                var Params = {
                    tunnelId: _this.queryCondition.tunnelId,
                    storeId: _this.queryCondition.storeId == 0 ?
                        null : _this.queryCondition.storeId,
                    areaId: _this.queryCondition.areaId == 0 ?
                        null : _this.queryCondition.areaId
                };
                MonitorDataService.getdataVideos(Params).then(result => {
                    if (result && result.length > 0) {
                        this.curCarousel.videolist = result;
                    }
                });
            },
            chooseModule(val) {
                this.curModule = val;
            }
        },
        beforeDestroy() {
            clearInterval(this.dataInterval);
            this.dataInterval = null;
        }
    };
</script>


<style scoped>
    .ivu-radio-group-button>>>.ivu-radio-wrapper {
        transition: all 0.1s cubic-bezier(0.6, -0.28, 0.74, 0.05);
    }

    .select_radio {
        color: #fff;
        background-color: #869bcb;
        background-position: 0 -15px;
    }

    .ivu-radio-group-button>>>.ivu-radio-wrapper:hover {
        color: #fff;
        background-color: #3dbbcb;
        font-size: 17px;
        background-position: 0 -15px;
    }

    .map {
        width: 42vw;
        height: calc(65vh);
        margin-left: 10px;
    }

    .data {
        height: calc(72vh);
        font-size: 1.66vmin;
        padding-bottom: 10px;
    }

    .ivu-modal-wrap>.ivu-modal {
        left: 500px;
        top: 500px;
    }

    .ivu-tabs.ivu-tabs-card>.ivu-tabs-bar .ivu-tabs-tab {
        background: #adb3e2;
        color: #fff;
    }

    .Section #pageBox {
        border: 1px solid #b3b0b0;
        bottom: 0px;
        width: 83.5vw;
        left: 0px;
        text-align: center;
        overflow-x: hidden;
    }

    .showSection {
        display: inline-block;
        line-height: 35px;
    }

    .showSection {
        overflow: hidden;
        padding-left: 20%;
    }

    .showSection ul li {
        display: inline-block;
        border-right: 1px solid #b3b0b0;
        line-height: 35px;
        text-align: center;
    }

    .showSection ul li {
        width: 100px;
    }

    .tunnelsInfo,
    .environmentalMonitoring,
    .theFireWarning,
    .monitoringSituation {
        margin: 0.5vw;
        border: 1px solid #b3b0b0;
        border-radius: 8px;
        padding-left: 20px;
        box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
        border-color: #eee;
    }

    .tunnelsInfo div,
    .environmentalMonitoring div,
    .theFireWarning div,
    .monitoringSituation div {
        line-height: 5vh;
        font-size: 14px;
    }

    .inline-box div {
        display: inline-block;
    }

    .numerical {
        width: 60%;
    }

    .status {
        width: 20%;
    }

    .nextPage {
        position: relative;
        bottom: 0px;
        float: right;
    }

    .titles {
        margin: 0 0 0.6vmin 1vmin;
    }

    .title {
        display: inline-block;
        margin-right: 1vmin;
        cursor: pointer;
        padding-bottom: 0.4vmin;
    }

    .icons {
        margin-right: 0.4vmin;
    }

    .active {
        color: rgb(45, 140, 240);
        position: relative;
    }

    .active:after {
        content: "";
        position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 0.2vmin;
        background-color: rgb(45, 140, 240);
    }
</style>