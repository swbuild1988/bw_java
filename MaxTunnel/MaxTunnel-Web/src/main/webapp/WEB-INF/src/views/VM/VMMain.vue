<template>
  <div id="VMMain">
      <div class="MainContent">
          <div
              v-for="item in VMMainContext"
              :class="item.className"
              :style="item.extendStyle"
              v-if="item.isShow"
      >
          <VMHead v-if = "item.className === 'VMExtend-0'"></VMHead>
          <VMBody v-if = "item.className === 'VMExtend-0'"></VMBody>
          <!-- <extend-model v-else-if = "item.className === 'VMExtend-1'"></extend-model> -->
          <!-- <extend-videos v-else-if = "item.className === 'VMExtend-2'"></extend-videos> -->

      </div>
      </div>


  </div>
</template>

<script>
    import VMHead from './header/VMHead'
    import VMBody from './body/VMBody'
    import extendModel from './extendViews/ExtendViewModal1'
    import extendModelTow from './extendViews/ExtendViewModal2'
    import extendModelThree from './extendViews/ExtendViewModal3'
    import extendVideos from './extendViews/ExtendVideos'

    let  { VMExtendStyle } = require('../../../static/VM/js/VMWebConfig');

    export default {
        data() {
            return {
                VMMainContext:[],
                classNameConfig:[],
                extendTemplateNumber:5,
            }
        },
        watch:{
              '$route'(to,from){
              },

        },
        components:{
           VMHead,
           VMBody,
           extendModel,
           extendVideos,
           extendModelTow,
           extendModelThree
        },
        created(){
            for(let i=0; i < this.extendTemplateNumber;i++)
                this.classNameConfig.push({ key:`模块${ i }`,val:`VMExtend-${i}` })
        },
        mounted(){
            this.init();
        },
        methods:{
            init(){
                this.VMMainContext.splice(0);

                for (let item in VMExtendStyle){
                    if( VMExtendStyle.hasOwnProperty( item ) ){
                        this.classNameConfig.forEach( obj => (obj.key === VMExtendStyle[item].className) && (VMExtendStyle[item].className = obj.val ));

                        this.VMMainContext.push(VMExtendStyle[item]);
                    }
                }
            },
        }
    }

</script>
<style scoped>
    .MainContent {
        width: 100%;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        background: url('../../assets/VM/bg_image.png') no-repeat;
        background-size: 100% 100%;
        background-color: #031324;
    }
    .MainContent .VMExtend-0,
    .MainContent .VMExtend-1,
    .MainContent .VMExtend-2,
    .MainContent .VMExtend-3,
    .MainContent .VMExtend-4{
        position: absolute;
        height: 100%;
    }
    .MainContent .VMExtend-1,
    .MainContent .VMExtend-2,
    .MainContent .VMExtend-3,
    .MainContent .VMExtend-4{
        padding-right: 0.7%;
        height: 94%;
        top: 6%;
    }
</style>
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
  #top{
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
  .ivu-notice-icon {
      margin-top: 1px;
  }
  .ivu-notice-icon-warning{
      color: #1DEBFF;
  }
</style>
