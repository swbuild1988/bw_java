<template>
  <Table border stripe :columns="columns1" :data="data1"></Table>
</template>
<script>
  export default {
    data () {
      return {
        columns1: [
          {
            title: '责任人',
            key: 'personInchargeName',
            align: 'center'
          },
          {
            title: '巡检时间',
            key: 'inspectionTime',
            align: 'center'
          },
          {
            title: '提交时间',
            key: 'submitT',
            align: 'center'
          },
          {
            title: '记录类型',
            key: 'recordType',
            align: 'center'
          }
        ],
        data1: [
          {
            personInchargeName: '张三',
            inspectionTime: '2018-5-22',
            submitT: '2018-5-22',
            recordType: '正常',
            align: 'center'
          },
          {
            personInchargeName: '李四',
            inspectionTime: '2018-5-22',
            submitT: '2018-5-22',
            recordType: '缺陷',
            align: 'center'
          },
          {
            personInchargeName: '王五',
            inspectionTime: '2018-5-22',
            submitT: '2018-5-22',
            recordType: '危险点',
            align: 'center'
          },
          {
            personInchargeName: '张三',
            inspectionTime: '2018-5-22',
            submitT: '2018-5-22',
            recordType: '隐患',
            align: 'center'
          }
        ]
      }
    }
  }
</script>
<style scoped>

</style>
