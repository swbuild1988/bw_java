<template>
    <Modal class="forBG" v-model="modalPrams.state" :width="modalWidth" title="告警信息">
        <section class="setionBox" v-for="(item,index1) in alarms" :key="index1">   
            <!-- title -->
            <section class="titleSection">
                <article>
                    <h2>{{item.location}}-{{item.objectName}}</h2>
                    <h3>{{item.alarmName}}</h3>
                </article>
            </section>
            <div class="box">
                <div class="column">
                    <!-- video -->
                    <section class="videoSection item">
                        <h4>关联视频</h4>
                        <div style="height: 100%">
                            <CarouselVideo :videolist="item.videos" :isloop='false' :preIndex="'modal_alamr'+index1+'_video'"></CarouselVideo>
                        </div>
                    </section>
                    <div class="item">
                        <div class="rightBox">
                            <!-- 极值 -->
                            <section class="extremeSection">
                                <h4>极值</h4>
                                <Row :gutter="16">
                                    <Col span="24" v-for="(temp, index) in item.cvList" :key="index">
                                        <Col span="12">{{temp.key}}：</Col>
                                        <Col span="12">{{temp.val}}{{temp.unit}}</Col>
                                    </Col>
                                </Row>
                            </section>
                            <!-- 预案 -->
                            <section class="planSection">
                                <h4>预案</h4>
                                <Row>
                                    <Col span="12">
                                        <Button type="default" @click="cancelPlan()">取消</Button>
                                    </Col>
                                    <Col span="12" v-for="(ele, index) in item.plans" :key="index">
                                        <Button type="primary" @click="startPlan(item, ele.id, ele.processKey)">{{ele.name}}</Button>
                                    </Col>
                                </Row>
                            </section>
                        </div>
                    </div>
                </div>
                <div class="column">
                    <!-- 执行步骤 -->
                    <section class="item" v-if="item.plan">
                        <h4>预案步骤</h4>
                        <div class="detailSection"  style="height:69vmin;max-width:54vmin">
                            <image-from-url :url="item.plan.processPicSrc"></image-from-url>
                        </div>
                    </section>
                </div>
            </div>
        </section>
    </Modal>
</template>

<script>
    import videoComponent from '../Video/VideoComponent.vue'
    import ImageFromUrl from "../../Common/ImageFromUrl"
    import UmLayoutBg from '@/assets/UM/UmLayoutBg.png'
    import CarouselVideo from '@/components/Common/Carousel.vue'
    export default {
        components: {
            videoComponent,
            ImageFromUrl,
            CarouselVideo
        },
        props: {
            modalPrams: {
                type: Object,
                default: function () {
                    return {
                        state: false,
                        planData: null
                    };
                }
            },
            alarmContainer: {
                type: Array
            }
        },
        data() {
            return {
                curVideo: null,
                videoNum: 0,
                modalWidth: 600,
                videoSpan: 0,
                planStepData: [],
                cvList: [{
                        key: '温度',
                        val: '24',
                        unit: '℃'
                    },
                    {
                        key: '温度',
                        val: '24',
                        unit: '℃'
                    }, {
                        key: '温度',
                        val: '24',
                        unit: '℃'
                    }, {
                        key: '温度',
                        val: '24',
                        unit: '℃'
                    }, {
                        key: '温度',
                        val: '24',
                        unit: '℃'
                    }
                ],
                alarms: null,
                alarmVideoList: [],
                alarmPerIndex: ''
            }
        },
        computed: {
            containerStyle(){
                return {
                    transform:`translate3d(${this.distance}vw, 0, 0)`
                }
            }
        },
        watch: {
            'videoNum'() {
                this.setVideoSpan();
            }
        },
        mounted() {
            this.modalWidth = window.innerWidth * 0.6
            this.alarms = this.alarmContainer
            this.setVideoSpan();
        },
        methods: {
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
            startPlan(alarm, processValue, processKey) {

                let _this = this;
                // 启动预案
                this.axios.post("/emplans/start", {
                    sectionId: alarm.sectionId,
                    processValue: processValue
                }).then(res => {
                    _this.Log.info("收到启动预案结果：", res)
                    this.$set(alarm,'plan',{
                        processInstanceId: res.data.data,
                        status: "预案已启动",
                        isFinished: false,
                        processPicSrc: null
                    })

                    // 启动预案监听
                    _this.acceptPlanData();
                    // _this.Log.info("alarmContainer", _this.alarmContainer);
                });

            },
            // 连接成功回调函数
            callback(data) {
                let result = JSON.parse(data);
                if (result.type && result.type == "Plan") {
                    let content = JSON.parse(result.content);
                    this.Log.info("ShowAlarm收到预案回调", content)

                    for (const alarm of this.alarmContainer) {
                        // 没启动预案，直接过
                        if (!alarm.plan) continue;

                        // this.Log.info("alarmContainer1", this.alarmContainer);
                        // 找到收到预案消息对应的告警
                        let curPlan = alarm.plan
                        if (curPlan.processInstanceId == content.processInstanceId) {
                            this.$set(curPlan,'processPicSrc',null)
                            curPlan.isFinished = content.status == "finished";
                            curPlan.status = content.status == "finished" ? "预案已完成" : "预案进行中";
                            let _this = this
                            this.$nextTick(() => {
                                _this.$set(curPlan,'processPicSrc',"/emplans/png/" + content.processInstanceId)
                            });
                        }
                        // this.Log.info("alarmContainer2", this.alarmContainer);
                        if (curPlan.isFinished) {
                            // 如果所有的启动了的预案都已经结束了，关掉预案
                            if (this.isAllAlarmsPlanFinished()) this.stopPlan();
                        }
                        // this.Log.info("alarmContainer3", this.alarmContainer);
                    }
                }
            },
            // 判断所有的告警的预案是不是都已经结束了
            isAllAlarmsPlanFinished() {
                for (const alarm of this.alarmContainer) {
                    if (alarm.plan && !alarm.plan.isFinished) return false;
                }
                return true;
            },
            //获取MQ推送的预案消息
            acceptPlanData() {
                // 如果已经有监听了，就不要坚挺了
                if (this.TransferStation.getListener("ShowAlarm")) return;
                this.Log.info("添加监听器到MQ")
                this.TransferStation.addListener("ShowAlarm", this.callback);
                this.nodePicState = 1;
            },
            // 预案结束，停止监听
            stopPlan() {
                this.Log.info("移除监听器")
                this.TransferStation.deleteListener("ShowAlarm");
            },
            //点击取消预案
            cancelPlan() {
                // 关掉这个告警
            }
        }
    }
</script>

<style scoped>
    .setionBox,.videoSection,.extremeSection, .planSection, .detailSection {
        margin-bottom: 2vmin;
        padding: 1vmin;
    }

    .setionBox{
        padding: 1vmin;
        border: 1px solid #ffffff;
        border-radius: 4px;
    }

    .titleSection{
        border: none;
    }

    .titleSection h2,
    .detailSection h2 {
        text-align: center;
    }

    .titleSection h3 {
        text-align: right;
    }

    .ivu-modal-body {
        max-height: 55vh;
        overflow-y: auto;
    }

    .forBG>>>.ivu-modal-content{
        background: url("../../../assets/UM/alarmBG.png") no-repeat;
        background-size: 100% 100%;
        color: #ffffff;
    } 
    .forBG>>>.ivu-modal-header-inner,.forBG>>>.ivu-modal-close .ivu-icon-ios-close-empty{
        color: #ffffff;
    }    
    /* 图片1轮播 */
    .videoSection{
        height: 40vh;
        width: 100%;
    }
    .planSection .ivu-btn-primary{
        background: linear-gradient(to left, #2734e1, #b195ed);
        border: none;
    }
    .planSection .ivu-btn-default{
        background: linear-gradient(to left, #8241a2, #bb92bae0);
        color: #fff;
        border: none;
    }
    .box {
        display: flex;
        flex-wrap: wrap;
        align-content: space-between;
    }

    .column {
        flex-basis: 100%;
        display: flex;
        justify-content: space-between;
    }
    .rightBox{
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: flex-start;
    }
    .extremeSection,.planSection{
        height: 20vh;
        overflow-y: auto;
        overflow-x: hidden;
        width: 100%;
    }
    .extremeSection .ivu-row .ivu-col.ivu-col-span-24{
        line-height: 3.5vmin;
    }
    .planSection .ivu-row .ivu-col.ivu-col-span-12{
        line-height: 7vmin;
    }
</style>