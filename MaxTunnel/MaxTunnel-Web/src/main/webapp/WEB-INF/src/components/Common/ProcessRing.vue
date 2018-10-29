<template>
    <div class="ProcessRing">
      <div class="ring-circle" :height="option.size" :width="option.size">
        <svg :height="option.size" :width="option.size" x-mlns="http://www.w3.org/200/svg">
            <defs>
                <linearGradient x1="1" y1="0" x2="0" y2="0" id="leftGradient">
                    <stop offset="0%" :stop-color="endColor"></stop>
                    <stop offset="100%" :stop-color="middleColor"></stop>
                </linearGradient>
                <linearGradient x1="1" y1="0" x2="0" y2="0" id="rightGradient">
                    <stop offset="0%" :stop-color="startColor"></stop>
                    <stop offset="100%" :stop-color="middleColor"></stop>
                </linearGradient>
            </defs>
            <g>
                <circle
                    :r="option.radius"
                    :cx="option.cx"
                    :cy="option.cy"
                    stroke="#dddddd"
                    :stroke-width="option.strokeWidth"
                    fill="none"
                    stroke-linecap="round"/>
                <circle
                    id="progressRound"
                    :stroke-dasharray="completenessLeftHandle"
                    :r="option.radius"
                    :cx="option.cx"
                    :cy="option.cy"
                    :stroke-width="option.strokeWidth"
                    stroke="url('#leftGradient')"
                    fill="none"
                    class="progressRound"
                    stroke-linecap="round"
                />
                <circle
                    id="progressRound"
                    :stroke-dasharray="completenessRightHandle"
                    :r="option.radius"
                    :cx="option.cx"
                    :cy="option.cy"
                    :stroke-width="option.strokeWidth"
                    stroke="url('#rightGradient')"
                    fill="none"
                    class="progressRound"
                    stroke-linecap="round"
                />
            </g>
        </svg>
        <div class="Mask">
            <slot></slot>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: "processRing",
  props: {
    // 圈圈渐变色
    startColor: {
      default: "#22A42A"
    },
    middleColor: {
      default: "#CDBFE4"
    },
    endColor: {
      default: "#E2C42A"
    },
    // 显示的数值，0.8（80%）
    percent: {
      default: 0
    },
    strokeWidth: {
      default: 10
    },
    explain: {
      default: "explain"
    },
    radius: {
      default: 60
    },
    progressOption: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {};
  },
  mounted() {},
  computed: {
    completenessLeftHandle() {
      let circleLength = Math.floor(2 * Math.PI * this.option.radius);
      let completenessLength = this.percent / 100 * circleLength;
      return `${completenessLength}, 100000000`;
    },
    completenessRightHandle() {
      let circleLength = Math.floor(2 * Math.PI * this.option.radius);
      let tmpPercent = this.percent > 50 ? 50 : this.percent;
      let completenessLength = tmpPercent / 100 * circleLength;
      return `${completenessLength}, 100000000`;
    },
    option() {
      // 所有进度条的可配置项
      let baseOption = {
        radius: this.radius,
        strokeWidth: this.strokeWidth
      };
      Object.assign(baseOption, this.progressOption);
      // 中心位置自动生成
      baseOption.cy = baseOption.cx =
        baseOption.radius + baseOption.strokeWidth;
      baseOption.size = (baseOption.radius + baseOption.strokeWidth) * 2;

      return baseOption;
    }
  },
  methods: {}
};
</script>

<style scoped>
.progressRound {
  transform-origin: center;
  transform: rotate(-90deg);
  transition: stroke-dasharray 0.3s ease-in;
}

.ProcessRing {
  height: 100%;
  width: 100%;
}

.ProcessRing .ring-circle {
  position: relative;
  display: inline-block;
}

.ProcessRing .ring-circle .Mask {
  width: 100%;
  text-align: center;
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  line-height: 1;
}
.ProcessRing .ring-circle .Mask p {
  font-size: 22px;
}
</style>