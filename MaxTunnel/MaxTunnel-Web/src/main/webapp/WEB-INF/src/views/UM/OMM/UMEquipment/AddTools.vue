<template>
    <div class="formBG">
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
                    <Option v-for="item in usingState" :key="item.val" :value="item.val">{{item.key}}</Option>
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
                <Button type="default" @click="goBack()" style="margin-right: 10px;" >返回</Button>
                <Button type="primary" @click="addToolsSubmit('tools')" :disabled="isDisable">提交</Button>
            </div>
        </Form>  
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
                // { val: '正常', key: 1 },
                // { val: '损坏', key: 0}
            ],
            toolsModel:[],
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
        //获取tool的状态
        EquipmentService.getStatus().then(
            res=>{
                this.usingState = res
                console.log(res)
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
        // handleReset(name){
        //     this.$refs[name].resetFields()
        // },
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
    padding: 10px 20px;
    margin-top: 30px;
    border-radius: 4px;
}
.goBack{
    position: absolute;
    bottom: 2vh;
    right: 3vw;
}
.formBG{
    background: url("../../../../assets/UM/infoBox.png") no-repeat;
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
    margin-right: 4px;
    line-height: 1;
    font-family: SimSun;
    font-size: 12px;
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
}
</style>

