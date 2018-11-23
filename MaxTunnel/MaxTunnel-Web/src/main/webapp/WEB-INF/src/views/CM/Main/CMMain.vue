<!--后台管理主页-->
<template>
  <div>
    <CModulePage v-bind="curpage"></CModulePage>
  </div>
</template>

<script>
  import CModulePage from '../../../components/CM/Common/ModulePage'
  export default {
    name: "c-m-main",
    data() {
      return {
        curpage:{
          leftTree:[]
        },
        treeNodeJumpUrl: "/CMMain",
      };
    },
    created() {
      this.curpage.leftTree = [];
      this.curpage.leftTree = [
        {
          id:1,
          name: "人员管理",
          typeName: 'android-people',
          childNode:[
            {
              id:11,
              name: "用户管理",
              url: "/CMMain/user"
            },
            {
              id:12,
              name: "员工管理",
              url: "/CMMain/staff"
            }
          ]
        },
        {
          id:2,
          name:"管廊管理",
          typeName: 'drag',
          childNode:[
            {
              id:21,
              name:"管廊管理",
              url:"/CMMain/pipe"
            },
            {
              id:22,
              name: "管仓管理",
              url: "/CMMain/store"
            },
            {
              id:23,
              name: "区域管理",
              url:"/CMMain/area"
            },
            {
              id:24,
              name: "区段管理",
              url: "/CMMain/section"
            },
            {
              id:25,
              name: "管仓类型管理",
              url: "/CMMain/storeType"
            }
          ]
        },
        {
          id:3,
          name: "权限管理",
          typeName:'android-lock',
          childNode:[
            {
              id:31,
              name: "权限管理"
            }
          ]
        },
        {
          id:4,
          name: "配置管理",
          typeName: 'android-settings',
          childNode:[
            {
              id:41,
              name: "MaxView终端配置",
              url: "/CMMain/config"
            },{
              id: 42,
              name: '视频服务配置',
              url: '/CMMain/queryVideoService'
            },{
              id: 43,
              name: '视频配置',
              url: '/CMMain/queryVideo'
            },{
              id: 44,
              name: '监测对象配置',
              url: '/CMMain/MeasObj'
            }
          ]
        },
        {
          id:5,
          name: "定时任务管理",
          typeName: 'clipboard',
          childNode:[
            {
              id:51,
              name: "定时任务管理",
              url: "/CMMain/schedulejob"
            }
          ]
        },
        {
          id:6,
          name: "应急管理",
          typeName:'medkit',
          childNode:[
            {
              id:61,
              name:"应急管理",
              url: "/CMMain/EmPlan"
            }
          ]
        }
      ];
    },
      // console.log("leftTree"+this.curpage.leftTree);
      // this.axios.get("/tunnels").then(result => {
      //   let {code, data} = result.data;
      //   var _this = this;
      //   if (code == 200) {
      //     data.forEach(a => {
      //       let temp = {};
      //       temp.id = a.id;
      //       temp.name = a.name;
      //       temp.url = _this.treeNodeJumpUrl + a.id;
      //       _this.curpage.leftTree.push(temp);
      //     })
      //     this.goToMoudle({path: this.curpage.leftTree[0].url});
      //   }
      // });
    // mounted() {
    // },
    // methods: {
    //   goToMoudle(path) {
    //     this.$router.push(path);
    //   }
    // },
    components: {
      CModulePage
    },
  }
</script>

<style scoped>

</style>
