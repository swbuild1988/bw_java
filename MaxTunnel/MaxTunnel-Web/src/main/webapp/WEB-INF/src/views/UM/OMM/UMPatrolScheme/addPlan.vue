<template>
    <div>
        <div class="planBox">
            <span class="planDec">计划编号</span>
            <Input v-model="uploadPlan.id" placeholder="" style="width: 150px;margin-right: 15px;"></Input>
            <span class="planDec">计划名称</span>
            <Input v-model="uploadPlan.name" placeholder="" style="width: 150px;margin-right: 15px;"></Input>
        </div>

        <div class="planBox">
            <span class="planDec">所属管廊</span>
            <Select v-model="uploadPlan.tunnelId" style="width: 150px;margin-right: 15px;font-size: 14px;" @on-change='changeTunnel()'>
              <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
            <span class="planDec">计划状态</span>
            <Input v-model="uploadPlan.status" placeholder="" style="width: 150px;margin-right: 15px;"></Input>
        </div>

        <div class="planBox">
            <span class="planDec">责任班组</span>
            <Select v-model="uploadPlan.groupId" style="width: 150px;margin-right: 15px;font-size: 14px;" @on-change='changeGroup()'>
              <Option v-for="(item,index) in groups" :value="item.id" :key="index">{{ item.name }}</Option>
            </Select>
            <span class="planDec personInCharge">责任人</span>
            <Input v-model="selectedGroup.leaderName" placeholder="" style="width: 150px;margin-right: 15px;"></Input>
        </div>

        <!-- <div class="planBox">
            <span class="planDec">计划描述</span>
            <Input v-model="planDescribe" type="textarea" :autosize="{minRows: 2,maxRows: 5}" 
                  placeholder="请输入计划描述"></Input>
        </div>
        <div class="planBox">
            <span class="planDec">审批记录</span>
            <Input v-model="record" type="textarea" :autosize="{minRows: 2,maxRows: 5}" 
              placeholder="请输入审批记录"></Input>
        </div> -->
        <div class="planBox">
          <span class="planDec">日历</span>
          <Tabs value="name1">
              <TabPane label="间隔模式" name="name1">
                <div>
                  开始日期：
                  <input  v-model="startDay" type="number">
                  结束日期：<input  v-model="endDay" type="number">
                  间隔天数：<input  v-model="intervalDay" type="number">
                  <Button type="default" @click="getDay">确定</Button>
                  <Button type="default" @click="defaultBtn">重置</Button>
                </div>
              </TabPane>
              <TabPane label="星期模式" name="name2">
                <Checkbox v-model="monday" @on-change="getWeek">星期一</Checkbox>
                <Checkbox v-model="tuesday" @on-change="getWeek">星期二</Checkbox>
                <Checkbox v-model="wednesday" @on-change="getWeek">星期三</Checkbox>
                <Checkbox v-model="thursay" @on-change="getWeek">星期四</Checkbox>
                <Checkbox v-model="friday" @on-change="getWeek">星期五</Checkbox>
                <Checkbox v-model="saturday" @on-change="getWeek">星期六</Checkbox>
                <Checkbox v-model="sunday" @on-change="getWeek">星期日</Checkbox>
                <Button type="default">确定</Button>
                <Button type="default" @click="defaultWeekBtn">重置</Button>
              </TabPane>
              <TabPane label="自定义" name="name3">
                请选择您需要的时间
              </TabPane>
          </Tabs>
          <calender ref="calender" v-on:childByValue="getActiveText" v-model="uploadPlan.dayStr"></calender>         
        </div>
        <div class="planBox planBtn">
            <Button type="primary" class="btn">取消</Button>
            <Button type="primary" class="btn" @click="submitPlan">提交</Button>
        </div>

        <!-- <div class="planBox">
          <el-radio-group v-model="selectedMode" @change="radioGroupChange">
            <el-radio :label="taskModes.intervalMode.key">{{taskModes.intervalMode.name}}</el-radio>
            <el-radio :label="taskModes.weekMode.key">{{taskModes.weekMode.name}}</el-radio>
            <el-radio :label="taskModes.customMode.key">{{taskModes.customMode.name}}</el-radio>
          </el-radio-group>
        </div>
        <div v-show="taskModes.intervalMode.value">
            <input v-model="taskModes.intervalMode.startDay">
           开始时间： <DatePicker type="date" v-model="startDay" @on-change="startDay=$event"  placeholder="请选择开始时间" style="width: 150px;margin-right: 15px;font-size: 14px;"></DatePicker>
            结束时间：
            <input  v-model="taskModes.intervalMode.endDay">
            <DatePicker type="date" v-model="endDay" @on-change="endDay=$event"  placeholder="请选择结束时间" style="width: 150px;margin-right: 15px;font-size: 14px;"></DatePicker>
            间隔时间：<input type="number"  v-model="taskModes.intervalMode.intervalDay">
            <Button type="default" @click="getDay">确定</Button>
            <Button type="default" @click="defaultBtn">重置</Button>
        </div>
        <div v-show="taskModes.weekMode.value">
          <Checkbox v-model="monday">星期一</Checkbox>
          <Checkbox v-model="tuesday">星期二</Checkbox>
          <Checkbox v-model="wednesday">星期三</Checkbox>
          <Checkbox v-model="thursay">星期四</Checkbox>
          <Checkbox v-model="friday">星期五</Checkbox>
          <Checkbox v-model="saturday">星期六</Checkbox>
          <Checkbox v-model="sunday">星期日</Checkbox>
          <Button type="default" @click="getWeek">确定</Button>
        </div>

         -->

        

    </div>
 
</template>

<script>
import axios from "axios";
import calender from "../../../Common/calender.vue";
export default {
  name: "plans",
  components: {
    calender
  },
  data() {
    return {
      uploadPlan:{
        id: "",
        name: "",
        tunnelId: 0,
        groupId: 0,
        inspectTime: new Date(),
        tasks:[],
        status: "",
        dayStr: "",
        monday: false,
        tuesday: false,
        wednesday: false,
        thursay: false,
        friday: false,
        saturday: false,
        sunday: false,
      },
      // workOrderStatus: "",
      groups: [{
        "id":1,
        "name":"group1",
        "leaderId":1,
        "leaderName":"l1"
      },{
        "id":2,
        "name":"group2",
        "leaderId":2,
        "leaderName":"l2"
      },{
        "id":3,
        "name":"group3",
        "leaderId":null,
        "leaderName":null
      }],
      selectedGroup:{
        leaderName:"",
      },
      selectedMode:"",
      // taskModes:{
      //   intervalMode:{
      //     key:"intervalMode",
      //     name:"间隔模式",
      //     value:true,
      //     startDay:1,
      //     endDay:31,
      //     intervalDay:3,
      //   },
      //   weekMode:{
      //     key:"weekMode",
      //     name:"星期模式",
      //     value:false,
      //     monday: false,
      //     tuesday: false,
      //     wednesday: false,
      //     thursay: false,
      //     friday: false,
      //     saturday: false,
      //     sunday: false
      //   },
      //   customMode:{
      //     key:"customMode",
      //     name:"自定义",
      //     value:false,
      //   }
      // },
      tunnels: [{
        "id":1,
        "name":"管廊1",
      },
      {
        "id":2,
        "name":"管廊2",
      },
      {
        "id":3,
        "name":"管廊3",
      }],
      startDay: this.startDay,
      endDay: "",
      intervalDay: "",
      monday: false,
      tuesday: false,
      wednesday: false,
      thursay: false,
      friday: false,
      saturday: false,
      sunday: false
    };
  },
  // mounted() {
  //   从数据库读取所属管廊select的option选项
  //   axios.get("/tunnels ").then(response => {
  //     let { code, data } = response.data;
  //     if (code == 200) {
  //       this.tunnels = data;
  //       this.selectedTunnel = this.tunnels.length > 0 ? this.tunnels[0] : {};
  //       this.uploadPlan.tunnelId = this.selectedTunnel.id;
  //     }
  //   }),
  //     从数据库读取责任班组select的option选项
  //     axios.get("/inspection-groups").then(response => {
  //       let { code, data } = response.data;
  //       if (code == 200) {
  //         this.groups = data;
  //         this.selectedGroup = this.groups.length > 0 ? this.groups[0] : this.selectedGroup;
  //         this.uploadPlan.groupId = this.selectedGroup.id;
  //         console.log("groups:", this.groups);
  //         console.log("selectedGroup:", this.selectedGroup);
  //       }
  //     });
  // },
  mounted(){
  },
  methods: {
    radioGroupChange(){
      console.log("selectedModel:",this.selectedMode);
      this.setTaskModesValue(this.selectedMode == this.taskModes.intervalMode.key,
                        this.selectedMode == this.taskModes.weekMode.key,
                        this.selectedMode == this.taskModes.customMode.key,)
      console.log("taskModes", this.taskModes);
    },
    setTaskModesValue(interval, week, custom){
      this.taskModes.intervalMode.value = interval;
      this.taskModes.weekMode.value = week;
      this.taskModes.customMode.value = custom;
    },
    submitPlan() {
      //提交数据
      // this.axios
      //   .post("/inspection-plans", planContent)
      //   .then(response => {
        //     console.log(response);
      //   })
      //   .catch(function(error) {
        //     console.log(error);
      //   });
      console.log(this.uploadPlan)
      this.$router.push("/UM/main/queryPlan");
    },
    changeTunnel() {
      // let selectedTunnelId = this.tunnelId;
    },
    changeGroup() {
      // let selectGroupId = this.groupId;
      this.selectedGroup = this.groups.filter(a=>a.id == this.uploadPlan.groupId)[0];
      // this.leader = this.group[selectGroupId - 1].leaderName;
    },
    getDay() {
      this.$refs.calender.interVal(this.startDay, this.endDay, this.intervalDay);
      this.$refs.calender.outputHeighLight();
    },
    defaultBtn() {
      this.$refs.calender.cancelActive();
      this.$refs.calender.outputHeighLight();
    },
    defaultWeekBtn() {
      this.$refs.calender.cancelActive();
      this.$refs.calender.outputHeighLight();
      this.monday=false;
      this.tuesday = false;
      this.wednesday = false;
      this.thursay = false;
      this.friday = false;
      this.saturday = false;
      this.sunday = false;
    },
    getWeek(){
      var week = []
      if(this.monday==true){
        week.push("1")
      }
      if(this.tuesday==true){
        week.push("2")
      }
      if(this.wednesday==true){
        week.push("3")
      }
      if(this.thursay==true){
        week.push("4")
      }
      if(this.friday==true){
        week.push("5")
      }
      if(this.saturday==true){
        week.push("6")
      }
      if(this.sunday==true){
        week.push("0")
      }
      this.$refs.calender.chooseWeek(week);
      this.$refs.calender.outputHeighLight();
      console.log(week);
    },
        getActiveText(childValue){
          this.dayStr = childValue;
          this.uploadPlan.tasks=[];
            for(var i=0;i<this.dayStr.length;i++){
                this.uploadPlan.tasks.push({taskTime:this.dayStr[i]})
            }
        }
  }
}
</script>

<style  scoped>
.planDec {
  padding-right: 5px;
  font-size: 14px;
}
.personInCharge {
  letter-spacing: 0.5em;
  margin-right: -0.5em;
}
.planBox {
  padding-top: 20px;
}
.ivu-input-wrapper {
  width: 385px;
}
.planBtn {
  padding-top: 30px;
  padding-left: 307px;
}
.btn {
  margin-left: 10px;
}
input[type='number']{
    display: inline-block;
    height: 32px;
    width: 55px;
    line-height: 1.5;
    padding: 4px 7px;
    font-size: 12px;
    border: 1px solid #dddee1;
    border-radius: 4px;
    color: #495060;
    background-color: #fff;
    background-image: none;
    position: relative;
    cursor: text;
    transition: border .2s ease-in-out,background .2s ease-in-out,box-shadow .2s ease-in-out;
    margin-right: 5px;
}
</style>