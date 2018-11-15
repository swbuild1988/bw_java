<template>
  <div>
    <Modal v-model="curModal.isShow" @on-ok="ok">
      <p>手动确认此预案是否通过</p>
    </Modal>
  </div>
</template>

<script>
  import {PlanService} from '../../../services/plan.js'

  export default {
    name: "comfirm-step",
    props: {
      curModal: {
        type: Object,
        default: function () {
          return {isShow: false, instanceId: "",objectId:""};
        }
      },
    },
    data() {
      return {
        show: false
      }
    },
    methods: {
      ok() {
        PlanService.ConfirmStep(this.curModal.instanceId,this.curModal.objectId, "yes").then()
      },
    }
  }
</script>

<style scoped>

</style>
