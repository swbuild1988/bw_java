<template>
    <div>
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
                <Button type="ghost" style="margin-right: 8px" @click="goBack()">返回 </Button>
                <Button type="primary" @click="submitTask">提交</Button>
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
                // id: 1,
                planName: '',
                startTime: null,
                endTime: null,
                taskPerson: null,
                accountId: 1,
                remark: ''
            },
            liable:[]
        }
    },
    mounted(){
        this.distributeTask.id =  this.$route.params.id;
        let _this = this
        this.getStaff()
    },
    methods:{
        getStaff(){
            var params = {
                    outside: 1
                }
            PatrolService.getStaffs().then(
                (result)=>{
                    this.liable = result
                },
                (error)=>{
                    this.Log.info(error)
                }
            )
        },
        submitTask(){
            let _this = this
            PatrolService.distributeTask(this.distributeTask.id, this.distributeTask.accountId).then(
                result=>{
                    this.distributeTask = result
                    _this.Log.info('success')
                },
                error=>{
                    _this.Log.info(error)
                })
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
        width: 680px;
        margin: 10px auto;
        background: #fff;
        padding: 10px 20px;
    }
    .formBG{
        background: url("../../../../assets/UM/infoBox.png") no-repeat;
        background-size: 100% 100%;
        padding-top: 3vmin;
        padding-bottom: 3vmin;
    }

    .formBG >>> .ivu-form-item-label{
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
        .ivu-form.ivu-form-label-right{
            width: 50%;
        }
        .ivu-form-item >>> .ivu-form-item-label{
            width: 15vmin !important;
            line-height: 2.5vmin;
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
