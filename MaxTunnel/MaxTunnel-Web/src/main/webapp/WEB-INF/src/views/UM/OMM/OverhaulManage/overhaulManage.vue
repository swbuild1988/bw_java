<template>
    <div>
        <ModulePage v-bind="overhaul"></ModulePage>
    </div>
</template>

<script>
import { TunnelService } from "../../../../services/tunnelService";
import ModulePage from "../../../../components/Common/ModulePage";
export default {
    name: "UM_main",
    components: { ModulePage },
    data() {
        return {
            overhaul: {
                moduleName: "检修管理",
                leftTree: [],
                selected: [0, -1]
            },
            treeNodeJumpUrl: "/UM/overhaul/query"
        };
    },
    created() {
        this.overhaul.leftTree = [];
        let _this = this;
        TunnelService.getTunnels().then(
            result => {
                result.forEach(a => {
                    let temp = {};
                    temp.id = a.id;
                    temp.name = a.name;
                    temp.url = "/UM/overhaul/query/" + a.id;
                    _this.overhaul.leftTree.push(temp);
                });
                if (
                    !sessionStorage["refreshAddress"] ||
                    sessionStorage["refreshAddress"].indexOf("/UM/overhaul") < 0
                ) {
                    _this.goToMoudle({ path: _this.overhaul.leftTree[0].url });
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