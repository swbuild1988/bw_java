<template>
    <div :id="id" v-cancellation class="h5StreamVideo">
        <!-- <video :id="id" class="videos" autoplay webkit-playsinline playsinline @dblclick="fullScreen"></video> -->
        <div v-if="isTextShow" class="text">
            <p style="padding: 10%">{{text}}</p>
        </div>
        <div
            :class="['embedControl', controlFlag ? '' : 'trsp']"
            @mousemove="show"
            @mouseout="hide"
            ref="embedControl"
        >
            <embeded-control v-bind:camera="video" v-bind:isShow="controlFlag"></embeded-control>
        </div>
        <!-- <div class="playPause"></div> -->
    </div>
</template>

<script>
import embededControl from "../../UM/MAM/videoControls/embededControl";
import { H5StreamPlugIn } from "./h5StreamPlugIn";

export default {
    name: "h5-stream-video",
    props: {
        video: {
            type: Object,
            required: true
        },
        id: {
            required: true
        },
        text: {
            required: false
        },
        index: {
            required: true
        }
    },
    watch:{
    },
    directives: {
        // 组件销毁前事件
        cancellation: {
            unbind(els) {
                // 将video元素移开
                let elementChild = [].slice.call(els.children);
                let [video] = elementChild.filter(el => el.nodeName == "VIDEO");
                console.info("remove video", video);
                if(video){
                    H5StreamPlugIn.clearVideoElement(video);
                }
            }
        }
    },
    data() {
        return {
            curVideo: null,
            controlFlag: false,
            isTextShow: false,
            videoId: ""
        };
    },
    components: {
        embededControl
    },
    computed: {
        config() {
            return {
                videoid: this.videoId,
                protocol: window.location.protocol, //http: or https:
                host: this.video.url,
                rootpath: "/", // '/' or window.location.pathname
                token: this.video.id,
                hlsver: "v1", //v1 is for ts, v2 is for fmp4
                session: "c1782caf-b670-42d8-ba90-2244d0b0ee83" //session got from login
            };
        }
    },
    mounted() {
        this.Log.info("h5stream mounted!");
        this.getVideoDom();
        this.isTextShow = this.text ? true : false;
    },
    updated() {
        this.disconnectVideo();
        // this.connectVideo(this.config);
        this.getVideoDom();
    },
    beforeDestroy() {
        this.disconnectVideo();
    },
    methods: {
        getVideoDom() {
            // 获取dom
            let videoDom = H5StreamPlugIn.getVideoElement(this.index);
            document.getElementById(this.id).appendChild(videoDom);
            this.addDblClick(videoDom);
            // 获取组件的id
            this.videoId = videoDom.id;
            this.Log.info("videoId:", this.videoId);
            this.connectVideo(this.config);
        },
        connectVideo(config) {
            if (this.config.token) {
                this.curVideo = H5sPlayerCreate(config);
                this.curVideo.connect();
            }
        },
        disconnectVideo() {
            if (!this.curVideo) return;
            this.curVideo.disconnect();
            delete this.curVideo;
            this.curVideo = null;
        },
        show() {
            let width = document.getElementsByClassName("h5StreamVideo")[0]
                .offsetWidth;
            let height = document.getElementsByClassName("h5StreamVideo")[0]
                .offsetHeight;
            if (width > 800 && height > 800 && this.video.positionSupport) {
                this.controlFlag = true;
                let height = document.getElementsByClassName("embedControl")[0]
                    .offsetHeight;
                document.getElementsByClassName("embedControl")[0].style.width =
                    height + "px";
            }
        },
        hide() {
            this.controlFlag = false;
        },
        addDblClick(videoDom) {
            videoDom.addEventListener("dblclick", function() {
                H5StreamPlugIn.fullScreen(videoDom.id);
                let fullScreenStyle = document.createElement("style");
                fullScreenStyle.innerText =
                    ".videos::-webkit-media-controls-panel{display: none;}";
                document.body.appendChild(fullScreenStyle);
            });
        }
    }
};
</script>

<style scoped>
.h5StreamVideo {
    position: relative;
    width: 100%;
    height: 100%;
}
.playPause {
    background-image: url("../../../assets/UM/media_play_pause_resume.png");
    background-repeat: no-repeat;
    width: 25%;
    height: 25%;
    position: absolute;
    left: 0%;
    right: 0%;
    top: 0%;
    bottom: 0%;
    margin: auto;
    background-size: contain;
    background-position: center;
}
.videos {
    width: 100%;
    height: 100%;
    border: 1px solid black;
    background-color: #000000;
    object-fit: fill;
    position: absolute;
    left: 0;
    top: 0;
    z-index: 1;
}
.videos::-webkit-media-controls-panel {
    display: none;
}

/* 全屏显示样式  */
/*:-webkit-full-screen{ 
    max-width: 600px !important;
    max-height: 400px !important;
    background-color: red;
}
*/
/*:-webkit-full-screen video{ 
    max-width: 600px;
    max-height: 400px;
}*/
:fullscreen {
    background-color: rgba(0, 0, 0, 0.1);
}
::backdrop {
    position: relative;
    width: 100%;
    height: 100%;
}

.embedControl {
    position: absolute;
    height: 30%;
    min-height: 100px;
    min-width: 98px;
    max-width: 240px;
    max-height: 240px;
    /* width: 98px;
    height: 100px;*/
    bottom: 0;
    left: 50%;
    transform: translate(-70%);
    z-index: 999;
}
.trsp {
    opacity: 0;
}
.text {
    position: absolute;
    top: 0;
    left: 0;
    width: 40%;
    height: 20%;
    /* background-color: white;*/
    z-index: 999;
    font-size: 20px;
    /*text-align: center;*/
    color: red;
}
</style>


