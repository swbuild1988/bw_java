<template>
    <div class="extendVideos">
        <Carousel 
            autoplay 
            v-model="loopSetting.defaultLoopIndex" 
            :dots="loopSetting.dots"
            :autoplay-speed="loopSetting.autoplaySpeed"
            :loop = "loopSetting.loop">
        <CarouselItem v-for="(val,key) in loopSceneVideos" :key="key">
            <div class="view" v-for="(item,index) in loopSceneVideos[key]" :style="mixinsStyle">
                <video-component :video="item" :id="item.id"></video-component>
            </div>
        </CarouselItem>
    </Carousel>
    </div>
</template>

<script>
import { CMVideoService } from '../../../services'
import videoComponent from '../../Common/Video/VideoComponent'

export default {
    name:'extendVideos',
    props:{
        mixinsStyle:{
            default: ()=>{
                return {
                    width:'24%',
                    height:'16%',
                    marginBottom: '.5%'
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
                loop:true
            },
            loopSceneVideos:[]
        }
    },
    computed:{
    },
    mounted(){
        this.init();
    },
    methods:{
        init(){
            this.getLoopSceneDate();
        },
        getLoopSceneDate(){
            CMVideoService.getSupportLoopSceneVideos().then( videos => { 
                this.loopSceneVideos.splice(0);
                this.loopSceneVideos.push(tunnel.videos);
            })
        }
    },
}
</script>


<style scoped>
    .extendVideos {
        width: 100%;
        height: 100%;
    }
    
</style>
