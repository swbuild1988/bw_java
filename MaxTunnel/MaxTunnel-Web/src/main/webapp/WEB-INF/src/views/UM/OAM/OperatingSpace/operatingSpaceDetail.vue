<template>
    <div class="detailWrapper">
        <p class="detailTitle">{{curTunnel.name + curStore.name + '管线详情'}}</p>
        <Button class="back" type="primary" @click="back">返回</Button>
        <div class="lineTable">
            <Table :columns="columns" :data="linesInfo"></Table>
        </div>
    </div>
</template>

<script>
import { SpaceService } from "../../../../services/spaceService";
import { TunnelService } from "../../../../services/tunnelService";
export default {
    name: "operatingSpaceDetail",
    data() {
        return {
            curStore: null,
            curTunnel: {
                id: null,
                name: ""
            },
            linesInfo: [],
            columns: [
                {
                    title: "管线名称",
                    key: "cableName",
                    align: "center"
                },
                {
                    title: "企业客户",
                    key: "contract",
                    align: "center",
                    render: (h, params) => {
                        return h("div", params.row.contract.company.name);
                    }
                },
                {
                    title: "管线长度",
                    key: "cableLength",
                    align: "center"
                },
                {
                    title: "管线状态",
                    key: "cableStatusName",
                    align: "center"
                },
                {
                    title: "联系人",
                    align: "center",
                    render: (h, params) => {
                        if (params.row.contract.company.customers.length) {
                            return h(
                                "div",
                                params.row.contract.company.customers[0].contact
                            );
                        }
                    }
                },
                {
                    title: "联系方式",
                    align: "center",
                    render: (h, params) => {
                        if (params.row.contract.company.customers.length) {
                            return h(
                                "div",
                                params.row.contract.company.customers[0].tel
                            );
                        }
                    }
                },
                {
                    title: "操作",
                    key: "action",
                    width: 150,
                    align: "center",
                    render: (h, params) => {
                        return h(
                            "Button",
                            {
                                props: {
                                    type: "primary",
                                    size: "small"
                                },
                                style: {
                                    marginRight: "5px"
                                },
                                on: {
                                    click: () => {
                                        this.turnToContractPage(
                                            params.row.contract.id
                                        );
                                    }
                                }
                            },
                            "查看合同详情"
                        );
                    }
                }
            ]
        };
    },
    mounted() {
        this.curTunnel.id = this.$route.params.id;
        this.curStore = this.$route.params.store;
        this.getData();
    },
    methods: {
        getData() {
            SpaceService.getCableList(this.curStore.id).then(
                res => {
                    this.linesInfo = res;
                },
                err => {
                    this.Log.info(err);
                }
            );
            TunnelService.getTunnels().then(res => {
                let curTunnel = res.find(tunnel => {
                    return tunnel.id == this.curTunnel.id;
                });
                this.curTunnel.name = curTunnel.name;
            });
        },
        turnToContractPage(contractId) {
            sessionStorage.setItem(
                "refreshAddress",
                "/UM/tunnelContract/detail"
            );
            this.$router.push({
                name: "合同详情",
                params: {
                    contractId: contractId,
                    type: 1
                }
            });
        },
        back() {
            this.$router.back(-1);
        }
    }
};
</script>

<style scoped>
.detailWrapper {
    width: 100%;
    min-height: 100%;
    margin: 0 auto;
    overflow: hidden;
    position: relative;
    color: #fff;
}
.detailTitle {
    font-size: 2.4vmin;
    margin: 4vmin;
    text-align: center;
}
.lineTable {
    margin: 0 2vmin;
}
.back {
    position: absolute;
    top: 4vmin;
    right: 4vmin;
    background: -webkit-linear-gradient(left, #e49b9b, #f61a1a);
    background: -o-linear-gradient(right, #e49b9b, #f61a1a);
    background: -moz-linear-gradient(right, #e49b9b, #f61a1a);
    background: linear-gradient(to right, #e49b9b, #f61a1a);
    border-color: #61a2b3;
    border-radius: 1vmin;
    font-size: 1.4vmin !important;
    color: #fff;
}
.lineTable .ivu-table-wrapper {
    border: none;
}
.lineTable .ivu-table-wrapper >>> .ivu-table:before,
.lineTable .ivu-table-wrapper >>> .ivu-table:after {
    background-color: #fffdfd00 !important;
}
.lineTable .ivu-table-wrapper >>> .ivu-table {
    color: #ffffff !important;
    background-color: #fffdfd00 !important;
}
.lineTable .ivu-table-wrapper >>> .ivu-table th,
.ivu-table-wrapper >>> .ivu-table td {
    background-color: #fffdfd00 !important;
    border-bottom: none;
}
.lineTable .ivu-table-wrapper >>> .ivu-btn-primary,
.ivu-table-wrapper >>> .ivu-btn-info {
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border: none;
}
</style>
