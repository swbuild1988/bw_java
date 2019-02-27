<template>
  <Modal
    title="清除告警"
    v-model="modalPrams.state"
    @on-ok="clearAlarms"
    :width="modalWidth"
    :styles="{top: '30%'}"
    @on-cancel="">
    <div style="text-align: center;font-size: 1.66vmin;line-height: 4vmin">
      <Row>
        <Col span="4">
        <span >备注:</span>
        </Col>
        <Col span="19" offset="1">
        <Input v-model="clearAlarmDes" type="textarea" :rows="4" placeholder="填写清除告警的描述信息"></Input>
        </Col>
      </Row>
    </div>
  </Modal>
</template>

<script>
  import {DataAnalysisService} from '../../../services/dataAnalysisService.js'

  export default {
    name: "clrar-alarm",
    props: {
      modalPrams: {
        type: Object,
        default: function () {
          return {
            state: false,
            ids: []};
        }
      },
    },
    data() {
      return {
        clearAlarmDes: "",
        modalWidth:400,
      }
    },
    methods: {
      //清除告警
      clearAlarms() {
        var _this = this;
        let pram = {};
        pram.description = _this.clearAlarmDes;
        pram.ids = _this.modalPrams.ids;
        DataAnalysisService.clearAlarms(pram).then((result) => {
        })
      },

    },
    mounted() {
      this.modalWidth = window.innerWidth * 0.35;
    },
  }
</script>

<style scoped>

</style>
