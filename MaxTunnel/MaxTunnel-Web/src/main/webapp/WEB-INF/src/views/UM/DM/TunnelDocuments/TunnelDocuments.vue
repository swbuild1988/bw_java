<template>
  <div>
    <ModulePage v-bind="tunnelDocuments"></ModulePage>
  </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
export default {
  name: "UM_main",
  components:{ModulePage},
  data() {
    return {
        tunnelDocuments:{
          moduleName:"管廊资料管理",
          leftTree:[
            {
              id: 1,
              name: '查看',
              url: '/UM/tunnelDocuments/list'
            },
            {
              id: 2,
              name: '添加',
              url: '/UM/tunnelDocuments/add'
            }
          ],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/tunnelDocuments/list",
    };
  },
  watch: {
    '$route': function(){
      if(this.$route.path == '/UM/tunnelDocuments/list'){
        this.tunnelDocuments.selected = [0,-1]
      }else{
        this.tunnelDocuments.selected = [1,-1]
      }
    }
  }
};
</script>