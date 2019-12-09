<template>
    <div class="main" :style="{height:fullHeight+'px'}">
        <!--Top标题-->
        <Row>
            <Col span="24">
            <div></div>
            <div id="top" class="topOpacity">
                <Row>
                    <Col span="14">
                    <span class="Icon"></span>
                    <div style="padding-top: 1vh;">
                        <img :src="mainName" style="height: 3vh">
                    </div>
                    </Col>
                    <Col span="6" offset="4">
                    <div style="float: right;padding-top: 0px;padding-right: 5px;">
                        <Dropdown divided @click.native="showAboutUs">
                            <a>
                                <Icon type="information-circled" color="#fff" title="关于我们" class="about"></Icon>
                            </a>
                        </Dropdown>
                        <showAboutUs v-bind="aboutUs"></showAboutUs>
                    </div>
                    <div style="float: right;padding-top: 1vmin;">
                        <Dropdown divided @click.native="logout">
                            <a>
                                <Icon type="power" color="#fff" class="power"></Icon>
                            </a>
                        </Dropdown>
                    </div>
                    </Col>
                </Row>
            </div>
            </Col>
        </Row>
        <br>
        <br>
        <br>
        <br>
        <br>
        <!--菜单内容-->
        <div class="modals">
            <Row type="flex" justify="center" align="top" class="code-row-bg">
                <!--运维管理 + 其他-->
                <Col span="6" offset="1">
                    <Row>
                        <h2 style="font-size: 2.4vmin">运维管理</h2>
                        <Col span="16">
                            <Button type="info" class="widthBtn btn-nenghao"
                                :disabled="findPath('/UM/virtualInspect')?false:true"
                                @click="goToMoudle({ path:'/UM/virtualInspect'})">
                                <div>
                                    <img src="../../assets/UM/virtualInspect.png" :width="imgWidth">
                                    <div :style="{fontSize:mySize}">虚拟巡检</div>
                                </div>
                            </Button>
                        </Col>
                        <Col span="8">
                            <Button class="normalBtn" :disabled="findPath('/UM/patrol/homePage')?false:true"
                                @click="goToMoudle({ path:'/UM/patrol/homePage'})" type="info">
                                <div>
                                    <img :width="imgWidth" src="../../assets/UM/巡检.png">
                                    <div :style="{fontSize:mySize}">巡检管理</div>
                                </div>
                            </Button>
                        </Col>
                        <Col span="8">
                            <Button class="normalBtn" type="info" :disabled="findPath('/UM/defect/query')?false:true"
                                @click="goToMoudle({ path:'/UM/defect/query'})">
                                <div>
                                    <img :width="imgWidth" src="../../assets/UM/defect.png">
                                    <div :style="{fontSize:mySize}">缺陷管理</div>
                                </div>
                            </Button>
                        </Col>
                        <Col span="8">
                            <Button class="normalBtn" type="info" :disabled="findPath('/UM/overhaul/query')?false:true"
                                @click="goToMoudle({ path:'/UM/overhaul/query'})">
                                <div>
                                    <img :width="imgWidth" src="../../assets/UM/检修.png">
                                    <div :style="{fontSize:mySize}">检修管理</div>
                                </div>
                            </Button>
                        </Col>
                        <Col span="8">
                            <Button class="normalBtn" type="info" :disabled="findPath('/UM/equipment/homePage')?false:true"
                                @click="goToMoudle({ path:'/UM/equipment/homePage'})">
                                <div>
                                    <img :width="imgWidth" src="../../assets/UM/equipments.png">
                                    <div :style="{fontSize:mySize}">设备管理</div>
                                </div>
                            </Button>
                        </Col>
                    </Row>
                    <Row>
                        <h2 style="font-size: 2.4vmin; margin-top: 1vmin">其他管理</h2>
                        <Col span="8">
                            <Button type="info" class="normalBtn btn-jiqiren" :disabled="findPath('/VMMain2')?false:true"
                                @click="goToMoudle({ path:'/VMMain2'})">
                                <div>
                                    <img src="../../assets/UM/空间.png" :width="imgWidth">
                                    <div :style="{fontSize:mySize}">可视化平台</div>
                                </div>
                            </Button>
                        </Col>
                        <Col span="8">
                            <Button class="normalBtn" type="info" :disabled="findPath('/UM/tunnelDocuments/list')?false:true"
                                @click="goToMoudle({ path:'/UM/tunnelDocuments/list/1.1'})">
                                <div>
                                    <img src="../../assets/UM/documents.png" :width="imgWidth">
                                    <div :style="{fontSize:mySize}">资料管理</div>
                                </div>
                            </Button>
                        </Col>
                        <Col span="8">
                            <Button class="normalBtn btn-quexian" type="info" :disabled="findPath('/UM/projectSurvey')?false:true"
                                @click="goToMoudle({ path:'/UM/projectSurveye'})">
                                <div>
                                    <img src="../../assets/UM/enterGallery.png" :width="imgWidth">
                                    <div :style="{fontSize:mySize}">项目概况</div>
                                </div>
                            </Button>
                        </Col>
                    </Row>
                </Col>

                <!--运营和应急-->
                <Col span="4" offset="1">
                    <Row>
                        <h2 style="font-size: 2.4vmin">运营管理</h2>
                        <Col span="12">
                            <Button type="info" class="normalBtn" :disabled="findPath('/UM/tunnelCustomer/list')?false:true"
                                @click="goToMoudle({ path:'/UM/tunnelCustomer/list'})">
                                <div>
                                    <img src="../../assets/UM/customer.png" :width="imgWidth">
                                    <div :style="{fontSize:mySize}">企业客户</div>
                                </div>
                            </Button>
                        </Col>
                        <Col span="12">
                            <Button class="normalBtn btn-quexian" :disabled="findPath('/UM/TunnelEnergy/homepage')?false:true"
                                @click="goToMoudle({path: '/UM/TunnelEnergy/homepage'})">
                                <div>
                                    <img src="../../assets/UM/energy.png" :width="imgWidth">
                                    <div :style="{fontSize:mySize}">能耗管理</div>
                                </div>
                            </Button>
                        </Col>
                        <Col span="12">
                            <Button type="info" class="normalBtn" :disabled="findPath('/UM/tunnelContract/list')?false:true"
                                @click="goToMoudle({ path:'/UM/tunnelContract/list'})">
                                <div>
                                    <img src="../../assets/UM/contract.png" :width="imgWidth">
                                    <div :style="{fontSize:mySize}">合同管理</div>
                                </div>
                            </Button>
                        </Col>
                        <Col span="12">
                            <Button type="info" class="normalBtn" :disabled="findPath('/UM/OperatingSpace/tunnel')?false:true"
                                @click="goToMoudle({ path:'/UM/OperatingSpace/tunnel'})">
                                <div>
                                    <img src="../../assets/UM/空间.png" :width="imgWidth">
                                    <div :style="{fontSize:mySize}">空间管理</div>
                                </div>
                            </Button>
                        </Col>
                    </Row>
                    <Row>
                        <h2 style="font-size: 2.4vmin; margin-top: 1vmin">应急管理</h2>
                        <Col span="12">
                            <Button type="info" class="normalBtn" :disabled="findPath('/UM/relatedUnits/query')?false:true"
                                @click="goToMoudle({ path:'/UM/relatedUnits/query'})">
                                <div>
                                    <img src="../../assets/UM/faultyEquipment.png" :width="imgWidth">
                                    <div :style="{fontSize:mySize}">基础信息</div>
                                </div>
                            </Button>
                        </Col>
                        <Col span="12">
                            <Button type="info" class="normalBtn" :disabled="findPath('/UM/plans/detial')?false:true"
                                @click="goToMoudle({ path:'/UM/plans/detial/VentilationPlanSOP_KEY'})">
                                <div>
                                    <img src="../../assets/UM/消防.png" :width="imgWidth">
                                    <div :style="{fontSize:mySize}">预案管理</div>
                                </div>
                            </Button>
                        </Col>
                    </Row>
                </Col>

                <!--综合监控-->
                <Col span="6" offset="1">
                    <h2 style="font-size: 2.4vmin">综合监控</h2>
                    <Col span="8">
                        <Button class="btn-xunjian normalBtn" type="info" :disabled="findPath('/UM/TunnelEnvironment')?false:true"
                            @click="goToMoudle({ path:'/UM/TunnelEnvironment'})">
                            <div>
                                <img src="../../assets/UM/environment.png" :width="imgWidth">
                                <div :style="{fontSize:mySize}">环境监测</div>
                            </div>
                        </Button>
                    </Col>
                    <Col span="8">
                        <Button class="normalBtn" type="info" :disabled="findPath('/UM/TunnelSafety')?false:true"
                            @click="goToMoudle({ path:'/UM/TunnelSafety'})">
                            <div>
                                <img :width="imgWidth" src="../../assets/UM/safe.png">
                                <div :style="{fontSize:mySize}">安防监测</div>
                            </div>
                        </Button>
                    </Col>
                    <Col span="8">
                        <Button class="normalBtn" type="info" :disabled="findPath('/UM/TunnelElectromechanical')?false:true"
                            @click="goToMoudle({ path:'/UM/TunnelElectromechanical'})">
                            <div>
                                <img :width="imgWidth" src="../../assets/UM/defect.png">
                                <div :style="{fontSize:mySize}">机电监控</div>
                            </div>
                        </Button>
                    </Col>
                    <Col span="16">
                        <Button class="normalBtn btn-shipin bigBtn" type="info" :disabled="findPath('/UM/VideoMonitoring/details')?false:true"
                            @click="goToMoudle({ path:'/UM/VideoMonitoring/details/1'})">
                            <div>
                                <img src="../../assets/UM/monitor.png" :width="imgWidth">
                                <div :style="{fontSize:mySize}">视频监控</div>
                            </div>
                        </Button>
                    </Col>
                    <Col span="8">
                        <Button class="btn-yonhu normalBtn" :disabled="findPath('/UM/PersonnelPosition/Visitors')?false:true"
                            @click="goToMoudle({ path:'/UM/PersonnelPosition/Visitors'})">
                            <div>
                                <img :width="imgWidth" src="../../assets/UM/position.png">
                                <div :style="{fontSize:mySize}">人员定位</div>
                            </div>
                        </Button>
                    </Col>
                    <Col span="8">
                        <Button class="normalBtn" :disabled="findPath('/UM/DataAnalysis/QueryData')?false:true"
                            @click="goToMoudle({ path:'/UM/DataAnalysis/QueryData'})" type="info">
                            <div>
                                <img :width="imgWidth" src="../../assets/UM/dataAnalysis.png">
                                <div :style="{fontSize:mySize}">数据分析</div>
                            </div>
                        </Button>
                    </Col>
                </Col>

                <!--最右侧时间-->
                <Col span="3" offset="1">
                <Row style="margin-top: 16vh;">
                    <Col span="24">
                    <p class="time">
                        <Icon type="ios-time-outline"></Icon>
                        <span class="curTime">{{nowTime}}</span>
                        <span style="font-size: 1.8vmin;font-family: 7LED;">{{timeSection}}</span>
                    </p>
                    </Col>
                    <Col span="24">
                    <p class="date">{{nowDate}}</p>
                    </Col>
                    <Col span="24" style="margin-top: 4vh">
                    <span style="font-size: 2.4vmin">
                        安全运营
                        <span class="days">{{days.total}}</span>天
                    </span>
                    </Col>
                </Row>
                </Col>
            </Row>
        </div>
    </div>
</template>

<script>
    import {
        getSimpleDate
    } from "../../scripts/DateFormat.js";
    import {
        LoginService
    } from "../../services/loginService.js";
    import {
        TunnelService
    } from "../../services/tunnelService";
    import showAboutUs from "../../components/Common/Modal/ShowAboutUs";
    import ImageFromUrl from "../../components/Common/ImageFromUrl";
    import topBg from "../../assets/VM/footLine.png";
    import Cookies from "js-cookie";
    export default {
        name: "UmMain",
        data() {
            return {
                topBg: topBg,
                aboutUs: {
                    show: {
                        state: false
                    }
                },
                timerChange: null,
                fullHeight: window.innerHeight,
                nowTime: null,
                nowDate: null,
                timeSection: null,
                mainName: require("../../assets/UM/maxTunnelHome.png"),
                mySize: "1.4rem",
                smSize: "16px",
                imgWidth: "52px",
                days: {
                    total: null,
                    safe: null
                }
            };
        },
        computed: {
            routers: {
                get() {
                    return this.$store.getters.permission_routers;
                }
            }
        },
        methods: {
            goToMoudle: function (path) {
                if (
                    String(this.$router.history.current.fullPath).indexOf(
                        String(path.path)
                    ) < 0
                ) {
                    sessionStorage.setItem("selectedName", "");
                    sessionStorage.setItem("refreshAddress", "")
                    if (path.path == "/VMMain") {
                        const {
                            href
                        } = this.$router.resolve({
                            name: "可视化主页"
                        });
                        window.open(href, "_blank");
                    } else {
                        this.$router.push(path);
                    }
                }
            },
            showAboutUs() {
                this.aboutUs.show.state = !this.aboutUs.show.state;
            },
            init() {
                let _this = this;
                TunnelService.getVmRunMessage().then(
                    result => {
                        _this.days.total = result.total;
                        _this.days.safe = result.safe;
                    },
                    error => {
                        _this.Log.info(error);
                    }
                );
            },
            logout: function () {
                var _this = this;
                this.$Modal.confirm({
                    title: "注销",
                    width: "25vw",
                    content: "<p>确认退出吗?</p>",
                    onOk: () => {
                        this.$store
                            .dispatch("LogOut")
                            .then(() => {
                                sessionStorage.removeItem("UMUser");
                                localStorage.removeItem('Authorization')
                                localStorage.removeItem('userName')
                                localStorage.removeItem('password')
                                _this.$router.push("/UMlogin");
                            })
                            .catch(() => {
                                _this.$Message.error(msg);
                            });
                    },
                    onCancel: () => {
                        // this.$Message.info('Clicked cancel');
                    }
                });
            },

            updateTime() {
                // this.nowTime =
                // 	new Date().getHours() < 12
                // 		? new Date().format("hh:mm:ss")
                // 		: new Date(
                // 				new Date().getTime() - 60 * 60 * 1000 * 12
                // 		  ).format("hh:mm:ss");
                this.nowTime = new Date().format("hh:mm:ss");
                // this.timeSection = new Date().getHours() > 12 ? "PM" : "AM";
                this.nowDate = getSimpleDate();
                this.fullHeight = window.innerHeight;
                if (window.innerWidth <= 1400) {
                    this.mySize = "1.8vmin";
                    this.smSize = "10px";
                    this.imgWidth = "50vw";
                } else {
                    if (window.innerWidth <= 1920) {
                        this.mySize = "1.5vmin";
                        this.smSize = "16px";
                        this.imgWidth = "50vw";
                    } else {
                        this.mySize = "1.5vmin";
                        this.smSize = "16px";
                        this.imgWidth = "100vw";
                    }
                }
            },
            findPath(url) {
                let tempUrl = url.split("/").filter(a => a != "");
                let len = tempUrl.length;
                let result = false;
                //根据vux中的权限表盘的用户是否有菜单权限
                this.routers.forEach(b => {
                    let index = 0,
                        curItem = {};
                    if (
                        b.path.replace("/", "").toLowerCase() ==
                        tempUrl[index].toLowerCase()
                    ) {
                        curItem = b.children;
                        index++;
                        result = true;
                        while (index < len && curItem && result) {
                            let temp = curItem.filter(
                                c =>
                                c.path
                                .toLowerCase()
                                .indexOf(tempUrl[index].toLowerCase()) > -1
                            );
                            if (temp.length > 0) {
                                if (index < len - 1 && temp[0].children) {
                                    curItem = temp[0].children;
                                }
                                index++;
                                result = true;
                            } else {
                                result = false;
                            }
                        }
                    }
                });
                return result;
            }
        },
        mounted() {
            var _this = this;
            _this.updateTime();
            _this.init();
            this.timerChange = setInterval(_this.updateTime, 1000);
        },
        beforeDestroy() {
            clearInterval(this.timerChange); //在vue实例销毁钱，清除我们的定时器
        },
        components: {
            ImageFromUrl,
            showAboutUs
        }
    };
</script>


<style scoped>
    .ivu-col-span-8,
    .ivu-col-span-12,
    .ivu-col-span-16 {
        margin-top: 1vh;
    }

    .time {
        font-size: 3.4vmin;
        text-align-last: justify;
    }

    .date {
        font-size: 1.8vmin;
        color: #f59e48;
        text-align-last: justify;
    }

    .normalBtn {
        width: 7vw;
        height: 7vw;
        margin-top: 0.2vmin;
        background-color: rgba(255, 255, 255, 0.5);
        box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
    }

    .normalBtn:focus,
    .normalBtn:hover {
        background-color: rgba(255, 255, 255, 0.7);
    }

    .normalBtn:active {
        background-color: rgba(255, 255, 255, 0.86);
    }

    .widthBtn {
        width: 14.6vw;
        height: 7vw;
        margin-top: 0.2vmin;
        box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
    }

    .bigBtn {
        width: 14.6vw;
        height: 14.6vw;
        margin-top: 0.2vmin;
        box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
    }

    .days {
        margin-left: 0.6vh;
        margin-right: 0.6vh;
        color: #99ff23;
        font-family: UnidreamLED;
        font-size: 4vmin;
    }

    .curTime {
        font-family: UnidreamLED;
        /* margin-left: 1vmin; */
        font-size: 5vmin;
    }

    .main {
        background-image: url("../../assets/UM/UMMainbg2.png");
        color: #fff;
        background-size: 100% 100%;
        -moz-background-size: 100% 100%;
        -webkit-background-size: 100% 100%;
    }

    .topOpacity {
        background-color: rgba(0, 0, 0, 0.2);
    }

    .btn-xunjian {
        color: #fff;
        background-color: #df5631;
        border-color: #d58512;
        border-width: thin;
    }

    .btn-xunjian:focus,
    .btn-xunjian:hover {
        color: #fff;
        background-color: #cb500a;
        background-position: 0 -15px;
    }

    .btn-xunjian:active {
        color: #fff;
        background-color: #d83c38;
        border-color: #d58512;
    }

    .btn-quexian {
        color: #fff;
        background-color: #753be5;
        border-color: #8e6ae5;
        border-width: thin;
    }

    .btn-quexian:focus,
    .btn-quexian:hover {
        color: #fff;
        background-color: #715fc8;
        background-position: 0 -15px;
    }

    .btn-quexian:active {
        color: #fff;
        background-color: #7123b9;
        border-color: #8e6ae5;
    }

    .btn-yonhu {
        color: #fff;
        background-color: #d500d1;
        border-color: #d50076;
        border-width: thin;
    }

    .btn-yonhu:focus,
    .btn-yonhu:hover {
        color: #fff;
        background-color: #ef00df;
        background-position: 0 -15px;
    }

    .btn-yonhu:active {
        color: #fff;
        background-color: #b70071;
        border-color: #d50076;
    }

    .btn-nenghao {
        color: #fff;
        background-color: #323aed;
        border-color: #3a20ed;
        border-width: thin;
    }

    .btn-nenghao:focus,
    .btn-nenghao:hover {
        color: #fff;
        background-color: #3a1ac7;
        background-position: 0 -15px;
    }

    .btn-nenghao:active {
        color: #fff;
        background-color: #3a5aff;
        border-color: #3a20ed;
    }

    .btn-tonxing {
        color: #fff;
        background-color: #d064c2;
        border-color: #d02cc2;
        border-width: thin;
    }

    .btn-tonxing:focus,
    .btn-tonxing:hover {
        color: #fff;
        background-color: #fa4586;
        background-position: 0 -15px;
    }

    .btn-tonxing:active {
        color: #fff;
        background-color: #d000b7;
        border-color: #d02cc2;
    }

    .btn-shipin {
        color: #fff;
        background-color: #f88252;
        border-color: #f83f58;
        border-width: thin;
    }

    .btn-shipin:focus,
    .btn-shipin:hover {
        color: #fff;
        background-color: #f83f52;
        background-position: 0 -15px;
    }

    .btn-shipin:active {
        color: #fff;
        background-color: #f80b6b;
        border-color: #f83f58;
    }

    .btn-jiqiren {
        color: #fff;
        background-color: #e5b23b;
        border-color: #e57f3b;
        border-width: thin;
    }

    .btn-jiqiren:focus,
    .btn-jiqiren:hover {
        color: #fff;
        background-color: #aeda3b;
        background-position: 0 -15px;
    }

    .btn-jiqiren:active {
        color: #fff;
        background-color: #ae993b;
        border-color: #e57f3b;
    }

    .btn-guanxian {
        color: #fff;
        background-color: #089fb2;
        border-color: #519fb2;
        border-width: thin;
    }

    .btn-guanxian:focus,
    .btn-guanxian:hover {
        color: #fff;
        background-color: #25c8b2;
        background-position: 0 -15px;
    }

    .btn-guanxian:active {
        color: #fff;
        background-color: #258486;
        border-color: #519fb2;
    }

    .Icon {
        height: 4vh;
        width: 10vw;
        background-image: url("../../assets/VM/MaxviewLogo.png");
        background-size: 100% 100%;
        -moz-background-size: 100% 100%;
        -webkit-background-size: 100% 100%;
        float: left;
    }

    .power {
        font-size: 4vmin;
    }

    .about {
        font-size: 2vmin;
    }

    @media (max-width: 1921px) {
        .modals {
            width: 92vw;
            margin-left: 4vw;
            margin-top: 4vh;
        }
    }

    @media (min-width: 1921px) {
        .modals {
            width: 92vw;
            margin-left: 4vw;
            margin-top: 10vh;
        }
    }
</style>