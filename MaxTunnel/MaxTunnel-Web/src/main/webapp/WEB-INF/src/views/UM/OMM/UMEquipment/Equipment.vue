<template>
  <div>
    <ModulePage v-bind="equipemnt"></ModulePage>
  </div>
</template>

<script>
  import ModulePage from '../../../../components/Common/ModulePage'

  export default {
    name: "UM_main",
    components: {ModulePage},
    data() {
      return {
        equipemnt: {
          moduleName: "设备管理",
          leftTree: [],
        },
        treeNodeJumpUrl: "/UM/equipment/homePage",
      };
    },
    created() {
      this.equipemnt.leftTree = [];
      this.equipemnt.leftTree = [{
        id: 1, name: "管廊设备", childNode: [
          {
            id: 11,
            name: "历史统计",
            url: "/UM/equipment/tunnelHistoryCount",
          },
          {
            id: 12,
            name: "设备查询",
            url: "/UM/equipment/queryequipment",
          }
        ]
      },
        {
          id: 2, name: "备品备件", childNode: [
            {
              id: 21,
              name: "历史统计",
              url: "/UM/equipment/backupHistoryCount",
            },
            {
              id: 22,
              name: "设备查询",
              url: "/UM/equipment/querybackup",
            }
          ]
        },
        {
          id: 3, name: "仪表工具", childNode: [
            {
              id: 31,
              name: "历史统计",
              url: "/UM/equipment/toolHistoryCount",
            },
            {
              id: 32,
              name: "设备查询",
              url: "/UM/equipment/querytool",
            }
          ]
        },
      ];

      // this.axios.get("/tunnels").then(result => {
      //   let { code, data } = result.data;
      //   // this.equipemnt.leftTree = [];
      //   var _this = this;
      //   if (code == 200) {
      //     data.forEach(a=>{
      //       let temp={};
      //       temp.id=a.id;
      //       temp.name=a.name;
      //       temp.url='/UM/equipment/query/'+a.id;
      //       _this.equipemnt.leftTree.push(temp);
      //     })
      //   }
      // });
      // let element = {};
      //   element.id = 1;
      //   element.name = '制定',
      //   element.url= '/UM/equipment/add'
      //   this.equipemnt.leftTree.push(element)
    },
  };
</script>
