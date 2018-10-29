<template>
  <div  @click="locationEquimpent">
    <Card class="clickStatic" :style="{backgroundColor:Obj.clickStatus?'#a1cacb':'#fff'}">
      <p slot="title">{{Obj.objtypeName}}</p>

      <div style="background-color:#7D8481;height: 100px; ">
        <span style="float:left;color: #ffffff;margin-top: 3.2vh;width:40%;font-size: 18px; ">{{Obj.ObjName}}</span>
        <div style="float:left;position: fixed;margin-left: 120px;margin-top:20px;">
          <i-switch style="position: fixed; width: 120px;height: 50px;" v-model="Obj.ObjVal" @on-change="change">
            <span slot="open" style="font-size: 20px;top:15px;position: absolute">ON</span>
            <span slot="close" style="font-size: 20px;top:15px;position: absolute;left: 48px;">OFF</span>
          </i-switch>
        </div>
        <!-- 风机 -->
        <div v-if="Obj.objtypeId==10" style="float:right;margin-left: 5px;">
          <Fans :switchData="Obj.ObjVal"></Fans>
        </div>
        <!-- 灯 -->
        <div v-if="Obj.objtypeId==11" style="float:right;margin-left: 5px;">
          <Light :switchData="Obj.ObjVal"></Light>
        </div>
      </div>
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
      };
    },
    components: {Light, Fans},
    methods: {
      change() {
        this.$emit('changeStatus', this.Obj.id, this.Obj.ObjVal,this.Obj.datatypeId,null);
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
    width: 70px;
    height: 50px;
    top: 0px;
  }

  .ivu-switch-checked:after {
    left: 50px;
  }

  .clickStatic:focus, .clickStatic:hover {
    color: #fff;
    border-color: #597ecb;
  }
</style>
