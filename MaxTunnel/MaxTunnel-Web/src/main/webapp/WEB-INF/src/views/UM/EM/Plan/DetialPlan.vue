<template>
  <div>
    <Tabs type="card" @on-click="changePane">
      <TabPane label="预案详情" name="detial">
        <Card style="width: 100%;">
          <Row>
            <Col span="1">
            <div class="begin">
              <Button type="success" @click="startPlan">启动预案</Button>
            </div>
            </Col>
            <Col span="1" offset="1">
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
              <div style="width: 7vw;height:3vh;margin-top: -10px;"
                   v-if="index > 0 && allSteps[index - 1].actionKey == 2"
                   :style="{paddingTop : allSteps[index - 1].toTop ? (item.toTop-1) * 3 + 'vh' : '0'}">
                <img style="width:50px;height:50px;" v-bind:src="status"/>
              </div>
              <Tooltip placement="bottom">
                <div
                  :style="{ marginTop : item.toTop  ? (allSteps[index - 1].actionKey == 2 ? (item.toTop > 1 ? '6vh': '2vh') : (item.toTop > 2 ? (item.toTop-1) * 4 + 3 + 'vh' : item.toTop * 4 + 'vh')) : '0'}"
                  class="border">
                  <Icon type="record" color=""></Icon>
                  {{ item.stepName }}
                </div>
                <div slot="content" class="target">
                  <p>{{ item.target }}</p>
                </div>
              </Tooltip>
              </Col>
              <Col span="1" v-if="index + 1 != allSteps.length && item.actionKey != 2">
              <div class="next"
                   :style="{ paddingTop : item.toTop ? (item.toTop > 2 ? (item.toTop-1) * 3 + 6 + 'vh' : (item.toTop-1) * 4 + 5 + 'vh') : '0'}">
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
        <hr class="hr3"/>
        <img style="width: 100%;height: 60vh" v-bind:src="showData"/>
      </TabPane>
      <TabPane label="执行记录" name="log">
        <Row class="conditions">
          <Col span="4">
          <span>开始时间:</span>
          <DatePicker v-model="queryPram.startTime" type="datetime" placeholder="请选择开始时间"
                      style="width: 60%"></DatePicker>
          </Col>
          <Col span="4">
          <span>结束时间</span>
          <DatePicker v-model="queryPram.endTime" type="datetime" placeholder="请选择结束时间"
                      style="width: 60%"></DatePicker>
          </Col>
          <Col span="4">
            <Button type="primary" icon="ios-search" size="small"  @click="queryTable">查询</Button>
          </Col>
        </Row>
        <Row>
          <Col span="24" style="margin-top: 10px;">
          <hr class="hr1"/>
          </Col>
        </Row>
        <Row>
          <Col span="24" style="margin-bottom: 30px;">
          <Table :height="tableHeight" stripe border :columns="tableColumns" :data="tableData" style="margin: 10px;"></Table>
          <Modal width="900px;"
                 title="详细信息:"
                 v-model="showTableDetial"
                 :mask-closable="false">
            <Table stripe border :columns="detialTableColumns" style="height: 540px;" :data="detialTableData"></Table>
          </Modal>
          </Col>
        </Row>
        <div style="position: absolute;bottom: 0px;width:100%;">
          <Page style="position: relative;float: right;right: 10px;" @on-page-size-change="handlePageSize"
                :total="pageTotal" :page-size="queryPram.pageSize"
                :current="queryPram.pageNum" show-total show-elevator show-sizer placement="top"
                @on-change="changePage">
          </Page>
        </div>
      </TabPane>
    </Tabs>
    <ShowStartPlan v-bind="showModal"></ShowStartPlan>
  </div>
</template>

<script>
  import start from '../../../../assets/UM/start.png'
  import status from '../../../../assets/UM/status.png'
  import toDown from "../../../../assets/UM/toDown.png"
  import next from '../../../../assets/UM/next.png'
  import toUp from "../../../../assets/UM/toUp.png"
  import showData from "../../../../assets/UM/demo.png"
  import ShowStartPlan from '../../../../components/Common/Modal/ShowStartPlan'
  import {PlanService} from '../../../../services/planService'

  export default {
    name: "detial-plan",
    data() {
      return {
        imgUrl: start,
        next: next,
        status: status,
        tableHeight:450,
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
          modalPrams: {state: false},
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
      // 设置表格高度
      this.tableHeight = window.innerHeight - 270;
      this.queryPram.processKey = this.$route.params.processKey;
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

      //手动开启预案
      startPlan() {
        this.showModal.modalPrams.state = !this.showModal.modalPrams.state;
      },
    },
    components: {ShowStartPlan},
    watch: {
      '$route': function () {
        // $route发生变化时再次赋值
        this.queryPram.processKey = this.$route.params.processKey;
        this.allSteps = []
      },
    },
  }
</script>

<style scoped>
  /* .top {
    margin-top: 4px;
    background-color: #fff;
    font-size: 16px;
    height: 50px;
    padding: 10px;
    margin-left: 10px;
    margin-right: 10px;
  } */

  .ivu-tabs-card >>>.ivu-tabs-bar {
    margin-bottom: 0px !important;
  }

  .ivu-tabs >>> .ivu-card-body {
    padding: 10px !important;
  }

  .ivu-tooltip >>> .ivu-tooltip-inner {
    background-color: rgba(110, 110, 110, 0.9);
    text-align: center;
    color: rgb(174, 205, 237);
    height: 100px;
    font-size: 14px;
    display: table-cell;
    vertical-align: middle;
    font-weight: bold;
  }

  .ivu-tooltip > > > .ivu-tooltip-arrow {
    border-bottom-color: rgb(112, 112, 112);
  }

  .border {
    box-shadow: 5px 5px 15px #cdf5ff inset;
    border: 1px solid #d9fff1;
    border-radius: 8px;
    color: #fff;
    width: 7vw;
    height: 6vh;
    background-color: #22a5f5;
    padding: 7px;
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

  .next {
    display: table-cell;
    text-align: center;
    width: 4vw;
    height: 6vh;
    vertical-align: middle;
  }

  .begin {
    display: table-cell;
    text-align: right;
    width: 7vw;
    height: 6vh;
    vertical-align: middle;
  }

  .target {
    width: 200px;
    letter-spacing: 2px;
  }
</style>
