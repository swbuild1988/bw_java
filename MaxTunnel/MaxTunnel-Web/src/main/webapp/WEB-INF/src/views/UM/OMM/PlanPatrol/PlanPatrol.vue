<template>
    <div>
        <ModulePage v-bind="task"></ModulePage>
    </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
import { TunnelService } from '../../../../services/tunnelService'
export default {
    name: "UM_main",
    components:{ModulePage},
    data() {
        return {
            task:{
            moduleName:"计划与任务",
            leftTree:[],
            selected:[0,-1]
            },
            treeNodeJumpUrl:"/UM/planPatrol/query",
        };
    },
    created(){
        this.task.leftTree = [];
        let _this = this;
        let temp1={};
        temp1.id=1;
        temp1.name='年度计划';
        temp1.url="/UM/planPatrol/queryAnnualPlan";
        _this.task.leftTree.push(temp1);
        let temp2={};
        temp2.id=2;
        temp2.name='本月计划';
        temp2.url="/UM/planPatrol/queryMonthPlan";
        _this.task.leftTree.push(temp2);
        if (sessionStorage["refreshAddress"] == "" || sessionStorage["refreshAddress"].indexOf("/UM/planPatrol") < 0) {
            _this.goToMoudle({path: _this.task.leftTree[0].url});
            sessionStorage.setItem('selectedName','')
        }
            sessionStorage.setItem("refreshAddress", "");
    },
    methods: {
        goToMoudle(path) {
          this.$router.push(path);
        }
    }
};
</script>