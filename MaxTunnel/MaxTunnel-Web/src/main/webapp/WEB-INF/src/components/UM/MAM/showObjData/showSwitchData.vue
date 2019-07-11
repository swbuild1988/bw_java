<template>
    <div @click="locationEquimpent" class="detailContext">
        <Card class="clickStatic" :style="{backgroundColor:Obj.clickStatus?'#a1cacb':'transparent'}">
            <p class="heard" style="font-size: 1.6vmin;margin-bottom: 2.5vmin;margin-top: -1vmin;">{{Obj.objtypeName}}</p>
            <slot name="heard">
                <!-- <p class="heard">{{Obj.objtypeName}}</p> -->
            </slot>
            <div class="equipment_image">
                <img src="../../../../assets/UM/lamp-on.png"
                    v-if="Obj.objtypeId==11 && stateProcess.open.value" class="img">
                <img src="../../../../assets/UM/lamp-off.png"
                    v-if="Obj.objtypeId==11 && !stateProcess.open.value" class="img">
                <img src="../../../../assets/UM/lamp-on.png"
                    v-if="Obj.objtypeId==12 && stateProcess.open.value" class="img">
                <img src="../../../../assets/UM/lamp-off.png"
                    v-if="Obj.objtypeId==12 && !stateProcess.open.value" class="img">
                <img src="../../../../assets/UM/风机gif.gif"
                    v-if="Obj.objtypeId==10 && stateProcess.run.value" class="img">
                <img src="../../../../assets/UM/水泵gif.gif"
                    v-if="Obj.objtypeId==59 && stateProcess.run.value" class="img">
                <img src="../../../../assets/UM/shutter-open.png"
                    v-if="Obj.objtypeId==58 && (!stateProcess.open.value && stateProcess.close.value)" class="img">
                <img src="../../../../assets/UM/covers-open.png"
                    v-if="Obj.objtypeId==56 && (!stateProcess.open.value && stateProcess.close.value)" class="img">
                <img src="../../../../assets/UM/红外正常gif.gif"
                    v-if="Obj.objtypeId==57 && !stateProcess.open.value" class="img">
                <img src="../../../../assets/UM/门-开.png"
                    v-if="Obj.objtypeId==55 && (!stateProcess.open.value && stateProcess.close.value)" class="img">
                <img src="../../../../assets/UM/place-troop-open.png" v-if="Obj.objtypeId==63 && curValue.open.value"
                    class="img">
                <img src="../../../../assets/UM/linkage-status-open.png"
                    v-if="Obj.objtypeId==64 && curValue.open.value" class="img">
                <img src="../../../../assets/UM/报警-红gif.gif"
                    v-if="Obj.objtypeId==41 && stateProcess.open.value" class="img">

                <img src="../../../../assets/UM/风机.png"
                    v-if="Obj.objtypeId==10 && !stateProcess.run.value" class="img">
                <img src="../../../../assets/UM/水泵.png"
                    v-if="Obj.objtypeId==59 && !stateProcess.run.value" class="img">
                <img src="../../../../assets/UM/shutter-close.png"
                    v-if="Obj.objtypeId==58 && (stateProcess.open.value && !stateProcess.close.value)" class="img">
                <img src="../../../../assets/UM/covers-close.png"
                    v-if="Obj.objtypeId==56 && (stateProcess.open.value && !stateProcess.close.value)" class="img">
                <img src="../../../../assets/UM/红外.gif"
                    v-if="Obj.objtypeId==57 && stateProcess.open.value" class="img">
                <img src="../../../../assets/UM/门-关.png"
                    v-if="Obj.objtypeId==55 && (stateProcess.open.value && !stateProcess.close.value)" class="img">
                <img src="../../../../assets/UM/报警.png"
                    v-if="Obj.objtypeId==41 && !stateProcess.open.value" class="img">
                <img src="../../../../assets/UM/place-troop-close.png" v-if="Obj.objtypeId==63 && curValue.open.value"
                    class="img">
                <img src="../../../../assets/UM/linkage-status-close.png"
                    v-if="Obj.objtypeId==64 && curValue.open.value" class="img">
                <!-- 过程 -->
                <img src="../../../../assets/UM/covers-process.gif"
                    v-if="Obj.objtypeId==56 && (!stateProcess.open.value && !stateProcess.close.value)" class="img">
                <img src="../../../../assets/UM/shutter-process.gif"
                    v-if="Obj.objtypeId==58 && (!stateProcess.open.value && !stateProcess.close.value)" class="img">
            </div>
            <slot></slot>
            <p class="time" v-if="isTimeShow">采集时间：{{ Obj.time }}</p>
            <div style="margin-top: -12%;font-size: 1.66vmin">{{Obj.ObjName}}</div>
        </Card>
    </div>
</template>

<script>
    //灯组件
    import Light from "../../../../components/Common/SimulateData/Light";
    //风机组件
    import Fans from "../../../../components/Common/SimulateData/Fans";

    export default {
        name: "show-switch-data",
        props: {
            Obj: {
                type: Object,
                default: () => {
                    return {
                        id: "",
                        objtypeName: "",
                        ObjName: "",
                        ObjVal: false,
                        imgUrl: "",
                        objtypeId: "",
                        clickStatus: false,
                        datatypeId: ""
                    };
                }
            },
            curValue: {
                required: true
            },
            // stateProcess: {
            //     type: Object,
            //     default: () => {
            //         return {
            //             open: {
            //                 describe: "",
            //                 value: false
            //             },
            //             close: {
            //                 describe: "",
            //                 value: true
            //             },
            //             run: {
            //                 describe: "",
            //                 value: false
            //             }
            //         };
            //     }
            // }
            stateProcess: 0
        },
        data: function () {
            return {
                isTimeShow: false
            };
        },
        components: {
            Light,
            Fans
        },
        methods: {
            //定位设备
            locationEquimpent() {
                this.Obj.clickStatus = !this.Obj.clickStatus;
                this.$emit(
                    "changeStatus",
                    this.Obj.id,
                    this.Obj.ObjVal,
                    this.Obj.datatypeId,
                    this.Obj.clickStatus
                );
            },
            change() {
                this.$emit(
                    "changeStatus",
                    this.Obj.id,
                    this.curValue,
                    this.Obj.datatypeId,
                    null
                );
            }
        },
        mounted() {
            console.log('proObj',this.propObj)
            // console.log('mounted')
            // console.log('this.obj',this.Obj)
            if (this.Obj.time != "") {
                this.isTimeShow = true;
            }
            // this.equipmentState.state = this.Obj.ObjVal;
            // this.curValue = this.Obj.ObjVal == 1;
            // this.transformStateImage();
        }
    };
</script>

<style scoped>
    .detailContext {
        background: url("../../../../assets/UM/detailsBg.png") no-repeat;
        background-size: 100% 100%;
    }

    .ivu-switch:after {
        width: 2vmin;
        height: 2vmin;
        left: 0.1vmin;
        top: 0.1vmin;
    }

    .ivu-switch-large {
        width: 6vmin;
    }

    .ivu-switch {
        background-color: #9e9e9e;
        height: 2.4vmin;
        line-height: 2.2vmin;
        margin-top: 9%;
    }

    .ivu-switch-checked:after {
        left: 3.7vmin;
    }

    .ivu-switch>>>.ivu-switch-inner {
        font-size: 1.2vmin;
        left: 2.2vmin;
    }

    .ivu-switch-checked {
        background-color: #2d8cf0;
        border-color: 2px solid #2d8cf0;
    }

    .clickStatic:focus,
    .clickStatic:hover {
        /*color: #fff;*/
        border-color: #597ecb;
    }

    .clickStatic {
        background-color: white;
        border-radius: 10px;
        margin: 1vh 0;
        text-align: center;
        height: 28vh;
    }

    /* .switchContent {
    width: 50%;
    position: absolute;
    right: 0;
    margin-top: 2vh;
} */

    .img {
        width: 70%;
        height: 65%;
    }

    .time {
        position: absolute;
        bottom: 0;
        left: 0;
        margin: 1vh 0;
        font-size: 1.6vmin;
        width: 100%;
    }

    .ivu-card-head p {
        height: 2vmin;
        line-height: 2vmin;
    }

    .equipment_image {
        top: 65%;
        width: 50%;
        height: 50%;
        position: absolute;
        transform: translate(0, -50%);
        left: 2%;
    }

    /* .equipment_state_image {
    width: 3.5vmin;
    height: 3.5vmin;
    margin-top: 2vmin; */
    /* } */
    .ivu-card-bordered>>>.ivu-card-head {
        border-bottom: none;
        padding: 1.4vmin 1.6vmin;
    }

    .ivu-card-bordered {
        border: none;
    }

    .ivu-card-head p {
        color: #fff;
    }

    .ivu-card-bordered>>>.ivu-card-body {
        color: #fff;
        padding: 2.6vmin;
    }

    /* .switchText {
    font-size: 1.4vmin;
    margin-top: .5vmin;
}
.switchText > span {
    display: inline-block;
    width: 30%;
    text-align: center;
} */
    .clickStatic .heard {
        font-size: 1.6vmin;
        margin-bottom: 2.5vmin;
        margin-top: -1vmin;
    }

    @media (min-width: 1921px) {
        .equipment_image {
            top: 57%;
            width: 53%;
        }

        /* .switchContent {
        margin-top: 4vh;
    } */
    }

    @media (max-width: 1920px) {
        .equipment_image {
            top: 58%;
            width: 50%;
        }

        /* .switchContent {
        margin-top: 3vh;
    } */
    }
</style>