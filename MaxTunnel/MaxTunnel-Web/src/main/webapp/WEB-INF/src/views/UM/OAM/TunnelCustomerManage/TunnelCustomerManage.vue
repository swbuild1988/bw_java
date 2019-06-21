<template>
    <div>
        <ModulePage v-bind="tunnelCustomer"></ModulePage>
    </div>
</template>

<script>
import ModulePage from "../../../../components/Common/ModulePage";
export default {
    name: "UM_main",
    components: { ModulePage },
    data() {
        return {
            tunnelCustomer: {
                moduleName: "企业客户管理",
                leftTree: [
                    {
                        id: 1,
                        name: "查看",
                        url: "/UM/tunnelCustomer/list"
                    },
                    {
                        id: 2,
                        name: "添加",
                        url: "/UM/tunnelCustomer/add"
                    }
                ],
                selected: [0, -1]
            },
            treeNodeJumpUrl: "/UM/tunnelCustomer/list"
        };
    },
    created() {
        if (
            !sessionStorage["refreshAddress"] ||
            sessionStorage["refreshAddress"].indexOf("/UM/tunnelCustomer") < 0
        ) {
            this.goToMoudle({ path: this.tunnelCustomer.leftTree[0].url });
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