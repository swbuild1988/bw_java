<template>
    <ModulePage v-bind="curModule"></ModulePage>
</template>

<script>
import ModulePage from "../../../../components/Common/ModulePage";
import { TunnelService } from "../../../../services/tunnelService";

export default {
    name: "fault-statistics",
    data() {
        return {
            curModule: {
                moduleName: "故障统计",
                leftTree: []
            }
        };
    },
    components: {
        ModulePage
    },
    created() {
        this.curModule.leftTree = [
            {
                id: 0,
                name: "统计总览",
                url: "/UM/FaultStatistics/homePage"
            }
        ];
        let _this = this;

        TunnelService.getTunnels().then(
            result => {
                result.forEach(a => {
                    let temp = {};
                    temp.id = a.id;
                    temp.name = a.name;
                    temp.url = "/UM/FaultStatistics/list/" + a.id;
                    _this.curModule.leftTree.push(temp);
                });
                if (
                    !sessionStorage["refreshAddress"] ||
                    sessionStorage["refreshAddress"].indexOf(
                        "/UM/FaultStatistics"
                    ) < 0
                ) {
                    _this.goToMoudle({
                        path: _this.curModule.leftTree[0].url
                    });

                    sessionStorage.setItem("selectedName", "");
                }
                // sessionStorage.setItem("refreshAddress", "");
            },
            error => {
                _this.Log.info(error);
            }
        );
    },
    methods: {
        goToMoudle(path) {
            this.$router.push(path);
        }
    }
};
</script>