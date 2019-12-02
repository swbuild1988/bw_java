<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="camera" id="camera">
            <videoloop :videolist="loopScene" :videoNumber="1" :loopSpeed=30></videoloop>
        </div>
    </div>
</template>

<script>
    import videoloop from "../../../components/Common/Carousel";
    import ModuleTitle from "../../../components/VM2/ModuleTitle";
    import {
        VideoService
    } from "../../../services/videoService"
    export default {
        data() {
            return {
                title: "视频轮巡",
                loopScene: [], //保存所有轮询场景
            };
        },
        components: {
            videoloop,
            ModuleTitle,
        },
        mounted() {
            this.getLoopScence();
        },
        methods: {
            getLoopScence() {
                VideoService.getSceneVideos().then(
                    res => {
                        this.loopScene.splice(0); //清空数组
                        res.forEach(scene =>
                            this.loopScene.push(...scene.videos)
                        );
                    }
                )
            }
        },
        beforeDestory() {
            clearInterval(this.currShowVideo.showVideoTimer)
        }
    };
</script>

<style scoped>
    .Main {
        width: 100%;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        background: url("../../../assets/VM/module_bg.png") no-repeat;
        background-size: 100% 100%;
    }

    .camera {
        position: absolute;
        width: 98%;
        height: 85%;
        top: 14%;
        left: 1%;
    }

    .VideoModule .Title {
        width: 100%;
        height: 15%;
    }

    .camera>>>.videoWrapper {
        width: 100%;
    }
</style>