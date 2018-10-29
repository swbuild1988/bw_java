//vm 全局变量
export const VM_TILTE='管廊统一管理可视化平台'; //vm title

export const LOGO_IMAGE='MaxviewLogo'; //vm logo image

export default{
    install:function(Vue,options){

      Vue.prototype.VMConfig = {

          VLC:{//vlc 属性
            VLC_ID:0
        },
          UNDERGROUND_MODE:true,//设置开启地下场景,默认false
          flyManagerProp:{
              flyUrl:'../static/VM/font/flytest.fpf', //飞行路线文件
              flySpeed:100, //飞行管理速度
              flyUpdateTime:1000 //单位ms
          },
          CAMERA:{//相机初始化视角
            longitude:112.49446991184571,
            latitude:37.70536834041335,
            height:121.73914318253694,
            roll:6.28318530714758,
            pitch:-0.7220718086739968,
            heading:5.868990772801154
        },
          weatherConfig:{
            city:310115, //行政区代码 例如:上海市浦东新区 310115
            key:'4bad9021a959fbc3f4709e9fe2fd51da', //高德地图密钥key
            networkAnomaly:0,// 0 代表网络正常，1 代表网络异常
            weathers:[
                {key:'晴',val:'icon_fine'},
                {key:'多云',val:'icon_cloudy'},
                {key:'阴',val:'icon_yin'},
                {key:'小雨',val:'icon_light_rain'},
                {key:'中雨',val:'icon_moderate_rain'},
                {key:'大雨',val:'icon_heavy_rain'},
                {key:'大暴雨',val:'icon_torrential_rain'},
                {key:'阵雨',val:'icon_shower'},
                {key:'雷阵雨',val:'icon_thundershower'},
                {key:'小雪',val:'icon_light_snow'},
                {key:'中雪',val:'icon_moderate_snow'},
                {key:'大雪',val:'icon_heavy_snow'},
                {key:'暴雪',val:'icon_blizzard'},
                {key:'雾',val:'icon_fog'},
                {key:'雷阵雨并伴有冰雹',val:'icon_thundershower_hail'},
                {key:'沙尘暴',val:'icon_dust_storms'},
                {key:'雨夹雪',val:'icon_sleet'},
                {key:'龙卷风',val:'icon_tornado'},
                {key:'霾',val:'icon_haze'},
                {key:'轻雾',val:'icon_mist'},
            ],
        },
          queryParam:{  //数据集查询参数
            dataSourceName: "taiyuan_0823",
            dataSetName: "安全设备",
        },
          detectionObj:{
            analog:[1,4], //模拟量
            sq:[], //开关量
            sa:[41],//状态量
        },
          unitType:[{ //单位类型
            "val": 1,
            "key": "hospital" //医院
        }, {
            "val": 2,
            "key": "school" //学校
        }, {
            "val": 3,
            "key": "institutional_unit" //机关单位
        }, {
            "val": 4,
            "key": "market" //商场
        }, {
            "val": 5,
            "key": "back" //银行
        }, {
            "val": 6,
            "key": "scenic_spot" //景点
        }, {
            "val": 7,
            "key": "airport" //火车站/机场
        }, {
            "val": 8,
            "key": "other" //其他
        }],
          personnelType:[{ //人员类型
            "val": 0,
            "key": "personnel-location" //人员定位
        }],
          flawType:[{ //缺陷类型
            "val": [1,2,4,7],
            "key": "flaw-location1" //缺陷定位
        }],
          videoType:[{ //视屏类型
            "val": 7,
            "key": "video-location" //视屏定位
        }],
          checkPointType:[{
              "val": 0,
              "key": "video-location" //巡检点定位
          }],
          alarmType:[{ //告警类型
                  "val": [1,2,4,7],
                  "key": "alarm2" //告警定位
              }]
      }
    }
  }