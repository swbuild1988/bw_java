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
          moduleName:"能耗管理",
          leftTree:[],
          selected:[-1,-1]
        },
        treeNodeJumpUrl:"/UM/TunnelEnergy/detials/",
    };
  },
  created(){
    this.patrolScheme.leftTree = [
      {
        id: 0,
        name: '总能耗分析',
        url: '/UM/TunnelEnergy/homePage',
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
              // temp.url=_this.treeNodeJumpUrl+a.id;
              var child=[{id:1,name: '总览 ', url: '/UM/TunnelEnergy/detials/'+a.id},
                          {id:2,name: '类别详情', url: '/UM/TunnelEnergy/categories/'+a.id}];
                          temp.childNode= child;
              _this.patrolScheme.leftTree.push(temp);
            })
          },
          (error)=>{
              console.log(error)
      })
  },
  watch: {
    '$route': function () {
      if(this.$route.path == '/UM/TunnelEnergy/homepage'){
        this.patrolScheme.selected = [-1,-1]
      }
    }
  }
};
</script>
