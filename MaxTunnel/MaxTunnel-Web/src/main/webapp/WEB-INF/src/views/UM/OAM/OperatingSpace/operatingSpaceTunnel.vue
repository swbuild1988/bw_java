<template>
    <div class="whole" v-if="!none">
        <Row class="storeInfo">
            <div class="storeItemsWrapper">
                <Col span="12" v-for="(store,index) in stores" :key="index">
                    <div class="storeCard">
                        <div class="innerCard">
                            <div class="storeName" @click="chooseStore(store)">
                                <Icon type="ios-flag" style="font-size: 3.6vmin;margin-right: 0.6vmin;"></Icon>
                                <span>{{store.name}}</span>
                            </div>
                            <Row>
                                <Col span="12">
                                    <div class="storeLineCount" v-if="store.values[2].val">
                                        <p
                                            v-for="(line,i) in store.values"
                                            :key="i"
                                            class="lines"
                                        >{{ line.key }}:{{ line.val }}</p>
                                    </div>
                                </Col>
                                <Col :span="store.values[2].val ? '12' : '24'">
                                    <div class="utilization" v-if="store.values[2].val">
                                        <i-circle
                                            :percent="parseInt(store.values[1].val / store.values[2].val * 100)"
                                            stroke-color="#1dffe2"
                                        >
                                            <span
                                                class="demo-Circle-inner"
                                                style="font-size:2.66vmin"
                                            >{{ parseInt(store.values[1].val / store.values[2].val * 100) }}%</span>
                                        </i-circle>
                                    </div>
                                    <div
                                        :class="['equCount',{'noLines': !store.values[2].val}]"
                                        @click="turnToEquPage(store.id)"
                                    >
                                        <span>{{'现存放设备' + store.equCount +'个'}}</span>
                                    </div>
                                </Col>
                            </Row>
                        </div>
                    </div>
                </Col>
            </div>
        </Row>
    </div>
    <div v-else class="noData">
        <span>建设中</span>
    </div>
</template>
<script>
import { TunnelService } from "../../../../services/tunnelService";
import { SpaceService } from "../../../../services/spaceService";
import Enum from "../../../../../static/Enum.json";
import { EquipmentService } from "../../../../services/equipmentService";

export default {
    data() {
        return {
            tunnelId: "",
            stores: [],
            curDetailIndex: "",
            curDetailId: "",
            isLineShow: false,
            isEquipShow: false,
            none: false
        };
    },
    mounted() {
        this.tunnelId = this.$route.params.id;
        this.initData();
    },
    watch: {
        $route: function() {
            // $route发生变化时再次赋值planId
            this.tunnelId = this.$route.params.id;
            this.initData();
            this.stores = [];
        }
    },
    methods: {
        initData() {
            let _this = this;
            Promise.all([
                TunnelService.getStoresByTunnelId(this.tunnelId),
                SpaceService.getCableCount(this.tunnelId),
                EquipmentService.getEquipmentCount(this.tunnelId)
            ]).then(
                result => {
                    let store = result[0],
                        lineList = result[1],
                        equList = result[2];
                    if (store.length > 0) {
                        store.forEach(a => {
                            let lines = [];
                            let usage = lineList.find(name => {
                                return name.key === a.name;
                            });
                            let equ = equList.find(count => {
                                return count.id === a.id;
                            });
                            for (let item in usage) {
                                if (item != "key") {
                                    lines.push({
                                        key: item.slice(0, 5),
                                        val: usage[item]
                                    });
                                    if (
                                        lines.length ===
                                        Object.keys(usage).length - 1
                                    ) {
                                        if (lines.length) {
                                            _this.stores.push({
                                                id: a.id,
                                                name: a.name,
                                                values: lines,
                                                equCount: equ.val
                                            });
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        _this.none = true;
                    }
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        chooseStore(store) {
            this.$router.push({
                name: "管廊空间详情",
                params: {
                    store
                }
            });
        },
        turnToEquPage(storeId) {
            sessionStorage.setItem(
                "refreshAddress",
                "/UM/equipment/queryequipment"
            );
            sessionStorage.setItem("selectedName", "1-1-1");
            this.$router.push({
                name: "管廊设备",
                params: {
                    storeId,
                    tunnelId: this.tunnelId
                }
            });
        }
    }
};
</script>
<style scoped>
.whole {
    min-height: 100%;
    position: relative;
}

.innerCard >>> .ivu-chart-circle {
    width: 12vmin !important;
    height: 12vmin !important;
}
.storeInfo {
    padding: 3vmin;
    
    height: 86vh;
    background: url("../../../../assets/UM/bodyBg.png") no-repeat;
    background-size: 100% 100%;
}
.storeName {
    font-size: 3vmin;
    font-weight: bold;
    cursor: pointer;
    margin: 3vmin 0 1vmin 0;
    text-align: center;
}
.utilization {
    margin-top: 2vmin;
    text-align: center;
}
.equCount {
    font-size: 2.66vmin;
    margin-top: 2vmin;
    text-align: center;
    cursor: pointer;
}
.noLines {
    margin-top: 8vmin;
    font-size: 4vmin;
}
.storeCard {
    margin: 2.3vmin 10%;
    height: 36vh;
    width: 80%;
    color: #eaeef2;
}
.innerCard {
    background: url("../../../../assets/UM/border2.png") no-repeat;
    background-size: 100% 100%;
    width: 100%;
    height: 100%;
    position: relative;
    margin: 2vmin 0;
    overflow: hidden;
}

.lines {
    padding: 6px;
    font-size: 2.4vmin;
    line-height: 5.4vmin;
}

.storeLineCount {
    font-size: 17px;
    text-align: center;
    margin-top: 1vmin;
}

.linesInfo >>> .ivu-tooltip-inner {
    font-size: 1.66vmin;
}
.noData {
    text-align: center;
    display: flex;
    height: 100%;
    justify-content: center;
    background: url("../../../../assets/UM/bodyBg.png") no-repeat;
    background-size: 100% 100%;
}
.noData span {
    font-size: 4vmin;
    color: #fff;
    align-self: center;
}
.storeItemsWrapper{
    overflow-y: auto;
    margin: 0.2vmin;
    height: 80vh;
}
.storeItemsWrapper::-webkit-scrollbar {
    /*滚动条整体样式*/
    width: 1vmin; /*高宽分别对应横竖滚动条的尺寸*/
    height: 0.2vmin;
}
.storeItemsWrapper::-webkit-scrollbar-thumb {
    /*滚动条里面小方块*/
    border-radius: 1vmin;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #83a6ed;
}
.storeItemsWrapper::-webkit-scrollbar-track {
    /*滚动条里面轨道*/
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    border-radius: 1vmin;
    background: #ededed;
}
</style>
