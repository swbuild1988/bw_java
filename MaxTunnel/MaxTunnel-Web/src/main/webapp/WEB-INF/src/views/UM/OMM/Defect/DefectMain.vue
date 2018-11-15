<template>
  <div>
    <ModulePage v-bind="defect"></ModulePage>
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
        defect:{
          moduleName:"缺陷管理",
          leftTree:[],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/defect/query",
    };
  },
  created(){
    this.defect.leftTree = [];
    let _this = this;
      TunnelService.getTunnels().then(
          (result)=>{
              result.forEach(a=>{
                  let temp={};
                  temp.id=a.id;
                  temp.name=a.name;
                  temp.url="/UM/defect/query/"+a.id;
                  _this.defect.leftTree.push(temp);
              })
              let element = {};
              element.id = result.length+1;
              element.name = '添加',
              element.url= '/UM/defect/detail'
              this.defect.leftTree.push(element)
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
    //       temp.url="/UM/defect/query/"+a.id;
    //       _this.defect.leftTree.push(temp);
    //     })
    //   }
    //   let element = {};
    //       element.id = data.length+1;
    //       element.name = '添加',
    //       element.url= '/UM/defect/detail'
    //       this.defect.leftTree.push(element)
    //   });
  },
};
</script>