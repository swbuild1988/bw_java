<template>
<div>
    <div class="monthCalender">
        <ul class="top">
            <li>
                <span @click="getPerMonth">
                    <Icon type="ios-arrow-left"></Icon><Icon type="ios-arrow-left"></Icon>
                </span>
            </li>
            <li class="curYear">{{currentYear}}</li>
            <li>
                <span @click="getNextMonth">
                    <Icon type="ios-arrow-right"></Icon><Icon type="ios-arrow-right"></Icon>
                </span>
            </li>
        </ul>
        <ul class="month">
            <li class="eveMonth" v-for="(item, index) in monthList" :key="index">
                <div ref="monthContent" class="monthContent" @click="chooseMonth">{{item.content}}</div>
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
            monthList: [
                { content: '一月' },{ content: '二月' },{ content: '三月' },{ content: '四月' },
                { content: '五月' },{ content: '六月' },{ content: '七月' },{ content: '八月' },
                { content: '九月' },{ content: '十月' },{ content: '十一月' },{ content: '十二月' }
            ],
            daySelect: [
                '1', '2', '3', '4', '5', '6', '7', '8', '9', '10',
                '11', '12', '13', '14', '15', '16', '17', '18', '19', '20',
                '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31' 
            ],
            dayInnerText: null
        }
    },
    watch: {
        'tasks': function(newVal){
            if(this.type=='name2'){
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
        },
        //选择年份
        chooseMonth(event){
            if(event.currentTarget.getAttribute("class") == "active"){
                event.currentTarget.className = "monthContent"
            }else{
                event.currentTarget.className = "active"
            }
            // this.outputHeighLight()
        },
        //向下翻页
        getNextMonth(){
            this.currentYear = this.currentYear+1
            this.cancelActive()
        },
        //向上翻页
        getPerMonth(){
            this.currentYear = this.currentYear-1
            this.cancelActive()
        },
        //取消所有的active
        cancelActive(){
            var monthArr = this.$refs.monthContent
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
        //高亮输出选中的年份
        outputHeighLight() {
            this.$nextTick(()=>{
                var arr = this.$refs.monthContent;
                let activeText = [];
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i].getAttribute("class") == 'active') {
                        let HeighLightDay = new Date(this.currentYear, i);
                        activeText.push(HeighLightDay);
                    }
                }
                this.$emit('getMonthChild', activeText)
            })
        },
        // 选择天
        chhoseDay(event){
            if(event.currentTarget.getAttribute("class") == "active"){
                event.currentTarget.className = "dayContent"
            }else{
                this.$nextTick(()=>{
                    var arr = this.$refs.dayContent;
                    for (var i = 0; i < arr.length; i++) {
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
            var timeContent = []
            var monthContent = this.$refs.monthContent
            for(let i=0; i<monthContent.length; i++){
                if(monthContent[i].getAttribute("class").indexOf('active')>-1){
                    let day = new Date(this.currentYear, i, this.dayInnerText.currentTarget.innerText)
                    timeContent.push(day)
                }
            }

            // 判断选中的日期，如果不存在，自动返回本月最后一天
            let activeText = [];
            for(let i=0; i<timeContent.length; i++){
                let temp = ''
                if(timeContent[i].getDate()==this.dayInnerText.currentTarget.innerText){
                    //本月包含最后一天
                    temp = timeContent[i]
                }else{
                    //本月最后一天不在所选范围内
                    temp = new Date(timeContent[i].getFullYear(),timeContent[i].getMonth(),0)
                }
                activeText.push(temp)
            }
            this.$emit('getMonthChild', activeText)
        },
        //回显
        showTaskTime(){
            let timeCon = []
            this.tasks.forEach(item => {
                this.currentYear = new Date(item.taskTime).getFullYear()
                var p1 = this.cancelActiveBeforeShow(this.$refs.monthContent, 'monthContent')
                Promise.all([p1]).then(res=>{
                    this.showActiveMonth(this.$refs.monthContent, new Date(item.taskTime).getMonth())
                })
                timeCon.push(new Date(item.taskTime).getDate())
            })
            //如果本月最后一天不在所选范围内，按照日期最大显示
            this.cancelActiveBeforeShow(this.$refs.dayContent, 'dayContent')
            var max = Math.max.apply(null, timeCon);
            this.shwoActiveDate(this.$refs.dayContent, max)
        },
        //回显选中的日期 月份
        showActiveMonth(list, month){
            this.$nextTick(()=>{   
                for (let i = 1; i <= list.length; i++) {
                    if (i==month) {
                        list[i].setAttribute("class", "active");
                    }
                }
            })
        },
        //回显选中的日期
        shwoActiveDate(list,day){
            this.$nextTick(()=>{ 
                for (let i = 0; i < list.length; i++) {
                    // 判断日期是否存在于当前列表 
                    if (list[i].innerText==day) {
                        list[i].setAttribute("class", "active");
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
.monthCalender{
    width: 15vw;
    box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.1), 0 1px 5px 0 rgba(0, 0, 0, 0.12);
    margin-top: 1vmin;
}
ul li{
    list-style: none;
}
.top .curYear{
    color: ##4dbcff;
    font-size: 1.6vmin;
    font-weight: 700;
}
.top,.month{
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
}
.top li,.month .eveMonth,.dayChild{
    flex: 0 0 33.33333%;
    text-align: center;
    line-height: 3.5vh;
    margin: 0.5vh auto;
    cursor: pointer;
}
.month .eveMonth .monthContent,.dayParent .dayChild .dayContent{
    width: 5.5vh;
    height: 3.5vh;
    margin: 0 auto;
}
.month .eveMonth .monthContent:hover,.dayParent .dayChild .dayContent:hover{
    background: #247aaf;
    color: antiquewhite;
    border-radius: 4px;
}
.month .eveMonth .active,.dayParent .dayChild .active{
    width: 5.5vh;
    height: 3.5vh;
    margin: 0 auto;
    background: #1d5f87;
    color: antiquewhite;
    border-radius: 4px;
}
.dayParent{
    display: flex;
    flex-flow: row wrap;
    align-content: flex-start;
    box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.1), 0 1px 5px 0 rgba(0, 0, 0, 0.12);
    margin-top: 1vmin;
}
.dayChild{
    flex: 0 0 10%;
}
.monthCalender,.daySelect{
    background: url("../../assets/UM/cardBG.png") no-repeat;
    background-size: 100% 100%; 
    color: #fff;
    width: auto;
}
</style>
