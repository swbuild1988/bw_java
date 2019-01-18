<template>
  <div @click="locationEquimpent">
    <Card class="clickStatic" :style="{backgroundColor:Obj.clickStatus?'#a1cacb':'#fff'}">
      <p slot="title" style="font-size: 1.66vmin;height: 1.7vmin;line-height: 1.66vmin">{{Obj.objtypeName}}</p>
      <!-- 数值正常时 -->
      <img src="../../../assets/UM/temp-normal.png" v-if="Obj.objtypeId==1 && normal || Obj.objtypeId==8 && normal" class="img">
      <img src="../../../assets/UM/humidity-normal.png" v-if="Obj.objtypeId==2 && normal" class="img">
      <img src="../../../assets/UM/O2-normal.png" v-if="Obj.objtypeId==3 && normal" class="img">
      <img src="../../../assets/UM/H2S-normal.png" v-if="Obj.objtypeId==4 && normal" class="img">
      <img src="../../../assets/UM/CH4-normal.png" v-if="Obj.objtypeId==5 && normal" class="img">
      <img src="../../../assets/UM/liquidLevel-normal.png" v-if="Obj.objtypeId==21 && normal" class="img">
      <img src="../../../assets/UM/CO-normal.png" v-if="Obj.objtypeId==6 && normal" class="img">

      <img src="../../../assets/UM/temp-error.png" v-if="Obj.objtypeId==1 && !normal || Obj.objtypeId==8 && !normal" class="img">
      <img src="../../../assets/UM/humidity-error.png" v-if="Obj.objtypeId==2 && !normal" class="img">
      <img src="../../../assets/UM/O2-error.png" v-if="Obj.objtypeId==3 && !normal" class="img">
      <img src="../../../assets/UM/H2S-error.png" v-if="Obj.objtypeId==4 && !normal" class="img">
      <img src="../../../assets/UM/CH4-error.png" v-if="Obj.objtypeId==5 && !normal" class="img">
      <img src="../../../assets/UM/liquidLevel-error.png" v-if="Obj.objtypeId==21 && !normal" class="img">
      <img src="../../../assets/UM/CO-error.png" v-if="Obj.objtypeId==6 && !normal" class="img">
      <p class="value">{{Obj.ObjVal}}<span style="font-size: 2vmin">{{ unit }}</span></p>
      <div class="extre">
        <div class="min" v-if="Obj.minValue != null">
          <Icon type="arrow-up-c"></Icon>
          <span>{{Obj.minValue}}</span>
        </div>
        <div class="max" v-if="Obj.minValue != null">
          <Icon type="arrow-down-c"></Icon>
          <span>{{Obj.maxValue}}</span>
        </div>
      </div>
      <div style="font-size: 1.66vmin">{{Obj.ObjName}}</div>
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
          GasMax: 100,
          GasMin: 0,
          clickStatus:false,
          datatypeId:"",
          minValue: 0,
          maxValue: 100
        }
    }
  },
    data: function () {
      return {
        gasMax: 200,
        gasMin: 0,
        normal: true,
        unit: '',
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
    watch: {
      'Obj.ObjVal': function(){
        if(this.Obj.minValue != null && this.Obj.maxValue != null && (this.Obj.ObjVal < this.Obj.minValue || this.Obj.ObjVal > this.Obj.maxValue)){
          this.normal = false
        } else {
          this.normal = true
        }
      }
    },
    mounted(){
      if(this.Obj.minValue != null && this.Obj.maxValue != null && (this.Obj.ObjVal < this.Obj.minValue || this.Obj.ObjVal > this.Obj.maxValue)){
        this.normal = false
      } else {
        this.normal = true
      }

      switch(this.Obj.objtypeId){
        case 1:
          this.unit = '℃'
          break
        case 2:
          this.unit = '%'
          break
        case 3:
          this.unit = '%'
          break
        case 4:
          this.unit = 'ppm'
          break
        case 5:
          this.unit = '%'
          break
        case 6:
          this.unit = '%'
          break
      }

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
    font-size: 3vmin;
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
    font-size: 1.66vmin;
  }
  .img{
    width: 28%;
    height: 28%;
    margin-top: 1%;
  }
</style>
