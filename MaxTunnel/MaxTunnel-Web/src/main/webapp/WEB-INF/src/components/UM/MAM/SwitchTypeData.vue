<template>
    <switch-data v-bind:obj="propList" :curValue="swicthState" v-on="$listeners">
        <div class="switchContent">
            <div class="switchBtn">
                <i-switch v-model="swicthState" @on-change="confirm" size="large">
                    <span slot="open">开</span>
                    <span slot="close">关</span>
                </i-switch>
            </div>
            <div class="switchImg">
                <img
                    :src="equipmentState.closeImage"
                    class="equipment_state_image"
                    :title="equipmentState.closeTitle"
                >
                <img
                    :src="equipmentState.openImage"
                    class="equipment_state_image"
                    :title="equipmentState.openTitle"
                >
            </div>
            <div class="switchText">
                <span>{{equipmentState.closeTitle}}</span>
                <span>{{equipmentState.openTitle}}</span>
            </div>
        </div>
    </switch-data>
</template>

<script>
import switchData from "./ShowSwitchData";

export default {
    name: "开关量数据",
    data() {
        return {
            swicthState: 0,
            imageState: [
                {
                    key: "closeState",
                    val: [
                        { key: "closeImage", val: "close-open-state" },
                        { key: "openImage", val: "open-close-state" }
                    ]
                },
                {
                    key: "openState",
                    val: [
                        { key: "closeImage", val: "close-close-state" },
                        { key: "openImage", val: "open-open-state" }
                    ]
                }
            ],
            equipmentState: {
                closeImage: "",
                openImage: "",
                faultImage: "",
                openTitle: "开",
                closeTitle: "关",
                faultTitle: "故障"
            }
        };
    },
    computed: {
        propList() {
            return this.$attrs.propList;
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
            this.imageState[this.propList.ObjVal].val.forEach(state =>
                this.replaceImage(state.key, state.val)
            );
        },
        replaceImage(replaceStr, replceImg) {
            this.equipmentState[replaceStr] = require("../../../assets/UM/" +
                replceImg +
                ".png");
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
.switchContent {
    width: 50%;
    position: absolute;
    right: 0;
    margin-top: 2vh;
}
.equipment_state_image {
    width: 3.5vmin;
    height: 3.5vmin;
    margin-top: 2vmin;
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
@media (min-width: 1921px) {
    .switchContent {
        margin-top: 2vh;
    }
    .switchBtn >>> .ivu-switch-inner {
        top: 0.7vmin;
    }
}
@media (max-width: 1920px) {
    .switchContent {
        margin-top: 3vh;
    }
}
</style>


