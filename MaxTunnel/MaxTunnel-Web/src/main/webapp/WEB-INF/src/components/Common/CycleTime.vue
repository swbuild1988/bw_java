<template>
 
    <div class="cycle-time-content">
        <div class="cycle-time-top">
            <span class="lf cycle-time-unit" v-show="showBottom">周期时间:</span>
            <Select v-model="selectData" style="padding: .1rem;box-sizing: border-box" class="lf" on-change="getChangeVal">
                <Option v-for="option in dataUnits" :key="option.value" :value="option.value">{{ option.text }}</Option>
            </Select>
        </div>
        <div class="cycle-time-body">
            <div v-for="(item,index) in cycleTimeParam" v-show="item.show">
                <Input v-model="item.number" style="width: 75%" :placeholder="item.placeholder"/><span class="cycle-time-unit bottom-unit">{{ item.unit }}</span>
            </div>
        </div>
        <!--<div class="cycle-time-bottom" v-show="showBottom">-->
            <!--<Button @click="comfirm" class="comfirm">确认</Button>-->
            <!--<Button @click="cancel" class="cancel" >取消</Button>-->
        <!--</div>-->
    </div>

</template>

<style>
    .cycle-time-top , .cycle-time-body,.cycle-time-bottom{
        display: grid;
        grid-template-columns: repeat(4,1fr);
    }
    .cycle-time-content {
        width: 100%;
        height: 100%;
    }
    .lf{
        float: left;
    }
    .cycle-time-unit {
        display: inline-block;
        color: #000;
        font-size: 1rem;
    }
    .bottom-unit {
        width: 25%;
        font-size: .7rem;
        text-align: center;
    }
    /*.cycle-time-bottom .comfirm {*/
        /*grid-column-start: 3;*/
    /*}*/
    /*.cycle-time-bottom .cancel {*/
        /*grid-column-start: 4;*/
    /*}*/

</style>

<script>
    const MONTH = '月';
    const WEEK = '周';
    const DAY ='日';
    const HOURS = '时';
    const MINUTES = '分';
    const SECONDS ='秒';

    export default {
        props:{
            displayData:{
                type: String,
                default: ''
            },

        },
        data() {
            return {
                dataUnits: [
                    { text:MONTH ,value:MONTH },
                    { text:WEEK ,value:WEEK },
                    { text:DAY ,value:DAY },
                    { text:HOURS ,value:HOURS },
                ],
                selectData:MONTH,
                showBottom:false,
                cycleTimeParam:[
                    {
                        number:'',
                        show:false,
                        unit:WEEK,
                        placeholder:'1 - 7日',
                        defaultData:'?',
                        pointer:5

                    },
                    {
                        number:'',
                        show:true,
                        unit:DAY,
                        placeholder:'1 - 31日',
                        defaultData:'*',
                        pointer:3
                    },
                    {
                        number:'',
                        show:true,
                        unit:HOURS,
                        placeholder:'0 - 23小时',
                        defaultData:'*',
                        pointer:2
                    },
                    {
                        number:'',
                        show:true,
                        unit:MINUTES,
                        placeholder:'0 - 59分',
                        defaultData:'*',
                        pointer:1
                    },
                    {
                        number:'',
                        show:true,
                        unit:SECONDS,
                        placeholder:'0 - 59秒',
                        defaultData:'*',
                        pointer:0
                    }
                ],
                associative:[
                    { key:MONTH,value:[WEEK] },
                    { key:WEEK,value:[DAY] },
                    { key:DAY,value:[DAY,WEEK] },
                    { key:HOURS,value:[DAY,WEEK,HOURS] },
                ]
            }
        },
        watch:{
            selectData(){
                let _this = this;

                _this.cycleTimeParam.forEach( param => param.number = '' ); //清空number值

                let [ nape ] = _this.associative.filter( item => item.key === _this.selectData );
                this.updateData( nape.key ,nape.value );

            },
            displayData(){
                this.init();
            }
        
        },
        mounted(){
            this.init();
        },
        methods:{
            init(){
                //用来判断是 展示数据 还是 发送数据
                !this.displayData ? this.showBottom = true : this.requestData( this.displayData ) ;
            },
            updateData(selectData,node){
                if( Object.prototype.toString.call( node ) !== "[object Array]" || node.length < 1) return;

                let paramPointer = 0; // cycleTimeParam 数组 默认指针 0

                for(let i = 0 ; i < node.length ;i++){
                    for(let j = paramPointer ; j < this.cycleTimeParam.length; j++){
                        if(node[i] === this.cycleTimeParam[j].unit){
                            this.cycleTimeParam[j].show = false;

                            this.addInitUnit(selectData); //修改默认单位

                            [this.cycleTimeParam[j],this.cycleTimeParam[paramPointer]] = [this.cycleTimeParam[paramPointer],this.cycleTimeParam[j]];
                            ++ paramPointer ; // 指针前移1个单位

                        }else {
                            this.cycleTimeParam[j].show = true;
                        }
                    }
                }

            },
            addInitUnit(data){

                this.cycleTimeParam.forEach( param => param.defaultData = data === WEEK ? (param.unit ===DAY ? '?' : '*') : (param.unit ===WEEK ? '?' : '*') )

            },
            collectData(){
                let str ='',
                    number;
                let { cycleTimeParam } = this;

                for(let i = 0 ;i< cycleTimeParam.length ;i++){
                    for(let j =0 ;j< cycleTimeParam.length;j++){

                        if( i === cycleTimeParam[j].pointer || ( i === cycleTimeParam.length-1 && cycleTimeParam[j].pointer === 5 ) ){

                            number = cycleTimeParam[j].number === ''? cycleTimeParam[j].defaultData : cycleTimeParam[j].number;
                            if( i === cycleTimeParam.length-1 ) str += '* ';
                            str+=(number+' ');

                        }

                    }
                }
                console.log('str',str)
                return str;
            },
            // comfirm(){
            //     let data = this.collectData();
            //     //向后端发送数据
            //     //xxx
            // },
            // cancel(){
            //     this.cycleTimeParam.forEach( param => param.number = '' )
            // },
            requestData(str){
                if( str.length < 1 ) return;

                let Pointer = 0 ;//默认为0

                str.replace(/[0-9A-Za-z*?/,]+(?=\s|)/g,key =>{

                    this.replaceData( key,Pointer );

                    Pointer++ ;
                })
            },
            replaceData(key,Pointer){
                let { cycleTimeParam } = this;

                for(let i = cycleTimeParam.length-1 ; i>-1 ;i-- ){
                    if( cycleTimeParam[i].pointer === Pointer ){

                        Pointer === 3 && ( this.selectData = key === '?' ? WEEK : MONTH );

                        setTimeout(()=> cycleTimeParam[i].number =  key === '*' || key === '?' ?  '' : key ,0)
                    }
                }
            }
        }
    }

</script>
    