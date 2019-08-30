<template>
  <div class="detailsBG">
    <Row>
        <Col span="12">
            <div style="overflow-y:auto;height: 78vh;padding: 5px;">
                <Form ref="equipment" :model="equipment" :label-width="130" :rules="validateRules" @submit.native.prevent>
                    <Col span="12">
                        <FormItem label="设备名称：" prop="name">
                            <Input v-model="equipment.name" :readonly=isReadonly></Input>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="资产编码：" prop="assetNo">
                            <Input v-model="equipment.assetNo" :readonly=isReadonly></Input>
                        </FormItem>
                    </Col>
                    <Col span="24">
                        <FormItem label="安装位置：" prop="tunnel.id">
                            <Row :gutter="8">
                                <Col span="7"> 
                                    <Select v-model="equipment.tunnel.id" :disabled="pageType==pageTypes.Read" @on-change="changeTunnelId">
                                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                    </Select>
                                </Col>
                                <Col span="7"> 
                                    <Select v-model="areaId" :disabled="pageType==pageTypes.Read">
                                        <Option v-for="item in areas" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                    </Select>
                                </Col>
                                <Col span="7"> 
                                    <Select v-model="storeId" :disabled="pageType==pageTypes.Read">
                                        <Option v-for="item in stores" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                    </Select>
                                </Col>
                                <Col span="5" v-show="false"> 
                                    <Input readonly v-model="equipment.sectionId"></Input>
                                </Col>
                            </Row>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="设备所属系统：" prop="type">
                            <Select v-model="equipment.type" :disabled="pageType==pageTypes.Read" v-show="pageType==pageTypes.Edit">
                                <Option v-for="item in equipmentTypes" :value="item.id" :key="item.id">{{ item.name }}</Option>
                            </Select>
                            <Input v-model="equipment.typeName" :readonly=isReadonly v-show="pageType==pageTypes.Read"></Input>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="规格型号：" prop="model.id">
                            <Select v-model="equipment.model.id" :disabled="pageType==pageTypes.Read">
                                <Option v-for="(item,index) in equipmentModels" :value="item.id" :key="index">{{ item.name }}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="安装时间：" prop="runTime">
                            <DatePicker type="datetime" placeholder="请选择安装时间" :readonly=isReadonly v-model="equipment.runTime" style="width: 100%;"></DatePicker>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="额定电压：" prop="ratedVoltage">
                            <Input v-model="equipment.ratedVoltage" :readonly=isReadonly></Input>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="量程：" prop="range">
                            <Input v-model="equipment.range" :readonly=isReadonly></Input>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="厂家：" prop="factory">
                            <Input v-model="equipment.factory" :readonly=isReadonly></Input>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="品牌：" prop="brand">
                            <Input v-model="equipment.brand" :readonly=isReadonly></Input>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="供应商：" prop="vender.id">
                            <Select v-model="equipment.vender.id" :disabled="pageType==pageTypes.Read">
                                <Option v-for="(item,index) in venders" :value="item.id" :key="index">{{ item.name }}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="质保期限：" prop="qaTerm">
                            <Input v-model="equipment.qaTerm" :readonly=isReadonly></Input>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="设备状态：" prop="status">
                            <Select v-model="equipment.status" :disabled="pageType==pageTypes.Read">
                                <Option v-for="item in equipmentStatus" :value="item.val" :key="item.val">{{ item.key }}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="关联监测对象：" prop="objId">
                            <!-- <Select v-model="equipment.objId" :disabled="pageType==pageTypes.Read" @on-change="getObj()">
                                <Option v-for="item in objs" :value="item.id" :key="item.id">{{ item.id }}</Option>
                            </Select> -->
                            <Input v-model="equipment.objId" @on-change="getObj(equipment.objId)" :readonly="pageType==pageTypes.Read"></Input>
                            <ul class="chooseObj" v-show="isShowObjs">
                                <li v-for="item in objs" :value="item" :key="item" @click="replaceInputValue(item)">{{item}}</li>
                            </ul>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="对象类型：" v-show="equipment.objId">
                            <div v-for="item in objTypes" :key="item.objtypeId">
                                <Input :value="item.objtypeName" readonly></Input>
                            </div>
                        </FormItem>
                    </Col>
                </Form>
                <div style="text-align: center" v-show="pageType==pageTypes.Edit">
                    <Button type="default" style="margin-right: 8px;" @click="goBack()">返回</Button>
                    <Button type="primary" @click="updateEquipmentInfo('equipment')" :disabled="isDisable">确定</Button>
                </div>
                <div style="text-align: center" v-show="pageType!=pageTypes.Edit">
                    <Button type="default" @click="goBack()">返回</Button>
                </div>
            </div>
        </Col>
        <Col span="12" style="height:78vh;" ref="gisBox">
            <TestSmViewer ref="smViewer" :openImageryProvider="false"></TestSmViewer>
        </Col>
    </Row>
  </div>
</template>
<script>
import types from "../../../../../static/Enum.json";
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
                type: null,
                typeName: null,
                status: null,
                statusName: null,
                obj: { id: null, name: null },
                objId: 1,
                sectionId: null,
                ratedVoltage: null,
                range: null,
                factory: null,
                brand: null,
                assetNo: null,
                qaTerm: null
            },
            validateRules: {
                name: [
                    { required: true, message: '请输入设备名称', trigger: 'blur' }
                ],
                type: [
                    { type: 'number', required: true, message: '请选择设备类型', trigger: 'change' }
                ],
                tunnelId: [
                    { type: 'number', required: true, message: '请选择所属管廊', trigger: 'change' }
                ],
                'model.id': [
                    { type: 'number', required: true, message: '请选择设备型号', trigger: 'change' }
                ],
                'tunnel.id': [
                    { type: 'number', required: true, message: '安装位置不能为空', trigger: 'change' }
                ],
                runTime: [
                    { type: 'date', required: true, message: '请选择预计投运时间', trigger: 'change'}
                ],
                status: [
                    { type: 'number', required: true, message: '请选择设备状态', trigger: 'change' }
                ],
                'vender.id': [
                    { type: 'number', required: true, message: '请选择供应商', trigger: 'change' }
                ],
                objId: [
                    { type: 'number', required: true, message: '请选择关联的监测对象', trigger: 'change' }
                ],
                assetNo: [
                    { required: true, message: '资产编码不能为空', trigger: 'blur' }
                ],
                ratedVoltage: [
                    { required: true, message: '额定电压不能为空', trigger: 'blur' }
                ],
                range: [
                    { required: true, message: '量程不能为空', trigger: 'blur' }
                ],
                factory: [
                    { required: true, message: '厂家不能为空', trigger: 'blur' }
                ],
                brand: [
                    { required: true, message: '品牌不能为空', trigger: 'blur' }
                ],
                qaTerm: [
                    { required: true, message: '质保期限不能为空', trigger: 'blur' }
                ]
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
            objTypes: null,
            isReadonly: false,
            areas: null,
            stores: null,
            areaId: null,
            storeId: null,
            isShowObjs: false,
            isDisable: false
        };
    },
    beforeRouteLeave(to, from, next) {
        if (
            to.name == "设备管理主页" ||
            to.name == "巡检计划总览" ||
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
        if (this.pageType == this.pageTypes.Read) {
            this.isReadonly = true;
        } else {
            this.isReadonly = false;
        }
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
            EquipmentService.getEquDetailByEquId(this.equipment.id).then(
                result => {
                    this.equipment = result;
                    this.getAarasByTunnelId(this.equipment.tunnel.id)
                    this.getStoresByTunnelId(this.equipment.tunnel.id)
                    this.equipment.runTime = new Date(result.runTime).format("yyyy-MM-dd hh:mm:s");
                    if (this.equipment.objId != null) {
                        this.getObjType();
                    }
                    this.areaId = result.section.area.id
                    this.storeId = result.section.store.id
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //关联监测对象
        getObj(objId) {
            this.isShowObjs = true 
            EquipmentService.changeObjId(objId).then(
                result => {
                    this.objs = result
                    this.getObjType();
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        replaceInputValue(id){
            this.equipment.objId = id
            this.isShowObjs = false
            this.getObjType()
        },
        // 对象类型
        getObjType() {
            var info = {
                id: this.equipment.objId,
                pageSize: 10,
                pageNum: 1
            };
            EquipmentService.getObjType(info).then(
                res => {
                    this.objTypes = res;
                },
                error => {
                    this.Log.info(error);
                }
            );
        },
        //修改设备信息
        updateEquipmentInfo(name) {
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                this.$refs[name].validate((valid) => {
                    if(valid){
                        let info = {
                            id: this.equipment.id,
                            name: this.equipment.name,
                            type: this.equipment.type,
                            runTime: this.equipment.runTime,
                            status: this.equipment.status,
                            tunnelId: this.equipment.tunnel.id,
                            modelId: this.equipment.model.id,
                            venderId: this.equipment.vender.id,
                            objId: this.equipment.objId,
                            sectionId: this.equipment.section.id,
                            ratedVoltage: this.equipment.ratedVoltage,
                            range: this.equipment.range,
                            factory: this.equipment.factory,
                            brand: this.equipment.brand,
                            assetNo: this.equipment.assetNo,
                            qaTerm: this.equipment.qaTerm
                        };
                        EquipmentService.updateEquipmentInfo(info).then(
                            result => {
                                this.$router.push("/UM/equipment/queryequipment");
                            },
                            error => {
                                this.Log.info(error)
                            }
                        )
                    }
                })
            },2000)            
        },
        //返回
        goBack() {
            this.$router.back(-1);
        },
        //获取areas
        getAarasByTunnelId(id){
            TunnelService.getAreasByTunnelId(id).then(
                result => {
                    this.areas = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //获取stores
        getStoresByTunnelId(id){
            TunnelService.getStoresByTunnelId(id).then(
                result => {
                    this.stores = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        changeTunnelId(id){
            this.areaId = null
            this.storeId = null
            this.equipment.section.name = null
            this.getAarasByTunnelId(id)
            this.getStoresByTunnelId(id)
            console.log('this.areaId111', this.areaId)
        }
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
.detailsBG{
    background: url("../../../../assets/UM/infoBox.png") no-repeat;
    background-size: 100% 100%;
    padding: 3vmin;
}
.detailsBG >>> .ivu-form-item-label{
    color: #fff;
}
.chooseObj{
    width: 13vw;
    max-height: 13.1vh;
    position: relative;
    border-radius: 4px;
    overflow-y: auto;
    z-index: 999;
    background: #fff;
}
.chooseObj:before, .chooseObj:after{
    width: 0vw;
    height: 0vh;
    border: transparent solid;
    position: absolute;
    bottom: 100%;
    content: ''
}
.chooseObj:before{
    border-width: 10px;
    border-bottom-color: #cccccc;
    left: 20px;
}
.chooseObj:after{
    border-width: 8px;
    border-bottom-color: #ffffff;
    left: 22px;
}
.chooseObj li{
    list-style: none;
    line-height: 2.7vh;
    padding-left: 1vw;
    cursor: pointer;
}
.chooseObj li:hover{
    background: #f3f3f3;
}
@media (min-width: 2200px) {
  h2 {
    font-size: 2.4vmin;
  }
  .ivu-form-item >>> .ivu-form-item-label {
    width: 13vmin !important;
    line-height: 6.5vmin;
  }
  .ivu-form-item >>> .ivu-form-item-content {
    margin-left: 13vmin !important;
    line-height: 6.5vmin;
  }
  .ivu-select,
  .ivu-select >>> .ivu-select-selection,
  .ivu-input-wrapper >>> .ivu-input,
  .ivu-date-picker >>> .ivu-input,
  .ivu-select.ivu-select-single >>> .ivu-select-selected-value,
  .ivu-select.ivu-select-single >>> .ivu-select-placeholder {
    height: 4vmin;
    line-height: 4vmin;
    font-size: 1.4vmin;
  }
  .ivu-form-item-content {
    line-height: 6.5vmin;
  }
  .btn {
    margin-left: 20% !important;
  }
  .ivu-form-item-required .ivu-form-item-label:before {
    font-size: 1.6vmin !important;
  }
}
</style>
