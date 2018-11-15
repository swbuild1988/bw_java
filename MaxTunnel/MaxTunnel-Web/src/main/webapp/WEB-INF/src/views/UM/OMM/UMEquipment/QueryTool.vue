<template>
  <div class="queryEquipment">
    <Row class="top">
      <Col span="6">
      <span class="planDec">设备类型：</span>
      <Select v-model="condition.type" @on-change='showTable()' style="width:60%">
        <Option value=null key="0">所有</Option>
        <Option v-for="item in equipmentTypes" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">设备状态：</span>
      <Select v-model="condition.status" @on-change='showTable()' style="width:60%">
        <Option value=null key="0">所有</Option>
        <Option v-for="item in equipmentStatus" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">入库时间：</span>
      <DatePicker type="date" placeholder="选择时间" style="width: 220px" v-model="condition.startEquipmentTime"
                  @on-change='showTable()'></DatePicker>
      </Col>
      <Col span="1" offset="5" >
      <div style="position: relative;float: right;right: 8px;">
        <Button type="primary"  icon="ios-search">查询</Button>
      </div>
      </Col>
    </Row>
    <div class="bottom">
      <Row>
        <Col span="5" class="equipmentList">
        备品列表</Col>
        <Col span="9" offset="10">
        <div style="position: relative;float: right">
          <Button type="success" @click="add({path: '/UM/equipment/add'})"  icon="ios-plus-outline">增加入库</Button>
          <Button type="success" @click="batchLoanTools()"  icon="reply">批量借出</Button>
          <Button type="success" @click="batchReturnTools()"  icon="forward">批量归还</Button>
        </div>
        </Col>
        <Col span="24">
        <Row :gutter="8">
          <Col span="6" v-for="(item,index) in equipments" :key="index"style="margin-top: 6px;">
          <div :style="{backgroundImage:'url(' + item.imgUrl + ')'}" class="backGoundBox">
            <div class="topBox">
              <a class="ivu-modal-close" style="right: 8px;top:0px;"><i class="ivu-icon ivu-icon-ios-close-empty"></i></a>
              <p class="equipentTitle">{{ item.name }}</p>
              <div class="imgBox"><img :src="item.imgUrl"></div>
            </div>
          </div>
          <div class="detailsBox">
            <p>设备状态：{{item.statusName}}</p>
            <p>设备类型：{{ item.typeName }}</p>
            <p>入库时间：{{ item.crtTime }}</p>
          </div>
          <div class="operation">
            <Row>
              <Col span="8" class="operationSee">
              <Icon type="reply" size=20></Icon>
              <p @click="show(index)">借出</p>
              </Col>
              <Col span="8" class="operationEdit">
              <Icon type="forward" size=19></Icon>
              <p @click="edit(index)">归还</p>
              </Col>
              <Col span="8" class="operationSee">
              <Icon type="eye" size=20></Icon>
              <p @click="show(index)">查看</p>
              </Col>
            </Row>
          </div>
          </Col>
          <ToolLoan v-bind="equipmentLoan"></ToolLoan>
          <ToolReturn v-bind="equipmentReturn"></ToolReturn>
          <BatchLoan v-bind="equipmentsLoan"></BatchLoan>
          <BatchReturn v-bind="equipmentsReturn"></BatchReturn>
        </Row>
        </Col>
      </Row>
    </div>
    <Page   :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show-sizer
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
            :style='pageStyle'></Page>
  </div>
</template>

<script>
  import types from '../../../../../static/Enum.json'
  import ToolLoan from '../../../../components/UM/OMM/ToolLoan'
  import ToolReturn from '../../../../components/UM/OMM/ToolReturn'
  import BatchLoan from '../../../../components/UM/OMM/BatchLoan'
  import BatchReturn from '../../../../components/UM/OMM/BatchReturn'
  import { TunnelService } from '../../../../services/tunnelService'
  import { EquipmentService } from '../../../../services/equipmentService'

  export default {
    name: "query-tool",
    data() {
      return {
        showOn: true,
        equipments: [],
        equipmentLoan: {       //要出借的单个设备信息
          show: {state:false},
          equipmentLoanInfo: [],
        },
        equipmentReturn: {    //要归还的单个设备信息
          show: {state:false},
          equipmentReturnInfo: [],
        },
        equipmentsLoan: {     //批量借
          show: {state:false},
          equipmentsLoanInfo: [],
        },
        equipmentsReturn: {     //批量还
          show: {state:false},
          equipmentsReturnInfo: [],
        },
        tunnels: [],
        tunnelId: 1,
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
        pagingList: [],
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
    components:{
        ToolLoan,ToolReturn,BatchLoan,BatchReturn
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
                 result.list[index].imgUrl =_this.ApiUrl  + result.list[index].imgUrl.replace(/\\/g, "/")
              }
            }
            _this.equipments = result.list;
            _this.equipmentsLoan.equipmentsLoanInfo = result.list;
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
        this.equipmentLoan.show.state =! this.equipmentLoan.show.state;
        this.OutStorageSimple.show.state=!this.OutStorageSimple.show.state;
        let _this = this
        EquipmentService.equipmentDatagird(this.params).then(
          (result)=>{
            _this.equipmentLoan.equipmentLoanInfo = result.list[index];
          },
          (error)=>{
            _this.Log.info(error)
          })
        // this.axios.post("/equipments/datagrid",(this.params)).then(result => {
        //   let {code,data} = result.data;
        //   if(code == 200){
        //     this.equipmentLoan.equipmentLoanInfo = data.list[index];
        //   }
        // });
        // console.log(index)
        // this.goToMoudle(index, types.pageType.Read);
      },
      edit(index) {
        this.equipmentReturn.show.state =! this.equipmentReturn.show.state;
        let _this = this
        EquipmentService.equipmentDatagird(this.params).then(
          (result)=>{
            _this.equipmentReturn.equipmentReturnInfo = result.list[index];
          },
          (error)=>{
            _this.Log.info(error)
          })
        // this.axios.post("/equipments/datagrid",(this.params)).then(result => {
        //   let {code,data} = result.data;
        //   if(code == 200){
        //     this.equipmentReturn.equipmentReturnInfo = data.list[index];
        //   }
        // });
        // this.goToMoudle(index, types.pageType.Edit);
      },
      add(path) {
        this.$router.push(path);
      },
      pickUp() {
        this.showOn = !this.showOn;
      },
      batchLoanTools(){
        this.equipmentsLoan.show.state =!this.equipmentsLoan.show.state;
      },
      batchReturnTools(){
        this.equipmentsReturn.show.state =!this.equipmentsReturn.show.state;
      }
    }
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


