<template>
    <Form ref="editPassInfo" :model="editPassInfo" :rules="ruleValidate" :label-width="120">
        <h2 class="formTitle">修改密码</h2>
        <FormItem label="旧密码：" prop="oldPw">
            <Input v-model="editPassInfo.oldPw" placeholder="请输入旧密码"></Input>
        </FormItem>
        <FormItem label="新密码：" prop="newPw">
            <Input v-model="editPassInfo.newPw" placeholder="请输入新密码"></Input>
        </FormItem>
        <FormItem label="确认密码：" prop="confirmNewPw">
            <Input v-model="editPassInfo.confirmNewPw" placeholder="请输入确认密码"></Input>
        </FormItem>
        <div style="text-align: center;">
            <Button type="ghost" style="margin-right: 8px" @click="goBack()">返回</Button>
            <Button type="primary" @click="submitPassWord('editPassInfo')">提交</Button>
        </div>
    </Form>  
</template>
<script>
export default {
    data(){
        return{
            editPassInfo:{
                oldPw: null,
                newPw: null,
                confirmNewPw: null
            },
            ruleValidate: {
                oldPw: [
                    { required: true, message: '请输入旧密码', trigger: 'blur' }
                ],
                newPw: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                    { validator: this.checkPwLength, trigger: 'blur' }
                ],
                confirmNewPw: [
                    { required: true, message: '请输入确认密码', trigger: 'blur' },
                    { validator: this.checkPwd, trigger:'blur' }                    
                ]
            }
        }
    },
    methods:{
        //返回
        goBack(){
            this.$router.back(-1);
        },
        checkPwLength(rule,value,callback){
            if(this.editPassInfo.newPw.length<6||this.editPassInfo.newPw.length>18){
                callback( new Error("密码长度为6-18位") )
            }else{
                callback()
            }
        },
        checkPwd(rule, value, callback){
            if(this.editPassInfo.confirmNewPw!=null&&this.editPassInfo.confirmNewPw!=this.editPassInfo.newPw){
                callback( new Error("确认密码与初始密码不一致") )
            }else{
                callback()
            }
        },
        submitPassWord(name){
            var sha256 = require("js-sha256").sha256
            this.$refs[name].validate((valid) => {
                if(valid){
                    var params = {
                        id: sessionStorage.getItem('UMUerId'),
                        oldPw: sha256(this.editPassInfo.oldPw),
                        newPw: sha256(this.editPassInfo.newPw)
                    }
                    this.axios.post('users/pw',params).then(res=>{
                        let{ code,data,msg } = res.data
                        if(code==200){
                            this.$Message.success('修改密码成功!')
                            setTimeout(this.goBack,3000)
                        }else{
                            this.$Message.error(msg)
                        }
                    })
                }else{
                    this.$Message.error('修改密码失败!')
                }
            })
        }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 700px;
    margin: 10px auto;
    background: #fff;
    padding: 10px 20px;
    margin-top: 30px;
    border-radius: 4px;
}
@media (min-width: 2200px){
    .formTitle{
        font-size: 2.8vmin;
    }
    .ivu-form.ivu-form-label-right{
        width: 50%;
        padding: 1vmin 2vmin;
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
}
</style>
