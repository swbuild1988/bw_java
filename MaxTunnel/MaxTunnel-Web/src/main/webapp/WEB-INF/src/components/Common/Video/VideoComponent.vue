<template>
    <div class="videoContent">
        <!-- <vlc-video :id= 'id' :video=video'></vlc-video> -->
        <!-- <rtmp-video :id= 'id' :video= 'video'></rtmp-video> -->
        <h5-stream-video :id='id' :video='video'></h5-stream-video>
        <!-- <hsl-video :id= 'id' :video= 'video'></hsl-video> -->
    </div>
</template>

<script>
import VlcVideo from "./vlcVideo";
import RtmpVideo from "./rtmpVideo";
import HslVideo from "./hslVideo";
import H5StreamVideo from "./H5StreamVideo";

export default {
    name: "video-component",
    props: {
        video: {
            type: Object,
            required: true
        },
        id: {
            required: true
        }
    },
    components: { VlcVideo, RtmpVideo, HslVideo, H5StreamVideo },
    mounted() {
        this.Log.info("videoComponent start!");
    }
};
</script>

<style scoped>
.videoContent {
    position: relative;
    width: 100%;
    height: 100%;
}
</style>


