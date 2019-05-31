<template>
    <Modal v-model="modalPrams.state" :width="modalWidth" title="告警信息">
        <section v-for="(item,index1) in alarms" :key="index1">
            <section class="titleSection">
                <article>
                    <h2>{{item.objectName}}</h2>
                    <h3>{{item.alarmName}}</h3>
                </article>
            </section>
            <section class="videoSection">
                <h4>关联视频</h4>
                <Row :gutter="16">
                    <Col :span="videoSpan" v-for="(element,index2) in item.videos" :key="index2"
                        style="margin-top: 16px">
                    <div class="videoContainer"
                        :class="{ oneScreen: videoSpan == 24, monitor: videoSpan == 12, fourMonitor: videoSpan ==8 }">
                        <video-component :index="'modal_alarm'+index1+'_video'+index2" :video="element"
                            :id="'camera'+element.id"></video-component>
                    </div>
                    </Col>
                </Row>
            </section>
            <section class="extremeSection">
                <h4>极值</h4>
                <Row :gutter="16">
                    <Col span="12" v-for="(temp, index) in item.cvList" :key="index">
                    <Col span="12">名称：{{temp.key}}</Col>
                    <Col span="12">极值：{{temp.val}}{{temp.unit}}</Col>
                    </Col>
                </Row>
            </section>
            <section class="planSection">
                <h4>预案</h4>
                <Row>
                    <Col span="8">
                    <Button type="default" @click="cancelPlan()">取消</Button>
                    </Col>
                    <Col span="8" v-for="(ele, index) in item.plans" :key="index">
                    <Button type="primary" @click="startPlan(item, ele.id, ele.processKey)">{{ele.name}}</Button>
                    </Col>
                </Row>
            </section>
            <section class="detailSection" v-if="item.plan">
                <h4>预案步骤</h4>
                <div style="height:69vmin;max-width:54vmin">
                    <image-from-url :url="item.plan.processPicSrc"></image-from-url>
                </div>
            </section>
        </section>
    </Modal>
</template>

<script>
    import videoComponent from '../Video/VideoComponent.vue'
    import ImageFromUrl from "../../Common/ImageFromUrl"
    export default {
        components: {
            videoComponent,
            ImageFromUrl
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
                alarms: null
            }
        },
        watch: {
            'videoNum'() {
                this.setVideoSpan();
            }
        },
        mounted() {
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
        },
    }
</script>

<style>
    section {
        border: 1px solid #ccc;
        border-radius: 8px;
        margin-bottom: 2vmin;
        padding: 1vmin;
        -webkit-box-shadow: 0px 3px 3px #c8c8c8;
        -moz-box-shadow: 0px 3px 3px #c8c8c8;
        box-shadow: 0px 3px 3px #c8c8c8;
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

    .extremeSection .ivu-col.ivu-col-span-12 .ivu-col.ivu-col-span-12 {
        line-height: 3.6vmin;
        background: #e0e0e0;
        margin-bottom: 5px;
        padding-left: 15px;
    }

    .videoContainer {
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

    .planSection .ivu-col.ivu-col-span-8 {
        text-align: center;
        margin-bottom: 1vmin;
    }

    .detailSection * {
        line-height: 3.6vmin;
    }

    .detailSection .stepNum {
        text-align: right
    }

    /* .detailSection .stepStatus{
    text-align: center;
} */
</style>