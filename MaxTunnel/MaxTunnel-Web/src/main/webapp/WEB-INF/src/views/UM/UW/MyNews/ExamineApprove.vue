<template>
<div class="formBG">
	<div class="formTitle" v-show="this.$route.params.isFinished!=false">审批入廊申请</div>
	<div class="formTitle" v-show="this.$route.params.isFinished==false">入廊申请审批中</div>
	<Form class="formHeight" :label-width="140" @submit.native.prevent>
		<FormItem label="申请人：">
			<Input v-model="addEnterGalleryApplication.staff.name" readonly></Input>
		</FormItem>
		<FormItem label="管廊：">
			<Input v-model="addEnterGalleryApplication.tunnel.name" readonly></Input>
		</FormItem>
		<FormItem label="入廊目的：">
			<Input v-model="addEnterGalleryApplication.actionName" readonly></Input>
		</FormItem>
		<FormItem label="计划入廊时间：">
			<DatePicker type="datetime" placeholder="请选择计划入廊时间" v-model="addEnterGalleryApplication.preTime" readonly style="width: 560px"></DatePicker>
		</FormItem>
		<FormItem label="访客总人数：">
			<InputNumber  :min="1" v-model="addEnterGalleryApplication.visitorNumber" style="width: 560px;"></InputNumber>
		</FormItem>
		<FormItem label="访客所属公司：">
			<Input v-model="addEnterGalleryApplication.company.name" readonly></Input>
		</FormItem>
			<FormItem label="访客详细信息：">
		<Table border :columns="columns1" :data="addEnterGalleryApplication.list"></Table>
		</FormItem>
		<FormItem label="备注：" v-show="this.$route.params.isFinished!=false">
			<Input type="textarea" v-model="addEnterGalleryApplication.remark" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入审批备注"></Input>
		</FormItem>
		<FormItem label="审批意见：">
			<Button type="success"  @click="agree(1)" v-show="this.$route.params.isFinished!=false">同意</Button>
			<Button type="error" style="margin-left: 8px" @click="agree(0)" v-show="this.$route.params.isFinished!=false">不同意</Button>
			<Button type="info"  class="btn" v-show="this.$route.params.isFinished==false">审核中</Button>	
		</FormItem>
		<FormItem style="text-align: center;margin-left: -14vmin">
			<Button @click="goBack()" type="gost">返回</Button>
		</FormItem>
	</Form>	
</div>
</template>
<script>
import axios from "axios";
export default {
    data(){
		return{
			addEnterGalleryApplication:{
				staff:{
					name: null
				},
				tunnel:{
					name: null            
				},
				action: null,
				actionName: null,
				preTime:  null,
				enterTime: null,
				visitorNumber: null,
				company: {
					name: null
				},
				positionId: null,
				remark: '',
				processInstanceId: null
			},
			columns1: [
				{
					title: '姓名',
					key: 'name',
					align: 'center'
				},
				{
					title: '身份证号',
					key: 'identityNO',
					align: 'center'
				},
				{
					title: '联系方式',
					key: 'telphone',
					align: 'center'
				}
			],
		}
    },
    mounted(){
      	//管理员审批-入廊
        axios.get("/req-historys/" + this.$route.params.id).then(response => {
          let{ code,data } = response.data
            if(code=200){
                this.addEnterGalleryApplication  = data
                this.addEnterGalleryApplication.preTime = new Date(this.addEnterGalleryApplication.preTime).format('yyyy-MM-dd hh:mm:s')
                this.addEnterGalleryApplication.enterTime = new Date(this.addEnterGalleryApplication.enterTime).format('yyyy-MM-dd hh:mm:s') 
            }
        })
    },
    methods:{
		agree(num){
			axios.post('users/activiti/req-historys/audit',{
				remark: this.addEnterGalleryApplication.remark,
				processInstanceId: this.addEnterGalleryApplication.processInstanceId,
				value: num
			}).then(response=>{
			let{ code,data } = response.data
			if(code==200){
					if(this.$route.params.isFinished==null){
						this.$router.push("/UM/myNews/queryMyTask");
					}else{
						this.$router.push("/UM/myTasks/query");
					}
				}
			}
		)},
		//返回
		goBack(){
			this.$router.back(-1);
		}	
    }
};
</script>
<style scoped>
.ivu-form.ivu-form-label-right {
    width: 740px;
    margin: 20px auto;
    padding: 20px;
}
ul li {
    list-style: none;
}
button {
    width: 80px;
}
.goBack{
    position: absolute;
    bottom: 2vh;
    right: 3vw;
}
.formBG{
    background: url("../../../../assets/UM/itemPageBg.png") no-repeat;
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
	margin-right: 0.4vmin;
	line-height: 1;
	font-family: SimSun;
	font-size: 1.2vmin;
}
.formTitle{
	font-size: 2.2vmin;
	color: #fff;
	margin-top: -3vmin;
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
    textarea.ivu-input{
        height: 5.5vmin !important;
        min-height: 5.5vmin !important;
    }
}
</style>

