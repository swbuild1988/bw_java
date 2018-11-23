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
                <div class="equipmentTypeImg" id="GISbox">
                    <sm-viewer
                      :id="id"
                      ref="smViewer">
                    </sm-viewer>
                    <TestSmViewer ref="smViewer"></TestSmViewer>
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
import { TunnelService } from '../../../../services/tunnelService'
import { EquipmentService } from '../../../../services/equipmentService'
import types from "../../../../../static/Enum.json";
import SmViewer from '../../../../components/Common/3D/3DViewer'
// import TestSmViewer from "../../../../components/Common/3D/Test3DViewer";
import { URL_CONFIG } from '../../../../../static/3DMap/js/3DMapConfig'
export default {
    data() {
        return {
        //   id: 'GISID',
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
        // TestSmViewer
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
            // this.setGIS()
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
        }
    },
    // beforeDestroy(){
    //     var gis = document.getElementById("newID");
    //     gis.style.display = "none";
    //     document.getElementById("GISbox").removeChild(gis)
    //     document.body.appendChild(gis)
    // }
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
}
.BIM{
    background: #F2F2F2;
    text-align: center;
    line-height: 220px;
}
</style>


