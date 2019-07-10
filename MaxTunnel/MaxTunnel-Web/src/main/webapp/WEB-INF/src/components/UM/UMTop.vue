<template>
    <Layout>
        <Header :style="{position: 'fixed', width: '100%',zIndex:999,padding:'0',color:'#1b1754'}">
            <Menu mode="horizontal" active-name="1" :style="{color:'#1b1754'}">
                <div class="layoutLeft">
                    <div class="layout-logo"></div>
                    <div class="layout-title">
                        <a @click="goToMoudle({ path: '/UMMain'})">
                            <img :src="titlePic" class="mainTitle">
                        </a>
                    </div>
                </div>
                <div class="layout-nav">
                    <div style="position: absolute;right: 18vmin;height:6%">
                        <MenuItem v-for="module in modules" :key="module.name" :name="module.name">
                            <Dropdown placement="bottom-start" trigger="click">
                                <div class="topBtn">
                                    <Icon :type="module.frontIcon"></Icon>
                                    <span>{{module.name}}</span>
                                    <Icon :type="module.endIcon"></Icon>
                                </div>
                                <DropdownMenu slot="list" :id="module.id">
                                    <DropdownItem
                                        v-for="child in module.children"
                                        :key="child.name"
                                        :name="child.name"
                                        divided
                                        :disabled="findPath(child.path)?false:true"
                                        @click.native="goToMoudle({ path: child.url})"
                                    >{{child.name}}</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                        </MenuItem>
                    </div>
                    <!-- 人物圆点 -->
                    <div class="select-dropdown">
                        <Dropdown>
                            <a href="javascript:void(0)" class="outBox">
                                <Badge :count="countNum">
                                    <Avatar
                                        :style="{background:'#f56a00'}"
                                        size="large"
                                        shape="circle"
                                        :src="umImg"
                                    ></Avatar>
                                </Badge>
                            </a>
                            <DropdownMenu slot="list">
                                <DropdownItem
                                    @click.native="goToMoudle({ path: '/UM/myNews/queryMyTask'})"
                                >我的消息</DropdownItem>
                                <!-- <DropdownItem @click.native="goToMoudle({ path: '/UM/myApplication/query'})">我的申请</DropdownItem> -->
                                <DropdownItem
                                    @click.native="goToMoudle({ path: '/UM/myTasks/query'})"
                                >我的任务</DropdownItem>
                                <DropdownItem @click.native="showAboutUs">关于我们</DropdownItem>
                                <showAboutUs v-bind="aboutUs"></showAboutUs>
                                <DropdownItem
                                    @click.native="goToMoudle({ path: '/UM/personCenter/editPass'})"
                                >个人中心</DropdownItem>
                                <DropdownItem @click.native="isShowAlarm">告警
                                    <span class="alarmCount">（{{alarmCount}}）</span>
                                </DropdownItem>
                                <!--<showUserInfo v-bind="userself"></showUserInfo>-->
                                <DropdownItem divided @click.native="logout">注销</DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                    </div>
                </div>
            </Menu>
        </Header>
        <showAlarm
            :modalPrams="videoModal.modalPrams"
            :alarmContainer="videoModal.alarmContainer"
            ref="video"
        ></showAlarm>
    </Layout>
</template>

<script>
import { LoginService } from "../../services/loginService.js";
import { UserService } from "../../services/userService";
import { removeToken } from "@/utils/auth"; // 验权
import { EnterGalleryService } from "../../services/enterGalleryService.js";
import showAboutUs from "../../components/Common/Modal/ShowAboutUs";
import Img from "../../assets/UM/UMIcon.png";
import Cookies from "js-cookie";
import showAlarm from "@/components/Common/Modal/showAlarms";
import ShowStartPlan from "../Common/Modal/ShowStartPlan";
import { VideoService } from "../../services/videoService.js";
import { MeasObjServer } from "../../services/MeasObjectSerivers.js";
import ShowNodesPic from "../Common/Modal/ShowNodesPic";
export default {
    name: "UMTop",
    data() {
        return {
            umImg: Img,
            // title: "综合管廊统一管理平台",
            titlePic: require("../../assets/UM/umToptitle.png"),
            aboutUs: {
                show: {
                    state: false
                }
            },
            userself: {
                show: {
                    state: false
                },
                userInfo: {}
            },
            modules: [
                {
                    id: 1,
                    name: "运维管理",
                    frontIcon: "ios-navigate",
                    endIcon: "arrow-down-b",
                    children: [
                        {
                            name: "巡检管理",
                            url: "/UM/Patrol/homePage",
                            path: "/UM/Patrol/homePage"
                        },
                        {
                            name: "巡检任务",
                            url: "/UM/patrolTask/query",
                            path: "/UM/patrolTask/query"
                        },
                        {
                            name: "缺陷管理",
                            url: "/UM/defect/query",
                            path: "/UM/defect/query"
                        },
                        {
                            name: "检修管理",
                            url: "/UM/overhaul/query",
                            path: "/UM/overhaul/query"
                        },
                        {
                            name: "设备管理",
                            url: "/UM/equipment/homePage",
                            path: "/UM/equipment/homePage"
                        },
                        {
                            name: "虚拟巡检",
                            url: "/UM/virtualInspect",
                            path: "/UM/virtualInspect"
                        },
                        {
                            name: "人员作业申请",
                            url: "/UM/enterGalleryApplication/query",
                            path: "/UM/enterGalleryApplication/query"
                        }
                    ]
                },
                {
                    id: 2,
                    name: "运营管理",
                    frontIcon: "ios-keypad",
                    endIcon: "arrow-down-b",
                    children: [
                        {
                            name: "管廊客户管理",
                            url: "/UM/tunnelCustomer/list",
                            path: "/UM/tunnelCustomer/list"
                        },
                        {
                            name: "管廊能耗管理",
                            url: "/UM/TunnelEnergy/homepage",
                            path: "/UM/TunnelEnergy/homepage"
                        },
                        {
                            name: "管廊空间管理",
                            url: "/UM/OperatingSpace/tunnel/1",
                            path: "/UM/OperatingSpace/tunnel"
                        },
                        {
                            name: "管廊合同管理",
                            url: "/UM/tunnelContract/list",
                            path: "/UM/tunnelContract/list"
                        }
                    ]
                },
                {
                    id: 3,
                    name: "综合监控",
                    frontIcon: "ios-analytics",
                    endIcon: "arrow-down-b",
                    children: [
                        {
                            name: "管廊环境监测",
                            url: "/UM/TunnelEnvironment",
                            path: "/UM/TunnelEnvironment"
                        },
                        {
                            name: "管廊安防监测",
                            url: "/UM/TunnelSafety",
                            path: "/UM/TunnelSafety"
                        },
                        {
                            name: "管廊机电监测",
                            url: "/UM/TunnelElectromechanical",
                            path: "/UM/TunnelElectromechanical"
                        },
                        {
                            name: "管廊消防监控",
                            url: "/UM/TunnelFire",
                            path: "/UM/TunnelFire"
                        },
                        {
                            name: "管廊管线监控",
                            url: "/UM/TunnelPipeline",
                            path: "/UM/TunnelPipeline"
                        },
                        {
                            name: "数据分析",
                            url: "/UM/DataAnalysis/QueryData",
                            path: "/UM/DataAnalysis/QueryData"
                        },
                        {
                            name: "视频监控系统",
                            url: "/UM/VideoMonitoring/details/1",
                            path: "/UM/VideoMonitoring/details"
                        },
                        {
                            name: "人员定位",
                            url: "/UM/PersonnelPosition/visitors",
                            path: "/UM/PersonnelPosition/visitors"
                        }
                    ]
                },
                {
                    id: 4,
                    name: "应急管理",
                    frontIcon: "ios-paper",
                    endIcon: "arrow-down-b",
                    children: [
                        {
                            name: "基础信息管理",
                            url: "/UM/relatedUnits/query",
                            path: "/UM/relatedUnits/query"
                        },
                        {
                            name: "预案管理",
                            url: "/UM/plans/detial/FirePlanProcess",
                            path: "/UM/plans/detial"
                        }
                    ]
                },
                {
                    id: 5,
                    name: "其他管理",
                    frontIcon: "document-text",
                    endIcon: "arrow-down-b",
                    children: [
                        {
                            name: "资料管理",
                            url: "/UM/tunnelDocuments/list/1.1",
                            path: "/UM/tunnelDocuments/list"
                        },
                        {
                            name: "可视化平台",
                            url: "/VMMain2",
                            path: "/VMMain2"
                        },
                        {
                            name: '项目概况',
                            url: '/UM/projectSurvey',
                            path: '/UM/projectSurvey'
                        }
                    ]
                }
            ],
            // countNum: 1,
            showalarm: "1",
            alarmQueue: null,
            planQueue: null,
            selectPlan: null,
            videoModal: {
                modalPrams: {
                    state: false
                },
                alarmContainer: []
            },
            saveAlarmContainer: [],
            alarmRouterList: [],
            alarmLevel: [],
            tempAlarm: null,
            durations: [],
            messageCount: 0,
            alarmCount: 0
        };
    },
    components: {
        showAboutUs,
        showAlarm,
        ShowStartPlan
    },
    watch:{
        'alarmCount': function(newVal, oldVal){
            if(newVal>0){
                let dom = document.getElementsByClassName('outBox')[0]
                dom.setAttribute('class', 'blingbling')
            }else{
                let dom = document.getElementsByClassName('blingbling')[0]
                dom.setAttribute('class', 'outBox')
            }
        }
    },
    computed: {
        routers: {
            get() {
                return this.$store.getters.permission_routers;
            }
        },
        planData: {
            get() {
                return this.$store.getters.getPlanData;
            },
            set(value) {
                this.$store.commit("setPlanData", value);
            }
        },
        countNum: function(){
            let countNum = this.messageCount+this.alarmCount
            return countNum
        }
    },
    mounted() {
        this.getCountInfoNum();
        /**.比较浪费带宽 先注释掉 方法可以使用 */
        // setInterval(this.getCountInfoNum,1000)
        this.startListenMQ();
        this.noticeTop = window.innerHeight - window.innerHeight * 0.15;
        UserService.getAlarmLevelTime().then(
            result => {
                this.durations = result
            },
            error => {
                this.Log.info(error)
            }
        )
    },
    methods: {
        setUserself() {
            var _this = this;
        },
        showAboutUs() {
            this.aboutUs.show.state = !this.aboutUs.show.state;
        },
        //退出登录
        logout: function() {
            var _this = this;
            this.$Modal.confirm({
                title: "注销",
                width: "25vw",
                styles: {
                    top: 400
                },
                content: "<p style='font-size:1.22.vmin'>确认退出吗?</p>",
                onOk: () => {
                    this.$store
                        .dispatch("LogOut")
                        .then(() => {
                            sessionStorage.removeItem("UMUser");
                            Cookies.remove("userName");
                            // MQ 退出连接
                            _this.MQ.closeMQ();
                            _this.$router.push("/UMlogin");
                        })
                        .catch(() => {
                            _this.$Message.error(msg);
                        });
                },
                onCancel: () => {}
            });
        },
        goToMoudle(path) {
            if (
                String(this.$router.history.current.fullPath).indexOf(
                    String(path.path)
                ) < 0
            ) {
                if (path.path == "/VMMain") {
                    const { href } = this.$router.resolve({
                        name: "可视化主页"
                    });
                    window.open(href, "_blank");
                } else {
                    let target = path.path.slice(0, path.path.lastIndexOf("/"));
                    let curPath = this.$route.path.slice(
                        0,
                        this.$route.path.lastIndexOf("/")
                    );
                    if (target != curPath) {
                        this.$router.push(path);
                    }
                    this.$router.push(path);
                }
            }
        },
        getCountInfoNum() {
            let _this = this;
            UserService.getMessageCount().then(
                result => {
                    _this.messageCount = result
                },
                error => {
                    _this.Log.info(error);
                }
            );
            UserService.getAlarmCount().then(
                result => {
                    _this.alarmCount = result
                },
                error => {
                    _this.Log.info(error);
                }
            )
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
        },
        startListenMQ() {
            this.Log.info("添加监听器到MQ");
            this.TransferStation.addListener("ModulePage", this.callback);
        },

        stopListenMQ() {
            this.Log.info("移除监听器");
            this.TransferStation.deleteListener("ModulePage");
        },
        // 连接成功回调函数
        callback(respond) {
            let result = JSON.parse(respond);
            let _this = this;
            if (result.type && result.type == "Alarm") {
                let content = JSON.parse(result.content);
                this.Log.info("ModulePage收到回调:", content);

                // 显示alarm modal框
                // _this.videoModal.modalPrams.state = true;
                // _this.videoModal.alarmContainer.push(content);

                //显示右下角提示框
                this.warningNotice(content);
                /* 滚动条以及不被遮挡 */
                document.getElementsByClassName("ivu-notice")[0].style.width=33+'vmin'
                if (document.getElementsByClassName("ivu-notice-notice")) {
                    let h = document.getElementsByClassName("ivu-notice")[0].offsetHeight + document.getElementsByClassName("ivu-notice-notice")[0].offsetHeight;
                    if (h > window.innerHeight) {
                        document.getElementsByClassName("ivu-notice")[0].style.bottom = "2vmin";
                    }
                }
            }
        },
        //设置告警面板中分页按钮的显隐
        changestatu() {
            this.showPage = !this.showPage;
        },
        //点击告警中的分页按钮
        alarmDataChangePage(index) {
            this.page.current = index;
        },
        confirmPlan() {
            this.nodesModal.showFlag = false;
        },
        warningNotice(alarm) {
            let durationTime = 0
            this.durations.forEach(item=>{
                if(alarm.alarmLevel==item.id){
                    durationTime = Number(item.name)
                }
            })
            var _this = this;
            var des = "";
            _this.videoModal.alarmContainer.unshift(alarm);
            _this.saveAlarmContainer.unshift(alarm);
            var plans = alarm.plans; //[{"name":"通风预案","id":4003}]
            if (plans && plans.length) {
                _this.selectPlan = plans[0].id;
            }
            _this.alarmLevel.forEach(a => {
                if (a.val == alarm.alarmLevel) {
                    des = a.key;
                }
            });
            var config = {
                title: alarm.alarmName,
                desc: alarm.objectName + alarm.location,
                duration: durationTime,
                onClose: function() {
                    let index = _this.videoModal.alarmContainer.indexOf(alarm);
                    if (index > -1) {
                        _this.videoModal.alarmContainer.splice(index, 1);
                    }
                    if (_this.videoModal.alarmContainer.length == 0) {
                        _this.videoModal.modalPrams.state = false;
                    }
                    /* 滚动条以及不被遮挡 */
                    if (document.getElementsByClassName("ivu-notice-notice")) {
                        let h = document.getElementsByClassName("ivu-notice-notice")[0].offsetHeight *(document.getElementsByClassName("ivu-notice-notice").length -1);
                        if (h < window.innerHeight) {
                            document.getElementsByClassName("ivu-notice")[0].style.bottom = "";
                        }
                    }
                    document.getElementsByClassName("ivu-notice")[0].style.width=33+'vmin'
                }
            };
            //详情按钮
            if (plans && plans.length > 0) {
                config.render = (h, params) => {
                    return h("div", [
                        h(
                            "span",
                            {
                                style: {
                                    fontSize: "1.4vmin"
                                }
                            },
                            alarm.location
                        ),
                        h(
                            "Button",
                            {
                                props: {
                                    type: "primary",
                                    size: "small"
                                },
                                style: {
                                    marginLeft: "1vmin"
                                },
                                on: {
                                    click: () => {
                                        this.showAlarmDetails(alarm.id);
                                    }
                                }
                            },
                            "详情"
                        )
                    ]);
                };
            }
            switch (alarm.alarmLevel) {
                case 1: {
                    this.$Notice.info(config);
                    break;
                }
                case 2: {
                    this.$Notice.success(config);
                    break;
                }
                case 3: {
                    this.$Notice.warning(config);
                    break;
                }
                case 4: {
                    this.$Notice.error(config);
                    break;
                }
            }
        },
        showAlarmDetails(id) {
            if (this.videoModal.alarmContainer.length > 21) {
                this.videoModal.alarmContainer.splice(
                    this.videoModal.alarmContainer.length - 1,
                    1
                );
            }
            this.saveAlarmContainer.map(item => {
                if (item.id == id) {
                    this.videoModal.modalPrams.state = false;
                    this.videoModal.alarmContainer = [];
                    this.videoModal.alarmContainer.push(item);
                }
            });
            this.videoModal.modalPrams.state = true;
        },
        isShowAlarm() {
            sessionStorage.setItem("refreshAddress", "/UM/DataAnalysis/QueryAlarmData")
            sessionStorage.setItem("selectedName", "1-1")
            this.$router.push({
                name: '告警查询',
                params: {
                    alarmLevels: [3,4]
                }
            })
        }
    },
    beforeDestroy() {
        this.stopListenMQ();
        clearTimeout(this.planTimer);
        this.$Notice.destroy();
    }
};
</script>

<style scoped>
.ivu-layout-header,
.ivu-menu-light {
    background: transparent;
}
.ivu-btn-primary {
    border-color: transparent;
}
.layoutLeft {
    float: left;
    height: 100%;
}
.layoutLeft .layout-logo {
    height: 6vmin;
    margin-top: 4%;
    width: 6.5vmin;
    background: url("../../assets/UM/Maxview.png") no-repeat;
    background-size: 100% 100%;
    float: left;
}

.ivu-menu-light.ivu-menu-horizontal .ivu-menu-item {
    color: #fff;
}

.layout-title {
    float: left;
    top: 1vh;
    color: #fff;
    height: 100%;
}

.layout-nav {
    width: 100%;
    position: fixed;
    top: 0;
}

.ivu-menu-horizontal {
    height: 9vh;
    line-height: 9vh;
}

.mainTitle {
    margin-top: 3vh;
    margin-left: 0.8vw;
    height: 4.5vh;
}

.ivu-avatar-large {
    width: 4vmin;
    height: 4vmin;
    border-radius: 50%;
}

.layout-nav >>> .ivu-badge-count {
    font-size: 1.4vmin;
    min-width: 2.2vmin;
    height: 2.2vmin;
    border-radius: 50%;
    padding: 0.4vmin 0.6vmin;
}

.layout-nav .topBtn {
    padding: 1.7vmin 2.5vmin 0vmin 2.5vmin;
    font-size: 1.66vmin;
    background: url("../../assets/UM/UmTopBg.png") no-repeat center;
    background-size: contain;
    top: 15%;
    position: relative;
    width: 100%;
}

.layout-nav >>> .ivu-dropdown-item {
    font-size: 1.6vmin !important;
    padding: 0.7vmin 1.6vmin;
    border-top: 0.1vmin solid #e9eaec;
    color: #fff;
    text-align: center;
}
.layout-nav >>> .ivu-dropdown-item:hover {
    color: #000;
    background: rgba(255, 255, 255, 0.6);
}
.layout-nav >>> .ivu-dropdown-item:first-child {
    border-top: none;
}

.ivu-dropdown-item-divided:before {
    content: "";
    height: 0.5vmin;
    display: block;
    margin: 0 -1.6vmin;
    background-color: transparent;
    position: relative;
    top: -0.7vmin;
}
.ivu-menu-horizontal.ivu-menu-light:after {
    background: transparent;
}
.ivu-radio-group-button .ivu-radio-wrapper:after,
.ivu-radio-group-button .ivu-radio-wrapper:before {
    background: #59b4e3;
}
.layout-nav >>> .ivu-select-dropdown {
    margin: 0;
    padding: 0;
    top: 8.2vh !important;
    background-color: rgb(50, 103, 156);
    box-shadow: 0 0px 8px 0 rgba(57, 123, 187, 0.5),
        0 1px 0px 0 rgba(57, 123, 187, 0.6);
    z-index: 1100;
}
.layout-nav >>> .ivu-dropdown-item-divided {
    margin-top: 0;
}
.select-dropdown {
    position: fixed;
    right: 6vmin;
}
.alarmCount{
    color: red;
    font-size: 1.8vmin;
    font-weight: 800;
}

@-webkit-keyframes scaleout {
    0% { -webkit-transform: scale(1.0) }
    100% {-webkit-transform: scale(1.1);opacity: 0;}
}
.blingbling{
    -webkit-animation: scaleout 1.5s infinite ease-in-out;
  }

/* 小屏幕（显示器，小于等于 1920px） */
@media (max-width: 1920px) {
    .layout-nav >>> .ivu-select-dropdown {
        left: 3.4vmin !important;
    }
    .select-dropdown >>> .ivu-select-dropdown {
        left: -4vmin !important;
    }
}
/* 大屏幕（显示器，大于等于 1920px） */
@media (min-width: 1921px) {
    .layout-nav >>> .ivu-select-dropdown {
        left: 2vmin !important;
    }
    .select-dropdown >>> .ivu-select-dropdown {
        left: 0vmin !important;
    }
    .ivu-notice{
        width: 30vmin !important;
    }
}
</style>
<style>
/* 提示框超出出现滚动条 */
.ivu-notice {
    overflow-y: auto;
}
.ivu-notice-with-desc.ivu-notice-with-icon .ivu-notice-title {
    font-size: 1.6vmin !important;
    line-height: 1.8vmin !important;
    padding-top: 0.5vmin;
}
.ivu-notice::-webkit-scrollbar,
.stepsBox::-webkit-scrollbar {
    width: 0.4vmin;
    height: 0.4vmin;
}
.ivu-notice::-webkit-scrollbar-thumb,
.stepsBox::-webkit-scrollbar-thumb {
    border-radius: 0.5vmin;
    -webkit-box-shadow: inset 0 0 5px rgba(228, 198, 198, 0.2);
    background: rgba(0, 0, 0, 0.2);
}
.ivu-notice::-webkit-scrollbar-track,
.stepsBox::-webkit-scrollbar-track {
    border-radius: 0;
    -webkit-box-shadow: inset 0 0 5px rgba(221, 208, 208, 0.2);
    background: rgba(0, 0, 0, 0.1);
}
</style>
