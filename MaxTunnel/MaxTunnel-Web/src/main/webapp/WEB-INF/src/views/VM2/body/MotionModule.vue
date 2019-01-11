<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="Chart">
            <timeline-chart v-bind="data"></timeline-chart>
        </div>
    </div>
</template>

<script>
import { TunnelService } from '../../../services/tunnelService'
import { EnterGalleryService } from '../../../services/enterGalleryService'
import { EnergyConsumptionService } from '../../../services/energyConsumptionService'
import TimelineChart from '../../../components/Common/Chart/TimelineChart'
import ModuleTitle from "../../../components/VM2/ModuleTitle";

export default {
    data() {
        return {
            title: "",
            data: {
                id: "VmMotionModuleChart",
                title: "每月管廊入廊次数及能耗",
                group: [],
                timeline: [],
                series: [],
                units: {
                    x: "kw/km",
                    y: "次",
                    z: "kw"
                },
                labels: {
                    x: "平均每公里能耗",
                    y: "入廊次数",
                    z: "总能耗",
                    group: "管廊"
                }
            }
        };
    },
    components: {
        TimelineChart,
        ModuleTitle
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.title = "运营-能耗";

            this.getData();
        },
        getData() {
            let p1 = TunnelService.getTunnels();
            let p2 = EnterGalleryService.getCountOfEnterEveryMonthAndTunnel();
            let p3 = EnergyConsumptionService.getTotleEnergyEveryMonthAndTunnel();
            let p4 = EnergyConsumptionService.getOneKmEneryEveryMonthAndTunnel();

            Promise.all([p1, p2, p3, p4]).then(result => {
                // console.log("result", result);
                this.data.group = []
                this.data.timeline = []
                this.data.series = []

                this.data.group = result[0].map(function (o) {
                    return o.name;
                })
                this.data.timeline = result[1].map(function (o) {
                    if (o.length && o.length > 0)
                        return o[0].time;
                    else
                        return "";
                })
                for (let i = 0; i < this.data.timeline.length; i++) {
                    let tmp = [];
                    for (let j = 0; j < this.data.group.length; j++) {
                        // 每公里能耗
                        let x = result[3].find(a => {
                            if (a.length && a.length > 0)
                                return a[0].time == this.data.timeline[i];
                            else
                                return false;
                        }).find(a => a.tunnel === this.data.group[j]).energy;
                        // 入廊次数
                        let y = result[1].find(a => {
                            if (a.length && a.length > 0)
                                return a[0].time == this.data.timeline[i];
                            else
                                return false;
                        }).find(a => a.tunnel === this.data.group[j]).count;
                        // 总能耗
                        let z = result[2].find(a => {
                            if (a.length && a.length > 0)
                                return a[0].time == this.data.timeline[i];
                            else
                                return false;
                        }).find(a => a.tunnel === this.data.group[j]).energy;
                        tmp.push([x, y, z, this.data.group[j], this.data.timeline[i]])
                    }
                    this.data.series.push(tmp);

                }

            }).then(error => {
                if (error) console.log("error", error)
            })
        }
    }
};
</script>

<style scoped>
.Main {
    position: relative;
    width: 100%;
    height: 100%;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.Main .Title {
    width: 100%;
    height: 15%;
}
.Main .Chart {
    margin-left: 1vw;
    width: 90%;
    height: 80%;
}
</style>
