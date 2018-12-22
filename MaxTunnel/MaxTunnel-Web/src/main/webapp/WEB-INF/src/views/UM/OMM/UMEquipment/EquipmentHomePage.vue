<template>
  <div style="padding-left: 10px;">
    <h1 style="line-height: 4vh">设备信息总览</h1>
    <Row>
        <Col span="18">
            <Col span="6">
                <data-box v-bind="equimentTotalNum" ref="dataBox"></data-box>
            </Col>
            <Col span="6">
                <data-box v-bind="equimentRunNum"></data-box>
            </Col>
            <Col span="6">
                <data-box v-bind="spareAFew"></data-box>
            </Col>
            <Col span="6">
                <data-box v-bind="duration"></data-box>
            </Col>
            <Col span="24" style="margin-top:1vh">
                <div class="GISbox" id="GISbox" ref="gisBox">
                    <!-- <sm-viewer
                    :id="id"
                    ref="smViewer"
                    >
                        </sm-viewer>-->
                    <TestSmViewer ref="TestSmViewer"></TestSmViewer>
                        <div class="tunnelProfile">
                        <div>管廊设备概况</div>
                        <div>统计:185</div>
                        <div>风岭北路:40</div>
                        <div>长虹路:30</div>
                        <div>高坡岭路:50</div>
                        <div>凤凰岭路:45</div>
                        <div>佛子岭路:20</div>
                        </div>
                </div>
            </Col>
        </Col>
        <Col span="6" class="chartBox">
            <Col span="24" style="height: 49%;">
            <div style="width: 21.4vw; height:100%;" class="equipmentChartBox">
                    <simple-bar v-bind="equipmentChart"></simple-bar>
            </div>
            </Col>
            <Col span="24" style="height: 49%;">
            <div style="width: 21.4vw; height:100%;" class="equipmentChartDoubleColor">
                    <simple-pie v-bind="equipmentChartDoubleColor"></simple-pie>
            </div>
            </Col>
        </Col>
    </Row>
    <Row style="margin-top:2vh;">
        <Col span="5">
            <div class="circleBox" style="background: #b189a357">
                <process-ring v-bind="spare">
                    <p>{{spare.explain}}</p>
                    <p>{{spare.percent}}%</p>
                </process-ring>
            </div>
        </Col>
        <Col span="5">
            <div class="circleBox" style="background: #6e6ec761">
                <process-ring v-bind="breakDown">
                    <p>{{breakDown.explain}}</p>
                    <p>{{breakDown.percent}}%</p>
                </process-ring>
            </div>
        </Col>
        <Col span="7">
            <Card style="width: 98%">
                <p slot="title">备品备件明细</p>
                <div style="height: 3vh">
                    <Row :gutter="16">
                        <Col span="10" offset="2" v-for="item in spareDetails" :key="item.id" style="line-height: 4vh;">
                            {{item.name}}：{{item.val}}
                        </Col>
                    </Row>
                </div>
            </Card>
        </Col>
        <Col span="7">
            <Card style="width: 98%">
                <p slot="title">最近一条故障信息</p>
                <Table stripe border :columns="breakColumns" height="100" :data="breakData"></Table>
            </Card>
        </Col>
    </Row>
  </div>
</template>

<script>
import DataBox from "../../../../components/Common/Box/DataBox";
import SimpleBar from "../../../../components/Common/Chart/SimpleBarChart";
import SimplePie from "../../../../components/Common/Chart/SimplePieChart";
// import SmViewer from "../../../../components/Common/3D/3DViewer";
import TestSmViewer from "../../../../components/Common/3D/Test3DViewer";
import ProcessRing from "../../../../components/Common/ProcessRing";
import equimentTotalNum from "../../../../assets/UM/equimentTotalNum.png";
import instrumentTool from "../../../../assets/UM/instrumentTool.png";
import faultyEquipment from "../../../../assets/UM/faultyEquipment.png";
import spareEquipment from "../../../../assets/UM/spareEquipment.png";
import { EquipmentService } from "../../../../services/equipmentService";
import Enum from '../../../../../static/Enum.json'

export default {
    name: "equipmentMain",
    data() {
        return {
        id: "GIS_ID",
        breakColumns: [
            {
                title: '缺陷名称',
                align: 'center',
                key: 'name'
            },
            {
                title:"发现时间",
                key:"createTime",
                align: 'center',
                width: 120,
                render: (h, params) => {
                    return h(
                    "div",
                    new Date(params.row.createTime).format("yyyy-MM-dd hh:mm:s")
                    );
                }
            },
            {
                title:"危险等级",
                key:"levelName",
                align: 'center',
                render: (h,params) => {
                    return h(
                        'div',
                        {
                            style:{
                                color:
                                    params.row.level==0 ? 'green'
                                    :  params.row.level==1 ? 'blue'
                                    :  params.row.level==2 ? 'orange'
                                    :  'red' 
                                
                            }
                        },params.row.levelName
                    )
                }
            },
            {
                title:"操作",
                key:"action",
                align: 'center',
                render:(h, params) => {
                    return  h('Button', {
                        props: {
                            type: 'primary',
                            size: 'small'
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                this.enterOrder(params.row.id)
                            }
                        }
                    }, '查看')
                }
            }        
        ],
        breakData: [
            {
                id: 1,
                name: "风机",
                createTime: 1529596800000,
                levelName: '危险',
                level: 2
            }
        ],
        equimentTotalNum: {
            label: "管廊设备总计",
            value: 10,
            imgSrc: equimentTotalNum,
            imgBac: "#CCCCFF",
            valBackground: "#e4bdeb",
            labelBackground: "#CC99CC"
        },
        equimentRunNum: {
            label: "备品设备总计",
            value: 87,
            imgSrc: spareEquipment,
            imgBac: "#999999",
            imgWidth: '90',
            valBackground: "#cccccc",
            labelBackground: "#336666"
        },
        spareAFew: {
            label: "仪表工具总计",
            value: "500",
            imgSrc: instrumentTool,
            imgBac: "#466b91",
            valBackground: "#98ccc2",
            labelBackground: "#b6a8db"
        },
        duration: {
            label: "故障设备总计",
            value: "158",
            imgSrc: faultyEquipment,
            imgBac: "#aaccd2",
            imgWidth: '90',
            valBackground: "#eaba0a",
            labelBackground: "#554d6b"
        },
        equipmentChart: {
            id: "equipmentChartId",
            requestUrl: "/equipments/type/count",
            parameters: {
            option: {
                title: {
                    text: "设备类别"
                }
            }
            }
        },
        equipmentChartDoubleColor: {
            id: "equipmentCharDoubleColortId",
            requestUrl: "/equipments/type/count",
            parameters: {
            option: {
                title: {
                text: "管廊故障设备统计",
                x: "left",
                textStyle: {
                    color: "#2e739b"
                }
                }
            }
            }
        },
        equimentPie: {
            id: "equipmentPieId",
            requestUrl: "/equipments/type/count",
            parameters: {
            option: {
                title: {
                    text: "各类型故障占比"
                }
            }
            }
        },
        spare: {
            id: "spare",
            explain: "管廊设备占比",
            percent: 85,
            strokeWidth: 8,
            radius: window.innerHeight * 0.07,
        },
        breakDown: {
            id: "breakDown",
            explain: "仪表工具占比",
            percent: 40,
            strokeWidth: 8,
            radius: window.innerHeight * 0.07,
        },
        tunnelInfoList: [],
        timer: {
            timeoutId: null,
            intervalId: null,
            sectionId: null //保留上次section
        },
        //备品备件明细
        spareDetails: [
            { id: 1, name: '出库至管廊设备数', val: 102 },
            { id: 2, name: '出库至仪表工具数', val: 23 },
            { id: 3, name: '备品备件当前在库数', val: 156 }
        ]
        };
    },
    components: {
        DataBox,
        SimpleBar,
        SimplePie,
        ProcessRing,
        // SmViewer
        TestSmViewer
    },
    beforeRouteLeave(to, from, next) {
        if (
            to.name == "UMPatrolHomePage" ||
            to.name == "UMDetailEquipment" ||
            to.name == "虚拟巡检" ||
            to.name == "人员定位详情" ||
            to.name == "管廊安防监控列表" ||
            to.name == "管廊环境监控列表" ||
            from.name == "人员定位详情" ||
            from.name == "虚拟巡检" ||
            from.name == "UMDetailEquipment" ||
            from.name == "UMPatrolHomePage" ||
            from.name == "管廊安防监控列表" ||
            from.name == "管廊环境监控列表"
        ) {
            from.meta.keepAlive = true;
            to.meta.keepAlive = true;
            this.$destroy();
            next();
        } else {
            from.meta.keepAlive = false;
            to.meta.keepAlive = false;
            this.$destroy();
            next();
        }
    },
    mounted() {
        this.getEquipmentType();
        this.setGIS();
    },
    methods: {
        //获取管廊设备类别信息
        getEquipmentType() {
            let _this = this;
            EquipmentService.getEquTypeAndCount().then(
                result => {
                _this.tunnelInfoList = result;
                },
                error => {
                _this.Log.info(error);
                }
            );
        },
        //跳转缺陷详情
        goToMoudle2: function(id, type) {
            this.$router.push({
                name: "UMDetailDefect",
                params: {
                    id: id,
                    type: type
                }
            });
        },
        enterOrder(index){
            this.goToMoudle2(index, Enum.pageType.Read);
        },
        setGIS() {
            var gis = document.getElementById("newID");
            gis.style.display = "block";
            gis.style.position = "absolute";
            gis.style.top = "0px";
            gis.style.height = "100%";
            gis.style.width = "99%";
            document.body.removeChild(gis);
            document.getElementById("GISbox").appendChild(gis);
            // 加载视角
            this.$refs.TestSmViewer.setViewAngAngle();
        },
        destory3D() {
            var gis = document.getElementById("newID");
            gis.style.display = "none";
            document.getElementById("GISbox").removeChild(gis);
            document.body.appendChild(gis);
        }
    },
    beforeDestroy() {
        this.destory3D();
    }
};
</script>
<style scoped>
h1,
.ivu-card > .ivu-card-head p {
    color: #2e739b;
}

.tunnelProfile {
    border: 1px solid #dddee1;
    border-radius: 4px;
    position: absolute;
    bottom: 2px;
    right: 13px;
    text-align: left;
    z-index: 2;
    color: #fff;
}

.tunnelProfile div {
    border: 1px solid #dddee1;
    border-bottom: none;
    line-height: 35px;
    text-align: center;
    padding-left: 10px;
    padding-right: 10px;
}

.chartBox {
    height: 100%;
    position: absolute;
    right: 0px;
    margin-right: 10px;
}

.chartBox .ivu-col-span-24 {
    margin-bottom: 10px;
}

.equipmentChartBox,
.equipmentChartDoubleColor {
    border: 1px solid #dddee1;
    border-radius: 4px;
    margin: 10px;
    margin-top: 0px;
}

.GISbox {
    width: 99%;
    height: 44vh;
    border: 1px solid #dddee1;
    border-radius: 4px;
    margin-top: 1vh;
}

ul {
    border: 1px solid #ccc;
    border-radius: 5px;
    padding-left: 5px;
    height: 80px;
    overflow: auto;
}

ul li {
    list-style: none;
    line-height: 20px;
}

.ivu-col-span-12 {
    padding: 10px;
}

.circleBox {
    text-align: center;
    height: 21vh;
    width: 98%;
    display: inline-block;
    vertical-align: middle;
    border: 1px solid #dddee1;
    border-radius: 4px;
}

.ivu-card >>> .ivu-card-body {
    height: 16vh;
    overflow-y: auto;
}
/* .ivu-card >>> .ivu-card-head{
    line-height: 2vh;
} */
</style>



