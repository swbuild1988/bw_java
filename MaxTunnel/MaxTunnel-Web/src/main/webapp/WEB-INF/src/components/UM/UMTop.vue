<template>
    <Layout>
        <Header :style="{position: 'fixed', width: '100%',zIndex:999,padding:'0',color:'#1b1754'}">
            <Menu
                mode="horizontal"
                active-name="1"
                :style="{color:'#1b1754'}"
            >
                <div class="layout-logo"></div>
                <div class="layout-title">
                    <h1><a
                            class="mainTitle"
                            @click="goToMoudle({ path: '/UMMain'})"
                        >{{title}}</a></h1>
                </div>
                <div class="layout-nav">
                    <div style="position: fixed;right: 3.4vmin;">
                        <MenuItem
                            v-for="module in modules"
                            :key="module.name"
                            :name="module.name"
                        >
                        <Dropdown placement="bottom-start">
                            <Button type="primary">
                                <Icon :type="module.frontIcon"></Icon>
                                {{module.name}}
                                <Icon :type="module.endIcon"></Icon>
                            </Button>
                            <DropdownMenu
                                slot="list"
                                :id="module.id"
                            >
                                <DropdownItem
                                    v-for="child in module.children"
                                    :key="child.name"
                                    :name="child.name"
                                    divided
                                    @click.native="goToMoudle({ path: child.url})"
                                >
                                    {{child.name}}
                                </DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                        </MenuItem>
                        <!-- 人物圆点 -->
                        <div style="position:relative;float: right;margin-left: 6.25rem;">
                            <Dropdown>
                                <a href="javascript:void(0)">
                                    <Badge :count="countNum">
                                        <!--<Avatar style="background-color: #f56a00" shape="circle" icon="person" ></Avatar>-->
                                        <Avatar
                                            :style="{background:'#f56a00'}"
                                            size="large"
                                            shape="circle"
                                            :src="umImg"
                                        ></Avatar>
                                    </Badge>
                                </a>
                                <DropdownMenu slot="list">
                                    <DropdownItem @click.native="goToMoudle({ path: '/UM/myNews/queryMyTask'})">我的消息</DropdownItem>
                                    <!-- <DropdownItem @click.native="goToMoudle({ path: '/UM/myApplication/query'})">我的申请</DropdownItem> -->
                                    <DropdownItem @click.native="goToMoudle({ path: '/UM/myTasks/query'})">我的任务</DropdownItem>
                                    <DropdownItem @click.native="showAboutUs">关于我们</DropdownItem>
                                    <showAboutUs v-bind="aboutUs"></showAboutUs>
                                    <DropdownItem @click.native="setUserself">个人中心</DropdownItem>
                                    <!--<showUserInfo v-bind="userself"></showUserInfo>-->
                                    <DropdownItem
                                        divided
                                        @click.native="logout"
                                    >注销</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                        </div>
                    </div>
                </div>
            </Menu>
        </Header>
    </Layout>
</template>

<script>
import { LoginService } from '../../services/loginService.js'
import { UserService } from '../../services/userService'
import { EnterGalleryService } from '../../services/enterGalleryService.js'
import showAboutUs from '../../components/Common/Modal/ShowAboutUs'
// import showUserInfo from '../../components/Common/Modal/ShowUserInfo.vue'
import Img from '../../assets/UM/UMIcon.png'

export default {
    name: "UMTop",
    data() {
        return {
            umImg: Img,
            title: '综合管廊统一管理平台',
            aboutUs: {
                show: { state: false },
                company: {
                    version: "MaxTunnel-5.20",
                    name: "上海波汇科技有限公司",
                    email: "Bandweaver@.cn",
                    telephone: "12413321231",
                    address: "上海市浦东新区上科路88号豪威科技园1幢503",
                }
            },
            userself: {
                show: { state: false },
                userInfo: {},
            },
            modules: [
                {
                    id: 1,
                    name: '运维管理',
                    frontIcon: 'ios-navigate',
                    endIcon: 'arrow-down-b',
                    children: [
                        {
                            name: '巡检管理',
                            url: '/UM/Patrol/homePage'
                        },
                        {
                            name: '巡检任务',
                            url: '/UM/patrolTask/query'
                        },
                        {
                            name: '缺陷管理',
                            url: '/UM/defect/query'
                        },
                        {
                            name: '检修管理',
                            url: '/UM/overhaul/query'
                        },
                        {
                            name: '设备管理',
                            url: '/UM/equipment/homePage'
                        },
                        {
                            name: '虚拟巡检',
                            url: '/UM/virtualInspect'
                        }
                    ]
                },
                {
                    id: 2,
                    name: '运营管理',
                    frontIcon: 'ios-keypad',
                    endIcon: 'arrow-down-b',
                    children: [
                        {
                            name: '管廊客户管理',
                            url: '/UM/tunnelCustomer/list'
                        },
                        {
                            name: '入廊申请管理',
                            url: '/UM/enterGalleryApplication/query'
                        },
                        {
                            name: '管廊能耗管理',
                            url: '/UM/TunnelEnergy/homepage'
                        },
                        {
                            name: '管廊空间管理',
                            url: '/UM/OperatingSpace/tunnel/1'
                        },
                        {
                            name: '管廊合同管理',
                            url: '/UM/tunnelContract/list'
                        },
                        {
                            name: '日志管理',
                            url: '/UM/operationLog/query'
                        },
                    ]
                },
                {
                    id: 3,
                    name: '综合监控',
                    frontIcon: 'ios-analytics',
                    endIcon: 'arrow-down-b',
                    children: [
                        // {
                        //   name: '管廊本体监测',
                        //   url: '/UM/TunnelSupervise'
                        // },
                        {
                            name: '管廊环境监测',
                            url: '/UM/TunnelEnvironment'
                        },
                        {
                            name: '管廊安防监测',
                            url: '/UM/TunnelSafety'
                        },
                        // {
                        //   name: '管线本体监测',
                        //   url: '/UM/PipelineSupervise'
                        // },
                        // {
                        //   name: '消防系统',
                        //   url: ''
                        // },
                        // {
                        //   name: '通信系统',
                        //   url: ''
                        // },
                        {
                            name: '数据分析',
                            url: '/UM/DataAnalysis/QueryData'
                        },
                        // {
                        //   name: '机器人监控系统',
                        //   url: '/UM/RobotMonitoring/query'
                        // },
                        {
                            name: '视频监控系统',
                            url: '/UM/VideoMonitoring/details/1'
                        },
                        {
                            name: '人员定位',
                            url: '/UM/PersonnelPosition/tunnel'
                        },
                        // {
                        //   name: '通讯系统',
                        //   url: '/UM/Communication/answers'
                        // }
                    ]
                },
                {
                    id: 4,
                    name: '应急管理',
                    frontIcon: 'ios-paper',
                    endIcon: 'arrow-down-b',
                    children: [
                        {
                            name: '基础信息管理',
                            url: '/UM/relatedUnits/query'
                        },
                        {
                            name: '预案管理',
                            url: '/UM/plans/detial/FirePlanProcess'
                        },
                        // {
                        //   name: '调度管理',
                        //   url: ''
                        // },
                        // {
                        //   name: '灾害管理',
                        //   url: '/UM/disaster/query'
                        // },
                    ]
                },
                {
                    id: 5,
                    name: '其他管理',
                    frontIcon: 'document-text',
                    endIcon: 'arrow-down-b',
                    children: [
                        {
                            name: '资料管理',
                            url: '/UM/tunnelDocuments/list'
                        },
                        {
                            name: '可视化平台',
                            url: '/VMMain2'
                        }
                    ]
                }
            ],
            countNum: 1,
            getUserName: null
        }
    },
    mounted() {
        this.getUsData();
        this.getCountInfoNum()
        /**.比较浪费带宽 先注释掉 方法可以使用 */
        // setInterval(this.getCountInfoNum,1000)
        this.getSessionUserName()
    },
    methods: {
        setUserself() {
            var _this = this;

        },
        getUsData() {
            var _this = this;
            EnterGalleryService.getAboutUsData().then((result => {
                if (result) {
                    _this.aboutUs.company.version = result.version;
                    _this.aboutUs.company.email = result.email;
                    _this.aboutUs.company.telephone = result.contact;
                    _this.aboutUs.company.address = result.address;
                    _this.aboutUs.company.name = result.company;
                }
            }))
        },
        showAboutUs() {
            this.aboutUs.show.state = !this.aboutUs.show.state;
        },
        //退出登录
        logout: function () {
            var _this = this;
            this.$Modal.confirm({
                title: '注销',
                content: '<p>确认退出吗?</p>',
                onOk: () => {
                    LoginService.UMLogout().then(
                        result => {
                            sessionStorage.removeItem('user');
                            _this.$router.push('/UMlogin');
                        },
                        error => {
                            this.$Message.error(msg);
                        })
                },
                onCancel: () => {
                }
            });
        },
        goToMoudle(path) {
            if (path.path == '/VMMain') {
                const { href } = this.$router.resolve({
                    name: "可视化主页"
                });
                window.open(href, '_blank');
            } else {
                this.$router.push(path);
            }
        },
        getCountInfoNum() {
            let _this = this
            UserService.getMessageCount().then(
                result => {
                    _this.countNum = result
                },
                error => {
                    _this.Log.info(error)
                })
        },
        getSessionUserName: function () {
            if (sessionStorage.UMUerName != null || sessionStorage.UMUerName != undefined || sessionStorage.UMUerName != '') {
                var str = sessionStorage.UMUerName.substring(1, 2)
                this.getUserName = str.substring(0, 1).toUpperCase()
            }
        },
    },
    components: {
        showAboutUs,
        // showUserInfo
    },
}
</script>

<style scoped>
.ivu-layout-header,
.ivu-menu-light {
    background: #357aa1;
}

.layout-logo {
    height: 6vh;
    width: 4vw;
    border-radius: 3px;
    position: relative;
    background: url("../../assets/UM/Maxview.png") no-repeat center;
    background-size: 100% 100%;
    float: left;
    top: 1.2vh;
}

.layout-title {
    float: left;
    top: 1vh;
    color: #fff;
}

.layout-nav {
    width: calc(98vw - 300px);
    position: fixed;
    top: 0;
}

.ivu-menu-horizontal {
    height: 9vh;
    line-height: 9vh;
}

.mainTitle {
    color: #fff;
    font-size: 4vmin;
}

.mainTitle:hover {
    color: #66ccee;
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

.layout-nav >>> .ivu-btn {
    padding: 0.6vmin 1.5vmin;
    font-size: 1.66vmin;
}

.layout-nav >>> .ivu-dropdown-item {
    font-size: 1.6vmin !important;
}
</style>
