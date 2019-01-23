<template>
  <div class="allDiv">
    <div class="conditions">
      <Row>
        <Col span="4">
          <span>流程状态：</span>
          <Select v-model="conditions.finished" style="width:60%">
            <Option value=null>所有</Option>
            <Option v-for="item in workOrderStatus" :value="item.key" :key="item.key">{{ item.value }}</Option>
          </Select>
        </Col>
        <Col span="4">
          <span>责任班组：</span>
          <Select v-model="conditions.groupId" style="width:60%">
            <Option value="">所有</Option>
            <Option v-for="item in Groups" :value="item.id" :key="item.id">{{ item.name }}</Option>
          </Select>
        </Col>
        <Col span="4">
            <span>开始时间：</span>
            <DatePicker type="datetime" v-model="conditions.startTime" placeholder="请输入开始时间" style="width: 60%"></DatePicker>
        </Col>
        <Col span="4">
            <span>结束时间：</span>
            <DatePicker type="datetime" v-model="conditions.endTime" placeholder="请输入结束时间" style="width: 60%"></DatePicker>
        </Col>
        <Col span="4">
            <Button type="primary" icon="ios-search" size="small" @click="conditionChange()">查询</Button>
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
                <span  @click="edit(index)" :disabled="item.isFinished">编辑</span>
              </Col>
              <Col span="12" class="timeContainer">
                <div>{{item.inspectTime}}</div>
              </Col>
            </Row>
          </div>
        </Col>
      </Row>
      <!-- </Scroll> -->
    </div>
    <div class="page">
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
    //从数据库中读取所有的小组option选项
    PatrolService.getGroups().then(
      (result)=>{
        _this.Groups = result;
      },
      (error)=>{
        _this.Log.info(error)
      })
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
  font-size: 2.2vmin;
}
.details-bottom div{
  margin: 10px;
}
.planStatus,.patrolGroup{
  color:#a4a4a4;
  font-size: 1.4vmin;
  margin-bottom: 2px;
}
.option{
  background: #F7F9FA;
  line-height: 30px;
  font-size: 1.66vmin;
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
    font-size: 1.3vmin;
    color: orange;
    line-height: 40px;
    text-align: center;
}
/* .planName{
  font-size: 1.66vmin;
} */

/* .conditions >>> .ivu-select-placeholder{
    font-size: 1.6vmin;
    height: 3vmin;
    line-height: 3vmin;
}
.conditions >>> .ivu-select-selection{
    height: 3vmin;
}
.conditions >>> .ivu-input{
  font-size: 1.2vmin;
  height: 3.2vmin;
} */
/*.conditions >>> .ivu-input-icon{
  font-size: 1.66vmin;
  width: 3.2vmin;
  height: 3.2vmin;
}*/
.page >>> .ivu-select-selection{
    height: 3.2vmin;
}
.page >>> .ivu-select-selected-value{
    font-size: 1.2vmin;
    height: 3vmin;
    line-height: 3vmin;
}
.page >>> .ivu-select-placeholder{
    font-size: 1.2vmin;
    height: 3vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-options-elevator input{
    font-size: 1.2vmin;
    height: 3vmin;
}
.page >>> .ivu-page-options-elevator{
    display: inline-block;
    height: 3.2vmin;
    line-height: 3.2vmin;
}
.page >>> .ivu-page-next{
    height: 3.2vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-next .ivu-icon{
    font-size: 1.6vmin;
}
.page >>> .ivu-page-prev{
    height: 3.2vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-prev .ivu-icon{
    font-size: 1.6vmin;
}

 /*日期选择*/
.conditions >>> .ivu-date-picker-header {
    height: 3.2vmin;
    line-height: 3.2vmin;
}

.conditions >>> .ivu-picker-panel-icon-btn{
    font-size: 1.66vmin;
    width: 1.28vmin;
    height: 2.5vmin;
}

.conditions >>> .ivu-date-picker-header-label{
    font-size: 1.66vmin;
}

@media (min-width: 1921px){
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .conditions >>> .ivu-date-picker-cells {
        width: 15vmin;
        font-size: 1.66vmin;
    }

    .conditions >>> .ivu-date-picker-cells-cell{
        width: 2vmin;
    }
    .conditions >>> .ivu-date-picker-cells-header span{
        padding-right: 2.5rem;
    }
    .detailsInfo{
      padding: 1vmin;
    }
    .option,.timeContainer{
      line-height: 4vmin;
      font-size: 1.6vmin;
    }
    .detailsList .everyList{
      border: 0.1vmin solid #ccc;
      border-radius: 0.5vmin;
    }
    .planName{
      font-size: 2.2vmin;
    }
}
</style>


