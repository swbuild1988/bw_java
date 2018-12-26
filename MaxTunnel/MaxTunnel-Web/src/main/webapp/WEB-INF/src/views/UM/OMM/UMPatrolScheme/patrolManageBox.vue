<template>
  <div>
    <queryCondition></queryCondition>
    <div class="bottom-div">
      <Row>
        <Col span="11">
          <planContent></planContent>
        </Col>
        <Col span="13">
          <div class="planBox inspectionRecordTitle">
            巡检记录
            <inspectionRecord></inspectionRecord>
          </div>
        </Col>
      </Row>
    </div>
  </div>
</template>
<script>

  import inspectionRecord from './inspectionRecord.vue'
  import planContent from './planContent.vue'
  import queryCondition from '../../../../components/UM/OMM/patrolScheme/queryCondition.vue'

  export  default {
    name: 'UMPatroManage',
    components: {
      inspectionRecord,
      planContent,
      queryCondition
    }
  }
</script>

<style scoped>
  *{
    font-size: 14px;
  }
  .planDec {
    padding-right: 5px;
    font-size: 14px;
  }
  .inspectionRecordTitle{
    font-size: 20px;
    font-weight: 600;
  }
</style>
