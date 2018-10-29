<template>
    <div class="videoContent" @click="playOrPause">
        <video :id= id class="videos" autoplay webkit-playsinline playsinline></video>
        <!-- <div class="playPause"></div> -->
    </div>
</template>

<script>
export default {
    name: "h5-stream-video",
    props: {
        video: {
            type: Object,
            required: false
        },
        id: {
            type: String,
            required: false
        }
    },
    data() {
        return {
            config: {
                videoid: this.id,
                protocol: window.location.protocol, //http: or https:
                host: this.video.url,
                rootpath: "/", // '/' or window.location.pathname
                token: this.video.id,
                hlsver: "v1", //v1 is for ts, v2 is for fmp4
                session: "c1782caf-b670-42d8-ba90-2244d0b0ee83" //session got from login
            },
            curVideo: null
        };
    },
    mounted() {
        this.Log.info("curVideo Config:", this.config);
        this.curVideo = H5sPlayerCreate(this.config);
        this.curVideo.connect();
    },
    beforeDestroy() {
        this.curVideo.disconnect();
        this.curVideo = null;
    },
    methods: {
        playOrPause() {
            this.Log.info("clicked");
        }
    }
};
</script>

<style scoped>
.videoContent {
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
}
</style>


