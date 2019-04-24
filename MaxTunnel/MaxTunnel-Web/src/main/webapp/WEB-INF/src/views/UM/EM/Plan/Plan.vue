<template>
    <div>
        <module-page v-bind="plans"></module-page>
    </div>
</template>

<script>
import ModulePage from "../../../../components/Common/ModulePage";
import { EnumsService } from "../../../../services/enumsService";
// import SockJS from "sockjs-client"; // 'sockjs-client' 必须与package.json文件当中dependencies 当中的一模一样
export default {
    name: "UM_plan",
    components: { ModulePage },
    data() {
        return {
        plans: {
            moduleName: "预案管理",
            leftTree: []
        },
        // treeNodeJumpUrl: "/UM/plans/detial/processKey",
        treeNodeJumpUrl: "/UM/plans/detial/",
        executeNodeUrl: "/UM/plans/execute/"
        };
    },
    created() {
        this.plans.leftTree = [];
        let _this = this;
        EnumsService.getPlanType().then(
            result => {
                result.forEach(a => {
                    let temp = {};
                    temp.name = a.key;
                    temp.id = a.val;
                    temp.processKey = a.processKey;
                    temp.url = _this.treeNodeJumpUrl + a.processKey;
                    temp.childNode = [
                        {
                            id: 11,
                            name: "预案详情",
                            url: _this.treeNodeJumpUrl + a.processKey
                        },
                        {
                            id: 12,
                            name: "执行预案",
                            url: _this.executeNodeUrl + a.processKey
                        }
                    ];
                    _this.plans.leftTree.push(temp);
                });
                let item = {
                    id: 13,
                    name: '人员组织架构',
                    url: '/UM/plans/personnelOrgStructure'
                }
                let element = {
                    id: 14,
                    name: '应急人员管理',
                    url: '/UM/plans/emerManage'
                }
                _this.plans.leftTree.push(item)
                _this.plans.leftTree.push(element)
                // if ( sessionStorage["refreshAddress"] == "" || sessionStorage["refreshAddress"].indexOf("/UM/plans") < 0 ) {
                //     _this.goToMoudle({ path: _this.plans.leftTree[0].childNode[0].url });
                //     sessionStorage.setItem("selectedName", "");
                // }
                //     sessionStorage.setItem("refreshAddress", "");
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
