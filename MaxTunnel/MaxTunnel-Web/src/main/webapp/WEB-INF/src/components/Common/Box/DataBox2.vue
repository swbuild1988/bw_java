<template>

  <div v-bind:style="{height:'11vh',backgroundColor: backGroundColor, width:'100%'}">
    <div style="position: relative; float: right; margin: 2px;">
      <Icon type="ios-arrow-thin-up" size="70" color="#fff"></Icon>
    </div>
    <div style="position: relative; float: right;color: #fff;margin: 7px;">
      <ul style="list-style-type:none; text-align: right;">
        <li style="font-size: x-large">{{value}}</li>
        <li style="width:'10%'">{{unit}}</li>
      </ul>
    </div>
    <div>
      <img :src="imgSrc" style="height: 7vh; padding-left:10px;top:5px; "/>
    </div>
    <Tag :style="{background:tagColor,height:'3vh',width:'15%',minWidth:'80px'}">{{label}}</Tag>
  </div>

</template>

<script>
export default {
  name: 'dataBox2',
  props: {
    minWidth: {
      default: 120
    },
    maxWidth: {
      default: 280
    },
    backGroundColor: {
      default: '#ffce2d'
    },
    value: {
      default: 0.0
    },
    unit:{
      default:"千瓦时"
    },
    label: {
      default: "label"
    },
    tagColor:{
      default: '#0a3420'
    },
    imgSrc: {
      default: ""
    }
  },
}
</script>



