<template>
  <ModulePage v-bind="curModule"></ModulePage>
</template>

<script>
  import ModulePage from '../../../../components/Common/ModulePage'
  import { TunnelService } from '../../../../services/tunnelService'

  export default {
    name: "UM_main",
    data() {
      return {
        curModule: {
          moduleName: "管廊本体监控",
          leftTree: [],
          selected:[0,-1]
        },
        treeNodeJumpUrl: "/UM/TunnelSupervise/list/",
      }
    },
    components: {
      ModulePage
    },
    created() {
      this.curModule.leftTree = [];
      let _this = this;
      TunnelService.getTunnels().then(
          (result)=>{
              result.forEach(a=>{
                  let temp={};
                  temp.id=a.id;
                  temp.name=a.name;
                  temp.url=_this.treeNodeJumpUrl+a.id;
                  _this.curModule.leftTree.push(temp);
              })
              _this.goToMoudle({path: _this.curModule.leftTree[0].url});
          },
          (error)=>{
              console.log(error)
      })
    },
    mounted() {
    },
    methods: {
      goToMoudle(path) {
        this.$router.push(path);
      }
    }
  };
</script>

