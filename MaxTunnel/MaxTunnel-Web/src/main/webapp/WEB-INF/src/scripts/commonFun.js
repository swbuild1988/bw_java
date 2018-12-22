
import Vue from 'vue'
import axios from 'axios'

/**
 * 判断浏览器版本中是否存在
 * requestAnimationFrame : 开始动画
 * cancelAnimationFrame : 结束动画
 */
(function() {
    var lastTime = 0;
    var vendors = ['webkit', 'moz'];
    for(var x = 0; x < vendors.length && !window.requestAnimationFrame; ++x) {
        window.requestAnimationFrame = window[vendors[x] + 'RequestAnimationFrame'];
        window.cancelAnimationFrame = window[vendors[x] + 'CancelAnimationFrame'] ||    // Webkit中此取消方法的名字变了
            window[vendors[x] + 'CancelRequestAnimationFrame'];
    }

    if (!window.requestAnimationFrame) {
        window.requestAnimationFrame = function(callback, element) {
            var currTime = new Date().getTime();
            var timeToCall = Math.max(0, 16.7 - (currTime - lastTime));
            var id = window.setTimeout(function() {
                callback(currTime + timeToCall);
            }, timeToCall);
            lastTime = currTime + timeToCall;
            return id;
        };
    }
    if (!window.cancelAnimationFrame) {
        window.cancelAnimationFrame = function(id) {
            clearTimeout(id);
        };
    }
}());

//由[{key:xx,val:[{key:xx,val:xx},{key:xx,val:xx}]},{key:xx,val:[{key:xx,val:xx},{key:xx,val:xx}]},{key:xx,val:[{key:xx,val:xx},{key:xx,val:xx}]}]
//转成{xx:[{name:xx,data:[1,2,3]},{name:xx,data:[1,2,3]}],xx:[xx,xx,xx]}
export function converArrayFun(data,series=[],yData=[],stack=null,type='bar'){

    for(let index=0;index<data.length;index++){
        let item=data[index];

        if(typeof item.val =='object'){
            converArrayFun(item.val,series,yData,stack,type);
            yData.push(item.key);
            continue;
        }

        series.length<data.length ? series.push({name:item.key,data:[item.val],type,stack}):
            series[index].data.push(item.val);
    }
    return {
        yData,
        series
    }
}

/**
 *
 * 添加事件监听
 * element : DOM元素
 * event : 事件名
 * handler : 回调
 * on(window, 'mousemove', this.handleMoveMove);
 */
export const on = (function() {
    if (document.addEventListener) {
        return function(element, event, handler) {
            if (element && event && handler) {
                element.addEventListener(event, handler, false);
            }
        };
    } else {
        return function(element, event, handler) {
            if (element && event && handler) {
                element.attachEvent('on' + event, handler);
            }
        };
    }
})();
/**
 *
 * 移除事件监听
 * element : DOM元素
 * event : 事件名
 * handler : 回调
 * off(window, 'mousemove', this.handleMoveMove);
 */
export const off = (function() {
    if (document.removeEventListener) {
        return function(element, event, handler) {
            if (element && event) {
                element.removeEventListener(event, handler, false);
            }
        };
    } else {
        return function(element, event, handler) {
            if (element && event) {
                element.detachEvent('on' + event, handler);
            }
        };
    }
})();
/**
 *
 * url : 请求路径
 * param : 参数 get为object post为String/空
 */
export const getJson = function(url,param){

    return new Promise((resolve,reject) => {
        let obj = {};

        if( Object.prototype.toString.call( param ) == '[object Object]' ){
            try {
                axios.post(url,Object.assign(obj,param))
                    .then( result=> {
                        let { code, data } = result.data;

                        code == 200 ? resolve(data) : reject('err'+ code)

                    })
            }catch (err){
                reject(err)
            }
        }

        if( Object.prototype.toString.call( param ) == '[object String]' ||  !param){
            try {
                axios.get(url,param)
                    .then( result=> {
                        let { code, data } = result.data;

                        code == 200 ? resolve(data) : reject('err'+ code)

                    })
            }catch (err){
                reject('err'+err)
            }
        }

        return false;
    })
}
/**
 * 设置3D相机角度
 */
export function setViewAngle(){
    let args=[].slice.call(arguments); //类数组转换成数组
    let [ scene,Cesium ,camera ]=args; //解析数组内容

    //设置相机位置、视角
    scene.camera.setView({
        destination : new Cesium.Cartesian3.fromDegrees(camera.longitude,camera.latitude,camera.height),
        orientation : {
            heading : camera.heading,
            pitch : camera.pitch,
            roll : camera.roll
        }
    });
}
/**
 * 相机从当前位置飞行到新的空间位置。
 */
export function flyToMyLocation(flyParam){

    if(typeof flyParam !='object'){ return }

    let duration,maximumHeight;
    let { longitude,latitude,height,roll,pitch,heading }=flyParam.position;

    duration = flyParam.duration || 5;
    maximumHeight = flyParam.maximumHeight || 6;

    flyParam.scene.camera.flyTo({
        destination : new Cesium.Cartesian3.fromDegrees(parseFloat(longitude),parseFloat(latitude),parseFloat(height)),// 设置位置
        orientation : {
            heading : heading,
            pitch : pitch,
            roll : roll
        },
        duration:duration,// 设置飞行持续时间，默认会根据距离来计算
        maximumHeight:maximumHeight,// 相机最大飞行高度
        complete:flyParam.completed,// 到达位置后执行的回调函数
        cancle:flyParam.cancled,// 如果取消飞行则会调用此函数
    })

}
/**
 * 追加定位广告牌
 */
export function addBillboard(viewer,typeMode,messageTypes,showEntity){
    let _this = this;

    return function(queryEventArgs){
        let selectedFeatures = queryEventArgs.originResult.features,
            IM=Vue.prototype.IM,
            entiyParam=null;

        ['videos'].indexOf(messageTypes) != -1 && IM.deleteInformation(selectedFeatures,messageTypes,'ID');

        for(var i=0;i<selectedFeatures.length;i++){

            let [ image ]=_this.VMConfig[typeMode].filter( type => {
                let moTypeId = _getFieldValues(selectedFeatures[i],'MOTYPEID');
                return ( type.val == moTypeId )
                    ||  ( Array.isArray(type.val) &&　type.val.indexOf(parseFloat(moTypeId)) != -1)
            });

            ['videos'].indexOf(messageTypes) != -1 && IM.addInformation(messageTypes,selectedFeatures[i]); //缓存信息

            entiyParam={viewer:viewer,
                moTypeId:_getFieldValues(selectedFeatures[i],'MOTYPEID'),
                moId:_getFieldValues(selectedFeatures[i],'MOID'),
                X:_getFieldValues(selectedFeatures[i],'X'),
                Y:_getFieldValues(selectedFeatures[i],'Y'),
                messageType:messageTypes,
                billboard:{
                    image:image.key,
                    scaleByDistance:new Cesium.NearFarScalar(0,1,3500,0.8),
                },
                show:showEntity}

            addEntity(entiyParam);
        }

    }
}
/**
 * 追加实体
 */
export function addEntity(entiyParam){

    if(typeof entiyParam != 'object')return;

    let width = entiyParam.billboard.width || 30,
        height = entiyParam.billboard.height || 40,
        moId = entiyParam.moId || '0',
        Z = entiyParam.Z || Vue.prototype.VMConfig.entityHeight,
        show = entiyParam.show || false;

    let billboard = entiyParam.billboard != undefined ? {
            image : require('../assets/VM/'+ entiyParam.billboard.image +'.png'),
            width:width,
            height:height,
            verticalOrigin : entiyParam.billboard.verticalOrigin != undefined ? entiyParam.billboard.verticalOrigin : Cesium.VerticalOrigin.BUTTON,
            horizontalOrigin:Cesium.HorizontalOrigin.CENTER,
            scaleByDistance: entiyParam.billboard.scaleByDistance != undefined ? entiyParam.billboard.scaleByDistance : new Cesium.NearFarScalar(0,1,25,0),

        } : undefined,
        label = entiyParam.label != undefined ? {
            text:entiyParam.label.text,
            font:'20px Helvetica',
            fillColor:Cesium.Color.WHITE,
            outlineColor:Cesium.Color.WHITE,
            verticalOrigin : entiyParam.label.verticalOrigin != undefined ? entiyParam.label.verticalOrigin :  Cesium.VerticalOrigin.CENTER,
            scaleByDistance:entiyParam.label.scaleByDistance != undefined ? entiyParam.label.scaleByDistance : new Cesium.NearFarScalar(0,1,25,0),
        } : undefined ;

    entiyParam.viewer.entities.add({
        id:entiyParam.id,
        moId,
        messageType:entiyParam.messageType,
        dataTypeName:entiyParam.dataTypeName,
        cv:entiyParam.cv,
        position: Cesium.Cartesian3.fromDegrees(parseFloat(entiyParam.X),parseFloat(entiyParam.Y),parseFloat(Z)),
        billboard,
        label,
        show
    })
}
/**
 * 获取实体集
 */
export function getEntitySet(setParam){
    let _this=this;
    if(typeof setParam != 'object'){ return }

    _this.axios.get(setParam.url)
        .then(result=>{
            let { code, data } = result.data;
            if(code == 200){
                console.log(setParam.messageType,data)
                let IM= Vue.prototype.IM,
                    sqlQueryBIMId = [];//sqlQueryBIMId;

                IM.deleteInformation(data,setParam.messageType,'id');
                if(Array.isArray(data) && data.length !== 0){

                    for(let i=0;i<data.length;i++){
                        if(data[i].latitude == null || data[i].longitude　== null)continue;

                        let [ type ]=_this.VMConfig[setParam.typeMode].filter(type=>{
                            let moTypeId = _getTypeValue(setParam.typeMode,data[i]);

                            return ( type.val == moTypeId )
                                ||  ( Array.isArray(type.val) &&　type.val.indexOf(parseFloat(moTypeId)) != -1)

                        });

                        IM.addInformation(setParam.messageType,data[i]);//缓存信息
                        if(['flaw'].indexOf(setParam.messageType) != -1 && data[i].type == 2){ //BIM查询
                            sqlQueryBIMId.push(data[i].objId);
                            continue;
                        }

                        addEntity({
                            viewer:setParam.viewer,
                            X:data[i].longitude,
                            Y:data[i].latitude,
                            moId:data[i].id,
                            show:setParam.show,
                            messageType:setParam.messageType,
                            billboard:{
                                image:type.key,
                                height:30,
                                scaleByDistance:['units'].indexOf(setParam.messageType) != -1 ? new Cesium.NearFarScalar(0,1,3500,0.1) : new Cesium.NearFarScalar(0,1,3500,0.8),
                                verticalOrigin : Cesium.VerticalOrigin.BOTTOM,
                            },
                            label: ['units'].indexOf(setParam.messageType) != -1 ?{
                                text:data[i].name,
                                scaleByDistance:new Cesium.NearFarScalar(0,1,3500,0.3),
                                verticalOrigin : Cesium.VerticalOrigin.TOP,
                            } : undefined ,
                        })
                    }

                    if(sqlQueryBIMId.length != 0){
                        doSqlQuery.call(_this,setParam.viewer,'MOID in ('+ sqlQueryBIMId.toString() +')',setParam.dataUrl,setParam.onQueryComplete,setParam.processFailed,setParam.typeMode,setParam.messageType,setParam.show)
                    }

                }

            }
        })
}
/**
 * 切换显示实体
 */
export function switchShowEntity(swtichParam){
    let _this=this,
        moId=null,
        IM=Vue.prototype.IM,
        messageType=IM.getInformation(swtichParam.messageType);

    if(typeof swtichParam != 'object' || messageType.length ==0 ){ return }

    messageType.forEach(currObj=>{

        moId = ['videos'].indexOf(swtichParam.messageType) != -1 ? _getFieldValues(currObj,"MOID")
            : IM._getEntityMoId(currObj,swtichParam.messageType);
        let entities =_this.viewer.entities._entities._array.filter(entitie=>entitie._moId == moId);

        if(entities){

            entities.forEach(entitie=>{
                if(entitie._messageType == swtichParam.messageType)entitie._show = !entitie._show
            })
        }
    })
}
/**
 * SQL 查询
 */
export function doSqlQuery(){
    let args=[].slice.call(arguments); //类数组转换成数组
    let [ viewer,SQL ,dataUrl,onQueryComplete,processFailed,startLocation,endLocation,labels ]=args; //解析数组内容,startLocation,endLocation主要用于section中label location计算
    if(typeof onQueryComplete != 'function' ||　typeof processFailed != 'function'){　return　}


    let _this=this,queryParam=_this.VMConfig.queryParam,getFeatureParam, getFeatureBySQLService, getFeatureBySQLParams;

    getFeatureParam = new SuperMap.REST.FilterParameter({
        attributeFilter: SQL
    });
    getFeatureBySQLParams = new SuperMap.REST.GetFeaturesBySQLParameters({
        queryParameter: getFeatureParam,
        datasetNames: [queryParam.dataSourceName+":" + queryParam.dataSetName]
    });

    getFeatureBySQLService = new SuperMap.REST.GetFeaturesBySQLService(dataUrl, {
        eventListeners: {
            "processCompleted": onQueryComplete.call(_this,viewer,startLocation,endLocation,labels), //成功回调
            "processFailed": processFailed //失败回调
        }
    });

    getFeatureBySQLService.processAsync(getFeatureBySQLParams);
}
/**
 * SQL 查询成功执行函数
 */
export  function onQueryComplete(viewer){
    let _this=this;

    return function(queryEventArgs){
        var selectedFeatures = queryEventArgs.originResult.features;
        viewer.entities.removeAll();

        for(var i=0;i<selectedFeatures.length;i++){

            var value = selectedFeatures[i].fieldValues["0"];
            var feature = selectedFeatures[i];

            for(var j = 0; j < feature.fieldNames.length ; j++){
                var index = j.toString();
                if(j == 0){
                    var des = '<table class="cesium-infoBox-defaultTable"><tbody>' + '<tr><th>' + selectedFeatures[i].fieldNames["0"] + '</th><td>' + selectedFeatures[i].fieldValues["0"] + '</td></tr>';
                }
                else if( j == feature.fieldNames.length - 1){
                    des += '<tr><th>' + selectedFeatures[i].fieldNames[index] + '</th><td>' + selectedFeatures[i].fieldValues[index] + '</td></tr>' + "</tbody></table>";
                }
                else{
                    des += '<tr><th>' + selectedFeatures[i].fieldNames[index] + '</th><td>' + selectedFeatures[i].fieldValues[index] + '</td></tr>';
                }
            }

            viewer.entities.add({
                id:i,
                position :Cesium.Cartesian3.fromDegrees(116.4452364361199+i,39.91946138913283+i,10.2353791933507947),
                billboard :{
                    image : require('../assets/VM/location.png'),
                    width:20,
                    height:30,
                },
                show:true,
                name : selectedFeatures[i].fieldValues["13"],
                description: des
            })
        }
    }
}
/**
 * SQL 查询失败执行函数
 */
export  function  processFailed(queryEventArgs){
    alert('查询失败！');
}
/**
 * 气泡
 */
export function bubble() {
    let args=[].slice.call(arguments); //类数组转换成数组
    let [ Cesium,scene,viewer,className]=args; //解析数组内容
    let scenePosition=null; //保存鼠标点击的地理位置
    let dom =document.getElementsByClassName(className)[0]; //获取要操作的DOM元素
    let _this=this;

    if(!dom){ return }


    var handler = new Cesium.ScreenSpaceEventHandler(scene.canvas);
    handler.setInputAction(function(e) {
        //传入坐标
        scenePosition = scene.pickPosition(e.position);

        var getSelect=viewer.scene.layers.find('安全设备@taiyuan_0823').getSelection(); //根据图层名称查找图层对象,获得选中集
        _this.modelProp.show.state = getSelect.length ? true : false; //根据选中集控制气泡显隐

        _monitor(scene,Cesium,scenePosition,dom); //注册监听函数

    }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

}
/**
 * 鼠标经过实体时，获取实体属性
 */
export function getEntityProperty(){
    let args=[].slice.call(arguments); //类数组转换成数组
    let [ scene,Cesium,modelProp,className ]=args; //场景位置,DOM元素
    let dom =document.getElementsByClassName(className)[0]; //获取要操作的DOM元素
    let IM = Vue.prototype.IM;

    if(!dom){ return }

    var handler = new Cesium.ScreenSpaceEventHandler(scene.canvas);
    handler.setInputAction(function(event) {

        if(!scene.pickPositionSupported){

            return;

        }
        // 获取屏幕坐标
        let scenePosition = scene.pickPosition(event.startPosition);
        // 通过屏幕坐标获取当前位置的实体信息
        let pickedObject = scene.pick(event.startPosition);
        // 如果实体信息存在则说明该位置存在实体
        if(Cesium.defined(pickedObject)){
            // 获取当前点的实体
            let entity = pickedObject.id;

            IM.searchInformation(entity,modelProp);
            _monitor(scene,Cesium,scenePosition,dom); //注册监听函数
        }
    }, Cesium.ScreenSpaceEventType.MOUSE_MOVE);
}
/**
 * 计算两经纬度之前的距离
 */
export function computeDistance() {
    let args=[].slice.call(arguments); //类数组转换成数组
    let [ startLat,startLng,endLat,endLng]=args; //开始经度,纬度,结束经度,纬度。

    var radLat1 = startLat * Math.PI / 180.0;
    var radLat2 = endLat * Math.PI / 180.0;
    var a = radLat1 - radLat2;
    var b = startLng * Math.PI / 180.0 - endLng * Math.PI / 180.0;
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
    s = s * 6378.137;
    s = Math.round(s * 10000) / 10000;
    return s
}
/**
 * 计算点到直线的垂直交点经纬度
 */
export function computeIntersections(coord,startLocation,endLocation) {

    var slope,equationB,equationM,intersetcLon,intersetcLat,adjustHeight;

    //计算点到直线的垂直交点经纬度
    slope = (parseFloat(startLocation.latitude)-parseFloat(endLocation.latitude))/(parseFloat(startLocation.longitude)-parseFloat(endLocation.longitude));

    switch(slope){

        case 0: //极限值，如果slope=0时
            intersetcLon=parseFloat(coord.x);
            intersetcLat=parseFloat(startLocation.latitude);
            break;
        case Infinity:
        case -Infinity: //极限值，如果slope不存在时
            intersetcLon=parseFloat(startLocation.longitude);
            intersetcLat=parseFloat(coord.y);
            break;
        default:
            equationB = (parseFloat(startLocation.latitude)-slope*parseFloat(startLocation.longitude));

            equationM = parseFloat(coord.x) + slope*parseFloat(coord.y);
            intersetcLon=(equationM-slope*equationB)/(slope*slope + 1);
            intersetcLat=slope*intersetcLon+equationB;
            break;
    }

    //计算label高度
    adjustHeight = (coord.z-parseFloat(startLocation.height))*0.7 + parseFloat(startLocation.height);

    return {
        intersetcLon,
        intersetcLat,
        adjustHeight
    }
}
/**
 * 添加label实体
 */
export function addLabel() {
    let args=[].slice.call(arguments); //类数组转换成数组
    let [ scene,viewer,wait,sqlQuery,dataUrl,onQueryComplete,processFailed,callback ]=args; //解析数组内容
    let _this= this;
    let labels = [];
    let lablesID = [];//缓存当前labelsID
    let detectionObj = _this.VMConfig.detectionObj;

    clearTimeout(_this.timer.timeoutId);
    clearInterval(_this.timer.intervalId);//清除定时更新label集
    _this.timer.timeoutId = setTimeout(function(){
        callback.call(_this,scene,viewer)
            .then(result=>{

                if(result.moInfo){

                    lablesID.splice(0);//清空当前所有展示labelID
                    labels.forEach(currEvent => viewer.entities.removeById( currEvent.id ));
                    labels.splice(0);//清空当前所有展示label

                    result.moInfo.forEach(label=>{labels.push(label);lablesID.push(label.id)});

                    sqlQuery.call(_this,viewer,'MOID in ('+ lablesID.toString() +')',dataUrl,onQueryComplete,processFailed,result.sectionInfo.startPoint,result.sectionInfo.endPoint,labels)

                    //更新label值
                    if(labels){
                        _this.timer.intervalId = setInterval(()=>{
                            for(var i=0;i<labels.length;i++){

                                let [ updateLabel ]=viewer.entities._entities._array.filter(label=>label._id==labels[i].id); //获取当前更新的实体
                                if( updateLabel==undefined ){ continue; }

                                _this.axios.get(`/measobjs/${labels[i].id}/${labels[i].dataType}/cache-cv`)
                                    .then(result=>{

                                        let { code, data } = result.data;
                                        if(code==200){
                                            let [ updateObj ] =data;

                                            if(detectionObj.analog.indexOf(updateObj.objtypeId) != -1){ //模拟量

                                                if(updateObj.cv.toFixed(2) != updateLabel._label._text._value){
                                                    updateLabel._label._text._value=updateObj.cv.toFixed(2);
                                                }
                                            }
                                            if(detectionObj.sa.indexOf(updateObj.objtypeId)!= -1){ //状态量

                                                let [ prevLabel ] = labels.filter(label=>label.id==updateObj.id);

                                                if(prevLabel.cv != updateObj.cv){
                                                    prevLabel.cv == updateObj.cv;

                                                    var image = updateObj.cv ? 'alarm' :'normal';
                                                    updateLabel.billboard.image =require('../assets/VM/'+ image +'.png')
                                                }

                                            }

                                        }
                                    })
                            }

                        },10000)
                    }
                }
            });
    },wait)

}
/**
 * 得到当前相机所处的section
 */
export function getSection(scene ,viewer) {
    var _this=this;

    return new Promise((resolve,reject)=>{
        //将笛卡尔坐标化为经纬度坐标
        var _cameras=scene.camera;


        var cartographic = Cesium.Cartographic.fromCartesian(_cameras._position);
        var longitude = Cesium.Math.toDegrees(cartographic.longitude);
        var latitude = Cesium.Math.toDegrees(cartographic.latitude);
        var height = cartographic.height;

        if(height < 0) {
            height = 0;
        }

        _this.axios.post('/sections/gps',
            {longitude,latitude,height})
            .then(result=>{
                let { code, data } = result.data;

                if(code==200
                    &&data!=null
                    &&_this.timer.sectionId!==data.sectionInfo.id
                ){
                    //缓存sectionId用于判断下次取到section是否一致
                    _this.timer.sectionId = data.sectionInfo.id;

                    resolve(data)
                }
            })
    })
}
/**
 * label查询成功回调,进行追加label
 */
export function labelSqlCompleted(viewer,startLocation,endLocation,labels){
    var _this=this;

    return function (queryEventArgs) {
        var selectedFeatures = queryEventArgs.originResult.features,
            moTypeId=null,
            image=null,
            detectionObj=_this.VMConfig.detectionObj;

        for(let i=0;i<selectedFeatures.length;i++){

            let [ currLabel ]=labels.filter(label=>label.id==_getFieldValues(selectedFeatures[i],'MOID')); //获取当前的label

            var geographic=computeIntersections({x:_getFieldValues(selectedFeatures[i],'X'),y:_getFieldValues(selectedFeatures[i],'Y'),z:_getFieldValues(selectedFeatures[i],'Z')},startLocation,endLocation);//得到点到直线的垂直交点经纬度

            moTypeId=_getFieldValues(selectedFeatures[i],'MOTYPEID');

            if(detectionObj.analog.indexOf(parseFloat(moTypeId)) != -1){

                addEntity({viewer:viewer,
                    id:currLabel.id,
                    X:(parseFloat(_getFieldValues(selectedFeatures[i],'X'))+geographic.intersetcLon)/2,
                    Y:(parseFloat(_getFieldValues(selectedFeatures[i],'Y'))+geographic.intersetcLat)/2,
                    Z:parseFloat(geographic.adjustHeight),
                    billboard:{
                        image:'label_alarm',
                        width:100,
                        height:80,
                    },
                    label:{
                        text:currLabel.cv.toFixed(2),
                    },
                    show:true});
            }
            if( detectionObj.sa.indexOf(parseFloat(moTypeId)) != -1 ){
                image = currLabel.cv ? 'open' :'close';

                addEntity({viewer:viewer,
                    id:currLabel.id,
                    dataTypeName:currLabel.dataTypeName,
                    cv:currLabel.cv,
                    X:(parseFloat(_getFieldValues(selectedFeatures[i],'X'))+geographic.intersetcLon)/2,
                    Y:(parseFloat(_getFieldValues(selectedFeatures[i],'Y'))+geographic.intersetcLat)/2,
                    Z:parseFloat(geographic.adjustHeight),
                    billboard:{
                        image:image,
                        width:134,
                        height:70,
                    },
                    show:true});
            }

        }
    }

}
/**
 * 得到数据集中的值
 */
export function _getFieldValues(selectedFeatures,fieldName){

    return Array.isArray(selectedFeatures.fieldNames)?
        selectedFeatures.fieldValues[selectedFeatures.fieldNames.indexOf(fieldName)]:
        selectedFeatures.fieldValues[[].values.call(selectedFeatures.fieldNames).indexOf(fieldName)]
}
/**
 * 内部函数 监听器
 */
function _monitor(){
    let args=[].slice.call(arguments); //类数组转换成数组
    let [ scene,Cesium,scenePosition,dom]=args; //场景位置,DOM元素

    scene.postRender.addEventListener(function () { //注册监听,每帧绘制结束后触发

        var canvasHeight = scene.canvas.height;
        var windowPosition = new Cesium.Cartesian2()
        Cesium.SceneTransforms.wgs84ToWindowCoordinates(  //事件里面把地理坐标点转成屏幕坐标点
            scene,
            scenePosition,
            windowPosition
        )
        dom.style.bottom = canvasHeight - (windowPosition.y - 50) + 'px'
        dom.style.left = windowPosition.x - 70 + 'px'

    })
}
/**
 * 内部函数，获取type值
 */
function _getTypeValue(type,disposeObj){
    let typeValue=null;

    switch(type){
        case 'unitType':
            typeValue=disposeObj.unitType;
            break;
        case 'personnelType':
            typeValue=disposeObj.type;
            break;
        case 'flawType':
            typeValue=disposeObj.type;
            break;
        case 'checkPointType':
            typeValue=disposeObj.type;
            break;
    }
    return typeValue;
}

