<template>
    <div>
        <ModulePage v-bind="task"></ModulePage>
    </div>
</template>

<script>
import ModulePage from "../../../../components/Common/ModulePage";
import { TunnelService } from "../../../../services/tunnelService";
export default {
    name: "UM_main",
    components: { ModulePage },
    data() {
        return {
            task: {
                moduleName: "计划与任务",
                leftTree: [],
                selected: [0, -1]
            },
            treeNodeJumpUrl: "/UM/planPatrol/query"
        };
    },
    created() {
        this.task.leftTree = [];
        let _this = this;
        let temp1 = {};
        temp1.id = 1;
        temp1.name = "年度计划";
        temp1.url = "/UM/planPatrol/queryAnnualPlan";
        _this.task.leftTree.push(temp1);
        let temp2 = {};
        temp2.id = 2;
        temp2.name = "本月计划";
        temp2.url = "/UM/planPatrol/queryMonthPlan";
        _this.task.leftTree.push(temp2);
        if (
            !sessionStorage["refreshAddress"] ||
            sessionStorage["refreshAddress"].indexOf("/UM/planPatrol") < 0
        ) {
            _this.goToMoudle({ path: _this.task.leftTree[0].url });
            sessionStorage.setItem("selectedName", "");
        }
        // sessionStorage.setItem("refreshAddress", "");
    },
    methods: {
        goToMoudle(path) {
            this.$router.push(path);
        }
    }
};
</script>
<style scoped>
.list .ivu-table-wrapper >>> .ivu-table {
    color: #ffffff !important;
    background-color: #fffdfd00 !important;
}
.list .ivu-table-wrapper >>> .ivu-table:before,
.list .ivu-table-wrapper >>> .ivu-table:after {
    background-color: #fffdfd00 !important;
}
.list .ivu-table-wrapper >>> .ivu-table th,
.ivu-table-wrapper >>> .ivu-table td {
    background-color: #fffdfd00 !important;
    border-bottom: none;
}
.list .ivu-table-wrapper >>> .ivu-btn-primary,
.ivu-table-wrapper >>> .ivu-btn-info {
    background: linear-gradient(to bottom right, #6952dd, #2d0dd3) !important;
    border: none;
}
.ivu-page >>> .ivu-page-total,
.ivu-page >>> .ivu-page-options-elevator {
    color: #fff;
}
</style>
