<template>
    <div>
        <ModulePage v-bind="myApplication"></ModulePage>
    </div>
</template>

<script>
import ModulePage from "../../../../components/Common/ModulePage";
export default {
    name: "UM_main",
    components: { ModulePage },
    data() {
        return {
            myApplication: {
                moduleName: "我的申请",
                leftTree: [
                    {
                        id: 1,
                        name: "查看",
                        url: "/UM/myApplication/query"
                    }
                ]
            },
            treeNodeJumpUrl: "/UM/myApplication/query"
        };
    },
    created() {
        if (
            !sessionStorage["refreshAddress"] ||
            sessionStorage["refreshAddress"].indexOf("/UM/myApplication") < 0
        ) {
            this.goToMoudle({ path: this.myApplication.leftTree[0].url });
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