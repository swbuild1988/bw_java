<template>
    <div style="padding: 10px;background: #fff;">
        <Row>
            <Col span="12">
                <Form :model="equipment" :label-width="100" @submit.native.prevent>
                        <Col span="12">
                            <FormItem label="设备编号">
                                <Input v-model='equipment.assetNo'></Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="设备名称">
                                <Input v-model='equipment.name'></Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="所属管廊">
                                <Select v-model='equipment.tunnelId'>
                                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="设备类型">
                                <Select v-model='equipment.type'>
                                    <Option v-for="item in equipmentTypes" :value="item.key" :key="item.key">{{ item.value }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="投运时间">
                                <DatePicker type="date" placeholder="请选择投运时间" v-model='equipment.crtTime' style="width: 100%;"></DatePicker>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="设备状态">
                                <Select v-model='equipment.status'>
                                    <Option v-for="item in equipmentStatus" :value="item.key" :key="item.key">{{ item.value }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="供应商">
                                 <Select v-model='equipment.venderId'>
                                    <Option v-for="item in venders" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="设备型号" >
                                <Select v-model='equipment.modelId'>
                                    <Option v-for="item in equipmentModels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="24">
                            <FormItem label="设备位置">
                                <Input v-model='equipment.tunnelName'></Input>
                            </FormItem>
                        </Col>
                        <Col span="24">
                            <FormItem label="维修记录">
                                <Input  type="textarea" :rows="5" ></Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="关联检测对象：">
                                 <Select>
                                    <Option v-for="item in venders" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="对象类型：">
                                 <Select>
                                    <Option v-for="item in venders" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col :xs="{ span: 21, offset: 3 }" :lg="{ span: 21, offset: 3 }">
                            <div class="showObjectStatus">
                                根据类型显示不同的对象状态
                            </div>
                        </Col>
                        <Col span="12" class="BtnBox">
                            <Button type="primary">相关备品</Button>
                        </Col>
                        <Col span="12" class="BtnBox">
                            <Button type="primary">编辑</Button>
                        </Col>
                </Form>
            </Col>
            <Col span="12">
                <div class="equipmentTypeImg">
                    <!-- <sm-viewer
                      :id="id"
                      ref="smViewer">
                    </sm-viewer> -->
                </div>
                <div class="BIM">
                    BIM
                </div>
            </Col>
            <Col span="12" class="BtnBox">
                <Button type="primary" @click="submitEquipment">提交</Button>
            </Col>
        </Row>
    </div>
</template>
<script>
import { TunnelService } from '../../../../services/tunnels'
import { EquipmentService } from '../../../../services/equipments'
import types from "../../../../../static/Enum.json";
import SmViewer from '../../../../components/Common/3D/3DViewer'
import { URL_CONFIG } from '../../../../../static/3DMap/js/3DMapConfig'
export default {
  data() {
    return {
      id: 'GISID',
      // 待编辑的设备
      equipment: {
        id: null,
        assetNo: "NO1",
        name: "设备1",
        modelId: null,
        venderId:null,
        type: null,
        tunnelId: null,
        startTime: new Date(),
        status: null
      },
      // 设备型号
      equipmentModels: [

      ],
      // 供应商
      venders: [

      ],
      // 管廊
      tunnels: [],
      // 设备类型
      equipmentTypes: types.equipmentType,
      // 设备状态
      equipmentStatus: types.equipmentStatus
    };
  },
  components:{
    SmViewer
  },
  mounted() {
    // 获取所有的管廊
      let _this = this
      TunnelService.getTunnels().then(
          (result)=>{
              _this.tunnels = result;
          },
          (error)=>{
              _this.Log.info(error)
      }) 
      // axios.get("/tunnels ").then(response => {
      //     let { code, data } = response.data;
      //     if (code == 200) {
      //         this.tunnels = data;
      //     }
      // });

      // 获取所有的供应商
      EquipmentService.getVenders().then(
        (result)=>{
          _this.venders = result
        },
        (error)=>{
          _this.Log.info(error)
        })
      // axios.get("/venders").then(response => {
      //     let { code, data } = response.data;
      //     if (code == 200) {
      //         this.venders = data;
      //     }
      // });

      // 获取所有的型号
      EquipmentService.getEquipmentModels().then(
        (result)=>{
          _this.equipmentModels = result;
        },
        (error)=>{
          _this.Log.info(error)
        })
      // axios.get("/equipment-models").then(response => {
      //     let { code, data } = response.data;
      //     if (code == 200) {
      //         this.equipmentModels = data;
      //     }
      // });
  },
  methods: {
      submitEquipment(){
          setTimeout(()=>{
                let _this = this
                EquipmentService.addEquipment(this.equipment).then(
                    (result)=>{
                    _this.Log.info('success')
                    },
                    (error)=>{
                    _this.Log.info(error)
                    })
          },2000)
          // axios.post('/equipments',this.equipment).then(response=>{
          // })
      },
      onload(parent) {
      let Cesium = parent.Cesium;

      // 初始化viewer部件
      var viewer = new Cesium.Viewer(this.id,{
        navigation:false //关闭导航控件
      });
      var scene = viewer.scene,
        widget = viewer.cesiumWidget,
        imageryLayers = viewer.imageryLayers,
        imagery_mec;

      var provider_mec = new Cesium.SuperMapImageryProvider({
        url : URL_CONFIG.IMG_MAP//墨卡托投影地图服务
      });
      imagery_mec = imageryLayers.addImageryProvider(provider_mec);


      try{
        //打开所发布三维服务下的所有图层
        var promise = scene.open(URL_CONFIG.BIM_SCP);
        Cesium.when(promise,function(layer){

          var overGroundLayer = scene.layers._layerQueue[0];
          //设置相机位置、视角，便于观察场景
          scene.camera.setView({
            destination : new Cesium.Cartesian3.fromDegrees(116.43709465365579,39.91793569836651,1245.5482069457155),
            orientation : {
              heading : 1.5735504792701676,
              pitch : -0.9120242487858476,
              roll : 6.281841926729911
            }
          });
        },function(e){
          if (widget._showRenderLoopErrors) {
            var title = '加载SCP失败，请检查网络连接状态或者url地址是否正确？';
            widget.showErrorPanel(title, undefined, e);
          }
        });
      }
      catch(e){
        if (widget._showRenderLoopErrors) {
          var title = '渲染时发生错误，已停止渲染。';
          widget.showErrorPanel(title, undefined, e);
        }
      }
    },
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
.showObjectStatus{
    background-color: #F2F2F2;
    height: 100px;
    line-height: 100px;
    text-align: center;
}
.BtnBox{
    text-align: center;
    margin-top: 20px;
}
.ivu-btn-primary{
    width: 110px;
}
.equipmentTypeImg,.BIM{
  height: 31vh;
  width: 98%;
}
.BIM{
    background: #F2F2F2;
    text-align: center;
    line-height: 220px;
}
</style>


