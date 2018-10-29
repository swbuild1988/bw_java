<!--备品历史统计-->
<template>
  <div>
    <Row >
      <Col span="12">
      <div>
        <Row >
          <Col span="12" >
          <div class="datapanle">
            <span slot="title" >
              故障设备排行
            </span>
            <div style="position: relative;float: right;">
              <a href="#" slot="extra">
                <Icon type="ios-loop-strong"></Icon>
                刷新
              </a>
            </div>
            <hr>
            <div>
              <ul v-for="(item,index) in breakData" :key="index">
                <li style="margin-top: 3px;line-height: 24px;list-style-type:none;">
                  <Icon type="star" color="#ff3c1d"></Icon>
                  {{item.key}}<span style="position: relative;float: right;">{{item.value}}</span></li>
              </ul>
            </div>
          </div>
          </Col>
          <Col span="12" >
          <div class="datapanle">
            <span slot="title" >
              借用人排行
            </span>
            <div style="position: relative;float: right;">
              <a href="#" slot="extra">
                <Icon type="ios-loop-strong"></Icon>
                刷新
              </a>
            </div>
            <hr>
            <div>
              <ul v-for="(item,index) in lendData" :key="index">
                <li style="line-height: 24px;list-style-type:none;">
                  <Icon type="reply-all" size="20" color="#15ffe4"></Icon>
                  {{item.user}}
                  <span style="position: relative;float: right;">
                  {{item.count}}<a type="text">    详情 </a>
                </span></li>
              </ul>
            </div>
          </div>
          </Col>
        </Row>
      </div>
      </Col>
      <Col span="6" >
      <div style="width:21vw;height: 30vh;">
        <SimplePieChart v-bind="countLendData"></SimplePieChart>
      </div>
      </Col>
      <Col span="6">
      <div style="width:21vw;height: 30vh;">
        <SimplePieChart v-bind="countBreakData"></SimplePieChart>
      </div>
      </Col>
      <Col span="12" style="margin-top: 1.5vh">
      <div style="width:42vw;height: 52vh;">
        <SimpleBarChart v-bind="toolDetailData"></SimpleBarChart>
      </div>
      </Col>
      <Col span="12" style="margin-top: 1.5vh"  >
      <Row :gutter="16">
        <Col span="10" style="z-index: 1001;">
        <span class="planDec">工具类型：</span>
        <Select v-model="condition.type" @on-change='showTable()' style="width:60%">
          <Option value=null key="0">全部</Option>
          <Option v-for="item in equipmentTypes" :value="item.key" :key="item.key">{{ item.value }}</Option>
        </Select>
        </Col>
        <Col span="10" style="z-index: 1001;">
        <span class="planDec">借用人：</span>
        <AutoComplete
          v-model="condition.user"
          :data="userData"
          :filter-method="filterMethod"
          placeholder="输入用户名"
          style="width:200px">
        </AutoComplete>
        </Col>
        <Col span="1" offset="3" >
        <div style="position: relative;float: right;right: 0px;z-index: 101;">
          <Button type="primary"  icon="ios-search" @click="queryTable">查询</Button>
        </div>
        </Col>
        <Col span="24" style="height: 49vh;">
        <Table stripe border :columns="toolColumns"  :style="{height:'90%',zIndex:101,marginTop:'3px'}"  :data="toolData"></Table>
        </Col>
        <Col span="24">
        <div style="position: absolute;margin-bottom: 2px;width: 410px;float: right;right: 0px;height: 2vh;z-index: 103">
          <Page  :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show-sizer
                 placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
                 :style='pageStyle'></Page>
        </div>
        </Col>
      </Row>
      </Col>
    </Row>
  </div>
</template>

<script>
  import SimplePieChart from '../../../../components/Common/Chart/SimplePieChart.vue'
  import SimpleBarChart from '../../../../components/Common/Chart/SimpleBarChart.vue'
  import { EquipmentService } from '../../../../services/equipments'
  export default {
    name: "tool-history-count",
    data() {
      return {
        breakData: [{
          key: "甲烷探测器",
          value: 43,
        },
          {
            key: "氧气探测器",
            value: 40,
          },
          {
            key: "湿度探测器",
            value: 37,
          },
          {
            key: "温度探测器",
            value: 33,
          }],
        lendData: [
          {user: "张三", count: "70", id: 1},
          {user: "王建", count: "60", id: 2,},
          {user: "王城", count: "50", id: 3,},
          {user: "Jisin", count: "30", id: 4,}],
        countLendData: {
          id:"loanDetail",
          requestUrl:"getCountLendData",
          title:{text:"借出工具比例",x:'left'},
          intervalTime:5000,
        },
        countBreakData:{
          id:"BreakDetail",
          requestUrl:"getCountBreakData",
          title:{text:"故障工具比例",x:'left'},
          intervalTime:5000,
        },
        toolDetailData:{
          id:" toolDetail",
          requestUrl:"getToolDetail",
          title:"工具明细",
          color:"#1affc9",
          intervalTime:5000,
        },
        toolColumns: [
          {
            title: '设备名称',
            key: 'name'
          },
          {
            title: '工具类型',
            key: 'type'
          },
          {
            title: '借用时间',
            key: 'time'
          },
          {
            title: '借用人',
            key: 'user',
          },
          {
            title: '借用状态',
            key: 'status',
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
                    type: 'info',
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
        toolData: [{name: "风2机", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机3", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机1", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机4", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机5", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机6", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机9", type: "管廊设备", time: "2018-1-12", user: "张三"}],
        equipmentTypes: [{key:1,value:"电压表"},{key:2,value:"温度计"},
          {key:3,value:"湿度计"},{key:8,value:"应力计"},
          {key:4,value:"有害气体监测器"},{key:5,value:"万能表"},
          {key:6,value:"螺丝刀"},{key:7,value:"扳手"}],
        condition: {
          tunnel: {
            name: '',
          },
          type: null,
          status: null,
          user:'',
          startEquipmentTime: null,
          endEquipmentTime: null
        },
        userData:['Steve Jobs', 'Stephen Gary Wozniak', 'Jonathan Paul Ive','张三','李四','小雨','大哥','董很累'],
        page: {
          pageNum: 1,
          pageSize: 8,
          pageTotal: 0,
        },
        pageStyle: {
          position: 'absolute',
          bottom: '20px',
          right: '15px'
        },
      }
    },
    components: {
      SimplePieChart,
      SimpleBarChart
    },
    methods: {
      //用户AutoComplete
      filterMethod (value, option) {
        return option.toUpperCase().indexOf(value.toUpperCase()) !== -1;
      },
      //查询表格数据
      queryTable(){
        let prams={};
        prams.type=this.condition.type;
        prams.status=this.condition.status;
        this.axios.post("").then(result=>{
          let {code, data} = response.data;
          if(code==200){
            this.breakData=data;
          }
        })
      },

      showTable() {
        let _this = this
        EquipmentService.equipmentDatagird(this.params).then(
          (result)=>{
            for (let index in result.list) {
              result.list[index].crtTime = new Date(result.list[index].crtTime).format("yyyy-MM-dd");
              if (result.list[index].imgUrl != null) {
                 result.list[index].imgUrl = "http://192.168.3.12:8080/MaxTunnel-Web/" + result.list[index].imgUrl.replace(/\\/g, "/")
              }
            }
            _this.equipments = result.list;
            _this.page.pageTotal = result.total;
          },
          (error)=>{
            _this.Log.info(error)
          })
        // this.axios.post("/equipments/datagrid", (this.params)).then(result => {
        //   let {code, data} = result.data;
        //   if (code == 200) {
        //     for (let index in data.list) {
        //       data.list[index].crtTime = new Date(data.list[index].crtTime).format("yyyy-MM-dd");
        //       if (data.list[index].imgUrl != null) {
        //         //   console.log(data.list[index].imgUrl)
        //         data.list[index].imgUrl = "http://192.168.3.6:8080/MaxTunnel-Web/" + data.list[index].imgUrl.replace(/\\/g, "/")
        //         // data.list[index].imgUrl = "http://192.168.0.41:8080/MaxTunnel-Web/" + data.list[index].imgUrl.replace(/\\/g, "/")
        //       }
        //     }
        //     this.equipments = data.list;
        //     this.page.pageTotal = data.total;
        //   }
        // });
      },

      handlePage(value) {
        this.page.pageNum = value
        this.showTable()
      },
      handlePageSize(value) {
        this.page.pageSize = value;
        this.showTable()
      },
    }
  }
</script>

<style scoped>
  .datapanle{
    box-shadow: 0 1px  6px  rgba(0,0,0,.2);
    border-color: #eee;
    width:98%;height: 30vh;
    margin-right: 10px;
    padding: 18px;
  }

</style>

