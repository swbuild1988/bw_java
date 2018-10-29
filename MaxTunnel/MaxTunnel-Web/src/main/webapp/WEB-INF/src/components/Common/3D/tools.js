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
        showDesModel(){
            this.model.show.state = true;
        },

        removeByEntityId(entity){
            let { viewer } = this;

            this.modelProp.show.state = false;
            viewer.entities.remove(entity);
        },
        eventNotie(){
            let _this = this

            getJson(`events`).then(data => {

                data.forEach( event => {
                    _this.addEventEntitys(event); //添加事件实体
                    Vue.prototype.IM.addInformation('events',event);
                })

            })
        },
        addEventEntitys(eventObj){
            let { viewer } = this;
            let color = 'red';

            viewer.entities.add( {
                id:eventObj.id,
                _moId:eventObj.id,
                messageType:'events',
                position : Cesium.Cartesian3.fromDegrees( parseFloat(eventObj.longitude), parseFloat(eventObj.latitude), 0 ),
                ellipse : {
                    semiMinorAxis :700000.0 ,
                    semiMajorAxis : 700000.0,
                    height : 0.0,
                    outline : true,
                    outlineColor : Cesium.Color.fromAlpha( Cesium.Color.fromCssColorString( color ), .4 ),
                    material : Cesium.Color.fromAlpha( Cesium.Color.fromCssColorString( color ), .3 )
                }
            } );
        }
    }
}