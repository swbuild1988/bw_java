<template>
    <div class="allDiv">
       <Row class="conditions">
            <Col span="8" offset="3">
            <span>监测仓:</span>
                <Select style="width:20vw;" v-model="query.storeId">
                  <Option value="null">全部</Option>
                  <Option v-for="item in stores" :value="item.id" :key="item.name">{{ item.name }}
                  </Option>
                </Select>
            </Col>
            <Col span="8">
             <span>区域:</span>
                <Select style="width:20vw;" v-model="query.areaId">
                  <Option value="null">全部</Option>
                  <Option v-for="item in areas" :value="item.id" :key="item.name">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="2">
                <Button type="primary" icon="ios-search" @click="search">查询</Button>
            </Col>
            <Col span="2" offset="1" v-if="!init">
                <Button type="ghost" @click="init = true">返回</Button>
            </Col>
        </Row>
        <Row class="storeInfo" v-if="init">
            <Col span="12" v-for="(store,index) in stores" :key="index">
                <div class="storeCard">
                    <div class="innerCard">
                        <div class="storeName">
                            <Icon type="cube"></Icon>
                            <span>{{store.name}}</span>
                        </div>
                        <div class="storeLineCount">
                            <p v-for="(line,i) in store.value" :key="i" class="lines">{{ line.key }}:{{ line.val }}</p>
                            <!-- <Progress :percent="parseInt(store.value[1].val / store.value[2].val * 100)" class="progress"></Progress> -->
                        </div>
                    </div>
                </div>
            </Col>
        </Row>
        <Row v-if="!init">
            <Col span="15" class="sectionsInfo">
                <Row>
                    <Col span="12" v-for="(cab,index) in cables" :key="index" class="left">
                    <div class="card">
                        <div class="title">
                             <Icon type="ios-keypad" size=15 color="#ff9b00"></Icon>
                            <span>{{cab.name}}</span>
                        </div>
                        <div class="linesInfo">
                             <Tooltip placement="bottom">
                                <i-circle :percent="parseInt(cab.value[1].val / cab.value[0].val * 100)" :size="40">
                                    <span class="demo-Circle-inner" style="font-size:16px">{{ cab.value[1].val }}</span>
                                </i-circle>
                                <div slot="content">
                                   <p v-for="(line,i) in cab.value" :key="i" :class="[{'red':line.key === '已用管线数'},{'green':line.key === '可用管线数'}]">{{ line.key }}:{{ line.val }}</p>
                                </div>
                            </Tooltip>
                        </div>
                        <div class="lineName">
                            <!-- <p v-for="(line,j) in cab.lines" :key="j" @mouseover="showDetails(line.id,index)" @mouseout="curDetailId = ''">{{ line.cableName +' '+ line.contract.customer.company.name}}</p> -->
                            <p v-for="(line,j) in cab.lines" :key="j" @click="isShow = true">{{ line.cableName +' '+ line.contract.customer.company.name}}</p>
                        </div>
                    </div>
                    <div class="pop" v-if="curDetailId === line.id && curDetailIndex === index" v-for="line in cab.lines" :key="line.id" @mouseover="showDetails(line.id,index)" @mouseout="curDetailId = ''">
                        <span @click="curDetailId = ''"><Icon type="ios-close" size=15 class="close"></Icon></span>
                        <h3 class="name">{{ line.cableName }}</h3>
                        <p>管线长度：{{ line.cableLength }}</p>
                        <p>管线状态：{{ line.cableStatusName }}</p>
                        <!-- <p>管线位置：{{ line.cableLocation }}</p> -->
                        <p>客户名称：{{ line.contract.customer.company.name }}</p>
                        <p>联系人：{{ line.contract.customer.contact }}</p>
                        <p>联系电话：{{ line.contract.customer.tel }}</p>
                    </div>
                    <Modal v-model="isShow" :title="line.cableName" v-for="line in cab.lines" :key="line.id" width="300">
                        <p>管线长度：{{ line.cableLength }}</p>
                        <p>管线状态：{{ line.cableStatusName }}</p>
                        <!-- <p>管线位置：{{ line.cableLocation }}</p> -->
                        <p>客户名称：{{ line.contract.customer.company.name }}</p>
                        <p>联系人：{{ line.contract.customer.contact }}</p>
                        <p>联系电话：{{ line.contract.customer.tel }}</p>
                    </Modal>
                    </Col>
                </Row>
            </Col>
            <Col span="8" offset="1" class="bim">
                <v_3DViewer :id="mapId" @onload="onload">
                </v_3DViewer>
            </Col>
        </Row>
        <Row>
            <Col span="15">
                <Page v-if="!init" :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle">
                </Page>
            </Col>
        </Row>
    </div>
</template>
<script>
import { TunnelService } from '../../../../services/tunnelService'
import { SpaceService } from '../../../../services/spaceService'
import Enum from "../../../../../static/Enum.json";
import Vue from 'vue';
import v_3DViewer from '../../../../components/Common/3DViewers'
import {URL_CONFIG} from "../../../../../static/3DMap/js/3DMapConfig"
 import {
    setViewAngle,
    bubble,
    addLabel,
    getSection,
    doSqlQuery,
    labelSqlCompleted,
    processFailed
  } from "../../../../scripts/commonFun.js"

export default {
    data(){
        return{
           tunnelId:'',
           stores:[],
           areas:[],
           sections:[],
           query:{
            storeId:'',
            areaId:'',
            sectionId:''
           },
           page:{
                pageNum: 1,
                pageSize: 10,
                pageTotal: null,
            },
            ids:[],
            cables:[],
            init: true,
            curDetailIndex: '',
            curDetailId: '',
            pageStyle: {
                backgroundColor: 'white',
                textAlign: 'right',
                padding: '10px'
            },
            mapId: "tunnnelMap",
            isShow: false
        }
    },
    components: { v_3DViewer },
    mounted() {
      this.tunnelId = this.$route.params.id;
      this.initData();
    },
    watch: {
      '$route': function () {
        // $route发生变化时再次赋值planId
        this.tunnelId = this.$route.params.id;
        this.initData();
        this.cables='';
        this.init = true;
        this.stores = [];
        this.query.areaId = '';
        },
        'page.pageNum': function(){
            this.page.pageNum = 1
        }
    },
    methods:{
        //加载超图模型
      onload(parent) {
        let _this = this;
        let Cesium = parent.Cesium;

        // 初始化viewer部件
        var viewer = new Cesium.Viewer(this.mapId, {
          navigation: false,//关闭导航控件
          infoBox: false
        });
        var scene = viewer.scene,
          widget = viewer.cesiumWidget,
          imageryLayers = viewer.imageryLayers,
          imagery_mec;
        this.scene = scene;
        var provider_mec = new Cesium.SuperMapImageryProvider({
          url: URL_CONFIG.IMG_MAP //墨卡托投影地图服务
        });
        imagery_mec = imageryLayers.addImageryProvider(provider_mec);

        try {
          //打开所发布三维服务下的所有图层
          var promise = scene.open(URL_CONFIG.BIM_SCP);

          promise.then(function (layer) {
            // 将东西设置成不可选择
            layer.forEach(element => {
              // 结构性框架的要查数据，非结构性框架的不用管了，也不能选择
              if (element.name.indexOf("结构框架") < 0) {
                element.selectEnabled = false;
              } else {
                // 设置查找参数
                element.setQueryParameter({
                  url: URL_CONFIG.BIM_DATA,
                  dataSourceName: "tunnel",
                  dataSetName: "结构框架",
                  keyWord: "SmID"
                });
              }
            });
          });
          //注册鼠标点击事件
          viewer.pickEvent.addEventListener(function (feater) {
          });
          // lay是所有的数据集
          Cesium.when(
            promise,
            function (layer) {
              //设置相机位置、视角，便于观察场景
              setViewAngle(scene, Cesium, _this.camera);
              viewer.pickEvent.addEventListener(function (feature) {
              });
            },
            function (e) {
              if (widget._showRenderLoopErrors) {
                var title =
                  "加载SCP失败，请检查网络连接状态或者url地址是否正确？";
                widget.showErrorPanel(title, undefined, e);
              }
            }
          );
        } catch (e) {
          if (widget._showRenderLoopErrors) {
            var title = "渲染时发生错误，已停止渲染。";
            widget.showErrorPanel(title, undefined, e);
          }
        }

        //滚轮滑动，获得当前窗口的经纬度，偏移角
        var handler = new Cesium.ScreenSpaceEventHandler(scene.canvas);
        handler.setInputAction(e => {
          addLabel.call(_this, scene, viewer, 500, doSqlQuery, URL_CONFIG.BIM_DATA, labelSqlCompleted, processFailed, getSection);
        }, Cesium.ScreenSpaceEventType.WHEEL)

        bubble.call(_this, Cesium, scene, viewer, 'model-content') //调用气泡
      },
        initData() {
            let _this = this
            Promise.all([TunnelService.getStoresByTunnelId(this.tunnelId),SpaceService.getCableCount(this.tunnelId)])
            .then(result=>{
                let store = result[0]
                _this.query.storeId = store[0].id;
                store.forEach(a=>{
                    let temp = {};
                    temp.id = a.id;
                    temp.name = a.name;
                    temp.typeId = a.storeTypeId;
                    temp.value = [];
                    _this.stores.push(temp);
                })
                _this.stores.forEach(store=>{
                    result[1].forEach(name => {
                        if(store.name == name.key){
                          for(let item in name){
                            if(item != 'key'){
                                let temp = {};
                                temp.key = item.slice(0,5);
                                temp.val = name[item];
                                store.value.push(temp)
                            }
                          }
                        }
                    })
                })
            },
            error=>{
                _this.Log.info(error)
            })
            // this.axios.get('tunnels/' + this.tunnelId + '/stores').then(response => {
            //   let {code, data} = response.data;
            //   if (code == 200) {
            //     // this.stores = data;
            //     // console.log(data);
            //     this.query.storeId = data[0].id;
            //     data.forEach(a=>{
            //         let temp = {};
            //         temp.id = a.id;
            //         temp.name = a.name;
            //         temp.typeId = a.storeTypeId;
            //         // temp.value = [{
            //         //     key: '设计管线数',
            //         //     val: 20
            //         // },{
            //         //     key: '已用管线数',
            //         //     val: 4
            //         // },{
            //         //     key: '可用管线数',
            //         //     val: 16
            //         // }];
            //         temp.value = [];
            //         this.stores.push(temp);
            //         console.log(this.stores)
            //     })
            //   }
            // })

            TunnelService.getAreasByTunnelId(this.tunnelId).then(
                result=>{
                    _this.areas = result
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        // changeQuery() {
        //     let params = {
        //          storeId: this.query.storeId,
        //          areaId: this.query.areaId
        //     };
        //     this.axios.post('tunnels/stores/areas/sections/condition',params).then(response =>{
        //         let {code,data} = response.data;
        //         if(code == 200){
        //             this.sections = data;
        //         }
        //     })
        // },
        search(){
            if(!this.query.storeId && !this.query.areaId){
               this.$Message.error("请至少选择一个监测仓或区域");
            }
            let params = {
                storeId: this.query.storeId,
                areaId: this.query.areaId,
                id: this.query.sectionId,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name:''
            };
            let _this = this
            SpaceService.sectionsDatagrid(params).then(
                result=>{
                    _this.init = false;
                    _this.cables = [];
                    _this.ids = [];
                    result.list.forEach(a=>{
                         _this.ids.push(a.id);
                         let temp={};
                         temp.name=a.store.name+a.area.name;
                         temp.id=a.id;
                         temp.value=null;
                         temp.lines=null;
                         _this.cables.push(temp);
                    })
                    SpaceService.getCableCountBysectionIds(_this.ids).then(
                        result=>{
                            result.forEach(a=>{
                                _this.cables.forEach(b=>{
                                    if(b.id==a.id){
                                        b.value=a.val;
                                    }
                                })
                            })
                        },
                        error=>{
                            _this.Log.info(error)
                        })
                    
                    _this.cables.forEach(a=>{
                        SpaceService.getCableInfo(a.id).then(
                            result=>{
                                a.lines = result;
                            },
                            error=>{
                                _this.Log.info(error)
                            })
                    })
                })
            // this.axios.post('sections/datagrid',params).then(res =>{
            //     let {code,data} = res.data;
            //     this.page.pageTotal = data.total;
            //     let _this = this;
            //     if(code == 200){
            //         _this.init = false;
            //         _this.cables = [];
            //         _this.ids = [];
            //         data.list.forEach(a=>{
            //              _this.ids.push(a.id);
            //              let temp={};
            //              temp.name=a.store.name+a.area.name;
            //              temp.id=a.id;
            //              temp.value=null;
            //              temp.lines=null;
            //              _this.cables.push(temp);
            //         })
            //         this.axios.get('tunnels/areas/sections/batch/' + _this.ids+ '/cables-count').then(response =>{
            //             let {code,data} = response.data;
            //             if(code == 200){
            //                 data.forEach(a=>{
            //                     _this.cables.forEach(b=>{
            //                         if(b.id==a.id){
            //                             b.value=a.val;
            //                         }
            //                     })
            //                 })
            //                 console.log(_this.cables[0].value['1'].val)
            //             }
            //         })
            //         _this.cables.forEach(a=>{
            //             this.axios.get('tunnels/areas/sections/' +a.id + '/cables').then(res =>{
            //               let {code,data} = res.data;
            //               if(code == 200){
            //                 a.lines = data;
            //               }
            //             })
            //         })
            //     }
            // })
        },
         handlePage(value) {
            this.page.pageNum = value;
            this.search();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.search();
        },
        showDetails(id,index) {
            this.curDetailId = id;
            this.curDetailIndex = index;
        }
    }
}
</script>
<style scoped>
.sectionsInfo{
    padding: 20px 0 24px 0;
    margin-top: 10px;
    background-color: white;
    height: 66vh;
    overflow-y: auto;
}
.left{
    position:relative;
}
.card{
    border: 1px solid #dddfe1;
    width: 80%;
    margin: 10px 10%;
    padding: 5px 0px;
    border-radius: 4px;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, .2);
    height:200px;
    overflow:auto;
}
.title{
    padding-left: 14px;
    font-size: 20px;
    display: inline-block;
    margin: 4px 0;
}
.linesInfo{
    text-align: center;
    float: right;
    width: 40px;
    height: 40px;
    font-size: 16px;
    margin: 4px 12px;
}
.option{
    float:right;
    padding: 0px 10px 0px 0px;
}
.storeInfo{
    margin-top: 10px;
    padding: 30px;
    background-color: white;
}
.storeName{
    font-size: 30px;
    position: absolute;
    font-weight: bold;
    top: 36%;
    left: 14%;
}
.storeCard{
    margin: 10px 7%;
    height: 28vh;
    width: 86%;
    /*background-color: rgb(244,217,164);*/
   /* border: 1px solid #fff;
    border-radius: 8px;*/
    color: #c6cdd2;
}
.innerCard{
    background: url('../../../../assets/UM/bodyI.png') no-repeat;
    background-size: 100% 100%;
    width: 100%;
    height: 100%;
    position: relative;
}
.green{
   /* color:rgb(25,190,107);*/
}
.red{
   /* color:rgb(255, 102, 0);*/
}
.lines{
    padding: 6px;
}
.bim{
    margin-top: 40px;
    height: 60vh;
}
.lineName{
    font-size: 18px;
    text-align: center;
    cursor:pointer;
    margin-top: 10px;
}
.pop{
    position:absolute;
    left: 10px;
    top: 0px;
    width: 200px;
    height: 230px;
    background-color: rgb(204, 153, 102);
    padding: 10px;
    z-index: 1;
}
.name{
    text-align: center;
    margin:10px;
}
.close{
    float:right;
    cursor: pointer;
}
.storeLineCount{
    position: absolute;
    top: 22%;
    right: 22%;
    font-size: 17px;
    text-align: center;
    
}
.progress{
    width: 60%;
    margin-left: 10%;
    margin-top: 20px;
}
</style>
