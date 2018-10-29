<template>
    <div class="top-div">
      <Row>
        <Col span="16">
          <span class="planDec">所属管廊</span>
          <Select v-model="selectedTunnelName" style="width: 150px;margin-right: 15px;font-size: 14px;" @on-change='changeTunnel()'>
            <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
          </Select>
          <span class="planDec">工单状态</span>
          <Select v-model="model2" style="width: 150px">
            <Option v-for="item in workOrderStatusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
        </Col>
        <Col span="8" align="right">
          <Button class="btn" @click="changeTunnel()">查询</Button>
          <Button class="btn">导出</Button>
          <Button class="btn" @click.native="goToMoudle({ path:'/UM/main/addPlan'})">添加</Button>
        </Col>
      </Row>
      <hr>
    </div>
</template>
<script>
import Bus from '../../../../assets/Bus.js';
import axios from "axios";
export default {
    data () {
      return{  
        selectedTunnelName:"",
        tunnels: [],
        originalTunnels: [],
        workOrderStatusList: [
          {
            value: '已分配',
            label: '已分配'
          },
          {
            value: '未分配',
            label: '未分配'
          }
        ],
        model2:''
      }
    },
    mounted(){
      //从数据库读取select的option选项
      axios.get("/tunnels ").then(response =>{
        let { code, data } = response.data;
        if (code == 200) {
          this.originalTunnels = data;
          this.tunnels = data;
          this.selectedTunnel = this.tunnels.length > 0 ? this.tunnels[0] : {};
          this.selectedTunnelName = this.selectedTunnel.name;
        }
      })
    },
    methods: {
      goToMoudle: function (path) {
        this.$router.push(path);
      },
      //将选中的option传到父组件 queryPlan
      changeTunnel(){
        let selectedTunnelId = this.selectedTunnelName;
        this.$emit('changeTunnel',selectedTunnelId);
      }
    }    
}
</script>

<style scoped>
    .planDec {
        padding-right: 5px;
        font-size: 14px;
    }
    hr{
        margin-top: 20px;
        margin-bottom: 40px;
    }
</style>

