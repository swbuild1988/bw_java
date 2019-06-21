<template>
    <ModulePage v-bind="curModule"></ModulePage>
</template>

<script>
import ModulePage from "../../../../components/Common/ModulePage";
import { TunnelService } from "../../../../services/tunnelService";

export default {
    name: "tunnel-environment",
    data() {
        return {
            curModule: {
                moduleName: "管廊环境监控",
                leftTree: [
                    {
                        id: "001",
                        name: "总览"
                    }
                ],
                selected: [0, -1]
            }
        };
    },
    components: {
        ModulePage
    },
    created() {
        console.log("jici");
        this.curModule.leftTree = [];
        let _this = this;

        TunnelService.getTunnels().then(
            result => {
                console.log("result", result);
                result.forEach(a => {
                    let temp = {};
                    temp.id = a.id;
                    temp.name = a.name;
                    temp.childNode = [
                        {
                            id: 21,
                            name: "监测总览",
                            url: "/UM/TunnelEnvironment/list/" + a.id
                        },
                        {
                            id: 22,
                            name: "监测详情",
                            url: "/UM/TunnelEnvironment/details/" + a.id
                        }
                    ];
                    _this.curModule.leftTree.push(temp);
                });
                if (
                    !sessionStorage["refreshAddress"] ||
                    sessionStorage["refreshAddress"].indexOf(
                        "/UM/TunnelEnvironment"
                    ) < 0
                ) {
                    _this.goToMoudle({
                        path: _this.curModule.leftTree[0].childNode[0].url
                    });

                    sessionStorage.setItem("selectedName", "");
                }
                // sessionStorage.setItem("refreshAddress", "");
            },
            error => {
                console.log(error);
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