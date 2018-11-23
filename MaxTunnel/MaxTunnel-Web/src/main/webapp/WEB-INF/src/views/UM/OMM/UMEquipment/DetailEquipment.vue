<template>
  <div style="padding: 8px;background: #fff;">
    <Row>
      <Col span="12">
      <div style="overflow-y:auto;height: 86vh;">
        <Form :model="equipment" :label-width="110" @submit.native.prevent>
          <Col span="12">
          <FormItem label="设备编号">
            <Input v-model='equipment.assetNo' :disabled='pageType!=pageTypes.Add'></Input>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="设备名称">
            <Input v-model='equipment.name' :disabled='pageType==pageTypes.Read'></Input>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="所属管廊">
            <Select v-model='equipment.tunnel.id' :disabled='pageType==pageTypes.Read'>
              <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="设备类型">
            <Select v-model='equipment.type' :disabled='pageType==pageTypes.Read'>
              <Option v-for="(item,index) in equipmentTypes" :value="index" :key="index">{{ item.value }}</Option>
            </Select>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="投运时间">
            <Input v-model='equipment.crtTime' :disabled='pageType==pageTypes.Read'></Input>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="设备状态">
            <Select v-model='equipment.status' :disabled='pageType==pageTypes.Read'>
              <Option v-for="(item,index) in equipmentStatus" :value="item.key" :key="index">{{ item.value }}</Option>
            </Select>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="供应商">
            <Select v-model='equipment.vender.id' :disabled='pageType==pageTypes.Read'>
              <Option v-for="(item,index) in venders" :value="item.id" :key="index">{{ item.name }}</Option>
            </Select>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="设备型号">
            <Select v-model='equipment.model.id' :disabled='pageType==pageTypes.Read'>
              <Option v-for="(item,index) in equipmentModels" :value="item.id" :key="index">{{ item.name }}</Option>
            </Select>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="设备位置">
            <Input v-model='equipment.tunnelName' :disabled='pageType==pageTypes.Read'></Input>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="维修记录" v-show="pageType!=pageTypes.Create" style="overflow-y:auto;max-height: 80px; min-height: 40px;">
            <ul class="record">
              <li v-for="(item,index) in repairRecord" :key="index" :disabled='pageType==pageTypes.Read'>
                {{item.id}} : {{item.record}}
              </li>
            </ul>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="报警次数">
            <Input v-model='equipment.alarmCount' disabled></Input>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="运行时间">
            <Input v-model='equipment.runTime' disabled style="width: calc(100% - 30px);"></Input>(时)
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="关联检测对象">
            <Select v-model='equipment.venderId' :disabled='pageType==pageTypes.Read'>
              <Option v-for="(item,index) in venders" :value="item.id" :key="index">{{ item.name }}</Option>
            </Select>
          </FormItem>
          </Col>
          <Col span="12">
          <FormItem label="对象类型">
            <Select v-model='equipment.venderId' :disabled='pageType==pageTypes.Read'>
              <Option v-for="(item,index) in venders" :value="item.id" :key="index">{{ item.name }}</Option>
            </Select>
          </FormItem>
          </Col>
          <Col span="24">
          <FormItem label="设备图片">
            <img :src="equipmentIMgSrc" class="equipmentImg">
          </FormItem>
          </Col>
          <Col span="24" style="text-align: center" v-show="pageType==pageTypes.Edit">
          <Button type="primary">确定</Button>
          <Button type="default">取消</Button>
          </Col>
        </Form>
      </div>
      </Col>
      <Col span="12" style="height:86vh;" id="GISbox" ref="gisBox">
      <!-- <sm-viewer
        :id="id"
        ref="smViewer">
      </sm-viewer> -->
        <TestSmViewer ref="smViewer"></TestSmViewer>
      </Col>
    </Row>
  </div>
</template>
<script>
  import types from "../../../../../static/Enum.json";
  // import SmViewer from '../../../../components/Common/3D/3DViewer'
  import TestSmViewer from "../../../../components/Common/3D/Test3DViewer";
  import {URL_CONFIG} from '../../../../../static/3DMap/js/3DMapConfig'
  import equipmentIMg from "../../../../assets/UM/equipment.png"
  import { TunnelService } from '../../../../services/tunnelService'
  import { EquipmentService } from '../../../../services/equipmentService'

  export default {
    data() {
      return {
        // id: 'detailsEquipmentGIS',
        // 页面类型 1：查看 2：编辑 3：新增
        pageType: 1,
        pageTypes: types.pageType,
        equipmentIMgSrc: equipmentIMg,
        // 待编辑的设备
        equipment: {
          id: 1,
          assetNo: "NO1",
          name: "设备1",
          alarmCount: 0,
          runTime: "",
          model: {
            id: null,
            name: null
          },
          tunnel: {
            id: null,
            name: null
          },
          vender: {
            id: null,
            name: null
          },
          type: 1,
          tunnelId: 1,
          startTime: new Date(),
          status: 1
        },
        // 设备型号
        equipmentModels: [],
        // 供应商
        venders: [],
        // 管廊
        tunnels: [],
        // 设备类型
        equipmentTypes: types.equipmentType,
        // 设备状态
        equipmentStatus: types.equipmentStatus,
        //维修记录
        repairRecord: [
          {id: 1, record: "检修一次"},
          {id: 2, record: "检修两次次"},
          {id: 3, record: "检修一次"},
          {id: 4, record: "检修两次次"},
        ]
      };
    },
    beforeRouteLeave (to, from, next) {
      if(to.name == '设备管理主页' || to.name == 'UMPatrolHomePage' || to.name == '虚拟巡检' || to.name == '人员定位详情' 
      || to.name == '管廊安防监控详情' || to.name == '管廊安防监控列表' || to.name == '管廊环境监测列表'){
        from.meta.keepAlive = false;
        to.meta.keepAlive = false;
        this.$destroy()
        next()
      }else{
        to.meta.keepAlive = false
        from.meta.keepAlive = false
        this.$destroy()
        next()
      }
    },
    components: {
      // SmViewer
      TestSmViewer
    },
    mounted() {
      this.equipment.id = this.$route.params.id;
      this.pageType = this.$route.params.type;
      this.init();
      this.setGIS()
    },
    methods: {
      init: function () {
        if (this.equipment.id == undefined) this.equipment.id = 1;
        let _this = this
        if (this.pageType != this.pageTypes.Add) {
          // 获取设备详细信息
          EquipmentService.getEquDetailByEquId(this.equipment.id).then(
            (result)=>{
              _this.equipment.tunnelId = result.tunnel.id
              _this.equipment = result;
              _this.equipment.crtTime = new Date(result.crtTime).format(
                "yyyy-MM-dd")
            },
            (error)=>{
               _this.Log.info(error)
            })
        }

        // 获取所有的管廊
        TunnelService.getTunnels().then(
          (result)=>{
            _this.tunnels = result;
          },
          (error)=>{
            _this.Log.info(error)
          })

        // 获取所有的供应商
        EquipmentService.getVenders().then(
        (result)=>{
          _this.venders = result
        },
        (error)=>{
          _this.Log.info(error)
        })

        // 获取所有的型号
        EquipmentService.getEquipmentModels().then(
        (result)=>{
          _this.equipmentModels = result;
        },
        (error)=>{
          _this.Log.info(error)
        })
      },
      setGIS(){
        var gis = document.getElementById('newID')
        gis.style.display = "block";
        gis.style.position = 'absolute';
        gis.style.top = '0px';
        gis.style.height = '100%';
        gis.style.width = '100%'    
        document.body.removeChild(gis)
        document.getElementById("GISbox").appendChild(gis)
      },
      destoryGis(){
        var gis = document.getElementById("newID");
        gis.style.display = "none";
        document.getElementById("GISbox").removeChild(gis)
        document.body.appendChild(gis)
      }
    },
    beforeDestroy(){
      this.destoryGis()
    }
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

  .equipmentImg {
    height: 250px;
    border: 1px solid gray;
    width: 100%;
  }
</style>


