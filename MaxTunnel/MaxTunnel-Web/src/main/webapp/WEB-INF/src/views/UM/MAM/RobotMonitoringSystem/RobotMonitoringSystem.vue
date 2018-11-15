<template>
  <div>
    <ModulePage v-bind="RobotMonitoring"></ModulePage>
  </div>
</template>

<script>
import { TunnelService } from '../../../../services/tunnelService'
import ModulePage from '../../../../components/Common/ModulePage'
export default {
  name: "UM_main",
  components:{ModulePage},
  data() {
    return {
        RobotMonitoring:{
          moduleName:"机器人监控系统",
          leftTree:[],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/RobotMonitoring/query",
    };
  },
  created(){
    this.RobotMonitoring.leftTree = [];
    let _this = this
    TunnelService.getTunnels().then(
        (result)=>{
              result.forEach(a=>{
              let temp={};
              temp.id=a.id;
              temp.name=a.name;
              temp.url=_this.treeNodeJumpUrl;
              var child=[{id:1,name: '总览', url: '/UM/RobotMonitoring/query'},
                          {id:2,name: '详情', url: '/UM/RobotMonitoring/details'}];
                          temp.childNode= child;
              _this.RobotMonitoring.leftTree.push(temp);
            })
        },
        (error)=>{
            console.log(error)
        })
  },
};
</script>