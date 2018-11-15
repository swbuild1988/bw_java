<template>
  <div>
    <ModulePage v-bind="patrolScheme"></ModulePage>
  </div>
</template>
<style scoped>
</style>
<script>
  import ModulePage from '../../../../components/Common/ModulePage'
  import { TunnelService } from '../../../../services/tunnelService'
  
  export default {
    name:"OperatingSpaceMain",
    data() {
      return {
        patrolScheme:{
          moduleName:"空间管理",
          leftTree:[],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/OperatingSpace/tunnel/"
      };
    },
    components: {ModulePage},
    created() {
      this.patrolScheme.leftTree = [];
      let _this = this;
      TunnelService.getTunnels().then(
          (result)=>{
              result.forEach(a=>{
                  let temp={};
                  temp.id=a.id;
                  temp.name=a.name;
                  temp.url=_this.treeNodeJumpUrl+a.id;
                  _this.patrolScheme.leftTree.push(temp);
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
      //     let temp={};
      //     temp.id=a.id;
      //     temp.name=a.name;
      //     temp.url=_this.treeNodeJumpUrl+a.id;
      //     _this.patrolScheme.leftTree.push(temp);
      //   })
      // }
      // });
     
    },
    // mounted() {
    // },
    // computed: {
    //   menuitemClasses: function () {
    //     return ["menu-item", this.isCollapsed ? "collapsed-menu" : ""];
    //   },

    // },
    // methods: {
    //   //跳转模块
    //   goToMoudle(path) {
    //     this.$router.push(path);
    //   },
    // }
  }
  ;
</script>


