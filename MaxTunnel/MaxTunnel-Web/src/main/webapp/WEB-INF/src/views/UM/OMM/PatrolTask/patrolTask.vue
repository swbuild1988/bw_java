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
          moduleName:"巡检任务",
          leftTree:[],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/patrolTask/query",
    };
  },
  created(){
    this.task.leftTree = [];
    let _this = this;
      TunnelService.getTunnels().then(
          (result)=>{
              result.forEach(a=>{
                  let temp={};
                  temp.id=a.id;
                  temp.name=a.name;
                  temp.url="/UM/patrolTask/query/"+a.id;
                  _this.task.leftTree.push(temp);
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
    //       temp.url="/UM/patrolTask/query/"+a.id;
    //       _this.task.leftTree.push(temp);
    //     })
    //   }
    // });
    // let element = {};
    //     element.id = 1;
    //     element.name = '制定',
    //     element.url= '/UM/patrolTask/add'
    //     this.task.leftTree.push(element)
    // let element1 = {};
    //     element1.id = 1;
    //     element1.name = '分配任务',
    //     element1.url= '/UM/patrolTask/distribute'
    //     this.task.leftTree.push(element1)  
  },
};
</script>