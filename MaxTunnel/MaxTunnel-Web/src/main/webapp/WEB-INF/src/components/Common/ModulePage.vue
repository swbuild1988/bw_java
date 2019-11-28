<script>
import { VideoService } from "../../services/videoService.js";
import { MeasObjServer } from "../../services/MeasObjectSerivers.js";
import UMCustom from "../../styles/UMCustom.css";
import { EnumsService } from "../../services/enumsService.js";
import ShowNodesPic from "../Common/Modal/ShowNodesPic";
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
                    selectPlan: null
                }
            },
            selectPlan: null,
            videoModal: {
                modalPrams: {
                    state: false
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
            showPage: false,
            page: {
                total: 20,
                current: 1
            },
            selectedName: null,
            openNames: ["1"]
        };
    },
    mounted() {
        var _this = this;
        EnumsService.getAlarmLevelList().then(result => {
            if (result) {
                _this.alarmLevel = result;
            }
        });
        this.screenWidth = window.innerWidth * 0.14; //就是将14vh转为数字
        this.minScreenWidth = window.innerWidth * 0.03;
        if (this.leftTree.length > 0) {
            if (
                this.leftTree[0].childNode &&
                this.leftTree[0].childNode.length > 0
            ) {
                if (!sessionStorage.getItem("selectedName")) {
                    this.selectedName = "1-0-0";
                    this.openNames = ["1", "1-0"];
                } else {
                    this.selectedName = sessionStorage.getItem("selectedName");
                    let index = this.selectedName.lastIndexOf("-");
                    this.openNames.push(this.selectedName.slice(0, index));
                }
            } else {
                if (!sessionStorage.getItem("selectedName")) {
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
    },
    watch: {
        leftTree: function(newValue, oldValue) {
            if (newValue.length > 0) {
                if (newValue[0].childNode && newValue[0].childNode.length > 0) {
                    if (!sessionStorage.getItem("selectedName")) {
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
                    if (!sessionStorage.getItem("selectedName")) {
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
                        style="background-color: transparent"
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
                        <Content style="overflow-y: auto;overflow-x: hidden;height: 200px;background-size: cover;background:transparent;">
                            <router-view />
                        </Content>
                        <Collapse>
                            <Footer class="layout-footer-center">
                                2009-2018 &copy; Bandweaver
                            </Footer>
                        </Collapse>
                    </Layout>
                </Layout>
            </div>
        );
    },
    methods: {
        goToMoudle(path, name) {
            sessionStorage.setItem("selectedName", name);
            sessionStorage.setItem("refreshAddress", path.path);
            sessionStorage.setItem("detailId", null);
            sessionStorage.setItem("pageType", 3);
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
                                        <span> {item.name} </span>{" "}
                                    </div>{" "}
                                </template>{" "}
                                {children}{" "}
                            </Submenu>
                        );
                    } else {
                        return (
                            <div class="menuList">
                                <MenuItem
                                    name={curLevel}
                                    nativeOnClick={() =>
                                        this.goToMoudle(
                                            {
                                                path: item.url
                                            },
                                            curLevel
                                        )
                                    }
                                >
                                    <div
                                        style={{
                                            paddingLeft: `${left}vmin !important`,
                                            display: "flex",
                                            alignItems: "center"
                                        }}
                                    >
                                        <Icon type="ios-paper" class="icons" />
                                        <span> {item.name} </span>{" "}
                                    </div>{" "}
                                </MenuItem>{" "}
                            </div>
                        );
                    }
                } else {
                    return (
                        <MenuItem
                            name={level + "-" + index}
                            nativeOnClick={() =>
                                this.goToMoudle(
                                    {
                                        path: item.url
                                    },
                                    level + "-" + index
                                )
                            }
                        >
                            <span style="font-size: 1.66vmin !important">
                                {" "}
                                {item.name}{" "}
                            </span>{" "}
                        </MenuItem>
                    );
                }
            });
        }
    },
    components: {
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
    padding-left: 4.6vmin !important;
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
    border-left: none;
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
    background: transparent;
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
    background: transparent;
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
    width: 16vmin;
    text-overflow: ellipsis;
    white-space: nowrap;
    vertical-align: bottom;
    transition: width 0.2s ease 0.2s;
}

.menu-item span:hover {
    text-overflow: inherit;
    overflow: visible;
    white-space: pre-line; /*合并空白符序列，但是保留换行符。*/
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
    background: transparent;
    color: #fff;
    /* background: #357aa1; */
    height: 5vh;
}

h1 {
    margin: 2% 1% 0 0;
    width: 8%;
    float: left;
}

.ivu-layout-sider >>> .ivu-layout-sider-trigger {
    background: transparent;
}

.ivu-layout {
    background: transparent;
}

.ivu-layout-footer {
    padding: 1.3vmin 5vmin;
    font-size: 1.66vmin;
}

.ivu-menu-light {
    background: transparent;
}

.ivu-menu-submenu >>> .ivu-menu {
    color: #fff;
}

.ivu-menu-item:hover {
    /* color: #595c56; */
    background-color: rgba(50, 103, 156, 0.5);
}

.ivu-menu {
    color: #fff;
    border-radius: 10px;
    /*  background-color: rgba(210, 182, 172, 0.2);*/
    padding: 4px;
}

li.ivu-menu-submenu-has-parent-submenu >>> .ivu-menu-submenu-title:hover,
.ivu-menu-light >>> .ivu-menu-submenu-title:hover {
    color: #6ea4c5;
    background-color: transparent;
}

.ivu-menu-light.ivu-menu-vertical .ivu-menu-item-active:not(.ivu-menu-submenu) {
    background: #269cdaad;
    /*color: #1d5f87 !important;*/
    /* background: #15252f45; */
    color: #fff;
    /* box-shadow: 0px 0px 15px 7px rgb(121, 170, 220) inset;*/
}

.ivu-menu-light.ivu-menu-vertical
    .ivu-menu-item-active:not(.ivu-menu-submenu)::after {
    content: "";
    width: 0;
    height: 0;
    position: absolute;
    left: 100%;
    top: 50%;
    transform: translate(0, -50%);
    border-top: 0.8vmin solid transparent;
    border-bottom: 0.8vmin solid transparent;
    border-left: 1vmin solid #269cdaad;
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
    font-size: 2vmin !important;
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

.ivu-menu-vertical.ivu-menu-light:after {
    background: transparent;
}

.ivu-collapse {
    background: transparent;
}
.ivu-layout-content::-webkit-scrollbar {
    width: 4px;
    height: 4px;
}
.ivu-layout-content::-webkit-scrollbar-thumb {
    border-radius: 5px;
    -webkit-box-shadow: inset 0 0 5px rgba(228, 198, 198, 0.2);
    background: rgba(0, 0, 0, 0.2);
}
.ivu-layout-content::-webkit-scrollbar-track {
    border-radius: 0;
    -webkit-box-shadow: inset 0 0 5px rgba(221, 208, 208, 0.2);
    background: rgba(0, 0, 0, 0.1);
}
</style>