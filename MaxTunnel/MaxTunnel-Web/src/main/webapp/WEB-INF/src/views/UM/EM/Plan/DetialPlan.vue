<template>
  <Tabs type="card" @on-click="changePane">
    <TabPane label="预案详情" name="detial">
      <div>
        <WorkModal v-if="showModal.val!=null" v-bind="showModal"></WorkModal>
        <Card style="width: 100%;">
          <Row :gutter="16">
            <Col span="2">
            <div style="position: relative;float: right; margin-top: 20px;margin-right: 0px;">
              <img style="width:30px;height:30px;" v-bind:src="imgUrl"/>
              <img style=" width:30px;height:30px;" v-bind:src="next"/>
            </div>
            </Col>
            <Col span="2">
            <div style="width: 7vw;height:6vh;background-color: #22a5f5;padding:7px; " class="border">
              <Icon type="record" color=""></Icon>
              <!--<div style="position: relative;float: right;margin-right: 36%; ">-->
              开启声音报警
              <!--</div>-->
            </div>
            </Col>
            <Col span="1">
            <div style="position: relative;float: right; margin-top: 20px;margin-right: 0px;">
              <img style=" width:30px;height:30px;" v-bind:src="next"/>
            </div>
            </Col>
            <Col span="2">
            <div
              style="position: relative;float: left;width: 7vw;height:6vh;background-color: #22a5f5;padding:7px;padding-top: 20px; "
              class="border">
              <Icon type="record" color=""></Icon>
              调用摄像头
            </div>
            </Col>
            <Col span="1">
            <div style="position: relative;float: right; margin-top: 20px;margin-right: 0px;">
              <img style=" width:30px;height:30px;" v-bind:src="next"/>
            </div>
            </Col>
            <Col span="1">
            <div style="margin-top: 10px;">
              <img style=" width:50px;height:20px;" v-bind:src="toUp"/>
              <img style=" width:50px;height:20px;" v-bind:src="toDown"/>
            </div>
            </Col>
            <Col span="2">
            <div style="position: relative;float: left; margin-top: -20px;margin-left: -20px;">
              <img style="width:50px;height:50px;" v-bind:src="status"/>
            </div>
            <div
              style="margin-top: 50px;width: 7vw;height:6vh;background-color: #22a5f5;padding:7px;padding-top: 20px;  "
              class="border">
              <Icon type="record" color=""></Icon>
              <div style="position: relative;float: right;margin-right: 36%; ">
                打开风机
              </div>
            </div>
            </Col>
            <Col span="1">
            <div style="position: relative;float: right; margin-top: 70px;margin-right: 0px;">
              <img style=" width:30px;height:30px;" v-bind:src="next"/>
            </div>
            </Col>
            <Col span="2">
            <div
              style="margin-top: 50px;width: 7vw;height:6vh;background-color: #22a5f5;padding:7px;padding-top: 20px;  "
              class="border">
              <Icon type="record" color=""></Icon>
              <div style="position: relative;float: right;margin-right: 36%; ">
                打开风阀
              </div>
            </div>
            </Col>
            <Col span="1">
            <div style="position: relative;float: right; margin-top: 70px;margin-right: 0px;">
              <img style=" width:30px;height:30px;" v-bind:src="next"/>
            </div>
            </Col>
            <Col span="2">
            <div
              style="margin-top: 50px;width: 7vw;height:6vh;background-color: #22a5f5;padding:7px;padding-top: 20px;  "
              class="border">
              <Icon type="record" color=""></Icon>
              <div style="position: relative;float: right;margin-right: 36%; ">
                打开百页
              </div>
            </div>
            </Col>
            <Col span="1">
            <div style="position: relative;float: right; margin-top: 70px;margin-right: 0px;">
              <img style=" width:30px;height:30px;" v-bind:src="next"/>
            </div>
            </Col>
            <Col span="2">
            <div style="margin-top: 50px;width: 7vw;height:6vh;background-color: #22a5f5;padding:7px; " class="border">
              <Icon type="record" color=""></Icon>
              <div style="position: relative;float: right;margin-right: 36%; ">
                启动<br/>
                干粉灭火
              </div>
            </div>
            </Col>
            <Col span="1">
            <div style="position: relative;float: right; margin-top: 70px;margin-right: 0px;">
              <img style=" width:30px;height:30px;" v-bind:src="next"/>
            </div>
            </Col>
            <Col span="2">
            <div style="margin-top: 50px;width: 7vw;height:6vh;background-color: #22a5f5;padding:7px;  " class="border">
              <Icon type="record" color=""></Icon>
              <div style="position: relative;float: right;margin-right: 36%; ">
                通知<br/>
                相关单位
              </div>
            </div>
            </Col>
          </Row>
        </Card>
        <!--<Button type="info" @click="openMQ">注册流程</Button>-->
        <!--<Button type="info" @click="startMQ">启动流程</Button>-->
        <!--<Button type="info" @click="showModal.show.state=!showModal.show.state">显示流程</Button>-->
        <!--<Button type="info" @click="makeShow">确认</Button>-->
        <!--<Button type="info" @click="closedMes">关闭</Button>-->
        <hr class="hr3"/>
        <img style="width:  100%" v-bind:src="showData"/>
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
        <div style="position: absolute;float: right;width: 100%;bottom: 0px;">
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
        tableData: []
      }
    },
    mounted() {

      // this.getMes();
    },
    methods: {
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
        this.queryPram = this.$route.params.processKey;
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

  .border {
    box-shadow: 5px 5px 15px #cdf5ff inset;
    border: 1px solid #d9fff1;
    border-radius: 8px;
    color: #fff;
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
</style>
