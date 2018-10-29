<template>
  <div v-show="show.state" transition="modal" >
    <div class="setCenter">
      <div class="modal-container">
        <!--header内容-->
        <div class="modal-header " style="position: relative">
          <slot name="header"><h2 slot="header" style="color: #22a5f5">{{flowData.key}}</h2>
            <a class="ivu-modal-close" style="top:0;right:0;">
              <i class="ivu-icon ivu-icon-ios-close-empty" @click="show.state=!show.state"></i> </a>
          </slot>
        </div>
        <!--body内容-->
        <div class="modal-body" style="max-height: 70vh;overflow:auto ">
          <slot name="body">
            <Card>
              <Row :gutter="16">
                <div id="startSigh">
                  <Col span="24">
                  <div class="setCenter">
                    <img style="width:30px;height:30px; margin-bottom: 5px;" v-bind:src="start"/>
                  </div>
                  </Col>
                </div>
                <div   v-for="(item,index)  in flowData.val" :key="index">
                  <Col span="24" >
                  <div class="setCenter">
                    <img style=" width:30px;height:30px;" v-bind:src="down"/>
                  </div>
                  </Col>
                  <Col span="24" >
                  <div class="setCenter">
                    <div class="workFlowModel " >
                      <Icon type="record" :color="item.status=='完成'?pass:working"></Icon>
                      &nbsp
                      {{item.node}}
                    </div>
                  </div>
                  </Col>
                </div>
                <div id="endSign" v-if="flowData.end">
                  <Col span="24" >
                  <div class="setCenter">
                    <img style=" width:30px;height:30px;" v-bind:src="down"/>
                  </div>
                  </Col>
                  <Col span="24">
                  <div class="setCenter">
                    <img style="width:50px;height:50px;margin-left: 3px;" v-bind:src="end"/>
                  </div>
                  </Col>
                </div>
              </Row>
            </Card>
          </slot>
        </div>
        <!--foot内容-->
        <div class="modal-footer">
          <slot name="footer">
          </slot>
          <Button class="modal-button" type="info" @click="makeShow" v-show="showCheck">确认</Button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import start from '../../assets/UM/start.png'
  import end from '../../assets/UM/status.png'
  import toDown from "../../assets/UM/toDown.png"
  import down from '../../assets/UM/down.png'
  import toUp from "../../assets/UM/toUp.png"
  import showData from "../../assets/UM/demo.png"

  export default {
    name: "show-work-flow-data",
    data() {
      return {
        pass: "#65ec00",
        working: "#e3ec26",
        cancle: "#fe821c",
        reject: "#fe0909",
        status: this.show,
        start: start,
        down: down,
        end: end,
        toUp: toUp,
        toDown: toDown,
        showData: showData,
        showCheck: false,
      }
    },
    props: {
      show: {
        type: Object,
      },
      top: {
        default: 0
      }, //鼠标点击位置top
      left: {
        default: 0
      },//鼠标点击位置left
      flowData: {
        type: Object,
        default: [{
          key: "消防预案执行流程12",
          iid: "40036",
          end: false,
          val: [{node: "开启声光报警", status: "完成"}]
        }]
      }
    },
    methods: {
      makeShow(){
        let _this = this;
        _this.axios.get("fireplans/"+this.flowData.iid+"/yes").then(result => {
          let {msg, code, data} = result.data;
          if (code == 200) {
            console.log(data);
          }
        })
      },
    },
    watch: {
      flowData: function () {
        let temp = this.flowData.val.filter(a => a.status == "进行中" && a.node == "值班人员确认")[0];
        if (temp) {
          this.showCheck = true;
        }
        else {
          this.showCheck = false;
        }
      },
    },
  }
</script>

<style scoped>
  .setCenter {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: Center;
  }
  .workFlowModel {
    box-shadow: 5px 5px 15px #cdf5ff inset;
    border: 1px solid #d9fff1;
    border-radius: 8px;
    color: #fff;
    position: relative;
    padding: 20px 0 0 20px;
    float: left;
    width: 18vw;
    height: 6vh;
    background-color: #22a5f5;
  }

  .modal-container {
    position:absolute;
    top: 2px;
    border: 1px solid #fff;
    border-radius: 8px;
    z-index: 9998;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, .2);
    width: 300px;
    padding: 10px 20px;
    background-color: #fff;
    transition: all .3s ease;
    font-family: Helvetica, Arial, sans-serif;
  }

  .modal-header h3 {
    margin-top: 0;
    color: #42b983;
  }

  .modal-body {
    margin:  20px  0 0;
    overflow-y: auto;
  }

  .modal-button {
    float: right;
    right: 0px;
    top: 5px;
    position: relative;
  }

  .modal-enter, .modal-leave {
    opacity: 0;
  }

  .modal-enter .modal-container, .modal-leave .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
  }
</style>

