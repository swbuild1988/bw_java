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
      this.equipemnt.leftTree = [
        {
          id: 0,
          name: '设备管理总览',
          url: '/UM/equipment/homePage',
          // childNode: [{}]
        },
        {
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
    },
    beforeRouteUpdate (to, from, next) {
      // console.log("equipment to",to)
      // console.log("equipment from",from)
      next()
    },                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
  };
</script>
