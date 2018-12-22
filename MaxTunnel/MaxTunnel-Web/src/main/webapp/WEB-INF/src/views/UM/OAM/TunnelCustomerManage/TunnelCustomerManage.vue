<template>
  <div>
    <ModulePage v-bind="tunnelCustomer"></ModulePage>
  </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
export default {
  name: "UM_main",
  components:{ModulePage},
  data() {
    return {
        tunnelCustomer:{
          moduleName:"管廊客户管理",
          leftTree:[
            {
              id: 1,
              name: '查看',
              url: '/UM/tunnelCustomer/list'
            },
            {
              id: 2,
              name: '添加',
              url: '/UM/tunnelCustomer/add'
            }
          ],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/tunnelCustomer/list",
    };
  },
};
</script>