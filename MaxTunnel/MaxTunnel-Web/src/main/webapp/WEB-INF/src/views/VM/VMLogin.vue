<template>
	<div class="main">
		<Form
			ref="formValidate"
			:model="formValidate"
			:rules="ruleValidate"
			class="demo-ruleForm login-container"
		>
			<h3 class="title">{{title}}</h3>
			<FormItem prop="userName">
				<Input class="loginInput" type="text" v-model="formValidate.userName" placeholder="用户名">
					<Icon type="ios-person-outline" slot="prepend"></Icon>
				</Input>
			</FormItem>
			<FormItem prop="passWord">
				<Input
					class="loginInput"
					type="password"
					v-model="formValidate.passWord"
					placeholder="密码"
					@keyup.enter.native="handleSubmit('formValidate')"
				>
					<Icon type="ios-locked-outline" slot="prepend"></Icon>
				</Input>
			</FormItem>
			<FormItem>
				<checkbox v-model="formValidate.checked" checked class="remember"></checkbox>
				<span>记住密码</span>
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
	</div>
</template>

<script>
import { LoginService } from "../../services/loginService";
import Cookies from "js-cookie";
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
			title: "管廊智慧管理平台",
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
			let _this = this;
			var sha256 = require("js-sha256").sha256;
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
						password: sha256(_this.formValidate.passWord)
					};
					Cookies.set("userName", loginParams.name);
					this.$store
						.dispatch("Login", loginParams)
						.then(() => {
							_this.logining = false;

							_this.$router.push({ path: "/VMmain" });
						})
						.catch(() => {
							this.loading = false;
						});
				} else {
					_this.$Message.error("输入不合法!");
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
.main >>> .ivu-input-group-append,
.main >>> .ivu-input-group-prepend {
	color: #fff;
	background-color: #3b94f1;
	border: 1px solid #2d8cf0;
}
.remember + span {
	float: left;
}
/* 小屏幕（显示器，小于等于 1920px） */
@media (max-width: 1920px) {
	.login-container {
		width: 350px;
		padding: 35px 35px 15px 35px;
	}
	.title {
		font-size: 1.5em;
		margin: 0px auto 20px auto;
	}
	.main >>> .ivu-btn {
		font-size: 16px;
	}
	.remember + span {
		margin-left: 0.4rem;
		font-size: 12px;
		margin-top: 0.08rem;
	}
}
/* 大屏幕（显示器，大于等于 1920px） */
@media (min-width: 1921px) {
	.login-container {
		width: 20%;
		height: 25%;
		padding: 3rem 3rem 0 3rem;
	}
	.title {
		font-size: 4.5em;
		margin: 0px auto 2rem auto;
	}
	.main >>> .ivu-btn {
		font-size: 1.6rem !important;
	}
	.main >>> .ivu-checkbox-inner {
		width: 2rem;
		height: 2rem;
	}
	.main >>> .ivu-checkbox-inner:after {
		width: 0.6rem;
		height: 1rem;
		top: 5px;
		left: 11px;
	}
	.remember + span {
		margin-left: 1rem;
		font-size: 1.4rem;
		margin-top: 0.02rem;
	}
	.main >>> .ivu-input-group-prepend {
		font-size: 2rem;
	}
	.loginInput >>> .ivu-input {
		height: 2.6rem;
		font-size: 1.5rem;
	}
}
</style>
