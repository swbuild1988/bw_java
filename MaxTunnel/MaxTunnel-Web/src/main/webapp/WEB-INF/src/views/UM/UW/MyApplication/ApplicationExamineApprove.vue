<template>
    <div class="formBG">
		<div class="formTitle" v-show="this.$route.params.isFinished==true">入廊申请详细信息</div>
		<div class="formTitle" v-show="this.$route.params.isFinished==false&&this.addEnterGalleryApplication.comment!=null">确认出廊</div>
		<div class="formTitle" v-show="this.$route.params.isFinished==null">确认出廊</div>  
		<div class="formHeight">
        <Form ref="addEnterGalleryApplication" :model="addEnterGalleryApplication" :label-width="140" :rules="ruleValidate" @submit.native.prevent>
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
            <DatePicker type="datetime" placeholder="请选择计划入廊时间" v-model="addEnterGalleryApplication.preTime" readonly style="width: 100%"></DatePicker>
          </FormItem>
          <FormItem label="访客总人数：">
            <InputNumber  :min="1" v-model="addEnterGalleryApplication.visitorNumber" style="width: 100%;"></InputNumber>
          </FormItem>
          <FormItem label="访客所属公司">
            <Input v-model="addEnterGalleryApplication.company.name" readonly></Input>
          </FormItem>
          <FormItem label="访客详细信息：">
            <Table border :columns="columns1" :data="addEnterGalleryApplication.list"></Table>
          </FormItem>
          <FormItem label="备注：">
            <Input class="remark" type="textarea" v-model="addEnterGalleryApplication.comment" readonly></Input>
          </FormItem>
          <FormItem label="审批意见：">
            <!-- <Button type="success"  class="btn" v-show="">同意</Button> -->
            <Button type="success"  class="btn" v-show="this.addEnterGalleryApplication.result=='agree'">同意</Button>
            <Button type="error"  class="btn" v-show="this.addEnterGalleryApplication.result!='agree'">不同意</Button>
          </FormItem>
          <FormItem label="进入管廊时间：" prop="enterTime" v-show="this.addEnterGalleryApplication.result=='agree'">
            <DatePicker type="datetime" placeholder="请选择进入管廊时间" v-model="addEnterGalleryApplication.enterTime" style="width: 560px" :readonly="this.$route.params.isFinished==true"></DatePicker>
          </FormItem>  
          <FormItem label="出廊时间："  prop="exitTime" v-show="this.addEnterGalleryApplication.result=='agree'">
            <DatePicker type="datetime" placeholder="请选择出廊时间" v-model="addEnterGalleryApplication.exitTime" style="width: 560px" :readonly="this.$route.params.isFinished==true"></DatePicker>
          </FormItem>
          <FormItem style="text-align: center;margin-left: -140px" v-show="this.$route.params.isFinished==false&&this.addEnterGalleryApplication.result=='agree'">
            <Button type="gost" style="margin-right: 8px;" @click="goBack()">返回</Button>
            <Button type="primary"  @click="submitExitTime('addEnterGalleryApplication')">提交</Button>
          </FormItem>
          <FormItem style="text-align: center;margin-left: -140px" v-show="this.$route.params.isFinished==null">
            <Button type="gost" style="margin-right: 8px;" @click="goBack()">返回</Button>
            <Button type="primary"  @click="submitExitTime('addEnterGalleryApplication')">提交</Button>
          </FormItem>
          <FormItem v-show="this.$route.params.isFinished==true" style="text-align: center;margin-left: -140px">
            <Button type="gost" @click="goBack()">返回</Button>
          </FormItem>   
        </Form>
		</div>
    </div>  
</template>
<script>
import types from "../../../../../static/Enum.json";
import { EnterGalleryService } from '../../../../services/enterGalleryService';
export default {
	data() {
		return {
			addEnterGalleryApplication: {
				id: null,
				staff: {
					name: null
				},
				tunnel: {
					name: null
				},
				actionName: null,
				preTime: null,
				enterTime: null,
				visitorNumber: null,
				company: {
					name: null
				},
				positionId: null,
				comment: null,
				processInstanceId: null,
				statusName: null,
				exitTime: null,
				processStatus: null,
				processType: null,
				result: null
			},
			columns1: [
				{
					title: "姓名",
					key: "name",
					align: "center"
				},
				{
					title: "身份证号",
					key: "identityNO",
					align: "center"
				},
				{
					title: "联系方式",
					key: "telphone",
					align: "center"
				}
			],
			ruleValidate: {
				enterTime: [
					{ required: true, type: "date", message: "请选择进入管廊时间", trigger: "change" }
				],
				exitTime: [
					{ required: true, type: "date", message: "请选择出廊时间", trigger: "change" },
					{ validator: this.checkEndTime, trigger: 'change' }
				]
			},
			examineStatus: 1,
			examine: types.examineStatus,
			agree: null
		};
	},
	computed: {
		params() {
			let param = {
				id: this.addEnterGalleryApplication.id,
				processStatus: this.addEnterGalleryApplication.processStatus,
				enterTime: this.addEnterGalleryApplication.enterTime,
				exitTime: this.addEnterGalleryApplication.exitTime,
				processType: this.addEnterGalleryApplication.processType
			};
			return Object.assign({}, param);
		}
	},
	mounted() {
		//用户-查看审批结果
		this.examineStatus = this.$route.params.type;
		let _this = this
		EnterGalleryService.getDetailsById(this.$route.params.id).then(
		result=>{
			_this.addEnterGalleryApplication = result;
			if(result.enterTime!=null&&result.exitTime!=null){
				_this.addEnterGalleryApplication.enterTime = new Date(result.enterTime).format('yyyy-MM-dd hh:mm:s')
				_this.addEnterGalleryApplication.exitTime = new Date(result.exitTime).format('yyyy-MM-dd hh:mm:s')
			}
			_this.getAgree()
			_this.addEnterGalleryApplication.preTime = new Date(
				_this.addEnterGalleryApplication.preTime
			).format("yyyy-MM-dd hh:mm:s");
		})
	},
	methods: {
		checkEndTime(rule, value, callback){
			if(this.addEnterGalleryApplication.enterTime>this.addEnterGalleryApplication.exitTime){
				callback( new Error("出廊时间不能大于进入管廊时间") )
			}else if(this.addEnterGalleryApplication.enterTime.getTime()===this.addEnterGalleryApplication.exitTime.getTime()){
				callback( new Error("进入管廊时间不能等于出廊时间") )
			}else{
				callback()
			}
		},
		submitExitTime(name) {
			this.$refs[name].validate(valid => {
				if (valid) {
					let _this = this
					EnterGalleryService.putExitTime(this.params).then(
						result=>{
							if(_this.$route.params.isFinished==null){
								_this.$router.push("/UM/myNews/queryMyTask");
							}else{
								_this.$router.push("/UM/myTasks/query");
							}
						},
						error=>{
							_this.Log.info(error)
					})
				} else {
					this.$Message.error("请输入正确的申请信息");
				}
			});
		},
		getAgree(){
			if(this.$route.params.isFinished==false&&this.addEnterGalleryApplication.exitTime==null||this.$route.params.isFinished==true&&this.addEnterGalleryApplication.exitTime!=null){
				this.agree=1
			}else{
				this.agree=2
			}
		},
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
.btn {
  cursor: text;
}
.formBG{
    background: url("../../../../assets/UM/itemPageBg.png") no-repeat;
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
    margin-right: 0.4vmin;
    line-height: 1;
    font-family: SimSun;
    font-size: 1.2vmin;
}
.formTitle{
    font-size: 2.2vmin;
    margin-top: -3vh;
}
.formHeight{
    height: 75vh;
    overflow-y: auto;
}
.formHeight::-webkit-scrollbar{
    width: 4px;
    height: 4px;
}
.formHeight::-webkit-scrollbar-thumb{
    border-radius: 5px;
    -webkit-box-shadow: inset 0 0 5px rgba(228, 198, 198, 0.2);
    background: rgba(0, 0, 0, 0.2)
}
.formHeight::-webkit-scrollbar-track{
    border-radius: 0;
    -webkit-box-shadow: inset 0 0 5px rgba(221, 208, 208, 0.2);
    background: rgba(0, 0, 0, 0.1)
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
    .remark textarea{
        height: 5.5vmin !important;
        min-height: 5.5vmin !important;
    }
    button{
        width: 8vmin;
    }
    .ivu-input-number,.ivu-input-number >>> .ivu-input-number-input{
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    input[type='number']{
        height: 4vmin;
        width: 8vmin;
        font-size: 1.6vmin;
    }
    .ivu-input-number-input-wrap{
        height: 3.2vmin;
    }
}
</style>

