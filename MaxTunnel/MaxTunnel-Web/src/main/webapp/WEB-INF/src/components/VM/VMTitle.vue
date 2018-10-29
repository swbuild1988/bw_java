<template>
    <div class="ComponentsTitle"><span>{{titleText}}</span></div>
</template>

<style scoped>
  .ComponentsTitle{
    position: absolute;
    top: 5%;
    left: 50%;
    padding: 2% 5%;
    box-sizing: border-box;
    transform: translateX(-50%);
    background: url("../../assets/VM/title_bg.png") no-repeat;
    background-size: 100% 100%;
    color: #fff;
  }
  .ComponentsTitle>span{
    display: inline-block;
    width: 100%;
    height: 100%;
    text-align: center;
  }
</style>

<script>

    export default {
        name:'ComponentsTitle',
        props:['titleText'],
    }

</script>
