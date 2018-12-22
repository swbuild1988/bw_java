<template>
      <div class="EquipmentContainer BackgroundImage">
          <div class="Equipment" :id=barSimpleid></div>
          <vm-title :title-text="title"></vm-title>
      </div>
</template>

<style scoped>
.Equipment {
    width: 100%;
    height: 100%;
}
</style>

<script>
import VmTitle from "../VMTitle";
export default {
    name: "Equipment",
    props: ["barSimpleid", "requestUrl"],
    data() {
        return {
            title: "设备数量",
            myChart: "",
            xData: [],
            sData: []
        };
    },
    components: {
        VmTitle
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.drawBar();
            this.fetchData(this.requestUrl);
            this.refreshData();
        },
        drawBar() {
            let _this = this;
            _this.myChart = _this.$echarts.init(
                document.getElementById(_this.barSimpleid)
            );
            let option = {
                textStyle: {
                    color: "rgba(255, 255, 255, 0.3)"
                },
                color: ["#61a0a8"],
                tooltip: {},

                xAxis: {
                    data: _this.xData,
                    axisLabel: {
                        interval: 0,
                        rotate: 40,
                        textStyle: {
                            color: "#F1F2F3"
                        }
                    }
                },
                yAxis: {
                    axisLabel: {
                        interval: 1,
                        textStyle: {
                            color: "#F1F2F3"
                        }
                    }
                },
                series: [
                    {
                        type: "bar",
                        data: _this.sData,
                        barMaxWith: "100",
                        itemStyle: {
                            normal: {
                                color: new _this.$echarts.graphic.LinearGradient(
                                    0,
                                    0,
                                    0,
                                    1,
                                    [
                                        { offset: 0, color: "#346699" },
                                        { offset: 1, color: "#70ABE8" }
                                    ]
                                )
                            },
                            emphasis: {
                                color: new _this.$echarts.graphic.LinearGradient(
                                    0,
                                    0,
                                    0,
                                    1,
                                    [
                                        { offset: 0, color: "#70ABE8" },
                                        { offset: 1, color: "#346699" }
                                    ]
                                )
                            }
                        }
                    }
                ]
            };
            _this.myChart.setOption(option);
            window.addEventListener("resize", this.myChart.resize);
        },
        fetchData(requestUrl) {
            let _this = this;
            // setInterval(()=>data([
            //   {key:"安全防范",val:_this.random(150,0)},
            //   {key:"视屏监控",val:_this.random(150,0)},
            //   {key:"环境监测",val:_this.random(150,0)},
            //   {key:"火灾报警",val:_this.random(150,0)},
            //   {key:"隧道通讯",val:_this.random(150,0)}])
            //   ,10000)
            _this.axios.get(requestUrl).then(result => {
                let { code, data } = result.data;
                if (code == 200) {
                    let newData = data.reduce((init, item) => {
                        return {
                            xData:
                                init.xData == undefined
                                    ? [].concat(item.key)
                                    : [].concat(init.xData, item.key),
                            sData:
                                init.sData == undefined
                                    ? [].concat(item.val)
                                    : [].concat(init.sData, item.val)
                        };
                    }, {});
                    if (
                        JSON.stringify(newData.xData) !=
                            JSON.stringify(_this.xData) ||
                        JSON.stringify(newData.sData) !=
                            JSON.stringify(_this.xData)
                    ) {
                        _this.xData = newData.xData;
                        _this.sData = newData.sData;
                        _this.myChart.setOption({
                            xAxis: { data: _this.xData },
                            series: { data: _this.sData }
                        });
                    }
                }
            });
        },
        refreshData() {
            let _this = this;
            // _this.fetchData(data=>{
            //   let newData=data.reduce((init,item)=>{
            // })
            setInterval(() => _this.fetchData(_this.requestUrl), 50000);
        }
    }
};
</script>
