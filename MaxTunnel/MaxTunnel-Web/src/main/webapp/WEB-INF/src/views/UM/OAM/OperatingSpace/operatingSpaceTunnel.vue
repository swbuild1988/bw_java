<template>
    <div class="whole">
        <Row class="storeInfo" v-if="stores.length">
            <Col span="12" v-for="(store,index) in stores" :key="index">
                <div class="storeCard">
                    <div class="innerCard">
                        <Row>
                            <Col span="12">
                                <div class="storeName" @click="chooseStore(store)">
                                    <Icon type="cube"></Icon>
                                    <span>{{store.name}}</span>
                                </div>
                                <div class="utilization" v-if="store.values[2].val">
                                    <i-circle
                                        :percent="parseInt(store.values[1].val / store.values[2].val * 100)"
                                    >
                                        <span
                                            class="demo-Circle-inner"
                                            style="font-size:2.66vmin"
                                        >{{ parseInt(store.values[1].val / store.values[2].val * 100) }}%</span>
                                    </i-circle>
                                </div>
                            </Col>
                            <Col span="12">
                                <div class="storeLineCount" v-if="store.values[2].val">
                                    <p
                                        v-for="(line,i) in store.values"
                                        :key="i"
                                        class="lines"
                                    >{{ line.key }}:{{ line.val }}</p>
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
        </Row>
        <div v-else class="noData">建设中</div>
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
            isEquipShow: false
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
    padding-top: 10px;
    padding: 30px;
    background-color: white;
}
.storeName {
    font-size: 3vmin;
    font-weight: bold;
    cursor: pointer;
    margin: 4vmin 0 2vmin 10vmin;
}
.utilization {
    margin-left: 18vmin;
}
.equCount {
    font-size: 2.66vmin;
    margin-top: 2vmin;
    margin-left: 4vmin;
    cursor: pointer;
}
.noLines {
    margin: 12vmin auto;
}
.storeCard {
    margin: 10px 7%;
    height: 28vh;
    width: 86%;
    color: #c6cdd2;
}
.innerCard {
    background: url("../../../../assets/UM/bodyI.png") no-repeat;
    background-size: 100% 100%;
    width: 100%;
    height: 100%;
    position: relative;
}
.green {
    /* color:rgb(25,190,107);*/
}
.red {
    /* color:rgb(255, 102, 0);*/
}
.lines {
    padding: 6px;
    font-size: 2vmin;
}

.storeLineCount {
    font-size: 17px;
    text-align: center;
    margin-top: 4vmin;
    margin-right: 12vmin;
}

.linesInfo >>> .ivu-tooltip-inner {
    font-size: 1.66vmin;
}
.noData {
    font-size: 3.4vmin;
    text-align: center;
    padding-top: 4vmin;
}
</style>
