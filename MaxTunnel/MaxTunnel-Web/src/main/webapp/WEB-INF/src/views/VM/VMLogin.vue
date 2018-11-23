<template>
  <div class="main">
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate"class="demo-ruleForm login-container">
      <h3 class="title">{{title}}</h3>
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
        <checkbox v-model="formValidate.checked" checked class="remember">记住密码 </checkbox>
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
import { LoginService } from "../../services/loginService";
export default {
    components: {},
    name: "VMlogin",
    data() {
        let checkName = (rule, val, callback) => {
            if (!val) {
                return callback(new Error("账号不能为空！"));
            }
            callback();
        };
        let vaildatePass = (rule, val, callback) => {
            if (!val) {
                return callback(new Error("请输入密码！"));
            }
            callback();
        };
        return {
            title: "综合管廊统一管理可视化平台",
            loading: false,
            logining: false,
            formValidate: {
                userName: "",
                passWord: "",
                checked: true
            },
            ruleValidate: {
                userName: [{ validator: checkName, trigger: "blur" }],
                passWord: [{ validator: vaildatePass, trigger: "blur" }]
            }
        };
    },
    mounted() {
        this.init();
    },
    methods: {
        //初始化函数
        init() {
            this.getCookie();
        },
        handleSubmit(name) {
            // sessionStorage.setItem('VMUser', JSON.stringify("admin"));
            // sessionStorage.setItem('VMUerName',JSON.stringify("admin"))
            // this.$router.push({ path:'VMmain'});
            let _this = this;
            _this.$refs[name].validate(valid => {
                if (valid) {
                    _this.logining = true;
                    _this.loading = true;
                    _this.formValidate.checked
                        ? _this.setCookie(
                              this.formValidate.userName,
                              this.formValidate.passWord,
                              7
                          )
                        : _this.clearCookie();

                    var loginParams = {
                        name: _this.formValidate.userName,
                        password: _this.formValidate.passWord
                    };
                    LoginService.UmLogin(loginParams).then(
                        result => {
                            _this.logining = false;
                            sessionStorage.setItem(
                                "VMUser",
                                JSON.stringify(result)
                            );
                            _this.$router.push({ path: "VMmain" });
                        },
                        error => {
                            _this.$Message.error(msg);
                            _this.loading = false;
                        }
                    );
                    // _this.axios.post("/login", loginParams).then(result => {
                    //     _this.logining = false;
                    //     let { msg, code, data } = result.data;
                    //     if (code !== "200") {
                    //         _this.$Message.error(msg);
                    //         _this.loading = false;
                    //     } else {
                    //         sessionStorage.setItem(
                    //             "VMUser",
                    //             JSON.stringify(data)
                    //         );
                    //         _this.$router.push({ path: "VMmain" });
                    //     }
                    // });
                } else {
                    _this.$Message.error("error submit !");
                }
            });
        },
        //保存cookie
        setCookie(c_name, c_pwd, exdays) {
            var exdate = new Date(); //获取时间
            exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays); //保存的天数
            //字符串拼接cookie
            window.document.cookie =
                "userName" +
                "=" +
                c_name +
                ";path=/;expires=" +
                exdate.toGMTString();
            window.document.cookie =
                "userPwd" +
                "=" +
                c_pwd +
                ";path=/;expires=" +
                exdate.toGMTString();
        },
        //读取cookie
        getCookie() {
            if (document.cookie.length > 0) {
                var arr = document.cookie.split("; "); //这里显示的格式需要切割一下自己可输出看下
                for (var i = 0; i < arr.length; i++) {
                    var arr2 = arr[i].split("="); //再次切割
                    //判断查找相对应的值
                    if (arr2[0] == "userName") {
                        this.formValidate.userName = arr2[1]; //保存到保存数据的地方
                    } else if (arr2[0] == "userPwd") {
                        this.formValidate.passWord = arr2[1];
                    }
                }
            }
        },
        //清除cookie
        clearCookie() {
            this.setCookie("", "", -1); //修改2值都为空，天数为负1天就好了
        }
    }
};
</script>

<style scoped>
.login-container {
    box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    /*margin: 180px auto;*/
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: rgba(3, 23, 56, 0.4);
    border: 1px solid rgba(166, 218, 241, 1);
    box-shadow: 0 0 4em rgba(60, 166, 234, 0.8);
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    color: rgba(255, 255, 255, 0.8);
}
.main {
    width: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    text-align: center;
    background-image: url("../../assets/VM/login_bg.jpg");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    overflow: hidden;
}
.title {
    font-size: 1.5em;
    margin: 0px auto 20px auto;
    text-align: center;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
        "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
.remember {
    margin: 0px 0px 35px 0px;
    float: left;
}
.main >>> 　.ivu-input-group-append,
.main >>> .ivu-input-group-prepend {
    color: #fff;
    background-color: #3b94f1;
    border: 1px solid #2d8cf0;
}
.ivu-checkbox-wrapper {
}
</style>
