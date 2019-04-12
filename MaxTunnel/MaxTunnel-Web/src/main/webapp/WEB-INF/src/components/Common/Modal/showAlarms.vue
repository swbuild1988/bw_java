<template>
    <Modal v-model="modalPrams.state" :width="modalWidth" title="告警信息">
        <section class="titleSection">
            <article>
                <h2>{{modalPrams.modalInfo.objectName}}</h2>
                <h3>{{modalPrams.modalInfo.alarmName}}</h3>
            </article>    
        </section>  
        <section class="videoSection">
            <h4>关联视频</h4>
            <Row :gutter="16">
                <Col :span="videoSpan" v-for="(item,index) in modalPrams.modalInfo.videos" :key="index" style="margin-top: 16px">
                    <div class="videoContainer" :class="{ oneScreen: videoSpan == 24, monitor: videoSpan == 12, fourMonitor: videoSpan ==8 }">
                        <video-component v-bind:video="item" v-bind:id="'camera'+item.id"></video-component>
                    </div>
                </Col>
            </Row>    
        </section>  
        <section class="extremeSection">
            <h4>极值</h4>
            <Row :gutter="16">
                <Col span="12" v-for="(item, index) in modalPrams.modalInfo.cvList" :key="index">
                    <Col span="12">名称：{{item.key}}</Col>
                    <Col span="12">极值：{{item.val}}{{item.unit}}</Col>
                </Col>
            </Row>
        </section>
        <section class="planSection">
            <h4>预案</h4>
            <Row>
                <Col span="8">
                    <Button type="default" @click="cancelPlan()">取消</Button>
                </Col>
                <Col span="8" v-for="(item, index) in modalPrams.modalInfo.plans" :key="index">
                    <Button type="primary" @click="showStep(modalPrams.modalInfo.sectionId,item.id,item.processKey)">{{item.name}}</Button>
                </Col>
            </Row>
        </section>
        <section class="detailSection" v-show="isShowStep">
            <h4>预案步骤</h4>
            <h2>{{planStepData.processName}}</h2>
            <article>
                <Row>
                    <Col span="24" v-for="(item, index) in planStepData" :key="index">
                        <Col span="10" class="stepNum">step{{index+1}}：</Col>
                        <Col span="5" class="stepName">{{item.stepName}}</Col>
                        <Col span="8" class="stepStatus">{{item.statusStr}}</Col>
                    </Col>
                </Row>
                <div >
                    <div class="stepNum"></div>
                    <div class="stepName"></div>
                </div>
            </article>
        </section>
    </Modal>    
</template>

<script>
import videoComponent from '../Video/VideoComponent.vue'
export default {
    components: {videoComponent},
    props: {
        modalPrams: {
            type: Object,
            default: function () {
                return {state: false, modalInfo: null, planData: null};
            }
        }
    },
    data() {
        return {
            curVideo: null,
            videoNum:0,
            modalWidth: 600,
            videoSpan: 0,
            isShowStep: false,
            planStepData: []
        }
    },
    watch: {
        'videoNum'() {
            this.setVideoSpan();
        },
        'modalPrams.planData': function(newVal, oldVal){
            this.showStepDetails()
        }
    },
    mounted() {
        this.setVideoSpan();
    },
    methods: {
        reflashVideo(){
            var _this = this;
            _this.videoNum = _this.modalPrams.modalInfo.videos.length;
            _this.setVideoSpan();
        },
        setVideoSpan() {
            let _this = this;
            _this.videoSpan = 0;
            if (_this.videoNum == 1) {
                _this.videoSpan = 24;
            }
            if (_this.videoNum > 1 && _this.videoNum <= 4) {
                _this.videoSpan = 12;
            }
            if (_this.videoNum > 4) {
                _this.videoSpan = 8;
            }
        },
        //点击预案名称显示预案步骤
        showStep(sectionId,processValue,processKey){
                this.axios.get("emplans/process-key/"+processKey).then(res=>{
                    let{code, data} = res.data
                    if( code == 200 ){
                        this.axios.post("/emplans/start", {sectionId: sectionId,processValue: processValue}).then()
                        this.isShowStep = !this.isShowStep
                        // this.$Message.info("预案开始执行")
                    }else{
                        this.Log.info()
                    }
                })
        },
        showStepDetails(){
            if(this.modalPrams.planData!=null){
                console.log("this.planStepData.nodeList", this.modalPrams.planData.nodeList)
                console.log("process", this.modalPrams.planData.process)
                this.modalPrams.planData.process.map(item => {
                    if(item.status==1){
                        this.modalPrams.planData.nodeList.map(element=>{
                            if(item.node == element.stepName){
                                element.statusStr = '已完成'
                            }
                        })
                    }
                    this.planStepData = this.modalPrams.planData.nodeList
                    // this.$Message.info("预案执行完毕")
                })
            }
        },
        //点击取消预案
        cancelPlan(){
            this.isShowStep = false
        }
    },
}
</script>

<style>
section{
    border: 1px solid #ccc;
    border-radius: 8px;
    margin-bottom: 2vmin;
    padding: 1vmin;
    -webkit-box-shadow: 0px 3px 3px #c8c8c8;
    -moz-box-shadow: 0px 3px 3px #c8c8c8;
    box-shadow: 0px 3px 3px #c8c8c8;
}
.titleSection h2,.detailSection h2{
    text-align: center;
}
.titleSection h3{
    text-align: right;
}
.ivu-modal-body{
    max-height: 55vh;
    overflow-y: auto;
}
.extremeSection .ivu-col.ivu-col-span-12 .ivu-col.ivu-col-span-12{
    line-height: 3.6vmin;
    background: #e0e0e0;
    margin-bottom: 5px;
    padding-left: 15px;
}
.videoContainer{
    height: 20vh;
}
.monitor {
    height: 20vh;
    width: 100%;
}
.fourMonitor {
    height: 33vh;
}
.oneScreen {
    height: 66vh;
}
.planSection .ivu-col.ivu-col-span-8{
    text-align: center;
    margin-bottom: 1vmin;
}
.detailSection *{
    line-height: 3.6vmin;
}
.detailSection .stepNum{
    text-align: right
}
/* .detailSection .stepStatus{
    text-align: center;
} */
</style>
