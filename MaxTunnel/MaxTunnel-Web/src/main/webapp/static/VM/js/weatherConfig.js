
let config={
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
    winddirections:[
        {key:'东北',val:'northeast'},
        {key:'东',val:'east'},
        {key:'东南',val:'southeast'},
        {key:'南',val:'south'},
        {key:'西南',val:'southwest'},
        {key:'西',val:'west'},
        {key:'西北',val:'northwest'},
        {key:'北',val:'north'},
    ]
}

export default config;