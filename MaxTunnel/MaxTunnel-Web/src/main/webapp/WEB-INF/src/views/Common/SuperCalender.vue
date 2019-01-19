<<<<<<< HEAD
<template>
<div id="superCalendar">
    <!-- 年份 月份 -->
    <div class="monthBox">
        <ul class="month">
            <!--点击会触发pickpre函数，重新刷新当前日期 @click(vue v-on:click缩写) -->
            <li class="year-month">
                <div class="choose-month-c">{{ currentMonth }}</div>
                <div class="choose-month-e">{{ eCurrentMonth }}</div>
                <div class="choose-year">{{ currentYear }}</div>
            </li>
        </ul>
        <div class="arrow" @click="pickPre(currentYear,currentMonth)" style="left: 10px">
            <Icon type="chevron-left" color="#357aa1" size="30"></Icon>
        </div>
        <div class="arrow" @click="pickNext(currentYear,currentMonth)" style="right: 10px">
            <Icon type="chevron-right" color="#357aa1" size="30"></Icon>
        </div>
    </div>
    <!-- 星期 -->
    <div class="weekAndDay">
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
                <!-- 非周末 -->
                <div class="daysBox" v-if="dayobject.day.getDay()!=0&&dayobject.day.getDay()!=6">
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
                </div>
                <!-- 周末 -->
                <div class="weekDaysBox" v-else>
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
                            <div class="explanationShow">{{item.inspectManName}}</div>
                            <div class="explanationShow" :title="item.processStatus">{{item.processStatus}}</div>
                        </div>
                    </span>
                </div>
            </li>
        </ul>
    </div>
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
            isShow: false,
            eCurrentMonth: null
        }
    },
    props:{
        tasks:Array
    },
    watch:{
        'currentMonth': function(){
            this.showMonth()
        }
    },
    created: function() { //在vue初始化时调用
        this.initData(null);
        this.showMonth()
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
                date = new Date(this.formatDate(now.getFullYear(), now.getMonth() + 1, 1));
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
            }else if(m==0){
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
        //数组月份转英文
        showMonth: function(){
            var month = this.currentMonth
            switch(month){
                case 1:
                    this.eCurrentMonth = 'JAN'
                    break;
                case 2:
                    this.eCurrentMonth = 'FEB'
                    break;
                case 3: 
                    this.eCurrentMonth = 'MAR'
                    break;
                case 4: 
                    this.eCurrentMonth = 'APR'
                    break;
                case 5: 
                    this.eCurrentMonth = 'MAY'
                    break;
                case 6: 
                    this.eCurrentMonth = 'JUN'
                    break;
                case 7: 
                    this.eCurrentMonth = 'JUL'
                    break;
                case 8: 
                    this.eCurrentMonth = 'AUG'
                    break;
                case 9: 
                    this.eCurrentMonth = 'SEPT'
                    break;
                case 10: 
                    this.eCurrentMonth = 'OCT'
                    break;
                case 11: 
                    this.eCurrentMonth = 'NOV'
                    break;
                case 12: 
                    this.eCurrentMonth = 'DEC'
                    break;
            }
        }
    }
}
</script>

<style scoped>
#superCalendar{
    margin-top: 10px;
}
ul li{
    list-style: none;
    display: inline-block;
    vertical-align: middle;
}
.arrow{
    width: 35px;
    border: 2px #357aa1 solid;
    text-align: center;
    border-radius: 100%;
    position: absolute;
    bottom: 10px;
}
/*年月*/
.year-month{
    margin: 10px;
    font-size: 24px;
    font-weight: 500;
}
.weekdays div,.days li{
    display: inline-block;
    width: 8vw;
    text-align: center;
    vertical-align: top;
    overflow: auto;
}
.weekdays div{
    float: left;
    background: #fff;
    border: 1px solid #64798c;
    border-radius: 4px;
    height: 5.3vh;
    line-height: 5vh;
}
.weekdays div:hover{
    background: #64798c;
    color: #fff;
    border-radius: 4px;
}
.days li{
    height: 11vh;
    font-size: 18px;
    padding: 3px;
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
    color: #E0AE6E;
    font-weight: bold;
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
    width: 48%;
    vertical-align: top;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    padding: 0px 2px;
}
.monthBox,.weekAndDay{
    display: inline-block;
    vertical-align: top;
}
.monthBox{
    background: #14293C;
    width: 20vw;
    height: 71.3vh;
    position: relative;
}
.month{
    text-align: right;
    position: absolute;
    right: 0px;
    top: 50%;
    transform: translate(0%,-70%);
}
.weekAndDay{
    width: 57vw;
    /* border: 1px solid red; */
}
.choose-month-c{
    color: #E0AE6E;
    font-weight: 500;
    font-size: 45px;
}
.choose-month-e{
    color: #64798C;
    font-size: 75px;
    font-weight: 600;
}
.choose-year{
    font-weight: 600;
    color: #fefffe;
    font-size: 53px;
}
.daysBox,.weekDaysBox{
    height: 100%;
    border-radius: 4px;
    padding: 3px;
}
.daysBox{
    border: 1px solid #14293c;
}
.weekDaysBox{
    border: 2px solid #E0AE6E;
}
@media (min-width: 2200px){
    #superCalendar{
        margin-top: 1vmin;
    }
    .arrow{
        width: 3.5vmin;
        border: 0.2vmin #357aa1 solid;
        bottom: 1vmin;
    }
    /*年月*/
    .year-month{
        margin: 1vmin;
        font-size: 2.4vmin;
    }
    .weekdays div,.days li{
        width: 8vw;
    }
    .weekdays div{
        border: 0.1vmin solid #64798c;
        border-radius: 0.4vmin;
        height: 5.3vh;
        line-height: 5vh;
    }
    .weekdays div:hover{
        border-radius: 0.4vmin;
    }
    .weekdays div{
        font-size: 1.4vmin;
    }
    .days li{
        font-size: 1.8vmin;
        padding: 0.3vmin;
    }
    /*说明*/
    .explanation{
        border: 0.1vmin solid pink;
        border-radius: 2.9vmin;
        font-size: 1.2vmin;
    }
    .explanationShow{
        padding: 0px 0.2vmin;
    }
    .choose-month-c{
        font-size: 4.5vmin;
    }
    .choose-month-e{
        font-size: 7.5vmin;
    }
    .choose-year{
        font-size: 5.3vmin;
    }
    .daysBox,.weekDaysBox{
        border-radius: 0.4vmin;
        padding: 0.3vmin;
    }
    .daysBox{
        border: 0.1vmin solid #14293c;
    }
    .weekDaysBox{
        border: 0.2vmin solid #E0AE6E;
    }
    .ivu-icon-chevron-left,.ivu-icon-chevron-right{
        font-size: 3vmin !important;
    }
}
</style>
=======
<template>
<div id="superCalendar">
    <!-- 年份 月份 -->
    <div class="monthBox">
        <ul class="month">
            <!--点击会触发pickpre函数，重新刷新当前日期 @click(vue v-on:click缩写) -->
            <li class="year-month">
                <div class="choose-month-c">{{ currentMonth }}</div>
                <div class="choose-month-e">{{ eCurrentMonth }}</div>
                <div class="choose-year">{{ currentYear }}</div>
            </li>
        </ul>
        <div class="arrow" @click="pickPre(currentYear,currentMonth)" style="left: 10px">
            <Icon type="chevron-left" color="#357aa1" size="30"></Icon>
        </div>
        <div class="arrow" @click="pickNext(currentYear,currentMonth)" style="right: 10px">
            <Icon type="chevron-right" color="#357aa1" size="30"></Icon>
        </div>
    </div>
    <!-- 星期 -->
    <div class="weekAndDay">
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
                <!-- 非周末 -->
                <div class="daysBox" v-if="dayobject.day.getDay()!=0&&dayobject.day.getDay()!=6">
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
                </div>
                <!-- 周末 -->
                <div class="weekDaysBox" v-else>
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
                </div>
            </li>
        </ul>
    </div>
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
            isShow: false,
            eCurrentMonth: null
        }
    },
    props:{
        tasks:Array
    },
    watch:{
        'currentMonth': function(){
            this.showMonth()
        }
    },
    created: function() { //在vue初始化时调用
        this.initData(null);
    },
    mounted(){
        // this.showMonth()
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
        //数组月份转英文
        showMonth: function(){
            var month = this.currentMonth
            switch(month){
                case 1:
                    this.eCurrentMonth = 'JAN'
                    break;
                case 2:
                    this.eCurrentMonth = 'FEB'
                    break;
                case 3: 
                    this.eCurrentMonth = 'MAR'
                    break;
                case 4: 
                    this.eCurrentMonth = 'APR'
                    break;
                case 5: 
                    this.eCurrentMonth = 'MAY'
                    break;
                case 6: 
                    this.eCurrentMonth = 'JUN'
                    break;
                case 7: 
                    this.eCurrentMonth = 'JUL'
                    break;
                case 8: 
                    this.eCurrentMonth = 'AUG'
                    break;
                case 9: 
                    this.eCurrentMonth = 'SEPT'
                    break;
                case 10: 
                    this.eCurrentMonth = 'OCT'
                    break;
                case 11: 
                    this.eCurrentMonth = 'NOV'
                    break;
                case 12: 
                    this.eCurrentMonth = 'DEC'
                    break;
            }
        }
    }
}
</script>

<style scoped>
#superCalendar{
    margin-top: 10px;
}
ul li{
    list-style: none;
    display: inline-block;
    vertical-align: middle;
}
.arrow{
    width: 35px;
    border: 2px #357aa1 solid;
    text-align: center;
    border-radius: 100%;
    position: absolute;
    bottom: 10px;
}
/*年月*/
.year-month{
    margin: 10px;
    font-size: 24px;
    font-weight: 500;
}
.weekdays div,.days li{
    display: inline-block;
    width: 8vw;
    text-align: center;
    vertical-align: top;
    overflow: auto;
}
.weekdays div{
    float: left;
    background: #fff;
    border: 1px solid #64798c;
    border-radius: 4px;
    height: 5.3vh;
    line-height: 5vh;
}
.weekdays div:hover{
    background: #64798c;
    color: #fff;
    border-radius: 4px;
}
.days li{
    height: 11vh;
    font-size: 18px;
    padding: 3px;
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
    color: #E0AE6E;
    font-weight: bold;
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
.monthBox,.weekAndDay{
    display: inline-block;
    vertical-align: top;
}
.monthBox{
    background: #14293C;
    width: 20vw;
    height: 71.3vh;
    position: relative;
}
.month{
    text-align: right;
    position: absolute;
    right: 0px;
    top: 50%;
    transform: translate(0%,-70%);
}
.weekAndDay{
    width: 57vw;
    /* border: 1px solid red; */
}
.choose-month-c{
    color: #E0AE6E;
    font-weight: 500;
    font-size: 45px;
}
.choose-month-e{
    color: #64798C;
    font-size: 75px;
    font-weight: 600;
}
.choose-year{
    font-weight: 600;
    color: #fefffe;
    font-size: 53px;
}
.daysBox,.weekDaysBox{
    height: 100%;
    border-radius: 4px;
    padding: 3px;
}
.daysBox{
    border: 1px solid #14293c;
}
.weekDaysBox{
    border: 2px solid #E0AE6E;
}
</style>
>>>>>>> origin/dev
