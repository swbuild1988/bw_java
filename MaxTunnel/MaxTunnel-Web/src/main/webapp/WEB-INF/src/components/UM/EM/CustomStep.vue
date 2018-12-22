<template>
  <div class="steps-status" :style="{height:defineHeight + 'px',}">
    <!--步骤条间隔线-->
    <div class="steps-tail" v-if="stepLength!=curIndex" :class="{ 'line-unWork': curStatusVal==3,'line-finish':curStatusVal==1, 'line-working':curStatusVal==2,}">
    </div>
    <!--步骤条圆形图标-->
    <div class="steps-heads">
      <div class="step-Icon">
        <div v-if="curStatusVal==1">
          <Icon type="ios-checkmark-empty" size="38" color="#1289ff" ></Icon>
        </div>

      </div>
      <div class="setp-head"  :class="{ 'circle_bottom animation head-finish': curStatusVal==2,'head-finish':curStatusVal==1,'head-unWork':curStatusVal==3,}">
          <span class="step-index"   :class="{ 'main-working': curStatusVal==2, 'main-unWork':curStatusVal==3}"   v-if="curStatusVal!=1">{{curIndex}}</span>
      </div>
      <div  class="setp-head  circle_bottom2 animation2" v-if="curStatusVal==2"  :class="{ 'head-finish': curStatusVal==2,}">
      </div>
    </div>
    <!--步骤条主体文字-->
    <div class="setp-main">
      <div class="status" :class="{'main-finish':curStatusVal==1, 'main-unWork':curStatusVal==3,}">
        {{curStatusStr}}
      </div>
      <div class="tip" :class="{'main-finish':curStatusVal==1, 'main-unWork':curStatusVal==3,}">
        {{stepName}}
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "custom-step",
    props: {
      //1已完成，2进行中，3待完成，4失败，
      curStatusVal: {
        type: Number,
        default:1
      },
      //当前节点序号
      curIndex: {
        type: Number,
        default:1
      },
      curStatusStr: {
        type: String,
        default:"已完成"
      },
      stepName: {
        type: String,
        default:"开启消防预案"
      },
      defineWidth: {
        type: String,
        default:"250"
      },
      defineHeight: {
        type: String,
        default:"150"
      },
      stepLength:{
        type: Number,
        default:8,
      }
    },
    data: function () {
      return {
      }
    },
    methods: {},
  }
</script>

<style scoped>
  .setp-main {
    position: absolute;
    top: 16%;
    width: 100%;
    height: 100%;
    left: calc(1% + 35px);
    font-size: 18px;
    z-index: 103;
  }

  .step-index{
    font-size: 20px;
    color:#999;
    text-align: center;
    z-index: 101;
  }

  .main-finish {
    color: #36b5ff;
    border-color: rgba(255, 255, 255, 0.47) !important;
  }
  .head-finish {
    border-color: #36b5ff !important;
  }
  .line-finish{
    border-color:#2d8cf0!important;
  }

  .main-working {
    color: #fff !important;
  }
  .line-working{
    border-color: #59f079!important;
  }

  .head-unWork, .main-unWork{
    color: #ccc;
  }
  .line-unWork{
    border-color:#ccc!important;
  }
  .status {
    font-weight: bold;
    background-color: #fff;
    width: 70px;
    position: absolute;
    text-align: center;
  }

  .tip {
    border: 3px solid #fff;
    position: absolute;
    margin-top: 40px;
    border-radius: 4px;
    width: auto;
    padding: 6px;
    text-align: center;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);
  }

  .steps-status {
    position: relative;
    width: 100%;
  }

  .steps-tail {
    position: absolute;
    width:calc(99% - 38px) ;
    height: 1px;
    border: 1px solid;
    top: 38px;
    left: calc(1% + 35px);
    z-index: 10;
  }

  .setp-head {
    width: 34px;
    height: 34px;
    position: absolute;
    border-radius: 50%;
    border: 1px solid ;
    background-color: #FFFFFF;
    z-index: 107;
    top: 15%;
    left: 1%;
    text-align: center;
  }

  .step-Icon {
    position: absolute;
    top: 20px;
    left: 10px;
    z-index: 108;
  }
  .circle {
    position: absolute;
    margin-top: 3px;
    margin-left: 3px;
    background: rgb(255, 53, 59);
    border-radius: 100%;
    width: 28px;
    height: 28px;
    z-index: 108;
  }
  .circle_bottom {
    position: absolute;
    margin-top: 3px;
    margin-left: 3px;
    background: rgb(255, 53, 59);
    border-radius: 100%;
    width: 30px;
    height: 30px;
    filter: alpha(opacity=40);
    z-index: 108;
  }

  .circle_bottom2 {
    position: absolute;
    background: rgba(255, 253, 47,0.4);
    border-radius: 100%;
    width: 36px;
    height: 36px;
    filter: alpha(opacity=20);
    z-index: 107;
  }

  .animation {;
    -webkit-animation: twinkling 1s infinite ease-in-out;
    animation: twinkling 1s infinite ease-in-out;
    -webkit-animation-fill-mode: both;
    animation-fill-mode: both;
  }

  .animation2 {;
    -webkit-animation: twinkling 1s infinite ease-in-out;
    animation: twinkling 1s infinite ease-in-out;
    -webkit-animation-fill-mode: both;
    animation-fill-mode: both;
  }

  @-webkit-keyframes twinkling {
    0% {
      opacity: 0.2;
      filter: alpha(opacity=20);
      -webkit-transform: scale(1);
    }
    50% {
      opacity: 0.5;
      filter: alpha(opacity=50);
      -webkit-transform: scale(1.12);
    }
    100% {
      opacity: 0.2;
      filter: alpha(opacity=20);
      -webkit-transform: scale(1);
    }
  }

  @keyframes twinkling {
    0% {
      opacity: 0.2;
      filter: alpha(opacity=20);
      -webkit-transform: scale(1);
    }
    50% {
      opacity: 0.5;
      filter: alpha(opacity=50);
      -webkit-transform: scale(1.12);
    }
    100% {
      opacity: 0.2;
      filter: alpha(opacity=20);
      -webkit-transform: scale(1);
    }
  }
</style>
