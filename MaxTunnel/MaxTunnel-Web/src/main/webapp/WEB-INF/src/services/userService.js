import axios from 'axios'

var UserService = {
	// 获取用户消息数
	getMessageCount: function() {
		return new Promise((resolve, reject) => {
			axios.get('/users/msg/count')
				.then(res => {
					let {
						msg,
						data,
						code
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：/users/msg/count')
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	}

}

export {
	UserService
}