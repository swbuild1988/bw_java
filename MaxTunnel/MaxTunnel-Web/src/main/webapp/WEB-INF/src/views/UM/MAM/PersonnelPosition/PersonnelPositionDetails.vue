<template>
    <div>
        <Row class="conditions">
           <!--  <Col span="8" offset="1">
                监测仓:
                <Select style="width:20vw;" v-model="query.storeId">
                    <Option value="">全部</Option>
                    <Option v-for="item in selectList.stores" :value="item.id" :key="item.name">{{ item.name }}
                    </Option>
                </Select>
            </Col>
            <Col span="10" offset="2">
                区域:
                <Select style="width:20vw;" v-model="query.areaId">
                    <Option value="">全部</Option>
                    <Option v-for="item in selectList.areas" :value="item.id" :key="item.name">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="3">
                <Button type="primary" icon="ios-search">查询</Button>
            </Col> -->
            <Col span="23" offset="1">
              <h1>入廊人员信息</h1>
            </Col>
        </Row>
        <Row class="list">
            <Col span="15" class="view" id="GISbox">
                <!-- <sm-viewer id="personnelPositionSMViewer" ref="smViewer"></sm-viewer> -->
                <!-- <sm-viewer id="personnelPositionSMViewer" :cameraPosition="VMConfig.CAMERA"  :personnelPosition="personnelPosition" @onload="onload" style="height:74vh">
                </sm-viewer> -->
                <TestSmViewer :cameraPosition="VMConfig.CAMERA" ref="smViewer"></TestSmViewer>
            </Col>
            <Col span="8" offset="1">
                <Collapse v-model="curPerson" accordion>
                    <Panel v-for="person in personnelInfo" :name="person.id+''" :key="person.id">
                        {{ person.username }}
                        <div slot="content" class="content">
                            <img src="../../../../assets/UM/能耗.png" width="100" height="100">
                            <ul class="perList">
                                <li>所属公司：{{ person.companyName }}</li>
                                <li>手机号：{{ person.phoneNum }}</li>
                                <li>身份证号：{{ person.idCard }}</li>
                            </ul>
                            <Button type="primary" class="call">通话</Button>
                        </div>
                    </Panel>
                </Collapse>
            </Col>
        </Row>
    </div>
</template>
<script>
  import { personnelPositionService } from '../../../../services/personnelPositionService'
//   import SmViewer from "../../../../components/Common/3D/3DViewer"
  import TestSmViewer from "../../../../components/Common/3D/Test3DViewer";
import { TunnelService } from '../../../../services/tunnelService';
//   import { setViewAngle ,bubble ,doSqlQuery , addBillboard,processFailed,getEntitySet,switchShowEntity,getEntityProperty  } from '../../../../scripts/commonFun'

  export default {
      data() {
          return {
            tunnelId: null,
            selectList: {
              stores: null,
              areas: null
            },
            query: {
              storeId: null,
              areaId: null
            },
            curPerson: '1',
            personnelInfo: [
              // {
              //   id: '1',
              //   name: '章散',
              //   figure: '../../../../assets/UM/用户.png',
              //   phoneNum: 13678263529,
              //   idCard: 223911199208102238
              // },
              // {
              //   id: '2',
              //   name: '里斯',
              //   figure: '../../../../assets/UM/用户.png',
              //   phoneNum: 13678263525,
              //   idCard: 213811199203102238
              // },
              // {
              //   id: '3',
              //   name: '王武',
              //   figure: '../../../../assets/UM/用户.png',
              //   phoneNum: 13978263529,
              //   idCard: 223911199208102641
              // },
              // {
              //   id: '4',
              //   name: '朱柳',
              //   figure: '../../../../assets/UM/用户.png',
              //   phoneNum: 18378263525,
              //   idCard: 213811199203107264
              // }
            ]
        }
    },
    components: { 
        // SmViewer
        TestSmViewer 
    },
    beforeRouteLeave(to,from,next){
        if(to.name == 'UMPatrolHomePage' || to.name == '设备管理主页' || to.name == '人员定位详情' || to.name == '设备分配' || to.name == 'UMDetailEquipment' || to.name == '管廊安防监控列表'
            || from.name == 'UMPatrolHomePage' || from.name == '设备管理主页' || from.name == '人员定位详情' || from.name == '设备分配' || from.name == '管廊安防监控列表' || from.name == '管廊安防监控详情'
            || from.name == 'UMDetailEquipment'
        ){
            from.meta.keepAlive = true
            to.meta.keepAlive= true
            this.$destroy()
            next()
        }else{
            from.meta.keepAlive = false
            to.meta.keepAlive = false
            this.$destroy()
            next()
        }
    },
    watch: {
        '$route': function () {
          this.tunnelId = this.$route.params.id
          this.getVisitors()
        }
    },
    mounted() {
        this.tunnelId = this.$route.params.id
        this.$refs.smViewer.startPersonnelPosition()
        this.getVisitors()
        this.setGIS()
    },
    methods: {
        setGIS(){
            var gis = document.getElementById("newID");
            gis.style.display = "block";
            gis.style.position = 'absolute';
            gis.style.top = '0px';
            gis.style.height = '100%';
            gis.style.width = '100%'    
            document.body.removeChild(gis)
            document.getElementById("GISbox").appendChild(gis)
        },
    //     onload(parent) {

    //         let Cesium = parent.Cesium,
    //             _this=this;
    //         // 初始化viewer部件
    //         var viewer = new Cesium.Viewer('personnelPositionSMViewer',{
    //             navigation:false, //关闭导航控件
    //             infoBox:false //禁用信息框
    //         });

    //         _this.viewer=viewer;
    //         _this.Cesium=Cesium;

    //         var scene = viewer.scene,
    //              widget = viewer.cesiumWidget,
    //              imageryLayers = viewer.imageryLayers,
    //              imagery_mec,
    //              queryParam=_this.VMConfig.queryParam;

    //         var provider_mec = new Cesium.SuperMapImageryProvider({
    //             url : this.SuperMapConfig.IMG_MAP//墨卡托投影地图服务
    //         });
    //          imagery_mec = imageryLayers.addImageryProvider(provider_mec);

    //         try{
    //           //打开所发布三维服务下的所有图层
    //           var promise = scene.open(this.SuperMapConfig.BIM_SCP);

    //           Cesium.when(promise,function(layer){

    //             //设置相机位置、视角，便于观察场景
    //               setViewAngle(scene,Cesium,_this.VMConfig.CAMERA)

    //               layer.forEach(element => {
    //                   // 结构性框架的要查数据，非结构性框架的不用管了，也不能选择
    //                   if (element.name.indexOf(queryParam.dataSetName) < 0) {
    //                       element.selectEnabled = false;
    //                   } else {
    //                       // 设置查找参数
    //                       element.setQueryParameter({
    //                           url: this.SuperMapConfig.BIM_DATA,
    //                           dataSourceName: queryParam.dataSourceName,
    //                           dataSetName: queryParam.dataSetName,
    //                           keyWord: "moId"
    //                       });

    //                   }
    //               });
    //               //点击对象查询对象属性
    //               viewer.pickEvent.addEventListener(function(feater){
    //                   console.log('pickEvent',feater)
    //               });

    //                doSqlQuery.call(_this,viewer,'MOTYPEID=7',this.SuperMapConfig.BIM_DATA,addBillboard,processFailed,'videoType','videos') //查询全部相机

    //           },function(e){
    //             if (widget._showRenderLoopErrors) {
    //               var title = '加载SCP失败，请检查网络连接状态或者url地址是否正确？';
    //               widget.showErrorPanel(title, undefined, e);
    //             }
    //           });
    //         }
    //         catch(e){
    //           if (widget._showRenderLoopErrors) {
    //             var title = '渲染时发生错误，已停止渲染。';
    //             widget.showErrorPanel(title, undefined, e);
    //           }
    //         }
    //         //设置鼠标左键单击回调事件，进行视屏联动
    //         viewer.selectedEntityChanged.addEventListener(feater=>{
    //             if(feater != undefined){

    //                 _this.$store.commit('closeVideoLoop')   //开启视屏监控轮播模式
    //                 _this.replaceVideoUrl(feater._id)
    //             }
    //         })


    //         getEntityProperty.call(_this,scene,Cesium,_this.modelProp,'model-content') //鼠标经过实体时,触发事件

    //         bubble.call(_this,Cesium,scene,viewer,'model-content') //调用气泡
    //         getEntitySet.call(this,{viewer:viewer,url:'actived-locators',show:true,typeMode:'personnelType',messageType:'personnel'}) //人员定位
    //   },
      getVisitors() {
          let _this = this
          personnelPositionService.getActiveLocators().then(
            (result)=>{
                  _this.personnelInfo = []
                  result.forEach(locator=>{
                      let temp = {}
                      temp.id = locator.id
                      temp.height = locator.height
                      temp.latitude = locator.latitude
                      temp.longitude = locator.longitude
                      temp.username = locator.owner.name
                      temp.companyName = locator.owner.companyName
                      temp.idCard = locator.owner.idCard
                      temp.phoneNum = locator.owner.phoneNum
                      _this.personnelInfo.push(temp)
                  })
            },(error)=>{
                console.log(error)
            }
            )
      },
    },
    beforeDestroy() {
        this.$refs.smViewer.stopPersonnelPosition()
        var gis = document.getElementById("newID");
        gis.style.display = "none";
        document.getElementById("GISbox").removeChild(gis)
        document.body.appendChild(gis)
    }
  }
</script>
<style scoped>
  .view{
    border: 1px solid #b3b0b0;
    height: 74vh;
  }
  .perList{
    display: inline-block;
    margin-left: 40px;
    list-style-type: none;
    position: relative;
    top: -32px;
  }
  .call{
    position: absolute;
    bottom: 0;
    right: 4px;
  }
  .content{
    position: relative;
    height: 16vh;
  }

</style>
