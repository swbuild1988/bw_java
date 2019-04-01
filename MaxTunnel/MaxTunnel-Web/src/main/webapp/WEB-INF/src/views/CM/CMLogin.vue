<template>
	<div class="main">
		<div class="ceng">
			<Form
				ref="formValidate"
				:model="formValidate"
				:rules="ruleValidate"
				class="demo-ruleForm login-container"
			>
				<h3 class="title">综合管廊后台管理系统</h3>
				<FormItem prop="userName">
					<!-- <Input type="text" v-model="formValidate.userName" placeholder="用户名">
					<Icon type="ios-person-outline" slot="prepend"></Icon>-->
					<!-- </Input> -->
					<div class="radiusBox">
						<div class="iconBox">
							<Icon type="ios-person-outline" color="gray" size="20"></Icon>
						</div>
						<div class="inputBox">
							<input type="text" v-model="formValidate.userName" placeholder="用户名" @blur="checkName()">
						</div>
						<div class="ivu-form-item-error-tip" v-if="isName" style="margin-left: 30px;">姓名不能为空</div>
					</div>
				</FormItem>
				<FormItem prop="passWord">
					<!-- <Input type="password" >
					</Input>-->
					<div class="radiusBox">
						<div class="iconBox">
							<Icon type="ios-locked-outline" color="gray" size="20"></Icon>
						</div>
						<div class="inputBox">
							<input type="password" v-model="formValidate.passWord" placeholder="密码" @blur="checkPass()">
						</div>
						<div class="ivu-form-item-error-tip" v-if="isPass" style="margin-left: 30px;">密码不能为空</div>
					</div>
				</FormItem>
				<FormItem>
					<!-- <checkbox v-model="checked" checked class="remember" >记住密码 </checkbox> -->
					<input
						id="color-input-red"
						class="chat-button-location-radio-input"
						v-model="checked"
						type="checkbox"
						checked
						name="color-input-red"
						value="#f0544d"
					>
					<label for="color-input-red"></label>
					<span style="font-size: 14px;">记住密码</span>
				</FormItem>
				<FormItem style="text-align: center">
					<Button
						type="primary"
						style="width:70%;border-radius: 25px;background: #052752;border-color:#052752"
						@click="handleSubmit('formValidate')"
						:loading="loading"
					>
						<span v-if="!loading">登录</span>
						<span v-else>Loading...</span>
					</Button>
				</FormItem>
			</Form>
		</div>
	</div>
</template>

<script>
import { LoginService } from "../../services/loginService";
import Cookies from "js-cookie";
export default {
	components: {},
	name: "CMlogin",
	data() {
		return {
			isName: false,
			isPass: false,
			checked: true,
			loading: false,
			logining: false,
			formValidate: {
				userName: null,
				passWord: null
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
			var _this = this;
			var sha256 = require("js-sha256").sha256;
			this.checkName();
			this.checkPass();
			if (this.isName == false && this.isPass == false) {
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
					password: sha256(_this.formValidate.passWord)
				};
				Cookies.set("userName", loginParams.name);
				this.$store
					.dispatch("Login", loginParams)
					.then(() => {
						_this.logining = false;
						_this.$router.push({ path: "/CMmain" });
					})
					.catch(() => {
						this.loading = false;
					});
			}
		},
		checkName() {
			if (
				this.formValidate.userName == "undefined" ||
				this.formValidate.userName == "" ||
				this.formValidate.userName == null
			) {
				this.isName = true;
			} else {
				this.isName = false;
			}
		},
		checkPass() {
			if (
				this.formValidate.passWord == "undefined" ||
				this.formValidate.passWord == "" ||
				this.formValidate.passWord == null
			) {
				this.isPass = true;
			} else {
				this.isPass = false;
			}
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
#color-input-red + label {
	display: block;
	width: 15px;
	height: 15px;
	cursor: pointer;
	position: absolute;
	top: 7px;
	left: -2px;
	background: rgb(5, 39, 82);
	border-radius: 4px;
}
#color-input-red:checked + label::before {
	display: block;
	content: "\2714";
	text-align: center;
	font-size: 13px;
	color: white;
	margin-top: -8px;
}

.login-container {
	box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);
	-webkit-border-radius: 5px;
	border-radius: 5px;
	-moz-border-radius: 5px;
	background-clip: padding-box;
	margin: 180px auto;
	width: 350px;
	padding: 35px 35px 15px 35px;
	background: #5e52523d;
	border: 1px solid #eaeaea;
	box-shadow: 0 0 25px #cac6c6;
	color: #fff;
}
.main {
	background-image: url("../../assets/CM/bgred.png");
	color: #fff;
	background-size: cover;
	-moz-background-size: cover;
	-webkit-background-size: cover;
	position: absolute;
	width: 100%;
	height: 100%;
}
.title {
	margin: 0px auto 20px auto;
	text-align: center;
	color: #fff;
}
.remember {
	margin: 0px 0px 35px 0px;
}
.ceng {
	position: absolute;
	background: rgba(41, 76, 120, 0.66);
	height: 100%;
	width: 100%;
}
.ivu-input-group-prepend,
.ivu-input {
	border-radius: 35px;
}
input::-webkit-input-placeholder {
	color: #aab2bd;
	font-size: 12px;
}
input,
input:hover,
input:focus {
	border: none;
	outline: none;
}
.inputBox {
	display: inline-block;
	vertical-align: middle;
}
.iconBox {
	display: inline-block;
	vertical-align: bottom;
	padding-left: 15px;
}
.radiusBox {
	background: #fff;
	border-radius: 35px;
}
/*checkbox的颜色*/
.remember. >>> .ivu-checkbox-checked .ivu-checkbox-inner {
	border-color: #052752 !important;
	background-color: #052752 !important;
}
/* .ivu-form-item-content label+.ivu-checkbox-inner{
    background: red;
  } */
</style>
