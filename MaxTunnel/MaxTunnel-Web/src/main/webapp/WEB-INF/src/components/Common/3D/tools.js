import Vue from 'vue'
import { getJson } from "../../../scripts/commonFun";

export default {
    data(){
        return {
            modelProp:{ //展示信息模态框
                show:{
                    state:false
                },
                showButton:false, //关闭底部按钮
                tilteProps:{
                    tilte:'详情', //对话框标题
                    backgroundColor:'#2D8CF0'
                },
                data:[],//属性集
            },
            model:{  //描述模态框
                show:{
                    state:false
                },
                tilteProps:{
                    tilte:'清除详情', //对话框标题
                },
            }
        }
    },
    methods:{
        //设置相机视角
        setViewAngle(){
            let { scene,cameraPosition } = this;

            if(Cesium.defined(scene)){
                scene.camera.setView({
                    destination : new Cesium.Cartesian3.fromDegrees(cameraPosition.longitude,cameraPosition.latitude,cameraPosition.height),
                    orientation : {
                        heading : cameraPosition.heading,
                        pitch : cameraPosition.pitch,
                        roll : cameraPosition.roll
                    }
                });
            }
        },
        //隐藏所有实体
        hideAllEntitys(){
            let {　viewer　} = this;

            viewer.entities._entities._array.forEach(entitie => entitie._show = true);
        },
        showDesModel(){
            this.model.show.state = true;
        },
        removeByEntityId(entity){
            let { viewer } = this;

            this.modelProp.show.state = false;
            viewer.entities.remove(entity);
        },
        eventNotie(){
            let _this = this;
            let day = Vue.prototype.VMConfig.searchEventsDay;

            getJson(`events/day/${ day }`).then(data => {
                console.log('data',data);
                data.forEach( event => {
                    _this.addEventEntitys(event); //添加事件实体
                    Vue.prototype.IM.addInformation('events',event);
                })

            })
        },
        addEventEntitys(event){
            let { viewer } = this;
            let height = Vue.prototype.VMConfig.entityHeight;
            let color = event.level == 1 ? '#57a3f3' :( event.level == 2 ? '#f90' : '#ed4014' );

            viewer.entities.add( {
                id:event.id,
                _moId:event.id,
                messageType:'events',
                position : Cesium.Cartesian3.fromDegrees( parseFloat( event.longitude ), parseFloat( event.latitude ), parseFloat( height ) ),
                ellipse : {
                    semiMinorAxis :7.0 ,
                    semiMajorAxis : 7.0,
                    height : height,
                    outline : true,
                    outlineColor : Cesium.Color.fromAlpha( Cesium.Color.fromCssColorString( color ), .8 ),
                    material : Cesium.Color.fromAlpha( Cesium.Color.fromCssColorString( color ), .6 )
                }
            } );
        }
    }
}