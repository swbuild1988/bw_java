<template>
  <div class="main">
  <Form ref="formValidate" :model="formValidate" :rules="ruleValidate"class="demo-ruleForm login-container">
    <h3 class="title">综合管廊后台管理系统</h3>
    <FormItem prop="userName">
      <Input type="text" v-model="formValidate.userName" placeholder="用户名">
      <Icon type="ios-person-outline" slot="prepend"></Icon>
      </Input>
    </FormItem>
    <FormItem prop="passWord">
      <Input type="password" v-model="formValidate.passWord" placeholder="密码" @keyup.enter.native="handleSubmit('formValidate')">
      <Icon type="ios-locked-outline" slot="prepend"></Icon>
      </Input>
    </FormItem>
    <FormItem>
      <checkbox v-model="checked" checked class="remember">记住密码 </checkbox>
    </FormItem>
    <FormItem>
      <Button type="primary" style="width:100%;" @click="handleSubmit('formValidate')" :loading="loading">
        <span v-if="!loading">登录</span>
        <span v-else>Loading...</span></Button>
    </FormItem>
  </Form>
  </div>
</template>

<script>
  export default {
    components: {},
    name: "CMlogin",
    data(){
      return {
        checked: true,
        loading: false,
        logining: false,
        formValidate: {
          userName: '',
          passWord:''
        },
        ruleValidate: {
          userName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          passWord: [
            { required: true, message: '密码不能为空', trigger: 'blur' },
          ]
        },
      }},
    methods: {
       handleSubmit (name) {
        sessionStorage.setItem('CMUser', JSON.stringify("admin"));
        sessionStorage.setItem('CMUerName',JSON.stringify("admin"))
        this.$router.push({ path:'CMmain'});
        // let _this=this;
        // this.$refs[name].validate((valid) => {
        //   if (valid) {
        //     this.logining = true;
        //     this.loading=true;
        //     this.checked?this.setCookie(this.formValidate.userName,this.formValidate.passWord,7):this.clearCookie();
        //     var loginParams = {name: _this.formValidate.userName, password: _this.formValidate.passWord};
        //     _this.axios.post('/login',loginParams).then(result => {
        //       _this.logining = false;
        //       let { msg, code, data } = result.data;
        //       //存用户名 例：“admin”
        //       let sessionUserName = result.data.data.name
        //       if (code !== "200") {
        //         _this.$Message.error(msg);
        //         _this.loading=false;
        //       } else {
        //         sessionStorage.setItem('UMUser', JSON.stringify(data));
        //         sessionStorage.setItem('UMUerName',JSON.stringify(sessionUserName))
        //         _this.$router.push({ path:'UMmain'});
        //       }
        //     });
        //   }
        //   else {
        //     this.$Message.error('输入不合法!');
        //   }
        // });

      },
    }
  }
</script>

<style scoped>
  .login-container {
    box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .main {
 /* background-image: url(../assets/loginbg.png);*/
  }
  .title {
    margin: 0px auto 20px auto;
    text-align: center;
    color: #505458;
  }
  .remember {
    margin: 0px 0px 35px 0px;
  }

</style>
