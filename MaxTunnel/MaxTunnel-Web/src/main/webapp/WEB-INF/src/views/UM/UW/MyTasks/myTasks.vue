<template>
  <div>
    <ModulePage v-bind="myNews"></ModulePage>
  </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
export default {
  name: "UM_main",
  components:{ModulePage},
  data() {
    return {
        myNews:{
          moduleName:"我的任务",
          leftTree:[
              {
                  id: 1,
                  name: '查看',
                  url: '/UM/myTasks/query'
              }
          ],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/myTasks/query",
    };
  },
};
</script>