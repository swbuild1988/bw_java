<template>
    <div class="whole">
        <Row class="body">
            <Button type="primary" class="back" @click="back">返回</Button>
            <Col span="8">
                <Card class="persons">
                    <p slot="title">
                        <Icon type="android-contact"></Icon>
                        {{ personnelInfo.name }}
                    </p>
                    <a href="#" slot="extra" @click.prevent="changePosType">历史轨迹</a>
                    <ul>
                        <li>所属公司：{{personnelInfo.companyName}}</li>
                        <li>手机号：{{ personnelInfo.telphone }}</li>
                        <li>身份证号：{{ personnelInfo.identityNO }}</li>
                        <br>
                        <li>入廊目的：{{ personnelInfo.actionName }}</li>
                        <li>入廊时间：{{ personnelInfo.preTime }}</li>
                        <li>行程状态：{{ personnelInfo.processStatus }}</li>
                    </ul>
                    <!-- <div class="options">
						<Button type="primary" @click="route.queryShow = true">历史轨迹</Button>
						<Button type="primary" class="call">通话</Button>
                    </div>-->
                </Card>
                <div class="cameras">
                    <Row>
                        <Col span="1" class="slipContent">
                            <Icon
                                type="chevron-left"
                                :class="['slipLeft',{'disabled': cameras.curPage == 1},{'clicked' : clicked.prev && cameras.curPage != 1}]"
                                @click.native="pageChange('prev')"
                                @mousedown.native="down('prev')"
                                @mouseup.native="up('prev')"
                            ></Icon>
                        </Col>
                        <Col span="22">
                            <div class="videos">
                                <h1
                                    v-if="cameras.nodata"
                                    style="text-align: center;margin-top: 2vmin;"
                                >暂无视频</h1>
                                <Row>
                                    <Col
                                        span="12"
                                        v-for="(item,index) in cameras.showList"
                                        :key="item.id"
                                        class="monitors"
                                    >
                                        <video-component
                                            v-bind:video="item"
                                            v-bind:id="'camera'+item.id"
                                            :index="index"
                                        ></video-component>
                                    </Col>
                                </Row>
                            </div>
                        </Col>
                        <Col span="1" class="slipContent">
                            <Icon
                                type="chevron-right"
                                :class="['slipRight',{'disabled' : cameras.curPage == cameras.totalPage},
						   {'clicked' : clicked.next && cameras.curPage != cameras.totalPage}]"
                                @click.native="pageChange('next')"
                                @mousedown.native="down('next')"
                                @mouseup.native="up('next')"
                            ></Icon>
                        </Col>
                    </Row>
                </div>
            </Col>
            <Col span="15" offset="1" class="view">
                <div class="query" v-if="route.queryShow">
                    <Row>
                        <Col span="12">
                            <DatePicker
                                type="datetimerange"
                                placeholder="请选择起始时间"
                                @on-change="getRouteList"
                                v-model="route.time"
                            ></DatePicker>
                        </Col>
                        <Col span="11" offset="1">
                            <Select v-model="route.curRoute" @on-change="getCurRoute(0)">
                                <Option
                                    v-for="route in route.list"
                                    :key="route.id"
                                    :value="route.id"
                                >{{ route.name }}</Option>
                            </Select>
                        </Col>
                    </Row>
                </div>
                <!-- <sm-viewer id="personnelPositionSMViewer" :cameraPosition="VMConfig.CAMERA"  :personnelPosition="personnelPosition" @onload="onload" style="height:74vh">
                </sm-viewer>-->
                <sm-viewer :cameraPosition="VMConfig.CAMERA" ref="positionDetail"></sm-viewer>
            </Col>
        </Row>
    </div>
</template>
<script>
import { personnelPositionService } from "../../../../services/personnelPositionService";
import SmViewer from "../../../../components/Common/3D/overLook3DViewer";
import { TunnelService } from "../../../../services/tunnelService";
import { VideoService } from "../../../../services/videoService";
//   import { setViewAngle ,bubble ,doSqlQuery , addBillboard,processFailed,getEntitySet,switchShowEntity,getEntityProperty  } from '../../../../scripts/commonFun'
import VideoComponent from "../../../../components/Common/Video/VideoComponent";
import Vue from "vue";
export default {
    data() {
        return {
            personnelInfo: {
                name: null,
                companyName: null,
                telphone: null,
                identityNO: null,
                actionName: null,
                preTime: null,
                processStatus: null
            },
            // personnelPosition: {
            // 	openPosition: true,
            // 	isShow: true,
            // 	refreshTime: 10000
            // },
            refresh: {
                id: null,
                time: 60000
            },
            cameras: {
                allList: [],
                showList: [],
                nodata: false,
                totalPage: 1,
                curPage: 1
            },
            clicked: {
                prev: false,
                next: false
            },
            route: {
                queryShow: false,
                curRoute: null,
                time: null,
                list: []
            },
            curPersonId: null,
            polylineAttr: {
                viewer: Vue.prototype.$viewer,
                id: null,
                type: "historyRoute"
            },
            queryVideos: {
                longitude: null,
                latitude: null,
                height: null
            }
        };
    },
    components: {
        SmViewer,
        VideoComponent
    },
    watch: {
        $route: function() {
            // this.$refs.positionDetail.stopPersonnelPosition();
        }
    },
    mounted() {
        // this.$refs.positionDetail.startPersonnelPosition();
        this.curPersonId = this.$route.params.userId;
        this.getDetails();
        this.refreshData();
    },
    methods: {
        getDetails() {
            let _this = this;
            personnelPositionService.getBindDetails(this.curPersonId).then(
                result => {
                    _this.personnelInfo.name = result.list[0].name;
                    _this.personnelInfo.companyName = result.company.name;
                    _this.personnelInfo.telphone = result.list[0].telphone;
                    _this.personnelInfo.identityNO = result.list[0].identityNO;
                    _this.personnelInfo.preTime = new Date(
                        result.preTime
                    ).format("yyyy-MM-dd hh-mm-ss");
                    _this.personnelInfo.processStatus = result.processStatus;
                    _this.personnelInfo.actionName = result.actionName;
                    _this.getRouteList();
                    if (_this.personnelInfo.processStatus === "确认出廊") {
                        _this.showCurPoint();
                    }
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        getCameraList() {
            let _this = this;
            VideoService.getNearbyVideos(this.queryVideos).then(result => {
                if (result.length !== 0) {
                    _this.cameras.nodata = false;
                    _this.cameras.allList = result;
                    _this.cameras.showList = result.slice(0, 4);
                    _this.cameras.totalPage = Math.ceil(result.length / 4);
                } else {
                    _this.cameras.allList = [];
                    _this.cameras.showList = [];
                    _this.cameras.nodata = true;
                }
            });
        },
        showCurPoint() {
            let params = {
                Id: this.curPersonId
            };
            this.$refs.positionDetail.entityFilter(params);
        },
        pageChange(type) {
            switch (type) {
                case "prev":
                    if (this.cameras.curPage > 1) {
                        this.cameras.curPage -= 1;
                    }
                    break;
                case "next":
                    if (this.cameras.curPage < this.cameras.totalPage) {
                        this.cameras.curPage += 1;
                    }
                    break;
            }
            this.cameras.showList = this.cameras.allList.slice(
                (this.cameras.curPage - 1) * 4,
                this.cameras.curPage * 4
            );
        },
        down(type) {
            this.clicked[type] = true;
        },
        up(type) {
            this.clicked[type] = false;
        },
        changePosType() {
            this.route.queryShow = !this.route.queryShow;
            if (!this.route.queryShow) {
                this.getRouteList();
            }
        },
        getCurRoute(routeInfo) {
            let params = {};
            if (routeInfo === 0) {
                let curRoutePos = this.route.list.find(item => {
                    return item.id == this.route.curRoute;
                });
                params = {
                    polyline: {
                        position: curRoutePos.position,
                        width: 3,
                        color: "yellow"
                    }
                };
            } else {
                params = {
                    polyline: {
                        position: routeInfo.positions,
                        // 	position: [
                        //     {"Lon":"112.49687675857342","Lat":"37.70371418429163"},
                        // 	{"Lon":"112.4891505280533","Lat":"37.71366062508612"}
                        // ],
                        width: 3,
                        color: "yellow"
                    }
                };
            }
            this.removePolyline();

            this.polylineAttr.id =
                routeInfo === 0 ? this.route.curRoute : routeInfo.id;
            this.$refs.positionDetail.addPolylineEntity(
                {
                    id: this.polylineAttr.id,
                    messageType: this.polylineAttr.type
                },
                params
            );
        },
        removePolyline() {
            let { polylineAttr } = this;

            if (!!polylineAttr.id) {
                let entitys = polylineAttr.viewer.entities.getById(
                    polylineAttr.id
                );

                if (Array.isArray(entitys) && entitys.length) {
                    entitys.forEach(entity => this.removeCommonEntity);
                } else {
                    this.removeCommonEntity(entitys);
                }
            }
        },
        removeCommonEntity(entitys) {
            let { polylineAttr } = this;

            return (
                entitys.messageType == polylineAttr.type &&
                polylineAttr.viewer.entities.removeById(polylineAttr.id)
            );
        },
        getRouteList() {
            let params = {
                staffId: this.curPersonId,
                equipmentId: null,
                startTime: this.route.time
                    ? new Date(this.route.time[0]).getTime()
                    : null,
                endTime: this.route.time
                    ? new Date(this.route.time[1]).getTime()
                    : null
            };

            let _this = this;
            personnelPositionService.getRoutes(params).then(
                result => {
                    if (_this.route.queryShow) {
                        _this.route.list = [];
                        result.forEach((route, index) => {
                            let temp = {};
                            temp.id = route.id;
                            temp.name = "轨迹" + (index + 1);
                            temp.position = [];
                            route.list.forEach(point => {
                                let position = {};
                                position.Lon = point.longitude;
                                position.Lat = point.latitude;
                                temp.position.push(position);
                            });
                            _this.route.list.push(temp);
                        });
                        _this.route.curRoute = result[result.length - 1].id;
                    } else {
                        let routeIndex = result.length - 1;
                        let temp = {};
                        temp.id = result[routeIndex].id;
                        temp.positions = [];
                        result[routeIndex].list.forEach(point => {
                            let position = {};
                            position.Lon = point.longitude;
                            position.Lat = point.latitude;
                            temp.positions.push(position);
                        });
                        _this.getCurRoute(temp);
                    }

                    if (_this.personnelInfo.processStatus === "确认出廊") {
                        let routeIndex = result.length - 1;
                        let index = result[routeIndex].list.length - 1;
                        for (let item in _this.queryVideos) {
                            _this.queryVideos[item] =
                                result[routeIndex].list[index][item];
                        }
                        _this.getCameraList();
                    }
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        back() {
            this.removePolyline();
            this.$router.push("/UM/PersonnelPosition/visitors");
        },
        refreshData() {
            let _this = this;
            _this.refresh.id = setInterval(() => {
                _this.getDetails();
            }, _this.refresh.time);
        }
    },
    beforeDestroy() {
        clearInterval(this.refresh.id);
        this.refresh.id = null;
    }
};
</script>
<style scoped>
.whole {
    min-height: 100%;
    position: relative;
    background-color: white;
    overflow: hidden;
}
.body {
    margin: 4vmin 2vmin;
    position: relative;
}
.back {
    position: absolute;
    right: 0vmin;
    top: -2.8vmin;
}
.view {
    border: 1px solid #b3b0b0;
    box-shadow: 0 0 1.3vmin 0.3vmin rgba(0, 0, 0, 0.5);
    height: 74vh;
    margin-top: 2.6vh;
    position: relative;
}
.query {
    position: absolute;
    top: 1vmin;
    left: 30%;
    width: 48vmin;
    height: 4vmin;
    z-index: 10;
}
.query >>> .ivu-input {
    height: 3.2vmin;
    font-size: 1.28vmin;
}
.options {
    position: absolute;
    right: 0;
    bottom: -0.8vmin;
}
.persons {
    height: 30vh;
    width: 100%;
}
.persons .ivu-card-head p {
    font-size: 1.8vmin;
    height: 2vmin;
    line-height: 2vmin;
}
.persons .ivu-card-extra a {
    font-size: 1.4vmin;
}
.persons ul {
    list-style-type: none;
    font-size: 1.6vmin;
}
.cameras {
    margin-top: 1.2vmin;
    height: 46vh;
    background-color: #f3e7d1;
    border-radius: 4px;
}
.slipContent {
    position: relative;
    width: 4%;
    height: 46vh;
}
.slipLeft {
    position: absolute;
    top: 46%;
    left: 4px;
    cursor: pointer;
    color: #656464;
    font-size: 2.88vmin;
}
.slipRight {
    position: absolute;
    top: 46%;
    right: 4px;
    cursor: pointer;
    color: #656464;
    font-size: 2.88vmin;
}
.disabled {
    display: none;
}
.clicked {
    color: rgb(0, 228, 236);
}
.monitors {
    padding: 1.4vmin 0.6vmin 0vmin 0.6vmin;
    height: 22.4vh;
}
</style>