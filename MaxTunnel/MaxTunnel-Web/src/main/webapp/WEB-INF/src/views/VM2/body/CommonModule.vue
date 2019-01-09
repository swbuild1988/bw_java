<template>
    <div class="Main">
        <div class="Title">
            <p>{{title}}</p>
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
import { TunnelService } from '../../../services/tunnelService'

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
        Grid
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
    background: url("../../../assets/VM/vm_module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.Main .Title {
    /*flex 布局*/
    display: flex;
    /*实现垂直居中*/
    align-items: center;
    /*实现水平居中*/
    justify-content: center;

    width: 25%;
    height: 15%;
    background: url("../../../assets/VM/vm_module_title.png") no-repeat;
    background-size: 100% 100%;
}
.Title > p {
    font-size: 1.5vmin;
    color: #fff;
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
    height: 35%;
}
.RunMessage > p {
    margin-left: 2vw;
    font-size: 2.2vmin;
    color: #fff;
}
.total {
    font-size: 2.7vmin;
    color: #fa0;
}
.safe {
    font-size: 2.7vmin;
    color: #0af;
}
</style>
