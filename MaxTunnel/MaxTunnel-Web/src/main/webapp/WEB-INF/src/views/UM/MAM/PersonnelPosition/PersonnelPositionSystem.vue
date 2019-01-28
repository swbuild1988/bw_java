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
      if (sessionStorage["refreshAddress"] == "" || sessionStorage["refreshAddress"].indexOf("/UM/PersonnelPosition") < 0) {
            this.goToMoudle({path: this.curModule.leftTree[0].url});
            sessionStorage.setItem('selectedName','')
        }
          sessionStorage.setItem("refreshAddress", "");
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

