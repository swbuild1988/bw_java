<template>
    <div
        id="main"
        class="Main"
    >
        <div
            class="MainLeft clear"
            id="mainLeft"
        >
            <div class="leftTop BackgroundImage clearfloat">
                <div class="VmModel2">
                    <vm-data-model
                        v-if="ModelData2"
                        :modelData="ModelData2"
                    ></vm-data-model>
                </div>
                <div class="VmModel4">
                    <vm-data-model
                        v-if="ModelData4"
                        :modelData="ModelData4"
                    ></vm-data-model>
                </div>
                <div class="VmModel3">
                    <vm-data-model
                        v-if="ModelData3"
                        :modelData="ModelData3"
                    ></vm-data-model>
                </div>
            </div>
            <div class="leftCenter BackgroundImage">
                <vm-video></vm-video>
            </div>
            <div class="leftBottem BackgroundImage">
                <three-dimensional ref="threeDimensional"></three-dimensional>
            </div>
        </div>

        <div
            class="MainCentre BackgroundImage threeDBackgroundImage"
            id="mainCentre"
        >
            <vm-map ref="vmmap" @jumpTo="destroyViewer"></vm-map>
        </div>
    </div>
</template>

<script>
import VmMap from "../../../components/VM/VMBodyCenter/Map";
import VmVideo from "../../../components/VM/VMMainRight/Video";
import ThreeDimensional from "../../../components/VM/VMMainRight/ThreeDimensional";
import VmDataModel from "../../../components/VM/VMDataModel";
import { baseView } from '../mixins/BaseView'

export default {
    mixins: [baseView],
    data() {
        return {
            ModelData1: null,
            ModelData2: null,
            ModelData3: null,
            ModelData4: null,
            ModelData5: null,
            ModelData6: null,
            ModelData7: null
        };
    },
    components: {
        VmMap,
        VmVideo,
        ThreeDimensional,
        VmDataModel
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.initModelData1();
            this.initModelData2();
            this.initModelData3();
            this.initModelData4();
            this.initModelData5();
            this.initModelData6();
            this.initModelData7();
        },
        initModelData1() {
            this.ModelData1 = this.getModelData("vmModel1");
        },
        initModelData2() {
            this.ModelData2 = this.getModelData("vmModel2");
            console.log("vmModelData2", this.ModelData2);
        },
        initModelData3() {
            this.ModelData3 = this.getModelData("vmModel3");
            // this.ModelData3.parameters.option = {
            //     title: {},
            //     tooltip: {},
            //     legend: {
            //         data: []
            //     },
            //     radar: {
            //         name: {
            //             formatter: `{value}`,
            //             textStyle: {}
            //         },
            //         splitArea: {
            //             areaStyle: {
            //                 color: ["#001A27"]
            //             }
            //         }
            //     },
            //     series: [
            //         {
            //             type: "radar"
            //         }
            //     ]
            // };
            console.log("vmModelData3", this.ModelData3);
        },
        initModelData4() {
            this.ModelData4 = this.getModelData("vmModel4");
            // this.ModelData4.parameters.option = {
            //     title: {},
            //     tooltip: {
            //         trigger: "item",
            //         formatter: params =>
            //             `${
            //                 params.name
            //             } : <span style="font-weight: bold;color:${
            //                 params.color
            //             } ;">${params.value}</span> kwh`
            //     },
            //     series: [
            //         {
            //             type: "pie",
            //             radius: [40, 100],
            //             center: ["50%", "50%"],
            //             label: {
            //                 normal: {
            //                     show: false,
            //                     position: "center",
            //                     textStyle: {}
            //                 },
            //                 emphasis: {
            //                     show: true,
            //                     textStyle: {
            //                         fontSize: "10",
            //                         fontWeight: "bold"
            //                     }
            //                 }
            //             },
            //             labelLine: {
            //                 normal: {
            //                     show: false,
            //                     lineStyle: {
            //                         color: "rgba(255, 255, 255, 0.3)"
            //                     },
            //                     smooth: 0.2,
            //                     length: 10,
            //                     length2: 20
            //                 }
            //             },
            //             animationType: "scale",
            //             animationEasing: "elasticOut"
            //         }
            //     ]
            // };
            console.log("vmModelData4", this.ModelData4);
        },
        initModelData5() {
            this.ModelData5 = this.getModelData("vmModel5");
            // this.ModelData5.parameters.option = {
            //     series: [
            //         {
            //             type: "line",
            //             areaStyle: {
            //                 normal: {
            //                     color: new this.$echarts.graphic.LinearGradient(
            //                         0,
            //                         0,
            //                         0,
            //                         1,
            //                         [
            //                             {
            //                                 offset: 0,
            //                                 color: "#699DCE"
            //                             },
            //                             {
            //                                 offset: 1,
            //                                 color: "#05202E"
            //                             }
            //                         ]
            //                     )
            //                 }
            //             }
            //         }
            //     ]
            // };
            console.log("vmModelData5", this.ModelData5);
        },
        initModelData6() {
            this.ModelData6 = this.getModelData("vmModel6");
            // this.ModelData6.parameters.option = {
            //     xAxis: {
            //         axisLabel: {
            //             interval: 0,
            //             rotate: 40
            //         }
            //     }
            // };
            console.log("vmModelData6", this.ModelData6);
        },
        initModelData7() {
            this.ModelData7 = this.getModelData("vmModel7");
            // this.ModelData7.parameters.option = {};
            console.log("vmModelData7", this.ModelData7);
        },
        destroyViewer() {
            this.$refs.threeDimensional.destroyViewer()
        }
    },
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
#main .MainLeft {
    /* padding-left: 0.7%; */
    width: 24.8%;
    height: 100%;
    position: absolute;
    /* top: 14%; */
}

#mainLeft .VmModel2,
#mainLeft .VmModel4,
#mainLeft .VmModel3 {
    position: absolute;
    width: 50%;
    height: 50%;
}
#mainLeft .VmModel2 {
    right: 0;
}
#mainLeft .VmModel4 {
    top: 50%;
}
#mainLeft .VmModel3 {
    top: 50%;
    right: 0;
}
.clearfloat:after {
    content: "";
    display: block;
    height: 0;
    clear: both;
    visibility: hidden;
}

#mainLeft .leftBottem,
#mainLeft .leftTop,
#mainLeft .leftCenter {
    position: relative;
    width: 100%;
    height: 33%;
    margin-bottom: 1%;
}
/*body conter*/
/* #main .MainCentre{
    position: absolute;
    height: 94%;
    top: 6%;
} */
#main .MainCentre {
    position: absolute;
    height: 94%;
    top: 6%;
    /* left: 15.7%;
    width: 59%; */
    left: 25%;
    width: 74.8%;
}
#mainCentre >>> .Map {
    position: relative;
    width: 100%;
    /* height: 71%; */
    height: 99.5%;
    /* margin-bottom: 0.5%; */
    overflow: hidden;
}
/*body Centre Bottom*/
#mainCentre .MainCentreBottom {
    width: 100%;
    height: 28%;
}
#mainCentreBottom >>> .VmModel4,
#mainCentreBottom >>> .VmModel5,
#mainCentreBottom >>> .VmModel6 {
    position: relative;
    height: 100%;
    width: 24.5%;
    margin-right: 0.15%;
    display: inline-block;
    vertical-align: top;
}
#mainCentreBottom >>> .VmModel7 {
    position: relative;
    height: 100%;
    width: 24%;
    display: inline-block;
    vertical-align: top;
}
/*body right*/
#main .MainRight {
    right: 0.7%;
    width: 24%;
}
#mainRight >>> .VideoContainer {
    position: relative;
    height: 61%;
    margin-bottom: 1%;
}
#mainRight >>> .ThreeDimensionalContainer {
    position: relative;
    /* height: 38%; */
    height: 34.5%;
    margin-top: 10%;
}
.BackgroundImage {
    padding: 1.3%;
    border-radius: 0.5rem;
    border: 1px solid rgba(0, 107, 149, 0.5);
    box-shadow: 0 0 1.5rem rgba(0, 107, 149, 1) inset;
}
.threeDBackgroundImage {
    padding: 0.3%;
}
.BodyCenterPadding {
    padding: 0.3%;
}
</style>
