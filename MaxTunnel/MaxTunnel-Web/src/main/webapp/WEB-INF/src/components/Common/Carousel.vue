<template>
    <div class="videosContent">
        <button type="button" class="commonButton left video-arrow-hover" @click="arrowEvent(-1)">
            <Icon type="ios-arrow-back"></Icon>
        </button>
        <video-component :video="currVideoObj" :id="'currLoopSceneVideoId'"></video-component>
        <button type="button" class="commonButton right video-arrow-hover" @click="arrowEvent(1)">
            <Icon type="ios-arrow-forward"></Icon>
        </button>
    </div>
   
</template>
<script>
import videoComponent from './Video/VideoComponent'

export default {
    props:{
        videolist:{
            type:Array,
            default:[],
        },
        isloop:{
            type:Boolean,
            default:true,
        },
        loopSpeed:{
            type:Number,
            default:10, //默认 s
        },
    },
    data(){
        return {
            currVideoObj:{
                id: null,
                url: ''
            },
            loopProp:{
                loopIndex : 0,
                loopTimer : null
            }
        }
    },
    components:{
        videoComponent
    },
    watch:{
        'videolist':{
            handler(newValue,oldValue){
                this.changeVideo();
            },
            deep:true,
        }
    },
    mounted(){},
    methods:{
        changeVideo(){
            let { loopProp } = this;

            this.gainVideo( loopProp.loopIndex );//获取视屏
            this.loopVideo();//轮询视屏
            
        },
        loopVideo(){
            clearInterval( this.loopProp.loopTimer );//清除定时器

            if( this.isloop ) {
                this.loopProp.loopTimer = setInterval( ()=> this.gainVideo(1),this.loopSpeed * 1000 )
            }
            
        },
        arrowEvent(offset){
            this.loopVideo();
            this.gainVideo( offset );//获取视屏
        },
        gainVideo(offset){
            let newLoopIndex = this.loopProp.loopIndex + offset;

            this.loopProp.loopIndex = newLoopIndex < 0 
                ? this.videolist.length - 1 
                : ( newLoopIndex > this.videolist.length - 1 ? 0 : newLoopIndex )

            this.currVideoObj = this.videolist[ this.loopProp.loopIndex ];
        }
    }
}
</script>
<style scoped>
.videosContent {
    widows: 100%;
    height: 100%;
}
.videosContent .commonButton {
    position: absolute;
    z-index: 1001;
    width: 10%;
    height: 11%;
    border-radius: 50%;
    top: 50%;
    font-size: 3rem;
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
.videosContent:hover {

}
.videosContent .video-arrow-hover:hover {
    background-color: rgba(255, 255, 255, .5);
    opacity: 1;
}
</style>


