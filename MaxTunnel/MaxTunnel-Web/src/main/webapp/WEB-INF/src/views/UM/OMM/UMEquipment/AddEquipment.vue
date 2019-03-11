<template>
    <div :style="backStyle">
        <Form ref="equipment" :model="equipment" :label-width="140" :rules="validateRules" @submit.native.prevent>
            <h1 class="formTitle">添加管廊设备</h1>
            <FormItem label="设备名称：" prop="name">
                <Input v-model='equipment.name'></Input>
            </FormItem>
            <FormItem label="所属管廊：" prop="tunnelId">
                <Select v-model='equipment.tunnelId'>
                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </FormItem>
            <FormItem label="设备类型：" prop="type">
                <Select v-model='equipment.type'>
                    <Option v-for="item in equipmentTypes" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </FormItem>
            <FormItem label="设备型号：" prop="modelId">
                <Select v-model='equipment.modelId'>
                    <Option v-for="item in equipmentModels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </FormItem>
            <FormItem label="设备状态：" prop="status">
                <Select v-model="equipment.status">
                    <Option v-for="item in equipmentStatus" :key="item.val" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="预计投运时间：" prop="runTime">
                <DatePicker type="datetime" placeholder="请选择预计投运时间" v-model='equipment.runTime' style="width: 100%;"></DatePicker>
            </FormItem>
            <FormItem label="供应商：" prop="venderId">
                <Select v-model='equipment.venderId'>
                    <Option v-for="item in venders" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </FormItem>
            <FormItem label="关联监测对象：" prop="objId">
                <Select v-model="equipment.objId" @on-change="getObjType">
                    <Option v-for="item in objs" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </FormItem>
            <FormItem label="对象类型：">
                <div v-for="item in objTypes" :key="item.objtypeId">
                    <Input :value="item.objtypeName" readonly></Input>
                </div>
            </FormItem>
            <!-- <FormItem label="设备图片：">
                <div class="demo-upload-list" v-for="item in uploadList" :key="item.url">
                    <template v-if="item.status === 'finished'">
                        <img :src="item.url">
                        <div class="demo-upload-list-cover">
                            <Icon type="ios-eye-outline" @click.native="handleView(item.name)"></Icon>
                            <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
                        </div>
                    </template>
                    <template v-else>
                        <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
                    </template>
                </div>
                <Upload
                    ref="upload"
                    :show-upload-list="false"
                    :default-file-list="defaultList"
                    :on-success="handleSuccess"
                    :format="['jpg','jpeg','png','gif']"
                    :max-size="2048"
                    :on-format-error="handleFormatError"
                    :on-exceeded-size="handleMaxSize"
                    :before-upload="handleBeforeUpload"
                    multiple
                    type="drag"
                    action="//jsonplaceholder.typicode.com/posts/"
                    style="display: inline-block;width:58px;">
                    <div style="width: 58px;height:58px;line-height: 58px;">
                        <Icon type="camera" size="20"></Icon>
                    </div>
                </Upload>
                <Modal title="View Image" v-model="visible">
                    <img :src="'https://o5wwk8baw.qnssl.com/' + imgName + '/large'" v-if="visible" style="width: 100%">
                </Modal>
            </FormItem> -->
            <!-- <FormItem label="相关备品：">
                <Button type="default" @click="associationsBackUp()">查看相关备品</Button>
            </FormItem> -->
            <div class="BtnBox">
                <Button type="default" @click="goBack()" style="margin-right: 8px;">返回</Button>
                <Button type="primary" @click="submitEquipment('equipment')" :disabled="isDisable">提交</Button>
            </div>
        </Form>
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
                type: null,
                runTime: null,
                status: 1,
                tunnelId: null,
                modelId: null,
                venderId: null,
                objId: 1
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
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                position: 'relative',
                paddingTop: '20px',
                paddingBottom: '20px',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%'
            },
            //图片上传
            defaultList: [
                // {
                //     'name': 'a42bdcc1178e62b4694c830f028db5c0',
                //     'url': 'https://o5wwk8baw.qnssl.com/a42bdcc1178e62b4694c830f028db5c0/avatar'
                // },
                // {
                //     'name': 'bc7521e033abdd1e92222d733590f104',
                //     'url': 'https://o5wwk8baw.qnssl.com/bc7521e033abdd1e92222d733590f104/avatar'
                // }
            ],
            imgName: '',
            visible: false,
            uploadList: [],
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
            }
        };
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
        // this.getObjType()
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
        //查看相关备品
        associationsBackUp(){
            
        },
        // //上传图片list
        // handleView (name) {
        //     this.imgName = name;
        //     this.visible = true;
        // },
        // handleRemove (file) {
        //     const fileList = this.$refs.upload.fileList;
        //     this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
        // },
        // handleSuccess (res, file) {
        //     file.url = 'https://o5wwk8baw.qnssl.com/7eb99afb9d5f317c912f08b5212fd69a/avatar';
        //     file.name = '7eb99afb9d5f317c912f08b5212fd69a';
        // },
        // handleFormatError (file) {
        //     this.$Notice.warning({
        //         title: '图片格式错误',
        //         desc: '图片 ' + file.name + ' 格式错误, 请选择jpg,jpeg,png,gif格式的图片.'
        //     });
        // },
        // handleMaxSize (file) {
        //     this.$Notice.warning({
        //         title: '超过图片大小限制',
        //         desc: '图片' + file.name + '过大, 图片大小不能超过2M.',
        //     });
        // },
        // handleBeforeUpload () {
        //     const check = this.uploadList.length < 2;
        //     if (!check) {
        //         this.$Notice.warning({
        //             title: '只能上传一张图片.'
        //         });
        //     }
        //     return check;
        // }
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
    background: #fff;
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
        line-height: 4.5vmin;
    }
    .formTitle{
        font-size: 2.4vmin;
    }
}
</style>


