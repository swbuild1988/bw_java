<template>
<div class="yearCalender">
    <ul class="top">
        <li>
            <span @click="getPerYears">
                <Icon type="ios-arrow-left"></Icon><Icon type="ios-arrow-left"></Icon>
            </span>
        </li>
        <li class="curYear">{{currentYear}}</li>
        <li>
            <span @click="getNextYears">
                <Icon type="ios-arrow-right"></Icon><Icon type="ios-arrow-right"></Icon>
            </span>
        </li>
    </ul>
    <ul class="year">
        <li class="eveYear" v-for="(item, index) in yearList" :key="index">
            <div ref="yearContent" class="yearContent" @click="chooseYear">{{item.content}}</div>
        </li>
    </ul>
</div>    
</template>
<script>
export default {
    data(){
        return{
            currentYear: null,
            yearList: []
        }
    },
    mounted(){
        this.init()
    },
    methods: {
        init(){
            this.currentYear = new Date().getFullYear()
            for(var i=this.currentYear-8; i<=this.currentYear; i++){
                this.yearList.push({ content: i })
            }
        },
        //选择年份
        chooseYear(event){
            if(event.currentTarget.getAttribute("class") == "active"){
                event.currentTarget.className = "yearContent"
            }else{
                event.currentTarget.className = "active"
            }
            this.outputHeighLight()
        },
        //向下翻页
        getNextYears(){
            this.cancelActive()
            var lastYear = this.yearList[8].content
            this.yearList = []
            for(var i=lastYear+1; i<=lastYear+9; i++){
                this.yearList.push({ content: i })
            }
        },
        //向上翻页
        getPerYears(){
            this.cancelActive()
            var firstYear = this.yearList[0].content
            this.yearList = []
            for(var i=firstYear-9; i<firstYear; i++){
                this.yearList.push({ content: i })
            }
        },
        //取消所有的active
        cancelActive(){
            var arr = this.$refs.yearContent
            if(arr!=undefined){
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i].getAttribute("class") == 'active') {
                        arr[i].setAttribute('class', '');
                    }
                }
            }
        },
        //高亮输出选中的年份
        outputHeighLight() {
            this.$nextTick(()=>{
                var arr = this.$refs.yearContent;
                let activeText = [];
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i].getAttribute("class") == 'active') {
                        let HeighLightDay = new Date(arr[i].innerText);
                        activeText.push(HeighLightDay);
                    }
                }
                this.$emit('getYearChild', activeText)
            })
        },
    }
}
</script>
<style scoped>
.yearCalender:before{
    transform: scale(2);
    transition: all 1s linear;
}
.yearCalender{
    width: 20vw;
    box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.1), 0 1px 5px 0 rgba(0, 0, 0, 0.12);
    margin-top: 1vmin;
}
ul li{
    list-style: none;
}
.top .curYear{
    color: #1d5f87;
    font-size: 1.6vmin;
    font-weight: 700;
}
.top,.year{
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
}
.top li,.year .eveYear{
    flex: 0 0 33.33333%;
    text-align: center;
    line-height: 3.5vh;
    margin: 0.5vh auto;
    cursor: pointer;
}
.year .eveYear .yearContent{
    width: 5.5vh;
    height: 3.5vh;
    margin: 0 auto;
}
.year .eveYear .yearContent:hover{
    background: #247aaf;
    color: antiquewhite;
    border-radius: 4px;
}
.year .eveYear .active{
    width: 5.5vh;
    height: 3.5vh;
    margin: 0 auto;
    background: #1d5f87;
    color: antiquewhite;
    border-radius: 4px;
}
</style>
