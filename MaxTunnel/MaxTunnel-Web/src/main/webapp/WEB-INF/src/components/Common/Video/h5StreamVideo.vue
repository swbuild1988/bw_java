<template>
    <div class="h5StreamVideo">
        <video :id="id" class="videos" autoplay webkit-playsinline playsinline @dblclick="fullScreen"></video>
        <div v-if="isTextShow" class="text">
            <p style="padding: 10%">{{text}}</p>
        </div>
        <div :class="['embedControl', controlFlag ? '' : 'trsp']" @mousemove="show" @mouseout="hide" ref="embedControl">
            <embeded-control v-bind:camera = "video" v-bind:isShow="controlFlag"></embeded-control>
        </div>
        <!-- <div class="playPause"></div> -->
    </div>
</template>

<script>
import embededControl from '../../UM/MAM/videoControls/embededControl'

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
        }
    },
    data() {
        return {
            curVideo: null,
            controlFlag: false,
            isTextShow: false
        };
    },
    components: {
        embededControl
    },
    computed: {
        config() {
            return  {
                videoid: this.id,
                protocol: window.location.protocol, //http: or https:
                host: this.video.url,
                rootpath: "/", // '/' or window.location.pathname
                token: this.video.id,
                hlsver: "v1", //v1 is for ts, v2 is for fmp4
                session: "c1782caf-b670-42d8-ba90-2244d0b0ee83" //session got from login
            }
        }
    },
    watch: {
        'config':function(newValue,oldValue){
            if(this.curVideo){
                this.curVideo.disconnect();
                this.curVideo = null;
                this.curVideo = H5sPlayerCreate(newValue);
                this.curVideo.connect();
            } 
        },
        deep: true
    },
    mounted() {
        this.Log.info("curVideo Config:", this.config);
        this.curVideo = H5sPlayerCreate(this.config);
        this.curVideo.connect(); 
        if(this.text){
            this.isTextShow = true
        }
        // this.resetSize()
    },
    beforeDestroy() {
        this.curVideo.disconnect();
        this.curVideo = null;
    },
    methods: {
        fullScreen(){
            let requestFullscreen =
                document.body.requestFullscreen ||
                document.body.webkitRequestFullscreen ||
                document.body.mozRequestFullScreen ||
                document.body.msRequestFullscreen;
            let fullscreenEnabled =
                document.fullscreenEnabled ||
                document.mozFullScreenEnabled ||
                document.webkitFullscreenEnabled ||
                document.msFullscreenEnabled;
            if(!!(requestFullscreen && fullscreenEnabled)){
                let element = document.getElementById(this.id)
                if (element.requestFullscreen) {
                    element.requestFullscreen();
                } else if (element.mozRequestFullScreen) {
                    element.mozRequestFullScreen();
                } else if (element.webkitRequestFullscreen) {
                    element.webkitRequestFullscreen();
                } else if (element.msRequestFullscreen) {
                    element.msRequestFullscreen();
                }
            }else{
                this.$Message.error({
                    content: "此浏览器不支持或未开启全屏模式",
                    duration: 5
                });
            }

        },
        show() {
            let width = document.getElementsByClassName('h5StreamVideo')[0].offsetWidth
            let height = document.getElementsByClassName('h5StreamVideo')[0].offsetHeight
            if(width > 800 && height > 600 && this.video.positionSupport){
                this.controlFlag = true
            }
        },
        hide() {
            this.controlFlag = false
        },
        resetSize() {
            let height = document.getElementsByClassName('embedControl')[0].offsetHeight
            document.getElementsByClassName('embedControl')[0].style.width = height + 'px'
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
.videos::-webkit-media-controls-panel{
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
    background-color: rgba(0,0,0,0.1);
}
::backdrop { 
    position: relative;
    width: 100%;
    height: 100%;
}

.embedControl{
    position: absolute;
 /*   width: 24%;*/
    /*height: 40%;
    min-height: 100px;
    min-width: 98px;*/
    width: 98px;
    height: 100px;
    bottom: 0;
    left: 50%;
    transform: translate(-70%);
    z-index: 999;
}
.trsp{
    opacity: 0;
}
.text{
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


