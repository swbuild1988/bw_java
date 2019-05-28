<template>
<div>
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
    <div class="monthSelect">
        <ul class="monthParent">
            <li class="monthChild" v-for="(item, index) in monthSelect" :key="index">
                <div ref="monthContent" class="monthContent" @click="chooseMonth">
                        {{item.monthText}}
                </div>
            </li>
        </ul>  
    </div> 
    <div class="daySelect">
        <ul class="dayParent">
            <li class="dayChild" v-for="(item, index) in daySelect" :key="index">
                <div ref="dayContent" class="dayContent" @click="chhoseDay">
                    {{item}}
                </div>
            </li>
        </ul>
    </div> 
</div>
</template>
<script>
export default {
    props: {
        tasks: Array,
        type: String
    },
    data(){
        return{
            currentYear: null,
            yearList: [],
            monthSelect: [
                { monthText: '1月', textIndex: '0' },{ monthText: '2月', textIndex: '01' },
                { monthText: '3月', textIndex: '02' },{ monthText: '4月', textIndex: '02' },
                { monthText: '5月', textIndex: '03' },{ monthText: '6月', textIndex: '04' },
                { monthText: '7月', textIndex: '05' },{ monthText: '8月', textIndex: '06' },
                { monthText: '9月', textIndex: '07' },{ monthText: '10月', textIndex: '08' },
                { monthText: '11月', textIndex: '09' },{ monthText: '12月', textIndex: '10' }
            ],
            daySelect: [
                '1', '2', '3', '4', '5', '6', '7', '8', '9', '10',
                '11', '12', '13', '14', '15', '16', '17', '18', '19', '20',
                '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31' 
            ],
            dayInnerText: null
        }
    },
    watch:{
        'tasks': function(newVal){
            if(this.type=='name1'){
                this.showTaskTime()
            }
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
            // this.outputHeighLight(yearContent)
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
            let yearArr = this.$refs.yearContent
            if(yearArr!=undefined){
                for (let i = 0; i < yearArr.length; i++) {
                    if (yearArr[i].getAttribute("class") == 'active') {
                        yearArr[i].setAttribute('class', '');
                    }
                }
            }
            let monthArr = this.$refs.monthContent
            if(monthArr!=undefined){
                for (let i = 0; i < monthArr.length; i++) {
                    if (monthArr[i].getAttribute("class") == 'active') {
                        monthArr[i].setAttribute('class', '');
                    }
                }
            }
            let dayArr = this.$refs.dayContent
            if(dayArr!=undefined){
               for (let i = 0; i < dayArr.length; i++) {
                    if (dayArr[i].getAttribute("class") == 'active') {
                        dayArr[i].setAttribute('class', '');
                    }
                } 
            }
        },
        //高亮输出选中的时间
        outputHeighLight(name) {
            this.$nextTick(()=>{
                var arr = this.$refs.name;
                let activeText = [];
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i].getAttribute("class") == 'active') {
                        let HeighLightDay = new Date(arr[i].innerText);
                        activeText.push(HeighLightDay);
                    }
                }
            })
        },
        //选择月份
        chooseMonth(event){
            if(event.currentTarget.getAttribute("class") == "active"){
                event.currentTarget.className = "monthContent"
            }else{
                this.$nextTick(()=>{
                    var arr = this.$refs.monthContent;
                    for (var i = 0; i < arr.length; i++) {
                        if (arr[i].getAttribute("class") == 'active') {
                            arr[i].className = "monthContent"
                        }
                    }
                    event.currentTarget.className = "active"
                })
            }
        },
        // 选择天
        chhoseDay(event){
            if(event.currentTarget.getAttribute("class") == "active"){
                event.currentTarget.className = "dayContent"
            }else{
                this.$nextTick(()=>{
                    let arr = this.$refs.dayContent;
                    for (let i = 0; i < arr.length; i++) {
                        if (arr[i].getAttribute("class") == 'active') {
                            arr[i].className = "dayContent"
                        }
                    }
                    event.currentTarget.className = "active"
                })
            }
            this.dayInnerText = event
            this.toParent()
        },
        toParent(){
            //选中的年份
            var timeContent = []
            var yearContent = this.$refs.yearContent
            for(let i=0; i<yearContent.length; i++){
                if(yearContent[i].getAttribute("class").indexOf('active')>-1){
                    timeContent.push(yearContent[i].innerText)
                }
            }
            // 选中的月份
            var monthContent = this.$refs.monthContent
            for(let i=0; i<monthContent.length; i++){
                if(monthContent[i].getAttribute('class').indexOf('active')>-1){
                    var selectedMonth = i
                }
            }
            // 判断选中的日期，如果不存在，自动返回本月最后一天
            let activeText = [];
            for(let i=0; i<timeContent.length; i++){
                let temp = ''
                if(new Date(timeContent[i],selectedMonth,this.dayInnerText.currentTarget.innerText).getMonth()==selectedMonth){
                    temp = new Date(timeContent[i], selectedMonth, this.dayInnerText.currentTarget.innerText).format('yyyy-MM-dd')
                }else{
                    temp = new Date(timeContent[i],selectedMonth+1,0).format('yyyy-MM-dd')
                }
                activeText.push(temp)
            }
            this.$emit('getYearChild', activeText)
        },
        //回显
        showTaskTime(){
            this.tasks.forEach(item => {
                let arr = []
                arr = item.taskTime.split('-')
                var p1 = this.cancelActiveBeforeShow(this.$refs.yearContent, 'yearContent')
                Promise.all([p1]).then(result => {
                    this.shwoActiveDate(this.$refs.yearContent, arr[0])
                })
                this.cancelActiveBeforeShow(this.$refs.monthContent, 'monthContent')
                this.showActiveMonth(this.$refs.monthContent, arr[1])
                this.cancelActiveBeforeShow(this.$refs.dayContent, 'dayContent')
                this.shwoActiveDate(this.$refs.dayContent, arr[2])
            })
        },
        //回显选中的日期 年份、日期
        shwoActiveDate(list,year){
            this.$nextTick(()=>{ 
                for (let i = 0; i < list.length; i++) {
                    // 判断日期是否存在于当前列表 
                    if (list[i].innerText==year) {
                        list[i].setAttribute("class", "active");
                    }
                }
            })
        },
        //回显选中的日期 月份
        showActiveMonth(list, month){
            this.$nextTick(()=>{   
                if(month<10){
                    month = month.substring(1,2)
                }
                for (let i = 1; i <= list.length; i++) {
                    if (i==month) {
                        list[i-1].setAttribute("class", "active");
                    }
                }
            })
        },
        //回显之前先清除以前的选择
        cancelActiveBeforeShow(list, className){
            this.$nextTick(()=>{   
                for (let i = 0; i < list.length; i++) {
                    // 判断日期是否存在于当前列表 
                    if (list[i].getAttribute("class") == 'active') {
                        list[i].className = className
                    }
                }
            })
        }
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
    color: #247aaf;
    font-size: 1.6vmin;
    font-weight: 700;
}
.top,.year{
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
}
.top li,.year .eveYear, .monthChild, .dayChild{
    text-align: center;
    line-height: 3.5vh;
    margin: 0.5vh auto;
    cursor: pointer;
}
.top li,.year .eveYear{
    flex: 0 0 33.33333%;
    color: #fff;
}
.year .eveYear .yearContent, .monthParent .monthChild .monthContent,.dayParent .dayChild .dayContent{
    width: 5.5vh;
    height: 3.5vh;
    margin: 0 auto;
}
.year .eveYear .yearContent:hover,.monthParent .monthChild .monthContent:hover,.dayParent .dayChild .dayContent:hover{
    background: #247aaf;
    color: antiquewhite;
    border-radius: 4px;
}
.year .eveYear .active,.monthParent .monthChild .active, .dayParent .dayChild .active{
    width: 5.5vh;
    height: 3.5vh;
    margin: 0 auto;
    background: #1d5f87;
    color: antiquewhite;
    border-radius: 4px;
}
.monthParent, .dayParent{
    /* width: 20vw; */
    display: flex;
    flex-flow: row wrap;
    align-content: flex-start;
    box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.1), 0 1px 5px 0 rgba(0, 0, 0, 0.12);
    margin-top: 1vmin;
}
.monthChild{
    flex: 0 0 25%;
}
.dayChild{
    flex: 0 0 10%;
}
.yearCalender,.monthSelect,.daySelect{
    background: url("../../assets/UM/cardBG.png") no-repeat;
    background-size: 100% 100%; 
    color: #fff;
    width: auto;
}
</style>
