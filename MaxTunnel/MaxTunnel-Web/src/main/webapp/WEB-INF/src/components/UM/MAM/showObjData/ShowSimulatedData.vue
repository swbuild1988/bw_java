<template>
    <div @click="locationEquimpent" class="detailContext">
        <Card class="clickStatic" :style="{backgroundColor:click?'#a1cacb':'transparent'}">
            <p
                slot="title"
                style="font-size: 1.66vmin;height: 1.7vmin;line-height: 1.66vmin"
            >{{Obj.objtypeName}}</p>
            <div class="monitorType">{{Obj.ObjName}}</div>
            <!-- 数值正常时 -->
            <img
                src="../../../../assets/UM/temp-normal.png"
                v-if="Obj.objtypeId==1 && normal || Obj.objtypeId==8 && normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/humidity-normal.png"
                v-if="Obj.objtypeId==2 && normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/O2-normal.png"
                v-if="Obj.objtypeId==3 && normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/H2S-normal.png"
                v-if="Obj.objtypeId==4 && normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/CH4-normal.png"
                v-if="Obj.objtypeId==5 && normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/liquidLevel-normal.png"
                v-if="Obj.objtypeId==21 && normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/CO-normal.png"
                v-if="Obj.objtypeId==6 && normal"
                class="img"
            >

            <img
                src="../../../../assets/UM/temp-error.png"
                v-if="Obj.objtypeId==1 && inRange  && !normal || Obj.objtypeId==8 && inRange  && !normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/humidity-error.png"
                v-if="Obj.objtypeId==2 && inRange && !normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/O2-error.png"
                v-if="Obj.objtypeId==3  && inRange && !normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/H2S-error.png"
                v-if="Obj.objtypeId==4  && inRange && !normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/CH4-error.png"
                v-if="Obj.objtypeId==5  && inRange && !normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/liquidLevel-error.png"
                v-if="Obj.objtypeId==21  && inRange && !normal"
                class="img"
            >
            <img
                src="../../../../assets/UM/CO-error.png"
                v-if="Obj.objtypeId==6 && inRange && !normal"
                class="img"
            >
            <img src="../../../../assets/UM/fault.png" class="img" v-if="!inRange">
            <p class="value">
                {{ inRange ? Obj.ObjVal : '故障' }}
                <span
                    style="font-size: 2vmin"
                    v-if="inRange"
                >{{ Obj.unit }}</span>
            </p>
            <p class="time" v-if="isTimeShow">采集时间：{{ Obj.time }}</p>
            <div class="extre">
                <div class="min" v-if="Obj.minNormal != null">
                    <Icon type="arrow-up-c"></Icon>
                    <span>{{Obj.minNormal}}</span>
                </div>
                <div class="max" v-if="Obj.maxNormal != null">
                    <Icon type="arrow-down-c"></Icon>
                    <span>{{Obj.maxNormal}}</span>
                </div>
                <div class="historyData">
                    <button class="historyDataBtn" @click="queryHistoryData(Obj)">历史数据</button>
                </div>
            </div>
        </Card>
    </div>
</template>

<script>
import Temperature from "../../../../components/Common/SimulateData/Temperature";
import Gas from "../../../../components/Common/SimulateData/Gas";

export default {
    name: "show-simulated-data",
    props: {
        Obj: {
            type: Object,
            default: {
                id: "",
                objtypeName: "",
                ObjName: "",
                ObjVal: "",
                imgUrl: "",
                objtypeId: "",
                GasMax: 100,
                GasMin: 0,
                clickStatus: false,
                datatypeId: "",
                minValue: 0,
                maxValue: 100,
                time: ""
            }
        }
    },
    data: function() {
        return {
            gasMax: 200,
            gasMin: 0,
            normal: true,
            isTimeShow: false,
            inRange: true,
            click: false,
            clickTimer: null
        };
    },
    components: {
        Temperature,
        Gas
    },
    methods: {
        //定位设备
        locationEquimpent() {
            this.Obj.clickStatus = !this.Obj.clickStatus;
            this.click = !this.click;
            if (this.click) {
                let _this = this;
                this.clickTimer = setTimeout(() => {
                    _this.click = false;
                }, 2000);
            }
            this.$emit(
                "changeStatus",
                this.Obj.id,
                this.Obj.ObjVal,
                this.Obj.datatypeId,
                this.Obj.clickStatus
            );
        },
        queryHistoryData(obj) {
            this.$router.push({
                name: "数据查询",
                params: {
                    id: obj.id
                }
            });
            event.stopPropagation();
        },
        checkValue() {
            this.normal =
                this.Obj.minNormal != null &&
                this.Obj.maxNormal != null &&
                (this.Obj.ObjVal < this.Obj.minNormal ||
                    this.Obj.ObjVal > this.Obj.maxNormal)
                    ? false
                    : true;
            this.inRange =
                this.Obj.minValue != null &&
                this.Obj.maxValue != null &&
                (this.Obj.ObjVal < this.Obj.minValue ||
                    this.Obj.ObjVal > this.Obj.maxValue)
                    ? false
                    : true;
        }
    },
    watch: {
        "Obj.ObjVal": function() {
            this.checkValue();
        }
    },
    mounted() {
        this.checkValue();

        if (this.Obj.time != "") {
            this.isTimeShow = true;
        }
    },
    beforeDestroy() {
        clearTimeout(this.clickTimer);
    }
};
</script>

<style scoped>
.detailContext {
    background: url("../../../../assets/UM/detailsBg.png") no-repeat;
    background-size: 100% 100%;
}

.valueclass {
    float: left;
    z-index: 1001;
    line-height: 100px;
    height: 100px;
    background-color: #49cecc;
    color: #0f4f5a;
    font-size: 70px;
    font-family: "7LED";
}

.clickStatic:focus,
.clickStatic:hover {
    /* color: #fff;*/
    background-color: #49cecc;
}

.clickStatic {
    background-color: white;
    border-radius: 10px;
    margin: 1vh 0;
    text-align: center;
    height: 28vh;
}

.value {
    margin-top: 2%;
    font-size: 3vmin;
    height: 4vh;
}

.min {
    position: absolute;
    top: 0;
    left: 2%;
    color: rgb(45, 140, 240);
}

.max {
    position: absolute;
    top: 0;
    right: 44%;
    color: red;
}

.extre {
    position: relative;
    height: 3vh;
    font-size: 1.66vmin;
}

.img {
    width: 28%;
    height: 28%;
    margin-top: 1%;
}

.time {
    margin: 0.4vh 0;
    font-size: 1.6vmin;
}

.monitorType {
    font-size: 1.66vmin;
    position: absolute;
    top: 4.5vmin;
    text-align: center;
    width: 82%;
    color: #fff;
}

.ivu-card-bordered >>> .ivu-card-head {
    border-bottom: none;
    padding: 1.4vmin 1.6vmin;
}

.ivu-card-bordered {
    border: none;
}

.ivu-card-head p {
    color: #fff;
}

.ivu-card-bordered >>> .ivu-card-body {
    color: #fff;
    padding: 2.6vmin;
}
.historyData {
    position: absolute;
    top: 0;
    right: 0;
}
.historyDataBtn {
    font-size: 0.8vmin;
    border-radius: 0.4vmin;
    padding: 0 0.4vmin;
}
</style>