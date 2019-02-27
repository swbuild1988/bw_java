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
  created(){
      if (sessionStorage["refreshAddress"] == "" || sessionStorage["refreshAddress"].indexOf("/UM/tunnelDocuments") < 0) {
              this.goToMoudle({path: this.tunnelDocuments.leftTree[0].url});
              sessionStorage.setItem('selectedName','')
          }
            sessionStorage.setItem("refreshAddress", "");
  },
  watch: {
    '$route': function(){
      if(this.$route.path == '/UM/tunnelDocuments/list'){
        this.tunnelDocuments.selected = [0,-1]
      }else{
        this.tunnelDocuments.selected = [1,-1]
      }
    }
  },
  methods: {
      goToMoudle(path) {
        this.$router.push(path);
      }
  }
};
</script>