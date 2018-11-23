<template>
<div id="superCalendar">
    <!-- 年份 月份 -->
    <div class="month">
        <ul>
            <!--点击会触发pickpre函数，重新刷新当前日期 @click(vue v-on:click缩写) -->
            <li class="arrow" @click="pickPre(currentYear,currentMonth)">
                <Icon type="chevron-left" color="#357aa1"></Icon>
            </li>
            <li class="year-month">
                <span class="choose-year">{{ currentYear }}年</span>
                <span class="choose-month">{{ currentMonth }}月</span>
            </li>
            <li class="arrow" @click="pickNext(currentYear,currentMonth)">
                <Icon type="chevron-right" color="#357aa1"></Icon>
            </li>
        </ul>
    </div>
    <!-- 星期 -->
    <div class="weekdays">
        <div style="color:red">星期天</div>
        <div>星期一</div>
        <div>星期二</div>
        <div>星期三</div>
        <div>星期四</div>
        <div>星期五</div>
        <div style="color:red">星期六</div>
    </div>
    <!-- 日期 -->
    <ul class="days">
        <!-- 核心 v-for循环 每一次循环用<li>标签创建一天 -->
        <li v-for="(dayobject,index) in days" :key="index">
            <!--本月-->
            <!--如果不是本月  改变类名加灰色-->
            <span v-if="dayobject.day.getMonth()+1 != currentMonth" class="other-month">{{ dayobject.day.getDate() }}</span>
            <!--如果是本月  还需要判断是不是这一天-->
            <span v-else>
                <!--今天  同年同月同日-->
                <p ref="dayLi"  v-if="
                    dayobject.day.getFullYear() == new Date().getFullYear() && 
                    dayobject.day.getMonth() == new Date().getMonth() && 
                    dayobject.day.getDate() == new Date().getDate()" class="curDay curMonthDay">
                {{ dayobject.day.getDate() }}
                </p>
                <p ref="dayLi" class="curMonthDay" v-else>{{ dayobject.day.getDate() }}</p>
                <div class="explanation" 
                    v-for="(item,index) in tasks" :key="index" v-if="
                    dayobject.day.getDate()==new Date(item.taskTime).getDate()&&
                    dayobject.day.getFullYear()==new Date(item.taskTime).getFullYear() && 
                    dayobject.day.getMonth()==new Date(item.taskTime).getMonth()" 
                    @click="show(item.id)" >
                    <!-- <span class="dot">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> -->
                    <div class="explanationShow">{{item.inspectManName}}</div>
                    <div class="explanationShow">{{item.processStatus}}</div>
                </div>
            </span>
        </li>
    </ul>
</div>
</template>
<script>
import Enum from '../../../static/Enum.json'
export default {
    data() {
        return {
            currentDay: 1,
            currentMonth: 1,
            currentYear: 1970,
            currentWeek: 1,
            // 日曆顯示日期數
            showDayNum: 42,
            days: [],
            class: 'active',
            arrWeek: [],
            selectedWeek: [],
            inspectionTimeCon: [],
            isShow: false
        }
    },
    props:{
        tasks:Array
    },
    watch:{
    },
    created: function() { //在vue初始化时调用
        this.initData(null);
    },
    mounted(){
    },
    methods: {
        initData: function(cur) {
            var leftcount = 0; //存放剩余数量
            var date;
            if (cur) {
                date = new Date(cur);
            } else {
                var now = new Date();
                var d = new Date(this.formatDate(now.getFullYear(), now.getMonth(), 1));
                d.setDate(this.showDayNum);
                date = new Date(this.formatDate(d.getFullYear(), d.getMonth() + 1, 1));
            }

            this.currentDay = date.getDate();
            this.currentYear = date.getFullYear();
            this.currentMonth = date.getMonth() + 1;

            this.currentWeek = date.getDay(); // 1...6,0
            var str = this.formatDate(this.currentYear, this.currentMonth, this.currentDay);
            this.days.length = 0;
            // 今天是周日，放在第一行第7个位置，前面6个
            //初始化本周
            for (var i = this.currentWeek; i >= 0; i--) {
                var d = new Date(str);
                d.setDate(d.getDate() - i);
                var dayobject = {}; //用一个对象包装Date对象  以便为以后预定功能添加属性
                dayobject.day = d;
                dayobject.isShow = false;
                this.days.push(dayobject); //将日期放入data 中的days数组 供页面渲染使用
            }
            //其他周
            for (var i = 1; i < this.showDayNum - this.currentWeek; i++) {
                var d = new Date(str);
                d.setDate(d.getDate() + i);
                var dayobject = {};
                dayobject.day = d;
                dayobject.isShow = false;
                this.days.push(dayobject);
            }
        },
        //上一个月
        pickPre: function(year,month){
            var d = new Date(this.formatDate(year, month, 1));
            d.setDate(this.showDayNum);
            var y
            var mon
            if(d.getMonth()<=1){
                mon = d.getMonth()+12
                y = d.getFullYear() - 1
            }else{
                mon = d.getMonth()
                y = d.getFullYear()
            }
                this.initData(this.formatDate(y, mon-1, 1));
                var firstDay = new Date(y,mon-2,1)
                var lastDay = new Date(y,mon - 1,0);
            this.$emit("listenToChildEvent",{firstDay,lastDay})
        },
        //下一个月
        pickNext: function(year, month) {
            var d = new Date(this.formatDate(year, month, 1));
            d.setDate(this.showDayNum);
            this.initData(this.formatDate(d.getFullYear(), d.getMonth() + 1, 1));
            var firstDay = new Date(d.getFullYear(),d.getMonth(),1)
            var lastDay = new Date(d.getFullYear(),d.getMonth()+1,0);
            this.$emit("listenToChildEvent",{firstDay,lastDay})
        },

        // 返回 类似 2016-01-02 格式的字符串
        formatDate: function(year, month, day) {
            var y = year;
            var m = month;
            if (0 < m < 10) {
                m = "0" + m
            }
            var d = day;
            if (d < 10) d = "0" + d;
            return y + "-" + m + "-" + d
        },
        goToMoudle: function(id, type) {
            this.$router.push({
                name: "UMAddTask",
                params: {
                    id: id,
                    type: type
                }
            });
        },
        show(id){
            this.goToMoudle(id,Enum.pageType.Read)
        },
    }
}
</script>

<style scoped>
#superCalendar{
    margin: 10px auto;
}
ul li{
    list-style: none;
    display: inline-block;
    vertical-align: middle;
}
.arrow{
    width: 22px;
    border: 2px #357aa1 solid;
    text-align: center;
    border-radius: 100%;
}
/*年月*/
.year-month{
    margin: 10px;
    font-size: 24px;
    font-weight: 500;
}
.weekdays div,.days li{
    display: inline-block;
    width: 11vw;
    text-align: center;
    vertical-align: top;
    overflow: auto;
}
.weekdays div{
    padding: 10px;
    float: left;
    background: #fff;
    border: 1px solid #eee;
}
.weekdays div:hover{
    background: #f1efef;
}
.days{
    width: 77.5vw;
    /* margin: 10px auto; */
}
.days li{
    border: 1px solid #eee;
    height: 11vh;
    font-size: 18px;
    padding: 5px;
    background: #Fff;
    text-align: right;
}
/*其他月份日期*/
.other-month{
    color: #989292;
}
.curMonthDay{
    color: black;
}
.curDay{
    color: #357aa1;
}
/*说明*/
.explanation{
    border: 1px solid pink;
    border-radius: 29px;
    background: #7d7fbf;
    font-size: 12px;
    text-align: left;
    color: #fff;
    cursor: pointer;
}
/* .dot{
    background: #ffbe00;
    border-radius: 30px;
} */
.explanationShow{
    text-align: center;
    display: inline-block;
    width: 49%;
    vertical-align: top;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    padding: 0px 2px;
}
</style>
