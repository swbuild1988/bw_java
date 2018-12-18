<template>
	<div class="diales">
		<div class="login" v-if="!loginData.isLogin">
			<Form ref="login" :model="login" :rules="ruleValidate" :label-width="100">
		        <h2 class="title">登录</h2>
		        <FormItem label="用户名：" prop="username">
		            <Input v-model="login.username" placeholder="请输入用户名" class="input"></Input>
		        </FormItem>
		        <FormItem label="密码：" prop="password">
		            <Input type="password" v-model="login.password" placeholder="请输入密码" class="input" @keyup.enter.native="submit('login')"></Input>
		        </FormItem>
		        <FormItem>
		          <Button type="primary" style="width:80%;" @click="submit('login')" >登录</Button>
		        </FormItem>
		    </Form>
		</div>
		<div v-if="loginData.isLogin">
			<Row class="conditions">
				<Col span="12">
					<span>去电编号：</span>
					<Input placehold="请输入去电编号" v-model="outboundid" style="width: 60%"></Input>
				</Col>
				<Col span="3" offset="9">
					<Button type="primary" @click="search" icon="ios-search">查询</Button>
					<Button type="error" @click="logout">退出</Button>
				</Col>
			</Row>
			<div class="list">
				<Table border ref="selection" :columns="columns" :data="outboundList"></Table>
			</div>
		</div>
	</div>
</template>

<script>
	import { CommunicationService } from '../../../../services/communicationService'

	export default {
		name: 'diales',
		data() {
			return {
				login: {
					username: '',
					password: ''
				},
				ruleValidate: {
	                username: [
	                    { required: true, message: "用户名不能为空", trigger: "blur" }
	                ],
	                password: [
	                    { required: true, message: "密码不能为空", trigger: "blur" }
	                ]
	            },
	            outboundid: null,
	            page: {
	            	pageNum: 1,
        			pageSize: 10,
        			pageTotal: 0
	            },
	            outboundList: [],
	            columns: [
		            {
		            	title: "去电编号",
				        key: "outboundid",
				        align: "center"
		            },
		            {
		            	title: "主叫号码",
				        key: "from",
				        align: "center"
		            },
		            {
		            	title: "被叫号码",
				        key: "to",
				        align: "center"
		            },
		            {
		            	title: "通话状态",
				        key: "status",
				        align: "center"
		            }
	            ],
	            intervalId: null,
			}
		},
		computed:{
	        loginData(){
	            return  this.$store.state.UMstate.communication
	        }
	    },    
		mounted() {
			this.search()
			if(this.loginData.isLogin  && this.loginData.intervalId == null){
				this.startHeartBeat()
			}
		},
		methods: {
			submit(name){
				let md5 = require('js-md5')
				this.$refs[name].validate(valid => {
					if(valid){
						let params = {
							username: this.login.username,
							password: md5(this.login.password)
						}
						let _this = this
						CommunicationService.login(params).then(
							result=>{
								let data = {
									isLogin: true,
									token: result.token
								}
								_this.$store.commit('comLogin',data)
								_this.search()
								_this.startHeartBeat()
							},
							error=>{
								_this.$Message.error("登录失败!")
							})
					}
				})
			},
			search(){
				let _this = this
				CommunicationService.getOutbound(this.token,this.inboundid).then(
					result=>{
						_this.outboundList = []
						_this.outboundList = result.outbound
					},
					error=>{
						_this.Log.info(error)
					})
			},
			handlePage(value) {
		        this.page.pageNum = value
		        this.search()
		    },
		    logout(){
		    	this.$Modal.confirm({
                    title: '确认',
                    content: '<p>确定退出吗？</p>',
                    onOk: () => {
                        let _this = this
						CommunicationService.logout(this.token).then(
							result=>{
								if(result == null){
									_this.$Message.error("退出失败!")
								} else {
									_this.$Message.info("退出成功!")
									_this.$store.commit('comLogout')
									_this.stopHeartBeat()
								}
							},
							error=>{
								_this.Log.info(error)
							})
                    }
                })
		    },
		    startHeartBeat(){
		    	let _this = this
			    let intervalId = setInterval(()=>{
					_this.heartbeat()
				},1000000)
				_this.$store.commit('startInterval',intervalId)
		    },
		    stopHeartBeat(){
		    	clearInterval(this.loginData.intervalId)
		    	this.$store.commit('stopInterval')
		    },
		    heartbeat(){
		    	let _this = this
		    	let params = {
		    		ipaddr: '192.168.11.144',
		    		port: '8260',
		    		url: '1112121212'
		    	}
		    	CommunicationService.heartBeat(this.token,params).then(
		    		result=>{
		    			_this.Log.info(result)
			    	},
			    	error=>{
			    		_this.Log.info(error)
			    	})
		    }
		}
	}
</script>

<style scoped>
.diales{
	position: relative;
	width: 100%;
	height: 100%;
}
.login{
	width: 26vw;
	height: 44vh;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%,-50%);
}
.title{
	text-align: center;
	margin-bottom: 20px;
}
.input{
	width: 80%;
}
</style>