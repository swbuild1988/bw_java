<template>
  <ModulePage v-bind="curModule"></ModulePage>
</template>

<script>
  import ModulePage from '../../../../components/Common/ModulePage'

  export default {
    name: "data-analysis",
    data() {
      return {
        curModule: {
          moduleName: "数据分析",
          leftTree: [{id: 1,name: "数据查询", url: "/UM/DataAnalysis/QueryData"}, {id: 2,name: "告警查询", url: "/UM/DataAnalysis/QueryAlarmData"},
          // {id: 4,name: "同比数据对比", url: "/UM/DataAnalysis/DataCompare"},
            {id: 3,name: "统计报表",childNode: [
                {
                  id: 21,
                  name: "综合监控报表",
                  url: "/UM/DataAnalysis/CountChart",
                },
                {
                  id: 22,
                  name: "设备安全状况报表",
                  url: "",
                },
                {
                  id: 24,
                  name: '巡检报表',
                  url: '/UM/DataAnalysis/taskChart'
                },
                {
                  id: 23,
                  name: '能耗报表',
                  url: '/UM/DataAnalysis/energyConsumptionReport'
                }
              ]}],
        }
      }
    },
    components: {
      ModulePage
    },
    methods: {
      goToMoudle(path) {
        this.$router.push(path);
      }
    },
  }
</script>

<style scoped>

</style>
