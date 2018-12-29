<template>
  <div>
    <ModulePage v-bind="myApplication"></ModulePage>
  </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
export default {
  name: "UM_main",
  components:{ModulePage},
  data() {
    return {
        myApplication:{
          moduleName:"我的申请",
          leftTree:[
              {
                  id: 1,
                  name: '查看',
                  url: '/UM/myApplication/query'
              },  
          ]
        },
        treeNodeJumpUrl:"/UM/myApplication/query",
    };
  },
};
</script>