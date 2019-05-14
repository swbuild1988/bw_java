<template>
    <div class="videosContent">
        <button type="button" class="commonButton left video-arrow-hover" @click="arrowEvent(-videoNumber)">
            <Icon type="ios-arrow-back"></Icon>
        </button>
        <Row style="height: 100%">
            <Col :span="videoNumber === 1 || videoNumber === 2 ? '24' : '12'" v-for="(video,index) in curVideoList"
                :key="video.id" :style="{height: wrapperHeight}">
            <div :class="['videoWrapper',{'oneVideo' : videoNumber === 1}]">
                <video-component :video="video" :index="index" :id="'curLoopSceneVideo'+video.id"></video-component>
            </div>
            </Col>
        </Row>
        <button type="button" class="commonButton right video-arrow-hover" @click="arrowEvent(+videoNumber)">
            <Icon type="ios-arrow-forward"></Icon>
        </button>
    </div>

</template>
<script>
    import videoComponent from './Video/VideoComponent'

    export default {
        props: {
            videolist: {
                type: Array,
                default: [],
            },
            isloop: {
                type: Boolean,
                default: true,
            },
            loopSpeed: {
                type: Number,
                default: 10, //默认 s
            },
            videoNumber: {
                type: Number,
                default: 1
            }
        },
        data() {
            return {
                currVideoObj: {
                    id: null,
                    url: ''
                },
                curVideoList: [],
                loopProp: {
                    loopFirstIndex: 0,
                    loopTimer: null
                },
                wrapperHeight: '100%'
            }
        },
        components: {
            videoComponent
        },
        watch: {
            'videolist': {
                handler(newValue, oldValue) {
                    this.changeVideo();
                },
                deep: true,
            }
        },
        mounted() {
            this.Log.info("Carousel create");
            this.changeVideo();
            this.wrapperHeight = this.videoNumber == 1 ? '100%' : '50%'
        },
        methods: {
            changeVideo() {
                let {
                    loopProp
                } = this;

                this.gainVideo(loopProp.loopFirstIndex); //获取视屏
                this.loopVideo(); //轮询视屏

            },
            loopVideo() {
                clearInterval(this.loopProp.loopTimer); //清除定时器

                if (this.isloop) {
                    this.loopProp.loopTimer = setInterval(() =>
                        this.gainVideo(this.videoNumber),
                        this.loopSpeed * 1000)
                }

            },
            arrowEvent(offset) {
                this.loopVideo();
                this.gainVideo(offset); //获取视屏
            },
            gainVideo(offset) {

                console.log("视频轮询组件，获取视频")

                let newLoopFirstIndex = this.loopProp.loopFirstIndex + offset;

                this.loopProp.loopFirstIndex = newLoopFirstIndex < 0 ?
                    this.videolist.length - this.videoNumber :
                    (newLoopFirstIndex > this.videolist.length - 1 ?
                        0 : newLoopFirstIndex)

                let newLoopLastIndex = this.loopProp.loopFirstIndex + this.videoNumber > this.videolist.length ?
                    this.videolist.length : this.loopProp.loopFirstIndex + this.videoNumber

                // this.currVideoObj = this.videolist[ this.loopProp.loopIndex ];
                this.curVideoList = this.videolist.slice(this.loopProp.loopFirstIndex, newLoopLastIndex)
            }
        },
        beforeDestroy() {
            this.isloop = false;
            clearInterval(this.loopProp.loopTimer); //清除定时器
        },
    }
</script>
<style scoped>
    .videosContent {
        width: 100%;
        height: 100%;
    }

    .videosContent .commonButton {
        position: absolute;
        z-index: 1001;
        border-radius: 50%;
        top: 50%;
        transition: .2s;
        color: #fff;
        text-align: center;
        font-family: inherit;
        line-height: inherit;
    }

    .videosContent .left {
        left: 2%;
        transform: translateY(-50%);
    }

    .videosContent .right {
        right: 2%;
        transform: translateY(-50%);
    }

    .video-arrow-hover {
        display: inherit;
        opacity: 0;
    }

    .videosContent .video-arrow-hover:hover {
        background-color: rgba(255, 255, 255, .5);
        opacity: 1;
    }

    .videoWrapper {
        width: 94%;
        height: 96%;
        margin: 0.4vmin auto;
    }

    .oneVideo {
        height: 100%;
    }


    /* 小屏幕（显示器，小于等于 1920px） */
    @media (max-width: 1920px) {
        .videosContent .commonButton {
            width: 6%;
            height: 8%;
            font-size: 1rem;
        }
    }

    /* 大屏幕（显示器，大于等于 1920px） */
    @media (min-width: 1921px) {
        .videosContent .commonButton {
            width: 10%;
            height: 11%;
            font-size: 3rem;
        }
    }
</style>