<template>
  <div class="dataBoxModul" v-bind:style="{width: boxWidth+'%', height: boxHeight+'vh',backgroundImage:'url(' + imgSrc + ')',background: imgBac}">
    <!--<div class="backBox" :style="{backgroundImage:'url(' + imgSrc + ')',backgroundRepeat:'no-repeat'}">-->
    <div class="backBox">
      <img :src=imgSrc />
    </div>
    <div class="showData">{{value}}</div>
    <div class="showData showDataTitle">{{label}}</div>
  </div>
</template>

<script>
  export default {
    name:'dataBox',
    props:{
      boxWidth:{
        default: 100
      },
      boxHeight:{
        default: 28
      },
      value: {
        default: 0.0
      },
      label: {
        default:"label"
      },
      imgSrc:{
        default:"./logo.png"
      },
      imgBac:{
        default: '#fff'
      },
      imgBorderLeftTopRadius:{
        default: 10
      },
      imgBorderRightTopRadius:{
        default: 10
      },
      valColor:{
        default: '#fff'
      },
      labelColor:{
        default: '#fff'
      },
      // dateSrc:{
      //   default: ''
      // }
    },
    methods(){
      this.axios.get(this.dataSrc).then(response=>{
        this.value = response.date
      })
    }
  }
</script>

<style scoped>
  .dataBoxModul{
    border-radius: 4px;
    position: relative;
  }
  .backBox{
    height: 100%;
    width: 7vw;
    overflow: hidden;
    position: absolute;
    padding-top: 2vw;
    right: 0vw;
  }
  .showData{
    padding-top: 7vw;
    font-size: 1.5vw;
    color: #fff;
    margin-left: 1vw;
    position: relative;
    z-index: 2;
  }
  .showDataTitle{
    padding-top: 0vh;
  }
</style>


