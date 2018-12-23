<template>
  <div @click="locationEquimpent">
    <Card class="clickStatic" :style="{backgroundColor:Obj.clickStatus?'#a1cacb':'#fff'}">
      <p slot="title">{{Obj.objtypeName}}</p>
      <!-- 数值正常时 -->
      <img src="../../../assets/UM/温度-蓝.png" v-if="Obj.objtypeId==1 || Obj.objtypeId==8" class="img">
      <img src="../../../assets/UM/湿度-蓝.png" v-if="Obj.objtypeId==2" class="img"> 
      <img src="../../../assets/UM/氧气-蓝.png" v-if="Obj.objtypeId==3" class="img">
      <img src="../../../assets/UM/H2S-蓝.png" v-if="Obj.objtypeId==4" class="img"> 
      <img src="../../../assets/UM/甲烷-蓝.png" v-if="Obj.objtypeId==5" class="img">
      <img src="../../../assets/UM/液位仪.png" v-if="Obj.objtypeId==21" class="img">
      <img src="../../../assets/UM/CO-蓝.png" v-if="Obj.objtypeId==6" class="img"> 
      <p class="value">{{Obj.ObjVal}}</p>
      <div class="extre">
        <div class="min">
          <Icon type="arrow-up-c"></Icon>
          <span>{{Obj.minValue}}</span>
        </div>
        <div class="max">
          <Icon type="arrow-down-c"></Icon>
          <span>{{Obj.maxValue}}</span>
        </div>
      </div>
      <div>{{Obj.ObjName}}</div>
      <!-- <Row style="background-color:#048488;height: 100px; ">
        <Col span="5">
        <span style="float:left;color: #ffffff;margin-top: 0.8vh;font-size: 16px;left: 2px; ">{{Obj.ObjName}}</span>
        </Col>
        <Col span="14">
        <span class="valueclass">{{Obj.ObjVal}}</span>
        </Col>
        <Col span="5" style="padding: 0;"> -->
        <!-- 温度 --> <!-- 湿度 -->
       <!--  <div v-if="Obj.objtypeId==1||Obj.objtypeId==2"
             style="height: 100px;position: relative;float: right;right: 0px;width: 5vw;">
          <Temperature v-bind="Obj"></Temperature>
        </div> -->
        <!-- 氧气，甲烷，一氧化碳 -->
        <!-- <div v-else style="height: 100px;position: relative;float: right;right: 0px;width: 5vw;">
          <Gas v-bind="Obj"></Gas>
        </div>
        </Col>
      </Row> -->
    </Card>
  </div>
</template>

<script>
  import Temperature from '../../../components/Common/SimulateData/Temperature'
  import Gas from '../../../components/Common/SimulateData/Gas'

  export default {
    name: "show-simulated-data",
    props: {
        Obj: {
            type: Object,
            default: {
                id: "",
                objtypeName: "",
                ObjName: "",
                ObjVal: "",
                imgUrl: "",
                objtypeId: "",
                maxValue: 100,
                minValue: 0,
                clickStatus: false,
                datatypeId: "",
            }
        }
    },
    data: function () {
      return {
        gasMax: 200,
        gasMin: 0,
      };
    },
    components: {Temperature, Gas},
    methods: {
      //定位设备
      locationEquimpent() {
        this.Obj.clickStatus= !this.Obj.clickStatus;
        this.$emit('changeStatus', this.Obj.id, this.Obj.ObjVal,this.Obj.datatypeId,this.Obj.clickStatus);
      },
    },
    watch: {},
    mounted(){
     
    }
  }
</script>

<style scoped>
  .valueclass {
    float: left;
    z-index: 1001;
    line-height: 100px;
    height: 100px;
    background-color: #49CECC;
    color:#0F4F5A;
    font-size: 70px;
    font-family: "7LED"
  }

  .clickStatic:focus, .clickStatic:hover {
   /* color: #fff;*/
    background-color: #49CECC;
  }
  .clickStatic{
    background-color: white;
    border-radius: 10px;
    margin: 1vh 0;
    text-align: center;
    height: 25vh;
  }
  .value{
    margin-top: 4%;
    font-size: 30px;
    height: 4vh;
  }
  .min{
    position: absolute;
    top: 0;
    left: 2%;
    color: rgb(45,140,240);
  }
  .max{
    position: absolute;
    top: 0;
    right: 2%;
    color: red;
  }
  .extre{
    position: relative;
    height: 3vh;
  }
  .img{
    width: 28%;
    height: 28%;
    margin-top: 1%;
  }
</style>
