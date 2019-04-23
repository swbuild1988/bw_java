<template>
  <div>
    <ComfirmStep  v-bind:curModal="curModal" ></ComfirmStep>
    <Row>
        <Col span="2">
            <div class="begin">
                <Button type="success" @click="startPlan">启动预案</Button>
            </div>
        </Col>
        <!-- <div style="border: 1px solid red;height: 200px;width: 100px">
        </div> -->
        <div v-for="(step,index) in stepList " :key="index">
            <Col  :span="spanNum">
                <CustomStep v-bind="step" @getSwitchStatus="getSwitchStatus"></CustomStep>
            </Col>
        </div>
    </Row>
    <hr class="hr3"/>
    <Row>
        <Col span="12" style="position: relative;padding: 10px;">
            <div  :style="{height:moduleHeight*2+20+'px'}" class="coolBox">
                <Carousel v-bind="curCarousel"></Carousel>
            </div>
        </Col>
        <Col span="12" style="padding: 10px;">
            <Card :bordered="false" :style="{height:moduleHeight+'px'}" class="card">
                <p slot="title" style="font-size: 2vmin;">执行日志</p>
                <Row style="line-height: 3vmin;font-size: 1.66vmin;overflow-y: auto;">
                        <div v-for="item in processLog" :key="item.stepName">
                            <Col span="8">{{item.stepName}}</Col>
                            <Col span="5">{{item.statusStr}}</Col>
                            <Col span="3">{{item.user}}</Col>
                            <Col span="8">
                                <span style="position: relative;float: right;right: 5px;">{{item.time}}</span>
                            </Col>
                        </div>
                </Row>
            </Card>
        </Col>
        <Col span="12" style="padding: 10px;">
            <Card :bordered="false" :style="{height:moduleHeight+'px'}" class="card">
                <p slot="title" style="font-size: 2vmin;">设备列表</p>
                <Row style="line-height: 3vmin;font-size: 1.66vmin;overflow-y: auto;">
                    <div v-for="item in equipmentList" :key="item.name">
                        <Col span="12" style="padding: 10px;font-size: 1.8vmin;">
                        <span>{{item.name}}</span>
                        <div style="float: right;">
                            <img :src="item.url" style="width: 40px;">
                        </div>
                        <i-Switch  :value="item.cv" size="large" @on-change="changeStatus(item.id,item.cv)" style="float:right;margin-right: 50px;margin-top: 5px;">
                            <span slot="open">开</span>
                            <span slot="close">关</span>
                        </i-Switch>
                        </Col>
                    </div>
                </Row>
            </Card>
        </Col>
    </Row>
    <ShowStartPlan v-bind="showModal" v-on:startPlan="ExcutePlanStart"></ShowStartPlan>
  </div>
</template>

<script>
import  CustomStep from "../../../../components/UM/EM/CustomStep"
import  ComfirmStep from '../../../../components/UM/EM/ConfirmStep'
import  {PlanService} from '../../../../services/planService'
import  {MeasObjServer} from '../../../../services/MeasObjectSerivers.js'
import  ShowStartPlan from '../../../../components/Common/Modal/ShowStartPlan'
import  Carousel from '../../../../components/Common/Carousel.vue'
import  fenClose from '../../../../assets/UM/fanClose.png'
import  fenOpen from '../../../../assets/UM/fenOpen.gif'
import  closeLamp from '../../../../assets/UM/照明-关.png'
import  openLamp from '../../../../assets/UM/照明-开.png'
import  openBlinds from '../../../../assets/UM/百叶-开.png'
import  closeBlinds from '../../../../assets/UM/百叶-关.png'

  export default {

    name: "execute-plan",
    data() {
        return {
            moduleHeight:400,
            curCarousel:{
                videolist:[]
            },
            fenOpen:fenOpen,
            fenClose:fenClose,
            openLamp:openLamp,
            closeLamp:closeLamp,
            closeBlinds:closeBlinds,
            openBlinds:openBlinds,
            img:{open:"",close:""},
            getStatusInterval:null,
            openInterval:false,
            equipmentList:[],
            processLog:[],
            showModal: {
                modalPrams: {
                    state: false,
                    selectPlan:""
                },
            },
            curModal:{
                isShow:false,
                instanceId:null,
                objectId:null
            },
            stepList: null,
            // stepList: [
            // {stepName: "开启声光报警", statusStr: "待进行", statusVal: 3},
            // {stepName: "调用摄像头", statusStr: "待进行", statusVal: 3},
            // {stepName: "值班人员确认", statusStr: "待进行", statusVal: 3},
            // {stepName: "打开风机", statusStr: "待进行", statusVal: 3},
            // {stepName: "打开风阀", statusStr: "待进行", statusVal: 3},
            // {stepName: "打开百叶", statusStr: "待进行", statusVal: 3},
            // {stepName: "启动干粉灭火", statusStr: "待进行", statusVal: 3},
            // {stepName: "通知相关单位", statusStr: "待进行", statusVal: 3},
            // ],
            spanNum:0,
            queryPram: {
                processKey: null,
            },
            resultExcutePlan: null
        }
    },

    mounted() {
        this.queryPram.processKey = this.$route.params.processKey;
        // this.syncStep();
        this.getProcess();
    },

    computed: {
        planData: {
            get() {
                return this.$store.getters.getPlanData
            },
            set(value) {
                this.$store.commit('setPlanData', value)
            }
        }
    },

    watch: {
        '$route': function () {
            this.queryPram.processKey = this.$route.params.processKey;
            this.getProcess();
        }
    },

    methods: {
        startPlan() {
            var _this = this;
            _this.showModal.modalPrams.state = !_this.showModal.modalPrams.state;
            _this.showModal.modalPrams.selectPlan = _this.queryPram.processKey;
        },

        ExcutePlanStart(){
            var _this = this;
            _this.showModal.modalPrams.state = !_this.showModal.modalPrams.state;
            this.acceptPlanData()
        },

        // 连接成功回调函数
		callback(respond) {
			let _this = this;
			let result = JSON.parse(respond.body);
            //将数据保存在vuex中
            this.resultExcutePlan = result
			if (_this.planData &&"/UM/plans/execute/processKey" !=_this.$router.history.current.path){
            }
            if(this.resultExcutePlan){
                this.resultExcutePlan.process.map(item => {
                    this.stepList.map(element => {
                        if(item.node==element.stepName){
                            element.curStatusStr = '已完成'
                        }
                    })
                    this.stepList = this.stepList
                })
            }
		},
		//获取MQ推送的预案消息
		acceptPlanData() {
			var _this = this;
			_this.MQ._InitMQ(1, "/queue/QUEUE_PLAN_UM", "", _this.callback);
			this.planQueue = _this.MQ.client;
		},

        //获取设备状态图片
        getStatusUrl(dataTypeId,cv){
            var _this=this;
            var imgUrl="";
            switch(dataTypeId){
                // 灯
                case 11:{
                    if(cv>0){
                        imgUrl=_this.openLamp;
                    }
                    else {
                        imgUrl=_this.closeLamp;
                    }
                    return  imgUrl;
                }
                //风机
                case 10:{
                    if(cv>0){
                        imgUrl=_this.fenOpen;
                    }
                    else {
                        imgUrl=_this.fenClose;
                    }
                    return  imgUrl;
                }
                //百叶
                case 58:{
                    if(cv>0){
                        imgUrl=_this.openBlinds;
                    }
                    else {
                        imgUrl=_this.closeBlinds;
                    }
                    return  imgUrl;
                }

            }
        },

        // 变更设备状态
        changeStatus(id,cv){
            var _this=this;
            var params={id:id,status:cv};
            _this.equipmentList.forEach(a=>{
                if(a.id==id){
                    a.status=!a.status;
                    a.cv=(a.cv==0?1:0)
                    a.url =  _this.getStatusUrl(a.objtypeId,a.cv)
                }
            })
            MeasObjServer.changeEquimentStatus(params).then();

        },


        // 获取打开开关后的状态跟新
        getSwitchStatus(temp){
            var _this=this;
            var temp={};
            temp.stepName=temp
            temp.statusStr="已完成";
            temp.user=sessionStorage.UMUerName;
            temp.time=new Date().format("yyyy-MM-dd hh:mm:ss");
            _this.processLog.push(temp);
            if(_this.openInterval){
                _this.getStatusInterval=setInterval(()=>{
                    // 获取打开风机或其他设备的数据
                },4000);
            }
        },

        // 获取预案步骤
        getProcess() {
            let _this = this;
            PlanService.getAllSteps(_this.queryPram.processKey).then((result) => {
                if(result){
                    _this.stepList=[];
                    let tempLen=result.planStatus.length;
                    _this.spanNum = tempLen > 3?((tempLen > 6) ? 4 : 3):7;
                    result.planStatus.forEach((step,index)=>{
                        var temp={};
                        temp.curIndex=index+1;
                        temp.stepName=step.stepName;
                        temp.curStatusStr=step.statusStr;
                        temp.curStatusVal=step.statusVal;
                        // temp.switBtn={isSwitch: true, status: false, mesaObjsId: []};
                        temp.stepLength=tempLen;
                        _this.stepList.push(temp);
                    })
                    console.log("_this.stepList", _this.stepList)
                    if(_this.stepList.length>5){
                        _this.moduleHeight=window.innerHeight*0.29-10;
                    }
                    else {
                        _this.moduleHeight=window.innerHeight*0.35-10;
                    }
                }
            })
        },

        //切换路由
        goToMoudle(path) {
            this.$router.push(path);
        },

        syncStep() {
            var _this = this;
            if (_this.planData) {
                if(_this.curCarousel.videolist.length==0){
                    _this.curCarousel.videolist=_this.planData.videos
                }
                _this.processLog=[];
                _this.equipmentList=[];
                _this.planData.process.forEach((a,index)=>{
                    if (a.status == 1) {
                        _this.stepList[index].curStatusStr= "已完成"
                        _this.stepList[index].curStatusVal= 1;
                        _this.stepList[index].isSwitch= a.isSwitch;
                        if(a.objectList.length>0){
                            a.objectList.forEach(b=>{
                                let temp = b;
                                temp.status=b.cv>0?true:false;
                                temp.url =  _this.getStatusUrl(b.objtypeId,b.cv)
                                _this.equipmentList.push(temp);
                            })
                        }
                    }
                    var log={};
                    log.stepName=a.node;
                    log.statusStr=  _this.stepList[index].curStatusStr;
                    log.user=sessionStorage.UMUerName;
                    log.time=new Date().format("yyyy-MM-dd hh:mm:ss");
                    _this.processLog.push(log);
                })
            }
        },
    },

    components: {CustomStep,ComfirmStep,ShowStartPlan,Carousel},
  }
</script>

<style scoped>
    .begin{
        padding-top: 2vmin;
        padding-left: 1vmin;
        width: 11vmin;
    }

    .border {
        box-shadow: 5px 5px 15px #cdf5ff inset;
        border: 1px solid #d9fff1;
        border-radius: 8px;
        color: #fff;
    }

    .hr3 {
        height: 7px;
        border: none;
        border-top: 5px ridge #45D6D8;
    }

    .hr1 {
        margin: 0 auto;
        border: 0;
        height: 5px;
        background: #333;
        background-image: linear-gradient(to right, #ccc, #333, #ccc);
    }

    .ivu-steps >>> .ivu-steps-title {
        font-size: 18px;
    }

    .ivu-steps >>> .ivu-steps-content {
        font-size: 18px;
    }

    .coolBox {
        border: 1px solid rgba(0, 0, 0, 0.2);
        -moz-border-radius: 5px;
        -webkit-border-radius: 5px;
        border-radius: 5px;
        background-clip: padding-box;
        background: rgba(36, 40, 42, 0.5);
        box-shadow: 0 0 13px 3px rgba(0, 0, 0, 0.5);
    }

    .card >>> .ivu-card-head p{
        height: 2vmin;
        line-height: 2vmin;
    }
</style>
