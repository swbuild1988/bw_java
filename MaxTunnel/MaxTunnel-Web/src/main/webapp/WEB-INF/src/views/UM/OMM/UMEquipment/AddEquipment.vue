<template>
    <div class="formBG">
        <div class="formTitle">添加管廊设备</div>
        <div class="formHeight">
        <Form ref="equipment" :model="equipment" :label-width="120" :rules="validateRules" @submit.native.prevent>
            <FormItem label="设备名称：" prop="name">
                <Input v-model='equipment.name'></Input>
            </FormItem>
            <FormItem label="资产编码：" prop="assetNo">
                <Input v-model='equipment.assetNo'></Input>
            </FormItem>
            <FormItem label="安装位置：" prop="tunnelId">
                <Row :gutter="4">
                    <Col span="7">    
                        <Select v-model='equipment.tunnelId' @on-change="changeTunnelId(equipment.tunnelId)">
                            <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                        </Select>
                    </Col>
                    <Col span="7">
                        <Select v-model="areaId" @on-change="changeSection()">
                            <Option v-for="item in areas" :value="item.id" :key="item.id">{{ item.name }}</Option>
                        </Select>
                    </Col>
                    <Col span="7">
                        <Select v-model="storeId" @on-change="changeSection()">
                            <Option v-for="item in stores" :value="item.id" :key="item.id">{{ item.name }}</Option>
                        </Select>
                    </Col>
                    <Col span="5" style="display: none">
                        <Input v-model="equipment.sectionId" v-show="false"></Input>
                        <!-- <Input v-model="sectionName" readonly></Input> -->
                    </Col>
                </Row>
            </FormItem>
            <FormItem label="投运时间：" prop="runTime">
                <DatePicker type="datetime" placeholder="请选择预计投运时间" v-model='equipment.runTime' style="width: 100%;"></DatePicker>
            </FormItem>
            <FormItem label="设备所属系统：" prop="type">
                <Select v-model='equipment.type'>
                    <Option v-for="item in equipmentTypes" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </FormItem>
            <FormItem label="规格型号：" prop="modelId">
                <Select v-model='equipment.modelId'>
                    <Option v-for="item in equipmentModels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </FormItem>
            <FormItem label="额定电压：" prop="ratedVoltage">
                <Input v-model="equipment.ratedVoltage"></Input>
            </FormItem>
            <FormItem label="量程：" prop="range">
                <Input v-model="equipment.range"></Input>
            </FormItem>
            <FormItem label="厂家：" prop="factory">
                <Input v-model="equipment.factory"></Input>
            </FormItem>
            <FormItem label="品牌：" prop="brand">
                <Input v-model="equipment.brand"></Input>
            </FormItem>
            <FormItem label="供应商：" prop="venderId">
                <Select v-model='equipment.venderId'>
                    <Option v-for="item in venders" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </FormItem>
            <FormItem label="质保期限：" prop="qaTerm">
                <Input v-model="equipment.qaTerm"></Input>
            </FormItem>
            <FormItem label="设备状态：" prop="status">
                <Select v-model="equipment.status">
                    <Option v-for="item in equipmentStatus" :key="item.val" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="关联监测对象：" prop="objId">
                <Input v-model="equipment.objId" @on-change="changeObjId(equipment.objId)"></Input>
                <ul class="chooseObj" v-show="isShowObjs">
                    <li v-for="item in objs" :value="item" :key="item.id" @click="replaceInputValue(item)">{{item}}</li>
                </ul>
            </FormItem>
            <FormItem label="对象类型：">
                <div v-for="item in objTypes" :key="item.objtypeId">
                    <Input :value="item.objtypeName" readonly></Input>
                </div>
            </FormItem>
            <div class="BtnBox">
                <Button type="default" @click="goBack()" style="margin-right: 8px;">返回</Button>
                <Button type="primary" @click="submitEquipment('equipment')" :disabled="isDisable">提交</Button>
            </div>
        </Form>
        </div>
    </div>
</template>
<script>
import { TunnelService } from "../../../../services/tunnelService";
import { EquipmentService } from "../../../../services/equipmentService";
import types from "../../../../../static/Enum.json";
import { URL_CONFIG } from "../../../../../static/3DMap/js/3DMapConfig";
export default {
    data() {
        return {
            //   id: 'GISID',
            // 待编辑的设备
            equipment: {
                name: null,
                assetNo: null,
                type: null,
                runTime: null,
                status: null,
                tunnelId: null,
                sectionId: null,
                modelId: null,
                venderId: null,
                objId: null,
                ratedVoltage: null,
                range: null,
                factory: null,
                brand: null,
                qaTerm: null
            },
            // 设备型号
            equipmentModels: [],
            // 供应商
            venders: [],
            // 管廊
            tunnels: [],
            // 设备类型
            equipmentTypes: [],
            // 设备状态
            equipmentStatus: [],
            //监测对象
            objs: [],
            objTypes: [],
            isDisable: false,
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
                modelId: [
                    { type: 'number', required: true, message: '请选择设备型号', trigger: 'change' }
                ],
                runTime: [
                    { type: 'date', required: true, message: '请选择预计投运时间', trigger: 'change'}
                ],
                status: [
                    { type: 'number', required: true, message: '请选择设备状态', trigger: 'change' }
                ],
                venderId: [
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
            areas: [],
            stores: [],
            areaId: null,
            storeId: null,
            // sectionName: null,
            isShowObjs: false,
            choosedObjId: null
        };
    },
    watch: {
        'equipment.tunnelId': function(newVal, oldVal){
            this.areaId = null
            this.storeId = null
            this.equipment.sectionId = null
            // this.sectionName = null
            this.changeTunnelId(newVal)
        }
    },
    mounted() {
        // 获取所有的管廊
        let _this = this;
        TunnelService.getTunnels().then(
        result => {
            _this.tunnels = result;
        },
        error => {
            _this.Log.info(error);
        }
        );
        //获取type
        EquipmentService.getEquipmentTypes().then(
            res=>{
                this.equipmentTypes = res
            },
            error => {
                this.Log.info(error);
            },
        );
        // 获取所有的供应商
        EquipmentService.getVenders().then(
            result => {
                _this.venders = result;
            },
            error => {
                _this.Log.info(error);
            }
        );
        // 获取所有的型号
        EquipmentService.getEquipmentModels().then(
            result => {
                _this.equipmentModels = result;
            },
            error => {
                _this.Log.info(error);
            }
        );
        //设备状态
        EquipmentService.getStatus().then(
            res=>{
                this.equipmentStatus = res
            },
            error=>{
                this.Log.info(error)
            }
        )
        //obj
        this.axios.get('measobjs').then(
            res=>{
                let{ code,data } = res.data
                if(code==200){
                    this.objs = data
                }
            }
        )
    },
    methods: {
        submitEquipment(name) {
            this.isDisable = true
            setTimeout(() => {
                this.isDisable = false
                this.$refs[name].validate((valid) => {
                    if(valid){
                        EquipmentService.addEquipment(this.equipment).then(
                            result => {
                                this.$router.push('/UM/equipment/queryequipment')
                            },
                            error => {
                                this.Log.info(error);
                            }
                        );
                    }
                })
                
            }, 2000);
        },
        //返回
        goBack(){
            this.$router.back(-1);
        },
        // 对象类型
        getObjType(){
            var info = {
                id: this.equipment.objId,
                pageSize: 10,
                pageNum: 1
            }
            this.axios.post('measobjs/datagrid',info).then(
                res=> {
                    let { code,data,msg } = res.data
                    if(code==200){
                        this.objTypes = data.list
                    }
                }
            )
        },
        //根据TunnelId,获取area和store
        changeTunnelId(id){
            if(id!=null){
                //获取store
                TunnelService.getStoresByTunnelId(id).then(
                    result => {
                        this.stores = result
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
                //获取area
                TunnelService.getAreasByTunnelId(id).then(
                    result => {
                        this.areas = result
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
            }
        },
        //根据areaId,storeId获取section
        changeSection(){
            if(this.storeId!=null&&this.areaId!=null){
                TunnelService.getSectionByAreaIdStoreId(this.storeId, this.areaId).then(
                    result => {
                        this.equipment.sectionId = result.id
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
            }
        },
        changeObjId(objId){
            this.isShowObjs = true 
            EquipmentService.changeObjId(objId).then(
                result => {
                    this.objs = result
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
        }
    }
};
</script>
<style scoped>
.BtnBox {
  text-align: center;
  margin-top: 20px;
}
.ivu-form.ivu-form-label-right{
    width: 700px;
    margin: 10px auto;
    padding: 10px 20px;
    margin-top: 30px;
    border-radius: 4px;
}
.goBack{
    position: absolute;
    bottom: 2vh;
    right: 3vw;
}
/* 上传图片 */
.demo-upload-list{
    display: inline-block;
    width: 60px;
    height: 60px;
    text-align: center;
    line-height: 60px;
    border: 1px solid transparent;
    border-radius: 4px;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0,0,0,.2);
    margin-right: 4px;
}
.demo-upload-list img{
    width: 100%;
    height: 100%;
}
.demo-upload-list-cover{
    display: none;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0,0,0,.6);
}
.demo-upload-list:hover .demo-upload-list-cover{
    display: block;
}
.demo-upload-list-cover i{
    color: #fff;
    font-size: 20px;
    cursor: pointer;
    margin: 0 2px;
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
.formBG{
    background: url("../../../../assets/UM/itemPageBg.png") no-repeat;
    background-size: 100% 100%;
    padding-top: 3vmin;
    padding-bottom: 3vmin;
}

.formBG >>> .ivu-form-item-label,.formTitle{
    color: #fff;
}
.formBG >>>.ivu-form .ivu-form-item-required .ivu-form-item-label:before, .formBG .ivu-form>>>.ivu-form-item-label:before {
    color: #00fff6;
    content: '★';
    display: inline-block;
    margin-right: 0.4vmin;
    line-height: 1;
    font-family: SimSun;
    font-size: 1.2vmin;
}
.formTitle{
    margin-top: -3.5vh;
    font-size: 2.2vmin
}
@media (min-width: 2200px){
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .ivu-form.ivu-form-label-right{
        width: 50%;
    }
    .ivu-form-item >>> .ivu-form-item-label{
        width: 15vmin !important;
        line-height: 4.5vmin;
    }
    .ivu-form-item >>> .ivu-form-item-content{
        margin-left: 15vmin !important;
        line-height: 5.5vmin;
    }
}
</style>


