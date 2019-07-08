<template>
    <switchData
        v-bind:Obj="propList"
        :curValue="swicthState"
        :stateProcess="curProcessState"
        v-on="$listeners"
    >
        <!-- <template v-slot:title>
           111
        </template>-->
        <div slot="heard">
            <p
                class="heard"
                style="font-size: 1.6vmin;margin-bottom: 2.5vmin;margin-top: -1vmin;"
            >{{propList.objtypeName}}</p>
            <img
                class="heardImg"
                src="../../../../assets/UM/reset.png"
                v-if="propList.objtypeId == 56 || propList.objtypeId ==10"
                title="复位"
                @click.native="reset"
            >
        </div>
        <div class="switchBtn">
            <i-switch v-model="swicthState" @on-change="confirm" size="large" v-if="$attrs.control">
                <span slot="open">开</span>
                <span slot="close">关</span>
            </i-switch>
        </div>
        <div class="switchContent">
            <Row>
                <Col span="8" v-for="item in curObjState" :key="item.id">
                    <div class="swichImg">
                        <img :src="item.img" class="equipment_state_image" :title="item.val">
                        <span style="font-size: 1.4vmin">{{item.val}}</span>
                    </div>
                </Col>
            </Row>
            <!-- <div class="switchImg" v-for="item in curObjState">
                <img
                    :src="item.img"
                    class="equipment_state_image"
                    :title="item.val"
                > 
                <span >{{item.val}}</span>  
                <img
                    :src="equipmentState.openImage"
                    class="equipment_state_image"
                    :title="equipmentState.openTitle"
                >
                <img
                    :src="equipmentState.faultImage"
                    class="equipment_state_image"
                    :title="equipmentState.faultTitle"
                >
            </div>-->
            <!-- <div class="switchText">
                <span v-for="item in curObjState" >{{item.val}}</span>
                 <span>{{equipmentState.openTitle}}</span>   
                <span>{{equipmentState.faultTitle}}</span> 
            </div>-->
        </div>
    </switchData>
</template>
<script>
import switchData from "./ShowSwitchData";

export default {
    data() {
        return {
            // imageState: [
            //     {
            //         key: "closeState",
            //         val: [
            //             { key: "closeImage", val: "close-open-state" },
            //             { key: "openImage", val: "open-close-state" },
            //             { key: "faultImage", val: "fault-close-state" }
            //         ]
            //     },
            //     {
            //         key: "openState",
            //         val: [
            //             { key: "closeImage", val: "close-close-state" },
            //             { key: "openImage", val: "open-open-state" },
            //             { key: "faultImage", val: "fault-close-state" }
            //         ]
            //     },
            //     {
            //         key: "faultState",
            //         val: [
            //             { key: "closeImage", val: "close-close-state" },
            //             { key: "openImage", val: "open-close-state" },
            //             { key: "faultImage", val: "fault-open-state" }
            //         ]
            //     }
            // ],
            // equipmentState: {
            //     // state: null,
            //     closeImage: "",
            //     openImage: "",
            //     faultImage: "",
            //     openTitle: "开",
            //     closeTitle: "关",
            //     faultTitle: "故障"
            // },
            swicthState: 0,
            objState: [
                {
                    key: "close",
                    val: [
                        {
                            0: "close-close-state",
                            1: "close-open-state"
                        }
                    ]
                },
                {
                    key: "open",
                    val: [
                        {
                            0: "open-close-state",
                            1: "open-open-state"
                        }
                    ]
                },
                {
                    key: "fault1",
                    val: [
                        {
                            0: "fault-close-state",
                            1: "fault-open-state"
                        }
                    ]
                },
                {
                    key: "fault2",
                    val: [
                        {
                            0: "fault-close-state",
                            1: "fault-open-state"
                        }
                    ]
                },
                {
                    key: "far",
                    val: [
                        {
                            0: "far-close-state",
                            1: "far-open-state"
                        }
                    ]
                }
            ],
            objStateTitle: {
                close: "关",
                open: "开",
                fault1: "故障1",
                fault2: "故障2",
                far: "远地"
            },
            curObjState: [],
            curProcessState: {}
        };
    },
    computed: {
        propList() {
            return this.$attrs.propList;
        }
    },
    watch: {
        propList: {
            handler(newVal, oldVal) {
                this.curObjState.length = 0;

                this.transformStateImage();
            },
            deep: true
        }
    },
    components: {
        switchData
    },
    mounted() {
        this.swicthState = this.propList.ObjVal == 1;
        this.transformStateImage();
    },
    methods: {
        transformStateImage() {
            if (!this.propList.ObjVal.length) return;
            let [stateObj] = this.propList.ObjVal;

            this.curProcessState = stateObj; //收集当前OPEN ,CLOSE的状态值

            for (
                let i = 0, stateVal = Object.keys(stateObj);
                i < stateVal.length;
                i++
            ) {
                for (let j = this.objState.length - 1; j > -1; j--) {
                    if (stateVal[i] === this.objState[j].key) {
                        this.replaceImage(
                            stateObj[stateVal[i]],
                            this.objState[j].key,
                            this.objState[j].val
                        );
                    }
                }
            }

            // (this.imageState[this.propList.ObjVal].val).forEach( state => this.replaceImage(state.key,state.val));
        },
        replaceImage(curStateVal, key, [curObjState]) {
            // 加载对应状态图片
            this.curObjState.push({
                img: require("../../../../assets/UM/" +
                    curObjState[curStateVal] +
                    ".png"),
                val: this.objStateTitle[key]
            });

            // this.equipmentState[replaceStr] = require("../../../assets/UM/" +replceImg +".png");
        },
        reset() {
            // this.$emit()
            alert("1");
        },
        confirm(data) {
            this.$nextTick(() => {
                this.swicthState = !data;
            });
            let text = data
                ? "确定打开" +
                  this.propList.objtypeName +
                  this.propList.ObjName +
                  "吗?"
                : "确定关闭 " +
                  this.propList.objtypeName +
                  this.propList.ObjName +
                  " 吗?";
            this.$Modal.confirm({
                render: h => {
                    return h("span", text);
                },
                onOk: () => {
                    this.swicthState = !this.swicthState;
                    this.change();
                }
            });
        },
        change() {
            this.$emit(
                "changeStatus",
                this.propList.id,
                this.swicthState,
                this.propList.datatypeId,
                null
            );
        }
    }
};
</script>
<style scoped>
.equipment_state_image {
    width: 3.5vmin;
    height: 3.5vmin;
    margin-top: 2vmin;
}
.switchContent {
    width: 50%;
    position: absolute;
    right: 0;
    margin-top: 2vh;
}
.switchText {
    font-size: 1.4vmin;
    margin-top: 0.5vmin;
}
.switchText > span {
    display: inline-block;
    width: 30%;
    text-align: center;
}
.switchBtn {
    position: absolute;
    bottom: 5.8vmin;
    left: 3.6vmin;
}
.switchBtn >>> .ivu-switch-inner {
    font-size: 1.4vmin;
    left: 2.5vmin;
}

.switchBtn >>> .ivu-switch:after {
    width: 2vmin;
    height: 2vmin;
    top: 0.2vmin;
}
.switchBtn >>> .ivu-switch-large {
    width: 6.8vmin;
    height: 2.6vmin;
}
.switchBtn >>> .ivu-switch-large.ivu-switch-checked:after {
    left: 4.4vmin;
}
.switchBtn >>> .ivu-switch-checked .ivu-switch-inner {
    left: 2.4vmin;
}
.heardImg {
    position: absolute;
    top: 2vmin;
    right: 0.5vmin;
    width: 1.7vmin;
    height: 1.7vmin;
}
@media (min-width: 1921px) {
    .switchContent {
        margin-top: 0;
    }
    .switchBtn >>> .ivu-switch-inner {
        top: 0.7vmin;
    }
}
@media (max-width: 1920px) {
    .switchContent {
        margin-top: -1vh;
    }
}
</style>

