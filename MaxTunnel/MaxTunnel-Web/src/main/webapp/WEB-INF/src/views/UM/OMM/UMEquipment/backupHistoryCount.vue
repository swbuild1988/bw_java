<!--备品历史统计-->
<template>
    <div style="padding: 5px;">
        <Row >
        <Col span="12">
        <div>
            <Row >
            <Col span="12" >
            <div class="datapanle">
                <span slot="title" >
                取用备品设备排行
                </span>
                <div style="position: relative;float: right;">
                <a href="#" slot="extra">
                    <Icon type="ios-loop-strong"></Icon>
                    刷新
                </a>
                </div>
                <hr>
                <div>
                <ul v-for="(item,index) in backupTakeData" :key="index">
                    <li style="margin-top: 3px;line-height: 24px;list-style-type:none;">
                        <Icon type="star" color="#ff3c1d"></Icon>
                        {{item.key}}
                        <span style="position: relative;float: right;">
                            {{item.value}}
                            <span class="orderDetails" @click="showTakingDetails(item.id)">&nbsp;&nbsp;详情</span>
                        </span>
                    </li>
                </ul>
                </div>
            </div>
            </Col>
            <Col span="12" >
            <div class="datapanle">
                <span slot="title" >
                取用设备人排行
                </span>
                <div style="position: relative;float: right;">
                    <a href="#" slot="extra">
                        <Icon type="ios-loop-strong"></Icon>
                        刷新
                    </a>
                </div>
                <hr>
                <div>
                    <ul v-for="(item,index) in takeUserData" :key="index">
                        <li style="line-height: 24px;list-style-type:none;">
                            <Icon type="reply-all" size="20" color="#15ffe4"></Icon>
                            {{item.user}}
                            <span style="position: relative;float: right;">
                                {{item.count}}
                                <span class="orderDetails" @click="showTakingPersonDetails(item.id)">&nbsp;&nbsp;详情</span>
                            </span>
                        </li>
                    </ul>
                </div>
            </div>
            </Col>
            </Row>
        </div>
        </Col>
        <Col span="6" >
        <div style="width:21vw;height: 30vh;">
            <SimplePieChart v-bind="countTakeData"></SimplePieChart>
        </div>
        </Col>
        <Col span="6">
        <div style="width:21vw;height: 30vh;">
            <SimplePieChart v-bind="countStoreBackupData"></SimplePieChart>
        </div>
        </Col>
        <Col span="12" style="margin-top: 1.5vh">
            <div style="width:42vw;height: 52vh;">
                <SimpleBarChart v-bind="backupDetailData"></SimpleBarChart>
            </div>
        </Col>
        <Col span="12" style="margin-top: 1.5vh"  >
            <Row :gutter="16">
                <Col span="10">
                    <span class="planDec">工具类型：</span>
                    <Select v-model="condition.type" @on-change='showTable()' style="width:60%">
                        <Option value=null key="0">全部</Option>
                        <Option v-for="item in equipmentTypes" :value="item.key" :key="item.key">{{ item.value }}</Option>
                    </Select>
                </Col>
                <Col span="10">
                    <span class="planDec">取用人：</span>
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
    <Modal
        v-model="isTaking"
        title="取用备品详情"
        width="740"
    >
        <Row style="margin-bottom: 10px;">
            <Col span="14">
                <span>出库时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 153px"></DatePicker> -
                <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 153px"></DatePicker>
            </Col>
            <Col span="7">
                <span>取用人：</span>
                <Input type="text" style="width: 130px"></Input>
            </Col>
            <Col span="3" style="text-align: center">
                <Button type="primary" size="small">查询</Button>
            </Col>
        </Row>
        <Table stripe border :columns="takingColums"  :data="takingDate"></Table>
        <div class="pageContainer">
            <Page :total="takingPage.pageTotal" :current="takingPage.pageNum" :page-size="takingPage.pageSize" 
            show-elevator @on-change="handlePage" @on-page-size-change='handlePageSize'></Page>
        </div>
    </Modal>
    <Modal
        v-model="isTakingPerson"
        title="取用备品人详情"
        width="740"
    >
        <Row style="margin-bottom: 10px">
            <Col span="14">
                <span>出库时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 153px"></DatePicker> -
                <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 153px"></DatePicker>
            </Col>
            <Col span="7">
                <span>备品名称：</span>
                <Input type="text" style="width: 130px"></Input>
            </Col>
            <Col span="3" style="text-align: center">
                <Button type="primary" size="small">查询</Button>
            </Col>
        </Row>
        <Table stripe border :columns="takingPersonColums"  :data="takingPersonDate"></Table>
        <div class="pageContainer">
            <Page :total="takingPersonPage.pageTotal" :current="takingPersonPage.pageNum" :page-size="takingPersonPage.pageSize" 
            show-elevator @on-change="handlePage" @on-page-size-change='handlePageSize'></Page>
        </div>
    </Modal>
  </div>
</template>

<script>
import SimplePieChart from "../../../../components/Common/Chart/SimplePieChart.vue";
import SimpleBarChart from "../../../../components/Common/Chart/SimpleBarChart.vue";
export default {
  name: "backup-history-count",
  data() {
    return {
        backupTakeData: [
            {
            key: "甲烷探测器",
            value: 43,
            id: 1,
            },
            {
            key: "氧气探测器",
            value: 40,
            id: 2
            },
            {
            key: "湿度计",
            value: 37,
            id: 3
            },
            {
            key: "温度计",
            value: 33, 
            id: 4
            }
        ],
        isTaking: false,
        takingColums:[
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '备品名称',
                    key: 'backUpName',
                    align: 'center'
                },
                {
                    title: '出库时间',
                    key: 'takingTime',
                    align: 'center'
                },
                {
                    title: '取用人',
                    key: 'takingPersonName',
                    align: 'center'
                },
                {
                    title: '操作员',
                    key: 'operation',
                    align: 'center'
                }
        ],
        takingDate:[
            { id: 1, backUpName: '温度计', takingTime: '', takingPersonName: '张三', operation: '管理员' },
            { id: 2, backUpName: '温度计', takingTime: '', takingPersonName: '张三', operation: '管理员' },
            { id: 3, backUpName: '温度计', takingTime: '', takingPersonName: '张三', operation: '管理员' }
        ],
        takingPage:{
            pageSize: 3,
            pageTotal: 100,
            pageNum: 1
        },
        isTakingPerson: false,
        takingPersonColums:[
            {
                type: 'index',
                width: 60,
                align: 'center'
            },
            {
                title: '取用人',
                key: 'takingPerson',
                align: 'center'
            },
            {
                title: '备品名称',
                key: 'backUpName',
                align: 'center'
            },
            {
                title: '出库时间',
                key: 'takingTime',
                align: 'center'
            },
            {
                title: '取用数量',
                key: 'takingNum',
                align: 'center'
            },
            {
                title: '操作员',
                key: 'operation',
                align: 'center'
            }
        ],
        takingPersonDate:[
            { id: 1, takingPerson: '张三', backUpName: '门禁卡', takingTime: '', takingNum: '2', operation: 'admin' },
            { id: 2, takingPerson: '张三', backUpName: '门禁卡', takingTime: '', takingNum: '2', operation: 'admin' },
            { id: 3, takingPerson: '张三', backUpName: '门禁卡', takingTime: '', takingNum: '2', operation: 'admin' },
        ],
        takingPersonPage:{
            pageSize: 3,
            pageTotal: 100,
            pageNum: 1
        },
        takeUserData: [
            { user: "张三", count: "70", id: 1 },
            { user: "王建", count: "60", id: 2 },
            { user: "王城", count: "50", id: 3 },
            { user: "Jisin", count: "30", id: 4 }
        ],
        countTakeData: {
            id: "loanDetail",
            requestUrl: "getCountLendData",
            // title: { text: "取用备品设备比例", x: "left" },
            // intervalTime: 5000
            parameters: {
            option: {
                title: {
                text: "取用备品设备比例"
                }
            },
            timer: {
                interval: 5000
            }
            }
        },
        countStoreBackupData: {
            id: "BreakDetail",
            requestUrl: "getCountBreakData",
            // title: { text: "库存备品比例", x: "left" },
            // intervalTime: 5000
            parameters: {
            option: {
                title: {
                text: "库存备品比例"
                }
            },
            timer: {
                interval: 5000
            }
            }
        },
        backupDetailData: {
            id: " toolDetail",
            requestUrl: "getToolDetail",
            parameters: {
                option: {
                    title: {
                        text: "备品备件明细",
                        textStyle: {
                            color: "#1affc9"
                        }
                    }
                },
                // timer: {
                //     interval: 5000
                // }
            }
        },
        toolColumns: [
            {
            title: "备品名称",
            key: "name"
            },
            {
            title: "工具类型",
            key: "type"
            },
            {
            title: "取用时间",
            key: "time"
            },
            {
            title: "取用人",
            key: "user"
            },
            {
            title: "备品状态",
            key: "status"
            },
            {
            title: "操作",
            key: "action",
            width: 150,
            align: "center",
            render: (h, params) => {
                return h("div", [
                h(
                    "Button",
                    {
                    props: {
                        type: "info",
                        size: "default"
                    },
                    style: {
                        marginRight: "5px"
                    },
                    on: {
                        click: () => {
                        this.show(params.index);
                        }
                    }
                    },
                    "查看详情"
                )
                ]);
            }
            }
        ],
        toolData: [
            {
            name: "风2机",
            type: "管廊设备",
            time: "2018-1-12",
            user: "张三"
            },
            {
            name: "风机3",
            type: "管廊设备",
            time: "2018-1-12",
            user: "张三"
            },
            {
            name: "风机1",
            type: "管廊设备",
            time: "2018-1-12",
            user: "张三"
            },
            {
            name: "风机4",
            type: "管廊设备",
            time: "2018-1-12",
            user: "张三"
            },
            {
            name: "风机5",
            type: "管廊设备",
            time: "2018-1-12",
            user: "张三"
            },
            {
            name: "风机6",
            type: "管廊设备",
            time: "2018-1-12",
            user: "张三"
            },
            {
            name: "风机9",
            type: "管廊设备",
            time: "2018-1-12",
            user: "张三"
            }
        ],
        equipmentTypes: [
            { key: 1, value: "电压表" },
            { key: 2, value: "温度计" },
            { key: 3, value: "湿度计" },
            { key: 8, value: "应力计" },
            { key: 4, value: "有害气体监测器" },
            { key: 5, value: "万能表" },
            { key: 6, value: "螺丝刀" },
            { key: 7, value: "扳手" }
        ],
        condition: {
            tunnel: {
            name: ""
            },
            type: null,
            status: null,
            user: "",
            startEquipmentTime: null,
            endEquipmentTime: null
        },
        userData: [
            "Steve Jobs",
            "Stephen Gary Wozniak",
            "Jonathan Paul Ive",
            "张三",
            "李四",
            "小雨",
            "大哥",
            "董很累"
        ],
        page: {
            pageNum: 1,
            pageSize: 8,
            pageTotal: 0
        },
        pageStyle: {
            position: "absolute",
            bottom: "20px",
            right: "15px"
        }
    };
  },
  components: {
    SimplePieChart,
    SimpleBarChart
  },
  methods: {
    //用户AutoComplete
    filterMethod(value, option) {
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1;
    },
    //查询表格数据
    queryTable() {
      let prams = {};
      prams.type = this.condition.type;
      prams.status = this.condition.status;
      this.axios.post("").then(result => {
        let { code, data } = response.data;
        if (code == 200) {
          this.backupTakeData = data;
        }
      });
    },

    showTable() {
      let _this = this;
      EquipmentService.equipmentDatagird(this.params).then(
        result => {
          for (let index in result.list) {
            result.list[index].crtTime = new Date(
              result.list[index].crtTime
            ).format("yyyy-MM-dd");
            if (result.list[index].imgUrl != null) {
              result.list[index].imgUrl =
                "http://192.168.3.6:8080/MaxTunnel-Web/" +
                result.list[index].imgUrl.replace(/\\/g, "/");
            }
          }
          _this.equipments = result.list;
          _this.page.pageTotal = result.total;
        },
        error => {
          _this.Log.info(error);
        }
      );
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
      this.page.pageNum = value;
      this.showTable();
    },
    handlePageSize(value) {
      this.page.pageSize = value;
      this.showTable();
    },
    //取出备品排行详情
    showTakingDetails(id){
        this.isTaking = true
    },
    //取用备品人排行详情
    showTakingPersonDetails(id){
        this.isTakingPerson = true
    }
  }
};
</script>

<style scoped>
.datapanle {
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.2);
  border-color: #eee;
  width: 98%;
  height: 30vh;
  margin-right: 10px;
  padding: 18px;
  background: #fff;
}
.orderDetails{
    color: #2d8cf0;
    cursor: pointer;
}
.pageContainer{
    margin-top: 10px;
    text-align: right;
}
</style>


