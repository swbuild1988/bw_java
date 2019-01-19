const baseView = {
    methods:{
        getModelData(id) {
            let deployCharts = this.VMWebConfig.WebConfig.deployCharts;
            let deployData = this.VMWebConfig.WebConfig.data;
            let res;
            deployCharts.forEach(element => {
                if (element.modelId == id) {
                    let tmp = deployData.find(item => {
                        return item.id == element.dataId;
                    });

                    if (!tmp) return null;

                    res = {
                        id: element.modelId,
                        title: tmp.title,
                        url: tmp.url,
                        parameters: tmp.parameters,
                        chartType: element.chartType
                    };
                }
            });

            return res;
        }
    }
}

export {
    baseView
}