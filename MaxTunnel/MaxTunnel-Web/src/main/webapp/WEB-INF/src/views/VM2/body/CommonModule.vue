<template>
    <div class="main">
        <div class="commonTitle">
            <module-title :title="title"></module-title>
        </div>
        <div class="tunnelMessage">
            <grid v-bind="tunnelMessage"></grid>
        </div>
        <div class="lineMessage">
            <grid v-bind="lineMessage"></grid>
        </div>
        <div class="runMessage">
            <!-- <p>
                管廊已运行<span class="total">{{runMessage.total}}</span>天
            </p> -->
            <p>
                安全运行<span class="safe">{{runMessage.safe}}</span>天
            </p>
        </div>
    </div>
</template>

<script>
import Grid from "../../../components/VM2/Grid1";
import ModuleTitle from "../../../components/VM2/ModuleTitle";
import { TunnelService } from '../../../services/tunnelService';

export default {
    data() {
        return {
            title: "",
            tunnelMessage: {
                title: "管廊信息",
                subTitle: ['状态', '数量', '占比'],
                data: []
            },
            lineMessage: {
                title: "管线信息",
                subTitle: ['类型', '长度', '利用率'],
                data: []
            },
            runMessage: {
                total: 50,
                safe: 20
            },
            refresh: {
                time: 300000,
                intervalId: null
            }
        };
    },
    components: {
        Grid,
        ModuleTitle
    },
    mounted() {
        this.init();
        let _this = this
        this.refresh.intervalId = setInterval(() => {
            _this.init()
        }, this.refresh.time)
    },
    beforeDestory() {
        clearInterval(this.refresh.intervalId)
    },
    methods: {
        init() {
            this.title = "基本信息";
            TunnelService.getVmTunnelsMessage().then(
                result => {
                    this.tunnelMessage.data = result.slice(0, 3)
                    this.tunnelMessage.data.forEach(tunnel => {
                        tunnel.percent = parseInt(tunnel.percent.replace('%', '')).toFixed(0) + '%'

                    })
                },
                error => {
                    this.Log.info(error)
                })
            TunnelService.getVmLineMessage().then(
                result => {
                    this.lineMessage.data = result.slice(0, 3)
                    this.lineMessage.data.forEach(line => {
                        line.value = line.value.toFixed(0)
                        line.percent = parseInt(line.percent.replace('%', '')).toFixed(0) + '%'
                    })
                },
                error => {
                    this.Log.info(error)
                })
            TunnelService.getVmRunMessage().then(
                result => {
                    this.runMessage.total = result.total;
                    this.runMessage.safe = result.safe;
                },
                error => {
                    this.Log.info(error)
                })
        }
    }
};
</script>

<style scoped>
.main {
    width: 100%;
    height: 100%;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.main .commonTitle {
    width: 100%;
    height: 15%;
}
.main .tunnelMessage {
    float: left;
    width: 50%;
    height: 64%;
}
.main .lineMessage {
    float: left;
    width: 50%;
    height: 64%;
}
.main .runMessage {
    float: left;
    width: 100%;
    height: 20%;
}
.runMessage > p {
    margin-left: 2vw;
    font-size: 2.4vmin;
    color: #fff;
    font-family: UnidreamLED;
    margin-top: -3.2%;
}
.total {
    font-size: 2.4vmin;
    color: #fa0;
    margin: 0 0.8vmin;
}
.safe {
    font-size: 4.4vmin;
    color: #fa0;
    margin: 0 0.8vmin;
}
</style>
