<template>
    <div id="VMMain">
        <div class="MainContent">
            <div class="VmHead">
                <vm-head></vm-head>
            </div>
            <div class="AlarmModule">
                <alarm-module></alarm-module>
            </div>
            <div class="CommonModule">
                <common-module></common-module>
            </div>
            <div class="EquipmentModule">
                <equipment-module></equipment-module>
            </div>
            <div class="InspectModule">
                <inspect-module></inspect-module>
            </div>
            <div class="MapModule">
                <map-module></map-module>
            </div>
            <div class="MeasureModule">
                <measure-module></measure-module>
            </div>
            <div class="MotionModule">
                <motion-module></motion-module>
            </div>
            <div class="ThreeDimensionsModule">
                <three-dimensions-module ></three-dimensions-module>
            </div>
            <div class="VideoModule">
                <video-module></video-module>
            </div>
        </div>
    </div>
</template>

<script>
import VmHead from "./header/VMHead";
import AlarmModule from "./body/AlarmModule";
import CommonModule from "./body/CommonModule";
import EquipmentModule from "./body/EquipmentModule";
import InspectModule from "./body/InspectModule";
import MapModule from "./body/MapModule";
import MeasureModule from "./body/MeasureModule";
import MotionModule from "./body/MotionModule";
import ThreeDimensionsModule from "./body/ThreeDimensionsModule";
import VideoModule from "./body/VideoModule";
import Vue from 'vue'

export default {
    data() {
        return {
            VMMainContext: [],
            classNameConfig: [],
            extendTemplateNumber: 5
        };
    },
    components: {
        VmHead,
        AlarmModule,
        CommonModule,
        EquipmentModule,
        InspectModule,
        MapModule,
        MeasureModule,
        MotionModule,
        ThreeDimensionsModule,
        VideoModule,
    },
    created() { },
    mounted() { },
    methods: {
    },
};
</script>
<style scoped>
.MainContent {
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    position: absolute;
    background: url("../../assets/VM/vm_main_bg.png") no-repeat;
    background-size: 100% 100%;
    background-color: #031324;
}
.MainContent .VmHead {
    position: absolute;
    top: 0%;
    left: 0%;
    width: 100%;
    height: 10%;
}
.MainContent .CommonModule {
    position: absolute;
    top: 6%;
    left: 0.5%;
    width: 24%;
    height: 26.5%;
}
.MainContent .InspectModule {
    position: absolute;
    top: 33.5%;
    left: 0.5%;
    width: 24%;
    height: 32.5%;
}
.MainContent .EquipmentModule {
    position: absolute;
    top: 67%;
    left: 0.5%;
    width: 24%;
    height: 32.5%;
}
.MainContent .MapModule {
    position: absolute;
    top: 10%;
    left: 25.5%;
    width: 49%;
    height: 56%;
}
.MainContent .MotionModule {
    position: absolute;
    top: 67%;
    left: 25.5%;
    width: 24%;
    height: 32.5%;
}
.MainContent .MeasureModule {
    position: absolute;
    top: 67%;
    left: 50.5%;
    width: 24%;
    height: 32.5%;
}
.MainContent .AlarmModule {
    position: absolute;
    top: 6%;
    left: 75.5%;
    width: 24%;
    height: 26.5%;
}
.MainContent .VideoModule {
    position: absolute;
    top: 33.5%;
    left: 75.5%;
    width: 24%;
    height: 32.5%;
}
.MainContent .ThreeDimensionsModule {
    position: absolute;
    top: 67%;
    left: 75.5%;
    width: 24%;
    height: 32.5%;
}
</style>
