<template>
  <div>
    <ModulePage v-bind="tunnelContract"></ModulePage>
  </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
export default {
  name: "UM_main",
  components:{ModulePage},
  data() {
    return {
        tunnelContract:{
          moduleName:"管廊合同管理",
          leftTree:[
            {
              id: 1,
              name: '查看',
              url: '/UM/tunnelContract/list'
            },
            {
              id: 2,
              name: '添加',
              url: '/UM/tunnelContract/add'
            }
          ],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/tunnelContract/list",
    };
  },
  created() {
      if (sessionStorage["refreshAddress"] == "" || sessionStorage["refreshAddress"].indexOf("/UM/tunnelContract") < 0) {
                this.goToMoudle({path: this.tunnelContract.leftTree[0].url});
                sessionStorage.setItem('selectedName','')
            }
              sessionStorage.setItem("refreshAddress", "");
  },
  watch: {
    '$route': function(){
      if(this.$route.path == '/UM/tunnelContract/add'){
        this.tunnelContract.selected = [1,-1]
      }else{
        this.tunnelContract.selected = [0,-1]
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