<template>
	<div class="main">
		<h1 class="form">综合管廊智慧管理平台</h1>
		<Form
			ref="formValidate"
			:model="formValidate"
			:rules="ruleValidate"
			class="demo-ruleForm login-container"
		>
			<h2 class="title">用户登录</h2>
			<FormItem prop="userName">
				<Input type="text" v-model="formValidate.userName" placeholder="用户名">
					<Icon type="ios-person-outline" slot="prepend"></Icon>
				</Input>
			</FormItem>
			<FormItem prop="passWord">
				<Input
					type="password"
					v-model="formValidate.passWord"
					placeholder="密码"
					@keyup.enter.native="handleSubmit('formValidate')"
				>
					<Icon type="ios-locked-outline" slot="prepend"></Icon>
				</Input>
			</FormItem>
			<FormItem>
				<checkbox v-model="checked" checked class="remember">一周内记住密码</checkbox>
			</FormItem>
			<FormItem>
				<Button
					type="primary"
					style="width:100%;"
					@click="handleSubmit('formValidate')"
					:loading="loading"
				>
					<span v-if="!loading">登录</span>
					<span v-else>Loading...</span>
				</Button>
			</FormItem>
		</Form>
		<LoginBG></LoginBG>
	</div>
</template>

<script>
import LoginBG from "../../components/UM/LoginBackGround.vue";
import VideoComponent from "../../components/Common/Video/VideoComponent";
import { LoginService } from "../../services/loginService";
import Cookies from "js-cookie";
export default {
	components: {
		LoginBG,
		VideoComponent
	},
	name: "UMlogin",
	data() {
		return {
			checked: false,
			loading: false,
			logining: false,
			formValidate: {
				userName: "",
				passWord: ""
			},
			ruleValidate: {
				userName: [
					{
						required: true,
						message: "用户名不能为空",
						trigger: "blur"
					}
				],
				passWord: [
					{
						required: true,
						message: "密码不能为空",
						trigger: "blur"
					}
				]
			}
		};
	},
	methods: {
		handleSubmit(name) {
			let _this = this;
			var sha256 = require("js-sha256").sha256;
			this.$refs[name].validate(valid => {
				if (valid) {
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
						password: sha256(_this.formValidate.passWord)
					};
					Cookies.set("userName", loginParams.name);
					this.$store
						.dispatch("Login", loginParams)
						.then(result => {
							let sessionUserId = result.userId;
							sessionStorage.setItem(
								"UMUerId",
								JSON.stringify(sessionUserId)
							);
							sessionStorage.setItem("UMUerName",result.token.username)
							_this.logining = false;
							this.loading = false;

							// 获取队列名
							let queueName = result.queueName;
							this.MQ.setQueueName(queueName);
							this.MQ.openMQ();

							_this.$router.push({ path: "/UMmain" });
						})
						.catch(() => {
							this.loading = false;
						});
				} else {
					this.$Message.error("输入不合法!");
				}
			});
		},
		//设置cookie
		setCookie(c_name, c_pwd, exdays) {
			Cookies.set(
				"user",
				{ name: c_name, pwd: c_pwd },
				{ expires: exdays }
			);
		},
		//读取cookie
		getCookie: function() {
			if (Cookies.getJSON("user")) {
				let { name, pwd } = Cookies.getJSON("user");
				this.formValidate.userName = name;
				this.formValidate.passWord = pwd;
			}
		},
		//清除cookie
		clearCookie: function() {
			Cookies.remove("user");
		},

		loadDefault: function() {
			if (Cookies.getJSON("user")) {
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
	color: #fff;
	text-align: center;
	padding-top: 5%;
	position: relative;
	z-index: 999;
	font-size: 2.6vmin;
}

.login-container {
	box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);
	background-clip: padding-box;
	margin: 180px auto;
	margin: 70px auto;
	width: 18vw;
	padding: 35px 35px 15px 35px;
	box-shadow: 0 0 25px #cac6c6;
	z-index: 999;
	position: relative;
	background-size: 350px 357px;
}

.title {
	margin: 0px auto 20px auto;
	text-align: center;
	color: #fff;
	font-size: 1.8vmin;
}

.remember {
	margin: 0px 0px 35px 0px;
	color: #fff;
}

.ivu-input-group {
	font-size: 1.8vmin;
}

.login-container >>> .ivu-input {
	font-size: 1.4vmin;
	height: 3.2vmin;
}

.ivu-checkbox-wrapper {
	font-size: 1.4vmin !important;
}

.ivu-btn {
	font-size: 1.6vmin !important;
}

.login-container >>> .ivu-checkbox-inner {
	width: 1.4vmin;
	height: 1.4vmin;
}

.login-container >>> .ivu-checkbox-inner:after {
	width: 0.6vmin;
	height: 1vmin;
}
</style>
