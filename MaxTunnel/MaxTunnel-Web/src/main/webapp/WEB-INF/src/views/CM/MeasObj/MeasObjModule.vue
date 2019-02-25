<template>
    <div>
        <Modal v-model="show.state" :title="title" width=600>
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="140">
                <FormItem label="监测对象名称：" prop="name">
                    <Input v-model="formValidate.name" placeholder="请输入监测对象名称" class="InputWidth"/>
                </FormItem>
                <FormItem label="监测对象ID：" :prop="isDisabled ? '' : 'id'">
                    <Input v-model="formValidate.id" placeholder="请输入监测对象ID" class="InputWidth" :disabled="isDisabled"/>
                </FormItem>
                <FormItem label="监测对象类型：" prop="objtype">
                    <Select v-model="formValidate.objtypeId" placeholder="请选择监测对象类型" class="InputWidth">
                        <Option v-for="item in lists.objtypes" :value="item.val" :key="item.val">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="管廊位置：">
                    <Row class="InputWidth">
                        <Col span="8">
                            <Select v-model="formValidate.tunnelId" placeholder="请选择管廊" @on-change="clearStoreAndArea">
                                <Option v-for="item in lists.tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                            </Select>
                        </Col>
                        <Col span="8">
                            <Select v-model="formValidate.storeId" placeholder="请选择管仓">
                                <Option v-for="item in lists.stores" :value="item.id" :key="item.id">{{item.name}}</Option>
                            </Select>
                        </Col>
                        <Col span="8">
                            <Select v-model="formValidate.areaId" placeholder="请选择区域">
                                <Option v-for="item in lists.areas" :value="item.id" :key="item.id">{{item.name}}</Option>
                            </Select>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="经度：" prop="longitude">
                    <Input v-model="formValidate.longitude" placeholder="请输入经度(整数或小数)" class="InputWidth"/>
                </FormItem>
                <FormItem label="纬度：" prop="latitude">
                    <Input v-model="formValidate.latitude" placeholder="请输入纬度(整数或小数)" class="InputWidth"/>
                </FormItem>
                <FormItem label="高度：" prop="height">
                    <Input v-model="formValidate.height" placeholder="请输入高度(m)" class="InputWidth"/>
                </FormItem>
                <FormItem label="偏移量：" prop="deviation">
                    <Input v-model="formValidate.deviation" placeholder="请输入偏移量" class="InputWidth"/>
                </FormItem>
                <FormItem label="描述：" prop="description">
                    <Input type="textarea" v-model="formValidate.description" placeholder="请输入描述" class="InputWidth"/>
                </FormItem> 
                <FormItem label="激活：">
                    <Select v-model="formValidate.actived" placeholder="请选择是否激活" class="InputWidth">
                        <Option v-for="item in isActived" :value="item.val" :key="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import { EnumsService } from '../../../services/enumsService'
import { TunnelService } from '../../../services/tunnelService'
import { MeasObjServer } from '../../../services/MeasObjectSerivers'
export default {
    name: 'meas-obj-module',
    data(){
        const validateNumber = (rule, value, callback) => {
                if (value === '') {
                    // callback(new Error('不能为空'));
                } else {
                    if (isNaN(value)) {
                        callback(new Error('请输入数字'))
                    } else {
                        callback()
                    }  
                }
            };
        const validateInt = (rule,value,callback) => {
            if (value === '') {
                    callback(new Error('不能为空'))
            } else {
                if (isNaN(value) ||  value % 1 !== 0) {
                    callback(new Error('请输入整数'))
                } else {
                    MeasObjServer.checkObjId(value).then(
                        result=>{
                            if(!result){
                                callback(new Error('ID重复，请输入其他ID'))
                            } else {
                                callback()
                            }
                        })
                }  
            }
        }
        const checkString = (rule,value,callback) => {
            if(value === ''){
                callback(new Error('不能为空'))
            } else {
                let regEn = /[`!@#$%^&*()_+<>?:"{},.\/;'[\]]/im
                let regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im
                if(regEn.test(value) || regCn.test(value)) {
                    callback(new Error('输入了非法字符，请重新输入'))
                } else {
                    callback()
                }
            }
        }

        return {
            formValidate:{
                name:null,
                id:null,
                objtypeId:null,
                datatypeId:null,
                longitude:null,
                latitude:null,
                height:null,
                deviation:null,
                description:null,
                actived: null,
                tunnelId:null,
                storeId: null,
                areaId: null
            },
            ruleValidate:{
                name: [
                    { required: true, message: '监测对象名称不能为空', trigger: 'blur' },
                    { validator: checkString, trigger: 'blur' }
                ],
                id: [
                    { required: true, message: '监测对象ID不能为空', trigger: 'blur' },
                    { validator: validateInt, trigger: 'blur' }
                ],
                objtypeId: [
                    { required: true, message: '监测对象类型不能为空', trigger: 'change' }
                ],
                datatypeId: [
                    { required: true, message: '数据类型不能为空', trigger: 'change' }
                ],
                deviation: [
                    { validator: validateNumber, trigger: 'blur' }
                ],
                longitude: [
                    { validator: validateNumber, trigger: 'blur' }
                ],
                latitude: [
                    { validator: validateNumber, trigger: 'blur' }
                ]
            },
            lists: {
                objtypes: [],
                tunnels: [],
                areas: [],
                stores: []
            }, 
            isActived:[
                {   val: 0,key: "否" },
                {   val: 1,key: "是" }
            ],
            isDisabled: false,
            idChecked: false,
            title: '新增监测对象'
        }
    },
    props:{
        show:{
            state:{
                default: false
            }
        },
        editInfo: {
            required: true
        },
        type: {
            required: true
        }
    },
    watch:{
        'show.state':function(newValue,oldValue){
            if(this.type == 'add'){
                this.cleanFormValidate()
                this.formValidate.actived = 0
                this.title = '新增监测对象'
            }else {
                this.title = '编辑监测对象'
                for(let item in this.editInfo){
                    for(let info in this.formValidate){
                        if(item === info){
                            this.formValidate[info] = this.editInfo[item]
                        }
                    }
                }
                this.idChecked = true
                if(this.formValidate.tunnelId){
                    this.tunnelChanged()
                }
                this.isDisabled = !this.isDisabled
            } 
        }
    },
    mounted(){
        this.init()
    },
    methods:{
        init(){      
            let _this = this
            EnumsService.getObjType().then(
                result=>{
                    _this.lists.objtypes = result
                },
                error=>{
                    _this.Log.info(error)
                })  
            TunnelService.getTunnels().then(
                result=>{
                    _this.lists.tunnels = result
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        sendMsg: function(name){
            this.$refs[name].validate((valid) => {
                if (valid && this.idChecked) {
                    this.$emit("addOrEdit",this.formValidate);
                } else {
                    this.$Message.error('操作失败，请稍后重试');
                }
            });
        },
        tunnelChanged() {
            let _this = this
            if(this.formValidate.tunnelId){
                TunnelService.getStoresByTunnelId(this.formValidate.tunnelId).then(
                result=>{
                    _this.lists.stores = result
                    if(_this.editInfo.storeId){
                        _this.formValidate.storeId = _this.editInfo.storeId
                    }
                },
                error=>{
                    _this.Log.info(error)
                })
            TunnelService.getAreasByTunnelId(this.formValidate.tunnelId).then(
                result=>{
                    _this.lists.areas = result
                    if(_this.editInfo.areaId){
                        _this.formValidate.areaId = _this.editInfo.areaId
                    }
                },
                error=>{
                    _this.Log.info(error)
                })
            }
        },
        clearStoreAndArea(){
            if(this.type == 'edit'){
                this.formValidate.areaId = null
                this.formValidate.storeId = null
            } 
            this.tunnelChanged()
            
        },
        cleanFormValidate(){
            for(let item in this.formValidate){
                this.formValidate[item] = null
            }
        }
    }
}
</script>

<style scoped>
.InputWidth{
    width: 85%;
}
</style>
