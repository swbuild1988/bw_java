<template>
    <div :style="backStyle">
        <Form :model="overhaulData" :label-width="140">
            <h2 class="formTitle" v-show="this.pageType==4">提交维修结果</h2>
            <h2 class="formTitle" v-show="this.pageType==1">维修工单详情</h2>
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
            <FormItem label="维修开始时间：">
                <DatePicker type="datetime" v-model="submitOverhaulDate.startTime" placeholder="请输入检修时间" style="width: 100%" :readonly="this.pageType==1||this.typeKey=='complete'"></DatePicker>
            </FormItem>
            <FormItem label="维修结束时间：">
                <DatePicker type="datetime" v-model="submitOverhaulDate.endTime" placeholder="请输入检修时间" style="width: 100%" :readonly="this.pageType==1||this.typeKey=='complete'"></DatePicker>
            </FormItem>
            <FormItem label="维修描述：">
                <Input v-model="submitOverhaulDate.describe" type="textarea" :rows="4" placeholder="请输入检修描述" :readonly="this.pageType==1||this.typeKey=='complete'"></Input>
            </FormItem>
            <FormItem style="text-align: center" v-show="this.pageType==4">
                <Button type="primary" @click="submitOrder()" :disabled="isDisable">提交</Button>
                <Button type="ghost" style="margin-left: 8px">取消 </Button>
            </FormItem>
        </Form>    
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
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                position: 'relative',
                paddingTop: '30px',
                paddingBottom: '20px',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%'
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
        submitOrder(){ 
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
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
            },2000)
        },
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 700px;
    margin: 0px auto;
    background: #fff;
    padding: 10px 20px;
}
</style>


