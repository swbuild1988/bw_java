<template>
    <div class="Main">
        <div class="mapTop">
            <row class="navigation_bar" type="flex" justify="center" align="bottom">
                <Col span="1">
                    <Dropdown divided @click.native="homeSwitch">
                        <a>
                            <img
                                class="To_home"
                                src="../../../assets/VM/home.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col>
                <Col span="1" offset="1" 
                    v-for="item in angleLists"
                    :key="item.id">
                    <Dropdown
                        divided
                        @click.native="switchAngle(item.id)"
                    >
                        <a>
                            <img
                                :class="'angle_'+item.id"
                                src="../../../assets/VM/angle.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col>
                <Col span="1" offset="1">
                    <Dropdown
                        divided
                        @click.native="personnelPosition.isShow = !personnelPosition.isShow"
                    >
                        <a>
                            <img
                                class="Personnel"
                                src="../../../assets/VM/personnel.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col>
                <!-- <Col span="1" offset="1">
                    <Dropdown divided @click.native="show.ges = !show.ges">
                        <a>
                            <img
                                class="Gas"
                                src="../../../assets/VM/genue.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col>
                <Col span="1" offset="1">
                    <Dropdown
                        divided
                        @click.native="show.showNonCleanedCount = !show.showNonCleanedCount"
                    >
                        <a>
                            <img
                                class="Alarm_number"
                                src="../../../assets/VM/alarm.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col>
                <Col span="1" offset="1">
                    <Dropdown
                        divided
                        @click.native="defectPosition.isShow = !defectPosition.isShow"
                    >
                        <a>
                            <img
                                class="Flaw_query"
                                src="../../../assets/VM/flaw.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col>
                <Col span="1" offset="1">
                    <Dropdown
                        divided
                        @click.native="eventsPosition.openPosition = !eventsPosition.openPosition"
                    >
                        <a>
                            <img
                                class="Events"
                                src="../../../assets/VM/event.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col>
                <Col span="1" offset="1">
                    <Dropdown divided @click.native="angleSwitchProp.show = !angleSwitchProp.show">
                        <a>
                            <img
                                class="Emergencies"
                                src="../../../assets/VM/emergencies.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col>
                <Col span="1" offset="1">
                    <Dropdown divided @click.native="unitsPosition.isShow = !unitsPosition.isShow">
                        <a>
                            <img
                                class="Unit_query"
                                src="../../../assets/VM/address_book.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col> -->
                <Col span="1" offset="1">
                    <Dropdown divided @click.native="jumpUMLogin">
                        <a>
                            <img
                                class="Management"
                                src="../../../assets/VM/pageLinkage.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col>
                <Col span="1" offset="1">
                    <Dropdown
                        divided
                        @click.native="show.showControlPanel = !show.showControlPanel"
                    >
                        <a>
                            <img
                                class="Fly"
                                src="../../../assets/VM/controlPanel.png"
                                height="100%"
                                width="100%"
                            >
                        </a>
                    </Dropdown>
                </Col>
            </row>
            <row justify="center" type="flex">
                <img src="../../../assets/VM/footLine.png" height="8px" width="100%">
            </row>
            <div class="tooltip">
                <div
                    v-for="(item,index) in divAttrList"
                    :class="item.className"
                    :style="{ opacity:item.show }"
                    :key="index"
                >
                    <span>{{ item.text }}</span>
                </div>
            </div>
            <angle-select-modal v-if="angleSwitchProp.show"></angle-select-modal>
        </div>
        <div class="test"></div>
        <!--<temperature class="maptemperature"></temperature>-->
        <transition :enter-active-class="enterClass" :leave-active-class="leaveClass">
            <map-gauges v-show="show.ges"></map-gauges>
        </transition>
        <transition :enter-active-class="enterClass" :leave-active-class="leaveClass">
            <alarm-count v-show="show.showNonCleanedCount"></alarm-count>
        </transition>
        <transition :enter-active-class="enterClass" :leave-active-class="leaveClass">
            <plan-process v-show="show.showPlan" @showPlan="showPlan"></plan-process>
        </transition>
        <transition :enter-active-class="enterClass" :leave-active-class="leaveClass">
            <move-control prefixCls="MapLLPanel" v-show="show.showControlPanel"></move-control>
        </transition>

        <sm-viewer
            ref="smViewer"
            :cameraPosition="camera"
            :personnelPosition="personnelPosition"
            :unitsPosition="unitsPosition"
            :defectPosition="defectPosition"
            :searchCamera="searchCamera"
            :eventsPosition="eventsPosition"
            :openPlanPosition="openPlanPosition"
            :openVideoLinkage="true"
            :infoBox="false"
            :navigation="false"
            :openImageryProvider="true"
            :openSpinShow="false"
        ></sm-viewer>
    </div>
</template>

<script>
import SmViewer from "../../../components/Common/3D/overLook3DViewer";
import Temperature from "../../../components/VM/VMBodyCenter/temperatureBox";
import MapGauges from "../../../components/VM/VMBodyCenter/gauges";
import AlarmCount from "../../../components/VM/AlarmManage/NonCleanedCount";
import Vue from "vue";
import PlanProcess from "../../../components/VM/VMBodyCenter/PlanProcess";
import MoveControl from "../../../components/VM/VMBodyCenter/moveControlPanel";
import AngleSelectModal from "../../../components/Common/Modal/AngleSelectModel";
export default {
    data() {
        return {
            msg: "地图",
            id: "cesiumContainer",
            show: {
                ges: false,
                showNonCleanedCount: false,
                showPlan: false,
                showControlPanel: false
            },
            divAttrList: [
                { className: "To_home", text: "返回主页", show: 0 },
                { className: "angle_1", text: "古城大街角度", show: 0 },
                { className: "angle_2", text: "实验路角度", show: 0 },
                { className: "angle_3", text: "经三路角度", show: 0 },
                { className: "angle_4", text: "经二路角度", show: 0 },
                { className: "angle_5", text: "纬三路角度", show: 0 },
                // { className: "Gas", text: "气体检测", show: 0 },
                // { className: "Alarm_number", text: "告警总数", show: 0 },
                // { className: "Flaw_query", text: "缺陷查询", show: 0 },
                // { className: "Events", text: "重大事件", show: 0 },
                // { className: "Emergencies", text: "紧急事件", show: 0 },
                // { className: "Unit_query", text: "单位查询", show: 0 },
                { className: "Personnel", text: "人员定位", show: 0 },
                { className: "Management", text: "管理平台", show: 0 },
                { className: "Fly", text: "飞行功能", show: 0 }
            ],
            enterClass: "animated bounceInDown",
            leaveClass: "animated zoomOut",
            allVideos: [],
            searchCamera: {
                openSearch: false,
                isShow: true
            },
            unitsPosition: {
                openPosition: false,
                isShow: true
            },
            personnelPosition: {
                openPosition: false,
                isShow: true,
                refreshTime: 10000
            },
            defectPosition: {
                openPosition: false,
                isShow: true
            },
            openPlanPosition: {
                openPosition: true
            },
            eventsPosition: {
                openPosition: false
            },
            angleSwitchProp:{
                show:false
            }
        };
    },
    computed: {
        camera() {
            return this.VMConfig.CAMERA;
        },
        angleLists() {
            return this.MapAngleLists;
        }
    },
    components: {
        SmViewer,
        Temperature,
        MapGauges,
        AlarmCount,
        PlanProcess,
        MoveControl,
        AngleSelectModal
    },
    mounted() {
        this.init();
    },
    methods: {
        switchAngle(id) {
            let [curAngle] = this.angleLists.filter(angle => angle.id == id);
            // this.setViewAngle(curAngle.angle)
            this.curAngleId = curAngle.id;
            // alert(curAngle.id);
        },
        init() {
            let _this = this;
            // Vue.prototype.MQ.openMQ(this.MQCallback);
            _this.getVideos(); //调用视屏接口
            _this.eventListener();
            _this.$refs.smViewer.addIdentifierViewer(); //添加路线
        },
        homeSwitch() {
            let _this = this;

            _this.show.ges = false; //隐藏气体状态
            _this.show.showNonCleanedCount = false; //隐藏未清除告警条数
            _this.show.showControlPanel = false;
            _this.$refs.smViewer.setViewAngle(); //恢复到初始化视角
            _this.$refs.smViewer.hideAllEntitys(); //隐藏所有实体
        },
        getVideos() {
            let _this = this;

            _this.axios.get("/videos").then(result => {
                let { code, data } = result.data;

                if (code == 200) {
                    _this.allVideos.splice(0); //清空当前数组

                    data.forEach(video => {
                        _this.allVideos.push({
                            MOID: video.id,
                            latitude: video.latitude,
                            longitude: video.longitude,
                            height: video.height,
                            url: video.url
                        });
                    });
                }
            });
        },
        MQCallback(message) {
            let result = JSON.parse(message.body);

            if (result) {
                this.$Notice.warning({
                    title: "接收到一条新告警",
                    duration: 3
                });

                Vue.prototype.IM.addInformation("alarm", result);
                Vue.prototype.AM.addAlarm(result);
                this.$refs.smViewer.addAlarmEntity(result);
            }
        },
        sendPlan() {
            this.axios
                .post("/emplans/start", {
                    objectId: 203012401,
                    processValue: 4003
                })
                .then(result => {
                    let { msg, code, data } = result.data;
                    if (code == 200) {
                        console.log("200");
                    }
                });
        },
        showPlan(status) {
            let { show } = this;

            show.showPlan = status;
        },
        jumpUMLogin() {
            let _this = this;

            _this.$router.push({
                path: "UMMain"
            });
        },
        playFly() {
            this.$refs.smViewer.playFly();
        },
        stopFly() {
            this.$refs.smViewer.stopFly();
        },
        eventListener() {
            let $navigation_bar = document.getElementsByClassName(
                "navigation_bar"
            )[0];

            $navigation_bar.addEventListener("mousemove", e => {
                if (e.target.nodeName.toLowerCase() !== "img") return;

                this.divAttrList.forEach(item =>
                    item.className === e.target.className
                        ? (item.show = 1)
                        : (item.show = 0)
                );
            });

            $navigation_bar.addEventListener("mouseout", () =>
                this.divAttrList.forEach(item => (item.show = 0))
            );
        },
        switchAngle(id){

            let [ curAngle ]  = this.angleLists.filter(angle => angle.id == id);
            this.$refs.smViewer.setViewAngle(curAngle.angle)
        }
    },
    beforeDestroy() {
        Vue.prototype.MQ.closeMQ();
    }
};
</script>

<style scoped>
.Main {
    width: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
}

.mapTop {
    z-index: 1001;
    position: absolute;
    width: 65%;
    height: 8%;
    top: 2%;
    left: 50%;
    transform: translate(-55%);
}

.tooltip {
    display: grid;
    grid-template-columns: repeat(9, 1fr);
    color: #fff;
    text-align: center;
    margin-left: 12%;
    margin-right: 12%;
}
.tooltip > div {
    transition: all 1s;
}

.Main >>> .moveControlPanel {
    width: 21%;
}

/* 小屏幕（显示器，小于等于 1920px） */
@media (max-width: 1920px) {
    .Main >>> .ControlPanelTitleDiff {
        font-size: 1rem;
        margin: 0.6rem 0 0 0;
    }
    .Main >>> .vm-btn {
        width: 31%;
        font-size: 0.7rem;
    }
    .tooltip {
        grid-template-rows: 5%;
        font-size: 0.7rem;
    }
}

/* 大屏幕（显示器，大于等于 1920px） */
@media (min-width: 1921px) {
    .Main >>> .ControlPanelTitleDiff {
        font-size: 2rem;
        margin: 1.5rem 0 0 0;
    }
    .Main >>> .vm-btn {
        width: 35%;
        height: 70%;
        padding: 0.7rem 0 0 0;
        font-size: 1.5rem;
    }
    .tooltip {
        grid-template-rows: 8%;
        font-size: 1.3rem;
    }
}
</style>
<style>
.cesium-infoBox {
    background: rgba(3, 19, 36, 0.9);
    box-shadow: 0 0 10px 1px rgba(3, 19, 36, 0.9);
    top: 16%;
}

.cesium-infoBox-title {
    background: rgba(3, 19, 36, 1);
}
</style>