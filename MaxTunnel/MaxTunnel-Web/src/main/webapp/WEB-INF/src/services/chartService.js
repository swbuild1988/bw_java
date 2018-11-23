import axios from 'axios'

var reqUrls = {
	simplePieChart: {
		energyComsumption: 'tunnels/items/energies'
	}
}

var ChartService = {
	// Charts
	getChartData: function(reqUrl) {
		return new Promise((resolve, reject) => {
			axios.get(reqUrl)
				.then(res => {
					let {
						code,
						data,
						msg
					} = res.data
					if (code == 200) {
						resolve(data)
					} else {
						reject(msg + '地址：' + reqUrl)
					}
				})
				.catch(error => {
					reject(error.response.status + '  ' + error.response.data)
				})
		})
	}
}

export {
	ChartService,
	reqUrls
}