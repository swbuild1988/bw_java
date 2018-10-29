<template>
    <div class="ProgressRect">
        <div class="backDiv" ref="BackDiv" :id="id">
            <svg width="100%" height="100%" version="1.1"
                xmlns="http://www.w3.org/2000/svg">
              <rect
                x="50%"
                :y="yOffset"
                width="1%"
                height="100%"
                stroke-width="50%"
                fill="none"
                :stroke="startColor"
                :stroke-dasharray="completeness"
                transform="rotate(0, 100 0)"
              />
            </svg>
        </div>
    </div>
</template>

<script>
import bkg from '../../../assets/UM/bkg.png'
export default {
    name:"gasShow",
    props:{
      id:{
        default:1
      },
      ObjVal:{
            default: 200
        },
        startColor:{
            default: "#ff6b38"
        },
      GasMax:{
            default: 200
        },
      GasMin:{
            default: 0
        }
    },
    data(){
        return{
          prot: 0,
          yOffset:0,
          yDistancce: 0,
        }
    },
    mounted(){
        this.getHeight();
    },
    computed:{
        completeness(){
          this.yDistancce = 0;
          this.yDistancce = this.ObjVal * this.prot * 0.85 / (this.GasMax - this.GasMin);
          var result = `${this.yDistancce},10000000000 Z`;
          this.yOffset=(this.prot- this.yDistancce)>=0?(this.prot- this.yDistancce):0;
          return result
        }
    },
    methods:{
        getHeight(){
            this.prot=this.$refs.BackDiv.offsetHeight;
        }
    }
}
</script>

<style scoped>
.ProgressRect{
    height: 100%;
    width: 100%;
    position: relative;
}
.Process {
  position: relative;
  display: inline-block;
}
.changeRotate{
    transform-origin: center;
    transform: rotate(180deg);
    transition: stroke-dasharray 0.3s ease-in;
}
.backDiv{
    background-size:100% 100%;
    background-repeat: no-repeat;
    background-image: url('../../../assets/UM/bkg.png');
    position: absolute;
    height:100%;
    width: 40%;
    right:0%;
}
</style>

