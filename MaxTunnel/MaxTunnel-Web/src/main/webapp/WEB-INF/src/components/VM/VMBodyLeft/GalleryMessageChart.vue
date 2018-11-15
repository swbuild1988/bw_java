<template>
      <div class="BackgroundImage">
        <div class="Information" :id=tableid>
          <Table
                  class="self-table-wrap"
                  ref="unitTable"
                  stripe
                  disabled-hover
                  :height="offsetHeight"
                  :columns="columns"
                  :show-header="false"
                  :data="tableData">
          </Table>
        </div>
        <vm-title :title-text="title"></vm-title>
      </div>
</template>

<style scoped>
.Information {
    position: relative;
    width: 100%;
    height: 85%;
    top: 15%;
}
/*iview table*/
.Information >>> .ivu-table-wrapper {
    border: 1px solid transparent;
    border-bottom: 0;
    border-right: 0;
    font-size: 1rem;
}
.Information >>> .ivu-table {
    color: #e1e4e5;
    background-color: transparent;
    font-size: 12px !important;
}
.Information >>> .ivu-table td {
    background-color: transparent;
    border-bottom: transparent;
}
.Information >>> .ivu-table-stripe .ivu-table-body tr:nth-child(2n) td {
    background: transparent;
}
.Information >>> .ivu-table:after,
.Information >>> .ivu-table:before {
    background-color: transparent;
}
.Information >>> .ivu-table-row td:nth-child(2) {
    color: #00fc41;
    font-weight: bold;
    text-align: right;
}
.self-table-wrap >>> .ivu-table-body table {
    width: 100% !important;
}
#information >>> .ivu-table-overflowY::-webkit-scrollbar {
    width: 5px;
    height: 5px;
}
#information >>> .ivu-table-overflowY::-webkit-scrollbar-thumb {
    background-color: #3389a2;
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
}
#information >>> .ivu-table-overflowX {
    overflow-x: hidden;
}
</style>

<script>
import VmTitle from "../VMTitle";
export default {
    name: "Information",
    props: ["tableid"],
    data() {
        return {
            title: "入廊信息",
            tableData: [
                {
                    data: "12",
                    name: "电力公司电缆",
                    unit: "km"
                },
                {
                    data: "3",
                    name: "自来水公司",
                    unit: "条"
                },
                {
                    data: "4",
                    name: "燃气公司",
                    unit: "条"
                },
                {
                    data: "4",
                    name: "燃气公司",
                    unit: "条"
                },
                {
                    data: "4",
                    name: "燃气公司",
                    unit: "条"
                },
                {
                    data: "4",
                    name: "燃气公司",
                    unit: "条"
                },
                {
                    data: "6",
                    name: "中国移动",
                    unit: "条"
                }
            ],
            columns: [
                {
                    key: "name",
                    width: "130",
                    align: "center",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "span",
                                {
                                    style: {
                                        display: "inline-block",
                                        overflow: "hidden",
                                        textOverflow: "ellipsis",
                                        whiteSpace: "nowrap",
                                        cursor: "pointer"
                                    },
                                    domProps: {
                                        title: params.row.name
                                    }
                                },
                                params.row.name
                            )
                        ]);
                    }
                },
                {
                    key: "data"
                },
                {
                    key: "unit"
                }
            ],
            offsetHeight: 0
        };
    },
    components: {
        VmTitle
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.offsetHeight = document.getElementById(
                this.tableid
            ).offsetHeight;
            window.onresize = () => {
                this.$refs.unitTable.handleResize();
            };
        }
    }
};
</script>
