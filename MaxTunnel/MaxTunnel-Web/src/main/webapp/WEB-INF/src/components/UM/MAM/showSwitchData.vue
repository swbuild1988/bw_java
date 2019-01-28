<template>
  <div  @click="locationEquimpent">
    <Card class="clickStatic" :style="{backgroundColor:Obj.clickStatus?'#a1cacb':'#fff'}">
      <p slot="title" style="font-size: 1.6vmin">{{Obj.objtypeName}}</p>
      <!-- 开 -->
      <img src="../../../assets/UM/lamp-on.png" v-if="Obj.objtypeId==11 && curValue" class="img">
      <img src="../../../assets/UM/风机gif.gif" v-if="Obj.objtypeId==10 && curValue" class="img">
      <img src="../../../assets/UM/水泵gif.gif" v-if="Obj.objtypeId==59 && curValue" class="img">
      <img src="../../../assets/UM/blinds-on.png" v-if="Obj.objtypeId==58 && curValue" class="img">
      <img src="../../../assets/UM/井盖开.png" v-if="Obj.objtypeId==56 && curValue" class="img">
      <img src="../../../assets/UM/红外正常gif.gif" v-if="Obj.objtypeId==57 && curValue" class="img">
      <img src="../../../assets/UM/门-开.png" v-if="Obj.objtypeId==55 && curValue" class="img">
      <img src="../../../assets/UM/报警-红gif.gif" v-if="Obj.objtypeId==41 && curValue" class="img">
      <!-- 关 -->
      <img src="../../../assets/UM/lamp-off.png" v-if="Obj.objtypeId==11 && !curValue" class="img">
      <img src="../../../assets/UM/风机.png" v-if="Obj.objtypeId==10 && !curValue" class="img">
      <img src="../../../assets/UM/水泵.png" v-if="Obj.objtypeId==59 && !curValue" class="img">
      <img src="../../../assets/UM/blinds-off.png" v-if="Obj.objtypeId==58 && !curValue" class="img">
      <img src="../../../assets/UM/井盖.png" v-if="Obj.objtypeId==56 && !curValue" class="img">
      <img src="../../../assets/UM/红外.gif" v-if="Obj.objtypeId==57 && !curValue" class="img">
      <img src="../../../assets/UM/门-关.png" v-if="Obj.objtypeId==55 && !curValue" class="img">
      <img src="../../../assets/UM/报警.png" v-if="Obj.objtypeId==41 && !curValue" class="img">
      <!-- 声光报警 41，红外 57，门禁 55-->

      <div class="switchContent">
        <i-switch v-model="curValue" @on-change="confirm" size="large">
          <span slot="open">开</span>
          <span slot="close">关</span>
        </i-switch>
      </div>
      <p class="time" v-if="isTimeShow">采集时间：{{ Obj.time }}</p>
      <div style="margin-top: 2vh;font-size: 1.66vmin">{{Obj.ObjName}}</div>
      <!-- <p slot="title">{{Obj.objtypeName}}</p>
      <div style="background-color:#7D8481;height: 100px; ">
        <span style="float:left;color: #ffffff;margin-top: 3.2vh;width:40%;font-size: 18px; ">{{Obj.ObjName}}</span>
        <div style="float:left;position: fixed;margin-left: 120px;margin-top:20px;">
          <i-switch style="position: fixed; width: 120px;height: 50px;" v-model="Obj.ObjVal" @on-change="change">
            <span slot="open" style="font-size: 20px;top:15px;position: absolute">ON</span>
            <span slot="close" style="font-size: 20px;top:15px;position: absolute;left: 48px;">OFF</span>
          </i-switch>
        </div> -->
        <!-- 风机,百叶，水泵，井盖 -->
       <!--  <div v-if="Obj.objtypeId==10 || 58 || 59 || 56" style="float:right;margin-left: 5px;">
          <Fans :switchData="Obj.ObjVal" :objId="Obj.objtypeId"></Fans>
        </div> -->
        <!-- 灯 -->
        <!-- <div v-if="Obj.objtypeId==11" style="float:right;margin-left: 5px;">
          <Light :switchData="Obj.ObjVal"></Light>
        </div>
      </div> -->
    </Card>
    <!-- <Modal v-model="confirmFlag" title="确认" @on-ok="open" :styles="{top: '80px', left: '20%',width: '300px'}">
        <p>确定打开{{ Obj.ObjName }}开关吗</p>
    </Modal> -->
  </div>
</template>

<script>
  //灯组件
  import Light from "../../../components/Common/SimulateData/Light"
  //风机组件
  import Fans from "../../../components/Common/SimulateData/Fans"

  export default {
    name: "show-switch-data",
    props: {
      Obj: {
        type: Object,
        default: {
          id: "",
          objtypeName: "",
          ObjName: "",
          ObjVal: false,
          imgUrl: "",
          objtypeId: "",
          clickStatus:false,
          datatypeId:"",
        }
      },
    },
    data: function () {
      return {
        curImgUrl: "",
        initOff: true,
        confirmFlag: false,
        curValue: null,
        isTimeShow: false
      };
    },
    components: {Light, Fans},
    methods: {
      confirm(data) {
        this.$nextTick(()=>{
          this.curValue = !data
        })
        let text = data ? '确定打开'+this.Obj.objtypeName + this.Obj.ObjName + '吗?' : '确定关闭 '+this.Obj.objtypeName + this.Obj.ObjName + ' 吗?'
        this.$Modal.confirm({
          render: (h)=>{
            return h('span', text)
          },
          onOk: ()=>{
            this.curValue = !this.curValue
            this.initOff = false
            this.change()
          }
        })
       
      },
      //定位设备
      locationEquimpent() {
        this.Obj.clickStatus = !this.Obj.clickStatus;
        this.$emit('changeStatus', this.Obj.id, this.Obj.ObjVal,this.Obj.datatypeId,this.Obj.clickStatus);
      },
      change() {
        this.$emit('changeStatus', this.Obj.id, this.Obj.ObjVal,this.Obj.datatypeId,null);
      }
    },
    mounted() {
      if(this.Obj.time != ''){
        this.isTimeShow = true
      }
      this.curValue = this.Obj.ObjVal > 0 ? true : false
    },
  }
</script>

<style scoped>
  .ivu-switch:after {
    width: 2vmin;
    height: 2vmin;
    left: 0.1vmin;
    top: 0.1vmin;
  }
  .ivu-switch-large{
    width: 6vmin;
  }
  .ivu-switch{
    background-color: #9e9e9e;
    height: 2.4vmin;
    line-height: 2.2vmin;
  }

  .ivu-switch-checked:after {
    left: 3.7vmin;
  }
  .ivu-switch >>> .ivu-switch-inner{
    font-size: 1.2vmin;
    left: 2.2vmin;
  }
  .ivu-switch-checked{
    background-color: #2d8cf0;
    border-color: 2px solid #2d8cf0;
  }

  .clickStatic:focus, .clickStatic:hover {
    /*color: #fff;*/
    border-color: #597ecb;
  }
  .clickStatic{
    background-color: white;
    border-radius: 10px;
    margin: 1vh 0;
    text-align: center;
    height: 28vh;
  }
  .switchContent{
    position: relative;
    margin-top: 2vh;
  }

  .img{
    width: 28%;
    height: 28%;
  }
  .time {
    margin: 1vh 0;
    font-size: 1.6vmin;
  }
  .ivu-card-head p{
    height: 2vmin;
    line-height: 2vmin;
  }
</style>
