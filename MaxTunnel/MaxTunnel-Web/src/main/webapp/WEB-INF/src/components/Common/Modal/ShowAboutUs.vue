<template>
  <Modal
    title="关于我们"
    v-model="show.state"
    width="600px">
    <div class="aboutFont">
      <Row>
        <Col span="24" style="text-align: center;">
        <h3>{{company.version}}</h3>
        </Col>
        <Col span="1">
        <span ><Icon type="android-star" class="icons" color="#00a1ff"></Icon></span>
        </Col>
        <Col span="23">
        <span>：{{company.name}}</span>
        </Col>
        <Col span="1">
        <span style="width: 20px;"><Icon type="ios-location" class="icons" color="#00a1ff"></Icon></span>
        </Col>
        <Col span="23">
        <span>：{{company.address}}</span>
        </Col>
        <Col span="1">
        <span><Icon type="ios-telephone" class="icons" color="#00a1ff"></Icon></span>
        </Col>
        <Col span="23">
        <span> ：{{company.telephone}}</span>
        </Col>
        <Col span="1">
        <span><Icon type="ios-email" class="icons" color="#00a1ff"></Icon></span>
        </Col>
        <Col span="23">
        <span>：{{company.email}}</span>
        </Col>
      </Row>
    </div>
  </Modal>
</template>

<script>
  export default {
    name: "show-about-us",
    props: {
      show: {
        type: Object,
        default: function () {
          return {state: false};
        }
      },
      company: {
        version: "",
        name: "",
        email: "",
        telephone: "",
        address: "",
      }
    },
  }
</script>

<style scoped>
  .aboutFont{
    font-size: 1.66vmin;
    line-height: 3.2vmin;
  }
  .icons{
    font-size: 2vmin;
  }
  .ivu-modal-header-inner{
    font-size: 1.66vmin;
  }

</style>
