<template>
  <div id="main">
      <VMHead></VMHead>
      <VMBody></VMBody>
  </div>
</template>

<script>
    import VMHead from './header/VMHead'
    import VMBody from './body/VMBody'
    export default {
        data() {
            return {
            }
        },
        watch:{
              '$route'(to,from){
                console.log(to)
                console.log(from)

              }
        },
      components:{
        VMHead,
        VMBody
      },
        mounted(){
            this.init();
        },
        methods:{
            init(){
            }
        }
    }

</script>

<style>
  /*通用*/
  *{
    box-sizing: border-box;
    -moz-box-sizing: border-box;
  }
  body {
    font: 12px "microsoft yahei", Arial, Helvetica, sans-serif;
    margin:0px;
    padding:0px;
  }
  html,body{
      overflow: hidden;
  }
  .lf   {float:left;}
  .rt  {float:right;}
  .clear  {clear:both;}

  /*整体布局*/
  #top,#man{
    width: 100%;
    margin: 0 auto;
  }
  #top{
    padding-right: 15px;
    padding-left: 0.7%;
    margin-right: auto;
    margin-left: auto;
  }
  .img-responsive{
    display: block;
    width: 100%;
    height: 100%;
    padding: 10px;
  }
  .ivu-notice{
      width: 11%;
  }
  .ivu-notice  .ivu-notice-notice {
      margin-bottom: 10px;
      padding: 13px;
      border-radius: 12px;
      line-height: 1;
      position: relative;
      overflow: hidden;
      color: rgba(60,240,250,1);
      border: 1px solid rgba(0,180,220,0.5);
      background: linear-gradient(180deg,rgba(0,180,220,0.3),rgba(0,180,220,0.1),rgba(0,180,220,0.1),rgba(0,180,220,0.3));
      box-shadow: 0 0 2rem rgba(0,180,220,.1) inset;
  }
  .ivu-notice-custom-content .ivu-notice-title{
      color: rgba(60,240,250,1);
  }
  .ivu-notice-with-warning:after {
      background: transparent;
  }
  /*.ivu-notice-notice .ivu-notice-notice-close {*/
      /*display: none;*/
  /*}*/
  .ivu-notice-icon {
      margin-top: 1px;
  }
  .ivu-notice-icon-warning{
      color: #1DEBFF;
  }
</style>
