<template>
  <div style="margin: 24px">
    <Row>
      <h1 style="margin-bottom: 10px;">巡检计划详情</h1>
      <Col span="12" style="padding-right: 20px;background: #fff;">
        <div class="leftContainer">
          <Form :model="plan" :label-width="100" @submit.native.prevent>
            <Col span="12">
              <FormItem label="计划编号：">
                <Input v-model='plan.planId' disabled></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="计划名称：">
                <Input v-model='plan.name' disabled></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="所属管廊：">
                <Select v-model='plan.tunnelId' disabled>
                  <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="计划状态：">
                <Input type="text" v-model="plan.processStatus" disabled></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="责任班组：">
                <Select v-model='plan.groupId' disabled>
                  <Option v-for="item in groups" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="责任人：">
                <Input v-model="plan.inspectionGroup.leader.name" disabled></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="巡检日期：">
                <Input v-model="plan.inspectTime" disabled></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="执行天数：">
                <Input v-model="plan.tasks.length" disabled></Input>
              </FormItem>
            </Col>
            <Col span="24">
              <FormItem label="计划描述：">
                <Input v-model="plan.remark" type="textarea" :rows="4" placeholder="请输入描述..." disabled></Input>
              </FormItem>
            </Col>
          </Form>
        </div>
        </Col>
        <Col span="12">
        <div class="rightContainer">
          <h3>巡检记录：（
            流程未结束：
            <div class="finish noFinish"></div>
            流程结束：
            <div class="finish isFinish"></div>
            ）
          </h3>
          <ul class="patrolRecordUl">
            <li v-for="item in patrolRecords" :value="item.id" :key="item.id" :class="[item.taskType==1?'noFinish':(item.taskType==2?'isFinish':'') ]">{{item.id}}</li>
          </ul>
          <Table border stripe :columns="columns1" :data="plan.tasks" style="margin: 20px auto;" height:440></Table>
        <Col span="24" v-show="pageType==pageTypes.Edit" style="text-align: right;margin-top: 20px;">
          <Button type="primary" @click="submit()">确定</Button>
          <Button type="primary">取消</Button>
        </Col>
       </div>
       </Col>
    </Row>
  </div>
</template>

<script>
import types from "../../../../../static/Enum.json";
import SmViewer from "../../../../components/Common/3D/3DViewer";
import { URL_CONFIG } from "../../../../../static/3DMap/js/3DMapConfig";
import { setViewAngle } from "../../../../scripts/commonFun";
import { TunnelService } from '../../../../services/tunnelService';
import { PatrolService } from '../../../../services/patrolService';
import { EnumsService } from '../../../../services/enumsService';
export default {
  name: "plans",
  data() {
    return {
      // 页面类型 1：查看 2：编辑 3：新增
      pageType: 1,
      pageTypes: types.pageType,
      plan: {
        planId: null,
        name: "",
        tunnelId: null,
        processStatus: null,
        groupId: null,
        createTime: null,
        inspectTime: null,
        tasks: [],
        remark: null,
        inspectionGroup: {
          leader: {
            name: null
          }
        }
      },
      reqstatus: [],
      // 管廊
      tunnels: [],
      groups: [
        {
          id: 1,
          name: "group1",
          leader: {
            id: 0,
            name: ""
          }
        }
      ],
      patrolRecords: [],
      columns1: [
        {
          title: "编号",
          key: "id",
          width: 70,
          align: "center"
        },
        {
          title: "巡检人",
          key: "inspectManName",
          align: "center"
        },
        {
          title: "巡检日期",
          key: "taskTime",
          width: 170,
          align: "center"
        },
        {
          title: "流程状态",
          key: "processStatus",
          align: "center"
        }
      ],
      year: 1,
      month: 1,
      days: 1
    };
  },
  components: {
    SmViewer
  },
  mounted() {
    this.plan.planId = this.$route.params.id;
    this.pageType = this.$route.params.type;
    let _this = this
    PatrolService.getPDetailByPlanId(this.plan.planId).then(
      (result)=>{
        _this.plan = result;
        // 暂定默认值
        _this.plan.inspectTime = new Date(_this.plan.inspectTime).format(
          "yyyy-MM"
        );
        _this.plan.tasks.forEach(element => {
          element.inspectTime = new Date(element.inspectTime).format(
            "yyyy-MM-dd"
          );
          element.taskTime = new Date(element.taskTime).format("yyyy-MM-dd");
        });
        _this.getRecords();
      },
      (error)=>{
        _this.Log.info(error)
      })
    // this.axios.get("inspection-plans/" + this.plan.planId).then(response => {
    //   let { code, data } = response.data;
    //   if (code == 200) {
    //     this.plan = data;
    //     // 暂定默认值
    //     this.plan.inspectTime = new Date(this.plan.inspectTime).format(
    //       "yyyy-MM"
    //     );
    //     this.plan.tasks.forEach(element => {
    //       element.inspectTime = new Date(element.inspectTime).format(
    //         "yyyy-MM-dd"
    //       );
    //       element.taskTime = new Date(element.taskTime).format("yyyy-MM-dd");
    //     });
    //     this.getRecords();
    //   }
    // });

    // 获取所有的管廊
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

    // 从数据库读取责任班组select的option选项
    PatrolService.getGroups().then(
      (result)=>{
        _this.groups = result;
      },
      (error)=>{
        _this.Log.info(error)
      })
    // this.axios.get("/inspection-groups").then(response => {
    //   let { code, data } = response.data;
    //   if (code == 200) {
    //     this.groups = data;
    //   }
    // });

    //申请状态
    EnumsService.getReqStatus().then(
      (result)=>{
        _this.reqstatus = result;
        for (let index in _this.reqstatus) {
          if (_this.reqstatus[index].val == _this.plan.requestStatus) {
            _this.plan.requestStatus = _this.reqstatus[index].key;
          }
        }
      },
      (error)=>{
        _this.Log.info(error)
      })
    // this.axios.get("/reqstatus-enums").then(response => {
    //   let { code, data } = response.data;
    //   if (code == 200) {
    //     this.reqstatus = data;
    //     for (let index in this.reqstatus) {
    //       if (this.reqstatus[index].val == this.plan.requestStatus) {
    //         this.plan.requestStatus = this.reqstatus[index].key;
    //       }
    //     }
    //   }
    // });
  },
  watch: {
    $route: function() {
      //2. $route发生变化时再次赋值planId
      this.plan.id = this.$route.params.id;
    }
  },
  methods: {
    //获取天数
    getRecords() {
      this.year = new Date(this.plan.inspectTime).getFullYear();
      this.month = new Date(this.plan.inspectTime).getMonth() + 1;

      if (this.month == 2) {
        this.days = year % 4 == 0 ? 29 : 28;
      } else if (
        this.month == 1 ||
        this.month == 3 ||
        this.month == 5 ||
        this.month == 7 ||
        this.month == 8 ||
        this.month == 10 ||
        this.month == 12
      ) {
        this.days = 31;
      } else {
        this.days = 30;
      }
      //循环得出所有的天数
      for (var i = 1; i <= this.days; i++) {
        // taskType 0:没任务， 1，任务未完成； 2： 任务完成
        this.patrolRecords.push({
          id: i,
          taskType: 0,
          getColor: function(type) {
            if (type == 1) return "noFinish";
            if (type == 2) return "isFinish";
            // return "#fff";
          }
        });
      }
      this.plan.tasks.forEach(a => {
        let day = new Date(a.taskTime).getDate();
        this.patrolRecords[day - 1].taskType = a.isFinished ? 2 : 1;
      });
    },
  }
};
</script>

<style  scoped>
.patrolRecordUl li {
  list-style: none;
  display: inline-block;
  width: 25px;
  height: 25px;
  border: 1px solid #eeeeee;
  text-align: center;
  line-height: 25px;
}
.leftContainer,
.rightContainer {
  background: #fff;
  padding: 20px;
}
h3 {
  line-height: 45px;
}
.ivu-input[disabled] {
  background: #fff;
  color: #495060;
}
.isFinish {
  background: #8524c6b3;
  color: #fff;
}
.noFinish {
  background: #338f8f;
  color: #fff;
}
.finish{
    display: inline-block;
    vertical-align: middle;
    width: 30px;
    height: 14px;
    border-radius: 4px;
    margin-right: 5px;
}
.ivu-input[disabled], fieldset[disabled] .ivu-input{
    background-color: #ffffff !important;
    color: #495060 !important;
}
</style>
