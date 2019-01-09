<template>
    <div class="Main">
        <div class="camera" id="camera">
            <videoloop :videolist="loopScene"></videoloop>
        </div>
    </div>
</template>

<script>
import videoloop from "../../../components/Common/Carousel";
export default {
    data() {
        return {
            loopScene:[],
            currShowVideo:{
                showVideoTimer:null,
                loopSceneIndex:0
            },
            style:{
                offsetHeight:0,
                offsetWidth:0
            },
        };
    },
    components: {
        videoloop
    },
    mounted() {
        this.getLoopScence()
    },
    watch: {
        // defaultLoopIndex(){
        //     let _this=this,
        //         index=this.defaultLoopIndex;
                
        //     if(_this.loopScene[index] == undefined || !_this.loopScene[index].length){ return };

        //     _this.loopScene[index].filter((val,index)=>{if(index==0){ this.VMConfig.VLC.VLC_ID = val.id }});

        //     // _this.changeCopyLoopScene(index);
        //     // 监听轮播索引
        //     _this.changeStyle(_this.loopScene[index]);
        // }
    },
    methods: {
        getLoopScence(){
            let _this = this;
            _this.fetchData({url:'/video_scenes',array:_this.loopScene})
                .then(()=>{
                    _this.getShowVideo();

                    _this.copyLoopScene(_this.loopScene,_this.virtualLoopScene);

                    _this.changeStyle(_this.loopScene[0]);
                });
        },
        getShowVideo(){
            this.changeVideo()
            this.currShowVideo.showVideoTimer = setInterval(()=>this.changeVideo(),10000)
        },
        copyLoopScene(originalArray,copyArray){
            copyArray.splice(0);
            originalArray.forEach((val,key) => !key ? copyArray.push( [val] ) : copyArray.push( [] ));
        },
        changeCopyLoopScene(oldValue, value){
            let { virtualLoopScene,loopScene } = this; 
            
            virtualLoopScene[value].push(loopScene[value]);
            virtualLoopScene[oldValue].splice(0);

        },
        changeVideo(){
            this.currLoopSceneVideo = this.loopScene[ this.currShowVideo.loopSceneIndex ];
            this.currShowVideo.loopSceneIndex >= this.loopScene.length-1 
                ? this.currShowVideo.loopSceneIndex = 0 
                : this.currShowVideo.loopSceneIndex++;
        },
        changeStyle(showVideos){
            // if(!Array.isArray(showVideos))return;

            let _this=this,
                 height=document.getElementById('camera').offsetHeight,
                 width=document.getElementById('camera').offsetWidth;

            _this.style.offsetHeight = height;
            _this.style.offsetWidth = width;

            return false;
        },
        fetchData(para){
            let _this=this;
            return new Promise(resolve => {
                _this.axios.get(para.url)
                    .then(result=>{
                        let { code,data }=result.data;

                        if(code==200){

                            para.array.splice(0)//清空数组
                            data.forEach(tunnel=>para.url=='/tunnels' ? para.array.push({value:tunnel.id,label:tunnel.name})
                                :para.array.push(...tunnel.videos));
                            resolve();
                        }
                    })
            })
        },
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
    background: url('../../../assets/VM/vm_module_bg.png') no-repeat;
    background-size: 100% 100%;
}
.camera{
    margin: 4.6% auto;
    width: 84%;
    height: 84%;
}
</style>
