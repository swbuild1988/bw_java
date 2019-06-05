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
                            <Dropdown placement="bottom-start">
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
                            <a href="javascript:void(0)">
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
                                <!--<showUserInfo v-bind="userself"></showUserInfo>-->
                                <DropdownItem divided @click.native="logout">注销</DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                    </div>
                </div>
            </Menu>
        </Header>
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
                        }
                        // {
                        // 	name: "人员定位",
                        // 	url: "/UM/PersonnelPosition/visitors",
                        // 	path: "/UM/PersonnelPosition/visitors"
                        // }
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
                        }
                    ]
                }
            ],
            countNum: 1
        };
    },
    computed: {
        routers: {
            get() {
                return this.$store.getters.permission_routers;
            }
        }
    },
    mounted() {
        this.getCountInfoNum();
        /**.比较浪费带宽 先注释掉 方法可以使用 */
        // setInterval(this.getCountInfoNum,1000)
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
                    _this.countNum = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
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

                        // console.log(index, tempUrl[index]);
                        // console.log(temp);
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
            // console.log(result);
            return result;
        }
    },
    components: {
        showAboutUs
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

/* .mainTitle:hover {
	color: #66ccee;
} */

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
    /* margin-top: 0.5vmin; */
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
/* .ivu-menu-item:hover {
    border-bottom: 0px;
    color: transparent;
} */
.layout-nav >>> .ivu-select-dropdown {
    margin: 0;
    padding: 0;
    top: 8.2vh !important;
    background-color: rgba(50, 103, 156, 0.9);
    box-shadow: 0 0px 8px 0 rgba(57, 123, 187, 0.5),
        0 1px 0px 0 rgba(57, 123, 187, 0.6);
}
.layout-nav >>> .ivu-dropdown-item-divided {
    margin-top: 0;
}
.select-dropdown {
    position: fixed;
    right: 6vmin;
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
}
</style>