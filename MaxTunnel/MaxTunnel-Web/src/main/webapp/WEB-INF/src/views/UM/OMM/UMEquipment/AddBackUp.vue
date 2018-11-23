<template>
    <div :style="backStyle">
        <Form ref="addBackUp" :model="addBackUp" :rules="validateRules" :label-width="120" @submit.native.prevent>
            <h1 class="formTitle">添加备品设备</h1>
            <FormItem label="备品名称：" prop="backUpName">
                <Input type="text" v-model="addBackUp.backUpName"></Input>
            </FormItem>
            <FormItem label="备品类型：" prop="backUpType">
                <Select v-model="addBackUp.backUpType">
                    <Option v-for="item in backUpTypes" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="备品型号：" prop="backUpModelId">
                <Select v-model="addBackUp.backUpModelId">
                    <Option v-for="item in backUpModel" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="备品数量：">
                <InputNumber v-model="addBackUp.number" :min="1" style="width: 540px;"></InputNumber>
            </FormItem>   
            <FormItem label="入库时间：" prop="interStoreTime">
                <DatePicker type="datetime" v-model="addBackUp.interStoreTime" placeholder="请输入入库时间" style="width: 100%"></DatePicker>
            </FormItem> 
            <FormItem style="text-align: center;">
                <Button type="primary" @click="submit('addBackUp')" :disabled="isDisable">提交</Button>
                <Button type="ghost" style="margin-left: 8px" @click="handleReset('addBackUp')">取消 </Button>
            </FormItem>
        </Form>
    </div>
</template>
<script>
    export default{
        data(){
            return{
                addBackUp:{
                    backUpName: null,
                    backUpType: null,
                    backUpModalId: null,
                    number: 1,
                    interStoreTime: null
                },
                backUpTypes:[
                    { id: 1,name: '消防设备' },
                    { id: 2,name: '通讯设备' },
                    { id: 3,name: '万能表' },
                    { id: 4,name: '螺丝刀' },
                    { id: 5,name: '电脑' },
                    { id: 6,name: '有害气体检测器' },
                ],
                backUpModel:[
                    { id: 1, name: '001' },
                    { id: 2, name: '002' },
                    { id: 3, name: '003' },
                    { id: 4, name: '004' }
                ],
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
                    backUpName: [
                        { required: true, message: '请输入备品名称', trigger: 'blur'  }
                    ],
                    backUpType: [
                        { type: 'number', required: true, message: '请选择备品类型', trigger: 'change' }
                    ],
                    backUpModelId: [
                        { type: 'number', required: true, message: '请选择备品型号', trigger: 'change' }
                    ],
                    interStoreTime: [
                        { type: 'date', required: true, message: '请选择入库时间', trigger: 'change'}
                    ]
                }
            }
        },
        methods:{
            //提交
            submit(name){
                this.isDisable = true
                setTimeout(()=>{
                    this.isDisable = false
                    this.$refs[name].validate((valid)=>{
                        if(valid){
                            this.$router.push("/UM/equipment/querybackup");
                        }
                    })
                },2000)
            },
            //取消
            handleReset(name){
                this.$refs[name].resetFields()
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
</style>