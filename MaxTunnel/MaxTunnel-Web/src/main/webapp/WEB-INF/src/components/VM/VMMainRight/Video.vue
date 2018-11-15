<template>
      <div class="VideoContainer BackgroundImage">
          <div class="Video" id="video">
              <div :style="{height:style.offsetHeight+'px',width:style.offsetWidth+'px'}"
                    v-show="style.showVideo"
                    class="view"
                    v-for="(item,index) in tempVideos" :key="index">
                  <video-component :video="item" :id="item.id"></video-component>
              </div>
              <Carousel v-model="defaultLoopIndex"
                        loop
                        :autoplay="isAutoplay"
                        v-show="!style.showVideo"
                        :dots="setting.dots"
                        :autoplay-speed="setting.autoplaySpeed">
                  <CarouselItem v-for="(val,key) in loopScene" :key="key">
                      <div class="view" v-for="(item,index) in loopScene[key]" :style="{height:style.offsetHeight+'px',width:style.offsetWidth+'px'}">
                          <video-component :video="item" :id="item.id"></video-component>
                      </div>
                  </CarouselItem>
              </Carousel>
          </div>
          <i-switch
                  v-model="setting.switchBtn"
                  size="large"
                  @on-change="getStatus">
              <span slot="open">轮播</span>
              <span slot="close">筛选</span>
          </i-switch>
          <vm-title :title-text="title"></vm-title>
          <select class="cd-select" id="cd-dropdown" ></select>
          <select class="cd-select " id="scene-dropdown"></select>
      </div>
</template>

<style scoped>
.Video {
    position: relative;
    top: 14.5%;
    width: 100%;
    height: 85.5%;
}
.Video >>> .ivu-carousel-arrow:hover {
    background-color: #001a27;
}
.Video >>> .ivu-switch-checked {
    border-color: #6da7e2;
    background-color: #00273a;
}
.Video + .ivu-switch {
    position: absolute;
    top: 6%;
    right: 6%;
    border: 1px solid #6da7e2;
    background-color: #00273a;
}
.Video .view {
    background: #000000;
    float: left;
    margin: 0.5%;
}
</style>
<style>
.VideoContainer .Areas {
    left: 20.5%;
}
</style>
<script>
import VmTitle from "../VMTitle";
import $ from "jquery";
import videoComponent from "../../Common/Video/VideoComponent";

export default {
    data() {
        return {
            title: "视屏监控",
            setting: {
                value: 0,
                dots: "outside",
                autoplaySpeed: 10000,
                switchBtn: false
            },
            style: {
                offsetHeight: 0,
                offsetWidth: 0,
                showVideo: false
            },
            optionList: [], //所有管廊
            sceneList: [], //当前管廊下所有场景
            videos: [], //缓存当前场景下所有摄像头
            tempVideos: [], //保存临时所有摄像头
            loopScene: [], //保存所有轮询场景
            defaultOption: 1,
            defaultVideo: 0,
            defaultLoopIndex: 0
        };
    },
    components: {
        VmTitle,
        videoComponent
    },
    mounted() {
        // this.init();
    },
    computed: {
        isAutoplay() {
            return this.$store.state.VMstate.autoplay;
        }
    },
    filters: {},
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
        }
    },
    methods: {
        init() {
            let _this = this;

            $("#cd-dropdown")
                .css("display", "none")
                .next()
                .css("display", "none");

            _this
                .fetchData({ url: "/video_scenes", array: _this.loopScene })
                .then(() => {
                    _this.changeStyle(_this.loopScene[0]);
                });
        },
        changeScene(val) {
            let _this = this,
                videos = _this.videos[val];

            _this.tempVideos.splice(0);

            if (videos) {
                for (let i = 0; i < videos.length; i++)
                    _this.tempVideos.push(videos[i]);

                _this.changeStyle(videos);
            }
        },
        changeStyle(showVideos) {
            if (!Array.isArray(showVideos)) return;
            let _this = this,
                height = document.getElementById("video").offsetHeight,
                width = document.getElementById("video").offsetWidth;

            _this.style.offsetHeight =
                showVideos.length > 6 ? height * 0.18 : height * 0.28;
            _this.style.offsetWidth =
                showVideos.length > 6 ? width * 0.32 : width * 0.48;

            return false;
        },
        fetchData(para) {
            let _this = this;

            return new Promise(resolve => {
                _this.axios.get(para.url).then(result => {
                    let { code, data } = result.data;

                    if (code == 200) {
                        para.array.splice(0); //清空数组
                        data.forEach(
                            tunnel =>
                                para.url == "/tunnels"
                                    ? para.array.push({
                                          value: tunnel.id,
                                          label: tunnel.name
                                      })
                                    : para.array.push(tunnel.videos)
                        );
                        resolve();
                    }
                });
            });
        },
        changeStatus(val) {
            let _this = this;
            _this.axios.get(`tunnels/${val}/video_scenes/`).then(result => {
                let { code, data } = result.data;

                if (code == 200) {
                    _this.sceneList.splice(0);
                    _this.videos.splice(0);
                    if (data) {
                        data.forEach((item, index) => {
                            _this.sceneList.push({
                                value: index,
                                label: item.name
                            });
                            _this.videos.push(item.videos);
                        });
                        _this.changeScene(_this.defaultVideo);
                    }
                }
            });
        },
        addOption(optionList, id = "cd-dropdown") {
            var $dropdown = $("#" + id),
                optionList = optionList,
                optlist = "";
            for (let i = 0; i < optionList.length; i++) {
                optlist += `<Option value="${optionList[i].value}" >${
                    optionList[i].label
                }</Option>`;
            }

            $dropdown.empty().append(optlist);

            $dropdown
                .children("option")
                .eq(0)
                .attr("selected", "selected"); //设置第一个为默认选中项
        },
        getStatus(status) {
            let _this = this;

            let display = status ? "block" : "none";

            $("#cd-dropdown")
                .css("display", display)
                .next()
                .css("display", display);

            if (status) {
                _this.fetchData({ url: "/tunnels", array: _this.optionList });
                _this.changeStatus(_this.defaultOption);
            } else {
                _this
                    .fetchData({ url: "/video_scenes", array: _this.loopScene })
                    .then(() => {
                        _this.changeStyle(_this.loopScene[0]);
                    });
            }
            _this.style.showVideo = status;
        },
        addIframe(id) {
            $("#" + id).append(
                $(
                    '<iframe src="about:block" frameborder="0" marginheight="0" marginwidth="0" style="position:relative;visibility:inherit;top:0;left:0;width:100%;height:100%;z-Index:-1;opacity: 0;"/>'
                )
            );
        }
    }
};
</script>
