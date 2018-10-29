<template>
  <Modal
    title="借出工具"
    v-model="show.state"
    :styles="{top: '20px'}">
    <Row :gutter="8">
      <Col span="6" style="margin-top: 6px;">
      <div :style="{backgroundImage:'url(' + equipmentData.imgUrl + ')'}" class="backGoundBox">
        <div class="topBox">
          <p class="equipentTitle">{{ equipmentData.name }}</p>
          <div class="imgBox"><img :src="equipmentData.imgUrl"></div>
        </div>
      </div>
      <div class="detailsBox">
        <p>设备状态：{{equipmentData.statusName}}</p>
        <p>设备类型：{{ equipmentData.typeName }}</p>
        <p>入库时间：{{ equipmentData.crtTime }}</p>
      </div>
      </Col>
    </Row>
  </Modal>
</template>

<script>
  export default {
    name: "back-up-euipment-operqtion",
    props: {
      show: {
        type: Object,
        default:{
          state:false,
        },
      },
      equipmentData:{
        default:{
          name:"",
          imgUrl:"",
          statusName:"",
          typeName:"",
          crtTime:"",
        }
      },
    },
    data() {
      return {

      }
    },
    methods: {

    },
  }
  ,

</script>

<style scoped>

</style>
