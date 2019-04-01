<template>
    <div :style="backStyle">
        <Form :model="distributeTask" :label-width="100">
            <h2 class="formTitle">分配巡检任务</h2>
            <FormItem label="所属计划：">
                <Input type="text" v-model="distributeTask.planName"></Input>
            </FormItem>
            <FormItem label="预计开始时间：">
                <DatePicker type="datetime" v-model="distributeTask.startTime" placeholder="请输入预计开始时间" style="width: 100%"></DatePicker>
            </FormItem>
            <FormItem label="预计结束时间：">
                <DatePicker type="datetime" v-model="distributeTask.endTime" placeholder="预计结束时间" style="width: 100%"></DatePicker>
            </FormItem>
            <FormItem label="责任人：">
                <Select v-model="distributeTask.accountId">
                    <Option v-for="(item,index) in liable" :value="item.accountId" :key="index">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="备注：">
                <Input v-model="distributeTask.remark" type="textarea" :rows="4" placeholder="请输入备注"></Input>
            </FormItem>
            <FormItem style="text-align: center;">
                <Button type="primary" @click="submitTask">提交</Button>
                <Button type="ghost" style="margin-left: 8px">取消 </Button>
            </FormItem>
        </Form>  
    </div>
</template>
<script>
import { PatrolService } from '../../../../services/patrolService'
export default {
    data(){
        return{
            distributeTask:{
                id: 1,
                planName: '',
                startTime: null,
                endTime: null,
                taskPerson: null,
                accountId: 1,
                remark: ''
            },
            liable:[],
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                position: 'relative',
                paddingTop: '20px',
                paddingBottom: '20px',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%'
            }
        }
    },
    mounted(){
        this.distributeTask.id =  this.$route.params.id;
        let _this = this
        this.getStaff()
        // this.axios.get('/staffs').then(response=>{
            //     let{ code,data } = response.data
        //     if(code==200){
            //         this.liable = data
        //     }
        // })
    },
    methods:{
        getStaff(){
            var params = {
                    outside: 1
                }
            PatrolService.getStaffs().then(
                (result)=>{
                    _this.liable = result
                },
                (error)=>{
                    _this.Log.info(error)
                }
            )
        },
        submitTask(){
            let _this = this
            PatrolService.distributeTask().then(
                (result)=>{
                    _this.Log.info('success')
                },
                (error)=>{
                    _this.Log.info(error)
                })
            // this.axios.get('maintenance-order/' + this.distributeTask.id + '/maintenance-person/' + this.distributeTask.accountId).then(response=>{
            // });

        }
    }
}
</script>
<style scoped>
    .ivu-form.ivu-form-label-right{
        width: 680px;
        margin: 10px auto;
        background: #fff;
        padding: 10px 20px;
    }
    @media (min-width: 2200px){
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
        .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
        .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
        {
            height: 4vmin;
            line-height: 4vmin;
            font-size: 1.4vmin;
        }
        .formTitle{
            font-size: 2.5vmin;
        }
        textarea.ivu-input{
            height: 5.5vmin !important;
            min-height: 5.5vmin !important;
        }
    }
</style>
