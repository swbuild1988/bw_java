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
  }
};
</script>