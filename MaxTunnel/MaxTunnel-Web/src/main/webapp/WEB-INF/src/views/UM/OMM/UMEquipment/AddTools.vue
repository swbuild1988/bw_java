<template>
    <div :style="backStyle">
        <Form ref="tools" :model="tools" :rules="validateRules" :label-width="140" @submit.native.prevent>
            <h1 class="formTitle">批量添加仪表工具</h1>
            <FormItem label="仪表工具名称：" prop="name">
                <Input v-model='tools.name'></Input>
            </FormItem>
            <FormItem label="仪表工具类型：" prop="typeId">
                <Select v-model="tools.typeId">
                    <Option v-for="item in toolsType" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="仪表工具型号：" prop="modelId">
                <Select v-model="tools.modelId">
                    <Option v-for="item in toolsModel" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="使用状态：" prop="useStatus">
                <Select v-model="tools.useStatus">
                    <Option v-for="item in usingState" :key="item.key" :value="item.key">{{item.val}}</Option>
                </Select>
            </FormItem>
            <FormItem label="添加数量：">
                <InputNumber v-model="tools.count" :min="1" style="width: 520px;"></InputNumber>
            </FormItem>
            <FormItem label="供应商：" prop="venderId">
                <Select v-model="tools.venderId">
                    <Option v-for="item in venders" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="入库时间：" prop="inTime">
                <DatePicker type="datetime" v-model="tools.inTime" placeholder="请输入入库时间" style="width: 100%"></DatePicker>
            </FormItem> 
            <div style="text-align: center">
                <Button type="primary" style="margin-right: 10px;" @click="addToolsSubmit('tools')" :disabled="isDisable">提交</Button>
                <Button type="default" @click="handleReset('tools')">取消</Button>
            </div>
        </Form> 
        <Icon class="goBack" type="chevron-left" size="30" @click="goBack()" title="返回" color="#fff"></Icon>   
    </div>
</template>
<script>
import { EquipmentService } from "../../../../services/equipmentService";
export default {
    data(){
        return{
            tools:{
                name: null,
                typeId: null,
                modelId: null,
                useStatus: 1,
                count: 1,
                inTime: null,
                venderId: null,
                //库存状态 1:在库 0：出库
                status: 1
            },
            toolsType:[
                { id: 1, name: '试电笔' },{ id: 2, name: '电工刀' },{ id: 3, name: '螺丝刀' },{ id: 7, name: '扳手' },
                { id: 4, name: '电烙铁' },{ id: 5, name: '信号发射器' },{ id: 6, name: '钢丝钳' }
            ],
            usingState: [
                { val: '正常', key: 1 },
                { val: '损坏', key: 0}
            ],
            toolsModel:[],
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                position: 'relative',
                paddingTop: '20px',
                paddingBottom: '20px',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%'
            },
            validateRules: {
                name: [
                    { required: true, message: '请输入仪表工具名称', trigger: 'blur'  }
                ],
                typeId: [
                    { type: 'number', required: true, message: '请选择仪表工具类型', trigger: 'change' }
                ],
                modelId: [
                    { type: 'number', required: true, message: '请选择仪表工具型号', trigger: 'change' }
                ],
                inTime: [
                    { type: 'date', required: true, message: '请选择入库时间', trigger: 'change'}
                ],
                venderId: [
                    { type: 'number', required: true, message: '请选择供应商', trigger: 'change' }
                ]
            },
            isDisable: false,
            venders: []
        }
    },
    mounted(){
        //获取type
        EquipmentService.getEquipmentTypes().then(
            res=>{
                this.toolsType = res
            },
            error => {
                this.Log.info(error);
            },
        );
        //获取model
        EquipmentService.getEquipmentModels().then(
            res=>{
                this.toolsModel = res
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
    },
    methods:{
        addToolsSubmit(name){
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                this.$refs[name].validate((valid)=>{
                    if(valid){
                        EquipmentService.addTools(this.tools.count,this.tools).then(
                            result => {
                                this.$router.push("/UM/equipment/querytool");
                            },
                            error => {
                                this.Log.info(error)
                            }
                        )
                    }
                })
            },2000)
        },
        //取消
        handleReset(name){
            this.$refs[name].resetFields()
        },
        //返回
        goBack(){
            this.$router.back(-1);
        }
    }
}
</script>
<style scoped>
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
</style>

