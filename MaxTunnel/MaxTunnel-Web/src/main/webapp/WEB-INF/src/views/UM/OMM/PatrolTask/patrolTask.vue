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
                moduleName: "巡检任务",
                leftTree: [],
                selected: [0, -1]
            },
            treeNodeJumpUrl: "/UM/patrolTask/query"
        };
    },
    created() {
        this.task.leftTree = [];
        let _this = this;
        TunnelService.getTunnels().then(
            result => {
                result.forEach(a => {
                    let temp = {};
                    temp.id = a.id;
                    temp.name = a.name;
                    temp.url = "/UM/patrolTask/query/" + a.id;
                    _this.task.leftTree.push(temp);
                });
                if (
                    !sessionStorage["refreshAddress"] ||
                    sessionStorage["refreshAddress"].indexOf("/UM/patrolTask") <
                        0
                ) {
                    // _this.goToMoudle({path: _this.task.leftTree[0].url});
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