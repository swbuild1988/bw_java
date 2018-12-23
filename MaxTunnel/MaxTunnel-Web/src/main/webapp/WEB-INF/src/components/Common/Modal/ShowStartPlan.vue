<!--开始预案-->
<template>
  <div>
    <Modal style="z-index: 1001" v-model="modalPrams.state" title="启动预案" @on-ok="startPlan" ok-text="启动"
           cancel-text="取消">
      <div style="text-align: center;font-size: 16px;">
        <Row>
          <Col span="24" style="margin: 4px;">
          <span>预案类型:</span>
          <Select v-model="prams.planType" style="width:300px">
            <Option v-for="item in planList" :value="item.key" :key="item.key">{{ item.val }}</Option>
          </select>
          </Col>
          <Col span="24" style="margin: 4px;">
          <span style="position: relative;float:left;margin-left: 90px;margin-right: 5px;margin-top: 5px;">位置:</span>
          <treeselect :options="locationList" placeholder="请选择" v-model="prams.location"
                      style="width: 300px;float: left;position: relative;"/>
          </Col>
          <Col span="24" style="margin: 4px;">
          <span>关联告警:</span>
          <Input v-model="prams.alarmId" placeholder="无" style="width: 300px" readonly h></Input>
          </Col>
        </Row>
      </div>
    </Modal>
  </div>
</template>

<script>
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import {EnumsService} from '../../../services/enumsService.js'

  export default {
    name: "show-start-plan",
    props: {
      modalPrams: {
        type: Object,
        default: function () {
          return {state: false,};
        }
      }
    },
    data() {
      return {
        planList: [],
        locationList: [],
        prams: {
          planType: "",
          location: [],
          alarmId: "",
        }
      }
    },
    created() {
      this.initData();
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
      startMQ() {
        let _this = this;
        _this.axios.get("/emplans/start/sections/10/process-type/4001").then(result => {
          let {msg, code, data} = result.data;
          if (code == 200) {
          }
        })
      },
      openMQ() {
        let _this = this;
        _this.axios.get("emplans/deploy/4001").then(result => {
          let {msg, code, data} = result.data;
          if (code == 200) {
          }
        })
      },
      // 连接成功回调函数
      callback(respond) {
        let _this = this;
        let result = JSON.parse(respond.body);
        //将数据保存在vuex中
        _this.planData = result;
        if(_this.planData&&"/UM/plans/execute/processKey"!=_this.$router.history.current.path){
          _this.showPlanTip();
        }
      },
      //获取MQ推送的预案消息
      acceptPlanData() {
        var _this = this;
        _this.MQ._InitMQ(1, "/queue/queuePlan", "", _this.callback);
      },
      closedMQ() {
        var _this = this;
        _this.MQ.closeMQ();
      },
      startPlan() {

        this.openMQ();
        this.startMQ();
        this.acceptPlanData();
      },
      initData() {
        var _this = this;
        EnumsService.getMonitorZone().then((result) => {
          if (result) {
            console.log(result);
            result.forEach(a => {
              var temp = {};
              temp.id = a.id;
              temp.label = a.name;
              temp.children = [];
              _this.locationList.push(temp);
              if (a.list.length > 0) {
                a.list.forEach(b => {
                  var child = {};
                  child.id = a.id + "_" + b.id;
                  child.label = b.name;
                  child.children = [];
                  temp.children.push(child);
                  if (b.list.length > 0) {
                    b.list.forEach(c => {
                      var child2 = {};
                      child2.id = a.id + "_" + b.id + "_" + c.id;
                      child2.label = c.name;
                      child.children.push(child2);
                    })
                  }
                })
              }
            })
          }
        });
        EnumsService.getPlanType().then(
          result => {
            result.forEach(a => {
              let temp = {};
              temp.key = a.processKey;
              temp.val = a.key;
              _this.planList.push(temp);
            });
          },
          error => {
            _this.Log.info(error)
          })
      },
    },
    components: {
      Treeselect
    },
  }
</script>

<style scoped>
  .ivu-input {
    font-size: 16px;
    height: 38px;
  }
</style>
