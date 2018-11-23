<template>
    <div>
        <Modal v-model="show.state" title="新增流程节点">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="120">
                <FormItem label="监测对象名称" prop="name">
                    <Input v-model="formValidate.name" placeholder="请输入监测对象名称" class="InputWidth"/>
                </FormItem>
                <FormItem label="监测对象ID" :prop="isDisabled ? '' : 'id'">
                    <Input v-model="formValidate.id" placeholder="请输入监测对象ID" class="InputWidth" :disabled="isDisabled" @on-blur="check"/>
                </FormItem>
                <FormItem label="数据类型" prop="datatype">
                    <Select v-model="formValidate.datatypeId" placeholder="请选择数据类型" class="InputWidth" @on-change="getObjTypeList">
                        <Option v-for="item in lists.datatypes" :value="item.val" :key="item.val">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="监测对象类型" prop="objtype">
                    <Select v-model="formValidate.objtypeId" placeholder="请选择监测对象类型" class="InputWidth">
                        <Option v-for="item in lists.objtypes" :value="item.val" :key="item.val">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="管廊位置">
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
                <FormItem label="经度">
                    <Input v-model="formValidate.longitude" placeholder="请输入经度" class="InputWidth"/>
                </FormItem>
                <FormItem label="纬度">
                    <Input v-model="formValidate.latitude" placeholder="请输入纬度" class="InputWidth"/>
                </FormItem>
                <FormItem label="高度" prop="height">
                    <Input v-model="formValidate.height" placeholder="请输入高度" class="InputWidth"/>
                </FormItem>
                <FormItem label="偏移量" prop="deviation">
                    <Input v-model="formValidate.deviation" placeholder="请输入偏移量" class="InputWidth"/>
                </FormItem>
                <FormItem label="描述" prop="description">
                    <Input v-model="formValidate.description" placeholder="请输入新应急结束" class="InputWidth"/>
                </FormItem> 
                <FormItem label="激活">
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
export default {
    name: 'meas-obj-module',
    data(){
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
                    { required: true, message: '监测对象名称不能为空', trigger: 'blur' }
                ],
                id: [
                    { required: true, message: '监测对象ID不能为空', trigger: 'blur' }
                ],
                objtypeId: [
                    { required: true, message: '监测对象类型不能为空', trigger: 'change' }
                ],
                datatypeId: [
                    { required: true, message: '数据类型不能为空', trigger: 'change' }
                ]
            },
            lists: {
                datatypes: [],
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
            idChecked: false
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
            }else {
                for(let item in this.editInfo){
                    for(let info in this.formValidate){
                        if(item === info){
                            this.formValidate[info] = this.editInfo[item]
                        }

                    }
                }
                if(this.formValidate.tunnelId){
                    this.tunnelChanged()
                }
                this.getObjTypeList()
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
            EnumsService.getDataType().then(
                result=>{
                    _this.lists.datatypes = result
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
        },
        getObjTypeList() {
            let list = this.lists.datatypes.find(datatype=>{
                return datatype.val == this.formValidate.datatypeId 
            })
            if(list)
                this.lists.objtypes = list.list
        },
        check(){
            let _this = this
            MeasObjServer.checkObjId(this.formValidate.id).then(
                result=>{
                    _this.idChecked = result
                    if(!_this.idChecked){
                        _this.$Message.error('ID重复，请输入其他ID')
                    }
                })
        }
    }
}
</script>

<style scoped>
.InputWidth{
    width: 85%;
}
</style>
