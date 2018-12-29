<template>
  <div>
   <p>
     {{text}}<Button type="primary" @click="beginPlan">开始</Button>
   </p>
    <p>
      {{text2}}<Button type="primary" @click="closedMQ">关闭</Button>
    </p>

  </div>
</template>

<script>
  export default {
    name: "text-plan",
    data() {
      return {
        text: "点击按钮开始执行预案",
        text2: "点击按钮关闭执行预案",
      }
    },
    mounted() {
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
      showPlanTip() {
        var _this=this;
        this.$Modal.warning({
          title: "预案提示",
          content: "系统已开始执行预案，请跳转至预案管理进行处理。",
          onOk: () => {
            _this.$router.push("/UM/plans/execute/processKey");
          },
        });
      },
      beginPlan() {
        this.openMQ();
        this.startMQ();
        this.acceptPlanData();
      },
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
    },
  }
</script>

<style scoped>

</style>
