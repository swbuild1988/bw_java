<template>
  <div>
    <ModulePage v-bind="patrolScheme"></ModulePage>
  </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
import { TunnelService } from '../../../../services/tunnels'
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
    this.patrolScheme.leftTree = [];
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
    // this.axios.get("/tunnels").then(result => {
    //   let {code, data} = result.data;
    //   var _this=this;
    //   if (code == 200) {
    //     data.forEach(a=>{
    //       let temp={};
    //       temp.id=a.id;
    //       temp.name=a.name;
    //       temp.url="/UM/patrol/query/"+a.id;
    //       _this.patrolScheme.leftTree.push(temp);
    //     })
    //   }
    //   let element = {};
    //       element.id = data.length+1;
    //       element.name = '制定',
    //       element.url= '/UM/patrol/add'
    //       this.patrolScheme.leftTree.push(element)
    // });
  },
};
</script>