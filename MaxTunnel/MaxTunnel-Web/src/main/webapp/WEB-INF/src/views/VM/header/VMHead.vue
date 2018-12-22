<template>
  <div id="top" class="top">
    <div class="ImgResponsive lf" id="imgResponsive">
      <img class="img-responsive" :src="logo">
    </div>
    <div class="TopTitle lf"><span>{{title}}</span></div>
  </div>
</template>

<script>

   import { VM_TILTE,LOGO_IMAGE } from '../../../../static/VM/js/VMGlobalConfig'
    
    export default {
        name:'top',
        data(){
            return {
                title:'',
                logo:''
            }
        },
        mounted(){
            this.init();
        },
        methods:{
            init(){
                
                let logo_img = LOGO_IMAGE ? LOGO_IMAGE : "MaxviewLogo";

                this.logo=require('../../../assets/VM/'+ logo_img +'.png') //logo image init
                this.title=VM_TILTE; //title init

            }
        }
    }
</script>

<style scoped>
  .top{
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
  }
  #top .ImgResponsive{
    width: 11%;
    height: 5.5%;
    position:absolute;
    top: 5%;
  }

  #top .TopTitle{
    height: 6%;
    width: 94%;
    overflow: hidden;
  }
  #top .TopTitle >span{
    font-family: inherit;
    display: inline-block;
    font-weight: 500;
    line-height: 1.1;
    margin-top: 0.5%;
    margin-bottom: 0.3%;
    color: rgba(200,242,250);
    /* margin-left: 16%; */
     margin-left: 26.5%;
    text-shadow: 0 0 6px #1765B3;
  }
  /* 小屏幕（显示器，小于等于 1920px） */
    @media (max-width: 1920px) {
        #top .TopTitle > span {
          font-size: 2rem;
        }
    }
    /* 大屏幕（显示器，大于等于 1920px） */
    @media (min-width: 1921px) {
      #top .TopTitle > span {
          font-size: 5rem;
        }
    }
</style>
