<template>
  <Modal
    title="清除告警"
    v-model="modalPrams.state"
    @on-ok="clearAlarms"
    @on-cancel="">
    <div style="text-align: center;font-size: 16px;">
      <span>备注:</span>
      <Input v-model="clearAlarmDes" type="textarea" :rows="4" placeholder="填写清除告警的描述信息"></Input>
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
          return {state: false, ids: []};
        }
      },
    },
    data() {
      return {
        clearAlarmDes: "",
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
  }
</script>

<style scoped>

</style>
