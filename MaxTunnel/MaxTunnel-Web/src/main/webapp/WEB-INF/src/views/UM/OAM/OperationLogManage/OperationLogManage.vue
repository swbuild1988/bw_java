<template>
  <div>
    <ModulePage v-bind="operationLog"></ModulePage>
  </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
export default {
  name: "UM_main",
  components:{ModulePage},
  data() {
    return {
        operationLog:{
          moduleName:"日志管理",
          leftTree:[
            {
              id: 1,
              name: '系统日志',
              url: "/UM/operationLog/query",
            },
            // {
            //   id: 2,
            //   name: '安全日志',
            //   url: ''
            // }
          ]
        },
        treeNodeJumpUrl:"/UM/operationLog/query",
    };
  },
  created(){
    if (sessionStorage["refreshAddress"] == "" || sessionStorage["refreshAddress"].indexOf("/UM/operationLog") < 0) {
          this.goToMoudle({path: this.operationLog.leftTree[0].url});
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