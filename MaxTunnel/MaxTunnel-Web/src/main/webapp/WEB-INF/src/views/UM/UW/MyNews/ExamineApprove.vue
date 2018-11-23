<template>
<div :style="backStyle">
  <div class="backImgStyle">
  <Form :label-width="140" @submit.native.prevent>
    <h2 class="formTitle" v-show="this.$route.params.isFinished!=false">审批入廊申请</h2>
    <h2 class="formTitle" v-show="this.$route.params.isFinished==false">入廊申请审批中</h2>
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
    <!-- <FormItem label="进入管廊时间：">
      <DatePicker type="datetime" placeholder="请选择进入管廊时间" v-model="addEnterGalleryApplication.enterTime" readonly style="width: 560px"></DatePicker>
    </FormItem> -->
    <FormItem label="访客总人数：">
      <InputNumber  :min="1" v-model="addEnterGalleryApplication.visitorNumber" style="width: 560px;"></InputNumber>
    </FormItem>
    <FormItem label="访客所属公司：">
      <Input v-model="addEnterGalleryApplication.company.name" readonly></Input>
    </FormItem>
    <FormItem label="访客详细信息：">
      <Table border :columns="columns1" :data="addEnterGalleryApplication.visitorInfo"></Table>
    </FormItem>
    <FormItem label="备注：" v-show="this.$route.params.isFinished!=false">
      <Input type="textarea" v-model="addEnterGalleryApplication.remark" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入审批备注"></Input>
    </FormItem>
    <FormItem label="审批意见：">
      <Button type="success"  @click="agree(1)" v-show="this.$route.params.isFinished!=false">同意</Button>
      <Button type="error" style="margin-left: 8px" @click="agree(0)" v-show="this.$route.params.isFinished!=false">不同意</Button>
      <Button type="info"  class="btn" v-show="this.$route.params.isFinished==false">审核中</Button>
    </FormItem>
  </Form>
  </div>
</div>
</template>
<script>
  import axios from "axios";
  export default {
    data(){
      return{
        backStyle:{
            backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
            position: 'relative',
            paddingTop: '20px',
            paddingBottom: '20px',
            backgroundAttachment: 'fixed',
            backgroundSize: 'cover',
            minHeight: '100%'
        },
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
          visitorInfo:[] ,
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
            width: 200,
            key: 'idCard',
            align: 'center'
          },
          {
            title: '联系方式',
            key: 'tel',
            align: 'center'
          }
        ],
      }
    },
    mounted(){
      //管理员审批-入廊
      console.log("this.$route.params",this.$route.params)
      // axios.get('/users/activiti/task/detail/'+this.$route.params.processInstanceId).then(response=>{
        axios.get("/req-historys/" + this.$route.params.id).then(response => {
          let{ code,data } = response.data
            if(code=200){
                this.addEnterGalleryApplication  = data
                var arr = new Array()
                for(let index in response.data.data.visitorInfo.split(',')){
                    var str = response.data.data.visitorInfo.split(',')[index]
                    arr.push(str)
                }
                var arr2 = new Array()
                for(let k in arr){
                  var obj = {
                    name: arr[k].split('-')[0],
                    idCard: arr[k].split('-')[1],
                    tel: arr[k].split("-")[2]
                  }
                  arr2.push(obj)
                }
                this.addEnterGalleryApplication.visitorInfo = arr2
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
        })
      },
    }
};
</script>
<style scoped>
.ivu-form.ivu-form-label-right {
    width: 740px;
    margin: 20px auto;
    background: #fff;
    padding: 20px;
}
ul li {
    list-style: none;
}
button {
    width: 80px;
}
</style>

