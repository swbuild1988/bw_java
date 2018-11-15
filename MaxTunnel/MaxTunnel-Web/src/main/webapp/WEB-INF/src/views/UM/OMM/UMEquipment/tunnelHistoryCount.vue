<!--管廊设备历史统计-->
<template>
  <div class="queryEquipment">
      <div style="height:42vh;position: relative;float: left;width: 42vw;">
        <ComparisonBarChart v-bind="chartData"></ComparisonBarChart>
      </div>
      <div style="height: 40vh;position: relative;float: right;width: 41.5vw">
        <Row :gutter="16">
          <Col span="10" style="z-index: 1001;">
          <span class="planDec">设备类型：</span>
          <Select v-model="condition.type" @on-change='showTable()' style="width:60%">
            <Option value=null key="0">所有</Option>
            <Option v-for="item in equipmentTypes" :value="item.key" :key="item.key">{{ item.value }}</Option>
          </Select>
          </Col>
          <Col span="10" style="z-index: 1001;">
          <span class="planDec">设备状态：</span>
          <Select v-model="condition.status" @on-change='showTable()' style="width:60%;">
            <Option value=null key="0">所有</Option>
            <Option v-for="item in equipmentStatus" :value="item.key" :key="item.key">{{ item.value }}</Option>
          </Select>
          </Col>
          <Col span="1" offset="3" >
          <div style="position: relative;float: right;right: 0px;z-index: 101;">
            <Button type="primary"  icon="ios-search" @click="queryTable">查询</Button>
          </div>
          </Col>
          <Col span="24" style="height: 38vh;">
          <Table stripe border :columns="breakColumns"  :style="{height:'90%',zIndex:101,marginTop:'3px'}"  :data="breakData"></Table>
          </Col>
          <Col span="24">
          <div style="position: absolute;margin-bottom: 4px;width: 480px;float: right;right: 0;height: 2vh;z-index: 103">
            <Page  :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show-sizer
                   placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
                   :style='pageStyle'></Page>
          </div>
          </Col>
        </Row>
      </div>
    <div style="height: 40vh;width: 86vw;position: relative">
      <ComplexBarChart v-bind="ComplexBar"></ComplexBarChart>
    </div>

  </div>
</template>

<script>
  import ComparisonBarChart from '../../../../components/Common/Chart/ComparisonBarChart'
  import ComplexBarChart from '../../../../components/Common/Chart/ComplexBarChart.vue'
  import types from '../../../../../static/Enum.json'
  import { TunnelService } from '../../../../services/tunnelService'
  import { EquipmentService } from '../../../../services/equipmentService'

  export default {
    name: "tunnel-history-count",
    data() {
      return {
        showOn: true,
        equipments: [],
        breakColumns: [
          {
            title: '设备名称',
            key: 'name'
          },
          {
            title: '设备类型',
            key: 'type'
          },
          {
            title: '入廊时间',
            key: 'time'
          },
          {
            title: '录入人',
            key: 'user',
          },
          {
            title: '设备状态',
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
        equipmentTypes: [],
        condition: {
          tunnel: {
            name: '',
          },
          type: null,
          status: null,
          fuzzy: null,
          startEquipmentTime: null,
          endEquipmentTime: null
        },
        equipmentStatus: types.equipmentStatus,
        breakData: [{name: "风2机", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机3", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机1", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机4", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机5", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机6", type: "管廊设备", time: "2018-1-12", user: "张三"},
          {name: "风机9", type: "管廊设备", time: "2018-1-12", user: "张三"}],
        ComplexBar: {
          id: "ComplexBarChart",
          yAxisName: "单位：个",
          requestUrl: "equipments/tunnels/count",
          title: "管廊设备明细",
          color: "#21d6ff",
        },
        chartData: {
          id: "comparisonBarChart",
           yAxisName: "单位：个",
          requestTotalUrl: "tunnels/equipments/count",
          requestPartUrl: "tunnels/equipments/broken/count",
          title: "管廊设备故障统计",
          color: "#46dbff",
        },
        tunnels: [],
        tunnelId: 1,
        equipmentTypes: [],
        deleteEquipmentInfo: [],   //要删除的设备
        condition: {
          tunnel: {
            name: "",
          },
          type: null,
          status: null,
          fuzzy: null,
          startEquipmentTime: null,
          endEquipmentTime: null
        },
        equipmentStatus: types.equipmentStatus,
        pagingList: [],
        page: {
          pageNum: 1,
          pageSize: 8,
          pageTotal: 0,
        },
        pageStyle: {
          position: 'absolute',
          bottom: '20px',
        },
      }
    },
    watch: {
      '$route': function () {
        //2. $route发生变化时再次赋值planId
        this.tunnelId = this.$route.params.id;
        this.tunnels.forEach(a => {
          if (a.id == this.tunnelId) {
            this.showTable();
          }
        });
      }
    },
    computed: {
      params() {
        let param = {
          pageNum: this.page.pageNum,
          pageSize: this.page.pageSize,
          tunnelId: this.tunnelId,
          type: this.condition.type,
          status: this.condition.status,
          startEquipmentTime: new Date(this.condition.startEquipmentTime).getTime(),
          endEquipmentTime: new Date(this.condition.endEquipmentTime).getTime()
        }
        return Object.assign({}, param);
      },
    },
    mounted() {
      //从数据库读取select的option选项
      let _this = this
      TunnelService.getTunnels().then(
          (result)=>{
              _this.tunnels = result;
              _this.selectedTunnel = _this.tunnels.length > 0 ? _this.tunnels[0] : {};
              _this.selectedTunnelName = _this.selectedTunnel.name;
          },
          (error)=>{
              _this.Log.info(error)
      })
      
      this.equipmentTypes = types.equipmentType;
      this.showTable()
    },
    methods: {
      // type 1:查看， 2：编辑
      goToMoudle: function (index, type) {
        this.$router.push({
          name: 'UMDetailEquipment',
          params: {
            id: this.equipments[index].id,
            type: type,
          }
        });
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
      },
      handlePage(value) {
        this.page.pageNum = value
        this.showTable()
      },
      handlePageSize(value) {
        this.page.pageSize = value;
        this.showTable()
      },
      show(index) {
        console.log(index)
        this.goToMoudle(index, types.pageType.Read);
      },
      edit(index) {
        this.goToMoudle(index, types.pageType.Edit);
      },
      del(index) {
        let _this = this
        EquipmentService.equipmentDatagird(this.params).then(
          (result)=>{
            _this.deleteEquipmentInfo = result.list[index];
          },
          (error)=>{
            _this.Log.info(error)
          })
        
        this.$Modal.confirm({
          title: '删除',
          content: '<p>确认删除吗?</p>',
          onOk: () => {
            let _this = this
            EquipmentService.deleteEquipment(this.deleteEquipmentInfo.id).then(
              (result)=>{
                _this.showTable();
              },
              (error)=>{
                _this.Log.info(error)
              })
            // this.axios.delete('/equipments/' + this.deleteEquipmentInfo.id).then(result => {
            //   this.showTable();
            // });
          }
        });
      },
      add(path) {
        this.$router.push(path);
      },
      pickUp() {
        this.showOn = !this.showOn;
      },
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
    },
    components: {ComparisonBarChart, ComplexBarChart},
  }
</script>
<style scoped>
  .queryEquipment {
    position: relative;
    min-height: 100%;
    padding-bottom: 60px;
  }

  .top {
    height: 60px;
    line-height: 60px;
    background: #fff;
    padding-left: 10px;
  }

  .bottom {
    background: #fff;
    margin-top: 10px;
    padding: 10px;
  }

  .planDec {
    margin-right: 5px;
  }

  .equipmentList {
    font-size: 17px;
    font-weight: 700;
  }

  .addList {
    border: 1px dashed #dfdfdf;
    text-align: center;
    line-height: 40px;
    color: #8b8b8b;
    margin-top: 20px;
    border-radius: 10px;
    background: #fff;
    z-index: 10001;
  }

  /*new*/
  .equipentTitle {
    color: #fff;
    font-size: 20px;
    text-align: center;
    line-height: 50px;
  }

  .imgBox {
    text-align: center;
  }

  .table li {
    list-style: none;
    width: 80%;
    margin: 10px auto;
  }

  .backGoundBox {
    position: relative;
    height: 70px;
    width: 100%;
  }

  .topBox {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(255, 255, 255, 0.3);
  }

  .imgBox img {
    height: 40px;
    width: 40px;
    border-radius: 40px;
  }

  .detailsBox {
    border: 1px solid #ebe8e8;
    PADDING-TOP: 20px;
    background: #fff;
  }

  .detailsBox p {
    line-height: 35px;
    padding-left: 20px;
  }

  .operationSee, .operationEdit, .operationDel {
    text-align: center;
    background: #fff;
    padding: 5px 0;
    border-bottom: 1px solid #ebe8e8;
  }

  .operationDel {
    border-right: 1px solid #ebe8e8;
  }

  .operationSee {
    border-left: 1px solid #ebe8e8;
  }

  .operationSee p, .operationEdit p, .operationDel p {
    cursor: pointer;
  }
</style>


