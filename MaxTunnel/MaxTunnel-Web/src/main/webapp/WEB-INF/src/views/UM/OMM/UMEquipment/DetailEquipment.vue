<template>
  <div style="background: #fff">
    <Row>
      <Col span="12">
        <div style="overflow-y:auto;height: 86vh;padding: 5px;">
          <Form :model="equipment" :label-width="130" @submit.native.prevent>
            <Col span="12">
              <FormItem label="设备名称：">
                <Input v-model="equipment.name" readonly="pageType==pageTypes.Read"></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="所属管廊：">
                <Select v-model="equipment.tunnel.id" :disabled="pageType==pageTypes.Read">
                  <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="设备类型：">
                <Select
                  v-model="equipment.typeId"
                  :disabled="pageType==pageTypes.Read"
                  v-show="pageType==pageTypes.Edit"
                >
                  <Option
                    v-for="item in equipmentTypes"
                    :value="item.id"
                    :key="item.id"
                  >{{ item.name }}</Option>
                </Select>
                <Input
                  v-model="equipment.typeName"
                  readonly="pageType==pageTypes.Read"
                  v-show="pageType==pageTypes.Read"
                ></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="投运时间：">
                <DatePicker
                  type="datetime"
                  placeholder="请选择投运时间"
                  :readonly="pageType==pageTypes.Read"
                  v-model="equipment.runTime"
                  style="width: 100%;"
                  @on-change="getHours(equipment.runTime)"
                ></DatePicker>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="设备状态：">
                <Select v-model="equipment.status" :disabled="pageType==pageTypes.Read">
                  <Option
                    v-for="item in equipmentStatus"
                    :value="item.val"
                    :key="item.val"
                  >{{ item.key }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="供应商：">
                <Select v-model="equipment.vender.id" :disabled="pageType==pageTypes.Read">
                  <Option
                    v-for="(item,index) in venders"
                    :value="item.id"
                    :key="index"
                  >{{ item.name }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="设备型号：">
                <Select v-model="equipment.model.id" :disabled="pageType==pageTypes.Read">
                  <Option
                    v-for="(item,index) in equipmentModels"
                    :value="item.id"
                    :key="index"
                  >{{ item.name }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="运行时间：">
                <Input
                  v-model="runTimeCount"
                  readonly="pageType==pageTypes.Read"
                  style="width: calc(100% - 100px);"
                ></Input>(时)
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="关联监测对象：">
                <Select
                  v-model="equipment.objId"
                  :disabled="pageType==pageTypes.Read"
                  @on-change="getObj()"
                >
                  <Option v-for="item in objs" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
                <!-- <Input v-model='equipment.obj.name' :disabled='pageType==pageTypes.Read' v-show="pageType==pageTypes.Read"></Input> -->
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="对象类型：" v-show="equipment.objId">
                <!-- <Select  :disabled='pageType==pageTypes.Read'>
                                    <Option v-for="(item,index) in venders" :value="item.id" :key="index">{{ item.name }}</Option>
                </Select>-->
                <div v-for="item in objTypes" :key="item.objtypeId">
                  <Input :value="item.objtypeName" readonly></Input>
                </div>
              </FormItem>
            </Col>
          </Form>
          <div style="text-align: center" v-show="pageType==pageTypes.Edit">
            <Button type="default" style="margin-right: 8px;" @click="goBack()">返回</Button>
            <Button type="primary" @click="updateEquipmentInfo()">确定</Button>
          </div>
        </div>
      </Col>
      <Col span="12" style="height:86vh;"  ref="gisBox">
        <TestSmViewer ref="smViewer" :openImageryProvider="false"></TestSmViewer>
      </Col>
    </Row>
  </div>
</template>
<script>
import types from "../../../../../static/Enum.json";
// import SmViewer from '../../../../components/Common/3D/3DViewer'
import TestSmViewer from "../../../../components/Common/3D/simple3DViewer";
import { TunnelService } from "../../../../services/tunnelService";
import { EquipmentService } from "../../../../services/equipmentService";
export default {
  data() {
    return {
      // id: 'detailsEquipmentGIS',
      // 页面类型 1：查看 2：编辑 3：新增
      pageType: 1,
      pageTypes: types.pageType,
      // 待编辑的设备
      equipment: {
        name: null,
        runTime: null,
        tunnel: { id: null },
        model: { id: null },
        vender: { id: null },
        typeId: null,
        typeName: null,
        status: null,
        statusName: null,
        obj: { id: null, name: null },
        objId: 1
      },
      runTimeCount: null,
      // 设备型号
      equipmentModels: [],
      // 供应商
      venders: [],
      // 管廊
      tunnels: [],
      objs: [],
      // 设备类型
      equipmentTypes: [],
      // 设备状态
      equipmentStatus: [],
      objTypes: null
    };
  },
  beforeRouteLeave(to, from, next) {
    if (
      to.name == "设备管理主页" ||
      to.name == "UMPatrolHomePage" ||
      to.name == "虚拟巡检" ||
      to.name == "人员定位详情" ||
      to.name == "管廊安防监控列表" ||
      to.name == "管廊环境监控列表" ||
      from.name == "管廊设备"
    ) {
      from.meta.keepAlive = false;
      to.meta.keepAlive = false;
      this.$destroy();
      next();
    } else {
      to.meta.keepAlive = false;
      from.meta.keepAlive = false;
      this.$destroy();
      next();
    }
  },
  components: {
    TestSmViewer
  },
  watch: {
    $route: function() {
      //2. $route发生变化时再次赋值planId
      this.equipment.id = this.$route.params.id;
    }
  },
  mounted() {
    this.equipment.id = this.$route.params.id;
    this.pageType = this.$route.params.type;
    // 获取所有的管廊
    TunnelService.getTunnels().then(
      result => {
        this.tunnels = result;
      },
      error => {
        this.Log.info(error);
      }
    );

    // 获取所有的供应商
    EquipmentService.getVenders().then(
      result => {
        this.venders = result;
      },
      error => {
        this.Log.info(error);
      }
    );
    //获取type
    EquipmentService.getEquipmentTypes().then(
      res => {
        this.equipmentTypes = res;
      },
      error => {
        this.Log.info(error);
      }
    );
    // 获取所有的型号
    EquipmentService.getEquipmentModels().then(
      result => {
        this.equipmentModels = result;
      },
      error => {
        this.Log.info(error);
      }
    );
    //设备状态
    EquipmentService.getStatus().then(
      res => {
        this.equipmentStatus = res;
      },
      error => {
        this.Log.info(error);
      }
    );
    //obj
    this.getObj();
    this.init();
  },
  methods: {
    init: function() {
      this.axios.get("/equipments/" + this.equipment.id).then(res => {
        let { code, data } = res.data;
        if (code == 200) {
          this.equipment = data;
          this.getHours(data.runTime);
          this.equipment.runTime = new Date(data.runTime).format(
            "yyyy-MM-dd hh:mm:s"
          );
          if (this.equipment.objId != null) {
            this.getObjType();
          }
        }
      });
    },
    //关联监测对象
    getObj() {
      this.axios.get("measobjs").then(res => {
        let { code, data } = res.data;
        if (code == 200) {
            this.objs = data;
            this.getObjType();
        }
      });
    },
    // 对象类型
    getObjType() {
      var info = {
        id: this.equipment.objId,
        pageSize: 10,
        pageNum: 1
      };
      this.axios.post("measobjs/datagrid", info).then(res => {
        let { code, data, msg } = res.data;
        if (code == 200) {
          this.objTypes = data.list;
        }
      });
    },
    //修改设备信息
    updateEquipmentInfo() {
      let info = {
        id: this.equipment.id,
        name: this.equipment.name,
        type: this.equipment.typeId,
        runTime: this.equipment.runTime,
        status: this.equipment.status,
        tunnelId: this.equipment.tunnel.id,
        modelId: this.equipment.model.id,
        venderId: this.equipment.vender.id,
        objId: this.equipment.objId
      };
      this.axios.put("/equipments", info).then(res => {
        let { code, data } = res.data;
        if (code == 200) {
          this.$router.push("/UM/equipment/queryequipment");
        }
      });
    },
    getHours(time) {
      var dataEnd = new Date();
      var dataStart = new Date(time);
      var dataDiff = dataEnd.getTime() - dataStart.getTime();
      var leaver1 = dataDiff % (24 * 3600 * 1000);
      var hours = Math.floor(dataDiff / (3600 * 1000));
      this.runTimeCount = hours;
      return this.runTimeCount;
    },
    //返回
    goBack(){
        this.$router.back(-1);
    }
  },

};
</script>
<style scoped>
.ivu-col-span-12 {
  padding-right: 10px;
}

.ivu-col-span-24 {
  padding-right: 10px;
}

.record {
  border: 1px solid #dddee1;
  line-height: 1.5;
  padding: 2px 7px;
  border-radius: 4px;
  color: #495060;
}

.record li {
  list-style: none;
}
@media (min-width: 2200px){
    h2{
        font-size: 2.4vmin;
    }
    .ivu-form-item >>> .ivu-form-item-label{
        width: 13vmin !important;
        line-height: 6.5vmin;
    }
    .ivu-form-item >>> .ivu-form-item-content{
        margin-left: 13vmin !important;
        line-height: 6.5vmin;
    }
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .ivu-form-item-content{
        line-height: 6.5vmin;
    }
    .btn{
        margin-left: 20% !important;
    }
    .ivu-form-item-required .ivu-form-item-label:before{
        font-size: 1.6vmin !important;
    }
}
</style>
