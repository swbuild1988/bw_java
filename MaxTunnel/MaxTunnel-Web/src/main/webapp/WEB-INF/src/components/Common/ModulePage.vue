<script>
import alarm from "../Common/AlarmDetial";
import { VideoService } from "../../services/videoService.js";
import { MeasObjServer } from "../../services/MeasObjectSerivers.js";
import UMCustom from "../../styles/UMCustom.css";
// import showVideo from "../../components/Common/Modal/ShowVideos.vue";
import showAlarm from "@/components/Common/Modal/showAlarms";
import { EnumsService } from "../../services/enumsService.js";
import ShowStartPlan from "../Common/Modal/ShowStartPlan";
import ShowNodesPic from "../Common/Modal/ShowNodesPic";
// import { getFormatTime } from "@/scripts/DateFormat.js";
import {
    Layout,
    Menu,
    Sider,
    Submenu,
    Icon,
    Content,
    Collapse,
    MenuItem,
    Footer
} from "iview";
import ShowNodesPicVue from "./Modal/ShowNodesPic.vue";

export default {
    name: "mudulePage",
    props: {
        moduleName: {
            default: "moduleName"
        },
        leftTree: {
            default: [
                {
                    id: 1,
                    name: "管廊1",
                    url: "",
                    childNode: [
                        {
                            id: 11,
                            name: "管廊11",
                            url: ""
                        },
                        {
                            id: 12,
                            name: "管廊12",
                            url: ""
                        }
                    ]
                },
                {
                    id: 2,
                    name: "管廊2",
                    url: ""
                }
            ]
        },
        selected: {
            type: Array,
            default: function() {
                return [-1, -1];
            }
        }
    },
    data() {
        return {
            alarmQueue: null,
            planQueue: null,
            showModal: {
                modalPrams: {
                    state: false,
                    selectPlan: ""
                }
            },
            selectPlan: null,
            videoModal: {
                modalPrams: {
                    state: false,
                    modalInfo: null,
                    planData: null
                },
                alarmContainer: []
            },
            alarmRouterList: [],
            alarmLevel: [],
            tempAlarm: null,
            isCollapsed: false,
            screenWidth: 300,
            minScreenWidth: 300,
            screenHeight: 900,
            childValue: "",
            showalarm: "1",
            showPage: false,
            page: {
                total: 20,
                current: 1
            },
            backStyle: {
                backgroundImage:
                    "url(" + require("../../assets/UM/bgCloudWhite.png") + ")",
                height: "100%",
                position: "relative"
            },
            selectedName: null,
            openNames: ["1"],
            nodesModal: {
                showFlag: false,
                imgUrl: null
            }
        };
    },
    mounted() {
        var _this = this;
        EnumsService.getAlarmLevelList().then(result => {
            if (result) {
                _this.alarmLevel = result;
            }
        });
        this.$Notice.config({
            top: 100
        });
        this.screenWidth = window.innerWidth * 0.14; //就是将14vh转为数字
        this.minScreenWidth = window.innerWidth * 0.03;
        if (this.leftTree.length > 0) {
            if (
                this.leftTree[0].childNode &&
                this.leftTree[0].childNode.length > 0
            ) {
                if (sessionStorage.getItem("selectedName") == "") {
                    this.selectedName = "1-0-0";
                    this.openNames = ["1", "1-0"];
                } else {
                    this.selectedName = sessionStorage.getItem("selectedName");
                    let index = this.selectedName.lastIndexOf("-");
                    this.openNames.push(this.selectedName.slice(0, index));
                }
            } else {
                if (sessionStorage.getItem("selectedName") == "") {
                    this.selectedName = "1-0";
                    this.openNames = ["1"];
                } else {
                    this.selectedName = sessionStorage.getItem("selectedName");
                    let index = this.selectedName.lastIndexOf("-");
                    this.openNames.push(this.selectedName.slice(0, index));
                }
            }
            this.$nextTick(() => {
                this.$refs.leftMenu.updateActiveName();
                this.$refs.leftMenu.updateOpened();
            });
        }
        this.acceptPlanData();
        this.acceptAlarmData();
    },
    beforeDestroy() {
        // this.closedMQ();
        this.$Notice.destroy();
    },
    watch: {
        leftTree: function(newValue, oldValue) {
            if (newValue.length > 0) {
                if (newValue[0].childNode && newValue[0].childNode.length > 0) {
                    if (sessionStorage.getItem("selectedName") == "") {
                        this.selectedName = "1-0-0";
                        this.openNames = ["1", "1-0"];
                    } else {
                        this.selectedName = sessionStorage.getItem(
                            "selectedName"
                        );
                        let index = this.selectedName.lastIndexOf("-");
                        this.openNames.push(this.selectedName.slice(0, index));
                    }
                    this.$nextTick(() => {
                        this.$refs.leftMenu.updateActiveName();
                        this.$refs.leftMenu.updateOpened();
                    });
                } else {
                    if (sessionStorage.getItem("selectedName") == "") {
                        this.selectedName = "1-0";
                        this.openNames = ["1"];
                    } else {
                        this.selectedName = sessionStorage.getItem(
                            "selectedName"
                        );
                        let index = this.selectedName.lastIndexOf("-");
                        this.openNames.push(this.selectedName.slice(0, index));
                    }
                    this.$nextTick(() => {
                        this.$refs.leftMenu.updateActiveName();
                        this.$refs.leftMenu.updateOpened();
                    });
                }
            }
        }
    },
    computed: {
        menuitemClasses: function() {
            return ["menu-item", this.isCollapsed ? "collapsed-menu" : ""];
        },
        selectedActive: function() {
            return this.selected;
        },
        planData: {
            get() {
                return this.$store.getters.getPlanData;
            },
            set(value) {
                this.$store.commit("setPlanData", value);
            }
        }
    },
    render() {
        const menuList = this.generateMenu(this.leftTree, "1");
        return (
            <div class="layout">
                <Layout class="coment">
                    <Sider
                        width={this.screenWidth}
                        collapsible
                        collapsed-width={this.minScreenWidth}
                        v-model="isCollapsed"
                        style="background-color: #1D5F87"
                    >
                        <Menu
                            active-name={this.selectedName}
                            width="auto"
                            class={this.menuitemClasses}
                            open-names={this.openNames}
                            accordion
                            ref="leftMenu"
                        >
                            <Submenu name="1">
                                <template slot="title">
                                    <div
                                        style={{
                                            paddingLeft: `2vmin !important`,
                                            display: "inline-block"
                                        }}
                                    >
                                        <Icon
                                            type="ios-navigate"
                                            class="icons"
                                        />
                                        <span>{this.moduleName}</span>
                                    </div>
                                </template>
                                {menuList}
                            </Submenu>
                        </Menu>
                    </Sider>
                    <Layout>
                        <Content style="overflow-y: auto;overflow-x: hidden;height: 200px;background-size: cover;background: #ececec;">
                            <router-view />
                            <showAlarm
                                modalPrams={this.videoModal.modalPrams}
                                alarmContainer={this.videoModal.alarmContainer}
                                ref="video"
                            />
                        </Content>
                        <Collapse v-model="showalarm" onOn-change="changestatu">
                            <Footer class="layout-footer-center">
                                2009-2018 &copy; Bandweaver
                            </Footer>
                        </Collapse>
                        <ShowStartPlan modalPrams={this.showModal.modalPrams} />
                        <ShowNodesPic
                            nodesModal={this.nodesModal}
                            confirmPlan={this.confirmPlan}
                        />
                    </Layout>
                </Layout>
            </div>
        );
    },
    methods: {
        //手动开启预案
        // startPlan(alarm) {
        // 	var _this = this;
        // 	if (_this.selectPlan) {
        // 		_this.showModal.modalPrams.state = !_this.showModal.modalPrams.state;
        // 		_this.showModal.modalPrams.selectPlan = _this.selectPlan;
        // 	}
        // },

        // 连接成功回调函数
        callback(respond) {
            let result = JSON.parse(respond.body);
            //将数据保存在vuex中
            // _this.videoModal.modalPrams.planData = result;
            // _this.planData = result;
            if (
                this.$router.history.current.path.indexOf("/UM/plans/execute") <
                0
            ) {
                // _this.showPlanTip();
                this.nodesModal.imgUrl = null;
                let _this = this;
                let planTimer = setTimeout(() => {
                    _this.nodesModal.imgUrl =
                        "/emplans/png/" + result.processInstanceId;
                    _this.nodesModal.showFlag = true;
                }, 500);
            }
        },
        //获取MQ推送的预案消息
        acceptPlanData() {
            var _this = this;
            _this.MQ._InitMQ(1, "/queue/QUEUE_PLAN_UM", "", _this.callback);
            this.planQueue = _this.MQ.client;
        },
        alarmCallback(respond) {
            let _this = this;
            let result = JSON.parse(respond.body);
            _this.videoModal.modalPrams.modalInfo = result;
            _this.showAlarmDetails();
            _this.warningNotice(result);
        },
        getMessageCallback(respond) {
            console.log("getMessageCallback-respond", respond);
        },

        //开启告警队列监听
        //接受告警队列推送的数据
        acceptAlarmData() {
            var _this = this;
            _this.MQ._InitMQ(
                1,
                "/queue/QUEUE_ALARM_UM",
                "",
                _this.alarmCallback
            );
            this.alarmQueue = _this.MQ.client;
            console.log("this.alarmQueue", this.alarmQueue);
            // _this.MQ._sendMessage("queue/QUEUE_ALARM_UM", _this.getMessageCallback)
        },
        closedMQ() {
            var _this = this;
            console.log(_this.alarmQueue, this.planQueue, this.MQ);
            this.alarmQueue.disconnect();
            _this.planQueue.disconnect();
        },
        goToMoudle(path, index, childIndex) {
            this.selectedActive[0] = index;
            this.selectedActive[1] = childIndex;
            if (childIndex < 0) {
                sessionStorage.setItem("selectedName", "1-" + index);
            } else {
                sessionStorage.setItem(
                    "selectedName",
                    "1-" + index + "-" + childIndex
                );
            }
            console.log("path", path);
            console.log("this.$route.path", this.$route.path);
            if (path.path == this.$route.path) {
                this.$router.replace({
                    path: "/refresh",
                    query: {
                        t: Date.now()
                    }
                });
            } else {
                this.$router.push(path);
            }

            this.$emit("childByValue", path);
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
            console.log("this plan is confirmed!");
            this.nodesModal.showFlag = false;
        },

        warningNotice(alarm) {
            var _this = this;
            var des = "";
            this.videoModal.alarmContainer.unshift(alarm);
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
                title: des + "告警",
                desc: alarm.objectName + alarm.alarmName,
                duration: 0
                // onClose: _this.startPlan(alarm)
            };
            if (plans && plans.length > 0) {
                config.render = (h, params) => {
                    return h("div", [
                        h(
                            "Button",
                            {
                                props: {
                                    type: "primary",
                                    size: "small"
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
            this.videoModal.alarmContainer.map(item => {
                if (item.id == id) {
                    this.videoModal.modalPrams.modalInfo = item;
                }
            });
            this.videoModal.modalPrams.state = !this.videoModal.modalPrams
                .state;
        },
        generateMenu(arr, level) {
            const hasChild = arr.find(item => {
                return item.childNode && item.childNode.length;
            });
            return arr.map((item, index) => {
                const curLevel = level + "-" + index;
                const left = curLevel.split("-").length * 0.8 + 1.2;
                if (hasChild) {
                    if (item.childNode && item.childNode.length) {
                        const children = this.generateMenu(
                            item.childNode,
                            curLevel
                        );
                        return (
                            <Submenu name={curLevel}>
                                <template slot="title">
                                    <div
                                        style={{
                                            paddingLeft: `${left}vmin !important`,
                                            display: "inline-block"
                                        }}
                                    >
                                        <Icon type="ios-paper" class="icons" />
                                        <span>{item.name}</span>
                                    </div>
                                </template>
                                {children}
                            </Submenu>
                        );
                    } else {
                        return (
                            <div class="menuList">
                                <MenuItem
                                    name={curLevel}
                                    nativeOnClick={() =>
                                        this.goToMoudle(
                                            { path: item.url },
                                            index,
                                            -1
                                        )
                                    }
                                >
                                    <div
                                        style={{
                                            paddingLeft: `${left}vmin !important`,
                                            display: "inline-block"
                                        }}
                                    >
                                        <Icon type="ios-paper" class="icons" />
                                        <span>{item.name}</span>
                                    </div>
                                </MenuItem>
                            </div>
                        );
                    }
                } else {
                    return (
                        <MenuItem
                            name={level + "-" + index}
                            nativeOnClick={() =>
                                this.goToMoudle({ path: item.url }, index, -1)
                            }
                        >
                            <span style="font-size: 1.66vmin !important">
                                {item.name}
                            </span>
                        </MenuItem>
                    );
                }
            });
        }
    },
    components: {
        alarm,
        ShowStartPlan,
        showAlarm,
        Layout,
        Menu,
        Sider,
        Submenu,
        Icon,
        Content,
        Collapse,
        MenuItem,
        Footer,
        ShowNodesPic
    }
};
</script>

<style scoped>
.ivu-select-single .ivu-select-selection .ivu-select-placeholder,
.ivu-select-single .ivu-select-selection .ivu-select-selected-value {
    width: 8vmin;
}
.ivu-menu >>> .ivu-menu-item {
    padding-left: 36px !important;
    /* font-size: 1.66vmin !important; */
    /* border-bottom: 1px solid #aaa;*/
}

.ivu-menu-item,
.ivu-menu-submenu {
    /*border-radius: 10px;
    background-color: rgba(23, 67, 160, 0.3);
    border: 1px solid rgba(252, 207, 24, 0.9);*/
    border-left: none;
    border-right: none;
}

.ivu-menu-submenu >>> .ivu-menu-submenu-title {
    padding-left: 0 !important;
    /*border-radius: 10px;
    background-color: rgba(23, 67, 160, 0.3);
    border: 1px solid rgba(224, 180, 23, 0.9);*/
    border-bottom: 1px solid #938e8e9c;
    border-top: 1px solid #938e8e9c;
    font-size: 2vmin;
}

.ivu-menu-light.ivu-menu-vertical .ivu-menu-item {
    border-right: none;
}

.unitBox {
    border: 1px solid rgba(224, 180, 23, 0.9);
    width: 85%;
    margin: 1vmin auto;
    padding: 0.5vmin 0;
    border-radius: 4px;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
    font-size: 1.6vmin;
    overflow: hidden;
}

.title {
    font-size: 2vmin !important;
    color: #697480;
    padding: 0 1vmin;
}

.address {
    color: #88929a;
    padding: 0 1vmin;
}

.contact {
    background: #f5f5f5;
    line-height: 3.5vmin;
    margin: 1vmin 0;
    padding: 0 1vmin;
}

.contactName,
.contactTel {
    display: inline-block;
}

.contactName {
    width: 45%;
}

.crtTime {
    float: left;
}

.option {
    padding: 0 0.4vmin;
    width: 100%;
    height: 2.5vmin;
}

.sectionTitle {
    float: left;
}

.sectionName {
    margin-left: 6vmin;
}

.ivu-dropdown-item >>> .ivu-select-dropdown {
    margin-left: 1.4vmin;
}

.ivu-collapse > .ivu-collapse-item >>> .ivu-collapse-header {
    position: absolute;
    right: 2vmin;
    color: #464d4f;
    line-height: 5vmin;
    /* font-size: 2.4vmin; */
    z-index: 10001;
}

.layout {
    background: #f5f7f9;
    position: relative;
    border-radius: 2px;
    overflow: hidden;
    height: 100%;
}

.coment {
    height: calc(100vh - 9vh);
}

/* .coment >>> .ivu-icon-arrow-right-b{
	display: none;
} */

.coment
    >>> .ivu-collapse
    > .ivu-collapse-item.ivu-collapse-item-active
    > .ivu-collapse-header
    > i {
    transform: rotate(180deg);
}

.menu-item span {
    display: inline-block;
    overflow: hidden;
    width: 17.4vmin;
    text-overflow: ellipsis;
    white-space: nowrap;
    vertical-align: bottom;
    transition: width 0.2s ease 0.2s;
}

.menu-item i {
    transform: translateX(0px);
    transition: font-size 0.2s ease, transform 0.2s ease;
    vertical-align: middle;
    font-size: 1.66vmin;
}

.collapsed-menu span {
    width: 0px;
    transition: width 0.2s ease;
}

.collapsed-menu i {
    transform: translateX(5px);
    transition: font-size 0.2s ease 0.2s, transform 0.2s ease 0.2s;
    vertical-align: middle;
    font-size: 2.2vmin;
}

.layout-footer-center {
    text-align: center;
    background: none;
    color: #fff;
    background: #357aa1;
    height: 5vh;
}

h1 {
    margin: 2% 1% 0 0;
    width: 8%;
    float: left;
}

.ivu-layout-sider >>> .ivu-layout-sider-trigger {
    background: #1c5d85;
}

.ivu-layout {
    background: #fff;
}

.ivu-layout-footer {
    padding: 1.3vmin 5vmin;
    font-size: 1.66vmin;
}

.ivu-menu-light {
    background: #1c5d85;
}

.ivu-menu-submenu >>> .ivu-menu {
    color: #fff;
}

.ivu-menu-item:hover {
    color: #595c56;
    background-color: #fff;
}

.ivu-menu {
    color: #fff;
    border-radius: 10px;
    /*  background-color: rgba(210, 182, 172, 0.2);*/
    padding: 4px;
}

li.ivu-menu-submenu-has-parent-submenu >>> .ivu-menu-submenu-title:hover,
.ivu-menu-light >>> .ivu-menu-submenu-title:hover {
    color: #1c5d85;
    background-color: #fff;
}

.ivu-menu-light.ivu-menu-vertical .ivu-menu-item-active:not(.ivu-menu-submenu) {
    /*background: #fff;*/
    /*color: #1d5f87 !important;*/
    background: #15252f45;
    color: #fff;
    /* box-shadow: 0px 0px 15px 7px rgb(121, 170, 220) inset;*/
}

.ivu-menu-light.ivu-menu-vertical
    .ivu-menu-item-active:not(.ivu-menu-submenu)::after {
    /*background: #1a4e6e;*/
    content: "";
    width: 0;
    height: 0;
    position: absolute;
    left: 100%;
    top: 50%;
    transform: translate(0, -50%);
    border-top: 0.8vmin solid transparent;
    border-bottom: 0.8vmin solid transparent;
    border-left: 1vmin solid #1a4e6e;
}

/*去除footer的白边*/
.ivu-collapse {
    border: none;
}

.coment >>> .ivu-layout-sider-trigger .ivu-icon {
    font-size: 1.66vmin;
}

.ivu-menu-vertical .ivu-menu-item,
.ivu-menu-vertical .ivu-menu-submenu-title {
    padding: 1.4vmin 2.4vmin;
}

.menuList >>> .ivu-menu-item {
    font-size: 2vmin;
    padding-left: 0 !important;
    border: 1px solid #938e8e9c;
}
.menuItem >>> .ivu-menu-item {
    font-size: 1.66vmin;
    border: 0px solid #938e8e9c;
}
.icons {
    margin-right: 0.8vmin;
}
</style>
