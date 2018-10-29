<template>
 <div v-show="show.state" transition="modal" >
    <div class="model-content" :style="{bottom:bottom,left:left,position:'absolute'}" >
        <div class="modal-container"
             :style="contentStyles">
               <!--header内容-->
            <div class="modal-header"
                 :style="{backgroundColor:tilteProps.backgroundColor}">
                <slot name="header">  <div class="model-header-inner" slot="header" >{{tilteProps.tilte}}</div>
                    <a class="ivu-modal-close">
                <i class="ivu-icon ivu-icon-ios-close-empty" @click="show.state=!show.state" ></i> </a>
            </slot>
             </div>
             <!--body内容-->
             <div class="modal-body" :style="{ height:bodyHeight}">
                 <slot name="body" >
                     <ul style="list-style: none;" class="modal-body-list">
                         <li  v-for=" item in data" :key="item.key">
                            <span class="contentkey">{{item.key}}</span>
                            <span class="contentVal">{{item.val}}</span>
                            <hr>
                         </li>
                     </ul>
                 </slot>
             </div>
             <!--foot内容-->
             <div class="modal-footer" >
                 <slot name="footer">
                     <Icon :type="arrow.className" class="icon-chevron" @click.native="arrow.state = !arrow.state" v-if="!showModelFooter"></Icon>
                     <Button type="primary" size="small" @click.native="sendDesModel" v-if="showModelFooter" style="float: right;margin-right: 5%;margin-bottom: 1%">清除告警</Button>
                 </slot>
                 <Button class="modal-default-button" type="info" @click="show.state=false" v-show="showButton">OK</Button>
             </div>
      </div>
    </div>
 </div>
</template>

<script>
    import  BaseData from './mixins/modalBaseData'

    export default {
        name: "show-map-data-modal",
        mixins:[ BaseData ],
        props:{
            bottom:{ //鼠标点击位置Bottom
                default:0
            },
            left:{ //鼠标点击位置left
                default:0
            },
            data:{ //展示数据
                default:[]
            },
            showButton:{ //是否显示按钮
                type: Boolean,
            },
            showModelFooter:{
                type: Boolean,
                default:false
            },
        },
        data(){
          return {
              arrow:{
                  state:false,
                  className:'chevron-down'
              },
              borderPadding:38,
              bodyHeight:null,
          }
        },
        computed:{
            contentStyles(){
                let style = {};

                const width = parseInt(this.width);
                const styleWidth = {
                    width: width <= 100 ? `${width}%` : `${width}px`
                };
                Object.assign(style, styleWidth);
                return style;
            }
        },
        watch:{
            'show.state'(){

                this.calcHideHeight();
                if(!this.show.state){
                    this.arrow.state = false;
                    this.changIconClassName(this.arrow.state);
                }
            },
            'arrow.state'(){

                this.arrow.state ? this.calcShowHeight() : this.calcHideHeight();
                this.changIconClassName(this.arrow.state);
            }
        },
        mounted(){
            this.init();
        },
        methods: {
            init(){
            },
            calcHideHeight(){
                this.$nextTick(()=>{
                    this.bodyHeight = (this.getDOMElement('modal-body-list').children[0].offsetHeight * 5 +(this.borderPadding / 2) ) +'px';
                })
            },
            calcShowHeight(){
                this.$nextTick(()=>{
                    this.bodyHeight = (this.getDOMElement('modal-body-list').offsetHeight +this.borderPadding) +'px';
                })
            },
            changIconClassName(state){
                this.arrow.className = state ? 'chevron-up' : 'chevron-down';
            },
            sendDesModel () {
                this.$emit('showDesModel')
            }
        },
    }
</script>

<style scoped>
  .contentkey{
    font-size: 14px;
  }
  .contentVal{
    font-size: 14px;
    position:relative;;
    float: right;
    right: 10px;
  }
  .modal-container {
    border-radius: 6px;
    box-shadow: 0px 0px 5px rgba(0, 0, 0, .5);
    width: 300px;
    margin: 0px auto;
    background-color: #fff;
    transition: all .3s ease;
    font-family: Helvetica, Arial, sans-serif;
    overflow: hidden;
  }
  .modal-header{
      padding: 7px 8px;
      border-bottom: 1px solid #aaa;
      line-height: 1px;
  }
  .modal-header .model-header-inner {
      display: inline-block;
      width: 100%;
      font-size: 14px;
      line-height: 20px;
      color: #17233d;
      font-weight: 700;
      overflow: hidden;
      white-space: nowrap;
  }
  .modal-header .ivu-modal-close{
      top:0;
      right:8px;
      color: #17233d;
      transition: color .2s ease;
  }
  .modal-body {
      padding: 16px 16px 0 16px;
      line-height: 1.5;
      overflow: hidden;
      transition: all .3s ease;
      color: #17233d;

  }
  .modal-default-button {
    /*float: right;*/
    /*top: 5px;*/
    /*position: relative;*/
  }
  .modal-enter, .modal-leave {
      opacity: 0;
  }
  .modal-enter .modal-container, .modal-leave .modal-container {
      -webkit-transform: scale(1.1);
      transform: scale(1.1);
  }
  .model-content::after{
      content: "";
      position: absolute;
      bottom: -50px;
      left: 50px;
      border-width: 0 20px 50px 0px;
      border-style: solid;
      border-color: transparent #fff;
      display: block;
      width: 0;
  }
    .modal-footer{
        text-align: center;
    }
    .icon-chevron{
        font-size: 20px;
    }
</style>
