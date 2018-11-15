<template>
    <div class="main">
        <!-- <div style="width : 500px; height : 500px">
        <video-component  id="test" :video="tmpVideo"></video-component>
        <video autoplay webkit-playsinline>      
            <source src="http://localhost/hls/h264.m3u8" type="application/vnd.apple.mpegurl" />      
            <p class="warning">Your browser does not support HTML5 video.</p>   
        </video>
        </div> -->
      <h1 class="form">综合管廊统一管理平台</h1>
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" class="demo-ruleForm login-container">
        <h2 class="title">用户登录</h2>
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
          <checkbox v-model="checked" checked class="remember">一周内记住密码 </checkbox>
        </FormItem>
        <FormItem>
          <Button type="primary" style="width:100%;" @click="handleSubmit('formValidate')" :loading="loading">
            <span v-if="!loading">登录</span>
            <span v-else>Loading...</span></Button>
        </FormItem>
      </Form>
      <LoginBG></LoginBG>
    </div>

</template>

<script>
import LoginBG from "../../components/UM/LoginBackGround.vue";
import VideoComponent from "../../components/Common/Video/VideoComponent";
import { LoginService } from '../../services/loginService'

export default {
    components: {
        LoginBG,
        VideoComponent
    },
    name: "UMlogin",
    data() {
        return {
            tmpVideo: {
                id: 111,
                name: "test"
            },
            checked: false,
            loading: false,
            logining: false,
            formValidate: {
                userName: "",
                passWord: ""
            },
            ruleValidate: {
                userName: [
                    { required: true, message: "姓名不能为空", trigger: "blur" }
                ],
                passWord: [
                    { required: true, message: "密码不能为空", trigger: "blur" }
                ]
            }
        };
    },
    methods: {
        handleSubmit(name) {
            let _this = this;
            this.$refs[name].validate(valid => {
                if (valid) {
                    this.logining = true;
                    this.loading = true;
                    this.checked
                        ? this.setCookie(
                              this.formValidate.userName,
                              this.formValidate.passWord,
                              7
                          )
                        : this.clearCookie();
                    var loginParams = {
                        name: _this.formValidate.userName,
                        password: _this.formValidate.passWord
                    };

                    LoginService.UmLogin(loginParams).then(
                    	result=>{
                    		_this.logining = false;
                    		let sessionUserName = result.name;
                        	let sessionUserId = result.id;
                    		sessionStorage.setItem(
                                "UMUser",
                                JSON.stringify(result)
                            );
                            sessionStorage.setItem(
                                "UMUerName",
                                JSON.stringify(sessionUserName)
                            );
                            sessionStorage.setItem(
                                "UMUerId",
                                JSON.stringify(sessionUserId)
                            );
                            _this.$router.push({ path: "UMmain" });
                    	},
                    	error=>{
                    		_this.$Message.error(msg);
                            _this.loading = false;
                    	})
                    // _this.axios.post("/login", loginParams).then(result => {
                    //     _this.logining = false;
                    //     let { msg, code, data } = result.data;
                    //     //存用户名 例：“admin”
                    //     let sessionUserName = result.data.data.name;
                    //     let sessionUserId = result.data.data.id;
                    //     if (code !== "200") {
                    //         _this.$Message.error(msg);
                    //         _this.loading = false;
                    //     } else {
                    //         sessionStorage.setItem(
                    //             "UMUser",
                    //             JSON.stringify(data)
                    //         );
                    //         sessionStorage.setItem(
                    //             "UMUerName",
                    //             JSON.stringify(sessionUserName)
                    //         );
                    //         sessionStorage.setItem(
                    //             "UMUerId",
                    //             JSON.stringify(sessionUserId)
                    //         );
                    //         _this.$router.push({ path: "UMmain" });
                    //     }
                    // });
                } else {
                    this.$Message.error("输入不合法!");
                }
            });
        },
        //设置cookie
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
        getCookie: function() {
            if (document.cookie.length > 0) {
                var arr = document.cookie.split("; "); //这里显示的格式需要切割一下自己可输出看下
                for (var i = 0; i < arr.length; i++) {
                    var arr2 = arr[i].split("="); //再次切割
                    //判断查找相对应的值
                    if (arr2[0] == "userName") {
                        this.formValidate.userName = arr2[1]; //保存到保存数据的地方
                        this.formValidate.userName = arr2[1]; //保存到保存数据的地方
                    } else if (arr2[0] == "userPwd") {
                        this.formValidate.passWord = arr2[1];
                        this.formValidate.passWord = arr2[1];
                    }
                }
            }
        },
        //清除cookie
        clearCookie: function() {
            this.setCookie("", "", -1); //修改2值都为空，天数为负1天就好了
        },

        loadDefault: function() {
            if (document.cookie.split("; ")[0]) {
                this.checked = true;
            }
        }
    },
    //页面加载调用获取cookie值
    mounted() {
        this.getCookie();
        this.loadDefault();
    }
};
</script>

<style scoped>
.form {
    /* color:powderblue; */
    color: #fff;
    text-align: center;
    padding-top: 5%;
    position: relative;
    z-index: 999;
}
.login-container {
    box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);
    /* -webkit-border-radius: 5px; */
    /* border-radius: 10px; */
    /* -moz-border-radius: 5px; */
    background-clip: padding-box;
    margin: 180px auto;
    margin: 70px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    /* border: 1px solid #eaeaea; */
    box-shadow: 0 0 25px #cac6c6;
    z-index: 999;
    position: relative;
    /* background-color:rgba(14, 60, 187, 0.3);     */
    background-size: 350px 357px;
}
.title {
    margin: 0px auto 20px auto;
    text-align: center;
    color: #fff;
}
.remember {
    margin: 0px 0px 35px 0px;
    color: #fff;
}
</style>
