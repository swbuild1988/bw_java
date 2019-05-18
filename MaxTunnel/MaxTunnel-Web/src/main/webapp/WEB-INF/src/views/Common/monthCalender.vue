<template>
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
</template>
<script>
export default {
    data(){
        return{
            currentYear: null,
            monthList: [
                { content: '一月' },{ content: '二月' },{ content: '三月' },{ content: '四月' },
                { content: '五月' },{ content: '六月' },{ content: '七月' },{ content: '八月' },
                { content: '九月' },{ content: '十月' },{ content: '十一月' },{ content: '十二月' }
            ]
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
            this.outputHeighLight()
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
            var arr = this.$refs.monthContent
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
    color: #1d5f87;
    font-size: 1.6vmin;
    font-weight: 700;
}
.top,.month{
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
}
.top li,.month .eveMonth{
    flex: 0 0 33.33333%;
    text-align: center;
    line-height: 3.5vh;
    margin: 0.5vh auto;
    cursor: pointer;
}
.month .eveMonth .monthContent{
    width: 5.5vh;
    height: 3.5vh;
    margin: 0 auto;
}
.month .eveMonth .monthContent:hover{
    background: #247aaf;
    color: antiquewhite;
    border-radius: 4px;
}
.month .eveMonth .active{
    width: 5.5vh;
    height: 3.5vh;
    margin: 0 auto;
    background: #1d5f87;
    color: antiquewhite;
    border-radius: 4px;
}
</style>
