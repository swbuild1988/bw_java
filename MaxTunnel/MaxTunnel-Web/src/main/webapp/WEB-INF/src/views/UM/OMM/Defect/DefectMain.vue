<template>
    <div>
        <ModulePage v-bind="defect"></ModulePage>
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
            defect: {
                moduleName: "缺陷管理",
                leftTree: [],
                selected: [0, -1]
            },
            treeNodeJumpUrl: "/UM/defect/query/1"
        };
    },
    created() {
        this.defect.leftTree = [];
        let _this = this;
        TunnelService.getTunnels().then(
            result => {
                result.forEach(a => {
                    let temp = {};
                    temp.id = a.id;
                    temp.name = a.name;
                    temp.url = "/UM/defect/query/" + a.id;
                    _this.defect.leftTree.push(temp);
                });
                // let element = {};
                // element.id = result.length+1;
                // element.name = '添加',
                // element.url= '/UM/defect/detail'
                // this.defect.leftTree.push(element)
                if (
                    !sessionStorage["refreshAddress"] ||
                    sessionStorage["refreshAddress"].indexOf("/UM/defect") < 0
                ) {
                    //gao 代码
                    // _this.goToMoudle({path: _this.defect.leftTree[0].url});
                    // sessionStorage.setItem('selectedName','')
                    //fan 代码 （巡检计划的故障列表跳转到详情）
                    // if(sessionStorage.patrolToDefect == "yes"){
                    //   _this.goToMoudle({path: '/UM/defect/detail'})
                    //   sessionStorage.setItem('patrolToDefect','')
                    // }else{
                    //   _this.goToMoudle({path: _this.defect.leftTree[0].url});
                    sessionStorage.setItem("selectedName", "");
                    // }
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