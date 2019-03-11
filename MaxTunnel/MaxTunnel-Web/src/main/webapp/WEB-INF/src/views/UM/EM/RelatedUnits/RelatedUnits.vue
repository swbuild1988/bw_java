<template>
  <div>
    <ModulePage v-bind="relatedUnits"></ModulePage>
  </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
export default {
  name: "UM_main",
  components:{ModulePage},
  data() {
    return {
        relatedUnits:{
          moduleName:"相关单位",
          leftTree:[
            {
              id: 1,
              name: '查询',
              url: '/UM/relatedUnits/query'
            },
            {
              id: 2,
              name: '添加',
              url: '/UM/relatedUnits/add'
            }
          ],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/relatedUnits/query",
    };
  },
  created(){
      if (sessionStorage["refreshAddress"] == "" || sessionStorage["refreshAddress"].indexOf("/UM/relatedUnits") < 0) {
          this.goToMoudle({path: this.relatedUnits.leftTree[0].url});
          sessionStorage.setItem('selectedName','')
      }
        sessionStorage.setItem("refreshAddress", "");
  },
  methods: {
      goToMoudle(path) {
        this.$router.push(path);
      }
    }
};
</script>