import {
    addEntity,
    computeIntersections,
    _getFieldValues,
} from "../../../../scripts/commonFun";

const addBarnLabel = {
    data(){
        return {
            timer:{
                timeoutId : null,
                intervalId : null,
                sectionId : null, //保留上次section
            },
            labelsArray : []
        }
    },
    computed:{
        detectionObj(){
            return this.VMConfig.detectionObj;
        }
    },
    methods: {
        //添加标签
        addLabel() {
            let args = [].slice.call(arguments); //类数组转换成数组
            let [ dataUrl,sqlQuery,processFailed,wait ] = args; //解析数组内容
            let { viewer } = this;
            let _this = this;

            clearTimeout(_this.timer.timeoutId);
            clearInterval(_this.timer.intervalId);//清除定时更新label集
            _this.timer.timeoutId = setTimeout(() => {
                _this._getSection()
                    .then(result => {

                        if (result.moInfo) {

                            _this.labelsArray.forEach(currEvent => viewer.entities.removeById(currEvent.id));
                            _this.labelsArray.splice(0);//清空当前所有展示label

                            result.moInfo.forEach(label => _this.labelsArray.push(label));

                            let lablesIDArray = _this.labelsArray.map(obj => obj.id);
                            let { startPoint,endPoint } = result.sectionInfo;

                            sqlQuery.call(_this, viewer, 'MOID in (' + lablesIDArray.toString() + ')', dataUrl, _this._labelSqlCompleted, processFailed, startPoint, endPoint, _this.labelsArray);

                            //更新label值
                            _this._updateEntityVal();
                        }
                    });
            }, wait)
        },
        //得到当前section
        _getSection() {
            let _this = this;

            return new Promise((resolve, reject) => {
                //将笛卡尔坐标化为经纬度坐标
                var _cameras = _this.scene.camera;


                var cartographic = Cesium.Cartographic.fromCartesian(_cameras._position);
                var longitude = Cesium.Math.toDegrees(cartographic.longitude);
                var latitude = Cesium.Math.toDegrees(cartographic.latitude);
                var height = cartographic.height;

                if (height < 0) {
                    height = 0;
                }

                _this.axios.post('/sections/gps',
                    {longitude, latitude, height})
                    .then(result => {
                        let {code, data} = result.data;

                        if (code == 200
                            && data != null
                            && _this.timer.sectionId !== data.sectionInfo.id
                        ) {
                            //缓存sectionId用于判断下次取到section是否一致
                            _this.timer.sectionId = data.sectionInfo.id;

                            resolve(data)
                        }
                    })
            })
        },
        //更新实体值/状态
        _updateEntityVal() {

            if (this.labelsArray.length) {

                this.timer.intervalId = setInterval(() => {

                    for (var i = 0; i < this.labelsArray.length; i++) {

                        let updateLabel = this._getLable(this.labelsArray[i].id); //获取当前更新的实体
                        if (!updateLabel) continue;

                        this.axios.get(`/measobjs/${ this.labelsArray[i].id }/${ this.labelsArray[i].dataType }/cache-cv`)
                            .then(result => {

                                let {code, data} = result.data;
                                if (code == 200) {
                                    let [ updateObj ] = data;

                                    if (this.detectionObj.analog.indexOf( updateObj.objtypeId ) != -1 &&
                                        updateObj.cv.toFixed(2) != updateLabel._label._text._value) { //模拟量

                                        updateLabel._label._text._value = updateObj.cv.toFixed(2);
                                    }
                                    if (this.detectionObj.sa.indexOf(updateObj.objtypeId) != -1) { //状态量

                                        let [prevLabel] = this.labelsArray.filter(label => label.id == updateObj.id);

                                        if ( prevLabel.cv != updateObj.cv ) {

                                            let image = updateObj.cv ? 'alarm' : 'normal';
                                            updateLabel.billboard.image = require('../../../../assets/VM/' + image + '.png');
                                            prevLabel.cv == updateObj.cv;
                                        }

                                    }

                                }
                            })
                    }

                }, 10000)
            }
        },
        //获取实体
        _getLable(id) {
            let {viewer} = this;

            if (global.Cesium.defined(viewer)) return viewer.entities.getById(id);

        },
        //sql 查询成功回调
        _labelSqlCompleted(viewer,startLocation,endLocation,labels){
            var _this=this;

            return function (queryEventArgs) {
                var selectedFeatures = queryEventArgs.originResult.features;

                for(let i=0;i<selectedFeatures.length;i++){

                    var geographic=computeIntersections({x:_getFieldValues(selectedFeatures[i],'X'),y:_getFieldValues(selectedFeatures[i],'Y'),z:_getFieldValues(selectedFeatures[i],'Z')},startLocation,endLocation);//得到点到直线的垂直交点经纬度

                    let entityProp = _this._judgeEntityType(labels,selectedFeatures[i],geographic);

                    entityProp &&　addEntity( entityProp );
                }
            }
        },
        //判断实体类型
        _judgeEntityType(labels,selectedFeatures,geographic){

            if( !labels.length ) return false;
            let moTypeId = _getFieldValues(selectedFeatures,'MOTYPEID');

            if( moTypeId == 7 ) return false; //moTypeId = 7 时，为相机对象

            let { viewer } = this;
            let [ currLabel ] = labels.filter( label => label.id == _getFieldValues(selectedFeatures,'MOID')); //获取当前的label

            let object = {
                viewer,
                id:currLabel.id,
                X:(parseFloat(_getFieldValues(selectedFeatures,'X'))+geographic.intersetcLon)/2,
                Y:(parseFloat(_getFieldValues(selectedFeatures,'Y'))+geographic.intersetcLat)/2,
                Z:parseFloat(geographic.adjustHeight),
                show:true
            },
            objectType = null;

            if( this.detectionObj.analog.indexOf( parseFloat( moTypeId ) ) != -1 ){
                objectType = {
                    billboard:{
                        image:'label_alarm',
                        width:100,
                        height:80,
                    },
                    label:{
                        text:currLabel.cv.toFixed(2),
                    },
                }
            }
            if( this.detectionObj.sa.indexOf( parseFloat( moTypeId ) ) != -1 ){
                objectType = {
                    dataTypeName:currLabel.dataTypeName,
                    cv:currLabel.cv,
                    billboard:{
                        image:currLabel.cv ? 'open' :'close',
                        width:134,
                        height:70,
                    },
                }
            }
            Object.assign(object,objectType);

            return object;
        }
    }
};

export {
    addBarnLabel
}