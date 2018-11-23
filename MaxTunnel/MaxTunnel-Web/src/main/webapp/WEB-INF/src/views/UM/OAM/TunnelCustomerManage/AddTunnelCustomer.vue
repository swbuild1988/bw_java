<template>
<div :style="backStyle">
    <Form ref="addCustomerInfo" :model="addCustomerInfo"  :rules="ruleValidate" :label-width="120">
        <h2 class="formTitle" v-show="pageType!=pageTypes.Edit">添加客户信息</h2>
        <h2 class="formTitle" v-show="pageType==pageTypes.Edit">修改客户信息</h2>
        <FormItem label="所属公司：" prop="companyId">
            <Select v-model="addCustomerInfo.companyId">
                <Option v-for="item in company" :value="item.id" :key="item.id">{{ item.name }}</Option>
            </Select>
        </FormItem>
        <FormItem label="联系人：" prop="contact">
            <Input v-model="addCustomerInfo.contact" placeholder="请输入联系人姓名"></Input>
        </FormItem>
        <FormItem label="联系方式：" prop="tel">
            <Input  v-model="addCustomerInfo.tel" placeholder="请输入联系方式" type="text"></Input>
        </FormItem>
        <FormItem style="margin-left: 65px;">
            <Button type="primary" @click="submitAddCustomerInfo('addCustomerInfo')" v-show="pageType!=pageTypes.Edit" :disabled="isDisable">提交</Button>
            <Button type="primary" @click="submitEditCustomerInfo('addCustomerInfo')" v-show="pageType==pageTypes.Edit" :disabled="isDisable">更新</Button>
            <Button type="ghost"  @click="handleReset('addCustomerInfo')" style="margin-left: 8px">取消</Button>
        </FormItem>
    </Form>
</div>
</template>
<script>
import types from '../../../../../static/Enum.json'
export default {
    data(){
        return{
            pageType: 2,
            pageTypes: types.pageType,
            addCustomerInfo:{
                id: 1,
                companyId: null,
                contact: null,
                tel: null
            },
            company:[],
            editCustomerInfo:[],
            ruleValidate: {
                companyId: [
                    { type: 'number', required: true, message: '请选择所属公司', trigger: 'change' }
                ],
                contact: [
                    { required: true, message: '请填写联系人', trigger: 'blur' }
                ],
                tel: [
                    { required: true, message: '请填写联系方式', trigger: 'blur' },
                    { validator(rule,value,callback,source,options) {
                        var errors = []
                        if(!(/^1(3|4|5|7|8)\d{9}$/).test(value)){
                            callback("请输入正确的联系方式")
                        }
                        callback(errors)
                    }}
                ]
            },
        //   validateTelRules: true,
            isDisable:false,
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                height: '100%',
                position: 'relative',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%',
                paddingTop: '40px'
            }
        }
    },
    mounted(){
        this.axios.get('/companies').then(response=>{
            let{ code,data } = response.data
            if(code==200){
                this.company=data
            }
        });
        this.addCustomerInfo.id = this.$route.params.id;
        this.pageType = this.$route.params.type
        this.getParams()
    },
    methods:{
        submitAddCustomerInfo(name){
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                this.$refs[name].validate((valid)=>{
                  if(valid){
                      this.axios.post("/customers", this.addCustomerInfo).then(response => {
                      })
                        .catch(function(error) {
                          console.log(error);
                        });
                      this.$router.push("/UM/tunnelCustomer/list");
                  }else{
                    this.$Message.error("请填写正确的客户信息")
                  }
                })
            },2000)
        },
        getParams(){
            if(this.pageType==this.pageTypes.Edit){
                this.axios.get('/customers/'+this.addCustomerInfo.id).then(response=>{
                    this.addCustomerInfo = response.data.data
                    this.addCustomerInfo.companyId = response.data.data.company.id
                    this.addCustomerInfo.tel = response.data.data.tel
                })
            }
        },
        submitEditCustomerInfo(name){
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                this.$refs[name].validate((valid)=>{
                if(valid){
                    this.axios.put('customers',this.addCustomerInfo).then(response=>{
                        this.$router.push("/UM/tunnelCustomer/list");
                    })
                      .catch(function(error){
                        console.log(error)
                      })
                }else{
                  this.$Message.error("请填写正确的客户信息")
                }
              })
            },2000)
        },
        handleReset(name){
            this.$refs[name].resetFields()
        },

    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 540px;
    background: #fff;
    padding: 10px 20px;
    border-radius: 4px;
    margin: 0 auto;
}
</style>
