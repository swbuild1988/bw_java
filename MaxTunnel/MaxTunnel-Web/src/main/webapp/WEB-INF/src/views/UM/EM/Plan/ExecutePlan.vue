<template>
  <div>
    <ComfirmStep  v-bind:curModal="curModal" ></ComfirmStep>
    <Row>
      <div v-for="(step,index) in stepList ">
        <Col span="3">
        <CustomStep :stepLength=stepList.lenght :curStatusVal=step.statusVal :curIndex=index+1
                    :curStatusStr=step.statusStr :stepName=step.stepName></CustomStep>
        </Col>
      </div>
    </Row>
    <hr class="hr3"/>
    <Row>
      <Col span="12">
      <Card :bordered="false" style="box-shadow: 5px 6px 4px rgba(0, 0, 0, 0.2);border: 1px solid rgba(0, 0, 0, 0.2);">
        <p slot="title" style="font-size: 20px;">执行日志</p>
        <div v-for="item in planData.process">
          <Row style="line-height: 30px;font-size: 16px;">
            <Col span="8">
            {{item.node}}
            </Col>
            <Col span="5">
            {{item.status}}
            </Col>
            <Col span="3">
            Admin
            </Col>
            <Col span="8">
            <span style="position: relative;float: right;right: 5px;">2018-10-22 16:00</span>
            </Col>
          </Row>
        </div>
      </Card>
      </Col>
      <Col span="11" style="position: relative;float: right;right: 5px;">
      <div style="height: 68vh;" class="coolBox">
        <video v-bind="curVideo"></video>
      </div>
      </Col>
    </Row>
  </div>
</template>

<script>
  import CustomStep from "../../../../components/UM/EM/CustomStep"
  import {getFormatTime} from '../../../../scripts/DateFormat.js'
  import ComfirmStep from '../../../../components/UM/EM/ConfirmStep'
import video from '../../../../components/Common/Video/VideoComponent'
  export default {
    name: "execute-plan",
    data() {
      return {
        curModal:{
          isShow:false,
          instanceId:null,
          objectId:null
        },
        curVideo:{
          id:"executePlanVideo",
          video:{},
        },
        stepList: [
          {stepName: "开启声光报警", statusStr: "待进行", statusVal: 3},
          {stepName: "调用摄像头", statusStr: "待进行", statusVal: 3},
          {stepName: "值班人员确认", statusStr: "待进行", statusVal: 3},
          {stepName: "打开风机", statusStr: "待进行", statusVal: 3},
          {stepName: "打开风阀", statusStr: "待进行", statusVal: 3},
          {stepName: "打开百叶", statusStr: "待进行", statusVal: 3},
          {stepName: "启动干粉灭火", statusStr: "待进行", statusVal: 3},
          {stepName: "通知相关单位", statusStr: "待进行", statusVal: 3},
        ],
        queryPram: {
          processKey: null,
        },
      }
    },
    mounted() {
      this.syncStep();
    },
    beforeDestroy() {

    },
    computed: {
      planData: {
        get() {
          return this.$store.getters.getPlanData
        },
        set(value) {
          this.$store.commit('setPlanData', value)
        }
      }
    },
    methods: {
      //切换路由
      goToMoudle(path) {
        this.$router.push(path);
      },

      syncStep() {
        var _this = this;
        if (_this.planData) {
          _this.planData.process.forEach(a => {
            _this.stepList.forEach(b => {
              if(b.stepName==a.node){
                if(a.status== 1){
                  b.statusVal=1;
                  b.statusStr="已完成"
                }
                else if(a.status== 2){
                  b.statusVal=2;
                  b.statusStr="进行中"
                  if(a.node=="值班人员确认"){
                    _this.curModal.isShow=true;
                    _this.curModal.instanceId= _this.planData.processInstanceId;
                    _this.curModal.objectId= _this.planData.objectId;
                  }
                }
                else{
                  b.statusVal=3;
                  b.statusStr="待进行"
                }
              }
            })
          })
        }
      },
    },
    components: {CustomStep,ComfirmStep},
    watch: {
      '$route': function () {
        this.queryPram.processKey = this.$route.params.processKey;
      },
      "planData": function () {
        this.syncStep();
      },
    },
  }
</script>

<style scoped>
  .border {
    box-shadow: 5px 5px 15px #cdf5ff inset;
    border: 1px solid #d9fff1;
    border-radius: 8px;
    color: #fff;
  }

  .hr3 {
    height: 7px;
    border: none;
    border-top: 5px ridge #45D6D8;
  }

  .hr1 {
    margin: 0 auto;
    border: 0;
    height: 5px;
    background: #333;
    background-image: linear-gradient(to right, #ccc, #333, #ccc);
  }

  .ivu-steps > > > .ivu-steps-title {
    font-size: 18px;
  }

  .ivu-steps > > > .ivu-steps-content {
    font-size: 18px;
  }

  .coolBox {
    border: 1px solid rgba(0, 0, 0, 0.2);
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    background-clip: padding-box;
    background: rgba(36, 40, 42, 0.5);
    box-shadow: 0 0 13px 3px rgba(0, 0, 0, 0.5);
  }
</style>
