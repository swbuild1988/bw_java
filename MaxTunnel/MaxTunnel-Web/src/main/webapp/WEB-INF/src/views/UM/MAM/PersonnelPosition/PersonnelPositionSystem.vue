<template>
  <ModulePage v-bind="curModule"></ModulePage>
</template>

<script>
  import ModulePage from '../../../../components/Common/ModulePage'

  export default {
    name: "personnelPosition",
    data() {
      return {
        curModule: {
          moduleName: "人员定位",
          leftTree: [],
          selected:[0,-1]
        },
        treeNodeJumpUrl: "/UM/PersonnelPosition/tunnel/",
      }
    },
    components: {
      ModulePage
    },
    created() {
      this.curModule.leftTree = [
        {
            name: '人员定位详情',
            url: '/UM/PersonnelPosition/tunnel'
        },
        {
            name: '设备分配',
            url: '/UM/PersonnelPosition/equipmentDistribution'
        }
      ];
      // this.axios.get("/tunnels").then(result => {
      //   let {code, data} = result.data;
      //   var _this = this;
      //   if (code == 200) {
      //     data.forEach(a => {
      //       let temp = {};
      //       temp.id = a.id;
      //       temp.name = a.name;
      //       temp.url = _this.treeNodeJumpUrl + a.id;
      //       _this.curModule.leftTree.push(temp);
      //     })
      //     _this.curModule.leftTree.push({
      //         name: '设备分配',
      //         url: '/UM/PersonnelPosition/equipmentDistribution'
      //     });
      //     this.goToMoudle({path: this.curModule.leftTree[0].url});
      //   }
      // });

    },
    beforeRouteUpdate (to, from, next) {
      next()
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

