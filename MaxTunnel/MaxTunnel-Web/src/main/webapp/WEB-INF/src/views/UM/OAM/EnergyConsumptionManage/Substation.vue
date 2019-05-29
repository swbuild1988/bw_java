<template>
    <div class="substationWrapper">
        <div class="title">{{curTunnel.name + '变电所信息'}}</div>
        <Table :columns="powerSubstationColumns" :data="powerSubstationsData" style="margin:20px;"></Table>
    </div>
</template>
<script>
import PowerSubstationService from "../../../../services/powerSubstationService";
import { TunnelService } from "../../../../services/tunnelService";
export default {
    data() {
        return {
            powerSubstationColumns: [
                // {
                //     title: '所属管廊',
                //     key: 'tunnel',
                //     align: 'center',
                //     render: (h,params)=>{
                //         return h('div',params.row.tunnel.name)
                //     }
                // },
                {
                    title: "进线电压监测对象",
                    key: "invId",
                    align: "center"
                    // render: (h,params) => {
                    //     return h('div',params.row.inv.name)
                    // }
                },
                {
                    title: "进线电流监测对象",
                    key: "inaId",
                    align: "center"
                    // render: (h,params) => {
                    //     return h('div',params.row.ina.name)
                    // }
                },
                {
                    title: "出线电压监测对象",
                    key: "outvId",
                    align: "center"
                    // render: (h,params) => {
                    //     return h('div',params.row.outv.name)
                    // }
                },
                {
                    title: "出线电流监测对象",
                    key: "outaId",
                    align: "center"
                    // render: (h,params) => {
                    //     return h('div',params.row.outa.name)
                    // }
                },
                {
                    title: "实时功率监测对象",
                    key: "powerId",
                    align: "center"
                    // render: (h,params) => {
                    //     return h('div',params.row.power.name)
                    // }
                },
                {
                    title: "供电半径",
                    key: "eleRadius",
                    align: "center"
                },
                {
                    title: "功率因数",
                    key: "powerFactor",
                    align: "center"
                },
                {
                    title: "功率补偿因数",
                    key: "compensationFactor",
                    align: "center"
                },
                {
                    title: "位置信息",
                    key: "position",
                    align: "center"
                },
                {
                    title: "创建时间",
                    key: "time",
                    align: "center",
                    render: (h, params) => {
                        let crtTime = params.row.time;
                        let time =
                            crtTime == null
                                ? ""
                                : new Date(crtTime).format(
                                      "yyyy-MM-dd hh:mm:ss"
                                  );
                        return h("div", time);
                    }
                }
            ],
            powerSubstationsData: [],
            curTunnel: {
                id: null,
                name: ""
            }
        };
    },
    mounted() {
        this.curTunnel.id = this.$route.params.id;
        this.init();
    },
    watch: {
        $route: function() {
            this.curTunnel.id = this.$route.params.id;
            this.init();
        }
    },
    methods: {
        init() {
            let param = {
                tunnelId: this.curTunnel.id
            };
            PowerSubstationService.getSubstationList(param).then(
                res => {
                    this.powerSubstationsData = res;
                },
                error => {
                    this.Log.info(error);
                }
            );
            TunnelService.getTunnels().then(
                res => {
                    let curTunnel = res.find(tunnel => {
                        return tunnel.id == this.curTunnel.id;
                    });
                    this.curTunnel.name = curTunnel.name;
                },
                error => {
                    this.Log.info(error);
                }
            );
        }
    }
};
</script>
<style scoped>
.substationWrapper {
    height: 100%;
    background: url("../../../../assets/UM/bodyBg.png") no-repeat;
    background-size: 100% 100%;
    overflow: hidden;
}
.title {
    font-size: 2.4vmin;
    margin: 2vmin 0 4vmin 0;
    font-weight: bold;
    color: #fff;
    text-align: center;
}
.substationWrapper .ivu-table-wrapper {
    border: none;
}
.substationWrapper .ivu-table-wrapper >>> .ivu-table:before,
.substationWrapper .ivu-table-wrapper >>> .ivu-table:after {
    background-color: #fffdfd00 !important;
}
.substationWrapper .ivu-table-wrapper >>> .ivu-table {
    color: #ffffff !important;
    background-color: #fffdfd00 !important;
}
.substationWrapper .ivu-table-wrapper >>> .ivu-table th,
.ivu-table-wrapper >>> .ivu-table td {
    background-color: #fffdfd00 !important;
    border-bottom: none;
}
.substationWrapper .ivu-table-wrapper >>> .ivu-btn-primary,
.ivu-table-wrapper >>> .ivu-btn-info {
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border: none;
}
</style>


