<template>
  <div class="allDiv">
    <Row class="queryCondition">
      <Col span="6">
        <span>设备名称</span><span>：</span>
        <Input v-model="conditions.name" style="width:60%"></Input>
      </Col>
      <Col span="6">
        <span>设备类型：</span>
        <Select v-model="conditions.typeId" style="width: 60%">
            <Option value=null key=0>所有</Option>
            <Option v-for="item in equipmentType" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </Col>
      <Col span="6">
        <span>设备型号：</span>
        <Select v-model="conditions.modelId" style="width: 60%">
            <Option value=null key=0>所有</Option>
            <Option v-for="item in equipmentModel" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </Col>
      <Col span="6">
        <span>设备状态：</span>
        <Select v-model="conditions.status" style="width:60%">
          <Option value=null key="0">所有</Option>
          <Option v-for="item in equipmentStatus" :value="item.key" :key="item.key">{{ item.value }}</Option>
        </Select>
      </Col>
      <Col span="6">
        <Span class="word43">供应商</Span><span>：</span>
        <Select v-model="conditions.venderId" style="width:60%">
            <Option value=null key="0">所有</Option>
            <Option v-for="item in venders" :key="item.id" :value="item.id">{{item.name}}</Option>
        </Select>
      </Col>
      <Col span="6">
        <span>开始时间：</span>
        <DatePicker type="date" placeholder="请选择开始时间" style="width:60%" v-model="conditions.startTime"></DatePicker>
      </Col>
      <Col span="6">
        <span>结束时间：</span>
        <DatePicker type="date" placeholder="请选择结束时间" style="width:60%" v-model="conditions.endTime"></DatePicker>
      </Col>
      <Col span="6">
        <Button type="primary"  icon="ios-search" @click="showTable()">查询</Button>
      </Col>
    </Row>
    <div class="list">
      <Row>
        <h1 class="equipmentList">设备列表</h1>
        <Col span="24">
          <Button class="addList" @click="add({path: '/UM/equipment/add'})" long>
            <Icon type="plus-round"></Icon>
            添加
          </Button>
        </Col>
        <Col span="24">
        <Row :gutter="16">
          <Col span="6" v-for="(item,index) in equipments" :key="index" style="margin-top: 1vmin;">
            <div :style="backImage" class="backGoundBox">
              <div class="topBox">
                <a class="ivu-modal-close" style="right: 8px;top:0px;color: #f5650b" ><i class="ivu-icon ivu-icon-ios-close-empty" @click="del(index)"></i></a>
                <p class="equipentTitle">{{ item.name }}</p>
                <div class="imgBox"><img :src="imgUrl"></div>
              </div>
            </div>
            <Row class="detailsBox">
              <Col span="11">所属管廊：{{item.tunnel.name}}</Col>
              <Col span="13">设备类型：{{ item.typeName }}</Col>
              <Col span="11">供应商：{{item.vender.name}}</Col>
              <Col span="13">所属型号：{{item.model.name}}</Col>
              <Col span="11">设备状态：{{item.statusName}}</Col>
              <Col span="13">启用时间：{{ item.crtTime }}</Col>
            </Row>
            <div class="operation">
              <Row>
                <Col span="8" class="operationSee">
                <Icon type="eye" size=20></Icon>
                <p @click="show(index)">查看</p>
                </Col>
                <Col span="8" class="operationEdit">
                <Icon type="edit" size=19></Icon>
                <p @click="edit(index)">编辑</p>
                </Col>
                <Col span="8" class="operationDel">
                <Icon type="trash-a" size=20></Icon>
                <p @click="del(index)">删除</p>
                </Col>
              </Row>
            </div>
          </Col>
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
import types from "../../../../../static/Enum.json";
import { TunnelService } from "../../../../services/tunnelService";
import { EquipmentService } from "../../../../services/equipmentService";
import equipemtTunnel from "../../../../assets/UM/equipemtTunnel.jpg";
export default {
  name: "queryEquipment",
  data() {
    return {
      imgUrl: equipemtTunnel,
      showOn: true,
      equipments: [],
      tunnels: [],
      tunnelId: 1,
      equipmentTypes: [],
      deleteEquipmentInfo: [], //要删除的设备
      conditions: {
        name: null,
        typeId: null,
        modelId: null,
        status: null,
        venderId: null,
        startTime: null,
        endTime: null
      },
      equipmentStatus: types.equipmentStatus,
      pagingList: [],
      page: {
        pageNum: 1,
        pageSize: 8,
        pageTotal: 0
      },
      pageStyle: {
        position: "absolute",
        bottom: "20px",
        right: "15px"
      },
      equipmentType: [],
      equipmentModel: [],
      venders: [],
      backImage: {
        backgroundImage: "url(" + require("../../../../assets/UM/equipemtTunnel.jpg") + ")",
      }
    };
  },
  beforeRouteLeave(to, from, next) {
    if (to.name == "UMDetailEquipment") {
      to.meta.keepAlive = false;
      next();
    } else {
      from.meta.keepAlive = false;
      next();
    }
  },
  watch: {
    $route: function() {
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
        name: this.conditions.name,
        type: this.conditions.typeId,
        status: this.conditions.status,
        tunnelId: this.conditions.tunnelId,
        venderId: this.conditions.venderId,
        modelId: this.conditions.modelId,
        startTime: this.conditions.startTime,
        endTime: this.conditions.endTime,
        pageNum: this.page.pageNum,
        pageSize: this.page.pageSize
      };
      return Object.assign({}, param);
    }
  },
  mounted() {
    //从数据库读取select的option选项
    let _this = this;
    TunnelService.getTunnels().then(
      result => {
        _this.tunnels = result;
        _this.selectedTunnel = _this.tunnels.length > 0 ? _this.tunnels[0] : {};
        _this.selectedTunnelName = _this.selectedTunnel.name;
      },
      error => {
        _this.Log.info(error);
      }
    );

    //获取type
    EquipmentService.getEquipmentTypes().then(
        res=>{
            this.equipmentType = res
        },
        error => {
            this.Log.info(error);
        },
    );

    //获取model
    EquipmentService.getEquipmentModels().then(
        res=>{
            this.equipmentModel = res
        },
        error => {
            this.Log.info(error)
        }
    )
    //获取供应商
    EquipmentService.getVenders().then(
        res=>{
            this.venders = res
        },
        error=>{
            this.Log.info(error)
        }
    )
    this.showTable();
  },
  methods: {
    // type 1:查看， 2：编辑
    goToMoudle: function(index, type) {
      this.$router.push({
        name: "UMDetailEquipment",
        params: {
          id: this.equipments[index].id,
          type: type
        }
      });
    },
    showTable() {
      let _this = this;
      if(new Date(_this.conditions.startTime)>new Date(_this.conditions.endTime)){
        _this.$Message.error('开始时间必须小于结束时间！');
        return;
      }
      EquipmentService.equipmentDatagird(this.params).then(
        result => {
          for (let index in result.list) {
            result.list[index].crtTime = new Date(result.list[index].crtTime).format("yyyy-MM-dd hh:mm:s");
            // if (result.list[index].imgUrl != null) {
            //   result.list[index].imgUrl =
            //     _this.ApiUrl + result.list[index].imgUrl.replace(/\\/g, "/");
            // }
          }
          _this.equipments = result.list;
          _this.page.pageTotal = result.total;
        },
        error => {
          _this.Log.info(error);
        }
      );
    },
    handlePage(value) {
      this.page.pageNum = value;
      this.showTable();
    },
    handlePageSize(value) {
      this.page.pageSize = value;
      this.showTable();
    },
    show(index) {
      this.goToMoudle(index, types.pageType.Read);
    },
    edit(index) {
      this.goToMoudle(index, types.pageType.Edit);
    },
    del(index) {
      let _this = this;
      EquipmentService.equipmentDatagird(this.params).then(
        result => {
          _this.deleteEquipmentInfo = result.list[index];
        },
        error => {
          _this.Log.info(error);
        }
      );

      this.$Modal.confirm({
        title: "删除",
        content: "<p>确认删除吗?</p>",
        onOk: () => {
          let _this = this;
          EquipmentService.deleteEquipment(this.deleteEquipmentInfo.id).then(
            result => {
              _this.showTable();
            },
            error => {
              _this.Log.info(error);
            }
          );
        }
      });
    },
    add(path) {
      this.$router.push(path);
    },
    pickUp() {
      this.showOn = !this.showOn;
    }
  }
};
</script>
<style scoped>

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
  line-height: 10vh;
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
  height: 10vh;
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
    width: 5vh;
    height: 5vh;
    border-radius: 5vh;
    position: absolute;
    z-index: 5;
    left: 50%;
    top: 70%;
    margin-left: -2.5vh
}

.detailsBox {
  border: 1px solid #ebe8e8;
  padding-top: 20px;
  background: #fff;
}

.detailsBox .ivu-col-span-11,.detailsBox .ivu-col-span-13 {
  line-height: 35px;
  padding-left: 20px;
}

.operationSee,
.operationEdit,
.operationDel {
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

.operationSee p,
.operationEdit p,
.operationDel p {
  cursor: pointer;
}
.word43{
  letter-spacing: 0.5em;
  margin-right:  -0.5em;
}
@media (min-width: 2200px){
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .queryCondition{
        font-size: 1.4vmin;
    }
    .equipmentList{
        font-size: 2.5vmin;
    }
    .addList {
        border: 0.2min dashed #dfdfdf;
        text-align: center;
        line-height: 4min;
        color: #8b8b8b;
        margin-top: 20px;
        border-radius: 10px;
        background: #fff;
        z-index: 10001;
    }
    .detailsBox{
        padding-top: 2vh;
    }
    .detailsBox .ivu-col-span-11,.detailsBox .ivu-col-span-13{
        line-height: 4vmin;
        font-size: 1.3vmin;
    }
    .operation{
        font-size: 1.4vmin;
    }
    .equipentTitle{
      font-size: 2vmin;
    }
    .ivu-icon{
      font-size: 2vmin !important;
    }
}
</style>


