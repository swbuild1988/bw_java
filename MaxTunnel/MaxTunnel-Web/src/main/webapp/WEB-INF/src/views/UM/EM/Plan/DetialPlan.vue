<template>
  <Tabs type="card" @on-click="changePane">
    <TabPane label="预案详情" name="detial">
      <div>
        <WorkModal v-if="showModal.val!=null" v-bind="showModal"></WorkModal>
        <Card style="width: 100%;">
          <Row :gutter="16">
            <Col span="2">
            <div class="begin">
              <img style="width:30px;height:30px;" v-bind:src="imgUrl"/>
            </div>
            </Col>
            <Col span="1">
              <div class="next">
                <img style=" width:30px;height:30px;" v-bind:src="next"/>
              </div>
            </Col>
            <div v-for="(item,index) in allSteps">
              <Col span="2"> 
                <div style="width: 7vw;height:3vh;margin-top: -10px;" v-if="index > 0 && allSteps[index - 1].actionKey == 2" :style="{paddingTop : allSteps[index - 1].toTop ? (item.toTop-1) * 3 + 'vh' : '0'}">
                  <img style="width:50px;height:50px;" v-bind:src="status"/>
                </div>
                <Tooltip placement="bottom">
                  <div :style="{ marginTop : item.toTop  ? (allSteps[index - 1].actionKey == 2 ? (item.toTop > 1 ? '6vh': '2vh') : (item.toTop > 2 ? (item.toTop-1) * 4 + 3 + 'vh' : item.toTop * 4 + 'vh')) : '0'}" class="border">
                  <Icon type="record" color=""></Icon>
                    {{ item.stepName }}
                  </div>
                  <div slot="content" class="target">
                      <p>{{ item.target }}</p>
                  </div>
                </Tooltip>
                </Col>
                <Col span="1" v-if="index + 1 != allSteps.length && item.actionKey != 2">
                <div class="next" :style="{ paddingTop : item.toTop ? (item.toTop > 2 ? (item.toTop-1) * 3 + 6 + 'vh' : (item.toTop-1) * 4 + 5 + 'vh') : '0'}">
                  <img style=" width:30px;height:30px;" v-bind:src="next"/>
                </div>
              </Col>
              <Col span="1" v-if="item.actionKey == 2">
              <div :style="{ marginTop: item.toTop ? item.toTop * 4 + 'vh' : '1vh'}">
                <img style=" width:50px;height:20px;" v-bind:src="toUp"/>
                <img style=" width:50px;height:20px;" v-bind:src="toDown"/>
              </div>
              </Col>
            </div>
          </Row>
        </Card>
        <!--<Button type="info" @click="openMQ">注册流程</Button>-->
        <!--<Button type="info" @click="startMQ">启动流程</Button>-->
        <!--<Button type="info" @click="showModal.show.state=!showModal.show.state">显示流程</Button>-->
        <!--<Button type="info" @click="makeShow">确认</Button>-->
        <!--<Button type="info" @click="closedMes">关闭</Button>-->
        <hr class="hr3"/>
        <img style="width: 100%;height: 60vh" v-bind:src="showData"/>
      </div>
    </TabPane>
    <TabPane label="执行记录" name="log">
      <div>
        <Row :gutter="16">
          <Col span="5">
          开始时间:
          <DatePicker v-model="queryPram.startTime" format="yyyy年MM月dd日-hh:mm:ss" type="datetime" placeholder="选择日期时间"
                      style="width: 200px"></DatePicker>
          </Col>
          <Col span="5">
          结束时间:
          <DatePicker v-model="queryPram.endTime" format="yyyy年MM月dd日-hh:mm:ss" type="datetime" placeholder="选择日期时间"
                      style="width: 200px"></DatePicker>
          </Col>
          <Col span="2" offset="12">
          <Button type="warning" shape="circle" icon="ios-search" @click="queryTable">查询</Button>
          </Col>
          <Col span="24" style="margin-top: 10px;">
          <hr class="hr1"/>
          </Col>
          <Col span="24" style="margin-top: 20px;">
          <Table stripe border :columns="tableColumns" :data="tableData"></Table>
          <Modal width="900px;"
                 title="详细信息:"
                 v-model="showTableDetial"
                 :mask-closable="false">
            <Table stripe border :columns="detialTableColumns" height="522" :data="detialTableData"></Table>
          </Modal>
          </Col>
        </Row>
        <div style="position: absolute;float: right;width: 100%;bottom: 50px;">
          <Page style="position: relative;float: right;right: 2px;" @on-page-size-change="handlePageSize"
                :total="pageTotal" :page-size="queryPram.pageSize"
                :current="queryPram.pageNum" show-total show-elevator show-sizer placement="top"
                @on-change="changePage">
          </Page>
        </div>
      </div>
    </TabPane>
  </Tabs>

</template>

<script>
  import start from '../../../../assets/UM/start.png'
  import status from '../../../../assets/UM/status.png'
  import toDown from "../../../../assets/UM/toDown.png"
  import next from '../../../../assets/UM/next.png'
  import toUp from "../../../../assets/UM/toUp.png"
  import showData from "../../../../assets/UM/demo.png"
  import WorkModal from '../../../../components/Common/ShowWorkFlowData'
  import { PlanService } from '../../../../services/planService'

  export default {
    name: "detial-plan",
    data() {
      return {
        imgUrl: start,
        next: next,
        status: status,
        toUp: toUp,
        toDown: toDown,
        showData: showData,
        pageTotal: 1,
        showTableDetial: false,
        queryPram: {
          startTime: null,
          endTime: null,
          pageNum: 1,
          pageSize: 12,
          processKey: null,
        },
        showModal: {
          show: { //默认隐藏
            state: false
          },
          flowData: null
        },
        tableColumns: [
          {
            title: 'Id',
            key: 'processInstanceId'
          },
          {
            title: '开始时间',
            key: 'startTime'
          },
          {
            title: '结束时间',
            key: 'endTime'
          },
          {
            title: '操作',
            key: 'action',
            width: 150,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'default'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.show(params.index)
                    }
                  }
                }, '查看详情')
              ]);
            }
          }
        ],
        detialTableColumns: [
          {
            title: 'Id',
            key: 'id'
          },
          {
            title: '开始时间',
            key: 'startTime'
          },
          {
            title: '结束时间',
            key: 'endTime'
          },
          {
            title: '流程',
            key: 'activityName'
          },
          {
            title: '流程类型',
            key: 'activityType'
          },

        ],
        detialTableData: [],
        tableData: [],
        allSteps: []
      }
    },
    mounted() {
      this.queryPram.processKey = this.$route.params.processKey
      this.getProcess()
      // this.getMes();
    },
    methods: {
      // 获取预案步骤
      getProcess(){
        let _this = this
        PlanService.getAllSteps(this.queryPram.processKey).then(
          result=>{
            // this.Log.info(result)
            let index = result.planStatus.findIndex(step=>{
              return step.stepName == '值班人员确认'    
            })
            if(index > -1){
              // result.planStatus.splice(index,1)
              result.planStatus[index].actionKey = 2
            }
            this.allSteps = []
            this.allSteps = result.planStatus
            let branchIndexs = []
            this.allSteps.filter((step,index)=>{
              if(step.actionKey == 2){
                branchIndexs.push(index)
              }
              switch(step.targetName){
                case '类型':
                  step.target = step.stepName.slice(0,2)+'所有类型为' + step.targetValue + '的' + step.stepName.slice(2)
                  break
                case '指定对象':
                  step.target = step.stepName.slice(0,2)+'id 为' + step.targetValue + '的'+ step.stepName.slice(2)
                  break
              }
            })
            let indexRange = []
            this.allSteps.forEach((step,index)=>{
              if(index > 6 && Number.isInteger((index - 7) / 8) && step.actionKey == 2 || index == 6 && step.actionKey == 2){
                indexRange[0] = index
                indexRange[1] = index + 8
              }
              let count = index > indexRange[0] && index <= indexRange[1] ? 1 : 0
              step.toTop = null
              let rows = index > 6 ? 6 + Math.floor(((index - 7) / 8)) * 8 : 0
              branchIndexs.forEach(a=>{
                if(index > a && (index > rows ? a > rows : 1)){
                  count ++
                }
              })
              step.toTop = count
            })
          },
          error=>{
            this.Log.info(error)
          })
      },
      //切换路由
      goToMoudle(path) {
        this.$router.push(path);
      },
      //切换页面
      changePage(index) {
        let _this = this;
        _this.queryPram.pageNum = index;
        _this.queryTable();
      },
      //切换页码数
      handlePageSize(value) {
        this.queryPram.pageSize = value;
        this.queryTable();
      },

      //点击切换面板事件
      changePane(name) {
        if (name == "log") {
          this.queryTable();
        }
        else {
        }
      },

      // 查询表数据
      queryTable() {
        this.queryPram.processKey = this.$route.params.processKey;
        let prams = {};
        prams = this.queryPram;
        let _this = this;
        _this.axios.post("plans/his-proc-inst/datagrid", prams).then(result => {
          let {code, data} = result.data;
          if (code == 200) {
            _this.tableData = [];
            data.list.forEach(a => {
              let temp = {};
              temp.startTime = new Date(a.startTime).format("YYYY-MM-dd hh:mm:ss");
              temp.endTime = new Date(a.endTime).format("YYYY-MM-dd hh:mm:ss");
              temp.processInstanceId = a.processInstanceId;
              _this.tableData.push(temp);
            })
            _this.pageTotal = data.totalCount;
          }
        })
      },

      //查询详细信息
      queryDetialData(id) {
        this.axios.get("plans/his-proc-inst/task/" + id).then(result => {
          let _this = this;
          let {code, data} = result.data;
          if (code == 200) {
            _this.detialTableData = [];
            data.forEach(a => {
              let temp = {};
              temp.activityName = a.activityName;
              temp.activityType = a.activityType;
              temp.endTime = new Date(a.endTime).format("YYYY-MM-dd hh:mm:ss");
              temp.startTime = new Date(a.startTime).format("YYYY-MM-dd hh:mm:ss");
              temp.id = a.id;
              _this.detialTableData.push(temp);
            })
          }
        })
      },

      show(index) {
        this.showTableDetial = true;
        this.queryDetialData(this.tableData[index].processInstanceId);
      },

      getMes() {
        this.MQ.InitMQ(1, "/queue/queuePlan", "", this.callback);
      },
      closedMes() {
        this.MQ.closeMQ();
      },
      // 连接成功回调函数
      callback(respond) {
        let result = JSON.parse(respond.body);
        if (result) {
          this.showModal.flowData = result;
        }
        return result;
      },

      openMQ() {
        let _this = this;
        _this.axios.get("/fireplans/deploy").then(result => {
          let {msg, code, data} = result.data;
          if (code == 200) {
            console.log(data);
          }
        })
      },

      startMQ() {
        let _this = this;
        _this.axios.get("/fireplans/demo").then(result => {
          let {msg, code, data} = result.data;
          if (code == 200) {
            console.log(data);
          }
        })
      },
      makeShow() {
        let _this = this;
        _this.axios.get("fireplans/" + this.showModal.flowData.iid + "/yes").then(result => {
          let {msg, code, data} = result.data;
          if (code == 200) {
            console.log(data);
          }
        })
      },
    },
    components: {WorkModal},
    watch: {
      '$route': function () {
        // $route发生变化时再次赋值
        this.queryPram.processKey = this.$route.params.processKey;
        this.allSteps = []
        this.getProcess()
      },
    },
  }
</script>

<style scoped>
  .ivu-tabs  >>> .ivu-tabs-bar{
    margin-bottom: 0px!important;
  }

  .ivu-tabs  >>> .ivu-card-body{
    padding: 10px !important;
  }
  .ivu-tooltip >>> .ivu-tooltip-inner{
    background-color: rgba(110,110,110,0.9);
    text-align: center;
    color: rgb(174,205,237);
    height: 100px;
    font-size: 14px;
    display: table-cell;
    vertical-align: middle;
    font-weight: bold;
  }
  .ivu-tooltip >>> .ivu-tooltip-arrow{
    border-bottom-color: rgb(112,112,112);
  }
  .border {
    box-shadow: 5px 5px 15px #cdf5ff inset;
    border: 1px solid #d9fff1;
    border-radius: 8px;
    color: #fff;
    width: 7vw;
    height:6vh;
    background-color: #22a5f5;
    padding:7px;
    cursor: pointer;
  }

  .hr3 {
    height: 7px;
    border: none;
    border-top: 5px ridge #45D6D8;
  }

  .hr1 {
    margin: 0 auto;
    border: 0;
    height: 5px;
    background: #333;
    background-image: linear-gradient(to right, #ccc, #333, #ccc);
  }
  .next{
    display: table-cell;
    text-align: center;
    width: 4vw;
    height: 6vh;
    vertical-align: middle;
  }
  .begin{
    display: table-cell;
    text-align: right;
    width: 7vw;
    height: 6vh;
    vertical-align: middle;
  }
  .target{
    width: 200px;
    letter-spacing: 2px;
  }
  
</style>
