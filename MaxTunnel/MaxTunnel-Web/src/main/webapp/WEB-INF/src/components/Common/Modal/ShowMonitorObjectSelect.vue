<!--选择监测对象弹框-->
<template>
  <Modal v-model="show.state" width="600px" @on-ok="confirmData">
    <p slot="header" style="color:#f60;font-size: 18px;">
      选择监测对象
    </p>
    <div style="text-align:center;font-size: 16px;">
      <Row>
        <Col span="20">
        <span style="float: left;margin-right: 8px;margin-top: 8px;">监测对象ID:</span>
        <Input v-model="queryPrams.objectId" style="width: 320px;float: left;">
        <Button slot="append" icon="ios-search" @click="queryMesObjByIds" style="height: 35px;"></Button>
        </Input>
        </Col>
        <Col span="20" style="margin-top: 10px; ">
        <span style="float: left;margin-right: 8px;margin-top: 2px;">查询类型:</span>
        <RadioGroup :value="queryPrams.selectType" v-model="queryPrams.selectType"
                    style="margin-left: 18px;float: left;margin-top: 3px;" size="large" @on-change="changeQueryType">
          <Radio label=1>对象类型</Radio>
          <Radio label=2>监测区域</Radio>
        </RadioGroup>
        </Col>
        <Col span="20" style="margin-top: 10px; ">
        <span style="float: left;margin-right: 8px;margin-top: 2px;">{{curQueryTitle}}:</span>
        <div v-if="queryPrams.selectType=='1'">
          <Select v-model="queryPrams.selectItem" style="width:320px;float: left;margin-left: 18px;"
                  @on-change="queryTable">
            <OptionGroup v-for="(group, index) in objectList" :label="group.key" :key="index" style="font-size: 18px;">
              <Option v-for="item in group.objectTypeList" :value="item.val" :key="item.val">{{ item.key }}</Option>
            </OptionGroup>
          </Select>
        </div>
        <div v-else>
          <treeselect :options="objectList" placeholder="请选择" v-model="queryPrams.selectItem"
                      style="width: 60%;float: left;left: 8px"/>
        </div>
        </Col>
        <Col span="24" style="margin-top: 10px;">
        <Table border ref="selection" :columns="columns" :data="tableData" @on-selection-change="setData"
               height="400"></Table>
        </Col>
      </Row>
    </div>
  </Modal>
</template>

<script>

  import {EnumsService} from '../../../services/enumsService.js'
  import {MeasObjServer} from '../../../services/MeasObjectSerivers.js'
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'

  export default {
    name: "show-monitor-object-select",
    data() {
      return {
        headTip: "选择监测对象",
        queryPrams: {
          selectType: "1",
          objectId: "",
          selectItem: null,
        },
        curQueryTitle: "对象类型",
        objectList: [],
        columns: [
          {
            type: 'selection',
            width: 60,
            align: 'center'
          },
          {
            title: '编号',
            key: 'id'
          },
          {
            title: '类型',
            key: 'datatype'
          },
          {
            title: '监测区域',
            key: 'Zone'
          },
          {
            title: '名称',
            key: 'name'
          }
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
        if (_this.queryPrams.selectType == "1") {
          EnumsService.getMonitorType().then((result) => {
            _this.objectList = result;
          });
        }
        else {
          _this.objectList = [];
          EnumsService.getMonitorZone().then((result) => {
            if (result) {
              result.forEach(a => {
                var temp = {};
                temp.id = a.id;
                temp.label = a.name;
                temp.children = [];
                _this.objectList.push(temp);
                if (a.list.length > 0) {
                  a.list.forEach(b => {
                    var child = {};
                    child.id = a.id + "_" + b.id;
                    child.label = b.name;
                    child.children = [];
                    temp.children.push(child);
                    if (b.list.length > 0) {
                      b.list.forEach(c => {
                        var child2 = {};
                        child2.id = a.id + "_" + b.id + "_" + c.id;
                        child2.label = c.name;
                        child.children.push(child2);
                      })
                    }
                  })
                }
              })
            }
          })
        }
      },

      changeQueryType(index) {
        if (index == "1") {
          this.curQueryTitle = "对象类型";
        }
        else {
          this.curQueryTitle = "监测区域";
        }
        this.initData();
      },

      queryTable() {
      },
      setData(arr) {
        if (arr && arr.length > 0) {
          this.curSelectData = "";
          arr.forEach(a => {
            this.curSelectData += a.No + ",";
          })
        }
      },

      clearSelect() {
        this.$refs.selection.selectAll(false);
      },

      confirmData() {
        this.selectData.idList = this.curSelectData;
      },

      queryMesObjByIds() {
        var _this = this;
        if (_this.queryPrams.objectId) {
          var prams={ids:_this.queryPrams.objectId};
          MeasObjServer.getObjByIds(prams).then((result) => {
            if(result&&tableData.length>0){
              _this.tableData  = [];
              result.forEach(a=>{
                var temp={};
                temp.id=a.id;
                temp.name=a.name;
                temp.datatype=a.datatypeId;
                _this.tableData.push(temp);
              })
            }

          })
        }
      },
    },
    mounted() {
      this.initData();
    },
    watch: {
      'show.state': function () {
        this.clearSelect();
      }
    },
    components: {Treeselect},
  }
</script>

<style scoped>
  .ivu-radio-large > > > .ivu-radio-wrapper {
    font-size: 16px;
  }
</style>
