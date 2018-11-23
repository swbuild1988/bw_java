<template>
	<div class="videoContent">
        <!-- <video-player class="vjs-custom-skin" ref="videoPlayer" :options="playerOptions">
        </video-player> -->
	</div>
</template>

<script>
// import "videojs-flash";

export default {
    name: "videojs",
    props: {
        video: {
            type: Object,
            required: true
        },
        id: {
            type: String,
            required: true
        }
    },
    data: function() {
        return {};
    },
    watch: {},
    mounted() {
        this.Log.info("rtmp start1!");
        this.Log.info(this.video);
    },
    methods: {},
    computed: {
        player() {
            return this.$refs.videoPlayer.player;
        },
        playerOptions() {
            return {
                // height: 300,
                autoplay: true,
                controls: true,
                techOrder: ["flash"],
                sourceOrder: true,
                fluid: true,
                // flash: {
                //     hls: { withCredentials: false },
                //     swf: this.ServerConfig + '/media/video-js.swf'
                // },
                sources: [
                    {
                        type: "rtmp/mp4",
                        // src: "rtmp://192.168.3.22:1935/live/" + this.video.id
                        src:
                            "rtmp://184.72.239.149/vod/&mp4:BigBuckBunny_115k.mov"
                    }
                ]
                // controlBar: {
                //   timeDivider: false, // 时间分割线
                //   durationDisplay: false, // 总时间
                //   progressControl: true, // 进度条
                //   customControlSpacer: true, // 未知
                //   fullscreenToggle: true // 全屏
                // },
            };
        }
    }
};
</script>

<style scoped>
.videoContent {
    position: relative;
    width: 100%;
    height: 100%;
    z-index: 101;
}
</style>

