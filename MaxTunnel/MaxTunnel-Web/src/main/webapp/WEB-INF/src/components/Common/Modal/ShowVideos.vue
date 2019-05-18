<template>
  <Modal v-model="modalPrams.state" :width="modalWidth" title="视频">
    <div class="modal-body" :style="{height:modalHeight+'px'}">
      <slot name="body">
        <div  >
          <Row :gutter="16">
            <Col :span="videoSpan" v-for="(item,index) in videosList" :key="index" style="margin-top: 16px">
            <div  :class="{ oneScreen: videoSpan == 24, monitor: videoSpan == 12, fourMonitor: videoSpan ==8 }">
              <video-component v-bind:video="item" v-bind:id="'camera'+item.id"></video-component>
            </div>
            </Col>
          </Row>
        </div>
      </slot>
    </div>
    <div class="modal-footer">
      <slot name="footer">
        <Button type="primary" size="small" @click.native="" v-if="showModelFooter"
                style="float: right;margin-right: 5%;margin-bottom: 1%">清除告警
        </Button>
      </slot>
      <Button class="modal-default-button" type="info" @click="show.state=false" v-show="showButton">OK</Button>
    </div>
  </Modal>
</template>

<script>
  import videoComponent from '../Video/VideoComponent.vue'

  export default {
    name: "show-videos",
    props: {
      modalPrams: {
        type: Object,
        default: function () {
          return {state: false, videosList: []};
        }
      },
      bottom: { //鼠标点击位置Bottom
        default: 0
      },
      left: { //鼠标点击位置left
        default: 0
      },
      showButton: { //是否显示按钮
        type: Boolean,
      },
      showModelFooter: {
        type: Boolean,
        default: false
      },
    },
    data() {
      return {
        videosList:[],
        curVideo: null,
        videoNum:0,
        modalWidth: 500,
        modalHeight: 500,
        arrow: {
          state: false,
          className: 'chevron-down'
        },
        borderPadding: 38,
        videoSpan: 0,
      }
    },
    computed: {
    },
    methods: {
      reflashVideo(){
        var _this=this;
        _this.videosList=_this.modalPrams.vedioIdList;
        _this.videoNum=_this.modalPrams.vedioIdList.length;
        console.log( _this.videosList);
        _this.setVideoSpan();
      },
      setVideoSpan() {
        let _this = this;
        _this.videoSpan = 0;
        if (_this.videoNum == 1) {
          _this.videoSpan = 24;
        }
        if (_this.videoNum > 1 && _this.videoNum <= 4) {
          _this.videoSpan = 12;
        }
        if (_this.videoNum > 4) {
          _this.videoSpan = 8;
        }
      },
    },
    components: {
      videoComponent
    },
    watch: {
      'videoNum'() {
        this.setVideoSpan();
      },
    },
    mounted() {
      this.modalWidth = window.innerWidth * 0.6
      this.modalHeight = window.innerHeight * 0.68
      this.setVideoSpan();
    },
  }
</script>

<style scoped>
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

  .modal-body {
    line-height: 1.5;
    overflow: hidden;
    transition: all .3s ease;
    color: #17233d;
  }
  .modal-enter .modal-container, .modal-leave .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
  }

  .model-content::after {
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

  .modal-footer {
    text-align: center;
  }

  .monitor {
    height: 33vh;
    width: 100%;
  }

  .fourMonitor {
    height: 33vh;
  }

  .oneScreen {
    height: 66vh;
  }

  .active {
    background-color: rgba(25, 190, 107, 0.4);
  }
</style>
