<template>
  <div>
    <ModulePage v-bind="disaster"></ModulePage>
  </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
export default {
  name: "UM_disaster",
  components:{ModulePage},
  data() {
    return {
        disaster:{
          moduleName:"灾害管理",
          leftTree:[],
        },
        treeNodeJumpUrl:"/UM/disaster/query",
    };
  },
  created(){
    this.disaster.leftTree = [];
    
    for (let i = 1; i < 5; i++) {
      let element = {};
      element.id = i;
      switch (i) {
        case 1:
          element.name = "灾害制定";
          break;

        case 2:
          element.name = "火警灾害";
          break;

        case 3:
          element.name = "漏水灾害";
          break;

        case 4:
          element.name = "停电灾害";
          break;

        default:
          break;
      }
      element.url = "/UM/disaster/add";

      this.disaster.leftTree.push(element);
    }
  },
};
</script>