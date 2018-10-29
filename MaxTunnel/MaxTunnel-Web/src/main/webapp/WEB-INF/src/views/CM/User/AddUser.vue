<!--添加用户-->
<template>
  <div>
    <h1 style="text-align: center">添加用户</h1>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100">
      <FormItem label="用户名" prop="name">
        <Input v-model="formValidate.name" placeholder="设置用户名"></Input>
      </FormItem>
      <FormItem label="密码" prop="mail">
        <Input v-model="formValidate.passWord" placeholder="输入密码"></Input>
      </FormItem>
      <FormItem label="确认密码" prop="mail">
        <Input v-model="formValidate.passWord" placeholder="确认密码"></Input>
      </FormItem>
      <FormItem label="角色" prop="role">
        <Select v-model="formValidate.role" multiple style="width:50vw" placeholder="勾选用户角色">
          <Option v-for="item in roleList" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
      </FormItem>
      <FormItem label="Date">
        <Row>
          <Col span="11">
          <FormItem prop="date">
            <DatePicker type="date" placeholder="Select date" v-model="formValidate.date"></DatePicker>
          </FormItem>
          </Col>
          <Col span="2" style="text-align: center">-</Col>
          <Col span="11">
          <FormItem prop="time">
            <TimePicker type="time" placeholder="Select time" v-model="formValidate.time"></TimePicker>
          </FormItem>
          </Col>
        </Row>
      </FormItem>
      <FormItem label="Gender" prop="gender">
        <RadioGroup v-model="formValidate.gender">
          <Radio label="male">Male</Radio>
          <Radio label="female">Female</Radio>
        </RadioGroup>
      </FormItem>
      <FormItem label="Hobby" prop="interest">
        <CheckboxGroup v-model="formValidate.interest">
          <Checkbox label="Eat"></Checkbox>
          <Checkbox label="Sleep"></Checkbox>
          <Checkbox label="Run"></Checkbox>
          <Checkbox label="Movie"></Checkbox>
        </CheckboxGroup>
      </FormItem>
      <FormItem label="Desc" prop="desc">
        <Input v-model="formValidate.desc" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="Enter something..."></Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formValidate')">Submit</Button>
        <Button type="ghost" @click="handleReset('formValidate')" style="margin-left: 8px">Reset</Button>
      </FormItem>
    </Form>
  </div>
</template>

<script>
    export default {
        name: "add-user",
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
            name: '',
            mail: '',
            role: '',
            gender: '',
            interest: [],
            date: '',
            time: '',
            desc: ''
          },
          ruleValidate: {
            name: [
              { required: true, message: '用户名不能为空', trigger: 'blur' }
            ],
            mail: [
              { required: true, message: '密码不能为空', trigger: 'blur' },
              { type: 'email', message: 'Incorrect email format', trigger: 'blur' }
            ],
            role: [
              { required: true, message: '勾选用户角色', trigger: 'change' }
            ],
            gender: [
              { required: true, message: 'Please select gender', trigger: 'change' }
            ],
            interest: [
              { required: true, type: 'array', min: 1, message: 'Choose at least one hobby', trigger: 'change' },
              { type: 'array', max: 2, message: 'Choose two hobbies at best', trigger: 'change' }
            ],
            date: [
              { required: true, type: 'date', message: 'Please select the date', trigger: 'change' }
            ],
            time: [
              { required: true, type: 'string', message: 'Please select time', trigger: 'change' }
            ],
            desc: [
              { required: true, message: 'Please enter a personal introduction', trigger: 'blur' },
              { type: 'string', min: 20, message: 'Introduce no less than 20 words', trigger: 'blur' }
            ]
          }
        }
      },
      methods: {
        handleSubmit (name) {
          this.$refs[name].validate((valid) => {
            if (valid) {
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

<style scoped>

</style>
