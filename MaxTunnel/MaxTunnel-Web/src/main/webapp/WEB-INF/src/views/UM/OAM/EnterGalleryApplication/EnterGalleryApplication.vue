<template>
    <div>
        <ModulePage v-bind="enterGalleryApplication"></ModulePage>
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
            enterGalleryApplication: {
                moduleName: "人员作业申请",
                leftTree: [],
                selected: [0, -1]
            },
            treeNodeJumpUrl: "/UM/enterGalleryApplication/query"
        };
    },
    created() {
        this.enterGalleryApplication.leftTree = [];
        let _this = this;
        TunnelService.getTunnels().then(
            result => {
                result.forEach(a => {
                    let temp = {};
                    temp.id = a.id;
                    temp.name = a.name;
                    temp.url = "/UM/enterGalleryApplication/query/" + a.id;
                    _this.enterGalleryApplication.leftTree.push(temp);
                });
                // let element = {};
                // element.id = result.length;
                // element.name = '制定',
                // element.url= '/UM/enterGalleryApplication/add'
                // this.enterGalleryApplication.leftTree.push(element)
                if (
                    !sessionStorage["refreshAddress"] ||
                    sessionStorage["refreshAddress"].indexOf(
                        "/UM/enterGalleryApplication"
                    ) < 0
                ) {
                    _this.goToMoudle({
                        path: _this.enterGalleryApplication.leftTree[0].url
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