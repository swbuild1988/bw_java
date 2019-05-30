<template>
    <Modal v-model="modalPrams.state" :width="modalWidth" title="告警信息">
        <section v-for="(item,index1) in alarmContainer" :key="index1">
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
                    <Button type="primary"
                        @click="startPlan(item.sectionId, ele.id, ele.processKey)">{{ele.name}}</Button>
                    </Col>
                </Row>
            </section>
            <section class="detailSection" v-show="isShowPlan">
                <h4>预案步骤</h4>
                <h2>{{planStepData.processName}}</h2>
                <div style="height:69vmin;max-width:54vmin">
                    <image-from-url :url="processPicSrc"></image-from-url>
                </div>
            </section>
        </section>
    </Modal>
</template>

<script>
    import videoComponent from '../Video/VideoComponent.vue'
    export default {
        components: {
            videoComponent
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
                type: Object
            }
        },
        data() {
            return {
                processPicSrc: null,
                curVideo: null,
                videoNum: 0,
                modalWidth: 600,
                videoSpan: 0,
                isShowPlan: false,
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
                ]
            }
        },
        watch: {
            'videoNum'() {
                this.setVideoSpan();
            }
        },
        mounted() {
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
            startPlan(sectionId, processValue, processKey) {

                // 启动预案监听
                this.acceptPlanData();
                // 启动预案
                this.axios.post("/emplans/start", {
                    sectionId: sectionId,
                    processValue: processValue
                }).then(res => {
                    this.$Message.info("预案执行完成")
                }).finally(() => {
                    this.stopPlan();
                });
                this.isShowPlan = !this.isShowPlan;

            },
            // 连接成功回调函数
            callback(data) {
                this.Log.info("ShowAlarm", data)
                let result = JSON.parse(data);
                if (result.type && result.type == "Plan") {
                    let content = JSON.parse(result.content);
                    this.Log.info("ShowAlarm", content)
                    this.processPicSrc = null;
                    let _this = this;
                    this.$nextTick(() => {
                        _this.processPicSrc =
                            "/emplans/png/" + content.processInstanceId;
                    });
                }
            },
            //获取MQ推送的预案消息
            acceptPlanData() {
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
                this.isShowPlan = false
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