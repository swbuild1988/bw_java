<template>
  <div class="Main">
    <div class="Title">
      <module-title :title="title"></module-title>
    </div>
    <div class="camera" id="camera">
      <videoloop :videolist="loopScene" :videoNumber="1"></videoloop>
    </div>
  </div>
</template>

<script>
import videoloop from "../../../components/Common/Carousel";
import ModuleTitle from "../../../components/VM2/ModuleTitle";
export default {
  data() {
    return {
      title: "视频管理",
      setting: {
        value: 0,
        dots: "outside",
        autoplaySpeed: 10000,
        switchBtn: false,
        loop: false
      },
      videos: [], //缓存当前场景下所有摄像头
      tempVideos: [], //保存临时所有摄像头
      loopScene: [], //保存所有轮询场景
      virtualLoopScene: [],
      allVideos: [], //保存所有的视屏服务
      defaultOption: 1,
      defaultVideo: 0,
      defaultLoopIndex: 0,
      currShowVideo: {
        showVideoTimer: null,
        loopSceneIndex: 0
      },
      currLoopSceneVideo: {
        id: null,
        url: ""
      },
      style: {
        offsetHeight: 0,
        offsetWidth: 0
      }
    };
  },
  components: {
    videoloop,
    ModuleTitle,
  },
  mounted() {
    this.getLoopScence();
  },
  computed: {
    isAutoplay() {
      return this.$store.state.VMstate.autoplay;
    },
    planLinkageVideo() {
      return this.$store.state.VMstate.planLinkageVideos;
    }
  },
  watch: {
    optionList() {
      let _this = this;
      _this.addOption(_this.optionList);

      $("#cd-dropdown").dropdown({
        gutter: 5,
        stack: false,
        slidingIn: 100,
        onOptionSelect(opt) {
          _this.changeStatus(opt.attr("data-value"));
        }
      });
      _this.addIframe("cd-dropdown");
    },
    sceneList() {
      let _this = this;
      _this.addOption(_this.sceneList, "scene-dropdown");

      $("#scene-dropdown").dropdown({
        defaultClass: ["cd-dropdown", "Areas"],
        gutter: 5,
        stack: false,
        slidingIn: 100,
        onOptionSelect(opt) {
          _this.changeScene(opt.attr("data-value"));
        }
      });
      _this.addIframe("scene-dropdown");
    },
    defaultLoopIndex() {
      let _this = this,
        index = this.defaultLoopIndex;

      if (
        _this.loopScene[index] == undefined ||
        !_this.loopScene[index].length
      ) {
        return;
      }

      _this.loopScene[index].filter((val, index) => {
        if (index == 0) {
          this.VMConfig.VLC.VLC_ID = val.id;
        }
      });

      // _this.changeCopyLoopScene(index);
      // 监听轮播索引
      _this.changeStyle(_this.loopScene[index]);
    },
    tempVideos() {
      if (!this.tempVideos.length) {
        return;
      }

      this.tempVideos.filter((val, index) => {
        if (index == 0) {
          this.VMConfig.VLC.VLC_ID = val.id;
        }
      });
    },
    planLinkageVideo: {
      handler({ videoIds, processInstanceId }) {
        if (videoIds == null || videoIds === undefined) {
          this.switchLoopStatus(true);
          this.getLoopScence();
        } else {
          try {
            let videosService = this.allVideos.filter(
              videos =>
                videoIds
                  .toString()
                  .split(",")
                  .indexOf(videos.id.toString()) !== -1
            );

            this.switchLoopStatus(false);
            this.showDropdown(false);

            this.loopScene.splice(0);
            this.loopScene.push(videosService);
            this.changeStyle(this.loopScene[0]);

            this.$nextTick(() =>
              PlanService.ConfirmStep(processInstanceId, videoIds, "yes")
            );
          } catch (err) {
            console.warn("err", err);
          }
        }
      },
      deep: true
    }
  },
  methods: {
    getLoopScence() {
      let _this = this;
      _this
        .fetchData({ url: "/video_scenes", array: _this.loopScene })
        .then(() => {
          _this.getShowVideo();

          _this.copyLoopScene(_this.loopScene, _this.virtualLoopScene);

          _this.changeStyle(_this.loopScene[0]);
        });
    },
    getShowVideo() {
      this.changeVideo();
      this.currShowVideo.showVideoTimer = setInterval(
        () => this.changeVideo(),
        10000
      );
    },
    copyLoopScene(originalArray, copyArray) {
      copyArray.splice(0);
      originalArray.forEach((val, key) =>
        !key ? copyArray.push([val]) : copyArray.push([])
      );
    },
    changeCopyLoopScene(oldValue, value) {
      let { virtualLoopScene, loopScene } = this;

      virtualLoopScene[value].push(loopScene[value]);
      virtualLoopScene[oldValue].splice(0);
    },
    changeVideo() {
      this.currLoopSceneVideo = this.loopScene[
        this.currShowVideo.loopSceneIndex
      ];
      this.currShowVideo.loopSceneIndex >= this.loopScene.length - 1
        ? (this.currShowVideo.loopSceneIndex = 0)
        : this.currShowVideo.loopSceneIndex++;
    },
    changeStyle(showVideos) {
      // if(!Array.isArray(showVideos))return;

      let _this = this,
        height = document.getElementById("camera").offsetHeight,
        width = document.getElementById("camera").offsetWidth;

      _this.style.offsetHeight = height;
      _this.style.offsetWidth = width;

      return false;
    },
    fetchData(para) {
      let _this = this;
      return new Promise(resolve => {
        _this.axios.get(para.url).then(result => {
          let { code, data } = result.data;

          if (code == 200) {
            para.array.splice(0); //清空数组
            data.forEach(tunnel =>
              para.url == "/tunnels"
                ? para.array.push({ value: tunnel.id, label: tunnel.name })
                : para.array.push(...tunnel.videos)
            );
            resolve();
          }
        });
      });
    }
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
  margin: 1% 7%;
  width: 84%;
  height: 86%;
}
</style>
