<template>
    <div :style="backStyle">
        <Form ref="addEnterGalleryApplication" :model="addEnterGalleryApplication" :label-width="140" :rules="ruleValidate" @submit.native.prevent>
          <h2 class="formTitle" v-show="this.$route.params.isFinished==true">入廊申请详细信息</h2>
          <h2 class="formTitle" v-show="this.$route.params.isFinished==false&&this.addEnterGalleryApplication.comment!=null">确认出廊</h2>
          <h2 class="formTitle" v-show="this.$route.params.isFinished==null">确认出廊</h2>  
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
          <FormItem label="访客所属公司">
            <Input v-model="addEnterGalleryApplication.company.name" readonly></Input>
          </FormItem>
          <FormItem label="访客详细信息：">
            <Table border :columns="columns1" :data="addEnterGalleryApplication.visitorInfo"></Table>
          </FormItem>
          <FormItem label="备注：">
            <Input type="textarea" v-model="addEnterGalleryApplication.comment" :autosize="{minRows: 2,maxRows: 5}" readonly></Input>
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
          <FormItem style="text-align: center" v-show="this.$route.params.isFinished==false&&this.addEnterGalleryApplication.result=='agree'">
            <Button type="primary"  @click="submitExitTime('addEnterGalleryApplication')">提交</Button>
            <Button type="default">取消</Button>
          </FormItem>
          <FormItem style="text-align: center" v-show="this.$route.params.isFinished==null">
            <Button type="primary"  @click="submitExitTime('addEnterGalleryApplication')">提交</Button>
            <Button type="default">取消</Button>
          </FormItem>   
        </Form>
    </div>  
</template>
<script>
import types from "../../../../../static/Enum.json";
import { EnterGalleryService } from '../../../../services/enterGalleryService';
export default {
  data() {
    return {
      backStyle:{
        backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
        position: 'relative',
        paddingTop: '20px',
        paddingBottom: '20px',
        backgroundAttachment: 'fixed',
        backgroundSize: 'cover',
        minHeight: '100%'
      },
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
        visitorInfo: [],
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
          key: "idCard",
          align: "center",
          width: 200
        },
        {
          title: "联系方式",
          key: "tel",
          align: "center"
        }
      ],
      ruleValidate: {
        enterTime: [
          {
            required: true,
            type: "date",
            message: "请选择进入管廊时间",
            trigger: "change"
          }
        ],
        exitTime: [
          {
            required: true,
            type: "date",
            message: "请选择出廊时间",
            trigger: "change"
          }
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
          var arr = new Array();
          for (let index in result.visitorInfo.split(",")) {
            var str = result.visitorInfo.split(",")[index];
            arr.push(str);
          }
          var arr2 = new Array();
          for (let k in arr) {
            var obj = {
              name: arr[k].split("-")[0],
              idCard: arr[k].split("-")[1],
              tel: arr[k].split("-")[2]
            };
            arr2.push(obj);
          }
          _this.addEnterGalleryApplication.visitorInfo = arr2;
          _this.addEnterGalleryApplication.preTime = new Date(
            _this.addEnterGalleryApplication.preTime
          ).format("yyyy-MM-dd hh:mm:s");
      })
    // axios.get("/req-historys/" + this.$route.params.id).then(response => {
    //     let { code, data } = response.data;
    //     if ((code = 200)) {
    //       this.addEnterGalleryApplication = data;
    //       if(data.enterTime!=null&&data.exitTime!=null){
    //         this.addEnterGalleryApplication.enterTime = new Date(data.enterTime).format('yyyy-MM-dd hh:mm:s')
    //         this.addEnterGalleryApplication.exitTime = new Date(data.exitTime).format('yyyy-MM-dd hh:mm:s')
    //       }
    //       this.getAgree()
    //       var arr = new Array();
    //       for (let index in response.data.data.visitorInfo.split(",")) {
    //         var str = response.data.data.visitorInfo.split(",")[index];
    //         arr.push(str);
    //       }
    //       var arr2 = new Array();
    //       for (let k in arr) {
    //         var obj = {
    //           name: arr[k].split("-")[0],
    //           idCard: arr[k].split("-")[1],
    //           tel: arr[k].split("-")[2]
    //         };
    //         arr2.push(obj);
    //       }
    //       this.addEnterGalleryApplication.visitorInfo = arr2;
    //       this.addEnterGalleryApplication.preTime = new Date(
    //         this.addEnterGalleryApplication.preTime
    //       ).format("yyyy-MM-dd hh:mm:s");
    //     }
    //   });
  },
  methods: {
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
          // axios
          //   .put("users/out/req-historys", this.params)
          //   .then(response => {
          //     let { code, data } = response.data;
          //     if (code == 200) {
          //       if(this.$route.params.isFinished==null){
          //         this.$router.push("/UM/myNews/queryMyTask");
          //       }else{
          //         this.$router.push("/UM/myTasks/query");
          //       }
          //     }
          //   });
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
    }
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
.btn {
  cursor: text;
}
</style>

