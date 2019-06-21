<template>
    <div>
        <ModulePage v-bind="tunnelDocuments"></ModulePage>
    </div>
</template>

<script>
import { EnumsService } from "../../../../services/enumsService";
import ModulePage from "../../../../components/Common/ModulePage";
export default {
    name: "UM_main",
    components: { ModulePage },
    data() {
        return {
            tunnelDocuments: {
                moduleName: "管廊资料管理",
                leftTree: [],
                selected: [0, -1]
            },
            treeNodeJumpUrl: "/UM/tunnelDocuments/list"
        };
    },
    created() {
        EnumsService.getDocType().then(
            result => {
                result.map(type => {
                    if (type.list.length) {
                        let children = [];
                        type.list.map(item => {
                            children.push({
                                id: item.val,
                                name: item.key,
                                url:
                                    "/UM/tunnelDocuments/list/" +
                                    type.val +
                                    "." +
                                    item.val
                            });
                        });
                        this.tunnelDocuments.leftTree.push({
                            id: type.val,
                            name: type.key,
                            childNode: children
                        });
                    } else {
                        this.tunnelDocuments.leftTree.push({
                            id: type.val,
                            name: type.key,
                            url: "/UM/tunnelDocuments/list/" + type.val
                        });
                    }
                });
                this.tunnelDocuments.leftTree.push({
                    id: result.length + 1,
                    name: "上传文件",
                    url: "/UM/tunnelDocuments/add"
                });
                if (
                    sessionStorage["refreshAddress"] == "" ||
                    sessionStorage["refreshAddress"].indexOf(
                        "/UM/tunnelDocuments"
                    ) < 0
                ) {
                    this.goToMoudle({
                        path: this.tunnelDocuments.leftTree[0].url
                    });
                    sessionStorage.setItem("selectedName", "");
                }
                // sessionStorage.setItem("refreshAddress", "");
            },
            error => {
                this.Log.info(error);
            }
        );
    },
    // watch: {
    // '$route': function(){
    // 	if(this.$route.path == '/UM/tunnelDocuments/list'){
    // 		this.tunnelDocuments.selected = [0,-1]
    // 	}else{
    // 		this.tunnelDocuments.selected = [1,-1]
    // 	}
    // }
    // },
    methods: {
        goToMoudle(path) {
            this.$router.push(path);
        }
    }
};
</script>