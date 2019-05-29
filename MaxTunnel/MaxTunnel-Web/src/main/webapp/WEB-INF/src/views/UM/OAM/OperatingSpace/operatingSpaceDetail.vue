<template>
    <div class="detailWrapper">
        <p class="detailTitle">{{store.name + '管线详情'}}</p>
        <Button class="back" type="primary" @click="back">返回</Button>
        <div class="lineTable">
            <Table :columns="columns" :data="linesInfo"></Table>
        </div>
    </div>
</template>

<script>
import { SpaceService } from "../../../../services/spaceService";
export default {
    name: "operatingSpaceDetail",
    data() {
        return {
            store: null,
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
        this.store = this.$route.params.store;
        this.getData();
    },
    methods: {
        getData() {
            SpaceService.getCableList(this.store.id).then(
                res => {
                    this.linesInfo = res;
                },
                err => {
                    this.Log.info(err);
                }
            );
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
    font-size: 2vmin;
    margin: 2vmin;
}
.lineTable {
    margin: 0 2vmin;
}
.back {
    position: absolute;
    top: 2vmin;
    right: 2vmin;
}
.lineTable .ivu-table-wrapper {
    border: none;
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
