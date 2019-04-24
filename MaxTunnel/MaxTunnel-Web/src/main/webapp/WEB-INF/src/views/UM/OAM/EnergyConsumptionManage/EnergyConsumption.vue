<template>
  <div>
    <ModulePage v-bind="eneryScheme"></ModulePage>
  </div>
</template>

<script>
  import ModulePage from '../../../../components/Common/ModulePage'
  import {TunnelService} from '../../../../services/tunnelService'

  export default {
    name: "UM_main",
    components: {ModulePage},
    data() {
      return {
        eneryScheme: {
          moduleName: "能耗管理",
          leftTree: [],
          selected: [-1, -1]
        },
        treeNodeJumpUrl: "/UM/TunnelEnergy/detials/",
      };
    },
    created() {
      this.eneryScheme.leftTree = [
        {
          id: 0,
          name: '总能耗分析',
          url: '/UM/TunnelEnergy/homePage',
          child: [
            {}
          ]
        }
      ];
      let _this = this;
      // let temp = {};
      // temp.id = 1;
      // temp.name = "管廊1";
      // temp.childNode = [{id: 1, name: '总览 ', url: '/UM/TunnelEnergy/detials/' + temp.id},
      //   {id: 2, name: '类别详情', url: '/UM/TunnelEnergy/EnergyConsumptionDetailLevel1/' + temp.id}
      // ];
      // _this.eneryScheme.leftTree.push(temp);
        TunnelService.getTunnels().then(
            (result)=>{
              result.forEach(a=>{
                let temp={};
                temp.id=a.id;
                temp.name=a.name;
                            temp.childNode= [{id:1,name: '总览 ', url: '/UM/TunnelEnergy/detials/'+a.id},
                              {id:2,name: '类别详情', url: '/UM/TunnelEnergy/EnergyConsumptionDetailLevel1/'+a.id},
                              {id:3,name: '变电所', url: '/UM/TunnelEnergy/substation/'+a.id}
                              ];
                _this.eneryScheme.leftTree.push(temp);
              })
              if (sessionStorage["refreshAddress"] == "" || sessionStorage["refreshAddress"].indexOf("/UM/TunnelEnergy") < 0) {
                  _this.goToMoudle({path: _this.eneryScheme.leftTree[0].url});
                  sessionStorage.setItem('selectedName','')
              }
                sessionStorage.setItem("refreshAddress", "");
            },
            (error)=>{
                console.log(error)
        })
    },
    watch: {
      '$route': function () {
        if (this.$route.path == '/UM/TunnelEnergy/homepage') {
          this.eneryScheme.selected = [-1, -1]
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
