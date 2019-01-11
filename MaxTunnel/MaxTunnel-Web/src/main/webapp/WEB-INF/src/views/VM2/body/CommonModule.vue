<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="TunnelMessage">
            <grid v-bind="tunnelMessage"></grid>
        </div>
        <div class="LineMessage">
            <grid v-bind="lineMessage"></grid>
        </div>
        <div class="RunMessage">
            <p>
                管廊已运行<span class="total">{{runMessage.total}}</span>天
            </p>
            <p>
                管廊连续安全运行<span class="safe">{{runMessage.safe}}</span>天
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
                data: []
            },
            lineMessage: {
                title: "管线信息",
                data: []
            },
            runMessage: {
                total: 50,
                safe: 20
            }
        };
    },
    components: {
        Grid,
        ModuleTitle
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.title = "基本信息";
            TunnelService.getVmTunnelsMessage().then(
                result => {
                    this.tunnelMessage.data = result
                },
                error => {
                    this.Log.info(error)
                })
            TunnelService.getVmLineMessage().then(
                result => {
                    this.lineMessage.data = result
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
.Main {
    width: 100%;
    height: 100%;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.Main .Title {
    width: 100%;
    height: 15%;
}
.Main .TunnelMessage {
    float: left;
    width: 50%;
    height: 50%;
}
.Main .LineMessage {
    float: left;
    width: 50%;
    height: 50%;
}
.Main .RunMessage {
    float: left;
    width: 100%;
    height: 34%;
}
.RunMessage > p {
    margin-left: 2vw;
    font-size: 2vmin;
    color: #fff;
}
.total {
    font-size: 2.4vmin;
    color: #fa0;
}
.safe {
    font-size: 2.4vmin;
    color: #0af;
}
</style>
