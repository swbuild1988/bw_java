<template>
    <div class="formBG">
        <Form ref="addBackUp" :model="addBackUp" :rules="validateRules" :label-width="120" @submit.native.prevent>
            <h1 class="formTitle">添加备品备件</h1>
            <FormItem label="备品名称：" prop="name">
                <Input type="text" v-model="addBackUp.name"></Input>
            </FormItem>
            <FormItem label="备品所属系统：" prop="typeId">
                <Select v-model="addBackUp.typeId">
                    <Option v-for="item in backUpTypes" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="备品规格型号：" prop="modelId">
                <Select v-model="addBackUp.modelId">
                    <Option v-for="item in backUpModel" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="额定电压：" prop="ratedVoltage">
                <Input v-model="addBackUp.ratedVoltage"></Input>
            </FormItem>
            <FormItem label="量程：" prop="range">
                <Input v-model="addBackUp.range"></Input>
            </FormItem>
            <FormItem label="厂家：" prop="factory">
                <Input v-model="addBackUp.factory"></Input>
            </FormItem>
            <FormItem label="品牌：" prop="brand">
                <Input v-model="addBackUp.brand"></Input>
            </FormItem>
            <FormItem label="供应商：" prop="venderId">
                <Select v-model="addBackUp.venderId">
                    <Option v-for="(item) in venders" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="质保期限：" prop="qaTerm">
                <Input v-model="addBackUp.qaTerm" placeholder="请输入质保期限"></Input>
            </FormItem>
            <FormItem label="备品数量：">
                <InputNumber v-model="addBackUp.count" :min="1" style="width: 100%;"></InputNumber>
            </FormItem>   
            <FormItem label="入库时间：" prop="inTime">
                <DatePicker type="datetime" v-model="addBackUp.inTime" placeholder="请输入入库时间" style="width: 100%"></DatePicker>
            </FormItem> 
            <div style="text-align: center;">
                <Button type="ghost" style="margin-right: 8px" @click="goBack()">返回</Button>
                <Button type="primary" @click="submit('addBackUp')" :disabled="isDisable">提交</Button>
            </div>
        </Form>
    </div>
</template>
<script>
    import { EquipmentService } from "../../../../services/equipmentService";
    export default{
        data(){
            return{
                venders: [],
                addBackUp:{
                    name: null,
                    typeId: null,
                    modelId: null,
                    count: 1,
                    inTime: new Date(),
                    status: true,
                    venderId: null,
                    ratedVoltage: null,
                    range: null,
                    factory: null,
                    brand: null,
                    qaTerm: null
                },
                backUpTypes:[],
                backUpModel:[],
                backStyle:{
                    backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                    position: 'relative',
                    paddingTop: '20px',
                    paddingBottom: '20px',
                    backgroundAttachment: 'fixed',
                    backgroundSize: 'cover',
                    minHeight: '100%'
                },
                isDisable: false,
                validateRules: {
                    name: [
                        { required: true, message: '请输入备品名称', trigger: 'blur'  }
                    ],
                    typeId: [
                        { type: 'number', required: true, message: '请选择备品类型', trigger: 'change' }
                    ],
                    modelId: [
                        { type: 'number', required: true, message: '请选择备品型号', trigger: 'change' }
                    ],
                    inTime: [
                        { type: 'date', required: true, message: '请选择入库时间', trigger: 'change'}
                    ],
                    venderId: [
                        { type: 'number', required: true, message: '请选择供应商', trigger: 'change' }
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
                }
            }
        },
        mounted(){
            //获取type
            EquipmentService.getEquipmentTypes().then(
                res=>{
                    this.backUpTypes = res
                },
                error => {
                    this.Log.info(error);
                },
            );
            //获取model
            EquipmentService.getEquipmentModels().then(
                res=>{
                    this.backUpModel = res
                },
                error => {
                    this.Log.info(error)
                }
            ),
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
            //提交
            submit(name){
                this.isDisable = true
                setTimeout(()=>{
                    this.isDisable = false
                    this.$refs[name].validate((valid) => {
                        if(valid){
                            this.axios.post('spares/'+this.addBackUp.count,this.addBackUp).then(res=>{
                                if(res.data.code==200){
                                    this.$router.push('/UM/equipment/querybackup')
                                }
                            })
                            .catch(function(error){
                                console.log(error)
                            })
                        }else{
                            this.$Message.error("请填写正确的备品备件信息")
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
    .ivu-input-number,.ivu-input-number >>> .ivu-input-number-input{
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
        width: 100%;
    }
}
</style>