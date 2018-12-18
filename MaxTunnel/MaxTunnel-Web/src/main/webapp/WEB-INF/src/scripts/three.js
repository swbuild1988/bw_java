/**
 * // 1. 利用笛卡尔坐标设置偏移
 * var center = Cesium.Cartesian3.fromDegrees(-98.0, 40.0);
 * viewer.camera.lookAt(center, new Cesium.Cartesian3(0.0, -4790000.0, 3930000.0));
 *
 * // 2. 利用HeadingPitchRange设置偏移
 * var center = Cesium.Cartesian3.fromDegrees(-72.0, 40.0);
 * var heading = Cesium.Math.toRadians(50.0);
 * var pitch = Cesium.Math.toRadians(-20.0);
 * var range = 5000.0;
 * viewer.camera.lookAt(center, new Cesium.HeadingPitchRange(heading, pitch, range));
 *
 * 这里用的是第二种方法
 */
export function lookAt(viewer, target, heading, pitch, range){
    viewer.camera.lookAt(target, new Cesium.HeadingPitchRange(heading, pitch, range));
}

export function SuperMapSqlQuery(dataUrl, queryParam, SQL){
    return new Promise(function(resolve, reject){

        let getFeatureParam = new SuperMap.REST.FilterParameter({
            attributeFilter: SQL
        });
        let getFeatureBySQLParams = new SuperMap.REST.GetFeaturesBySQLParameters({
            queryParameter: getFeatureParam,
            datasetNames: [queryParam.dataSourceName+":" + queryParam.dataSetName]
        });
        let getFeatureBySQLService = new SuperMap.REST.GetFeaturesBySQLService(dataUrl, {
            eventListeners: {
                // 成功
                "processCompleted": function(queryEventArgs){
                    var selectedFeatures = queryEventArgs.originResult.features;
                    console.log("selected Features", selectedFeatures);
                    var result = [];
                    selectedFeatures.forEach(feature => {
                        result.push({
                            id: parseFloat(getFeatureValue(feature, "MOID")),
                            typeId:  parseFloat(getFeatureValue(feature, "MOTYPEID")),
                            longitude:  parseFloat(getFeatureValue(feature, "X")),
                            latitude: parseFloat(getFeatureValue(feature, "Y")),
                            height: parseFloat(getFeatureValue(feature, "Z")),
                        });
                    });

                    resolve(result);
                },
                // 失败
                "processFailed": function(){
                    console.log("selected fail");
                    reject(null);
                }
            }
        });
        // 进行sql查询
        getFeatureBySQLService.processAsync(getFeatureBySQLParams);
    });

    function getFeatureValue(feature, param){
        for (let index = 0; index < feature.fieldNames.length; index++) {
            const element = feature.fieldNames[index];
            if (element == param) return feature.fieldValues[index];
        }
        return null;
    }
}
