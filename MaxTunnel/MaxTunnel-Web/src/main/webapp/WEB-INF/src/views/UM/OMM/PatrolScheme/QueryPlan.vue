<template>
  <div class="allDiv">
    <div class="conditions">
      <Row>
        <Col span="6">
          <span class="planDec">流程状态：</span>
          <Select v-model="conditions.finished"  @on-change='conditionChange()' style="width:60%">
            <Option value=null key="0">所有</Option>
            <Option v-for="item in workOrderStatus" :value="item.key" :key="item.key">{{ item.value }}</Option>
          </Select>
        </Col>
        <Col span="6">
          <span class="planDec">责任班组：</span>
          <Select v-model="conditions.groupId"   @on-change='conditionChange()' style="width:60%">
            <Option value="">所有</Option>
            <Option v-for="item in Groups" :value="item.id" :key="item.id">{{ item.name }}</Option>
          </Select>
        </Col>
        <Col span="6">
            <span>开始时间：</span>
            <DatePicker type="datetime" v-model="conditions.startTime" placeholder="请输入开始时间" style="width: 60%" @on-change="conditionChange()"></DatePicker>
        </Col>
        <Col span="6">
            <span>结束时间：</span>
            <DatePicker type="datetime" v-model="conditions.endTime" placeholder="请输入结束时间" style="width: 60%" @on-change="conditionChange()"></DatePicker>
        </Col>
      </Row>
    </div>
    <div class="list">
      <!-- <Scroll :on-reach-bottom="handleReachBottom"  height="777px;"> -->
      <Row class="detailsList code-row-bg" type="flex"  align="top">
        <Col span="6" dis-hover v-for="(item,index) in plans" :key="index" ref="planBox">
          <div class="everyList">
            <div class="detailsInfo">
              <div class="details">
              <div class="icon">
                <Icon type="leaf" size="24" color="#fff"></Icon>
              </div>
                <div class="details-top">
                  <!-- <div class="tunnelName">{{item.tunnelName}}</div> -->
                  <div class="planName">{{item.name}}</div>
                </div>
                <div class="details-bottom">
                  <div>
                    <p class="planStatus">流程状态</p>
                    <p class="planStatusDec">{{item.processStatus}}</p>
                  </div>
                  <div>
                    <p class="patrolGroup">责任班组</p>
                    <p class="patrolGroupName">{{item.groupName}}</p>
                  </div>
                </div>
              </div>
            </div>
            <Row class="option">
              <Col span="6">
                <span @click="show(index)">详情</span>
              </Col>
              <Col span="6">
                <span  @click="edit(index)">编辑</span>
              </Col>
              <Col span="12" class="timeContainer">
                <div>{{item.inspectTime}}</div>
              </Col>
            </Row>
          </div>
        </Col>
      </Row>
      <!-- </Scroll> -->
    <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
      placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>   
    </div>
  </div>
</template>
<script>
import Enum from "../../../../../static/Enum.json";
import { TunnelService } from '../../../../services/tunnelService';
import { PatrolService } from '../../../../services/patrolService';

export default {
  data() {
    return {
      plans: [
        {
          id: 1,
          tunnelName: '凤岭北路',
          name: '张三',
          status: '未',
          groupName: 'group1'
        }
      ],
      tunnelId: 1,
      tunnels: [],
      Groups: [],
      workOrderStatus: Enum.patrolPlanOrderStatus,
      conditions: {
        tunnelId: null,
        finished: null,
        groupId: null,
        startTime: null,
        endTime: null
      },
      page: {
        pageNum: 1,
        pageSize: 12,
        pageTotal: 0
      },
      pageStyle: {
        position: 'absolute',
        bottom: '20px',
        right: '15px'
      }
    };
  },
  computed: {
    params() {
      let param = {
        pageNum: this.page.pageNum,
        pageSize: this.page.pageSize,
        tunnelId: this.$route.params.id,
        groupId: this.conditions.groupId,
        finished: this.conditions.finished,
        startTime: this.conditions.startTime,
        endTime: this.conditions.endTime
      };
      return Object.assign({}, param);
    }
  },
  watch:{
      '$route': function () {
        //2. $route发生变化时再次赋值planId
        this.tunnelId = this.$route.params.id;
        this.tunnels.forEach(a => {
          if (a.id == this.tunnelId) {
            this.conditionChange();
          }
        });
      },
  },
  mounted() {
    let _this = this
    // 从数据库读取select的option选项
    TunnelService.getTunnels().then(
      (result)=>{
        _this.tunnels = result;
      },
      (error)=>{
        _this.Log.info(error)
      })
    // this.axios.get("/tunnels ").then(response => {
    //   let { code, data } = response.data;
    //   if (code == 200) {
    //     this.tunnels = data;
    //   }
    // });
    //从数据库中读取所有的小组option选项
    PatrolService.getGroups().then(
      (result)=>{
        _this.Groups = result;
      },
      (error)=>{
        _this.Log.info(error)
      })
    // this.axios.get("/inspection-groups").then(response => {
    //   let { code, data } = response.data;
    //   if (code == 200) {
    //     this.Groups = data;
    //   }
    // });
    this.conditionChange();
  },
  methods: {
    conditionChange: function() {
      let _this = this
      PatrolService.patrolPlanDatagrid(this.params).then(
        (result)=>{
          for (let index in result.list) {
              result.list[index].inspectTime = new Date(
                result.list[index].inspectTime
              ).format("yyyy-MM");
            }
            _this.plans = result.list;
            _this.page.pageTotal = result.total;
        },
        (error)=>{
          _this.Log.info(error)
        })
      // this.axios.post("inspection-plans/datagrid", (this.params)).then(response => {
      //     let { code, data } = response.data;
      //     if (code == 200) {
      //       for (let index in data.list) {
      //         data.list[index].inspectTime = new Date(
      //           data.list[index].inspectTime
      //         ).format("yyyy-MM");
      //       }
      //       this.plans = data.list;
      //       this.page.pageTotal = data.total;
      //     }
      //   });
    },
    goToMoudle: function(path) {
      this.$router.push(path);
    },
    // type 1:查看， 2：编辑
    goToMoudle2: function(index, type) {
      this.$router.push({
        name: "UMPatrolDetails",
        params: {
          id: this.plans[index].planId,
          type: type
        }
      });
    },
    // type 编辑
    goToMoudle3: function(index, type) {
      this.$router.push({
        name: "UMEditPlan",
        params: {
          id: this.plans[index].planId,
          type: type
        }
      });
    },
    show(index) {
      this.goToMoudle2(index, Enum.pageType.Read);
    },
    edit(index) {
      this.goToMoudle3(index, Enum.pageType.Edit);
    },
    handlePage(value) {
        this.page.pageNum = value;
        this.conditionChange();

    },
    handlePageSize(value) {
        this.page.pageSize = value;
        this.conditionChange();
    },
  }
};
</script>
<style scoped>
.planDec {
  margin-right: 5px;
}
.detailsList .everyList{
  border: 1px solid #1c5d853d;
  width: 82%;
  margin-bottom: 20px;
  border-radius: 4px;
}
.detailsInfo{
  height: 80%;
}
.icon,.details,.details-bottom div{
  display: inline-block;
}
.icon{
  background-color: #009DFF;
  width: 30px;
  height: 30px;
  border-radius: 100%;
  vertical-align: top;
  margin-top: 15px;
  margin-left: 5px;
  margin-right: 2px;
}
.ivu-icon{
    margin-left: 5px;
    margin-top: 1px;
}
.details-top{ 
    font-size: 16px;
    font-weight: 700;
    line-height: 48px;
    padding-top: 5px;
    display: inline-block;
    margin-left: 5px;
}
.planStatusDec,.patrolGroupName{
  font-size: 22px;
}
.details-bottom div{
  margin: 10px;
}
.planStatus,.patrolGroup{
  color:#a4a4a4;
  font-size: 14px;
  margin-bottom: 2px;
}
.option{
  background: #F7F9FA;
  line-height: 30px;
  font-size: 16px;
  color: #979696;
  border-top: 1px solid #e8e8e8
}
.option .ivu-col-span-6{
  border-right: 1px solid #e8e8e8;
  margin: 5px 0px;
  text-align: center;
  cursor: pointer;
}
.timeContainer{
    width: 50%;
    font-size: 13px;
    color: orange;
    line-height: 40px;
    text-align: center;
}
</style>


