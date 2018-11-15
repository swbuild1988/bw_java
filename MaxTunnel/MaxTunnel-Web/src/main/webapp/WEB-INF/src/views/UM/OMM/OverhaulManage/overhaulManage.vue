<template>
  <div>
    <ModulePage v-bind="overhaul"></ModulePage>
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
        overhaul:{
          moduleName:"检修管理",
          leftTree:[],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/overhaul/query",
    };
  },
  created(){
    this.overhaul.leftTree = [];
    let _this = this;
    TunnelService.getTunnels().then(
        (result)=>{
            result.forEach(a=>{
                let temp={};
                temp.id=a.id;
                temp.name=a.name;
                temp.url="/UM/overhaul/query/"+a.id;
                _this.overhaul.leftTree.push(temp);
            })
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
    //       temp.url="/UM/overhaul/query/"+a.id;
    //       _this.overhaul.leftTree.push(temp);
    //     })
    //   }
    // });
  },
};
</script>