<template>
    <div class='SimpleGrid' :id="id">
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
</template>

<script>
export default {
    name: "SimpleGrid",
    props: {
        id: {
            type: String
        },
        requestUrl: {
            type: String
        },
        parameters: {
            type: Object
        }
    },
    data() {
        return {
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
    components: {},
    mounted() {
        this.init();
        this.refreshData();
    },
    methods: {
        init() {
            this.fetchData(this.requestUrl);
        },

        fetchData(requestUrl) {
            // let _this = this;
            // _this.axios.get(requestUrl).then(result => {
            //     let { code, data } = result.data;
            //     if (code == 200) {

            // });
            this.offsetHeight = document.getElementById(this.id).offsetHeight;
            window.onresize = () => {
                this.$refs.unitTable.handleResize();
            };
        },
        //定时刷新数据
        refreshData() {
            // let _this = this;
            // setInterval(() => {
            //     _this.fetchData(_this.requestUrl);
            // }, _this.intervalTime);
        }
    }
};
</script>

<style scoped>
.SimpleGrid {
    width: 100%;
    height: 100%;
}
/*iview table*/
.SimpleGrid >>> .ivu-table-wrapper {
    border: 1px solid transparent;
    border-bottom: 0;
    border-right: 0;
    font-size: 1rem;
}
.SimpleGrid >>> .ivu-table {
    color: #e1e4e5;
    background-color: transparent;
    font-size: 12px !important;
}
.SimpleGrid >>> .ivu-table td {
    background-color: transparent;
    border-bottom: transparent;
}
.SimpleGrid >>> .ivu-table-stripe .ivu-table-body tr:nth-child(2n) td {
    background: transparent;
}
.SimpleGrid >>> .ivu-table:after,
.SimpleGrid >>> .ivu-table:before {
    background-color: transparent;
}
.SimpleGrid >>> .ivu-table-row td:nth-child(2) {
    color: #00fc41;
    font-weight: bold;
    text-align: right;
}
.self-table-wrap >>> .ivu-table-body table {
    width: 100% !important;
}
.ivu-table-overflowY::-webkit-scrollbar {
    width: 5px;
    height: 5px;
}
.ivu-table-overflowY::-webkit-scrollbar-thumb {
    background-color: #3389a2;
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
}
.ivu-table-overflowX {
    overflow-x: hidden;
}
</style>
