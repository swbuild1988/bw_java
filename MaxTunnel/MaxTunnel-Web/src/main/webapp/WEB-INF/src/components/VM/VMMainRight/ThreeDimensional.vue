<template >
    <div class="ThreeDimensionalContainer BackgroundImage">
        <sm-viewer
                id="threeDViewer"
                ref="smViewer"
                :camera="camera"
        ></sm-viewer>
        <vm-title :title-text="title" @click.native="sendAlarms"></vm-title>
        <select class="cd-select" id="list-dropdown" ></select>
        <select class="cd-select" id="area-dropdown" ></select>
    </div>
</template>

<style scoped>
    .ThreeDimensionalContainer >>> .ivu-select{
        position: absolute;
        top: 5%;
        left: 2%;
        width: 18%;
    }
    .ThreeDimensionalContainer >>> .ivu-select-dropdown,.ThreeDimensionalContainer >>> .ivu-select-selection{
        background-color: transparent;
    }
    .ThreeDimensionalContainer  >>> .ivu-select-selection{
        border: 1px solid #001A27;
    }
    .ThreeDimensionalContainer  >>>  .ivu-select-selection{
        border-color: #001A27;
    }
    .ThreeDimensionalContainer  >>> .ivu-select-selection:hover{
        border-color: #001A27;
    }
    .ThreeDimensionalContainer >>> .ivu-select:focus,.ThreeDimensionalContainer >>> .ivu-select-selection{
        border-color: #001A27;
        box-shadow: 0 0 0 2px rgba(0,26,39,.2);
    }
    .ThreeDimensionalContainer  >>>  .ivu-select-item, .ThreeDimensionalContainer  >>>  .ivu-select{
        color: #FFF;
    }
    .ThreeDimensionalContainer .ivu-select-item-selected, .ThreeDimensionalContainer  .ivu-select-item-selected:hover,.ThreeDimensionalContainer  .ivu-select-item:hover{
        background: #001A27;
    }

</style>
<style>
    .cesium-viewer-bottom{
        display:none
    }
    .ThreeDimensionalContainer .Areas{
        left: 20.5%;
    }
</style>

<script>
    import VmTitle from '../VMTitle'
    import SmViewer from '../../Common/3D/3DViewer'
    import Vue from 'vue'

    export default {
        data() {
            return {
                title:'三维系统',
                id:'threeDimensional',
                camera:{
                    longitude:112.49446991184571,
                    latitude:37.70536834041335,
                    height:121.73914318253694,
                    roll:6.28318530714758,
                    pitch:-0.7220718086739968,
                    heading:5.868990772801154
                },
                labelsID:[],
                addLabels:[],
                optionList: [],//所有管廊
                areaList:[],//当前管廊下所有区域
                location:[],//缓存当前管廊下所有区域的位置
                defaultOption: '',
                defaultArea:'',
                showArea:false,
                defaultAreaVal:1,
                switchType:false,
                unitsPosition:{
                    openPosition:true,
                    isShow:true,
                },
            }
        },
        components:{
            VmTitle,
            SmViewer
        },
        mounted(){
            this.init();
        },
        watch:{
            optionList(){
                let _this=this;
                _this.addOption(_this.optionList,'list-dropdown');

                $('#list-dropdown').dropdown( {
                    gutter : 5,
                    stack : false,
                    slidingIn : 100,
                    onOptionSelect(opt){
                        _this.changeStatus(opt.attr('data-value'));
                    }
                } )
            },
            areaList(){
                let _this=this;
                _this.addOption(_this.areaList,'area-dropdown');

                $('#area-dropdown').dropdown( {
                    defaultClass:['cd-dropdown','Areas'],
                    gutter : 5,
                    stack : false,
                    slidingIn : 100,
                    onOptionSelect(opt){
                        _this.changeArea(opt.attr('data-value'));
                    }
                } );
            }
        },
        methods:{
            init(){
                this.fetchData();
                this.changeStatus(this.defaultAreaVal);
            },
            // onload(parent){
            //     var _this=this;
            //
            //     var  Cesium=parent.Cesium;
            //     // 初始化viewer部件_
            //     var viewer = new Cesium.Viewer(this.id,{
            //         infoBox:false //禁用信息框
            //     });
            //
            //     var scene = viewer.scene;
            //     _this.scene=scene;
            //     _this.viewer=viewer;
            //
            //     scene.undergroundMode = this.VMConfig.UNDERGROUND_MODE //设置是否开启地下场景
            //     scene.screenSpaceCameraController.minimumZoomDistance = scene.undergroundMode ? -8 : 0;//设置相机最小缩放距离,距离地表-8米
            //     var widget = viewer.cesiumWidget;
            //
            //     try{
            //       //打开所发布三维服务下的所有图层
            //       var promise = scene.open(URL_CONFIG.BIM_SCP);
            //        //获取相机
            //         var camera=viewer.scene.camera;
            //
            //         // setInterval(()=>{
            //         //     var camera=viewer.scene.camera;
            //         //     var position=camera.position;
            //         //     //将笛卡尔坐标化为经纬度坐标
            //         //     var cartographic = Cesium.Cartographic.fromCartesian(position);
            //         //     var longitude = Cesium.Math.toDegrees(cartographic.longitude);
            //         //     var latitude = Cesium.Math.toDegrees(cartographic.latitude);
            //         //     var height = cartographic.height;
            //         //     console.log(longitude+"/"+latitude+"/"+height);
            //         //     console.log('pitch'+camera.pitch)
            //         //     console.log('roll'+camera.roll)
            //         //     console.log('heading'+camera.heading)
            //         // },10000)
            //       Cesium.when(promise,function(layer){
            //           //设置BIM图层不可选择
            //           layer.forEach(curBIM=>curBIM._selectEnabled=false);
            //           //设置相机位置、视角，便于观察场景
            //           setViewAngle(scene,Cesium,_this.camera)
            //
            //       },function(e){
            //         if (widget._showRenderLoopErrors) {
            //           var title = '加载SCP失败，请检查网络连接状态或者url地址是否正确？';
            //           widget.showErrorPanel(title, undefined, e);
            //         }
            //       });
            //     }
            //     catch(e){
            //       if (widget._groundPrimitives) {
            //         var title = '渲染时发生错误，已停止渲染。';
            //         widget.showErrorPanel(title, undefined, e);
            //       }
            //     }
            //     //滚轮滑动，获得当前窗口的经纬度，偏移角
            //     var handler = new Cesium.ScreenSpaceEventHandler(scene.canvas);
            //     handler.setInputAction(e=>{
            //         addLabel.call(_this,scene,viewer,500,doSqlQuery,URL_CONFIG.BIM_DATA,labelSqlCompleted,processFailed,getSection);
            //     },Cesium.ScreenSpaceEventType.WHEEL)
            //     //鼠标左键松开，获得当前窗口的经纬度，偏移角
            //     // handler.setInputAction(e=>{
            //     //     addLabel.call(_this,scene,viewer,500,doSqlQuery,URL_CONFIG.BIM_DATA,labelSqlCompleted,processFailed,getSection);
            //     // },Cesium.ScreenSpaceEventType.LEFT_UP)
            //
            //
            //     handler.setInputAction(e=>{
            //         // var position=scene.pickPosition(e.position)
            //         // var camera=viewer.scene.camera;
            //         // var cartographic = Cesium.Cartographic.fromCartesian(position)
            //         // var longitude = Cesium.Math.toDegrees(cartographic.longitude);
            //         // var latitude = Cesium.Math.toDegrees(cartographic.latitude);
            //         // var height = cartographic.height;
            //
            //         // console.log(longitude+"/"+latitude+"/"+height);
            //         // console.log('pitch'+camera.pitch)
            //         // console.log('roll'+camera.roll)
            //         // console.log('heading'+camera.heading)
            //     },Cesium.ScreenSpaceEventType.LEFT_CLICK)
            //
            //     flyManager.call(_this,Cesium,scene,this.flyManager) //飞行管理
            // },
            fetchData(){
                let _this=this;
                _this.axios.get('/tunnels')
                    .then(result=>{
                        let { code,data }=result.data;
                        if(code==200){
                            _this.optionList.splice(0)//清空管廊数组
                            data.forEach(tunnel=>_this.optionList.push({value:tunnel.id,label:tunnel.name}));
                        }
                    })
            },
            changeStatus(val){
                let _this=this;

                _this.axios.get(`tunnels/${val}/areas/`)
                    .then(result=>{
                        let { code,data }=result.data;
                        if(code==200){
                            _this.showArea=true;
                            _this.areaList.splice(0)//清空管廊数组
                            _this.location.splice(0)//清空缓存位置

                            if(data){
                                data.reverse().forEach((area,index)=>{
                                    _this.areaList.push({value:index,label:area.name})
                                    _this.location.push(area.location)
                                })
                            }
                        }

                    })
            },
            changeArea(index){
                let _this=this;
                try{
                    let location =_this.location[index].split('/');

                    _this.scene.camera.flyTo({
                        destination : new Cesium.Cartesian3.fromDegrees(parseFloat(location[0]),parseFloat(location[1]),parseFloat(location[2])),
                        orientation : {
                            heading : parseFloat(location[3]),
                            pitch : parseFloat(location[4]),
                            roll : parseFloat(location[5])
                        }
                    })
                }catch (e){

                }

            },
            addOption(optionList,id='cd-dropdown'){
                var $dropdown =$('#'+id), optionList=optionList,optlist='';

                for(let i=0;i<optionList.length;i++){
                    optlist+=`<Option value="${optionList[i].value}" >${optionList[i].label}</Option>`
                }

                $dropdown.empty().append(optlist);

                $dropdown.children('option').eq(0).attr('selected','selected');//设置第一个为默认选中项

            },
            sendAlarms(){

                setTimeout(()=>{
                    this.axios.post('/alarms',
                        {
                            alarmDate:+new Date(),
                            alarmLevel:1,
                            tunnelId:1002,
                            objectId:7001,
                            latitude: "112.49408263714506",
                            longitude: "37.70642444695577",
                            description:'sad',
                            isDistribute:true
                        })
                        .then(err=>console.log(err)).catch(err=>console.log('err2',err))
                },1000)
                // setTimeout(()=>{
                //     this.axios.post('/alarms',
                //         {
                //             alarmDate:+new Date(),
                //             alarmLevel:1,
                //             tunnelId:1002,
                //             objectId:7002,
                //             latitude: "37.70718914626341",
                //             longitude: "112.4940169552599",
                //             description:'sad'
                //         })
                //         .then(err=>console.log(err)).catch(err=>console.log('err2',err))
                // },1000)
                // setTimeout(()=>{
                //     this.axios.post('/alarms',
                //         {
                //             alarmDate:+new Date(),
                //             alarmLevel:1,
                //             tunnelId:1002,
                //             objectId:7003,
                //             latitude: "37.70614188197423",
                //             longitude: "112.4940660989837",
                //             description:'sad'
                //         })
                //         .then(err=>console.log(err)).catch(err=>console.log('err2',err))
                // },1000)
                // setTimeout(()=>{
                //     this.axios.post('/alarms',
                //         {
                //             alarmDate:+new Date(),
                //             alarmLevel:1,
                //             tunnelId:1002,
                //             objectId:7004,
                //             latitude: "37.707189145668366",
                //             longitude: "112.49406611961243",
                //             description:'sad'
                //         })
                //         .then(err=>console.log(err)).catch(err=>console.log('err2',err))
                // },1000)
            },

        }
    }

</script>
