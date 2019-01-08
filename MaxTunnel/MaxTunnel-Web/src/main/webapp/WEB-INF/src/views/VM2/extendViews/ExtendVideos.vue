<template>
    <div class="extendVideos BackgroundImage">
        <Carousel 
            autoplay 
            v-model="loopSetting.defaultLoopIndex" 
            :dots="loopSetting.dots"
            :autoplay-speed="loopSetting.autoplaySpeed"
            :loop = "loopSetting.loop">
        <CarouselItem v-for="(val,key) in loopSceneVideos" :key="key">
            <div class = "extendView"
                :style="{height:videoStyle.height+'px',width:videoStyle.width+'px'}"
                v-for ="(item,index) in loopSceneVideos[key]"
            >
                <video-component :video="item" :id="'video-'+ item.id "></video-component>
            </div>
        </CarouselItem>
    </Carousel>
    </div>
</template>

<script>
import { CMVideoService } from '../../../services/cmVideoService'
import videoComponent from '../../../components/Common/Video/VideoComponent'

export default {
    name:'extendVideos',
    props:{
        mixinsStyleCoeff:{
            default: ()=>{
                return {
                    width:0.32, //宽度系数
                    height:0.24,//高度系数
                }
            }
        }
    },
    data(){
        return {
            loopSetting : {
                defaultLoopIndex : 0,
                dots: 'outside',
                autoplaySpeed: 10000,
                loop:true //是否启动循环
            },
            videoStyle:{
                width : 0,
                height : 0
            },
            loopSceneVideos:[]
        }
    },
    watch:{
        'mixinsStyleCoeff':{
            handler(){
                this.caclVideoStyle();
            },
            deep:true
        }
    },
    components:{
        videoComponent
    },
    mounted(){
        this.init();
    },
    methods:{
        init(){
            this.getLoopSceneDate();
    
            this.caclVideoStyle();
        },
        getLoopSceneDate(){
            CMVideoService.getSupportLoopSceneVideos().then( loopVideos => { 
                this.loopSceneVideos.splice(0);

                loopVideos.forEach( loopVideo =>  this.loopSceneVideos.push(loopVideo.videos) );
                
            })
        },
        caclVideoStyle(){
            let { videoStyle } = this;
            let dom = this.getDomElement('extendVideos');

            let obj = {
                width : parseFloat(dom.offsetWidth) * this.mixinsStyleCoeff.width,
                height : parseFloat(dom.offsetHeight) * this.mixinsStyleCoeff.height,
            };

            videoStyle.width = obj.width;
            videoStyle.height = obj.height;

        },
        getDomElement(dom){
            return document.getElementsByClassName(dom)[0]
        }
    },
}
</script>


<style scoped>
    .extendVideos {
        width: 100%;
        height: 100%;
    }
    .extendVideos .extendView {
        margin-right: .2%;
        margin-bottom: 0.5%;
        float: left;
    }
    .BackgroundImage {
        padding: 1.3%;
        border-radius: 0.5rem;
        border: 1px solid rgba(0, 107, 149, 0.5);
        box-shadow: 0 0 1.5rem rgba(0, 107, 149, 1) inset;
    }
</style>
