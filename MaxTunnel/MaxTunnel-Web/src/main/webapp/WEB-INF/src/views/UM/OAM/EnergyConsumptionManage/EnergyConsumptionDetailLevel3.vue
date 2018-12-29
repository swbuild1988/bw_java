<!--管廊能耗总览层级3-->
<template>
  <div>
    <h1 style="margin-left: 10px;">{{title}}:</h1>
    <Row>
      <Col span="24">
      <div class="chart">
        <SimpleBarWithClickChart v-bind="clickBarChart"  style="height:calc(85vh - 80px)"></SimpleBarWithClickChart>
      </div>
      </Col>
    </Row>
  </div>
</template>

<script>
  import SimpleBarWithClickChart from '../../../../components/Common/Chart/SimpleBarWithClickChart'

  export default {
    name: "energy-consumption-detail-level3",
    data() {
      return {
        title:"",
        clickBarChart: {
          id: "BarChart",
          requestUrl: "BarChart",
          clickPrams: {
            tunnelId: "",
            storeId: "",
            ammeterTypeId: "",
            curIndexId: "",
          }
        },
      }
    },
    created() {
      this.initPrams();
    },
    methods: {
      //初始化参数
      initPrams() {
        this.clickBarChart.clickPrams = this.$route.query;
        if (this.$route.query.areaType == 1) {
          this.clickBarChart.requestUrl = "areas/consume-datas";
          this.title="具体区段详情";
        }
        else if (this.$route.query.areaType == 2) {
          this.clickBarChart.requestUrl = "stores/consume-datas";
          this.title="具体仓段详情";
        }
      }
    },
    components: {
      SimpleBarWithClickChart
    },
  }
</script>

<style scoped>
  .chart {
    padding: 10px;
  }
  .top {
    margin: 10px;
    background-color: #f1f1f1;
    padding-left: 10px;
  }

  .planDec {
    padding: 4px;
    font-size: 14px;
    float: left;
  }

  .col {
    height: 60px;
    padding-top: 10px;
  }
</style>
