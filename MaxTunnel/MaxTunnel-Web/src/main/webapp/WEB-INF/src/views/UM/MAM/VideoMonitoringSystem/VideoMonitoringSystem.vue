<template>
  <div>
    <ModulePage v-bind="VideoMonitoring"></ModulePage>
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
        VideoMonitoring:{
          moduleName:"视频监控系统",
          leftTree:[],
          selected:[0,0]
        },
        treeNodeJumpUrl:"/UM/VideoMonitoring/details/",
    };
  },
  created(){
    this.VideoMonitoring.leftTree = [];
    let _this = this;
    TunnelService.getTunnels().then(
          (result)=>{
              result.forEach(a=>{
                  let temp={};
                  temp.id=a.id;
                  temp.name=a.name;
                  temp.url=_this.treeNodeJumpUrl;
                  var child=[
                              {id:2,name: '详情', url: '/UM/VideoMonitoring/details/'+a.id}];
                              temp.childNode= child;
                  _this.VideoMonitoring.leftTree.push(temp);
                })
          },
          (error)=>{
              console.log(error)
      })
  },
};
</script>