<template>
  <div class="ProcessRing">
    <div class="bgDiv" ref="BgDiv" :id="id">
      <svg width="100%" height="100%" version="1.1"
           xmlns="http://www.w3.org/2000/svg">
        <g>
          <!-- 一个变化矩形 -->
          <rect
            x="49%"
            :y="yOffset"
            rx="20" ry="20"
            width="2%"
            height="100%"
            stroke-width="5%"
            fill="none"
            :stroke="startColor"
            stroke-linecap="butt"
            :stroke-dasharray="completeness"
            transform="rotate(0, 0 0)"
          />
        </g>
      </svg>
    </div>
  </div>
</template>

<script>
  import temper from '../../../assets/UM/temper.png'

  export default {
    name: "temperture",
    props: {
      ObjVal: {
        default: 0
      },
      id: {
        default: 1
      },
      startColor: {
        default: "#FF0000"
      },
      GasMax: {
        default: 50
      },
      GasMin: {
        default: 0
      }
    },
    data() {
      return {
        prot: 0,
        yOffset:0,
        yDistancce: 0,
      }
    },
    mounted() {
      this.getHeight();
    },
    computed: {
      completeness() {
        this.yDistancce = 0;
        this.yDistancce = this.ObjVal * this.prot * 0.85 / (this.GasMax - this.GasMin);
        var result = `${this.yDistancce},10000000000 Z`;
        this.yOffset=(this.prot- this.yDistancce)>=0?(this.prot- this.yDistancce):0;
        return result
      },
      option() {
        let baseOption = {
          strokWidth: this.strokWidth,
          width: this.width,
          height: this.height
        };
        return baseOption;
      }
    },
    methods: {
      getHeight() {
        this.prot = this.$refs.BgDiv.offsetHeight;
      },
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

  .changeRotate {
    transform-origin: center;
    transform: rotateY(180deg);
    transition: stroke-dasharray 0.3s ease-in;
  }

  .bgDiv {
    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-image: url('../../../assets/UM/temper.png');
    height: 100%;
    width: 50%;
    position: absolute;
    right: 0%;
  }
</style>
