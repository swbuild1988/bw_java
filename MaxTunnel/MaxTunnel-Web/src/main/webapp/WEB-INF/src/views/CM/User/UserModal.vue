<!--添加或者修改用户通用的弹出框组件-->
<template>
  <div>
    <Modal v-model="show.state" :title="title">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="90">
        <FormItem label="用户名" prop="username">
          <Input v-model="formValidate.username" placeholder="请输入用户名"></Input>
        </FormItem>
        <FormItem label="密码" prop="password">
          <Input v-model="formValidate.password" placeholder="请输入密码"></Input>
        </FormItem>
        <FormItem label="确认密码" prop="_password">
          <Input v-model="formValidate._password" placeholder="请输入确认密码" @on-blur="checkPwd()"></Input>
          <div class="ivu-form-item-error-tip"  v-show="validatePwdCheck">确认密码与初始密码不一致</div>
        </FormItem>
        <FormItem label="年龄" prop="age">
          <Input v-model="formValidate.age" placeholder="请输入年龄"></Input>
        </FormItem>
        <FormItem label="性别" prop="gender">
          <RadioGroup v-model="formValidate.gender">
            <Radio label="male">男</Radio>
            <Radio label="female">女</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="真实姓名" prop="realName">
          <Input v-model="formValidate.realName" placeholder="请输入真实姓名"></Input>
        </FormItem>
        <FormItem label="联系方式" prop="phone">
          <Input v-model="formValidate.phone" placeholder="请输入联系方式"></Input>
        </FormItem>
        <FormItem label="角色" prop="role">
          <Select v-model="formValidate.role">
            <Option v-for="item in roleList" :value="item.label" :key="item.value">{{ item.label }}</Option>
          </Select>
        </FormItem>
      </Form>
      <div slot="footer">
          <Button type="primary" size="large" @click="modalOk('formValidate')">保存</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
    export default {
      name: "add-user",
      props:{
          title:'',
          condition:{
            default: -2           //判断是要进行什么操作
          },
          show:{
            type:Object,         //控制是否弹出
          },
          userInfo:{
            username:'',
            password:'',
            _password:'',           //确认密码
            age:'',
            gender:'',
            realName:'',
            phone:'',
            role:''                //权限
          }
      },
      data () {
        return {
          roleList: [
            {
              value: '1',
              label: '系统管理员'
            },
            {
              value: '2',
              label: '普通用户'
            },
            {
              value: '3',
              label: '巡检员工'
            },
            {
              value: '4',
              label: '巡检班主'
            },
            {
              value: '5',
              label: '管廊主任'
            },
            {
              value: '6',
              label: '后台管理员'
            }
          ],
          formValidate: {
            username:'',
            password:'',
            _password:'',           //确认密码
            age:'',
            gender:'',
            realName:'',
            phone:'',
            role:''                //权限
          },
          validatePwdCheck: false,
          ruleValidate: {
            username: [
              { required: true, message: '用户名不能为空', trigger: 'blur' }
            ],
            password: [
              { required: true, message: '请输入密码', trigger: 'blur'}
            ],
            _password: [
              { required: true, message: '确认密码不能为空', trigger: 'blur' }
            ],
            age: [
              { required: true, message: '年龄不能为空', trigger: 'blur' }
            ],
            gender: [
              { required: true, message: '请勾选性别', trigger: 'change' }
            ],
            realName: [
              { required: true, message: '真实姓名不能为空', trigger: 'blur' }
            ],
            phone: [
              { required: true, message: '联系方式不能为空', trigger: 'blur' }
            ],
            role: [
              { required: true, message: '勾选用户角色', trigger: 'change' }
            ]
          }
        }
      },
      mounted(){
        this.formValidate._password = this.formValidate.password;
        // this.handleReset('formValidate');
      },
      watch: {
        condition: function(){
          // -1是添加新用户，index是修改现有用户
          if(this.condition == -1){
            this.formValidate = '';
            this.handleReset('formValidate');
            this.addNewUser();
          }else if(this.condition >=0){
            this.formValidate = this.userInfo;
            this.editOldUser();
          }
        }
      },
      methods: {
        modalOk (name) {
          this.$refs[name].validate((valid) => {
            if (valid&&this.validatePwdCheck==false) {
              this.$Message.success('成功!');
              this.show.state = false
            } else {
              this.$Message.error('失败!');
            }
          })
        },
        addNewUser(){
          console.log("111");
          //返回父组件，将数据存入
        },
        editOldUser(){
          console.log(this.formValidate);
          //返回父组件，替换原有数据
        },
        handleReset (name) {
          this.$refs[name].resetFields();
        },
        checkPwd(){
          if(this.formValidate.password!=null&&this.formValidate.password!=this.formValidate._password){
            this.validatePwdCheck = true
          }else{
            this.validatePwdCheck = false
          }
        }
      }
    }
</script>

<style scoped>

</style>
