import axios from 'axios'
const isRealData = require('../../static/serverconfig').isRealData

var LogoutService = {
	// UMLogout
	UMLogout: function(params) {
		return new Promise((resolve, reject) => {
			if (isRealData) {
				axios.get('/logout')
					.then(res => {
						let {
							msg,
							code,
							data
						} = res.data
						if (code == 200) {
							resolve(data)
						} else {
							reject(msg + '地址：/logout')
						}
					})
					.catch(error => {
						reject(error.response.status + '  ' + error.response.data)
					})
			} else {
				let data = {}
				resolve(data)
			}
		})
	}
}

export {
	LogoutService
}