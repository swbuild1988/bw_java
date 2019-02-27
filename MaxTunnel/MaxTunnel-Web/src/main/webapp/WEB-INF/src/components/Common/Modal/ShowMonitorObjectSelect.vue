<!--选择监测对象弹框-->
<template>
  <Modal v-model="show.state" width="40vw"  title="选择监测对象"  @on-ok="confirmData">
      <Form  :label-width="labelWidth"  style="line-height:4vmin;font-size: 1.66vmin;">
        <FormItem label="对象名称" >
          <Input v-model="queryPrams.name" style="width: 15vw;float: left;" placeholder="输入对象名称">
          </Input>
        </FormItem>
        <FormItem label="对象类型" >
          <Select v-model="queryPrams.objType" style="width:15vw;float: left">
            <OptionGroup v-for="(group, index) in objectList" :label="group.key" :key="index" style="font-size: 1.66vmin;">
              <Option v-for="item in group.objectTypeList" :value="item.val" :key="item.val">{{ item.key }}</Option>
            </OptionGroup>
          </Select>
        </FormItem>
        <FormItem label="所属管廊" >
          <Select v-model="queryPrams.tunnelId" style="width:15vw;float: left;">
            <Option v-for="item in tunnelList" :value="item.id" :key="item.id">{{ item.name }}</Option>
          </Select>
          <Button style="float: right;margin-right: 2vw;font-size: 1.66vmin" type="primary" shape="circle" icon="ios-search" @click="queryTable" title="查询" >查询</Button>
        </FormItem>
      </Form>
      <Row>
        <Col span="24" style="margin-top: 10px;">
        <Table border ref="selection" :columns="columns" :data="tableData" @on-selection-change="setData"
               :height="tableHeight"></Table>
        </Col>
      </Row>
    <Page style="text-align:center;font-size: 16px;margin-top: 10px; " @on-change="changePage"
          @on-page-size-change="handlePageSize"
          :total="queryPrams.total"
          show-total show-elevator
          :current="queryPrams.pageNum"
          show-total show-elevator show-sizer placement="top"
          :page-size="queryPrams.pageSize"></Page>
  </Modal>
</template>

<script>
  import {EnumsService} from '../../../services/enumsService.js'
  import {MeasObjServer} from '../../../services/MeasObjectSerivers.js'
  import {TunnelService} from '../../../services/tunnelService.js'

  export default {
    name: "show-monitor-object-select",
    data() {
      return {
        headTip: "选择监测对象",
        tableHeight: 400,
        labelWidth:120,
        queryPrams: {
          name: "",
          objType: "",
          tunnelId: -1,
          total: 0,
          pageNum: 1,
          pageSize: 12,
        },
        objectList: [],
        tunnelList: [],
        columns: [
          {
            type: 'selection',
            width: 60,
            align: 'center'
          },
          {
            title: 'Id',
            key: 'id'
          },
          {
            title: '对象名称',
            key: 'name'
          },
          {
            title: '对象类型',
            key: 'objtypeName'
          },
          {
            title: '所属管廊',
            key: 'tunnelName'
          },
        ],
        tableData: [],
        curSelectData: [],
      }
    },
    props: {
      show: {
        type: Object,
      },
      selectObjects: {
        type: Object,
        default: []
      },
      selectData: {
        type: Object,
        default: {idList: ""}
      },
    },
    methods: {
      initData() {
        var _this = this;
        _this.objectList = [];
        //对象类型
        EnumsService.getMonitorType().then((result) => {
          _this.objectList = result;
        });
        //管廊列表
        _this.tunnelList = [];
        TunnelService.getTunnels().then((result) => {
          _this.tunnelList = [{id: -1, name: "全部"}];
          result.reduce((a, b) => {
            _this.tunnelList.push(b);
          }, _this.tunnelList)
        });

      },

      queryTable() {
        let _this = this;
        let prams = {};
        prams.name = _this.queryPrams.name;
        prams.objtypeIds = _this.queryPrams.objType != "" ? [_this.queryPrams.objType] : null;
        prams.tunnelId = _this.queryPrams.tunnelId >= 0 ? _this.queryPrams.tunnelId : "";
        prams.total = _this.queryPrams.total
        prams.pageNum = _this.queryPrams.pageNum
        prams.pageSize = _this.queryPrams.pageSize
        MeasObjServer.measObjDataGrid(prams).then((result) => {
          if (result) {
            _this.tableData=[];
            this.queryPrams.total = result.total;
            result.list.forEach(b=>{
                let temp = {};
                temp.id = b.id
                temp.name = b.name
                temp.objtypeName = b.objtypeName
                temp.tunnelName=b.section!=null?b.section.store.tunnel.name:"无";
                _this.tableData.push(temp);
            })
          }
        })
      },

      setData(arr) {
        if (arr && arr.length > 0) {
          this.curSelectData = "";
          arr.forEach(a => {
            this.curSelectData += a.id + ",";
          })
        }
      },

      clearSelect() {
        this.$refs.selection.selectAll(false);
      },

      confirmData() {
        this.selectData.idList = this.curSelectData;
      },
      //切换页面
      changePage(index) {
        let _this = this;
        _this.queryPrams.pageNum = index;
        _this.queryTable();
      },

      //切换页码数
      handlePageSize(value) {
        this.queryPrams.pageSize = value;
        this.queryTable();
      },
    },
    mounted() {
      this.tableHeight = window.innerHeight * 0.4;
      this.labelWidth=window.innerWidth*0.08
      this.initData();
    },
    watch: {
      'show.state': function () {
        this.clearSelect();
      },
    },
    components: {},
  }
</script>

<style scoped>
  .ivu-radio-large > > > .ivu-radio-wrapper {
    font-size: 1.22vmin;
    line-height: 4vmin;
  }
  .ivu-input-type >>> .ivu-input {
    font-size: 1.22vmin;
    height: 4vmin;
  }
</style>
