<template>
    <div class="formBG">
        <div class="formTitle" v-show="this.pageType==4">提交维修结果</div>
        <div class="formTitle" v-show="this.pageType==1">维修工单详情</div>
        <div class="formHeight">
        <Form ref="submitOverhaulDate" :model="submitOverhaulDate" :label-width="140" :rules="validateOverhaul">
            <FormItem label="所属管廊：">   
                <Input type="text" v-model="overhaulData.tunnelName" readonly></Input>
            </FormItem>
            <FormItem label="所属区段：">
                <Input type="text" v-model="overhaulData.area.name" readonly></Input>
            </FormItem>
            <FormItem label="所属管仓：">
                <Input type="text" v-model="overhaulData.store.name" readonly></Input>
            </FormItem>
            <FormItem label="所属缺陷：">
                <Input type="text" v-model="overhaulData.name" readonly></Input>
            </FormItem>
            <FormItem label="维修进程：">
                <Input type="text" v-model="submitOverhaulDate.processStatus" readonly v-show="false"></Input>
                <image-from-url :url="imageUrl"></image-from-url>
            </FormItem>
            <FormItem label="维修人：">
                <Input type="text" v-model="submitOverhaulDate.workerName" readonly></Input>
            </FormItem>
            <FormItem label="维修开始时间：" prop="startTime">
                <DatePicker type="datetime" v-model="submitOverhaulDate.startTime" placeholder="请输入检修时间" style="width: 100%" :readonly="this.pageType==1||this.typeKey=='complete'"></DatePicker>
            </FormItem>
            <!-- <FormItem label="维修开始时间：" v-show="this.pageType==1||this.typeKey=='complete'">
                <DatePicker type="datetime" v-model="submitOverhaulDate.startTime" placeholder="请输入检修时间" style="width: 100%" readonly></DatePicker>
            </FormItem> -->
            <FormItem label="维修结束时间：" prop="endTime">
                <DatePicker type="datetime" v-model="submitOverhaulDate.endTime" placeholder="请输入检修时间" style="width: 100%" :readonly="this.pageType==1||this.typeKey=='complete'"></DatePicker>
            </FormItem>
            <FormItem label="维修描述：">
                <Input v-model="submitOverhaulDate.describe" type="textarea" :rows="4" placeholder="请输入检修描述" :readonly="this.pageType==1||this.typeKey=='complete'"></Input>
            </FormItem>
            <div v-show="this.pageType==4" style="text-align: center;margin-left: -140px;">
                <Button type="ghost" style="margin-right: 8px" @click="goBack()">返回 </Button>
                <Button type="primary" @click="submitOrder('submitOverhaulDate')" :disabled="isDisable">提交</Button>
            </div>
            <div v-show="this.pageType==1" style="margin-left: 15vmin;text-align: center;">
                <Button type="ghost" @click="goBack()">返回 </Button>
            </div>
        </Form>    
        </div>
    </div>
</template>
<script>
import types from '../../../../../static/Enum.json'
import ImageFromUrl from "../../../../components/Common/ImageFromUrl";
export default {
    components: {
        ImageFromUrl
    },
    data(){
        return {
            imageUrl: '',
            // 页面类型 1：查看 2：编辑 3：新增
            pageType: 1,
            pageTypes: types.pageType,
            overhaulData:{
                id: 1,
                tunnelId: 1,
                area:{
                    id: null,
                    name: null
                },
                store:{
                    id: null,
                    name: null
                },
                name: '凤岭北路电力故障',
            },
            submitOverhaulDate:{
                id: null,
                processStatus: null,
                workerName: null,
                startTime: null,
                endTime: null,
                describe: ''
            },
            tunnel: [],
            typeKey: null,
            isDisable: false,
            tip: null,
            validateOverhaul: {
                startTime: [
                    { type: 'date', required: true, message: '维修开始时间不能为空', trigger: 'change' }
                ],
                endTime: [
                    { type: 'date', required: true, message: '维修结束时间不能为空', trigger: 'change' },
                    { validator: this.checkEndTime, trigger: 'change' }
                ]
            }
        }    
    },
    mounted(){
        this.overhaulData.id = this.$route.params.id;
        this.pageType = this.$route.params.type;
        this.typeKey = this.$route.params.typeKey
        this.axios.get('/tunnels').then(response=>{
            let{code,data} = response.data
            if(code==200){
                this.tunnel = data
            }
        })
        this.tip = this.$route.params.tip
        // this.getSessionUserName()
        //获取缺陷详情
        this.axios.get('/orders/'+this.overhaulData.id+'/defect').then(response=>{
            let{ code,data} = response.data
            if(code=200){
                this.overhaulData = data
            }
        })
        //获取维修工单详情
        this.axios.get('/orders/'+this.overhaulData.id).then(response=>{
            let{ code,data} = response.data
            if(code=200){
                this.submitOverhaulDate = data
                if(data.startTime!=null){
                    this.submitOverhaulDate.startTime = new Date(data.startTime).format('yyyy-MM-dd hh:mm:s')
                }
                if(data.endTime!=null){
                    this.submitOverhaulDate.endTime = new Date(data.endTime).format('yyyy-MM-dd hh:mm:s')
                }
                this.showPic()
            }
        })
    },
    methods: {
        showPic() {
            this.imageUrl = "/maintenance-order/" + this.submitOverhaulDate.id + "/activiti-png";
        },
        checkEndTime(rule, value, callback){
            if(this.submitOverhaulDate.startTime>this.submitOverhaulDate.endTime){
                callback( new Error("巡检结束时间不能大于巡检开始时间") )
            }else if(this.submitOverhaulDate.startTime.getTime()===this.submitOverhaulDate.endTime.getTime()){
                callback( new Error("巡检结束时间不能等于巡检开始时间") )
            }else{
                callback()
            }
        },
        submitOrder(name){ 
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                this.$refs[name].validate((valid) => {
                    if(valid){
                        var formInfo = {
                            id: this.submitOverhaulDate.id,
                            startTime: this.submitOverhaulDate.startTime,
                            endTime: this.submitOverhaulDate.endTime,
                            describe: this.submitOverhaulDate.describe
                        }
                        this.axios.put('/orders',formInfo).then(response=>{
                            if(this.tip=='tasks'){
                                this.$router.push("/UM/myTasks/query");
                            }
                            if(this.tip=="news"){
                                this.$router.push("/UM/myNews/queryMyTask");
                            }
                        })
                        .catch(function(error){
                            console.log(error)
                        })
                    }else{
                        this.$Message.error("请输入正确的巡检结果信息")
                    }
                })
            },2000)
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
    width: 33vw;
    margin: 1vmin auto;
    padding: 1vmin 2vmin;
    margin-top: 3vmin;
    border-radius: 0.4vmin;
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
    width: auto;
    text-align: center;
    margin-left: 40%;
    margin-right: 40%;
    font-size: 2.2vmin;
    margin-top: -3.5vmin;
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
    .ivu-icon-chevron-left{
        font-size: 3vmin;
    }
}
</style>


