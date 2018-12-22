<template>
  <div  @click="locationEquimpent">
    <Card class="clickStatic" :style="{backgroundColor:Obj.clickStatus?'#a1cacb':'#fff'}">
      <p slot="title">{{Obj.objtypeName}}</p>
      <!-- 开 -->
      <img src="../../../assets/UM/照明-开.png" v-if="Obj.objtypeId==11 && isOpen" class="img">
      <!-- 关 -->
      <img src="../../../assets/UM/照明-关.png" v-if="Obj.objtypeId==11 && !isOpen" class="img">
      <div class="switchContent">
        <i-switch v-model="Obj.ObjVal" @on-change="change" class="switch">
          <span slot="open" class="open">ON</span>
          <span slot="close" class="close">OFF</span>
        </i-switch>
        <span v-if="initOff" class="initOff">OFF</span>
      </div>
      <div style="margin-top: 3vh">{{Obj.ObjName}}</div>
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
        isOpen: this.ObjVal,
        initOff: true
      };
    },
    components: {Light, Fans},
    methods: {
      change() {
        this.$emit('changeStatus', this.Obj.id, this.Obj.ObjVal,this.Obj.datatypeId,null);
        this.isOpen = !this.isOpen
        this.initOff = false
      },
      //定位设备
      locationEquimpent() {
        this.Obj.clickStatus = !this.Obj.clickStatus;
        this.$emit('changeStatus', this.Obj.id, this.Obj.ObjVal,this.Obj.datatypeId,this.Obj.clickStatus);
      },
    },
    mounted() {

    },
  }
</script>

<style scoped>
  .ivu-switch:after {
    width: 30px;
    height: 30px;
    top: -1px;
  }
  
  .ivu-switch{
    background-color: #9e9e9e;
  }
  .ivu-switch-checked:after {
    left: 42px;
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
    height: 25vh;
  }
  .open{
    position: absolute;
    top: 2px;
    left: 0;
  }
  .close{
    position: absolute;
    top: 2px;
    right: -40px;
  }
  .switchContent{
    position: relative;
    margin-top: 2vh;
  }
  .switch{
    width: 74px;
    height: 30px;
    position: relative;
  }

  .img{
    width: 28%;
    height: 28%;
  }
  .initOff{
    position: absolute;
    top: 14px;
    left: 114px;
    color: white;
  }
</style>
