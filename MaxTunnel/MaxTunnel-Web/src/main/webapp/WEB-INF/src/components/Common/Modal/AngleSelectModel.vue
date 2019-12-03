<template>
    <div class="angle">
        <div class="anglePanel" :class="[angleProp.open ? 'anglePanel-open' : 'anglePanel-close']">
            <div class="angleTitle">
                <span>当前角度</span>
                <span>{{angleProp.currTitle}}</span>
            </div>
            <ul class="angleUls">
                <li  v-for="item in angleLists" 
                    :key="item.id"
                    @click="switchAngle(item.id)"
                >
                <span>{{item.name}}</span>
                </li>
            </ul>
        </div> 
        <div class="angleBut" 
            :style="{left:angleProp.currLeft,top:angleProp.panelBtnTop}"
            @click="switchPanel">
                <img :src="angleProp.angleButImg" style="height:100%;width:100%;">
        </div>
    </div>
</template>

<script>
export default {
    props:{
        smViewer:{
            required:true
        }
    },
    data() {
        return {
            angleProp:{
                angleButImg:require(`../../../assets/UM/angleButOpen.png`),
                open:false,
                panelBtnLeft:null,
                panelBtnTop:null,
                currLeft:0,
                currTitle:'总览'
            }
        };
    },
    computed: {
        angleLists() {
            return this.MapAngleLists;
        },
    },
    mounted(){
        this.computedBtnStyle();
    },
    methods: {
        switchAngle(id){
            
            let [ curAngle ]  = this.angleLists.filter(angle => angle.id == id);

            this.angleProp.currTitle = curAngle.name;
            this.smViewer.flyToAngle(curAngle.angle)
        },
        switchPanel(){
            this.angleProp.open = !this.angleProp.open;
            
            this.angleProp.currLeft = this.angleProp.open ? this.angleProp.panelBtnLeft : 0 ;
            let img = this.angleProp.open ? 'angleButClose' : 'angleButOpen';
            
            this.angleProp.angleButImg = require(`../../../assets/UM/${ img }.png`)
            
        },
        computedBtnStyle(){
            let dom = document.querySelector('.anglePanel');
            let btn = document.querySelector('.angleBut')
            
            if(!dom || !btn) return;
            
            this.angleProp.panelBtnLeft = dom.offsetWidth+'px';
            this.angleProp.panelBtnTop = (dom.offsetHeight/2 - btn.offsetHeight/2 + dom.offsetTop)+'px'
        }
    }
};
</script>

<style scoped>
.angle {
    overflow: hidden;
    height: 100%;
    position: absolute;
    z-index: 1001;
    width: 10vmin;
}
.anglePanel {
    position: absolute;
    z-index: 1001;
    color: #fff;
    background: #16375d;
    border: 1px solid #00d7ff;
    border-left: none;
    border-radius: 0 1vmin 1vmin 0;
    padding: 1vmin 0;
    top: 15vmin;
    font-size: 1vmin;
    transition: all 2s;
}
.anglePanel-open {
    left: 0;
    opacity: 1;
}
.anglePanel-close {
    left: -9vmin;
    opacity: 0;
}
.btnPositionOpen{
    left: 8.9vmin;
}
.btnPositionClose{
    left: 0vmin;
}
.angleTitle > span {
    display: block;
    margin: 0 .7vmin;
    height: 2.5vmin;
    line-height: 2.5vmin;
    color: #00d7ff;
    text-align: center;
}
.angleTitle > span:last-child {
    border-bottom: 1px solid #00d7ff;
} 
.anglePanel  .angleUls {
    list-style: none;
    /* overflow-y:scroll; */
    padding-top: .8vmin;
}
.angleUls > li{
    text-align: center;
    padding: 0 1vmin;
    height: 2.5vmin;
    line-height: 2.5vmin;
    cursor: pointer;
 }
 .angle .angleBut{
    position: absolute;
    width: 1.2vmin;
    height: 2vmin;
    z-index: 1001;  
    cursor: pointer;
    transition: all 1.5s;
 }
</style>
