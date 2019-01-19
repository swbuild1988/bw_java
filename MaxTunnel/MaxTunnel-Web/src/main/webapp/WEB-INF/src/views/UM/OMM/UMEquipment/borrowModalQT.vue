<template>
    <Modal
        v-model="isBorrow"
        title="借出信息登记"
    >
        <Form ref="borrow" :model="borrow" :rules="borrowRules" :label-width="140">
            <FormItem label="借用人：" prop="staffId">
                <Select v-model="borrow.staffId" style="width: 70%">
                    <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>                   
            </FormItem>
            <FormItem label="借用时间：" prop="borrowTime">
                <DatePicker type="datetime"  placeholder="请输入取用时间" style="width: 70%" v-model="borrow.borrowTime"></DatePicker>                  
            </FormItem>  
            <FormItem label="借用备注：">
                <Input type="textarea" v-model="borrow.Describe" style="width: 70%"></Input>
            </FormItem>  
        </Form>
        <div slot="footer">
            <Button type="default">取消</Button>
            <Button type="primary" @click="confirmBorrow('borrow')" :disabled="confirmBorrowBtn">确定</Button>
        </div>
    </Modal>
</template>
<script>
import { EquipmentService } from "../../../../services/equipmentService";
export default {
    props: {
        isBorrow: false,
        isBorrowId: null,
        onsubmit: {
            type: Function,
            default: null
        }
    },
    data(){
        return{
            confirmBorrowBtn: false,
            isBorrowModal: false,
            borrow:{
                staffId: null,
                borrowTime: null,
                Describe: null
            },
            borrowRules: {
                staffId: [
                    { type: 'number', required: true, message: '请选择借用人', trigger: 'change' }
                ],
                borrowTime: [
                    { type: 'date', required: true, message: '请选择借出时间', trigger: 'change' }
                ]
            },
            staffs: []
        }
    },
    created(){
        console.log(this.isBorrow,"this.isBorrow")
        this.isBorrowModal = this.isBorrow
    },
    mounted(){
        //获取借用人列表
        this.axios.get('staffs').then(res=>{
            let{ code,data } = res.data
            if(code==200){
                this.staffs = data
            }
        })
    },
    methods:{
        confirmBorrow(name){
            this.confirmBorrowBtn = true
            setTimeout(()=>{
                this.confirmBorrowBtn = false
                this.$refs[name].validate((valid) => {
                    if(valid){
                        let lendParams={
                            ids: this.isBorrowId,
                            staffId: this.borrow.staffId,
                            borrowTime: this.borrow.borrowTime,
                            Describe: this.borrow.describe
                        }
                        EquipmentService.batchLend(this.isBorrowId,lendParams).then(
                            result => {
                                // this.isBorrow = false
                                if(this.onsubmit){
                                    this.onsubmit()
                                    this.isBorrowModal = false
                                }
                            },
                            error => {
                                this.Log.info(error)
                            }
                        )
                    }else{
                        this.$Message.error("请填写正确的借出信息")
                    }
                })
            },2000)
        }
    }
}
</script>
<style>

</style>

