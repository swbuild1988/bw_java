<template>
  <div class="Map">
      <div class=mapTop>
          <row type="flex" justify="center" align="bottom">
            <Col span="1">
            <Dropdown divided @click.native="homeSwitch">
              <a> <img src="../../../assets/VM/home.png" height="100%" width="100%"></a>
            </Dropdown>
            </Col>
            <Col span="1" offset="1">
            <Dropdown divided @click.native="personnelPosition.isShow = !personnelPosition.isShow">
              <a> <img src="../../../assets/VM/personnel.png" height="100%" width="100%"></a>
            </Dropdown>
            </Col>
            <Col span="1" offset="1">
            <Dropdown divided @click.native="searchCamera.openSearch = !searchCamera.openSearch">
              <a> <img src="../../../assets/VM/video.png" height="100%" width="100%"></a>
            </Dropdown>
            </Col>
            <Col span="1" offset="1">
            <Dropdown divided @click.native="show.ges = !show.ges">
              <a> <img src="../../../assets/VM/genue.png" height="100%" width="100%"></a>
            </Dropdown>
            </Col>
            <Col span="1" offset="1">
            <Dropdown divided @click.native="show.showNonCleanedCount = !show.showNonCleanedCount">
              <a> <img src="../../../assets/VM/alarm.png" height="100%" width="100%"></a>
            </Dropdown>
            </Col>
            <Col span="1" offset="1">
            <Dropdown divided @click.native="defectPosition.isShow = !defectPosition.isShow">
              <a> <img src="../../../assets/VM/flaw.png" height="100%" width="100%"></a>
            </Dropdown>
            </Col>
            <Col span="1" offset="1">
            <Dropdown divided @click.native="eventsPosition.openPosition = !eventsPosition.openPosition">
              <a> <img src="../../../assets/VM/event.png" height="100%" width="100%"></a>
            </Dropdown>
            </Col>
            <Col span="1" offset="1">
            <Dropdown divided @click.native="sendPlan">
              <a> <img src="../../../assets/VM/emergencies.png" height="100%" width="100%"></a>
            </Dropdown>
            </Col>
            <Col span="1" offset="1">
            <Dropdown divided @click.native="unitsPosition.isShow = !unitsPosition.isShow">
              <a> <img src="../../../assets/VM/address_book.png" height="100%" width="100%"></a>
            </Dropdown>
            </Col>
          </row>
          <row justify="center" type="flex">
            <img src="../../../assets/VM/footLine.png" height="8px" width="85%">
          </row>
      </div>
      <temperature class=maptemperature></temperature>
      <transition
              :enter-active-class="enterClass"
              :leave-active-class="leaveClass">
          <map-gauges v-show="show.ges"></map-gauges>
      </transition>
      <transition
              :enter-active-class="enterClass"
              :leave-active-class="leaveClass">
          <alarm-count v-show="show.showNonCleanedCount"></alarm-count>
      </transition>
      <transition
              :enter-active-class="enterClass"
              :leave-active-class="leaveClass">
          <plan-process v-show="show.showPlan" @showPlan="showPlan"></plan-process>
      </transition>


      <sm-viewer
              id="mapViewer"
              ref="smViewer"
              :cameraPosition="camera"
              :personnelPosition="personnelPosition"
              :unitsPosition="unitsPosition"
              :defectPosition="defectPosition"
              :searchCamera="searchCamera"
              :eventsPosition="eventsPosition"
              :openPlanPosition="openPlanPosition"
              :openVideoLinkage="true"
              :infoBox="false"
              :navigation="false"
              :openImageryProvider="true"
              @replaceVideoUrl="replaceVideoUrl"
      >
      </sm-viewer>
  </div>
</template>
<style scoped>
.mapTop {
    z-index: 1001;
    position: absolute;
    width: 65%;
    height: 8%;
    top: 2%;
    left: 50%;
    transform: translate(-55%);
}
</style>
<style>
.cesium-infoBox {
    background: rgba(3, 19, 36, 0.9);
    box-shadow: 0 0 10px 1px rgba(3, 19, 36, 0.9);
    top: 16%;
}
.cesium-infoBox-title {
    background: rgba(3, 19, 36, 1);
}
</style>

<script>
import SmViewer from "../../Common/3D/3DViewer";
import Temperature from "../VMBodyCenter/temperatureBox";
import MapGauges from "./gauges";
import AlarmCount from "../AlarmManage/NonCleanedCount";
import Vue from "vue";
import mapViewer from "../../Common/3DViewers";
import PlanProcess from "./PlanProcess";

export default {
    data() {
      return {
          msg: '地图',
          id: 'cesiumContainer',
          show:{
              ges:false,
              showNonCleanedCount : false,
              showPlan : false
          },
          enterClass:'animated bounceInDown',
          leaveClass:'animated zoomOut',
          allVideos:[],
          searchCamera:{
              openSearch:true,
              isShow:false,
          },
          unitsPosition:{
              openPosition:false,
              isShow:true,
          },
          personnelPosition:{
              openPosition:false,
              isShow:true,
          },
          defectPosition:{
              openPosition:false,
              isShow:true,
          },
          eventsPosition:{
              openPosition:true,
          },
          openPlanPosition:{
              openPosition:true,
          }
      }
    },
    computed: {
        camera() {
            return this.VMConfig.CAMERA;
        }
    },
    components: {
        SmViewer,
        Temperature,
        MapGauges,
        AlarmCount,
        mapViewer,
        PlanProcess
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            let _this = this;
            // Vue.prototype.MQ.openMQ(this.MQCallback);
            _this.getVideos(); //调用视屏接口
        },
        homeSwitch() {
            let _this = this;

            _this.show.ges = false; //隐藏气体状态
            _this.show.showNonCleanedCount = false; //隐藏未清除告警条数
            _this.$refs.smViewer.setViewAngle(); //恢复到初始化视角
            _this.$refs.smViewer.hideAllEntitys(); //隐藏所有实体
        },
        getVideos() {
            let _this = this;

            _this.axios.get("/videos").then(result => {
                let { code, data } = result.data;

                if (code == 200) {
                    _this.allVideos.splice(0); //清空当前数组

                    data.forEach(video => {
                        _this.allVideos.push({
                            MOID: video.id,
                            latitude: video.latitude,
                            longitude: video.longitude,
                            height: video.height,
                            url: video.url
                        });
                    });
                }
            });
        },
        replaceVideoUrl(videoId) {
            let vlc = document.getElementById(this.VMConfig.VLC.VLC_ID);

            let [video] = this.allVideos.filter(video => video.MOID == videoId);
            if (video == undefined) {
                this.$store.commit("startVideoLoop"); //开启视屏监控轮播模式
                return;
            }
            let mrl = video.url;

            vlc.playlist.playItem(vlc.playlist.add(mrl)); //动态修改vlc播放路径
        },
        MQCallback(message) {
            let result = JSON.parse(message.body);

            if (result) {
                this.$Notice.warning({
                    title: "接收到一条新告警",
                    duration: 3
                });

                Vue.prototype.IM.addInformation("alarm", result);
                Vue.prototype.AM.addAlarm(result);
                this.$refs.smViewer.addAlarmEntity(result);
            }
        },
        sendPlan() {
            this.axios
                .get("/emplans/start/sections/10/process-type/4001")
                .then(result => {
                    let { msg, code, data } = result.data;
                    if (code == 200) {
                    }
                });
        },
        showPlan(status){
            let { show } = this;

            show.showPlan = status;
        }
    }
};
</script>
