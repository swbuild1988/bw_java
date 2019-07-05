<template>
    <div>
        <ModulePage v-bind="myNews"></ModulePage>
    </div>
</template>

<script>
import ModulePage from "../../../../components/Common/ModulePage";
export default {
    name: "UM_main",
    components: { ModulePage },
    data() {
        return {
            myNews: {
                moduleName: "我的任务",
                leftTree: [
                    {
                        id: 1,
                        name: "查看",
                        url: "/UM/myTasks/query"
                    }
                ],
                selected: [0, -1]
            },
            treeNodeJumpUrl: "/UM/myTasks/query"
        };
    },
    created() {
        if (
            !sessionStorage["refreshAddress"] ||
            sessionStorage["refreshAddress"].indexOf("/UM/myTasks") < 0
        ) {
            this.goToMoudle({ path: this.myNews.leftTree[0].url });
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