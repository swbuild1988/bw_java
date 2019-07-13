<template>
    <switchData v-bind:Obj="propObj" :stateProcess="curProcessState" @getSwicthState="changeSwicthState"
        v-on="$listeners">
        <div slot="heard" @click="reset">
            <img class="heardImg" src="../../../../assets/UM/reset.png" v-if="$attrs.reset" title="复位">
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
                    <span style="font-size: 1.32vmin">{{item.val}}</span>
                </div>
                </Col>
            </Row>
        </div>
    </switchData>
</template>
<script>
    import switchData from "./ShowSwitchData";

    export default {
        data() {
            return {
                swicthState: false,
                objState: [{
                        key: "close",
                        val: {
                            0: "close-close-state",
                            1: "close-open-state"
                        }
                    },
                    {
                        key: "open",
                        val: {
                            0: "open-close-state",
                            1: "open-open-state"
                        }
                    },
                    {
                        key: "run",
                        val: {
                            0: "open-close-state",
                            1: "open-open-state"
                        }
                    },
                    {
                        key: "fault",
                        val: {
                            0: "fault-close-state",
                            1: "fault-open-state"
                        }
                    },
                    {
                        key: "fault1",
                        val: {
                            0: "fault-close-state",
                            1: "fault-open-state"
                        }
                    },
                    {
                        key: "fault2",
                        val: {
                            0: "fault-close-state",
                            1: "fault-open-state"
                        }
                    },
                    {
                        key: "remote",
                        val: {
                            0: "far-close-state",
                            1: "far-open-state"
                        }
                    }
                ],
                curObjState: [],
                curProcessState: {}
            };
        },
        computed: {
            propObj() {
                return this.$attrs.propObj;
            }
        },
        watch: {
            propObj: {
                handler(newVal, oldVal) {
                    this.curObjState = [];

                    this.transformStateImage();
                },
                deep: true
            }
        },
        components: {
            switchData
        },
        mounted() {
            // this.sort().then(() => this.transformStateImage());
            // this.stateSort();

            this.transformStateImage();
        },
        methods: {
            transformStateImage() {
                let stateObj = this.propObj.ObjVal;

                this.curProcessState = stateObj; //收集当前OPEN ,CLOSE的状态值
                let stateVal = Object.keys(stateObj);

                for (let i = 0; i < stateVal.length; i++) {
                    for (let j = this.objState.length - 1; j > -1; j--) {
                        if (stateVal[i] === this.objState[j].key) {
                            this.replaceImage(
                                stateObj[stateVal[i]].value ? 1 : 0,
                                this.objState[j].val,
                                stateObj[stateVal[i]].descript,
                                stateObj[stateVal[i]].index
                            );
                        }
                    }
                }
            },
            replaceImage(curStateVal, val, descript, item) {
                // 加载对应状态图片
                this.curObjState.push({
                    img: require("../../../../assets/UM/" +
                        val[curStateVal] +
                        ".png"),
                    val: descript,
                    index: item
                });
            },
            reset() {
                this.$emit("reset", this.propObj.id);
            },
            confirm(data) {
                let text = data ?
                    "确定打开" +
                    this.propObj.objtypeName +
                    this.propObj.ObjName +
                    "吗?" :
                    "确定关闭 " +
                    this.propObj.objtypeName +
                    this.propObj.ObjName +
                    " 吗?";
                this.$Modal.confirm({
                    render: h => {
                        return h("span", text);
                    },
                    onOk: () => {
                        this.change();
                    }
                });
            },
            change() {
                this.$emit(
                    "changeStatus",
                    this.propObj.id,
                    this.swicthState,
                    this.propObj.datatypeId,
                    null
                );
            },
            changeSwicthState(id, state) {
                this.swicthState = state == 1;
            },
            stateSort() {
                console.log(this.curObjState);
            }
        }
    };
</script>
<style scoped>
    .equipment_state_image {
        width: 3.5vmin;
        height: 3.5vmin;
        margin-top: 1vmin;
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

    .switchText>span {
        display: inline-block;
        width: 30%;
        text-align: center;
    }

    .switchBtn {
        position: absolute;
        bottom: 5.8vmin;
        left: 3.6vmin;
    }

    .switchBtn>>>.ivu-switch-inner {
        font-size: 1.4vmin;
        left: 2.5vmin;
    }

    .switchBtn>>>.ivu-switch:after {
        width: 2vmin;
        height: 2vmin;
        top: 0.2vmin;
    }

    .switchBtn>>>.ivu-switch-large {
        width: 6.8vmin;
        height: 2.6vmin;
    }

    .switchBtn>>>.ivu-switch-large.ivu-switch-checked:after {
        left: 4.4vmin;
    }

    .switchBtn>>>.ivu-switch-checked .ivu-switch-inner {
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
            margin-top: -1vh;
            height: 18vmin;
            display: flex;
            align-items: center;
        }

        .switchBtn>>>.ivu-switch-inner {
            top: 0.7vmin;
        }
    }

    @media (max-width: 1920px) {
        .switchContent {
            margin-top: -1vh;
            height: 20vmin;
            display: flex;
            align-items: center;
        }
    }
</style>