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
            <Dropdown divided @click.native="gesSwitch">
              <a> <img src="../../../assets/VM/genue.png" height="100%" width="100%"></a>
            </Dropdown>
            </Col>
            <Col span="1" offset="1">
            <Dropdown divided @click.native="alarmNotice">
              <a> <img src="../../../assets/VM/roboto.png" height="100%" width="100%"></a>
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
            <Dropdown divided @click.native="logout">
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

      <sm-viewer
              id="mapViewer"
              ref="smViewer"
              :cameraPosition="camera"
              :personnelPosition="personnelPosition"
              :unitsPosition="unitsPosition"
              :defectPosition="defectPosition"
              :searchCamera="searchCamera"
              :eventsPosition="eventsPosition"
              :openVideoLinkage="true"
              :infoBox="false"
              :navigation="false"
              :openImageryProvider="false"
              @replaceVideoUrl="replaceVideoUrl"
      >
      </sm-viewer>
      <describe-model v-bind="model"></describe-model>

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
    .cesium-infoBox{
        background: rgba(3,19,36,0.9);
        box-shadow: 0 0 10px 1px rgba(3,19,36,0.9);
        top:16%;
    }
    .cesium-infoBox-title {
        background: rgba(3,19,36,1);
    }

</style>

<script>

  import SmViewer from '../../Common/3D/3DViewer'
  import Temperature from '../VMBodyCenter/temperatureBox'
  import MapGauges from './gauges'
  import Vue from 'vue'
  import describeModel from '../AlarmManage/DescAlarmModel'

  export default {
    data() {
      return {
          msg: '地图',
          id: 'cesiumContainer',
          show:{
              ges:false,
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
          model:{
              show:{ //默认隐藏
                  state:false
              },
              tilteProps:{
                  tilte:'清除详情', //对话框标题
                  backgroundColor:'#2D8CF0'
              },
          }
      }
    },
      computed:{
          camera(){
              return  this.VMConfig.CAMERA;
          }
      },
    components: {
        SmViewer,
        Temperature,
        MapGauges,
        describeModel
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            let _this=this;
            // Vue.prototype.MQ.openMQ(this.MQCallback);
            _this.getVideos(); //调用视屏接口

        },
      //   onload(parent) {
      //
      //       let Cesium = parent.Cesium,
      //           _this=this;
      //       // 初始化viewer部件
      //       var viewer = new Cesium.Viewer(this.id,{
      //           navigation:false, //关闭导航控件
      //           infoBox:false //禁用信息框
      //       });
      //
      //       _this.viewer=viewer;
      //       _this.Cesium=Cesium;
      //
      //       var scene = viewer.scene,
      //            widget = viewer.cesiumWidget,
      //            imageryLayers = viewer.imageryLayers,
      //            imagery_mec,
      //            queryParam=_this.VMConfig.queryParam;
      //
      //       var provider_mec = new Cesium.SuperMapImageryProvider({
      //           url : URL_CONFIG.IMG_MAP//墨卡托投影地图服务
      //       });
      //        imagery_mec = imageryLayers.addImageryProvider(provider_mec);
      //
      //       try{
      //         //打开所发布三维服务下的所有图层
      //         var promise = scene.open(URL_CONFIG.BIM_SCP);
      //
      //         Cesium.when(promise,function(layer){
      //
      //           //设置相机位置、视角，便于观察场景
      //             setViewAngle(scene,Cesium,_this.VMConfig.CAMERA)
      //
      //             layer.forEach(element => {
      //                 // 结构性框架的要查数据，非结构性框架的不用管了，也不能选择
      //                 if (element.name.indexOf(queryParam.dataSetName) < 0) {
      //                     element.selectEnabled = false;
      //                 } else {
      //                     // 设置查找参数
      //                     element.setQueryParameter({
      //                         url: URL_CONFIG.BIM_DATA,
      //                         dataSourceName: queryParam.dataSourceName,
      //                         dataSetName: queryParam.dataSetName,
      //                         keyWord: "moId"
      //                     });
      //
      //                 }
      //             });
      //             //点击对象查询对象属性
      //             viewer.pickEvent.addEventListener(function(feater){
      //                 console.log('pickEvent',feater)
      //             });
      //
      //              doSqlQuery.call(_this,viewer,'MOTYPEID=7',URL_CONFIG.BIM_DATA,addBillboard,processFailed,'videoType','videos',true) //查询全部相机
      //
      //         },function(e){
      //           if (widget._showRenderLoopErrors) {
      //             var title = '加载SCP失败，请检查网络连接状态或者url地址是否正确？';
      //             widget.showErrorPanel(title, undefined, e);
      //           }
      //         });
      //       }
      //       catch(e){
      //         if (widget._showRenderLoopErrors) {
      //           var title = '渲染时发生错误，已停止渲染。';
      //           widget.showErrorPanel(title, undefined, e);
      //         }
      //       }
      //       //设置鼠标左键单击回调事件，进行视屏联动
      //       viewer.selectedEntityChanged.addEventListener(feater=>{
      //           if(feater != undefined){
      //               console.log('feater',feater)
      //               if(feater._dataTypeName == "状态量"){
      //
      //                   let [ updateLabel ]=viewer.entities._entities._array.filter(label=>label._id==feater._id); //获取当前更新的实体
      //                   var image = !feater.cv ? 'open' :'close';
      //
      //                   updateLabel.billboard.image =require('../../../assets/VM/'+ image +'.png'); //修改告警图片
      //                   updateLabel._cv = !feater.cv; //修改cv值
      //                   return;
      //               }
      //               _this.$store.commit('closeVideoLoop')   //开启视屏监控轮播模式
      //               _this.replaceVideoUrl(feater._moId)
      //           }
      //       })
      //
      //       //滚轮滑动，获得当前窗口的经纬度，偏移角
      //       var handler = new Cesium.ScreenSpaceEventHandler(scene.canvas);
      //       handler.setInputAction(e=>{
      //           addLabel.call(_this,scene,viewer,500,doSqlQuery,URL_CONFIG.BIM_DATA,labelSqlCompleted,processFailed,getSection);
      //       },Cesium.ScreenSpaceEventType.WHEEL);
      //       getEntityProperty.call(_this,scene,Cesium,_this.modelProp,'model-content') //鼠标经过实体时,触发事件
      //
      //       bubble.call(_this,Cesium,scene,viewer,'model-content') //调用气泡
      //       getEntitySet.call(this,{viewer:viewer,url:'relatedunits',show:true,typeMode:'unitType',messageType:'units'}) //单位
      //       // getEntitySet.call(this,{viewer:viewer,url:'actived-locators',show:false,typeMode:'personnelType',messageType:'personnel'}) //人员定位
      //       // getEntitySet.call(this,{
      //       //     viewer:viewer,
      //       //     url:'defects/list',
      //       //     typeMode:'flawType',
      //       //     messageType:'flaw',
      //       //     show:false,
      //       //     dataUrl:URL_CONFIG.BIM_DATA,
      //       //     onQueryComplete:addBillboard,
      //       //     processFailed:processFailed
      //       //     }) //缺陷定位
      //
      //       // setInterval(()=>{
      //       //     var camera=viewer.scene.camera;
      //       //     var position=camera.position;
      //       //     //将笛卡尔坐标化为经纬度坐标
      //       //     var cartographic = Cesium.Cartographic.fromCartesian(position);
      //       //     var longitude = Cesium.Math.toDegrees(cartographic.longitude);
      //       //     var latitude = Cesium.Math.toDegrees(cartographic.latitude);
      //       //     var height = cartographic.height;
      //       //     console.log(longitude+"/"+latitude+"/"+height);
      //       //     console.log('pitch'+camera.pitch)
      //       //     console.log('roll'+camera.roll)
      //       //     console.log('heading'+camera.heading)
      //       // },10000)
      //
      // },
        gesSwitch(){
            let _this=this;

            _this.show.ges=!_this.show.ges;
            _this.show.alermUnit=false;
        },
        homeSwitch(){
            let _this=this;

            _this.show.ges=false; //隐藏气体状态
            _this.$refs.smViewer.setViewAngle(); //恢复到初始化视角
            _this.$refs.smViewer.hideAllEntitys(); //隐藏所有实体

        },
        getVideos(){
            let _this=this;

            _this.axios.get('/videos')
                .then(result=>{
                    let { code, data } = result.data;

                    if(code == 200){
                        _this.allVideos.splice(0); //清空当前数组

                        data.forEach(video=>{_this.allVideos.push({MOID:video.id,latitude:video.latitude,longitude:video.longitude,height:video.height,url:video.url})});
                    }
                })
        },
        replaceVideoUrl(videoId){

            let vlc=document.getElementById(this.VMConfig.VLC.VLC_ID)

            let [ video ]=this.allVideos.filter(video=>video.MOID == videoId);
            if(video == undefined) {
                this.$store.commit('startVideoLoop') //开启视屏监控轮播模式
                return;
            }
            let mrl=video.url;

            vlc.playlist.playItem(vlc.playlist.add(mrl)); //动态修改vlc播放路径
        },
        alarmNotice() {
            this.$Notice.warning({
                title: '接收到一条新告警',
                duration: 3
            });
        },
        MQCallback(message){
            let result = JSON.parse(message.body);
            console.log('result',result)
            if(result){

                this.$Notice.warning({
                    title: '接收到一条新告警',
                    duration: 3
                });
                Vue.prototype.IM.addInformation('alarm',result);
                this.$refs.smViewer.addAlarmEntity(result);
            }
        },
    }
  }

</script>
