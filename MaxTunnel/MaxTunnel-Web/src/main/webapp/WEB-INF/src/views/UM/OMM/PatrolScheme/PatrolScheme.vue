<template>
  <div>
    <ModulePage v-bind="patrolScheme"></ModulePage>
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
        patrolScheme:{
          moduleName:"巡检计划",
          leftTree:[],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/patrol/homePage",
    };
  },
  created(){
    this.patrolScheme.leftTree = [
      {
        id: 0,
        name: '巡检管理总览',
        url: '/UM/Patrol/homePage',
        child: [
          {}
        ]
      }
    ];
    let _this = this;
      TunnelService.getTunnels().then(
          (result)=>{
              result.forEach(a=>{
                let temp={};
                temp.id=a.id;
                temp.name=a.name;
                temp.url="/UM/patrol/query/"+a.id;
                _this.patrolScheme.leftTree.push(temp);
              })
              let element = {};
              element.id = result.length+1;
              element.name = '制定',
              element.url= '/UM/patrol/add'
              this.patrolScheme.leftTree.push(element)
          },
          (error)=>{
              _this.Log.info(error)
      })
  },
  beforeRouteUpdate (to, from, next) {
    next()
  },
};
</script>