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
        this.axios.get("videos/" + this.video.id + "/convert").then(result => {
            this.Log.info(this.video.id + "开启转换");
        });
    },
    beforeDestroy() {
        this.axios
            .get("videos/" + this.video.id + "/stop-convert")
            .then(result => {
                this.Log.info(this.video.id + "停止转换");
            });
    },
    methods: {},
    computed: {
        player() {
            return this.$refs.videoPlayer.player;
        },
        playerOptions() {
            return {
                autoplay: true,
                controls: true,
                techOrder: ["flash"],
                sourceOrder: true,
                fluid: true,
                flash: {
                    hls: { withCredentials: false },
                    swf: this.ServerConfig + "/media/video-js.swf"
                },
                sources: [
                    {
                        type: "rtmp/mp4",
                        // src: this.video.url
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

