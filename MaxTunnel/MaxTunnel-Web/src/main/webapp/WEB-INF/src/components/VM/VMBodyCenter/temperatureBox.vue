<template>
    <div class="Weather">
        <div class="weather-content">
            <span class="cur_weather"><img :src="prevVal.prev_src" v-model="prevVal.prev_src"></span>
            <div class="weather-middle-content">
                <span class="temperature">{{prevVal.temperature | symbol}}</span>
                <span class="weatherVal">{{prevVal.weatherVal}}</span>
            </div>
            <div class="weather-right-content">
                <span class="cur_date" v-model="prevVal.prev_date">{{prevVal.prev_date}}</span>
                <span class="cur_time" v-model="prevVal.prev_time">{{prevVal.prev_time}}</span>
            </div>
            <!-- <img :src="direction_src"> -->
        </div>
    </div>
</template>

<style scoped>
    .Weather{
        position: absolute;
        width: 19.5%;
        height: 12.8%;
        color: #fff;
        z-index: 1003;
        background: url("../../../assets/VM/temperature_bg.png") no-repeat;
        background-size: 100% 100% ;
        right: 2%;
        top: 2%;
    }
    .weather-content{
        width: 85%;
        height: 50%;
        position: relative;
        top: 26%;
        left: 7%;
    }
    .weather-content .weather-middle-content{
        position: absolute;
        width: 22%;
        left: 29%;
        text-align: center;
        top: 0;
        padding: 1.5%;
        height: 100%;
        font-size: 1em;
        font-weight: bold;
    }
    .weather-middle-content .temperature{
        /*color: #031324;*/
        color: #fff;
    }
    .weather-middle-content .weatherVal{
        font-weight: bold;
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
        /*color: #031324;*/
        color: #fff;
    }
    .weather-content .weather-right-content{
        position: absolute;
        width: 38%;
        right: 5%;
        text-align: center;
        top: 0;
        font-size: 1.1em;
        border-left: 1px solid transparent;
        border-image: linear-gradient(0deg,rgba(22,78,137,.3) 0%, rgba(22,78,137,1) 50%,rgba(22,78,137,.3) 100%) 1;
    }
    .weather-right-content .cur_time{
        display: block;
        font-family: UnidreamLED;
        font-size: 1.6em;
    }
    .weather-right-content .cur_date{
        font-family: UnidreamLED;
        font-size: 1.1em;
    }
    .cur_weather > img{
        padding: 2%;
        margin-top: 1%;
        width: 23%;
        height: 90%;
        position: absolute;
        left: 8%;
    }
</style>

<script>
    export default {
        name: "temperature-box",
        data(){
            return{
                prevVal:{
                    prev_src:'',
                    prev_weather:'',
                    prev_date:'',
                    prev_time:'',
                    temperature:0,
                    weatherVal:''
                },
                weatherDiv:'',
                momObjectDiv:'',
            }
        },
        filters:{
            symbol(value){
                if(!value)return;
                return value+' ℃';
            }
        },
        mounted(){
            this.init();
        },
        methods:{
            init(){
                this.getWeather();
            },
            getWeather(){
                var _this=this,
                    weatherConfig=_this.VMConfig.weatherConfig;

                _this.weatherDiv=_this.weatherDiv ? _this.weatherDiv:_this.getDOMElement('Weather');
                // 网络正常
                if(!weatherConfig.networkAnomaly){

                    _this.weatherDiv.style.display = _this.weatherDiv.style.display == 'none' && 'block';

                    _this.axios.get(`http://restapi.amap.com/v3/weather/weatherInfo?key=${ weatherConfig.key }&city=${ weatherConfig.city }`)
                        .then(rel=>{
                            let { data }=rel;

                            if(data){
                                let  lives =data.lives[0];
                                _this.weatherDiv.style.display='block';

                                _this.changeTime();
                                setInterval(()=>_this.changeTime(),1000)
                                _this.prevVal.temperature=lives.temperature;
                                _this.prevVal.weatherVal=lives.weather;

                                // 得到当天天气
                                let curWeather=weatherConfig.weathers.filter(cur_obj=>cur_obj.key==lives.weather);

                                let weather_url = curWeather ? curWeather[0].val : "icon_fine";
                                _this.prevVal.prev_src=require('../../../assets/VM/'+ weather_url +'.png');

                            }
                        },
                            err=>_this.weatherDiv.style.display='none')
                }else {
                    // 网络异常 ,隐藏天气
                    _this.weatherDiv.style.display='none';
                }


            },
            changeTime(){
                let _this=this;
                new Date().format('yyyy-MM-ddThh:mm').replace(/(\d{4}-\d{2}-\d{2})T(\d{2}:\d{2})/g,(match,cur_date,cur_time)=>{

                    if(cur_date!=_this.prevVal.prev_date)_this.prevVal.prev_date=cur_date;

                    if(cur_time!=_this.prevVal.prev_time)_this.prevVal.prev_time=cur_time;
                })
            },
            getDOMElement(className){
                return document.getElementsByClassName(className)[0];
            }
        }
    }
</script>

