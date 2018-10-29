<template>
  <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80" >
    <FormItem label="姓名" prop="name">
      <Input v-model="formValidate.name" placeholder="输入姓名"></Input>
    </FormItem>
    <FormItem label="密码" prop="pwd">
      <Input type="password" v-model="formValidate.pwd" placeholder="输入一个密码"></Input>
    </FormItem>
    <FormItem  label="确认密码" prop="passwdCheck">
      <Input type="password" v-model="formValidate.passwdCheck" placeholder="再次输入密码确认"></Input>
    </FormItem>
    <FormItem label="邮箱" prop="mail">
      <Input v-model="formValidate.mail" placeholder="输入邮箱地址"></Input>
    </FormItem>
    <FormItem label="城市" prop="city">
      <Select v-model="formValidate.city" placeholder="选择你的城市">
        <Option value="beijing">北京</Option>
        <Option value="shanghai">上海</Option>
        <Option value="shenzhen">深圳</Option>
      </Select>
    </FormItem>
    <FormItem label="性别" prop="gender">
      <RadioGroup v-model="formValidate.gender">
        <Radio label="male">男</Radio>
        <Radio label="female">女</Radio>
      </RadioGroup>
    </FormItem>
    <FormItem label="爱好" prop="interest">
      <CheckboxGroup v-model="formValidate.interest">
        <Checkbox label="吃"></Checkbox>
        <Checkbox label="睡"></Checkbox>
        <Checkbox label="跑"></Checkbox>
        <Checkbox label="电影"></Checkbox>
      </CheckboxGroup>
    </FormItem>
    <FormItem label="备注" prop="desc">
      <Input v-model="formValidate.desc" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="留下什么吧......"></Input>
    </FormItem>
    <FormItem>
      <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
      <Button type="ghost" @click="handleReset('formValidate')" style="margin-left: 8px">重置</Button>
    </FormItem>
  </Form>
</template>
<script>
  export default {
    name:"AddUser",
    data () {
      const validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('密码不能为空'));
        } else {
          if (this.formValidate.passwdCheck !== '') {
            // 对第二个密码框单独验证
            this.$refs.formValidate.validateField('passwdCheck');
          }
          callback();
        }
      };
      const validatePassCheck = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入你的密码'));
        } else if (value !== this.formValidate.pwd) {
          callback(new Error('输入2次的密码不匹配!'));
        } else {
          callback();
        }
      };
      return {
        formValidate: {
          name: '',
          pwd:'',
          passwdCheck:'',
          mail: '',
          city: '',
          gender: '',
          interest: [],
          date: '',
          time: '',
          desc: ''
        },
        ruleValidate: {
          name: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          pwd: [
            { validator: validatePass, trigger: 'blur' }
          ],
          passwdCheck: [
            { required: true, message: '密码不能为空', trigger: 'blur' },
            { validator: validatePassCheck, trigger: 'blur' }
          ],
          mail: [
            { required: true, message: '邮箱不能为空', trigger: 'blur' },
            { type: 'email', message: '邮箱格式错误', trigger: 'blur' }
          ],
          city: [
            { required: true, message: '请选择一个城市', trigger: 'change' }
          ],
          gender: [
            { required: true, message: '请选择性别', trigger: 'change' }
          ],
          interest: [
            { required: true, type: 'array', min: 1, message: '至少选择一个爱好', trigger: 'change' },
            { type: 'array', max: 2, message: '最多选择2个', trigger: 'change' }
          ],
          date: [
            { required: true, type: 'date', message: 'Please select the date', trigger: 'change' }
          ],
          time: [
            { required: true, type: 'string', message: 'Please select time', trigger: 'change' }
          ],
          desc: [
            { required: true, message: '请填写一个自我介绍', trigger: 'blur' },
            { type: 'string', min: 10, message: '至少填写10个字描述', trigger: 'blur' }
          ]
        },
      }
    },
    methods: {
      handleSubmit (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.axios.get('/api/user/list').then((response) => {
              console.log(response);
            })
            this.$Message.success('Success!');
          } else {
            this.$Message.error('Fail!');
          }
        })
      },
      handleReset (name) {
        this.$refs[name].resetFields();
      }
    }
  }
</script>
