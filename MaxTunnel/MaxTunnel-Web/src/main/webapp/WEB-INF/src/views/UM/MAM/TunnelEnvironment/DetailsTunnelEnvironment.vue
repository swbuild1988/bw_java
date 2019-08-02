<template>
    <div>
        <!-- <div style="margin: 1vh;">
            <RadioGroup
                v-model="queryCondition.curDataType"
                type="button"
                @on-change="changeDataType"
                size="large"
            >
                <Radio
                    v-for="(item,index) in curDataTypeList"
                    :key="index"
                    :label="item.val"
                    style="font-size: 1.5vmin;height: 3vmin;line-height: 3vmin"
                    :class="{select_radio:queryCondition.curDataType==item.val}"    
                >{{item.key}}</Radio>
            </RadioGroup>
        </div>-->
        <div class="areas">
            <span class="common_spen">区域:</span>
            <Select
                v-model="queryCondition.areaId"
                style="width:76%"
                @on-change="changeAreaLocation"
            >
                <Option v-for="item in areas" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
        </div>

        <div class="detectionType">
            <span class="common_spen">检测类型:</span>
            <Select
                v-model="queryCondition.curDataType"
                style="width:76%"
                @on-change="changeDataType"
            >
                <Option
                    v-for="item in curDataTypeList"
                    :value="item.value"
                    :key="item.value"
                >{{ item.label }}</Option>
            </Select>
        </div>

        <!-- <div style="margin: 1vh;">
            <RadioGroup
                v-model="queryCondition.areaId"
                type="button"
                @on-change="changeAreaLocation"
                size="large"
            >
                <Radio
                    v-for="(item,key) in areas"
                    :key="key"
                    :label="item.id"
                    style="font-size: 1.5vmin;height: 3vmin;line-height: 3vmin"
                    :class="{select_radio:queryCondition.areaId==item.id}"
                >{{item.name}}</Radio>
            </RadioGroup>
        </div>-->
        <!-- <div style="margin: 1vh;">
            <check-select v-bind="storeProp" v-on:toParent="getStoreId"></check-select>
        </div>-->
        <div class="detectionBin">
            <span class="common_spen">检测仓:</span>
            <Select v-model="queryCondition.storeId" style="width:76%" @on-change="getStoreId">
                <Option
                    v-for="item in storeProp.dataList"
                    :value="item.value"
                    :key="item.value"
                >{{ item.label }}</Option>
            </Select>
        </div>
        <div class="area_length">
            <strong>里程:</strong>
            {{ areaLeath }}
            <strong v-if="areaLeath">{{areaLeathUnit}}</strong>
        </div>
        <tabs :tabList="tabs.tabList" :tabIndex="tabsIndex" @changeTab="changeTabs">
            <Row :gutter="16" v-show="isShowComponent">
                <Col span="12">
                    <div class="data">
                        <div class="titles">
                            <div
                                class="title"
                                @click="chooseModule(0)"
                                :class="{'active' : curModule === 0}"
                            >
                                <span>
                                    <Icon type="ios-film" class="icons"></Icon>视频
                                </span>
                            </div>
                            <div
                                class="title"
                                @click="chooseModule(1)"
                                :class="{'active' : curModule === 1}"
                            >
                                <span>
                                    <Icon type="map" class="icons"></Icon>管廊模型
                                </span>
                            </div>
                            <div class="screenNumChange">
                                <Poptip placement="left">
                                    <Icon type="navicon-round" class="button"></Icon>
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
                                            @click.native=" (9)"
                                        ></Icon>
                                    </div>
                                </Poptip>
                            </div>
                        </div>
                        <div class="map">
                            <Carousel v-bind="curCarousel" v-if="curModule === 0"></Carousel>
                            <TestSmViewer
                                ref="smViewer"
                                v-if="curModule === 1"
                                :detectionObjInfor="detectionObj"
                            ></TestSmViewer>
                            <!-- <Table :columns="environmentColums" :data="objTableDate" v-if="curModule === 2"></Table> -->
                        </div>
                    </div>
                </Col>
                <Col span="12" class="data" style="overflow-y:auto ">
                    <Row :gutter="16" style="margin-right: 2px;">
                        <Col
                            :span="queryCondition.curDataType == 30 ? '12' : '8'"
                            v-for="item in Obj"
                            :value="item.name"
                            :key="item.id"
                        >
                            <show-obj-data
                                v-bind:Obj="item"
                                @getSwicthState="changeSwicthState"
                                @changeStatus="changeStatus"
                                @changeView="setView"
                            ></show-obj-data>
                        </Col>
                    </Row>
                </Col>
            </Row>
            <Table :columns="environmentColums" :data="objTableDate" v-show="!isShowComponent"></Table>
        </tabs>
    </div>
</template>
<script>
import videoComponent from "../../../../components/Common/Video/VideoComponent.vue";
import Modal from "../../../../components/Common/Modal/ShowMapDataModal.vue";
import TestSmViewer from "../../../../components/Common/3D/simple3DViewer";
import ShowObjData from "../../../../components/UM/MAM/ShowObjData/ShowObjData";
import { TunnelService } from "../../../../services/tunnelService";
import { EnumsService } from "../../../../services/enumsService";
import { MonitorDataService } from "../../../../services/monitorDataService";
import { SuperMapSqlQuery } from "../../../../scripts/three.js";
import EnvironmentShow from "../../../../components/Common/TunnelDisplay/EnvironmentShow";
import Carousel from "../../../../components/Common/Carousel.vue";
import checkSelect from "../../../../components/Common/CheckSelect.vue";
import { changStrLength } from "../../../../scripts/commonFun";
import { MeasObjServer } from "../../../../services/MeasObjectSerivers";
import tabs from "../../../../components/Common/Tabs.vue";

export default {
    name: "detail-tunnel-environment",
    computed: {
        isShowComponent() {
            return this.$store.state.UMstate.tabelCrad.isShowCardComponent;
        },
        tabsIndex() {
            return this.$store.state.UMstate.tabelCrad.buttomIndex;
        }
    },
    data() {
        return {
            tabName: "",
            refreshData: {
                intervalTime: 1,
                refreshTime: 30,
                curTime: 0
            },
            storeProp: {
                itemLen: 12,
                dataList: [],
                selectObj: {
                    selectId: ""
                }
            },
            curCarousel: {
                videolist: [],
                videoNumber: 1
            },
            dataInterval: null,
            Obj: [],
            scene: null,
            queryCondition: {
                tunnelId: null, //监测仓ID
                storeId: null, //监测区段ID
                areaId: null,
                // detectionId:null,
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
            curModule: 0,
            detectionObj: null,
            environmentColums: [
                {
                    type: "index",
                    width: (((window.innerWidth / 100) * 80) / 100) * 4,
                    align: "center"
                },
                {
                    title: "名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "所属区域",
                    key: "area",
                    align: "center"
                },
                {
                    title: "所属仓",
                    key: "store",
                    align: "center"
                },
                {
                    title: "当前值",
                    align: "center",
                    render: (h, params) => {
                        let temp = params.row.curValue + params.row.unit;
                        return h("div", temp);
                    }
                },
                {
                    title: "采集时间",
                    align: "center",
                    render: (h, params) => {
                        let temp = new Date(params.row.time).format(
                            "yyyy-MM-dd hh:mm:ss"
                        );
                        return h("div", temp);
                    }
                },
                {
                    title: "最小值",
                    align: "center",
                    render: (h, params) => {
                        let temp = params.row.minValue + params.row.unit;
                        return h("div", temp);
                    }
                },
                {
                    title: "最大值",
                    align: "center",
                    render: (h, params) => {
                        let temp = params.row.maxValue + params.row.unit;
                        return h("div", temp);
                    }
                }
            ],
            objTableDate: [],
            areaLeath: "",
            areaLeathUnit: "米",
            tabs: {
                tabList: [
                    {
                        index: 0,
                        name: "卡片"
                    },
                    {
                        index: 1,
                        name: "表格"
                    }
                ]
            }
        };
    },
    watch: {
        $route: function() {
            this.tunnelId = this.$route.params.id || this.$route.query.tunnelId;
            this.queryCondition.tunnelId = this.tunnelId;
            this.fentchData();
            this.getObjDetialData();
        },
        storeProp: {
            handler: function(newVal, oldVal) {
                let _this = this;
                _this.queryCondition.storeId = newVal.selectObj.selectId;
                _this.changeStore();
            },
            deep: true
        }
    },
    created() {
        if (localStorage.getItem("choosedTab")) {
            this.choosedTabPane = localStorage.getItem("choosedTab");
        } else {
            this.choosedTabPane = "卡片";
        }
    },
    components: {
        ShowObjData,
        Modal,
        EnvironmentShow,
        TestSmViewer,
        videoComponent,
        Carousel,
        checkSelect,
        tabs
    },
    mounted() {
        if (this.$route.query) {
            this.tunnelId = this.$route.query.tunnelId;
            this.queryCondition.storeId = this.$route.query.storeId;
            this.queryCondition.areaId = this.$route.query.areaId;
        }
        this.fentchData();
        this.changeDataType();
        this.intervalData();
    },
    methods: {
        changeTabs(tab) {
            this.$store.commit("changeCardStatus", {
                status: tab.index == 0 ? true : false,
                index: tab.index
            }); //保存当前按钮状态
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
            if (this.$route.query.storeId != undefined) {
                _this.queryCondition.storeId = this.$route.query.storeId;
            }
            let curView = _this.storeProp.dataList.filter(
                a => a.value == _this.queryCondition.storeId
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
            this.tunnelId =
                parseInt(this.$route.params.id) ||
                parseInt(this.$route.query.tunnelId);
            this.queryCondition.tunnelId = this.tunnelId;
            let _this = this;
            //获取监测内容
            EnumsService.getMonitorType().then(result => {
                if (result) {
                    result.forEach(a => {
                        if (a.val == _this.queryCondition.monitorType) {
                            a.objectTypeList.forEach(item =>
                                _this.curDataTypeList.push({
                                    value: item.val,
                                    label: item.key
                                })
                            );

                            if (this.$route.query.objtypeKey === undefined) {
                                _this.queryCondition.curDataType =
                                    _this.curDataTypeList[0].value;
                            } else {
                                a.objectTypeList.forEach((item, index) => {
                                    if (
                                        item.key ===
                                        this.$route.query.objtypeKey
                                    ) {
                                        _this.queryCondition.curDataType =
                                            _this.curDataTypeList[index].value;
                                    }
                                });
                            }
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
                    var arr = [
                        {
                            value: 0,
                            label: "全部"
                        }
                    ];

                    let transitionResult = result.map(item => {
                        let {
                            id,
                            name,
                            camera,
                            crtTime,
                            endPoint,
                            height,
                            k,
                            l,
                            parentId,
                            sn,
                            startPoint,
                            storeType,
                            storeTypeId,
                            tunnel,
                            tunnelId,
                            width
                        } = item;
                        return {
                            value: id,
                            label: name,
                            camera,
                            crtTime,
                            endPoint,
                            height,
                            k,
                            l,
                            parentId,
                            sn,
                            startPoint,
                            storeType,
                            storeTypeId,
                            tunnel,
                            tunnelId,
                            width
                        };
                    });

                    _this.storeProp.dataList = arr.concat(transitionResult);
                    // _this.queryCondition.detectionId = _this.storeProp.dataList[0].value;
                    if (this.$route.query.storeId != undefined) {
                        _this.storeProp.selectObj.selectId = this.$route.query.storeId;
                    } else {
                        _this.storeProp.selectObj.selectId =
                            _this.storeProp.dataList[0].value;
                    }
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
                    _this.areas = [
                        {
                            value: 0,
                            label: "全部"
                        }
                    ];
                    result.forEach(a => {
                        _this.areas.push({
                            value: a.id,
                            label: a.name
                        });
                    });
                    _this.queryCondition.areaId = _this.areas[0].value;

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

        // 刷新控制量的值
        refresh(id, target) {
            let _this = this;
            setTimeout(() => {
                if (_this.refreshData.curTime > _this.refreshData.refreshTime)
                    return;
                // 判断是否已经达到了目标
                for (let o of _this.Obj) {
                    if (o.id != id) {
                        continue;
                    }

                    if (!isNaN(o.curStatus)) {
                        // 如果打开了且目标为打开
                        if (target && o.curStatus == 1) return;
                        if (!target && o.curStatus == 0) return;
                    }
                }
                MeasObjServer.getMeasObjCurValue(id).then(
                    res => {
                        for (let a of _this.Obj) {
                            if (a.id != res.id) {
                                continue;
                            }
                            a.time = new Date(res.time).format(
                                "yyyy-MM-dd hh:mm:ss"
                            );
                            a.ObjVal = res.curValue;
                        }
                    },
                    error => {
                        _this.Log.info("获取数据:" + id + "当前值错误");
                    }
                );

                _this.refreshData.curTime += _this.refreshData.intervalTime;
                _this.refresh(id, target);
            }, _this.refreshData.intervalTime * 1000);
        },

        //定位设备切换开关量控制
        changeStatus(id, ObjVal, datatypeId, clickStatus) {
            if (clickStatus === null) {
                let param = {
                    id: id,
                    code: ObjVal ? "open" : "close"
                };
                MeasObjServer.changeEquimentStatus(param).then(
                    res => {
                        // 刷新
                        this.refreshData.curTime = 0;
                        this.refresh(id, ObjVal);

                        this.$Message.info("操作成功");
                    },
                    error => {
                        this.$Message.error("操作失败");
                    }
                );
            }
        },
        setView(id, datatypeId) {
            if (!!id && !!datatypeId) {
                this.detectionObj = {
                    id: changStrLength(id, 10),
                    moTypeId: datatypeId
                };
            }
        },
        // 复位按钮
        reset(id) {
            let param = {
                id: id,
                code: "clear"
            };
            MeasObjServer.changeEquimentStatus(param).then(
                res => {
                    this.$Message.info("操作成功");
                },
                error => {
                    this.$Message.error("操作失败");
                }
            );
        },

        getStoreId(data) {
            this.queryCondition.storeId = data;
            this.getObjDetialData();
            this.getvideos();
        },
        // //获取详情面板的数据
        getObjDetialData() {
            if (this.areas.length == 0) return;
            if (this.storeProp.dataList.length == 0) return;
            if (this.curDataTypeList.length == 0) return;
            let _this = this;
            var Params = {
                tunnelId: _this.queryCondition.tunnelId,
                storeId:
                    _this.queryCondition.storeId == 0
                        ? null
                        : _this.queryCondition.storeId,
                areaId:
                    _this.queryCondition.areaId == 0
                        ? null
                        : _this.queryCondition.areaId,
                objtypeId: _this.queryCondition.curDataType
            };
            MonitorDataService.objDetailDatagrid(Params).then(
                result => {
                    _this.objTableDate = result;
                    _this.Obj = [];
                    result.forEach(a => {
                        let temp = {};
                        _this.areaLeath = a.areaLeath;
                        temp = a;
                        temp.ObjName = a.name;
                        temp.ObjVal = a.curValue;
                        temp.clickStatus = false;
                        temp.objtypeId = _this.queryCondition.curDataType;
                        temp.time =
                            a.time == undefined || a.time == ""
                                ? ""
                                : new Date(a.time).format(
                                      "yyyy-MM-dd hh:mm:ss"
                                  );
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
                storeId:
                    _this.queryCondition.storeId == 0
                        ? null
                        : _this.queryCondition.storeId,
                areaId:
                    _this.queryCondition.areaId == 0
                        ? null
                        : _this.queryCondition.areaId
            };
            MonitorDataService.getdataVideos(Params).then(result => {
                if (result && result.length > 0) {
                    this.curCarousel.videolist = result;
                }
            });
        },
        chooseModule(val) {
            this.curModule = val;
        },
        chooseTab(name) {
            localStorage.setItem("choosedTab", name);
        },
        handleScreensNum(num) {
            this.curCarousel.videoNumber = num;
        },
        //操作状态
        changeSwicthState(id, status) {
            // state --> 0：关；1：开；2：开或关的过程中；3：故障
            this.Log.info("statusListener", id, status);
            for (let o of this.Obj) {
                if (o.id == id) {
                    o.curStatus = status;
                }
            }
        }
    },
    beforeDestroy() {
        clearInterval(this.dataInterval);
        this.dataInterval = null;
    }
};
</script>


<style scoped>
@import "../CommonCss/DetailsCom.css";

.ivu-radio-group-button >>> .ivu-radio-wrapper {
    transition: all 0.1s cubic-bezier(0.6, -0.28, 0.74, 0.05);
}

.select_radio {
    color: #fff;
    background-color: #869bcb;
    background-position: 0 -15px;
}

.ivu-radio-group-button >>> .ivu-radio-wrapper:hover {
    color: #fff;
    background-color: #3dbbcb;
    font-size: 17px;
    background-position: 0 -15px;
}

.map {
    width: 40vw;
    height: calc(65vh);
    margin-left: 10px;
}

.data {
    height: calc(72vh);
    font-size: 1.66vmin;
    padding-bottom: 10px;
}

.ivu-modal-wrap > .ivu-modal {
    left: 500px;
    top: 500px;
}

.ivu-tabs.ivu-tabs-card > .ivu-tabs-bar .ivu-tabs-tab {
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
    position: relative;
}

.titles {
    margin: 0 0 0.6vmin 1vmin;
    position: relative;
}

.title {
    display: inline-block;
    margin-right: 1vmin;
    cursor: pointer;
    padding-bottom: 0.4vmin;
}

.screenNumChange {
    position: absolute;
    top: 0;
    right: 0;
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

.areas {
    margin-top: 0.5%;
    margin-left: 1%;
    width: 19%;
    display: inline-block;
}

.detectionType {
    display: inline-block;
    width: 21%;
    margin-top: 0.5%;
}

.detectionBin {
    display: inline-block;
    width: 20%;
    margin-top: 0.5%;
    margin-right: 3%;
}

@media (min-width: 1921px) {
    .area_length {
        top: 1.7%;
    }
}

@media (max-width: 1920px) {
    .area_length {
        top: 1.7%;
    }
}
</style>