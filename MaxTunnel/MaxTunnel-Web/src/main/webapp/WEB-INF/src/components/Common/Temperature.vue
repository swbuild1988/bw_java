<template>
    <div class="ProcessRing">
        <div class="bgDiv" ref="BgDiv">
            <svg width="100%" height="100%" version="1.1"
                xmlns="http://www.w3.org/2000/svg">
                <g>
                    <!-- 一个变化矩形 --> 
                    <rect 
                        x="50%"
                        y="5%"
                        width="1%"
                        height="90%"
                        stroke-width="5%"
                        fill="none"
                        :stroke="startColor"
                        stroke-linecap="butt"
                        :stroke-dasharray="completeness"
                        class="changeRotate"
                    />
                </g>
            </svg>
        </div>
    </div>
</template>
<script>
import temper from '../../assets/UM/temper.png'
export default {
    name:"temperture",
    props:{
        length:{
            default: 0
        },
        startColor:{
            default: "#FF0000"
        },
        temperMax:{
            default: 120
        },
        temperMin:{
            default: 0
        }
    },
    data(){
        return{
            prot:0
        }
    },
    mounted(){
        this.getHeight();
    },
    computed: {
        completeness(){
            let comlength = this.length * this.prot *0.85 / (this.temperMax - this.temperMin);
            return `${comlength},10000000000`;
        },
        option(){
            let baseOption ={
                strokWidth: this.strokWidth,
                width:this.width,
                height:this.height
            };
            return baseOption;
        }
    },
    methods:{
        getHeight(){
            this.prot=this.$refs.BgDiv.offsetHeight;
        }
    }
};
</script>
<style scoped>
.ProcessRing {
    height: 100%;
    width: 100%;
    position: relative;
}
.ProcessRing .ring-rect {
    position: relative;
    display: inline-block;
}
.changeRotate{
    transform-origin: center;
    transform: rotate(180deg); 
    transition: stroke-dasharray 0.3s ease-in;
}
.bgDiv{
    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-image: url('../../assets/UM/temper.png');
    height:100%;
    width:10%;
    position:absolute;
    right:0%;
}
</style>